define(['app/widgets/grid/app-grid', 'jquery/jquery-ui', 'jquery/jquery.download'], function(){
	var TIME_INTERVAL = 5000;
	var ExpTask = function(){
		this.init();
	}
	
	ExpTask.prototype = {
		init: function(){
			var that = this;
			this.$container = $('<div class="etl-export-task-box" style="z-index:9999;">' +
				'<div class="task-header">' +
					'<span class="task-title">导出任务列表</span>' +
					'<div class="task-btn-list">' +
						'<span id="taskHelpBtn" class="glyphicon glyphicon-question-sign" title="查看帮助"></span>' +
						'<span id="taskSwitchBtn" class="glyphicon glyphicon-minus" title="最小化"></span>' +
						'<span id="taskCloseBtn" title="关闭" class="glyphicon glyphicon-remove"></span>' +
					'</div>' +
				'</div>' +
				'<div class="task-body"><table id="exportTaskGrid"></table></div>' + 
			'</div>');
			
			this.$container.hide().appendTo($.$appPanelContainer);
			this.$table = this.$container.find('#exportTaskGrid');
			this.$helpBtn = this.$container.find('#taskHelpBtn');
			this.$closeBtn = this.$container.find('#taskCloseBtn');
			this.$switchBtn = this.$container.find('#taskSwitchBtn');
			this._initTable();
			this._initEvent();
		},
		_initTable: function(){
			var _self = this;
			this.$table.grid({
				url: 'egov/asset/etl/task/getEtlExportTaskPage.do',
				fitColumns: 'E',
				rownumbers: 'normal',
				pager: 'up',
				height: 200,
				autoLoad: false,
				columns: [[
		            {title: '任务Id', field: 'id', hidden: true},
				    {title: '文件类型', field: 'fileType', hidden: true},
				    {title: '任务名称', field: 'configName', width: 30, align: 'left', showTitle: true},
				    {title: '文件名称', field: 'fileName', width: 45, align: 'left', showTitle: true, formatter:function(val, rowData){
				    	return rowData.fileName + '.' + (rowData.fileType || 'xlsx');
				    }},
				    {title: '下载次数', field: 'downloadCount', width: 15, align: 'center'},
				    {title: '状态', field: 'status', width: 10, align: 'center', 
				    	formatter: function(val){
				    		switch(val.toUpperCase()){
				    		case 'SUCCESS':
				    			return '<i class="exp-icon btn-ok-hover" title="导出成功,点击下载"></i>';
				    		case 'ERROR':
				    			return '<i class="exp-icon btn-close-hover" title="导出失败,点击删除"></i>';
				    		case 'EXECUTING':
				    			return '<img src="resources/egov/js/ext/theme/icons/loading.gif" title="正在导出数据"/>';
				    		case 'WAIT':
				    			return '<img src="resources/egov/js/ext/theme/icons/loading.gif" title="等待导出数据"/>';
			    			default:
			    				return '<img src="resources/egov/js/ext/theme/icons/loading.gif" title="等待导出数据"/>';
				    		}
				    	}
				    }
		        ]],
		        onDblClickRow: function(rowData, rowIndex){
		        	// 如果是导出成功的文件，则双击直接下载
		        	if(rowData.status == 'SUCCESS'){
		        		_self.download(rowData.id);
		        	}
		        },
		        onClickCell: function(rowData, rowIndex, field){
		        	if(field != 'status'){
		        		return false;
		        	}
		        	var $grid = this;
		        	if(rowData.status == 'SUCCESS'){
		        		$messager.confirm('是否下载文件？', {
			        		okCall: function(){
		        				_self.download(rowData.id);
			        		}
			        	});
		        	} else if(rowData.status == 'ERROR'){
		        		$messager.confirm(rowData.remark + '<br>是否删除该记录？', {
			        		okCall: function(){
			        			$A.showWaitScreen('正在删除任务...');
			        			$.ajax({
		            				url: 'egov/asset/etl/task/deleteEtlExportTask.do',
		            				data: {'id': rowData.id, 'configName': rowData.configName},
		            				type: 'post',
		            				dataType: 'json',
		            				success: function(result){
		            					$A.messager.warn(result.message);
		            					if(result.statusCode == '200'){
		            						$grid.reload();
		            					}
		            				},
		            				complete: function(){
		            					$A.hideWaitScreen();
		            				}
		            			});
			        		}
			        	});
		        	}
		        }
			});
		},
		download: function(id){
			$.uiExtend({
				type:'download',
				url: 'egov/asset/etl/download.do',
				options:{
       				httpMethod:'POST',
                    data:{
                	   id: id
                    },
       				onSuccess:function(){
       					$grid.reload();
       				},
       				onFail:function(data){
       					$A.assetMsg.warn(JSON.parse(data).message);
       			    }
				}
			});
		},
		_initEvent: function(){
			var _self = this;
			// 窗体可拖动
			this.$container.draggable({
				handle: '.task-header',
				distance: 20,
				containment: 'body',
				stop: function(event){
					_self.containerTop = _self.$container.css('top'); 
					_self.containerLeft = _self.$container.css('left');
				}
			});
			// 任务栏操作帮助
			this.$helpBtn.click(function(){
				$A.messager.info(
					'1. 图标<i class="btn-ok-hover"></i>表示数据导出成功，双击数据行或点击图标下载文件。<br>' +
					'2. 图标<i class="btn-close-hover"></i>表示数据导出出错，点击图标可删除任务。<br>' +
					'3. 图标<img src="resources/egov/js/ext/theme/icons/loading.gif"/>表示数据正在导出。'
				);
			});
			// 窗口缩放
			this.$switchBtn.click(function(){
				if($(this).hasClass('glyphicon-minus')){
					_self.minimize();
				} else{
					_self.maximize(_self.containerTop, _self.containerLeft);
				}
			});
			// 关闭窗口
			this.$closeBtn.click(function(){
				_self.minimize(function(){
					_self.closeAutoReload();
					_self.$container.hide();
				});
			});
		},
		/**
		 * 开启自动刷新
		 */
		openAutoReload: function(){
			var _self = this;
			
			if(_self.reloadTime){
				clearInterval(_self.reloadTime);
			}
			_self.reloadTime = setInterval(function(){
				var i;
				var rows = _self.$table.grid('getRows');
				for(i = 0; i < rows.length; i++){
					// 如果当期列表页有任务正在执行，则自动刷新
					if(rows[i].status == 'WAIT' || rows[i].status == 'EXECUTING'){
						_self.$table.grid('reload');
						break;
					}
				}
				// 如果没有任务正在执行，则关闭自动刷新
				if(i > rows.length){
					_self.closeAutoReload();
				}
			}, TIME_INTERVAL);
		},
		/**
		 * 关闭自动刷新
		 */
		closeAutoReload: function(){
			clearInterval(this.reloadTime);
		},
		/**
		 * 最大化任务栏
		 */
		maximize: function(top, left, callback){
			var _self = this;
			_self.$container.animate({
				top: top,
				left: left,
				width: 450,
				height: 230
			}, 'normal', 'swing', function(){
				_self.$container.find('.task-body').slideDown();
				_self.$switchBtn.attr({'title': '最小化'}).removeClass('glyphicon-plus').addClass('glyphicon-minus');
				_self.$table.grid('reload');
				if(_self.openReload){
					_self.openAutoReload();
				}
				if(typeof callback === 'function'){
					callback();
				}
			});
		},
		/**
		 * 最小化任务栏
		 * @param callback 动画完成后回调
		 */
		minimize: function(callback){
			var _self = this;
			_self.$container.animate({
				position: 'absolute',
				left: document.body.clientWidth - 200,
				top: document.body.clientHeight - 30,
				width: 200,
				height: 30
			}, 'normal', 'swing', function(){
				_self.closeAutoReload();
				_self.$container.find('.task-body').slideUp();
				_self.$switchBtn.removeClass('glyphicon-minus').addClass('glyphicon-plus').attr('title', '最大化');
				if(typeof callback === 'function'){
					callback();
				}
			});
		},
		/**
		 * 显示任务栏
		 * @param openReload 是否开启网格自动刷新
		 */
		show: function(openReload){
			var _self = this;
			
			this.openReload = openReload;
			if(_self.$switchBtn.hasClass('glyphicon-minus')){
				_self.$container.find('.task-header').addClass('active');
				_self.$container.addClass('active');
			}
			
			_self.$container.show();
			// 初始化显示任务栏
			var top = document.body.clientHeight -230;
			var left = document.body.clientWidth - 480;
			if(_self.$switchBtn.hasClass('glyphicon-minus') && _self.containerTop && _self.containerLeft){
				top = _self.containerTop;
				left = _self.containerLeft;
			}
			
			_self.maximize(top, left, function(){
				_self.containerTop = _self.$container.css('top'); 
				_self.containerLeft = _self.$container.css('left');
				_self.$container.find('.task-header').removeClass('active');
				_self.$container.removeClass('active');
			});
		}
	}
	
	ExpTask.getInstance = function(){
		if(!this.instance){
			this.instance = new ExpTask();
		}
		return this.instance;
	}
	return ExpTask.getInstance();
});
define(['app/app-pagebase',
        'app/widgets/window/app-dialog', 
        'app/widgets/window/app-messager',
        'app/widgets/grid/app-grid'], 
	function(PageBase){
	var _self;
	var RANK_RULE = '2333333333';
	var AssetDiIndex = PageBase.extend({
		initialize : function() {
			AssetDiIndex.superclass.initialize.call(this);
		},
		/**
		 * 根据数据接口分组构建网格
		 * @param gridDatas 按任务类型分组数据列表
		 */
		loadDataInterface: function(gridDatas, taskId){
			var $tree = $('#diTaskDetails_tree').getAppZTree();
			// 清空树节点
			while($tree.getNodes().length){
				$tree.removeNode($tree.getNodes()[0]);
			}
			
			for(var key in gridDatas){
				var nodes = [];	// 父节点
				for(var i = 0; i < gridDatas[key].length; i++){
					nodes.push($.extend(gridDatas[key][i], {nocheck: true}));
				}
				
				$tree.addNodes(null, {
					taskMxId: key, 
					taskMxName: gridDatas[key][0].taskTypeName,
					taskType: gridDatas[key][0].taskType,
					chkDisabled: gridDatas[key][0].ismust,
					open: false,
					checked: taskId == 'OLD_BOSSSOFT_FROM_NEW_BOSSSOFT' && key == 10 ? false : true
				});
				$tree.addNodes($tree.getNodeByParam('taskMxId', key), nodes);
			}
			$tree.expandAll(false);
		},
		/**
		 * 获取选择的任务类型
		 */
		getTaskTypes: function(){
			var $tree = $('#diTaskDetails_tree').getAppZTree();
			var taskTypes = [];
			var nodes = $tree.getNodes();
			for(var i = 0; i < nodes.length; i++){
				if(nodes[i].checked){
					taskTypes.push(nodes[i].taskType);
				}
			}
			return taskTypes;
		},
		/**
		 * 显示数据源配置
		 * @param callback 配置完成回调
		 */
		showDataSourceCfg: function(type, callback){
			$.openModalDialog({
				title: !type ? '数据源配置' : '导入文件上传',
				url: 'egov/asset/di/showDataSourceCfg.do?type=' + type + '&groupId=' + _self.groupId,
				height: !type ? 145 : 250,
				afterShow: function($dlg){
					if(type == 0){
						$A('#dataSourceCfgPage_north').remove();
					} else{
						$A('#dataSourceCfgPage_center').remove();
					}
					
					// 确认根据数据源进行单位对照
					$dlg.find('#dataSourceCfg_okBtn').on('click', function(){
						var data;
						if(type == 0){
							// 获取数据源
							var dataSourceData = $A('#dataSourceCfgPage_form').getSumbitData();
							if(!dataSourceData){
								return false;
							}
							delete _self.attachId;	// 删除附件id
							_self.dataSourceData = dataSourceData.data;
						} else{
							// 验证附件是否有上传
							var fileInfo = $.sliderAttachRequired(null, 'dataSourceCfgPage_north');
							if(fileInfo.required){
								return false;
							}
							if($A('#dataSourceCfgPage_north').find('.file-item').length != 1){
								$A.messager.warn('只能上传一个数据包！');
								return false;
							}
							var attachId = $A('#dataSourceCfgPage_north').find('.file-item').attr('fileid');
							delete _self.dataSourceData;	// 删除数据源配置
							_self.attachId = attachId;
						}
						
						if(_self.orgCompare){
							$dlg.hide();
							_self.showOrgCompare(_self.dataSourceData || {attachId: _self.attachId}, $dlg);
						} else{
							$dlg.closeDialog();
							_self.initOrgCompare(_self.dataSourceData || {attachId: _self.attachId}, $dlg);
						}
					});
				}
			});
		},
		/**
		 * 单位对照
		 */
		showOrgCompare: function(data, $dataSourceDlg){
			$.openModalDialog({
				title: '单位对照',
				url: 'egov/asset/di/showOrgCompare.do',
				width: 1000,
				height: 600,
				afterShow: function($dlg){
					_self.importSuccess = false;
					_self.initOrgCompare(data, $dlg, $dataSourceDlg);
					_self.initOrgCompareBtnEvent($dlg, $dataSourceDlg);
				},
				afterClose: function(){
					if(!_self.importSuccess){
						$dataSourceDlg.showDialog();
					}
				}
			});
		},
		/**
		 * 初始化单位对照网格数据
		 */
		initOrgCompare: function(data, $dlg, $dataSourceDlg){
			$A.showWaitScreen('正在加载数据...');
			$.ajax({
				url: 'egov/asset/di/importOrgCompare.do',
				data: $.extend(data, {importTaskId: _self.taskId}),
				type: 'get',
				dataType: 'json',
				success: function(result){
					if(result.statusCode != 200){
						$A.messager.warn(result.message);
					} else{
						_self.orgCompareData = result.data.clone();
						if(_self.orgCompare){
							$A('#orgCompare_grid').grid('loadData', result.data);
						} else{
							// 不进行单位对照，直接将数据导入
							setTimeout(function(){
								_self.importData(result.data, $dataSourceDlg);
							}, 200);
						}
					}
				},
				error: function(data){
					console.log(data);
				},
				complete: function(){
					$A.hideWaitScreen();
				}
			});
		},
		// 初始化单位对照界面按钮事件
		initOrgCompareBtnEvent: function($dlg, $dataSourceDlg){
			// 还原选中行
			$dlg.find('#orgCompare_resetSelected').on('click', function(){
				$A.messager.confirm('确认还原选中行已填信息？', {
					okCall: function(){
						var $orgCompareGrid = $dlg.find('#orgCompare_grid').data('grid');
						var nodes = $orgCompareGrid.getSelections();
						if(nodes.length == $orgCompareGrid.getAllData().length){
							// 还原所有
							$orgCompareGrid.loadData(_self.orgCompareData);
							return;
						}
						
						for(var i = 0; i < nodes.length; i++){
							var node = _self.orgCompareData[$orgCompareGrid.getRowIndex(nodes[i])];
							$orgCompareGrid.saveRow({
								rowIndex: $orgCompareGrid.getRowIndex(nodes[i]),
								rowData: $.extend($orgCompareGrid.getRowByParam('srcOrgId', nodes[i].srcOrgId), {
									destOrgCode: node.destOrgCode,
									destOrgPid: node.destOrgPid,
									destOrgId: node.destOrgId,
									destOrgPcode: node.destOrgPcode,
									destOrgName: node.destOrgName,
									destOrgRank: node.destOrgRank,
									destOrgIsleaf: node.destOrgIsleaf
								})
							});
						}
					}
				});
			});
			// 清空所有已编辑数据
			$dlg.find('#orgCompare_clearSelected').on('click', function(){
				$A.messager.confirm('确认清空选中行已填信息？', {
					okCall: function(){
						var $orgCompareGrid = $dlg.find('#orgCompare_grid').data('grid');
						var nodes = $orgCompareGrid.getSelections();
						for(var i = 0; i < nodes.length; i++){
							$orgCompareGrid.saveRow({
								rowIndex: $orgCompareGrid.getRowIndex($orgCompareGrid.getRowByParam('srcOrgId', nodes[i].srcOrgId)),
								rowData: $.extend($orgCompareGrid.getRowByParam('srcOrgId', nodes[i].srcOrgId), {
									destOrgCode: '',
									destOrgId: '',
									destOrgPid: '',
									destOrgPcode: '',
									destOrgName: '',
									destOrgRank: '',
									destOrgIsleaf: ''
								})
							});
						}
					}
				});
			});
			
			// 确认导入
			$dlg.find('#orgCompare_okBtn').on('click', function(){
				var $orgCompareGrid = $dlg.find('#orgCompare_grid').data('grid');
				$orgCompareGrid.endEdit($orgCompareGrid.getCurrentEditRowIndex());
				
				// 校验对照数据
				if(_self.validationData && !_self.validationOrgCompare($dlg)){
					return false;
				}
				_self.importData($orgCompareGrid.getAllData(), $dataSourceDlg);
			});
		},
		/**
		 * 检查行数据是否符合规则
		 */
		checkRowData: function(rowData, $dlg){
			if(!_self.validationData || !rowData.destOrgCode){
				return rowData;
			}
			
			var $orgCompareGrid = $dlg.find('#orgCompare_grid').data('grid');
			if(rowData.destOrgCode.indexOf(rowData.destOrgPcode) != 0){
				$A.messager.warn('第' + ($orgCompareGrid.getRowIndex(rowData) + 1) + '行编码不符合规则！');
				return false;
			}
			
			var len = 0;
			for(var i = 0; i < RANK_RULE.length; i++){
				len += parseInt(RANK_RULE.charAt(i));
				if(rowData.destOrgCode.length == len){
					rowData.destOrgRank = i + 1;
					return rowData;
				}
			}
			
			if(rowData.destOrgPcode == 'SJ' && rowData.destOrgCode.length > 3){
				for(var i = 0; i < RANK_RULE.length; i++){
					len += parseInt(RANK_RULE.charAt(i));
					if(rowData.destOrgCode.substr(3).length == len){
						rowData.destOrgRank = i + 1;
						return rowData;
					}
				}
			}
			
			$A.messager.warn('第' + ($orgCompareGrid.getRowIndex(rowData) + 1) + '行编码不符合规则！');
			return false;
		},
		/**
		 * 导入数据
		 */
		importData: function(datas, $dataSourceDlg){
			$A.messager.confirm('导入将覆盖已有数据，确认导入？', {
				okCall: function(){
					$A.showWaitScreen('正在清除数据...');
					_self.curProgress = 0;
					setTimeout(function(){
						_self.startProgressListener();
					}, 2000);
					
					$.ajax({
						url: 'egov/asset/di/dataImport.do',
						data: $.extend({
							importTaskId: _self.taskId,
							reportQj: _self.reportInterval,
							attachId: _self.attachId,
							importTaskTypes: _self.getTaskTypes().join(','),
							orgCompareList: JSON.stringify(datas)
						}, _self.dataSourceData),
						type: 'post',
						dataType: 'json',
						success: function(result){
							if(result.statusCode == 200){
								_self.importSuccess = true;
								_self.showWaitScreenText(100, 1500);
								setTimeout(function(){
									$.closeDialog();
									$dataSourceDlg.closeDialog();
									$A.hideWaitScreen();
									$A.messager.success('导入成功！');
								}, 1500);
							} else{
								$A.hideWaitScreen();
								$A.messager.warn(result.message);
							}
						},
						error: function(result){
							$A.hideWaitScreen();
							$A.messager.warn(result.responseJSON.message);
						},
						complete: function(){
							clearTimeout(_self.progressTime);
						}
					});
				}
			});
		},
		/**
		 * 开启导入导出进度监听
		 */
		startProgressListener: function(){
			if(_self.curProgress && _self.curProgress >= 100){
				return false;
			}
			$.ajax({
				url: 'egov/asset/di/getCurProgress.do',
				type: 'post',
				dataType: 'json',
				success: function(result){
					var time = 5000;
					if(result.data < 0){
						$A.showWaitScreen(result.message);
					} else if(result.data == 200){
						$A.hideWaitScreen();
						$A.messager.success('导出成功！');
						return false;
					} else if(result.data == 300){
						$A.hideWaitScreen();
					} else{
						if(_self.curProgress == 0){
							$A.showWaitScreen('开始导入数据...');
							time = 6000;
							setTimeout(function(){
								_self.showWaitScreenText(result.data, 4000);
							}, 1500);
						} else{
							_self.showWaitScreenText(result.data, 4500);
						}
					}
					if(_self.progressTime){
						clearTimeout(_self.progressTime);
					}
					_self.progressTime = setTimeout(_self.startProgressListener, time);
				}
			});
		},
		/**
		 * 定时刷新显示进度
		 * @param val 目标进度
		 * @param time 所需达到进度的时间
		 */
		showWaitScreenText: function(val, time){
			var sub = val - (_self.curProgress < 0 ? 0 : _self.curProgress);
			if(sub <= 0){
				return false;
			}
			var progressInterval = setInterval(function(){
				_self.curProgress += 1;
				_self.setWaitScreenText();
				if(_self.curProgress >= val){
					_self.curProgress = val;
					clearInterval(progressInterval);
				}
			}, time/sub);
		},
		/**
		 * 设置显示导入导出进度
		 */
		setWaitScreenText: function(){
			if(_self.curProgress >= 0 && _self.curProgress < 10){
				$('#waitScreenMsg').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + _self.curProgress + '%');
			} else if(_self.curProgress >= 10 && _self.curProgress < 100){
				$('#waitScreenMsg').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + _self.curProgress + '%');
			} else if(_self.curProgress >= 100){
				$('#waitScreenMsg').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;100%');
			}
		},
		/**
		 * 校验对照数据
		 */
		validationOrgCompare: function($dlg){
			var $orgCompareGrid = $dlg.find('#orgCompare_grid').data('grid');
			var datas = $orgCompareGrid.getAllData();
			for(var i = 0; i < datas.length; i++){
				var data = datas[i];
				// 验证数据是否填写完整
				if(!data.destOrgCode || !data.destOrgName || !data.destOrgPcode){
					$A.messager.warn('第' + ($orgCompareGrid.getRowIndex(data) + 1) + '行数据填写不完整!');
					return false;
				}
				
				// 验证编码是否重复
				if(data.destOrgCode && !_self.checkOrgCodeIsRepeat(data, $dlg)){
					return false;
				}
				// 验证编码是否符合规则
				if(!_self.checkRowData(data, $dlg)){
					return false;
				}
			}
			return true;
		},
		/**
		 * 检测对照单位编码是否重复
		 */
		checkOrgCodeIsRepeat: function(rowData, $dlg){
			var $orgCompareGrid = $dlg.find('#orgCompare_grid').data('grid');
			var datas = $orgCompareGrid.getAllData();
			for(var i = 0; i < datas.length; i++){
				if(rowData.destOrgCode === datas[i].destOrgCode && rowData.__rowId != datas[i].__rowId){
					$A.messager.warn('第【' + ($orgCompareGrid.getRowIndex(rowData) + 1) + '，' +
							($orgCompareGrid.getRowIndex(datas[i]) + 1) +
						'】行相同的编码【' + rowData.destOrgCode + '】！')
					return false;
				}
			}
			return true;
		},
		listeners: {
			impBtn: {
				click: function(){
					// 校验导出选项
					var dataSourceData = $A('#assetDiPage_form').getSumbitData();
					if(!dataSourceData){
						return false;
					}
					
					var taskTypes = _self.getTaskTypes();
					if(!taskTypes.length){
						$A.messager.warn('请选择导入内容！');
						return false;
					}
					
					_self.orgCompare = $A('#orgCompare').combobox('getValue');
					_self.validationData = $A('#validationData').combobox('getValue');
					// _self.reportInterval = $A('#reportInterval').combobox('getValue');
					_self.autoValidation = $A('#autoValidation').combobox('getValue');
					// 根据接口类型
					var taskNode = $A('#assetDiTask').combobox('getSelectedNode');
					var type = taskNode.taskSrcConn.toUpperCase();
					
					switch(type){
						case 'ORACLE': case 'MYSQL': case 'MSSQLNATIVE': {
							_self.showDataSourceCfg(0);
						} break;
						case 'DZP': {
							_self.showDataSourceCfg(1);
						} break;
					}
				}
			},
			expBtn: {
				click: function(){
					// 校验导出选项
					var sumbitData = $A('#assetDiPage_form').getSumbitData();
					if(!sumbitData){
						return false;
					}
					
					var taskTypes = _self.getTaskTypes();
					if(!taskTypes.length){
						$A.messager.warn('请选择导出内容！');
						return false;
					}
					
					$A.messager.confirm('确认导出？', {
						okCall: function(){
							$A.showWaitScreen('正在导出数据...');
							$.uiExtend({
								type: 'download',
								url: 'egov/asset/di/export.do?' 
									+ 'exportTaskId=' + $A('#assetDiTask').combobox('getValue') 
									+ '&billDate=' + $A('#endDate').datetime('getValue')
									+ '&orgCodes=' + JSON.stringify($A('#orgSelect').comboztree('getValue').split(','))
									+ '&exportTaskTypes=' + JSON.stringify(taskTypes),
								options: {
									successCallback: function(){
										$A.hideWaitScreen();
									},
									failCallback: function(responseHtml, url, error){
										$A.messager.warn(error);
										$A.hideWaitScreen();
									}
								}
							});
							
							setTimeout(function(){
								_self.startProgressListener();
							}, 2000);
						}
					});
				}
			},
			assetDiTask: {
				onChange: function(taskId){
					var node = this.getSelectedNode();
					if(!node || !taskId || node.taskType == '0'){
						// 未选择或导出
						_self.initForm(0);
					} else{
						// 导入
						_self.initForm(1);
					}
					_self.taskId = taskId;
					
					$A.showWaitScreen('正在加载数据...');
					// 获取任务明细
					$.ajax({
						url: 'egov/asset/di/getDiTaskDetailsList.do',
						data: {taskId: taskId},
						type: 'get',
						dataType: 'json',
						success: function(taskDetailsList){
							var gridDatas = {};
							for(var i = 0; i < taskDetailsList.length; i++){
								if(!gridDatas[taskDetailsList[i].taskType]){
									gridDatas[taskDetailsList[i].taskType] = [];
								}
								gridDatas[taskDetailsList[i].taskType].push(taskDetailsList[i]);
							}
							_self.loadDataInterface(gridDatas, taskId);
						},
						error: function(){
							$A.messager.warn(result.responseJSON.message);
						},
						complete: function(){
							$A.hideWaitScreen();
						}
					});
				}
			}
		},
		/**
		 * 初始化表单信息
		 */
		initForm: function(type){
			// $A('#dataProcessMode').combobox('readonly', !type);
			$A('#validationData').combobox('readonly', !type);
			$A('#orgSelect').comboztree('readonly', !!type);
			$A('#endDate').datetime('readonly', !!type);
			$A('#orgCompare').combobox('readonly', !type);
			$A('#autoValidation').combobox('readonly', !type);
			$A('#autoValidation').parents('tr').hide();
			if(!type){
				$A('#expBtn').show();
				$A('#impBtn').hide();
				$A('#orgSelect').comboztree('setValue', '');
				$A($A('.panel-title')[0]).text('导出内容');
				$A($A('.panel-title')[1]).text('导出选项');
			} else{
				$A('#expBtn').hide();
				$A('#impBtn').show();
				$A('#orgSelect').comboztree('setValue', '1');
				$A('#orgSelect').comboztree('setText', '');
				$A($A('.panel-title')[0]).text('导入内容');
				$A($A('.panel-title')[1]).text('导入选项')
			}
		},
		initPage: function(data){
			_self = AssetDiIndex.getInstance();
			_self.data = data;
			_self.groupId = new Date().getTime();
			
			// 报表年份区间，自16年至今
			var reportIntervalData = [];
			var year = new Date().getFullYear();
			for(var i = 2016; i < year; i++){
				reportIntervalData.push({
					reportIntervalCode: i,
					reportIntervalName: i + '年'
				});
			}
			
			$A('#endDate').datetime('setValue', 20171231);
			_self.initForm();
			
			// $A('#reportInterval').combobox('loadData', reportIntervalData);
		}
	});
	
	AssetDiIndex.getInstance = function(){
		if(!this.instance){
			this.instance = new AssetDiIndex();
		}
		return this.instance;
	}
	
	return AssetDiIndex.getInstance();
});
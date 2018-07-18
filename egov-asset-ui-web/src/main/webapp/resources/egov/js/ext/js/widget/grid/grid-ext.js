/**
 * 网格扩展
 * 1.自定义列显隐设置
 * 2.自定义列排序设置
 */
define(['resources/egov/js/ext/js/base/ext-base',
        'resources/egov/js/ext/js/etl/etl-helper',
        'resources/egov/js/ext/js/etl/etl-export-task',
        'resources/egov/js/ext/js/widget/grid/grid-xquery-ext',
        'resources/egov/js/common/utils/Tools',
        'app/widgets/window/app-dialog'], 
        function(ExtBase, EtlHelper, EtlExportTask, QueryExt){
	var GridExt = ExtBase.extend({
		max_level: 1,	// 记录网格标题行数
		init: function(config){
			// 保存默认配置
			if(config){
				$.extend(this, $.extend({}, config));
			}
			// 保留网格事件
			if(this.gridOptions){
				$.extend(this.gridOptions.options, this.gridOptions.events);
			}
			
			// 网格是否渲染过
			if(this.gridOptions && this.gridOptions.options && !this.gridOptions.options.isRender){
				this.gridOptions.$container = this.gridOptions.$container || $('#' + this.gridId).parent();
				$('#' + this.gridId).remove();
				this.default_options = $.extend({}, this.gridOptions.options);
			
				this.initOptions();
			} else{
				this.initToolBtn();
			}
			
			return this;
		},
		/**
		 * 读取网格配置并进行初始化
		 */
		initOptions: function(){
			var _self = this;
			if(_self.gridOptions){
				$.ajax({
					url: 'ext/grid/getGridCfg.do',
					data: {
						gridId: _self.gridId,
						bizType: _self.bizType
					},
					async: false,
					dataType: 'json',
					success: function(temp){
						// 用户当前网格配置信息是否存在
						if(temp && temp.listObj.length && temp.oper){
							// 设置网格配置
							_self.currGridCfg = temp.oper;	// 当前面板选择配置
							_self.gridOptions.cfgId = temp.oper.cfgId;	// 当前使用网格配置
							_self.gridOptions.cfgName = temp.oper.cfgName;
							_self.gridOptions.options.viewType = JSON.parse(temp.oper.attr || "{}").viewType 
								|| _self.gridOptions.options.viewType;
							
							// 重新初始化网格列
							_self._initColumn(temp.oper);
						} else{
							_self.currGridCfg = {cfgId: -1, cfgName: '默认配置'};	// 当前选择的配置
							_self.gridOptions.cfgName = '默认配置';
							_self.gridOptions.cfgId = -1;
						}
						
						// 重新初始化网格
						_self.resetGrid();
					},
					error: function(){
						$A.messager.error('服务器出错！');
					}
				});
			} else{
				// 重新初始化网格
				_self.resetGrid();
			}
		},
		/**
		 * 初始化网格列配置
		 */
		_initColumn: function(grid){
			var cols = [], row = [];
			
			for(var i = 1; i <= grid.levelCount; i++){
				row = [];
				for(var j = 0; j < grid.columns.length; j++){
					if(grid.columns[j].colLevel == i){
						col = this._getColumn(grid.columns[j]);
						if(!!col){
							row.push(col);
						}
					}
				}
				cols.push(row);
			}
			this._changeColumn(grid, cols);
			
			this.gridOptions.options.columns = cols;
		},
		/**
		 * 如果存在列变动，将变动的列添加至网格最后面
		 */
		_changeColumn: function(grid, cols){
			var cls = this.gridOptions.options.columns;
			var i, j, n;
			
			for(var i = 0; i < cls.length; i++){
				for(var j = 0; j < cls[i].length; j++){
					for(n = 0; n < grid.columns.length; n++){
						if(cls[i][j].field == grid.columns[n].field){
							break;
						}
					}
					// 隐藏新增的列数据
					if(n >= grid.columns.length){
						cols[0].push($.extend({}, cls[i][j], {
							rowspan: grid.levelCount,
							colspan: null,
							hidden: 1
						}));
					}
				}
			}
		},
		_getColumn: function(col){
			var cols = this.gridOptions.options.columns;
			for(var i = 0; i < cols.length; i++){
				for(var j = 0; j < cols[i].length; j++){
					if(col.field == cols[i][j].field || col.field == cols[i][j].id){
						return $.extend({}, cols[i][j], col);
					}
				}
			}
			return null;
		},
		/**
		 * 显示自定义网格设置页面
		 */
		showGridCfgView: function(){
			var _self = this;
			$.openModalDialog({
				title: '网格配置',
				mode: 'html',
				width: 600,
				height: _self.cardView ? 515 : 475,
				url: _self._getPanel(),
				afterShow: function(dlg){
					var $header = $('.custom-grid-header');
					
					_self.currGridCfg.cfgId = _self.gridOptions.cfgId;
					_self.currGridCfg.cfgName = _self.gridOptions.cfgName;
					
					if(!_self.gridOptions.cardView){
						$header.find('.view-type').hide();
					} else{
						// 视图选择配置
						$('#viewType').combobox({
							width: 175,
							panelheight: 60,
							tips: '请选择视图类型',
							textfield: 'text',
							valuefield: 'value',
							value: _self.gridOptions.options.viewType || 'GridView',	// 默认网格视图
							data: [{value: 'GridView', text: '网格视图'}, {value: 'CardView', text: '卡片视图'}]
						});
						$('#viewType').parent().css('border', '1px solid #62a9f1');
					}
					
					// 模板选择配置
					_self.initTemplateCfg(dlg);
					_self.initCustomPanel(dlg, _self.gridOptions.options.columns);
					// 初始化面板按钮事件
					_self._initBtnEvent(dlg);
				}
			});
		},
		initTemplateCfg: function(dlg){
			var _self = this;
			
			$('#gridCfgTemplate').combobox({
				width: 175,
				panelheight: 200,
				panelwidth: 250,
				textfield: 'cfgName',
				valuefield: 'cfgId',
				action: 'ext/grid/getGridCfgList.do?gridId=' + _self.gridId + '&bizType=' + _self.bizType,
				text: _self.gridOptions.cfgName,
				value: _self.gridOptions.cfgId,
				afterSelected: function(node){
					if(node.cfgId == _self.currGridCfg.cfgId){
						return false;
					}
					_self.currGridCfg = node;
					
					var columns = [];
					if(node.cfgId != -1){
						columns = [];
						if(node.levelCount == 1){
							columns.push(node.columns);
						} else{
							var cols = node.columns;
							for(var i = 1; i <= node.levelCount; i++){
								var row = [];
								for(var j = 0; j < cols.length; j++){
									if(cols[j].colLevel == i){
										row.push(cols[j]);
									}
								}
								columns.push(row);
							}
						}
						if(_self.gridOptions.cardView){
							$A('#viewType').combobox('setValue', !node.attr ? 'GridView' : JSON.parse(node.attr).viewType);
						}
					} else{
						columns = _self.default_options.columns;
						if(_self.gridOptions.cardView){
							$A('#viewType').combobox('setValue', 'GridView');
						}
					}
					_self.initCustomPanel(dlg, columns);
				},
				addHoverDom: function($node, node){
					if(node.cfgId == -1){
						return false;
					}
					
					$.comboItemAddHover($('#gridCfgTemplate'), $node, {
						params: node,
						viewBtn: false,
						addBtn: false,
						editTitle: '修改名称',
						editCallback: function(){
							_self.showEditCfgName(node);
						},
						delTitle: '删除模板',
						delCallback: function(){
							$A.messager.confirm('确认删除【' + node.cfgName + '】？', {
								okCall: function(){
									$A.showWaitScreen('正在删除......');
									$.ajax({
										url: 'ext/grid/deleteGridCfg.do',
										data: $.extend({}, node, {columns: []}),
										type: 'post',
										dataType: 'json',
										success: function(result){
											if(result.statusCode == 200){
												$A.messager.warn('模板【' + node.cfgName + '】删除成功');
												dlg.find('#gridCfgTemplate').combobox('reload', 
													'ext/grid/getGridCfgList.do?gridId=' + _self.gridId + '&bizType=' + _self.bizType);
												dlg.find('#gridCfgTemplate').combobox('setValue', 0);
												dlg.find('#gridCfgTemplate').combobox('setText', '');
											} else{
												$A.messager.warn(result.message);
											}
										},
										complete: function(){
											$A.hideWaitScreen();
										}
									});
								}
							})
						}
					});
				},
				removeHoverDom: function($node, node){
					if(node.cfgId == -1){
						return false;
					}
					$.comboItemRemoveHover($node);
				}
			});
			$('#gridCfgTemplate').parent().css('border', '1px solid #62a9f1');
		},
		initCustomPanel: function(dlg, columns){
			var _self = this;
			var leftColumns = [], rightColumns = [],
			leftCols = [], rightCols = [], i, j;
			
			// 清空面板
			dlg.find('.item-list').attr('class', 'item-list').empty();
			
			_self.disableColumns = [];	// 存放禁止用户操作的列信息
			for(i = 0; i < columns.length; i++){
				leftCols = [], rightCols = [];
				for(j = 0; j < columns[i].length; j++){
					// 如果网格删除列，需要过滤掉不存在的列
					if(_self.existColumn(columns[i][j])){
						if(_self.gridOptions.disableFields && _self.gridOptions.disableFields.indexOf(columns[i][j].field || columns[i][j].id) != -1){
							_self.disableColumns.push(columns[i][j]);
						} else if(columns[i][j].hidden){
							leftCols.push(columns[i][j]);
						} else{
							rightCols.push(columns[i][j]);
						}
					}
				}
				if(leftCols.length > 0){
					leftColumns.push(leftCols);
				}
				if(rightCols.length > 0){
					rightColumns.push(rightCols);
				}
			}
			
			// 如果网格新增列，需要把新增的列加入待选区
			if(leftColumns.length > 0){
				var cols = _self.getColumns(columns);
				for(var i = 0; i < cols.length; i++){
					leftColumns[0].push(cols[i]);
				}
			} else{
				leftColumns.push(_self.getColumns(columns));
			}
			
			// 初始化列表选项
			_self._initItemList(dlg.find('#leftList'), leftColumns);
			_self._initItemList(dlg.find('#rightList'), rightColumns);
			
			_self._initItemEvent(dlg);
			
			// 左右列表拖动选择
			dlg.find('.item-list,.child-item-list').sortable({
				placeholder: 'item-placeholder',
				connectWith: '.item-list,.child-item-list',
				revert: true
			}).droppable({
				accept: '.item',
				hoverClass: 'item-list-hover',
				drop: function(event, ui){
					ui.draggable.unbind('click');
					setTimeout(function(){
						_self._initItemEvent(null, ui.draggable);
						ui.draggable.removeClass('selected-item');
						ui.draggable.children('div').removeClass('item-asc').removeClass('item-desc');
					}, 500);
				}
			}).disableSelection();
		},
		/**
		 * 获取网格新增的列
		 */
		getColumns: function(columns){
			var i, j, m, n;
			var cols = [], col;
			var defColumns = this.default_options.columns;
			for(i = 0; i < defColumns.length; i++){
				for(j = 0; j < defColumns[i].length; j++){
					col = null;
					inner: for(m = 0; m < columns.length; m++){
						for(n = 0; n < columns[m].length; n++){
							if(defColumns[i][j].field == columns[m][n].field){
								col = columns[m][n];
								break inner;
							}
						}
					}
					if(!col && this.gridOptions.disableFields.indexOf(defColumns[i][j].field) == -1){
						cols.push($.extend({}, defColumns[i][j]));
					}
				}
			}
			
			return cols;
		},
		/**
		 * 验证默认网格配置是否包含该列
		 */
		existColumn: function(col){
			var defColumns = this.default_options.columns;
			for(var i = 0; i < defColumns.length; i++){
				for(var j = 0; j < defColumns[i].length; j++){
					if(defColumns[i][j].field == col.field){
						return true;
					}
				}
			}
			return false;
		},
		/**
		 * 显示保存模板名称编辑弹窗
		 * @param node 当前操作节点数据
		 * @param type 操作类型
		 * @param callback 回调函数
		 * @param data 要保存的数据
		 */
		showEditCfgName: function(node, type, callback, pDlg){
			var _self = this;
			
			$.openModalDialog({
				title: type && type.toUpperCase() === 'ADD' ? '新建模板' : '修改模板',
				mode: 'html',
				url: '<div class="custom-grid"><div class="dialog-content" style="padding:5px;">' +
						'<table><tr>' +
							'<td style="width:80px;text-align:right;">模板名称：</td>' +
							'<td style="width:450px;">' +
								'<input id="gridCfgName" class="app-textbox" _options="{clearbtn:true}"/>' +
							'</td>' +
						'</tr></table>' +
					'</div>' +
					'<div class="dialog-footer">' +
						'<a class="ok-btn list-footer-btn l-btn l-btn-small">确认</a>' +
						'<a class="cancel-btn list-footer-btn l-btn l-btn-small">取消</a>' +
					'</div></div>',
				afterShow: function(dlg){
					$A('#gridCfgName').textbox('setValue', node && node.cfgName || '');
					$A('#gridCfgName').parent().css('border', '1px solid #62a9f1');
					
					dlg.find('.dialog-footer .ok-btn').click(function(){
						var cfgName = $A('#gridCfgName').textbox('getValue');
						
						if(!$.trim(cfgName)){
							$A.messager.warn('模板名称不能为空！');
							return false;
						}
						
						if(cfgName.length > 20){
							$A.messager.warn('模板名称不能超过20个字符！');
							return false;
						}
						
						if(type){
							if(typeof callback === 'function'){
								callback.call(_self, [$.extend({}, node, {
									cfgId: node.cfgId,
									cfgName: cfgName,
									type: type
								}), pDlg]);
							}
						} else{
							$A.showWaitScreen('正在保存配置......');
							// 保存修改信息
							$.ajax({
								url: 'ext/grid/doCommit.do',
								data: $.extend({}, node, {
									cfgId: node.cfgId, 
									cfgName: cfgName, 
									columns: JSON.stringify(node.columns),
									type: 'UPDATE'
								}),
								type: 'post',
								dataType: 'json',
								success: function(result){
									if(result.statusCode == 200){
										$.closeDialog();
										$('#gridCfgTemplate').combobox('reload', 
											'ext/grid/getGridCfgList.do?gridId=' + _self.gridId + '&bizType=' + _self.bizType);
										$('#gridCfgTemplate').combobox('setValue', node.cfgId);
										$('#gridCfgTemplate').combobox('setText', cfgName);
										_self.currGridCfg.cfgId = node.cfgId;
										_self.currGridCfg.cfgName = cfgName;
										if(_self.gridOptions.cfgId == node.cfgId){
											_self.gridOptions.cfgId = node.cfgId;
											_self.gridOptions.cfgName = cfgName;
										}
									} else{
										$A.messager.warn(result.message);
									}
								},
								complete: function(){
									$A.hideWaitScreen();
								}
							});
						}
					});
					dlg.find('.dialog-footer .cancel-btn').click(function(){
						$.closeDialog();
					});
				}
			});
		},
		exportGridData: function(){
			var _self = this;
			if($A('#' + _self.gridId).grid('getSelections').length > 0){
				// 直接导出选中行
				$A.messager.confirm('确认导出选中行？', {
					okCall: function(){
						EtlHelper.exportExcel(_self.gridId, {
							checkRow: true,
							exportType: 'xlsx',
							fileName: _self.exportOptions.fileName || _self.bizTypeName,
							sheetName: 'sheet1'
						});
					}
				})
			} else{
				// 判断网格数据总页数，如果页数为1则直接导出当前页数据
				var $data = $A('#' + _self.gridId).grid('getData');
				if($data && $data.totalPage == 1){
					EtlHelper.exportExcel(_self.gridId, {
						exportType: 'xlsx',
						fileName: _self.exportOptions.fileName || _self.bizTypeName,
						sheetName: 'sheet1'
					});
				} else{
					// 启用线程导出
					EtlHelper.threadExpExcel(_self.gridId, {
						data: {
							configCode: _self.exportOptions.configCode
						}
					});
				}
			}
		},
		/**
		 * 显示高级查询弹窗
		 */
		showAdvanceQuery: function(){
			this.queryExt.openQuery(this.gridId, this.bizType, this.advanceOptions.advances);
		},
		/**
		 * 初始化列表信息项
		 */
		_initItemList: function($ul, columns, row, colspan){
			var _self = this;
			var i, j, arr, $li, $child;
			
			if(!!row && row > 0){
				if(!columns[row]){return false;}
				arr = columns[row].splice(0, colspan);
				for(j = 0; j < arr.length; j++){
					$child = _self._initItem($ul, arr[j]);
					
					if(!!arr[j].colspan && arr[j].colspan >= 1){
						// 递归添加信息项
						_self._initItemList($child, columns, row+1, arr[j].colspan);
					}
				}
			} else{
				for(i = 0; i < columns.length; i++){
					arr = columns[i];
					for(j = 0; j < arr.length; j++){
						$child = _self._initItem($ul, arr[j]);
						
						if(!!arr[j].colspan && arr[j].colspan >= 1){
							// 递归添加信息项
							_self._initItemList($child, columns, i+1, arr[j].colspan);
						}
					}
				}
			}
		},
		/**
		 * 初始化信息项
		 */
		_initItem: function($ul, obj){
			var $li = $('<li class="item"></li>').attr({field: obj.field || obj.id});
			var sortbox = $('<div title="按住可拖动"></div>').text(!obj.title ? obj.field : obj.title);
			if(this.gridOptions.options.remoteSort){
				if(obj.sortType === 'asc'){
					sortbox.attr('title', '按住可拖动(升序)').addClass('item-asc');
				} else if(obj.sortType === 'desc'){
					sortbox.attr('title', '按住可拖动(降序)').addClass('item-desc');
				}
			}
			
			var $child = $('<ul class="child-item-list"></ul>').attr({field: obj.field});
			$li.append(sortbox);
			$li.append($child);
			
			$ul.append($li);
			
			return $child;
		},
		/**
		 * 初始化列表项操作事件
		 */
		_initItemEvent: function(dlg, $item){
			if(!dlg && !$item) return;
			var $items = !$item ? dlg.find('.item') : $item;
			var _self = this;
			
			$items.unbind('click').click(function(){
				if($(this).children('.child-item-list').children().length == 0){
					$(this).toggleClass('selected-item');
					// 开启排序设置
					if(_self.gridOptions.options.remoteSort && $(this).parents('.right-item-box').length === 1){
						var $child = $(this).children('div');
						if($child.hasClass('item-desc')){
							$child.attr('title', '按住可拖动').removeClass('item-desc');
						} else if($child.hasClass('item-asc')){
							$child.attr('title', '按住可拖动(降序)').removeClass('item-asc').addClass('item-desc');
						} else{
							$child.attr('title', '按住可拖动(升序)').addClass('item-asc');
						}
					}
				}
			});
		},
		/**
		 * 初始化添加、移除按钮事件
		 */
		_initBtnEvent: function(dlg){
			var _self = this;
			var i, j;
			var $leftList = dlg.find('#leftList');
			var $rightList = dlg.find('#rightList');
			
			// 添加已选项
			dlg.find('.button-box .add-selected').click(function(){
				$leftList.find('.selected-item').children('div')
					.removeAttr('title').removeClass('item-asc').removeClass('item-desc');
				$rightList.append($leftList.find('.selected-item').removeClass('selected-item'));
			});
			// 添加所有项
			dlg.find('.button-box .add-all').click(function(){
				$leftList.find('.item').children('div')
					.removeAttr('title').removeClass('item-asc').removeClass('item-desc');
				$rightList.append($leftList.find('.item').removeClass('selected-item'));
			});
			// 移除已选项
			dlg.find('.button-box .remove-selected').click(function(){
				$rightList.find('.selected-item').children('div')
					.removeAttr('title').removeClass('item-asc').removeClass('item-desc');
				$leftList.append($rightList.find('.selected-item').removeClass('selected-item'));
			});
			// 移除所有项
			dlg.find('.button-box .remove-all').click(function(){
				$rightList.find('.item').children('div')
					.removeAttr('title').removeClass('item-asc').removeClass('item-desc');
				$leftList.append($rightList.find('.item').removeClass('selected-item'));
			});
			
			// 新建模板配置
			dlg.find('.dialog-footer .create-btn').click(function(){
				if(dlg.find('#rightList').children().length == 0){
					$A.messager.warn('当前显示列为空请重新设置！');
					return false;
				}
				_self.save(dlg, 'ADD');
			});

			// 保存当前模板配置
			dlg.find('.dialog-footer .ok-btn').click(function(){
				if(dlg.find('#gridCfgTemplate').combobox('getValue') == 0
					|| !dlg.find('#gridCfgTemplate').combobox('getText')){
					$A.messager.warn('请选择模板！');
					return false;
				}
				// 确认保存并更新模板配置
				_self.save(dlg, 'UPDATE');
			});
			// 取消关闭弹窗
			dlg.find('.dialog-footer .cancel-btn').click(function(){
				$.closeDialog();
			});
		},
		/**
		 * 保存配置
		 */
		save: function(dlg, type){
			var _self = this;
			_self.max_level = 1,
			sortFieldCount = this.gridOptions.sortFieldCount || 3;
			var i, j;
			
			if(dlg.find('#rightList').find('.item-asc').length 
				+ dlg.find('#rightList').find('.item-desc').length > sortFieldCount){
				$A.messager.warn('进行排序的列不能超过' + sortFieldCount + '个！');
				return false;
			}
			
			if(this.gridOptions.cardView){
				this.gridOptions.options.viewType = $A('#viewType').combobox('getValue');
			}
			
			var sxh = 1;
			var leftList = _self.getList(dlg.find('#leftList'), 1, 1);
			var rightList = _self.getList(dlg.find('#rightList'), 1, 0);
			
			for(i = 0; i < leftList.length; i++){
				leftList[i].rowspan = _self.max_level;
				leftList[i].sxh = sxh++;
			}
			
			var columns = [];
			for(i = 1; i <= _self.max_level; i++){
				var rows = [];
				if(i == 1){
					rows = $.extend(rows, leftList);
				}
				for(j = 0; j < rightList.length; j++){
					if(i == rightList[j].colLevel){
						rightList[j].sxh = sxh++;
						
						rows.push(rightList[j]);
					}
				}
				columns.push(rows);
			}
			
			_self.gridOptions.options.columns = columns;

			// 验证默认配置是否变动
			if(_self.currGridCfg.cfgId == -1 && type.toUpperCase() === 'UPDATE'){
				if(_self.gridOptions.cardView && $A('#viewType').combobox('getValue') != 'GridView'){
					$A.messager.warn('默认配置不可变动！');
					return false;
				}
				if(type.toUpperCase() == 'UPDATE' 
					&& !_self.validationDefaultCfg(columns)){
					$A.messager.warn('默认配置不可变动！');
					return false;
				}
			}
			
			// 保存数据
			var cols = [];
			var _self = this;
			var cls = leftList.concat(rightList);
			
			for(var i = 0; i < cls.length; i++){
				cols.push(this.clearColumns($.extend({}, cls[i])));
			}
			
			var data = {
				gridId: _self.gridId,
				bizType: _self.bizType,
				bizTypeName: _self.bizTypeName,
				levelCount: _self.gridOptions.options.columns.length,
				columns: JSON.stringify(cols),
				attr: JSON.stringify({
					viewType: _self.gridOptions.options.viewType
				}),
				type: type
			};
			
			if(type == 'ADD'){
				_self.showEditCfgName(data, type, _self.addGridCfg, dlg);
			} else{
				data.cfgId = _self.currGridCfg.cfgId;
				data.cfgName = _self.currGridCfg.cfgName;
				_self.addGridCfg(data);
			}
		},
		addGridCfg: function(param){
			var _self = this, data;
			
			if($.type(param) == 'array'){
				data = param[0];
				var dlg = param[1];
			} else{
				data = param;
			}
			
			$A.showWaitScreen('正在重构网格...');
			$.ajax({
				url: 'ext/grid/doCommit.do',
				data: data,
				type: 'post',
				dataType: 'json',
				success: function(result){
					if(result.statusCode == 200){
						_self.currGridCfg = result.data;
						_self.gridOptions.cfgId = result.data.cfgId;
						_self.gridOptions.cfgName = result.data.cfgName;
						_self.resetGrid();
						$.closeDialog();
						if(typeof _self.gridOptions.callback === 'function'){
							var $grid = $('#' + _self.gridId).data('grid');
							_self.gridOptions.callback($grid.options);
						}
						if(dlg){
							dlg.closeDialog();
						}
					} else{
						$A.messager.warn(result.message);
					}
				},
				complete: function(){
					$A.hideWaitScreen();
				}
			});
		},
		/**
		 * 验证默认网格配置是否变动
		 */
		validationDefaultCfg: function(columns){
			var i, j, k, row;
			var defColumns = this.default_options.columns;
			var disableFields = this.gridOptions.disableFields;
			var defCols = [];
			
			for(i = 0; i < defColumns.length; i++){
				row = [];
				for(j = 0; j < defColumns[i].length; j++){
					for(k = 0; k < disableFields.length; k++){
						if(defColumns[i][j].field == disableFields[k]){
							break;
						}
					}
					if(k == disableFields.length && !defColumns[i][j].hidden){
						row.push($.extend({}, defColumns[i][j]));
					}
				}
				defCols.push(row);
			}
			
			var cols = [];
			for(i = 0; i < columns.length; i++){
				row = [];
				for(j = 0; j < columns[i].length; j++){
					if(!columns[i][j].hidden){
						row.push($.extend({}, columns[i][j]));
					}
				}
				cols.push(row);
			}
			
			// 验证网格显示列是否对应
			if(defCols.length != cols.length){
				return false;
			}
			for(i = 0; i < defCols.length; i++){
				if(defCols[i].length != cols[i].length){
					return false;
				}
				for(j = 0; j < defCols[i].length; j++){
					if(defCols[i][j].field != cols[i][j].field
						|| defCols[i][j].sortType != cols[i][j].sortType){
						return false;
					}
				}
			}
			
			return true;
		},
		/**
		 * 重构网格
		 */
		resetGrid: function(){
			var _self = this;
			
			if(!!$('#' + this.gridId).data('grid')){
				// 添加固定参数
				this.gridOptions.fixedParameter = $('#' + this.gridId).grid('getFixedParameter');
				$('#' + this.gridId).grid('destroy');
			}
			
			if(!$('#' + this.gridId).length){
				this.gridOptions.$container.append($('<table style="display:none;"></table>').attr({
					'id': this.gridId,
					'_options': '{"class":"com.bosssoft.platform.ui.domain.component.UIXGrid"}'
				}));
			}
			
			var cols = this.gridOptions.options.columns;
			for(var i = 0; i < cols.length; i++){
				for(var j = 0; j < cols[i].length; j++){
					var col = cols[i][j]; 
					this.gridOptions.options.columns[i][j] = this.clearColumns(cols[i][j]);
				}
			}
			
			// 未设置默认为true加载完配置自动刷新
			var autoLoad = this.gridOptions.options.autoLoad == undefined ? true : this.gridOptions.options.autoLoad;
			
			// 重构网格
			$('#' + this.gridId).grid($.extend({}, this.gridOptions.options, {autoLoad: false, isRender: true}));
			$('.grid-table-header td').removeClass('grid-cell-left').removeClass('grid-cell-right');
			
			this.initToolBtn();
			
			var $grid = $('#' + this.gridId).data('grid');
			$grid.setFixedParameter(this.gridOptions.fixedParameter || {});
			
			$grid._sort = this._getSort();
			if(autoLoad){
				$grid.reload();
			}
			
			// 网格渲染完成回调
			if(typeof this.gridOptions.callback === 'function'){
				this.gridOptions.callback($grid.options);
			}
		},
		clearColumns: function(col){
			if(col.colspan > 1){
				delete col.rowspan;
			}
			if(col.editor){
				col.editor = {
					type: col.editor.type,
					options: col.editor.options
				}
			}
			
			delete col.children;
			delete col.parentColumn;
			delete col.__prev;
			return col;
		},
		/**
		 * 隐藏工具栏按钮
		 */
		hideToolBtn: function(){
			var $grid = $A('#' + this.gridId).next();
			$grid.find('.grid-ext-btn').hide();
			if(this.btnPosition === 'toolbar'){
				// 判断工具条里面是否有其他按钮
				if(!$grid.find('.grid-header-toolbar>ul>li:not(.grid-ext-btn)').length){
					$grid.find('.grid-header-toolbar').hide();
				}
			}
		},
		/**
		 * 显示工具栏按钮
		 */
		showToolBtn: function(){
			var $grid = $A('#' + this.gridId).next();
			$grid.find('.grid-ext-btn').show();
			if(this.btnPosition === 'toolbar'){
				// 判断工具条里面是否有其他按钮
				if(!$grid.find('.grid-header-toolbar>ul>li:not(.grid-ext-btn)').length){
					$grid.find('.grid-header-toolbar').show();
				}
			}
		},
		/**
		 * 在工具栏添加按钮
		 */
		initToolbarToolBtn: function(){
			var _self = this;
			var $grid = $('#' + this.gridId).next();
			var $toolbar = $grid.find('.grid-header-toolbar');
			
			// 如果按钮已初始化过则返回
			if($toolbar.attr('ext-tool-btn')){
				return false;
			}
			
			// 工具栏不存在需添加
			if(!$toolbar.length){
				$toolbar = $('<div class="grid-header-toolbar" style="max-height:33px;"><ul></ul></div>').insertAfter($grid.find('.grid-header-title'));
			}
			
			$toolbar.show();
			var $ul = $toolbar.find('ul');
			
			// 数据筛选按钮
			if(this.advanceOptions){
				var $advanceBtn = $('<li class="grid-ext-btn"><a group>' +
						'<span class="l-btn-left l-btn-icon-left grid-ext-btn" style="font-size:13px;">' +
							'<span class="glyphicon glyphicon-filter" style="top:0;color:#62a9f1;"></span>' +
							'<span style="margin-left:5px;">数据筛选</span>' +
						'</span>' +
					'</a></li>').appendTo($ul);
				_self.queryExt = new QueryExt(this.gridId);
				$advanceBtn.find('a').attr({
					'id': this.gridId + '_advanceBtn',
					'class': 'l-btn l-btn-small'
				});
				$advanceBtn.click(function(){
					_self.showAdvanceQuery();
				});
			}
			
			if(this.exportOptions){
				// 数据导出按钮
				var $exportBtn = $('<li class="grid-ext-btn"><a group>' +
						'<span class="l-btn-left l-btn-icon-left grid-ext-btn" style="font-size:13px;">' +
							'<span class="glyphicon glyphicon-share" style="top:0;color:#62a9f1;"></span>' +
							'<span style="margin-left:5px;">导出数据</span>' +
						'</span>' +
					'</a></li>').appendTo($ul);
				$exportBtn.find('a').attr({
					'id': this.gridId + '_exportBtn',
					'class': 'l-btn l-btn-small'
				});
				$exportBtn.click(function(){
					_self.exportGridData();
				});
				// 导出任务列表按钮
				var $exportTaskListBtn = $('<li class="grid-ext-btn"><a group>' +
						'<span class="l-btn-left l-btn-icon-left grid-ext-btn" style="font-size:13px;">' +
							'<span class="glyphicon glyphicon-list-alt" style="top:0;color:#62a9f1;"></span>' +
							'<span style="margin-left:5px;">任务列表</span>' +
						'</span>' +
					'</a></li>').appendTo($ul);
				$exportTaskListBtn.find('a').attr({
					'id': this.gridId + '_exportBtn',
					'class': 'l-btn l-btn-small'
				});
				$exportTaskListBtn.click(function(){
					EtlExportTask.show(true);
				});
			}
			// 网格配置按钮
			if(this.gridOptions){
				var $customGridBtn = $('<li class="grid-ext-btn"><a group>' +
						'<span class="l-btn-left l-btn-icon-left grid-ext-btn" style="font-size:13px;">' +
							'<span class="glyphicon glyphicon-cog" style="top:0;color:#62a9f1;"></span>' +
							'<span style="margin-left:5px;">网格配置</span>' +
						'</span>' +
					'</a></li>').appendTo($ul);
				$customGridBtn.find('a').attr({
					'id': this.gridId + '_exportBtn',
					'class': 'l-btn l-btn-small'
				});
				$customGridBtn.click(function(){
					_self.showGridCfgView();
				});
			}
			
			$toolbar.attr('ext-tool-btn', true);
		},
		/**
		 * 在标题后面添加按钮
		 */
		initTitleToolBtn: function(){
			// 按钮添加到标题后面
			var $grid = $('#' + this.gridId).next();
			if($grid.find('.grid-pager-ext .pager-tool-ext').length){
				return false;
			}
			
			var $header = $grid.find('.grid-header-title');
			var $gridPager = $('<div class="grid-pager-ext"></div>').appendTo($header);
			
			var _self = this;
			var $pagerTools = $('<table class="pager-tool-ext" cellspacing="0" cellpadding="0"><tr></tr></table>').prependTo($gridPager);
			if($pagerTools && $pagerTools.length == 1){
				var $pagerToolsTr = $pagerTools.find('tr');
				// 在分页栏新增按钮
				if(this.advanceOptions){
					var advanceTips = _self.advanceOptions.title || '数据筛选';
					var $advanceBtn = $('<a class="grid-ext-btn" title="' + advanceTips + '"><span class="glyphicon glyphicon-filter"></span>数据筛选</a>');
					$pagerToolsTr.append($('<td></td>').append($advanceBtn));
					_self.queryExt = new QueryExt(_self.gridId);
					$advanceBtn.click(function(){
						_self.showAdvanceQuery();
					});
				}
				if(this.exportOptions){
					var exportDataTips = _self.exportOptions.title || '导出网格数据';
					var $exportDataBtn = $('<a class="grid-ext-btn" title="' + exportDataTips + '"><span class="glyphicon glyphicon-share"></span>导出数据</a>');
					$pagerToolsTr.append($('<td></td>').append($exportDataBtn));
					$exportDataBtn.click(function(){
						_self.exportGridData();
					});
					
					var $exportTaskListBtn = $('<a class="grid-ext-btn" title="查看任务列表"><span class="glyphicon glyphicon-list-alt"></span>任务列表</a>');
					$pagerToolsTr.append($('<td></td>').append($exportTaskListBtn));
					$exportTaskListBtn.click(function(){
						EtlExportTask.show(true);
					});
				}
				if(this.gridOptions){
					var customGridTips = _self.gridOptions.title || '网格配置';
					var $customGridBtn = $('<a class="grid-ext-btn" title="' + customGridTips + '"><span class="glyphicon glyphicon-cog"></span>网格配置</a>');
					$pagerToolsTr.append($('<td></td>').append($customGridBtn));
					$customGridBtn.click(function(){
						_self.showGridCfgView();
					});
				}
			}
		},
		initToolBtn: function(){
			if(this.btnPosition && this.btnPosition.toLowerCase() === 'toolbar'){
				this.initToolbarToolBtn();
			} else{
				this.initTitleToolBtn();
			}
		},
		_getSort: function(){
			var _sort = [];
			var cols = this.gridOptions.options.columns;
			for(var i = 0; i < cols.length; i++){
				for(var j = 0; j < cols[i].length; j++){
					if(!!cols[i][j].sortType){
						_sort.push({
							col: {field: cols[i][j].field},
							order: cols[i][j].sortType
						});
					}
				}
			}
			return _sort;
		},
		getList: function($ul, level, hidden){
			var cols = [], i;
			var list = !hidden ? $ul.children('li') : $ul.find('li');
			for(i = 0; i < list.length; i++){
				cols = cols.concat(this.getItem($(list[i]), level, hidden));
			}
			
			return cols;
		},
		/**
		 * 根据选项获取对应列数据项
		 */
		getItem: function($li, level, hidden){
			var _self = this;
			var cols = _self.gridOptions.options.columns, i, j;
			
			if(_self.max_level < level){
				_self.max_level = level;
			}
			for(i = 0; i < cols.length; i++){
				for(j = 0; j < cols[i].length; j++){
					if(cols[i][j].field == $li.attr('field') || cols[i][j].id == $li.attr('field')){
						var col = $.extend({}, cols[i][j], {
							hidden: hidden,
							colLevel: level,
							pfield: null,
							colspan: null,
							rowspan: null,
							field: $li.attr('field')
						});
						
						if(!!$li.parent().attr('field')){
							col.pfield = $li.parent().attr('field');
						}
						// 是否排序
						if($li.children('div').hasClass('item-asc')){
							col.sortType = 'asc';
						} else if($li.children('div').hasClass('item-desc')){
							col.sortType = 'desc';
						} else{
							col.sortType = null;
						}
						
						// 判断是否有子项
						if($li.children('.child-item-list').children('.item').length > 0 && !hidden){
							col.colspan = _self.getColspan($li);
							if(col.children && col.children.length > 0){
								col.field = col.id;
								delete col.children;
							}
							
							var list = [col];
							// 递归获取列表
							return list.concat(_self.getList($li.children('.child-item-list'), level+1, hidden));
						} else{
							col.rowspan = _self.getRowspan($li.parents('.item-list')) - level + 1;
							if(col.parentColumn){
								col.pfield = col.pfield || col.parentColumn.field || col.parentColumn.id;
							}
							
							return col;
						}
					}
				}
			}
		},
		getRowspan: function($ul){
			var _self = this;
			var rowspan = 0;
			
			$ul.children('.item').each(function(){
				if($(this).children('.child-item-list').children('.item').length > 0){
					// 递归计算rowspan
					var val = _self.getRowspan($(this).children('.child-item-list'));
					if(rowspan < val){
						rowspan = val;
					}
				}
			});
			return rowspan + 1;
		},
		getColspan: function($li){
			var _self = this;
			var colspan = 0;
			
			$li.children('.child-item-list').children('.item').each(function(){
				if($(this).children('.child-item-list').children('.item').length > 0){
					// 递归计算colspan
					colspan += _self.getColspan($(this));
				} else{
					colspan ++;
				}
			});
			return colspan;
		},
		/**
		 * 网格列选择面板
		 */
		_getPanel: function(){
			var $html = $('<div id="customGrid" class="custom-grid">' +
				'<div class="custom-grid-header">' +
					'<table style="width:100%;">' +
						'<tr>' +
							'<td style="width:75px;"><label for="gridCfgTemplate">配置模板：</label></td>' +
							'<td><input id="gridCfgTemplate"/></td>' +
							'<td style="width:65px;"></td>' +
							'<td style="width:75px;"><label class="view-type" for="viewType">视图类型：</label></td>' +
							'<td><input class="view-type" id="viewType"/></td>' +
						'</tr>' +
					'</table>' +
				'</div>' +
				'<div class="custom-grid-body">' +
					'<div class="item-box left-item-box">' +
						'<div>待选列</div>' +
						'<ul id="leftList" class="item-list"></ul>' +
					'</div>' +
					'<div class="button-box">' +
						'<a class="add-selected item-selected-btn l-btn l-btn-small" title="添加选中项">></a>' +
						'<a class="add-all item-selected-btn l-btn l-btn-small" title="添加全部">>></a>' +
						'<a class="remove-selected item-selected-btn l-btn l-btn-small" title="移除选中项"><</a>' +
						'<a class="remove-all item-selected-btn l-btn l-btn-small" title="移除全部"><<</a>' +
					'</div>' +
					'<div class="item-box right-item-box">' +
						'<div>已选列' + (this.gridOptions.options.remoteSort ? '<span style="color:red;">（点击下面信息项设置排序）</span>' : '') + '</div>' +
						'<ul id="rightList" class="item-list"></ul>' +
					'</div>' +
				'</div>' +
				'<div class="dialog-footer">' +
					'<a class="create-btn list-footer-btn l-btn l-btn-small" title="保存为新模板">新建</a>' +
					'<a class="ok-btn list-footer-btn l-btn l-btn-small" title="保存当前模板">保存</a>' +
					'<a class="cancel-btn list-footer-btn l-btn l-btn-small" title="取消当前操作">取消</a>' +
				'<div>' +
			'</div>');
			
			return $html;
		}
	});
	
	return GridExt;
});
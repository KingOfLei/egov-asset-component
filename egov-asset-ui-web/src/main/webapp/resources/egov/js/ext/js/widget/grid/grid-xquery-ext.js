define(['resources/common/js/app/core/app-jquery', 
        'app/widgets/window/app-dialog', 
        'resources/common/js/app/widgets/form/app-comp',
        'resources/common/js/app/widgets/form/app-form',
        'resources/common/js/app/widgets/form/app-combobox',
        'resources/common/js/app/widgets/form/app-datetime',
        'resources/common/js/app/widgets/form/app-number',
        'resources/common/js/app/widgets/form/app-money',
        'resources/common/js/app/widgets/form/app-textbox',
        'resources/common/js/app/widgets/form/app-comboztree',
        'resources/egov/js/common/utils/Tools'], function($){
	"use strict";
	
	var QueryExt = function(gridId){
		this.$element = $('#' + gridId);
		this.dlgId = getDlgId(gridId);
	}
	
	function getDlgId(gridId){
		if(!gridId){
			throw new Error('组件必须包含id属性');
		}
		return "advquery_ext_" + gridId;
	}
	
	/**
	 * 获取查询参数
	 */
	function collectionParams(dlgId){
		var params = [], queryName = '';
		
		$('#' + dlgId).find('.cfield .app-wrapper').each(function(){
			var $this = $(this);
			var cls = $this.attr('class');
			cls = cls.substring(cls.indexOf(' app-') + 5);
			var $item = $this.children('input:first-child');
			var val = $item[cls]('getValue');
			
			if(val){
				queryName += (!queryName ? '':'&') + $item.parents('.cfield').prev().children().text() + '=' + $item[cls]('getText');
				params.push({
					prop: $item.attr('field'),
					op: $item.attr('operator'),
					val: val,
					text: $item[cls]('getText'),
					type: cls
				});
			}
		});
		return {'__xquery': JSON.stringify(params), 'queryName': queryName};
	}
	
	QueryExt.prototype = {
		openQuery: function(gridId, bizType, advs){
			var _self = this;
			$('#_dialogMask').show();
			if(_self.isShow){
				var $dlg = $('#' + getDlgId(gridId));
				$A.dialog.setCurrent($dlg);
				$dlg.show();
				if(_self.reload){
					$('#' + _self.dlgId).find('#queryHistory').combobox('reload', 
						'ext/query/history/getGridQueryHistory.do?gridId=' + gridId + '&bizType=' + bizType);
					_self.reload = false;
				}
				return false;
			}
			
			// 窗体显示后回调
			var showFn = function(){
				$('#' + getDlgId(gridId)).find('.dialog-content').css('height', parseInt($A('.dialog-content').css('height')) - 35);
				var action = 'ext/query/history/getGridQueryHistory.do?gridId=' + gridId + '&bizType=' + bizType;
				$A('#queryHistory').combobox({
					action: action,
					textfield: 'queryName',
					valuefield: 'queryId',
					clearbtn: true,
					/*onChange: function(newNode, oldNode){
						if(!newNode){
							$A('.xquery-toolbar .advancequery.rest.btn').trigger('click');
						}
					},*/
					beforeSelected: function(node){
						if(node.queryId == -1){
							$A.messager.confirm('确认清空查询记录？', {
								okCall: function(){
									_self.clearQueryHistory(gridId, bizType, action);
								}
							});
							return false;
						}
					},
					afterSelected: function(node){
						_self.initQueryPanel(JSON.parse(node.attr));
					}
				});
				$A('#queryHistory').parent().css('border', '1px solid #62a9f1');
				
				// 初始化表单数据
				_self.isShow = true;
				$('#' + _self.dlgId).find('.advancequery-ext.ok.btn').click(function(){
					_self.advQuery(gridId, bizType);
					funcs.closeDialog();
				});
			}
			
			this.advs = advs || [];
			
			$.openModalDialog({
				dialogId: _self.dlgId,
				title: '数据筛选',
				url: _self._getContent(),
				mode: 'node',
				reload: false,
				afterShow: showFn
			});
		},
		clearQueryHistory: function(gridId, bizType, action){
			$A.showWaitScreen('正在清除记录...');
			$.ajax({
				url: 'ext/query/history/clearQueryHistory.do',
				data: {gridId: gridId, bizType: bizType},
				type: 'post',
				dataType: 'json',
				success: function(result){
					if(result.statusCode != 200){
						$A.messager.warn(result.message);
					} else{
						$A('#queryHistory').combobox('setText', '');
						$A('#queryHistory').combobox('reload', action);
						$A('.xquery-toolbar .advancequery.rest.btn').trigger('click');
					}
				},
				complete: function(){
					$A.hideWaitScreen();
				}
			});
		},
		/**
		 * 初始化查询面板
		 * @param attr
		 */
		initQueryPanel: function(objs){
			$A('.xquery-toolbar .advancequery.rest.btn').trigger('click');
			for(var i = 0; i < objs.length; i++){
				var obj = objs[i]; 
				$A('input[field="' + obj.prop + '"][operator="' + obj.op + '"]')[obj.type]('setValue', obj.val);
				$A('input[field="' + obj.prop + '"][operator="' + obj.op + '"]')[obj.type]('setText', obj.text);
			}
		},
		advQuery: function(gridId, bizType){
			var params = collectionParams(this.dlgId);
			this.$element.grid('setParameter', params);
			this.$element.grid('load');
			
			// 保存查询记录
			this.saveQueryHistory(gridId, bizType, params);
		},
		saveQueryHistory: function(gridId, bizType, params){
			var _self = this;
			var queryId = $A('#queryHistory').combobox('getValue');
			$.ajax({
				url: 'ext/query/history/doCommit.do',
				data: {
					gridId: gridId,
					bizType: bizType,
					attr: params.__xquery,
					queryId: queryId,
					queryName: params.queryName
				},
				type: 'post',
				dataType: 'json',
				success: function(result){
					if(result.statusCode != 200){
						$A.messager.warn(result.message);
					} else{
						_self.reload = true;
					}
				}
			});
		},
		/**
		 * 组装高级查询面板
		 */
		_getContent: function(gridId, bizType){
			var id = this.$element.attr('id');
			if(!id){
				id = $A.nextId() + '_queryadv';
			} else{
				id += '_queryadv';
			}
			
			var $html = $(
			'<div class="xquery-adv-history xquery-adv-dlg" style="color:#62a9f1;width:100%;border-bottom:1px solid #62a9f1;padding:5px 10px;">' +
				'<table class="query-itmes-table"><tr>' +
					'<td style="width:80px;">历史查询记录</td>' +
					'<td><input id="queryHistory"/></td>' +
				'</tr></table>' +
			'</div>' +
			'<div class="dialog-content">' +
				'<div id="' + id + '" class="xquery-adv-dlg" query-target="' + this.$element.attr('id') + '"></div>' +
			'</div>' +
			'<div class="dialog-footer">' +
				'<div class="xquery-toolbar">' +
					'<div class="buttonarea">' +
						'<a class="advancequery-ext ok btn btn-primary">确定</a>' +
						'<a class="advancequery rest btn">重置</a>' +
						'<a class="advancequery cancel btn">取消</a>' +
					'</div>' +
				'</div>' +
			'</div>');
			$html.find('#' + id).append(this._getItems());
			
			return $html;
		},
		_getItems: function(){
			var $table = $('<table class="query-itmes-table"></table>');
			var $grid = this.$element.data('grid');
			var columns = $grid.getColumns();
			if(columns && columns.center){
				columns = columns.center;
			}
			
			var colspan = this.getAdvanceColspan(columns);
			for(var i = 0; i < columns.length; i++){
				for(var j = 0; j < columns[i].length; j++){
					var col = columns[i][j];
					if(!col.hidden && (!col.colspan || col.colspan == 1)){
						$table.append(this._getItem(col, colspan-1));
					}
				}
			}
			
			return $table;
		},
		/**
		 * 获取查询项
		 * @param col
		 * @param colspan
		 * @returns
		 */
		_getItem: function(col, colspan){
			var $tr = $('<tr></tr>');
			var advs = this.advs;
			for(var i = 0; i < advs.length; i++){
				// 使用配置高级查询
				if(col.field == advs[i].field){
					var type = 'app-' + (advs[i].type || 'textbox').toLowerCase();
					var oper = 'like';
					var $rg;
					var opts = {
						clearbtn: true
					}
					
					// 日期或数值进行范围设置
					if(type === 'app-datetime' || type === 'app-number'){
						var range = (!advs[i].options || advs[i].options.range == undefined) ? true : advs[i].options.range;
						if(range){
							colspan = 1;
							oper = '>=';
							$rg = $('<td class="clabel"><label class="left">至</label></td>' +
								'<td class="cfield" colspan="1"></td>');
							$($rg[1]).append($('<input/>').attr({
								id: 'end_' + advs[i].field,
								name: advs[i].field,
								'class': type,
								field: $.getField(advs[i].column||advs[i].field),
								operator: '<=',
								_options: '{clearbtn:true}'
							}));
						} else{
							oper = '=';
						}
						if(type === 'app-datetime'){
							opts.dateend = 'end_' + advs[i].field;
						}
					}
					
					$('<td class="clabel"><label class="left">' + (advs[i].name||col.title||col.field) + '</label></td>').appendTo($tr);
					
					$.extend(opts, advs[i].options);
					
					var $val = $('<td class="cfield"></td>').attr('colspan', colspan).append($('<input/>').attr({
						id: advs[i].field,
						'class' : type,
						name: col.field,
						field: $.getField(advs[i].column||advs[i].field),
						operator: oper,
						usesuggest: true,
						_options: JSON.stringify(opts)
					})).appendTo($tr);
					
					if($rg){
						$tr.append($rg);
					}
					break;
				}
			}
			
			// 未配置则启用默认配置高级查询
			if(!$tr.children().length){
				$('<td class="clabel"><label class="left">' + (col.title||col.field) + '</label></td>').appendTo($tr);
				$('<td class="cfield"></td>').attr('colspan', colspan).append($('<input/>').attr({
					id: col.field,
					'class': 'app-textbox',
					name: col.field,
					field: $.getField(col.field),
					operator: 'like',
					_options: '{clearbtn:true}'
				})).appendTo($tr);
			}
			if(colspan == 1 && $tr.children().length == 2){
				$tr.find('td:last-child').css('width', '439px');
			}
			
			return $tr;
		},
		/**
		 * 高级查询表单所占列数
		 * @returns {Number}
		 */
		getAdvanceColspan: function(columns){
			var advs = this.advs;
			for(var n = 0; n < advs.length; n++){
				for(var i = 0; i < columns.length; i++){
					for(var j = 0; j < columns[i].length; j++){
						if(!columns[i][j].hidden && columns[i][j].field == advs[n].field){
							var type = advs[n].type.toLowerCase();
							if(type === 'datetime' || type === 'number'){
								var range = (!advs[n].options || advs[n].options.range == undefined) ? true : advs[n].options.range;
								if(range){
									return 4;
								}
							}
						}
					}
				}
			}
			
			return 2;
		}
	}
	
	return QueryExt;
});
define(['app/data/app-ajax',
        'app/app-pagebase',
        'app/widgets/grid/app-grid'], 
	function(AppAjax, PageBase){
	var _self;
	var RANK_RULE = '2333333333';
	var OrgCompare = PageBase.extend({
		initialize : function() {
			OrgCompare.superclass.initialize.call(this);
		},
		listeners: {
			/**
			 * 计算下级
			 */
			orgCompare_batchCreateOrgCode: {
				click: function(){
					_self.$grid.endEdit(_self.$grid.getCurrentEditRowIndex());
					var datas = _self.$grid.getSelections();
					if(!datas || !datas.length){
						$A.messager.warn('请先勾选行数据！');
						return false;
					}
					$A.messager.confirm('确认对勾选行进行计算下级？', {
						okCall: function(){
							setTimeout(_self.batchGenerateOrgCode, 200);
						}
					});
				}
			},
			/**
			 * 批量设置上级编码
			 */
			orgCompare_batchSetPcode: {
				click: function(){
					_self.$grid.endEdit(_self.$grid.getCurrentEditRowIndex());
					var datas = _self.$grid.getSelections();
					if(!datas || !datas.length){
						$A.messager.warn('请先勾选行数据！');
						return false;
					}
					
					$.openModalDialog({
						title: '批量设置上级编码',
						url:'<div class="dialog-content">' +
								'<input id="batchSetPcode">' +
							'</div>' +
							'<div class="dialog-footer">' +
								'<div id="btnarea" class="btnarea btn-toolbar">' +
									'<a id="batchSetPcode_okBtn" class="app-button l-btn l-btn-small">' +
										'<span class="l-btn-left"><span class="l-btn-text">确认</span></span>' +
									'</a>' +
									'<a id="batchSetPcode_cancelBtn" class="app-button l-btn l-btn-small">' +
										'<span class="l-btn-left"><span class="l-btn-text">取消</span></span>' +
									'</a>' +
								'</div>' +
							'</div>',
						mode: 'html',
						afterShow: function($batchDlg){
							$batchDlg.find('#batchSetPcode').comboztree({
								tips: '请选择单位',
								action: 'egov/asset/aims/basic/aimsbasicorg/loadAllOrgList.do',
								async: false,
								idfield: 'orgId',
								pidfield: 'orgPid',
								textfield: 'treeNode',
								valuefield: 'orgCode',
								multiple: false,
								acceptText: true,
								usesuggest: true,
								clearbtn: true
							});
							
							// 确认
							$batchDlg.find('#batchSetPcode_okBtn').on('click', function(){
								var node = $A('#batchSetPcode').comboztree('getSelectedNode');
								if(!node){
									$A.messager.warn('请选择单位！');
									return false;
								}
								for(var i = 0; i < datas.length; i++){
									var data = datas[i];
									var rowData = $.extend(data, {
										destOrgPcode: node.orgCode, 
										destOrgPid: node.orgId
									});
									
									_self.$grid.saveRow({
										rowIndex: _self.$grid.getRowIndex(data),
										rowData: rowData
									});
								}
								$batchDlg.closeDialog();
							});
							// 取消关闭
							$batchDlg.find('#batchSetPcode_cancelBtn').on('click', function(){
								$batchDlg.closeDialog();
							});
						}
					});
				}
			},
			/**
			 * 单位名称匹配
			 */
			orgCompare_setDestOrgName: {
				click: function(){
					var selectedData = _self.$grid.getSelections();
					if(!selectedData || selectedData.length == 0){
						$A.messager.warn('请先勾选行数据！');
						return false;
					}
					
					for(let i = 0; i < selectedData.length; i++){
						$app.ajax.ajaxCall({
							url: 'egov/asset/aims/basic/aimsbasicorg/getAimsBasicOrg.do',
							type: 'post',
							data: {orgName: selectedData[i].srcOrgName},
							dataType: 'json',
							success: function(org){
								let rowData = $.extend(selectedData[i], {
									destOrgName: selectedData[i].srcOrgName
								});
								if(!!org && org.length > 0){
									rowData = $.extend(selectedData[i], {
										destOrgCode: org[0].orgCode,
										destOrgName: org[0].orgName,
										destOrgPcode: org[0].orgPcode,
										destOrgPid: org[0].orgPid,
										destOrgIsleaf: org[0].orgIsleaf
									});
								}
								
								if(rowData.destOrgCode.indexOf(rowData.destOrgPcode) == 0){
									if(!rowData.destOrgRank){
										// 级次等于上级级次+1
										var rank = _self.getOrgRank(rowData.destOrgCode, rowData.destOrgPcode);
										rowData.destOrgRank = !rank ? '' : rank + 1;
									}
								}
								_self.$grid.saveRow({
									rowIndex: _self.$grid.getRowIndex(selectedData[i]),
									rowData: rowData
								});
							}
						});
					}
				}
			}
		},
		/**
		 * 批量计算下级编码
		 */
		batchGenerateOrgCode: function($dlg){
			var datas = _self.$grid.getSelections();
			
			for(var i = 0; i < datas.length; i++){
				if(!datas[i].destOrgPcode){
					$A.messager.warn('第' + (_self.$grid.getRowIndex(datas[i]) + 1) + '行上级编码不能为空！');
					return false;
				} else{
					datas[i].destOrgCode = datas[i].destOrgPcode + datas[i].srcOrgCode.substr(-3);
				}
				// 计算级次(获取上级单位的级次+1)
				// 级次等于上级级次+1
				var rank = _self.getOrgRank(datas[i].destOrgCode, datas[i].destOrgPcode);
				datas[i].destOrgRank = !rank ? '' : rank + 1;
				datas[i].destOrgName = datas[i].srcOrgName;
				
				// 更新行数据
				_self.$grid.saveRow({
					rowIndex: _self.$grid.getRowIndex(datas[i]),
					rowData: datas[i]
				});
			}
		},
		/**
		 * 本级编码改变
		 */
		onChangeOrg: function(data){
			var node = this.getSelectedNode();
			var rowData = _self.$grid.getCurrentEditRowData();
			
			if(!node || !data || node.treeNode === data){
				rowData.destOrgId = '';
				rowData.destOrgCode = data;
				rowData.destOrgRank = '';
				
				// 计算级次
				if(rowData.destOrgCode.indexOf(rowData.destOrgPcode) == 0){
					if(!rowData.destOrgRank){
						var rank = _self.getOrgRank(rowData.destOrgCode, rowData.destOrgPcode);
						rowData.destOrgRank = !rank ? '' : rank + 1;
					}
				}
			} else if(node.orgId){
				// 下拉选择单位
				rowData.destOrgId = node.orgId;
				rowData.destOrgCode = node.orgCode;
				rowData.destOrgPid = node.orgPid;
				rowData.destOrgPcode = node.orgPcode;
				rowData.destOrgName = node.orgName;
				rowData.destOrgRank = node.orgRank;
				rowData.destOrgIsleaf = node.orgIsleaf;
			}
			
			// 验证编码是否重复
			if(_self.checkOrgCodeIsRepeat(rowData.destOrgCode)){
				$A.messager.warn('编码【' + rowData.destOrgCode + '】已存在，不能重复选择！');
				rowData.destOrgId = '';
				rowData.destOrgCode = '';
				rowData.destOrgRank = '';
			}
			
			_self.$grid.saveRow({
				rowIndex: _self.$grid.getCurrentEditRowIndex(),
				rowData: rowData
			});
		},
		/**
		 * 获取上级单位级次
		 */
		getOrgRank: function(orgCode, orgPcode){
			if(!orgPcode) return;
			var datas = _self.$grid.getAllData();
			for(var i = 0; i < datas.length; i++){
				if(datas[i].destOrgCode == orgPcode && !!datas[i].destOrgRank){
					return parseInt(datas[i].destOrgRank);
				}
			}
			
			var len = 0;
			for(var i = 0; i < RANK_RULE.length; i++){
				len += parseInt(RANK_RULE.charAt(i));
				if(orgCode.length == len){
					return i;
				}
			}
			
			if(orgPcode == 'SJ' && orgCode.length > 3){
				len = 0;
				for(i = 1; i < RANK_RULE.length; i++){
					len += parseInt(RANK_RULE.charAt(i));
					if(orgCode.substr(3).length == len){
						return i;
					}
				}
			}
			return null;
		},
		/**
		 * 上级编码改变
		 */
		onChangePOrg: function(data){
			var node = this.getSelectedNode();
			var rowData = _self.$grid.getCurrentEditRowData();
			
			if(!node || !data || node.treeNode === data){
				// 清空上级编码
				rowData.destOrgPid = '';
				rowData.destOrgPcode = data;
				rowData.destOrgRank = '';
			} else if(node.orgId){
				// 下拉选择上级单位
				rowData.destOrgPid = node.orgId;
				rowData.destOrgPcode = node.orgCode;
			}
			
			// 验证下级编码是否符合级次规则
			if(rowData.destOrgCode.indexOf(rowData.destOrgPcode) == 0){
				if(!rowData.destOrgRank){
					// 级次等于上级级次+1
					var rank = _self.getOrgRank(rowData.destOrgCode, rowData.destOrgPcode);
					rowData.destOrgRank = !rank ? '' : rank + 1;
				}
			} else{
				rowData.destOrgPid = '';
				rowData.destOrgRank = '';
			}
			
			_self.$grid.saveRow({
				rowIndex: _self.$grid.getCurrentEditRowIndex(),
				rowData: rowData
			});
		},
		checkOrgCodeIsRepeat: function(orgCode){
			return false;
		},
		initPage: function(data){
			_self = OrgCompare.getInstance();
			_self.$grid = $A('#orgCompare_grid').data('grid');
		}
	});
	
	OrgCompare.getInstance = function(){
		if(!this.instance){
			this.instance = new OrgCompare();
		}
		return this.instance;
	}
	
	return OrgCompare.getInstance();
});
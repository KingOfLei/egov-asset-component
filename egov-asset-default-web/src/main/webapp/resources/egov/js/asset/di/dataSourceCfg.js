define(['app/app-pagebase'], 
		function(PageBase){
	var _self;
	var DataSourceCfg = PageBase.extend({
		initialize : function() {
			DataSourceCfg.superclass.initialize.call(this);
		},
		listeners: {
			dbId: {
				onChange: function(val){
					var $importOrgItem = $A('#importOrg').parents('.formitem');
					var validations = $A('#importOrg').attr('validations');
					
					$A('#importOrg').comboztree('destroy');
					// 重构单位下拉树
					$importOrgItem.empty();
					$('<input/>').attr({
						'id': 'importOrg',
						'name': 'importOrg',
						'validations': "{required: true,messages: {required: '请选择数据源单位'}}"
					}).appendTo($importOrgItem);
					
					$A('#importOrg').comboztree({
						action: 'egov/asset/di/getOrgListFromDataBase.do?dbId=' + val + '&importTaskId=' + $('#assetDiTask').val(),
						async: false,
						idfield: 'SRC_ORG_ID',
						pidfield: 'SRC_ORG_PID',
					    textfield: 'SRC_ORG_TITLE',
					    valuefield: 'SRC_ORG_CODE',
					    multiple: true,
					    usesuggest: true,
					    clearbtn: true
					});
				}
			}
		},
		initPage: function(data){
			_self = DataSourceCfg.getInstance();
			_self.data = data;
			
			// 添加表单项空白字符校验
			$.validator.addMethod('emptyName', function(value){
				if(value.match(/\s/g)){
					return false;
				}
				return true;
			});
			
			$A('#password').css({
				width: '100%',
				border: 0
			}).hover(function() {
				$(this).css({border: '1px solid #62a9f1'});
			}, function(){
				$(this).css({border: 0});
			});
			
			// 添加附件项上传附件信息
			if(data.type == 1){
				$A("#dataSourceCfgPage_north").createNewAttach({
					grade: 1,
					bizType : 'DATA_INTERFACE',
					groupId: _self.data.groupId,
					status: 1,
					expand:true,
					attachId:'dataSourceCfgPage_north'
				});
				$A("#dataSourceCfgPage_north .upload-div").css({width: '100%', height: '176px'})
					.find('.remark-div').hide();
			}
		}
	});
	
	DataSourceCfg.getInstance = function(){
		if(!this.instance){
			this.instance = new DataSourceCfg();
		}
		return this.instance;
	}
	
	return DataSourceCfg.getInstance();
});
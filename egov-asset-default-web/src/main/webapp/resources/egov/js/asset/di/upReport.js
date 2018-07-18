define(['app/app-pagebase'], function(){
	var UpReport = PageBase.extend({
		initialize : function() {
			UpReport.superclass.initialize.call(this);
		},
		listeners: {
			upReport_btn: {
				click: function(){
					$A.messager.confirm('确认上报？', {
						okCall: function(){
							$A.showWaitScreen('正在上报...');
							$.ajax({
								url: 'egov/asset/aims/basic/aimsbasicorg/doUpReport.do',
								type: 'post',
								dataType: 'json',
								success: function(result){
									if(result.statusCode == 200){
										$A.messager.success('上报成功！');
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
				}
			},
			downReport_btn: {
				click: function(){
					$A.messager.confirm('确认撤销？', {
						okCall: function(){
							$A.showWaitScreen('正在撤销...');
							$.ajax({
								url: 'egov/asset/aims/basic/aimsbasicorg/doDownReport.do',
								type: 'post',
								dataType: 'json',
								success: function(result){
									if(result.statusCode == 200){
										$A.messager.success('撤销成功！');
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
				}
			}
		}
	});
	
	UpReport.getInstance = function(){
		if(!this.instance){
			this.instance = new UpReport();
		}
		return this.instance;
	}
	
	return UpReport.getInstance();
});
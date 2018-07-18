define([],function(){
	var ACTION = {
			"ADD":"新建",
			"VIEW":"查看",
			"EDIT":"修改",
			"AUDIT":"审核"
	};
	
	//存储弹出窗口回调
	var showPageCallBack=null;
	
	//自定义退回返回信息
	var RADOM_BACK_INFO=null;
	return {
	
		//获取退回节点信息  		
		getBackNodeInfo:function(data){
		   var busId = data.billId ||"-111";
		   //console.log(showPageCallBack);
		   var backInfo = null;
			$app.ajax.ajaxCall({
                url: "activitiWeb/getReturnList.do",
                async: false,
                data: {
                	busId:busId
                },
                success:function(data){
                    var len = data.length;
                    var menus =[];
                    var back={text: '退回', method: 'backBtn'};
                	if(len){
//                	   for(var i =0;i<len;i++){
//                		   //var userTaskName = "退回"+(data[i].userTaskName).substr(data[i].userTaskName.indexOf('】')+1);
//                		   var userTaskName = "退回"+data[i].userTaskName
//                           var taskId = data[i].userTaskId;
//                           if(taskId){
//                               menus.push({text:userTaskName,method:'billPage_backBtn',params:{taskId:taskId,userTaskName:data[i].userTaskName}});
//                           }
//                           
//                	   }
//                	   //back.menus =JSON.stringify(menus);
//                	   back.menus =menus;
//                	   backInfo = back;
                	backInfo= data;
                   }                	
                },
                error:function(){
                	$A.assetMsg.warn('系统获取退回信息错误！');
                }
            });
			return backInfo;
		},
		//显示自定义退回界面
		showPage:function(data,callback){
			 this.showPageCallBack=callback||function(){};
			 var self=this;
			 
			 var backNodeInfo =data.backNodeInfo;
	         var $html =$('<form id="radio" class="common-class-radiolist ">'+
	        			'</form>')
	         var radioHtml = '';
        	 if(backNodeInfo.length){
        		for (var i=0;i<backNodeInfo.length;i++){
    				radioHtml += '<label>'+
    					'<input type="radio" name="radom_back_radio" value="' + backNodeInfo[i].userTaskId + '">' + backNodeInfo[i].userTaskName + 
    				'</label>';	
    			}
	        	$html.append($(radioHtml));
	      
	        	$.openModalDialog({
        			dialogId:'radomBackDlg',
					title: '自定义退回',
					mode: 'html',
					width: 500,
					url: $html,
					beforeClose:function(obj,dig,event){
						//self.showPageCallBack(RADOM_BACK_INFO);
					},
					closeCallback:function(){
						if(RADOM_BACK_INFO){
							self.showPageCallBack(RADOM_BACK_INFO);
						}
					},
				});
				
        	    var btnHtml=	'<div class="dialog-footer" style="text-align:right;">' +
				'<a class="common-class-footerbtn" id="radomBack_okBtn" >确定</a>' +
				'<a class="common-class-footerbtn" id="radomBack_cancelBtn" >取消</a>' +
			    '</div>';
		         var $dlg =  $A.getCurrentDialog();
		         $dlg.append(btnHtml);
		         $dlg.find('#radomBack_okBtn').click(function(){
		        	    var taskId =$A("#radio input[name='radom_back_radio']:checked").val();
		        	    var userTaskName =$A("#radio input[name='radom_back_radio']:checked").parent().text();
		        	    RADOM_BACK_INFO	={'taskId':taskId,'userTaskName':userTaskName};
		        	    $.closeDialog();
				 });
		         $dlg.find('#radomBack_cancelBtn').click(function(){
		        	    RADOM_BACK_INFO=null;		
		        	    $.closeDialog();
				 });
        	}
		},
		getAuditInfo:function(field){
			if(field=="AGREE"){
				return "同意";
			}else if(field=="BACK"){
				return "退回";
			}else if(field=="REJECT"){
				return "驳回";
			}else{
				return "同意";
			}
		}
	}
});
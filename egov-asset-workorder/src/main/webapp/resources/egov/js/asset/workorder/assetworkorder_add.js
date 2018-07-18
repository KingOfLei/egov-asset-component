/**
 * * Auto generated by Bossssoft Studio version 1.0 beta
 * Wed May 24 11:21:09 CST 2017
 */
//define(['引入外部js模块或控件'],function('引入外部js或控件的别名'){})
define(
		[ 
		  "app/widgets/window/app-dialog",
		  "app/app-pagebase"],
		function(Dialog,PageBase) {
		   var AssetWorkorderDlg = PageBase.extend({
		             //类初始化
		            initialize : function() {
						AssetWorkorderDlg .superclass.initialize.call(this);
					},
					ACTION:{
						ADD:"ADD",
						VIEW:"VIEW",
						EDIT:"EDIT"
					},
					//存储弹出窗口回调
					showPageCallBack:null,
					//当前窗口状态
					indexAction:null,
					pmodel:null,
					saveLx:'',
					//控件监听事件  格式：#控件id#:{事件名:事件方法}
					listeners:{
				         	//新增并保存
						   aimsWorkOrder_tmpSaveBillBtn : {
								click : function() {
									 var self=AssetWorkorderDlg.getInstance();
									 $A("#aimsWorkOrderPage_form").attr('validate',false);
									 $A("#bizStatus").textbox('setValue',"1");
									 $A("#bizStatusName").textbox('setValue',"暂存");
									 $A("#billStatus").textbox('setValue',"0");
									 saveLx="TEMP";
									 self.doSave({isSaveAdd:true})					
								}
							},
							//新增
							assetCommentPage_addBtn : {
								click : function() {
								  $A("#aimsWorkOrderPage_form").clearFormEditorValue();
								}
							},
							//提交
							aimsWorkOrder_saveBillBtn : {
								click : function() {
									 var self=AssetWorkorderDlg.getInstance();
									 var prioritylevel=$A('input[name="prioritylevel"]:checked').val();
									 if ((prioritylevel=='')||(prioritylevel==null)){
										 $A.assetMsg.warn('请选择优先级！');	
										 return;
									 }
									 $A("#bizStatus").textbox('setValue',"10");
									 $A("#bizStatusName").textbox('setValue',"已录入");
									 $A("#billStatus").textbox('setValue',"0");
									 saveLx="SAVE";
									 self.doSave({isSaveAdd:true})					
								}
							},
					},
					
					//保存数据cofig:{isSaveAdd:"保存并新增",isSaveClose:"保存关闭",isSaveView:"保存查看"}可自行扩展
					doSave:function(config,callback){
						var url="";
						if (this.indexAction == this.ACTION.ADD) {
							url="egov/asset/workorder/doInsert.do"
						}
						if (this.indexAction == this.ACTION.EDIT) {
							url="egov/asset/workorder/doUpdate.do"
						}
						 var _self=AssetWorkorderDlg.getInstance();
						$A('#aimsWorkOrderPage_form').sumbitAllComp({
									                     submitMode : 'all',
									                     url : url,
									                     callback : function(json) {
									                     	if (config.isSaveAdd){
									                     	  $A("#assetCommentPage_form").clearFormEditorValue();              	 
															  _self.indexAction=_self.ACTION.ADD;
									                     	}else if (config.isSaveClose){
									                     	 									                         $.closeDialog();
									                         _self.indexAction="";
									                     	}else if (config.isSaveView){
									                     		$A("#assetCommentPage_form").toggleFormState("view");									                     												                                   							                                     _self.indexAction=_self.ACTION.VIEW;
									                     	}
									                     	if (_self.showPageCallBack){
									                     		_self.showPageCallBack();
									                     	}
									                     	if (saveLx=='TEMP'){// 暂存
									                     		$A.assetMsg.success('暂存成功！');
									                     	} else {
									                     	   $A.assetMsg.success('提交成功！');
									                     	}
									                     	$.closeDialog();
									                     }
							                         });		
					},
				
					beforePageInit:function(page,model){
						var _self = AssetWorkorderDlg.getInstance();
						_self.indexAction =model.action;
						if (model.action=='NULL') {
							$A.assetMsg.success('没有找到对应的接收数据！');					
							_self.beforeFormInit(page,'');					
							return;
						}
						pmodel=model;				
						var action =model.action;	    					    	
				    	_self.beforeFormInit(page,action);		    			        
				        //_self.beforeFootInfoInit(pmodel.aimsWorkorder);// 设置单据底部信息
					   
						$(".form").css({'margin-top':'11px'}); //设置顶格位置
					},
					showPage:function(action,data,callback){
					    this.showPageCallBack=callback||function(){};
						var _self=this;
						var msg='';
						this.indexAction=action;
						_self.lockObj = {lock:false};
						var billId= null;
						if (data!=null)
						    billId=data.billId;						
						
						var url = "egov/asset/workorder/showAdd.do";
						$.uiExtend({
			                type: 'flow-page',
			                options: {
			                     title: '业务工单',  
			                     dialogId: "showReceiveDlg",
				                 title: "查看",
				                 wrapper: true,
				                 hasheader: false,
				                 reload: true,
								 hasFoot:true,
							     footHeight:30,
							     footInner:'<span></span>',
							     beforeClose: null,
							     closeCallback:function(){				    	 
							    	    var _close =AssetWorkorderDlg.getInstance();
								    	if(_close.REFRESH_FLAG){
								    		if(callback) callback();
								    		_close.REFRESH_FLAG =false;							
								    	}								    									      
							    },
			                },
			                tabsOp: {
			                    type: 'flow-tabs',
			                    beforeClick: function () {
			                    },
			                    afterClick: function () {
			                    },
			                    data: {
			                        title: '业务工单',
			                        tabs: [
			                            {
			                                text: '工单管理',
			                                url:url,                             
			                                params :{billId:billId,action:action},
								            buttonConfig:{
								                // 索引从1开始，1代表第二个tab，依次类推
								                showIndex:[1]
								            },					            
			                                onPageLoad: function (page,index,onPageArgs) {	                                	                               	
			                        			var flowTabs = page.FlowTabs;
			                        			flowTabs.$foot.css({'margin':'0 auto','width':'998px'});
			                        			
			                        			var _self = AssetWorkorderDlg.getInstance();                      			                                                      			
			                        			_self.beforeCloseReset(_self);// 关闭窗口前方法重置
			                                    var btnConfig =[{text:'暂存',method:'aimsWorkOrder_tmpSaveBillBtn'},
			                                                     {text: '提交', method: 'aimsWorkOrder_saveBillBtn'}];                                                                      
			                                    if (!(_self.ACTION.VIEW == _self.indexAction))
			                                      flowTabs.addButtons(btnConfig,index);                                    			                                                                                                                                            			             		 		    
			                                }                                	
			                            },
			                            {
		                                    text: '相关附件',
		                                    url:'egov/asset/workorder/showAttach.do',// 页面url
		                                    hide:false,
		                                    params: {
		                                    	attachType:'WORKORDER'   
		                	                }
		                              }
			                        ]
			                    }
			                },
			                complete: function () {
			            
			                }
			             });
						
					},
					beforeCloseReset:function(_self){
			    	    var  beforeOptions ={}
			            var  button,msg,beforeClose ;

				        if(_self.ACTION.ADD == _self.indexAction || _self.ACTION.EDIT == _self.indexAction){
				        	msg = "当前编辑未保存，是否保存？",
				        	button=[ {name: '是', 
				        		       callback: function () {
							 	        	$('#aimsUsePage_form').attr('validate', false);
							 	             AimsDisposeDlg.getInstance().doSave({ isTempSave: true  });
				 	                    }
				        	         },
				 	            {  name: '否',callback: function () { $A.dialog.closeCurrent(); } },
				 	            { name: '取消', focus: true,callback: function () { }}
				 	        ];
				        }
				       	
				        if((_self.ACTION.AUDIT ==_self.indexAction)){
				        	msg ="是否退出？";
			        		button=[ {name: '是', callback: function () {$A.dialog.closeCurrent(); }},
				 	            { name: '否', focus: true,callback: function () { }}
				 	       ];
				        }
				        if(!((_self.ACTION.VIEW == _self.indexAction )||(_self.indexAction=='NULL'))){
				        	 beforeOptions={msg:msg,button:button };
				        	 var $dlg = $A.dialog.getCurrent();
			                	var dlgObj = $dlg.data("options");
			                	dlgObj.beforeClose=$.closeCustom(beforeOptions);
				        	
				        }
			       },
			       beforeFormInit:function(page,action){
				       	var _self = AssetWorkorderDlg.getInstance();				       	
				         	
				       	if(_self.ACTION.ADD==action|| _self.ACTION.EDIT==action ){
				       		page.find('#billCode').attr('_options','{readonly: true}').addClass('form-field-view');	
				       	} else {       		
				       	  //page.find('#prioritylevel').attr('_options','{readonly: true}').addClass('form-field-view');	 	
				       	  page.find('input[name="prioritylevel"]').attr("disabled",true); $('input[name="prioritylevel"]').attr("disabled","disabled");	
				       	  page.find('#billCode').attr('_options','{readonly: true}').addClass('form-field-view');	
				       	  page.find('#orderApplicant').attr('_options','{readonly: true}').addClass('form-field-view');
				       	  page.find('#billDate').attr('_options','{readonly: true}').addClass('form-field-view');	       		  
			       		  page.find('#busTypeName').attr('_options','{readonly: true}').addClass('form-field-view');
			       		  page.find('#busTypeId').attr('_options','{readonly: true}').addClass('form-field-view');
			       		  page.find('#problemDescibe').attr('_options','{readonly: true}').addClass('form-field-view');
				       	}        		      					       	    	 
			        },
			     // 设置附件
					setAttach: function(action){			
						// 保存附件信息
						var _self=AssetWorkorderDlg.getInstance();	
						if($A("#workorder_attach").length==0)return;
						var billId = $A("#billId").textbox("getValue");
						bizTypeName ='业务工单';
						var bizType = 'WORKORDER';															
					    var itemType= '业务工单'; 			
					  		 		  	  
					    $("#workorder_attach").createNewAttach({
							grade: 1,
							groupId : billId,
							bizTypeName : bizTypeName,
							bizType : bizType,
							itemType : itemType,
							status:action,
							expand:true,
							attachId:'workorder_attach'
						});		
					},
					//页面加载后初始化
					initPage:function(data){
						var _self = AssetWorkorderDlg.getInstance();
						$A('#busTypeName').combobox('setText',$A('#busTypeName').combobox('getValue'));
						if(_self.ACTION.ADD==action|| _self.ACTION.EDIT==action ){
						  _self.setAttach(1);
						} else {
							_self.setAttach(0);
						}
					},
					busTypeAfterSelect:function(selectedRowData){
						$A("#busTypeCode").textbox("setText",selectedRowData.itemCode);
						$A("#busTypeCode").textbox("setValue",selectedRowData.itemCode);	
					},
					//控件属性重置
					initUIExtConfig : function() {
						this.uiExtConfig={
							//格式 #控件id#:function(控件属性集类){ config.setAttr("控件属性名","属性值"),// 网格，下拉网格，特殊设置config.getColumn("网列id").setAttr("列属性名","列属性值")config.getButton("网格内包含的按钮id").setAttr("handler","点击事件")  }								
						
						}
					}

		 });
		   //创建窗体类单例
			 AssetWorkorderDlg.getInstance=function(){
		     if (!this.instance){
		    	 this.instance =new AssetWorkorderDlg();
		     }
		     return this.instance;
		 }
		
		 return  AssetWorkorderDlg.getInstance();
	   })
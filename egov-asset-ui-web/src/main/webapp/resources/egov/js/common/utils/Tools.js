define(["app/util/app-utils","resources/egov/js/common/thirdparty/MD5","resources/egov/js/ext/js/attach/AttachmentUtils"],function(Utils,md5){


	function Tool(jq){

		var flowHelp ={
		 "REPORT":{
			 bizType : "REPORT",
			 bizTypeName:"资产年报",
			 imgSrc:"REPORT_SINGLE_SUMMARY.png"
		 },
		 "REPORT_NB":{
			 bizType : "REPORT",
			 bizTypeName:"资产年报",
			 imgSrc:"REPORT_NB.png"
		 },
		 "REPORT_ZCCZCS":{
			 bizType : "REPORT_ZCCZCS",
			 bizTypeName:"出租出售自查表",
			 imgSrc:"REPORT_ZCCZCS.png"
		 },
		  "CARD":{
			  bizType : "CARD",
			  bizTypeName:"卡片",
			  imgSrc:"CARD.png"
		  },
		  "CARD_VOID":{
			  bizType : "CARD_VOID",
			  bizTypeName:"卡片作废",
			  imgSrc:"CARD_VOID.png"
		  },
		  "CARD_SPLIT":{
			  bizType : "CARD_SPLIT",
			  bizTypeName:"卡片拆分",
			  imgSrc:"CARD_SPLIT.png"
		  },
		  "CARD_CHANGE":{
			  bizType : "CARD_CHANGE",
			  bizTypeName:"卡片变动",
			  imgSrc:"CARD_CHANGE.png"
		  },
		  "CARD_CONFIRM":{
			  bizType : "CARD_CONFIRM",
			  bizTypeName:"卡片确认",
			  imgSrc:"CARD_CONFIRM.png"
		  },
		  "DISPOSE_ALLOT":{
			  bizType : "DISPOSE_ALLOT",
			  bizTypeName:"资产无偿调拨",
			  imgSrc:"DISPOSE_ALLOT.png"
		  },
		  "DISPOSE_DAMAGE":{
			  bizType : "DISPOSE_DAMAGE",
			  bizTypeName:"资产报损",
			  imgSrc:"DISPOSE_DAMAGE.png"
		  },
		  "DISPOSE_DONATE":{
			  bizType : "DISPOSE_DONATE",
			  bizTypeName:"资产对外捐赠",
			  imgSrc:"DISPOSE_DONATE.png"
		  },
		  "DISPOSE_SELL":{
			  bizType : "DISPOSE_SELL",
			  bizTypeName:"资产出售、出让",
			  imgSrc:"DISPOSE_SELL.png"
		  },
		  "DISPOSE_SWAP":{
			  bizType : "DISPOSE_SWAP",
			  bizTypeName:"资产置换",
			  imgSrc:"DISPOSE_SWAP.png"
		  },
		  "DISPOSE_WASTE":{
			  bizType : "DISPOSE_WASTE",
			  bizTypeName:"资产报废",
			  imgSrc:"DISPOSE_WASTE.png"
		  },
		  "INVENTORY":{
			  bizType : "INVENTORY",
			  bizTypeName:"资产盘点",
			  imgSrc:"INVENTORY.png"
		  },
		  "RECOVER_LEND":{
			  bizType : "RECOVER_LEND",
			  bizTypeName:"资产出借",
			  imgSrc:"RECOVER_LEND.png"
		  },
		  "RECOVER_RENT":{
			  bizType : "RECOVER_RENT",
			  bizTypeName:"资产出租",
			  imgSrc:"RECOVER_RENT.png"
		  },
		  "USE_APPLY":{
			  bizType : "USE_APPLY",
			  bizTypeName:"资产领用",
			  imgSrc:"USE_APPLY.png"
		  },
		  "REG":{
			  bizType : "REG",
			  bizTypeName:"占有登记",
			  imgSrc:"REG.png"
		  },
		  "REGISTER_MAIN":{
			  bizType : "REGISTER_MAIN",
			  bizTypeName:"占有登记",
			  imgSrc:"REGISTER_MAIN.png"
		  },
		  "ASSET_QC":{
			  bizType : "ASSET_QC",
			  bizTypeName:"资产清查",
			  imgSrc:"ASSET_QC.png"
		  },
		  "BCPM_PROJECT":{
			  bizType : "BCPM_PROJECT",
			  bizTypeName:"项目登记",
			  imgSrc:"BCPM_PROJECT.png"
		  },
		  "BCPM_PROJECT_SPLIT":{
			  bizType : "BCPM_PROJECT_SPLIT",
			  bizTypeName:"项目细化",
			  imgSrc:"BCPM_PROJECT_SPLIT.png"
		  },
		  "BCPM_DISPOSE":{
			  bizType : "BCPM_DISPOSE",
			  bizTypeName:"项目取消",
			  imgSrc:"BCPM_DISPOSE.png"
		  },
		  "BCPM_RECEIVE":{
			  bizType : "BCPM_RECEIVE",
			  bizTypeName:"项目接收",
			  imgSrc:"BCPM_RECEIVE.png"
		  },
		  "BCPM_TOCARD":{
			  bizType : "BCPM_TOCARD",
			  bizTypeName:"项目转资产",
			  imgSrc:"BCPM_TOCARD.png"
		  },
		  "BCPM_TMPUSE":{
			  bizType : "BCPM_TMPUSE",
			  bizTypeName:"临时使用",
			  imgSrc:"BCPM_TMPUSE.png"
		  },
		  "BCPM_TRANSFER":{
			  bizType : "BCPM",
			  bizTypeName:"项目移交",
			  imgSrc:"BCPM_TRANSFER.png"
		  },
		  "BCPM_FINAL":{
			  bizType : "BCPM",
			  bizTypeName:"账务处理",
			  imgSrc:"BCPM_FINAL.png"
		  },
		  "DISPOSE_PRESENT":{
			  bizType : "DISPOSE_PRESENT",
			  bizTypeName:"转让",
			  imgSrc:"DISPOSE_PRESENT.png"
		  },
		  "DISPOSE":{
			  bizType : "DISPOSE",
			  bizTypeName:"处置",
			  imgSrc:"DISPOSE.png"
		  },
		  "CARD_TOTAL":{
			  bizType : "CARD_TOTAL",
			  bizTypeName:"卡片",
			  imgSrc:"CARD_TOTAL.png"
		  },
		  "USE":{
			  bizType : "USE",
			  bizTypeName:"使用",
			  imgSrc:"USE.png"
		  },
		  "USE_LEND":{
			  bizType : "USE_LEND",
			  bizTypeName:"资产出借",
			  imgSrc:"USE_LEND.png"
		  },
		  "USE_RENT":{
			  bizType : "USE_RENT",
			  bizTypeName:"资产出租",
			  imgSrc:"USE_RENT.png"
		  },
		  "USE_APPLY":{
			  bizType : "USE_APPLY",
			  bizTypeName:"资产领用",
			  imgSrc:"USE_APPLY.png"
		  },
		  "USE_INVEST":{
			  bizType : "USE_INVEST",
			  bizTypeName:"资产对外投资",
			  imgSrc:"USE_INVEST.png"
		  },
		  "USE_REPAIRE":{
			  bizType : "USE_REPAIRE",
			  bizTypeName:"资产维修",
			  imgSrc:"USE_REPAIRE.png"
		  },
		  "RECOVER_APPLY":{
			  bizType : "RECOVER_APPLY",
			  bizTypeName:"资产交回",
			  imgSrc:"RECOVER_APPLY.png"
		  },
		  "RECOVER":{
			  bizType : "RECOVER",
			  bizTypeName:"交回、收回",
			  imgSrc:"RECOVER.png"
		  },
		  "RECOVER_INVEST":{
			  bizType : "RECOVER_INVEST",
			  bizTypeName:"资产对外投资收回",
			  imgSrc:"RECOVER_INVEST.png"
		  },
		  "RECOVER_RENT":{
			  bizType : "RECOVER_RENT",
			  bizTypeName:"资产出租收回",
			  imgSrc:"RECOVER_RENT.png"
		  },
		  "RECOVER_LEND":{
			  bizType : "RECOVER_LEND",
			  bizTypeName:"资产出借收回",
			  imgSrc:"RECOVER_LEND.png"
		  },
		  "INVENTORY":{
			  bizType : "INVENTORY",
			  bizTypeName:"资产盘点",
			  imgSrc:"INVENTORY.png"
		  },
		  "DEPR":{
			  bizType : "DEPR",
			  bizTypeName : "计提折旧算法说明",
			  //imgSrc:"DEPR.png"
			  imgSrc:"DEPR_NEW.png"
		  }
		  , "USER_MANAGE":{
			  bizType : "USER_MANAGE",
			  bizTypeName : "用户管理审核",
			  imgSrc:"USER_MANAGE.png"
		  },
		  "HPRO_PROFIT": {
		  	  bizType: "HPRO_PROFIT",
		  	  bizTypeName: "房产收益登记",
		  	  imgSrc: "HPRO_PROFIT.png"
		  }
		};
		jq.extend({
			/**
			 * 格式化金额 0 不显示
			 * * @param s 数值
			 * @param n 保留小数位
			 */

			'formatNumberHiddenZero':function(s, n){
				var val = Utils.formatNumber(s,2);
				if(parseFloat(val) == 0){
					return '';
				}
				if(typeof n == 'undefined' || n == 0 || n =='0'){
					//截取后面几位
					return val.replace('.00','');
				} 
				return val;
			},
			//设置打印默认设置
			 "getDefaultPrintPaperSet":function(printOption){				   
				   return {
					   Paper:'A4',
					   Direction:'横向',
					   MarginUp:'1',
					   MarginDown:'1',
					   MarginLeft:'1',
					   MarginRight:'1'
				   };
			   },
			/**
			 * 附件上传功能
			 */
			'attachmentUpload':function(options){
				var def = {
					billId:'',
					bizTypeName:'',
					bizType:'',
					itemType:'',
				};
				 $.extend(def, options);
				var _options = {
						url : "attachment/showUpload.do",
						dialogId : "attachmentShowUpload-" + def.billId,
						title : "附件上传",
						width : 700,
						height : 400,
						wrapper : true,
						hasheader : true,
						reload : true,
						params:def,
						onPageLoad:function(){
							$A(".dialog-header >h5").html(def.bizTypeName + '-附件上传');
						}
					};
					$.openModalDialog(_options);
			},
			//卡片联查
			"cardJoinSearch":function(opt,callback){
				 var assetType6Code = opt.assetType6Code;
				 if(typeof assetType6Code == 'undefined' || assetType6Code == null){
					  assetType6Code = '';
				 }
				 var jsList = "resources/egov/js/aims/card/aimscard6/aimscard6_add.js";
				 if(assetType6Code.indexOf('102')==0){
					 jsList = "resources/egov/js/asset/oams/aimscard6/aimscard6_add.js"  
				 } else if(assetType6Code.indexOf('201')==0){
					 jsList = "resources/egov/js/asset/lvca/aimscard6/aimscard6_add.js"
				 }
				 
				  require([jsList],function($card){
						var def = {
								assetId:'',
								assetClassify6Code:'',
								assetCode:'',
								aseetName:'',
								bizStatus:-1,
								lifeState:-1,
								toDoStatus:-1
							};
						$.extend(def, opt);
						$card.showPage($card.ACTION.VIEW,opt,callback,{showSwitchBtn :false});
					});
			},
			//处置联查
			"disposeJoinsSearch":function(opts,callback){

				require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_add.js"],function(dispose){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1,
							isTodo:0
						};
					$.extend(def, opts);
					dispose.showPage(dispose.ACTION.VIEW,opts,callback);
				});
			},
			//处置联查
			"disposeTodo":function(opts,callback){

				require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_add.js"],function(dispose){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1,
							isTodo:0
						};
					$.extend(def, opts);
					dispose.showAimsDisposeBillInfo(opts,callback);
				});
			},
			//使用联查
			"useJoinSearch":function(opts,callback){
				require(["resources/egov/js/asset/aims/aimsuse/aimsuse/aimsuse_add.js"],function(use){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					use.showAimsUseBillInfo(opts,callback);
				});
			},
			//回收
			"recoverJoinSearch":function(opts,callback){
				require(["resources/egov/js/asset/aims/aimsuse/aimsrecover/aimsrecover_add.js"],function(use){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					use.showAimsRecoverBillInfo(opts,callback);
				});
			},
			"splitJoinSearch":function(opts,callback){

				require(["resources/egov/js/aims/card/aimssplit/aimssplit_add.js"],function(split){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					split.showPage(split.ACTION.VIEW,opts,callback);
				});
			},
            "changeJoinSearch":function(opts,callback){
            	require(["resources/egov/js/aims/card/aimschange/aimschange_add.js"],function(change){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					change.showPage(change.ACTION.VIEW,opts,callback);
				});

			},
			"stdlCardTodo":function(opts,callback){
				require(["resources/egov/js/asset/stdl/aimscard6/aimscard6_main.js"],function($card){
					var def = {
							assetId:'',
							assetClassify6Code:'',
							assetCode:'',
							aseetName:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1

						};
					$.extend(def, opts);
					$card.showToDoPage($card.ACTION.VIEW,opts,callback);
				});
			},
			"lvcaCardTodo":function(opts,callback){
				require(["resources/egov/js/asset/lvca/aimscard6/aimscard6_main.js"],function($card){
					var def = {
							assetId:'',
							assetClassify6Code:'',
							assetCode:'',
							aseetName:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1

						};
					$.extend(def, opts);
					$card.showToDoPage($card.ACTION.VIEW,opts,callback);
				});
			},
			"oamsCardTodo":function(opts,callback){
				var jsPath = "resources/egov/js/asset/oams/aimscard6/aimscard6_main.js";
				var assetType6Code = opts.assetType6Code;
				if (typeof assetType6Code == 'undefined' || assetType6Code == null) {
					assetType6Code = '';
				}
				if(assetType6Code.indexOf("10201") == 0){
					jsPath = "resources/egov/js/asset/hpro/aimscard6/aimscard6_main.js";
				}
				require([jsPath],function($card){
					var def = {
							assetId:'',
							assetClassify6Code:'',
							assetCode:'',
							aseetName:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1

						};
					$.extend(def, opts);
					$card.showToDoPage($card.ACTION.VIEW,opts,callback);
				});
			},
			"cardTodo":function(opts,callback){
				require(["resources/egov/js/aims/card/aimscard6/aimscard6_main.js"],function($card){
					var def = {
							assetId:'',
							assetClassify6Code:'',
							assetCode:'',
							aseetName:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1

						};
					$.extend(def, opts);
					$card.showToDoPage($card.ACTION.VIEW,opts,callback);
				});
			},
            "voidTodo":function(opts,callback){

				require(["resources/egov/js/aims/card/aimsvoid/aimscard6_void.js"],function(cardVoid){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					cardVoid.showToDoPage(cardVoid.ACTION.VIEW,opts,callback);
				});
			},
			//卡片拆分
			"splitTodo":function(opts,callback){

				require(["resources/egov/js/aims/card/aimssplit/aimssplit_add.js"],function(split){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					split.showToDoPage(split.ACTION.VIEW,opts,callback);
				});
			},
			//经管拆分
			"oamssplitTodo":function(opts,callback){
				require(["resources/egov/js/asset/oams/aimssplit/aimssplit_add.js"],function(split){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					split.showToDoPage(split.ACTION.VIEW,opts,callback);
				});
			},
			"changeTodo":function(opts,callback){
            	require(["resources/egov/js/aims/card/aimschange/aimschange_add.js"],function(change){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					change.showToDoPage(change.ACTION.VIEW,opts,callback);
				});

			},
			"oamschangeTodo":function(opts,callback){
            	require(["resources/egov/js/asset/oams/aimschange/aimschange_add.js"],function(change){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					change.showToDoPage(change.ACTION.VIEW,opts,callback);
				});
			},
			"lvcachangeTodo":function(opts,callback){
            	require(["resources/egov/js/asset/lvca/aimschange/aimschange_add.js"],function(change){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					change.showToDoPage(change.ACTION.VIEW,opts,callback);
				});

			},
			"cardTodoJoin":function(opts,callback){
				var assetType6Code = opts.assetType6Code;
				  if(typeof assetType6Code == 'undefined' || assetType6Code == null){
					  assetType6Code = '';
				  }
				  if(assetType6Code.indexOf('102')==0){
					  $.oamsCardTodo(opts,callback); 
				  } else if(assetType6Code.indexOf('201')==0){
					  $.lvcaCardTodo(opts,callback);
				  } else if(assetType6Code.indexOf('10102') == 0){
				     $.stdlCardTodo(opts,callback);
				  } else {
					  $.cardTodo(opts,callback);
				  }
			},
			//流程监控跳转接口
			"todoJoinMonitor":function(type,opts,callback){
				var nType = type;
				if(typeof type == 'undefined' || type == null){
					type = '';
				}
				type = type.toUpperCase().split('_')[0];
				if(type == 'CARD'){ //卡片
					if(nType == 'CARD_VOID'){
					  $.voidTodo(opts, callback);
					} else {
					  var assetType6Code = opts.assetType6Code;
					  if(typeof assetType6Code == 'undefined' || assetType6Code == null){
						  assetType6Code = '';
					  }
					  if(assetType6Code.indexOf('102')==0){
						  $.oamsCardTodo(opts,callback);
					  } else if(assetType6Code.indexOf('201')==0){
						  $.lvcaCardTodo(opts,callback);
					  } else if(assetType6Code.indexOf('10102') == 0){
						  $.stdlCardTodo(opts,callback);
					  } else {
					     $.cardTodo(opts,callback);
					  }
					}
					//$.cardJoinSearch(opts, callback);
				} else if(type == 'USE'){
					$.useJoinSearch(opts,callback);
				} else if(type == 'RECOVER'){
					$.recoverJoinSearch(opts,callback);
				} else if(type == 'SPLIT'){ //拆分
//					$.splitJoinSearch(opts, callback);
					var assetType6Code = opts.assetType6Code;
					if(typeof assetType6Code == 'undefined' || assetType6Code == null){
						  assetType6Code = '';
					}
					if(assetType6Code.indexOf('102')==0){
					   $.oamssplitTodo(opts,callback); 
					} else {
						$.splitTodo(opts, callback);
					}  
					
				} else if(type == 'DISPOSE'){//处置
					if(nType == 'DISPOSE_WASTE_RECOVEY' || nType == 'DISPOSE_WASTE_PROCESS'){
						opts.viewType = (nType == 'DISPOSE_WASTE_PROCESS') ? 0 : 1;
						if(nType == 'DISPOSE_WASTE_PROCESS'){
						  $.disposeWasteRecoveyTodo(opts, callback);
						} else {
						  $.disposeRecycleTodo(opts,callback);
						}
					} else if(nType == 'DISPOSE_WAITRECEIVE'){
					     $.disposeWaitReceiveTodo(opts, callback);
					} else {
						 $.disposeTodo(opts, callback);
					}
				} else if(type == 'CHANGE'){ //变动
//					$.changeJoinSearch(opts, callback);
					 var assetType6Code = opts.assetType6Code;
					  if(typeof assetType6Code == 'undefined' || assetType6Code == null){
						  assetType6Code = '';
					  }
					  if(assetType6Code.indexOf('102')==0){
						  $.oamschangeTodo(opts,callback); 
					  } else if(assetType6Code.indexOf('201')==0){
						  $.lvcachangeTodo(opts,callback);
					  } else {
						  $.changeTodo(opts, callback);
					  }
					
				} else if(type == 'BCPM'){

					if(nType == 'BCPM_PROJECT_ADD' || nType == 'BCPM_PROJECT_EDIT'){
						$.projectTodo(opts,callback);
					} else if(nType == 'BCPM_TMPUSE'){
						$.projectTmpUseTodo(opts,callback);
					} else if(nType == 'BCPM_DISPOSE'){
						$.projectDisposeTodo(opts,callback);
					} else if(nType == 'BCPM_TRANSFER'){
						$.projectTransferTodo(opts,callback);
					} else if(nType == 'BCPM_PROJECT_SPLIT'){
						//细化
						$.projectSplitTodo(opts,callback);
					} else if(nType == 'BCPM_FINAL'){
						$.projectFinal(opts,callback);
					} else if(nType == 'BCPM_TMPUSE_NEW'){
						opts.bizType = 'NEW';
						opts.billId = opts.bizId;
						$.projectTmpUseTodo(opts,callback);
					} else if(nType == 'BCPM_TRANSFER_NEW'){
						opts.bizType = 'NEW';
						opts.billId = opts.bizId;
						$.projectTransferTodo(opts,callback);
					} else if(nType == 'BCPM_DISPOSE_NEW'){
						opts.bizType = 'NEW';
						opts.billId = opts.bizId;
						$.projectDisposeTodo(opts,callback);
					} else if(nType == 'BCPM_FINAL_NEW'){
						opts.bizType = 'NEW';
						opts.billId = opts.bizId;
						$.projectFinal(opts,callback);
					} else if(nType == 'BCPM_RECEIVE'){
						$.projectReceive(opts,callback);
					}
				}else if(type.indexOf("REPORT") == 0){
					opts.targetFlag = type.substring(6);
					$.reportFinal(opts,callback);
				} else if(type.indexOf("INVENTORY") == 0){
					if(nType == 'INVENTORY_TASK'){
					  $.inventoryTaskTodo(opts,callback);
					} else if(nType == 'INVENTORY_PLAN_BILL'
							|| nType == 'INVENTORY_PLAN'
							|| nType == 'INVENTORY_PLAN_RESULT'){
						opts.nType = nType;
						$.inventoryPlanTodo(opts,callback);
					} else if (nType == "INVENTORY_CHECK") {
						opts.nType = nType;
						$.inventoryCheckTodo(opts,callback);
					}
					else{
						$a.assetMsg.warn("未知的盘点业务类型【" + nType +"】！");
					}
				} else if(type == "REGISTER"){
					if(nType.indexOf("REGISTER_") == 0){
						opts.nType = nType;
						$.registerTaskTodo(opts,callback);
					}else{
						$a.assetMsg.warn("未知的盘点业务类型【" + nType +"】！");
					}
				} 
				else if(nType == "USER_MANAGE"){
					$.userManageTodo(opts,callback);
				} else if(type == 'HPRO'){
					if(nType == 'HPRO_PROFIT'){
						$.hproProfitTodo(opts, callback);
					} else{
						$a.assetMsg.warn('未知的房产业务类型【' + nType + '】！');
					}
				}
				else {
					$a.assetMsg.warn("未知的业务类型【" + nType +"】！");
				}
			},
			//占有登记
			registerTaskTodo:function(opts,callback){
				if(opts.nType == "REGISTER_MAIN"){
					require(["resources/egov/js/aims/register/aimsregistermain/aimsregistermain_task.js"],function(reportFinal){
						reportFinal.showMainDialog(opts,callback);
					});
				}else if(opts.nType == "REGISTER_CHANGE"){
					require(["resources/egov/js/aims/register/aimsregisterchange/aimsregisterchange_task.js"],function(reportFinal){
						reportFinal.showMainDialog(opts,callback);
					});
				}else{
					$a.assetMsg.warn("未知的盘点业务类型【" + nType +"】！");
				}
			},
			//盘点任务
			inventoryTaskTodo:function(opts,callback){
				require(["resources/egov/js/aims/inventory/aimsinventorytask/aimsinventorytask_add.js"],function(inventoryTodo){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1,
							isTodo:0
						};
					$.extend(def, opts);
					inventoryTodo.showAimsInventoryBillInfo(opts,callback);
				});
			},
			//盘点计划
			inventoryPlanTodo:function(opts,callback){
				require(["resources/egov/js/aims/inventory/aimsinventory/aimsinventory_index.js"],function(inventoryTodo){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1,
							isTodo:0
						};
					$.extend(def, opts);
					inventoryTodo.showAimsInventoryBillInfo(opts,callback);
				});
			},
			
			// 盘点批次
			inventoryCheckTodo:function(opts,callback){
				require(["resources/egov/js/aims/inventory/aimsinventorycheck/aimsinventorycheck_add.js"],function(inventoryTodo){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1,
							isTodo:0
						};
					$.extend(def, opts);
					inventoryTodo.showAimsInventoryBillInfo(opts,callback);
				});
			},
			//资产年报代办
			reportFinal:function(opts,callback){
				var assetType6Code = opts.assetType6Code;
				var period = opts.str01;
				if(typeof assetType6Code == 'undefined' || assetType6Code == null){
					//默认固定资产
					assetType6Code = '10101';
				}
				if(assetType6Code == '102'){
					require(["resources/egov/js/aims/report/main/zfjg_common_dialog.js"],function(reportFinal){
						reportFinal.showReportDialog(opts,callback);
					});
				}else if(assetType6Code == '199'){
					require(["resources/egov/js/aims/report/main/zfbb_common_dialog.js"],function(reportFinal){
						reportFinal.showReportDialog(opts,callback);
					});
				}else if(assetType6Code == '299'){
					require(["resources/egov/js/aims/report/zcczcs/zcczcs_common_dialog.js"],function(reportFinal){
						reportFinal.showReportDialog(opts,callback);
					});
				}else if(assetType6Code == '298'){
					require(["resources/egov/js/aims/report/qygyqy/qygyqy_common_dialog.js"],function(reportFinal){
						reportFinal.showReportDialog(opts,callback);
					});
				}else if(assetType6Code == '297'){
					require(["resources/egov/js/aims/report/gljbb/report_common_dialog.js"],function(reportFinal){
						reportFinal.showReportDialog(opts,callback);
					});
				}else if(assetType6Code == '10101' && period == '2016'){
					require(["resources/egov/js/aims/report/main/common_dialog.js"],function(reportFinal){
						reportFinal.showReportDialog(opts,callback);
					});
				}else{
					require(["resources/egov/js/aims/report/nb/report_common_dialog.js"],function(reportFinal){
						reportFinal.showReportDialog(opts,callback);
					});
				}
			},
			//账务处理
			projectFinal:function(opts,callback){
				require(["resources/egov/js/asset/bcpm/project/split/projectFinal_add.js"],function(projectFinal){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					if(opts.bizType){
					   projectFinal.showNewTodoPage(opts,callback);
					} else {
					   projectFinal.showTodoPage(opts,callback);
					}
				});
			},
			//系统内调拨接收单
			disposeWaitReceiveTodo:function(opts,callback){
				//require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_allotreceive_add.js"],function(disposeWaitReceive){
				require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_add.js"],function(disposeWaitReceive){
				var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1,
							isTodo:0
						};
					$.extend(def, opts);
					disposeWaitReceive.showAimsDisposeBillInfo(opts,callback);
				});
			},
			//报废清单
			disposeWasteRecoveyTodo:function(opts,callback){
			//	require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_billshowmerge.js"],function(disposeWasteRecovey){
				//require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_billshow_ex.js"],function(disposeWasteRecovey){
				//require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_billrecycle.js"],function(disposeWasteRecovey){	
				require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_add.js"],function(disposeWasteRecovey){
				var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1,  
							isTodo:0
						};
					$.extend(def, opts);
					disposeWasteRecovey.showAimsDisposeBillInfo(opts,callback);
				});
			},
			//回收单
			disposeRecycleTodo:function(opts,callback){
				//require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_recycle_ex.js"],function(disposeWasteRecovey){
				//require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_billrecycle.js"],function(disposeWasteRecovey){
				require(["resources/egov/js/asset/aims/dispose/aimsdispose/aimsdispose_add.js"],function(disposeWasteRecovey){
				var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1,
							isTodo:0
						};
					$.extend(def, opts);
					disposeWasteRecovey.showAimsDisposeBillInfo(opts,callback);
				});
			},
			//接收
			projectReceive:function(opts, callback){
				require(["resources/egov/js/asset/bcpm/receive/bcpmreceive/bcpmreceive_add.js"],function(projectReceive){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					projectReceive.showPage(projectReceive.ACTION.VIEW,opts,function(){
						callback()
					});

			   });
			},
			//项目新增、细化、修改（调概）
			projectTodo:function(opts, callback){
				require(["resources/egov/js/asset/bcpm/project/bcpmproject_add.js"],function(projectAdd){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					projectAdd.showProjectTodoPage(opts,callback);
				});
			},
			//项目细化、修改（调概）
			projectSplitTodo:function(opts, callback){
				require(["resources/egov/js/asset/bcpm/project/split/projectSplit_edit.js"],function(projectSplitAdd){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					projectSplitAdd.showTodoPage(opts,callback);
				});
			},
			projectTmpUseTodo:function(opts, callback){
				require(["resources/egov/js/asset/bcpm/tmpuse/bcpmtmpuse/bcpmtmpuse_add.js"],function(projectTmpUse){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
						};
					$.extend(def, opts);
					if(opts.bizType){
						projectTmpUse.showNewTodoPage(opts, callback);
					} else {
					   projectTmpUse.showTodoPage(opts,callback);
					}
				});
			},
			projectTransferTodo:function(opts, callback){
				require(["resources/egov/js/asset/bcpm/transfer/bcpmtransfer/bcpmtransfer_add.js"],function(projectTmpUse){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
					};
					$.extend(def, opts);
					if(opts.bizType){
					   projectTmpUse.showNewTodoPage(opts,callback);
					} else {
					   projectTmpUse.showTodoPage(opts,callback);
					}
				});
			},
			projectDisposeTodo:function(opts, callback){
				require(["resources/egov/js/asset/bcpm/dispose/bcpmdispose/bcpmdispose_add.js"],function(projectTmpUse){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
					};
					$.extend(def, opts);
					if(opts.bizType){
						projectTmpUse.showNewTodoPage(opts, callback);
					} else {
					  projectTmpUse.showTodoPage(opts,callback);
					}
				});
			},
			
			//用户管理待办
			userManageTodo:function(opts, callback){
				require(["resources/egov/js/asset/amcuser/amcuser_modify.js"],function(userManage){
					var def = {
							billId:'',
							bizStatus:-1,
							lifeState:-1,
							toDoStatus:-1
					};
					$.extend(def, opts);
					//isNeedAudit参数用于区分是否需要走审核流程（值为1时表明需要走审核流程）
					opts.isNeedAudit = "1";
					userManage.showUserInfo(opts, callback);
				});
			},
			//所有联查公共口
			"joinSearch":function(type,opts,callback){
				if(typeof type == 'undefined'){
					type = '';
				}
				type = type.toUpperCase().split('_')[0];
				if(type == 'CARD'){ //卡片
					$.cardJoinSearch(opts, callback);
				} else if(type == 'USE'){
					$.useJoinSearch(opts,callback);
				} else if(type == 'SPLIT'){ //拆分
					$.splitJoinSearch(opts, callback);
				} else if(type == 'DISPOSE'){//处置
					$.disposeJoinsSearch(opts, callback);
				} else if(type == 'CHANGE'){ //变动
					$.changeJoinSearch(opts, callback);
				} else if(type == 'BCPM'){

				} else {
					$a.assetMsg.warn("未知的业务类型【" + type +"】！");
				}
			},
			//流转状态
			"flowSearch":function(opts,callback){
				require(["resources/egov/js/asset/aims/activity/activity_add.js"],function(acdlg){
					var def = {
							busType:'',
							businessId:'',
							data:{},
							auditType:''
						};
					$.extend(def, opts);
//					var data ={
//							"busType":'BCPM_PROJECT',
//							"businessId":rows[0].prjId,
//							"data":rows[0]
//					     };
//					var data ={"busType":'BCPM_PROJECT',"businessId":rows[0].prjId,"data":rows[0]};
//					acdlg.showPage(acdlg.SHOW_TYPE.SHOW_LIST,acdlg.AUDIT_TYPE.BCPM_PROJECT_AUDIT,data,function () {
//                        
//                     })
					acdlg.showPage(acdlg.SHOW_TYPE.SHOW_LIST,def.auditType,def,callback);
				});
			},
			//关闭当前活动tab页
			closeCurrentTab:function(){
				$('.page-tabs-content').find('.active i').trigger("click");
			},
			/**
			 * 房产收益待办
			 */
			hproProfitTodo: function(opts, callback){
				require(['resources/egov/js/asset/hpro/profit/hpro_profit_add'], function(dlg){
					dlg.showPage(null, opts, callback);
				});
			},

			//字符串拼接字符
			stringJoinChar:function(){
				return '-';
			},
			//是否统管单位
			isMgrOrg:function(){
				if(typeof $ismgrorg === 'undefined'){
					 $A.ajax.ajaxCall({
			                url:'egov/asset/api/org/getCurretnOrgMgrType.do',
			                type:'GET',
			                async: false,
			                callback:function (data) {
			                    if(data == 1 || data == '1'){
			                    	$ismgrorg = true;
			                    } else {
			                    	$ismgrorg = false;
			                    }
			                }
			            });
				}
				return $ismgrorg;
			},
			//获取当前登录单位信息
			getCurrentOrgInfo:function(){
				if(typeof $currentorg === 'undefined'){
					 $A.ajax.ajaxCall({
			                url:'egov/asset/api/getCurrentOrgInfo.do',
			                type:'GET',
			                async: false,
			                callback:function (data) {
			                	$currentorg = data;
			                }
			            });
				}
				return $currentorg;
			},
			//
			clearInfo:function(){
				$ismgrorg = undefined;
				$currentorg = undefined;
			},
			getCurrentUser:function(){
				if(typeof $user === 'undefined'){
					 $A.ajax.ajaxCall({
			                url:'egov/asset/api/getUserInfo.do',
			                type:'GET',
			                async: false,
			                callback:function (data) {
			                	$user = data;
			                }
			            });
				}
				return $user;
			},
			//格式化点击网格列
			formatGridClickCol:function(val,callback){
//				var $html = $('').text(val);
//				$html.on("click",function(e){
//      	      	   if(!!callback){
//      	      		   callback();
//      	      	   }
//				})
				if(typeof val==='undefined' || val == null){
					return '';
				}
//				if(val === ''){
//					val = '&nbsp;&nbsp;';
//				}
				return '<span class="grid-format-col" title="' + val + '">' + val + '</span>';
			},
            //是否关闭当前对话框
            closeConfirm:function (options) {
                if(!!!options){
                    options = {};
                }
                if(!$.isFunction(options.ok)){
                    options.ok = $.noop;
                }
                if(!$.isFunction(options.cancel)){
                    options.cancel = $.noop;
                }

                return function ($dlg,event) {
                    if(!!!event){
                        return;
                    }
                    var flg = $dlg.data('_custom_flg');
                    if(typeof flg==='undefined'){
                        flg = false;
                        $A.assetMsg.confirm(options.msg,{
                            okVal:'是',
                            cancelVal:'否',
                            ok:function () {
                                options.ok();
                                $dlg.data('_custom_flg',true);
                                $dlg.closeDialog();
                                return;
                            },
                            icon:'question',
                            cancel:function () {
                                options.cancel();
                            }
                        })
                    }else {
                        $dlg.data('_custom_flg',undefined);
                    }

                    return flg;
                }
            },
            //是否关闭当前对话框
            /**
			 * {
			 * lock:false
			 * }
             * @param obj
             * @returns {Function}
             */
			closeCustom:function (options) {
                if(!!!options){
                    options = {};
                }
                return function ($dlg,event) {
                    if(!!!event){
                        return;
                    }
                    var flg = $dlg.data('_custom_flg');
                    if(typeof flg==='undefined'){
                        flg = false;
                        $A.assetMsg.info(options.msg,{
                        	ok:false,
                        	icon:'question',
                            button:options.button
                        });
                    }else {
                        $dlg.data('_custom_flg',undefined);
                    }
                    return flg;
                }
            },
            MD5:function(text){
            	return md5(text);
            },
            //流程帮助
            flowHelp:function(bizType){
            	$("#_ajaxProgressBar").hide();
            	var obj = flowHelp[bizType];
            	if(!!obj){
            		var options= {
            			 lock:true,
            			 width:'70%',
            			 height:'500px',
            			 title : obj.bizTypeName + "流程帮助",
            			 content: "<div style='width:\"100%\";height:500px;overflow:hidden;overflow-y:auto;';><img src='resources/flowhelp/" + obj.imgSrc + "'></div>"
            		}
            		$._amdialog(options);
            	}
            },
            //总和计算
            sumext:function (){
                var m;
                var sumA = [];
                var sum = 0;
                $(arguments).each(function(i,item){
                    var r1 = 0;
                    try{r1 = item.toString().split(".")[1].length}catch(e){r1 = 0};
                    sumA.push(r1);
                });
                m = Math.pow(10, Math.max.apply(window,sumA));
                $(arguments).each(function(i,item){
                    sum = sum + parseInt(parseFloat(item*m).toFixed(2));
                });
                return sum/ m;
            },
    		commonSlider:function (customOP) {
                $A("#common-attachment-div").createNewAttach(customOP);
            },
			sliderAttachRequired:function (isMsg,attId,checkType) {
				var attachId ='';
            	if(attId){
            		attachId =attId;
            	}else{
            		attachId ="common-attachment-div";
            	}
            	
            	//额外参数
            	var itemCnt = 0;//附件项个数
            	var fileCnt = 0;//包含文件的附件项 个数
            	if(!checkType){
            		checkType = 0;
            	}
				
            	var required = false;
            	var requiredType = 1;
            	var attachments = [];
				var remarkList = [];
				if(typeof isMsg === 'undefined'){
					isMsg = true;
				}
                $A('[id='+attachId+'] input:not([type="file"]):not(".upload-remark"):not(".upload-progress")').each(function (i,item) {
                	itemCnt++;
                	var $input = $(item);
					var upload = $input.data('upload');
					var attachment = $input.data('attachment');

                    var $remark = $input.closest('div.upload-div').find('.upload-remark');
                    $remark.removeClass('remark-required');
                    var remark = $.trim($remark.val());
                    //判断 当attachment.required==1 时 附件文字二选一 
                    //当 attachment.required = 2 时 附件必须上传 xds 2017-08-15
                    if(attachment&&attachment.required=='1'){
	                    if((remark===''||remark.length<10)&&upload.getFileList().length==0){
	                        attachments.push(attachment);
	                        required = true;
	                        requiredType = 1;
	                        $remark.addClass('remark-required');
	                        remarkList.push($remark);
						}
                    } else if (attachment&&attachment.required=='2'){
                    	if(upload.getFileList().length==0){
                    		attachments.push(attachment);
 	                        required = true;
 	                        requiredType = 2;
 	                        $remark.addClass('remark-required');
 	                        remarkList.push($remark);
                    	}
                    }
                    if(upload.getFileList().length >0 ){
                    	fileCnt++;//附件项有附件
                    }
                });
                if(remarkList.length){
                    remarkList[0].focus();
				}
				if(required){
                    var msg = '';
                    $(attachments).each(function(i,item){
                        msg = '['+item.itemName+'] '
                    });
                    if(requiredType == 1){
                      msg = msg + '附件不能为空！或者文字说明最少10个字符！';
                    } else if(requiredType == 2){
                    	msg = msg + '附件不能为空！';
                    }
                    $assetMsg.warn(msg);
				} else { //核验都通用情况下 若checkType为1 所有附件项 必须有一个附件项有附件
					if(checkType == 1 && fileCnt == 0){
						required = true;
						$assetMsg.warn('至少上传一个附件！');
					}
					
				}
                return {required:required,attachments:attachments}
            },
            //保存备注
            saveUploadRemark:function (attId) {
               	var attachId ='';
            	if(attId){
            		attachId =attId;
            	}else{
            		attachId ="common-attachment-div";
            	}
                var required = false;
                var remarks = [];
                $('[id='+attachId+'] input.upload-remark').each(function (i,item) {
                    var $remark = $(item);
                    var remark = $.trim($remark.val());
                    var bizId = $remark.attr('bizId');
                    if(remark!==''){
                        remarks.push({
                            bizId:bizId,
                            remark:remark
                        });
					}

                });
                $.ajax('attachment/doInsertRemark.do',{
                    data:{list:JSON.stringify(remarks)},
                    type:'POST',
                    dataType: "json",
                    callback:function (json,data) {

                    }
                });

            },
            loadRemark:function (attId) {
            	var attachId ='';
            	if(attId){
            		attachId =attId;
            	}else{
            		attachId ="common-attachment-div";
            	}
                var groupIds = [];
                $A('[id='+attachId+'] input:not([type="file"])[id]').each(function (i,item) {
                    var $input = $(item);
                    groupIds.push($input.prop('id'));

                });
                $A.ajax.ajaxCall({
                    url:'attachment/getInsertRemark.do',
                    data:{groupIds:groupIds[0]},
                    type:'GET',
                    callback:function (json,data) {
                    	$(json).each(function (i,item) {
                            var $remark = $A('#'+item.bizId).find('.upload-remark');
                            $remark.val(item.remark);
                        });
                    }
                });
            },
			//根据字典代码设置
			setTxtByDicCode:function(options,format){
            	if(!!!options){
            		return undefined;
				}
                var formatFun = $.isFunction(format)?format:$.noop;
                var dictCodes = $(options).map(function(i,item){
                    return item.code
                });
                $.ajax('egov/asset/aims/dicthelper/getDictItems.do',{
                    type: 'POST',
                    dataType: "json",
                    data:{dictCodes:JSON.stringify(dictCodes.toArray())},
                    success:function(dicMap){
                        $(options).each(function (i,item) {
                            if(!!!item.code){
                                return;
                            }
                            if($.isFunction(item.format)){
                                formatFun = item.format;
                            }
                            var selector  = $A;
                            if(options.isJQ){
                                selector = $;
                            }
                            formatFun(selector(item.id),dicMap[item.code],dicMap);
                        });
                    }
                });
			},
            '_toString':function (val){
				if(val == null || val == 'null'){
					return '';
				}
				return val.toString();
			},
			//显示隐藏简单查询
			queryHide:function (id) {
            	id = '#'+id;
                var $td = $A(id).closest('td');
                $td.hide().prev().hide();
            },
			queryShow:function (id) {
                id = '#'+id;
                var $td = $A(id).closest('td');
                $td.show().prev().show();
            },
            // 格式化使用年限
            formatterExpectedDate : function(expectedDate){
            	if(isNaN(expectedDate)){
					return '';
				}
				expectedDate = parseInt(expectedDate);
            	return !expectedDate ? '' : 
            		(Math.floor(expectedDate/12) > 0 ? Math.floor(expectedDate/12) + '年' : '')
            		+ ((expectedDate%12) > 0 ? expectedDate%12 + '个月' : '');
            },
            /**
             * $combo: 所绑定组件
             * $node: 要添加按钮的节点 
             * 		  comboztree -> $('#' + node.tId + '_a')  取下拉树节点 li>a 
             * 		  combobox   -> $('#bs_01')	下拉项 li
             * opts: 参数配置
             */
            comboItemAddHover: function($combo, $node, opts){
            	var _this = this;
            	opts = opts || {};
            	
            	opts.viewBtn = opts.viewBtn == false ? false : true;
            	opts.addBtn = opts.addBtn == false ? false : true;
            	opts.editBtn = opts.editBtn == false ? false : true;
            	opts.delBtn = opts.delBtn == false ? false : true;
            	opts.viewTitle = opts.viewTitle || '查看';
            	opts.addTitle = opts.addTitle || '新增';
            	opts.editTitle = opts.editTitle || '修改';
            	opts.delTitle = opts.delTitle || '删除';
            	opts.params = opts.params || {};
            	
            	var btnId = $node.attr('id');
            	
            	// ie8处理
            	if(navigator.appVersion.indexOf('MSIE 8.0') != -1){
            		var style = {
            			display: 'inline-block',
            			width: '100%'
            		};
            		
            		if(opts.widgetType && opts.widgetType.toUpperCase() === 'COMBOBOX'){
            			style.color = '#257dd6';
            			style.background = '#ffd';
            			style['font-weight'] = 'bold';
            			$node.addClass('combobox-item')
            		}
            		
            		$node.css(style);
            	}
            	
            	// 查看按钮
            	if(!!opts.viewBtn && !document.getElementById('viewBtn_' + btnId)){
            		var $viewBtn = document.createElement('span');
            		$viewBtn.id = 'viewBtn_' + btnId;
            		$viewBtn.className = 'combo-btn';
            		
            		$viewBtn.title = opts.viewTitle;
            		$viewBtn.style.display = 'inline-block';
            		$viewBtn.style.backgroundImage = 'url("resources/egov/js/ext/theme/images/new-icons.png")';
            		$viewBtn.style.backgroundPosition = '-21px -63px';
            		$viewBtn.style.width = '20px';
            		$viewBtn.style.height = '20px';
            		$viewBtn.style.verticalAlign = 'middle';
            		
            		$viewBtn.onclick = function(){
    					// 如果viewUrl不存在，viewCallback存在，则只执行自定义viewCallback,否则在viewUrl回调成功后执行viewCallback
            			if(!!opts.viewCallback && typeof opts.viewCallback === 'function'){
            				opts.viewCallback();
            			} else {
            				_this._showEdit(opts, $combo, 'VIEW');
            			}
            		}
            		
            		$node.append($viewBtn);
            	}
            	
            	// 新增按钮
            	if(!!opts.addBtn && !document.getElementById('addBtn_' + btnId)){
            		var $addBtn = document.createElement('span');
            		$addBtn.id = 'addBtn_' + btnId;
            		$addBtn.className = 'combo-btn';
            		
            		$addBtn.title = opts.addTitle;
            		$addBtn.style.display = 'inline-block';
            		$addBtn.style.backgroundImage = 'url("resources/common/themes/default/images/tree/ztreestandard.gif")';
            		$addBtn.style.backgroundPosition = '0px -73px';
            		$addBtn.style.width = '20px';
            		$addBtn.style.height = '20px';
            		$addBtn.style.verticalAlign = 'middle';
            		
            		$addBtn.onclick = function(){
            			// 如果addUrl不存在，addCallback存在，则只执行自定义addCallback,否则在addUrl回调成功后执行addCallback
            			if(!opts.addUrl && !!opts.addCallback && typeof opts.addCallback === 'function'){
            				opts.addCallback();
            			} else if(!!opts.addUrl){
            				_this._showEdit(opts, $combo, 'ADD');
            			}
            		}
            		
            		$node.append($addBtn);
            	}
            	
            	// 编辑按钮
            	if(!!opts.editBtn && !document.getElementById('editBtn_' + btnId)){
            		var $editBtn = document.createElement('span');
            		$editBtn.id = 'editBtn_' + btnId;
            		$editBtn.className = 'combo-btn';
            		
            		$editBtn.title = opts.editTitle;
            		$editBtn.style.display = 'inline-block';
            		$editBtn.style.backgroundImage = 'url("resources/common/themes/default/images/tree/ztreestandard.gif")';
            		$editBtn.style.backgroundPosition = '0px -91px';
            		$editBtn.style.width = '20px';
            		$editBtn.style.height = '20px';
            		$editBtn.style.verticalAlign = 'middle';
            		
            		$editBtn.onclick = function(){
            			// 如果editUrl不存在，editCallback存在，则只执行自定义editCallback,否则在editUrl回调成功后执行editCallback
            			if(!opts.editUrl && !!opts.editCallback && typeof opts.editCallback === 'function'){
            				opts.editCallback();
            			} else if(!!opts.editUrl){
            				_this._showEdit(opts, $combo, 'EDIT');
            			}
            		}
            		
            		$node.append($editBtn);
            	}
            	
            	// 删除按钮
            	if(!!opts.delBtn && !document.getElementById('delBtn_' + btnId)){
            		var $delBtn = document.createElement('span');
            		$delBtn.id = 'delBtn_' + btnId;
            		$delBtn.className = 'combo-btn';
            		
            		$delBtn.title = opts.delTitle;
            		$delBtn.style.display = 'inline-block';
            		$delBtn.style.backgroundImage = 'url("resources/common/themes/default/images/tree/ztreestandard.gif")';
            		$delBtn.style.backgroundPosition = '0px -115px';
            		$delBtn.style.width = '20px';
            		$delBtn.style.height = '20px';
            		$delBtn.style.verticalAlign = 'middle';
            		
            		$delBtn.onclick = function(){
            			// 如果delUrl不存在，delCallback存在，则只执行自定义delCallback,否则在delUrl回调成功后执行delCallback
            			if(!opts.delUrl && !!opts.delCallback && typeof opts.delCallback === 'function'){
            				opts.delCallback();
            			} else if(!!opts.delUrl){
            				_this._doDelete($combo, opts);
            			}
            		}
            		
            		$node.append($delBtn);
            	}
            	
            	if($node.find('.combo-btn').length > 0){
            		$node.find('.combo-btn')[0].style.marginLeft = '5px';
            	}
            },
            /**
             * 移除增、删、改、查按钮
             */
            comboItemRemoveHover: function($node){
            	var btnId = $node.attr('id');
            	var $viewBtn = document.getElementById('viewBtn_' + btnId);
            	var $addBtn = document.getElementById('addBtn_' + btnId);
            	var $editBtn = document.getElementById('editBtn_' + btnId);
            	var $delBtn = document.getElementById('delBtn_' + btnId);
            	
            	if(navigator.appVersion.indexOf('MSIE 8.0') != -1){
            		if($node.hasClass('combobox-item')){
            			$node.css({
                			color: '#434343',
                			background: 'none',
                			'font-weight': 'normal'
                		}).removeClass('combobox-item');
            		}
            	}
            	
            	if($viewBtn){
            		$viewBtn.parentNode.removeChild($viewBtn);
            	}
            	if($addBtn){
            		$addBtn.parentNode.removeChild($addBtn);
            	}
            	if($editBtn){
            		$editBtn.parentNode.removeChild($editBtn);
            	}
            	if($delBtn){
            		$delBtn.parentNode.removeChild($delBtn);
            	}
            },
            _doDelete: function($combo, opts){
            	$A.messager.confirm('确认删除' + opts.title + '【' + opts.params.itemName + '】？', {
					okCall: function(){
						$.ajax({
        					url: opts.delUrl,
        					data: {itemId: opts.params.itemId},
        					type: 'post',
        					dataType: 'json',
        					success: function(result){
        						// 删除成功刷新数据
        						$combo[opts.widgetType]('reload', $combo.attr('action'));
        						// 删除自动清除下拉框标签的值
        						$combo[opts.widgetType]('setText', '');
        						$combo[opts.widgetType]('setValue', '');
        						if(!!opts.delCallback && typeof opts.delCallback === 'function'){
        							opts.delCallback(result, opts.delCallback.arguments);
        						}
        					},
        					error: function(){
        						$A.messager.warn('删除失败，请刷新后重试！');
        					}
        				});
					}
				});
            },
            /**
             * 显示编辑框
             */
            _showEdit: function(opts, $combo, type){
            	var _this = this;
            	var _url = type === 'ADD' ? opts.addUrl : (
            		type === 'EDIT' ? opts.editUrl : ''
            	);
            	var _callback = type === 'ADD' ? opts.addCallback : (
            		type === 'EDIT' ? opts.editCallback : null
            	);
            	
            	// 读取标签的配置
            	var _options = $combo.attr('_options').replace(/^({\s*)|(\s}*)$/g, '');	// 读取配置并去前后空格
            	var _optionsArr = _options.split(',');
            	var optAction = '{';
            	for(var i = 0; i < _optionsArr.length; i++){
            		var arr = _optionsArr[i].split(':');
            		optAction += '"' + $.trim(arr[0]) + '":' + (
            				$.trim(arr[1]).indexOf("'") == -1 ? ('"' + $.trim(arr[1]) + '"') 
        						: $.trim(arr[1]).replace(/'/g, '"')) + ',';
            	}
            	optAction = optAction.replace(/(\s*,)$/, '') + '}';
            	var _opt = JSON.parse(optAction);
            	
            	var _action = !opts.action ? (!!$combo.attr('action') ? $combo.attr('action') : (
            			!!_opt ? _opt.action : ''
            		)) : opts.action;
            	
            	$.openModalDialog({
            		title: (type === 'VIEW' ? '查看' : (type === 'ADD' ? '新增' : '修改')) + opts.title,
            		mode: 'html',
            		url: _this._getPanel($combo, opts, opts.params, _url, _action, _callback, type)
            	});
            	
            	if(!!opts.afterShow && typeof opts.afterShow === 'function'){
            		opts.afterShow(opts.params, type);
            	}
            },
            /**
             * 初始化操作面板
             */
            _getPanel: function($combo, opts, params, url, action, callback, type){
            	var $html = $('<div class="form"></div>');
            	var $form = $('<form></form>');
            	var $table = $('<table></table>');
            	
            	if(type === 'ADD'){
            		// 新增用户可选新增同级、新增下级
            		$table.append($('<tr>' +
						'<td class="clabel" style="width:135px;padding-right:5px;font-size:15px;">新增方式</td>' +
						'<td colspan="3">' +
							'<div class="editor" style="text-align:left;height:28px;">' +
								'<div class="multipleboxDiv" style="padding-left:10px;line-height:28px;">' +
									'<div class="radioboxDiv">' +
										'<input id="selectSetType0" type="radio" name="addType" value="SAME" checked ' +
											'style="vertical-align:middle;margin-top:0;width:15px;height:15px;">' +
										'<label for="selectSetType0" style="vertical-align:middle;font-size:15px;">新增同级</label>' +
									'</div>' +
									'<div class="radioboxDiv">' +
										'<input id="selectSetType1" type="radio" name="addType" value="LOWER" ' +
											'style="vertical-align:middle;margin-top:0;width:15px;height:15px;">' +
										'<label for="selectSetType1" style="vertical-align:middle;font-size:15px;">新增下级</label>' +
									'</div>' +
								'</div>' +
							'</div>' +
						'</td>' +
					'</tr>'));
            		
            		// 新增显示当前点击节点的编码、名称
            		$table.append($('<tr>' +
        				'<td class="clabel" style="width:135px;text-align:right;padding-right:5px;font-size:15px;">当前' + opts.title + '编码</td>' +
        				'<td>' +
        					'<input id="currItemId" type="hidden" value="' + params.itemId + '">' +
        					'<input id="currItemCode">' +
        				'</td>' +
        				'<td class="clabel" style="width:135px;text-align:right;padding-right:5px;font-size:15px;">当前' + opts.title + '名称</td>' +
        				'<td>' +
        					'<input id="currItemName">' +
        				'</td>' +
            		'</tr>'));
            	}
            	
            	$table.append($('<tr>' +
					'<td class="clabel" style="width:135px;text-align:right;padding-right:5px;font-size:15px;">' + opts.title + '编码</td>' +
					'<td style="width:145px;">' +
						'<input id="itemId" type="hidden">' +
						'<input id="itemLevel" type="hidden">' +
						'<input id="itemCode">' +
					'</td>' +
					'<td class="clabel" style="width:135px;text-align:right;padding-right:5px;font-size:15px;">' + opts.title + '名称</td>' +
					'<td style="width:145px;"><input id="itemName"></td>' +
				'</tr>'));
            	
            	$table.find('input').css({
            		border: '1px solid #fff'
            	});
            	$form.append($table);
            	
            	$table.find('#itemCode,#itemName').textbox();
            	$table.find('#currItemCode').textbox({
            		readonly: true,
            		value: params.itemCode
            	});
            	$table.find('#currItemName').textbox({
            		readonly: true,
            		value: params.itemName
            	});
            	
            	$html.append($form);
            	$html.append($('<div class="dialog-footer" style="text-align:right;">' +
    					'<a class="app-button l-btn l-btn-small" id="okBtn" style="margin-right:20px;">确定</a>' +
    					'<a class="app-button l-btn l-btn-small" id="cancelBtn" style="margin-right:20px;">取消</a>' +
    				'</div>'));
            	
            	if(type === 'VIEW'){
            		$table.find('#currItemCode,#currItemName,#itemCode,#itemName').textbox('readonly', true);
            		$html.find('#okBtn').remove();
            		$html.find('#cancelBtn').text('关闭');
            	} else{
            		if(!opts.openCodeEdit){
            			$table.find('#itemCode').textbox('readonly', true);
            		}
            	}
            	
            	if(!!params.itemLevel){
        			$table.find('#itemLevel').val(params.itemLevel);
        		}
            	if(type !== 'ADD'){
            		if(params.itemId && params.itemCode && params.itemName){
            			$table.find('#itemId').val(params.itemId);
            			$table.find('#itemCode').textbox('setValue', params.itemCode);
                		$table.find('#itemName').textbox('setValue', params.itemName);
            		}
            	} else{
            		$table.find('#currItemCode,#currItemName').attr('readonly', 'readonly').css({
            			border: '1px solid #ddd',
            			background: '#ddd'
            		});
            	}
            	
            	// 确认修改或新增
            	$html.find('#okBtn').click(function(){
            		var data = {
        				itemCode: $html.find('#itemCode').val(),
            			itemName: $html.find('#itemName').val()
            		};
            		
            		if(type === 'ADD'){
            			// 新增
            			if($html.find('input[name="addType"]:checked').val() == 'LOWER'){
            				data.itemPid = $html.find('#currItemId').val();
            			}
            			data.itemPcode = $html.find('#currItemCode').val();
            			data.itemPname = $html.find('#currItemName').val();
            		} else{
            			// 修改
            			data.itemId = $html.find('#itemId').val();
            		}
            		if(!!$table.find('#itemLevel').val()){
            			data.itemLevel = $table.find('#itemLevel').val();
            		}
            		
            		// 验证
            		if(!!opts.openCodeEdit && !data.itemCode){
            			$A.messager.warn(opts.title + '编码输入不能为空！');
            			return false;
            		}
            		if(!data.itemName){
            			$A.messager.warn(opts.title + '名称输入不能为空！');
            			return false;
            		}
            		
            		$.ajax({
            			url: url,
            			data: data,
            			type: 'post',
            			dataType: 'json',
            			success: function(result){
            				var itemName = $html.find('#itemName').val();
            				var itemCode = $html.find('#itemCode').val();
            				itemCode = !itemCode ? '' : itemCode + '-';
            				
            				$combo[opts.widgetType]('reload', action);
            				if(!!callback && typeof callback === 'function'){
            					callback(result, callback.arguments);
            				}
            				$.closeDialog();
            			},
    					error: function(){
    						$A.messager.warn((type === 'ADD' ? '新增' : '修改') + '失败请重试！');
    					}
            		});
            	});
            	$html.find('#cancelBtn').click(function(){
            		$.closeDialog();
            	});
            	
            	return $html;
            },

        	/**
        	 * 获取字段，将驼峰转下划线
        	 */
            getField: function(str){
        		var reg = new RegExp(/(\S[A-Z])/g);
        		var match = str.match(reg);
        		if(match){
        			for(var i = 0; i < match.length; i++){
        				var index = str.indexOf(match[i]) + 1;
        				str = str.substr(0, index) + '_' + str.substr(index, 1).toLowerCase() + str.substr(index + 1); 
        			}
        		}
        		return str.match(reg) ? this.getField(str):str;
        	}
			/*
			 * 
			* 网格自定义格式化单击事件
			* options
			* {
			* 	pageJsObject:页面js对象ID
			* 	formatter:'自定义格式化函数字符串(在页面js对象里面的函数名)',
			*   click:'自定义单击函数(在页面js对象里面的函数名)'
			* }
			*
			gridClickFormatter:function (options) {
				//console.log(options);
				var pageJsObj = options.pageJsObj;
				var gridId = options.gridId;
				var click = options.click;
                var csFomatter = options.pageJsObj[options.formatter];
                //获取用户自定义格式化函数
                if(!$.isFunction(csFomatter)){
                	csFomatter = function (val,rowData,i) {
						return val;
                    }
				}
				var formatter = function(val,row,i) {
                	var csVal = csFomatter(val,row,i);
                    var $html = $('<span class="grid-format-col">').html(csVal);
                    $html.attr('data-pageJsObj',);
                    return $html[0].outerHTML;
                }
				return formatter;
            }
			 */
		});

		// ie8下不支持 indexOf
		if (!Array.prototype.indexOf){
			Array.prototype.indexOf = function(elt){
			    var len = this.length >>> 0;
			    var from = Number(arguments[1]) || 0;
			    from = (from < 0) ? Math.ceil(from) : Math.floor(from);
			    if (from < 0){
			    	from += len;
			    }
			    
			    for (; from < len; from++){
			    	if (from in this && this[from] === elt)
				        return from;
			    }
			    return -1;
			}
		}
		
		// 数组深拷贝
		if(!Array.prototype.clone){
			Array.prototype.clone = function(){
				var arr = [];
				var len = this.length >>> 0;
				for(var i = 0; i < len; i++){
					var type = $.type(this[i]);
					if(type === 'array'){
						// 如果数组元素还是数组需进行递归
						arr.push(this[i].clone());
					} else if(type === 'object' || type === 'date'){
						// 如果元素是对象或日期类型需进行对象深克隆
						arr.push($.extend({}, this[i]));
					} else{
						// 其他数据类型默认直接添加元素
						arr.push(this[i]);
					}
				}
				return arr;
			}
		}

		//sumbitAllComp 扩展
        jq.fn.extend({
            sumbitAllCompExt: function (options) {
                var data = this.getSumbitData(options);
                if (data===false){
                	return;
				}
                //附加数据
                if (options && options.attachData) {
                    var attachData = options.attachData;
                    var transor = data.data.__transor;
                    if ($.type(transor) === 'string') {
                        transor = $.parseJSON(transor);
                    } else {
                        transor = {};
                    }
                    for (var item in attachData) {
                        var obj = attachData[item];
                        if ($.isArray(obj)) {
                            transor[item] = '[]';
                            data.data[item] = JSON.stringify(obj);
                        } else if ($.type(obj) === 'object') {
                            for (var field in obj) {
                                data[field] = obj[field];
                            }
                        }
                    }
                    data.data.__transor = JSON.stringify(transor);
                }
				this.sumbitData(options, data);
            }
        });

    }
    return Tool;
});

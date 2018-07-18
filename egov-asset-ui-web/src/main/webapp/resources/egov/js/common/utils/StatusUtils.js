define([],function(){
	var ACTION = {
			"ADD":"新建",
			"VIEW":"查看",
			"EDIT":"修改",
			"AUDIT":"审核"
	};
	return {
		/**
			资产状态
			0	正常
			
			10	新增中
			11	变动中
			12	拆分中
			13	作废中
			
			20	使用中
			21	领用中
			22	维修中
			23	出租中
			24	出借中
			25	对外投资中
			
			30	回收中
			31	交回中
			
			33	出租回收中
			34	出借回收中
			35	对外投资回收中
			
			
			70	处置中
			71	报废中
			72	报损中
			73	无偿调拨中
			74	对外捐赠中
			75	置换中
			76	出售中、出让中
			77      转让中
			
			80	已处置
			81	已报废
			82	已报损
			83	已无偿调拨
			84	已对外捐赠
			85	已置换
			86	已出售、已出让
			87      已转让
			88 调账
			89  盘亏
			
			98	已拆分
			99	已作废
		 */
		transferAssetStatusColumn:function(assetStatus){
			if(assetStatus == undefined){
				return '';
			}
			if(assetStatus == '0'){
				return "正常";
			} else if(assetStatus == '10'){
				return "新增中";
			} else if(assetStatus == '11'){
				return "变动中";
			} else if(assetStatus == '12'){
				return "拆分中";
			} else if(assetStatus == '13'){
				return "作废中";
			} else if(assetStatus == '20'){
				return "使用中";
			} else if(assetStatus == '21'){
				return "领用中";
			} else if(assetStatus == '22'){
				return "维修中";
			} else if(assetStatus == '23'){
				return "出租中";
			} else if(assetStatus == '24'){
				return "出借中";
			} else if(assetStatus == '25'){
				return "对外投资中";
			} else if(assetStatus == '30'){
				return "回收中";
			} else if(assetStatus == '31'){
				return "交回中";
			} else if(assetStatus == '33'){
				return "出租回收中";
			} else if(assetStatus == '34'){
				return "出借回收中";
			} else if(assetStatus == '35'){
				return "对外投资回收中";
			} else if(assetStatus == '70'){
				return "处置中";
			} else if(assetStatus == '71'){
				return "报废中";
			} else if(assetStatus == '72'){
				return "报损中";
			} else if(assetStatus == '73'){
				return "无偿调拨中";
			} else if(assetStatus == '74'){
				return "对外捐赠中";
			} else if(assetStatus == '75'){
				return "置换中";
			} else if(assetStatus == '76'){
				return "出售中、出让中";
			} else if(assetStatus == '77'){
				return "转让中";
			} else if(assetStatus == '80'){
				return "已处置";
			} else if(assetStatus == '81'){
				return "已报废";
			} else if(assetStatus == '82'){
				return "已报损";
			} else if(assetStatus == '83'){
				return "已无偿调拨";
			} else if(assetStatus == '84'){
				return "已对外捐赠";
			} else if(assetStatus == '85'){
				return "已置换";
			} else if(assetStatus == '86'){
				return "已出售、已出让";
			} else if(assetStatus == '87'){
				return "已转让";
			} else if(assetStatus == '88'){
				return "调账";
			} else if(assetStatus == '89'){
				return "盘亏";
			} else if(assetStatus == '97'){
				return "已删除";
			} else if(assetStatus == '98'){
				return "已拆分";
			} else if(assetStatus == '99'){
				return "已作废";
			}      			
		},
		/**
		 * 盘点结果 01 无盈亏 02 盘盈 03 盘亏
		 */
		transferInventoryStatusColumn:function(inventoryStatus){
			if(inventoryStatus == undefined){
				return '';
			}
			if(inventoryStatus == '01'){
				return "无盈亏";
			} else if(inventoryStatus == '02'){
				return "盘盈";
			} else if(inventoryStatus == '03'){
				return "盘亏";
			}
		},
		//是否可以进行修改操作
		isCanEdit:function(bizStatus){
			if(bizStatus > 10 && bizStatus != 9){
				return false;
			}
			return true;
		},
		//是否可以进行删除
		isCanDel:function(bizStatus){
			if(bizStatus > 10 || bizStatus == 9){
				return false;
			}
			return true;
		},
		//0-8 10 新建 9 及 大于10 审核中 999999 卡片入账
		formatterCardBizStatus:function(bizStatus){
			if(bizStatus == null){
				return '';
			}
			if((bizStatus >=0 && bizStatus <=8) || bizStatus == 10){
				if(bizStatus == 7){
					return "已驳回";
				} else {
					
					return "暂存";
				}
			}
			if(bizStatus == 9){
				return "已退回";
			}
			if((bizStatus > 10 && bizStatus <999999)){	
				if(bizStatus == 51){
					 return "已删除";
				} else {
				   return "审核中";
				}
			}
			if(bizStatus == 999999){
				return "已入账";
			}
		},
		formatterBizStatus:function(bizStatus){
			if(bizStatus == null){
				return '';
			}
			if((bizStatus >=0 && bizStatus <=8) || bizStatus == 10){
				if(bizStatus == 7){
					return "已驳回";
				} else {
					
					return "暂存";
				}
			}
			if(bizStatus == 9){
				return "已退回";
			}
			 
			if((bizStatus > 10 && bizStatus <999999)){				
				if(bizStatus == 51){
					 return "已删除";
				} else {
				   return "审核中";
				}
			}
			if(bizStatus == 999999){
				return "办结";
			}
		},
		formatterProjectBizStatus:function(bizStatus){
			if(bizStatus == null){
				return '';
			}
			if((bizStatus >=0 && bizStatus <=8) || bizStatus == 10){
				if(bizStatus == 7){
					return "已驳回";
				} else {
					
					return "暂存";
				}
			}
			if(bizStatus == 9){
				return "已退回";
			}
			
			if((bizStatus > 10 && bizStatus <999999)){
				return "审核中";
			}
			if(bizStatus == 999999){
				return "已备案";
			}
		},
		formatterProjectBizStatus2:function(bizStatus){
			if(bizStatus == null){
				return '';
			}
			if((bizStatus >=0 && bizStatus <=8) || bizStatus == 10){
				if(bizStatus == 7){
					return "已驳回";
				} else {
					
					return "暂存";
				}
			}
			if(bizStatus == 9){
				return "退回";
			}
			
			if((bizStatus > 10 && bizStatus <999999)){
				return "审核中";
			}
			if(bizStatus == 999999){
				return "完成备案";
			}
		},
		projectUseStatus2:function(useStatus){
			if(useStatus == undefined || useStatus == null){
				return '';
			}
			if(useStatus == '-1'){
				return '新增中';
			} else if(useStatus == '0'){
				return '项目备案';
			} else if(useStatus == '1'){
				return '临时使用';
			} else if(useStatus == '2'){
				return '项目移交';
			} else if(useStatus == '3'){
				return '项目取消';
			} else if(useStatus == '9'){
				return '已转资产';
			} else if(useStatus == '11'){
				return '临时使用中';
			} else if(useStatus == '21'){
				return '项目移交中';
			} else if(useStatus == '31'){
				return '项目取消中';
			} else if(useStatus == '41'){
				return '项目细化中';
			} else if(useStatus == '71'){
				return '账务处理中';
			} else if(useStatus == '7'){
				return '账务处理';
			}
		},
		projectUseStatus:function(useStatus){
			if(useStatus == undefined || useStatus == null){
				return '';
			}
			if(useStatus == '-1'){
				return '新增中';
			} else if(useStatus == '0'){
				return '项目备案';
			} else if(useStatus == '1'){
				return '临时使用';
			} else if(useStatus == '2'){
				return '项目移交';
			} else if(useStatus == '3'){
				return '项目取消';
			} else if(useStatus == '9'){
				return '已转资产';
			} else if(useStatus == '11'){
				return '临时使用中';
			} else if(useStatus == '21'){
				return '移交中';
			} else if(useStatus == '31'){
				return '取消中';
			} else if(useStatus == '41'){
				return '细化中';
			} else if(useStatus == '71'){
				return '账务处理中';
			} else if(useStatus == '7'){
				return '账务处理';
			}
		},
		//跳窗dlg
		showDlgTitle:function(action,bizTypeName){
			if(bizTypeName == null || typeof bizTypeName == 'undefined'){
				bizTypeName = "";
			}
//			var actionName = ACTION[action];
			var actionName = "";
			if(actionName != null || typeof actionName != 'undefined'){				
				return actionName + bizTypeName;
			}
			return bizTypeName;
		},
		formatterOrg:function(val,rowData){
			if($._toString(rowData.financeBudgetCode) != '')
				return $._toString(rowData.financeBudgetCode) + $.stringJoinChar() + $._toString(rowData.orgName);
			else 
				return rowData.orgName; 
		},
		formatterNextStep:function(val,rowData){
			//显示下一环节可以做什么
//			if(rowData.orgId != rowData.agenMgrId){
//				//可临时使用 及移交
//				return "临时使用/项目移交/项目取消";
//			} else {							
//				return "账务处理/项目取消";
//			}
            var info = "";
			switch(rowData.prjUseStatus){
			  case 0:
				   if(rowData.prjFlag == '1'){
					   info = "项目细化"
				   } else {
					   if(rowData.agenMgrId == null || typeof rowData.agenMgrId === 'undefined'){
						   return "临时使用/项目移交"; 
					   } else if(rowData.orgId != rowData.agenMgrId){
						   return "项目移交"; 
					   } else {
						   return "账务处理";
					   }
				   }
				   break;
			  case 1:
			  case 11:
				   //临时使用
				   info = "项目移交";
				   break;
			  case 7:
			  case 71:
				   info = "生成卡片"
				    break;
			  case 41:
				    break;
			}
			
			return info;
		}
	}
});
define(["jquery","app/widgets/app-workspace"],function($,WorkSPace){
	
	var learunindex = {
			
			initEvent:function(){
				
				
			},
	        load: function () {
	       
	        	 $("body").removeClass("hold-transition");
	        	 
	        	 if (top != window) {
	        		 $("body").addClass("nesting-frame");
	        		 
	        	 }
	        	   $(".sidebar-toggle").click(function () {
		                 if (!$("body").hasClass("sidebar-collapse")) {
		                     $("body").addClass("sidebar-collapse");
		                 	
		                  
		                 } else {
		                     $("body").removeClass("sidebar-collapse");
		                 }
		                 $(window).resize()
		             })
	        
	          
	           
	        },
	        jsonWhere: function (data, action) {
	            if (action == null) return;
	            var reval = new Array();
	            $(data).each(function (i, v) {
	                if (action(v)) {
	                    reval.push(v);
	                }
	            })
	            return reval;
	        },
	        loadMenu: function () {
	        	var data=[];
	           // var data = [
	                      //  { "menuId": "1", "parentMenuId": "0", "menuCode": "SysManage", "menuName": "系统管理", "menuIcon": "fa fa-desktop", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2015-11-17 11:22:46", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "8", "parentMenuId": "2", "menuCode": "OrganizeManage", "menuName": "机构管理", "menuIcon": "fa fa-sitemap", "menuUrl": "BaseManage/Organize/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-29 11:55:28", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "7ae94059-9aa5-48eb-8330-4e2a6565b193", "parentMenuId": "1", "menuCode": "AreaManage", "menuName": "应用管理2", "menuIcon": "fa fa-leaf", "menuUrl": "platform/appframe/app/afaapplication/showIndex.do", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "行政区域管理", "F_CreateDate": "2015-11-12 14:38:20", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:05:33", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "34e362f4-c220-4fb7-b3f0-288c83417cb3", "parentMenuId": "7cec0a0f-7204-4240-b009-312fa0c11cbf", "menuCode": "DataBaseLink", "menuName": "数据库连接", "menuIcon": "fa fa-plug", "menuUrl": "SystemManage/DataBaseLink/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "动态链接数据库", "F_CreateDate": "2015-11-24 09:50:22", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:07:45", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "1b642904-d674-495f-a1e1-4814cc543870", "parentMenuId": "5", "menuCode": "发起流程", "menuName": "发起流程", "menuIcon": "fa fa-edit", "menuUrl": "FlowManage/FlowLaunch/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 22:12:27", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-01-12 17:39:01", "F_ModifyUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_ModifyUserName": "佘赐雄" }, { "menuId": "a977d91e-77b7-4d60-a7ad-dfbc138f7c0a", "parentMenuId": "b9f9df92-8ac5-46e2-90ac-68c5c2e034c3", "menuCode": "企业号设置", "menuName": "企业号设置", "menuIcon": "fa fa-plug", "menuUrl": "WeChatManage/Token/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-12-22 17:20:21", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2015-12-29 19:05:02", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "00ae31cf-b340-4c17-9ee7-6dd08943df02", "parentMenuId": "458113c6-b0be-4d6f-acce-7524f4bc3e88", "menuCode": "FormCategory", "menuName": "表单类别", "menuIcon": "fa fa-tags", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=FormSort", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-27 10:30:47", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2015-12-01 09:42:16", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClientBaseData", "menuName": "基础设置", "menuIcon": "fa fa fa-book", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 1, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "客户基础资料", "F_CreateDate": "2016-03-11 11:51:34", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-03-29 09:41:15", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "92a535c9-4d4b-4500-968d-a142e671c09b", "parentMenuId": "6", "menuCode": "ReportTemp", "menuName": "报表管理", "menuIcon": "fa fa-cogs", "menuUrl": "ReportManage/Report/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 1, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "报表模板管理", "F_CreateDate": "2016-01-13 17:21:17", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:14:56", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "e35d24ce-8a6a-46b9-8b3f-6dc864a8f342", "parentMenuId": "4", "menuCode": "NewManage", "menuName": "新闻中心", "menuIcon": "fa fa-feed", "menuUrl": "PublicInfoManage/News/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-27 09:47:16", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:17:09", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "3b03806d-98d8-40fe-9895-01633119458c", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_ProductInfo", "menuName": "产品信息", "menuIcon": "fa fa-shopping-bag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_ProductInfo", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 1, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "销售产品信息", "F_CreateDate": "2016-03-11 16:42:57", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-03-23 16:36:07", "F_ModifyUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_ModifyUserName": "佘赐雄" }, { "menuId": "27b6c487-a2d9-4a3a-a40d-dbba27a53d26", "parentMenuId": "b5cb98f6-fb41-4a0f-bc11-469ff117a411", "menuCode": "FlowMonitor", "menuName": "流程监控", "menuIcon": "fa fa-eye", "menuUrl": "FlowManage/FlowProcess/MonitoringIndex", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 21:58:17", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-26 12:06:13", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "6252983c-52f5-402c-991b-ad19a9cb1f94", "parentMenuId": "4", "menuCode": "NoticeManage", "menuName": "通知公告", "menuIcon": "fa fa-volume-up", "menuUrl": "PublicInfoManage/Notice/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-27 09:47:33", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:17:39", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "66f6301c-1789-4525-a7d2-2b83272aafa6", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClientChance", "menuName": "商机管理", "menuIcon": "fa fa-binoculars", "menuUrl": "CustomerManage/Chance/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "商机管理", "F_CreateDate": "2016-03-11 11:55:16", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-29 14:19:13", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "affacee1-41a3-4c7b-8804-f1c1926babbd", "parentMenuId": "6", "menuCode": "PurchaseReport", "menuName": "采购报表", "menuIcon": "fa fa-bar-chart", "menuUrl": "ReportManage/ReportDemo/Purchase", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-01-04 16:29:07", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-29 14:15:19", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "6be31cc9-4aee-4279-8435-4b266cec33f0", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_Trade", "menuName": "客户行业", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_Trade", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-11 16:45:14", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-03-23 16:36:23", "F_ModifyUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_ModifyUserName": "佘赐雄" }, { "menuId": "5cc9d2d9-e097-4b51-9b9e-84ca9f1a0ab5", "parentMenuId": "b9f9df92-8ac5-46e2-90ac-68c5c2e034c3", "menuCode": "企业号部门", "menuName": "企业号部门", "menuIcon": "fa fa-sitemap", "menuUrl": "WeChatManage/Organize/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-12-22 17:20:38", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:10:46", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "08dfd779-92d5-4cd8-9982-a76176af0f7c", "parentMenuId": "458113c6-b0be-4d6f-acce-7524f4bc3e88", "menuCode": "FlowCategory", "menuName": "流程类别", "menuIcon": "fa fa-tags", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=FlowSort", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 14:42:18", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2015-11-27 10:41:42", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "c4d7ce1f-72de-4651-b495-6c466261e9af", "parentMenuId": "7cec0a0f-7204-4240-b009-312fa0c11cbf", "menuCode": "DataBaseBackup", "menuName": "数据库备份", "menuIcon": "fa fa-cloud-download", "menuUrl": "SystemManage/DataBaseBackup/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "数据备份、数据还原", "F_CreateDate": "2015-11-24 09:55:52", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:08:22", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "691f3810-a602-4523-8518-ce5856482d48", "parentMenuId": "5", "menuCode": "草稿流程", "menuName": "草稿流程", "menuIcon": "fa fa-file-text-o", "menuUrl": "FlowManage/FlowRoughdraft/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 22:13:21", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-03-19 16:15:15", "F_ModifyUserId": "24a055d6-5924-44c5-be52-3715cdd68011", "F_ModifyUserName": "陈彬彬" }, { "menuId": "9", "parentMenuId": "2", "menuCode": "DepartmentManage", "menuName": "部门管理", "menuIcon": "fa fa-th-list", "menuUrl": "BaseManage/Department/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-29 11:57:20", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "2", "parentMenuId": "0", "menuCode": "BaseManage", "menuName": "单位组织", "menuIcon": "fa fa-coffee", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-03-11 11:02:06", "F_ModifyUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_ModifyUserName": "佘赐雄" }, { "menuId": "4efd37bf-e3ef-4ced-8248-58eba046d78b", "parentMenuId": "1", "menuCode": "DataItemManage", "menuName": "通用字典", "menuIcon": "fa fa-book", "menuUrl": "SystemManage/DataItemDetail/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 2, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "通用数据字典", "F_CreateDate": "2015-11-12 14:37:04", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:06:26", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "5", "parentMenuId": "0", "menuCode": "FlowManage", "menuName": "工作流程", "menuIcon": "fa fa-share-alt", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-11 10:21:44", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "7adc5a16-54a4-408e-a101-2ddab8117d67", "parentMenuId": "1", "menuCode": "CodeRule", "menuName": "单据编码", "menuIcon": "fa fa-barcode", "menuUrl": "SystemManage/CodeRule/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "自动产生号码", "F_CreateDate": "2015-11-12 14:47:51", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-05-03 15:56:56", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "11", "parentMenuId": "2", "menuCode": "RoleManage", "menuName": "角色管理", "menuIcon": "fa fa-paw", "menuUrl": "BaseManage/Role/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-05-23 18:12:29", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "a57993fa-5a94-44a8-a330-89196515c1d9", "parentMenuId": "458113c6-b0be-4d6f-acce-7524f4bc3e88", "menuCode": "FormDesign", "menuName": "表单设计", "menuIcon": "fa fa-puzzle-piece", "menuUrl": "FlowManage/FormDesign/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-27 10:29:53", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2015-12-01 09:41:58", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "4d0f2e44-f68f-41fd-a55c-40ac67453ef4", "parentMenuId": "b9f9df92-8ac5-46e2-90ac-68c5c2e034c3", "menuCode": "企业号成员", "menuName": "企业号成员", "menuIcon": "fa fa-users", "menuUrl": "WeChatManage/User/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-12-22 17:20:53", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:11:24", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "c30310a7-d0a5-4bf6-8655-c3834a8cc73d", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_Sort", "menuName": "客户类别", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_Sort", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-11 16:47:39", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-03-23 16:36:33", "F_ModifyUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_ModifyUserName": "佘赐雄" }, { "menuId": "ff1823b5-a966-4e6c-83de-807854f4f0fb", "parentMenuId": "6", "menuCode": "SalesReport", "menuName": "销售报表", "menuIcon": "fa fa-line-chart", "menuUrl": "ReportManage/ReportDemo/Sales", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-01-04 16:29:46", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-29 14:15:34", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "021a59b0-2589-4f9e-8140-6052177a967c", "parentMenuId": "5", "menuCode": "我的流程", "menuName": "我的流程", "menuIcon": "fa fa-file-word-o", "menuUrl": "FlowManage/FlowMyProcess/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-19 13:32:54", "F_CreateUserId": "24a055d6-5924-44c5-be52-3715cdd68011", "F_CreateUserName": "陈彬彬", "F_ModifyDate": "2016-03-22 20:02:21", "F_ModifyUserId": "24a055d6-5924-44c5-be52-3715cdd68011", "F_ModifyUserName": "陈彬彬" }, { "menuId": "1d3797f6-5cd2-41bc-b769-27f2513d61a9", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClientInfoManage", "menuName": "客户管理", "menuIcon": "fa fa-suitcase", "menuUrl": "CustomerManage/Customer/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "客户管理", "F_CreateDate": "2016-03-11 11:57:48", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-29 14:19:05", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "04b88c96-8d99-45ec-956c-444efa630020", "parentMenuId": "4", "menuCode": "ResourceFileManage", "menuName": "文件资料", "menuIcon": "fa fa-jsfiddle", "menuUrl": "PublicInfoManage/ResourceFile/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "文件管理", "F_CreateDate": "2015-11-27 09:47:48", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-21 15:06:21", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "2f820f6e-ae2e-472f-82cc-0129a2a57597", "parentMenuId": "7cec0a0f-7204-4240-b009-312fa0c11cbf", "menuCode": "DataBaseTable", "menuName": "数据表管理", "menuIcon": "fa fa-table", "menuUrl": "SystemManage/DataBaseTable/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "数据库表结构", "F_CreateDate": "2015-11-24 09:53:42", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:08:55", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "c0544bfd-a557-45fc-a856-a678a1e88bfc", "parentMenuId": "b5cb98f6-fb41-4a0f-bc11-469ff117a411", "menuCode": "FlowDelegate", "menuName": "流程指派", "menuIcon": "fa fa-random", "menuUrl": "FlowManage/FlowProcess/DesignationIndex", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 3, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 21:58:36", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-26 12:06:40", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "901e6122-985d-4c84-8d8c-56560520f6ed", "parentMenuId": "6", "menuCode": "StorageReport", "menuName": "仓存报表", "menuIcon": "fa fa-area-chart", "menuUrl": "ReportManage/ReportDemo/Store", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 4, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-01-04 16:30:25", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-29 14:15:52", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "aed02ee7-322f-47f0-8ad6-ab0a2172628f", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_Degree", "menuName": "客户程度", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_Degree", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 4, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-11 16:49:46", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-06 10:23:36", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "5f1fa264-cc9b-4146-b49e-743e4633bb4c", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClientInvoice", "menuName": "客户开票", "menuIcon": "fa fa-coffee", "menuUrl": "CustomerManage/Invoice/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 4, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "开票管理", "F_CreateDate": "2016-04-01 10:40:18", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:20:23", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "aa844d70-7211-41d9-907a-f9a10f4ac801", "parentMenuId": "b9f9df92-8ac5-46e2-90ac-68c5c2e034c3", "menuCode": "企业号应用", "menuName": "企业号应用", "menuIcon": "fa fa-safari", "menuUrl": "WeChatManage/App/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 4, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-12-22 17:21:25", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2015-12-25 10:34:44", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "f63a252b-975f-4832-a5be-1ce733bc8ece", "parentMenuId": "458113c6-b0be-4d6f-acce-7524f4bc3e88", "menuCode": "FlowDesign", "menuName": "流程设计", "menuIcon": "fa fa-share-alt", "menuUrl": "FlowManage/FlowDesign/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 4, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 14:42:43", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2015-11-27 10:41:09", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "0d296398-bc0e-4f38-996a-6e24bc88cc53", "parentMenuId": "5", "menuCode": "待办流程", "menuName": "待办流程", "menuIcon": "fa fa-hourglass-half", "menuUrl": "FlowManage/FlowBeforeProcessing/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 4, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 22:13:39", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-03-23 18:07:42", "F_ModifyUserId": "24a055d6-5924-44c5-be52-3715cdd68011", "F_ModifyUserName": "陈彬彬" }, { "menuId": "7cec0a0f-7204-4240-b009-312fa0c11cbf", "parentMenuId": "1", "menuCode": "DatabaseManage", "menuName": "数据管理", "menuIcon": "fa fa-database", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 4, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-12 15:03:09", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-03-11 12:10:01", "F_ModifyUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_ModifyUserName": "佘赐雄" }, { "menuId": "6", "parentMenuId": "0", "menuCode": "ReportManage", "menuName": "报表中心", "menuIcon": "fa fa-area-chart", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 4, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-11 10:21:54", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "4", "parentMenuId": "0", "menuCode": "CommonInfo", "menuName": "公共信息", "menuIcon": "fa fa-globe", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 5, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-11 10:21:59", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "13", "parentMenuId": "2", "menuCode": "PostManage", "menuName": "岗位管理", "menuIcon": "fa fa-graduation-cap", "menuUrl": "BaseManage/Post/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 5, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-29 11:59:17", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "923f7d65-e307-45f7-8f96-73ecbf23b324", "parentMenuId": "5", "menuCode": "已办流程", "menuName": "已办流程", "menuIcon": "fa fa-flag", "menuUrl": "FlowManage/FlowAferProcessing/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 5, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 22:14:03", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-03-25 11:39:51", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "52fe82f8-41ba-433e-9351-ef67e5b35217", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_Level", "menuName": "客户级别", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_Level", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 5, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-11 16:52:08", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-06 10:23:29", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "b352f049-4331-4b19-ac22-e379cb30bd55", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClientOrder", "menuName": "客户订单", "menuIcon": "fa fa-modx", "menuUrl": "CustomerManage/Order/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 5, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "客户订单管理", "F_CreateDate": "2016-03-11 12:01:30", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-29 14:20:16", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "b9f9df92-8ac5-46e2-90ac-68c5c2e034c3", "parentMenuId": "1", "menuCode": "WeChatManage", "menuName": "微信管理", "menuIcon": "fa fa-weixin", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 5, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-12-22 16:42:12", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2015-12-22 18:20:30", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "9db71a92-2ecb-496c-839f-7a82bc22905d", "parentMenuId": "6", "menuCode": "MoneyReport", "menuName": "对账报表", "menuIcon": "fa fa-pie-chart", "menuUrl": "ReportManage/ReportDemo/Reconciliation", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 5, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "现金银行报表", "F_CreateDate": "2016-01-04 16:31:03", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-29 14:16:09", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "c6b80ed5-b0cb-4844-ba1a-725d2cb4f935", "parentMenuId": "4", "menuCode": "EmailManage", "menuName": "邮件中心", "menuIcon": "fa fa-send", "menuUrl": "PublicInfoManage/Email/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 6, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "邮件管理", "F_CreateDate": "2015-11-27 09:48:38", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-21 15:06:31", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "23713d3a-810f-422d-acd5-39bec28ce47e", "parentMenuId": "4", "menuCode": "ScheduleManage", "menuName": "日程管理", "menuIcon": "fa fa-calendar", "menuUrl": "PublicInfoManage/Schedule/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 6, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "日程管理", "F_CreateDate": "2016-04-21 14:15:30", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-21 16:08:46", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "parentMenuId": "0", "menuCode": "CustomerManage", "menuName": "客户关系", "menuIcon": "fa fa-briefcase", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 6, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "客户关系管理", "F_CreateDate": "2016-03-11 10:53:05", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-21 16:00:07", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "be9cbe61-265f-4ddd-851e-d5a1cef6011b", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_ChanceSource", "menuName": "商机来源", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_ChanceSource", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 6, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-12 11:01:38", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-03-23 16:36:58", "F_ModifyUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_ModifyUserName": "佘赐雄" }, { "menuId": "535d92e9-e066-406c-b2c2-697150a5bdff", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClienReceivable", "menuName": "收款管理", "menuIcon": "fa fa-money", "menuUrl": "CustomerManage/Receivable/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 6, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "收款管理", "F_CreateDate": "2016-04-06 16:04:16", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:20:56", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "6a67a67f-ef07-41e7-baa5-00bc5f662a76", "parentMenuId": "5", "menuCode": "工作委托", "menuName": "工作委托", "menuIcon": "fa fa-coffee", "menuUrl": "FlowManage/FlowDelegate/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 6, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 22:14:20", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-03-28 17:34:24", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "12", "parentMenuId": "2", "menuCode": "JobManage", "menuName": "职位管理", "menuIcon": "fa fa-briefcase", "menuUrl": "BaseManage/Job/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 6, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-29 12:00:32", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "f21fa3a0-c523-4d02-99ca-fd8dd3ae3d59", "parentMenuId": "1", "menuCode": "SystemLog", "menuName": "系统日志", "menuIcon": "fa fa-warning", "menuUrl": "SystemManage/Log/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 6, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "登录日志、操作日志。异常日志", "F_CreateDate": "2015-11-12 15:04:58", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:12:14", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "14", "parentMenuId": "2", "menuCode": "UserGroupManage", "menuName": "用户组管理", "menuIcon": "fa fa-group", "menuUrl": "BaseManage/UserGroup/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 7, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-29 12:01:17", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "21", "parentMenuId": "1", "menuCode": "SystemModule", "menuName": "系统功能", "menuIcon": "fa fa-navicon", "menuUrl": "AuthorizeManage/Module/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 7, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "系统导航功能", "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-29 14:13:00", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "1ef31fba-7f0a-46f7-b533-49dd0c2e51e0", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClienReceivableReport", "menuName": "收款报表", "menuIcon": "fa fa-bar-chart", "menuUrl": "CustomerManage/ReceivableReport/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 7, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "收款报表", "F_CreateDate": "2016-04-20 09:41:51", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:21:24", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "80620d6f-55bd-492b-9c21-1b04ca268e75", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_ChancePhase", "menuName": "商机阶段", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_ChancePhase", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 7, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-12 11:02:09", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-03-23 16:37:06", "F_ModifyUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_ModifyUserName": "佘赐雄" }, { "menuId": "458113c6-b0be-4d6f-acce-7524f4bc3e88", "parentMenuId": "5", "menuCode": "流程配置", "menuName": "流程配置", "menuIcon": "fa fa-wrench", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 7, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-27 10:39:01", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-03-19 13:34:52", "F_ModifyUserId": "24a055d6-5924-44c5-be52-3715cdd68011", "F_ModifyUserName": "陈彬彬" }, { "menuId": "b0261df5-7be0-4c8e-829c-15836e200af0", "parentMenuId": "1", "menuCode": "SystemForm", "menuName": "系统表单", "menuIcon": "fa fa-paw", "menuUrl": "AuthorizeManage/ModuleForm/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 8, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "系统功能自定义表单", "F_CreateDate": "2016-04-11 11:19:06", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:14:02", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "9fc384f5-efb7-439e-9fe1-3e50807e6399", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClienExpenses", "menuName": "支出管理", "menuIcon": "fa fa-credit-card-alt", "menuUrl": "CustomerManage/Expenses/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 8, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "支出管理", "F_CreateDate": "2016-04-20 11:31:56", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-29 14:21:50", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "77f13de5-32ad-4226-9e24-f1db507e78cb", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_PaymentMode", "menuName": "收支方式", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_PaymentMode", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 8, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-14 19:49:53", "F_CreateUserId": "0f36148c-719f-41e0-8c8c-16ffbc40d0e0", "F_CreateUserName": "佘赐雄", "F_ModifyDate": "2016-04-20 09:55:52", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "b5cb98f6-fb41-4a0f-bc11-469ff117a411", "parentMenuId": "5", "menuCode": "FlowManage", "menuName": "流程管理", "menuIcon": "fa fa-cogs", "menuUrl": null, "F_Target": "expand", "isLeaf": 0, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 8, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2015-11-23 10:20:00", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-03-19 13:33:50", "F_ModifyUserId": "24a055d6-5924-44c5-be52-3715cdd68011", "F_ModifyUserName": "陈彬彬" }, { "menuId": "cfa631fe-e7f8-42b5-911f-7172f178a811", "parentMenuId": "1", "menuCode": "CodeCreate", "menuName": "快速开发", "menuIcon": "fa fa-code", "menuUrl": "GeneratorManage/Template/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 8, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "自动生成代码、自动生成功能", "F_CreateDate": "2015-11-12 15:21:38", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-12 10:52:30", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "10", "parentMenuId": "2", "menuCode": "UserManage", "menuName": "用户管理", "menuIcon": "fa fa-user", "menuUrl": "BaseManage/User/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 8, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": null, "F_CreateUserId": null, "F_CreateUserName": null, "F_ModifyDate": "2016-04-29 11:51:54", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "a653a69e-5dcc-4ece-b457-fc0875271a88", "parentMenuId": "1", "menuCode": "AppCreate", "menuName": "移动开发", "menuIcon": "fa fa-file-code-o", "menuUrl": "AppManage/AppProjects/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 9, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "手机移动端开发", "F_CreateDate": "2016-06-14 15:57:59", "F_CreateUserId": "24a055d6-5924-44c5-be52-3715cdd68011", "F_CreateUserName": "陈彬彬", "F_ModifyDate": "2016-06-15 16:27:42", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "dec79ca7-3b54-432a-be1e-c96e7a2c7150", "parentMenuId": "ad147f6d-613f-4d2d-8c84-b749d0754f3b", "menuCode": "ClienCashBalanceReport", "menuName": "现金报表", "menuIcon": "fa fa-bar-chart", "menuUrl": "CustomerManage/CashBalanceReport/Index", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 1, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 9, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": "收支报表", "F_CreateDate": "2016-04-28 15:12:16", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-05-27 16:29:15", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "ddce0dc1-3345-41b7-9716-22641fbbfaed", "parentMenuId": "6", "menuCode": "rpt001", "menuName": "销售日报表", "menuIcon": "fa fa-pie-chart", "menuUrl": "ReportManage/Report/ReportPreview?keyValue=a9762855-cd45-4815-a8e1-c8b818f79ad5", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 9, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-03-22 16:55:20", "F_CreateUserId": "eab01522-f4fe-48ce-8db6-76fd7813cdf5", "F_CreateUserName": "刘晓雷", "F_ModifyDate": "2016-03-29 16:53:54", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "62af0605-4558-47b1-9530-bc3515036b37", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_PaymentAccount", "menuName": "收支账户", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_PaymentAccount", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 9, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-04-20 09:54:48", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-20 09:55:13", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }, { "menuId": "eab4a37f-d976-42b7-9589-489ed0678151", "parentMenuId": "16d4e2d5-d154-455f-94f7-63bf80ab26aa", "menuCode": "Client_ExpensesType", "menuName": "支出种类", "menuIcon": "fa fa-tag", "menuUrl": "SystemManage/DataItemList/Index?ItemCode=Client_ExpensesType", "F_Target": "iframe", "isLeaf": 1, "F_AllowExpand": 0, "F_IsPublic": 0, "F_AllowEdit": null, "F_AllowDelete": null, "F_SortCode": 10, "F_DeleteMark": 0, "F_EnabledMark": 1, "F_Description": null, "F_CreateDate": "2016-04-20 15:06:10", "F_CreateUserId": "System", "F_CreateUserName": "超级管理员", "F_ModifyDate": "2016-04-20 15:06:46", "F_ModifyUserId": "System", "F_ModifyUserName": "超级管理员" }];
	          
	        	if (menuList){
	        		
	        		data=menuList;
	        	}
	        	var _html = "";
	            $.each(data, function (i) {
	                var row = data[i];
	                if (!row.parentMenuId ||row.parentMenuId == "0") {
	                    if (i == 0) {
	                        _html += '<li class="treeview active">';
	                    } else {
	                        _html += '<li class="treeview">';
	                    }
	                    _html += '<a href="#">'
	                    _html += '<i class="fa fa-plus-square-o ' + row.menuIcon + '"></i><span>' + row.menuName + '</span><i class="fa fa-angle-left pull-right"></i>'
	                    _html += '</a>'
	                    var childNodes = learunindex.jsonWhere(data, function (v) { return v.parentMenuId == row.menuId });
	                    if (childNodes.length > 0) {
	                        _html += '<ul class="treeview-menu">';
	                        $.each(childNodes, function (i) {
	                            var subrow = childNodes[i];
	                            var subchildNodes = learunindex.jsonWhere(data, function (v) { return v.parentMenuId == subrow.menuId });
	                            _html += '<li>';
	                            if (subchildNodes.length > 0) {
	                                _html += '<a href="#"><i class="fa ' + (subrow.menuIcon|| 'fa-desktop')+ '"></i>' + subrow.menuName + '';
	                                _html += '<i class="fa fa-angle-left pull-right"></i></a>';
	                                _html += '<ul class="treeview-menu">';
	                                $.each(subchildNodes, function (i) {
	                                    var subchildNodesrow = subchildNodes[i];
	                                    _html += '<li><a class="menuItem" data-id="' + subrow.menuId + '" href="' + subrow.menuUrl + '"><i class="' + subchildNodesrow.menuIcon + '"></i>' + subchildNodesrow.menuName + '</a></li>';
	                                });
	                                _html += '</ul>';

	                            } else {
	                                _html += '<a class="menuItem" data-id="' + subrow.menuId + '" href="' + subrow.menuUrl + '"><i class="fa ' + (subrow.menuIcon|| 'fa-desktop')+ '"></i>' + subrow.menuName + '</a>';
	                            }
	                            _html += '</li>';
	                        });
	                        _html += '</ul>';
	                    }
	                    _html += '</li>'
	                }
	            });
	            $("#sidebar-menu").append(_html);
	            $("#sidebar-menu li a").click(function () {
	                var d = $(this), e = d.next();
	                if (e.is(".treeview-menu") && e.is(":visible")) {
	                    e.slideUp(500, function () {
	                        e.removeClass("menu-open")
	                    }),
	                    e.parent("li").removeClass("active")
	                } else if (e.is(".treeview-menu") && !e.is(":visible")) {
	                    var f = d.parents("ul").first(),
	                    g = f.find("ul:visible").slideUp(500);
	                    g.removeClass("menu-open");
	                    var h = d.parent("li");
	                    e.slideDown(500, function () {
	                        e.addClass("menu-open"),
	                        f.find("li.active").removeClass("active"),
	                        h.addClass("active");

	                        var _height1 = $(window).height() - $("#sidebar-menu >li.active").position().top - 41;
	                        var _height2 = $("#sidebar-menu li > ul.menu-open").height() + 10
	                        if (_height2 > _height1) {
	                            $("#sidebar-menu >li > ul.menu-open").css({
	                                overflow: "auto",
	                                height: _height1
	                            })
	                        }
	                    })
	                }

					//二级菜单单击事件
					var $li = d.parent();
					if($li.is('li')&&$li.prop('class')===''){
						$('.menu-child-selected').removeClass('menu-child-selected');
						$li.addClass('menu-child-selected');
					}

	                e.is(".treeview-menu");
	            });
	        }
	    }
	
	
	var TabWorkSpace=WorkSPace.extend({
		initialize : function(el, options) {
			TabWorkSpace.superclass.initialize.call(this, options);
			
		},requestFullScreen: function () {
            var de = document.documentElement;
            if (de.requestFullscreen) {
                de.requestFullscreen();
            } else if (de.mozRequestFullScreen) {
                de.mozRequestFullScreen();
            } else if (de.webkitRequestFullScreen) {
                de.webkitRequestFullScreen();
            }
        },
        exitFullscreen: function () {
            var de = document;
            if (de.exitFullscreen) {
                de.exitFullscreen();
            } else if (de.mozCancelFullScreen) {
                de.mozCancelFullScreen();
            } else if (de.webkitCancelFullScreen) {
                de.webkitCancelFullScreen();
            }
        },
        refreshTab: function () {
            var currentId = $('.page-tabs-content').find('.active').attr('data-id');
            var target = $('.tab-page[data-id="' + currentId + '"]');
            var url = target.attr('src');
            //$.loading(true);
            target.attr('src', url).load(function () {
                //$.loading(false);
            });
        },
     
        activeTab: function () {
            var currentId = $(this).data('id');
            if (!$(this).hasClass('active')) {
                $('.main-content .tab-page').each(function () {
                    if ($(this).data('id') == currentId) {
                        $(this).show().siblings('.tab-page').hide();
                        return false;
                    }
                });
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                learuntab.scrollToTab(this);
            }
        },
        closeOtherTabs: function () {
            $('.page-tabs-content').children("[data-id]").find('.fa-remove').parents('a').not(".active").each(function () {
                $('.tab-page[data-id="' + $(this).data('id') + '"]').remove();
                $(this).remove();
            });
            $('.page-tabs-content').css("margin-left", "0");
        },
        closeTab: function () {
            var closeTabId = $(this).parents('.menuTab').data('id');
            var currentWidth = $(this).parents('.menuTab').width();
            if ($(this).parents('.menuTab').hasClass('active')) {
                if ($(this).parents('.menuTab').next('.menuTab').size()) {
                    var activeId = $(this).parents('.menuTab').next('.menuTab:eq(0)').data('id');
                    $(this).parents('.menuTab').next('.menuTab:eq(0)').addClass('active');

                    $('.main-content .tab-page').each(function () {
                        if ($(this).data('id') == activeId) {
                            $(this).show().siblings('.tab-page').hide();
                            return false;
                        }
                    });
                    var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
                    if (marginLeftVal < 0) {
                        $('.page-tabs-content').animate({
                            marginLeft: (marginLeftVal + currentWidth) + 'px'
                        }, "fast");
                    }
                    $(this).parents('.menuTab').remove();
                    $('.main-content .tab-page').each(function () {
                        if ($(this).data('id') == closeTabId) {
                            $(this).remove();
                            return false;
                        }
                    });
                }
                if ($(this).parents('.menuTab').prev('.menuTab').size()) {
                    var activeId = $(this).parents('.menuTab').prev('.menuTab:last').data('id');
                    $(this).parents('.menuTab').prev('.menuTab:last').addClass('active');
                    $('.main-content .tab-page').each(function () {
                        if ($(this).data('id') == activeId) {
                            $(this).show().siblings('.tab-page').hide();
                            return false;
                        }
                    });
                    $(this).parents('.menuTab').remove();
                    $('.main-content .tab-page').each(function () {
                        if ($(this).data('id') == closeTabId) {
                            $(this).remove();
                            return false;
                        }
                    });
                }
            }
            else {
                $(this).parents('.menuTab').remove();
                $('.main-content .tab-page').each(function () {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });
                learuntab.scrollToTab($('.menuTab.active'));
            }
            return false;
        },
        addPage: function (pageInfo) {
        	  var dataId,dataUrl ,menuName,isClose=true;
        	  $(".navbar-custom-menu>ul>li.open").removeClass("open");
        	if (pageInfo&&pageInfo.pageTitle){
        		dataId=pageInfo.pageId;
        		menuName=pageInfo.pageTitle;
        		dataUrl=pageInfo.pageUrl;
        		isClose=pageInfo.isClose
        		
        	}else{
	           
	            dataId=$(this).attr('data-id');
	            if (dataId != "") {
	                //top.$.cookie('nfine_currentmoduleid', dataId, { path: "/" });
	            }
	            dataUrl = $(this).attr('href');
	            menuName = $.trim($(this).text());
        	}
            var flag = true;
            if (dataUrl == undefined || $.trim(dataUrl).length == 0) {
                return false;
            }
            $('.menuTab').each(function () {
                if ($(this).data('id') == dataUrl) {
                    if (!$(this).hasClass('active')) {
                        $(this).addClass('active').siblings('.menuTab').removeClass('active');
                        learuntab.scrollToTab(this);
                        $('.main-content .tab-page').each(function () {
                            if ($(this).data('id') == dataUrl) {
                                $(this).show().siblings('.tab-page').hide();
                                return false;
                            }
                        });
                    }
                    flag = false;
                    return false;
                }
            });
            if (flag) {
                var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + (isClose?' <i class="fa fa-remove"></i>':'')+'</a>';
                $('.menuTab').removeClass('active');
                //var str1 = '<iframe class="LRADMS_iframe" id="iframe' + dataId + '" name="iframe' + dataId + '"  width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
               // $('.main-content').find('iframe.tab-page').hide();
                var $panel = $('<div class="tab-page" id="tabpage' + dataId + '" name="tabpage' + dataId + '"    data-id="' + dataUrl + '" seamless></div>');
                $('.main-content').find('div.tab-page').hide();
                $panel.appendTo($('.main-content'));
                
               // $('.main-content').append(str1);
                //$.loading(true);
               /* $('.main-content iframe:visible').load(function () {
                    //$.loading(false);
                });*/
                $('.menuTabs .page-tabs-content').append(str);
                learuntab.scrollToTab($('.menuTab.active'));
                $("#_tabmask").show();
            	$panel.htmlAJAX({
					type:"GET", url:dataUrl, callback:function(){
						//navTab._loadUrlCallback($panel);
						
					},error:function(){
						
					}
				});
            }
            return false;
        },
        scrollTabRight: function () {
            var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
            var tabOuterWidth = learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").width() < visibleWidth) {
                return false;
            } else {
                var tabElement = $(".menuTab:first");
                var offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                scrollVal = learuntab.calSumWidth($(tabElement).prevAll());
                if (scrollVal > 0) {
                    $('.page-tabs-content').animate({
                        marginLeft: 0 - scrollVal + 'px'
                    }, "fast");
                }
            }
        },
        scrollTabLeft: function () {
            var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
            var tabOuterWidth = learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").width() < visibleWidth) {
                return false;
            } else {
                var tabElement = $(".menuTab:first");
                var offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                offsetVal = 0;
                if (learuntab.calSumWidth($(tabElement).prevAll()) > visibleWidth) {
                    while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                        offsetVal += $(tabElement).outerWidth(true);
                        tabElement = $(tabElement).prev();
                    }
                    scrollVal = learuntab.calSumWidth($(tabElement).prevAll());
                }
            }
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        },
        scrollToTab: function (element) {
            var marginLeftVal = learuntab.calSumWidth($(element).prevAll()), marginRightVal = learuntab.calSumWidth($(element).nextAll());
            var tabOuterWidth = learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").outerWidth() < visibleWidth) {
                scrollVal = 0;
            } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
                if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                    scrollVal = marginLeftVal;
                    var tabElement = element;
                    while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                        scrollVal -= $(tabElement).prev().outerWidth();
                        tabElement = $(tabElement).prev();
                    }
                }
            } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
                scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
            }
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        },
        calSumWidth: function (element) {
            var width = 0;
            $(element).each(function () {
                width += $(this).outerWidth(true);
            });
            return width;
        },
        init: function () {

        	
    	$(".content-iframe").height($("body").outerHeight()-$(".content-iframe").position().top-$(".content-tabs").outerHeight());
    	
    	$(window).resize(function() {
    		$(".content-iframe").height($("body").outerHeight()-$(".content-iframe").position().top-$(".content-tabs").outerHeight());
    		});
            $('.menuItem').on('click', learuntab.addPage);
            $('.menuTabs').on('click', '.menuTab i', learuntab.closeTab);
            $('.menuTabs').on('click', '.menuTab', learuntab.activeTab);
            $('.tabLeft').on('click', learuntab.scrollTabLeft);
            $('.tabRight').on('click', learuntab.scrollTabRight);
            $('.tabReload').on('click', learuntab.refreshTab);
            $('.tabCloseCurrent').on('click', function () {
                $('.page-tabs-content').find('.active i').trigger("click");
            });
            $('.tabCloseAll').on('click', function () {
                $('.page-tabs-content').children("[data-id]").find('.fa-remove').each(function () {
                    $('.tab-page[data-id="' + $(this).data('id') + '"]').remove();
                    $(this).parents('a').remove();
                });
                $('.page-tabs-content').children("[data-id]:first").each(function () {
                    $('.tab-page[data-id="' + $(this).data('id') + '"]').show();
                    $(this).addClass("active");
                });
                $('.page-tabs-content').css("margin-left", "0");
            });
            $('.tabCloseOther').on('click', learuntab.closeOtherTabs);
            $('.fullscreen').on('click', function () {
                if (!$(this).attr('fullscreen')) {
                    $(this).attr('fullscreen', 'true');
                    learuntab.requestFullScreen();
                } else {
                    $(this).removeAttr('fullscreen')
                    learuntab.exitFullscreen();
                }
            });
        },getActionPage:function(){
        	 var currentId = $('.page-tabs-content').find('.active').attr('data-id');
             var target = $('.tab-page[data-id="' + currentId + '"]');
			return target;
		}
		})
	
	
	var learuntab=new TabWorkSpace();
	return {
		
		init:function(){
			learunindex.load();	
			learunindex.loadMenu();
		
			learuntab.init();
			learuntab.addPage({pageTitle:"欢迎首页",pageId:'default',pageUrl:'default.jsp',isClose:false})
			//learunindex.initEvent()
			$A.registerWorkSpace(learuntab)
		}
		
	}
})



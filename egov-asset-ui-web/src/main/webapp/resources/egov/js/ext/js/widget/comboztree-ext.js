/**
 *  扩展 下拉框添加 新增、刷新按钮
 *  author: zxy
 *  date: 2017-04-27
 *  $.uiExtend({
		type:'comboztree-ext',						// (*必传)扩展插件类型
		$combo:$A('#mgrDeptCode'),					// (*必传，JQ对象)下拉控件JQ对象
		widgetType:'comboztree',					// (*必传，控件类型:comboztree,combobox)
		options:{
			loadUrl:'egov/asset/aims/basic/aimsbasicdept/loadEnableDeptTree.do',  	// (*必传)加载下拉选项请求地址
			addUrl:'egov/asset/aims/basic/aimsbasicdept/addOuterDept.do',			// (*必传)新增选项请求地址
			paramName:'deptName',					// (*必传)新增选项请求参数
			textName:'treeNode',					// (*必传)下拉选项text字段名
			valueName:'deptCode',					// (*必传)下拉选项value字段名
			even:{
				add:function(text){},				// (*必传)新增完成后事件(text: 获取当前输入值)	
				refresh:function(text){}			// (非必传)刷新按钮事件,默认根据loadUrl刷新列表(text: 获取当前输入值)	
			}
		}
	});
 */
 define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util"
	 ],function(ExtBase,CUtil) {
	 var Comboztree = ExtBase.extend({
         // 初始化
		 init: function () {
			 var _this = this;
			 var defaultOpts = {
				 even:{
					 add:function(text){
//						 $A.assetMsg.success("新增存放地点【"+text+"】成功！");
					 },
		        	 refresh:function(text){
		        		 // 默认进行刷新操作
		        		 _this.$combo[_this.widgetType]('reload',_this.options.url);
		        	 }
				 }
			 };
			 
			 var opts = $.extend(true, {}, defaultOpts, _this.options);
			 _this.extPanel(_this, opts);
         },
         
         // 下拉面板扩展增加按钮
         extPanel: function(_this, opts){
        	 var comboData;
        	 if(_this.widgetType){
        		 comboData = _this.$combo.data(_this.widgetType);
        		 var $curObj;
        		 // 判断控件类型获取对应下拉面板
        		 if(_this.widgetType === 'comboztree'){
        			 $curObj = comboData.$ztree;
        		 }else{
        			 $curObj = comboData.$list;
        		 };
        		 var $curPanel = $curObj.parents('.drop-panel');
        		 var $curPanelNext = $curObj.parents('.drop-panel').next('.drop-panel');
        		 
        		 $curPanel.add($curPanelNext).css('overflow','visible').append('<div class="combo-toolbar"><p>\
				 		 <span class="add">新增</span>\
				 		 <span class="refresh">刷新</span>\
				 	 	 </p></div>').on('click','span',function(e){
				 	 	 if(!e.target.className){ return ;}
				 		 // 获取输入的自定义内容
				 		 var text = _this.$combo[_this.widgetType]('getText');
						 
				 		 if(e.target.className === 'add'){
							 // 在点击新增按钮时自动收起下拉面板
							 _this.$combo[_this.widgetType]('togglePanel');
							 
				 			 _this.addOption(text, opts, function(data){
				 				 // 后台新增完成后
				 				 if(_this.widgetType === 'comboztree'){
					 				 // 刷新面板
				 					 _this.$combo[_this.widgetType]('togglePanel');
				        			 // 重载数据
				 					 _this.$combo[_this.widgetType]("reload",opts.loadUrl);
									 // 下拉框赋值
				 					 _this.$combo[_this.widgetType]('setValue',data[opts.valueName]);
				 					 _this.$combo[_this.widgetType]('setText',data[opts.textName]);
				 				 }else{
					 				 _this.$combo[_this.widgetType]("reload",opts.loadUrl);
									 // 下拉框赋值
				        			 var curCombo = _this.$combo.data(_this.widgetType);
				        			 curCombo.$list.children().filter('[_v='+data[opts.textName]+']').click();
				 				 };
						 		 // 执行传入的回调函数
					 			 opts.even[e.target.className](text);
				 			 });
				 		 }else{
					 		 // 执行传入的回调函数
				 			 opts.even[e.target.className](text);
				 		 };
				 		 
				 		 
			 	 });
        	 };
        	 
         },
         
         // 新增请求
         addOption: function(text, opts, callback){
			 var compBack = callback||function(){};
			 var params = {};
			 params[opts.paramName] = text;
			 
			 $app.ajax.ajaxCall({
				 url : opts.addUrl,
				 data : params,
				 success : function(data) {
					 if(data.tag){
						 if(compBack)compBack(data.msg);
					 }else{
						 $A.assetMsg.error(data.msg);
					 }
				 },
				 error : function(){
					 console.log("新增失败！");
				 }
			 });
         }
         
     });
	 
     return Comboztree;
});
 
 
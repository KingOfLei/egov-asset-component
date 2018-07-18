/**
 *  扩展 表单文本域
 *  author: zxy
 *  date: 2017-05-9
 *  $.uiExtend({
	    type:'form-textarea',						// (*必传)扩展插件类型
		$parentObj:$('#aimsCard6Page'),				// (*必传，JQ对象)需插入的父级JQ对象
	    options:{
			textareaId:'aimsAuditPage_btnAudit',	// (*必传)文本域ID
			textareaName:'审批意见',				// (非必传)文本域label标题
			textVal:'',								// (非必传)文本域返回值
			height:80,								// (非必传)文本域高度
			disabled: false,						// (非必传)文本域是否只读
	    	dblclick:true,							// (非必传)选项列表是否支持双击选择，默认为 true
	    	loadName:'常用意见',					// (非必传)加载选项标题
	    	loadUrl:'',								// (*必传)加载选项请求地址
			loadParams:{},							// (非必传)加载选项请求参数，默认无参数
	    	addUrl:'',								// (*必传)新增选项请求地址
			addParams:{},							// (非必传)新增选项请求参数，默认参数为文本域输入值
			addItemField:'content',					// (非必传)新增请求的文本域取值参数字段名，默认字段名为 content
			deleteUrl:'',							// (*必传)删除选项请求地址
	    	deleteItemField:'id',					// (非必传)删除选项请求参数字段名，默认字段名为 id
	    	initialOpts:[							// (非必传)默认显示选项
		        {
		        	id:'1',
		        	content:'同意',
		        },
		        {
		        	id:'2',
		        	content:'不同意',
		        }
	        ]
	    }
	});
 */ 

define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util"
	 ],function(ExtBase,CUtil) {
	 var FormTextarea = ExtBase.extend({
         // 初始化
		 init: function () {
			 var _this = this;
			 var defaultOpts = {
				 textareaId:'',	
	    		 textareaName:'审批意见',
	    		 textVal:'',
	    		 height:80,
	    		 disabled: false,
	    		 dblclick:true,
	    		 addItemField:'content',
	    		 deleteItemField:'id'
			 };
			 
			 var opts = $.extend(true, {}, defaultOpts, _this.options);
			 _this.createTextatea(_this, opts);
			 
         },

         // 创建文本域
         createTextatea: function($this, opts){
        	 var toolbarHtml = '', disableVal = '';
        	 var formWidth = 0,labelWidth = 0,loadNameWidth = 0,count = 0, timer = null;
        	 // 判断是否在表单末尾创建文本域
        	 if($this.$parentObj.find('>.form').length > 0){
            	 formWidth = $this.$parentObj.find('table').eq(0).width();
            	 labelWidth = 150;
        	 }else{
        		 formWidth = $this.$parentObj.width();
        		 labelWidth = 80;
        	 };
        	 var textareaWidth = (formWidth - labelWidth - 2)/2;
        	 if(opts.disabled){
        		 disableVal = 'disabled="disabled"';
        	 };
        	 loadNameWidth = opts.loadName? 80:loadNameWidth;
        	 var loadName = opts.loadName?'<label class="loadName" style="' + loadNameWidth + 'px;">' + opts.loadName + '</label>':'';
        	 $this.$parentObj.append('<div class="form-textarea" style="width:' + formWidth + 'px;height:' + parseInt(opts.height) + 'px;">\
			  <label style="width:'+ labelWidth +'px;">' + opts.textareaName + '</label>\
			  <div class="inputArea"><textarea id="' + opts.textareaId + '" style="width:' + (textareaWidth - loadNameWidth) + 'px;" ' + disableVal + '>' + opts.textVal + '</textarea></div>\
			  	<div class="operateArea" style="width:' + (textareaWidth + loadNameWidth) + 'px;">\
 					<div class="buttonArea" style="width:' + 50 + 'px;">\
 						<span class="chose" title="选择">&lt;&lt;</span>\
 						<span class="add" title="添加">&gt;&gt;</span>\
 						<span class="delete" title="删除">x</span>\
 					</div>' + loadName + '\
 					<div class="listArea" style="width:' + (textareaWidth - 50 - 2) + 'px;">\
 						<ul></ul>\
 					</div>\
			  	</div>\
			  </div>').on('click','.form-textarea span',function(e){
				  $this.bindBtnEven(e, $this, opts);
			  }).on('mouseup','.form-textarea li',function(e){
				   count ++;
				   timer = window.setTimeout(function(){
				      clearTimeout(timer);
			    	  $this.bindListEven(e);
			    	  // 列表是否支持双击选中
		        	  if( count !== 1 && opts.dblclick){
		        		  $this.bindBtnEven(e, $this, opts, 'chose', true); 
		        	  };
				      count=0;
				   },250);
			  });
        	 
        	 $this.loadList($this, opts);
         },
         
         // 加载列表选项
         loadList: function($this, opts){
        	 $app.ajax.ajaxCall({
				 url : opts.loadUrl,
				 data : opts.loadParams,
				 success : function(data) {
					 
        	 		 data = data.length>0?data:opts.initialOpts;
	 		 		 $this.refreshList(data, $this, opts);
				 },
				 error : function(){
					 $A.assetMsg.warn('操作失败！');
				 }
			 });
         },
         
         // 根据返回数据刷新列表
         refreshList: function(data, $this, opts){
			 var $ul = $this.$parentObj.find('.form-textarea ul');
			 $ul.children().remove();
			 if(data.length > 0){
				 var ulHtml = '';
				 for(var i = 0; i < data.length; i++){
					 ulHtml += '<li _id="' + data[i]['id'] + '" title="' + data[i][opts.addItemField] + '">' + data[i][opts.addItemField] + '</li>';
				 };
				 $ul.append(ulHtml);
			 };
         },
         
         // 新增列表选项
         addList: function($this, opts){
        	 opts.addParams[opts.addItemField] = $A('#'+opts.textareaId).val();
        	 $app.ajax.ajaxCall({
				 url : opts.addUrl,
				 data : opts.addParams,
				 success : function(data) {
        		 	 $this.refreshList(data, $this, opts);
				 },
				 error : function(){
					 $A.assetMsg.warn('新增失败！');
				 }
			 });
         },
         
         // 删除列表选项
         deleteList: function($this, opts, row){
        	 var param = {};
        	 param[opts.deleteItemField] = $(row).attr('_id');
        	 $app.ajax.ajaxCall({
				 url : opts.deleteUrl,
				 data : param,
				 success : function(data) {
        		 	 $this.refreshList(data, $this, opts);
				 },
				 error : function(){
					 $A.assetMsg.warn('删除失败！');
				 }
			 });
         },
         
         // 绑定按钮点击事件
         bindBtnEven: function(e, $this, opts, type, noCheck){
        	 var evenType = type?type:e.target.className,curRow;
        	 if(!evenType){ return ;};
			 // 点击选中按钮
			 if(evenType === 'chose'){
				 curRow = $this.isChose($this, opts, noCheck);
				 if(curRow){
					 $A('#'+opts.textareaId).val(curRow.html());
				 };
			 // 点击添加按钮
			 }else if(evenType === 'add'){
				 $this.addList($this, opts);
			 }else if(evenType === 'delete'){
				 curRow = $this.isChose($this, opts);
				 if(curRow){
					 $this.deleteList($this, opts, curRow);
				 };
			 };
         },
         
         // 绑定列表li点击事件
         bindListEven: function(e){
        	 if($(e.target).hasClass("select")){
				$(e.target).removeClass('select');
			 }else{
				 $(e.target).addClass('select').siblings('li').removeClass('select');
			 };
         },
         
         // 判断是否选中数据
         isChose: function($this, opts, noCheck){
			 var selRow = $this.$parentObj.find('.form-textarea .listArea ul>li[class*="select"]');
			 if(selRow.length === 1){
				return selRow;
			 }else{
				 if(noCheck) return false;
				 $A.assetMsg.info("请选择一条数据！");
				 return false;
			 };
         }
     });
	 
     return FormTextarea;
});
 
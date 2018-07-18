/**
 *  扩展 网格切换卡片
 *  author: zxy
 *  date: 2017-05-15
 *  $.uiExtend({
        type:'view-card',						// (*必传)扩展插件类型
		$parentObj:$('#aimsCard6Page'),				// (*必传，JQ对象)需插入的父级表单JQ对象
        options:{
    		textareaId:'aimsAuditPage_btnAudit',	// (*必传)文本域ID
    		textareaName:'审批意见',					// (非必传)文本域label标题
    		textVal:'',								// (非必传)文本域返回值
    		height:80,								// (非必传)文本域高度
    		disabled: false,						// (非必传)文本域是否只读
        	dblclick:true,							// (非必传)选项列表是否支持双击选择，默认为 true
        	loadUrl:'',								// (*必传)加载选项请求地址
			loadParams:{},							// (非必传)加载选项请求参数，默认无参数
        	addUrl:'',								// (*必传)新增选项请求地址
			addParams:{},							// (非必传)新增选项请求参数，默认参数为文本域输入值
			addItemField:'content',					// (非必传)新增请求的文本域取值参数字段名，默认字段名为 content
			deleteUrl:'',							// (*必传)新增选项请求地址
        	deleteItemField:'id'					// (非必传)删除选项请求参数字段名，默认字段名为 id
        }
    });
 */
 define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util"
	 ],function(ExtBase,CUtil) {
	 var ViewCard = ExtBase.extend({
         // 初始化
		 init: function () {
			 var _this = this;
			 var defaultOpts = {
				 cardFields:{
	     			 head:{
	     				 'assetCode':'卡片编码',
	     				 'orgName':'所属单位'
	     			 },
	    			 content:{
	   			         'assetType6Name':'卡片类型',
	   			         'orgName':'部门名称',
	   			         'cardSrcType':'卡片来源',
	   			         'orgCode':'部门编码',
	   			         'getModeName':'获得方式'
	    			 },
	    			 foot:[
    			         {
    			        	 'maker': '建卡人',
    			        	 'makeDate': '建卡日期'
    			         },
    			         {
    			        	 'ownerName': '所有者',
    			        	 'makeDate': '建卡日期'
    			         }
	    			 ]
	    		 },
	    		 listFields:{
	    			 head:{
	    				 'assetCode': '卡片编码'
	    			 },
	    			 content:{
    			         'assetName':'卡片名称',
    			         'assetType6Name':'卡片类型',
    			         'orgName':'部门名称',
    			         'orgCode':'部门编码',
    			         'orgCode1':'部门编码1'
	    		 	 }
	    		 }
    		 };
			 
			 var opts = $.extend(true, {}, defaultOpts, _this.options);
			 _this.createPage(_this, opts);
			 
         },

         // 创建卡片页面
         createPage: function(_this, opts){
        	 var data = opts.data.rows;
        	 var $content = $A('#aimsCard6Page_grid').next().find('.grid-table');
        	 if($content.find('.view-card').length === 0){
            	 var mainHeight = $content.height() - 1;
            	 $content.children().remove();
        		 // 添加卡片主体样式
            	 $content.append('<div class="view-card" style="height:' + mainHeight + 'px;"><div class="show-card"><div class="show-card-table-cell">\
            			 <div class="show-card-item">\
	            			 <div class="show-card-title"></div>\
	            			 <div class="show-card-head"><ul></ul></div>\
	            			 <div class="show-card-main"><ul></ul><div class="images"></div></div>\
	            			 <div class="show-card-foot"></div>\
            			 </div></div></div>\
            			 <div class="list-card"><div class="list-card-title">卡片列表</div><div class="list-card-content"></div></div></div>');
        	 };
    		 
    		 _this.loadListData(_this, opts, data, $content);
         },
         
         // 加载大卡片信息
         loadCardData: function(_this, opts, data, $content, $listCard){
    		 // 添加大卡片标题
    		 $content.find('.show-card-item>.show-card-title').html('单位设备卡片');
    		 // 添加大卡片头部
    		 $content.find('.show-card-item>.show-card-head>ul').children().remove().end()
    		 		 .append(_this.ergodicData(opts.cardFields.head, data, 'card-head'));
    		 // 添加大卡片主体内容
			 $content.find('.show-card-item .show-card-main>ul').children().remove().end()
			 		 .append(_this.ergodicData(opts.cardFields.content, data, 'card-content'));
			 // 添加大卡片foot内容
    		 $content.find('.show-card-item>.show-card-foot').children().remove().end()
    		 		 .append(_this.ergodicData(opts.cardFields.foot, data, 'card-foot'));
    		 // 加载大卡片轮播图
    		 $content.find('.show-card-item .show-card-main>.images').children().remove();
    		 $.uiExtend({
		         type:'img-switch',	
		         $parentObj:$content.find('.show-card-main .images'),
		         options:{
		        	 imgAreaId:'slides'
		         }
		     });
         },
         
         // 加载小卡片列表信息
         loadListData: function(_this, opts, data, $content){
    		 // 数据过滤
        	 if(!data.assetCode){return ;};
         	 var listContentHtml = _this.ergodicData(opts.listFields.content, data, 'list-content'),
         	 	 listHeadHtml = _this.ergodicData(opts.listFields.head, data, 'list-head');
    		 // 添加右侧列表小卡片
    		 var listHtml = '<div c_id="' + data.assetCode + '" class="list-card-item">\
    			 <div class="list-card-head">'+ listHeadHtml +'</div>\
    			 <div class="list-card-main"><div class="list-card-main-info">' + listContentHtml + '</div>\
    			 <div class="list-card-main-imgs"><div class="list-card-main-imgs-cell"></div></div></div>\
    			 </div>';
    		 $content.find('.list-card-content').append(listHtml);
    		 $content.find('.list-card-main-imgs').height($content.find('.list-card-main-info').height());
    		 $.uiExtend({
		         type:'img-switch',	
		         $parentObj:$content.find('[c_id="'+data.assetCode+'"] .list-card-main-imgs-cell'),
		         options:{
		        	 imgAreaId:'slides'+data.assetCode
		         }
		     });
    		 // 小卡片绑定事件
    		 $('[c_id="' + data.assetCode + '"]').bind('click','.list-card-item',function(e){
    			 $(this).addClass('list-card-item-select').siblings('.list-card-item').removeClass('list-card-item-select');
    			 _this.loadCardData(_this, opts, data, $content, $('[c_id="' + data.assetCode + '"]'));
    		 });
    		 
    		 // 大卡片加载默认数据
    		 if(data['assetCode'] === 'SJ301002TD2017000109'){
    			 _this.loadCardData(_this, opts, data, $content, $('[v_id="' + data.assetCode + '"]'));
    		 };
         },
         
         // 遍历对象后返回值
         ergodicData: function(opts, data, type){
        	 var mosaicHtml = '';
        	 switch(type){
        	 	 // 列表content
    	 	 	 case 'list-head':
		         	 for(var key in opts){
		         		 data[key] = data[key]?data[key]:'';
	              		 mosaicHtml += data[key] + '：' + data.assetCode;
		 			 };
	 			 break;
        	 	 case 'list-content':
    	         	 for(var key in opts){
    	         		 data[key] = data[key]?data[key]:'';
	              		 mosaicHtml += '<p>' + opts[key] + '：<label title="' + data[key] + '">' + data[key] + '</label></p>';
		 			 };
		 			 break;
        	 	case 'card-head': 
        	 		for(var key in opts){
        	 			data[key] = data[key]?data[key]:'';
   	         			mosaicHtml += '<li>' + opts[key] + '：'+ data[key] + '</li>';
		 			};
        	 		
        	 		break;
        	 	case 'card-content': 
        	 		for(var key in opts){
        	 			data[key] = data[key]?data[key]:'';
   	         			mosaicHtml += '<li>' + opts[key] + '：<div>'+ data[key] +'</div></li>';
		 			};
        	 		
        	 		break;
        	 	case 'card-foot': 
        	 		for(var i = 0 ;i < opts.length; i++){
            	 		var pHtml = '';
        	 			for(var key in opts[i]){
            	 			data[key] = data[key]?data[key]:'';
            	 			var css = Object.keys(opts[i]).length>1?'recordDate':'';
            	 			pHtml += '<p class="' + css + '">' + opts[i][key] + '：<label>' + data[key] + '</label></p>';
    		 			};
            	 		mosaicHtml += '<div>'+ pHtml +'</div>';
        	 		};
        	 		
        	 		break;
        	 };
 			 return mosaicHtml;
         }
         
     });
	 
     return new ViewCard;
});
 
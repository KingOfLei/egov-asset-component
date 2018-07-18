/**
 *  扩展 网格切换卡片
 *  author: zxy
 *  date: 2017-05-15 v1.0
 *  update: 2017-6-19 v2.1
    // 调用方法参数 opts 说明
		 imgs: [{
		 	 src:'resources/egov/js/ext/theme/images/image-switch/img/example-slide-2.jpg',
		 	 title:''
	 	 },{
		 	 src:'resources/egov/js/ext/theme/images/image-switch/img/example-slide-1.jpg',
		 	 title:''
	 	 },{
		 	 src:'resources/egov/js/ext/theme/images/image-switch/img/example-slide-3.jpg',
		 	 title:''
	 	 }],
		 idField: 'assetId',					// 参数data中数据行的唯一识别字段
		 cardFields:{
 			 title:'assetType6Name',
			 head:{
 				 'assetCode':{
 					 name:'卡片编码',
 					 fn:function(){
 						 alert("打开卡片编辑页面");
 					 }
 				 },
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
				 'assetCode':'卡片编码'
			 },
			 content:{
		         'assetName':'卡片名称',
		         'assetType6Name':'卡片类型',
		         'orgName':'部门名称',
		         'orgCode':'部门编码'
		 	 }
		 }
 */
 define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util",
	 "resources/egov/js/aims/card/utils/CardUtil.js",
	  "resources/egov/js/ext/js/lib/jquery.jcarousellite.min.js",
	  "resources/egov/js/ext/js/lib/jquery.gallerypreview.min.js"
	 ],function(ExtBase,CUtil,cardUtil) {
	 var SwitchView = ExtBase.extend({
		 initialize : function(config) {
			 var defaultOpts = {};
			 this.options = $.extend(true, {}, defaultOpts,config);
			 SwitchView.superclass.initialize.call(this);
			 
			 this.minCardTpl=new Template(this.createMinCardTpl());
		 },
         // 初始化
		 init: function () {
			 var _this = this;
         },
         
         // 卡片列表网格行--图片显示区域格式化
         renderCard:function(data){
         	var imgHtml=data.imgs?'<img src="' + data.imgs[0].src + '" alt="图片加载失败" />':'';
         	data["imagesList"]=imgHtml;
        	var renderHtml=this.minCardTpl.apply(data);
        	delete data["imagesList"];
        	
        	return renderHtml;
         },
         
         // 创建卡片页面
         createPage: function(){
         	 if (this.cardInfoDom){
         		return this.cardInfoDom;
         	 };
        	 var opts=this.options;
        	 var cardFields=opts.cardFields,title=cardFields.title,head=cardFields.head,content=cardFields.content,footLeft=cardFields.foot.left,footRight=cardFields.foot.right;
        	 var cardInfoStr=[];
        	 cardInfoStr.push('<div class="switch-view">');
        	 cardInfoStr.push('  <div class="show-card">');
        	 cardInfoStr.push('    <div class="show-card-table-cell">');
        	 cardInfoStr.push('       <div class="show-card-item">');
        	 cardInfoStr.push('         <div class="show-card-title">');
        	 cardInfoStr.push('           <span class="card-field" data-field="'+title+'"></span>');
        	 cardInfoStr.push('         </div>');
        	 //输出头部
        	 cardInfoStr.push('         <div class="show-card-head">');
        	 cardInfoStr.push('           <ul>');
        	 for(var key in head){
        		 var name = head[key]['name'] ? head[key]['name'] : head[key];
        		 cardInfoStr.push('         <li><label>' + name + '：</label><label class="card-field" data-field="'+key+'" _field-belong="head"></label></li>'     );
 			 };
        	 cardInfoStr.push('           </ul>');
        	 cardInfoStr.push('         </div>');
        	 //输出内容
        	 cardInfoStr.push('         <div class="show-card-main">');
        	 cardInfoStr.push('           <ul>');
        	 
        	 for(var key in content){
        		 var name = content[key]['name'] ? content[key]['name'] : content[key];
        		 cardInfoStr.push(     '<li><label>' + name + '：</label><label class="card-field" data-field="'+key+'" _field-belong="content"></label></li>'     );
 			 };
        	 cardInfoStr.push('           </ul>');
        	 //输出图片
        	 cardInfoStr.push('           <div class="images">');
        	 cardInfoStr.push('           </div>');
        	 cardInfoStr.push('         </div>');
        	 //输出尾部
        	 cardInfoStr.push('         <div class="show-card-foot">');
        	 cardInfoStr.push('         	<div class="show-card-foot-left"><ul>');
        	 for(var opt in footLeft){
        		 cardInfoStr.push('         <li><label>'+footLeft[opt]+'：</label><label class="card-field" data-field="'+opt+'"></label></li>'     );
 			 };
        	 cardInfoStr.push('         	</ul></div>');
        	 cardInfoStr.push('         	<div class="show-card-foot-right">');
        	 for(var i=0,len=footRight.length;i<len;i++){
        		 var keys=footRight[i];
        		 cardInfoStr.push('           <ul>');
        		 for(var key in keys){
            		 cardInfoStr.push(     '<li><label>'+keys[key]+'：</label><label class="card-field" data-field="'+key+'"></label></li>'     );
     			 };
        		 cardInfoStr.push('           </ul>');
        	 };
        	 cardInfoStr.push('         	</div>');
        	 cardInfoStr.push('          </div>');
        	 cardInfoStr.push('       </div>');
        	 cardInfoStr.push('    </div>');
        	 cardInfoStr.push('  </div>');
        	 cardInfoStr.push('</div>');
        	 
             var cardInfoDom=$(cardInfoStr.join(""));
            	 
             this.cardInfoDom=cardInfoDom;
            	 
        	 return cardInfoDom;
         },

         // 卡片列表网格行格式化
         createMinCardTpl:function(){
        	 var tpl=[];
        	 var opts=this.options; 
        	 var listFields=opts.listFields,head=listFields.head,content=listFields.content;
        	 tpl.push('<div c_id="{'+opts.idField+'}" class="list-card-item">');
        	 tpl.push('<input type="checkbox" />')
        	 tpl.push('  <div class="list-card-head">') 
        	 for(var key in head){
        		 tpl.push( head[key]+' {'+key+'}'     );
 			 };
        	 tpl.push('  </div>');
        	 
        	 tpl.push('  <div class="list-card-main">')
        	 tpl.push('    <div class="list-card-main-info">')
        	 for(var key in content){
        		 tpl.push(     '<p>'+content[key]+'：<label title={'+key+'}>{'+key+'}</label></p>');
 			 };
        	 tpl.push('    </div>');
        	 tpl.push('    <div class="list-card-main-imgs">{imagesList}');
        	 tpl.push('    </div">');
        	 tpl.push('  </div>');
        	 tpl.push('</div>');
        
		     return tpl.join("");

         },
         
         // 网格数据加载完成后
         // cardEven: 给卡片绑定事件，参数为卡片JQ对象
         afterDataRender: function(datas, imgOpts, cardEven){
        	 var _this = this;

        	 // 处理网格无数据的情况
        	 if(datas.length === 0){
        		 if($('.noList').length === 0){
	        		 $('.switch-view').find('.list-card-content').children().hide().after('<span class="noList" style="display: inline-block;width: 100%;text-align: center;">无记录</span>');
        		 };
        		 _this.loadCardData({});
        		 $('.switch-view>.list-card').css('height', '100%');
        	 }else{
        		 $('.switch-view').find('.list-card-content').children().show().end().find('.noList').remove();
            	 // 默认加载第一张卡片数据
            	 _this.loadCardData(datas[0], imgOpts);
            	 // 绑定大卡片事件
            	 if(cardEven){
            		 cardEven($('.switch-view .show-card-item'));
            	 };
            	 $('.switch-view>.list-card').css('height', 'auto');
        	 };
        	 
        	 // 绑定复选框点击事件
        	 $('.switch-view div[c_id]>input').on('click', function(){
        		 $(this).prop("checked", function(i, val){
    				 return !val;
    			 }); 
        	 });
         },
         
         // 加载大卡片信息
         loadCardData: function(data, imgParams){
        	 var _this = this;
        	 var $cardInfoItem = $A(this.cardInfoDom).find(".card-field");
        	 $cardInfoItem.attr('title', '').html("");
         	 $A(this.cardInfoDom).find(".images").html("");
         	 
         	 $cardInfoItem.each(function(index,itemDom){
         		 var dataField=$A(itemDom).attr("data-field");
         		 if (data[dataField]){
         			 $A(itemDom).attr('title', data[dataField]).html(data[dataField]);
         		 };

         		 var cardOpts = _this.options.cardFields;
        		 if(cardOpts[$A(itemDom).attr('_field-belong')] && typeof cardOpts[$A(itemDom).attr('_field-belong')][dataField] === 'object'){
             		 // 绑定点击事件--注意先解绑事件再绑定，否则
            		 if(cardOpts[$A(itemDom).attr('_field-belong')][dataField]['fn']){
            			 $A(itemDom).unbind('click').on('click', function(){
            				 cardOpts[$A(itemDom).attr('_field-belong')][dataField]['fn'](data);
            			 }).addClass('item-link');
            		 };
        		 };
         		 
        		 
         	 });
         	 
         	 if(imgParams){
//         		IE8.9下网格样式有问题 宽度继承有问题
             	$A.ajax.ajaxCall({
                    url: imgParams.URL,	
                    data: imgParams.params,
                    success:function (pics) {
                    	var _galleryOpts = {
                            // 请求返回的所有图片路径
                            imgs:pics,
                            jclOpts:{
                                // 是否轮询
                                circular:false,
                                // 可见图片数量
                                visible: 5,
                                // 每张图片大小
                                imgSize:{width:80,height:60}
                            },
                            // 是否开启预览
                            openPreview: true,
                            defaultImg: ''
                        };
                    	var galleryOpts = $.extend(true, {}, _galleryOpts, imgParams.galOpts);
                   	 	$A(_this.cardInfoDom).find(".show-card-main .images").galleryPreview('refresh', galleryOpts);
                    },error:function(){}
                    
             	});
         	 };
         	 
        	 // 添加卡片列表的选中样式
        	 var opts=this.options; 
        	 $A('.list-card-item').removeClass('list-card-item-select');
        	 $A('[c_id='+data[opts.idField]+']').addClass('list-card-item-select');
         },
         
         // 扩展卡片列表 点击事件
         onClickRow: function(data, rowIndex, imgParams){
        	 var _this = this;
        	 // 判断复选框状态
			 $('[c_id="'+data[this.options.idField]+'"]>input').prop("checked", function(i, val){
				 return !val;
			 }); 
        	 
        	 // 加载当前点击的卡片内容到大卡片中
        	 _this.loadCardData(data, imgParams);
         }
       
     });
	
	 return  SwitchView;
});
 
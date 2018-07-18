/**
 *  插件 图片轮播(只显示单张图片，基于轮播图插件slidesjs) 
 *  author: zxy
 *  date: 2017-05-22
 *  $.uiExtend({
        type:'img-switch',							// (*必传)扩展插件类型
		$parentObj:$('#aimsCard6Page'),				// (*必传，JQ对象)需插入的父级表单JQ对象
        options:{
    		imgAreaId:'slides',						// (*必传)显示轮播图区域ID
    		width:'100px',							// (非必传) 设置宽度
			imgs:[									// (*必传)轮播图src路径
		        {
		        	src:'resources/egov/js/ext/theme/images/image-switch/img/example-slide-1.jpg'
		        },
		        {
		        	src:'resources/egov/js/ext/theme/images/image-switch/img/example-slide-2.jpg'
		        }
	        ]
			,plugInParams:{							// (非必传)轮播图插件参数
				start: 1,
				callback: {
					loaded: function(num) {			// 插件第一次初始化完成后的回调方法
						$('#'+_this.options.imgAreaId+' img').bind('click',function(){
							console.log($(this).attr('src'));
						});
			        },
			        start:function(number) {		// 图片开始切换时的回调方法
			        
			        },
			        complete:function(number) {		// 图片切换完成时的回调方法
			        
			        }
			    }
			}
        }
    });
 */
 define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util",
	 "resources/egov/js/ext/js/lib/jquery.slides.min"
	 ],function(ExtBase,CUtil,JSlides) {
	 var ImgSwitch = ExtBase.extend({
         // 初始化
		 init: function (option) {
			 var _this = this;
			 var defaultOpts = {
				 width:'100px'
				 ,plugInParams:{
					 start: 1
				 }
			 };
			 var opts = $.extend(true, {}, defaultOpts, _this.options);
			 // 插件bug修改：当图片只有一张时的显示问题，特殊处理
			 if(opts.plugInParams && opts.plugInParams.callback && opts.plugInParams.callback.loaded){
				 var _loaded = opts.plugInParams.callback.loaded;
				 opts.plugInParams.callback.loaded = function(num) {			// 插件第一次初始化完成后的回调方法
					 if(_this.$parentObj.find('img').length === 1){
						 // 当只有一张图片时调整显示位置
						 _this.$parentObj.find('img').css('left','0px');
						 // 隐藏链接按钮
						 _this.$parentObj.find('a').hide();
					 };
					 
					 // 执行自定义事件
					 _loaded(num,_this.$parentObj);
		         };
			 };
			 
			 _this.createImgatea(_this, opts);
			 
         },

         // 创建文本域
         createImgatea: function( $this, opts){
        	 var imgHtml = '';
        	 for(var i = 0; i < opts.imgs.length; i++){
        		 imgHtml += '<img src="' + opts.imgs[i].src + '" alt="图片加载失败" />';
        	 };
        	 $this.$parentObj.append('<div class="image-switch"><div id="' + opts.imgAreaId + '" class="slides">' + imgHtml + '</div></div>');
        	 // 插件初始化
        	 $('#' + opts.imgAreaId).parent().css('width', opts.width).end()
        	 						.slidesjs(opts.plugInParams);
         }
     });
	 
     return ImgSwitch;
});
 
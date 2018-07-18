/**
 *  扩展 绑定显示浮动文本域
 *  author: zxy
 *  date: 2017-04-11
 *  $.uiExtend({
        type:'flow-textarea',								// (*必传)扩展插件类型
        $obj:$('#aimsAuditPage_btnAudit'),					// (*必传，JQ对象)需绑定事件的JQ对象
        options:{													
    		showContextmenu:true,							//  (布尔值)是否使用右键菜单插件
    		showMode:'click',								//  (*必传，字符串) 显示方式：(click: 点击按钮, hover：悬停)
    		showPosition:'top',								// 文本域显示位置：(top: 显示在按钮上方，left: 显示在按钮左侧，bottom: 显示在按钮底部)
    		textareaId:'',									// (非必传)文本域ID
    		textareaName:'审批意见',							// 文本域label名称
    		toolBar:[{
    			id:'',
    			name: '确定',								// 按钮名称
    			eventFun: function(){}						// 按钮点击事件
    		},{
    			name: '取消',
    			eventFun: function(){}						// 默认将textarea内容清空
    		}]
        }
    });
 */
 define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util"
	 ],function(ExtBase,CUtil) {
	 var FlowTextarea = ExtBase.extend({
         // 初始化
		 init: function () {
			 var _this = this;
			 
			 var defaultOpts = {
				 showContextmenu:true,							//  (布尔值)是否使用右键菜单插件
				 widgetType:'button',
				 showMode:'click',								//  (*必传，字符串) 显示方式：(click: 点击按钮, hover：悬停)
	    		 showPosition:'top',								// 文本域显示位置：(top: 显示在按钮上方，left: 显示在按钮左侧，bottom: 显示在按钮底部)
	    		 textareaId:'',									// (非必传)文本域ID
	    		 textareaName:'审批意见',
	    		 toolBar:{
	    			 textareaOk:{								// 按钮ID
	    				 name: '确定',							// 按钮名称
		    			 eventFun: function(){					// 按钮点击事件
		    				 alert('确定');
		    			 }
	    			 },
	    			 textareaCancel:{
	    				 name: '取消',
		    			 eventFun: function(){
		    				 _this.cancelEven(_this);
		    			 }	
	    			 }
	    		 }
			 };
			 
			 var opts = $.extend(true, {}, defaultOpts, _this.options);
			 _this.bindEven(_this, opts);
			 
  		    $(document).on("click", function(){
  		    	var focusedElement = document.activeElement; 
  		    	_this.hideTextatea(_this.$obj);
  		    });
         },
         
         // 绑定浮动文本域显示事件
         bindEven: function($this, opts){
        	 var $flowTextarea = $this.createTextatea($this, opts);
        	 switch(opts.showMode){
        	 	case 'hover':
        	 		$this.$obj.add($flowTextarea).hover(function(){
				        	$this.showTextatea($this.$obj);
				        },
				        function(){
//			            	$this.hideTextatea($this.$obj);
				        }
				    );
        	 		break;
    	 		default:
//    	 			if(opts.widgetType === 'input'){
//    	 				$this.$obj[opts.showMode] = function(){
//	               		   $this.showTextatea($this.$obj);
//	               	 	};
//    	 			}else{
        	 			$this.$obj.on(opts.showMode,function(){
   	               		   $this.showTextatea($this.$obj);
   	               	 	});
//    	 			}
    	 			break;
        	 };
         },
         
         // 显示浮动文本域
         showTextatea: function($obj){
        	 $obj.siblings('.flow-textarea').show();
        	 $obj.siblings('.flow-textarea').find('textarea').focus();
         },
         
         // 隐藏文本域
         hideTextatea: function($obj){
        	 $obj.siblings('.flow-textarea').hide();
         },
         
         // 默认取消按钮点击事件
         cancelEven: function($this){
        	 $this.$obj.siblings('.flow-textarea').find('.flow-content>textarea').val('');
        	 $this.hideTextatea($this.$obj);
         },

         // 创建浮动文本域
         createTextatea: function($this, opts){
        	 var toolbarHtml = '';
        	 // 存储按钮需绑定的事件
        	 var btnEven = {};
        	 
        	// 工具按钮参数为对象时
        	 for(var i in opts.toolBar){
        		 var curHtml = '<label id="' + i + '">' + opts.toolBar[i].name + '</label>';
        		 btnEven[i] = opts.toolBar[i].eventFun;
        		 toolbarHtml += curHtml;
        	 };
        	 
        	 // 工具按钮参数为数组时
//        	 for(var i = 0; i < opts.toolBar.length;i++){
//        		 var curHtml = '<label id="' + opts.toolBar[i].id + '">' + opts.toolBar[i].name + '</label>';
//        		 btnEven[opts.toolBar[i].id] = opts.toolBar[i].eventFun;
//        		 toolbarHtml += curHtml;
//        	 };
        	 
        	 // 计算浮动文本域显示位置
//        	 var flowTop = 120-$this.$obj.height();
        	 var flowTop = 120-3;
        	 var flowLeft = $this.$obj.offset().left + $this.$obj.width() - 400;
        	 
        	 $this.$obj.parent().css('position','relative')
	        	 			  .append('<div class="flow-textarea" style="top: -' + flowTop + 'px;left: ' + flowLeft + 'px;">\
		        			 	<div class="flow-content"><label>' + opts.textareaName + '：</label>\
			 					    <textarea id="' + opts.textareaId + '"></textarea></div>\
			        			 	<div class="flow-toolbar">' + toolbarHtml + '\
			        			 	</div>\
		        			 	</div>');
        	 //　绑定按钮事件
    		 $('.flow-toolbar').on('click','label',function(e){
    			 btnEven[e.target.id]();
    		 });
    		 
     		$(".flow-textarea").on("click", function(e){
     			var focusedElement = document.activeElement; 
     		    e.stopPropagation();
     		});
        	 return $this.$obj.siblings('.flow-textarea');
         },
     });
	 
     return FlowTextarea;
});
 
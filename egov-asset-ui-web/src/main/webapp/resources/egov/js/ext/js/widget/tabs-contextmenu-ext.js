/**
 *  扩展 tab页面菜单快捷切换
 *  author: zxy
 *  date: 2017-03-13
 *  $.uiExtend({
        type:'tabs-contextmenu-ext',                                // (*必传)扩展插件类型
        $tab:$('#gridTab'),                                         // (*必传，JQ对象)tab的JQ对象
        options:{                                                   
            showContextmenu:true,                                   //  (布尔值)是否使用右键菜单插件
            showMode:'clickBtn',                                    //  (*必传，字符串) 菜单显示方式：clickBtn: 标题右侧按钮, rightClick：右击标题, both:同时添加
            tabHeads:[{                                             //  (*必传，数组对象)tab页签参数：对象中必须包括对应页签title
                title:'无形资产情况汇总表',                      
                url:'ooooo',                                        //  暂未实现，后期扩展
                callback:function(){}                               //  暂未实现，后期扩展
            },{
                title:'固定资产情况汇总表'
            }],
            callback:false                                          //  暂未实现，后期扩展
        }
    });
 */

 define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util"
	 ],function(ExtBase,CUtil) {
	 var TabsContextmenu = ExtBase.extend({
         // 初始化
		 init: function () {
			 var _this = this;
			 var contextMenuId = _this.options.contextMenuId = 'contentMenu';
			 if(!_this.options.showContextmenu){ return;};
			 if($('#'+contextMenuId).length > 0){$('#'+contextMenuId).remove();};
			 // 创建右键菜单
			 _this.createContextMenu(_this);
			 
			 // 鼠标离开菜单500毫秒后自动隐藏
			 $('#'+contextMenuId).hover(function(){
			     if(this.timeout){clearTimeout(this.timeout)}
			    	 $('#'+contextMenuId).show();
			     },
			     function(){
			         this.timeout = setTimeout(function(){
			        	 $('#'+contextMenuId).hide();
			         }, 500);
			     }
			 );

         },
         
         // 显示菜单
         showContextMenu: function($menu){
        	 $menu.show();
         },
         
         // 隐藏菜单
         hideContextMenu: function($menu){
        	 $menu.hide();
         },
         
         // 扩展tabs标题栏右击事件
         bindContextMenu: function(event,contextMenuId,_self){
        	 // 显示右键菜单
        	 _self.showContextMenu($('#'+contextMenuId));
        	 
        	 var forRight=document.getElementById(contextMenuId);
        	 // 获取当前鼠标与窗口右侧边界的距离
        	 var widthDistance = parseInt($(window).width()-event.clientX);
        	 if(widthDistance > 200){
    			 forRight.style.left = event.clientX + "px"; 
    			 forRight.style.right = 'inherit'; 
        	 }else{
        		 forRight.style.left = 'inherit';
    			 forRight.style.right = widthDistance + "px"; 
        	 };
        	 
			 forRight.style.top = event.clientY + "px";
			 
			 // 判断当前选中tab,样式区分显示
			 var curTabTitle = _self.$tab.tabs('getSelected').panel('options').title;
			 $('#'+contextMenuId).find('li').each(function(ele, index){
				 var menuTitle = $(this)[0].innerText;
				 if(menuTitle === curTabTitle){
					 $(this).addClass('contextmenu-on');
				 }else{
					 $(this).removeClass('contextmenu-on');
				 };
			 });
			 
         },
         
         // 绑定右击
         bindRightClick: function(_self,contextMenuId){
        	 
			var tabObj = _self.$tab.data('tabs');
			// 绑定tab的title右键事件
			tabObj.on('onContextMenu',function(event, title, index){
				_self.bindContextMenu(event, contextMenuId, _self);
				// 阻止默认事件
				event.stopPropagation();
				event.preventDefault();
				return false;
			});
			
         },
         
         // 绑定点击按钮
         bindClickBtn: function(_self,contextMenuId){

 	 		// tabs右侧增加按钮用于点击显示更多tab标签
 	 		var moreTabsHtml = '<div id="tab-scroller-more" class="tabs-scroller-more"></div>';
 	 		_self.$tab.find('.tabs-header>.tabs-scroller-right').after(moreTabsHtml);
 	 		
 	 		// 绑定更多tab按钮点击事件
 	 		$('#tab-scroller-more').on('click',function(e){
 	 			_self.bindContextMenu(e, contextMenuId, _self);
 	 		});
 	 		
         },
         
         // 创建右键菜单
         createContextMenu: function( _self){
        	 var contextMenuId = _self.options.contextMenuId;
        	 var arrHeads = _self.options.tabHeads;
        	 var contextMenuHtml = '',menuHtml = '', $parent;
        	 for(var i = 0; i < arrHeads.length; i++){
        		 var liHtml = '<li><a>' + arrHeads[i]['title'] + '</a></li>';
        		 menuHtml += liHtml;
        	 };
        	 contextMenuHtml = '<ul id="' + contextMenuId + '" class="tabs-contextmenu">'
        		 				+ menuHtml +
        		 				'</ul>';

        	 $('body').append(contextMenuHtml);
        	 
        	 // 判断菜单显示方式： tabs标签title右键显示   或   tabs固定按钮点击后显示
        	 switch(_self.options.showMode){
        	 	case 'rightClick':
        	 		_self.bindRightClick(_self,contextMenuId);
        	 		
        	 		break;
        	 	case 'clickBtn':
        	 		_self.bindClickBtn(_self,contextMenuId);
        	 		
        	 		break;
        	 	case 'both':
        	 		_self.bindRightClick(_self,contextMenuId);
        	 		_self.bindClickBtn(_self,contextMenuId);
        	 		
        	 		break;
        	 };
        	 

			 // 绑定右键菜单li点击事件
			 $('#' + contextMenuId + ' li').on('click',function(e){
				 _self.bindItemsEvent(_self.$tab, $(this)[0].innerText,_self,contextMenuId);
			 });
         },
         
         // 绑定右键菜单li点击事件
         bindItemsEvent: function($tab, title, _self, contextMenuId){
        	 // 隐藏右键菜单
        	 _self.hideContextMenu($('#'+contextMenuId));
        	 
        	 // 选中相应tab
//        	 $tab.tabs('select',title);
        	 $tab.find('.tabs .tabs-title').each(function(index,ele){
        		 if($(ele).html() === title){
        			 $(ele).closest('li').trigger('click');
        		 }
        	 });
         }
     });

     return TabsContextmenu;
});
 
 
 
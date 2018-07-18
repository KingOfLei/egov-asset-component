/**
 * 流程tab控件
 *
 *
 * $A('#tbasicZyqOrgPage_panel_center').uiExtend({
                            type: 'flow-tabs',
                            beforeClick: function () {
                            },
                            afterClick: function () {
                            },
                            data: {
                                title: '12312',
                                tabs: [
                                    {
                                        text: '全部',
                                        num: 5,
                                        url:'egov/asset/aims/basic/aimsbasicorg/showIndex.do',//页面url
                                        buttons: [//按钮 text 显示文字，method assetFlow2 page对象的方法
                                            {
                                                text: '保存',
                                                method: 'test1'
                                            },
                                            {
                                                text: '编辑',
                                                method: 'test2'
                                            }
                                        ]，
                                        buttonConfig:{
                                            //索引从1开始，1代表第二个tab，依次类推
                                            showIndex:[1,2]
                                        }
                                    },
                                    {
                                        text: '附件',
                                        num: 1,
                                        url:'egov/aims/tzyq/tbasiczyqorg/assetFlow4.do',//页面url
                                        buttons: [//按钮 text 显示文字，method assetFlow2 page对象的方法
                                            {
                                                text: 'btn4',
                                                method: 'test'
                                            }
                                        ]
                                    }
                                ]
                            }
                        });
 *
 */
define([
    "resources/egov/js/ext/js/base/ext-base",
    "app/widgets/menu/app-menu",
    "app/widgets/button/app-menubutton"

],function(ExtBase) {
    var FlowTabs = ExtBase.extend({
        init: function () {
            //内容
            this.contentDivs = [];
            var self = this;
            var data = self.data ? self.data : {title: 'flowpage', tabs: [{text: 'all', num: 0}]};
            var buttonConfig = [];
            this.buttonConfig = buttonConfig;
            $(data.tabs).each(function (i, item) {
                var bc = [];
                item.buttonConfig&&$.isArray(item.buttonConfig.showIndex)&&(bc=item.buttonConfig.showIndex);
                buttonConfig.push(bc);
            });
        },
        beforeRender: function () {
            var self = this;
            var $component = this.$component;
            $component.addClass('flow-page');
            var $head = $('<div class="page-head">');
            var $title = $('<div class="title">');
            var $tabs = $('<div class="flow-tabs">');
            var $ul = $('<ul>');
            var $content = $('<div class="flow-content">');
            var $foot = $('<div class="page-foot">');

            $head.append($title).append($tabs);
            $tabs.append($ul);
            $component.append($head).append($content);
            self.hasFoot&&($component.append($foot));

            self.$head = $head;
            self.$tabs = $tabs;
            self.$ul = $ul;
            self.$content = $content;
            self.$title = $title;
            self.$foot = $foot;
        },
        render: function (config) {
            var self = this;
            var data = self.data ? self.data : {title: 'flowpage', tabs: [{text: 'all', num: 0}]};
            var $ul = self.$ul;
            var $tabs = self.$tabs;

            self.$title.text(data.title);

            var li = '<li>\
                    <div class="tab-title">\
                    <span class="text"></span>\
                    <span class="num"></span>\
                    </div>\
                    <span class="split">|</span>\
                </li>';
            if ($.isArray(data.tabs)) {
                $(data.tabs).each(function (i, item) {
                    //tab初始化
                    var $li = $(li);
                    $li.attr('_forTab',i);
                    $li.data('data', item);
                    var $text = $li.find('.tab-title .text');
                    $text.text(item.text);
                    $ul.append($li);
                    //隐藏tab
                    if(item.hide===true){
                        $li.hide();
                    }
                    var $pli= $li.prev();
                    if($pli.length){
                        $pli.find('.split').hide();
                    }
                    if (!!item.num) {
                        self.setTabNum(i, item.num);
                    }
                });
            }
            //删除最后一个分隔符
            $ul.find('li:last .split').remove();
            $ul.append($('<li class="sel-li">'));
            if (config.tabIndex != undefined) {
            	self.select(config.tabIndex);
            }else {
            	self.select(0);
            }
        },
        bind: function () {
            var self = this;
            var $ul = self.$ul;
            var beforeClick = $.isFunction(self.beforeClick) ? self.beforeClick : function () {
                    return true
                };
            var afterClick = $.isFunction(self.afterClick) ? self.afterClick : function () {
                };
            $ul.find('li:not(".sel-li") .tab-title').on('click', function (e) {
                var $li = $(this).parent();
                if (beforeClick.apply(self, [$li]) === false) {
                    return;
                }
                self.select($li.index());
                afterClick.apply(self, [$li])
            });
        },
        hideTab:function (index) {
            var self = this;
            var $ul = self.$ul;
            var $li = $ul.find('li').eq(index);
            var $pli= $li.prev();
            if($pli.length)
            {
                $pli.find('.split').hide();
            }
            $li.hide();
        },
        showTab:function (index) {
            var self = this;
            var $ul = self.$ul;
            var $li = $ul.find('li').eq(index);
            var $pli= $li.prev();
            if($pli.length)
            {
                $pli.find('.split').show();
            }
            $li.show();
        },
        /**
         * 根据下标选tab
         * @param index
         */
        select: function (index) {
            var self = this;
            var $ul = self.$ul;
            var $tabs = self.$tabs;
            $ul.find('.selected').removeClass('selected');
            var $li = $ul.find('li').eq(index);
            if ($li.length == 0) {
                return;
            }
            $li.addClass('selected');
            var $seli = $ul.find('li.sel-li');
            var offset = $li.offset();
            var left = offset.left + $li.width() / 2 - 7;
            var top = offset.top + $li.height() - 6;
            $seli.offset({top: top, left: left});
            $seli.offset({left: left});
            self.switchContent(index);
            return this;
        },
        //切换tab内容
        switchContent: function (index) {
            var self = this;
            self.hideContents();
            self.hideButtons();

            var $ul = self.$ul;
            var divs = self.contentDivs;
            var $div = divs[index];
            if (!!$div) {
                $div.show();
                $div.find('.layout:eq(0)').layout('resize');
                self.showButton(index);
                return;
            }
            var $li = $ul.find('>li').eq(index);
            var data = $li.data('data');
            $div = $('<div class="content-box">');
            $div.attr('_forTab',index);
            self.$content.append($div);
            divs[index] = $div;
            data && !!data.url && this.ajaxHtml(data, $div,index);
            return this;
        },
        hideContents: function () {
            var _self = this;
            $(_self.contentDivs).each(function (i, o) {
                $(o).hide();
            });
            return this;
        },
        showContent:function (index) {
            this.$content.find('.content-box[_forTab="'+index+'"]').show();
            return this;
        },
        isSkipButton: function (index) {
            var isSkip = false;
            var buttonIndex = undefined;
            var result = {};
            if (typeof index === 'undefined') {
                isSkip = false;
            }
            var self = this;
            var buttonConfig = self.buttonConfig;
            for (var i = 0, len = buttonConfig.length; index && i < len; i++) {
                var showIndex = buttonConfig[i];
                if ($.inArray(index,showIndex) != -1) {
                    isSkip = true;
                    buttonIndex = i;
                    break;
                }
            }
            result = {
                skip:isSkip,
                buttonIndex:buttonIndex
            }
            return result;
        },
        hideButtons:function () {
            this.$tabs.find('.flow-btn-area[_forTab]').hide();
            return this;
        },
        showButton:function (index) {
            var skipDes = this.isSkipButton(index);
            if(skipDes.skip){
                index = skipDes.buttonIndex;
            }
            this.$tabs.find('.flow-btn-area[_forTab="'+index+'"]').css({display:'inline-block'});
            return this;
        },
        ajaxHtml: function (data, $div,index) {
            var self = this;
            $div.htmlAJAX({
                type: "POST",
                url: data.url,
                data: data.params,
                callback: function () {

                },
                onPageLoad: function () {
                	$div.css('visibility','');
                    self.addButtons(data.buttons);
                    var appJsObj = $div.getAppJsObject();
                    var page = undefined;
                    !!appJsObj && appJsObj.objs && (page = appJsObj.objs[0]);
                    if(!!page){
                        page.FlowTabs =  self;
                    }
                    if ($.isFunction(data.onPageLoad)) {
                        var args = [page,index];
                        args =args.concat([].slice.call(arguments,0));
                        data.onPageLoad.apply(data,args);
                    }
                    if(!$A('.flow-btn-area[_forTab]').length){
                        self.addButtons(data.buttons,index);
                    }
                    self.$tabs.find('.flow-btn-area[_forTab="'+index+'"] button').on('click',(function(){
                        return function (e) {
                            var _invoke = $(this).attr('_method');
                            var _params = $(this).attr('_params');
                            if(!!_params){
                            	  page&&page[_invoke]&&page[_invoke](JSON.parse(_params));
                            }else{
                            	  page&&page[_invoke]&&page[_invoke]();
                            }
                        };
                    })());
                    $(document).find('[_forTabMenu="'+index+'"] div.menu-item').on('click',(function(){
                        return function (e) {
                            var _invoke = $(this).attr('_method');
                            var _params = $(this).attr('_params');
                            if(!!_params){
                          	  page&&page[_invoke]&&page[_invoke](JSON.parse(_params));
                            }else{
                           	  page&&page[_invoke]&&page[_invoke]();
                            }
                        };
                    })());
                    self.showButton(index);
                }
            });
            return this;
        },
        addButtons:function (buttons,index) {
            var self = this;
            var $tabs = self.$tabs;

            //tabs按钮初始化
            var $btnArea = $('<div class="flow-btn-area">');
            $tabs.append($btnArea);
            $btnArea.attr('_forTab',index);
            var menus = '<div id="${id}_menu" _forTab="${index}" class="app-menu dlg-menubutton"\
                                    data-options={"duration":100,"minWidth":64,"alignTo":null,"align":null}>\
                                </div>';
            var menuButton = '<div id="${id}" class=""\
                                        data-options={"height":null,"href":null,"disabled":null,"iconCls":null}>\
                                        ${text}\
                                   </div>';

            $(buttons).each(function (j,btn) {
                var $btn = $('<button>');

                $btn.text(btn.text);
                btn.index = index;
                if(!!!btn.id){
                    btn.id = btn.method;
                }
                $btn.prop('id',btn.id);
                $btn.attr('_method', btn.method);
                if(!!btn.params){
                	 $btn.attr('_params', JSON.stringify(btn.params));
                }
                $btnArea.append($btn);
                //添加子菜单
                if(!$.isArray(btn.menus)){
                    return;
                }

                var $menus =  $(dotpl.applyTpl(menus,btn));
                $menus.attr('_forTabMenu',index);
                $btn.attr("data-options",JSON.stringify({
                    'iconAlign':null ,
                    'menu':$menus.prop('id') ,
                    'name':'新增',
                    'plain':true ,
                    'css':null ,
                    'iconCls':'btn-add' ,
                    'size':null}));
                $(btn.menus).each(function (j,menu) {
                    if(!!!menu.id){
                        menu.id = btn.method+'_'+menu.method;
                    }else{
                        menu.id = btn.method+'_'+menu.id;
                    }
                    var $menusBtn =  $(dotpl.applyTpl(menuButton,menu));
                    $menusBtn.attr('_method', menu.method);
                    if(!!menu.params){
                    	$menusBtn.attr('_params', JSON.stringify(menu.params));
                    }
                    $menus.append($menusBtn);
                });
                $menus.hide();
                $menus.appendTo($btnArea);

                //使用平台组件
                $menus.menu();
                $btn.menubutton();
                $btn.addClass('app-menubutton');
                //为了兼容样式，调整，懒的写css~
                var $span = $btn.find('>span');
                $span.find('i').remove();
                $span.find('span.l-btn-text').removeClass('l-btn-text');
                $span .children().each(function(i,item){$(item).appendTo($btn)});
                $btn.addClass($span.prop('class'));
                $span.remove();
            });

        },
        /**
         *
         * @param $num
         * @param val
         */
        setTabNum: function (index, val) {
            var self = this;
            var $ul = self.$ul;
            var $num = $ul.find('li').eq(index).find('.tab-title .num');
            $num.text('(' + val + ')');
            return this;
        },
        /**
         * 获取选中tabs的下标
         * @returns {*}
         */
        getSelectIndex: function () {
            var self = this;
            var $ul = self.$ul;
            var sel = $ul.find('.selected');
            return sel.index();
        },
        /**
         * 根据下标获取tab
         * @param index
         * @returns {*}
         */
        getTabByIndex: function (index) {
            var self = this;
            var $ul = self.$ul;
            var $li = $ul.find('li').eq(index)
            return $li;
        },
        destroy:function () {
            this.$component.removeClass('flow-page').empty();
        }
    });
    return FlowTabs;
});

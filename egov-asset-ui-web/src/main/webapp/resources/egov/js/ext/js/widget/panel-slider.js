/**
 * Created by qiu.yong on 2017/3/13.
 */
;(function ($) {
    var PanelSlider = function (options) {
        $.extend(this, this.defaultParams);
        $.extend(this, options);
        this.init();
        this.beforeRender();
        this.render();
        this.afterRender();
        this.bind();
        this.completed();
    };

    PanelSlider.prototype = {
        MODE:{
            HTML:'html',
            URL:'url'
        },
        defaultParams: {
            height: 150,
            width: '100%',
            position: 'bottom',
            toggleSpeed: 1000,
            mode:'html',
            content:''
        },
        init: function () {
            var self = this;
            self.$component = $('body');
            var width = $(window).width();
            var height = $(window).height();
            self.panelOffset = {
                bottom: -self.height,
                left: 0
            }
        },
        beforeRender: function () {
            var self = this;
            var $componet = self.$component;
            var elements = '<div class="panel-slider">\
                            <div class="panel-tab first">\
                            <span></span>\
                            </div>\
                            <div class="panel-content" >\
                            </div>\
                         </div>';
            var $elements = $(elements);
            var $content = $elements.find('.panel-content');
            var $tabText = $elements.find('.panel-tab>span');
            self.$elements = $elements;
            self.$content = $content;
            self.$tabText = $tabText;
        },
        render: function () {
            var self = this;
            var $componet = self.$component;
            var $elements =self.$elements;
            var $content = self.$content;
            var $tabText = self.$tabText;

            $elements.hide();
            $elements.prop('id', self.id);
            $elements.height(self.height);
            $elements.width(self.width);
            $elements.css(self.panelOffset);

            $tabText.text(self.tabText);

            switch (self.mode){
                case self.MODE.HTML:
                    $content.html(self.content);
                    break;
                case self.MODE.URL:

                    break;
            };

            $componet.append($elements);
        },
        afterRender: function () {
            var self = this;
            self.$elements.show();
        },
        bind: function () {
            var self = this;
            var $elements = self.$elements;

            var $tabs = $elements.find('.panel-tab');
            $tabs.on('click', function () {
                var $tab = $(this);
                self.selectTab($tab);
            });

        },
        completed:function () {
            //成功初始化后
            $.panelSlider.list.push(this);
        },
        //选择tab
        selectTab: function ($tab) {
            var self = this;
            var $elements = self.$elements;
            if ($tab.length == 0) {
                $tab = $elements.find('.panel-tab').eq(0);
            }
            if ($tab.is('.popup')) {
                $tab.removeClass('popup');
            } else {
                $tab.addClass('popup');
            }
            self.togglePanel();
        },
        //切换 显示或者隐藏panel
        togglePanel: function () {
            var self = this;
            var $elements = self.$elements;
            var toggleSpeed = self.toggleSpeed;
            //TODO  css3 transform
            var css = '{"p":"v"}'.replace('p', self.position);
            if ($elements.is('.show')) {
                $elements.removeClass('show');
                css = css.replace('v', -self.height+4);
                $elements.animate($.parseJSON(css), toggleSpeed);
            } else {
                css = css.replace('v', 0);
                $elements.animate($.parseJSON(css), toggleSpeed);
                $elements.addClass('show');
            }
        },
        show:function () {
            var self = this;
            var $elements = self.$elements;
            if (!$elements.is('.show')) {
                this.togglePanel();
            }
        },
        hide:function () {
            var self = this;
            var $elements = self.$elements;
            if ($elements.is('.show')) {
                this.togglePanel();
            }
        },
        //销毁
        destroy:function () {
            var self = this;
            var $elements = self.$elements;
            $elements.remove();
        }
    };

    $.extend({
        panelSlider:{
            list:[],
            idMap:{},
            create:function (options) {
                return  new PanelSlider(options);
            },
            destroyAll:function () {
                if(!!$.panelSlider.list){
                    $($.panelSlider.list).each(function (i,obj) {
                        obj.destroy();
                    });
                    $.panelSlider.list = [];
                }
            },
            destroyById:function (id) {
                $($.panelSlider.list).each(function (i,obj) {
                    if(obj.id===id){
                        obj.destroy();
                    }
                });
            },
            getById:function (id) {
                var obj = undefined;
                for(var i =0;i<$.panelSlider.list.length;i++){
                    var obj = $.panelSlider.list[i];
                    if(obj.id===id){
                        return obj;
                    }
                }
            },
            getCurrent:function () {
                return this.list[this.list.length-1];
            }
        }
    });
})(jQuery);


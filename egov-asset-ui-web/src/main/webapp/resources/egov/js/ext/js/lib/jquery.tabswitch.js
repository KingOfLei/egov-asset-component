/*!
 * tab页签插件
 * author: zhangxiaoyun
 * date: 2017.12.05
 * version: 1.0
 *  --初始化
 *  $('#tabSwitch').tabswitch({
        data: [{
            // 设置该标题id
            id:'',
            title: '标题1',
            // 是否为当前选中标签, 默认false
            active: true,
            // 是否未读, 默认true
            unread: false,
            onClick: function(data, index){
                alert("您当前点击了" + data.title);
            },
            // 后期扩展使用，可不传
            url:''
        }]
    });
 *  
    // 选中第三个tab  index从0开始
    // $('#tabSwitch').tabswitch('selectTab', 2);
    // 将tab设置未读
    // $('#tabSwitch').tabswitch('setUnread', { index: 0, flag: true});
 */

;(function($){
    $.fn.tabswitch = function(options, content) {
        var TabSwitch = {};
        var _this = this, _opts = {}, fnType;
        var type = typeof options;

        // 私有方法--初始化插件
        TabSwitch.privateFn = function(options, fnType){
            // return _this.each(function() {
                // var $_this = $(this);
                var tabSwitch = {};
                // tabSwitch.headClassName = {
                //     active: ' tabswitch-header-active ',
                //     last: ' tabswitch-header-last ',
                //     unread: ' tabswitch-header-unread '
                // };
                // 初始化创建页面
                tabSwitch._init = function(options){
                    return _this.each(function() {
                        var $_this = $(this);

                        var tabHtml = '', tabHeadHtml = '', tabHeadLiHtml = '', 
                            tabContentHtml = '', tabContentBoxHtml = '';
                        var data = options.data;
                        for (var i = 0; i < data.length; i++) {
                            var tabOnClass = '', tabUnreadClass = '', tabLastClass = '', tabLiClass = '';
                            // 判断是否选中状态
                            if (data[i].active) {
                                tabOnClass = tabLiClass = options.headClassName.active;
                            };
                            // 判断是否为最后一个元素，进行样式处理
                            if (i == (data.length-1)) {
                                tabLiClass += options.headClassName.last;
                            };

                            // 判断是否为未读状态
                            if (data[i].unread) {
                                tabLiClass += options.headClassName.unread;
                            };

                            tabHeadLiHtml += '<li id="' + (data[i].id ? data[i].id : '') + '" class="' + tabLiClass +'"><a>' + data[i].title + ' </a><sup></sup></li>';
                            
                            // 使用闭包绑定标题点击事件
                            (function(data, index){
                                // 先解绑事件，避免事件重复绑定
                                $_this.off("click",".tabswitch>.tabswitch-header>ul>li:eq(" + index +")>a");
                                
                                $_this.on("click",".tabswitch>.tabswitch-header>ul>li:eq(" + index +")>a",function(e){ 
                                    tabSwitch._bindBeforeClick($(this), index, options.headClassName.active);
                                    if (data.onClick) {
                                        data.onClick(data, index);
                                    }; 

                                });
                            })(data[i], i);
                        };

                        tabHeadHtml = '<div class="tabswitch-header">\
                            <ul>' + tabHeadLiHtml + '</ul>\
                        </div>';

                        tabContentHtml = '<div class="tabswitch-content">\
                            <div class="tabswitch-content-center">' + tabContentBoxHtml + '</div>\
                        </div>';

                        $_this.append('<div class="tabswitch">' + tabHeadHtml + tabContentHtml + '</div>');
                    });
                };

                // 绑定tab选中事件(非自定义事件)
                tabSwitch._bindBeforeClick = function($this, index, activeClass){
                    $this.parent().siblings().removeClass(activeClass).end().addClass(activeClass);
                    // 后续扩展需绑定点击后展示对应content内容事件
                    
                };
                
                return tabSwitch._init(options);

            // });
        };
        

        // ----------------------以下为开放的共有方法-------------------------
        // 手动选中对应tab
        $.fn.tabswitch.selectTab = function(index){
            return _this.each(function() {
                var $_this = $(this);
                $_this.find(".tabswitch>.tabswitch-header>ul>li:eq(" + index +")>a").trigger("click");
            });
        }

        // 获取当前选中tab
        $.fn.tabswitch.getSelected = function(){
            var $tab;
            $tab = _this.find(".tabswitch>.tabswitch-header>ul>li.tabswitch-header-active");
            return $tab;
        };

        // 获取所有Tab对象
        $.fn.tabswitch.getTabs = function(){
            var $tabs;
            $tabs = _this.find(".tabswitch>.tabswitch-header>ul>li");
            return $tabs;
        };

        // 手动设置已读未读样式(外部调用方法)
        // opts = { index: 0, flag: true};
        $.fn.tabswitch.setUnread = function(opts){
            return _this.each(function() {
                var $_this = $(this);
                var data = $_this.data('tabswitch');
                if (opts.flag) {
                    $_this.find(".tabswitch>.tabswitch-header>ul>li:eq(" + opts.index +")").addClass(data.headClassName.unread);
                }else{
                    $_this.find(".tabswitch>.tabswitch-header>ul>li:eq(" + opts.index +")").removeClass(data.headClassName.unread);
                };
            });
        };

        // ----------------------以上为开放的共有方法-------------------------

        // // 开放公有方法
        // $.fn.tabswitch.publicFn = function(options, fnType){
            
        //     var tabSwitchPubFn = {};
        //     // 手动选中Tab(外部调用方法)
        //     // index 从0开始
        //     tabSwitchPubFn.selectTab = function(index){
        //         return _this.each(function() {
        //             var $_this = $(this);
        //             $_this.find(".tabswitch>.tabswitch-header>ul>li:eq(" + index +")>a").trigger("click");
        //         });
        //     };

        //     return tabSwitchPubFn[fnType](options);
        // };

        // 判断是插件初始化或刷新方法调用
        if (type === 'object') {
            _opts = $.extend(true, {}, $.fn.tabswitch.options, options || {});
        }else if(type === 'string'){
            fnType = options;
            if((content instanceof Object) && !(content instanceof Array)){
                _opts = $.extend(true, {}, $.fn.tabswitch.options, content || {});
            }else{
                _opts = content;
            };
        };


        if (fnType) {
            return $.fn.tabswitch[fnType](_opts);
        }else{
            _this.data('tabswitch', _opts);
            return TabSwitch.privateFn(_opts, fnType);
        };
    };

    // 默认参数设置
    $.fn.tabswitch.options = {
        headClassName: {
            active: ' tabswitch-header-active ',
            last: ' tabswitch-header-last ',
            unread: ' tabswitch-header-unread '
        },
        cssStyle: {
            width: '100%',
            height: '100%',
            // 是否显示未读标识
            unread: false,
            // 是否为当前选中状态
            active: false
        }
    };

})(jQuery);
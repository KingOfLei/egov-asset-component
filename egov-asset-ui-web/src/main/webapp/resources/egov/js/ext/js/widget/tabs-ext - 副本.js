/**
 * 对app-tabs的扩展将横向tabs变为纵向
 */
define([
    "app/core/app-core",
    "resources/egov/js/ext/js/base/ext-base",
    "resources/egov/js/ext/js/util/common-util",
],function($A,ExtBase,CUtil) {

    var TabsExtension = ExtBase.extend({
        //纵向tabs父容器ID
        tabsId:'',
        tabs:undefined,
        //原tabs控件所在的父级panel的id ，滚动翻页监听用
        srcTabsParentId:'',
        isOnScroll:false,

        //初始化
        init:function(config){
            var _self = this;

            if(_self.tabsId){
                _self.tabsId = '#'+_self.tabsId;
            }
            if(!_self.width){
                _self.width = 100;
            }

            return _self;
        },

        beforeRender: function () {
            var _self = this;
            //隐藏组件tab
            _self.$component.find('>ul').hide();

            _self.$element = $('<div class="app-layout" data-options="{fit:true}" style="overflow:hidden;" tabsFor="'+_self.id+'">'+
                '<div id="'+_self.id+'WestId" tabsFor="'+_self.id+'West" region="west" data-options="{split:false,border:false}" style="width:'+_self.width+'px; overflow:hidden;"></div>'+
                '<div id="'+_self.id+'CenterId" tabsFor="'+_self.id+'Center" region="center" data-options="{split:false,border:false}" style="overflow:hidden;overflow-y: auto;"></div>'+
                '</div>');
            _self.$center = _self.$element.find('[tabsFor="'+_self.id+'Center"]');
            _self.$west = _self.$element.find('[tabsFor="'+_self.id+'West"]')
        },

        render: function () {

            var _self = this;
            //标签组装为纵向
            var tabTemplate = '<div class="_custom_tabs"><ul></ul></div>';
            _self.tabs = $(tabTemplate);
            _self.$component.find('>ul li').each(function(){
                var $ulLi = $(this);
                var $item = $('<li></li>');
                $item.html($ulLi.html());
                $item.find('a').each(function (index,item) {
                    var $a = $(item);
                    $a.prop('href',$a.attr('data-target'));
                });

                if($ulLi.hasClass('active')){
                    $item.addClass('hover');
                }
                _self.tabs.find('ul').append($item);
            });

            if(CUtil.isNullStr(_self.tabsId)){
                _self.tabsId = '#'+_self.id+'WestId';
                _self.$west.append(_self.tabs);
                //将tabs父容器布局放tabs控件屁股后面，再将tabs控件插入到内容中。
                _self.$component.after(_self.$element);
                _self.$component.parent().find(_self.$element).layout();
                _self.$component.appendTo(_self.$center);

            }else{
                if(CUtil.isNotNullStr(_self.srcTabsParentId)){
                    _self.$center = $('#'+_self.srcTabsParentId);
                }else{
                    _self.$center = _self.$component.parent();
                }
                $(_self.tabsId).append(_self.tabs);
            }

            //如果流动的话
            if(_self.isFlow){
                //删除相应tabContent样式
                _self.$component.find('.tab-content>div').removeClass('tab-pane layout-body inactive');
            }

            //定位方法只调用一次
            getPositions.apply(_self);

        },

        bind: function () {
            var _self = this;


            if(_self.isFlow){
                // _self.bindInterval();
                _self.scrollPage(true);
                // $('#'+_self.id+'WestId ._custom_tabs ul').onePageNav({currentClass:'hover',win:$('#'+_self.id+'CenterId')});
                //单击标签事件
                $(_self.tabsId+' ul li a').on('click',function(e){

                    var $a = $(this);
                    var target = $a.attr('data-target');
                    var $target = $(target);
                    if($a.parent().hasClass('hover')){
                        return;
                    }


                    var topPos = _self.sectionsById[$target.prop('id')].start;

                    if(_self.$center.is(':animated')){
                        return;
                    }

                    //取消滚动监听
                    _self.unbindScroll();
                    // _self.unbindInterval()
                    // _self.$center.scrollTo(target,400);
                    _self.$center.animate(
                        {
                            scrollTop:topPos
                        },400,function () {
                            _self.scrollPage(true);
                            // _self.bindInterval();
                        });
                    //样式
                    _self.adjustNavSel($(target).prop('id'));

                });

            }else{
                //单击标签事件
                $(_self.tabsId+' ul li a').on('click',function(e){
                    var $a = $(this);
                    _self.show($a.attr('data-target'));
                });
            }

            _self.scrollPage(_self.isScroll||_self.isFlow);

        },


        //获取当前页
        getCurrPanel: function() {
            var _self = this;
            var $panel = _self.$component.find(".tab-pane.active");
            return $panel;
        },
        //激活后的回调


        /*
         * 切换标签
         * see app/weights/tab/app-tab.js
         * TAB DATA-API
         * ============
         * $(document).on(
         *
         */
        show:function(target){
            var _self = this;

            if(typeof target !== 'string'){
                return;
            }

            _self.adjustNavSel($(target).prop('id'));


            var selector = target;
            if (!selector)
                return;
            if (!selector) {
                selector = $this.attr('href');
                selector = selector
                    && selector.replace(/.*(?=#[^\s]*$)/, ''); //strip for ie7
            }
            var $target = $A(selector);
            if ($target.length == 0)
                return;
            _self.$component.appTabs('show', $target);
            return _self;
        },

        /**
         * 调整导航栏选中
         * @param target
         */
        adjustNavSel:function (target) {
            var _self = this;
            _self.activeTarget = target;
            //样式删除
            _self.tabs.find('ul li').removeClass('hover');
            //樣式添加
            $('a[data-target$="#'+target+'"]').parent().addClass('hover');
        },

        //滚动翻页
        scrollPage:function(isOnScroll){
            var _self = this;
            var $panel = '';

            //导航滚动
            if(_self.isFlow){
                _self.$center.on('scroll',function(e){
                    e = e || window.event;

                    _self.didScroll = true;
                    var scrollTop = _self.$center.scrollTop();
                    var target = _self.getSection(scrollTop);
                    if(!target){
                        return;
                    }
                    _self.adjustNavSel(target);
                });
            }

            if(CUtil.isNotNullStr(_self.srcTabsParentId)){
                $panel = $('#'+_self.srcTabsParentId);
            }else{
                $panel = _self.$component.parent();
            }
            var preScroll = {scrollTop:-1,scrollSum:-1,counter:0};
            if(isOnScroll){
                if(!_self.isOnScroll&&!_self.isFlow){

                    //firefox DOMMouseScroll ,other mousewheel
                    $panel.on('mousewheel DOMMouseScroll',function(e){
                        var $panelHeight = $panel.get(0).clientHeight;
                        var $panelScrollHeight = $panel.get(0).scrollHeight;


                        e = e || window.event;
                        var scrollTop = $panel.scrollTop();
                        var scrollSum = Math.ceil(scrollTop+$panelHeight);

                        var $liHover = _self.tabs.find('ul li.hover');
                        var isPage = false;


                        var wDelta = e.wheelDelta?e.wheelDelta:e.originalEvent.wheelDelta < 0 ? 'down' : 'up';
                        switch(wDelta){
                            case 'down':
                                if(scrollSum >= $panelScrollHeight && preScroll.counter > 0) {
                                    var $liNext = $liHover.next();
                                    if($liNext.length>0){
                                        _self.show($liNext.find('a').attr('data-target'));
                                        isPage = true;
                                    }
                                }else if(scrollSum >= $panelScrollHeight){
                                    preScroll.counter = preScroll.counter + 1;
                                }
                                break;

                            case 'up':

                                if( (scrollTop===0  || (scrollTop===0 && scrollSum>=$panelScrollHeight) ) && preScroll.counter > 0){
                                    var $liPre = $liHover.prev();
                                    if($liPre.length>0){
                                        _self.show($liPre.find('a').attr('data-target'));
                                        isPage = true;
                                    }
                                }else if(scrollTop===0){
                                    preScroll.counter = preScroll.counter + 1;
                                }

                                break;

                        }
                        //记录这次滚动的数据
                        if(!isPage){
                            preScroll.scrollTop = scrollTop;
                            preScroll.scrollSum = scrollSum;
                        }else{
                            preScroll.scrollTop = -1;
                            preScroll.scrollSum = -1;
                            preScroll.counter = 0;
                        }

                    })


                    _self.isOnScroll = true;
                }
            }else{
                $panel.unbind('scroll');
            }

            return _self;
        },
        unbind:function () {
            this.unbindScroll();
            return this;
        },
        unbindScroll:function () {
            this.$center.unbind('scroll');
        },
        bindInterval: function() {
            var _self = this;
            var docHeight;

            _self.t = setInterval(function() {
                var scrollTop = _self.$center.scrollTop();
                //If it was scrolled
                if(!_self.didScroll) {
                    return;
                }
                var target = _self.getSection(scrollTop);
                if(!target){
                    return;
                }
                _self.adjustNavSel(target);
            }, 250);
        },
        unbindInterval: function() {
            clearInterval(this.t);
        },

        /**
         * 根据滚动条位置返回相应 nav对象
         * @param scrollTop
         * @returns {undefined}
         */
        getSection: function(scrollTop) {
            //避免高分屏小数
            scrollTop = Math.ceil(scrollTop)
            var _self = this;
            var sections = _self.sections;
            var scrollHeight = _self.$center[0].scrollHeight;
            var maxScroll    = scrollHeight - _self.$center.height()
            var activeTarget = _self.activeTarget;
            var i;
            var centerOffset = _self.$center.offset();

            var returnValue = undefined;

            if (scrollTop >= maxScroll) {
                return activeTarget != (i = sections[sections.length - 1]) && i.target;
            }

            for (i = sections.length; i--;) {
                activeTarget != sections[i]
                && scrollTop >= sections[i].offsetTop
                && (sections[i + 1] === undefined || scrollTop < sections[i + 1].offsetTop)
                && (returnValue = sections[i].target);
            }

            return returnValue;
        }
    });


    /**
     * 计算每个tabPanel的位置区间 offsetTop 到 offsetTop + tabPanel.height
     */
    var getPositions = function() {
        var _self = this;

        _self.sections = [];
        _self.sectionsById = [];
        var sections = _self.sections;
        var sectionsById = _self.sectionsById;



        _self.$component.find('.tab-content>div').each(function(index,item){
            var $item = $(item);
            // var topPos = $item.offset().top;
            var offsetTop = $item[0].offsetTop;
            var data = {
                target:$item.prop('id'),
                offsetTop:offsetTop,
                start:offsetTop
            };
            sections[index] = data;
            sectionsById[data.target] = data;
        })

        if(sections.length>0){
            sections[0].start = 0;
        }
        return sections;
    };

    return TabsExtension;
});

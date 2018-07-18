/**
 * Created by qiu.yong on 2017/3/2.
 */

define([
    "resources/egov/js/ext/js/base/ext-base"
], function (ExtBase) {
    var TimeShaft = ExtBase.extend({
        defaultOptions: {
            scrollSpeed: 340,
            showCount: 3,
            lineSpeed: 940,
            fadeInSpeed: 240,
            fadeOutSpeed: 240,
            offset: 120,
            fadeDistance: 190,
            easing: 'swing'
        },
        init: function () {
            var _options = {};
            $.extend(true, _options, this.defaultOptions);
            $.extend(_options, this);
            $.extend(this, _options);
        },
        beforeRender: function () {
            var self = this;
            //控件元素生成
            var $component = self.$component;
            $component.addClass('history');
            var $box = $('<div class="history-box">');
            var $dateBox = $('<div class="history-date">');
            var $head = $('<h2 class="first" style="position: relative;"><a></a><span class="clock-text"></span></h2>');
            var $list = $('<ul class="list">');
            var $up = $('<div class="up-btn">');
            var $down = $('<div class="down-btn">');

            self.$box = $box;
            self.$dateBox = $dateBox;
            self.$head = $head;
            self.$list = $list;
            self.$up = $up;
            self.$down = $down;

            $component.append($box)
            $box.append($dateBox).append($up).append($down);
            $dateBox.append($head).append($list);
        },
        render: function () {
            var self = this;
            var $list = self.$list;
            var distance = self.fadeDistance;
            var getView = self.getView;
            if (!$.isFunction(getView)) {
                getView = function () {
                }
            }
            var left = + self.$down.css('left').replace('px','');
            self.$down.css({'left':(left+2)});
            $list.height(0);
            var li = '<li class="bounce-in-down">\
            <h3></h3>\
            <dl>\
                <dt></dt><span></span>\
            </dl>\
        </li>';
            $(self.data).each(function (i, item) {
                var $li = $(li);
                var $date = $li.find('h3');
                var $title = $li.find('dl dt');
                var $content = $li.find('dl span');
                var $dl = $li.find('dl');
                $date.css({left: -distance});
                $dl.css({right: -distance});
                $li.data('data', item);
                $li.hide();
                $list.append($li);
                getView($date, $title, $content, item);

            });
        },
        afterRender: function () {
            var self = this;
            var $list = self.$list;

            if(self.data.length==0){
                self.$up.hide();
                self.$down.hide();
                self.$list.remove();
                var $noData  = $('<div class="no-data">').hide();
                self.$noData = $noData;
                self.$component.append($noData);
                $noData.show();
                $noData.animate({marginLeft: 276}, 400, self.easing,function () {
                    $noData.animate({fontSize: 18}, 300, self.easing);
                });
                $noData.text(!!self.noDataTip?self.noDataTip:'no data！');
                return;
            }

            $list.animate({height: (self.showCount * self.offset)}, self.lineSpeed, self.easing, function () {
                var $lis = self.getCurrentItems();
                var showIndex = 0;
                var size = $lis.length;
                //第一次加载动漫
                function showFun($obj) {
                    self.fadeIn($lis[showIndex], function () {
                        showIndex++;
                        if (showIndex < size) {
                            showFun($lis[showIndex]);
                        } else {
                            $($list.find('>li:hidden')).each(function (i, li) {
                                var $li = $(li);
                                var $date = $li.find('h3');
                                var $dl = $li.find('dl');
                                $date.css({left: 0});
                                $dl.css({right: 0});
                            }).show();
                        }
                    });
                }

                showFun($lis[showIndex]);
            });
        },
        bind: function () {
            var self = this;
            var $list = self.$list;
            var $first = $list.find('>li').eq(0);
            var offset = self.offset;
            var size = self.data.length;
            var top = offset * (self.showCount >= 1 ? self.showCount - 1 : self.showCount);
            var bottom = offset * (size >= 1 ? size - 1 : size);

            //firefox DOMMouseScroll ,other mousewheel
            self.$component.on('mousewheel DOMMouseScroll', function (e) {
                if ($first.length <= 0) {
                    return;
                }
                e = e || window.event;
                var position = e.wheelDelta ? e.wheelDelta : e.originalEvent.wheelDelta < 0 ? 'down' : 'up';

                var currentItems = self.getCurrentItems();
                switch (position) {
                    case 'up':
                        self.downScroll();
                        break;
                    case 'down':
                        self.upScroll();
                        break;
                }

            });
            self.$up.on('click',function () {
                self.downScroll();
            });
            self.$down.on('click',function () {
                self.upScroll();
            });
        },
        upScroll:function () {
            var self = this;
            var $list = self.$list;
            var $first = $list.find('>li').eq(0);
            var offset = self.offset;
            var size = self.data.length;
            var top = offset * (self.showCount >= 1 ? self.showCount - 1 : self.showCount);
            var bottom = offset * (size >= 1 ? size - 1 : size);
            var marginTop = +$first.css('marginTop').replace('px', '');
            self.scroll(function () {
                $first.animate({
                    marginTop: (marginTop - offset) + 'px'
                }, self.scrollSpeed, self.easing, function () {
                });


            }, 'up', marginTop);
        },
        downScroll:function () {
            var self = this;
            var $list = self.$list;
            var $first = $list.find('>li').eq(0);
            var offset = self.offset;
            var size = self.data.length;
            var top = offset * (self.showCount >= 1 ? self.showCount - 1 : self.showCount);
            var bottom = offset * (size >= 1 ? size - 1 : size);
            var marginTop = +$first.css('marginTop').replace('px', '');
            self.scroll(function () {
                $first.animate({
                    marginTop: (marginTop + offset) + 'px'
                }, self.scrollSpeed, self.easing, function () {

                });


            }, 'down', marginTop);
        },
        //是否滚动
        scroll:function(f, p, marginTop) {
            var self = this;
            var $list = self.$list;
            var $first = $list.find('>li').eq(0);
            var offset = self.offset;
            var size = self.data.length;
            var top = offset * -(size >= 1 ? size - 1 : size);
            var bottom = offset * (self.showCount >= 1 ? self.showCount - 1 : self.showCount);
            if (!$first.is(':animated')) {
                if (p == 'up' && marginTop == top) {
                    return;
                } else if (p === 'down' && marginTop == bottom) {
                    return;
                }
                f();
            }
        },
        fadeIn: function ($li, fn) {
            var self = this;
            $li.show();
            var $date = $li.find('h3');
            var $dl = $li.find('dl');
            var speed = self.fadeInSpeed;
            var distance = self.fadeDistance;
            $date.animate({left: 0}, speed, self.easing);
            $dl.animate({right: 0}, speed, self.easing, fn);
        },
        fadeOut: function ($li, fn) {
            var self = this;
            var $date = $li.find('h3');
            var $dl = $li.find('dl');
            var speed = self.fadeInSpeed;
            var distance = self.fadeDistance;

            $date.animate({left: -distance}, speed, self.easing);
            $dl.animate({right: -distance}, speed, self.easing, function () {
                if ($.isFunction(fn)) {
                    fn();
                }
                $li.hide();
            });
        },
        getCurrentItems: function () {
            var self = this;
            var $list = self.$list;
            var $lis = $list.find('>li');
            var $first = $list.find('>li').eq(0);
            var marginTop = +$first.css('marginTop').replace('px', '');
            var index = (marginTop > 0 ? marginTop : -marginTop) / self.offset;
            var a = [];
            var end = index + self.showCount;
            for (var i = index; i < end; i++) {
                var $li = $lis.eq(i);
                if ($li.length == 0) {
                    break;
                }
                a.push($li);
            }
            return a;
        }
    });
    return TimeShaft;
});


/**
 * Created by qiu.yong on 2017/3/13.
 */

define(["resources/egov/js/ext/js/util/common-util"], function () {
        /**
         * Created by qiu.yong on 2017/3/13.
         */
        var ImgWaterFall = function (options) {
            $.extend(this, options);
            this.init();
            this.beforeRender();
            this.render();
            this.bind();
        };

        ImgWaterFall.prototype = {
            init: function () {

            },
            beforeRender: function () {
                var self = this;
                var $content = $('<div class="img-pick">');
                var $ul = $('<ul>');
                $content.append($ul);
                self.$component.append($content);

                self.$content = $content;
                self.$ul = $ul;
            },
            render: function () {
                var self = this;
                var $ul = self.$ul;
                var getView = $.isFunction(self.getView) ? self.getView : function () {
                    };
                var li = '<li class="img-item">\
                    <div class="img-item-bd">\
                        <i class="del"></i>\
                        <span class="img-span" data-src=""></span>\
                        <span class="check-content">\
                            <label class="checkbox-label">\
                                <span class="lbl-content" title=""></span>\
                            </label>\
                        </span>\
                    </div>\
            </li>';
                $(self.data).each(function (i, item) {
                    var $li = $(li);
                    $li.data('data', item);

                    getView.call(self, {
                        img: $li.find('.img-span'),
                        text: $li.find('.lbl-content'),
                        del: $li.find('.del')
                    }, item);

                    $ul.append($li);
                });
            },
            afterRender: function () {

            },
            bind: function () {
                var self = this;
                var delClick = $.isFunction(self.delClick) ? self.delClick : function () {
                    };
                var imgClick = $.isFunction(self.imgClick) ? self.imgClick : function () {
                    };
                self.$ul.on('click', '.del', function () {
                    var $li = $(this).closest('li');
                    delClick($li, $li.data('data'));
                });
                self.$ul.on('click', '.img-span', function () {
                    var $li = $(this).closest('li');
                    imgClick($li, $li.data('data'));
                });
            },
            setImage: function ($img, url) {
                $img.prop('src', url);
            },
            setBackgroundImage: function ($img, url) {
                $img.css({'background-image': 'url("' + url + '")'});
            }
        };
        return ImgWaterFall;
    }
);

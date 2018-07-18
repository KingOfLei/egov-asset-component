/**
 * tips 扩展
 *
 * 使用方法
 * $(selector).tip('content',{
 *      id:'id-1'
 *      position:'top'
 * })
 * @example

 var $te = $('id');

 $te.tip('default top left',{
                id:'top',
                position: 'top left'
            });


 *
 */
define(['resources/egov/js/ext/js/lib/jquery.poshytip'],function () {
    ;(function ($) {

        //选中元素扩展
        $.fn.extend({
            tip: function (content, options) {
                var self = this;

                if (!!!options) {
                    options = {};
                }
                options.content = content;
                //tip位置初始化
                var position = options.position;
                if(!!!position){
                    position = 'top';
                }

                //默认配置
                var defStyleConfig = {
                    alignTo:'target',
                    showTimeout:100,
                    hideTimeout:150
                };
                $.extend(defStyleConfig,options);
                options = defStyleConfig;

                if(position.indexOf('top')!=-1){
                    options.alignX = 'center';
                    options.alignY = 'top';
                }else if(position.indexOf('bottom')!=-1){
                    options.alignX = 'center';
                    options.alignY = 'bottom';
                }

                var right = 'inner-right';
                var left = 'inner-left';

                if(position.indexOf('center')!=-1){
                    options.alignY = 'center';
                    right = 'right';
                    left = 'left';
                }


                if(position.indexOf('right')!=-1){
                    options.alignX = right;
                }else if(position.indexOf('left')!=-1){
                    options.alignX = left;
                }



                //创建tips对象
                self.poshytip(options);
                return self;
            }
        });

    })(jQuery);
});

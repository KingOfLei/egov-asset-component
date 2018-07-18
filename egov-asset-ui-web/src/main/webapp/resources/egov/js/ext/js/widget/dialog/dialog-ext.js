/**
 *  扩展对话框自动覆盖内容区域
 *  uiExtend({
 *  type:'dialog',
 *  options:{}//对话框配置
 *  })
 */
define(["resources/egov/js/ext/js/base/ext-base",
    "resources/egov/js/ext/js/util/common-util",
    "resources/egov/js/ext/js/widget/dialog/dialog-switch-page",
    "resources/egov/js/ext/js/widget/dialog/dialog-attach"
], function (ExtBase, util, DialogSwitch,DialogAttach) {

    var Dialog = ExtBase.extend({
        Implements: [DialogSwitch,DialogAttach],
        init: function () {
            var self = this;
            var options = self.options;
            //显示之后调用
            var opAfterShow = options.afterShow;
            var closeCallback = options.closeCallback;
            var beforeShow = options.beforShow;

            closeCallback = util.getFunction(closeCallback);
            beforeShow = util.getFunction(beforeShow);
            opAfterShow = util.getFunction(opAfterShow);

            options.closeCallback = function () {
                //TODO 业务逻辑代码
                $.panelSlider.destroyAll();
                options.attach&&self.destroyAttach();

                // 翻页插件弹框关闭后执行事件 
	                // 销毁翻页按钮，同时将参数初始化
	                $('.switchBtns').remove();
	                switchPageNewRowIndex = undefined;
	                if(options.switchPageParams){
		                options.switchPageParams.exist = false;
	                };
                
                // 关闭回调函数
                closeCallback();
            };

            options.beforShow = function ($dlg) {
                beforeShow();
            };

            options.afterShow = function () {
                // 根据传入参数判断是否需要显示切换按钮
                if (options.switchPageParams && options.switchPageParams.showSwitchBtn) {
                    if (!options.switchPageParams.switchBtnPos || (options.switchPageParams.switchBtnPos !== 'top' && options.switchPageParams.switchBtnPos !== 'right')) {
                        $('#' + options.dialogId).css('top', '0px');
                    }
                    ;
                    // 弹框页面加载完成后执行 (dialog-switch-page.js)
                    self.initPage(self);
                }
                if(options.attach){
                    self.initAttach();
                }
                opAfterShow();
            };

            if (options.drawable !== true) {
                options.drawable = false;
            }
            var $panel = $("body");

            // 判断弹框需减少的高度
            var reduceHeight = 0;
            if (options.switchPageParams && options.switchPageParams.showSwitchBtn) {
                if (!options.switchPageParams.switchBtnPos || (options.switchPageParams.switchBtnPos !== 'top' && options.switchPageParams.switchBtnPos !== 'right')) {
                    // 高度暂时写死  页面未加载完成无法动态获取按钮图片高度
                    reduceHeight = 50;
                }
                ;
            }
            ;
            //附件高度
            options.attach&&(reduceHeight+=self.attachHeight);
            options.height = $panel.height() - reduceHeight;
            options.width = $(window).width();
            options.hasheader = true;
            //调用平台显示对话框
            $.openModalDialog(options)
        }
    });

    return Dialog;
});

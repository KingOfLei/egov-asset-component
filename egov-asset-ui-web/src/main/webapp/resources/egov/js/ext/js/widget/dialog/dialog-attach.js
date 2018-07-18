/**
 * Created by qiu.yong on 2017/4/1.
 */
define(
    [
        "resources/egov/js/ext/js/base/ext-base",
        "resources/egov/js/ext/js/util/common-util"
    ],
    function (ExtBase, util) {
        var DialogAttach = ExtBase.extend({
            attachHeight:152,
            initAttach: function () {
                var $dlg = $A.getCurrentDialog();
                $dlg.css('top',0);
                var zIndex = $dlg.css('z-index');
                var self = this;
                self.$attach = $('<div id="common-attachment-div" class="common-attachment-div"></div>');
                var $attach = self.$attach;

                var $title = $('<div id="common-attachment-div-title-bar" class="common-attachment-div-title-bar">' );
                self.$title = $title;
                $title.text('附件');
                // $attach.css('z-index',zIndex);
                // $title.css('z-index',zIndex);
                // $('body').append($attach);
                // $('body').append($title);
                 
                $dlg.append($attach);
                $dlg.append($title);
            },
            //摧毁附件
            destroyAttach:function(){
                var self = this;
                $.saveUploadRemark();
                self.$attach.remove();
                self.$title.remove();
            }
        });
        return DialogAttach;
    }
);
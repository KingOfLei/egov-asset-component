/**
 *  提示信息
 */
define(["resources/egov/js/ext/js/base/ext-base",
    "resources/egov/js/ext/js/util/common-util",
    ],function(ExtBase,util) {

	var Prompt = ExtBase.extend({
	    init:function () {
	        if(!this.msgType){
                this.success(this.msg);
            }else {
                this.error(this.msg);
            }
        },
        show:function(message,type){
	        var _self = this;
            var $p = $A.messager._open('info',message,[]);
            var $icon = $p.find('div.alertContent > i');
            if(type===$A.messager._types.correct){
                var css = $icon.css('background');
                $icon.css('background',css.replace('warn.png"','ok.png"'))
            }else {
                $icon.removeClass();
                $icon.addClass(type);
            }
		},
        success:function (message) {
            this.show(message,$A.messager._types.correct);
        },
        error:function (message) {
            this.show(message,$A.messager._types.error);
        }
	});

	return Prompt;
});

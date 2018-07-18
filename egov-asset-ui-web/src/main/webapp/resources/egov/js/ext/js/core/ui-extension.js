/**

 *
 */
define(["app/core/app-class",
        "resources/egov/js/ext/js/config/url-config",
        "resources/egov/js/common/utils/Tools",
        "resources/egov/js/ext/js/core/crumb",
        "resources/egov/js/common/utils/assetMessageUtils",
        "resources/egov/js/ext/js/widget/cookie-ext",
        "resources/egov/js/ext/js/widget/panel-slider",
        "resources/egov/js/ext/js/util/math",
        "resources/egov/js/ext/js/widget/open-tip.js"
    ]
    , function(Class,Config,Tools,Crumb,$msg,CookieExt){

    //TODO 全局控制
    function globalFun() {
        $('body').on('click',function(e){
            var $ele = $(e.target);
            var $btn = $ele.closest('.app-button');
            if(!$btn.is('.app-button')){
                return;
            };
            $btn.data('button');
            if(!$btn.length){
                return;
            }

            var $buttons = $('.app-button:not(".l-btn-disabled"):not(".l-btn-plain-disabled"):not(".custom-disable-btn")');

            $buttons.button('disable');
            setTimeout(function(){
                $buttons.button('enable');
                // $buttons.each(function (i,btn) {
                //    var $btn = $(btn);
                //    if($btn.is('.l-btn-disabled')||$btn.is('.l-btn-plain-disabled')){
                //        return;
                //    }
                //    $btn.button('enable');
                // });
            },1400);
        });
        $.extend({
           "btnCustomDisable":function () {
               $(arguments).each(function(i,item){
                   var $ele = $('#'+item);
                   $ele.addClass('custom-disable-btn');
               });
           }
        });
    };
        
        
    function UIExtension(win){
	    //jq 扩展
        $ = win.$;
	    Crumb(win.$);
        Tools(win.$);
        win.$assetMsg = $msg;win.$A.assetMsg = win.$assetMsg;
        win.$ismgrorg = undefined;
        win.$currentorg = undefined;
        win.$user = undefined;
	    win.$.extend({
            'uiExtend':function(config){
                var _self = $(document);
                var defConfig = extendConfig.apply(_self,[config]);
                var jsModule = '';
                //类型必须存在
                if(defConfig.type===''){
                    return undefined;
                }
                //根据控件类型初始化控件
                switch(defConfig.type){
                    case 'dialog':
                        jsModule = 'dialog/dialog-ext';
                        break;
                    case 'download':
                        jsModule = 'upload/upload-file-download';
                        break;
                    case 'prompt':
                        jsModule = 'dialog/prompt';
                        break;
                    case 'grid-ext':
                    	jsModule = 'grid/grid-ext';
                    	break;
                    default:
                        jsModule = defConfig.type;
                }
                if(jsModule!==''){
                    requireComponent(jsModule,defConfig);
                }

                return _self;
            }
        });

	    win.$.fn.extend({
            /**
             * 获取控件扩展对象
             */
            'extension':function (){
                return this.data('$_ui_extensions');
            },

            /**
             *  编码规范不统一，无法自动匹配控件类型
             * @param config
             * @returns {extensions}
             */
            'uiExtend':function(config){
                var _self = this;
                var defConfig = extendConfig.apply(_self,[config]);
                var jsModule = '';
                //类型必须存在
                if(defConfig.type===''){
                    return undefined;
                }
                //根据控件类型初始化控件
                switch(defConfig.type){
                    case 'xgrid':
                        jsModule = 'xgrid-ext';
                        break;
                    case 'tabs':
                        jsModule = 'tabs-ext';
                        break;
                    case 'upload':
                        jsModule = 'upload/upload-ext';
                        break;
                    case 'flowstep':
                        jsModule = 'flowstep';
                        break;
                    case 'listflow':
                        jsModule = 'listflow';
                        break;
                    default:
                        jsModule = defConfig.type;
                }
                if(jsModule!==''){
                    requireComponent(jsModule,defConfig);
                }


                return _self;
            }

        });

	    //window 扩展
        (function () {
            win.cookiext = CookieExt;
        })();

        globalFun();
	};




    /**
	 * 控件配置默认值设置
     * @param config
     * @returns {*}
     */
	function extendConfig(config){
        var _self = this;

        if(!isObject(config)){
            return undefined;
        }
        var defConfig = {
            id:_self.prop('id'),
            $component:_self,
            completed:function () {}
        };

        $.extend(true,defConfig,config);
        return defConfig;
	};

    /**
	 * 获取控件模块，并初始化相应控件
     * @param str
     * @param config
     */
	function requireComponent(str,config){
        require([Config.widgetBase+str],function (Component) {
            var component = new Component(config);
        });
	}




    var toString = Object.prototype.toString;
    function isFunction(val) {
        return toString.call(val) === '[object Function]';
    };

    function isObject(val) {
        return toString.call(val) === '[object Object]';
    };

    function isUndefined(val){
        return (typeof val === 'undefined')
    };

	return UIExtension;
});

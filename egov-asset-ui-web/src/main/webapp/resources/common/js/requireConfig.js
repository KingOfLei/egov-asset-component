require.config({
    urlArgs: 'v=' + _VERSION,
    baseUrl: _contextPath + "/resources/common/js/"
    , shim: {

        "base/json2": {
            exports: "JSON"
        },
        "base/dotpl-js": {
            exports: "dotpl"
        },
        "bootstrap/bootstrap": {
            deps: ["jquery"],
            exports: '$.fn.popover'
        },
        "jquery/jquery.validate": {
            deps: ["jquery"],
            exports: '$'
        },
        "jquery/jquery.history": {
            deps: ["jquery", "base/json2"],
            exports: '$'
        },
        "jquery/jquery.jqgrid": {
            deps: ["jquery"],
            exports: '$'
        },
        "jquery/jquery.resize": {
            deps: ["jquery"],
            exports: '$'
        },
        "jquery/jquery.metadata": {
            deps: ["jquery"],
            exports: '$'
        },
        "jquery/jquery.metadata": {
            deps: ["jquery"],
            exports: '$'
        },
        "jquery/jquery.upload": {
            deps: ["jquery"],
            exports: '$'
        },
        "resources/egov/js/ext/js/lib/jquery.artDialog": {
            deps: ["jquery"],
            exports: '$'
        },
        "resources/egov/js/ext/js/widget/panel-slider": {
            deps: ["jquery"],
            exports: '$'
        },
        "resources/egov/js/ext/js/util/math": {
            deps: ["jquery"],
            exports: '$'
        },
        "resources/egov/js/ext/js/lib/jquery-ui-tooltip": {
            deps: ["jquery"],
            exports: '$'
        },
        "resources/egov/js/ext/js/lib/jquery.poshytip": {
            deps: ["jquery"],
            exports: '$'
        },
        "resources/egov/js/ext/js/lib/jquery.filepreview": {
            deps: ["jquery"],
            exports: '$'
        }
    }
    , paths: {
        resources: _contextPath + "/resources/"
    }
});
/**
 * establish history variables
 */
var History = window.History; // Note: We are using a capital H instead of a lower h
var localeFile = "app/widgets/app-lang_zh_CN";

var initJsList = [
    "resources/egov/js/common/thirdparty/ueditor/third-party/zeroclipboard/ZeroClipboard.js",
    "resources/egov/js/ext/js/lib/jquery.artDialog",
    "resources/egov/js/ext/js/core/ui-extension",
    "app/core/app-jquery", "app/core/app-core",
    "base/dotpl-js", "app/app-funcbase",
    "app/widgets/window/app-messager",
    "app/util/app-utils",
    "app/widgets/window/app-dialog",
    "app/core/app-register",
    "app/core/app-main",
    "app/widgets/form/app-validate",
    "base/template"
    ];

if (initAppJSPath) {
    initJsList.push(initAppJSPath)
}


require(initJsList, function (zeroClipboard,artDialog,UIExtension, $, App, template, func, $messager, $utils, dialog, register, appmain, validate, extTemplate, appJSObj) {
    window.jQuery = $;
    window.$ = $;
    window['ZeroClipboard']=zeroClipboard;
    $.browser = {};
    $.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
    $.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
    $.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
    $.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());

    //当焦点在input中时，用退格键删除文字不会造成浏览器后退的问题
    $(document).on("keydown", function (e) {
        if (e.keyCode == App.keyCode["BACKSPACE"]) {
            var $target = $(e.target);
            if ($target.is("input,textarea")) {
                if ($target.val() == "") {
                    return false;
                } else if ($target.attr("readonly")) {
                    return false;
                }
            }
        }
    });

    window.$template = function (render, vars) {
        return template.applyTpl(render, vars);
    };
    window.funcs = window.$funcs = func;
    window.$messager = $messager;
    window.$utils = $utils;
    /**
     * 增加启动方法
     */
    window.$app = window.$A = window.$a = App;
    $A.setContextPath(_contextPath);


    //UI控件扩展
    UIExtension(window);
    //TODO grid 翻页项配置
    {
        App.options.appDefaults.Grid.pageList = [15, 30, 50, 100, 200, 500];
        App.options.appDefaults.Grid.pager = 'up';
        App.options.appDefaults.Grid.pageSize = 30;
    }
    if (appJSObj && appJSObj.init) {
        appJSObj.init();


    }


    App.boot();

});
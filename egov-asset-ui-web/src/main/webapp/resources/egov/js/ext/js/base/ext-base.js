/**
 * Created by qiu.yong on 2016/12/5.
 */
define(["app/core/app-class",
    "app/core/app-attribute",
    "app/core/app-jquery",
    "resources/egov/js/ext/js/util/common-util"],function(Class,Attribute,$A,util){

    var ExtBase = Class.create({
        Implements: [Attribute],
        initialize: function(config) {
            //TODO 配置验证
            if(typeof config !== "object")return;

            // 初始化配置
            var config = $A.extend(true,{},config);
            $A.extend(this,config)

            var eventId = util.nextId();
            //注册completed回调
            this.$component.one('_completed.ui_extension'+eventId,config.completed);

            this.init();
            this.beforeRender();
            this.render(config);
            this.afterRender();
            this.bind();
            this.setup();

            //保存扩展对象
            config.$component.data('$_ui_extensions',this);

            //初始化完成回调
            this.$component.trigger('_completed.ui_extension'+eventId,this);

        },

        /**
         *
         *
         */
        init: function () {},

        /**
         * 提供给子类覆盖的初始化方法
         * 在 bin() 之后执行
         */
        setup: function() {},

        /**
         * 渲染之前
         */
        beforeRender: function(){},

        /**
         * 渲染控件
         *
         */
        render:function(){},


        /**
         *  渲染之后
         */
        afterRender: function(){},

        /**
         * 绑定事件
         */
        bind: function(){},

        /**
         * 解除事件绑定
         */
        unbind: function(){},

        /**
         *  摧毁
         */
        destroy: function(){},

        /**
         *
         */
        extConfig: function (src,dist) {
            return $A.extend(true,src,dist);
        }
    });





    return ExtBase;


});
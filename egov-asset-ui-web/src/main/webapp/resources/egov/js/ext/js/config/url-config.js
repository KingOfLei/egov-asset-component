/**
 * Created by qiu.yong on 2016/12/12.
 */
define(["app/core/app-class",
    "app/core/app-attribute",
    "app/core/app-jquery"],function(Class,Attribute,$A){

    var Config = Class.create({
        Implements: [Attribute],
        initialize: function (config) {
            $A.extend(this,config);
            this.base = 'resources/egov/js/ext/js/';

            this.extBase = this.base+'base/ext-base';

            this.utilBase = this.base+'util/';

            this.widgetBase = this.base+'widget/';
        }



    });

    var config = new Config();

    return config;
});
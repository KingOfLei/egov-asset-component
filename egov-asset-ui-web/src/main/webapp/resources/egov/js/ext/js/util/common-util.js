/**
 * Created by qiu.yong on 2016/12/6.
 */
define([], function () {
        var idno = 0;

        function CommonUtil() {

        }

        var toString = Object.prototype.toString;
        CommonUtil.prototype = {
            isFunction: function (val) {
                return toString.call(val) === '[object Function]';
            },

            isObject: function (val) {
                return toString.call(val) === '[object Object]';
            },

            isUndefined: function (val) {
                return (typeof val === 'undefined')
            },

            /**
             * String 工具类
             * @param val
             * @returns {boolean}
             */
            isNullStr: function (val) {
                return (val === '' || isUndefined(val))
            },
            isNotNullStr: function (val) {
                return (val !== '' && !isUndefined(val))
            },
            nextId: function () {
                return ++idno;
            },
            getFunction:function (fn) {
                return $.isFunction(fn)?fn:$.noop;
            }
        };

        return new CommonUtil();
    }
);
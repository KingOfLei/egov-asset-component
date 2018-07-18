/**
 *  cooki
 */
define(["resources/egov/js/ext/js/lib/jquery.cookie"],function($cookie) {
	var CookieExt = function () {

    };
	CookieExt.prototype={
        setOneDay:function (key,value) {
            this.set(key,value,{expires:1})
        },
        setOneWeek:function (key,value) {
            this.set(key,value,{expires:7})
        },
        setOneMonth:function (key,value) {
            this.set(key,value,{expires:30})
        },
        setOneYear:function (key,value) {
            this.set(key,value,{expires:365})
        },
        set:function(key,value,options){
            try {
                $.cookie(key,value,options);
            }catch(e) {
                console.debug(e);
                return false;
            }
            return true;
        },
        get:function (key) {
            return $.cookie(key);
        },
        exists:function (key) {
            var value = $.cookie(key);
            if(!!value){
                return true;
            }
            return false;
        }
    };
    CookieExt.getInstance=function(){
        if (!this.instance){
            this.instance =new CookieExt();
        }
        return this.instance;
    };
	return CookieExt.getInstance();
});

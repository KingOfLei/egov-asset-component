define(["app/core/app-core","app/data/app-ajax"], function(App){
	var DEFAULTOPTIONS = {};
	var Portlet =function($div){
		this.$div = $div;
		
		this.options = $.extend({},$div.getJsonAttr("_options"),DEFAULTOPTIONS);
		
		this.load();
	};
	Portlet.prototype.load = function(){
		var url = this.options["url"];
		if(url){
			this.$div.empty().loadAppURL({
				"url":url,
				"noloading":true
			});
		}
	};
	
	/**
	 * 当切换到首页时触发
	 */
	Portlet.prototype.onSwitchMainTab = function(){
		var onSwitchMainTab = this.$div.data("onSwitchMainTab");
		if(onSwitchMainTab){
			onSwitchMainTab(this.$div);
		}
	};
	
	/**
	 * 注册到到jquery中
	 */
	$.fn.portlet = function(option,val){
		var args = arguments;
		$(this).each(function(){
			var $div = $(this);
			if(args.length==0){	//初始化Portlet对象
				$div.data("portlet",new Portlet($div));
			}else{
				var portlet = $div.data("portlet");
				if(!portlet){
					window.alert("portlet没有初始化不能调用");
					return;
				}
				var func =  portlet[option];
				if(func && typeof func==='function'){
					func.call(portlet,$.makeArray(arguments).slice(1));
					//return func($.makeArray(arguments).slice(1));
				}else{
					window.alert("portlet中没有"+option+"这个方法");
				}
			}
		});
	};
	if($A.navTab){
		var $mainTab = $A.navTab.getMainTab();
		if($mainTab){
			$mainTab.data("onSwitch",function($tab,$panel){
				$panel.find(".app-portlet").portlet("onSwitchMainTab");
			});
		}
	}
	
	return Portlet;
});
		
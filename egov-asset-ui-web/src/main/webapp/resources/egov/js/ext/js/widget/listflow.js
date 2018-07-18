 /**
 * 对app-tabs的扩展将横向tabs变为纵向
 */
 define([
 	"app/core/app-core",
	 "resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util",
	 ],function($A,ExtBase,CUtil) {

     var FlowStep = ExtBase.extend({

         defaultOption:{
         },

         init:function () {
             var _self = this;
             _self.extConfig(_self,_self.defaultOption);
         },

         render:function () {
             var _self = this;
             var data = _self.data;
             var time = _self.time;
             var text = _self.name;
             var $ul = $('<ul class="list_flow_box">');
             var li = '<li class="">'+
                 '<div class="timeline_title">'+
                 '<div class="timeline_circle">'+
                 '<i class=""></i>'+
                 '</div>'+
                 '<div class="info">'+
                 '<span class="time"></span>'+
                 '<span class="text"></span>'+
                 '</div>'+
                 '</div>'+
                 '</li>';
             $(data).each(function (index,item) {
                 var $li = $(li);
                 _self.getEleAttach($li);
                 $li.icon.addClass('circle_old');
                 $li.addClass('flow_label');
                 $li.time.text(item[time]);
                 $li.text.text(item[text]);
                 data[index].$ele = $li;
                 $li.appendTo($ul);
             });
             if(data.length>0){
                 var $first = data[0].$ele;
                 $first.removeClass();
                 $first.icon.removeClass();
                 $first.addClass('flow_new');
                 $first.icon.addClass('circle_new');
             }
             $ul.appendTo( _self.$component);
         },
         getEleAttach:function($li){
             var _self = this;
             $li.icon = $li.find('.timeline_circle i');
             $li.time = $li.find('.info .time');
             $li.text = $li.find('.info .text');
             return $li;
         },


         unbind:function () {
             return this;
         }
	 });
	return FlowStep;
});

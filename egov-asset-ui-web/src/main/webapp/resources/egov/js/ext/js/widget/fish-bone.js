 /**
 * 对app-tabs的扩展将横向tabs变为纵向
 */
 define([
 	"app/core/app-core",
	 "resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util",
     "resources/egov/js/ext/js/lib/tipso",
     "resources/egov/js/ext/js/lib/jquery.SuperSlide.2.1.1"
	 ],function($A,ExtBase,CUtil) {

     var currying = function(context,fn) {
         var args = [].slice.call(arguments, 2);
         return function() {
             var newArgs = args.concat([].slice.call(arguments));
             return fn.apply(context, newArgs);
         };
     };
     var FishBone = ExtBase.extend({

         defaultOption:{
         },
         colors: ['#F89782', '#1A84CE',
             '#F7A259', '#43A6DA',
             '#F9BF3B', '#88C7CC',
             '#EF6D5F', '#60A96E',
             '#F03852', '#3A9284'],
         init: function () {
             var self = this;
         },
         render: function () {
             var self = this;
             var $fishBone = $("<div class='fish-bone'/>");
             var wrapper = $("<div class='wrapper'></div>");
             var bd = $("<div class='bd'></div>");
             var $ul_item = $("<ul/>");

             //遍历数据
             $(self.data).each(function (index, item) {
                 var itemClass = self.itemClass(index);//显示在轴上方或下方标识 top/bottom
                 var color = self.getColor(index);
                 var lineFirstY = self.getLineFirstY(index);
                 var titleLeftY = self.getTitleLeftY(index);
                 var titleCenterY = self.getTitleCenterY(index);
                 var titleRightY = self.getTitleRightY(index);
                 var $ul = $("<ul></ul>");

                 var views_one = [];
                 var views_two = [];
                 //遍历封装属性
                 //封装审理时间和案号
                 if (itemClass == 'top') {
                     var $lineFirst = $("<li class='line-first'></li>")
                         .css('background-position', "0 " + (parseInt(lineFirstY) + 9) + "px");//9是原计算结果的偏移量，显示位置正合适
                     $lineFirst.appendTo($ul);

                     var $lineSecond = $("<li class='title'></li>");
                     var $titleLeft = $("<span class='title-left'>&nbsp;</span>").css('background-position', "0 " + titleLeftY);
                     var $titleCenter = $("<span class='title-center'></span>").css('background-position', "0 " + titleCenterY);
                     var $titleRight = $("<span class='title-right'>&nbsp;</span>").css('background-position', "0 " + titleRightY);

                     $lineSecond.data('color', color);
                     $lineSecond.data('data', item);
                     $lineSecond.data('itemClass', itemClass);

                     $lineSecond.append($titleLeft).append($titleCenter).append($titleRight);
                     $lineSecond.appendTo($ul);

                     views_one.push($lineFirst);
                     views_one.push($titleCenter);
                 }
                 //封装其他属性
                 for (var i = 2; i < 6; i++) {
                     var $li = $("<li class='line-common'>&nbsp;</li>").css("border-left", "1px solid " + color);
                     views_two.push($li);
                     $li.appendTo($ul);
                 }

                 //封装审理时间和案号
                 if (itemClass == "bottom") {
                     var $lineSecond = $("<li class='title'></li>");
                     var $titleLeft = $("<span class='title-left'>&nbsp;</span>").css('background-position', "0 " + titleLeftY);
                     var $titleCenter = $("<span class='title-center'></span>").css('background-position', "0 " + titleCenterY);
                     var $titleRight = $("<span class='title-right'>&nbsp;</span>").css('background-position', "0 " + titleRightY);
                     $lineSecond.append($titleLeft).append($titleCenter).append($titleRight);
                     $lineSecond.appendTo($ul);

                     var $lineFirst = $("<li class='line-first'></li>")
                         .css('background-position', "0 " + (parseInt(lineFirstY) - 33) + "px");//30是原计算结果的偏移量
                     $lineFirst.appendTo($ul);

                     $lineSecond.data('color', color);
                     $lineSecond.data('data', item);
                     $lineSecond.data('position', itemClass);

                     views_one.push($lineFirst);
                     views_one.push($titleCenter);
                     views_two.reverse();
                 }
                 //封装轴线上的圆点
                 var linePointY = self.getLinePointY(index);
                 var point = $("<li class='line-circle line-point'></li>").css('background-position', '0px ' + linePointY);
                 //自动连接
                 views_one = views_one.concat(views_two);
                 $(views_one[views_one.length - 1]).addClass('line-last');
                 for (var i = 0; i < views_one.length; i++) {
                     views_one[i].setValue = currying(self, self.setValue, views_one[i]);
                     views_one[i].setTip = currying(self, self.setTip, views_one[i], color)
                 }
                 self.getView(views_one, itemClass, item)
                 point.appendTo($ul);

                 //生成一个item
                 var $li_item = $("<li class='item'></li>");
                 var $content = $("<div class='content'></div>");
                 $ul.appendTo($content);
                 $content.appendTo($li_item);
                 $li_item.addClass(self.itemClass(index)).appendTo($ul_item);
                 $li_item.data('data', item);
             });
             $ul_item.appendTo(bd);
             bd.appendTo(wrapper);

             // var prev = $("<a class='prev'></a>");
             // var next = $("<a class='next'></a>");
             var line = $("<div class='line'/>");

             $fishBone.append(wrapper).append(line);
             self.$component.append($fishBone);

         },
         afterRender: function () {
             var self = this;
             self.rowCount = rowCount;
             var showArray = self.$component.find('li.item').toArray();
             var rowCount = self.fixWindow();
             var showIndex = 0;
             if (rowCount <= 0) {
                 return;
             }
             var distance = 200;
             self.$component.find('li.item.top').css({'top': -distance});
             self.$component.find('li.item.bottom').css({'top': distance});
             //第一次加载动漫
             function showFun($obj) {
                 $obj.css({'display': 'inline-block'});
                 $obj.animate(
                     {
                         top: 0,
                     }, 340, 'swing', function () {
                         showIndex++;
                         if (showIndex < rowCount) {
                             showFun($(showArray[showIndex]));
                         } else {
                             $(showArray.slice(showIndex)).css({'display': 'inline-block', top: 0});
                             self.$component.find('.bd').css({'overflow-x': 'auto'});
                         }
                     });
             }

             showFun($(showArray[showIndex]));

         },
         bind: function () {
             var self = this;
             var $liTitle = self.$component.find('li.title');
             //card单击回调
             if (!!self.cardClick) {
                 $liTitle.css({cursor: 'pointer'});
                 $liTitle.on('click', function () {
                     var $li = $(this);
                     self.cardClick($li.data('data'));
                 })
             }

         },
         //设置显示
         setValue: function ($view, value) {
             value = $.trim(value)
             if (!!!value) {
                 $view.html('&nbsp;');
                 return;
             }
             $view.text(value);
         },
         setTip: function ($view, color, value, position,options) {
             if (!!!position) {
                 position = 'top';
             }
             var width = 300;
             if(!!options&&!!options.width){
                 width = options.width;
             }
             $view.tipso({
                 useTitle: false,
                 tooltipHover: true,
                 background: color,
                 hideDelay: 600,
                 width: width,
                 background: color,
                 titleBackground: '#333333',
                 color: '#ffffff',
                 titleColor: '#ffffff',
                 content: value,
                 position: position
             });
         },
         /**自适应 平均分布*/
         fixWindow: function () {
             var self = this;
             var $component = self.$component;
             //item所占的宽度 = 自身宽度+marginleft
             var item = $component.find(".bd .item");
             var marginLeft = parseInt(item.css('margin-left'));
             var item_w = item.width() + marginLeft;

             //显示区域
             var bd_w = $component.find(".bd").width();
             //能显示的个数 取整
             var rowCount = parseInt(bd_w / item_w);
             if (self > item.size()) {
                 //rowCount = item.size();
             }
             //设置新的宽度使其平均分布
             var item_w_temp = bd_w / rowCount - marginLeft;
             item.width(item_w_temp);
             return rowCount;
         },
         /**li左边框线颜色 border-left-color 动态获取*/
         getColor: function (i) {
             var self = this;
             var colors = self.colors;
             var length = colors.length;
             var color = 'gray';
             if (i <= length - 1) {
                 color = colors[i];
             } else {
                 color = colors[i % length];
             }
             return color;
         },
         /**轴线上圆点位置纵坐标，见图片line-point.png*/
         getLinePointY: function (i) {
             var self = this;
             var colors = self.colors;
             var length = colors.length;
             var y = 0;
             if (i <= length - 1) {
                 y = -i * 20;
             } else {
                 y = -(i % length) * 20;
             }
             return y + "px";
         },
         /**第一行日期圆点位置纵坐标，图片line-first.png*/
         getLineFirstY: function (i) {
             var self = this;
             var colors = self.colors;
             var length = colors.length;
             var y = 0;
             if (i <= length - 1) {
                 y = -i * 60;
             } else {
                 y = -(i % length) * 60;
             }
             return y + "px";
         },
         /** .title-left背景纵坐标，0px开始，见图片title.png*/
         getTitleLeftY: function (i) {
             var self = this;
             var colors = self.colors;
             var length = colors.length;
             var y = 0;//图片位置
             if (i <= length - 1) {
                 y += -i * 60;
             } else {
                 y += -(i % length) * 60;
             }
             return y + "px";
         },
         /** .title-center背景纵坐标，600px开始，见图片title.png*/
         getTitleCenterY: function (i) {
             var self = this;
             var colors = self.colors;
             var length = colors.length;
             var y = -600;//图片位置
             if (i <= length - 1) {
                 y += -i * 60;
             } else {
                 y += -(i % length) * 60;
             }
             return y + "px";
         },
         /**.title-right背景纵坐标，1200px开始，见图片title.png*/
         getTitleRightY: function (i) {
             var self = this;
             var colors = self.colors;
             var length = colors.length;
             var y = -1200;//图片位置
             if (i <= length - 1) {
                 y += -i * 60;
             } else {
                 y += -(i % length) * 60;
             }
             return y + "px";
         },
         /**item添加样式，显示在上方或下方*/
         itemClass: function (index) {
             index += 1;
             if (index % 2 == 0) {
                 //偶数显示到下方
                 return "bottom";
             } else {
                 //奇数显示到上方
                 return "top";
             }
         }
	 });
	return FishBone;
});

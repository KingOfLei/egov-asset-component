/**
 * Created by qiu.yong on 2017/3/25.
 */
;(function ($) {

    $.extend({
        math:{
            //总和计算
            add:function (){
                var m;
                var sumA = [];
                var sum = 0;
                $(arguments).each(function(i,item){
                    var r1 = 0;
                    try{r1 = item.toString().split(".")[1].length}catch(e){r1 = 0};
                    sumA.push(r1);
                });
                m = Math.pow(10, Math.max.apply(window,sumA));
                $(arguments).each(function(i,item){
                    sum = sum + parseInt(parseFloat(item*m).toFixed(0));
                });
                return sum / m;
            },
            //乘
            multiply:function () {
                var m;
                var sumA = [];
                var sum = 1;
                $(arguments).each(function(i,item){
                    var r1 = 0;
                    try{r1 = item.toString().split(".")[1].length}catch(e){r1 = 0};
                    sumA.push(r1);
                });
                m = Math.pow(10, Math.max.apply(window,sumA));
                $(arguments).each(function(i,item){
                    sum = sum * parseInt(parseFloat(item*m).toFixed(0));
                });
                return sum / (Math.pow(m,arguments.length));
            },
            //减
            subtract:function (base) {
                return function () {
                    var m;
                    var sumA = [];
                    var args = [].slice.call(arguments, 0); args.push(base);
                    $(args).each(function(i,item){
                        var r1 = 0;
                        try{r1 = item.toString().split(".")[1].length}catch(e){r1 = 0};
                        sumA.push(r1);
                    });
                    m = Math.pow(10, Math.max.apply(window,sumA));
                    base = parseInt(parseFloat(base*m).toFixed(0));
                    $(arguments).each(function(i,item){
                        base = base - parseInt(parseFloat(item*m).toFixed(0));
                    });
                    return base / m;
                }
            },
            divide:function (base) {
                return function () {
                    var m;
                    var sumA = [];
                    var args = [].slice.call(arguments, 0); args.push(base);
                    $(args).each(function(i,item){
                        var r1 = 0;
                        try{r1 = item.toString().split(".")[1].length}catch(e){r1 = 0};
                        sumA.push(r1);
                    });
                    m = Math.pow(10, Math.max.apply(window,sumA));
                    base = parseInt(parseFloat(base*m).toFixed(0));
                    $(arguments).each(function(i,item){
                        base = base / parseInt(parseFloat(item*m).toFixed(0));
                    });
                    return base ;
                }
            }
        }
    });

})(jQuery);
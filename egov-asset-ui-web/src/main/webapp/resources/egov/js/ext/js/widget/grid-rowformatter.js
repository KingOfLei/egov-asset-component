/**
 *  扩展 网格行格式化
 *  author: zxy
 *  date: 2017-04-14
 *  $.uiExtend({
        type:'grid-rowformatter',							// (*必传)扩展插件类型
        $grid:$('#aimsCard6Page_grid'),						// (*必传，JQ对象)需操作的JQ对象
        options:{													
    		className:'font-weight',						// 样式名称(默认加粗显示)
    		judgeFieldName: 'read' ,						// 用于判断的字段名(默认为已读未读字段名read)
    		judgeFieldVal: 0,								// 用于判断的字段值，当该值与相应字段名的值相同时条件成立，增加参数样式
        	valStyle:{										// (非必传)字段值对应样式名，该参数不传时，默认对应第一个参数className的样式
    			 0:'font-weight',							// 如：字段值为0时，样式为 font-weight
    			 1:'color-red',
    			 2:'color-orange'
    		 },
    		 css:{											// (非必传，优先级最高，将覆盖前两个参数中同名属性)直接书写样式
	    		 0:{
	    			 'color': 'gray',
	    			 'background-color': 'pink'
    		 	 },
	    		 1:{
	    			 'color': 'green',
	    			 'background-color': '#62a9f1'
	    		 },
	    		 2:{
	    			 'color': 'gray',
	    			 'background-color': 'pink'
	    		 }
    		 }								
        }
    });
 */
 define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util"
	 ],function(ExtBase,CUtil) {
	 var GridRowformatter = ExtBase.extend({
         // 初始化
		 init: function () {
			 var _this = this;
			 
			 var defaultOpts = {
				 className:'font-weight',
	    		 judgeFieldName: 'read' ,
	    		 judgeFieldVal: 0
			 };
			 
			 var opts = $.extend(true, {}, defaultOpts, _this.options);
			 _this.formatterRow(_this, opts);
         },
         
         // 格式化行样式
         formatterRow: function(_this, opts){
        	 var rowDatas = _this.$grid.grid('getAllData');
        	 if(!rowDatas || rowDatas.length === 0){return ;};
        	 for(var i = 0; i < rowDatas.length; i++){
        		 var $obj = _this.$grid.parent().find('.grid-body-main #' + rowDatas[i].__rowId);
    			 var css = opts.css?opts.css:{};
        		 // 判断 是否格式化行存在多个样式参数
        		 if(opts.css){
        			 for(var op in opts.css){
        				 if(rowDatas[i][opts.judgeFieldName] == op){
        					 $obj.css(opts.css[op]);
        				 };
        			 };
        		 }else if(opts.valStyle){
        			 for(var opt in opts.valStyle){
        				 if(rowDatas[i][opts.judgeFieldName] == opt){
        					 $obj.addClass(opts.valStyle[opt]);
        				 };
        			 };
        		 }else{
            		 if(rowDatas[i][opts.judgeFieldName] === opts.judgeFieldVal){
            			 $obj.addClass(opts.className).css(css);
            		 }else{
            			 $obj.removeClass(opts.className).css(css);
            		 };
        		 }
        	 };
        	 
         }
     });
	 
     return GridRowformatter;
});
 
 
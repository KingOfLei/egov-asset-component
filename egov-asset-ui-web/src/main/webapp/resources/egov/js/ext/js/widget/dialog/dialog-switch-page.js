/**
 *  扩展弹框内容根据数据上下翻页
 *  author: zxy
 *  date: 2017-03-13
 *  
 *  $.uiExtend({
        type:'dialog',
        contentId:'XXX',                                                              // *(必传)弹框ID(字符串)
        options:{
            switchPageParams: {                                                       // 封装切换页面插件参数
                showSwitchBtn:true,                                                   // 是否显示上下翻页按钮(布尔值,默认不显示)
                dialogParentGrid:$('#aimsCard6Page_grid')或'aimsCard6Page_grid',      // *(必传)弹框父级网格(jq对象或网格id)
                curRowIndex:1,                                                        // 弹框父级网格选中行的行号(行号从0开始)
                showMsg:true,                                                         // 是否弹出提示(默认弹出)
                switchBtnPos:'bottom',                                                // 按钮显示位置(top、right,默认bottom)
                newPageCallback:function(data){                                       // 翻页返回数据回调函数
                    // 可根据 data.isLimit 判断是否到达边界(如：返回值为 next,则说明点击下一张时到达边界)
                    // data._newGridRowIndex 为新数据的行号
                }   
            }
        }
    });
 */

define(["resources/egov/js/ext/js/base/ext-base",
    "resources/egov/js/ext/js/util/common-util"], 
        function(ExtBase,util) {

		switchPageNewRowIndex =  undefined;
        var DialogSwitch = ExtBase.extend({
            // 初始化页面完成后
            initPage:function(){
            	var _this = this;

            	// 设置默认参数
            	var defaultOpts = {
            			showSwitchBtn:true,														
            			newPageCallback:function(data){},										
            			showMsg:true,										
            			switchBtnPos:'bottom'
            	};
            	// 由于切换页面时刷新JS,故暂时使用全局变量存储当前数据行号
            	 if(typeof switchPageNewRowIndex !== 'undefined'){
            		 _this.options.switchPageParams.curRowIndex = switchPageNewRowIndex;
            	 };
            	_this.options.switchPageParams = $.extend(true,{}, defaultOpts, _this.options.switchPageParams);
            	
                // 父级表格对象 (判断传入参数是对象或是字符串)
                if(typeof _this.options.switchPageParams.dialogParentGrid === 'object'){
                	_this.options.switchPageParams.$dialogParentGrid = _this.options.switchPageParams.dialogParentGrid;
                }else{
                	_this.options.switchPageParams.$dialogParentGrid = $('#'+_this.options.switchPageParams.dialogParentGrid);
                };
                var getAllData = _this.options.switchPageParams.$dialogParentGrid.grid('getAllData');
                // 初始化时存储当前网格页数据
        		switchPageDatas = getAllData.length?getAllData:switchPageDatas;
            	// 设置按钮位置
            	_this.setPosition(_this, _this.options.switchPageParams.switchBtnPos);
            	
            	//
            	_this.options.switchPageParams.exist = true;
            	
            },
            
            // 绑定按钮点击事件
            bindSwitchEvent: function(_this,type){
            	_this.options.switchPageParams.noFirstLoad = false;
            	_this.getGridRow({
            		_this:_this,
            		$grid:_this.options.switchPageParams.$dialogParentGrid,
            		loadType: type,
            		callback:function(data){
            			_this.options.switchPageParams.newPageCallback(data);
            			if(data){
                			switchPageNewRowIndex = data._newGridRowIndex;
            			};
            		}
            	});
            },
            
            // 设置按钮显示位置
            setPosition:function(_this, pos){
                var $curDialog = $A('#'+_this.contentId);
                var btnHtml = '',$btnParent;
            	switch(pos){
            		case "top":		// 顶部
            			btnHtml = 	'<div style="width:100%;background-color:#fff;">\
            						<div class="switch-top-btn-group switchBtns">\
	            						<div class="switch-top-btn switch-top-first-btn switch-first-btn" title="第一张卡片"></div>\
	            						<div class="switch-top-btn switch-top-prev-btn switch-prev-btn" title="上张卡片"></div>\
	            						<div class="switch-top-btn switch-top-next-btn switch-next-btn" title="下张卡片"></div>\
	            						<div class="switch-top-btn switch-top-last-btn switch-last-btn" title="最后一张卡片"></div>\
            						</div>\
            						</div>';

                        // 动态添加按钮
                        $curDialog.parent('.dialog-content').before(btnHtml);
                        // 取得按钮父级对象
                        $btnParent = $curDialog.parent('.dialog-content').prev('div');
//                        $curDialog.before(btnHtml);
                        
            			break;
            		case "right":	// 右侧
                    	
                        btnHtml =  '<div class="switch-right-btn switch-right-first-btn switch-first-btn" title="第一张卡片"></div>\
                        			<div class="switch-right-btn switch-right-prev-btn switch-prev-btn" title="上张卡片"></div>\
                        			<div class="switch-right-btn switch-right-next-btn switch-next-btn" title="下张卡片"></div>\
                        			<div class="switch-right-btn switch-right-last-btn switch-last-btn" title="最后一张卡片"></div>';

                        // 动态添加按钮
                        $curDialog.parent('.dialog-content').addClass('switch-page-content');
                        // 取得按钮父级对象
                        $btnParent = $curDialog;
                        $curDialog.append(btnHtml);
                        
            			break;
            		case "bottom":	// 底部
            			btnHtml =  '<div class="switch-bottom-btn-group switchBtns">\
            						<div style="margin:0 auto;width: 200px;overflow:hidden;">\
            						<div class="switch-bottom-btn switch-bottom-first-btn switch-first-btn" title="第一张卡片"></div>\
                					<div class="switch-bottom-btn switch-bottom-prev-btn switch-prev-btn" title="上张卡片"></div>\
                					<div class="switch-bottom-btn switch-bottom-next-btn switch-next-btn" title="下张卡片"></div>\
                					<div class="switch-bottom-btn switch-bottom-last-btn switch-last-btn" title="最后一张卡片"></div>\
            						</div>\
            						</div>';

		                // 动态添加按钮
//		                $curDialog.parent('.dialog-content').addClass('switch-page-content');
		                // 取得按钮父级对象
		                $btnParent = $curDialog.parents('.dialog');
//		                $curDialog.append(btnHtml);
		                $btnParent.append(btnHtml);
		                
		    			break;
    			
            	};

                // 存储按钮对象
            	_this.options.switchPageParams.firstBtn = $btnParent.find('.switch-first-btn');
            	_this.options.switchPageParams.prevBtn = $btnParent.find('.switch-prev-btn');
            	_this.options.switchPageParams.nextBtn = $btnParent.find('.switch-next-btn');
            	_this.options.switchPageParams.lastBtn = $btnParent.find('.switch-last-btn');
                                
                // 绑定按钮事件
            	_this.options.switchPageParams['firstBtn'].bind("click", function(){
                	_this.bindSwitchEvent(_this,'first');
                });
            	_this.options.switchPageParams['prevBtn'].bind("click", function(){
                	_this.bindSwitchEvent(_this,'prev');
                });
            	_this.options.switchPageParams['nextBtn'].bind("click", function(){
                	_this.bindSwitchEvent(_this,'next');
                });
            	_this.options.switchPageParams['lastBtn'].bind("click", function(){
                	_this.bindSwitchEvent(_this,'last');
                });
                // 上部
//	            dialog-content 前插入
//	            <div style="width:100%;background-color:#fff;">
//	                <div style="width:50px;height:50px;border:1px solid #000;margin:0 auto;">我是第一张卡片</div>
//	            </div>
            },
            
            // 获取表格选中行数据 
            getGridRow: function(_opts){
            	_this = _opts._this;
            	$grid = _opts.$grid;
            	loadType = _opts.loadType;
            	_this.options.switchPageParams.isLimit = false;

            	var curGridDatas = $grid.grid('getAllData'),curDataLen = curGridDatas.length;
//            	$grid.grid('getRowIndex',$grid.grid('getSelections')[0]));
                // 获取选中行数据,若无选中行则根据记录行号取得网格中相应数据
            	var selRow = curGridDatas[_this.options.switchPageParams.curRowIndex]?curGridDatas[_this.options.switchPageParams.curRowIndex]:$grid.grid('getSelections')[0],newRow = 0;
                // 根据参数中传递的行号获取当前数据
//                var selRow = $grid.grid('getDataByRowIndex',_this.options.switchPageParams.curRowIndex),newRow = 0;

                // 获取选中行行号
            	var selRowIndex,newRowIndex = 0;
            	
                if(!selRow){
                	selRowIndex = _this.options.switchPageParams.curRowIndex;
                }else{
                	selRowIndex = $grid.grid('getRowIndex',selRow);
                };
            	// 无返回数据时，给出提示
            	var msg = false;
            	
            	// 获取当前分页对象
            	var $curPage = $grid.next('.app-grid');
            	
            	// 获取当前页号
            	var curPageNum = newPageNum = parseInt($curPage.find('.currPage').val());
            	// 获取当前网格总页数
            	var totalPage = parseInt($curPage.find('.totalPages').html());
            	
            	// 取出当前页边界值及网格最大值
            	var curPageMin = parseInt($curPage.find('.pager-infos').find('label').eq(0).html());
            	var curPageMax = parseInt($curPage.find('.pager-infos').find('label').eq(1).html());
            	var curDataMaxNum = parseInt($curPage.find('.pager-infos').find('label').eq(2).html());

                switch(loadType){
	        		case 'prev':
	                	// 若当前行 为第一行
	                	if(selRowIndex === 0 && curPageMin === 1){
	                		msg = '当前已选中第一张卡片！';
	                		_this.options.switchPageParams.isLimit = 'prev';
	                	}else{
	                		if(selRowIndex === 0 || curDataLen === (curPageMax-curPageMin)){

	                        	$grid.grid('unselectRow',selRowIndex);
		                		// 切换上页
	                			$grid.grid('loadForPage',curPageNum-1);
	                			// 存储新页号
	                			newPageNum = curPageNum-1;
	                			
		                		newRowIndex = curPageMax-curPageMin;
		                	}else{
		                		newRowIndex = selRowIndex-1;
		                	};
		                	
	                	};
	                	
	        			break;
	        		case 'next':
	        			// 若当前行 为最后一行
	                	if(selRowIndex === (curDataLen-1) && curPageNum === totalPage){
	                		msg = '当前已选中最后一张卡片！';
	                		_this.options.switchPageParams.isLimit = 'next';
	                	}else{
                			if(selRowIndex === (curDataLen-1) || curDataLen === 0){
	                        	$grid.grid('unselectRow',selRowIndex);
		                		// 切换下页
	                			$grid.grid('loadForPage',curPageNum+1);
	                			// 存储新页号
	                			newPageNum = curPageNum+1;
	                			
	                			// 新行号为当前网格下页第一条数据
		                		newRowIndex = 0;
		                	}else{
		                		newRowIndex = selRowIndex+1;
		                	};
		                
	                	};
	                	
	        			break;
	        		case 'first':
	        			if(selRowIndex === 0 && curPageMin === 1){
	                		msg = '当前已选中第一张卡片！';
	                		_this.options.switchPageParams.isLimit = 'first';
	                	}else{
                			$grid.grid('loadForPage',1);
                			// 存储新页号
                			newPageNum = 1;

                			// 新行号为网格首页第一条数据
	                		newRowIndex = 0;
	                	};
	                	
	        			break;
	        		case 'last':
	        			if(selRowIndex === (curDataLen-1) && curPageNum === totalPage){
	                		msg = '当前已选中最后一张卡片！';
	                		_this.options.switchPageParams.isLimit = 'last';
	                	}else{
                			$grid.grid('loadForPage',totalPage);
                			// 存储新页号
                			newPageNum = totalPage;
                			
                			// 新行号为网格末页最后一条数据
	                		 newRowIndex = curDataLen-1;
	                	};
	                	
	        			break;
	        	};
            	
            	// 若有错误提示语则弹出提示，否则返回新选中行
            	if(_this.options.switchPageParams.showMsg && _this.options.switchPageParams.isLimit && msg){
            		$a.assetMsg.info(msg);
            		newRowIndex = selRowIndex;
            	};
            	
            	// 存储当前数据在父级网格页面中的行号
            	_this.options.switchPageParams.curRowIndex = newRowIndex;
//            	$grid.data('grid').on('onLoadSuccess',function(data){
//            		$grid.grid('selectRow',data.data[newRowIndex]['__rowId']);
//            	};
            	// 存储返回数据对象
            	var returnDatas = {};
            	if(curPageNum !== newPageNum){
                	var subGrid = $grid.data('grid');
                	// 加载完成前增加遮罩层
                	$('body').css('position','relative').append('<div id="pageShade" style="width:100%;height:100%;background-color:black;filter:alpha(opacity=30);-moz-opacity:0.3;opacity:0.3;position: absolute;top: 0;z-index: 9999;font-size: 25px;text-align:center;padding-top:200px;color:#ffffff;">正在努力加载...</div>');
                	subGrid.on('onLoadSuccess',function(data){
                		if(!_this.options.switchPageParams.exist){ return ;}
//                		$grid.grid('selectRow',data.data[newRowIndex]['__rowId']);
                    	// 用于解决弹框关闭后刷新网格数据 导致原先取得数据丢失问题
                    	if(parseInt($grid.next('.app-grid').find('.currPage').val()) !== newPageNum){
                        	$grid.grid('loadForPage',newPageNum);
//                        	curPageNum = newPageNum;
                    	}else{
	                		// 移除遮罩层
	                		$('#pageShade').remove();
	                		
	                		if(_this.options.switchPageParams.noFirstLoad){
	                			return false;
	                		};
	
	                		if(!_this.options.switchPageParams.noFirstLoad){
	                			_this.options.switchPageParams.noFirstLoad = true;
	                		};
	                		// 刷新网格后存储当前网格数据
	                    	switchPageDatas = data.data;
	                    	
	                		// 最后一页数据行数判断
	                		if(loadType === 'last'){
	                			_this.options.switchPageParams.curRowIndex = data.data.length-1;
	                		};
	                		// 切换网格页面时有bug，无法选中数据
//	                		$grid.grid('selectRow',newRowIndex);
	                		returnDatas.data = data.data[_this.options.switchPageParams.curRowIndex];
	                		returnDatas._newGridRowIndex = _this.options.switchPageParams.curRowIndex;
	                		returnDatas.isLimit = _this.options.switchPageParams.isLimit;
	                    	_opts.callback(returnDatas);
                    	};
//                    	$grid.grid('selectRow',returnDatas.data.__rowId);
//                    	$grid.grid('selectRow',newRowIndex);
                	});
            	}else{
            		newRow = $grid.grid('getDataByRowIndex',newRowIndex);
                	newRow = newRow?newRow:switchPageDatas[newRowIndex];
            		if(newRow){
                		returnDatas._newGridRowIndex = newRowIndex;
                		returnDatas.isLimit = _this.options.switchPageParams.isLimit;
            		};
        			returnDatas.data = newRow;
//            		$grid.grid('selectRow',newRow.__rowId);
            		_opts.callback(returnDatas);
            	};
            	
            }
        });
        
    return DialogSwitch;
});

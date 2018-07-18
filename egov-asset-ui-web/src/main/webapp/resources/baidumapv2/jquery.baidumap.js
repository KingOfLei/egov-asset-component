/*!
 * 扩展百度API :注意区分在线版和离线版的方法，离线版部分方法不可用
 * 注意：此扩展插件中只包含部分常用方法
 * $('#initPreview').baidumap('refresh',insertOpts);
 * author: zhangxiaoyun
 * date: 2017.8.7
 * version: 1.0
 * updata: 
 *     增加地图允许的最大/最小级别  
 *     2017-12-26  
 *     zhangxiaoyun
 */

;(function($){
    $.fn.baidumap = function(options, content) {
        var BaiduMap = {};
        var _this = this, _opts = {}, fnType;
        var type = typeof options;

        // 初始化插件
        BaiduMap.initPlugIn = function(options, fnType){
            _this.each(function() {
                var $_this = $(this);
                var baiduMap = {};

                baiduMap.map = window['_current_map_'+$_this.attr('id')];
                // 初始化(包括页面和功能)
                baiduMap._init = function(){
                    // 初始化并设置地图允许的最大/最小级别
                    this.map = window['_current_map_'+$_this.attr('id')] = new BMap.Map($_this.attr('id'),{minZoom: options.minZoom,maxZoom: options.maxZoom});
                    this._mapInit();
                };

                // 地图功能初始化
                baiduMap._mapInit = function(){
                    var map = baiduMap.map;
                    map.centerAndZoom(new BMap.Point(options.lng,options.lat),options.level); // 初始化地图:设置中心坐标及地图级别
                    map.enableScrollWheelZoom();     //开启鼠标滚轮缩放
                    map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT}));   //缩放按钮
                    map.enableKeyboard();                           //启用键盘操作
                    
                    // 是否在点击地图时添加坐标点
                    if(options.mapClickEven){
                        map.addEventListener("click",function(e){       // 拾取坐标
                            if(e.overlay){ return; };
                            // 自定义地图点击事件
                            options.mapClickEven(e);
                            // baiduMap.addCover([{
                            //     lng: e.point.lng,
                            //     lat: e.point.lat,
                            //     label: {
                            //         text: e.point.lng+" , "+e.point.lat,
                            //         x:20,
                            //         y:-10
                            //     },
                            //     onlyOneMarker: options.clickSetMarker.onlyOneMarker,
                            //     enableDrag: true
                            //     ,even:[{
                            //         name: "mouseup",
                            //         fn: function(e, _this){
                            //             var p = e.target;
                            //             var lab = _this.getLabel();
                            //             lab.setContent(p.getPosition().lng+" , "+p.getPosition().lat);
                            //         }
                            //     }]
                            // }]);

                            // 点击结束后执行事件
                            // options.clickSetMarker.onAfterClick(e);
                        }); 
                    };

                    // 是否显示自定义面板
                    if(options.showPanel){
                        // Object.keys(options.showPanel).length>0 ? baiduMap.addPanel(options.showPanel) : baiduMap.addPanel();
                        baiduMap.addPanel(options.showPanel);
                    };

                    // 地图初始化完成执行事件
                    if(options.onLoadSuccess){
                        options.onLoadSuccess();
                    };
                };

                // 销毁地图
                baiduMap.destory = function(){
                    $_this.children().remove();
                };

                // 批量添加覆盖物
                //  addOpts 参数为数组对象 包括lng、lat、label的text、x、y对象值
                //  addOpts = [{
                //      lng: 118.079646,
                //      lat: 24.469558,
                //      label: {
                //          text:"交通大厦",
                //          x:20,
                //          y:-10
                //      },
                //      imgURL: $('#setCoverImg').siblings('label').eq(2).find('input').val(),
                //      width: $('#setCoverImg').siblings('label').eq(3).find('input').val(),
                //      height: $('#setCoverImg').siblings('label').eq(4).find('input').val(),
                //      enableDrag: true,
                //      even:[{
                //          name: "mouseup",
                //          fn: function(e){
                //              // 执行事件
                //          }
                //      }]
                //  }]
                baiduMap.addCover = function(addOpts){
                    // 循环添加覆盖物
                    for (var i = 0; i < addOpts.length; i++) {
                        // 是否只显示一个坐标点
                        if(addOpts[i].onlyOneMarker){
                            // 清除地图上所有覆盖物
                            baiduMap.map.clearOverlays();
                        };

                        var point = new BMap.Point(addOpts[i].lng, addOpts[i].lat);
                        // baiduMap.map.panTo(point);           //移动到目标点
                        // 判断是否替换自定义图标
                        if(addOpts[i].imgURL){
                            var icon = new BMap.Icon(addOpts[i].imgURL, new BMap.Size(addOpts[i].width,addOpts[i].height));
                            var marker = new BMap.Marker(point,{icon:icon});
                        }else{
                            var marker = new BMap.Marker(point);
                        };
                        // 添加label标签
                        if (addOpts[i].label) {
                            var label = new BMap.Label(addOpts[i].label.text,{offset:new BMap.Size(addOpts[i].label.x,addOpts[i].label.y)});
                            label.setStyle(addOpts[i].label.style)
                            marker.setLabel(label);
                        };

                        // 坐标点是否可拖动
                        if(addOpts[i].enableDrag){
                            marker.enableDragging();
                        };
                        // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                        
                        // 循环绑定事件
                        if(addOpts[i].even && addOpts[i].even.length > 0) {
                            for (var j = 0; j < addOpts[i].even.length; j++) {
                                var fn = addOpts[i].even[j].fn;
                                // 闭包
                                (function(evenName, fn){
                                    marker.addEventListener(evenName, function(e){
                                        fn(e,this);
                                    });
                                })(addOpts[i].even[j].name, fn);
                            };
                        };

                        // 存储自定义属性
                        marker._id = addOpts[i]._id;
                        marker._self_attr = addOpts[i]._self_attr;
                        
                        // 添加坐标点到地图
                        baiduMap.map.addOverlay(marker);
                    };

                };

                // 获取地图上的所有覆盖物信息
                baiduMap.getMap = function(){
                    return baiduMap.map;
                };

                // 获取地图上的所有覆盖物信息
                baiduMap.getCover = function(){
                    var coverObjs = [];
                    // 根据条件清除覆盖物
                    var allOverlay = baiduMap.map.getOverlays();
                    for (var i = 0; i < allOverlay.length -1; i++){
                        var p = allOverlay[i].getPosition();
                        coverObjs[i].lng = p.lng;
                        coverObjs[i].lat = p.lat;
                    };

                    return coverObjs;
                };

                // 删除覆盖物
                // 若delOpts参数不存在，则删除地图上所有覆盖物;
                // delOpts 为数组对象，包括lng、lat对象值
                // delOpts = [{
                //     lng: 118.079646,
                //     lat: 24.469558
                // }]
                baiduMap.deleteCover = function(delOpts){
                    var flag = false;
                    if(delOpts && delOpts.length>0){
                        // 根据条件清除覆盖物
                        var allOverlay = baiduMap.map.getOverlays();
                        for (var i = 0; i < allOverlay.length; i++){
                            if(allOverlay[i].toString()=="[object Marker]"){
                                for (var j = 0; j < delOpts.length; j++) {
                                    if(delOpts[j].type === 'id'){
                                        if(allOverlay[i]._id === delOpts[j].id){
                                            baiduMap.map.removeOverlay(allOverlay[i]);
                                            return false;
                                        };
                                    }else{

                                        var p = allOverlay[i].getPosition();
                                        if(p.lng === delOpts[j].lng && p.lat === delOpts[j].lat){
                                            baiduMap.map.removeOverlay(allOverlay[i]);
                                            return false;
                                        };
                                    };
                                };
                            };
                        };
                    }else{
                        // 清除地图上所有覆盖物
                        baiduMap.map.clearOverlays();
                    };
                };

                // 缩放地图
                baiduMap.setZoom = function(zoomOpts){
                    if(zoomOpts.lng){
                        baiduMap.map.centerAndZoom(new BMap.Point(zoomOpts.lng,zoomOpts.lat),zoomOpts.level); 
                    }else{
                        baiduMap.map.setZoom(zoomOpts.level);
                    };
                };

                // 移动地图到指定坐标
                baiduMap.moveTo = function(moveOpts){
                    baiduMap.map.panTo(new BMap.Point(moveOpts.lng,moveOpts.lat));
                };

                // 鼠标拉框放大
                baiduMap.frameEnlarge = function(enlargeOpts){
                    if(enlargeOpts.enlarge){
                        baiduMap.map.frameEnlarge = new BMapLib.RectangleZoom(baiduMap.map, {
                            followText: "拖拽鼠标拉框放大"
                        });
                        baiduMap.map.frameEnlarge.open();  //开启拉框放大
                    }else{
                        baiduMap.map.frameEnlarge.close();  //关闭拉框放大
                    };
                };

                // 获取行政划区(离线不支持)
                baiduMap.getBoundary = function(bOpts){
                    var bdary = new BMap.Boundary();
                    bdary.get(bOpts.searchName, function(rs){       //获取行政区域
                        map.clearOverlays();        //清除地图覆盖物       
                        var count = rs.boundaries.length; //行政区域的点有多少个
                        if (count === 0) {
                            alert('未能获取当前输入行政区域');
                            return ;
                        }
                        var pointArray = [];
                        for (var i = 0; i < count; i++) {
                            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
                            map.addOverlay(ply);  //添加覆盖物
                            pointArray = pointArray.concat(ply.getPath());
                        }    
                        map.setViewport(pointArray);    //调整视野  
                        addlabel();               
                    });
                };

                // 设置自定义覆盖物
                // baiduMap.setCoverImg = function(iOpts){
                //     //创建自定义坐标点
                //     var pt = new BMap.Point(iOpts.lng, iOpts.lat);
                //     var myIcon = new BMap.Icon(iOpts.imgURL, new BMap.Size(iOpts.width,iOpts.height));
                //     var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
                //     baiduMap.map.addOverlay(marker2);              // 将标注添加到地图中
                // };

                // 添加自定义面板
                baiduMap.addPanel = function(pOpts){
                    var contentHtml = '<div class="search-box">\
                        <input class="search-input" type="text" placeholder="输入查询条件">\
                        <a class="search-submit"></a>\
                    </div>';
                    var defaultPanelOpts = {
                        panelID: 'addNewPanel',
                        panelContent: contentHtml,
                        // 默认停靠位置和偏移量
                        defaultAnchor: BMAP_ANCHOR_TOP_LEFT,
                        defaultOffset: [0, 0],
                        afterAppendPanel: function(){}
                    };
                    var _opts = $.extend(true, {}, defaultPanelOpts, pOpts);

                    // 定义一个控件类,即function
                    var ZoomControl = function(){
                        // 默认停靠位置和偏移量
                        this.defaultAnchor = _opts.defaultAnchor;
                        this.defaultOffset = new BMap.Size(_opts.defaultOffset[0], _opts.defaultOffset[1]);
                    };

                    // 通过JavaScript的prototype属性继承于BMap.Control
                    ZoomControl.prototype = new BMap.Control();

                    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
                    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
                    ZoomControl.prototype.initialize = function(map){
                        var div;
                        // 判断页面是否存在id为panelID的元素，若存在，则直接将该元素添加到控件中
                        if($('#' + _opts.panelID).length > 0){
                            div = $('#' + _opts.panelID)[0];
                            // 添加DOM元素到地图中
                            baiduMap.map.getContainer().appendChild(div);
                        }else{
                            // 创建一个DOM元素
                            div = document.createElement("div");
                            div.id = _opts.panelID;
                            $(div).append(_opts.panelContent);
                            // 添加DOM元素到地图中
                            baiduMap.map.getContainer().appendChild(div);
                            
                            // TODU 需要重新定义该区域的鼠标滚轮事件

                            // 添加自定义面板后触发的事件
                            _opts.afterAppendPanel();
                        };
                        
                        // 将DOM元素返回
                        return div;
                    }
                    // 创建控件
                    var myZoomCtrl = new ZoomControl();
                    // 添加到地图当中
                    baiduMap.map.addControl(myZoomCtrl);
                };
                
                // 判断是初始化还是调用方法
                if (fnType) {
                    baiduMap[fnType](options);
                }else{
                    baiduMap._init();
                };

            });
        };
        
        // 判断是插件初始化或刷新方法调用
        if (type === 'object') {
            _opts = $.extend(true, {}, $.fn.baidumap.options, options || {});
        }else if(type === 'string'){
            fnType = options;
            if((content instanceof Object) && !(content instanceof Array)){
                _opts = $.extend(true, {}, $.fn.baidumap.options, content || {});
            }else{
                _opts = content;
            };
        };
        return BaiduMap.initPlugIn(_opts, fnType);
    };

    $.fn.baidumap.options = {
        // enableScrollWheelZoom: true,    // 滚轮缩放
        // addControl: true,               // 缩放按钮
        // click: function(){},
        // clickSetMarker: {
        //     onlyOneMarker: true,
        //     onAfterClick: function(){

        //     }
        // }
        minZoom: 11,
        maxZoom: 16,
        mapClickEven: false
    };

})(jQuery);
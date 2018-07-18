/*!
 * 步骤提示插件
 * author: zhangxiaoyun
 * date: 2017.11.23
 * version: 1.0
 *  --初始化
 *  $('#stepHint').stephint({
        cssStyle:{
            border: '1px solid #62a9f1',
            backgroundColor: '#fff'
        },
        data: [{
            title: '1.基础数据(导入/同步)',
            state: 'finish',
            childData:[
                '2017-11-22'
            ]
        }]
    });
 *  --流程完成
    $('#stepHint').stephint('update', 'finish');
 */

;(function($){
    $.fn.stephint = function(options, content) {
        var StepHint = {};
        var _this = this, _opts = {}, fnType;
        var type = typeof options;

        // 初始化插件
        StepHint.initPlugIn = function(options, fnType){
            _this.each(function() {
                var $_this = $(this);
                var stepHint = {};

                // 初始化创建页面
                stepHint._init = function(options){
                    var shintHtml = '';
                    // 存储与匹配元素相关的任意数据
                    $_this.data('stephint', options);

                    for (var i = 0; i < options.data.length; i++) {
                        var state = options.data[i].state, 
                            curLiClass = (state?'stephint-'+state:''),
                            childHtml = '';
                        var childData = options.data[i].childData;
                        
                        // 判断是否为第一个节点，若是，则增加开始节点
//                        if (i === 0) {
//                            if(state === 'doing'){
//                                curLiClass = 'stephint-finish';
//                            };
//                           shintHtml += '<li class="' + curLiClass + '">\
//                           <h5>开始</h5><div></div></li>';
//                        };

                        shintHtml += stepHint._createLi(options.data[i], i);

                    };

                    var ulHtml = '<div class="stephint">\
                        <div class="stephint-list"><ul>' + shintHtml + '<li style="min-height: 40px;">\
                            <h5>结束</h5></li>\
                        </ul></div><div class="stephint-attach"></div></div>'
                    $_this.html(ulHtml).find('.stephint').css(options.cssStyle);

                    // 若存在附件配置项,则创建对应初始化页面代码
                    if (options.showAttach) {
                        stepHint.setAttach(options.showAttach);
                    }else{
                        $_this.find('.stephint-attach').css('border', 'none');
                    };

                };

                // 创建各li的布局
                stepHint._createLi = function(liData, index){
                    var liHtml = '', childHtml = '', childData = liData.childData, 
                        curLiClass = (liData.state?'stephint-'+liData.state:'');
                    // 添加 li 下的显示数据
                    if (childData.length > 0) {
                        for (var j = 0; j < childData.length; j++) {
                            childHtml += '<span>' + childData[j] + '</span>';
                        };
                    };

                    // li布局
                    liHtml = '<li class="' + curLiClass + '">\
                        <h5><a>' + liData.title + '</a></h5>\
                        <div>' + childHtml + '</div>\
                    </li>';

                    // 使用闭包绑定标题点击事件
                    (function(data, index){
                        // 先解绑事件，避免事件重复绑定
                        $_this.off("click",".stephint>.stephint-list>ul>li:eq(" + (index) +")>h5>a");
                        if (data.onClick) {
                            $_this.on("click",".stephint>.stephint-list>ul>li:eq(" + (index) +")>h5>a",function(){ 
                                var curDatas = $_this.data('stephint').data;
                                data.onClick(curDatas, (index+1));
                            });
                        }; 
                    })(liData, index);

                    return liHtml;
                };

                // 获取当前data数据信息
                stepHint.getDatas = function(){
                    var curData = {};
                    curData = $_this.data('stephint').data;
                    return curData;
                };

                // 获取当前Attach数据信息
                stepHint.getAttach = function(){
                    var curAttach = {};
                    curAttach = $_this.data('stephint').showAttach;
                    
                    return curAttach;
                };

                // 更新步骤图数据状态--设置当前节点为doing节点或完成所有
                stepHint.update = function(opts){
                    var type;
                    // 若为更新数据行
                    if(typeof opts === 'object'){
                        var updateHtml = stepHint._createLi(opts.opts);
                        var $self = $_this.find('.stephint-list>ul>li').eq(opts.index-1);
                        // 此处存储Data
                        
                        $self.after(updateHtml);
                        $self.remove();
                    }else if(typeof opts == 'string' && opts == 'finish'){
                        // 若只更新行状态
                        type = 'finish';
                    };

                    // 处理前后数据行样式
                    stepHint._setCurrentState(opts, type);
                };

                // 更新当前行内容
                stepHint.updateNodeContent = function(opts){
                    // _datas 获取匹配元素相关的数据
                    var updateHtml = '', _datas = $_this.data('stephint');
                    var $self = $_this.find('.stephint-list>ul>li').eq(opts.index-1).find('>div');
                    for (var i = 0; i < opts.childData.length; i++) {
                        updateHtml += '<span>' + opts.childData[i] + '</span>';
                    };
                    // 更新当前data数据
                    _datas.data[opts.index-1]['childData'] = opts.childData;
                    $self.html(updateHtml);
                };

                // 设置附加信息内容
                stepHint.setAttach = function(attachObjs){

                    var attachHtml = '';
                    for (var k = 0; k < attachObjs.length; k++) {
                        attachHtml += '<p>\
                            <span class="stephint-attach-title">' + attachObjs[k].title + ':&nbsp</span><span>' + attachObjs[k].text + '</span></p>';
                    };

                    // 添加元素并动态设置宽度(将stephint-attach宽度设置与stephint-list相同)
                    $_this.find('.stephint>.stephint-attach').html(attachHtml)
//                    .width($_this.find('.stephint>.stephint-list').width())
                    .find('p>.stephint-attach-title').each(function(index, ele){
                        var $ele = $(ele);
                        // 动态设置信息内容的宽度
                        $ele.next().width($ele.parent().width()-$ele.width()-1);
                    });
                    
                    $_this.find('.stephint>.stephint-list').css('paddingBottom', ($_this.find('.stephint>.stephint-attach').height()+5)+'px');
                };

                // 设置当前状态行(更新前后数据样式) 
                // TODU--更新绑定事件时，需同时更新
                stepHint._setCurrentState = function(opts, type){
                    
                    // 获取的当前进行中的数据行
                    var $lis = $_this.find('.stephint-list>ul>li');
                    var rowLen = $lis.length;
                    // 获取匹配元素相关的数据
                    var _datas = $_this.data('stephint');
                    // debugger;
                    // 判断是否设置流程完成
                    if(!type){
                        var doingRowIndex = opts.index-1;
                        for (var i = 0; i < rowLen; i++) {
                            // 处理正在进行的数据行前后样式
                            if (i < doingRowIndex) {
                                $_this.find('.stephint-list>ul>li').eq(i)
                                      .removeClass('stephint-doing').addClass('stephint-finish');
                                
                                // 更新全局数据
                                _datas.data[i].state = 'finish';
                            }else if(i > doingRowIndex){
                                $_this.find('.stephint-list>ul>li').eq(i)
                                      .removeClass('stephint-doing stephint-finish');
                                
                                if (_datas.data[i-1]) {
                                    // 更新全局数据
                                    _datas.data[i-1].state = '';
                                };
                            }else{
                                // i == doingRowIndex
                                // 更新全局数据
                                _datas.data[i-1] = opts.opts;
                            };
                        };
                    }else{
                        $lis.removeClass('stephint-doing stephint-finish').addClass('stephint-finish');
                    };

                    // 更新当前匹配元素相关的数据
                    $_this.data('stephint', _datas);
                };
                
                // 判断是初始化还是调用方法
                if (fnType) {
                    stepHint[fnType](options);
                }else{
                    stepHint._init(options);
                };

            });
        };
        
        // 判断是插件初始化或刷新方法调用
        if (type === 'object') {
            _opts = $.extend(true, {}, $.fn.stephint.options, options || {});
        }else if(type === 'string'){
            fnType = options;
            if((content instanceof Object) && !(content instanceof Array)){
                _opts = $.extend(true, {}, $.fn.stephint.options, content || {});
            }else{
                _opts = content;
            };
        };
        return StepHint.initPlugIn(_opts, fnType);
    };

    $.fn.stephint.options = {
        cssStyle: {
            width: '100%',
            height: '100%'
        }
    };

})(jQuery);
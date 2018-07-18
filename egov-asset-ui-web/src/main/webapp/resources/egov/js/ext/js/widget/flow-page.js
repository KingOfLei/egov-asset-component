/**
 * 对app-tabs的扩展将横向tabs变为纵向
 *
 * $.uiExtend({
                type: 'flow-page',
                options: {
                    title: '流程页面', dialogId: 'aaa1'，
                    hasFoot:true,
                    footHeight:200,
                    footInner:'<span></span>',
                },
                tabsOp: {
                    type: 'flow-tabs',
                    beforeClick: function () {
                    },
                    afterClick: function () {
                    },
                    data: {
                        title: '',
                        tabs: [
                            {
                                text: '全部',
                                num: 5,
                                url:'egov/aims/card/aimscard6/showAimsCard6.do',//页面url
                                params:{},//参数
                                onPageLoad:function(page){
                                    page.FlowTabs.addButtons([//按钮 text 显示文字，method assetFlow2 page对象的方法
                                    {
                                        text: 'btn1',
                                        method: 'test1'
                                    },
                                    {
                                        text: 'btn2',
                                        method: 'test2'
                                    }
                                    ]);
                                }
                            }
                        ]
                    }
                },
                complete: function () {
                    console.log(arguments);
                }
            });
 */
define(["resources/egov/js/ext/js/util/common-util"
    ,"resources/egov/js/ext/js/widget/flow-tabs"], function (util) {
    var FlowPage = function (options) {
        $.extend(this, options);
        this.init();
        this.render();
    };
    FlowPage.prototype = {
        init:function () {
            var self = this;
            var options = !!self.options?self.options:self.options={};
            //显示之后调用
            self.beforeShow = $.isFunction(options.beforeShow)?options.beforeShow:function () {};
            if (options.drawable !== true) {
                options.drawable = false;
            }
            options.height = $(window).height();
            options.width = $(window).width();
            options.hasheader = false;
            options.dialogId = !!options.dialogId?options.dialogId:('flowpage'+util.nextId());
            self.tabsOp.hasFoot = options.hasFoot;
            var tabsCompleted = self.tabsOp.complete;
            self.tabsCompleted = $.isFunction(tabsCompleted)?tabsCompleted:function () {};
        },
        render:function () {
            var self = this;
            var options = self.options;
            var closeCallback = $.isFunction(options.closeCallback)?options.closeCallback:$.noop;
            //自定属性
            $.extend(options,{
                url:'<div class="dialog-content" style="overflow: hidden"><div class="flow-page"></div></div>',
                mode:'html',
                beforShow:function ($dlg) {
                    $dlg.data('flowpage',self);

                    var tabsCompleted = $.isFunction(self.tabsOp.completed)?self.tabsOp.completed:function () {};
                    //设置流程页面初始化回调
                    self.tabsOp.completed = function (e,flowTabs) {
                        var pageHeight = $dlg.find('.dialog-content').height();
                        pageHeight = pageHeight - flowTabs.$head.height();
                        if(options.footHeight){
                            flowTabs.$foot.height(options.footHeight);
                            pageHeight = pageHeight- options.footHeight;
                        }

                        if(typeof options.footInner !== "undefined"){
                            flowTabs.$foot.html(options.footInner);
                        }
                        flowTabs.$content.height(pageHeight);
                        self.tabsCompleted(flowTabs);
                        $dlg.data('flowtabs',flowTabs);

                        //关闭按钮添加
                        var closeBtn = $('<a class="fg-close">×</a>');
                        flowTabs.$title.append(closeBtn);
                        closeBtn.on('click',function (event) {
                            $A.dialog.close($A.dialog.getCurrent(),event);
                        });

                        //custom invoke
                        tabsCompleted();
                    };
                    $dlg.data('flowPage',self);
                    $dlg.find('.flow-page').uiExtend(self.tabsOp);
                    self.beforeShow($dlg);
                }
            });

            $.openModalDialog(options);
        },
        bind:function () {

        }
    }
    return FlowPage;
});

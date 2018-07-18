define(["resources/egov/js/ext/js/util/common-util"],
    function (util) {
    var messager = null;
    messager = {
        _id:'_assetMessage_box',
        _contentId:'_assetMessage_box_content',
        defaultOptions: {
            title:'',
            ok:true,
            okVal:'确定',
            cancel:false,
            cancelVal:'取消',
            drag:true,
            lock:true,
            opacity:0.0
        },
        //欺骗方法 TODO
        close:function(){

        },
        init: function (msg, options) {
            this.id = this._id+util.nextId();
            if(!!!options){
                options = {};
            }
            if(!!!options.id){
                options.id = this.id;
            }
            if(!!options.okName){
                options.okVal = options.okName;
            }
            if(!!options.okCall){
                options.ok = options.okCall;
            }
            if(!!options.cancelName){
                options.cancelVal = options.cancelName;
            }
            if(!!options.cancelCall){
                options.cancel = options.cancelCall;
            }
            this._options = {};
            $.extend(true,this._options, this.defaultOptions);
            $.extend(this._options, options);
            this._options.msg = msg;
        },
        show: function () {
            var options = this._options;
            var $content = $('<div class="art-dialog-content">');
            $content.prop('id',this._contentId);
            $content.width(options.width);
            $content.height(options.height);
            if (!!!options.msg) {
                options.msg = options.message;
            }
            $content.html(options.msg);
            options.content = $content[0].outerHTML;
            var dialog = this.list()[this.id];
            if(!!dialog){
                dialog.close();
            }
            $._amdialog(options);
        },
        success: function (msg, options) {
            //居中显示
            this.init(msg, options);
            this._options.icon = 'succeed';
            //this._options.time = 3;
            this.show();
        },
        correct: function (msg, options) {
            this.success();
        },
        error: function (msg, options) {
            //居中显示
            this.init(msg, options);
            this._options.icon = 'error';
            this.show();
        },
        warn: function (msg, options) {
            //居中显示
            this.init(msg, options);
            this._options.icon = 'warning';
            this.show(this._options);
        },
        /**
         * 通用信息对话框
         * @param msg
         * @param options
         */
        info: function (msg, options) {
            this.init(msg, options);
            this.show();
        },
        /**
         * 提示对话框
         * @param msg
         * @param options
         */
        confirm: function (msg,options) {
            this.init(msg,options);
            options = this._options;
            if(!!!options.icon){
                options.icon = 'question';
            }
            if(!!!options.ok){
            	options.ok = true;
            }
            if(!!!options.cancel){
            	options.cancel = true;
            }
            
            this.show();
        },
        appendMsg:function (msg,options) {
            if(options&&options.type){
                var dialog = this.list()[this.id];
                var newLine = '<br/>';
                options.newLine&&(newLine = options.newLine);
                if(!!dialog){
                    var oldTxt = $('#'+this._contentId).html();
                    $('#'+this._contentId).html(oldTxt+newLine+msg);
                }else{
                    this[options.type](msg,options);
                }

            }else{
                this.info(msg,options);
            }
        },
        ajaxProgressShow:function () {
            $._amdialog({id:'progress',title:''});
        },
        ajaxProgressHide:function () {
            var dialog = $._amdialog.get('progress');
            if(!!dialog){
                dialog.close();
            }
        },
        list:function () {
            return $.artDialog.list;
        }
    };
    return messager;

});
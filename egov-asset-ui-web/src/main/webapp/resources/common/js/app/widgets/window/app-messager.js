define(
	[
		"app/core/app-core",
		"app/widgets/app-frags",
		"app/widgets/app-lang_zh_CN"
		],
	function($A,$frags,$lang) {
	var $msglang = $lang.messager;
	var that = null;
	that = {
			
		/**
		 * 消息框id
		 */
		_boxId: "#_alertMsgBox",
		/**
		 * 消息框id
		 */
		_contentId: "_alertMsgBox_content",

		/**
		 * Tips框ID
		 */
		_tipsId:"#_tipsMsgBox",
		
		/**
		 * 消息框背景
		 */
		_bgId: "#_alertBackground",
		
		/**
		 * 关闭时长
		 */
		_closeTimer: null,
		
		/**
		 * 消息类型
		 */
        _types: {error: "error", info: "info", warn: "warn", correct: "correct", confirm: "confirm",success:"success"},

        _typesOptions: {
            error: {icon: 'error'},
            info: {icon:''},
            warn: {icon: 'warning'},
            confirm: {icon: 'question'},
            success: {icon: 'succeed'},
        },

		/**
		 * 取得消息框标题
		 */
		_getTitle: function(key){
			return $msglang[key.toUpperCase()];
		},
		
		/**
		 * 按回车键事件
		 */
		_keydownOk: function(event){
			if (event.keyCode == $A.keyCode.ENTER) event.data.target.trigger("click");
		},
		
		/**
		 * 按取消键事件
		 */
		_keydownEsc: function(event){
			if (event.keyCode == $A.keyCode.ESC||event.keyCode == $A.keyCode.ENTER) event.data.target.trigger("click");
		},
		toggleDetail:function(){
			$('#__alertDetails').toggle();
			this.relayout();
		},
		relayout:function(){
			var $box=$(this._boxId);
			var pos = $box.attr("pos");
			if(pos == "top"){
				$box.css( {"left":($(window).width() - $box.width())/2 + "px","top":"0px"});
			}else if(pos=="bottom"){
				$box.css( {"bottom":"0px","right":"0px"});
			}else{
				$box.css( {"left":($(window).width() - $box.width())/2 + "px","top":($(window).height() - $box.height())/2 + "px"} );
			}
		},

		
		/**
		 * 关系消息框
		 */
		close: function(){
            var dialog = this.list()[this._boxId];
            if(!!dialog){
                dialog.close();
            }
		},
		
		/**
		 * 打开错误消息框
		 */
		error: function(msg, options) {
			this._alert(this._types.error, msg, options);
		},

		/**
		 * 打开成功消息框
		 */
		success: function(msg, options) {
			this._alert(this._types.success, msg, options);
		},

		/**
		 * 打开普通消息框
		 */
		info: function(msg, options) {
			this._alert(this._types.info, msg, options,"bottom");
		},
		
		/**
		 * 打开警告消息框
		 */
		warn: function(msg, options) {
			this._alert(this._types.warn, msg, options);
		},

        /**
         * 打开确认消息框
         * @param {Object} msg 消息内容
         * @param {Object} options {okName, okCal, cancelName, cancelCall} 按钮
         */
        confirm: function(msg, options) {
        	if(!!!options){
        		options = {};
			}
            if(!!!options.cancel){
                options.cancel = true;
            }
            this._alert(this._types.confirm, msg, options);
        },


		getOptions:function (type,msg,options) {
        	var op =  {
                title:'',
                ok:true,
                okVal:'确定',
                cancel:false,
                cancelVal:'取消',
                drag:true,
                lock:true,
                opacity:0.0
            };
            if(!!!options){
                options = {};
            }
            if(!!!options.id){
                options.id = this._boxId;
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
            options.msg = msg;
            if (!!!options.msg) {
                options.msg = options.message;
            }
            //设置图标
            options.icon = this._typesOptions[type].icon;
            $.extend(op, options);

            var $content = $('<div class="art-dialog-content">');
            $content.prop('id',this._contentId);
            $content.width(options.width);
            $content.height(options.height);
            $content.html(options.msg);
            op.content = $content[0].outerHTML;
			return op;
        },
        /**
         * 打开消息框
         */
        _alert: function(type, msg, options) {
            var ops = this.getOptions(type,msg,options);
			this._open(ops);
        },

        /**
         * 打开消息框
         * @param {Object} type 消息类型
         * @param {Object} msg 消息内容
         * @param {Object} buttons [button1, button2] 消息按钮
         */
        _open: function(options){
			var dialog = this.list()[this._boxId];
			if(!!dialog){
				dialog.close();
			}
			dialog = $.artDialog(options);
            return dialog;
        },
		
		/**
		 * 打开成功消息框
		 */
		correct: function(msg, options) {
			//this._tips(this._types.correct, msg, options,"center");
		},
		

		
		_tips:function(type,msg,options,pos){
			$(this._tipsId).remove();
			var msgObj = {
					"icon":type,
					"message":msg
			};
			
			options = options||{};
			var displayTime = options.displayTime || 1500;//默认两秒
			var fadeSpeed = options.fadeSpeed || 800;//默认两秒
			
			var tpl = $frags["tipsBoxFrag"];
			var boxHtml = $template(tpl,msgObj);
			
			$(boxHtml).appendTo("body");
			
			var $box=$(this._tipsId);
			$box.show();
			$box.attr("pos",pos);
			var pos = $box.attr("pos");
			if(pos == "top"){
				$box.css( {"left":($(window).width() - $box.width())/2 + "px","top":"0px"});
			}else if(pos=="bottom"){
				$box.css( {"bottom":"0px","right":"0px"});
			}else{
				$box.css( {"left":($(window).width() - $box.width())/2 + "px","top":45 + "px"} );
			}
			$box.show();
			setTimeout(function(){
/*				$box.fadeOut(fadeSpeed,function(){
					$box.remove();
				});*/
				$box.animate({
					"top":"-=38",
					"opacity":0
				},fadeSpeed,function(){
					$box.remove();
				});
				
			},displayTime);
		},
		list:function () {
			return $.artDialog.list;
        }

	};
	$A.messager = that;
	return $A.messager;
});


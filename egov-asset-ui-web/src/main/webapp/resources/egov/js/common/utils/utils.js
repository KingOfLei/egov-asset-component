define([],function(){
	
	return {
		/**
		 * 核验联系方式
		 */
		checkPhoneNumber:function(value, element){
			   var isMobile=/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/;  //手机号码验证规则
	           var isPhone=/^(\d{3,4}\-?)?\d{6,8}$/;   //座机验证规则
	           if(value && !isMobile.test(value) && !isPhone.test(value)){
	        	   return false;
	           }
	           return true;
		},
		/**
		 * 联系人
		 */
		checkStaff:function(value){
			var isStaff = /^[\u4e00-\u9fa5a-zA-Z]+$/;
			if(value && !isStaff.test(value)){
				return false;
			}
			return true;
		},
		/* public 限制文本宽长度
         * params: $ele 文本对象 ；len 长度
         */
		limitInputLength:function($ele,len){
			$ele.on('change keyup blur keydown',function(){
	            if(this.value.length>len){
	              this.value = this.value.substr(0,len);
	            }
	        });
		},
		
		/* 弹出带遮罩层的页面
         * author: zxy
         * date: 2017.6.30
         * alertMaskPage({type:'version',fn:function(){XXX}});
         * 		type: 弹出页面类型
         * 		fn: 关闭遮罩层 后执行的事件
         */
		alertMaskPage:function(options){
			var maskHtml = '';
			switch(options.type){
				case 'version':
					maskHtml = '<div class="alert-mask">\
                        <div class="alert-mask-cell">\
                            <div class="alert-mask-center" style="height:auto;">\
                                <div class="new-version">\
									<div class="new-version-btn";></div>\
								</div>\
                            </div>\
                            <div class="alert-mask-close" onclick="$(\'.alert-mask\').remove();"></div>\
                        </div>\
                    </div>';
                    $('body').append(maskHtml);
                    $('.new-version-btn').on('click', function(){
                    	$('.alert-mask').remove();
                    	options.fn();
                    });
					
                    break;
			};
			
		},
		/* 弹出带遮罩层的提示
         * author: xds
         * date: 2017.7.03
         *  month2Year(13) ---> 1年1月 
         *  month2Year(1) ---> 1月
         *  month2Year(0) ---> ''
         *  month2Year(-1) ---> ''
         *  month2Year() ---> ''
         */
		month2Year:function(value){
			if(value == undefined || value == null || value == '' || value <= 0){
				return '';
			}
			var _mod = value%12;//求余
			var _int = parseInt(value/12);//除整
			if(_mod == 0){
				return _int + "年";
			} else { //	
			  if(_int == 0){
				  return _mod + "个月";
			  } else {
			      return _int + "年" + _mod + "个月";
			  }
			}
		}
		
	}
});
/* 
 * 文件预览插件:
 * 		1.封装jQuery.viewer.js: 控制预览页面布局
 * author: zhangxiaoyun
 * date: 2017.7.17
 */

;(function($){
    $.fn.filePreview = function(options, content) {
        var FILEPreview = {};
        var _this = this, _opts = {}, fnType;
        var type = typeof options;

        // PDF预览插件初始化
        FILEPreview.initPlugIn = function (options, fnType) {
            _this.each(function() {
                var $_this = $(this);
                var FilePreview = {};

                // 初始化
                FilePreview.init = function(){
                	// 根据文件后缀选择文件打开方式
                    var point = options.file_src.lastIndexOf(".");
                    // 获取后缀
                    var fileType = options.file_src.substr(point);
                    // 过滤后缀的问号及之后的参数
                    fileType = fileType.slice(0, fileType.indexOf("?")>0?fileType.indexOf("?"):fileType.length);
                    
                    // 判断若为IE8浏览器 则不预览直接显示
                    var browser = FilePreview.checkBrowser();
                    if(browser.browser === "Explorer" && browser.version === 8) {
                    	// 下载文件
                    	FilePreview.saveFiles(options.download_src);
                    }else{
                    	var fileInfo = FilePreview.checkOfficeFiles(fileType.toLowerCase());
                    	// 其他浏览器  用switch判断若为office文档直接下载
                    	if(!fileInfo.openPreview){
                    		FilePreview.saveFiles(options.download_src);
                    	}else{
                    		this.creatMask(fileType, fileInfo.openPreview);
                    	};
                    };
                    
				};
				
				// 校验是否为office文件类型
				FilePreview.checkOfficeFiles = function(fileType){
					var fileInfo = {
						openPreview : false
					};
					var allowType = ['.doc','.docx','.xls','.xlsx','.html','.jpg','.pdf'];
					for(var i = 0; i < allowType.length; i++){
						if(fileType === allowType[i]){
							fileInfo.openPreview = allowType[i];
							break;
						};
					};
					
					return fileInfo;
				};
				
//				// 下载图片可用
//				FilePreview.savepic = function(src){
//					$_this.attr('href', src);
//                	if (document.all.a1 == null) { 
//                		objIframe = document.createElement("IFRAME"); 
//                		document.body.insertBefore(objIframe); 
//                		objIframe.outerHTML = "<iframe name=a1 style='width:400px;hieght:300px' src=" + $_this.attr('href') + "></iframe>"; 
//                		re = setTimeout(function(){
//                			FilePreview.savepic();
//                		}, 1);
//            		}else { 
//                		clearTimeout(re);
//                		pic = window.open($_this.attr('href'), "a1"); 
//                		// IE8弹出保存弹框点击取消后出现未知报错，暂时使用此方式捕获报错
//                		try{
//                    		pic.document.execCommand('SaveAs');
//                		}catch(e){};
//                		document.all.a1.removeNode(true);
//            		}
//				};
				
				// IE8 测试通过：图片新页面自动打开，xls和doc文件等office文件提示打开或保存文件，pdf在有插件情况下可直接打开
				FilePreview.saveFiles = function(src){
					try{    
			            $('body').append('<iframe id="_previewDownloadIframe" src="' + src + '" style="display: none;"></iframe>');
					}catch(e){};
				};

                // 创建遮罩层
                FilePreview.creatMask = function(fileType, previewType){
                    var srcInfo = options.projectPath + options.file_src;
                    // viewerjs插件路径
                    options.viewerjsPath = "resources/egov/js/common/thirdparty/viewerjs/";

                    if(fileType.toLowerCase() === '.pdf'){
                        srcInfo = options.viewerjsPath + "viewer.jsp";
                    };
                    var contentHeader = '', rotateHtml = '';
                    // 若存在头部参数，则创建头部标签布局
                    if(options.header){
                    	var btnHtml = '';
                    	if(options.header.btn){
                    		for(var i = 0; i < options.header.btn.length; i++){
                    			btnHtml += '<span>' + options.header.btn[i].name + '</span>';
                    			// 闭包
                    			(function(btn, i){
                    				// 先解绑事件，避免事件重复绑定
                    				$(document).off("click",".file-mask .file-content-header>span:eq(" + i +")");
                        			$(document).on("click",".file-mask .file-content-header>span:eq(" + i +")",function(){ 
                        				btn[i].fn();
                        			}); 
                    			})(options.header.btn, i);
                    		};
                    	};
                    	contentHeader = '<div class="file-content-header"><h3>' + options.header.title + '</h3>' + btnHtml + '</div>';
                    };
                    
                    // 预览附件若为图片，则显示旋转按钮
                    if(previewType === '.jpg'){
                    	rotateHtml = '<div class="file-mask-rotate"></div>';
                    };
                    var maskHtml = '<div class="file-mask">\
						<div class="file-mask-close"></div>' + rotateHtml + '\
						<div class="file-content" style="background-color:white">' + contentHeader + '\
							<iframe id="filepreview-iframe" _pdf-src="' + options.projectPath + options.file_src + '" src="' + srcInfo + '" scrolling="auto"></iframe>\
						</div>\
					</div>';
                    $('body').append(maskHtml);

                    $(".file-mask-close").on('click', function() {
                        if(options.closeCallback){
                            options.closeCallback();
                        };
                        FilePreview.closeMask();
                    });
                    
                    // 绑定旋转事件
                    if(rotateHtml){
                		$.getScript("resources/egov/js/ext/js/lib/jquery.rotate.2.2.js",function(response,status){
                			var num = 0; 
                            $(".file-mask-rotate").on('click', function() {
                            	num ++; 
                            	var imgObj = document.getElementById("filepreview-iframe").contentWindow.document.getElementsByTagName("img");
                    			$(imgObj).rotate(90*num);
                    			if(num % 2 == 1 ){
                    				imgObj[0].style.marginTop=Math.abs(imgObj[0].width-imgObj[0].height)/2+'px';
                    			}else{
                    				imgObj[0].style.marginTop='0px';
                    			}
                            });
            	        });
                    }

                    // 处理预览图片时，图片过大，导致无法看到大致图片内容
                    var deptObjs= document.getElementById("filepreview-iframe");
                    deptObjs.onload = function(){
	                	var imgObj = document.getElementById("filepreview-iframe").contentWindow.document.getElementsByTagName("img"); 
	                	var divBox = document.getElementById("filepreview-iframe").contentWindow.document.getElementById("attachment2HtmlContainer");
	                	// 用于区分图片和转换为html的文档
	                	if(imgObj.length == 1 && !divBox){
	                    	imgObj[0].style.width="auto";
		                	imgObj[0].style.maxWidth="100%";
	                	};
                	};

                };

                // 销毁预览层
                FilePreview.closeMask = function(){
                    $('.file-mask').remove();
                };

                // 校验浏览器版本
                FilePreview.checkBrowser = function(){
                    var BrowserDetect = {
                        init: function () {
                            this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
                            this.version = this.searchVersion(navigator.userAgent) || this.searchVersion(navigator.appVersion) || "an unknown version";
                            this.OS = this.searchString(this.dataOS) || "an unknown OS";
                            var browerInfo = {
                                browser:this.browser,
                                version:this.version,
                                OS:this.OS
                            };
                            return browerInfo;
                        },
                        searchString: function (data) {
                            for (var i=0;i<data.length;i++)   {
                                var dataString = data[i].string;
                                var dataProp = data[i].prop;
                                this.versionSearchString = data[i].versionSearch || data[i].identity;
                                if (dataString) {
                                    if (dataString.indexOf(data[i].subString) != -1){
                                        return data[i].identity;
                                    }
                                }else if (dataProp) {
                                    return data[i].identity;
                                }
                            }
                        },
                        searchVersion: function (dataString) {
                            var index = dataString.indexOf(this.versionSearchString);
                            if (index == -1) return;
                            return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
                        },
                        dataBrowser: [
                            {
                                string: navigator.userAgent,
                                subString: "Chrome",
                                identity: "Chrome"
                            },
                            {      
                                string: navigator.userAgent,
                                subString: "OmniWeb",
                                versionSearch: "OmniWeb/",
                                identity: "OmniWeb"
                            },
                            {
                                string: navigator.vendor,
                                subString: "Apple",
                                identity: "Safari",
                                versionSearch: "Version"
                            },
                            {
                                prop: window.opera,
                                identity: "Opera",
                                versionSearch: "Version"
                            },
                            {
                                string: navigator.userAgent,
                                subString: "Firefox",
                                identity: "Firefox"
                            },
                            {             // for newer Netscapes (6+)
                                string: navigator.userAgent,
                                subString: "Netscape",
                                identity: "Netscape"
                            },
                            {
                                string: navigator.userAgent,
                                subString: "MSIE",
                                identity: "Explorer",
                                versionSearch: "MSIE"
                            },
                            {
                                string: navigator.userAgent,
                                subString: "Gecko",
                                identity: "Mozilla",
                                versionSearch: "rv"
                            },
                            {             // for older Netscapes (4-)
                                string: navigator.userAgent,
                                subString: "Mozilla",
                                identity: "Netscape",
                                versionSearch: "Mozilla"
                            }
                        ],
                        dataOS : [
                            {
                                string: navigator.platform,
                                subString: "Win",
                                identity: "Windows"
                            },
                            {
                                string: navigator.platform,
                                subString: "Mac",
                                identity: "Mac"
                            },
                            {
                                string: navigator.userAgent,
                                subString: "iPhone",
                                identity: "iPhone/iPod"
                            },
                            {
                                string: navigator.platform,
                                subString: "Linux",
                                identity: "linux"
                            }
                        ]
                    };
                    return BrowserDetect.init();
                };

                FilePreview.init();
            });
        };

        // 判断是插件初始化或刷新方法调用
        if (type === 'object') {
            _opts = $.extend(true, {}, $.fn.filePreview.options, options || {});
        }else if(type === 'string' && options === 'refresh'){
            fnType = 'refresh';
            _opts = $.extend(true, {}, $.fn.filePreview.options, content || {});
        };

        return FILEPreview.initPlugIn(_opts, fnType);
    };

    $.fn.filePreview.options = {
        width:'',
		height:'',
		closeCallback: function(){}
    };

})(jQuery);
define(["app/core/app-jquery",
        "app/util/app-utils",
        "app/widgets/form/app-upload"
        ],
    function (jq, Utils) {
        jq.fn.extend({
            "createAttach": function (groupId, bizType, itemType, options) {
                var settings = $.extend({
                    bizStatus: -1
                }, options);
                var url = "attachment/getItems.do";
                var _self = this;
                // 先清空
                _self.empty();
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: {
                        bizType: JSON.stringify(bizType),
                        itemType: JSON.stringify(itemType)
                    },
                    dataType: "json",
                    success: function (data) {
                        // 获取到值
                        for (var i = 0; i < data.length; i++) {
                            // 新增
                            createHtml(_self, data[i], groupId, settings);
                        }
                    }
                });
            },
            //校验附件
            "checkAttachmentRequired":function(options){
            	var settings = $.extend(true,{
                    bizStatus: -1,
                    groupId: "",
                    bizType: "",
                    itemType: "",
                    grade: "",
                    bizId:"",
                    checkType:0,
                    excludeItemCode:"",//排除的itemCode
                    includeItemType:"",//额外添加的bizType
                    callback:function(data){
                	}
                }, options);
            	if(settings.itemType == ""){
            	  settings.itemType = [];
            	} else {
            	  settings.itemType = settings.itemType.split(',');
            	}
            	if(settings.excludeItemCode == ""){
            		settings.excludeItemCode = [];
            	} else {
            	   settings.excludeItemCode = settings.excludeItemCode.split(',');
            	}
            	if(settings.includeItemType == ""){
            		settings.includeItemType = [];
            	} else {
            	   settings.includeItemType = settings.includeItemType.split(',');
            	}
                var url = "attachment/checkAttachmentRequired.do";
                if(settings.checkType == 1){
                	url = "attachment/checkIncludeAtLeastOneAttachment.do";
                }
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: "json",
                    data: {
                        bizType: JSON.stringify(settings.bizType),
                        itemType: JSON.stringify(settings.itemType),
                        excludeItemCode: JSON.stringify(settings.excludeItemCode),
                        includeItemType: JSON.stringify(settings.includeItemType),
                        grade: settings.grade,
                        bizId: settings.bizId
                    },
                    success: function (data) {
                    	settings.callback(data,settings.checkType);
                    }
                });
                
            },
            "createNewAttach": function (options) {
                var settings = $.extend(true,{
                    bizStatus: -1,
                    groupId: "",
                    bizType: "",
                    itemType: "",
                    grade: "",
                    excludeItemCode:"",//排除的itemCode
                    includeItemType:"",//额外添加的bizType
                }, options);
                if(settings.itemType == ""){
              	  settings.itemType = [];
              	} else {
              	  settings.itemType = settings.itemType.split(',');
              	}
              	if(settings.excludeItemCode == ""){
              		settings.excludeItemCode = [];
              	} else {
              	   settings.excludeItemCode = settings.excludeItemCode.split(',');
              	}
              	if(settings.includeItemType == ""){
              		settings.includeItemType = [];
              	} else {
              	   settings.includeItemType = settings.includeItemType.split(',');
              	}
//                settings.itemType = settings.itemType.split(',');
//                settings.excludeItemCode = settings.excludeItemCode.split(',');
//                settings.includeItemType = settings.includeItemType.split(',');
                var url = "attachment/getItems.do";
                var _self = this;
                // 先清空
                _self.empty();

                $.ajax({
                    url: url,
                    type: 'POST',
                    data: {
                        bizType: JSON.stringify(settings.bizType),
                        itemType: JSON.stringify(settings.itemType),
                        excludeItemCode: JSON.stringify(settings.excludeItemCode),
                        includeItemType: JSON.stringify(settings.includeItemType),
                        grade: settings.grade
                    },
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        // 获取到值
                        for (var i = 0; i < data.length; i++) {
                            // 新增
                            newHtml(_self, data[i], options.groupId, settings, i);
                        }
                        $.loadRemark(options.attachId);
                        //TODO
                        var $context='';
                        if(options.attachId){
                             $context = $("#"+options.attachId);
                        }
                        else{
                            $context = $('#common-attachment-div');
                        }
                        
                        var $body = $('body');

                        $context.find('.upload-remark').hover(function (e) {
                            $(e.target).removeClass('remark-required');
                        });
//                        $context.tooltip();
                    }
                });
            }

        });

        function createHtml(self, item, groupId, options) {
            var id = groupId + "_" + item.bizType + "_" + item.itemCode;
            var required = '<font class="attachment-item-remark" style="font-size:14px">' + (item.required == '1' ? '*' : '') + "</font>";
            var $title = $('<p class="attachment-item-name" style="margin-bottom: 5px"><span title="'
                + item.itemRemark
                + '">' + required + '<u>'
                + item.itemName
                + '</span></u></p>');
            //<font class="hits">&nbsp;&nbsp;注：最大上传内容不要超过'
//				+ _getSize(item.itemMaxSize) + '</font>

            var $content = $('<div class="attachment-upload-' + id + '">'
                + '<input id="' + id + '" />' + '</div>');
            var $uploadDiv = $('<div class="upload-div">').append($title).append($content);
            self.append($uploadDiv);
            var $importText = $content.find('input');
            $importText.upload({
                groupId: id,
                uploadBtnText: '选择文件',
                requestFile: true,
                uploadUrl: "attachment/upload.do?bizType=" + item.bizType,
                deleteUrl: "attachment/delete.do",
                fileListUrl: "attachment/getFiles.do",
                exts: item.itemExts,
                canDelete: false,
                fileWidth: "200px",
                toolbarPosition: 'top',
                css: 'attachmentItemStyle',
                uploadSuccess: function (obj) {

                },
                afterDelete: function () {

                },
                uploadError: function () {

                }
            });
            $importText.data('attachment', item);
            var uploadSub = $importText.data('upload');
            uploadSub.getFileList = function () {
                var $file = uploadSub.$fileList.find('>div.upload-done'), files = [];
                $file.each(function (i, item) {
                    var file = {}, $file = $(item);
                    file.name = $file.attr('title');
                    file.id = $file.attr('fileid');
                    files.push(file);
                });
                return files;
            };
            uploadSub.disableUpload();
            $importText.uiExtend({
                type: 'upload',
                downloadUrl: 'attachment/download.do',
                fieldName: 'id',
                options: {
                    onFail: function (responseHtml, url, error) {
                    }
                }
            });

            return "";
        };


        function newHtml(self, item, groupId, options, i) {
            var id = groupId + "_" + item.bizType + "_" + item.itemCode;
            var requiredStr = '';
            //required 1 是 附件文字二选一  2时附件必须上传
            if(item.required == '1'){
            	requiredStr = '*';
            } else if(item.required == '2'){
            	requiredStr = '**';
            } ;
            
            // 格式化附件标题数字
            var formatNum = function(num, n){
            	return (Array(n).join(0) + num).slice(-n);
            };
            
            var required = '<span class="attach-required">' + requiredStr + "</span>";
            var $title = $('<div class="upload-head"><span>' + formatNum(i+1, 2) + '</span><p class="attachment-item-name upload-head-title"><b title="">' + required + '<u>' + item.itemName + '</b></u></p></div>');
            //附件项提示
            $title.find("u").tip(item.itemRemark, {
                position: 'top'
            });
            var $content = $('<div class="attachment-upload-' + id + ' attachment-list" title="单个附件大小不可超过5M!"><input id="' + id + '" /></div>');
            var $textarea = $('<div class="remark-div">\
                    <span>文字说明：</span>\
                    <input type="text" class="upload-remark" title="文字说明最少10个字符，至多60个字符" maxlength="65"/>\
                </div>\
            ');
            var $remark = $textarea.find('.upload-remark');
            $remark.attr('bizId',id);
            if(item.required==1){
                $textarea.find('upload-remark').attr('required','');
            }
            var $uploadDiv = $('<div class="upload-div" id="'+id+'" >').append($title).append($content).append($textarea);

            self.append($uploadDiv);
            var $importText = $content.find('input');

            //status 1 为可上传，删除(修改状态)
            var cancelDelete;
            var staus = (options.status == 1);
            //当状态为0 时 剩下不判断了 为1 时 在根据级次去判断
            if (staus) {
                if (item.grade == null) item.grade = '';
                //20470319 xds grade 为null
                if(options.grade == null) options.grade = '';                
                if (item.grade != options.grade) {
                    cancelDelete = false;
                } else {
                    cancelDelete = true;
                }
            } else {
                cancelDelete = false;
            }
            $importText.upload({
                groupId: id,
                uploadBtnText: '上传文件',
                ignorUploadError:true,
                requestFile: true,
                uploadUrl: "attachment/upload.do?bizType=" + item.bizType,
                deleteUrl: "attachment/delete.do",
                fileListUrl: "attachment/getFiles.do",
                exts: item.itemExts,
                createDownloadUrl:function (fileId) {
                    return 'attachment/download.do?id='+fileId;
                },
                canDelete: cancelDelete,
                fileWidth: "200px",
                toolbarPosition: 'top',
                uploadSuccess: function (obj) {
                	// 用于修改附件title显示值
                	$('[fileid=' + obj.id + ']>.file-name').add('[fileid=' + obj.id + ']').attr('title', obj.name + '（'+ obj.createDate2 + '）');
                },
                afterDelete: function () {

                },
                uploadError: function (obj) {
                    if($.type(obj)!=='object'){
                        return;
                    }
                    if($.type(obj.responseText) === 'undefined' || obj.responseText.indexOf('File upload failed,maybe file is too large!') >=0){
                        $messager.warn('上传失败，附件大小不可超过5M！');
                    }else{
                        $messager.warn('上传失败！');
                    }
                }
            });
            $importText.data('attachment', item);
            var uploadSub = $importText.data('upload');
            if (!cancelDelete) {
                uploadSub.disableUpload();
                uploadSub.readonly(true);
                $uploadDiv.find('.remark-div .upload-remark').attr('readonly','readonly').addClass('remark-disabled');
            }
//
            uploadSub.getFileList = function () {
                var $file = uploadSub.$fileList.find('.file-item'), files = [];
                $file.each(function (i, item) {
                    var file = {}, $file = $(item);
                    file.name = $file.attr('title');
                    file.id = $file.attr('fileid');
                    files.push(file);
                });
                return files;
            };
            
          //覆盖点击事件 设置成预览
            uploadSub.$element.unbind("click"); //移除click
            uploadSub.$element.on('click', '.file-name', function(e){
				var fileId = $(this).closest('.file-item').attr('fileId');
				var title = $(this).closest('.file-item').attr('title');
				//请求预览接口
				$a.showWaitScreen('正在加载中......'); 
				$A.ajax.ajaxCall({
	                    url:'attachment/preview.do',
	                    data:{id:fileId},
	                    type:'POST',
	                    callback:function (json,data) {
	                    	$a.hideWaitScreen();
	                    	if(json.flag == '-1'){ //判断文件是否存在 不存在直接提示
	                    		$a.messager.warn('附件[' + title + ']不存在！');
	                    		return;
	                    	} if(json.flag == '9'){ //预览异常
	                    		 $A.assetMsg.confirm(json.msg,{ok:function(){
	                    			 $.uiExtend({type:'download',url:"attachment/download.do?id=" + fileId,options:{onFail:function(){
     									
     								}}});
	                             },okName:'直接下载'});
	                    		return;
	                    	}else {
		                    	var path = "attachment/download.do?id=" + fileId;
		                    	if(json.path != null && json.path != ''){
		                    		path = json.path;
		                    	}
		                    	require(["resources/egov/js/ext/js/lib/jquery.filepreview"],function(){
		                    		$(this).filePreview({
			                            // 文件名
			                            file_src:path,
			                            // 文件下载路径
			                            download_src: "attachment/download.do?id=" + fileId,
			                            // 项目路径
			                            projectPath:$a.getHostUrl(),
			        					header: {
			        						title: title,
			        						btn:[{
			        							name:'下载',
			        							fn:function(){
			        								$.uiExtend({type:'download',url:"attachment/download.do?id=" + fileId,options:{onFail:function(){
			        									
			        								}}});
//			        								console.log('当前点击了下载按钮！');
			        							}
		
			        						}]
			        					}
			                        });	
		                    	});		        				
	                    	}
	                    },
	                    error:function(e){
	                    	$a.hideWaitScreen();
	                    }
	                });
			});
            
            // $importText.uiExtend({
            //     type: 'upload',
            //     downloadUrl: 'attachment/download.do',
            //     fieldName: 'id',
            //     options: {
            //         onFail: function (responseHtml, url, error) {
            //         }
            //     }
            // });
            return "";
        };


        function _getSize(size) {
            if (isNaN(size)) {
                return '';
            }
            var result = {};
            result.fileSize = (size / 1024).toFixed(2);
            result.unit = 'KB';
            if (result.fileSize > 1000) {
                result.fileSize = (size / 1048576).toFixed(2);
                result.unit = 'MB';
            }
            return Utils.formatNumber(result.fileSize) + result.unit;
        }
    });
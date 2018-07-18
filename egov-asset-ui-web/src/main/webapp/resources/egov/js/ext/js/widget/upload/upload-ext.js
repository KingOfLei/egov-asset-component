/**
 *  扩展对话框自动覆盖内容区域
 *  uiExtend({
 *  type:'dialog',
 *  options:{}//对话框配置
 *  })
 */
define(["resources/egov/js/ext/js/base/ext-base",
    "resources/egov/js/ext/js/util/common-util",
    "resources/egov/js/ext/js/widget/upload/upload-file-download",
    ],function(ExtBase,util,FileDownload) {

	var UploadExt = ExtBase.extend({
        Implements: [FileDownload],
        init:function(){
            var _self = this;
            _self.$fileList = _self.$component.siblings('.fileList');
		},
        bind:function () {
            var _self = this;
            _self.$fileList.on('click.uploadExt.fileDownload','.upload-done .fileName',function () {
                var $file = $(this).closest('.upload-done');
                var fileId = $file.attr('fileId');
                var url = _self.downloadUrl+'?'+_self.fieldName+'='+fileId;
                _self.download(url,_self.options)
            })

            _self.$fileList.on('mouseover.uploadExt.fileDownload','.upload-done .fileName',function(){
                $(this).css('cursor','pointer');
            });
            _self.$fileList.on('mouseout.uploadExt.fileDownload','.upload-done .fileName',function(){
                $(this).css('cursor','default');
            });

        },
        unbind:function () {
            var _self = this;
            self.$fileList.find('.upload-done .fileName').unbind();
        }
	});

	return UploadExt;
});

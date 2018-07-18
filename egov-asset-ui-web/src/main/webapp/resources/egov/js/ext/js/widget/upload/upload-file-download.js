/**
 *  文件下载扩展
 */
define(["resources/egov/js/ext/js/base/ext-base",
    "resources/egov/js/ext/js/util/common-util",
    "resources/egov/js/ext/js/lib/jquery-file-download/src/Scripts/jquery.fileDownload",
    ],function(ExtBase,util) {

	var FileDownload = ExtBase.extend({
	    init:function () {
            this.download(this.url,this.options)
        },
        download:function(url,options){
            $.fileDownload(url,options)
                .fail(options.onFail);
		}
	});

	return FileDownload;
});

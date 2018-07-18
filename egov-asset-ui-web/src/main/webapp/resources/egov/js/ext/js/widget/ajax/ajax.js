define(["resources/egov/js/common/utils/assetMessageUtils"],function (dialog) {
    $(document).ajaxStart(function(){
        //dialog.ajaxProgressShow();
    }).ajaxStop(function(){
        //dialog.ajaxProgressHide();
    }).ajaxError(function(){
        //dialog.ajaxProgressHide();
    })
});
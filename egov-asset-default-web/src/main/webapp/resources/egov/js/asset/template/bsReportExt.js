define(["bs-http-plugin/bs-print"],function(BsPrintX){
/*	   var _cookies = "";
		$app.ajax.ajaxCall({
			 url:"egov/asset/template/cookieString.do",
			 data:{},
			 async:false,
			 callback:function(cookieStr){
				 _cookies = cookieStr;
			 },error:function (data) {
           }
		});*/
	   var  printInstance = BsPrintX.getInstance({
           config:{
               appId:'AIMS'
           },
           onError:function () {
               console.log(arguments);
           },
           __cookies:'',
           initOptions:{
               Cookie:this.__cookies,
               HostUrl:$A.getHostUrl(),
               DownLoadUrl:'/egov/asset/template/downLoad.do',
               QueryTempNameUrl:'/egov/asset/template/queryTempName.do',
               QueryTempListUrl:'/egov/asset/template/queryTemplateDetailPage.do',
           }
       });
	   //创建窗体类单例
	   printInstance.getInstance=function(cookies){
		     if (!this.instance){
		    	 this.instance =new printInstance({__cookies:cookies});
		     }
		     return this.instance;
		 }
		
		return printInstance;
})
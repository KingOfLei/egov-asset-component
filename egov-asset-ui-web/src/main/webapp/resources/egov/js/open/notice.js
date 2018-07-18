//加载通知公告
var _apiUrl = 'http://58.22.61.222:9696/gov-asset-aims-web';
//TYPE 通知公告类型:通知0、公告1
$.ajax({
            type: "get",
            async: false,
            url: _apiUrl + "/open/notice/queryPortalNotice.do",
            dataType: "jsonp",
            jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
            jsonpCallback:"noticeHandler",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
            success: function(noticeJson){
               var $mainNotice = $(".main-notice");
               var $flowNotice = $('.flow-notice');
               $mainNotice.empty();
               for(var i = 0; i < noticeJson.length; i++){
               	   var notice = noticeJson[i];
               	   if(i == 0){
               	      $first = $('<div class="first-title">' + notice.title + '</div>');
               	      var content = notice.content;
               	      //截取文字
               	      if(GetLength(content) > 175){
               	         content = cutstr(content, 175);
               	      }
               	      $second = $('<div class="second-title" style="text-indent:2em;height:60px;" title="'+ content +'">' + content + '</div>');    	
               	      $mainNotice.append($first);
               	      $mainNotice.append($second); 
               	      $flowNotice.empty();              	      
               	   } else {
               	   	   var typeName = '[通知]';
               	   	   if(notice.type == '1'){
               	   	   	   typeName = '[公告]';
               	   	   }
               	   	 
               	   	   $row = $('<div class="notice-row"><a href="#">' + 
               	   	   	       '<span class="title-header">'+ typeName + '</span>' +
               	   	   	       '<span class="title" title="' + notice.title + '">' + notice.title + '</span>' + 
               	   	   	       '<span class="time"> ' + notice.realeaseDate + '</span>' + 
               	   	   	       '</a></div>')
               	   	   $flowNotice.append($row);
               	   }
               }
            }
        });
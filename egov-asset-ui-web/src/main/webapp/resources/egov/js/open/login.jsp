<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.bosssoft.platform.cas.server.configuration.CasConfig"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>登录</title>
    <meta
    content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
    name="viewport" />
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/frame/themes/bule/css/login.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/jsp/download-plugin/css/zebra_dialog.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/jsp/download-plugin/css/ocx-plugin.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/jsp/download-plugin/js/jquery.js"></script>
<%--<script type="text/javascript" src="<%=request.getContextPath() %>/frame/themes/bule/js/jquery1.42.min.js"></script>--%>
    <script type="text/javascript" src="<%=request.getContextPath() %>/frame/themes/bule/js/Validform_v5.3.2.js"></script>
    <!-- CSS -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/frame/themes/bule/js/MD5.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/frame/themes/bule/js/MD5.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/jsp/download-plugin/js/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/jsp/download-plugin/js/zebra_dialog.js"></script>


<%

boolean  isNeedVerity=CasConfig.isEnableVerifycode();

String uiLoginType=CasConfig.getCasUILoginType();

request.setAttribute("isNeedVerity", isNeedVerity);
request.setAttribute("uiLoginType", uiLoginType);
%>

<script>
 var _apiUrl = 'http://58.22.61.222:9696/gov-asset-aims-web';
    $(function () {
        var rootUrl = '<%=request.getContextPath() %>/jsp/download-plugin/';
        var d = undefined;
       $('#ocxBtn').click('click',function () {
           if(!!d){
               d.close();
           }
           d = new $.Zebra_Dialog({
               'title': '插件下载',
               'custom_class':  'ocx-box',
               width:600,
               height:400,
               'buttons':  false,
               'source':  {'ajax': rootUrl+'ocx-plugin-exists.html'},
               'modal': true
           });
       });
       $('#helpBtn').click('click',function () {
           if(!!d){
               d.close();
           }
           d = new $.Zebra_Dialog({
               'title': '文件操作',
               'custom_class':  'help-box',
               width:711,
               height:550,
               'buttons':  false,
               'source':  {'ajax': rootUrl+'asset-help-file.jsp'},
               'modal': false
           });
       });
    });

$(function(){

$(".i-text").focus(function(){
$(this).addClass('h-light');
});

$(".i-text").focusout(function(){
$(this).removeClass('h-light');
});

$("#username").focus(function(){
 var username = $(this).val();
 if(username=='输入账号'){
 $(this).val('');
 }
});

$("#username").focusout(function(){
 var username = $(this).val();

});


$("#password").focus(function(){
 var username = $(this).val();
 if(username=='输入密码'){
 $(this).val('');
 }
});


$("#validateCode").focus(function(){
 var username = $(this).val();
 if(username=='输入验证码'){
 $(this).val('');
 }
});

$("#validateCode").focusout(function(){
 var username = $(this).val();
 if(username==''){

 }
});



$("#loginForm").Validform({
	tiptype:function(msg,o,cssctl){
		var objtip=$(".error-box");
		objtip.text("");
		cssctl(objtip,o.type);
		if (o.type==3){
		
	
		objtip.text(msg);
		}
		
	},
	beforeSubmit:function(curform){
	
	
		var username=$.trim($("#username").val());
		var password=$.trim($("#password").val());
		 var pswd = MD5(username +"#" + password);
		 $("#password").val(pswd);
		return true;
	},
	ajaxPost:false
});

//加载通知公告
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
});

var GetLength = function (str) {
        ///<summary>获得字符串实际长度，中文2，英文1</summary>
        ///<param name="str">要获得长度的字符串</param>
        var realLength = 0, len = str.length, charCode = -1;
        for (var i = 0; i < len; i++) {
            charCode = str.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128) realLength += 1;
            else realLength += 2;
        }
        return realLength;
    };

    //js截取字符串，中英文都能用  
    //如果给定的字符串大于指定长度，截取指定长度返回，否者返回源字符串。  
    //字符串，长度  

    /** 
     * js截取字符串，中英文都能用 
     * @param str：需要截取的字符串 
     * @param len: 需要截取的长度 
     */
    function cutstr(str, len) {
        var str_length = 0;
        var str_len = 0;
        str_cut = new String();
        str_len = str.length;
        for (var i = 0; i < str_len; i++) {
            a = str.charAt(i);
            str_length++;
            if (escape(a).length > 4) {
                //中文字符的长度经编码之后大于4  
                str_length++;
            }
            str_cut = str_cut.concat(a);
            if (str_length >= len) {
                str_cut = str_cut.concat("...");
                return str_cut;
            }
        }
        //如果给定字符串小于指定长度，则返回源字符串；  
        if (str_length < len) {
            return str;
        }
    }
<%
String num = "1234567890abcdefghijklmnopqrstopqrstuvwxyz";
int size = 6;
char[] charArray = num.toCharArray();
StringBuffer sb = new StringBuffer();
for (int i = 0; i < size; i++) {
	sb.append(charArray[((int) (Math.random() * 10000) % charArray.length)]);
}
request.getSession().setAttribute("original_data", sb.toString());

// 设置认证原文到页面，给页面程序提供参数，用于产生认证请求数据包
request.setAttribute("original", sb.toString());
%>


function jitLogin(){
	<%
	String original=null,certAuthen =null;
	original = request.getAttribute("original")==null?null:request.getAttribute("original").toString();
	%>
	var Auth_Content = '<%=original%>';
	var DSign_Subject = $("#RootCADN").val();
	if(Auth_Content==""){
		alert("认证原文不能为空!");
		return;
	}else{
		//控制证书为一个时，不弹出证书选择框
		JITDSignOcx.SetCertChooseType(1);
		JITDSignOcx.SetCert("SC","","","",DSign_Subject,"");
		if(JITDSignOcx.GetErrorCode()!=0){
			alert("错误码："+JITDSignOcx.GetErrorCode()+"　错误信息："+JITDSignOcx.GetErrorMessage(JITDSignOcx.GetErrorCode()));
			return;
		}else {
			 var temp_DSign_Result = JITDSignOcx.DetachSignStr("",Auth_Content);
			 if(JITDSignOcx.GetErrorCode()!=0){
					alert("错误码："+JITDSignOcx.GetErrorCode()+"　错误信息："+JITDSignOcx.GetErrorMessage(JITDSignOcx.GetErrorCode()));
					return;
			 }
		//如果Get请求，需要放开下面注释部分
		//	 while(temp_DSign_Result.indexOf('+')!=-1) {
		//		 temp_DSign_Result=temp_DSign_Result.replace("+","%2B");
		//	 }
			$("#signed_data").val(temp_DSign_Result);
		}
	}
	$("#original_jsp").val(Auth_Content);
	
	var postData = {};
	
	$("#jitLoginForm").submit();
	//postData["RootCADN"] = $("#RootCADN").val();
	//postData["signed_data"] = $("#signed_data").val();
	//postData["original_jsp"] = $("#original_jsp").val();

}


</script>
</head>

<body id="body">

<div class="header">
	<h1 class="header-logo"></h1>
	<div class="header-nav">
			<span id="helpBtn" style="cursor: pointer" class="btn btn-no-img">操作文件</span>
			<span id="ocxBtn" style="cursor: pointer" class="btn">插件下载</span>
	</div>
</div>


<div class="authcenter">
<div class='authcenter-body fn-clear  <c:if test="${isNeedVerity==true}">need-verity</c:if>'>

<div id="notice" class="notice">
	<div class="main-notice" >
		<div class="first-title">
			暂无
		</div>
		<div class="second-title">
		
		</div>
	</div>
	<div class="flow-notice">
		<div class="notice-row">
			<a href="#">
				<span class="title-header">[公告]</span>
				<span class="title">暂无</span>
			</a>
		</div>
	</div>
</div>


<div class="authcenter-body-login ">
<ul class="ui-nav" id="J-loginMethod-tabs">

                			<li data-status="J-login" id="tab-userLogin" style="display: none">账密登录</li>

                            <li data-status="ca-login"id="tab-caLogin"  style="display: none">CA登录</li>
 
                            <br class="clear-float">
            </ul>
            
 <div class="login login-modern" id="J-login" style="display: none;">
 <div class="error-box"></div>
	<form:form name="loginForm" id="loginForm" method="post">
	   <div class="fm-item">
		   <label for="logonId" class="form-label">用户：</label>
		   <input type="text" value="" placeholder="输入用户" maxlength="100"  name="username" id="username" class="i-text"  datatype="s6-18" nullmsg="请输入输入账号！"  errormsg="用户名至少6个字符,最多18个字符！"  >
	       <div class="ui-form-explain"></div>
	  </div>
	  
	  <div class="fm-item">
		   <label for="logonId" class="form-label">密码：</label>
		   <input type="password" placeholder="输入密码" value="" maxlength="100" id="password" name="password" class="i-text" nullmsg="请设置密码！" datatype="*" errormsg="密码范围在6~16位之间！">    
	       <div class="ui-form-explain"></div>
	  </div>
	  <c:if test="${isNeedVerity==true}"> 
	  <div class="fm-item pos-r" 
		   <label for="logonId" class="form-label">验证码</label>
		   <input type="text" placeholder="输入验证码" maxlength="100" id="validateCode"  name="validateCode" class="i-text yzm" datatype="*" nullmsg="请输入验证码！" >    
	       <div class="ui-form-explain"> <img id="yzmimg" src="captcha.htm"  class="yzm-img" align="absmiddle"
													title="验证码" /></div>
	  </div>
	  </c:if>
	  <div class="fm-item">
		   <label for="logonId" class="form-label"></label>
		   <input type="submit" value="登  录" tabindex="4" id="send-btn" class="btn-login"> 
	       <div class="ui-form-explain"></div>
	  </div>
	  <input type="hidden" name="lt" value="${loginTicket}" />
				<input type="hidden" name="execution" value="${flowExecutionKey}" />
				<input type="hidden" name="_eventId" value="submit" />
				<input type="hidden"  name="loginType" value='1' /> 
				
	  </form:form>
 </div>
 


<div class="login login-modern" id="ca-login" style="display: none;">
 			<div class="error-box"></div>
			<object classid="clsid:707C7D52-85A8-4584-8954-573EFCE77488" id="JITDSignOcx" width="0"  height="0" codebase="<%=request.getContextPath() %>/frame/themes/bule/bsnetfun/JITComVCTK.cab#version=2,0,24,42"></object>
			<form:form id="jitLoginForm" name="jitLoginForm" method="post"  class="jitLoginForm">
				<div>
					<%--
					颁发者DN：
					<select id="RootCADN" style="width:140px;">
						<option value="">CN=Certificate Authority Of MOF,O=MOF,C=CN</option>
					</select>
					 --%>
					<input type="hidden" id="RootCADN" value="" style="width:100px;" />
					<input type="hidden" id="signed_data" name="signed_data" /> 
					<input type="hidden" id="original_jsp" name="original_jsp" /> 
				</div>
			
				<div style="padding-top:45%;">
		   		<input type="button" value="登  陆" tabindex="4" id="ca-send-btn" onclick="jitLogin()" class="btn-login"> 
				<input type="hidden" name="lt" value="${loginTicket}" />
				<input type="hidden" name="execution" value="${flowExecutionKey}" />
				<input type="hidden" name="_eventId" value="submit" />
				<input type="hidden"name="loginType" value='2' /> 
				<input type="hidden" name="password"  value='1' /> 
				<input type="hidden" name="username"  value='1' /> 
				</div>
			</form:form>
</div>
</div>
</div>
</div>


<div class="footer">
   <p>技术支持电话：0591-8638191</p>
</div>
<script>
	<c:forEach var="error" items="${messages}">
			
					var objtip=$(".error-box");
					objtip.text('${error.text}');
				</c:forEach>
	$("#J-loginMethod-tabs li").click(function(){
	
	$("#J-loginMethod-tabs li").removeClass("active");
	$(this).addClass("active");
	
	var id=$(this).attr("data-status");
	$(".login-modern").hide();
	
	$("#"+id).show();
	})
	
	
	var uiLoginType='${uiLoginType}';
	
	var LOGIN_TYPE_MAP={
			"1":"tab-userLogin",
			"2":"tab-caLogin"
	}
	if (LOGIN_TYPE_MAP[uiLoginType]){
		
		$("#"+LOGIN_TYPE_MAP[uiLoginType]).trigger("click");
		
		for(key in LOGIN_TYPE_MAP){
			if (key!=uiLoginType){
				var contentId=$("#"+LOGIN_TYPE_MAP[key]).attr("data-status");
				$("#"+LOGIN_TYPE_MAP[key]).remove();
				$("#"+contentId).remove();
			}else{
				$("#"+LOGIN_TYPE_MAP[key]).css("width","100%");
				$("#"+LOGIN_TYPE_MAP[key]).show();
			}
			
		}
		
	}else{
		for(key in LOGIN_TYPE_MAP){
			
				$("#"+LOGIN_TYPE_MAP[key]).show();
		
			
		}
		$("#"+LOGIN_TYPE_MAP["1"]).trigger("click");

		
	}

	//新闻滚动
    var target=$("#notice");
    var Timer;
    target.hover(function (){
        clearInterval(Timer);
    },function (){
        Timer=setInterval(function (){
            noteicFlow(target);
        },2000)
    }).trigger("mouseleave");

    function noteicFlow(obj){
        var $flow = obj.find(".flow-notice");
        var $current = $flow.find("div:first");
        var liHeight = 33;//获取行高；
        $current.animate({"marginTop":-liHeight+"px"},900,function (){
            $current.css({marginTop:0}).appendTo($flow);
        })
    }
	</script>
	
	</body>
</html>
<%

org.springframework.webflow.execution.RequestContextHolder.getRequestContext().getMessageContext().clearMessages();
request.setAttribute("messages", null);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.bosssoft.platform.appframe.api.util.MenuUtils"%>
<%@page import="com.alibaba.fastjson.JSON"%>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=request.getContextPath()%>/resources/frame/multiplatform/css/font-icon.css?version=${VERSION}" rel="stylesheet" media="screen, projection" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/frame/multiplatform/js/bootstrap/css/bootstrap.min.css?version=${VERSION}" rel="stylesheet" media="screen, projection" type="text/css" />
    
    <link href="<%=request.getContextPath()%>/resources/frame/multiplatform/css/index.css?version=${VERSION}" rel="stylesheet" media="screen, projection" type="text/css" />
    <link href="<%=request.getContextPath()%>/resources/frame/multiplatform/themes/bule/css/skins.css?version=${VERSION}" rel="stylesheet" media="screen, projection" type="text/css" />
   <%@include file="/WEB-INF/include/header.jsp"%>

</head>
<script>
 var menuList=<%=JSON.toJSONString(MenuUtils.getCurrentUserAndCurrentApplicationMenus())%>;
</script>

<style>


</style>

<body class='hold-transition skin-blue sidebar-mini <c:if test="${!empty param.form}">nesting-frame</c:if>' style="overflow:hidden;">


 <div class="wrapper">
        <!--头部信息-->
        <header class="main-header">
            <a class="logo">
                <span class="logo-mini"></span>
                <span class="logo-lg"><strong>厦门市政府资产综合管理</strong></span>
            </a>
            <nav class="navbar navbar-static-top">
                <a class="sidebar-toggle">
                    <span class="sr-only">Toggle navigation</span>
                </a>
                <div class="app-list">
                   <ul>
                     
                   </ul>
                </div>
                <div class="navbar-custom-menu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown messages-menu">
                           
                        </li>
                        <li class="dropdown notifications-menu">
                           
                        </li>
                        <li class="dropdown tasks-menu">
                           
                        </li>
                        <li class="dropdown user user-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                               
                                <span class="hidden-xs">administrator</span>
                            </a>
                            <ul class="dropdown-menu pull-right">
                                <li><a class="menuItem" data-id="userInfo" href="/SystemManage/User/Info"><i class="fa fa-user"></i>个人信息</a></li>
                                <li><a href="javascript:void();"><i class="fa fa-trash-o"></i>清空缓存</a></li>
                                <li><a href="javascript:void();"><i class="fa fa-paint-brush"></i>皮肤设置</a></li>
                                <li class="divider"></li>
                                <li><a href="logout"><i class="ace-icon fa fa-power-off"></i>安全退出</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
			<div class="main-sidebar">
			
		    <div class="sidebar">
		    	<div class="header"><a class="sidebar-toggle"><i class="fa"></i><span>系统菜单</span></a></div>
		    
				<ul class="sidebar-menu" id="sidebar-menu">
				
				</ul>
		   </div>
	</div>
	<!--中间内容-->
	<div id="content-wrapper" class="content-wrapper">
		<div class="content-tabs">
			<button class="roll-nav roll-left tabLeft">
				<i class="fa fa-backward"></i>
			</button>
			<nav class="page-tabs menuTabs">
				<div class="page-tabs-content" style="margin-left: 0px;">
					<!-- <a href="javascript:;" class="menuTab active"
						data-id="default.jsp">欢迎首页</a> -->
				</div>
			</nav>
			<button class="roll-nav roll-right tabRight">
				<i class="fa fa-forward" style="margin-left: 3px;"></i>
			</button>
			<div class="btn-group roll-nav roll-right">
				<button class="dropdown tabClose" data-toggle="dropdown">
					页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
				</button>
				<ul class="dropdown-menu dropdown-menu-right">
					<li><a class="tabReload" href="javascript:void();">刷新当前</a></li>
					<li><a class="tabCloseCurrent" href="javascript:void();">关闭当前</a></li>
					<li><a class="tabCloseAll" href="javascript:void();">全部关闭</a></li>
					<li><a class="tabCloseOther" href="javascript:void();">除此之外全部关闭</a></li>
				</ul>
			</div>
			<button class="roll-nav roll-right fullscreen">
				<i class="fa fa-arrows-alt"></i>
			</button>
		</div>
		<div class="content-iframe" style="overflow: hidden;">
			<div id="_tabmask"></div>
			<div class="main-content" id="content-main">
				
			</div>
		</div>
	</div>
	</div>
	<%@include file="/WEB-INF/include/footer.jsp"%>
	<script>
	  var initAppJSPath="resources/frame/multiplatform/js/index";
	</script>
	
	<script data-main="<%=request.getContextPath()%>/resources/common/js/requireConfig.js" src="<%=request.getContextPath()%>/resources/common/js/require.js"></script>
 
        
    </body>
</html>
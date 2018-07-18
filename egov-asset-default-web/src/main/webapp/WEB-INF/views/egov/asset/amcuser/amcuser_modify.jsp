<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>用户信息修改</title>
<af:jsfile path="resources/egov/js/asset/amcuser/amcuser_modify.js"  id="amcuser_modify"  onPageLoad="amcuser_modify.init" />
<%-- <div class="dialog-content">
	<af:page id="amcUserPageModify" />
</div> --%>
<div class="content-main"> 
	<af:page id="amcUserPageModify"/>
</div>

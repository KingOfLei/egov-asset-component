<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title></title>
<af:jsfile path="resources/egov/js/asset/amcuser/amcuser_resetpassword.js"
	id="amcuser_resetpassword" onPageLoad="amcuser_resetpassword.init" />
<div class="dialog-content">
	<af:page id="amcUserPageResetPassWord" />
</div>
<div class="dialog-footer">
	<a id="amcUserPage_saveaddBtn" href="#" class="btn btn-primary singlebtn" style="width: 200px;">确定</a>

</div>
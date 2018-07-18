<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>用户管理（固定资产）</title>
<style>
<!--
#afaUserPage_query{display:none;height:90px !important;}
-->
</style>
<af:jsfile path="resources/egov/js/asset/amcuser/amcuser_index.js"  id="amcuser_index"  onPageLoad="amcuser_index.init" />
<af:page id="amcUserPage" />
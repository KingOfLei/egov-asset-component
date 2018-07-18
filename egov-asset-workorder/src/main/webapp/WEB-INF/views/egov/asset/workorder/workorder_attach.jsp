<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>资产处置</title>
<style>

</style>
<af:jsfile path="resources/egov/js/asset/workorder/workorder_attach.js"  id="workorder_attach"  
           onPageLoad="workorder_attach.init" />
<div id="workorder_attach" class="dialog-content content-main"> 
	<af:page id="workorderAttachPage"></af:page>
</div> 



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>

<title>activity</title>
<af:jsfile path="resources/egov/js/asset/aims/activity/activity_add.js"  id="activity_add"  onPageLoad="activity_add.init" />

<%-- <c:set value="${nodeSequence }" var="nodeSequence"></c:set> --%>
<%-- <c:set value="${remark }" var="remark"></c:set> --%>
<c:set value="${isEnd }" var="isEnd"></c:set>
<div class="dialog-header">
	<h5>流转状态</h5>
</div>
<div class="dialog-content">
	<af:page id="activityPage" />
</div>

<div class="dialog-footer">
	<af:btnarea id="btns" displayType="BUTTON">
		<af:button id="activityPage_closeBtn" name="返回"   icon=""  iconMode="TOP" css="hidden"/>
	</af:btnarea>
</div>
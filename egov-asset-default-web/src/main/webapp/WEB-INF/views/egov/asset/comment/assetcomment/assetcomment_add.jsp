<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title></title>
<af:jsfile path="resources/egov/js/asset/comment/assetcomment/assetcomment_add.js"  id="assetcomment_add"  onPageLoad="assetcomment_add.init" />
<div class="dialog-header">
	<h5>意见</h5>
</div>
<div class="dialog-content">
	<af:page id="assetCommentPage" />
</div>
<div class="dialog-footer" style="display: none">
   <af:btnarea id="assetCommentBtns" displayType="BUTTON" >
		<af:button id="assetCommentPage_close" name="返回" icon="" iconMode="TOP" css="hidden" />
   </af:btnarea>
</div>

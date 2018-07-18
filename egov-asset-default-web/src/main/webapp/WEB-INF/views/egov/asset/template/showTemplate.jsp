<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>showTemplate</title>
<af:jsfile path="resources/egov/js/asset/template/showTemplate.js"  id="showTemplate"  onPageLoad="showTemplate.init" />
<div class="dialog-content">
	<af:page id="showTemplate" />
</div>
<div class="dialog-footer">
	<af:btnarea id="btns" displayType="BUTTON">
		<%-- <af:button id="showTemplate_printSetBtn" name="设置" icon=""   iconMode="TOP" css="hidden"></af:button>
		<af:button id="showTemplate_printPrintBtn" name="打印" icon=""   iconMode="TOP" css="hidden"></af:button> --%>
		<af:button id="showTemplate_printPreviewBtn" name="预览/打印" icon="" iconMode="TOP" css="hidden"></af:button>
		<%-- <af:button id="showTemplate_printCloseBtn" name="返回"   icon="close48"  iconMode="TOP"  css="hidden"></af:button> --%>
	</af:btnarea>
</div>

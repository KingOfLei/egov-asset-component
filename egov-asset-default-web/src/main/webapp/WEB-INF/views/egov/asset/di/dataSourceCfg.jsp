<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<af:jsfile path="resources/egov/js/asset/di/dataSourceCfg.js" id="dataSourceCfg" onPageLoad="dataSourceCfg.init" />
<div class="dialog-content">
	<af:page id="dataSourceCfgPage"/>
</div>
<div class="dialog-footer">
	<af:btnarea id="btnarea" displayType="BUTTON">
		<af:button id="dataSourceCfg_okBtn" name="确认"/>
		<af:button id="dataSourceCfg_cancelBtn" name="取消" handle="$.closeDialog();"/>
	</af:btnarea>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>asset_oper_log</title>

<af:jsfile path="resources/egov/js/asset/log/assetoperlog/assetoperlog_index.js"  id="assetoperlog_index"  onPageLoad="assetoperlog_index.init" />
<af:page id="assetOperLogPage" />
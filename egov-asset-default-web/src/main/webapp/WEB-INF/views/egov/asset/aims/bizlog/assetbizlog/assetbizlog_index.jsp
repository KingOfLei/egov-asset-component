<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>近期操作</title>

<af:jsfile path="resources/egov/js/asset/aims/bizlog/assetbizlog/assetbizlog_index.js"  id="assetbizlog_index"  onPageLoad="assetbizlog_index.init" />
<af:page id="assetBizLogPage" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>通知公告</title>

<af:jsfile path="resources/egov/js/asset/cms/notice/notice.js"  id="cmsnotice_index"  onPageLoad="cmsnotice_index.init" />

<input type="hidden" value="${params.noticeType}" id="noticeType"/>
<input type="hidden" value="${params.timeType}" id="timeType"/>
<af:page id="cmsNoticeViewPage" />

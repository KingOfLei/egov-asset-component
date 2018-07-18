<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<%@include file="/WEB-INF/include/import.jsp" %>
<head>
<af:jsfile path="resources/egov/js/asset/cms/notice/cmsnotice_add.js"  id="cmsnotice_add"  onPageLoad="cmsnotice_add.init" />
<link href="${ctx}/resources/egov/themes/default/notice_detail.css" rel="stylesheet" type="text/css" />	
<title icon="detailNoteice">查看通知公告详细信息</title>
</head>
<%-- <div class="dialog-header">
	<div class="dlg-box-head-left" id="dragTarget">
		<span class="dlg-box-head-title">通知公告新增</span>
		<table class="dlg-box-head-table" cellpadding="0" cellspacing="0">
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div class="dlg-box-head-right">
	<af:btnarea id="btns" displayType="DIALOG">
		<af:button id="cmsNoticePage_saveaddBtn" name="保存并新增"  icon="saveNew48"  iconMode="TOP" css="hidden"></af:button>
		<af:button id="cmsNoticePage_addBtn" name="新增"  icon="add48"  iconMode="TOP" css="hidden" ></af:button>
		<af:button id="cmsNoticePage_editorBtn" name="编辑"   icon="edit48"  iconMode="TOP" css="hidden"></af:button>
		<af:button id="cmsNoticePage_saveBtn" name="保存"   icon="save48"  iconMode="TOP" css="hidden"></af:button>
		<af:button id="cmsNoticePage_closeBtn" name="关闭"   icon="close48"  iconMode="TOP"  css="hidden"></af:button>
	</af:btnarea>
	</div>
</div> --%>
<div class="dialog-content">
<div class="detailNews">
<h1>${notice.title}</h1>

<table class="tableNews" cellpadding="0" cellspacing="0">
	<tr>
		<c:if test="${notice.realeaseDate!=null }">
	    	<td class="tbg">发布日期：</td>
	    	<td>${notice.realeaseDate }</td>
    	</c:if>
    	<c:if test="${notice.realeaseUser!=null }">
	    	<td class="tbg">发布人：</td>
	    	<td>${notice.realeaseUser }</td>
    	</c:if>
    	
        	<c:if test="${notice.attachCnt > 0 }">          	
	        	<td class="tbg">附件下载：</td>
	    		<td style="width:30px;"><div title="点击下载附件" handle="cmsnotice_add.downloadAll('attachment/downloadAll.do?bizId=${notice.id}');" handleType="JS" style="cursor: pointer;margin-bottom:5px;"><img src="${ctx}/resources/egov/themes/default/images/download.png" width="24" height="24" /></div></td>
    		</c:if> 
    		<td class="tbg">正文下载：</td>
	    	<td style="width:30px;"><div title="点击下载正文" handle="cmsnotice_add.downloadAll1('attachment/downloadAll.do?bizId=content_${notice.id}');" handleType="JS" style="cursor: pointer;margin-bottom:5px;"><img src="${ctx}/resources/egov/themes/default/images/content.png" width="28" height="28" /></div></td>
    		  
	</tr>
 </table>          
<div class="contentDiv">
	${notice.content}
　</div>
 </div>
 </div>

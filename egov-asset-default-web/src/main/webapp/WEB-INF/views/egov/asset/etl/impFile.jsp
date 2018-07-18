<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>${_UIPageModel.__config__.templeteName}导入</title>
<af:jsfile path="resources/egov/js/asset/etl/impFile.js"  id="impFile"  onPageLoad="impFile.init" />
<div class="dialog-header">
	<h5>${_UIPageModel.__config__.templeteName}导入</h5>
</div>
<div class="dialog-content">
	<!-- 上传主体 -->
	<div class="upload-div" style="width: 99%">
	  <p class="attachment-item-name"><b title=""><u title="">选择导入的文件</u></b></p>
	  
	  <af:upload id="etlImpFile" 
	    groupId="${_UIPageModel.__id__}"
	    uploadUrl="attachment/upload.do?bizType=etlImpFile"
	    deleteUrl="attachment/delete.do"
		toolbarPosition="bottom"
		exts=".xls,.xlsx"
		fileWidth="200px"
		requestFile="false"
		canDelete="true"
		multiple="false"
	/>
	</div>
	<div id="note" style="text-align:left;float: left;">
	<p style="
    margin: 10px 0px 0px 10px;
    color: red;
	">
    注：
</p>
<ol style="list-style-type: decimal;padding-left: 30px;">
    <li style="list-style: decimal;">
        <p style="">只支持excel2003(xls)、excel2007(xlsx)文件格式导入；
        </p>
    </li>
    <li style="list-style: decimal;">
        <p>
            上传文件大小不能大于<span style="text-decoration: underline;"><strong><span style="text-decoration: underline; color: rgb(255, 0, 0);">5M</span></strong></span>，如有超过请分多次上传；
        </p>
    </li>
    <li style="list-style: decimal;">
        <p>
            导入前先下载导入<a title="模版下载" handleType="JS" handle="impFile.downloadTemplte('resources/template/${_UIPageModel.__config__.templete}')" style="cursor: pointer;color: #277ed6;"><u>标准模版</u></a>，防止出现导入模版错误问题。
        </p>
    </li>
</ol>
	</div>
</div>
<input type="hidden" id="configCode" value="${_UIPageModel.__config__.configCode}">
<input type="hidden" id="templeteName" value="${_UIPageModel.__config__.templeteName}">
<div class="dialog-footer">
   <af:btnarea id="btns" displayType="BUTTON">
			<af:button id="impFile_ok" name="导入" icon="" iconMode="TOP"
				css="hidden"></af:button>
			<af:button id="impFile_cancel" name="取消" icon=""
				iconMode="TOP" css="hidden" handle="$.closeDialog();" ></af:button>
		</af:btnarea>
</div>

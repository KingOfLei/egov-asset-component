<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.bosssoft.com.cn/tags" prefix="af"%>
<title>通知公告新增</title>
<af:jsfile path="resources/egov/js/asset/cms/notice/cmsnotice_add.js"  id="cmsnotice_add"  onPageLoad="cmsnotice_add.init" />
<script type="text/javascript">window.UEDITOR_HOME_URL="/egov-asset-amc-web/resources/egov/js/common/thirdparty/ueditor/"; </script>
<div class="dialog-content">
	<af:page id="cmsNoticePage" />
    <script id="content1" type="text/plain"></script>
    
	<af:upload id="uploadContentJspDemo" 
		groupId="content_${notice.id}"		
	    uploadUrl="attachment/upload.do?bizType=notice"
	    deleteUrl="attachment/delete.do"
	    fileListUrl="attachment/getFiles.do"		
		toolbarPosition="bottom"
		exts=".doc,.docx"
		fileWidth="250px"
		requestFile="true"
		style="width: 600px;height: 80px;margin-left: 150px;float: left;border:1px solid #62a9f1;"
		css="uploadTagDemoClass"
		canDelete="true"
	/>
	<p style="font-size: 13px;float: left;height: 80px;line-height: 80px;"> 正文上传：    <font style="font-weight:bold;color:red;">&nbsp;&nbsp;*最大上传内容不要超过5M</font>
    </p> 
	<af:upload id="uploadJspDemo" 
		groupId="${notice.id}"		
	    uploadUrl="attachment/upload.do?bizType=notice"
	    deleteUrl="attachment/delete.do"
	    fileListUrl="attachment/getFiles.do"		
		toolbarPosition="bottom"
		exts=".doc,.xls,.rar,.zip,.txt,.jpg,.pdf,.jpeg,.docx,.xlsx,.png"
		fileWidth="250px"
		requestFile="true"
		style="width: 600px;height: 80px;margin-left: 150px;float: left;border:1px solid #62a9f1;"
		css="uploadTagDemoClass"
		canDelete="true"
	/>
    <p style="font-size: 13px;
    float: left;
    height: 80px;
    line-height: 80px;">附件    <font style="font-weight:bold;color:red;">&nbsp;&nbsp;*最大上传内容不要超过5M</font>
    </p>
	
</div>
<div id="contentRevert" style="display:none"></div>
<div class="dialog-footer">
	<af:btnarea id="btns" displayType="BUTTON">
		<af:button id="cmsNoticePage_saveBtn" name="保存"   icon=""  iconMode="TOP" css="hidden"></af:button>
		<af:button id="cmsNoticePage_closeBtn" name="关闭"   icon=""  iconMode="TOP"  css="hidden"></af:button>
	</af:btnarea>
</div>
  	
    <!-- 配置文件 -->
    <script type="text/javascript" src="resources/egov/js/common/thirdparty/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <!-- <script type="text/javascript" src="resources/egov/js/common/thirdparty/ueditor/ueditor.all.js"></script> -->
    <script type="text/javascript" charset="utf-8" src="resources/egov/js/common/thirdparty/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="resources/egov/js/common/thirdparty/ueditor/lang/zh-cn/zh-cn.js"></script>
    
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
        var ue = UE.getEditor('content1');
    </script>  
<!-- <script type="text/javascript" src="resources/egov/js/common/thirdparty/ckeditor/ckeditor.js"></script>
	<script>
        CKEDITOR.replace('content');
        CKEDITOR.on('instanceReady', function (ev) {
			editor = ev.editor;
			editor.setReadOnly(false); 
		});
    </script> -->
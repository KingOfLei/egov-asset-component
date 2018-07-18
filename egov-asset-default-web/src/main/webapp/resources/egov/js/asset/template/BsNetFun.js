
define(["./PrintUtils",
		"app/widgets/window/app-dialog",
		"resources/egov/js/asset/template/showTemplate.js",
		"resources/egov/js/asset/template/CreatePrintControl.js",
		"resources/egov/js/asset/template/download.js","bs-plugin/PluginAssist"], 
		function(printUtil,dialog,showTemplate,createPrintControl,download,PluginAssist) {
	var Control_Version = 'V1' ;
	var conf;
	var tempId=null;
	var moduleCode=null;//模块编码,或批次号
	var isIE = !!window.ActiveXObject || "ActiveXObject" in window;
	var disabledTimes=60;
	var t ;
	if (isIE) { // 此判断，待谷歌浏览器可使用后删除
		/**
		 * 根据平台封装的结构，打印控件只会初始化一次
		 */
		
		// 设置本js内部全局控件对象
		var _bsNetFun;
		var HostUrl = "";
		
		var DownLoadUrl = 'egov/asset/template/downLoad.do';
		var QueryTempleteUrl = 'egov/asset/template/queryTempName.do' ;
		var QueryTemplistUrl ='printFace/queryTemplist.do' ;
//		var LoadReportURL = 'egov/asset/template/queryTempById.do';
		var SaveReportURL = 'egov/asset/template/saveTempToServer.do';
		//插件目前只能在32位浏览器中使用，待锐浪公司升级插件
		var browserNum = window.navigator.platform;
		if (browserNum.indexOf("64") > 0){
			$messager.warn("打印控件不能运行在64位浏览器中，请改用32位浏览器！");
			return;z
		}	
		HostUrl = $A.getHostUrl() ;
		//可用  但是会影响页面加载速度  相应代码移至showPrintTemplateDialog中 huangzhenxiong
		/*if(_bsNetFun==undefined||_bsNetFun==null){	
			$app.ajax.ajaxCall({url:'egov/asset/template/cookieString.do',
					data:{},callback:function(cookieStr){
				// 针对IE浏览器设置控件 
				var objTypeId = ' classid="clsid:BB22FAF9-15E7-408C-BCE0-4B1FAE992B01" '+
				'codebase="resources/agency/widget/BsPrintX.CAB#version=1,0,0,1" ';
				if(Control_Version=='V1'){
					 objTypeId = ' classid="clsid:703D2712-EC7D-48DF-904F-55E5B2DEDF99" '+
					'codebase="resources/bsnetfun/BsNetCtl.CAB#version=1,0,3,0" ';
				}
				//区分浏览器(是否是IE浏览器)
				var isIE = !!window.ActiveXObject || "ActiveXObject" in window;
				if (!isIE) {
					objTypeId = 'type="application/x-grplugin-printviewer" ';
					// 补充cookie信息
					if(cookieStr != null&&cookieStr.indexOf('"'+_global_sessionId+'"') != -1){
						cookieStr += _global_sessionId;
					}
				}
				var printObj = '<OBJECT id="_bsNetFun" ' + objTypeId +
				'width=164 height=50 align=center hspace=0 vspace=0> ' +
				'<param name="Cookie" value="Cookie:' + cookieStr + '"> ' +
				'<param name="HostUrl" value="'+HostUrl+'"> ' +
				'<param name="DownloadUrl" value="'+DownLoadUrl+'"> ' +
				'<param name="QueryTemplistUrl" value="'+QueryTemplistUrl+'"> ' +
				'<param name="QueryTempnameUrl" value="'+QueryTempleteUrl+'"> ' +
				'<param name="SaveReportURL" value=" '+SaveReportURL+'">' +				
				'</OBJECT>';			
				$('head').append(printObj);
				// 设置本js内部全局控件对象
				_bsNetFun = document.getElementById('_bsNetFun');
				
			}});
			
			
		}*/
	}
	//获取打印控件对象
	bsPrintObj = function(){
		// 此判断，待谷歌浏览器可使用后删除
		if (!isIE) {
			$messager.warn("暂时只支持IE浏览器！");
			return;
		}
		
	    var bsPrintObject = document.getElementById("_bsNetFun");
	    if (bsPrintObject == undefined || bsPrintObject == null) //if ((_ReportOK == null) || (_ReportOK.Register == undefined))
	    {
	        document.write('<div style="width: 100%; background-color: #fff8dc; text-align: center; vertical-align: middle; line-height: 20pt; padding-bottom: 12px; padding-top: 12px;">');
	            document.write('<strong> 本系统需要安装博思网络组件 才能保证其正常运行<br /></strong>');
	            
	        if( isIE )
	            document.write('<strong><span style="color: #ff0000"> 如浏览器的顶部或底部出现提示条，左键点击提示条并运行加载项，按此方式安装最简便</span><br /></strong>');
	            
	            document.write('<a href="resources/agency/widget/setup.exe"><span style="color: #ff0000"><strong>点击此处下载博思网络组件安装包<br /></strong></span></a>');
	            document.write('博思网络组件安装后，<a href="#" onclick="javascript:document.location.reload();">点击此处</a> 重新加载此网站');
	        document.write('</div>');
	        return false;
	    }	
	    return bsPrintObject ;
	}
	//处理异常事件
	dealErrorEvent = function(err){
		bsPrintObj(); 
	}
	/**
	 * 修复不能连上真实打印机问题。
	 * 打印设置中 \\ 被替换掉了
	 */
	reSetPrintConfig = function(configStr){
		if(!configStr)
			return "" ;
		else{
			configStr = configStr.replace(/\\/g, "\\\\") ;//replace(new RegExp('\\',"gm"),'\\\\');
			return configStr ;
		}	
	}
	/**
	 * 显示选择打印模板窗口
	 */
	function showPosPanel() {			
		var html = "";
		html += "<div class=\"dialog-header\" id=\"print_dialog\">";
		html += "<div class=\"closebg\"><a id=\"btn_print_close\" vclass=\"close\" href=\"#close\"></a></div><h5>POS刷卡</h5></div>";
		
		html += "<div class=\"dialog_content\">";
	
		html += "<div style=\"padding:0px 10px 0px 10px;position:relative;margin-top:10px;margin-bottom:10px;\">"+
				"<div style=\"border:1px dotted #FF3333;background:#ffe4c4;height:30px;padding-top:0px;padding-left:0px;\">"+
				"<span style=\"padding:5px;height:20px;line-height:20px;display:block;\"><strong>提示：</strong>请核对缴款信息无误后，点击POS刷卡，进行POS收款</span></div></div>";
		html += "<table align=\"center\" height=\"180\"><tbody>" ;		
		html += "<tr><td class=\"clabel \"><label class=\"left\"></label></td>";
		html += "<td><input id=\"fpaybookId\" type=\"hidden\"\" /></td></tr>";			
		html += "<tr><td class=\"clabel \"><label class=\"left\">缴款码:&nbsp;</label></td>";
		html += "<td><input id=\"fposPayCode\" class=\"app-textbox\" _options=\"{readonly: 'readonly',value: ''}\" /></td></tr>";
		html += "<tr><td class=\"clabel \"><label class=\"left\">付款人:&nbsp;</label></td>";
		html += "<td><input id=\"fposPayer\" class=\"app-textbox\" _options=\"{readonly: 'readonly',value: ''}\" /></td></tr>";
		html += "<tr><td class=\"clabel \"><label class=\"left\">总金额:&nbsp;</label></td>";
		html += "<td><input id=\"fposTotalAmt\" class=\"app-textbox\" _options=\"{readonly: 'readonly',value: ''}\" /></td></tr>";
		
		html += "<tr><td colspan=\"2\" align=\"left\" height=\"60\">" +
				"<div id=\"fstateSuccess\" style=\"margin-top:15px;padding-top:5px;display:none;margin-bottom:10px;\">" +
				"<div id=\"fstateSuccessMsg\" class=\"alertScs\"/>" +
				"</div></div>"+	
				"<div id=\"fstateError\" style=\"margin-top:15px;padding-top:5px;display:none;margin-bottom:10px;\">" +
				"<div id=\"fstateErrorMsg\" class=\"alertFai\"/>" +			
				"</div></div>"+		
				"<div id=\"fstateNormal\" style=\"margin-top:15px;padding-top:5px;margin-bottom:10px;\">" +				
				"<span id=\"fstateNormalMsg\" style=\"font-size:18px;font-weight:bold;margin-left:10px;color:#006699;\">请刷卡并输入密码！</span>" +
				"</div>"+						
				"</td></tr>";	
		html += "</tbody></table>";				
		html += "</div> ";
		
		html += "<div class=\"dialog-footer\">";
		html += "<div class=\"btnarea btn-toolbar\">";
		html += "<a id=\"btn_pos_query\" class=\"app-button l-btn l-btn-small\"><span class=\"l-btn-left\"><span class=\"l-btn-text\">缴款状态查询</span></span></a>";
		html += "<a id=\"btn_pos_pay\" class=\"app-button l-btn l-btn-small\"><span class=\"l-btn-left\"><span class=\"l-btn-text\">POS刷卡</span></span></a>";
		html += "<a id=\"btn_pos_cancel\" class=\"app-button l-btn l-btn-small\"><span class=\"l-btn-left\"><span class=\"l-btn-text\">关闭</span></span></a>";
		html += "</div></div>";	
		return html;
	}	
	/**
	 * 显示选择打印模板窗口
	 */
	function showTemplatePanel() {
		var html = "";
		html += "<div class=\"dialog-header\" id=\"print_dialog\">";
		html += "<div class=\"closebg\"><a id=\"btn_print_close\" vclass=\"close\" href=\"#close\"></a></div><h5>选择模板</h5></div>";
		
		html += "<div class=\"dialog_content\" style=\"height: 210px;\">";
		html += "<div id=\"_print_grid\" class=\"grid-container\" style=\"width:550px;\" style=\"height: 210px;\"> ";
		html += "<div class=\"grid-header\"> ";
		html += "<div id=\"_print_grid_pageTool\" class=\"grid-page\"></div> ";
		html += "</div > ";
		/*html += "<table id=\"_print_table\" pageId=\"_print_grid_pageTool\" usePage=\"false\" class=\"jqgrid\" showFooter=\"false\"  manualFooter=\"false\" ";
		html += "autoHeight=\"false\" shrinkToFit=\"true\" pageSize=\"0\" width=\"550px\" height=\"200\" checkboxable=\"false\" editRow=\"false\" eventSelf=\"true\" ";
		html += "sortname=\"fno\" sortorder=\"desc\" autoLoad=\"false\" fillNum=\"0\" export=\"false\" > ";
		html += "<thead> ";
		html += "<tr> ";
		html += "<th id=\"moduleItemId\" columnType=\"DEFAULT\" chidden=\"true\" align=\"CENTER\" ";  
		html += "sortable=\"false\" frozen=\"false\" footerType=\"TEXT\" editable=\"false\" edittype=\"TEXTBOX\">id</th> ";
		html += "<th id=\"moduleItemCode\" columnType=\"DEFAULT\" chidden=\"false\" align=\"CENTER\" ";  
		html += "sortable=\"false\" frozen=\"false\" footerType=\"TEXT\" editable=\"false\" edittype=\"TEXTBOX\" width=\"60\" >编码</th> ";
		html += "<th id=\"moduleItemName\" columnType=\"DEFAULT\" chidden=\"false\" align=\"LEFT\" ";  
		html += "sortable=\"false\" frozen=\"false\" footerType=\"TEXT\" editable=\"false\" edittype=\"TEXTBOX\">名称</th> ";
		html += "</tr> ";
		html += "</thead> ";
		html += "</table> ";*/
		
		
		html += "<div class=\"dialog-footer\">";
		html += "<div class=\"btnarea btn-toolbar\">";
		html += "<a id=\"btn_print_set\" class=\"app-button l-btn l-btn-small\"><span class=\"l-btn-left\"><span class=\"l-btn-text\">设置</span></span></a>";
		html += "<a id=\"btn_print_print\" class=\"app-button l-btn l-btn-small\"><span class=\"l-btn-left\"><span class=\"l-btn-text\">打印</span></span></a>";
		html += "<a id=\"btn_print_preview\" class=\"app-button l-btn l-btn-small\"><span class=\"l-btn-left\"><span class=\"l-btn-text\">预览</span></span></a>";
		html += "<a id=\"btn_print_cancel\" class=\"app-button l-btn l-btn-small\"><span class=\"l-btn-left\"><span class=\"l-btn-text\">关闭</span></span></a>";
		html += "</div></div>";
		
		return html;
	}
	/**
	 * 显示打印设置窗口
	 */
	function showPrintPanel(){
		var html="";
		html+="<div class=\"dlg-box-head\">";
		html+="<div class=\"dlg-box-head-left\" id=\"dragTarget\">";
		html+="<span class=\"dlg-box-head-title\">打印设置</span></div>";
		//1 标题按钮
		html+="<div class=\"dlg-box-head-right\">";
		html+="<div id=\"mybtnarea\" class=\" btnarea menubar btn-dlg-toolbar\">";
		html+="<a id=\"printerClose\" class=\"app-button l-btn l-btn-large l-btn-plain\" "+
				" style=\"float:right\" data-options=\"{'iconAlign':'top','menu':null,'name':'关闭','plain':true,'css':null,'iconCls':'close48','size':'large'}\""+
				" href=\"#\" handletype=\"JS\" tips=\"关闭\" group=\"\">关闭</a>";
		html+="<a id=\"printerSave\" class=\"app-button l-btn l-btn-large l-btn-plain\" "+
				" style=\"float:right\" data-options=\"{'iconAlign':'top','menu':null,'name':'确定','plain':true,'css':null,'iconCls':'save48','size':'large'}\""+
				" href=\"#\" handletype=\"JS\" tips=\"确定\" group=\"\">确定</a></div>";
		html+="</div></div>";
		//2 窗体panel
		html+="<div class=\"dialog-content\" style=\"height: 350px;\">";
		html+="<div id=\"printerInfo\" class=\"app-layout layout\" style=\"width: 100%; height: 100%; overflow: hidden; margin: 0px; padding: 0px;\">";
		html+="<div class=\"panel layout-panel layout-panel-center layout-panel-broder-center\" style=\"left: 0px; top: 0px; width: 560px;\">"+
			"<div id=\"pnlCenter\" class=\"app-layout layout-body panel-body panel-body-noheader panel-body-noborder layout\" style=\"margin: 0px; width: 560px; height: 350px; padding: 0px;\" data-options=\"{&quot;region&quot;:&quot;center&quot;,&quot;fit&quot;:false,&quot;border&quot;:false}\">";
		//3 表单1
		html+="<div class=\"form\"> <form id=\"printerForm1\" class=\"required-validate\" opstate=\"\" initedit=\"true\"> ";
		html+="<div id=\"printerForm1defaultgroup\" class=\"formgroup onecolumn\" summary=\"false\" sumstate=\"all\" sumcount=\"0\" opstate=\"EDIT\"> ";
		html+="<input type=\"hidden\" id=\"ModuleId\" name=\"ModuleId\" hiddencomp=\"true\" forform=\"printerForm1\" field=\"ModuleId\" style=\"\" value=\"\" manual=\"false\">";
		//3.1 打印机
		html+="<table align=\"center\"><tbody><tr>";
		html+="<td forpanel=\"pnlCenter\" class=\"clabel Printer\" colspan=\"1\"><label id=\"label_Printer\" class=\"left\">打印机</label></td>";
		html+="<td forpanel=\"pnlCenter\" class=\"Printer\" colspan=\"1\">";
		html+="<div class=\"formitem null\" style=\"width:350px\" forform=\"printerForm1\" item-field=\"Printer\"><div class=\"editor\" style=\"text-align:left;\"><input id=\"Printer\" width=\"350px\"/></div></div></td>";
		html+="</tr><tr>";
		//3.2 纸张
		html+="<td forpanel=\"pnlCenter\" class=\"clabel Paper\" colspan=\"1\"><label id=\"label_Paper\" class=\"left\">纸张</label></td>";
		html+="<td forpanel=\"pnlCenter\" class=\"Paper\" colspan=\"1\">";
		html+="<div class=\"formitem null\" style=\"width:350px\" forform=\"printerForm1\" item-field=\"Paper\"><div class=\"editor\" style=\"text-align:left;\"><div class=\"app-wrapper app-combobox\" style=\"width:350px;\"><input id=\"Paper\" name=\"Paper\" class=\"\" width=\"350px\" valuefield=\"id\" textfield=\"name\" async=\"false\" multiple=\"false\" manual=\"false\" field=\"Paper\" eventself=\"true\" tabindex=\"-1\" style=\"display: none;\"><input type=\"showValue\" autocomplete=\"off\" readonly=\"readonly\"><span class=\"wrapper-btn wrapper-clear\" style=\"display: none;\"><i></i></span><a href=\"#\" class=\"wrapper-btn wrapper-open\" tabindex=\"-1\"><i></i></a></div></div></div></td>";
		html+="</tr></tbody></table></div></form></div>";
		//3.3  自定义分割线
		html+="<div align=\"center\"><div style=\"height:1px;width:500px;background:#E8E8E8;overflow:hidden;\"></div></div>";
		//4 表单2
		html+="<div class=\"form\"> <form id=\"printerForm2\" class=\"required-validate\" opstate=\"\" initedit=\"true\"> ";
		html+="<div id=\"printerForm2defaultgroup\" class=\"formgroup multicolumn\" summary=\"false\" sumstate=\"all\" sumcount=\"0\" opstate=\"EDIT\"> ";
		html+="<table align=\"center\"><tbody><tr>";
		//4.1 上边距
		html+="<td forpanel=\"pnlCenter\" class=\"clabel MarginUp\" colspan=\"1\"><label id=\"label_MarginUp\" class=\"left\">上边距</label></td>";
		html+="<td forpanel=\"pnlCenter\" class=\"MarginUp\" colspan=\"1\">";
		html+="<div class=\"formitem null\" style=\"width:100px\" forform=\"printerForm2\" item-field=\"MarginUp\"><div class=\"editor\" style=\"text-align:left;\"><div class=\"app-wrapper app-number\" style=\"width:100px;\"><input id=\"MarginUp\" name=\"MarginUp\" class=\"\" precision=\"0\" width=\"100px\" manual=\"false\" field=\"MarginUp\" eventself=\"true\" tabindex=\"-1\" style=\"display: none;\"><input type=\"showValue\" autocomplete=\"off\" style=\"ime-mode: disabled;\"><span class=\"wrapper-btn wrapper-clear\"><i></i></span></div></div></div></td>";
		//4.2 下边距
		html+="<td forpanel=\"pnlCenter\" class=\"clabel MarginDown\" colspan=\"1\"><label id=\"label_MarginDown\" class=\"left\">下边距</label></td>";
		html+="<td forpanel=\"pnlCenter\" class=\"MarginDown\" colspan=\"1\">";
		html+="<div class=\"formitem null\" style=\"width:100px\" forform=\"printerForm2\" item-field=\"MarginDown\"><div class=\"editor\" style=\"text-align:left;\"><div class=\"app-wrapper app-number\" style=\"width:100px;\"><input id=\"MarginDown\" name=\"MarginDown\" class=\"\" precision=\"0\" width=\"100px\" manual=\"false\" field=\"MarginDown\" eventself=\"true\" tabindex=\"-1\" style=\"display: none;\"><input type=\"showValue\" autocomplete=\"off\" style=\"ime-mode: disabled;\"><span class=\"wrapper-btn wrapper-clear\" style=\"display: none;\"><i></i></span></div></div></div></td>";
		html+="</tr><tr>";
		//4.3 左边距
		html+="<td forpanel=\"pnlCenter\" class=\"clabel MarginLeft\" colspan=\"1\"><label id=\"label_MarginLeft\" class=\"left\">左边距</label></td>";
		html+="<td forpanel=\"pnlCenter\" class=\"MarginLeft\" colspan=\"1\">";
		html+="<div class=\"formitem null\" style=\"width:100px\" forform=\"printerForm2\" item-field=\"MarginLeft\"><div class=\"editor\" style=\"text-align:left;\"><div class=\"app-wrapper app-number\" style=\"width:100px;\"><input id=\"MarginLeft\" name=\"MarginLeft\" class=\"\" precision=\"0\" width=\"100px\" manual=\"false\" field=\"MarginLeft\" eventself=\"true\" tabindex=\"-1\" style=\"display: none;\"><input type=\"showValue\" autocomplete=\"off\" style=\"ime-mode: disabled;\"><span class=\"wrapper-btn wrapper-clear\"><i></i></span></div></div></div></td>";
		//4.4 右边距
		html+="<td forpanel=\"pnlCenter\" class=\"clabel MarginRight\" colspan=\"1\"><label id=\"label_MarginRight\" class=\"left\">右边距</label></td>";
		html+="<td forpanel=\"pnlCenter\" class=\"MarginRight\" colspan=\"1\">";
		html+="<div class=\"formitem null\" style=\"width:100px\" forform=\"printerForm2\" item-field=\"MarginRight\"><div class=\"editor\" style=\"text-align:left;\"><div class=\"app-wrapper app-number\" style=\"width:100px;\"><input id=\"MarginRight\" name=\"MarginRight\" class=\"\" precision=\"0\" width=\"100px\" manual=\"false\" field=\"MarginRight\" eventself=\"true\" tabindex=\"-1\" style=\"display: none;\"><input type=\"showValue\" autocomplete=\"off\" style=\"ime-mode: disabled;\"><span class=\"wrapper-btn wrapper-clear\" style=\"display: none;\"><i></i></span></div></div></div></td>";
		html+="</tr></tbody></table></div></form></div>";
		//4.5 自定义分割线
		html+="<div align=\"center\"><div style=\"height:1px;width:500px;background:#E8E8E8;overflow:hidden;\"></div></div>";
		//5 表单3
		html+="<div class=\"form\"> <form id=\"printerForm3\" class=\"required-validate\" opstate=\"\" initedit=\"true\"> ";
		html+="<div id=\"printerForm3defaultgroup\" class=\"formgroup multicolumn\" summary=\"false\" sumstate=\"all\" sumcount=\"0\" opstate=\"EDIT\"> ";
		html+="<table align=\"center\"><tbody><tr>";
		//5.1 横向偏移
		html+="<td forpanel=\"pnlCenter\" class=\"clabel OffsetX\" colspan=\"1\"><label id=\"label_OffsetX\" class=\"left\">横向偏移</label></td>";
		html+="<td forpanel=\"pnlCenter\" class=\"OffsetX\" colspan=\"1\">";
		html+="<div class=\"formitem null\" style=\"width:100px\" forform=\"printerForm3\" item-field=\"OffsetX\"><div class=\"editor\" style=\"text-align:left;\"><div class=\"app-wrapper app-number\" style=\"width:100px;\"><input id=\"OffsetX\" name=\"OffsetX\" class=\"\" precision=\"0\" width=\"100px\" manual=\"false\" field=\"OffsetX\" eventself=\"true\" tabindex=\"-1\" style=\"display: none;\"><input type=\"showValue\" autocomplete=\"off\" style=\"ime-mode: disabled;\"><span class=\"wrapper-btn wrapper-clear\"><i></i></span></div></div></div></td>";
		//5.2 纵向偏移
		html+="<td forpanel=\"pnlCenter\" class=\"clabel OffsetY\" colspan=\"1\"><label id=\"label_OffsetY\" class=\"left\">纵向偏移</label></td>";
		html+="<td forpanel=\"pnlCenter\" class=\"OffsetY\" colspan=\"1\">";
		html+="<div class=\"formitem null\" style=\"width:100px\" forform=\"printerForm3\" item-field=\"OffsetY\"><div class=\"editor\" style=\"text-align:left;\"><div class=\"app-wrapper app-number\" style=\"width:100px;\"><input id=\"OffsetY\" name=\"OffsetY\" class=\"\" precision=\"0\" width=\"100px\" manual=\"false\" field=\"OffsetY\" eventself=\"true\" tabindex=\"-1\" style=\"display: none;\"><input type=\"showValue\" autocomplete=\"off\" style=\"ime-mode: disabled;\"><span class=\"wrapper-btn wrapper-clear\" style=\"display: none;\"><i></i></span></div></div></div></td>";
		html+="</tr></tbody></table></div></form></div>";
		html+="</div></div>";
		html+="</div></div>";
		html+="</div>";
		return html;
	}
	
	/**
	 * 显示打印设置窗口
	 * @param ModuleId 控件ID
	 */
	showPrintDialog = function(ModuleId){
		//1 控件ID不能为空
		if(ModuleId==''||ModuleId==null){
			$messager.warn("请输入控件ID");
			return false;
		}
		//2 获取打印设置界面
		var $printer = $(showPrintPanel());
		
		//3 初始化组件
		$printer.find('#Printer').combobox({
			valuefield:'id',
			textfield:'name',
			idfield:'id'
		});
		$printer.find('#Paper').combobox({
			valuefield:'id',
			textfield:'name',
			idfield:'id'
		});
		$printer.find('#MarginUp').number({precision:0});
		$printer.find('#MarginDown').number({precision:0});
		$printer.find('#MarginLeft').number({precision:0});
		$printer.find('#MarginRight').number({precision:0});
		$printer.find('#OffsetX').number({precision:0});
		$printer.find('#OffsetY').number({precision:0});
		//4  绑定打印设置保存事件
		$printer.find("#printerSave").bind("click",function(){
			var printer = $A("#Printer").combobox('getValue');
			var paper=$A("#Paper").combobox('getValue');
			var marginUp=$A("#MarginUp").number('getValue');
			var marginDown=$A("#MarginDown").number('getValue');
			var marginLeft=$A("#MarginLeft").number('getValue');
			var marginRight=$A("#MarginRight").number('getValue');
			var offsetX=$A("#OffsetX").number('getValue');
			var offsetY=$A("#OffsetY").number('getValue');
			
			if(isEmpty(paper)){
				$A("#Paper").combobox('setValue','A4');
			}
			if(isEmpty(marginUp)){
				$A("#MarginUp").number('setValue',0);
			}
			if(isEmpty(marginDown)){
				$A("#MarginDown").number('setValue',0);
			}
			if(isEmpty(marginLeft)){
				$A("#MarginLeft").number('setValue',0);
			}
			if(isEmpty(marginRight)){
				$A("#MarginRight").number('setValue',0);
			}
			if(isEmpty(offsetX)){
				$A("#OffsetX").number('setValue',0);
			}
			if(isEmpty(offsetY)){
				$A("#OffsetY").number('setValue',0);
			}
			
			//4.1 表单序列化
			var param1=$A("#printerForm1").serialize();
			var param2=$A("#printerForm2").serialize();
			var param3=$A("#printerForm3").serialize();
			var postdata= 'Printer='+printer+'&'+param1+'&'+param2+'&'+param3;
			
			var result = bsPrintObj().setPrinter(postdata);
			if(result){
				var msgObj = eval('(' + result  + ')') ;
				$A.messager.correct(msgObj.message);
			}
			$.closeDialog();
		});
		//5 关闭绑定事件
		$printer.find("#printerClose").bind("click",function(){
			$.closeDialog();
		});
		//6 判断参数是否为空
		function isEmpty(param){
			if(param==""||param==null)
				return true;
			else return false;
		}
		
		//7 打开打印设置窗口
		var options = {
				dialogId : "prtDlg",
				hasheader : false,
				height : 380,
				width : 300,
				mode : "node",
				url : $printer,
				onPageLoad:function(){
					$A("#ModuleId").val(ModuleId) ;
					/*var sss = '[{"id":"Print1","name":"Print1"},{"id":"Print2","name":"Print2"},{"id":"333","name":"333"}]';*/
					var printerList=bsPrintObj().executeMethod('{"Method":"GET_PRINTER_LIST"}') ;
					getPrinterList(printerList);
					
					/*var ssd='[{"id":"A2","name":"A2"},{"id":"A4","name":"A4"}]';*/
					var paperList = bsPrintObj().executeMethod('{"Method":"GET_PAPER_LIST"}'); 
					getPaperList(paperList);
					
					/*var str='{"ModuleId":"20160101" , "Printer":"333" , "Paper":"A4","MarginUp":"1" , "MarginDown":"1","MarginLeft":"1" , "MarginRight":"1","OffsetX":"1","OffsetY":"1"}';*/
					var currentPrinter = bsPrintObj().executeMethod('{"Method":"GET_PRINTSET","Params":{"ModuleId":"'+ModuleId+'"}}');
					setCurPrinter(currentPrinter);
				}
			}	 
		$.openModalDialog(options);
		
		//8 设置打印机列表
		function getPrinterList(params){
			var loaddata=eval('(' + params + ')');
			$A("#Printer").combobox('loadData',loaddata);
		}
		//9 设置纸张列表
		function getPaperList(params){
			var loaddata=eval('(' + params + ')');
			$A("#Paper").combobox('loadData',loaddata);
		}
		//10 当前可用打印设置
		function setCurPrinter(data){
			var printer=eval('(' + data + ')');
			$A("#Printer").combobox('setText',printer.Printer);
			$A("#Printer").combobox('setValue',printer.Printer);
			
			$A("#Paper").combobox('setText',printer.Paper);
			$A("#Paper").combobox('setValue',printer.Paper);
			
			$A("#MarginUp").number('setValue',printer.MarginUp);
			$A("#MarginDown").number('setValue',printer.MarginDown);
			$A("#MarginLeft").number('setValue',printer.MarginLeft);
			$A("#MarginRight").number('setValue',printer.MarginRight);
			$A("#OffsetX").number('setValue',printer.OffsetX);
			$A("#OffsetY").number('setValue',printer.OffsetY);
		}
	}
	/**
	 * 往当前类型中添加标示符
	 */
	function appendTypeToUrl(word){
		/*this.loopGetAppendTypeToUrl(function(){
			if(bsPrintObj().DownloadUrl.indexOf("?type")>=0){
				var newUrl=bsPrintObj().DownloadUrl.substring(0,bsPrintObj().DownloadUrl.indexOf("?type")); 
				bsPrintObj().DownloadUrl=newUrl+word;
			}else{
				bsPrintObj().DownloadUrl=bsPrintObj().DownloadUrl+word;
			}
		})*/
		try{
			if(bsPrintObj().DownloadUrl.indexOf("?type")>=0){
				var newUrl=bsPrintObj().DownloadUrl.substring(0,bsPrintObj().DownloadUrl.indexOf("?type")); 
				bsPrintObj().DownloadUrl=newUrl+word;
			}else{
				bsPrintObj().DownloadUrl=bsPrintObj().DownloadUrl+word;
			}
		}catch(err){
			/*setTimeout(function(){
				download.showPage(function(){
					$.closeDialog();
				});
			},1000);*/
			PluginAssist.install({
				classid : '703D2712-EC7D-48DF-904F-55E5B2DEDF99',
				codebase: 'resources/bsnetfun/BsNetCtl.CAB#version=1,0,3,0',
				exebase : 'resources/bsnetfun/BsPrint1.0.3.0.exe',
			});
		}
	}
	
	
	function _gridReportOptions(opt){

		var heaerTitle={
			id:'title',
			name:opt.title,
			type:'title',
			control:'MemoBox',
			needValue:false,
			center:1
		};
		//普通控件样式定义
		var com_controls={
			needValue:true,
			type:'condition',
			control:'StaticBox',//控件类行
			exp:'',//表达式
			value:{},//值
			option:{
				target:'',//对照对象，以前一个对象的位置属性进行定位
				position:'x'//布局方式 x y ,若为"auto",则对照方式，安照控件写入顺序
			},
			needSplit:false//是否与前一个控件隔开
		};
		var gridOpt={titleHeight:1.8};
		
		var reportOpt={};
		reportOpt.controls=[];
		reportOpt.controls.push(heaerTitle);
		for(var i=0;i<opt.controls.length;i++)
		{
			var _copt=$.extend({},com_controls,opt.controls[i]);
			_copt.exp=opt.controls[i].id;
			var fillvalue=opt.controls[i].value[opt.controls[i].id];
			if(fillvalue==null||$.trim(fillvalue).length==0){
				continue;
			}
			reportOpt.controls.push(_copt);
		}
		
		
		
		
		var grid=$.extend({},gridOpt,opt.grid);
		reportOpt.grid=grid;
		return reportOpt;
		
		
	}	
	
	

	var mainobj= {
		/**
		 * 根据模块Id[分类Id]初始化模板id
		 * @param moduleId moduleId 模块Id[分类Id]
		 */
		initDefaultModuleId : function(moduleId){
			
			// 此判断，待谷歌浏览器可使用后删除
			/*if (!isIE) {
				return;
			}
			
			var menuId = $A.getCurrentNavTab().data("__menuid");
			
			//存本地默认模板ID
			var str = '<div id="_'+menuId+'_moduleId" style="display:none;"></div>';
			$('body').append(str);
			// 保存默认模板id本地
			$('#_'+menuId+'_moduleId').attr('_moduleId', moduleId);*/
		},
		
		/**
		 * 根据模板id(打印或预览)
		 * @param dataUrl 数据源Url  
		 * @param templateId 模板id
		 * @param isPreview false 打印 true 预览 默认为false
		 */
		printByTemplateUrl : function(dataUrl, templateId, isPreview){
			if(dataUrl == null||dataUrl == ''){
				$messager.warn("请设置数据源url");
				return;
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
//			var tempId = templateId.replace("\"", "").replace("\"", "");
			var tempId = templateId; 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintUrl(templateUrl, HostUrl+dataUrl, isPreview,conf);
		},
		
		/**
		 * 根据模板id(打印或预览)打印多个子报表
		 * @param urlParam Url参数 
		 * @param templateId 模板id
		 * @param isPreview false 打印 true 预览 默认为false
		 */
		printMultipleReport : function(urlParam, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
//			var tempId = templateId.replace("\"", "").replace("\"", "");
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultipleReport(templateUrl,HostUrl+urlParam, isPreview,conf);
		},
		
		printMultipleReport1 : function(urlParam, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultipleReport1(templateUrl,HostUrl+urlParam, isPreview,conf);
		},
		
		printMultipleReport2 : function(urlParam, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultipleReport2(templateUrl,HostUrl+urlParam, isPreview,conf);
		},
		
		printMultipleReport3 : function(urlParam,urlParam4Single, params, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultipleReport3(templateUrl,HostUrl+urlParam,HostUrl+urlParam4Single, params, isPreview,conf);
		},
		
		//固定资产报废处置表
		printMultipleReport4 : function(mxbUrl, hzbUrl, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultipleReport4(templateUrl, HostUrl+mxbUrl, HostUrl+hzbUrl, isPreview, conf);
		},
		//固定资产处置表
		printMultipleReport5 : function(mxbUrl, hzbUrl, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultipleReport5(templateUrl, HostUrl+mxbUrl, HostUrl+hzbUrl, isPreview,conf);
		},
		
		//年报汇总表
		printMultipleReport6 : function(urlParam,urlParam4Single, params, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultipleReport6(templateUrl,HostUrl+urlParam,HostUrl+urlParam4Single, params, isPreview,conf);
		},
		
		//固定资产报损处置表
		printMultipleReport7 : function(mxbUrl, hzbUrl, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultipleReport7(templateUrl, HostUrl+mxbUrl, HostUrl+hzbUrl, isPreview, conf);
		},
		
		//多个子报表打印 统一调用口
		printMultiReport:function(dataUrl, templateId, isPreview){
			if(templateId == null||templateId == ''){
				templateId = getDefaultTemplateId();
			}
			if(isPreview == null||isPreview == ''){
				isPreview = false;
			}
			var tempId = templateId;
			 
			if(tempId==null||$.trim(tempId).length==0){
				$A.messager.warn("未找到打印模板!");
				return;
			}
			appendTypeToUrl("?type=form");
			var templateUrl=bsPrintObj().getTempletePath(templateId);
			printUtil.PrintMultiReport(templateUrl, dataUrl, isPreview, conf);
		},
		
		
		loopGetObj:function(callBack){
			var self = this;
			setTimeout(function(){
				if($('#_bsNetFun').length){
					_bsNetFun = document.getElementById('_bsNetFun');
					callBack();
				}else{
					self.loopGetObj(callBack);
				}
			},500);
		},
		//
		loopGetAppendTypeToUrl:function(callback){
			var self = this;
			setTimeout(function(){
				if(bsPrintObj().DownloadUrl != null){
					callback();
				}else{
					self.loopGetAppendTypeToUrl(callback);
				}
			},500);
		},
		/**
		 * 显示模板列表
		 * @param moduleId 模块Id[分类Id]
		 * @Param printFunc 弹窗之后事件 打印
		 * @param previewFunc 弹窗之后事件 预览
		 * @param openBefore 弹窗之前事件
		 * @param closeBefore 关闭之前事件
		 * @param params 参数集
		 */
		showPrintTemplateDialog : function(moduleId,  
				printFunc, previewFunc, openBefore, closeBefore, params){
			if(_bsNetFun==undefined||_bsNetFun==null){	
				$app.ajax.ajaxCall({url:'egov/asset/template/cookieString.do',
						data:{},callback:function(cookieStr){
					// 针对IE浏览器设置控件 
					var objTypeId = ' classid="clsid:BB22FAF9-15E7-408C-BCE0-4B1FAE992B01" '+
					'codebase="resources/agency/widget/BsPrintX.CAB#version=1,0,0,1" ';
					if(Control_Version=='V1'){
						 objTypeId = ' classid="clsid:703D2712-EC7D-48DF-904F-55E5B2DEDF99" '+
						'codebase="resources/bsnetfun/BsNetCtl.CAB#version=1,0,3,0" ';
					}
					//区分浏览器(是否是IE浏览器)
					var isIE = !!window.ActiveXObject || "ActiveXObject" in window;
					if (!isIE) {
						objTypeId = 'type="application/x-grplugin-printviewer" ';
						// 补充cookie信息
						if(cookieStr != null&&cookieStr.indexOf('"'+_global_sessionId+'"') != -1){
							cookieStr += _global_sessionId;
						}
					}
					var printObj = '<OBJECT id="_bsNetFun" ' + objTypeId +
					'width=164 height=50 align=center hspace=0 vspace=0> ' +
					'<param name="Cookie" value="Cookie:' + cookieStr + '"> ' +
					'<param name="HostUrl" value="'+HostUrl+'"> ' +
					'<param name="DownloadUrl" value="'+DownLoadUrl+'"> ' +
					'<param name="QueryTemplistUrl" value="'+QueryTemplistUrl+'"> ' +
					'<param name="QueryTempnameUrl" value="'+QueryTempleteUrl+'"> ' +
					'<param name="SaveReportURL" value=" '+SaveReportURL+'">' +				
					'</OBJECT>';			
					$('head').append(printObj);
					
					// 设置本js内部全局控件对象
					_bsNetFun = document.getElementById('_bsNetFun');
				}});
			}
			
			
			this.loopGetObj(function(){
				// 获取选择模板界面
				var $node = $(showTemplatePanel());
				// 获取模板列表
				var url = "egov/asset/template/queryTemplateDetailPage.do";
				if(conf == undefined){
					conf = "";
				}
				var netFun = $('#_bsNetFun');
				var bsPrintObject = document.getElementById("_bsNetFun");
			    if (bsPrintObject == undefined || bsPrintObject == null) //if ((_ReportOK == null) || (_ReportOK.Register == undefined))
			    {
			    	$messager.warn("本系统需要安装博思网络组件 才能保证其正常运行");
			    	return;
			    }
				// 打开选择打印模板窗口
				showTemplate.showPage(moduleId,null,url,printFunc,previewFunc,params,null);
			},arguments)
		},
		
		/**
		 * 打印设置
		 * @param moduleId 分类Id
		 */
		printSet : function(moduleId){
			
			if(Control_Version='V2'){
				bsPrintObj().setPrinter(moduleId);
//				showPrintDialog(moduleId) ;
			}else{
				//第一版参数与第二版不一样
				bsPrintObj().setPrinter(moduleId);
			}
			//alert(bsPrintObj().QueryTempleteList(moduleId)) ;
			//bsPrintObj().setPrinter('ModuleId=20160101&Printer=333&Paper=A4&MarginUp=1&MarginDown=1&MarginLeft=1&MarginRight=1&OffsetX=1&OffsetY=1');
		},
		/**
		 * 获取默认打印模板ID
		 * @param moduleId 模块Id[分类Id]
		 * @type 当前打印类型   bill or form
		 */
		getDefualtTemplateId : function(moduleId){
			
			if(moduleId == null||moduleId == ''){
				return getDefaultTemplateId(type);
			}
			return printUtil.QueryDefaultTemplateId(moduleId,"form");
		},
		
		
		/**
		 * 设置本地属性
		 * @param node 节点
		 * @param key 关键值
		 * @param value
		 */
		writeProperty : function(node, key, value) {
			
			if (node == null || node == '') {
				$messager.warn("节点不能为空");
				return;
			}
			if (key == null || key == '') {
				$messager.warn("关键字不能为空");
				return;
			}

			bsPrintObj().setLocal(node, key, value);
		},
		/**
		 * 获取本地属性
		 * @param node 
		 * @param key
		 */
		readProperty : function(node, key) {			
	
			if (node == null || node == '') {
				$messager.warn("节点不能为空");
				return;
			}
			if (key == null || key == '') {
				$messager.warn("关键字不能为空");
				return;
			}
			//更改取数设置。如果取的是打印机配置，替换 /
			var PropertyValue =  undefined;
			try{
				PropertyValue = bsPrintObj().getLocal(node, key);
			}catch(e){
				dealErrorEvent(e)  ;
			}
			return PropertyValue;
		},
		/**
		 * 根据url获取数据
		 * @param url 
		 */
		queryData : function(url) {
				
			return bsPrintObj().QueryData(url);
		},
		/**
		 * 根据url获取下载
		 * @param url 
		 */
		downLoad : function(url) {
			
			return bsPrintObj().DownLoad(url);
		},
		
		/**
		 * 填充打印页面的内容
		 * @param moduleCode 模块编码
		 * @param dataUrl 数据源地址
		 * @param type  类型 票据 还是模块
		 * @author Q.R.H 2016-08-30
		 */
		doFillView:function(moduleCode,dataUrl,type){
			tempId=mainobj.showFormReportView(moduleCode,HostUrl+dataUrl,null,type,true);
		},
		

	}
	
	
	
	
	$.extend(mainobj);
	return mainobj;
});
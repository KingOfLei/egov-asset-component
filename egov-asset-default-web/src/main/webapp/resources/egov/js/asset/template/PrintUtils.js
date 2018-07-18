define(["resources/egov/js/asset/template/CreatePrintControl"], function(BsNetFun) {
	/**
	 * 如果开发人员未定义的设置参数,则默认使用定制好的参数
	 */
	var initControllerOption = {
		StaticBox : {
			Center : 0,
			Point : 15,
			Bold : true,
			Top : 0.4,
			Left : 0,
			Width : 5.64,
			Height : 0.58
		},
		MemoBox : {
			Center : 0,
			Point : 15,
			Bold : true,
			Top : 0.4,
			Left : 0,
			Width : 5.64,
			Height : 0.79
		}
	};
	/**
	 * 打印列的参数
	 */
	var reportGrid = {
		titleHeight : '',
		contentHeight : 0.78
	};

	/**
	 * 对齐方式
	 */
	var textAlign = {
		left : 33,
		right : 36,
		center : 34
	};
	/**
	 * 设置打印策略
	 */
	var printAdaptMethod={
			grcpamToNewPageRFCEx:8,
			grcpamToNewPageRFC:7,
			grcpamToNewPageEx:6,
			grcpamResizeToFit :3,
			grcpamShrinkToFit:4
	};
	var staticFont = "微软雅黑";
	// 每个控件之间的水平分割距离
	var splitdistance = 0.3;
	var paperWidth = 21;

	/**
	 * 定义报表头的默认属性 target 定位对照控件 position : x y x 或 y方向采取定位 exp:'表达式'
	 */
	var header = {
		title : {
			font : {
				Bold : true,
				Point : 16.0
			}
		},
		height : '2',
		controls : [{
					id : 'title',
					name : '报表标题',
					type : 'title',
					control : 'StaticBox',
					needValue : false,
					center : 1,
					valueWidth : 4.5
				}, {
					id : 'condition',
					name : '查询单位',
					needValue : true,
					valueWidth : 1.2,
					type : 'condition',
					control : 'StaticBox',
					option : {
						target : 'title',
						position : 'y'
					},
					exp : '[title]'
				}, {
					id : 'condition1',
					name : '时间',
					needValue : true,
					valueWidth : 1.2,
					type : 'condition',
					control : 'StaticBox',
					option : {
						target : 'condition',
						position : 'x'
					},
					exp : '[title]'
				}]
	};
	/**
	 * 定义报表头条件显示的位置
	 */
	var _condition = {};
	/**
	 * 报表的对象
	 */
	var gridReport = {};

	/**
	 * 查询模板列表
	 * 
	 * @moduleId 模块Id
	 * @callback 成功查询，返回数据，需要I回调的方法
	 */
	function queryTemplateList(moduleId, callback) {
		var url = "egov/asset/aims/template/aimstemplatedetail/queryTemplateDetailPage.do";
		/*$app.ajax.ajaxCall({
			url : url,
			data : {
				moduleId:moduleId
			},
			callback : function(templateList) {
				console.log(templateList);
				return templateList.data;
			}
		});*/
		var templateList = $.ajax({
			url : url,
			async : false,
			data : {
				moduleId : moduleId
			},
		}).responseText;
		console.log(templateList);
		return templateList.data;

	}
	
	/*function getObj(){
		var obj = BsNetFun.CreatePrint('Report');
		return obj;
	}*/
	function getReport(){
		var obj = BsNetFun.CreatePrint('Report');
		var tempWrap = $("<div>");// 创建安一个临时嵌套
		tempWrap.html(obj.html);
		$('body').append(tempWrap);
		var Report = document.getElementById("Report");
		try{
			//注册
			Report.Register(obj.username, obj.serialNo);
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("还未安装打印控件！");
			return false;
		}
		return Report;
	}
	
	/**
	 * 获取当前模块的默认模板,默认打印表单
	 * 
	 * @moduleId 票据批次号 或者模块号
	 * @type bill 票据打印 form 模块打印
	 */
	function getDefualtTemplateId(moduleId, type) {
		if (type == null) {
			type = "form";
		}
		var json = $.ajax({
					url : _contextPath
							+ "/printFace/queryTempNameByModuleCode.do?type="
							+ type+'&moduleCode='+moduleId,
					async : false,
					data : {},
					dataType : 'json',
					type : 'POST'
				}).responseText;
		return json;
	}
	
	setPrint =  function(report, localSet) {
	    report.Printer.LeftMargin = localSet.MarginLeft;
		report.Printer.RightMargin = localSet.MarginRight;
		report.Printer.TopMargin = localSet.MarginUp;
		report.Printer.BottomMargin = localSet.MarginDown;
		report.Printer.PrintOffsetX = localSet.OffsetX;
		report.Printer.PrintOffsetY = localSet.OffsetY;
		report.Printer.PrinterName = localSet.Printer;
	}

	/**
	 * 过滤json数据，若出现undefined则用" "代替
	 */
	jsonObjectFilter = function(data) {
		for (var i in data) {

			if (typeof(data[i]) == "string") {
				if (data[i] == null || $.trim(data[i]).length == 0) {
					data[i] = "";
				}
			}
			if (typeof(data[i]) == "object" && data[i] != null) {
				jsonObjectFilter(data[i]);
			}
			if (data[i] == null) {
				data[i] = "";
			}
		}
	}
	/**
	 * 打印模板
	 * 
	 * @templateId 类别Id
	 * @dataUrl 获取数据的地址
	 * @isPreview true 预览 false 打印
	 */
	PrintUrl = function(LoadReportUrl, dataUrl, isPreview, conf,callBack) {
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportUrl);
			Report.LoadDataFromURL(dataUrl);
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		}
	}
	
	/**
	 * 未用
	 * 打印多个子报表
	 * @templateId 类别Id
	 * @dataUrl 获取数据的地址
	 * @isPreview true 预览 false 打印
	 */
	PrintMultipleReport = function(LoadReportURL,map, isPreview, conf,callBack) {
		var obj = BsNetFun.CreatePrint('Report');
		var tempWrap = $("<div>");// 创建安一个临时嵌套
		tempWrap.html(obj.html);
		$('body').append(tempWrap);
		var ReportViewer = document.getElementById("Report");
		try {
			//注册
			ReportViewer.Register(obj.username, obj.serialNo);
			//加载报表模板
			ReportViewer.LoadFromURL(LoadReportURL);
			//设置子报表
			if (!isPreview) {
				ReportViewer.Print(false);
			} else {
				ReportViewer.PrintPreview(false);
			}			
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
//			$messager.warn("还未安装打印控件！");
			$A.assetMsg.warn("还未安装打印控件！");
			return false;
		}		
	
	}
	//处理清单
	PrintMultipleReport1 = function(LoadReportURL,urlParam, isPreview, conf,callBack) {
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportURL);
		}
		try {
			//设置子报表
			//无害化 01
			var whh = Report.ControlByName("WHH").AsSubReport.Report;
			//残值回收  02
			var czhs = Report.ControlByName("CZHS").AsSubReport.Report;
			//自行处置 03
			var dwzxcl = Report.ControlByName("DWZXCL").AsSubReport.Report;
			//专管机构销毁 04
			var zgjgxh = Report.ControlByName("ZGJGXH").AsSubReport.Report;
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
//			$messager.warn("子报表不存在！");
			$A.assetMsg.warn("子报表不存在！");
			return false;
		}
		try {
			whh.LoadDataFromURL(urlParam + "&disposeModeCode=01");
			czhs.LoadDataFromURL(urlParam + "&disposeModeCode=02");
			dwzxcl.LoadDataFromURL(urlParam + "&disposeModeCode=03");
			zgjgxh.LoadDataFromURL(urlParam + "&disposeModeCode=04");
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("获取数据失败！");
			return false;
		}	
	
	}
	//回收单
	PrintMultipleReport2 = function(LoadReportURL,urlParam, isPreview, conf,callBack) {
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportURL);
		}
		try{
			//设置子报表
			//废弃电器电子产品回收处理接收单
			var fqdzjsd = Report.ControlByName("fqdzjsd").AsSubReport.Report;
			//报废资产残值回收处理接收单
			var czhsjsd = Report.ControlByName("czhsjsd").AsSubReport.Report;
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("子报表不存在！");
			return false;
		}
		try{
			fqdzjsd.LoadDataFromURL(urlParam + "&recycleType=1");
			czhsjsd.LoadDataFromURL(urlParam + "&recycleType=2");
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("获取数据失败！");
			return false;
		}
	}
	
	//年报单户表
	PrintMultipleReport3 = function(LoadReportURL,urlParam,urlParam4Single, params, isPreview, conf,callBack) {
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportURL);
		}
		try {
			//设置子报表
			//封面
			var fm = Report.ControlByName("FM").AsSubReport.Report;
			//负债表
			var fzb = Report.ControlByName("FZB").AsSubReport.Report;
			//机构人员情况表
			var jgryqkb = Report.ControlByName("JGRYQKB").AsSubReport.Report;
			//固定资产情况表
			var gdzcqkb = Report.ControlByName("GDZCQKB").AsSubReport.Report;
			//固定资产情况表 续表
			var gdzcqkb_xb = Report.ControlByName("GDZCQKB_XB1").AsSubReport.Report;
			//固定资产情况表 续表
			var gdzcqkb_xb2 = Report.ControlByName("GDZCQKB_XB2").AsSubReport.Report;
			//续表3
			var gdzcqkb_xb3 = Report.ControlByName("GDZCQKB_XB3").AsSubReport.Report;
			//无形资产情况表
			var wxzcqkb = Report.ControlByName("WXZCQKB").AsSubReport.Report;
			//无形资产情况表 续表
			var wxzcqkb_xb = Report.ControlByName("WXZCQKB_XB1").AsSubReport.Report;
			var wxzcqkb_xb2 = Report.ControlByName("WXZCQKB_XB2").AsSubReport.Report;
			var wxzcqkb_xb3 = Report.ControlByName("WXZCQKB_XB3").AsSubReport.Report;
			//土地情况表
			var tdqkb = Report.ControlByName("TDQKB").AsSubReport.Report;
			//土地情况表 续表
			var tdqkb_xb = Report.ControlByName("TDQKB_XB").AsSubReport.Report;
			//房屋情况表
			var fwqkb = Report.ControlByName("FWQKB").AsSubReport.Report;
			//房屋情况表2
			var fwqkb2 = Report.ControlByName("FWQKB_XB2").AsSubReport.Report;
			//房屋情况表3
			var fwqkb3 = Report.ControlByName("FWQKB_XB3").AsSubReport.Report;
			//车辆情况表
			var clqkb = Report.ControlByName("CLQKB").AsSubReport.Report;
			var clqkb_xb = Report.ControlByName("CLQKB_XB").AsSubReport.Report;
			//大型设备情况表
			var dxsbqkb = Report.ControlByName("DXSBQKB").AsSubReport.Report;
			//在建工程情况表
			var zjgcqkb = Report.ControlByName("ZJGCQKB").AsSubReport.Report;
			//出租出借情况表
			var czcjqkb = Report.ControlByName("CZCJQKB").AsSubReport.Report;
			//资产处置情况表
			var zcczqkb = Report.ControlByName("ZCCZQKB").AsSubReport.Report;
			//单位投资情况表
			var dwtzqkb = Report.ControlByName("DWTZQKB").AsSubReport.Report;
			//单位投资情况表 续表
			var dwtzqkb_xb = Report.ControlByName("DWTZQKB_XB").AsSubReport.Report;
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("子报表不存在！");
			return false;
		}
		try {
			//封面
			fm.LoadDataFromURL(findUrl(urlParam4Single,params[0]));
			//负债表
			fzb.LoadDataFromURL(findUrl(urlParam,params[1]));
			//机构人员情况表
			jgryqkb.LoadDataFromURL(findUrl(urlParam,params[2]));
			//固定资产情况表
			gdzcqkb.LoadDataFromURL(findUrl(urlParam,params[3]));
			//固定资产情况表 续表
			gdzcqkb_xb.LoadDataFromURL(findUrl(urlParam,params[3]));
			//固定资产情况表 续表
			gdzcqkb_xb2.LoadDataFromURL(findUrl(urlParam,params[3]));
			//续表3
			gdzcqkb_xb3.LoadDataFromURL(findUrl(urlParam,params[3]));
			//无形资产情况表
			wxzcqkb.LoadDataFromURL(findUrl(urlParam,params[4]));
			//无形资产情况表 续表
			wxzcqkb_xb.LoadDataFromURL(findUrl(urlParam,params[4]));
			wxzcqkb_xb2.LoadDataFromURL(findUrl(urlParam,params[4]));
			wxzcqkb_xb3.LoadDataFromURL(findUrl(urlParam,params[4]));
			//土地情况表
			tdqkb.LoadDataFromURL(findUrl(urlParam,params[5]));
			//土地情况表 续表
			tdqkb_xb.LoadDataFromURL(findUrl(urlParam,params[5]));
			//房屋情况表
			fwqkb.LoadDataFromURL(findUrl(urlParam,params[6]));
			//房屋情况表2
			fwqkb2.LoadDataFromURL(findUrl(urlParam,params[6]));
			//房屋情况表3
			fwqkb3.LoadDataFromURL(findUrl(urlParam,params[6]));
			//车辆情况表
			clqkb.LoadDataFromURL(findUrl(urlParam,params[7]));
			clqkb_xb.LoadDataFromURL(findUrl(urlParam,params[7]));
			//大型设备情况表
			dxsbqkb.LoadDataFromURL(findUrl(urlParam,params[8]));
			//在建工程情况表
			zjgcqkb.LoadDataFromURL(findUrl(urlParam,params[9]));
			//出租出借情况表
			czcjqkb.LoadDataFromURL(findUrl(urlParam,params[10]));
			//资产处置情况表
			zcczqkb.LoadDataFromURL(findUrl(urlParam,params[11]));
			//单位投资情况表
			dwtzqkb.LoadDataFromURL(findUrl(urlParam,params[12]));
			//单位投资情况表 续表
			dwtzqkb_xb.LoadDataFromURL(findUrl(urlParam,params[12]));
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("获取数据失败！");
			return false;
		}
	}
	//年报单户表获取数据的参数url
	function findUrl(urlParam,params){
		var orgPid = params.orgPid!=null?params.orgPid:"";
			var para = "orgId="+params.orgId+"&orgPid="+orgPid+"&orgCode="+params.orgCode+
			"&orgPeriod="+params.orgPeriod+"&reportId="+params.reportId+"&rptDataTable="+params.rptDataTable+
			"&rptType="+params.rptType+"&switchFlag="+params.switchFlag+"&orgName="+params.orgName;
			return urlParam + "?" + para;
	}
	
	//固定资产报废处置表
	PrintMultipleReport4 = function(LoadReportURL, mxbUrl, hzbUrl, isPreview, conf, callBack) {
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportURL);
		}
		try {
			//设置子报表
			//报废处置明细表
			var bfczmxb = Report.ControlByName("BFCZMXB").AsSubReport.Report;
			//报废处置汇总表
			var bfczhzb = Report.ControlByName("BFCZHZB").AsSubReport.Report;
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error);
			}
			$A.assetMsg.warn("子报表不存在！");
			return false;
		}
		try {
			bfczmxb.LoadDataFromURL(mxbUrl);
			bfczhzb.LoadDataFromURL(hzbUrl);
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("获取数据失败！");
			return false;
		}
	}
	
	//固定资产处置表
	PrintMultipleReport5 = function(LoadReportURL, mxbUrl, hzbUrl, isPreview, conf,callBack) {
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportURL);
		}
		try {
			//设置子报表
			//处置明细表
			var czmxb = Report.ControlByName("CZMXB").AsSubReport.Report;
			//处置汇总表
			var czhzb = Report.ControlByName("CZHZB").AsSubReport.Report;
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("子报表不存在！");
			return false;
		}
		try {
			czmxb.LoadDataFromURL(mxbUrl);
			czhzb.LoadDataFromURL(hzbUrl);
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$messager.warn("获取数据失败！");
			return false;
		}
	},
	//年报汇总表打印报表
	PrintMultipleReport6 = function(LoadReportURL, urlParam,urlParam4Single, params, isPreview, conf,callBack){
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportURL);
		}
		try {
			//设置子报表
			//封面
			var fm = Report.ControlByName("FM").AsSubReport.Report;
			//负债表
			var fzb = Report.ControlByName("FZB").AsSubReport.Report;
			//机构人员情况表
			var jgryqkb = Report.ControlByName("JGRYQKB").AsSubReport.Report;
			//固定资产情况汇总表
			var gdzcqkhzb = Report.ControlByName("GDZCQKHZB").AsSubReport.Report;
			//固定资产情况汇总表 续表1
			var gdzcqkhzb_xb1 = Report.ControlByName("GDZCQKHZB_XB1").AsSubReport.Report;
			//固定资产情况汇总表 续表2
			var gdzcqkhzb_xb2 = Report.ControlByName("GDZCQKHZB_XB2").AsSubReport.Report;
			//固定资产情况汇总表续表3
			var gdzcqkhzb_xb3 = Report.ControlByName("GDZCQKHZB_XB3").AsSubReport.Report;
			//无形资产情况表
			var wxzcqkhzb = Report.ControlByName("WXZCQKHZB").AsSubReport.Report;
			//无形资产情况表 续表
			var wxzcqkhzb_xb1 = Report.ControlByName("WXZCQKHZB_XB1").AsSubReport.Report;
			var wxzcqkhzb_xb2 = Report.ControlByName("WXZCQKHZB_XB2").AsSubReport.Report;
			var wxzcqkhzb_xb3 = Report.ControlByName("WXZCQKHZB_XB3").AsSubReport.Report;
			//土地情况表
			var tdqkb = Report.ControlByName("SubReport15").AsSubReport.Report;
			//房屋情况表
			var fwqkb = Report.ControlByName("SubReport16").AsSubReport.Report;
			//房屋情况表2
			var fwqkb2 = Report.ControlByName("SubReport26").AsSubReport.Report;
			//车辆情况表
			var clqkb = Report.ControlByName("SubReport17").AsSubReport.Report;
			//车辆情况表 续表一
			var clqkb_xb1 = Report.ControlByName("SubReport18").AsSubReport.Report;
			//车辆情况表 续表二
			var clqkb_xb2 = Report.ControlByName("SubReport19").AsSubReport.Report;
			//大型设备情况表
			var dxsbqkb = Report.ControlByName("SubReport20").AsSubReport.Report;
			//大型设备情况表 续表一
			var dxsbqkb_xb1 = Report.ControlByName("SubReport21").AsSubReport.Report;
			//在建工程情况表
			var zjgcqkb = Report.ControlByName("SubReport22").AsSubReport.Report;
			//在建工程情况表  续表一
			var zjgcqkb_xb1 = Report.ControlByName("SubReport28").AsSubReport.Report;
			//出租出借情况表
			var czcjqkb = Report.ControlByName("SubReport23").AsSubReport.Report;
			//资产处置情况表
			var zcczqkb = Report.ControlByName("SubReport24").AsSubReport.Report;
			//单位投资情况表
			var dwtzqkb = Report.ControlByName("SubReport25").AsSubReport.Report;
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("子报表不存在！");
			return false;
		}
		try {
			fm.LoadDataFromURL(findUrl(urlParam4Single,params[0]));
			//负债表
			fzb.LoadDataFromURL(findUrl(urlParam,params[1]));
			//机构人员情况表
			jgryqkb.LoadDataFromURL(findUrl(urlParam,params[2]));
			//固定资产情况汇总表
			gdzcqkhzb.LoadDataFromURL(findUrl(urlParam,params[3]));
			//固定资产情况汇总表 续表1
			gdzcqkhzb_xb1.LoadDataFromURL(findUrl(urlParam,params[3]));
			//固定资产情况汇总表 续表2
			gdzcqkhzb_xb2.LoadDataFromURL(findUrl(urlParam,params[3]));
			//固定资产情况汇总表续表3
			gdzcqkhzb_xb3.LoadDataFromURL(findUrl(urlParam,params[3]));
			//无形资产情况表
			wxzcqkhzb.LoadDataFromURL(findUrl(urlParam,params[4]));
			//无形资产情况表 续表
			wxzcqkhzb_xb1.LoadDataFromURL(findUrl(urlParam,params[4]));
			wxzcqkhzb_xb2.LoadDataFromURL(findUrl(urlParam,params[4]));
			wxzcqkhzb_xb3.LoadDataFromURL(findUrl(urlParam,params[4]));
			//土地情况表
			tdqkb.LoadDataFromURL(findUrl(urlParam,params[5]));
			//房屋情况表
			fwqkb.LoadDataFromURL(findUrl(urlParam,params[6]));
			//房屋情况表2
			fwqkb2.LoadDataFromURL(findUrl(urlParam,params[6]));
			//车辆情况表
			clqkb.LoadDataFromURL(findUrl(urlParam,params[7]));
			//车辆情况表 续表一
			clqkb_xb1.LoadDataFromURL(findUrl(urlParam,params[7]));
			//车辆情况表 续表二
			clqkb_xb2.LoadDataFromURL(findUrl(urlParam,params[7]));
			//大型设备情况表
			dxsbqkb.LoadDataFromURL(findUrl(urlParam,params[8]));
			//大型设备情况表 续表一
			dxsbqkb_xb1.LoadDataFromURL(findUrl(urlParam,params[8]));
			//在建工程情况表
			zjgcqkb.LoadDataFromURL(findUrl(urlParam,params[9]));
			//在建工程情况表  续表一
			zjgcqkb_xb1.LoadDataFromURL(findUrl(urlParam,params[9]));
			//出租出借情况表
			czcjqkb.LoadDataFromURL(findUrl(urlParam,params[10]));
			//资产处置情况表
			zcczqkb.LoadDataFromURL(findUrl(urlParam,params[11]));
			//单位投资情况表
			dwtzqkb.LoadDataFromURL(findUrl(urlParam,params[12]));
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("获取数据失败！");
			return false;
		}
	}
	
	//固定资产报损处置表
	PrintMultipleReport7 = function(LoadReportURL, mxbUrl, hzbUrl, isPreview, conf, callBack) {
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportURL);
		}
		try {
			//设置子报表
			//报损处置明细表
			var bsczmxb = Report.ControlByName("BSCZMXB").AsSubReport.Report;
			//报损处置汇总表
			var bsczhzb = Report.ControlByName("BSCZHZB").AsSubReport.Report;
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error);
			}
			$A.assetMsg.warn("子报表不存在！");
			return false;
		}
		try {
			bsczmxb.LoadDataFromURL(mxbUrl);
			bsczhzb.LoadDataFromURL(hzbUrl);
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("获取数据失败！");
			return false;
		}
	},
	//打印多个子报表 统一调用口
	PrintMultiReport = function(LoadReportURL,dataUrl, isPreview, conf, callBack){
		var Report = getReport();
		if(Report != null){
			//加载报表模板
			Report.LoadFromURL(LoadReportURL);
		}
		var subReportList=[],subReport;
		for(var i=0,len=dataUrl.length;i<len;i++){
			subReport=dataUrl[i];
			var reportId=subReport["id"];
			var reportDataUrl=subReport["dataUrl"];
			try{
				var subRpObjet = Report.ControlByName(reportId).AsSubReport.Report;
				if (subRpObjet){
					subReportList.push({subRpObjet:subRpObjet,dataUrl:reportDataUrl})
				}
			}catch(error){	
				if(callBack&&typeof callBack =='function'){
					callBack(error);
				}
				$A.assetMsg.warn("子报表不存在！");
				return false;
			}
		}
		try {
			for(var i=0,len=subReportList.length;i<len;i++){
				subReport=subReportList[i];
				subReport["subRpObjet"].LoadDataFromURL(subReport["dataUrl"])
			}
			if (!isPreview) {
				Report.Print(false);
			} else {
				Report.PrintPreview(false);
			}
		} catch (error) {
			if(callBack&&typeof callBack =='function'){
				callBack(error) ;
			}
			$A.assetMsg.warn("获取数据失败！");
			return false;
		}
	}
	
	hideReportWithoutData = function(report,ReportViewer,index){
		var val = report.DetailGrid;	
		if(val.Recordset.RecordCount == 0){
			//隐藏报表头
			var sections = report.Sections;
			for(i = 1; i <=sections.Count;i++){
				var section = sections.Item(i);
				section.Height = 0;
			}
			//隐藏列
			var columns = report.DetailGrid.Columns;
			for(j = 1; j <=columns.Count;j++){
				var column = columns.Item(j);
				column.Visible = false;
			}	
		}else{
			var sections = ReportViewer.Sections;
			var section = sections.Item(index);
			section.Height = 1*val.Recordset.RecordCount; 
		}		
	}
	
	hideReport = function(report){	
		//隐藏报表头
		var sections = report.Sections;
		for(i = 1; i <=sections.Count;i++){
			var section = sections.Item(i);
			section.Height = 0;
		}
		//隐藏列
		var columns = report.DetailGrid.Columns;
		for(j = 1; j <=columns.Count;j++){
			var column = columns.Item(j);
			column.Visible = false;
		}			
	}
	
	showReport = function(ReportViewer,index,list){	
		var sections = ReportViewer.Sections;
		var section = sections.Item(index);
		section.Height = list[index-2]; 			
	}


	/**
	 * 生成网格报表头
	 */
	function createGripReportHead(report, options, gridId) {
		try{
			report.InsertPageHeader();
			report.PageHeader.Height = 0.11;
			
//			report.Printer.PaperName = 'A3';
//			report.Printer.LeftMargin = 1 ;
//			report.Printer.RightMargin = 1 ;
//			report.Printer.TopMargin = 1 ;
//			report.Printer.BottomMargin = 1 ;			
			
			var option = $.extend({}, header, options);
			var Reportheader = report.InsertReportHeader();
			Reportheader.Height = option.height;
			Reportheader.RepeatOnPage = true;// 每页重复打印
			for (var i = 0; i < option.controls.length; i++) {

				gridReport[option.controls[i].id] = controlFactory(Reportheader,option.controls[i].control);
				gridReport[option.controls[i].id].Text = option.controls[i].name;

				// 设置字体 和文字大小
				gridReport[option.controls[i].id].Font.Name = staticFont;
				gridReport[option.controls[i].id].Font.Point = 11;
				if (option.controls[i].center != null) {
					gridReport[option.controls[i].id].Center = option.controls[i].center;
				} else {
					gridReport[option.controls[i].id].Center = 0;
				}
				// 判断该控件是否有定义字体属性
				if (option.controls[i].type == "title") {
					// gridReport[option.controls[i].id].Font.Bold=option.title.font.Bold;
					gridReport[option.controls[i].id].Font.Name = staticFont;
					// 设置文字间隔
					gridReport[option.controls[i].id].CharacterSpacing = 600;
					gridReport[option.controls[i].id].Font.Point = option.title.font.Point;
					option.controls[i].valueWidth = paperWidth;
					gridReport[option.controls[i].id].TextAlign = textAlign.center;
				}else{
			   	        gridReport[option.controls[i].id].Width = option.controls[i].name.length * 1 ;		   	 
				  }
				if (option.controls[i].option != null) {// 开发人员未设置
					var cur_option = initControllerOption[option.controls[i].control];
					setControlOption(gridReport[option.controls[i].id], cur_option,
							option.controls, i, "name", gridId,
							option.controls[i].type);
				} else {
					// 使用我们默认的参数
					var cur_option = initControllerOption[option.controls[i].control];
					setControlOption(gridReport[option.controls[i].id], cur_option,
							option.controls, i, "name", gridId,
							option.controls[i].type);

				}
				// 根据
				if (option.controls[i].needValue) {// 如果该控件需要创建一个对应的值控件
					var cur_option = initControllerOption[option.controls[i].control];
					cur_option.Width = cur_option.Width * 1.5 ;
					gridReport[option.controls[i].id + "_value"] = controlFactory(Reportheader, option.controls[i].control);
					gridReport[option.controls[i].id + "_value"].DataField = option.controls[i].exp;
					gridReport[option.controls[i].id + "_value"].BorderWidth = 1;
					gridReport[option.controls[i].id + "_value"].BorderStyles = 8;
					gridReport[option.controls[i].id + "_value"].Font.Name = staticFont;
					gridReport[option.controls[i].id + "_value"].Font.Point = 10;
					// gridReport[option.controls[i].id+"_value"].ShrinkFontToFit=true;
					option.controls[i].option = {
						target : option.controls[i].id,
						position : 'x'
					};
					// 设置改值对应的属性信息
					setControlOption(gridReport[option.controls[i].id + "_value"],cur_option, option.controls, i, option.controls[i].exp,gridId, "value");
					if (option.controls[i].value[option.controls[i].exp] != null) {
						gridReport[option.controls[i].id + "_value"].Text = option.controls[i].value[option.controls[i].exp];
					}
				}

				// 如果当前操作的是最后一个，则通过最后一个，算出报表头的高度
				if ((i == option.controls.length - 1) && option.controls.length > 1) {
					var y = gridReport[option.controls[i].id + "_value"].Top;
					var height = gridReport[option.controls[i].id + "_value"].Height;
					var realHeight = y + height + 0.35;
					Reportheader.Height = realHeight;
				} else {
					var y = gridReport[option.controls[i].id].Top;
					var height = gridReport[option.controls[i].id].Height;
					var realHeight = y + height + 0.35;
					Reportheader.Height = realHeight;
				}

			}
		}catch(e){
			$A.messager.warn(e) ;
		}
	}
	/**
	 * @controls 所有控件集合
	 * @curIndex 当前要运算的索引
	 */
	function getAutoLayOut(controls, curIndex, gridId, type) {
		var curCtrol = controls[curIndex];
		var preCtrol = controls[curIndex - 1];
		// 设置对照对象
		curCtrol.option.target = preCtrol.id;
		// 判断布局方式
		width = getControlSpace(gridReport, preCtrol, curCtrol, gridId, type);
		if (parseFloat(width) > parseFloat(21)) {
			curCtrol.option.position = "y";
			if (curIndex > 2) {
				curCtrol.option.target = getReferToObjectFlag(gridReport,
						controls, curIndex);
				curCtrol.option.linefeed = true;
			}
		} else {
			curCtrol.option.position = "x";
		}

	}
	/**
	 * 获取对照对象,即每行的第一个
	 */
	function getReferToObjectFlag(gridReport, controls, curIndex) {
		// 查处前面第一个y坐标不一样的
		for (var i = curIndex - 1; i >= 0; i--) {
			var preObj = gridReport[controls[i].id];
			var ppreObj = gridReport[controls[i - 1].id];
			if (ppreObj.Top != preObj.Top) {
				return controls[i].id;
			}

		}
		return "";
	}
	/**
	 * @gridReport 已经转成锐浪控件的对象
	 * @curControl 要计算的控件
	 */
	function getControlSpace(gridReport, preControl, curControl, gridId, type) {
		// 如果用户有设置，值显示的宽度，则取用户设置的，否则，计算文字所占的虚拟空间
		// 先计算标签控件
		if (curControl != null && curControl.valueWidth != null) {
			width = curControl.valueWidth;
		} else {
			width = getVirualWidth(word, type);

		}
		// 计算值控件
		if (curControl != null)
			width = parseFloat(getReportControlWidth(curControl.id, gridId))
					+ parseFloat(width);

		// /判断当前这个坐标会不会超出页面
		var _control = null;
		if (preControl.needValue) {
			_control = gridReport[preControl.id + "_value"];
		} else {
			_control = gridReport[preControl.id];
		}
		return (parseFloat(_control.Left) + parseFloat(_control.Width) + parseFloat(width))

	}

	/**
	 * 设置一个属性的控件属性
	 * 
	 * @target 相对定位的对照对象
	 * @option 默认配置
	 */
	function setControlOption(control, option, controls, index, valueField,
			gridId, type) {
		var posoption = null;
		var center = null;
		var controlOption = null;
		// 获取参照对象的位置信息
		var target_x = null;
		var target_y = null;
		if ("value" == type) {
			word = controls[index].value[valueField];
		} else {
			word = controls[index][valueField];
		}
		if (controls[index].option != null) {
			posoption = controls[index].option;
		}
		center = controls[index].center;
		controlOption = controls[index];
		// 如果position为auto ,则判断一下当前控件需要采取的排列方式

		if (posoption != null && posoption.position == "auto") {
			getAutoLayOut(controls, index, gridId, type);
		}
		if (posoption != null && posoption.target != null
				&& $.trim(posoption.target).length > 0) {

			var targetoption = null;
			if (posoption.linefeed) {
				targetoption = gridReport[posoption.target];
			} else {
				targetoption = gridReport[posoption.target + "_value"];
			}
			if (targetoption == control || targetoption == null) {
				targetoption = gridReport[posoption.target];
			}
			// 判断当前控件所要采取的控件定位方式
			var pos = posoption.position.split(",");

			for (var i = 0; i < pos.length; i++) {
				var style = pos[i];
				if (style == "x") {
					if (controlOption != null && controlOption.needSplit
							&& type != "value") {
						target_x = targetoption.Left + targetoption.Width
								+ splitdistance;
					} else {
						target_x = targetoption.Left + targetoption.Width
					}
					// 获取是否该控件存在对应的值框

				}
				if (style == "y") {
					target_y = targetoption.Top + targetoption.Height + 0.2;
				}
				if (target_y == null) {
					target_y = targetoption.Top;
				}
				if (target_x == null) {
					target_x = targetoption.Left;
				}
			}
		}

		if (target_x == null) {
			control.Left = option.Left;
		} else {
			control.Left = target_x;
		}
		if (target_y == null) {
			control.Top = option.Top;
		} else {
			control.Top = target_y;
		}
		var width;
		// 如果用户有设置，值显示的宽度，则取用户设置的，否则，计算文字所占的虚拟空间
		if (controlOption != null && controlOption.valueWidth != null
				&& 'title' == type) {
			width = controlOption.valueWidth;
		} else {
			width = getVirualWidth(word, type);

			if (controlOption != null && "value" == type) {
				var _width = getReportControlWidth(controlOption.id, gridId);
				width = width > _width ? width : _width;
				if (controlOption.valueWidth != null)
					width = parseFloat(controlOption.valueWidth);
			}

			// 判断当前控件是否填满一行

		}
		control.Width = width + 0.1;
		control.Height = option.Height;

	}
	/**
	 * 新网格分组
	 */
	function getGroupColumnsOfNewGrid(gridId) {
		var groupHeader = $A("#" + gridId).grid("getColumns");
		var group = [];

		for (var j = 0; j < groupHeader.center.length; j++) {
			var _groupHeader = {};
			var k = 0;
			var colSpan = groupHeader.center[j].colSpan;
			var startColumnName = groupHeader.center[j].title;
			_groupHeader.name = "bsGroup" + j;
			_groupHeader.numberOfColumns = colSpan;
			_groupHeader.startColumnName = startColumnName;
			_groupHeader.titleText = groupHeader.center[j].title;
			_groupHeader.headers = [];

			var i = 0;
			for (; i < model.length; i++) {
				if (model[i].name == startColumnName) {
					k = i + colSpan;
					break;
				}
			}
			// i=i-1;
			for (; i < k; i++) {
				if (!model[i].hidden) {
					var header = {};
					header.name = model[i].name;
					header.width = model[i].width;
					_groupHeader.headers.push(header);
				}
			}
			group.push(_groupHeader);
		}
		return group;

	}	
	function getNewGridGroupColumns(headerGroup){
			var group = [];
			if(headerGroup&&headerGroup.length>0){
					for(var j=0 ;j<headerGroup.length;j++){
						
							var _groupHeader = {};
							var k = 0;
							var colSpan = headerGroup[j].colspan==null ? 1 : headerGroup[j].colspan;
							var startColumnName = headerGroup[j].id;
							_groupHeader.name = "bsGroup" + j;
							_groupHeader.numberOfColumns = colSpan;
							_groupHeader.startColumnName = startColumnName;
							_groupHeader.titleText = headerGroup[j].title;
							_groupHeader.headers = [];

							if(headerGroup[j].children){
									var childs = headerGroup[j].children ;
									for (var n=0; n <childs.length;n++) {													
											var itemChild = childs[n] ;
											if(!itemChild.sysCol){
													if(itemChild.children){
															var header = getNewGridGroupColumns(itemChild);
															//header.name = childs[n].id;
															//header.width = childs[n].width;
															_groupHeader.headers.push(header);		
													}else{
															var header = {};
															header.name = childs[n].id;
															header.width = childs[n].width;
															_groupHeader.headers.push(header);		
													}
											}								
									}
							}
							group.push(_groupHeader);
					}						
			}
			return group ;
	}
	/**
	 * 获取双网格头的分组情况,即一级网格头下面 有几个二级网格头
	 */
	function getGroupColumns(gridId) {
		var group = [];
		if($A("#" + gridId).data("grid")){
				var columns = $A("#" + gridId).grid("getColumnTree") ;
				group = getNewGridGroupColumns(columns) ;
		}else{
			var model = $A("#" + gridId).bsgrid("getGridParam", "colModel");
			var groupHeader = $A("#" + gridId).bsgrid("getGridParam","gridheadersCfg");	
			for (var j = 0; j < groupHeader.groupHeaders.length; j++) {
				var _groupHeader = {};
				var k = 0;
				var colSpan = groupHeader.groupHeaders[j].numberOfColumns;
				var startColumnName = groupHeader.groupHeaders[j].startColumnName;
				_groupHeader.name = "bsGroup" + j;
				_groupHeader.numberOfColumns = colSpan;
				_groupHeader.startColumnName = startColumnName;
				_groupHeader.titleText = groupHeader.groupHeaders[j].titleText;
				_groupHeader.headers = [];
	
				var i = 0;
				for (; i < model.length; i++) {
					if (model[i].name == startColumnName) {
						k = i + colSpan;
						break;
					}
				}
				// i=i-1;
				for (; i < k; i++) {
					if (!model[i].hidden) {
						var header = {};
						header.name = model[i].name;
						header.width = model[i].width;
						_groupHeader.headers.push(header);
					}
				}
				group.push(_groupHeader);
			}
		}

		return group;

	}
	function GetColorValue(r,g,b){
	  return r + g*256 + b*256*256;
	} 	
	/**
	 * 生成网格模板
	 */
	function createGridReportMain(report, gridId, opt) {
		//是否新网格
		var isNewGrid = false ;
		if($A("#"+gridId).data("grid"))
			isNewGrid = true ;
			
		// /网格列显示有效宽度

//		report.Printer.PaperName = 'A3';
//		
		var printer = report.Printer;
//		report.Printer.LeftMargin = 1 ;
//		report.Printer.RightMargin = 1 ;
//		report.Printer.TopMargin = 1 ;
//		report.Printer.BottomMargin = 1 ;		
		var viewPaper = paperWidth - printer.LeftMargin - printer.RightMargin;
		var rs = {
			multitle : false
		};
		rs.freeControls = {};// 自由格控件
		rs.columnTitles = {};
		report.InsertDetailGrid();
		report.DetailGrid.ColumnTitle.Height = 0.48 * 2;
		report.DetailGrid.ColumnTitle.Font.Name = "微软雅黑";// 设置内容格字体
		report.DetailGrid.ColumnTitle.Font.Point = 11;// 设置内容格字体大小
		report.DetailGrid.ColumnTitle.RepeatStyle = 2;// 标题行每页重复打印
		report.DetailGrid.ColumnContent.Height = reportGrid.contentHeight;// 设置内容格高度

		report.DetailGrid.ColumnContent.Font.Name = "微软雅黑";// 设置字体
		report.DetailGrid.ColumnContent.Font.Point = 10;// 设置文字大小
		report.DetailGrid.PrintAdaptMethod = printAdaptMethod.grcpamToNewPageRFCEx;// 设置打印策略

		
		report.DetailGrid.ColumnTitle.BackColor=GetColorValue(224, 224, 224) ;
		
		report.DetailGrid.ColumnTitle.CanGrow = true ;
		report.DetailGrid.ColumnTitle.CanShrink = true ;
		report.DetailGrid.ColumnTitle.WordWrap=true;//文字超出折行
			  
		report.DetailGrid.ColumnContent.Height = reportGrid.contentHeight;//设置内容格高度	
	

//		report.DetailGrid.PrintAdaptFitText = true ;
		report.DetailGrid.PrintAdaptMethod= printAdaptMethod.grcpamShrinkToFit  ;//printAdaptMethod.grcpamToNewPageRFCEx;//设置打印策略
		
		
		
		var RecordSet = report.DetailGrid.Recordset;
		var data = getGridObjDetail(gridId);

		// 定义数据集
		for (var i = 0; i < data.length; i++) {	
			if(data[i].align==20||data[i].align==36||data[i].align==68)
				RecordSet.AddField(data[i].id, 3);		
			else
				RecordSet.AddField(data[i].id, 1);
		}
		var column = [];
		//
		for (var i = 0; i < data.length; i++) {
			// 去掉操作列
			if(isNewGrid){
				if (data[i].sysCol)
					break;
			}else
				if ("operation" == data[i].id)
					break;
			// 表格每个单元格的宽度
			var cellwidth = convertToCm(data[i].width);// data[i].width*paperWidth;
			// 记录网格单元的宽度
			data[i].realWidth = cellwidth;
			gridReport[data[i].id + "_column"] = report.DetailGrid.AddColumn(
					data[i].id, data[i].name, data[i].id, cellwidth);
			var columns = gridReport[data[i].id + "_column"];
			//设置标题行属性
			var titleCell = columns.TitleCell;
			titleCell.TextAlign = textAlign.center;
			titleCell.WordWrap = true;// 文字超出折行
			titleCell.CanGrow = true;// 行高可伸展
			//设置内容行属性
			var cell = columns.ContentCell;
			cell.TextAlign = data[i].align;
			cell.WordWrap = true;// 文字超出折行
			cell.CanGrow = true;// 行高可伸展
			column.push(data[i].id);
		}

		// /判断网格是否需要分组
		var groupHeader = undefined ;
		if(isNewGrid){
			//新网格是否有头部分组的判断
			groupHeader = $A("#" + gridId).grid("getColumnTree");
			//判断如果网格头的列数小于最底行的列数。就是有多行网格头
			if(groupHeader.length ==column.length)
				groupHeader = null ;
			
		}else
			   groupHeader = $A("#" + gridId).bsgrid("getGridParam","gridheadersCfg");
		

		if (groupHeader != null) {
			rs.multitle = true;
			report.DetailGrid.ColumnTitle.Height = opt.grid.titleHeight;			
			var grouper = getGroupColumns(gridId);
			for (var i = 0; i < grouper.length; i++) {
				var ColumnTitleCell1 = report.DetailGrid.AddGroupTitle(
						grouper[i].name, grouper[i].titleText);
				ColumnTitleCell1.TextAlign = textAlign.center;
				ColumnTitleCell1.Height = 1.0;
				// /判断是否需要添加自由格属性,适用于双层网格头，超出单页，而新叶不显示网格头
				var titleControl = isNeedSetFree(grouper, viewPaper);
				rs.grouper = grouper;
				if (titleControl[grouper[i].name] != null
					&& titleControl[grouper[i].name].length > 0) {
					// /设置双网格头 标题控件
					ColumnTitleCell1.FreeCell = true;// /设置为自由格
					var freeControl = [];
					for (var j = 0; j < titleControl[grouper[i].name].length; j++) {
						var _title_control = controlFactory(ColumnTitleCell1,"MemoBox");
						// 设置控件的宽度
						_title_control.Width = titleControl[grouper[i].name][j].width;
						_title_control.Text = grouper[i].titleText;
						_title_control.Name = grouper[i].name + j;
						_title_control.TextAlign = textAlign.center;
						_title_control.Height = ColumnTitleCell1.Height;
						
						if (titleControl[grouper[i].name][j - 1]) {	
							_title_control.Left = titleControl[grouper[i].name][j- 1].width  * 1.2;
						}else
							_title_control.Left = 0 - (_title_control.Width * 0.3 ) ;
						
						var pj = {};
						pj.ColumnTitleCell1 = ColumnTitleCell1;

						pj.TitleControl = _title_control;
						freeControl.push(pj);
					}
					rs.freeControls[grouper[i].name] = freeControl;
				}
				// 添加这个组下面的标题
				for (var j = 0; j < grouper[i].headers.length; j++) {
					if(!grouper[i].headers)
						continue ;
					if(grouper[i].headers.length>1){
						for(var k = 0; k < grouper[i].headers.length; k++){
							ColumnTitleCell1.EncloseColumn(grouper[i].headers[j].name);
						}
					}else
						ColumnTitleCell1.EncloseColumn(grouper[i].headers[j].name);
				}
				rs.columnTitles[grouper[i].name] = ColumnTitleCell1;
			}	
		}
		if($A("#" + gridId).data("grid")){
		
			
		}else{
			// /判断是否进行分组计算
			var _groupingView = $A("#" + gridId).bsgrid("getGridParam",
					"groupingView");
			var _groups = _groupingView.groups;
			if (_groups != null && _groups.length > 0) {
				var field = "";
				for (var _i = 0; _i < _groups.length; _i++) {
					field += _groups[_i].dataIndex + ";";
				}
				var groupIndex = report.DetailGrid.Groups.Add();
				groupIndex.Header.Height = 0;
				groupIndex.ByFields = field;
				groupIndex.Footer.Height = reportGrid.contentHeight;
	
				// /根据网格的宽度 创建统计控件，同时，控件在合适的位置
				createGroupCountControl(data, groupIndex);
			}
			// /添加所有记录分组
			var footer = $A("#" + gridId).bsgrid("getGridParam", "footerrow");
			if (footer) {
				var groupIndex = report.DetailGrid.Groups.Add();
				groupIndex.Header.Height = 0;
				groupIndex.Footer.Height = reportGrid.contentHeight;
				createGroupCountControl(data, groupIndex);
			}
		}

		
		// /载入数据
		var dataobj = getGridData(gridId);
		setGridReportData(report, dataobj, column);
		//report.SaveToFile("d:\\Program2.grf");
		return rs;
	}

	function updateFreeControl(report, viewPaper, rs) {
		// 清空自由格控件
		for (var i in rs.freeControls) {
			var controls = rs.freeControls[i];
			for (var j = 0; j < controls.length; j++) {
				controls[j].TitleControl.Visible = false;
			}
		}
		var titleControl = isNeedSetFree(rs.grouper, viewPaper);
		for (var i in rs.columnTitles) {
			var ColumnTitleCell1 = rs.columnTitles[i];
			ColumnTitleCell1.Controls.RemoveAll();
			if (titleControl[i] != null && titleControl[i].length > 0) {
				var freeControl = [];
				for (var j = 0; j < titleControl[i].length; j++) {
					var _title_control = controlFactory(ColumnTitleCell1,
							"MemoBox");
					// //设置控件的宽度

					_title_control.Width = titleControl[i][j].width;
					_title_control.Text = titleControl[i][j].title;
					_title_control.Name = i + j;
					_title_control.TextAlign = textAlign.center;
					_title_control.Height = ColumnTitleCell1.Height;
					if (titleControl[i][j - 1]) {
						_title_control.Left = titleControl[i][j - 1].width;
					}

					var pj = {};
					pj.ColumnTitleCell1 = ColumnTitleCell1;
					pj.TitleControl = _title_control;
					freeControl.push(pj);
				}
				rs.freeControls[i] = freeControl;
			}
		}
		//report.SaveToFile("d:\\Program2.grf");
		return rs;
	}

	/**
	 * 判断是否需要添加网格头为自由格
	 */
	function isNeedSetFree(groups, viewPaper) {
		var titleControl = {};
		var length = 0;
		var preLen = 0;

		if (groups != null && groups.length > 0) {

			for (var i = 0; i < groups.length; i++) {
				titleControl[groups[i].name] = [];
				preLen += length;
				length = 0;
				if (groups[i].headers != null && groups[i].headers.length > 0) {
					for (var h = 0; h < groups[i].headers.length; h++) {

						length += convertToCm(groups[i].headers[h].width - 17);
						if ((length + preLen) > viewPaper) {
							length = length
									- convertToCm(groups[i].headers[h].width
											- 17);
							h--;
							if (length > 0) {
								var obj = {
									width : length,
									title : groups[i].titleText
								};
								titleControl[groups[i].name].push(obj);
							}
							length = 0;
							preLen = 0;
						}
					}
				}

				// /第二页标题行网格列的设置
				if (length > 0) {
					var obj = {
						width : length,
						title : groups[i].titleText
					};
					titleControl[groups[i].name].push(obj);
				}

			}
		}

		return titleControl;
	}

	/**
	 * 生成统计控件
	 * 
	 * @Param 数据集合
	 * @reportFooter 容器
	 * @sumTitle 统计标题
	 * @args 统计字段
	 */
	function createGroupCountControl(data, reportFooter, sumTitle, args) {

		var defaultTitle = $.extend({
					name : '合计'
				}, sumTitle);
		var footSumary = {};
		var pos = 0;
		for (var i = 0; i < data.length; i++) {
			footSumary[i] = controlFactory(reportFooter.Footer, i == 0
							? "MemoBox"
							: data[i].controlType);
			// //设置大小，以及位置
			footSumary[i].Height = 0.58;
			footSumary[i].Left = pos;
			footSumary[i].Width = data[i].realWidth;
			pos += data[i].realWidth;
			footSumary[i].Font.Name = "微软雅黑";// 设置内容格字体
			footSumary[i].Font.Point = 11;// 设置内容格字体大小
			// /设置首列标题
			if (i == 0) {
				footSumary[i].Text = defaultTitle.name;
				footSumary[i].TextAlign = textAlign.center;
			} else {
				// 如果该列需要统计,绑定统计字段
				if (data[i].summaryType) {
					footSumary[i].DataField = data[i].id;
					footSumary[i].Format = '#,##0.00';
					footSumary[i].TextAlign = data[i].align;
				}
			}

		}
	}

	/**
	 * 设置网格报表数据
	 */
	function setGridReportData(report, dataobj, column) {
		report.PrepareRecordset();
		for (var i = 0; i < dataobj.length; i++) {
			report.DetailGrid.Recordset.Append();
			for (var j in column) {
				// 设置对齐方式
				if(dataobj[i][column[j]])
					if(report.FieldByName(column[j]).FieldType==3)
					    report.FieldByName(column[j]).AsFloat = delcommafy(dataobj[i][column[j]]);
					else
						report.FieldByName(column[j]).AsString = dataobj[i][column[j]];	

			}
			report.DetailGrid.Recordset.Post();
		}

	}
	/**  
	 * 去除千分位  
	 *@param{Object}num  
	 */  
	delcommafy = function(num){  
		if(typeof num == "number")
			return num ;
		else{
		    num = num.replace(/[ ]/g, "");//去除空格  
		    num=num.replace(/,/gi,'');  
		    return num; 
		} 
	}  
	/**
	 * 获取colmodel中需要进行格式化的
	 */
	function getFormatterColumn(clmodel) {
		var len = clmodel.length ;
		var fn = [];
		if(clmodel.center&&clmodel.center.length>0){
			len = clmodel.center.length ;
			for (var i = 0; i < clmodel.center[len-1].length; i++) {
				if (clmodel.center[len-1][i].formatter
						&& typeof(clmodel.center[len-1][i].formatter) == "function") {
					var obj = new Object();
					obj.field = clmodel.center[len-1][i].field;
					obj.formatter = clmodel.center[len-1][i].formatter;
					fn.push(obj);
				}
			}				
		}else{
			for (var i = 0; i < clmodel.length; i++) {
				if (clmodel[i].formatter
						&& typeof(clmodel[i].formatter) == "function") {
					var obj = new Object();
					obj.field = clmodel[i].name;
					obj.formatter = clmodel[i].formatter;
					fn.push(obj);
				}
			}			
		}
			
		

		return fn;
	}
	// 获取网格数据，并根据绑定格式化事件 格式化
	function getGridData(gridId) {
		var dataObj = [];
		if($A("#" + gridId).data("grid")){
			var model = $A("#" + gridId).grid("getColumns");
			var fn = getFormatterColumn(model);
			var layer = model.center.length ;
			dataObj = $A("#" + gridId).grid('getAllData');
			
			// 添加当前页合计行数据
			var currPagefooter = $A("#" + gridId).grid("getSummaryRow");
			if(currPagefooter){
				dataObj.push(currPagefooter) ;
			}			
			// 添加总合计行数据
			var footer = $A("#" + gridId).grid('getFooterRows');
			if(footer&&footer.length>0){
				dataObj.push(footer) ;
			}
			// /对所有数据进行格式化
			for (var i in dataObj) {
				var od = dataObj[i];
				if (fn.length > 0) {
					for (var j = 0; j < fn.length; j++) {
						var fun = fn[j].formatter;
						od[fn[j].field] = fun.call(this, od[fn[j].field], null, od,"print");
						od.rowdata = od;
					}
				}
			}			

			
		}else{
			// /载入数据
			var IDS = $A("#" + gridId).bsgrid("getDataIDs");
			var model = $A("#" + gridId).bsgrid("getGridParam", "colModel");
			var fn = getFormatterColumn(model);
			
			for (var i = 0; i < IDS.length; i++) {
				var od = $A("#" + gridId).bsgrid("getRowData", IDS[i]);
				// 判断这个对象中那一列的数据需要进行格式化的
				// alert(od.fcode);
				dataObj.push(od);
			}
			

			// /添加后台返回的总合计行数据
			var footer = $A("#" + gridId).bsgrid("getGridParam", "footerrow");
			if(footer&&footer.length>0){
				dataObj.push(footer) ;
			}
			// /对所有数据进行格式化
			for (var i in dataObj) {
				var od = dataObj[i];
				if (fn.length > 0) {
					for (var j = 0; j < fn.length; j++) {
						var fun = fn[j].formatter;
						od[fn[j].field] = fun.call(this, od[fn[j].field], null, od,"print");
						od.rowdata = od;
					}
				}
			}
		}
		return dataObj;
	}

	function getGridWidth(gridId) {
		var gridObj = $A("#" + gridId);
		var gridWidth = 21 ;
		if(gridObj.data("grid")){
			var parent = gridObj.parent() ;
			gridWidth = parent.width() ;
		}else
			gridWidth = gridObj.bsgrid("getGridParam", "width");
		return gridWidth;
	}

	/**
	 * 处理新网格数据集
	 */
	function mergeDataOfNewGrid(columnStyle){
		var styleObj = [];
		styleObj.countField = [];// 记录统计字段
		// /判断当前column 是否存在操作列，获取操作列的宽度
		var ope_width = 0;// 操作列宽度
		for (var j = 0; j < columnStyle.length; j++) {	
			if(!columnStyle[j])
				continue ;
			//操作列，序号列
			if (columnStyle[j].sysCol == true) {
				ope_width += columnStyle[j].width;
			}
		}
		
		if (columnStyle != null && columnStyle.length > 0) {
			for (var i = 0; i < columnStyle.length; i++) {
				if(!columnStyle[i])
					continue ;				
				if (!columnStyle[i].hidden
						&& !columnStyle[i].sysCol) {
					var obj = {};
					// 宽度单位转换
					if (columnStyle[i].width > 17) {
						obj.width = columnStyle[i].width - 17;// columnStyle[i].width/(gridWidth-ope_width);//获取比例
					} else {
						obj.width = columnStyle[i].width;
					}
					// 按比例计算
					obj.summaryType = columnStyle[i].summaryType;
					obj.controlType = columnStyle[i].summaryType
							? "SummaryBox"
							: "MemoBox";
					obj.name = columnStyle[i].title;
					if(columnStyle[i].id)
						obj.id = columnStyle[i].id;						
					else
						obj.id = columnStyle[i].field;
					obj.align = textAlign[columnStyle[i].align];
					if(columnStyle[i].sysCol)
						obj.sysCol = true;
					else
						obj.sysCol = false ;
					styleObj.push(obj);
				}
			}
		}
		return styleObj;
	}
	/**
	 * 过滤掉当前隐藏的字段，并且将两则的属性进行合并
	 */
	mergeDataOfOldGrid = function(columnStyle, colNames, gridWidth) {
		var styleObj = [];
		styleObj.countField = [];// 记录统计字段
		// /判断当前column 是否存在操作列，获取操作列的宽度
		var ope_width = 0;// 操作列宽度
		for (var j = 0; j < columnStyle.length; j++) {
			if (columnStyle[j].name == "operation") {
				ope_width += columnStyle[j].width;
			}
		}
		if (columnStyle != null && columnStyle.length > 0) {
			for (var i = 0; i < columnStyle.length; i++) {
				if (!columnStyle[i].hidden
						&& $.trim(columnStyle[i].index).length > 0) {
					var obj = {};
					// 宽度单位转换
					if (columnStyle[i].width > 17) {
						obj.width = columnStyle[i].width - 17;// columnStyle[i].width/(gridWidth-ope_width);//获取比例
					} else {
						obj.width = columnStyle[i].width;
					}
					// 按比例计算
					obj.summaryType = columnStyle[i].summaryType;
					obj.controlType = columnStyle[i].summaryType
							? "SummaryBox"
							: "MemoBox";
					obj.name = colNames[i];
					obj.id = columnStyle[i].index;
					obj.align = textAlign[columnStyle[i].align];
					styleObj.push(obj);
				}
			}
		}
		return styleObj;
	}
	/**
	 * 获取网格对象信息，包括 列名-字段名 值
	 * Modify by C.j.b  2015-09-16 修改兼容新网格
	 */
	function getGridObjDetail(gridId) {
		var gridObj = $A("#" + gridId);
		// 获取网格每个列的样式，包括长度，是否显示，field
		var columnStyle = undefined; // 网格列样式
		var colNames = undefined; // 获取网格显示列明
		var gridWidth = undefined; // 网格列宽度
		var data = undefined ;
		//如果是旧网格。就用旧网格的取数方法
		if (!gridObj.data('grid')) {
			columnStyle = gridObj.bsgrid("getGridParam", "colModel");
			colNames = gridObj.bsgrid("getGridParam", "colNames");
			gridWidth = gridObj.bsgrid("getGridParam", "width");
			data = mergeDataOfOldGrid(columnStyle, colNames, gridWidth);
		} else {
			var gridColumns = gridObj.grid("getColumns") ;//TODO
			var layer = 0 ;
			if(gridColumns){
				layer = gridColumns.center.length ;
				var mergeData = [] ;
				//如果网格头只有一层
				if(layer==1){
					columnStyle = gridColumns.center[layer-1];
				}else{ //多层网格头
					columnStyle = gridColumns.center[0]
				}
				var columnLeft = gridColumns.left[layer-1] ;
				var columnRight = gridColumns.right[layer-1] ;					
				if(columnLeft){
					for(var i=0;i<columnLeft.length;i++){
						mergeData.push(columnLeft[i]) ;
					}					
				}
				if(columnStyle){
					for(var i=0;i<columnStyle.length;i++){
						if(columnStyle[i].children&&columnStyle[i].children.length>0){
							for(var n=0;n<columnStyle[i].children.length;n++){
								mergeData.push(columnStyle[i].children[n]) ;
							}							
						}else
							mergeData.push(columnStyle[i]) ;
					}		
				}
				if(columnRight){
					for(var i=0;i<columnRight.length;i++){
						mergeData.push(columnRight[i]) ;
					}		
				}					
				data = mergeDataOfNewGrid(mergeData) ;
				
			}
		}

		/**
		 * 获取合并后的数据
		 */
		
		return data;
	}
	

	/**
	 * 获取这端文字在网页上所能显示出来的所需要的宽度
	 */
	function getVirualWidth(word, type) {
		var span = $("<span id='virtualWidth' style='display:none,font-family:微软雅黑'></span>");

		if ("value" == type) {
			span.css("fontSize", "10.5px");
		}
		span.html($.trim(word));
		$("body").append(span);
		var width = span.width();
		width = convertToCm(width);
		span.remove();
		return width;
	}
	/**
	 * px 转成cm
	 */
	function convertToCm(width) {
		var bili = 1 / 28.346;
		return width * bili;
	}
	/**
	 * 报表字颜色值
	 */
	function ColorFromRGB(red, green, blue) {
		return red + green * 256 + blue * 256 * 256;
	}

	return {
		QueryTemplateList : function(moduleId, callback) {
			return queryTemplateList(moduleId, callback);
		},
		/*QueryDefaultTemplateId : function(moduleId, type) {
			return getDefualtTemplateId(moduleId, type);
		},*/
		PrintUrl : function(templateId, dataUrl, isPreview, conf) {
			PrintUrl(templateId, dataUrl, isPreview, conf);
		},
		PrintMultipleReport : function(templateId, urlParam, isPreview, conf) {
			PrintMultipleReport(templateId, urlParam, isPreview, conf);
		},
		//处理清单
		PrintMultipleReport1 : function(templateId, urlParam, isPreview, conf) {
			PrintMultipleReport1(templateId, urlParam, isPreview, conf);
		},
		//回收单
		PrintMultipleReport2 : function(templateId, urlParam, isPreview, conf) {
			PrintMultipleReport2(templateId, urlParam, isPreview, conf);
		},
		//年报单户表
		PrintMultipleReport3 : function(templateId, urlParam,urlParam4Single,params, isPreview, conf) {
			PrintMultipleReport3(templateId, urlParam,urlParam4Single,params, isPreview, conf);
		},
		//固定资产报废处置表
		PrintMultipleReport4 : function(templateId, urlParam, isPreview, conf){
			PrintMultipleReport4(templateId, urlParam, isPreview, conf);
		},
		//固定资产处置表
		PrintMultipleReport5 : function(templateId, urlParam, isPreview, conf){
			PrintMultipleReport5(templateId, urlParam, isPreview, conf);
		},
		//年报汇总表
		PrintMultipleReport6 : function(templateId, urlParam,urlParam4Single,params, isPreview, conf){
			PrintMultipleReport6(templateId, urlParam,urlParam4Single,params, isPreview, conf);
		},
		//固定资产报损处置表
		PrintMultipleReport7 : function(templateId, mxbUrl, hzbUrl, isPreview, conf){
			PrintMultipleReport7(templateId, mxbUrl, hzbUrl, isPreview, conf);
		},
		PrintMultiReport : function(LoadReportURL,dataUrl, isPreview, conf){
			PrintMultiReport(LoadReportURL,dataUrl, isPreview, conf);
		}
	}
});
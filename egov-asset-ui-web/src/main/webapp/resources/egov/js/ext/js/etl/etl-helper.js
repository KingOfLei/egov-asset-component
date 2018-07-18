define(["app/core/app-jquery",
        "app/util/app-utils",
        "resources/egov/js/ext/js/etl/etl-export-task",
        "app/widgets/form/app-upload",
        "jquery/jquery.upload"
        ],function(jq, Utils, EtlExportTask){
	
	var etlUrl = "egov/asset/etl/expExcel.do";
	var etlDownloadUrl = "egov/asset/etl/download.do";
	var etlRunTask = "egov/asset/etl/runTask.do";
	var etlImpFileUrl = "egov/asset/etl/impFile.do";
	var etlThreadUrl = "egov/asset/etl/threadExpExcel.do";
	
	function getHeader(gridData,detailHeaders,tmpSubheader){
		var newHeader = [];
		for(var i = 0; i < detailHeaders.length; i++){
			var detailHeader = detailHeaders[i];
			newHeader[i] = getHeaderData(gridData,detailHeader,tmpSubheader);			
		}
		return newHeader;
	}
	
	function getHeaderData(gridData,detailHeader,tmpSubheader){
		var newHeader = {};
		var fieldName = detailHeader.Name;
		var subHeader = detailHeader.SubHeaders;
		var fieldMeta = gridData._fieldColumns[fieldName];			
		if(fieldMeta == undefined && subHeader){
			//说明包含子集
			newHeader.subHeaders = getHeader(gridData,subHeader,tmpSubheader);
			newHeader.name= fieldName;
			newHeader.title = detailHeader.Title;
			newHeader.align = detailHeader.Align;
		} else {
			newHeader.name = fieldName;
			newHeader.title = detailHeader.Title;
			newHeader.align = detailHeader.Align;
			newHeader.width =  (!detailHeader.Width) ? fieldMeta.width : detailHeader.Width;
			tmpSubheader.push(fieldName);
		}
		
		return newHeader;
	}
	
	return {
		/**
		 * 启用线程导出
		 */
		"threadExpExcel": function(gridId, opts){
			var defaultOptions = {
				data: {
					configCode: ''
				},
				fileName: 'excel数据导出',
				sheetName: 'sheet1',
				waitMsg:'数据正在导出......',
				tipMsg:'导出',
				download:false,//实现需要文件下载
				exportType:'xlsx'//文件后缀					
			};
			$.extend(defaultOptions, opts);
			
			var rows = [];
			var $grid = $('#' + gridId);
			var _gridData = $grid.data('grid');
			if($grid.length == 0 || !_gridData) {
				$a.assetMsg.warn("非网格对象暂不支持数据导出!");
				return;
			}
			if(defaultOptions.checkRow){
				rows = $grid.grid('getSelections');
			} else {
				rows = $grid.grid('getAllData');
			}
			var printData = $grid.grid('getPrintModel',{},rows);
			//二次处理数据 不必要的列就不上传了
			//表头信息
			var detailHeaders = printData.ReportDetail.DetailHeaders;
			//网格数据
			var detailDatas = printData.ReportDetail.DetailDatas;
			var newDatas = [];
		    var newHeader = [];
		    var tmpSubheader = {};//存放存在二维及以上列时对应的子列字段值
		    if(!detailDatas || !detailDatas.length){
		    	$A.messager.warn('当前网格没有数据！');
		    	return false;
		    }
		    //循环记录
		    for(var j = 0; j < detailDatas.length; j++){
		    	newDatas[j] = {};
				var rowData = detailDatas[j];
				for (var i = 0; i < detailHeaders.length; i++) {
					// 获取字段属性
					var fieldName = detailHeaders[i].Name;
                    if(j == 0){
    					tmpSubheader[fieldName]=[];                    	
						newHeader[i] = getHeaderData(_gridData,detailHeaders[i],tmpSubheader[fieldName]);
	                }
                    for(var n=0; n < tmpSubheader[fieldName].length; n++){
    					var _readFieldName = tmpSubheader[fieldName][n];
                    	newDatas[j][_readFieldName] = rowData[_readFieldName];// 赋值					
                    }
				}
		    }
			
			var data = {
				detailDatas:JSON.stringify(newDatas),
				detailHeaders:JSON.stringify(newHeader),
				configCode: defaultOptions.data.configCode,
				exportType:defaultOptions.exportType,
				fileName:defaultOptions.fileName,
				sheetName:defaultOptions.sheetName
			};
			
			var condition = '';
			// 添加固定参数
			var fixedParameter = _gridData.getFixedParameter();
			if(fixedParameter && typeof fixedParameter === 'object'){
				for(var key in fixedParameter){
					condition += " AND " + $.getField(key) + " = '" + fixedParameter[key] + "'";
				}
			}
			
			// 添加查询参数
			var parameter = _gridData.getParameter();
			if(parameter && $.type(parameter) === 'object' 
				&& parameter.__xquery && $.type(parameter.__xquery) === 'string'){
				var params = JSON.parse(parameter.__xquery);
				for(var i = 0; i < params.length; i++){
					condition += ' AND ';
					condition += params[i].prop + " " + params[i].op;
					if($.trim(params[i].op).toLowerCase() === 'like'){
						condition += " '%" + params[i].val + "%'"
					} else{
						condition += " '" + params[i].val + "'"
					}
				}
			}
			$.extend(data, {condition: condition});
			
			// 添加排序
			var sorts = _gridData.getCurrentSort();
			if(sorts && sorts.length){
				var order = " order by ";
				for(var i = 0; i < sorts.length; i++){
					order += $.getField(sorts[i].field) + " " + sorts[i].order + ",";
				}
				$.extend(data, {order: order.substr(0, order.length-1)});
			}
			
			$A.showWaitScreen(defaultOptions.waitMsg);
			$A.ajax.ajaxCall({
				url: etlThreadUrl,
				data: data,
				type: 'POST',
				complete: function(){
					$A.hideWaitScreen();
				},
				callback: function(data){
					if(data.exception){
						$A.messager.warn(data.message);
					} else{
						// 任务成功执行，开启任务窗口
						EtlExportTask.show(true);
					}
				},
				error: function(e){
					$A.messager.warn(defaultOptions.tipMsg + '异常!');
				}
			});
		},
		/**
		 * 网格数据导出excel
		 * gridId 网格id
		 * checkRow 是否导出选中行 否则 当前页所有数据
		 */
		"exportExcel":function(gridId, opts){
            var defaultOptions = {
            	checkRow:false,
                exportType:"xlsx",
                fileName:"excel数据导出",
                sheetName:'列表数据',
            };
			$.extend(true,defaultOptions,opts);
			var rows = [];
			var $grid = $('#' + gridId);
			var _gridData = $grid.data('grid');
			if($grid.length == 0 || !_gridData) {
				$a.assetMsg.warn("非网格对象暂不支持数据导出!");
				return;
			}
			if(defaultOptions.checkRow){
				rows = $grid.grid('getSelections');
			} else {
				rows = $grid.grid('getAllData');
			}
			var printData = $grid.grid('getPrintModel',{},rows);
			//二次处理数据 不必要的列就不上传了
			//表头信息
			var detailHeaders = printData.ReportDetail.DetailHeaders;
			//网格数据
			var detailDatas = printData.ReportDetail.DetailDatas;
			var newDatas = [];
		    var newHeader = [];
		    var tmpSubheader = {};//存放存在二维及以上列时对应的子列字段值
		    //循环记录
		    for(var j = 0; j < detailDatas.length; j++){
		    	newDatas[j] = {};
				var rowData = detailDatas[j];
				for (var i = 0; i < detailHeaders.length; i++) {
					// 获取字段属性
					var fieldName = detailHeaders[i].Name;
					//var subHeader = detailHeaders[i].SubHeaders;
                    if(j == 0){
    					tmpSubheader[fieldName]=[];                    	
						newHeader[i] = getHeaderData(_gridData,detailHeaders[i],tmpSubheader[fieldName]);
//						newHeader[i] = {};
//						var fieldMeta = _gridData._fieldColumns[fieldName];	
//						if(fieldMeta == undefined && subHeader){
//							//说明包含子集
//							
//						} else {
//							newHeader[i].name = fieldName;
//							newHeader[i].title = detailHeaders[i].Title;
//							newHeader[i].align = detailHeaders[i].Align;
//							newHeader[i].width =  (!detailHeaders[i].Width) ? fieldMeta.width : detailHeaders[i].Width;
//						}
	                }
                    for(var n=0; n < tmpSubheader[fieldName].length; n++){
    					var _readFieldName = tmpSubheader[fieldName][n];
                    	newDatas[j][_readFieldName] = rowData[_readFieldName];// 赋值					
                    }
					//newDatas[j][fieldName] = rowData[fieldName];// 赋值					
				}
		    }
			
			var data = {
					detailDatas:JSON.stringify(newDatas),
					detailHeaders:JSON.stringify(newHeader),
					exportType:defaultOptions.exportType,
					fileName:defaultOptions.fileName,
					sheetName:defaultOptions.sheetName
			};
			$A.showWaitScreen("数据正在导出......");
			$A.ajax.ajaxCall({
				 url:etlUrl,
                 data:data,
                 type:'POST',
                 callback:function(data,extData){
                	 $a.hideWaitScreen();                	 
                	 if(data.exception){
                		 $a.assetMsg.warn(data.message);
                		 return;
                	 } else {
                		 $.uiExtend({type:'download',url:etlDownloadUrl,options:{
              				httpMethod:'POST',
                              data:{id:data.data},
              				onSuccess:function(){
              					$a.hideWaitScreen();
              				},
              				onFail:function(){
              					$a.assetMsg.warn("数据导出异常!");
              			    }
              			}});
                	 }
                 },
                 error:function(e){
                 	$a.hideWaitScreen();
                 }
			});
		},
		"runTask":function(opts){
			var defaultOptions = {
					data:{
						configCode:''
					},
					waitMsg:'正在处理......',
					tipMsg:'处理',
					download:false,//实现需要文件下载
					exportType:''//文件后缀					
			};
			$.extend(true,defaultOptions,opts);
			$A.showWaitScreen(defaultOptions.waitMsg);
			$A.ajax.ajaxCall({
				 url:etlRunTask,
                 data:defaultOptions.data,
                 type:'POST',
                 callback:function(data,extData){
                	 $a.hideWaitScreen(); 
                	 if(data.exception){
                		 $a.assetMsg.warn(data.message);
                		 return;
                	 }
                	 if(defaultOptions.download){
                		 $.uiExtend({type:'download',url:etlDownloadUrl,options:{
               				httpMethod:'POST',
                            data:{
                            	   id:data.data,
                            	   exportType:defaultOptions.exportType
                            },
               				onSuccess:function(){
               					
               				},
               				onFail:function(){
               					$a.assetMsg.warn(defaultOptions.tipMsg + "异常!");
               			    }
               			}}); 
                	 }
                 },
                 error:function(e){
                  	$a.hideWaitScreen();
                  	$a.assetMsg.warn(defaultOptions.tipMsg + "异常!");
                  }
			});
		},
		//导入方法
		"impFileTask":function(opts){
			var defaultOptions = {
					data:{
						configCode:'',
						fileIds:[]
					},
					waitMsg:'正在导入......',
					tipMsg:'导入',
					successCallback:function(){
						
					},
					errorCallback:function(){
						
					}
			}
			$.extend(true,defaultOptions,opts);
			$A.showWaitScreen(defaultOptions.waitMsg);			
			$A.ajax.ajaxCall({
                 url: etlImpFileUrl,
                 data: defaultOptions.data,
                 type:'POST',
                 callback: function (data,extData) {
                 	 $a.hideWaitScreen(); 
                	 if(data.exception){
                		 $a.assetMsg.warn(data.message);
                		 defaultOptions.errorCallback(data);
                		 return;
                	 }
                	 $a.assetMsg.warn(defaultOptions.tipMsg + "成功!");
                	 defaultOptions.successCallback(data);
                 },
                 error: function (obj) { 
                    $A.hideWaitScreen();   
                   	$a.assetMsg.warn(defaultOptions.tipMsg + "异常!");
                   	defaultOptions.errorCallback(data);
                 }
             });
		},
		//导入窗口
		"impFile":function(opts){
			var defaultOptions = {
					data:{},
					beforeClose:function(){
						
					},
					onPageLoad:function(){
						
					}
			}
			$.extend(true,defaultOptions,opts);
			var options = {
					url : "egov/asset/etl/showImpFile.do",
					dialogId : "showImpFile",
					title : "导入",
					width : 500,
					height : 300,
					wrapper : true,
					hasheader : false,
					params:defaultOptions.data,
					reload : true,
					beforeClose:function(obj,dig,event){
						defaultOptions.beforeClose();
					},
					onPageLoad : function(obj) {
						defaultOptions.onPageLoad();
					}
				};
			$.openModalDialog(options);
		}
		
	}
});
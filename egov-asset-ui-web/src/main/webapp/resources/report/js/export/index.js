define(['resources/report/js/export/file-task-manager', 'app/data/app-ajax', 
        'app/widgets/grid/app-grid', 
        'app/widgets/window/app-dialog'],function(task, AppAjax){
	var func = {},
		$exportGrid = null;
	
	func.initUi = function(){
		func.initGrid();
		func.initQuery();
		$exportGrid = $A('#exportTaskGrid').data('grid');
	}
	
	func.initQuery = function(){
		$A('#query .queryquick').on('click', function(e){
			var queryParam = {};
			queryParam.taskName = $A('#query #taskName').val();
			queryParam.createDate = $A('#query #createDate').val();
			$exportGrid.setParameter(queryParam);
			$exportGrid.load();
		});
		$A('#query .open-task').on('click', function(e){
			task.show();
		});
	}
	
	func.initGrid = function(){
		$A('#exportTaskGrid').grid({
			title: '导出数据任务列表',
			rownumbers: 'normal',
			headerCustom: '#query',
			idField: 'id',
			fitColumns: 'ES',
			url: 'report/export/task/getExportTasks.do',
			pager: $a.options.appDefaults.Grid.pager,
			toolbarHeight: 35,
			toolbar: [{
				text: '任务栏',
				iconCls: 'btn-detail',
				handler: function(){
					task.show();
				}
			},{
				text: '查询条件',
				iconCls: 'btn-search',
				handler: function(){
					$exportGrid.toggleCustom();
				}
			}],
			columns: [[
			     {title: '任务Id', field: 'id', width: 80, hidden: true},
			     {title: '任务名称', field: 'taskName', width: 50, align: 'left'},
			     {title: '文件名称', field: 'fileName', width: 100, align: 'left'},
			     {title: '任务状态', field: 'status', width: 40, align: 'center', 
			    	 formatter: function(val){
			    		 switch(val.toUpperCase()){
			    		 case 'SUCCESS':
			    			 return '导出成功';
			    		 case 'ERROR':
			    			 return '导出失败';
			    		 case 'EXECUTING':
			    			 return '正在导出';
			    		 case 'WAIT':
			    			 return '等待导出';
		    			 default:
		    				 return '等待导出';
			    		 }
			    	 }
			     },
			     {title: '备注', field: 'commons', width: 200, align: 'left',
			    	 formatter: function(val){
			    		 if(!val){
			    			 return '无';
			    		 }
			    		 return '<span title="' + val + '">' + val + '</span>';
			    	 }
			     },
			     {title: '下载次数', field: 'downloadCount', width: 20, align: 'right'},
			     {title: '任务创建时间', field: 'createTime', width: 60, align: 'center'}
            ]],
            frozenColumnsRight: [[
             {
            	 title: '操作',
 				 field: 'opts',
 				 buttons: [
 				     {
 				    	text: '下载',
			            iconCls: 'btn-download',
			            handler: function(rowData){
			            	if(rowData.status != 'SUCCESS'){
			            		$a.messager.warn('文件状态非[导出成功]，无法进行下载');
			            		return;
			            	}
			            	var url = 'report/export/task/download.do?taskId=' + rowData.id;
		        			$A.showWaitScreen('正在下载文件...');
		        			$.fileDownload(url, {
		        				successCallback: function (url) {
		        					$A.hideWaitScreen();
		        				},
		        			   	failCallback: function (html, url) {
		        			   		$A.messager.warn($('<div>'+ html + '</div>').find('.error h1').text());
		        					$A.hideWaitScreen();
		        			   	} 
		        			}); 
			            }
 				     },
 				     {
  				    	text: '删除',
 			            iconCls: 'btn-delete',
 			            handler: function(rowData){
 			            	$messager.confirm('确定删除<b>' + rowData.taskName + '</b>？', {
 			            		okCall: function(){
 			            			AppAjax.ajaxCall({
 			            				url: 'report/export/task/deleteTask.do',
 			            				data: {'taskId': rowData.id},
 			            				dataType: 'json',
 			            				success: function(result){
 			            					if(result == 'SUCCESS'){
 			            						$messager.correct('删除成功!');
 	 			            					$A('#exportTaskGrid').grid('reload');
 			            					} else{
 			            						$messager.warn(result);
 			            					}
 			            				}
 			            			});
 			            		}
 			            	});
 			            }
  				     }
	             ]
             }                 
            ]]
		});
	}
	
	return func;
});
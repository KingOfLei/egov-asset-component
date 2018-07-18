/**
 *  扩展 下拉框多级联动
 *  author: zxy
 *  date: 2017-05-31
 *  $.uiExtend({
		type:'multistage-linkage',								// (*必传)扩展插件类型
		$parent:$A('#aimsCard6Page'),							// (*必传，JQ对象)承载的父级JQ对象
		options:{
			wrapstyle: 'multistage-linkage',					// 外层样式名, 参考 multistage-linkage.scss
			showDetail: {										// 是否在联动组件最后显示输入框,不需要输入框时设为false
			 	detailField:'detail-adderss',					// 输入框取值字段名
			 	value:''										// 输入框赋值
			},									
			okBtn: false,										// 是否显示确定按钮
			onClickOk: function(datas){},						// 点击确定按钮的回调方法, datas为多级联动组件返回的取值
			clearBtn: false,									// 是否显示清空按钮
			childrenOpts:{										// 联动参数对象
				field:'province',								// (*必传)取值字段名
				initParam: 'itemId=201603000000',				// 下拉选项时赋值参数
				_options:{										// (*必传)下拉框初始化参数
					action:'egov/asset/aims/dicthelper/getAreaList.do',
					textfield:'itemName',
					valuefield:'itemId',
					keyShowPanel:true,
					tips:'请选择省份'
				},
				childrenOpts:{									// 下级联动参数对象
					field:'city',
					initParam: 'itemId=201603150000',
					_options:{
						action:'egov/asset/aims/dicthelper/getAreaList.do',
						textfield:'itemName',
						valuefield:'itemId',
						tips:'请选择市'
					},
					childrenOpts:{
						field:'country',
						initParam: 'itemId=201603150700',
						_options:{
							action:'egov/asset/aims/dicthelper/getAreaList.do',
							textfield:'itemName',
							valuefield:'itemId',
							tips:'请选择区/县'
						}
					}
				}
			}
		}
	});
 */
 define(["resources/egov/js/ext/js/base/ext-base",
	 "resources/egov/js/ext/js/util/common-util",
	 "resources/baidumapv2/baidumap_offline_v2_20160921_min.js",
	 "resources/baidumapv2/jquery.baidumap.js"
	 ],function(ExtBase,CUtil) {
	 var MultistageLinkage = ExtBase.extend({
         // 初始化
		 init: function () {
			 var _this = this;
			 _this.comboFields = [];
			 var defaultOpts = {
				 wrapstyle: 'multistage-linkage',					// 外层样式名
				 showDetail: false,
				 okBtn: false,
				 clearBtn: false,
				 // 显示的地图参数
				 showMap: {
					 lngField:'lng',
					 latField:'lat',
					 initMarker:{
                         even:[{
                             name: "mouseup",
                             fn: function(e, $this){
                             	_this.updateLatLng(e, {lngField:'lng',latField:'lat'});
                             }
                         }]
					 }
				 }
			 };
			 
			 _this._opts = $.extend(true, {}, defaultOpts, _this.options);
			 
			 _this.$parent.append('<div class="' + _this._opts.wrapstyle + '"><div class="mult-linkage-widget"></div><div class="mult-linkage-btns"></div></div>')
			 _this.createWidget(_this, _this._opts);
         },
         
         // 创建插件DOM元素
         createWidget: function(_this, opts){
        	 var comboParam = opts.childrenOpts;
        	 _this.$widget = _this.$parent.find('.mult-linkage-widget');
        	 var $btns = _this.$parent.find('.mult-linkage-btns');
        	 // 存储对应组件的field、textField及valueField
        	 _this.comboFields.push(comboParam.field);
        	 
        	 _this.$widget.append('<div class="mult-linkage-item"><input _field="' + comboParam.field + '" /></div>');
        	 $combo = $A('[_field=' + comboParam.field +']');
//			 var param = _this.getParams(_this, comboParam);
//			 comboParam._options.action += param;
			 
			 // // 若存在下拉框自定义onChange事件
			 if(comboParam._options.onChange){
				 var onChangeEven = comboParam._options.onChange;
				 comboParam._options.onChange = function(newVal,oldVal){
					 if(comboParam.childrenOpts){
    					 _this.changeValue(_this, comboParam.childrenOpts, newVal, oldVal);
    				 };
    				 
    				 // 执行自定义参数中的onChange回调方法
    				 onChangeEven( newVal, oldVal);
				 };
			 }else{
				 comboParam._options.onChange = function(newVal,oldVal){
					 if(comboParam.childrenOpts){
    					 _this.changeValue(_this, comboParam.childrenOpts, newVal, oldVal);
    				 };
				 };
			 };
			 
			 // 若不存在下拉框自定义afterSelected事件
			 if(!comboParam._options.afterSelected){
				 comboParam._options.afterSelected = function(node){
					 // 根据经纬度移动地图
					 $('#locationMap').baidumap('moveTo',{lng:node.str04, lat:node.str05});
				 };
			 };
			 
			 // 存储初始action参数
			 comboParam.defaultAction = comboParam._options.action;
			 if(comboParam.initParam){
				 comboParam._options.action += '?' + comboParam.initParam;
			 };

        	 // 下拉控件初始化
			 $combo.combobox(comboParam._options);
        	 
        	 // 若存在下级下拉框参数对象
        	 if(comboParam.childrenOpts){
            	 _this.createWidget(_this, comboParam);
        	 }else{
        		 // 创建详细信息输入框
        		 if(_this._opts.showDetail){
        			 var val = _this._opts.showDetail.value?_this._opts.showDetail.value:'';
        			 _this.$widget.append('<input class="mult-linkage-detail mult-linkage-input" _field="' + _this._opts.showDetail.detailField + '" value="' + val + '" />');
        		 };
        		 
        		 // 创建地图
        		 if(_this._opts.showMap){
        			 var longitude =  '', latitude =  '';

        			 if(_this._opts.showMap.initMarker){
        				 longitude = _this._opts.showMap.initMarker.lng;
        				 latitude = _this._opts.showMap.initMarker.lat;
        			 };
        			 
        			 _this.$widget.append('<div id="locationMap" class="mult-linkage-map">不要怕！我是地图。</div>\
        					 <label>经度：<span _field="' + _this._opts.showMap.lngField + '">' + longitude + '</span></label>\
					 		 <label>纬度：<span _field="' + _this._opts.showMap.latField + '">' + latitude + '</span></label>');
           		  	 // 地图初始化
            		 $('#locationMap').baidumap({
    		             lng: longitude,
    		             lat: latitude,
    		             level: 14,
    		             mapClickEven: function(e){
    		            	 $('#locationMap').baidumap('addCover', [{
        		            	 lng: e.point.lng,
                                 lat: e.point.lat,
                                 onlyOneMarker: true,
                                 enableDrag: true
                                 ,even:[{
                                     name: "mouseup",
                                     fn: function(e, $this){
                                    	 _this.updateLatLng(e, _this._opts.showMap);
                                     }
                                 }]
        		             }]);
    		            	 _this.updateLatLng(e, _this._opts.showMap);
    		             },
    		             onLoadSuccess: function(){
    		            	 if(_this._opts.showMap.initMarker){
        	            		 $('#locationMap').baidumap('addCover', [_this._opts.showMap.initMarker]);
    	        			 };
    		             }
    		         });
            		 
            		 
            		 
        		 };
        		 
        		 // 创建确定按钮
        		 if(_this._opts.okBtn){
        			 $btns.append('<a href="#" id="_mult-ok" class="btn btn-primary singlebtn">确定</a>')
                	 			  .on('click','#_mult-ok', function(){
                	 				  _this.getValues(_this, opts);
                	 			  });
        		 };
        		// 创建清空按钮
        		 if(_this._opts.clearBtn){
        			 $btns.append('<a href="#" id="_mult-clear" class="btn btn-primary singlebtn">清空</a>')
                	 			  .on('click','#_mult-clear', function(){
			    	 				  _this.clearValues(_this, opts);
			   	 			  	  });
        		 };
        		 
        	 };
         },
         
         // 更新经纬度
         updateLatLng: function(e, showMapOpts){
        	 var _this = this;
        	 _this.$widget.find('[_field=' + showMapOpts.lngField + ']').html(e.point.lng);
        	 _this.$widget.find('[_field=' + showMapOpts.latField + ']').html(e.point.lat);
         },
         
         // 拼接请求参数
//         getParams: function(_this, opts){
//        	 var param = '';
//			 for(var opt in opts.addParams){
//				 param += opt + '=' + opts.addParams[opt];
//			 };
//			 param = param ? '?=' + param : '';
//			 return param;
//         },
         
         // 非空校验
         nonNullCheck: function(_this){
        	 var flag = true, datas = {}, address = '', splitSymbol = (_this._opts.splitField && _this._opts.splitField.length) ? _this._opts.splitField : '';
        	 // 校验下拉选项
        	 for(var i = 0; i < _this.comboFields.length; i++){
        		 if($A('[_field=' + _this.comboFields[i] +']').combobox('getValue') === '' || $A('[_field=' + _this.comboFields[i] +']').combobox('getValue') == null){
        			 flag = false;
        			 break;
        		 }else{
            		 datas[_this.comboFields[i]] = $A('[_field=' + _this.comboFields[i] +']').combobox('getValue');
            		 address += splitSymbol + $A('[_field=' + _this.comboFields[i] +']').combobox('getText');
        		 };
        	 };
        	 
        	 // 校验详细地址
        	 if(flag && _this._opts.showDetail){
        		 var detailField = _this.$parent.find('[_field=' + _this._opts.showDetail.detailField + ']').val();
        		 if(detailField === ''){
        			 flag = false;
        		 }else{
            		 datas[_this._opts.showDetail.detailField] = _this.$parent.find('[_field=' + _this._opts.showDetail.detailField + ']').val();
            		 address += splitSymbol + datas[_this._opts.showDetail.detailField];
        		 };
        	 };
        	 
        	 // 校验经纬度
        	 if(flag && _this._opts.showMap){
//        		 var lng = _this.$parent.find('.mult-linkage-widget').find('[_field=' + _this._opts.showMap.lngField + ']').html();
//        		 if(lng === ''){
//        			 flag = false;
//        		 }else{
            		 datas['longitude'] = _this.$parent.find('.mult-linkage-widget').find('[_field=' + _this._opts.showMap.lngField + ']').html();
            		 datas['latitude'] = _this.$parent.find('.mult-linkage-widget').find('[_field=' + _this._opts.showMap.latField + ']').html();
            		 
//        		 };
        	 };
        	 
        	 if(flag) {
        		 flag = {
        			 datas: datas,
        			 address: address.slice(splitSymbol.length) 	// 用于截取分割符后的内容
        		 };
        	 }
//        	 
        	 return flag;
         },
         
         // 获取所有值
         getValues: function(_this, opts){
        	 // 非空校验
        	 var datas = _this.nonNullCheck(_this);
        	 if(!datas){
        		 $A.assetMsg.warn("请将信息填写完整!");
        		 return false;
    		 };
        	 
        	 // 自定义确定按钮事件
        	 if(_this._opts.onClickOk){
        	 	 _this._opts.onClickOk(datas.datas);
        	 };
        	 // 回显拼接地址信息
    		 $.closeReference({text:datas.address,value:datas.address});
         },
         
         // 清空所有值
         clearValues: function(_this, opts){
        	 // 清空下拉选项值
        	 for(var i = 0; i < _this.comboFields.length; i++){
        		 $A('[_field=' + _this.comboFields[i] +']').combobox('clearValue');
        		 if(i != 0){
        			 $A('[_field=' + _this.comboFields[i] +']').combobox('loadData',[]);
        		 };
        	 };
        	 // 清空详细地址栏
        	 if(_this._opts.showDetail){
        		 $A('[_field=' + _this._opts.showDetail.detailField + ']').val('');
        	 };
        	 // 清空地图坐标点
        	 if(_this._opts.showMap){
        		 $('#locationMap').baidumap('deleteCover');
        		 _this.$widget.find('[_field=' + _this._opts.showMap.lngField + ']').html('');
            	 _this.$widget.find('[_field=' + _this._opts.showMap.latField + ']').html('');
        	 };
        	 
         },
         
         // 上级选项值改变事件
         changeValue: function(_this, opts, newVal, oldVal){
			 $A('[_field=' + opts.field +']').combobox('clearValue');
			 $A('[_field=' + opts.field +']').combobox('reload',opts.defaultAction + '?'+ opts._options.valuefield + '=' + newVal)
         }
         
     });
	 
     return MultistageLinkage;
});
 
 
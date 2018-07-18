/**
 * 对app-tabs的扩展将横向tabs变为纵向
 */
define(["resources/egov/js/ext/js/base/ext-base",
    "app/core/app-core"],function(ExtBase,$A) {

	var XGridExtension = ExtBase.extend({
        id:'',
        initialConfig:{},
        remoteConfig:{},

        init:function(){
            var _self = this;

            _self.$grid = $A('#'+_self.id);
            if(_self.$grid.length <= 0){
                console.log(' no found grid by '+_self.id+'');
                return;
            }
            //设置初始化值,深度拷貝
            _self.initialConfig.columns = $.extend(true,{},_self.$grid.grid('getColumns'));

            var $removeConfig = $('#'+_self.id+'Config');
            if($removeConfig.length<=0){
                console.log(' no found grid config by '+_self.id+'');
                return;
            }

            //解析配置
            _self.remoteConfig = $.parseJSON($('#'+_self.id+'Config').attr('_options'));
            $('#'+_self.id+'Config').remove();
		},

        render:function(){
            var _self = this;
            var $pagerTool = _self.$grid.data('grid').$element.find('.pager-tool');
		},

        //设置列
        setColumn:function(columns){
            var _self = this;
            if(!columns){
                return;
            }
            $.each(columns,function(gridRegion,colNameIndex){
                var colArray =  columns[gridRegion][0];
                $.each(colArray,function(colName,i){
                    var col = colArray[colName];
                    _self.$grid.grid('setColumnWidth', col);
                })
            });
            return _self;
        },

        afterRender:function () {
            var _self = this;
            //应用远端配置
            _self.resetRemoteConfig();
        },

        //保存配置
        save:function(){
            var _self = this;
            var  url = 'xgrid/setConfig.do';

            var columns = _self.$grid.grid('getColumns');
            var config =  {columns:columns};
            var data =
                {
                    id:_self.id,
                    config:JSON.stringify(config)
                };
            $A.ajax.ajaxCall({
                url: url,
                data: data,
                contentType:'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                type: 'POST',
                success: function(data){
                    $A.messager.correct('保存成功');
                    _self.extConfig(_self.remoteConfig,config);
                },
                errorHandle: function(){
                    $A.messager.error('保存失败');
                }
            });
            return _self;
        },
        //应用配置
        applyConfig:function(config){
            var _self = this;

            _self.setColumn(config.columns);

            return _self;
        },
        //重置到出厂配置
        resetInitialConfig:function(){
            var _self = this;
            _self.applyConfig(_self.initialConfig);
            return _self;
        },
        //重置到远端已存储配置
        resetRemoteConfig:function(){
            var _self = this;
            _self.applyConfig(_self.remoteConfig);
            return _self;
        }
	});

	return XGridExtension;
});

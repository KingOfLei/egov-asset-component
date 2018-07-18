package com.bosssoft.egov.asset.lifecycle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/**
 *
 * @ClassName 类名：LifecycleLogConfigurationConstants
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2018年1月11日
 * @author 创建人：wujian
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2018年1月11日 wujian 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
@Component("_LifecycleLogConfigurationConstants")
public class LifecycleLogConfigurationConstants {

//	private static class LazyHolder {
//
//		private static LifecycleLogConfigurationConstants INSTANCE = AppContext.getAppContext().getBeanContext()
//				.getBean(LifecycleLogConfigurationConstants.class);
//	}
//
//	private LifecycleLogConfigurationConstants() {
//	}
//
//	public static LifecycleLogConfigurationConstants getInstance() {
//		return LazyHolder.INSTANCE;
//	}

	private static LifecycleLogConfigurationConstants INSTANCE;
	public static LifecycleLogConfigurationConstants getInstance(){
    	if(INSTANCE == null){
    		synchronized(LifecycleLogConfigurationConstants.class){
        		if(INSTANCE == null){
    			   INSTANCE = WebApplicationContext.getContext().getBeanContext().getBean(LifecycleLogConfigurationConstants.class);
    			}
    		}
    	}    	
    	return INSTANCE;
    }
	/**
	 * 方法业务规则 参照ActionBeanMethodOperLog 类
	 */
	public static final String[][] rule = { { "^doSave.*", "保存" }, { "^save.*", "保存" }, { "^insert.*", "保存" },
			{ "doInsert.*", "保存" }, { "^doUpdate.*", "修改" }, { "^update.*", "更新" }, { "^del.*", "删除" },
			{ "^doDel.*", "删除" }, { "^edit.*", "显示修改" }, { "^showEdit.*", "显示修改" }, { "^showAdd.*", "显示新增" },
			{ "^showIndex.*", "显示列表" }, { "^query.*", "查询" }, { "^get.*", "查询" }, { "^load.*", "查询" } };

	public static final String OPER_LIFECYCLE_RESULT_LOG_ENABLE = "oper.lifecycle.result.log.enable";

	public static final String OPER_LIFECYCLE_ENABLE = "oper.lifecycle.enable";


	public static final String OPER_LIFECYCLE_USE_ENABLE = "oper.lifecycle.use.enable";

	/**
	 * #是否要开启新生命周期的使用
	 */
	@Value("${" + OPER_LIFECYCLE_RESULT_LOG_ENABLE + "}")
	private String operLifecycleResultLogEnable;

	/**
	 * #新版生命周期操作结果为成功的是否要记录
	 */
	@Value("${" + OPER_LIFECYCLE_ENABLE + "}")
	private String operLifecycleEnable;

	@Value("${" + OPER_LIFECYCLE_USE_ENABLE + "}")
	private String operLifecycleUseEnable;
	
	
	public String getOperLifecycleUseEnable() {
		return operLifecycleUseEnable;
	}

	public void setOperLifecycleUseEnable(String operLifecycleUseEnable) {
		this.operLifecycleUseEnable = operLifecycleUseEnable;
	}

	public String getOperLifecycleResultLogEnable() {
		return operLifecycleResultLogEnable;
	}

	public void setOperLifecycleResultLogEnable(String operLifecycleResultLogEnable) {
		this.operLifecycleResultLogEnable = operLifecycleResultLogEnable;
	}

	public String getOperLifecycleEnable() {
		return operLifecycleEnable;
	}

	public void setOperLifecycleEnable(String operLifecycleEnable) {
		this.operLifecycleEnable = operLifecycleEnable;
	}

	
	//判断
	public static boolean operLifecycleResultLogEnable() {
		return "true".equalsIgnoreCase(getInstance().getOperLifecycleResultLogEnable());
	}

	public static boolean operLifecycleEnable() {
		return "true".equalsIgnoreCase(getInstance().getOperLifecycleEnable());
	}
	public static boolean operLifecycleUseEnable() {
		return "true".equalsIgnoreCase(getInstance().getOperLifecycleUseEnable());
	}
	
}

package com.bosssoft.egov.asset.aims.activity.controller;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bosssoft.egov.asset.activiti.entity.ActivitiException;
import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ActivitiShowPic;
import com.bosssoft.egov.asset.activiti.service.ActivitiProcessService;
import com.bosssoft.egov.asset.log.annotations.Operation;
import com.bosssoft.platform.common.utils.StringUtils;

/** 
 *
 * @ClassName   类名：ActivitiProcess 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2017年1月6日
 * @author      创建人：黄振雄
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2017年1月6日   黄振雄   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
@Controller
@RequestMapping(value="egov/asset/aims/activity")
public class ActivitiProcessController {
	@Autowired
	private ActivitiProcessService activitiprocessservice;
	
	@Operation(comment = "显示审核情况(流转状态)入口页面")
	@RequestMapping(value="showActivitiProcess.do")
	public String showActivitiProcess() {
		return "egov/asset/aims/activity/activity_add.ui";
	}
	
	/**
	 * <p>函数名称： getActivitiProcess       </p>
	 * <p>功能说明：获取审核流程情况
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param busType     业务类型
	 * @param businessId  业务ID
	 * @return
	 * @throws DocumentException
	 *
	 * @date   创建时间：2017年1月12日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	@Operation(comment = "获取审核情况(流转状态)历史记录")
	@RequestMapping(value="getActivitiProcess.do")
	@ResponseBody
	public ActivitiShowPic getActivitiProcess(String busType, String businessId) throws DocumentException {
		ActivitiParams params = new ActivitiParams();
		String realBustype = activitiprocessservice.queryOutBusType(businessId);
//		if(StringUtils.isNullOrBlank(realBustype)){
//			ActivitiShowPic temp = new ActivitiShowPic();
//			temp.setCode(ActivitiException.GETEMPTYREMARK.getCode());
//			temp.setMessage(ActivitiException.GETEMPTYREMARK.getName());
//			return temp;
//		}
		params.setBusinessId(businessId);
		params.setBusType(realBustype);
		
		ActivitiShowPic pic = activitiprocessservice.getHistoryListPic(params);
		return pic;
	}
}

package com.bosssoft.egov.asset.activiti.web;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bosssoft.egov.asset.activiti.activitiKit.ActivitiToolsService;
import com.bosssoft.egov.asset.activiti.entity.ActivitiException;
import com.bosssoft.egov.asset.activiti.entity.ActivitiNode;
import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ActivitiShowPic;
import com.bosssoft.egov.asset.activiti.entity.ActivitiTaskMoveComboboxShow;
import com.bosssoft.egov.asset.activiti.entity.RemarkLogStatements;
import com.bosssoft.egov.asset.activiti.service.ActivitiProcessService;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：AttachmentController 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月2日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月2日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@RequestMapping("activitiWeb")
@Controller
public class ActivitiWebController {
	 
	 @Autowired
	 private ActivitiProcessService activitiProcessService;
	 @Autowired
	 private  ActivitiToolsService activitiToolsService;
	
	 @RequestMapping(value = "reject.do")  
	 @ResponseBody
	 public Boolean reject(String userCode, String busType){
		 ActivitiParams params = new ActivitiParams();
		 params.setBusType(busType);
		 params.setUserCode(userCode);
		 return activitiProcessService.hasRejectOption(params);
	 } 
	 
	 @RequestMapping(value = "getReturnList.do",name="获取可跳转的退回列表")
	 @ResponseBody
	 public List<ActivitiTaskMoveComboboxShow> getReturnList(String busId){
		 ActivitiParams params = new ActivitiParams();
		 params.setBusinessId(busId);
		 return activitiToolsService.getReturnList2(params);
	 }
	 @RequestMapping(value = "flowStateList.do",name="获取可跳转的退回列表")
	 @ResponseBody
	 public ActivitiShowPic flowStateList(String busId) throws DocumentException{
		ActivitiParams params = new ActivitiParams();
		String realBustype = activitiProcessService.queryOutBusType(busId);
		params.setBusinessId(busId);
		params.setBusType(realBustype);
		ActivitiShowPic pic  = new ActivitiShowPic();
//		if(StringUtilsExt.isEmpty(realBustype)){
//			return pic;
//		}
		pic = activitiProcessService.getHistoryListPic(params);
		if (!pic.getCode().equals(ActivitiException.SUCCESS.getCode())) {
			return pic;
		}
		List<ActivitiNode> nodes = pic.getNodeSequence();
		List<ActivitiNode> afterNodes = new ArrayList<ActivitiNode>();
		for (ActivitiNode activitiNode : nodes) {
			String nodeName = activitiNode.getNodeName();
			if(StringUtilsExt.isNotEmpty(nodeName)){
				if(nodeName.contains("]") || nodeName.contains("】")){
					String [] str1 = nodeName.split("\\]");
					String [] str2 = nodeName.split("\\】");
					if (str1.length == 2) {
						activitiNode.setNodeName(str1[1]);
					}
					if (str2.length == 2) {
						activitiNode.setNodeName(str2[1]);
					}
				}
			}
			afterNodes.add(activitiNode);
		}
		List<RemarkLogStatements> rls = pic.getList();
		List<RemarkLogStatements> afterRls = new ArrayList<RemarkLogStatements>();
		for(int i=0,len=rls.size();i<len;i++){
			RemarkLogStatements r =  new RemarkLogStatements();
			r = rls.get(i);
			String rName = r.getRemark();
			String nodeName = r.getNodeName();
			if(StringUtilsExt.isNotEmpty(nodeName)){
				if(nodeName.contains("]") || nodeName.contains("】")){
					String [] str1 = nodeName.split("\\]");
					String [] str2 = nodeName.split("\\】");
					if (str1.length == 2) {
						r.setNodeName(str1[1]);
					}
					if (str2.length == 2) {
						r.setNodeName(str2[1]);
					}
				}
			}
			if(StringUtilsExt.isNotEmpty(rName)){
				if(rName.contains("：")){
					String [] str1 = rName.split("：");
					if (str1.length == 2) {
						r.setRemark(str1[1]);
					}
				}
			}
			afterRls.add(r);
		}
//		for (RemarkLogStatements r : rls) {
//		}
		pic.setList(afterRls);
		pic.setNodeSequence(afterNodes);
		return pic;
	 }
	 
}

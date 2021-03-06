package com.bosssoft.egov.asset.monitor.service.impl;

/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Mon Jan 16 13:08:55 CST 2017
 */

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.common.idgenerator.GUID;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.monitor.entity.AlterTypeConsts;
import com.bosssoft.egov.asset.monitor.entity.DateConsts;
import com.bosssoft.egov.asset.monitor.entity.FabMonitorEvt;
import com.bosssoft.egov.asset.monitor.entity.SystemConsts;
import com.bosssoft.egov.asset.monitor.mapper.FabMonitorEvtMapper;
import com.bosssoft.egov.asset.monitor.service.FabMonitorEvtService;
import com.bosssoft.egov.asset.monitor.service.FabMonitorRecordService;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.type.BDateTime;
import com.bosssoft.platform.common.utils.StringUtils;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.runtime.exception.BusinessException;

/**
 * 类说明: FabMonitorEvtService接口实现类.
 *
 * 类用途：
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-01-16   jinbiao　　　新建
 * </pre>
 */
@Service
public class FabMonitorEvtServiceImpl implements FabMonitorEvtService {

	private static Logger logger = LoggerFactory.getLogger(FabMonitorEvtServiceImpl.class);

	// TODO 添加需要的Mapper
	@Autowired
	private FabMonitorEvtMapper fabMonitorEvtMapper;
	@Autowired
	private FabMonitorRecordService fabMonitorRecordService;

	/**
	 *
	 * @param fabMonitorEvt
	 */
	public void addFabMonitorEvt(FabMonitorEvt fabMonitorEvt) {

//		fabMonitorEvt.setFhndlorgcode("1");
//		fabMonitorEvt.setFhndlorgid("1");
//		fabMonitorEvt.setFhndlorgname("1");
		if (fabMonitorEvt.getFissmsnoti() == null) {
			// 默认不通知
			fabMonitorEvt.setFissmsnoti(0);
		}
		if (fabMonitorEvt.getFisautoclose() == null) {
			// 默认处理后自动关闭
			fabMonitorEvt.setFisautoclose(0);
		}
		if (fabMonitorEvt.getFtrigcond() == null) {
			// 默认有结果时触发
			fabMonitorEvt.setFtrigcond(0);
		}
		if (fabMonitorEvt.getFhndlway() == null) {
			// 默认处理方式填写情况
			fabMonitorEvt.setFhndlway(1l);
		}
		if (fabMonitorEvt.getIsResult() == null) {
			// 默认结果即为统计结果
			fabMonitorEvt.setIsResult("0");
		}
		if (fabMonitorEvt.getIsNew() == null) {
			fabMonitorEvt.setIsNew("1");
		}
		fabMonitorEvt.setFid(GUID.newGUID());
		fabMonitorEvt.setFevtcode(GUID.newGUID());
		fabMonitorEvt.setFcreatetime(DateUtilsExt.formatDate(new Date(), "yyyy-MM-dd"));
		fabMonitorEvt.setFupdatetime(DateUtilsExt.formatDate(new Date(), "yyyy-MM-dd"));
		fabMonitorEvt.setFaltercode(AlterTypeConsts.ADD);
		fabMonitorEvt.setFversion(1l);
		fabMonitorEvt.setFopedate(DateUtilsExt.formatDate(new Date(), "yyyy-MM-dd"));
		
		fabMonitorEvtMapper.insert(fabMonitorEvt);
	}

	/**
	 *
	 * @param fabMonitorEvt
	 */
	public void delFabMonitorEvt(FabMonitorEvt fabMonitorEvt) throws BusinessException{
		
    	if(StringUtils.isNullOrBlank(fabMonitorEvt.getFid())){
    		throw new BusinessException("监控事项id不能为空");
    	}
		Searcher searcher = new Searcher();
		searcher.addCondition("FID", fabMonitorEvt.getFid());
		searcher.addCondition("FISENABLE", "1");
		Integer count = fabMonitorEvtMapper.queryCount(searcher);
		// List<FabMonitorRecord> list =
		// fabMonitorRecordService.queryList(searcher);
		if (count > 0) {
			throw new BusinessException("监控事项已被使用不能删除");
		}
		fabMonitorEvtMapper.deleteByPrimaryKey(fabMonitorEvt);
	}
	public void batchDelFabMonitorEvt(List<FabMonitorEvt> fabMonitorEvts) {
		for (FabMonitorEvt fabMonitorEvt : fabMonitorEvts) {
			this.delFabMonitorEvt(fabMonitorEvt);
		}
	}

	/**
	 *
	 * @param fabMonitorEvt
	 */
	public void updateFabMonitorEvt(FabMonitorEvt fabMonitorEvt) {
		
		fabMonitorEvtMapper.updateByPrimaryKey(fabMonitorEvt);
	}

	/**
	 *
	 * @param fabMonitorEvt
	 * @retrun
	 */
	public List<FabMonitorEvt> getFabMonitorEvtList(FabMonitorEvt fabMonitorEvt) {
		return null;
	}

	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<FabMonitorEvt> queryFabMonitorEvtPage(Searcher searcher, Page<FabMonitorEvt> page) {
		return fabMonitorEvtMapper.queryFabMonitorEvtPage(searcher, page);
	}

	public List<FabMonitorEvt> queryList(Searcher searcher) {
		return this.fabMonitorEvtMapper.queryList(searcher, null, null);
	}
	
	public void doEnable(List<FabMonitorEvt> fabMonitorEvts) throws BusinessException{
		for (FabMonitorEvt fabMonitorEvt : fabMonitorEvts) {
			this._doEnable(fabMonitorEvt);
		}
	}
	public void doDisable(List<FabMonitorEvt> fabMonitorEvts) throws BusinessException{
		for (FabMonitorEvt fabMonitorEvt : fabMonitorEvts) {
			this._doDisable(fabMonitorEvt);
		}
	}

	public void _doEnable(FabMonitorEvt fabMonitorEvts) throws BusinessException {
//		MonitorResult result = new MonitorResult();
//		if (StringUtils.isNullOrBlank(id)) {
//			result.setCode(MonitorException.CHOOSEMONITOR.getCode());
//			result.setCode(MonitorException.CHOOSEMONITOR.getName());
//			return result;
//		}
		if(StringUtils.isNullOrBlank(fabMonitorEvts.getFid())){
			throw new BusinessException("请选择要启用的监控事项");
		}
		_checkEnable(fabMonitorEvts.getFid(), SystemConsts.INT_YES);
//		FabMonitorEvt param = new FabMonitorEvt();
//		param.setFid(fabMonitorEvts.getFid());
//		param.setFisenable(SystemConsts.INT_YES);// 启用
//		param.setFaltercode(AlterTypeConsts.EDIT);// 修改
		fabMonitorEvts.setFisenable(SystemConsts.INT_YES);
		fabMonitorEvts.setFaltercode(AlterTypeConsts.EDIT);
		BDateTime curTime = new BDateTime();
		fabMonitorEvts.setFupdatetime(curTime.toString(DateConsts.DEFAULT_FORMAT));
//		param.setFupdatetime(curTime.toString(DateConsts.DEFAULT_FORMAT));// 最后修改时间
//		param.setFversion(1l);// 版本
		this.updateFabMonitorEvt(fabMonitorEvts);

//		return result;
	}
	
	public void _doDisable(FabMonitorEvt fabMonitorEvts) throws BusinessException {
		if(StringUtils.isNullOrBlank(fabMonitorEvts.getFid())){
			throw new BusinessException("请选择要启用的监控事项");
		}
		_checkEnable(fabMonitorEvts.getFid(), SystemConsts.INT_NO);
//		FabMonitorEvt param = new FabMonitorEvt();
//		param.setFid(fabMonitorEvts.getFid());
//		param.setFisenable(SystemConsts.INT_NO);//停用
		fabMonitorEvts.setFisenable(SystemConsts.INT_NO);
//		param.setFaltercode(AlterTypeConsts.EDIT);//修改
		fabMonitorEvts.setFaltercode(AlterTypeConsts.EDIT);
		BDateTime curTime = new BDateTime();
		fabMonitorEvts.setFupdatetime(curTime.toString(DateConsts.DEFAULT_FORMAT));
//		param.setFupdatetime(curTime.toString(DateConsts.DEFAULT_FORMAT));//最后修改时间
//		param.setFversion(1l);// 版本
		this.updateFabMonitorEvt((fabMonitorEvts));
		
	}


	/** 以下为私有方法.私有方法应以下划线"_"开头 */
	/**
	 * 判断监控事项是否已经启用或停用
	 * 
	 * @param id
	 * @param isEnable
	 */
	private void _checkEnable(String id, Integer isEnable) {
		// MonitorResult result = new MonitorResult();
		String exception = "当前监控事项已停用，无需再次停用";
		if (isEnable == SystemConsts.INT_YES) {
			exception = "当前监控事项已启用，无需再次启用";
		}
		Searcher searcher = new Searcher();
		searcher.addCondition("fid", id);
		searcher.addCondition("FISENABLE", isEnable);
		Integer count = fabMonitorEvtMapper.queryCount(searcher);
		if (count > 0) {
			throw new BusinessException(exception);
		}

		// return result;
	}

	public FabMonitorEvt queryOne(String fevtid) {
		FabMonitorEvt evt = new FabMonitorEvt();
		evt.setFid(fevtid);
		evt.setIsResult(null);
		evt.setIsNew(null);
		return fabMonitorEvtMapper.selectByPrimaryKey(fevtid);
	}

}
package com.bosssoft.egov.asset.monitor.service.impl;

/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Mon Jan 16 13:10:31 CST 2017
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.common.idgenerator.GUID;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.OrderByUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.monitor.entity.AlterTypeConsts;
import com.bosssoft.egov.asset.monitor.entity.DateConsts;
import com.bosssoft.egov.asset.monitor.entity.FabMonitorEvt;
import com.bosssoft.egov.asset.monitor.entity.FabMonitorEvtVersion;
import com.bosssoft.egov.asset.monitor.entity.FabMonitorModel;
import com.bosssoft.egov.asset.monitor.entity.FabMonitorRecord;
import com.bosssoft.egov.asset.monitor.entity.FabMonitorRecordDetail;
import com.bosssoft.egov.asset.monitor.entity.FabMonitorRecordUrlortype;
import com.bosssoft.egov.asset.monitor.entity.MointorInfo;
import com.bosssoft.egov.asset.monitor.entity.MonitorConsts;
import com.bosssoft.egov.asset.monitor.entity.MonitorException;
import com.bosssoft.egov.asset.monitor.entity.MonitorHndlWayConsts;
import com.bosssoft.egov.asset.monitor.entity.MonitorResult;
import com.bosssoft.egov.asset.monitor.entity.SystemConsts;
import com.bosssoft.egov.asset.monitor.mapper.FabMonitorRecordMapper;
import com.bosssoft.egov.asset.monitor.service.FabMonitorEvtService;
import com.bosssoft.egov.asset.monitor.service.FabMonitorEvtVersionService;
import com.bosssoft.egov.asset.monitor.service.FabMonitorModelService;
import com.bosssoft.egov.asset.monitor.service.FabMonitorRecordDetailService;
import com.bosssoft.egov.asset.monitor.service.FabMonitorRecordService;
import com.bosssoft.egov.asset.monitor.service.FabMonitorRecordUrlortypeService;
import com.bosssoft.egov.asset.monitor.service.MonitorInfoService;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.type.BDate;
import com.bosssoft.platform.common.lang.type.BDateTime;
import com.bosssoft.platform.common.utils.StringUtils;
import com.bosssoft.platform.persistence.entity.Condition;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.persistence.util.StringUtil;
import com.bosssoft.platform.runtime.exception.BusinessException;

/**
 * 类说明: FabMonitorRecordService接口实现类.
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
public class FabMonitorRecordServiceImpl implements FabMonitorRecordService {

	private static Logger logger = LoggerFactory.getLogger(FabMonitorRecordServiceImpl.class);

	// TODO 添加需要的Mapper
	@Autowired
	private FabMonitorRecordMapper fabMonitorRecordMapper;
	@Autowired
	private FabMonitorModelService fabMonitorModelService;
	@Autowired
	private FabMonitorEvtService fabMonitorEvtService;
	@Autowired
	private MonitorInfoService monitorInfoService;
	@Autowired
	private FabMonitorEvtVersionService fabMonitorEvtVersionService;
	@Autowired
	private FabMonitorRecordDetailService fabMonitorRecordDetailService;
	@Autowired
	private FabMonitorRecordUrlortypeService fabMonitorRecordUrlortypeService;

	/**
	 *
	 * @param fabMonitorRecord
	 */
	public void addFabMonitorRecord(FabMonitorRecord fabMonitorRecord) {
		fabMonitorRecordMapper.insert(fabMonitorRecord);
	}

	/**
	 *
	 * @param fabMonitorRecord
	 */
	public void delFabMonitorRecord(FabMonitorRecord fabMonitorRecord) {
		fabMonitorRecordMapper.deleteByPrimaryKey(fabMonitorRecord);
	}

	public FabMonitorRecord queryRecordByMonitorOrgCode(String orgCode) {
		Searcher searcher = new Searcher();
		searcher.addCondition("FOMONITORCODE", orgCode);
		List<FabMonitorRecord> list = fabMonitorRecordMapper.queryList(searcher, null, null);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 *
	 * @param fabMonitorRecord
	 */
	public void updateFabMonitorRecord(FabMonitorRecord fabMonitorRecord) {
		fabMonitorRecordMapper.updateByPrimaryKey(fabMonitorRecord);
	}

	/**
	 *
	 * @param fabMonitorRecord
	 * @retrun
	 */
	public List<FabMonitorRecord> getFabMonitorRecordList(FabMonitorRecord fabMonitorRecord) {
		return fabMonitorRecordMapper.select(fabMonitorRecord);
	}

	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<FabMonitorRecord> queryFabMonitorRecordPage(Searcher searcher, Page<FabMonitorRecord> page) {
		return fabMonitorRecordMapper.queryFabMonitorRecordPage(searcher, page);
	}

	private List<FabMonitorEvt> _getEnableEvt() {
		Searcher searcher = new Searcher();
		searcher.addCondition("fisenable", "1");
		List<FabMonitorEvt> list = fabMonitorEvtService.queryList(searcher);
		return list;
	}

	/**
	 * 使用监控事项code启动监控事项
	 */
	public void executeEnableEvt() {
		List<FabMonitorEvt> list = this._getEnableEvt();
		if (list != null && list.size() > 0) {
			for (FabMonitorEvt fabMonitorEvt : list) {
				this.executeMonitor(fabMonitorEvt.getFevtcode());
			}
		}
	}

	/**
	 * 我只是大自然的搬运工
	 */
	public MonitorResult executeMonitor(String code) {
		// code
		MonitorResult result = new MonitorResult();
		MonitorResult temp = new MonitorResult();
		if (StringUtil.isEmpty(code)) {
			result.setCode(MonitorException.MONITOREVTISEMPTY.getCode());
			result.setMessage(MonitorException.MONITOREVTISEMPTY.getName());
			return result;
		}
		// 监控事项
		temp = this._getEvt(code);
		if (!temp.getCode().equals(MonitorException.SUCCESS.getCode())) {
			result.setCode(temp.getCode());
			result.setMessage(temp.getMessage());
			return result;
		}
		FabMonitorEvt evt = temp.getFabMonitorEvt();
		if (evt == null) {
			result.setCode(MonitorException.NOEXCUTEMONITOR.getCode());
			result.setMessage(MonitorException.NOEXCUTEMONITOR.getName());
			return result;
		}
		if ((evt.getFisenable() == MonitorConsts.INT_YES)) {
			// 监控数据模型
			temp = this._getModel(evt.getFmodelid());
			if (!temp.getCode().equals(MonitorException.SUCCESS.getCode())) {
				result.setCode(temp.getCode());
				result.setMessage(temp.getMessage());
				return result;
			}
			FabMonitorModel model = temp.getFabMonitorModel();
			if (model == null) {
				result.setCode(MonitorException.MODELDATANOTEXIST.getCode());
				result.setMessage(MonitorException.MODELDATANOTEXIST.getName());
				return result;
			}
			// 完整的预警查询条件
			String querySql = "";
			if (!StringUtil.isEmpty(evt.getFaddsql())) {
				querySql = model.getFsql() + " " + _dealWithAddSql(evt.getFaddsql(), evt.getFid());
			} else {
				querySql = model.getFsql();
			}
			String isResult = "";
			isResult = evt.getIsResult();

			if ("1".equals(StringUtilsExt.convertNullToString(isResult))) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("ORG_CODE", evt.getFmcmcode());
				map.put("ORG_NAME", evt.getFmcmname());
				querySql = StringUtilsExt.environmentSubstitute(querySql, map);
			}
			// 预警查询结果
			List<MointorInfo> mInfo = monitorInfoService.queryInfoList(querySql);
			// 产生预警记录
			_doMointorInfo(mInfo, evt, model);
			// 维护业务表版本号
			if (evt.getFtrigcond() == 0) {
				_doMatenEvtVersion(mInfo, evt);
			}
		}
		return result;
	}

	/**
	 * 获取监控事项
	 * 
	 * @param evtCode监控事项编码
	 * @return
	 * @throws BusinessException
	 */
	private MonitorResult _getEvt(String evtCode) {
		MonitorResult result = new MonitorResult();
		if (StringUtil.isEmpty(evtCode)) {
			result.setCode(MonitorException.MONITOREVTIDISEMPTY.getCode());
			result.setMessage(MonitorException.MONITOREVTIDISEMPTY.getName());
			return result;
		}
		Searcher searcher = new Searcher();
		searcher.addCondition("fevtcode", evtCode);
		List<FabMonitorEvt> list = fabMonitorEvtService.queryList(searcher);
		if (list.size() <= 0) {
			result.setCode(MonitorException.MONITOREVTISEMPTY.getCode());
			result.setMessage(MonitorException.MONITOREVTISEMPTY.getName());
			return result;
		}
		result.setFabMonitorEvt(list.get(0));
		return result;
	}

	/**
	 * 获取监控数据模型
	 * 
	 * @param modelId监控数据模型id
	 * @return
	 * @throws BusinessException
	 */
	private MonitorResult _getModel(String modelId) {
		MonitorResult result = new MonitorResult();
		if (StringUtil.isEmpty(modelId)) {
			result.setCode(MonitorException.MONITORMODELIDISEMPTY.getCode());
			result.setMessage(MonitorException.MONITORMODELIDISEMPTY.getName());
			return result;
		}
		result.setFabMonitorModel(fabMonitorModelService.queryOne(modelId));

		return result;
	}

	/**
	 * 处理附件条件
	 * 
	 * @param addSql
	 * @param evtId
	 * @return
	 */
	private String _dealWithAddSql(String addSql, String evtId) {
		if (StringUtil.isEmpty(addSql)) {
			return "";
		}
		if (addSql.indexOf("?") >= 0) {
			FabMonitorEvtVersion version = this.fabMonitorEvtVersionService.queryByEvtId(evtId);
			String result = null;
			if (version == null) {
				result = addSql.replace("?", "=0");
			} else {
				result = addSql.replace("?", version.getFbusiversion());
			}
			return result;
		} else {
			return addSql;
		}
	}

	/**
	 * 处理预警结果
	 * 
	 * @param mInfo预警查询结果
	 * @param evt监控事项
	 * @param model监控数据模型
	 * @throws BusinessException
	 */
	private void _doMointorInfo(List<MointorInfo> mInfo, FabMonitorEvt evt, FabMonitorModel model) {
		// 新产生的监控记录
		List<FabMonitorRecord> list = new ArrayList<FabMonitorRecord>();
		// 用于获取相应的URL以及配置
		List<FabMonitorRecordUrlortype> urlList = new ArrayList<FabMonitorRecordUrlortype>();
		Map<String, FabMonitorRecordUrlortype> urlMap = new HashMap<String, FabMonitorRecordUrlortype>();

		urlList = fabMonitorRecordUrlortypeService.getFabMonitorRecordUrlortypeListAll();
		for (FabMonitorRecordUrlortype fabMonitorRecordUrlortype : urlList) {
			urlMap.put(fabMonitorRecordUrlortype.getTypeCode(), fabMonitorRecordUrlortype);
		}
		// 监控历史记录
		List<FabMonitorRecord> listhis = _getRecordHis(evt.getFid());
		List<String> recordOrg = new ArrayList<String>();
		List<String> recordMOrg = new ArrayList<String>();
		Map<String, FabMonitorRecord> recordMap = new HashMap<String, FabMonitorRecord>();
		List<FabMonitorRecordDetail> detailList = new ArrayList<FabMonitorRecordDetail>();
		String isResult = evt.getIsResult();
		if (evt.getFtrigcond() == 0) {// 结果集有记录时触发
			if (mInfo.size() > 0) {
				for (MointorInfo info : mInfo) {
					if (StringUtilsExt.convertNullToString(isResult).equals("0")) {
						String orgCode = "";
						if (StringUtils.isNotNullAndBlank(info.getOrgCode())) {
							orgCode = StringUtilsExt.convertNullToString(info.getOrgCode());
						} else {
							orgCode = StringUtilsExt.convertNullToString(evt.getFhndlorgcode());
						}
						String morgCode = StringUtilsExt.convertNullToString(evt.getFmcmcode());
						if (!(recordOrg.contains(orgCode) && recordMOrg.contains(morgCode))) {
							recordOrg.add(orgCode);
							recordMOrg.add(morgCode);
							FabMonitorRecord mr = _setMonitorRecord(info, evt, model, urlMap);
							if (mr == null) {
								continue;
							}
							recordMap.put(orgCode, mr);
							list.add(mr);
							FabMonitorRecordDetail detail = new FabMonitorRecordDetail();
							String comment = StringUtilsExt.environmentSubstituteByBean(evt.getFasitcont(), info);
							detail.setId(GUID.newGUID());
							if (StringUtils.isNotNullAndBlank(info.getOrgCode())) {
								detail.setOrgId(info.getOrgId());
								detail.setOrgCode(orgCode);
								detail.setOrgName(info.getOrgName());
							} else {
								detail.setOrgId(evt.getFhndlorgid());
								detail.setOrgName(evt.getFhndlorgname());
								detail.setOrgCode(orgCode);
							}
							detail.setBillId(info.getBusId());
							detail.setContent(comment);
							detail.setType(info.getType());
							detail.setMorgCode(evt.getFmcmcode());
							detail.setMorgId(evt.getFmcmid());
							detail.setMorgName(evt.getFmcmname());
							if (StringUtils.isNullOrBlank(info.getPtype())) {
								detail.setPtype(info.getPtype());
								detail.setPtypeName(info.getPtypeName());
							}
							detail.setTypeName(urlMap.get(info.getType()).getTypeName());
							detail.setUrl(urlMap.get(info.getType()).getUrl());
							detailList.add(detail);
						} else {
							FabMonitorRecordDetail detail = new FabMonitorRecordDetail();
							String comment = StringUtilsExt.environmentSubstituteByBean(evt.getFasitcont(), info);
							detail.setId(GUID.newGUID());
							if (StringUtils.isNotNullAndBlank(info.getOrgCode())) {
								detail.setOrgId(info.getOrgId());
								detail.setOrgCode(orgCode);
								detail.setOrgName(info.getOrgName());
							} else {
								detail.setOrgId(evt.getFhndlorgid());
								detail.setOrgName(evt.getFhndlorgname());
								detail.setOrgCode(orgCode);
							}
							detail.setBillId(info.getBusId());
							if (StringUtils.isNullOrBlank(info.getPtype())) {
								detail.setPtype(info.getPtype());
								detail.setPtypeName(info.getPtypeName());
							}
							detail.setContent(comment);
							detail.setType(info.getType());
							detail.setTypeName(urlMap.get(info.getType()).getTypeName());
							detail.setUrl(urlMap.get(info.getType()).getUrl());
							detail.setMorgCode(evt.getFmcmcode());
							detail.setMorgId(evt.getFmcmid());
							detail.setMorgName(evt.getFmcmname());
							detailList.add(detail);
						}
					} else {
						FabMonitorRecord mr = _setMonitorRecord(info, evt, model, urlMap);
						if (mr == null) {
							continue;
						}
						list.add(mr);
					}
				}
			}
			_doInsertDetail(detailList);
			_updateRecorkContent(list);
			_updateMonitorRecord(list, listhis, evt);
			// _doHasCond(list, listhis, evt);
			// 统计每个单位的个数
		} else {// 结果集无记录时触发
			if (mInfo.size() <= 0) {
				FabMonitorRecord mr = _setMonitorRecord(null, evt, model, urlMap);
				list.add(mr);
				_doHasNoCond(list, listhis, evt);
			} else if (listhis.size() > 0) {
				for (FabMonitorRecord mrhis : listhis) {
					_doUpdate(mrhis, evt);
				}
			}
		}
	}

	private void _updateMonitorRecord(List<FabMonitorRecord> list, List<FabMonitorRecord> listhis, FabMonitorEvt evt) {
		// boolean isHas = false;
		String isResult = "";
		for (FabMonitorRecord fabMonitorRecordNew : list) {
			isResult = fabMonitorRecordNew.getIsResult();
			if ("0".equals(StringUtilsExt.convertNullToString(isResult))) {
				// for (FabMonitorRecord fabMonitorRecord : listhis) {
				// isHas = false;

				// Searcher searcher = new Searcher();
				// searcher.addCondition("FMCMCODE",
				// fabMonitorRecordNew.getFmcmcode());
				// searcher.addCondition("FOMONITORCODE",
				// fabMonitorRecordNew.getFomonitorcode());
				FabMonitorRecord record = new FabMonitorRecord();
				record.setFclick(null);
				record.setFmcmcode(fabMonitorRecordNew.getFmcmcode());
				record.setFomonitorcode(fabMonitorRecordNew.getFomonitorcode());
				List<FabMonitorRecord> one = fabMonitorRecordMapper.select(record);

				if (!one.isEmpty()) {
					FabMonitorRecord record1 = new FabMonitorRecord();
					record1.setFclick(null);
					record1.setFmcmcode(fabMonitorRecordNew.getFmcmcode());
					record1.setFomonitorcode(fabMonitorRecordNew.getFomonitorcode());
					fabMonitorRecordMapper.delete(record1);
				}
				fabMonitorRecordMapper.insertSelective(fabMonitorRecordNew);

				// if
				// (fabMonitorRecordNew.getFomonitorcode().equals(fabMonitorRecord.getFomonitorcode()))
				// {
				// if
				// (fabMonitorRecordNew.getFmcmcode().equals(fabMonitorRecord.getFmcmcode()))
				// {
				// isHas = true;
				// BDateTime curTime = new BDateTime();
				// fabMonitorRecord.setFupdatetime(curTime.toString(DateConsts.DEFAULT_FORMAT));//
				// 最后修改时间
				// fabMonitorRecord.setFupdatetime(curTime.toString("yyyy-MM-dd"));//
				// 最后修改时间
				// fabMonitorRecord.setFmntcont(fabMonitorRecordNew.getFmntcont());
				// fabMonitorRecord.setFversion(fabMonitorRecord.getFversion() +
				// 1);
				// this.updateFabMonitorRecordByOrgCode(fabMonitorRecord);
				// break;
				// }
				// }
				// }
				// if (!isHas) {
				// fabMonitorRecordMapper.insertSelective(fabMonitorRecordNew);
				// }
			} else {
				// for (FabMonitorRecord fabMonitorRecord : listhis) {
				// isHas = false;
				// if
				// (fabMonitorRecordNew.getFomonitorcode().equals(fabMonitorRecord.getFomonitorcode()))
				// {
				// if
				// (fabMonitorRecordNew.getFevtid().equals(fabMonitorRecord.getFevtid()))
				// {
				// if
				// (fabMonitorRecordNew.getFmcmcode().equals(fabMonitorRecord.getFmcmcode()))
				// {
				// String typeNew =
				// StringUtilsExt.convertNullToString(fabMonitorRecordNew.getType());
				// String type =
				// StringUtilsExt.convertNullToString(fabMonitorRecord.getType());
				// if (typeNew.equals(type) &&
				// StringUtils.isNotNullAndBlank(type)) {
				//
				// isHas = true;
				// BDateTime curTime = new BDateTime();
				// fabMonitorRecord.setFupdatetime(curTime.toString(DateConsts.DEFAULT_FORMAT));//
				// 最后修改时间
				// fabMonitorRecord.setFupdatetime(curTime.toString("yyyy-MM-dd"));//
				// 最后修改时间
				// fabMonitorRecord.setFmntcont(fabMonitorRecordNew.getFmntcont());
				// fabMonitorRecord.setFversion(fabMonitorRecord.getFversion() +
				// 1);
				// this.updateFabMonitorRecordByOrgCodeByType(fabMonitorRecord);
				// break;
				// }
				// }
				// }
				// }
				// }
				// if (!isHas) {
				// fabMonitorRecordMapper.insertSelective(fabMonitorRecordNew);
				// }
				FabMonitorRecord record = new FabMonitorRecord();
				record.setFclick(null);
				record.setFmcmcode(fabMonitorRecordNew.getFmcmcode());
				record.setFomonitorcode(fabMonitorRecordNew.getFomonitorcode());
				record.setType(fabMonitorRecordNew.getType());
				List<FabMonitorRecord> one = fabMonitorRecordMapper.select(record);
				if (!one.isEmpty()) {
					FabMonitorRecord record1 = new FabMonitorRecord();
					record1.setFclick(null);
					record1.setFmcmcode(fabMonitorRecordNew.getFmcmcode());
					record1.setFomonitorcode(fabMonitorRecordNew.getFomonitorcode());
					record1.setType(fabMonitorRecordNew.getType());
					fabMonitorRecordMapper.delete(record1);
				}
				fabMonitorRecordMapper.insertSelective(fabMonitorRecordNew);
			}
		}
	}

	public void updateFabMonitorRecordByOrgCodeByType(FabMonitorRecord fabMonitorRecord) {
		Condition condition = new Condition(FabMonitorRecord.class);
		condition.createCriteria().andEqualTo("fomonitorcode", fabMonitorRecord.getFomonitorcode()).andEqualTo("type", fabMonitorRecord.getType())
				.andEqualTo("fmcmcode", fabMonitorRecord.getFmcmcode());
		fabMonitorRecordMapper.updateByExampleSelective(fabMonitorRecord, condition);
		// fabMonitorRecordDetailMapper.insert(fabMonitorRecordDetail);
	}

	public void updateFabMonitorRecordByOrgCode(FabMonitorRecord fabMonitorRecord) {
		Condition condition = new Condition(FabMonitorRecord.class);
		condition.createCriteria().andEqualTo("fomonitorcode", fabMonitorRecord.getFomonitorcode()).andEqualTo("fmcmcode", fabMonitorRecord.getFmcmcode());
		fabMonitorRecordMapper.updateByExampleSelective(fabMonitorRecord, condition);
		// fabMonitorRecordMapper.updateByExampleSelective(fabMonitorRecord,
		// condition);
		// fabMonitorRecordDetailMapper.insert(fabMonitorRecordDetail);
	}

	// 更新内容
	private void _updateRecorkContent(List<FabMonitorRecord> list) {
		for (FabMonitorRecord fabMonitorRecord : list) {
			String isResult = fabMonitorRecord.getIsResult();
			if ("1".equals(StringUtilsExt.convertNullToString(isResult))) {

			} else {
				String orgCode = fabMonitorRecord.getFomonitorcode();
				String morgCode = fabMonitorRecord.getFmcmcode();
				List<FabMonitorRecordDetail> detailList = fabMonitorRecordDetailService.getFabMonitorRecordDetailListByOrgCodeMorgCode(orgCode, morgCode);
				Integer count = detailList.size();
				fabMonitorRecord.setFmntcont(fabMonitorRecord.getFevtname() + "（" + count + "）条");
			}
		}
	}

	private void _doInsertDetail(List<FabMonitorRecordDetail> list) {
		for (int i = 0, len = list.size(); i < len; i++) {
			FabMonitorRecordDetail record = list.get(i);
			String billId = list.get(i).getBillId();
			if (StringUtils.isNullOrBlank(billId)) {
				continue;
			}
			FabMonitorRecordDetail detail = fabMonitorRecordDetailService.getFabMonitorRecordDetailListByBillIdOrgCodeMorgCode(record);
			FabMonitorRecordDetail saveDetail = new FabMonitorRecordDetail();
			if (detail != null) {
				// saveDetail = (FabMonitorRecordDetail)
				// BeanUtilsExt.copyBean(list.get(i), saveDetail);
				// saveDetail.setOrgCode(detail.getOrgCode());
				// saveDetail.setOrgId(detail.getOrgId());
				// saveDetail.setOrgName(detail.getOrgName());
				// saveDetail.setMorgCode(detail.getMorgCode());
				// saveDetail.setMorgId(detail.getMorgId());
				// saveDetail.setMorgName(detail.getMorgName());
				// fabMonitorRecordDetailService.updateFabMonitorRecordDetailByBillId(saveDetail);
				fabMonitorRecordDetailService.deleteDetailBy3Condition(detail);
			}
			// else {
			saveDetail = (FabMonitorRecordDetail) BeanUtilsExt.copyBean(list.get(i), saveDetail);
			saveDetail.setClick(detail.getClick());
			saveDetail.setContent(detail.getContent());
			fabMonitorRecordDetailService.addFabMonitorRecordDetailNull(saveDetail);
			// }
		}
	}

	/**
	 * 结果集无记录 判断新生产预警记录和历史预警记录通过 fmntcont(预警内容 )来判断
	 * 
	 * @param list
	 *            新生产记录集
	 * @param listHis
	 *            历史记录集
	 * @throws BusinessException
	 */
	private void _doHasNoCond(List<FabMonitorRecord> list, List<FabMonitorRecord> listhis, FabMonitorEvt evt) {
		// 新记录和历史记录同事存在
		if (list.size() > 0 && listhis.size() > 0) {

			Boolean ishas = false;
			// 处理历史数据
			for (FabMonitorRecord mrhis : listhis) {
				for (FabMonitorRecord mr : list) {
					if (mrhis.getFmntcont().equals(mr.getFmntcont())) {
						ishas = true;
					}
				}
				if (!ishas) {
					_doUpdate(mrhis, evt);
					ishas = false;
				}
			}
			// 处理新增数据
			for (FabMonitorRecord mr : list) {
				for (FabMonitorRecord mrhis : listhis) {
					if (mrhis.getFmntcont().equals(mr.getFmntcont())) {
						ishas = true;
					}
				}
				if (!ishas) {
					if (_queryCount(mr.getFbusiid(), mr.getFmntcont()) <= 0) {
						this.addFabMonitorRecord(mr);
						if (evt.getFissmsnoti() == 1) {
							// _sendSms(evt.getFnotitel(),
							// mr.getFmntcont());//发送短信
						}
					}

					ishas = false;
				}
			}
		} else if (list.size() > 0) {// 仅存在新记录
			for (FabMonitorRecord mr : list) {
				if (_queryCount(mr.getFbusiid(), mr.getFmntcont()) <= 0) {
					this.addFabMonitorRecord(mr);
					if (evt.getFissmsnoti() == 1) {
						// _sendSms(evt.getFnotitel(), mr.getFmntcont());//发送短信
					}
				}
			}
		}
	}

	/**
	 * 结果集有记录，判断新生产预警记录和历史预警记录通过 fbusiId(预警记录来源ID )来判断
	 * 
	 * @param list
	 *            新生成记录集
	 * @param listhis
	 *            历史记录集
	 * @throws BusinessException
	 */
	// private void _doHasCond(List<FabMonitorRecord>
	// list,List<FabMonitorRecord> listhis,FabMonitorEvt evt){
	//
	// if (list.size() > 0 && listhis.size() > 0) {
	//
	// Boolean ishas = false;
	// // 处理历史数据
	// for (FabMonitorRecord mrhis : listhis) {
	// // 通过field5（busId）以及内容来判断是否重复，先判断busid，然后再判断内容
	// for (FabMonitorRecord mr : list) {
	// if (!StringUtil.isEmpty(mrhis.getFomonitorcode()) &&
	// !StringUtil.isEmpty(mr.getFomonitorcode())) {
	// if (mrhis.getFomonitorcode().equals(mr.getFomonitorcode())) {
	// //
	// if(!StringUtil.isEmpty(mrhis.getFbusiid())&&!StringUtil.isEmpty(mr.getFbusiid())){
	// // if(mrhis.getFbusiid().equals(mr.getFbusiid())){
	// ishas = true;
	// }
	// } else {
	// if (mrhis.getFmntcont().equals(mr.getFmntcont())) {
	// ishas = true;
	// }
	// }
	// }
	// // 历史中有，新记录无
	// if (!ishas) {
	// _doUpdate(mrhis, evt);
	// ishas = false;
	// }
	// }
	//
	// // 处理新增数据
	// for (FabMonitorRecord mr : list) {
	// for (FabMonitorRecord mrhis : listhis) {
	// if (!StringUtil.isEmpty(mrhis.getFbusiid()) &&
	// !StringUtil.isEmpty(mr.getFbusiid())) {
	// if (mrhis.getFbusiid().equals(mr.getFbusiid())) {
	// ishas = true;
	// }
	// } else {
	// if (mrhis.getFmntcont().equals(mr.getFmntcont())) {
	// ishas = true;
	// }
	// }
	// }
	// // 新记录中有，历史中无
	// if (!ishas) {
	// if (_queryCount(mr.getFbusiid(), mr.getFmntcont()) <= 0) {
	// this.addFabMonitorRecord(mr);
	// if (evt.getFissmsnoti() == 1) {
	// // _sendSms(evt.getFnotitel(),
	// // mr.getFmntcont());//发送短信
	// }
	// }
	// ishas = false;
	// }
	// }
	// } else if (list.size() > 0) {
	// for (FabMonitorRecord mr : list) {
	// if (_queryCount(mr.getFbusiid(), mr.getFmntcont()) <= 0) {
	// this.addFabMonitorRecord(mr);
	// if (evt.getFissmsnoti() == 1) {
	// // _sendSms(evt.getFnotitel(), mr.getFmntcont());//发送短信
	// }
	// }
	// }
	// } else if (listhis.size() > 0) {
	// for (FabMonitorRecord mrhis : listhis) {
	// _doUpdate(mrhis, evt);
	// }
	// }
	//
	// }
	// private void _doHasCond(List<FabMonitorRecord>
	// list,List<FabMonitorRecord> listhis,FabMonitorEvt evt){
	//
	// if(list.size()>0&&listhis.size()>0){
	//
	// Boolean ishas = false;
	// //处理历史数据
	// for(FabMonitorRecord mrhis:listhis){
	// //通过field5（busId）以及内容来判断是否重复，先判断busid，然后再判断内容
	// for(FabMonitorRecord mr:list){
	// if(!StringUtil.isEmpty(mrhis.getFbusiid())&&!StringUtil.isEmpty(mr.getFbusiid())){
	// if(mrhis.getFbusiid().equals(mr.getFbusiid())){
	// ishas = true;
	// }
	// }else{
	// if(mrhis.getFmntcont().equals(mr.getFmntcont())){
	// ishas = true;
	// }
	// }
	// }
	// //历史中有，新记录无
	// if(!ishas){
	// _doUpdate(mrhis, evt);
	// ishas = false;
	// }
	// }
	//
	// //处理新增数据
	// for(FabMonitorRecord mr:list){
	// for(FabMonitorRecord mrhis:listhis){
	// if(!StringUtil.isEmpty(mrhis.getFbusiid())&&!StringUtil.isEmpty(mr.getFbusiid())){
	// if(mrhis.getFbusiid().equals(mr.getFbusiid())){
	// ishas = true;
	// }
	// }else{
	// if(mrhis.getFmntcont().equals(mr.getFmntcont())){
	// ishas = true;
	// }
	// }
	// }
	// //新记录中有，历史中无
	// if(!ishas){
	// if(_queryCount(mr.getFbusiid(), mr.getFmntcont())<=0){
	// this.addFabMonitorRecord(mr);
	// if(evt.getFissmsnoti()==1){
	// // _sendSms(evt.getFnotitel(), mr.getFmntcont());//发送短信
	// }
	// }
	// ishas = false;
	// }
	// }
	// }else if(list.size()>0){
	// for(FabMonitorRecord mr:list){
	// if(_queryCount(mr.getFbusiid(), mr.getFmntcont())<=0){
	// this.addFabMonitorRecord(mr);
	// if(evt.getFissmsnoti()==1){
	// // _sendSms(evt.getFnotitel(), mr.getFmntcont());//发送短信
	// }
	// }
	// }
	// }else if(listhis.size()>0){
	// for(FabMonitorRecord mrhis:listhis){
	// _doUpdate(mrhis, evt);
	// }
	// }
	//
	// }

	/**
	 * 更新已处理预警记录信息
	 * 
	 * @param mrhis
	 *            历史预警记录
	 * @param evt
	 *            预警事项
	 * @throws BusinessException
	 */
	private void _doUpdate(FabMonitorRecord mrhis, FabMonitorEvt evt) {
		if (evt.getFhndlway() != MonitorHndlWayConsts.MONITOR_HNDLWAY_EXPLAIN) {
			BDateTime curTime = new BDateTime();
			mrhis.setFishndl(SystemConsts.INT_YES);// 已处理
			mrhis.setFhndlname("系统自动处理");
			mrhis.setFhndldate(new BDate().toString());
			mrhis.setFhndltime(curTime.toString(DateConsts.HHMMSS));
			if (evt.getFisautoclose() == SystemConsts.INT_YES && mrhis.getFisclose() != SystemConsts.INT_YES) {// 自动关闭
				mrhis.setFisclose(SystemConsts.INT_YES);
				mrhis.setFclsman("系统自动关闭");
				mrhis.setFclsdate(new BDate().toString());
				mrhis.setFclstime(curTime.toString(DateConsts.HHMMSS));
			}
			mrhis.setFupdatetime(curTime.toString(DateConsts.DEFAULT_FORMAT));// 最后修改时间
			mrhis.setFupdatetime(curTime.toString("yyyy-MM-dd"));// 最后修改时间
			mrhis.setFaltercode(AlterTypeConsts.EDIT);// 变更类型编码
			mrhis.setFversion(mrhis.getFversion() + 1);// 版本
			this.updateFabMonitorRecord(mrhis);
		}
	}

	/**
	 * 判断插入的预警记录是否已经存在
	 * 
	 * @param pid
	 * @param pname
	 * @return
	 * @throws BusinessException
	 */
	private Integer _queryCount(String pid, String pname) {
		Searcher searcher = new Searcher();
		if (!StringUtil.isEmpty(pid)) {
			searcher.addCondition("fbusiId", pid);
		} else {
			searcher.addCondition("fmntcont", pname);
		}
		return fabMonitorRecordMapper.queryCount(searcher);
	}

	private List<FabMonitorRecord> _getRecordHis(String id) {
		Searcher searcher = new Searcher();
		searcher.addCondition("FEVTID", id);// 监控事项Id
		searcher.addCondition("fishndl", MonitorConsts.INT_NO);// 处理状态--未处理
		return fabMonitorRecordMapper.queryList(searcher, null, null);
	}

	/**
	 * 向新生成的历史记录插入关键字段
	 * 
	 * @param info
	 * @param evt
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	private FabMonitorRecord _setMonitorRecord(MointorInfo info, FabMonitorEvt evt, FabMonitorModel model, Map<String, FabMonitorRecordUrlortype> urlMap) {
		FabMonitorRecord monitorrecord = new FabMonitorRecord();
		monitorrecord.setFid(GUID.newGUID());// 主键

		monitorrecord.setFevtid(evt.getFid());// 监控事项ID
		monitorrecord.setFevtcode(evt.getFevtcode());// 监控事项编码
		monitorrecord.setFevtname(evt.getFevtname());// 监控事项名称
		monitorrecord.setFmcmid(evt.getFmcmid());// 发生机构ID
		monitorrecord.setFmcmcode(evt.getFmcmcode());// 发生机构编码
		monitorrecord.setFmcmname(evt.getFmcmname());// 发生机构名称
		/**
		 * 改造
		 */
		String type = info.getType();
		monitorrecord.setType(type);
		FabMonitorRecordUrlortype urlType = urlMap.get(type);
		if (urlType == null) {
			return null;
		}
		monitorrecord.setTypeName(urlMap.get(type).getTypeName());
		monitorrecord.setUrl(urlMap.get(type).getUrl());
		// 单条记录是否为前端显示统计结果
		monitorrecord.setIsResult(evt.getIsResult());

		// if (info != null && !StringUtil.isEmpty(info.getField2()) && //
		// 当遇见发生机构不为空时
		// !StringUtil.isEmpty(info.getField3()) &&
		// !StringUtil.isEmpty(info.getField4()) && MonitorConsts.INT_YES ==
		// evt.getFisnoticehndlorg()) {// 如果是否通知发生机构为是,则设置为处理机构,否则使用备用处理机构
		// monitorrecord.setFhndlorgid(info.getField2());// 处理机构ID
		// monitorrecord.setFhndlorgcode(info.getField3());// 处理机构编码
		// monitorrecord.setFhndlorgname(info.getField4());// 处理机构名称
		// } else {
		// monitorrecord.setFhndlorgid(evt.getFhndlorgid());// 处理机构ID
		// monitorrecord.setFhndlorgcode(evt.getFhndlorgcode());// 处理机构编码
		// monitorrecord.setFhndlorgname(evt.getFhndlorgname());// 处理机构名称
		// }

		if (evt.getFtrigcond() == 0) {// 结果集有记录时触发
			// 放入是否进行细分的名称,，ptype
			monitorrecord.setPtype(StringUtilsExt.convertNullToString(info.getPtype()));
			// 该记录是否为所需要的结果
			monitorrecord.setFrgnid(info.getField1());// 区划id
			if (StringUtils.isNotNullAndBlank(info.getOrgId())) {
				monitorrecord.setFomonitorcode(info.getOrgCode());// 预警发生机构名称,orgCode
				monitorrecord.setFomonitorname(info.getOrgName());// 预警发生机构编码
				monitorrecord.setFomonitorid(info.getOrgId());// 预警发生机构ID
				monitorrecord.setFbusiid(info.getBusId());// 预警记录来源ID
			} else {
				monitorrecord.setFomonitorid(evt.getFhndlorgid());// 预警发生机构ID
				monitorrecord.setFomonitorcode(evt.getFhndlorgcode());// 预警发生机构名称,orgCode
				monitorrecord.setFomonitorname(evt.getFhndlorgname());// 预警发生机构编码
			}
		} else {// 结果集无记录时触发--区划默认为预警事项区划，预警发生机构默认为预警处理机构，预警记录来源不添加
			monitorrecord.setFrgnid(evt.getFrgnid());// 区划id
		}

		BDateTime curTime = new BDateTime();
		if (evt.getFtrigcond() == 0) {// 有结果集时提示内容
			// 预警提示内容
			// monitorrecord.setFmntcont(this._getCont(evt.getFasitcont(),
			// info));
			String comment = StringUtilsExt.environmentSubstituteByBean(evt.getFasitcont(), info);
			if (StringUtilsExt.convertNullToString(info.getIsResult()).equals("0")) {
				monitorrecord.setFmntcont(evt.getFevtname());
			} else {
				monitorrecord.setFmntcont(comment);
			}
		} else {// 结果集无记录时提示内容
			// 预警提示内容
			// monitorrecord.setFmntcont(this._getNoCont(evt.getFasitcont()));
			String comment = StringUtilsExt.environmentSubstituteByBean(evt.getFasitcont(), info);
			if (StringUtilsExt.convertNullToString(info.getIsResult()).equals("0")) {
				monitorrecord.setFmntcont(evt.getFevtname());
			} else {
				monitorrecord.setFmntcont(comment);
			}
		}
		monitorrecord.setFmntdate(new BDate().toString());// 预警发生日期
		monitorrecord.setFmnttime(curTime.toString(DateConsts.HHMMSS));// 预警发生时间
		monitorrecord.setFishndl(SystemConsts.INT_NO);// 是否处理-- 否
		monitorrecord.setFisclose(SystemConsts.INT_NO);// 是否关闭--否
		monitorrecord.setFupdatetime(curTime.toString("yyyy-MM-dd"));// 最后修改时间
		monitorrecord.setFaltercode(AlterTypeConsts.ADD);// 变更类型编码
		// monitorrecord.setFversion(ticketFacadeService.getNextVersionNo());//
		// 版本
		monitorrecord.setFversion(1l);// 版本
		return monitorrecord;
	}

	// private String _getNoCont(String cont){
	// String[] conditions = cont.split("\\}");
	// StringBuffer info = new StringBuffer();
	// for(int i = 0;i<conditions.length;i++){
	// String[] fields = conditions[i].split("\\{");
	// if(fields.length==1){
	// info.append(fields[0]);
	// }else{
	// String name = fields[0];
	// String element = fields[1];
	// info.append(name).append(_getNoContInfo(element));
	// }
	// }
	//
	// return info.toString();
	// }

	// 无结果集时提供预警提示内容要素
	// private String _getNoContInfo(String element) {
	// String result = "";
	// if(element.equals("yyyy")){//年
	// result =new BDate().toString("yyyy");
	// }
	// if(element.equals("MM")){//月
	// result =new BDate().toString("MM");
	// }
	// if(element.equals("dd")){//日
	// result =new BDate().toString("dd");
	// }
	// if(element.equals("HH")){//时
	// result =new BDate().toString("HH");
	// }
	// if(element.equals("mm")){//分
	// result =new BDate().toString("mm");
	// }
	// if(element.equals("ss")){//秒
	// result =new BDate().toString("ss");
	// }
	// if(element.equals("JONE")){//第一季
	// result ="第一季";
	// }
	// if(element.equals("JTWO")){//第二季
	// result ="第二季";
	// }
	// if(element.equals("JTHREE")){//第三季
	// result ="第三季";
	// }
	// if(element.equals("JFOUR")){//第四季
	// result ="第四季";
	// }
	// if(element.equals("BY")){//上半年
	// result ="上半年";
	// }
	// if(element.equals("AY")){//下半年
	// result ="下半年";
	// }
	// return result;
	// }
	/** 以下为私有方法.私有方法应以下划线"_"开头 */
	/**
	 * 维护业务数据版本号记录表
	 * 
	 * @param info
	 * @param evt
	 */
	private void _doMatenEvtVersion(List<MointorInfo> info, FabMonitorEvt evt) {
		// 结果集有记录时触发
		if (info != null && info.size() > 0) {
			List<MointorInfo> list = OrderByUtil.sort(info, "field31 desc");
			String maxVersion = list.get(0).getField31();
			if (!StringUtil.isEmpty(maxVersion)) {
				FabMonitorEvtVersion version = fabMonitorEvtVersionService.queryByEvtId(evt.getFid());
				if (version == null) {
					FabMonitorEvtVersion insert = new FabMonitorEvtVersion();
					insert.setFid(GUID.newGUID());
					insert.setFbusiversion(maxVersion);
					insert.setFevtcode(evt.getFevtcode());
					insert.setFevtname(evt.getFevtname());
					insert.setFevtid(evt.getFid());
					fabMonitorEvtVersionService.addFabMonitorEvtVersion(insert);
				} else {
					version.setFbusiversion(maxVersion);
					fabMonitorEvtVersionService.updateFabMonitorEvtVersion(version);
				}
			}
		}
	}

	/**
	 * 获取预警记录信息
	 * 
	 * @param cont
	 *            预警提示内容模板
	 * @param param
	 *            预警记录
	 * @return
	 * @throws BusinessException
	 */
	// private String _getCont(String cont,MointorInfo param){
	//
	// String[] conditions = cont.split("\\}");
	// StringBuffer info = new StringBuffer();
	// for(int i = 0;i<conditions.length;i++){
	// String[] fields = conditions[i].split("\\{");
	// if(fields.length==1){
	// info.append(fields[0]);
	// }else{
	// String name = fields[0];
	// String element = fields[1];
	// info.append(name).append(_getContInfo(element, param));
	// }
	// }
	//
	// return info.toString();
	// }

	// 获取预警查询出记录信息的字段
	// private String _getContInfo(String element,MointorInfo param){
	// String result = "";
	// try {
	// result =BeanUtils.getProperty(param,element.toLowerCase());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

	public void batchDelFabMonitorRecord(List<FabMonitorRecord> fabMonitorRecords) {
		for (FabMonitorRecord fabMonitorRecord : fabMonitorRecords) {
			this.delFabMonitorRecord(fabMonitorRecord);
		}

	}

	public List<FabMonitorRecord> queryList(Searcher searcher) {
		return fabMonitorRecordMapper.queryList(searcher, null, null);
	}

	public Page<Map<String, Object>> queryRecordCountData(Searcher searcher, Page<FabMonitorRecord> page) {
		return fabMonitorRecordMapper.queryRecordCountData(searcher, page);
	}

	public FabMonitorRecord queryRecordOne(FabMonitorRecord record) {
		return fabMonitorRecordMapper.selectOne(record);
	}

	/**
	 * 每一次请求，增加一次点击数
	 */
	public void updateClickEveryRequest(FabMonitorRecord record) {
		// 点击查看后，fclick置位1
		if (record.getFclick() == null || record.getFclick() == 0) {
			record.setFclick(1l);
			// record.setFclick(NumberUtilsExt.toLong(record.getFclick() + "",
			// 1));
			fabMonitorRecordMapper.updateByPrimaryKey(record);
		}
	}

	public void doHandle(User user, FabMonitorRecord record) {
		FabMonitorRecord recordId = new FabMonitorRecord();
		recordId.setFid(record.getFid());
		recordId.setFclick(null);
		FabMonitorRecord newRecord = this.queryRecordOne(recordId);
		// if(SystemConsts.INT_YES == newRecord.getFishndl()){
		// throw new BusinessException("预警已经处理,不能再次处理!");
		// }
		// 如果为财政用户,设值为1,单位用户为0
		// if(user.getFrgnId().equals(user.getForgId())){
		// throw new BusinessException("财政用户不能处理预警记录");
		// }
		FabMonitorEvt evt = fabMonitorEvtService.queryOne(newRecord.getFevtid());
		// 如果处理方式为填写情况说明,不需要手动处理
		// if(MonitorHndlWayConsts.MONITOR_HNDLWAY_BUS == evt.getFhndlway()){
		// throw new BusinessException("此记录为业务数据,不能手动处理");
		// }
		newRecord.setFishndl(SystemConsts.INT_YES);
		newRecord.setFhndldesc(record.getFhndldesc());// 预警记录处理说明
		BDate date = new BDate();
		BDateTime time = new BDateTime();
		newRecord.setFhndlid(user.getUserId());
		newRecord.setFhndlname(user.getUserName());
		newRecord.setFhndldate(date.toString());
		newRecord.setFhndltime(time.toString(DateConsts.HHMMSS));
		// 判断是否处理完自动关闭
		if (SystemConsts.INT_YES == evt.getFisautoclose()) {
			newRecord.setFisclose(SystemConsts.INT_YES);
			newRecord.setFclsman("系统自动关闭");
			newRecord.setFclsdate(date.toString());
			newRecord.setFclstime(time.toString(DateConsts.HHMMSS));
		}

		newRecord.setFaltercode(AlterTypeConsts.EDIT);
		newRecord.setFupdatetime(time.toString("yyyy-MM-dd"));
		// newRecord.setFversion(ticketFacadeService.getNextVersionNo());
		newRecord.setFversion(newRecord.getFversion() + 1);
		this.updateFabMonitorRecord(newRecord);
		// this.monitorRecordDao.update(newRecord);

	}

	public void doClose(User user, String id) {
		FabMonitorRecord record = new FabMonitorRecord();
		record.setFid(id);
		FabMonitorRecord newRecord = this.queryRecordOne(record);
		// 没有处理的不能关闭
		if (SystemConsts.INT_NO == newRecord.getFishndl()) {
			throw new BusinessException("该预警还未处理,不能关闭!");
		}
		// 判断是否已经关闭
		if (SystemConsts.INT_YES == newRecord.getFisclose()) {
			throw new BusinessException("该预警已经关闭,不能再次关闭!");
		}

		FabMonitorEvt evt = fabMonitorEvtService.queryOne(newRecord.getFevtid());
		// 判断是否需要手动关闭
		if (SystemConsts.INT_YES == evt.getFisautoclose()) {
			throw new BusinessException("此记录不需要手动关闭");
		}
		// 只有财政用户才可以关闭
		// if(!user.getRgnId().toString().equals(user.getOrgId())){
		// throw new BusinessException("非财政用户不能关闭预警记录");
		// }
		BDate date = new BDate();
		BDateTime time = new BDateTime();
		newRecord.setFisclose(SystemConsts.INT_YES);
		newRecord.setFclsmanid(user.getUserId());
		newRecord.setFclsman(user.getUserName());
		newRecord.setFclsdate(date.toString());
		newRecord.setFclstime(time.toString(DateConsts.HHMMSS));
		// TODO 没有处理的怎么办?
		newRecord.setFaltercode(AlterTypeConsts.EDIT);
		newRecord.setFupdatetime(time.toString("yyyy-MM-dd"));
		newRecord.setFversion(evt.getFversion() + 1);
		// newRecord.setFversion(ticketFacadeService.getNextVersionNo());
		this.updateFabMonitorRecord(newRecord);
		// this.monitorRecordDao.update(newRecord);

	}

	public List<FabMonitorRecord> getFabMonitorRecordEvtName(Searcher searcher, String morgCode) {
		return fabMonitorRecordMapper.getFabMonitorRecordEvtName(searcher, morgCode);

	}
}
package com.bosssoft.egov.asset.lifecycle.service.impl;
/** 
*
* @ClassName   类名：OperateLifecycleServiceImpl 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月12日
* @author      创建人：wujian
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月12日   wujian   创建该类功能。
*
***********************************************************************
*</p>
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.SpringObjectUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.common.util.collection.CollectionUtilsExt;
import com.bosssoft.egov.asset.lifecycle.entity.AssetLifeConfig;
import com.bosssoft.egov.asset.lifecycle.entity.AssetLifeConfigItem;
import com.bosssoft.egov.asset.lifecycle.mapper.AssetLifeConfigItemMapper;
import com.bosssoft.egov.asset.lifecycle.mapper.AssetLifeConfigMapper;
import com.bosssoft.egov.asset.lifecycle.mapper.AssetLifecycleMapper;
import com.bosssoft.egov.asset.lifecycle.service.OperateLifecycleService;
import com.bosssoft.platform.runtime.exception.BusinessException;

@Service
public class OperateLifecycleServiceImpl implements OperateLifecycleService {

	@Autowired
	private AssetLifecycleMapper assetLifecycleMapper;

	@Autowired
	private AssetLifeConfigMapper assetLifeConfigMapper;

	@Autowired
	private AssetLifeConfigItemMapper assetLifeConfigItemMapper;

	private final static String LIFE_UPDATE_SIMPLE = "UPDATE_SIMPLE";

	private final static String LIFE_UPDATE = "UPDATE";

	private final static String LIFE_INSERT = "INSERT";

	private final static String LIFE_DELETE = "DELETE";

	private final static String INSERT_LIFE_LOG = "INSERT_LIFE_LOG";

	private final static String DELETE_LIFE_LOG = "DELETE_LIFE_LOG";

	@Override
	public void doWriteAimsCard6Life(Long orgId, String billType, Integer isAudit, List<Long> billIdsList) {
		StringBuffer srcFieldSql = new StringBuffer();
		StringBuffer destFieldSql = new StringBuffer();

		// StringBuffer sqlbuffer = new StringBuffer();
		// sqlbuffer
		// .append("update aims_use set declare_rea ='" + "啊啊啊啊啊" + "' where
		// bill_id=22629193929982979");
		// generalMapper.commonUpdate(sqlbuffer.toString());
		if (SpringObjectUtilsExt.isEmpty(orgId)
				|| SpringObjectUtilsExt.isEmpty(isAudit) || CollectionUtilsExt.isEmpty(billIdsList)) {
			throw new BusinessException("系统在更新生命周期过程中，参数为空！");
		}

		List<AssetLifeConfig> assetLifeConfigList = assetLifeConfigMapper.getAssetLifeConfig(billType,
				isAudit, orgId, 0);

		if (SpringObjectUtilsExt.isEmpty(assetLifeConfigList)) {
			throw new BusinessException("系统在更新生命周期过程中，获取配置失败！");
		}

		for (AssetLifeConfig assetLifeConfig : assetLifeConfigList) {

			Long busConfigId = assetLifeConfig.getBusConfigId();
			Long dictConfigId = assetLifeConfig.getDictConfigId();
			Long commonConfigId = assetLifeConfig.getCommonConfigId();
			String configOperation = assetLifeConfig.getConfigOperation();
			String srcViewName = assetLifeConfig.getSourceViewName();

			Map<String, Object> columnMap = new LinkedHashMap<>();
			if (!LIFE_DELETE.equals(configOperation)) {

				// 1、获取字典配置信息项
				List<AssetLifeConfigItem> assetLifeDictConfigItemsList = assetLifeConfigItemMapper
						.getAssetLifeDictConfigItem(dictConfigId);

				// 2、获取基本业务配置信息项
				List<AssetLifeConfigItem> assetLifeConfigItemsList = assetLifeConfigItemMapper
						.getAssetLifeConfigItem(busConfigId);

				// 3、合并字典配置信息项和基本业务配置信息项
				List<AssetLifeConfigItem> configItemsList = new ArrayList<AssetLifeConfigItem>();
				configItemsList.addAll(assetLifeConfigItemsList);
				configItemsList.addAll(assetLifeDictConfigItemsList);

				// 4、合并后的信息项拆分为基本信息项和校验信息项
				List<AssetLifeConfigItem> basicConfigItemsList = new ArrayList<AssetLifeConfigItem>();
				List<AssetLifeConfigItem> checkConfigItemsList = new ArrayList<AssetLifeConfigItem>();

				for (AssetLifeConfigItem assetLifeConfigItem : configItemsList) {

					if (SpringObjectUtilsExt.isEmpty(assetLifeConfigItem.getCheckStatus())
							|| assetLifeConfigItem.getCheckStatus() == 0) {
						basicConfigItemsList.add(assetLifeConfigItem);
					} else {
						checkConfigItemsList.add(assetLifeConfigItem);
					}
				}

				// 校验以及 判断配置明细是否有配置业务日期、时间、类型，没有则从配置实体读取
				checkConfigItemsList = checkAssetLifeConfigItem(checkConfigItemsList, srcViewName,
						billIdsList);

				for (AssetLifeConfigItem assetLifeConfigItem : basicConfigItemsList) {
					columnMap.put(assetLifeConfigItem.getDestFieldName(),
							assetLifeConfigItem.getSrcFieldName());
				}

				for (AssetLifeConfigItem assetLifeConfigItem : checkConfigItemsList) {
					columnMap.put(assetLifeConfigItem.getDestFieldName(),
							assetLifeConfigItem.getSrcFieldName());
				}

				// 获取公共卡片信息项
				List<AssetLifeConfigItem> assetLifeConfigItemList_common = assetLifeConfigItemMapper
						.getCommonColumn(busConfigId, dictConfigId, commonConfigId);

				for (AssetLifeConfigItem assetLifeConfigItem : assetLifeConfigItemList_common) {
					columnMap.put(assetLifeConfigItem.getDestFieldName(),
							assetLifeConfigItem.getDestFieldName());
				}
			} else if (LIFE_UPDATE.equals("----")) {
				// 获取字典配置信息项
				List<AssetLifeConfigItem> assetLifeDictConfigItemsList = assetLifeConfigItemMapper
						.getAssetLifeDictConfigItem(dictConfigId);
				for (AssetLifeConfigItem assetLifeConfigItem : assetLifeDictConfigItemsList) {
					columnMap.put(assetLifeConfigItem.getDestFieldName(),
							assetLifeConfigItem.getSrcFieldName());
				}
			} else if (LIFE_DELETE.equals(configOperation)) {

			} else {
				throw new BusinessException("系统更新生命周期过程失败,未配置该种业务类型【" + configOperation + "】！");
			}
			String destField = null;
			String srcField = null;
			if (!SpringObjectUtilsExt.isEmpty(columnMap)) {

				srcFieldSql.setLength(0);
				destFieldSql.setLength(0);
				for (Map.Entry<String, Object> entry : columnMap.entrySet()) {
					destFieldSql.append(entry.getKey() + ",");
					srcFieldSql.append(entry.getValue() + ",");
				}
				destField = destFieldSql.toString();
				destField = destField.substring(0, destField.length() - 1);

				srcField = srcFieldSql.toString();
				srcField = srcField.substring(0, srcField.length() - 1);

			}
			try {
				if (StringUtilsExt.isEmpty(srcViewName)) {
					throw new BusinessException("系统更新生命周期过程失败,配置数据源丢失！");
				}

				switch (configOperation) {
				case LIFE_INSERT:
					assetLifecycleMapper.insertLife(destField, srcField, srcViewName, billIdsList);
					break;
				case LIFE_UPDATE:
					assetLifecycleMapper.updateLife(destField, srcField, billIdsList, srcViewName);
					break;
				case LIFE_UPDATE_SIMPLE:
					assetLifecycleMapper.updateLifeSimple(destField, srcField, billIdsList, srcViewName);
					break;
				case LIFE_DELETE:
					assetLifecycleMapper.deleteLife(billIdsList, srcViewName);
					break;
				default:
					throw new BusinessException("系统更新生命周期过程失败,未配置该种业务类型【" + configOperation + "】！");
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
		}

	}

	/**
	 * 
	 * <p>
	 * 函数名称：
	 * </p>
	 * <p>
	 * 功能说明：校验配置信息项
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param billDateFlag
	 * @param busTimeFlag
	 * @param typeCodeFlag
	 * @param checkConfigItemsList
	 * @param assetLifeConfig
	 * @param srcViewName
	 * @param billIdsList
	 * @return
	 *
	 * @date 创建时间：2017年12月6日
	 * @author 作者：wujian
	 */
	private List<AssetLifeConfigItem> checkAssetLifeConfigItem(List<AssetLifeConfigItem> checkConfigItemsList,
			String srcViewName, List<Long> billIdsList) {

		// 判断校验配置信息项是否为空
		if (SpringObjectUtilsExt.isEmpty(checkConfigItemsList)) {
			return checkConfigItemsList;
		}

		// 获取校验sql片段
		List<Map<String, Object>> tempCheckSqlInfoList = assetLifeConfigItemMapper
				.getCheckSqlInfo(checkConfigItemsList, srcViewName);

		List<Map<String, String>> checkSqlInfoList = new ArrayList<Map<String, String>>();

		// 遍历替换校验sql里的参数

		for (Map<String, Object> map : tempCheckSqlInfoList) {

			for (AssetLifeConfigItem assetLifeConfigItem : checkConfigItemsList) {
				if (String.valueOf(assetLifeConfigItem.getCheckItemId())
						.equals(StringUtilsExt.toString(map.get("CHECK_ITEM_ID")))) {
					Map<String, String> params = new HashMap<String, String>();
					params.put("param", assetLifeConfigItem.getSrcFieldName());
					params.put("SQLUNION", StringUtilsExt
							.environmentSubstitute(StringUtilsExt.toString(map.get("SQLUNION")), params));
					checkSqlInfoList.add(params);
				}
			}
		}
		try {
			// 获取校验结果
			List<Map<String, Object>> checkData = assetLifeConfigItemMapper.checkConfigItem(billIdsList,
					checkSqlInfoList, srcViewName);
			// 判断
			if (checkData.size() > 0) {
				StringBuffer msgStringBuffer = new StringBuffer();
				for (Map<String, Object> map : checkData) {
					Long billId = NumberUtilsExt.toLong(StringUtilsExt.toString(map.get("BILL_ID")));
					Long assetId = NumberUtilsExt.toLong(StringUtilsExt.toString(map.get("ASSET_ID")));
					// String assetName =
					// StringUtilsExt.toString(map.get("ASSET_NAME"));
					// String assetCode =
					// StringUtilsExt.toString(map.get("ASSET_CODE"));
					String errorMsg = StringUtilsExt.toString(checkData.get(0).get("SHOW_INFO"));
					msgStringBuffer.append("单据【" + billId + "】中资产卡片【" + assetId + "】:" + errorMsg + ";");
				}
				throw new BusinessException(msgStringBuffer.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return checkConfigItemsList;
	}

	@Override
	public void doWriteAimsCard6LifeLog(Long orgId, String billType, Integer isAudit, Long billId) {
		StringBuffer srcFieldSql = new StringBuffer();
		StringBuffer destFieldSql = new StringBuffer();

		List<AssetLifeConfig> assetLifeConfigList = assetLifeConfigMapper.getAssetLifeConfig(billType,
				isAudit, orgId, 1);

		if (SpringObjectUtilsExt.isEmpty(assetLifeConfigList)) {
			throw new BusinessException("系统在写入生命周期日志过程中，获取配置失败！");
		}

		for (AssetLifeConfig assetLifeConfig : assetLifeConfigList) {

			Long busConfigId = assetLifeConfig.getBusConfigId();
			Long dictConfigId = assetLifeConfig.getDictConfigId();
			String configOperation = assetLifeConfig.getConfigOperation();
			String srcViewName = assetLifeConfig.getSourceViewName();

			Map<String, Object> columnMap = new LinkedHashMap<>();
			if (INSERT_LIFE_LOG.equals(configOperation)) {

				// 1、获取字典配置信息项
				List<AssetLifeConfigItem> assetLifeDictConfigItemsList = assetLifeConfigItemMapper
						.getAssetLifeDictConfigItem(dictConfigId);

				// 2、获取基本业务配置信息项
				List<AssetLifeConfigItem> assetLifeConfigItemsList = assetLifeConfigItemMapper
						.getAssetLifeConfigItem(busConfigId);

				// 3、合并字典配置信息项和基本业务配置信息项
				List<AssetLifeConfigItem> configItemsList = new ArrayList<AssetLifeConfigItem>();
				configItemsList.addAll(assetLifeConfigItemsList);
				configItemsList.addAll(assetLifeDictConfigItemsList);

				for (AssetLifeConfigItem assetLifeConfigItem : configItemsList) {
					columnMap.put(assetLifeConfigItem.getDestFieldName(),
							assetLifeConfigItem.getSrcFieldName());
				}

			} else if (DELETE_LIFE_LOG.equals(configOperation)) {

			} else {
				throw new BusinessException("系统更新生命周期日志过程失败,未配置该种业务类型【" + configOperation + "】！");
			}
			String destField = null;
			String srcField = null;
			if (!SpringObjectUtilsExt.isEmpty(columnMap)) {

				srcFieldSql.setLength(0);
				destFieldSql.setLength(0);
				for (Map.Entry<String, Object> entry : columnMap.entrySet()) {
					destFieldSql.append(entry.getKey() + ",");
					srcFieldSql.append(entry.getValue() + ",");
				}
				destField = destFieldSql.toString();
				destField = destField.substring(0, destField.length() - 1);

				srcField = srcFieldSql.toString();
				srcField = srcField.substring(0, srcField.length() - 1);

				Map<String, String> params = new HashMap<String, String>();
				params.put("bizType", billType);
				params.put("bizId", StringUtilsExt.toString(billId));
				srcField = StringUtilsExt.environmentSubstitute(srcField.toString(), params);
			}
			try {
				if (isAudit == 1) {
					if (StringUtilsExt.isEmpty(srcViewName)) {
						throw new BusinessException("系统更新生命周期日志过程失败,配置数据源丢失！");
					}
				}

				switch (configOperation) {
				case INSERT_LIFE_LOG:
					assetLifecycleMapper.insertLifeLog(destField, srcField, srcViewName, billId);
					break;
				case DELETE_LIFE_LOG:
					assetLifecycleMapper.deleteLifeLog(billId);
					break;
				default:
					throw new BusinessException("系统更新生命周期过程失败,未配置该种业务类型【" + configOperation + "】！");
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
		}

	}
}

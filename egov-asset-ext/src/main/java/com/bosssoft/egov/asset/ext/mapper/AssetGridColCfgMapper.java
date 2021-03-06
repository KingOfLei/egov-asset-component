/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * 2017年10月25日 下午3:03:36
 */
package com.bosssoft.egov.asset.ext.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.ext.entity.AssetGridColCfg;
import com.bosssoft.platform.persistence.common.Mapper;

/**
 * 类说明: 网格列配置mapper接口
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017年10月25日  chenzhibin  新建
 * </pre>
 */
public interface AssetGridColCfgMapper extends Mapper<AssetGridColCfg>{

	/**
	 * 根据网格配置id获取列信息
	 * @param gridCfgId
	 * @return
	 */
	List<AssetGridColCfg> getGridColCfgByGridCfgId(@Param("gridCfgId")Long gridCfgId);

	/**
	 * 新增网格列配置
	 * @param columns
	 * @param gridCfgId
	 */
	int insertColumnsCfg(@Param("columns")List<AssetGridColCfg> columns);

	/**
	 * 删除列配置
	 * @param id
	 */
	int deleteGridColCfgByGridCfgId(@Param("gridCfgId")Long gridCfgId);
}

package com.bosssoft.egov.asset.amc.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.amc.user.entity.DrcAuthRule;
import com.bosssoft.platform.persistence.common.Mapper;

/** 
 *
 * @ClassName   类名：AmcDrcAuthRuleMapper 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2017年4月9日
 * @author      创建人：黄振雄
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2017年4月9日   黄振雄   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public interface AmcDrcAuthRuleMapper extends Mapper<DrcAuthRule>{

	public int updateById(DrcAuthRule drcAuthRule);
	
	public int deleteById(DrcAuthRule drcAuthRule);
	
	public int delSubPos(DrcAuthRule drcAuthRule);
	
	/**
	 * <p>函数名称：batchDeleteAuthRule        </p>
	 * <p>功能说明：批量删除机构岗位信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param drcAuthRules
	 *
	 * @date   创建时间：2017年5月3日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public void batchDeleteAuthRule(@Param("userCodes") List<String> userCodes);
}

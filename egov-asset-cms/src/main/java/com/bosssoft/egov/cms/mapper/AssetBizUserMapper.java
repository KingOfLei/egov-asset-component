/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Wed Apr 12 00:06:32 CST 2017
 */
package com.bosssoft.egov.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.bosssoft.egov.cms.entity.AssetBizUser;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.persistence.common.Mapper;
import com.bosssoft.platform.common.lang.data.Searcher;

/**
 * 类说明: 业务读取历史 Mapper接口（ Mapper层） .
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-04-12   Administrator　　　新建
 * </pre>

 */

public interface AssetBizUserMapper extends Mapper<AssetBizUser>{



/**
	 * 
	 * 查询业务读取历史分页.
	 *	`
	 * @param searcher 查询条件
	 * @param pageable 分页信息
	 * @return AssetBizUserPage列表
	 * @throws BusinessException 自定义异常
	 *
	 */
	public Page<AssetBizUser> queryAssetBizUserPage(@Param("searcher")Searcher searcher,@Param("page")Page<AssetBizUser> page);
			
	@Update("UPDATE ASSET_BIZ_USER SET COUNT=COUNT+1,READ_TIME=#{readTime} WHERE BIZ_ID = #{bizId} AND USER_CODE = #{userCode}")
    public int updateReadCount(@Param("bizId")Long bizId,@Param("userCode")String userCode,@Param("readTime") String readTime);
		
	/**
	 * <p>函数名称：batchDeleteByBizId        </p>
	 * <p>功能说明：根据bizType和bizIds删除记录
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType
	 * @param bizIds
	 *
	 * @date   创建时间：2017年12月22日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public void batchDeleteByBizId(@Param("bizType") String bizType,@Param("bizIds") List<String> bizIds);
	
}
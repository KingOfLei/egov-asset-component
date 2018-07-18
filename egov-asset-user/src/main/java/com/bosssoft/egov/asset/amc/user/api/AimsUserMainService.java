/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Fri Sep 15 09:37:29 CST 2017
 */
package com.bosssoft.egov.asset.amc.user.api;

import java.util.List;

import com.aspose.words.PageInfo;
import com.bosssoft.egov.asset.amc.user.model.AimsUserMain;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-09-15   BS　　　新建
 * </pre>
 */
public interface AimsUserMainService {

/**
	 * 增加.
	 * 
	 * @param aimsUserMain 
	 */
	public void addAimsUserMain(AimsUserMain aimsUserMain);

	/**
	 * 删除及相关信息.
	 * 
	 * @param aimsUserMain 
	 */
	public void delAimsUserMain(AimsUserMain aimsUserMain);

	/**
	 * 修改.
	 * 
	 * @param aimsUserMain 
	 */
	public void updateAimsUserMain(AimsUserMain aimsUserMain);

	/**
	 * 获取列表.
	 * 
	 */
	public List<AimsUserMain> getAimsUserMainList(AimsUserMain aimsUserMain);




   	/**
	 * 获取分页.
	 * 
		 */
	public Page<AimsUserMain> queryAimsUserMainPage(Searcher searcher, PageInfo pageInfo);
	
	public List<AimsUserMain> select(AimsUserMain aimsUserMain);
			
    /**
     * <p>函数名称：queryShowTodoPage        </p>
     * <p>功能说明：
     *
     * </p>
     *<p>参数说明：</p>
     * @param searcher
     * @param pageInfo
     * @return
     *
     * @date   创建时间：2017年9月26日
     * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
     */
    public Page<AimsUserMain> queryShowTodoPage(Searcher searcher, Page<AimsUserMain> pageInfo);
}
 
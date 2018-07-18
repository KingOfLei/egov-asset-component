/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Wed Nov 30 13:41:11 CST 2016
 */
package com.bosssoft.egov.asset.activiti.service;

import java.util.List;

import com.bosssoft.egov.asset.activiti.entity.AssetActivitiBus;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-11-30   jinbiao　　　新建
 * </pre>
 */
public interface AssetActivitiBusService {

	/**
	 * 增加.
	 * 
	 * @param assetActivitiBus
	 */
	public void addAssetActivitiBus(AssetActivitiBus assetActivitiBus);

	/**
	 * 删除及相关信息.
	 * 
	 * @param assetActivitiBus
	 */
	public void delAssetActivitiBus(AssetActivitiBus assetActivitiBus);

	/**
	 * 修改.
	 * 
	 * @param assetActivitiBus
	 */
	public void updateAssetActivitiBus(AssetActivitiBus assetActivitiBus);

	/**
	 * 获取列表.
	 * 
	 */
	public List<AssetActivitiBus> getAssetActivitiBusList(AssetActivitiBus assetActivitiBus);

	/**
	 * 获取分页.
	 * 
	 */
	public Page<AssetActivitiBus> queryAssetActivitiBusPage(Searcher searcher, Page<AssetActivitiBus> page);

	public AssetActivitiBus queryAssetActivitiBus(String busType);

	/**
	 * 
	 * <p>函数名称：  内部查询用这个，用key查询      </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param busKey
	 * @return
	 *
	 * @date   创建时间：2017年2月9日
	 * @author 作者：jinbiao
	 */
	public AssetActivitiBus queryAssetActivitiBusByKey(String busKey);

	public List<AssetActivitiBus> getAssetActivitiBusList();
}
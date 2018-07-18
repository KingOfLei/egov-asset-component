package com.bosssoft.egov.asset.drc.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.drc.entity.DrcAuthOrg;
import com.bosssoft.egov.asset.drc.mapper.DrcAuthOrgMapper;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;
import com.bosssoft.platform.persistence.entity.Condition;

/** 
*
* @ClassName   类名：DrcAuthOrgCacheLoader 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年4月27日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年4月27日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class DrcAuthOrgCacheLoader implements CacheLoader{

	
	@Resource
	private DrcAuthOrgMapper drcAuthOrgMapper;
	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {
		Condition condition = new Condition(DrcAuthOrg.class);
		condition.createCriteria().andEqualTo("status", 1);
		condition.orderBy("userCode");
		List<DrcAuthOrg> data = drcAuthOrgMapper.selectByExample(condition);
		return buildDrcAuthOrg(data);
	}
	
	private Map<Serializable, Serializable> buildDrcAuthOrg(List<DrcAuthOrg> data){
		Map<Serializable, Serializable> result = new HashMap<Serializable, Serializable>();
		if(data != null && !data.isEmpty()){
			Set<String> keyMap = new HashSet<String>();
			List<String> orgList = new ArrayList<String>();;
			String curUserCode= "";
			String preUserCode= "";
			int i = 0;
			for(DrcAuthOrg authOrg : data){
				curUserCode = authOrg.getUserCode();
				//已存在 加入
				if(keyMap.contains(curUserCode)){
					orgList.add(authOrg.getOrgCode());
				} else { //第一次key 不存在
					if(i != 0){ //第一次 肯定不存在的  无需加入result中 因为还未开始						
						result.put(preUserCode, StringUtilsExt.join(orgList.toArray(), ",")); 
					}
					keyMap.add(curUserCode);
					preUserCode = curUserCode;
					orgList.clear();//清空
					orgList.add(authOrg.getOrgCode());//加入当次数据
				}
				i++;
			}
			//考虑最后一个分组时 循环已经结束
			result.put(curUserCode, StringUtilsExt.join(orgList.toArray(), ",")); 
		}
		return result;
	}

	@Override
	public Serializable get(Serializable key) {
		Condition condition = new Condition(DrcAuthOrg.class);
		condition.createCriteria().andEqualTo("status", 1).andEqualTo("userCode", key);
		List<DrcAuthOrg> data = drcAuthOrgMapper.selectByExample(condition);
		return buildDrcAuthOrg(data).get(key);
	}

	@Override
	public void put(Serializable key, Serializable value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable remove(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

}

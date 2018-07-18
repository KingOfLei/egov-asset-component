package com.bosssoft.egov.asset.aims.dictionary.impl.service;

import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBasicDict;
import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBasicDictItem;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
 *
 * @ClassName   类名：AssetBasciDictService 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月22日
 * @author      创建人：黄振雄
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年12月22日   黄振雄   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public interface AssetBasciDictService {

	/**
	 * <p>函数名称：addAssetBasciDict        </p>
	 * <p>功能说明：增加字典类型
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDict 字典信息
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> addAssetBasciDict(AssetBasicDict assetBasicDict);

	/**
	 * <p>函数名称：updateAssetBasicDict        </p>
	 * <p>功能说明：修改字典信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDict 字典信息
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> updateAssetBasicDict(AssetBasicDict assetBasicDict);

	/**
	 * <p>函数名称：delAssetBasicDict        </p>
	 * <p>功能说明：删除字典信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDict 字典信息
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> delAssetBasicDict(AssetBasicDict assetBasicDict);
	
	/**
	 * <p>函数名称：delDictItem        </p>
	 * <p>功能说明：删除字典项
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDictItem  字典项信息
	 * @return
	 *
	 * @date   创建时间：2017年11月2日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public Map<String, Object> delDictItem(AssetBasicDictItem assetBasicDictItem);
	/**
	 * <p>函数名称：queryOneById        </p>
	 * <p>功能说明：查询字典信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dictId
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public AssetBasicDict queryOneById(Long dictId);
	
	/**
	 * <p>函数名称： queryOneByItemId       </p>
	 * <p>功能说明： 查询字典项信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dictId  字典id
	 * @param itemId  字典项id
	 * @return
	 *
	 * @date   创建时间：2017年11月2日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public AssetBasicDictItem queryOneByItemId(Long dictId, Long itemId);
	/**
	 * <p>函数名称：getAssetBasicDictList        </p>
	 * <p>功能说明：获取数据字典树列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年12月22日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public List<AssetBasicDict> getAssetBasicDictList();
	
	/**
	 * <p>函数名称： queryDictItemPage       </p>
	 * <p>功能说明：获取字典项分页
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param searcher 查询条件
	 * @param pageInfo 分页信息
	 * @param dictId   字典ID
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Page<AssetBasicDictItem> queryDictItemPage(Searcher searcher, Page<AssetBasicDictItem> pageInfo,
			Long dictId);
	
	/**
	 * <p>函数名称：addDictItem        </p>
	 * <p>功能说明：新增字典项信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDictItem  字典项信息
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> addDictItem(AssetBasicDictItem assetBasicDictItem);
	
	/**
	 * <p>函数名称：updateDictItem        </p>
	 * <p>功能说明：修改字典项信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDictItem 字典项信息
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> updateDictItem(AssetBasicDictItem assetBasicDictItem);
	
	/**
	 * <p>函数名称：doEnableDictItem        </p>
	 * <p>功能说明：启用字典项(不使用)
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDictItem 字典项信息
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> doEnableDictItem(AssetBasicDictItem assetBasicDictItem);
	
	/**
	 * <p>函数名称：doDisableDictItem        </p>
	 * <p>功能说明：停用字典项(不使用)
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDictItem 字典项信息
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> doDisableDictItem(AssetBasicDictItem assetBasicDictItem);

	/**
	 * <p>函数名称：searchListByCode        </p>
	 * <p>功能说明：根据字典编码查询树列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dictCode 字典编码
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public List<AssetBasicDict> searchListByCode(String dictCode);
	
	/**
	 * <p>函数名称：  searchListByKey      </p>
	 * <p>功能说明：根据_key值匹配搜索
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param _key
	 * @return
	 *
	 * @date   创建时间：2017年10月30日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public List<AssetBasicDict> searchListByKey(String _key);
	
	/**
	 * <p>函数名称：getDictItemCode        </p>
	 * <p>功能说明：根据字典信息获取一个新的字典项编码
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDict     字典信息
	 * @param assetBasicDictItem 字典项信息
	 * @return
	 *
	 * @date   创建时间：2016年12月23日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> getDictItemCode(AssetBasicDict assetBasicDict, AssetBasicDictItem assetBasicDictItem);

	/**
	 * <p>函数名称： queryDictItemList       </p>
	 * <p>功能说明：获取字典项子级信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDictItem
	 * @return
	 *
	 * @date   创建时间：2017年11月2日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public List<AssetBasicDictItem> queryDictItemList(AssetBasicDictItem assetBasicDictItem);

	/**
	 * <p>函数名称：checkAddChild        </p>
	 * <p>功能说明：校验是否可以新增下级
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dictId 字典ID
	 * @param level  级数
	 * @return
	 *
	 * @date   创建时间：2016年12月24日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> checkAddChild(Long dictId,Integer level);

	/**
	 * <p>函数名称：isItemCodeUnique        </p>
	 * <p>功能说明：校验字典项编码唯一
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dictId   字典id
	 * @param itemCode 字典项编码
	 * @return
	 *
	 * @date   创建时间：2017年11月2日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public boolean isItemCodeUnique(Long dictId, String itemCode);
	
	/**
	 * <p>函数名称：  清除字典数据的redis缓存      </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param redisKey  redis的key值
	 *
	 * @date   创建时间：2017年11月1日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public void clearRedis(String redisKey);
	
	/**
	 * <p>函数名称：select        </p>
	 * <p>功能说明：查询字典项列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dictItem  字典项
	 * @return
	 *
	 * @date   创建时间：2017年11月2日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public List<AssetBasicDictItem> select(AssetBasicDictItem dictItem);
	
	/**
	 * <p>函数名称： updateParentItem       </p>
	 * <p>功能说明： 更新字典项父级
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetBasicDictItem
	 *
	 * @date   创建时间：2017年11月2日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public void updateParentItem(AssetBasicDictItem assetBasicDictItem);
}

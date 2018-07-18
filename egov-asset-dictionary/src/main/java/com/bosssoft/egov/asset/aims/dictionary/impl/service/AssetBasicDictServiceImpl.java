package com.bosssoft.egov.asset.aims.dictionary.impl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.common.util.OrderByUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBasicDict;
import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBasicDictItem;
import com.bosssoft.egov.asset.dictionary.impl.mapper.AssetBasicDictItemMapper;
import com.bosssoft.egov.asset.dictionary.impl.mapper.AssetBasicDictMapper;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.runtime.exception.BusinessException;

/** 
 *
 * @ClassName   类名：AssetBasicDictServiceImpl 
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
@Service
public class AssetBasicDictServiceImpl implements AssetBasciDictService{
	
	@Autowired
	private AssetBasicDictMapper assetBasicDictMapper;
	
	@Autowired
	private AssetBasicDictItemMapper assetBasicDictItemMapper;

	@Override
	public Map<String, Object> addAssetBasciDict(AssetBasicDict assetBasicDict) {
		Map<String, Object> result = new HashMap<String, Object>();
		//判断编码是否重复
		String dictCode = assetBasicDict.getDictCode();
		AssetBasicDict queryDict = new AssetBasicDict();
		queryDict.setDictCode(dictCode);
		List<AssetBasicDict> dicts = assetBasicDictMapper.select(queryDict);
		if(dicts != null && dicts.size() > 0){
			result.put("tag", false);
			result.put("msg", "字典编码【"+ dictCode +"】重复！");
			return result;
		}
		assetBasicDictMapper.insertSelective(assetBasicDict);
		this.clearRedis("EgovAssetDictCache");
		result.put("tag", true);
		result.put("msg", assetBasicDict);
		return result;
	}

	@Override
	public Map<String, Object> updateAssetBasicDict(AssetBasicDict assetBasicDict) {
		Map<String, Object> result = new HashMap<String, Object>();
		assetBasicDictMapper.updateById(assetBasicDict);
		this.clearRedis("EgovAssetDictCache");
		result.put("tag", true);
		result.put("msg", assetBasicDict);
		return result;
	}

	@Override
	public Map<String, Object> delAssetBasicDict(AssetBasicDict assetBasicDict) {
		Map<String, Object> result = new HashMap<String, Object>();
		Long dictId = assetBasicDict.getDictId();
		assetBasicDictMapper.deleteById(dictId);
		this.clearRedis("EgovAssetDictCache");
		result.put("tag", true);
		result.put("msg", assetBasicDict);
		return result;
	}

	@Override
	public List<AssetBasicDict> getAssetBasicDictList() {
		List<AssetBasicDict> dicts = assetBasicDictMapper.selectAll();
		return OrderByUtil.sort(dicts, "dictCode");
	}

	@Override
	public Page<AssetBasicDictItem> queryDictItemPage(Searcher searcher,
			Page<AssetBasicDictItem> pageInfo, Long dictId) {
		return assetBasicDictItemMapper.queryAssetBasicDictItemPage(searcher, pageInfo, dictId);
	}

	@Override
	public AssetBasicDict queryOneById(Long dictId) {
		AssetBasicDict assetBasicDict = new AssetBasicDict();
		assetBasicDict.setDictId(dictId);
		return assetBasicDictMapper.selectOne(assetBasicDict);
	}

	@Override
	public Map<String, Object> addDictItem(AssetBasicDictItem assetBasicDictItem) {
		Map<String, Object> result = new HashMap<String, Object>();
		//判断编码是否重复
		Long dictId = assetBasicDictItem.getDictId();
		String itemCode = assetBasicDictItem.getItemCode();
		if(this.isItemCodeUnique(dictId, itemCode) == false){
			result.put("tag", false);
			result.put("msg", "字典项编码【"+ itemCode +"】重复!");
			return result;
		}
		//校验编码
		String curCode = assetBasicDictItem.getItemCode();
		Integer curLevel = assetBasicDictItem.getItemLevel();
		String pCode = assetBasicDictItem.getItemPcode();
		result = this._checkCodeRule(dictId,curCode,curLevel,pCode);
		if(result.get("tag") == Boolean.FALSE){
			return result;
		}
		assetBasicDictItem.setItemIsleaf("1");
		assetBasicDictItem.setAppId("egov-asset-aims");
		assetBasicDictItem.setIsenable("1");
		assetBasicDictItemMapper.insertSelective(assetBasicDictItem);
		
		//查询父级  更新细级状态位
		this.updateParentItem(assetBasicDictItem);
		
		this.clearRedis("EgovAssetDictCache");
		result.put("tag", true);
		result.put("msg", assetBasicDictItem);
		return result;
	}

	@Override
	public Map<String, Object> updateDictItem(AssetBasicDictItem assetBasicDictItem) {
		Map<String, Object> result = new HashMap<String, Object>();
		Long dictId = assetBasicDictItem.getDictId();
		String curCode = assetBasicDictItem.getItemCode();
		Integer curLevel = assetBasicDictItem.getItemLevel();
		String pCode = assetBasicDictItem.getItemPcode();
		result = this._checkCodeRule(dictId,curCode,curLevel,pCode);
		if(result.get("tag") == Boolean.FALSE){
			return result;
		}
		assetBasicDictItemMapper.updateById(assetBasicDictItem);
		//更新父级  细级状态
		this.updateParentItem(assetBasicDictItem);
		
		this.clearRedis("EgovAssetDictCache");
		result.put("tag", true);
		result.put("msg", assetBasicDictItem);
		return result;
	}

	@Override
	public AssetBasicDictItem queryOneByItemId(Long dictId, Long itemId) {
		AssetBasicDictItem assetBasicDictItem = new AssetBasicDictItem();
		if(dictId != null && itemId != null){
			assetBasicDictItem.setDictId(dictId);
			assetBasicDictItem.setItemId(itemId);
			return assetBasicDictItemMapper.selectOne(assetBasicDictItem);
		}else{
			return null;
		}
	}

	@Override
	public Map<String, Object> delDictItem(AssetBasicDictItem assetBasicDictItem) {
		Map<String, Object> result = new HashMap<String, Object>();
		assetBasicDictItemMapper.deleteById(assetBasicDictItem);
		//查询父级  更新细级状态位
		this.updateParentItem(assetBasicDictItem);
		
		this.clearRedis("EgovAssetDictCache");
		result.put("tag", true);
		result.put("msg", assetBasicDictItem);
		return result;
	}

	@Override
	public Map<String, Object> doEnableDictItem(AssetBasicDictItem assetBasicDictItem) {
		Map<String, Object> result = new HashMap<String, Object>();
		assetBasicDictItem.setIsenable("1");
		assetBasicDictItemMapper.updateById(assetBasicDictItem);
		result.put("tag", true);
		result.put("msg", assetBasicDictItem);
		return result;
	}

	@Override
	public Map<String, Object> doDisableDictItem(AssetBasicDictItem assetBasicDictItem) {
		Map<String, Object> result = new HashMap<String, Object>();
		assetBasicDictItem.setIsenable("0");
		assetBasicDictItemMapper.updateById(assetBasicDictItem);
		result.put("tag", true);
		result.put("msg", assetBasicDictItem);
		return result;
	}

	@Override
	public List<AssetBasicDict> searchListByCode(String dictCode) {
		AssetBasicDict assetBasicDict = new AssetBasicDict();
		assetBasicDict.setDictCode(dictCode);
		return assetBasicDictMapper.select(assetBasicDict);
	}

	@Override
	public Map<String, Object> getDictItemCode(AssetBasicDict assetBasicDict, AssetBasicDictItem assetBasicDictItem) {
		Map<String, Object> result = new HashMap<String, Object>();
		AssetBasicDictItem dictItem = new AssetBasicDictItem();
		Long dictId = assetBasicDict.getDictId();
		//编码规则
		String codeRule = assetBasicDict.getCodeRule();
		dictItem.setAppId(assetBasicDict.getAppId());
		dictItem.setDictId(assetBasicDict.getDictId());
		dictItem.setDictCode(assetBasicDict.getDictCode());
		dictItem.setItemIsleaf("1");
		dictItem.setIsenable("1");
		if(assetBasicDictItem == null){
			Integer level = new Integer(1);
			String itemCode = "";
			//如果编码规则不为空，则根据编码规则获取字典项编码
			if(StringUtilsExt.isNotBlank(codeRule)){
				itemCode = this._queryNextCode(dictId, null, level);
			}
			dictItem.setItemLevel(1);
			dictItem.setItemCode(itemCode);
		}else {
			Long itemPid = assetBasicDictItem.getItemId();
			String itemCode = "";
			Integer level = new Integer(1);
			dictItem.setItemPid(assetBasicDictItem.getItemId());
			dictItem.setItemPcode(assetBasicDictItem.getItemCode());
			
			if(assetBasicDictItem.getItemLevel() != null){
				level = assetBasicDictItem.getItemLevel() + 1;
			}
			if(StringUtilsExt.isNotBlank(codeRule)){
				itemCode = this._queryNextCode(dictId, itemPid, level);
			}
			dictItem.setItemLevel(level);
			dictItem.setItemCode(itemCode);
		}
		result.put("tag",true);
		result.put("msg", dictItem);
		return result;
	}
	
	
	/**
	 * <p>函数名称：_queryNextCode        </p>
	 * <p>功能说明：获取新的字典项编码
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dictId 字典ID
	 * @param pid    字典项ID 
	 * @param level  级数
	 * @return
	 *
	 * @date   创建时间：2016年12月24日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	private String _queryNextCode(Long dictId, Long pid,Integer level) {
		AssetBasicDict dict = this.queryOneById(dictId);
		String itemCode = "";
		int curLen = 0;
		if(dict != null){
			String codeRule = StringUtilsExt.convertNullToString(dict.getCodeRule());
			int codeRuleLength = codeRule.length();
			if(level != null){
				if(level.intValue() > codeRuleLength){
					throw new BusinessException("ERR_01", "根据字典编码规则仅支持新增"+ codeRuleLength +"级字典项");
				}else{
					curLen = Integer.parseInt(codeRule.substring(level-1, level));
				}
			}
		}
		
		AssetBasicDictItem dictItem = new AssetBasicDictItem();
		dictItem.setDictId(dictId);
		if(pid == null){
			dictItem.setItemLevel(1);
		}else{
			dictItem.setItemPid(pid);
		}
		//查询最大编码
		List<AssetBasicDictItem> items = OrderByUtil.sort(this.queryDictItemList(dictItem),"itemCode desc");
		if(items.isEmpty()){
			//dictItem = this.queryOneByItemId(pid);
			dictItem = this.queryOneByItemId(dictId, pid);
			if(dictItem != null){
				String itemPcode = dictItem.getItemCode();
				itemCode = itemPcode + StringUtilsExt.lPad("1", "0", curLen);
			}else{
				//itemCode = "1";
				itemCode = StringUtilsExt.lPad("1", "0", curLen);
			}
			
		}else{
			AssetBasicDictItem res = items.get(0);
			
			if (res.getItemPid() == null) {
				itemCode = StringUtilsExt.lPad(String.valueOf(Integer.valueOf(res.getItemCode()) + 1), "0", curLen);
			} else {
				// 得到上级编码
				//AssetBasicDictItem parent = this.queryOneByItemId(res.getItemPid());
				AssetBasicDictItem parent = this.queryOneByItemId(dictId, res.getItemPid());
				itemCode = StringUtilsExt.lPad(String.valueOf(Integer.valueOf(res.getItemCode().substring(
					parent.getItemCode().length(),res.getItemCode().length())) + 1), "0",curLen);
				itemCode = parent.getItemCode() + itemCode;
			}
		}
		return itemCode;
	}
	
	/**
	 * <p>函数名称：_checkCodeRule        </p>
	 * <p>功能说明：校验编码合法性
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dictId
	 * @param curCode
	 * @param curLevel
	 * @param pCode
	 * @return
	 *
	 * @date   创建时间：2016年12月24日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	private Map<String, Object> _checkCodeRule(Long dictId, String curCode, Integer curLevel, String pCode){
		Map<String, Object> result = new HashMap<String, Object>();
		if(dictId == null){
			result.put("tag", false);
			result.put("msg", "数据字典分类Id不能为空!");
			return result;
		}else if(StringUtilsExt.isEmpty(curCode)){
			result.put("tag", false);
			result.put("msg", "编码不能为空!");
			return result;
		}else if(curLevel == null){
			result.put("tag", false);
			result.put("msg", "级次不能为空!");
			return result;
		}
		//取编码规则
		AssetBasicDict dict = this.queryOneById(dictId);
		if(StringUtilsExt.isEmpty(dict.getCodeRule())){
			/*result.put("tag", false);
			result.put("msg", "编码规则不能为空,请先设定编码规则!");
			return result;*/
			result.put("tag", true);
			result.put("msg", "不进行编码校验！");
			return result;
		}
		//父级编码长度
		int pLen = StringUtilsExt.isEmpty(pCode)? 0: pCode.length();
		
		//根据规则取本级编码的规定长度
		int curLen = Integer.parseInt(dict.getCodeRule().substring(curLevel-1, curLevel));
		//判断实际传来的编码 是否等于 pLen+curLen
		if(curCode.length() != (pLen+curLen)){
			result.put("tag", false);
			result.put("msg", "编码不符合规定长度【"+(pLen+curLen)+"】,请修改!");
			return result;
		}
		if(pLen != 0 && !curCode.substring(0, pLen).equals(pCode)){
			result.put("tag", false);
			result.put("msg", "编码前"+pLen+"位，必须与父级编码相同!");
			return result;
		}
		result.put("tag", true);
		result.put("msg", "编码符合规则!");
		return result;
	}
	
	public Map<String, Object> checkAddChild(Long dictId,Integer level){
		Map<String, Object> result = new HashMap<String, Object>();
		AssetBasicDict dict = this.queryOneById(dictId);
		if(dict == null){
			result.put("tag", false);
			result.put("msg","字典类型不存在，请刷新后重试!");
		}else if(dict.getCodeRule() == null){
			result.put("tag", false);
			result.put("msg","请先定义编码规则!");
		}else if(level > dict.getCodeRule().length()){
			result.put("tag", false);
			result.put("msg","系统不支持"+level+"级字典条目!");
		}else{
			result.put("tag", true);
			result.put("msg","可以新增下级字典项!");
		}
		return result;
	}

	@Override
	public List<AssetBasicDictItem> queryDictItemList(AssetBasicDictItem assetBasicDictItem) {
		return assetBasicDictItemMapper.queryDictItemList(assetBasicDictItem);
	}

	@Override
	public boolean isItemCodeUnique(Long dictId, String itemCode) {
		AssetBasicDictItem assetBasicDictItem = new AssetBasicDictItem();
		assetBasicDictItem.setDictId(dictId);
		assetBasicDictItem.setItemCode(itemCode);
		List<AssetBasicDictItem> dictItem = assetBasicDictItemMapper.select(assetBasicDictItem);
		if(dictItem != null && dictItem.size() > 0){
			return false;
		}
		return true;
	}

	@Override
	public List<AssetBasicDict> searchListByKey(String _key) {
		return assetBasicDictMapper.searchListByKey("%"+_key+"%");
	}

	@Override
	public void clearRedis(String redisKey) {
		//EgovAssetDictCache
		CacheFactory.getInstance().findCache(redisKey).clear();
	}

	@Override
	public List<AssetBasicDictItem> select(AssetBasicDictItem dictItem) {
		List<AssetBasicDictItem> items = assetBasicDictItemMapper.select(dictItem);
		return items;
	}

	@Override
	public void updateParentItem(AssetBasicDictItem assetBasicDictItem) {
		Long dictId = assetBasicDictItem.getDictId();
		Long itemPid = assetBasicDictItem.getItemPid();
		if(dictId != null && itemPid != null){
			AssetBasicDictItem parentItem = this.queryOneByItemId(dictId, itemPid);
			if(parentItem != null){
				//查询是否有子级字典项
				AssetBasicDictItem queryItem = new AssetBasicDictItem();
				queryItem.setItemPid(itemPid);
				List<AssetBasicDictItem> items = this.select(queryItem);
				if(items != null && items.size() > 0){
					parentItem.setItemIsleaf("0");
				}else{
					parentItem.setItemIsleaf("1");
				}
				assetBasicDictItemMapper.updateById(parentItem);
			}
		}
		
	}
	
	
}

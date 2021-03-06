package com.bosssoft.egov.asset.card.service.basic;
/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Wed May 31 13:53:48 CST 2017
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.attach.service.AttachmentService;
import com.bosssoft.egov.asset.attach.web.AtttachHelper;
import com.bosssoft.egov.asset.card.entity.basic.AssetAttachCardPic;
import com.bosssoft.egov.asset.card.mapper.basic.AssetAttachCardPicMapper;
import com.bosssoft.egov.asset.common.PropertiesHelper;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.persistence.entity.Condition;
import com.bosssoft.platform.persistence.entity.Example;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;

import net.coobird.thumbnailator.Thumbnails;


/**
 * 类说明: AssetAttachCardPicService接口实现类. 
 *
 * 类用途：
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-05-31   Administrator　　　新建
 * </pre>
 */
@Service("AssetAttachCardPicService")
public class AssetAttachCardPicServiceImpl implements AssetAttachCardPicService {

	private static Logger logger = LoggerFactory.getLogger(AssetAttachCardPicServiceImpl.class);
	
	@Autowired
	private AssetAttachCardPicMapper assetAttachCardPicMapper;
	
	@Value("${defaultAttachService}")
	private String attachmentServiceName;
	
	private AttachmentService getAttachmentService(String type){
		return RuntimeApplicationContext.getBean(type);
	};
	
	/**
	 *
	 * @param assetAttachCardPic
	 */
	public void addAssetAttachCardPic(AssetAttachCardPic assetAttachCardPic)  {
		
		assetAttachCardPicMapper.insertSelective(assetAttachCardPic);
	}

	/**
	 *
	 * @param assetAttachCardPic
	 */
	public void delAssetAttachCardPic(AssetAttachCardPic assetAttachCardPic)  {
		Condition condition = new Condition(AssetAttachCardPic.class);
		condition.createCriteria().andCondition("BIZ_ID = "+ assetAttachCardPic.getBizId())
								  .andCondition("ATTACH_ID = "+assetAttachCardPic.getAttachId());
		
		assetAttachCardPicMapper.deleteByExample(condition);
	}

	/**
	 *
	 * @param assetAttachCardPic
	 */
	public void updateAssetAttachCardPic(AssetAttachCardPic assetAttachCardPic)  {
		assetAttachCardPicMapper.updateByPrimaryKey(assetAttachCardPic);
	}

	/**
	 *
	 * @param assetAttachCardPic
	 * @retrun
	 */
	public List<AssetAttachCardPic> getAssetAttachCardPicList(AssetAttachCardPic assetAttachCardPic)  {
		return assetAttachCardPicMapper.getAssetAttachCardPicList(assetAttachCardPic);
	}
	/**
	 *
	 * @param assetAttachCardPic
	 * @retrun
	 */
	public List<AssetAttachCardPic> getPreAssetCardPicList(AssetAttachCardPic assetAttachCardPic)  {
		return assetAttachCardPicMapper.getPreAssetCardPicList(assetAttachCardPic);
	}

	/**
	 * 
	 */
	public AssetAttachCardPic queryOneCardPicFile(AssetAttachCardPic assetAttachCardPic){
		return assetAttachCardPicMapper.queryOneCardPicFile(assetAttachCardPic);
	}
	
	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<AssetAttachCardPic> queryAssetAttachCardPicPage(Searcher searcher, Page<AssetAttachCardPic> page)  {
		return assetAttachCardPicMapper.queryAssetAttachCardPicPage(searcher, page,"");
	}
	
	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<AssetAttachCardPic> queryAssetAttachCardPicPage(Searcher searcher, Page<AssetAttachCardPic> page,String condition)  {
		return assetAttachCardPicMapper.queryAssetAttachCardPicPage(searcher, page, condition);
	}

	public void saveCardPic(AssetAttachCardPic picInfo,
			InputStream is,byte[] bytes) {
			
			if(picInfo.getFileSize() == 0){
				try {
					picInfo.setFileSize(NumberUtilsExt.toLong(is.available()+""));
				} catch (IOException e) {
				}
			}
			//获取上传类型
			String uploadType = getUploadType();
			picInfo.setUploadType(uploadType);
			

			//文件上传 
			String filePath = getAttachmentService(uploadType).upload(StringUtilsExt.toString(picInfo.getAttachId()),is, picInfo.getBizType(), picInfo.getContentType());			
			//插入
			picInfo.setFilePath(filePath);
			
			//生成缩略图
				//String thumb = PathUtil.getTempDirectory("okongThumb") + GUID.newGUID() + ".thumb";
				//CommonFileUtilsExt.copyInputStreamToFile(new ByteArrayInputStream(bytes),thumb);			
			picInfo.setThumbPath(createThumb(new ByteArrayInputStream(bytes),picInfo));
			assetAttachCardPicMapper.insertSelective(picInfo);

		
	}

	@Override
	public InputStream downloadPic(AssetAttachCardPic cardPic) {
		 return downloadPic(cardPic, false);
	}
	
	@Override
	public InputStream downloadPic(AssetAttachCardPic cardPic,boolean thumb) {
		
	        String pathFile = cardPic.getFilePath();	      
	        if(thumb){
	        	pathFile = cardPic.getThumbPath();
	        }
	        InputStream dowload = getAttachmentService(getUploadType()).dowload(pathFile);
	        if(thumb && dowload == null){
	        	//缩略图情况下 默认情况下丢失 新增下
	        	//获取原图
	        	InputStream img = getAttachmentService(getUploadType()).dowload(cardPic.getFilePath());
	        	if(img == null) return null;
	        	ByteArrayOutputStream output = new ByteArrayOutputStream();
	   	        AssetAttachCardPic upPic = new AssetAttachCardPic();
	        	upPic.setAttachId(cardPic.getAttachId());
	        	upPic.setThumbPath(createThumb(img,cardPic,output));
	        	assetAttachCardPicMapper.updateByPrimaryKeySelective(upPic);
	        	return new ByteArrayInputStream(output.toByteArray());
	        }
	        return dowload;
	}

	@Override
	public String filePath() {
		
		String filePath = getAttachmentService(getUploadType()).getPath();
        
        return filePath;
}

	@Override
	public void doBatchDel(List<AssetAttachCardPic> cardPicList) {
		
		 assetAttachCardPicMapper.doBatchDel(cardPicList);
		
	}
	
	private String getUploadType(){
		return StringUtilsExt.isEmpty(attachmentServiceName, AtttachHelper.UPLOAD_TYPE_LOCALHOST);
	}
	
	private String createThumb(InputStream input,AssetAttachCardPic picInfo){
		return createThumb(input,picInfo,new ByteArrayOutputStream());
	}
	
	private String createThumb(InputStream input,AssetAttachCardPic picInfo,ByteArrayOutputStream output){
		try {
			//判断缩略图大小 小于约定值 不进行压缩
			if(PropertiesHelper.getLong("default.size",1024*20l).compareTo(picInfo.getFileSize()) > 0){
				return picInfo.getFilePath();
			}	
			Thumbnails.of(input).scale(PropertiesHelper.getDouble("default.scale",0.5d)).outputQuality(PropertiesHelper.getDouble("defaultPic.quality", 1d)).toOutputStream(output);
			String thumbPath = getAttachmentService(getUploadType()).upload(StringUtilsExt.toString(picInfo.getAttachId()),new ByteArrayInputStream(output.toByteArray()), picInfo.getBizType(), picInfo.getContentType());
            return thumbPath;
		} catch (IOException e) {
			logger.debug("Create thumb pic Exception!!!", e);
		}
		
		return "";
	}

	/**
	 * 删除卡片对应图片
	 */
	@Override
	public void updatePicStatus(AssetAttachCardPic cardPic,boolean del) {
		//del: true 物理删除 ; false 逻辑删除 
		if (del) {
			Condition condition = new Condition(AssetAttachCardPic.class);
			condition.createCriteria().andCondition("BIZ_ID = ",cardPic.getBizId());
			assetAttachCardPicMapper.deleteByExample(condition);
		}else{
			assetAttachCardPicMapper.updatePicStatus(cardPic);
		}
		
	}

	@Override
	public List<AssetAttachCardPic> queryCardPicByIds(List<String> ids) {
		List<AssetAttachCardPic> picList = new ArrayList<AssetAttachCardPic>();
		if (ids != null && ids.size() > 0) {
			for(String id : ids){
				AssetAttachCardPic cardPic = new AssetAttachCardPic();
				cardPic.setBizId(id);
				List<AssetAttachCardPic> cardPics = assetAttachCardPicMapper.getAssetAttachCardPicList(cardPic);
				if (cardPics != null && cardPics.size() > 0) {
					
					picList.addAll(cardPics);
				}
			}
		}
		return picList;
	}

	@Override
	public void updateRemark(AssetAttachCardPic cardPic) {
		
		Example example = new Example(AssetAttachCardPic.class);
		example.createCriteria().andCondition("BIZ_ID = "+cardPic.getBizId()).andCondition("ATTACH_ID = "+cardPic.getAttachId());
		assetAttachCardPicMapper.updateByExampleSelective(cardPic, example);
		
	}

	/**
	 * 修改图片业务状态
	 */
	public void updateBizStatus(AssetAttachCardPic cardPic){
		Example example = new Example(AssetAttachCardPic.class);
		example.createCriteria().andCondition("BIZ_ID = "+cardPic.getBizId());
		assetAttachCardPicMapper.updateByExampleSelective(cardPic, example);
	}
	/**
	 * 
	 * <p>函数名称：  confirmPics      </p>
	 * <p>功能说明：	保存图片的业务逻辑操作 图片上传或删除时bizStatus均为暂存状态(临时状态) 1，
	 * 				只有卡片或单据保存了才能更改图片bizStatus为最终状态 999999
	 *
	 * </p>
	 *<p>参数说明：卡片Id</p>
	 * @param assetId
	 *
	 * @date   创建时间：2018年2月27日
	 * @author 作者：cyx
	 */
	public void confirmPics(String bizId,Integer bizStatus,String bizStatusName) {
		AssetAttachCardPic cardPic = new AssetAttachCardPic();
		cardPic.setBizId(bizId);
		cardPic.setBizStatus(bizStatus);
		cardPic.setBizStatusName(bizStatusName);
		assetAttachCardPicMapper.updateBizStatus(cardPic);
	}
	@Override
	public void revertUnconfirmPics(String bizId) {
		//cyx-20180228 删除未确认保存的图片及恢复未确认删除的图片
		Example example = new Example(AssetAttachCardPic.class);
		example.createCriteria().andCondition("BIZ_ID = "+ bizId)
		.andCondition(" BIZ_STATUS = 1 AND STATUS = 1 ");
		assetAttachCardPicMapper.deleteByExample(example);
		AssetAttachCardPic cardPic = new AssetAttachCardPic();
		cardPic.setBizId(bizId);
		cardPic.setStatus(1);
		cardPic.setBizStatus(999999);
		cardPic.setBizStatusName("已入账");
		Example exp = new Example(AssetAttachCardPic.class);
		exp.createCriteria().andCondition("BIZ_ID = "+ bizId)
		.andCondition(" BIZ_STATUS = 1 AND STATUS = 0 ");
		assetAttachCardPicMapper.updateByExampleSelective(cardPic, exp);
		
	}
}
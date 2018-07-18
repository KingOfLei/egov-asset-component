package com.bosssoft.asset.etl.comp.handler;

import org.pentaho.di.trans.step.StepMetaInterface;

import com.bosssoft.asset.etl.entity.AssetEtlConfig;
import com.bosssoft.platform.common.extension.SPI;

/** 
*
* @ClassName   类名：Handler 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月13日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月13日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@SPI
public interface Handler {
   
	/**
	 * 
	 * <p>函数名称：  getHandler      </p>
	 * <p>功能说明： 返回当前类型
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public HandlerType getHandler();
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode
	 * @return
	 *
	 * @date   创建时间：2017年9月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public StepMetaInterface getStepMeta(Long configId);
	
	public void setEtlConfig(AssetEtlConfig config);
}

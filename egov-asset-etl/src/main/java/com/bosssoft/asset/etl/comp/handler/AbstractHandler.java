package com.bosssoft.asset.etl.comp.handler;

import org.pentaho.di.trans.step.StepMetaInterface;

import com.bosssoft.asset.etl.entity.AssetEtlConfig;

/** 
*
* @ClassName   类名：AbstractHandler 
* @Description 功能说明：抽象处理类
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
public abstract class AbstractHandler implements Handler{

	protected AssetEtlConfig etlConfig;
	
	@Override
	public void setEtlConfig(AssetEtlConfig config) {
		this.etlConfig = config;		
	}
	
	@Override
	public StepMetaInterface getStepMeta(Long configId) {	
		StepMetaInterface stepMeta = createStepMeta(configId);
		return (stepMeta == null ) ? getDefaultStepMeta() : stepMeta;
	}
	
	protected StepMetaInterface getDefaultStepMeta(){
		return HandlerType.DUMMY.getHandler().getStepMeta(null);
	};
	
	public abstract StepMetaInterface createStepMeta(Long configId);
}

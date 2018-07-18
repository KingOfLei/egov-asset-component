package com.bosssoft.asset.etl.comp.handler;

import org.pentaho.di.trans.step.StepMetaInterface;

import com.bosssoft.platform.common.extension.Activate;

/** 
*
* @ClassName   类名：XlsHandler 
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
@Activate
public class XlsHandler extends AbstractHandler implements Handler{

	@Override
	public HandlerType getHandler() {
		// TODO Auto-generated method stub
		return HandlerType.XLS;
	}

	@Override
	public StepMetaInterface createStepMeta(Long configId) {
		// TODO Auto-generated method stub
		return null;
	}

}

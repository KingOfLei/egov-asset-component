package com.bosssoft.asset.etl.comp.handler;

import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.dummytrans.DummyTransMeta;

import com.bosssoft.platform.common.extension.Activate;

/** 
*
* @ClassName   类名：DummyHandler 
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
public class DummyHandler extends AbstractHandler{

	@Override
	public HandlerType getHandler() {
		return HandlerType.DUMMY;
	}

	@Override
	public StepMetaInterface createStepMeta(Long configId) {
		DummyTransMeta dummy = new DummyTransMeta();
		dummy.setDefault();
		return dummy;
	}

}

package com.bosssoft.egov.asset.basic;

import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.runtime.web.response.AjaxResult;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.bosssoft.egov.asset.runtime.User;

/** 
*
* @ClassName   类名：BaseBillController 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年6月29日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年6月29日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public abstract class BaseBillController<T> extends BaseController {
	
	/**
	 * 
	 * <p>函数名称：showIndex        </p>
	 * <p>功能说明：列表页
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年6月29日
	 * @author 作者：xds
	 */
	public abstract String showIndex(Model model);
	
	/**
	 * 
	 * <p>函数名称：showAdd        </p>
	 * <p>功能说明：增加页
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年6月29日
	 * @author 作者：xds
	 */
	public abstract String showAdd(Model model);
	/**
	 * 
	 * <p>函数名称：showEdit        </p>
	 * <p>功能说明：修改页
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年6月29日
	 * @author 作者：xds
	 */
	public abstract String showEdit(Model model);
	/**
	 * 
	 * <p>函数名称： doInsert     </p>
	 * <p>功能说明： 新增操作 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param prjs
	 * @return
	 *
	 * @date   创建时间：2017年6月29日
	 * @author 作者：xds
	 */
	public  AjaxResult doInsert(List<T> prjs){
		return null;
	}
	/**
	 * 
	 * <p>函数名称： doUpdate     </p>
	 * <p>功能说明： 修改操作 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param prjs
	 * @return
	 *
	 * @date   创建时间：2017年6月29日
	 * @author 作者：xds
	 */
	public abstract AjaxResult doUpdate(List<T> prjs);
	/**
	 * 
	 * <p>函数名称： doDelete     </p>
	 * <p>功能说明： 删除操作 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param prjs
	 * @return
	 *
	 * @date   创建时间：2017年6月29日
	 * @author 作者：xds
	 */
	public abstract AjaxResult doDelete(List<T> prjs);
	/**
	 * 
	 * <p>函数名称：doVerify        </p>
	 * <p>功能说明： 校验操作
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param prjs
	 * @return
	 *
	 * @date   创建时间：2017年6月29日
	 * @author 作者：xds
	 */
	public abstract boolean doVerify(List<T> prjs);

}

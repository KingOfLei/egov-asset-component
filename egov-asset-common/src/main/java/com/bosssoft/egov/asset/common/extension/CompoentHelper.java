package com.bosssoft.egov.asset.common.extension;

import java.util.ArrayList;
import java.util.List;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.lang.DataType;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.common.lang.expression.ChainLogicExpression;
import com.bosssoft.platform.common.lang.expression.Expression;
import com.bosssoft.platform.common.lang.expression.Operator;
import com.bosssoft.platform.common.lang.expression.ValueExpression;

/** 
*
* @ClassName   类名：CompoentHelper 
* @Description 功能说明：平台组件帮助类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月16日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月16日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CompoentHelper {
   
	/**
	 * 
	 * <p>函数名称：   xQueryMultipleConverter     </p>
	 * <p>功能说明： searcerh类 多选转换
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param searcher
	 * @param multipleField
	 * @param separatorChar
	 * @return
	 *
	 * @date   创建时间：2017年12月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static Searcher xQueryMultipleConverter(Searcher searcher,String multipleField,Character separatorChar){
		List<Expression> list = new ArrayList<Expression>();
		for(Expression exp : searcher.getConditions()) {
			if(Expression.TYPE_CHAINLOGIC.equals(exp.getType())){
				list.add(exp);
			} else if (Expression.TYPE_LOGIC.equals(exp.getType())){
				list.add(exp);
			} else if(Expression.TYPE_SIMPLE.equals(exp.getType())){
				ValueExpression expValue = (ValueExpression) exp;
				if(expValue.getPropertyName().equalsIgnoreCase(multipleField)){
					DataType dataType = expValue.getDataType();
					Operator operator = expValue.getOperator();
					String propertyName = expValue.getPropertyName();
					Object value = expValue.getValue();
					ChainLogicExpression chainLogicExpression = new ChainLogicExpression();
					chainLogicExpression.setOr(true);
					for(String val : StringUtilsExt.split(StringUtilsExt.toString(value), separatorChar)){
						chainLogicExpression.addExpression(new ValueExpression(propertyName, val, dataType, operator));
					}
					list.add(chainLogicExpression);
				} else {
					list.add(exp);
				}
			}
		}
		searcher.setConditions(list);
		return searcher;
	}
}

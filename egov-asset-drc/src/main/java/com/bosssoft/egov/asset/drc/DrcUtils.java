package com.bosssoft.egov.asset.drc;

import java.util.List;

import com.bosssoft.egov.asset.drc.entity.DrcAuthRule;
import com.bosssoft.egov.asset.drc.entity.Operator;

/** 
*
* @ClassName   类名：DrcUtils 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月10日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月10日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class DrcUtils {
	
	/**
	 * 
	 * <p>函数名称：   buildRuleSql     </p>
	 * <p>功能说明： 拼凑条件
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param ruleList
	 * @return
	 *
	 * @date   创建时间：2017年1月10日
	 * @author 作者：xds
	 */
	public static String buildRuleSql(List<DrcAuthRule> ruleList){
		StringBuffer sql = new StringBuffer();
		for(DrcAuthRule authRule : ruleList){
			String ruleField = authRule.getRuleField();
			String ruleValue = authRule.getRuleValue();
			Operator operator = Operator.parse(authRule.getRuleOperator());
			if(sql.length() > 0){
				sql.append(" and ");
			}
			switch (operator) {
			case NOTEQUAL:
				sql.append(ruleField + "!="+ruleValue);
				break;
				
			case GREATER:
				sql.append(ruleField + ">"+ruleValue);
				break;
				
			case LESS:
				sql.append(ruleField + "<"+ruleValue);				
				break;
			
			case GREATEREQUAL:
				sql.append(ruleField + ">="+ruleValue);								
				break;
				
			case LESSEQAUL:
				sql.append(ruleField + "<="+ruleValue);												
				break;
			case LIKEANY:
				sql.append(ruleField + " like '%"+ruleValue + "%'");												
                break;
			case LIKESTART:
				sql.append(ruleField + " like '"+ruleValue + "%'");												
                break;
			case LIKEEND:
				sql.append(ruleField + " like '%"+ruleValue + "'");												
                break;
			case NOTLIKEANY:
				sql.append(ruleField + " not like '%"+ruleValue + "%'");												
                break;
			case ISNULL:
				sql.append(ruleField + " is null ");												
                break;
			case ISNOTNULL:
				sql.append(ruleField + " is not null ");												
                break;
			case IN:{
				sql.append(ruleField + " in ( " + ruleValue +  ")");												
                break;
			}
			default:
				sql.append(ruleField + "="+ruleValue);
			}
	
		}
		if(sql.length() == 0 ){
			sql.append(" 1 = 1");
		}
		return sql.toString();
	}
	
	/**
	 * 
	 * <p>函数名称： buildRuleInSql       </p>
	 * <p>功能说明：拼凑值为in形式
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param ruleList
	 * @return
	 *
	 * @date   创建时间：2017年1月10日
	 * @author 作者：xds
	 */
	public static String buildRuleInSql(List<DrcAuthRule> ruleList){
		StringBuffer sql = new StringBuffer();
		for(DrcAuthRule authRule : ruleList){
			String ruleValue = authRule.getRuleValue();
			if(sql.length() > 0){
				sql.append(",");
			}
			sql.append("'").append(ruleValue).append("'");
		}		
		return sql.toString();
	}
}

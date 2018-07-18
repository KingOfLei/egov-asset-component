package com.bosssoft.egov.asset.di.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.di.entity.AssetDiTaskOrgCompare;
import com.bosssoft.egov.asset.mapper.AssetGeneralMapper;

/** 
*
* @ClassName   类名：AssetDiServiceImpl 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2018年1月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2018年1月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service
public class AssetDiServiceImpl implements AssetDiService {

	@Autowired
	private AssetGeneralMapper generalMapper;
	/**
	 * 
	 * <p>函数名称：  bussinessProcess      </p>
	 * <p>功能说明： 处理或删除业务表数据
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param taskId
	 * @param taskType
	 * @param reportQj
	 *
	 * @date   创建时间：2018年1月20日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	@Override
	public void bussinessProcess(String taskId,String taskType,String reportQj,List<AssetDiTaskOrgCompare> orgCompareList){
		StringBuffer sql = new StringBuffer();
        Map<String, Object>  params = new HashMap<String, Object>();
        params.put("taskId", taskId);
        params.put("taskType", taskType);
        sql.delete(0, sql.length());
        sql.append("SELECT T.* FROM ASSET_DI_TASK_BUS_TABLE T WHERE T.AUXILIARY IN(");
        sql.append("SELECT F.F_SRC_AUXILIARY FROM ASSET_DI_TASK_DETAILS F WHERE F.F_TASK_ID=#{params.taskId}");
        sql.append(" AND F.F_TASK_TYPE IN (" +  taskType + ") ");
		sql.append(" AND F.F_STATE='1' ");
		sql.append(" AND F.F_DEAL_TYPE='0')");
		sql.append(" ORDER BY TO_NUMBER(T.SXH)");
		
        List<Map<String,Object>> data = generalMapper.queryCommon(sql.toString(), params);
        if(data != null){
        	for(Map<String,Object> mapData : data){
        	    String key = MapUtilsExt.getString(mapData, "EXTSTS_FIELD");
        	    String tableName = MapUtilsExt.getString(mapData, "TABLE_NAME");
        	    String delSql = MapUtilsExt.getString(mapData, "DEL_SQL");	        	
	        	StringBuffer idStr = new StringBuffer();
	        	for(int i = 0,size = orgCompareList.size(); i < size ; i++){
	        		AssetDiTaskOrgCompare diTaskOrgCompare = orgCompareList.get(i);
	        		idStr.append("'").append(diTaskOrgCompare.getDestOrgId()).append("'");
	        		if ( ( i !=0 && i % 100 == 0) || i == size - 1 ){
	            		if (key == null || key.equals("")){
		            		//语句删除
	            			String tmp = delSql.replaceAll("\\__ORG_ID__", idStr.toString());
	            			tmp = tmp.replaceAll("\\__REPORTQJ__", reportQj);
	            			sql.delete(0, sql.length());
	            			sql.append(tmp);
	            		}else{
	            			sql.delete(0, sql.length());
	            			sql.append(" DELETE FROM ").append(tableName).append(" T ");
	            			sql.append(" WHERE T.").append(key).append(" IN (" + idStr + ")");
	            		}
	            		
	            		String[] sqls = sql.toString().split("__SEPARATOR__");
	            		for(String s : sqls){
	            			generalMapper.commonUpdate(StringUtilsExt.trim(s), params);
	            		}
	            		idStr.delete(0, idStr.length());
	   			    } else {
		  				idStr.append(",");
	  			  }//if else
	        	}
        	}
        }
	}

}

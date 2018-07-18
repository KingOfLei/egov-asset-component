package com.bosssoft.egov.asset.codegen;
/** 
*
* @ClassName   类名：CurrentStatus 
* @Description 功能说明：当前状态 即 新增状态 还是新增保存状态
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public enum CurrentStatus {
	    ADD("ADD","新增"),
	    ADD2SAVE("SAVE","新增保存");
	    
		String code;
		String name;
		
		private CurrentStatus(String code, String name){
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.getCode() + this.getName();
		}
}

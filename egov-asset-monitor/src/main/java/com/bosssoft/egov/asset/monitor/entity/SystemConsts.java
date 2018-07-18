package com.bosssoft.egov.asset.monitor.entity;
/** 
 *
 * @ClassName   类名：SystemConsts 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2017年1月18日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2017年1月18日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public interface SystemConsts {

	/**
	 * 初始密码
	 */

	String DEF_PASSWORD = "111111";//用户初始密码
	
	
	String SESSION_USER ="user";
	
	/**
	 * 表示是否的常量  0 否  1 是
	 */
	String YES = "1";
	
	String NO = "0";
	
	//表示是否的常量  0 否  1 是
	int INT_NO = 0;
	
	int INT_YES = 1;
	
	/**
	 * 审核状态  0 未审核  1 审核通过 2审核退回
	 */
	String AUDIT_STATUS_UNCHECK = "0";
	
	String AUDIT_STATUS_PASS = "1";
	
	String AUDIT_STATUS_BACK = "2";
	
	/**
	 * 默认用户名后缀
	 */
	String DEFAULT_USERCODE_POSTFIX = "000";

	/**
	 * 默认名称
	 */
	String DEFAULT_USERNAME_FINANCE = "管理员";
	/**
	 * 默认性别
	 */
	String DEFAULT_USERSEX = "0";

	/**
	 * 初始化机构用户  
	 */
	String INI_USER_ORG = "0";

	/**
	 * 初始化区划用户 
	 */
	String INI_USER_REGION = "1";

	/**
	 * 操作类型 0 增加 1 修改 2删除 3 审核 4撤销审核 5 重置密码 6 其他
	 */
	int OPER_ADD = 0;

	int OPER_EDIT = 1;
	
	int OPER_DELETE = 2;
	
	int OPER_AUDIT = 3;
	
	int OPER_UNAUDIT = 4;
	
	int OPER_REST = 5;
	
	int OPER_OTHER = 6;
	
	/**
	 * 应用角色
	 */
	String SUBSYS_ROLE_ARBITRAILY ="公用角色";
	
	String SUBSYS_ROLE_ADMIN ="管理员角色";
	
	/**
	 * 当前所选的树节点 
     * orgtype 机构类型 
	 * org 机构  
	 * dept 部门  
	 * user 用户
	 * menu 菜单
	 * roleres 角色关联菜单
	 * roleresoper 菜单关联操作
	 * role 角色
	 * subsystem 应用
	 */
	String TREENODETYPE_ORG ="org";
	
	String TREENODETYPE_DEPT ="dept";
	
	String TREENODETYPE_ORGTYPE ="orgtype";
	
	String TREENODETYPE_USER ="user";
	
	String TREENODETYPE_MENU ="menu";
	
	String TREENODETYPE_ROLERES ="roleres";
	
	String TREENODETYPE_ROLERESOPER ="roleresoper";
	
	String TREENODETYPE_ROLE ="role";
	
	String TREENODETYPE_SUBSYSTEM="subsystem";
	
	String TREENODETYPE_OPERATION="operation";
	
	String TREENODETYPE_FUNCTION ="function";
	
	String TREENODETYPE_DATARIGHTELEMENTS ="datarightelements";
	
	String TREENODETYPE_DATARIGHT ="dataright";
	
	/***角色级别
	 * GLOBAL 全局
	 * REGION  区划
	 * ORG  机构
	 */
	String ROLE_GRADE_GLOBAL = "1";

	String ROLE_GRADE_REGION = "2";

	String ROLE_GRADE_ORG = "3";
	
	/**
	 * 新增应用时，生成的两个角色的名称
	 */
	String ROLE_NAME_ADMIN ="管理员";
	
	String ROLE_NAME_PUBLIC ="公用角色";
	
	String ROLE_REMARK ="新增应用时，自动创建";
	
	/**
	 * 角色类别
	 * 0 公用角色
	 * 1 管理员
	 * 2业务人员
	 */
	String ROLE_TYPE_PUBLIC = "0";
	
	String ROLE_TYPE_ADMIN = "1";
	
	String ROLE_TYPE_COMMON = "2";
	
	/**
	 * 菜单类型  0 普通菜单  1 管理类菜单
	 */
	String MENU_TYPE_COMMON = "0";
	
	String MENU_TYPE_ADMIN = "1";
	
	/**
	 * 菜单 打开方式  0 iframe方式   1 其他方式
	 */
	String MENU_OPENTYPE_IFRAME = "0";
	
	String MENU_OPENTYPE_OTHER = "1";
	
	/**
	 * 参照类型 tree 弹出树   ;grid 弹出列表
	 */
	String REF_TREE ="tree";

	String REF_GRID ="grid";
	
	/**
	 * 数据权限类型 0 全部权限  1 部分权限
	 */
	String DATARIGHT_TYPE_ALL ="0";
	
	String DATARIGHT_TYPE_PART ="1";
	
	/**
	 * 数据权限类型为全部时的初始值
	 */
	String DATARIGHT_ALL_CODE = "000";//编码
	String DATARIGHT_ALL_NAME = "全部权限";//名称
	
	
	/**
	 * 数据项类型 依次为 单位 项目 票据 仓库
	 */
	String DATAITEM_UNIT ="UNIT";
	
	String DATAITEM_ITEM ="ITEM";
	
	String DATAITEM_BILL ="BILL";
	
	String dATAITEMDATAITEMString_WAREHOUSE ="WAREHOUSE";
	
 
	
	
	/**
	 * 数据字典编码
	 */
	String DIC_CODE_BGTLEVEL = "110";
	
	/**
	 * 通用机构查询目的 
	 */
	String COMMONORG_QUERYPURPOSE_ALL ="all";
	
	String COMMONORG_QUERYPURPOSE_TREE ="tree";
	
	/**
	 * 分割符
	 */
	String SPLIT_COMMA = ",";
	
	String SPLIT_UNDERLINE = "_";
	
	String SPLIT_AT = "@";
	
	String SPLIT_LOGIC_AND = "&&";
	
	String SPLIT_LOGIC_OR = "||";
	
	String SPLIT_QUESTION_MARK = "?";
	
	/**
	 * 操作连接符
	 */
	String OPERATOR_LIKE ="like";
	/**
	 * 通知公告状态
	 * 0:未审核
	 * 1:已审核
	 * 2：已发布
	 * 
	 */
	String NOTICE_NOT_CHECKED="0";
	String NOTICE_CHECKED="1";
	String NOTICE_RELEASED="2";
	/**
	 * 通知公告类型
	 * 1:下发
	 * 2:回复
	 */
	String NOTICE_TYPE_RELEASED = "1";
	String NOTICE_TYPE_REPLY = "2";
	
	int DEFAULT_ROLE_COUNT = 2;
	
	/***
	 * 接收状态
	 */
	String RECEIVETYPE_ALL="0";
	String RECEIVETYPE_ORGTYPE="1";
	String RECEIVETYPE_ORG="2";
	String RECEIVETYPE_USER="3";
	
	String DEFAULT_FORMAT = "yyyyMMddHHmmssSSS";
	
	String DEFAULT_REGION = "000000";//默认区划Id
	
	String PARAMCODE_LOGINTYPE = "013";
	
	String CZB_RGNCODE = "000000";//中央区划
	/**
	 * 专员办常量,收缴分析报表中用到
	 */
	String ATTACHE_OFFICE = "599";
	/**
	 * 收缴分析报表常量:1,部门小计;2专员办小计;3部门合计
	 */
	Integer REV_ANALYSIS_DEPT_SUBTOTAL = 1;
	Integer REV_ANALYSIS_OFFICE_SUBTOTAL = 2;
	Integer REV_ANALYSIS_DEPT_TOTAL = 3;
	/**
	 * 用户类型:0为单位用户,1为财政用户;
	 */
	Integer USERTYPE_AGEN = 0;
	Integer USERTYPE_FIN = 1;
}

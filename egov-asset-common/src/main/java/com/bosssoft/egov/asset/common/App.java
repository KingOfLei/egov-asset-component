package com.bosssoft.egov.asset.common;

import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String lookupKey = "type::913268368812033_!@#_target::AIMS_DICT_ASSET_GD_1";
    	String typeId = StringUtilsExt.substringBefore(lookupKey, "_!@#_");
		String table = StringUtilsExt.substringAfter(lookupKey, "_!@#_");
		System.out.println(StringUtilsExt.removeStart(typeId, "type::"));
		System.out.println(StringUtilsExt.removeStart(table, "target::"));
		System.out.println(table);
        System.out.println( "Hello World!" );
    }
}

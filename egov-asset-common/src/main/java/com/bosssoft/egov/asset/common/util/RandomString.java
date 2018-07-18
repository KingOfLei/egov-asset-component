package com.bosssoft.egov.asset.common.util;

import java.util.Random;

/** 
*
* @ClassName   类名：RandomString 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年3月12日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年3月12日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class RandomString {
	private static Random random = new Random();
	  private static char[] numbers = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	  private static char[] charAndNumbers = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

	  public static String getNumber(int size)
	  {
	    char[] tmp = new char[size];
	    for (int i = 0; i < size; i++)
	    {
	      tmp[i] = numbers[random.nextInt(10)];
	    }
	    return new String(tmp);
	  }

	  public static String randomString(int size)
	  {
	    char[] tmp = new char[size];
	    for (int i = 0; i < size; i++)
	    {
	      tmp[i] = charAndNumbers[random.nextInt(62)];
	    }
	    return new String(tmp);
	  }
	  
	  public static void main(String[] args) {
		 System.out.println(randomString(4));
		 System.out.println(getNumber(4));
	}
}

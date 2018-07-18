package com.bosssoft.egov.asset.common.util.file;

import java.io.InputStream;

import com.aspose.words.Document;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

/** 
*
* @ClassName   类名：WordToHtmlUtilExt 
* @Description 功能说明：word to html 
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年8月27日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年8月27日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class WordToHtmlUtilExt {
	
  public static void toHtml(String fileName, String targetFileName){
	  try {
		setLicense();
		Document doc = new Document(fileName);
		HtmlSaveOptions options = new HtmlSaveOptions();
		doc.save(targetFileName, options);
      } catch(Exception e){
    	  throw new IllegalArgumentException("Word转换异常", e);
      }
  }
  
  /**
   * 
   * <p>函数名称：   toHtml     </p>
   * <p>功能说明： word 转 html
   *
   * </p>
   *<p>参数说明：</p>
   * @param input
   * @param targetFileName
   *
   * @date   创建时间：2017年8月28日
   * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
   */
  public static void toHtml(InputStream input, String targetFileName){
	  try {
		    setLicense();
			Document doc = new Document(input);
			HtmlSaveOptions options = new HtmlSaveOptions();
			
			doc.save(targetFileName, options);
	      } catch(Exception e){
	    	  throw new IllegalArgumentException("Word转换异常", e);
	      }
  }
  
  private static void setLicense() throws Exception{
	  License aposeLic = new License();
	  aposeLic.setLicense(WordToHtmlUtilExt.class.getResourceAsStream("aposelicense.xml"));
  }
  
  public static void main(String[] args) throws Exception {
	  //加载licence
	  setLicense();
	  String dataDir = "C:\\Users\\xiedeshou\\Desktop\\1\\";
	  // Load the document. 
      Document doc = new Document(dataDir + "厦门市政府资产综合管理平台V3.0.1.1(build0728)版本升级说明.doc");

      HtmlSaveOptions options = new HtmlSaveOptions();

      //HtmlSaveOptions.ExportRoundtripInformation property specifies 
      //whether to write the roundtrip information when saving to HTML, MHTML or EPUB. 
      //Default value is true for HTML and false for MHTML and EPUB. 
      options.setExportRoundtripInformation(true);
      doc.save(dataDir + "ExportRoundtripInformation_out_1.html", options);

      doc = new Document(dataDir + "ExportRoundtripInformation_out_1.html");

      //Save the document Docx file format 
      doc.save(dataDir + "TestFile_out_1.docx", SaveFormat.DOCX);
		//ExEnd:ConvertDocumentToHtmlWithRoundtrip 
      System.out.println("Document converted to html with roundtrip informations successfully.");
  } 
}

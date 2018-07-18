
package com.bosssoft.asset.xm.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>updateTxl complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="updateTxl"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="qzxx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lxrxx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateTxl", propOrder = {
    "qzxx",
    "lxrxx"
})
public class UpdateTxl {

    protected String qzxx;
    protected String lxrxx;

    /**
     * 获取qzxx属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQzxx() {
        return qzxx;
    }

    /**
     * 设置qzxx属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQzxx(String value) {
        this.qzxx = value;
    }

    /**
     * 获取lxrxx属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLxrxx() {
        return lxrxx;
    }

    /**
     * 设置lxrxx属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLxrxx(String value) {
        this.lxrxx = value;
    }

}

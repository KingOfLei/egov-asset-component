
package com.bosssoft.asset.xm.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>sendMsg complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="sendMsg"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sendName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sendDept" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sendTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xtsqm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="recName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="recDept" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="recPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="localTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="qm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="qyhf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendMsg", propOrder = {
    "sendName",
    "sendDept",
    "content",
    "sendTime",
    "xtsqm",
    "recName",
    "recDept",
    "recPhone",
    "localTime",
    "qm",
    "qyhf"
})
public class SendMsg {

    protected String sendName;
    protected String sendDept;
    protected String content;
    protected String sendTime;
    protected String xtsqm;
    protected String recName;
    protected String recDept;
    protected String recPhone;
    protected String localTime;
    protected String qm;
    protected String qyhf;

    /**
     * 获取sendName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendName() {
        return sendName;
    }

    /**
     * 设置sendName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendName(String value) {
        this.sendName = value;
    }

    /**
     * 获取sendDept属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendDept() {
        return sendDept;
    }

    /**
     * 设置sendDept属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendDept(String value) {
        this.sendDept = value;
    }

    /**
     * 获取content属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置content属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * 获取sendTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * 设置sendTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendTime(String value) {
        this.sendTime = value;
    }

    /**
     * 获取xtsqm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXtsqm() {
        return xtsqm;
    }

    /**
     * 设置xtsqm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXtsqm(String value) {
        this.xtsqm = value;
    }

    /**
     * 获取recName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecName() {
        return recName;
    }

    /**
     * 设置recName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecName(String value) {
        this.recName = value;
    }

    /**
     * 获取recDept属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecDept() {
        return recDept;
    }

    /**
     * 设置recDept属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecDept(String value) {
        this.recDept = value;
    }

    /**
     * 获取recPhone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecPhone() {
        return recPhone;
    }

    /**
     * 设置recPhone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecPhone(String value) {
        this.recPhone = value;
    }

    /**
     * 获取localTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalTime() {
        return localTime;
    }

    /**
     * 设置localTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalTime(String value) {
        this.localTime = value;
    }

    /**
     * 获取qm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQm() {
        return qm;
    }

    /**
     * 设置qm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQm(String value) {
        this.qm = value;
    }

    /**
     * 获取qyhf属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQyhf() {
        return qyhf;
    }

    /**
     * 设置qyhf属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQyhf(String value) {
        this.qyhf = value;
    }

}

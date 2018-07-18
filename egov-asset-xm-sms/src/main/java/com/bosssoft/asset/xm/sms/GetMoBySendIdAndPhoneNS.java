
package com.bosssoft.asset.xm.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getMoBySendIdAndPhoneNS complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getMoBySendIdAndPhoneNS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sendId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="xtsqm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMoBySendIdAndPhoneNS", propOrder = {
    "sendId",
    "phone",
    "xtsqm"
})
public class GetMoBySendIdAndPhoneNS {

    @XmlElement(namespace = "http://cn.xm.czj.dxpt")
    protected String sendId;
    @XmlElement(namespace = "http://cn.xm.czj.dxpt")
    protected String phone;
    @XmlElement(namespace = "http://cn.xm.czj.dxpt")
    protected String xtsqm;

    /**
     * 获取sendId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendId() {
        return sendId;
    }

    /**
     * 设置sendId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendId(String value) {
        this.sendId = value;
    }

    /**
     * 获取phone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置phone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
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

}


package com.webservice.ssdi.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StockID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stockID",
    "startDate",
    "endDate"
})
@XmlRootElement(name = "serviceInput")
public class ServiceInput {

    @XmlElement(name = "StockID", required = true)
    protected BigDecimal stockID;
    @XmlElement(name = "StartDate")
    protected int startDate;
    @XmlElement(name = "EndDate")
    protected int endDate;

    /**
     * Gets the value of the stockID property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStockID() {
        return stockID;
    }

    /**
     * Sets the value of the stockID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStockID(BigDecimal value) {
        this.stockID = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     */
    public int getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     */
    public void setStartDate(int value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     */
    public int getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     */
    public void setEndDate(int value) {
        this.endDate = value;
    }

}

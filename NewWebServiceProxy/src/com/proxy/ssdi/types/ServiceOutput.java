
package com.proxy.ssdi.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="outputXBar" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="outputYBar" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="outputBeta0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="outputBeta1" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "result",
    "outputXBar",
    "outputYBar",
    "outputBeta0",
    "outputBeta1"
})
@XmlRootElement(name = "serviceOutput")
public class ServiceOutput {

    protected double result;
    protected double outputXBar;
    protected double outputYBar;
    protected double outputBeta0;
    protected double outputBeta1;

    /**
     * Gets the value of the result property.
     * 
     */
    public double getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     */
    public void setResult(double value) {
        this.result = value;
    }

    /**
     * Gets the value of the outputXBar property.
     * 
     */
    public double getOutputXBar() {
        return outputXBar;
    }

    /**
     * Sets the value of the outputXBar property.
     * 
     */
    public void setOutputXBar(double value) {
        this.outputXBar = value;
    }

    /**
     * Gets the value of the outputYBar property.
     * 
     */
    public double getOutputYBar() {
        return outputYBar;
    }

    /**
     * Sets the value of the outputYBar property.
     * 
     */
    public void setOutputYBar(double value) {
        this.outputYBar = value;
    }

    /**
     * Gets the value of the outputBeta0 property.
     * 
     */
    public double getOutputBeta0() {
        return outputBeta0;
    }

    /**
     * Sets the value of the outputBeta0 property.
     * 
     */
    public void setOutputBeta0(double value) {
        this.outputBeta0 = value;
    }

    /**
     * Gets the value of the outputBeta1 property.
     * 
     */
    public double getOutputBeta1() {
        return outputBeta1;
    }

    /**
     * Sets the value of the outputBeta1 property.
     * 
     */
    public void setOutputBeta1(double value) {
        this.outputBeta1 = value;
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:50:57 PM GMT 
//


package com.siebentag.cj.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;


/**
 * <p>Java class for Wicket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Wicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objectId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="batsman" type="{http://www.siebentag.com/steamboat/xsd}Player"/>
 *         &lt;element name="wicketType" type="{http://www.siebentag.com/steamboat/xsd}WicketType"/>
 *         &lt;element name="isOut" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Wicket", propOrder = {
    "objectId",
    "batsman",
    "wicketType",
    "isOut"
})
@Entity(name = "com.siebentag.cj.model.Wicket")
@Table(name = "t_wicket")
@Inheritance(strategy = InheritanceType.JOINED)
public class Wicket
    implements Equals, HashCode
{

    protected long objectId;
    @XmlElement(required = true)
    protected com.siebentag.cj.model.Player batsman;
    @XmlElement(required = true)
    protected WicketType wicketType;
    protected boolean isOut;

    /**
     * Gets the value of the objectId property.
     * 
     */
    @Id
    @Column(name = "object_id", scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getObjectId() {
        return objectId;
    }

    /**
     * Sets the value of the objectId property.
     * 
     */
    public void setObjectId(long value) {
        this.objectId = value;
    }

    @Transient
    public boolean isSetObjectId() {
        return true;
    }

    /**
     * Gets the value of the batsman property.
     * 
     * @return
     *     possible object is
     *     {@link com.siebentag.cj.model.Player }
     *     
     */
    @ManyToOne(targetEntity = com.siebentag.cj.model.Player.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "wicket_batsman_id")
    public com.siebentag.cj.model.Player getBatsman() {
        return batsman;
    }

    /**
     * Sets the value of the batsman property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.siebentag.cj.model.Player }
     *     
     */
    public void setBatsman(com.siebentag.cj.model.Player value) {
        this.batsman = value;
    }

    @Transient
    public boolean isSetBatsman() {
        return (this.batsman!= null);
    }

    /**
     * Gets the value of the wicketType property.
     * 
     * @return
     *     possible object is
     *     {@link WicketType }
     *     
     */
    @Basic
    @Column(name = "wicket_type")
    @Enumerated(EnumType.STRING)
    public WicketType getWicketType() {
        return wicketType;
    }

    /**
     * Sets the value of the wicketType property.
     * 
     * @param value
     *     allowed object is
     *     {@link WicketType }
     *     
     */
    public void setWicketType(WicketType value) {
        this.wicketType = value;
    }

    @Transient
    public boolean isSetWicketType() {
        return (this.wicketType!= null);
    }

    /**
     * Gets the value of the isOut property.
     * 
     */
    @Basic
    @Column(name = "is_out")
    public boolean isIsOut() {
        return isOut;
    }

    /**
     * Sets the value of the isOut property.
     * 
     */
    public void setIsOut(boolean value) {
        this.isOut = value;
    }

    @Transient
    public boolean isSetIsOut() {
        return true;
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof Wicket)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        final Wicket that = ((Wicket) object);
        equalsBuilder.append(this.getObjectId(), that.getObjectId());
        equalsBuilder.append(this.getBatsman(), that.getBatsman());
        equalsBuilder.append(this.getWicketType(), that.getWicketType());
        equalsBuilder.append(this.isIsOut(), that.isIsOut());
    }

    public boolean equals(Object object) {
        if (!(object instanceof Wicket)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
        equals(object, equalsBuilder);
        return equalsBuilder.isEquals();
    }

    public void hashCode(HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.getObjectId());
        hashCodeBuilder.append(this.getBatsman());
        hashCodeBuilder.append(this.getWicketType());
        hashCodeBuilder.append(this.isIsOut());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

}

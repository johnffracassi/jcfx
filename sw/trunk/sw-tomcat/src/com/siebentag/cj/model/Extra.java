//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:50:57 PM GMT 
//


package com.siebentag.cj.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;


/**
 * <p>Java class for Extra complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Extra">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objectId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="wide" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="noball" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="legbye" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bye" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="other" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Extra", propOrder = {
    "objectId",
    "wide",
    "noball",
    "legbye",
    "bye",
    "other"
})
@Entity(name = "com.siebentag.cj.model.Extra")
@Table(name = "t_extra")
@Inheritance(strategy = InheritanceType.JOINED)
public class Extra
    implements Equals, HashCode
{

    protected long objectId;
    protected int wide;
    protected int noball;
    protected int legbye;
    protected int bye;
    protected int other;

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
     * Gets the value of the wide property.
     * 
     */
    @Basic
    @Column(name = "wide", scale = 0)
    public int getWide() {
        return wide;
    }

    /**
     * Sets the value of the wide property.
     * 
     */
    public void setWide(int value) {
        this.wide = value;
    }

    @Transient
    public boolean isSetWide() {
        return true;
    }

    /**
     * Gets the value of the noball property.
     * 
     */
    @Basic
    @Column(name = "noball", scale = 0)
    public int getNoball() {
        return noball;
    }

    /**
     * Sets the value of the noball property.
     * 
     */
    public void setNoball(int value) {
        this.noball = value;
    }

    @Transient
    public boolean isSetNoball() {
        return true;
    }

    /**
     * Gets the value of the legbye property.
     * 
     */
    @Basic
    @Column(name = "legbye", scale = 0)
    public int getLegbye() {
        return legbye;
    }

    /**
     * Sets the value of the legbye property.
     * 
     */
    public void setLegbye(int value) {
        this.legbye = value;
    }

    @Transient
    public boolean isSetLegbye() {
        return true;
    }

    /**
     * Gets the value of the bye property.
     * 
     */
    @Basic
    @Column(name = "bye", scale = 0)
    public int getBye() {
        return bye;
    }

    /**
     * Sets the value of the bye property.
     * 
     */
    public void setBye(int value) {
        this.bye = value;
    }

    @Transient
    public boolean isSetBye() {
        return true;
    }

    /**
     * Gets the value of the other property.
     * 
     */
    @Basic
    @Column(name = "other", scale = 0)
    public int getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     */
    public void setOther(int value) {
        this.other = value;
    }

    @Transient
    public boolean isSetOther() {
        return true;
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof Extra)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        final Extra that = ((Extra) object);
        equalsBuilder.append(this.getObjectId(), that.getObjectId());
        equalsBuilder.append(this.getWide(), that.getWide());
        equalsBuilder.append(this.getNoball(), that.getNoball());
        equalsBuilder.append(this.getLegbye(), that.getLegbye());
        equalsBuilder.append(this.getBye(), that.getBye());
        equalsBuilder.append(this.getOther(), that.getOther());
    }

    public boolean equals(Object object) {
        if (!(object instanceof Extra)) {
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
        hashCodeBuilder.append(this.getWide());
        hashCodeBuilder.append(this.getNoball());
        hashCodeBuilder.append(this.getLegbye());
        hashCodeBuilder.append(this.getBye());
        hashCodeBuilder.append(this.getOther());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

}

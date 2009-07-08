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
 * <p>Java class for BatsmanInnings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatsmanInnings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objectId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ordinal" type="{http://www.siebentag.com/steamboat/xsd}Ordinal"/>
 *         &lt;element name="player" type="{http://www.siebentag.com/steamboat/xsd}Player"/>
 *         &lt;element name="balls" type="{http://www.siebentag.com/steamboat/xsd}BallCollection"/>
 *         &lt;element name="wicket" type="{http://www.siebentag.com/steamboat/xsd}Wicket"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatsmanInnings", propOrder = {
    "objectId",
    "ordinal",
    "player",
    "balls",
    "wicket"
})
@Entity(name = "com.siebentag.cj.model.BatsmanInnings")
@Table(name = "t_batsman_innings")
@Inheritance(strategy = InheritanceType.JOINED)
public class BatsmanInnings
    implements Equals, HashCode
{

    protected long objectId;
    protected int ordinal;
    @XmlElement(required = true)
    protected com.siebentag.cj.model.Player player;
    @XmlElement(required = true)
    protected com.siebentag.cj.model.BallCollection balls;
    @XmlElement(required = true)
    protected com.siebentag.cj.model.Wicket wicket;

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
     * Gets the value of the ordinal property.
     * 
     */
    @Basic
    @Column(name = "ordinal", scale = 0)
    public int getOrdinal() {
        return ordinal;
    }

    /**
     * Sets the value of the ordinal property.
     * 
     */
    public void setOrdinal(int value) {
        this.ordinal = value;
    }

    @Transient
    public boolean isSetOrdinal() {
        return true;
    }

    /**
     * Gets the value of the player property.
     * 
     * @return
     *     possible object is
     *     {@link com.siebentag.cj.model.Player }
     *     
     */
    @ManyToOne(targetEntity = com.siebentag.cj.model.Player.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "batsman_innings_player_id")
    public com.siebentag.cj.model.Player getPlayer() {
        return player;
    }

    /**
     * Sets the value of the player property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.siebentag.cj.model.Player }
     *     
     */
    public void setPlayer(com.siebentag.cj.model.Player value) {
        this.player = value;
    }

    @Transient
    public boolean isSetPlayer() {
        return (this.player!= null);
    }

    /**
     * Gets the value of the balls property.
     * 
     * @return
     *     possible object is
     *     {@link com.siebentag.cj.model.BallCollection }
     *     
     */
    @ManyToOne(targetEntity = com.siebentag.cj.model.BallCollection.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "batsman_innings_balls_id")
    public com.siebentag.cj.model.BallCollection getBalls() {
        return balls;
    }

    /**
     * Sets the value of the balls property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.siebentag.cj.model.BallCollection }
     *     
     */
    public void setBalls(com.siebentag.cj.model.BallCollection value) {
        this.balls = value;
    }

    @Transient
    public boolean isSetBalls() {
        return (this.balls!= null);
    }

    /**
     * Gets the value of the wicket property.
     * 
     * @return
     *     possible object is
     *     {@link com.siebentag.cj.model.Wicket }
     *     
     */
    @ManyToOne(targetEntity = com.siebentag.cj.model.Wicket.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "batsman_innings_wicket_id")
    public com.siebentag.cj.model.Wicket getWicket() {
        return wicket;
    }

    /**
     * Sets the value of the wicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.siebentag.cj.model.Wicket }
     *     
     */
    public void setWicket(com.siebentag.cj.model.Wicket value) {
        this.wicket = value;
    }

    @Transient
    public boolean isSetWicket() {
        return (this.wicket!= null);
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof BatsmanInnings)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        final BatsmanInnings that = ((BatsmanInnings) object);
        equalsBuilder.append(this.getObjectId(), that.getObjectId());
        equalsBuilder.append(this.getOrdinal(), that.getOrdinal());
        equalsBuilder.append(this.getPlayer(), that.getPlayer());
        equalsBuilder.append(this.getBalls(), that.getBalls());
        equalsBuilder.append(this.getWicket(), that.getWicket());
    }

    public boolean equals(Object object) {
        if (!(object instanceof BatsmanInnings)) {
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
        hashCodeBuilder.append(this.getOrdinal());
        hashCodeBuilder.append(this.getPlayer());
        hashCodeBuilder.append(this.getBalls());
        hashCodeBuilder.append(this.getWicket());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

}

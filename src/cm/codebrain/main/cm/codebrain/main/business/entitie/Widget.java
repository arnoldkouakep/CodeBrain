/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.entitie;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author KSA-INET
 */
@Entity
@Table(catalog = "", schema = "BRAIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Widget.findAll", query = "SELECT w FROM Widget w")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@cb")
public class Widget implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "WIDGET_ID", nullable = false, length = 64)
    private String widgetId;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String label;
    private Short available;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @JoinColumn(name = "LEVELS_ID", referencedColumnName = "LEVELS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Levels levelsId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Widget> widgetSet;
    @JoinColumn(name = "PARENT", referencedColumnName = "WIDGET_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Widget parent;

    public Widget() {
    }

    public Widget(String widgetId) {
        this.widgetId = widgetId;
    }

    public Widget(String widgetId, String name, String label) {
        this.widgetId = widgetId;
        this.name = name;
        this.label = label;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Short getAvailable() {
        return available;
    }

    public void setAvailable(Short available) {
        this.available = available;
    }

    public Date getDtCreated() {
        return dtCreated;
    }

    public void setDtCreated(Date dtCreated) {
        this.dtCreated = dtCreated;
    }

    public Date getDtModified() {
        return dtModified;
    }

    public void setDtModified(Date dtModified) {
        this.dtModified = dtModified;
    }

    public Levels getLevelsId() {
        return levelsId;
    }

    public void setLevelsId(Levels levelsId) {
        this.levelsId = levelsId;
    }

    public Users getUserModified() {
        return userModified;
    }

    public void setUserModified(Users userModified) {
        this.userModified = userModified;
    }

    public Users getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Users userCreated) {
        this.userCreated = userCreated;
    }

    @XmlTransient
    public Set<Widget> getWidgetSet() {
        return widgetSet;
    }

    public void setWidgetSet(Set<Widget> widgetSet) {
        this.widgetSet = widgetSet;
    }

    public Widget getParent() {
        return parent;
    }

    public void setParent(Widget parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (widgetId != null ? widgetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Widget)) {
            return false;
        }
        Widget other = (Widget) object;
        if ((this.widgetId == null && other.widgetId != null) || (this.widgetId != null && !this.widgetId.equals(other.widgetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Widget[ widgetId=" + widgetId + " ]";
    }
    
}

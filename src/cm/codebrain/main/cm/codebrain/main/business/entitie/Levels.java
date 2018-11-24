/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.entitie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author KSA-INET
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Levels.findAll", query = "SELECT l FROM Levels l")
    , @NamedQuery(name = "Levels.findByLevelsId", query = "SELECT l FROM Levels l WHERE l.levelsId = :levelsId")
    , @NamedQuery(name = "Levels.findByCode", query = "SELECT l FROM Levels l WHERE l.code = :code")
    , @NamedQuery(name = "Levels.findByIntitule", query = "SELECT l FROM Levels l WHERE l.intitule = :intitule")
    , @NamedQuery(name = "Levels.findByStateDb", query = "SELECT l FROM Levels l WHERE l.stateDb = :stateDb")
    , @NamedQuery(name = "Levels.findByDtCreated", query = "SELECT l FROM Levels l WHERE l.dtCreated = :dtCreated")
    , @NamedQuery(name = "Levels.findByDtModified", query = "SELECT l FROM Levels l WHERE l.dtModified = :dtModified")
    , @NamedQuery(name = "Levels.findByUserCreated", query = "SELECT l FROM Levels l WHERE l.userCreated = :userCreated")
    , @NamedQuery(name = "Levels.findByUserModified", query = "SELECT l FROM Levels l WHERE l.userModified = :userModified")})
public class Levels implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "LEVELS_ID")
    private String levelsId;
    @Basic(optional = false)
    private int code;
    private String intitule;
    @Column(name = "STATE_DB")
    private String stateDb;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @Column(name = "USER_CREATED")
    private String userCreated;
    @Column(name = "USER_MODIFIED")
    private String userModified;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelsId")
    @JsonIgnore
    private Collection<Users> usersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelsId")
    @JsonIgnore
    private Collection<Widget> widgetCollection;

    public Levels() {
    }

    public Levels(String levelsId) {
        this.levelsId = levelsId;
    }

    public Levels(String levelsId, int code) {
        this.levelsId = levelsId;
        this.code = code;
    }

    public String getLevelsId() {
        return levelsId;
    }

    public void setLevelsId(String levelsId) {
        this.levelsId = levelsId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getStateDb() {
        return stateDb;
    }

    public void setStateDb(String stateDb) {
        this.stateDb = stateDb;
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

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public String getUserModified() {
        return userModified;
    }

    public void setUserModified(String userModified) {
        this.userModified = userModified;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @XmlTransient
    public Collection<Widget> getWidgetCollection() {
        return widgetCollection;
    }

    public void setWidgetCollection(Collection<Widget> widgetCollection) {
        this.widgetCollection = widgetCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (levelsId != null ? levelsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Levels)) {
            return false;
        }
        Levels other = (Levels) object;
        if ((this.levelsId == null && other.levelsId != null) || (this.levelsId != null && !this.levelsId.equals(other.levelsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Levels[ levelsId=" + levelsId + " ]";
    }
    
}

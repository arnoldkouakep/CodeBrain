/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.entitie;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @NamedQuery(name = "Groupe.findAll", query = "SELECT g FROM Groupe g")
    , @NamedQuery(name = "Groupe.findByGroupeId", query = "SELECT g FROM Groupe g WHERE g.groupeId = :groupeId")
    , @NamedQuery(name = "Groupe.findByCode", query = "SELECT g FROM Groupe g WHERE g.code = :code")
    , @NamedQuery(name = "Groupe.findByIntitule", query = "SELECT g FROM Groupe g WHERE g.intitule = :intitule")
    , @NamedQuery(name = "Groupe.findByStateDb", query = "SELECT g FROM Groupe g WHERE g.stateDb = :stateDb")
    , @NamedQuery(name = "Groupe.findByDtCreated", query = "SELECT g FROM Groupe g WHERE g.dtCreated = :dtCreated")
    , @NamedQuery(name = "Groupe.findByDtModified", query = "SELECT g FROM Groupe g WHERE g.dtModified = :dtModified")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@cb", scope = Groupe.class)
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GROUPE_ID", nullable = false, length = 64)
    private String groupeId;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String code;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String intitule;
    @Column(name = "STATE_DB", length = 64)
    private String stateDb;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @JoinColumn(name = "SALLE_ID", referencedColumnName = "SALLE_ID", nullable = false)
    @ManyToOne(optional = false)
    private Salle salleId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID")
    @ManyToOne
    private Users userCreated;
    @OneToMany(mappedBy = "groupeId")
    private Collection<Classe> classeCollection;

    public Groupe() {
    }

    public Groupe(String groupeId) {
        this.groupeId = groupeId;
    }

    public Groupe(String groupeId, String code, String intitule) {
        this.groupeId = groupeId;
        this.code = code;
        this.intitule = intitule;
    }

    public String getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(String groupeId) {
        this.groupeId = groupeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public Salle getSalleId() {
        return salleId;
    }

    public void setSalleId(Salle salleId) {
        this.salleId = salleId;
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
    public Collection<Classe> getClasseCollection() {
        return classeCollection;
    }

    public void setClasseCollection(Collection<Classe> classeCollection) {
        this.classeCollection = classeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupeId != null ? groupeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupe)) {
            return false;
        }
        Groupe other = (Groupe) object;
        if ((this.groupeId == null && other.groupeId != null) || (this.groupeId != null && !this.groupeId.equals(other.groupeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Groupe[ groupeId=" + groupeId + " ]";
    }
    
}

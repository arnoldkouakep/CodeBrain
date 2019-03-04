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
import javax.persistence.CascadeType;
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
    @NamedQuery(name = "Classe.findAll", query = "SELECT c FROM Classe c")
    , @NamedQuery(name = "Classe.findByClasseId", query = "SELECT c FROM Classe c WHERE c.classeId = :classeId")
    , @NamedQuery(name = "Classe.findByCode", query = "SELECT c FROM Classe c WHERE c.code = :code")
    , @NamedQuery(name = "Classe.findByIntitule", query = "SELECT c FROM Classe c WHERE c.intitule = :intitule")
    , @NamedQuery(name = "Classe.findByStateDb", query = "SELECT c FROM Classe c WHERE c.stateDb = :stateDb")
    , @NamedQuery(name = "Classe.findByDtCreated", query = "SELECT c FROM Classe c WHERE c.dtCreated = :dtCreated")
    , @NamedQuery(name = "Classe.findByDtModified", query = "SELECT c FROM Classe c WHERE c.dtModified = :dtModified")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@cb", scope = Classe.class)
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CLASSE_ID", nullable = false, length = 64)
    private String classeId;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classeId")
    private Collection<Salle> salleCollection;
    @JoinColumn(name = "GROUPE_ID", referencedColumnName = "GROUPE_ID")
    @ManyToOne
    private Groupe groupeId;
    @JoinColumn(name = "SECTIONS_ID", referencedColumnName = "SECTIONS_ID", nullable = false)
    @ManyToOne(optional = false)
    private Sections sectionsId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID")
    @ManyToOne
    private Users userCreated;

    public Classe() {
    }

    public Classe(String classeId) {
        this.classeId = classeId;
    }

    public Classe(String classeId, String code, String intitule) {
        this.classeId = classeId;
        this.code = code;
        this.intitule = intitule;
    }

    public String getClasseId() {
        return classeId;
    }

    public void setClasseId(String classeId) {
        this.classeId = classeId;
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

    @XmlTransient
    public Collection<Salle> getSalleCollection() {
        return salleCollection;
    }

    public void setSalleCollection(Collection<Salle> salleCollection) {
        this.salleCollection = salleCollection;
    }

    public Groupe getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Groupe groupeId) {
        this.groupeId = groupeId;
    }

    public Sections getSectionsId() {
        return sectionsId;
    }

    public void setSectionsId(Sections sectionsId) {
        this.sectionsId = sectionsId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classeId != null ? classeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classe)) {
            return false;
        }
        Classe other = (Classe) object;
        if ((this.classeId == null && other.classeId != null) || (this.classeId != null && !this.classeId.equals(other.classeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Classe[ classeId=" + classeId + " ]";
    }
    
}

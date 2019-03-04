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
    @NamedQuery(name = "Sections.findAll", query = "SELECT s FROM Sections s")
    , @NamedQuery(name = "Sections.findBySectionsId", query = "SELECT s FROM Sections s WHERE s.sectionsId = :sectionsId")
    , @NamedQuery(name = "Sections.findByCode", query = "SELECT s FROM Sections s WHERE s.code = :code")
    , @NamedQuery(name = "Sections.findByIntitule", query = "SELECT s FROM Sections s WHERE s.intitule = :intitule")
    , @NamedQuery(name = "Sections.findByStateDb", query = "SELECT s FROM Sections s WHERE s.stateDb = :stateDb")
    , @NamedQuery(name = "Sections.findByDtCreated", query = "SELECT s FROM Sections s WHERE s.dtCreated = :dtCreated")
    , @NamedQuery(name = "Sections.findByDtModified", query = "SELECT s FROM Sections s WHERE s.dtModified = :dtModified")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@cb", scope = Sections.class)
public class Sections implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SECTIONS_ID", nullable = false, length = 64)
    private String sectionsId;
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
    @JoinColumn(name = "ETABLISSEMENT_ID", referencedColumnName = "ETABLISSEMENT_ID", nullable = false)
    @ManyToOne(optional = false)
    private Etablissement etablissementId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID")
    @ManyToOne
    private Users userCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sectionsId")
    private Collection<Classe> classeCollection;

    public Sections() {
    }

    public Sections(String sectionsId) {
        this.sectionsId = sectionsId;
    }

    public Sections(String sectionsId, String code, String intitule) {
        this.sectionsId = sectionsId;
        this.code = code;
        this.intitule = intitule;
    }

    public String getSectionsId() {
        return sectionsId;
    }

    public void setSectionsId(String sectionsId) {
        this.sectionsId = sectionsId;
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

    public Etablissement getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(Etablissement etablissementId) {
        this.etablissementId = etablissementId;
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
        hash += (sectionsId != null ? sectionsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sections)) {
            return false;
        }
        Sections other = (Sections) object;
        if ((this.sectionsId == null && other.sectionsId != null) || (this.sectionsId != null && !this.sectionsId.equals(other.sectionsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Sections[ sectionsId=" + sectionsId + " ]";
    }
    
}

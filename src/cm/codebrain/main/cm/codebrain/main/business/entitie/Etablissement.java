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
    @NamedQuery(name = "Etablissement.findAll", query = "SELECT e FROM Etablissement e")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@cb")
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ETABLISSEMENT_ID", nullable = false, length = 64)
    private String etablissementId;
    @Basic(optional = false)
    @Column(name = "NAME_ABREGE", nullable = false, length = 64)
    private String nameAbrege;
    @Basic(optional = false)
    @Column(name = "FULL_NAME", nullable = false, length = 255)
    private String fullName;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String adresse;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String ville;
    @Basic(optional = false)
    @Column(nullable = false, length = 16)
    private String telephone;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String email;
    @Column(name = "STATE_DB", length = 64)
    private String stateDb;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "etablissementId", fetch = FetchType.LAZY)
    private Set<Section> sectionSet;

    public Etablissement() {
    }

    public Etablissement(String etablissementId) {
        this.etablissementId = etablissementId;
    }

    public Etablissement(String etablissementId, String nameAbrege, String fullName, String adresse, String ville, String telephone, String email) {
        this.etablissementId = etablissementId;
        this.nameAbrege = nameAbrege;
        this.fullName = fullName;
        this.adresse = adresse;
        this.ville = ville;
        this.telephone = telephone;
        this.email = email;
    }

    public String getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(String etablissementId) {
        this.etablissementId = etablissementId;
    }

    public String getNameAbrege() {
        return nameAbrege;
    }

    public void setNameAbrege(String nameAbrege) {
        this.nameAbrege = nameAbrege;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public Set<Section> getSectionSet() {
        return sectionSet;
    }

    public void setSectionSet(Set<Section> sectionSet) {
        this.sectionSet = sectionSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etablissementId != null ? etablissementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etablissement)) {
            return false;
        }
        Etablissement other = (Etablissement) object;
        if ((this.etablissementId == null && other.etablissementId != null) || (this.etablissementId != null && !this.etablissementId.equals(other.etablissementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Etablissement[ etablissementId=" + etablissementId + " ]";
    }
    
}

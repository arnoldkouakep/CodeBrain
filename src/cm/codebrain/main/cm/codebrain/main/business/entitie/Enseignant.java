/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.entitie;

import cm.codebrain.main.business.controller.ObjectIdResolver;
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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Enseignant.findAll", query = "SELECT e FROM Enseignant e")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@enseignant", scope = Enseignant.class, resolver = ObjectIdResolver.class)
public class Enseignant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ENSEIGNANT_ID", nullable = false, length = 64)
    private String enseignantId;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String matricule;
    @Basic(optional = false)
    @Column(name = "FIRST_NAME", nullable = false, length = 64)
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LAST_NAME", nullable = false, length = 64)
    private String lastName;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Basic(optional = false)
    @Column(name = "BORN_LOCATION", nullable = false, length = 64)
    private String bornLocation;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String cni;
    @Basic(optional = false)
    @Column(name = "DATE_DELIVRANCE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateDelivrance;
    @Basic(optional = false)
    @Column(name = "DATE_EXPIRE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateExpire;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String sexe;
    @Lob
    private Serializable profil;
    @Column(name = "STATE_DB", length = 64)
    private String stateDb;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @JoinColumn(name = "PROFESSION_ID", referencedColumnName = "PROFESSION_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profession professionId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enseignantId", fetch = FetchType.LAZY)
    private Set<AffectationCours> affectationCoursSet;

    public Enseignant() {
    }

    public Enseignant(String enseignantId) {
        this.enseignantId = enseignantId;
    }

    public Enseignant(String enseignantId, String matricule, String firstName, String lastName, Date birthday, String bornLocation, String cni, Date dateDelivrance, Date dateExpire, String sexe) {
        this.enseignantId = enseignantId;
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.bornLocation = bornLocation;
        this.cni = cni;
        this.dateDelivrance = dateDelivrance;
        this.dateExpire = dateExpire;
        this.sexe = sexe;
    }

    public String getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(String enseignantId) {
        this.enseignantId = enseignantId;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBornLocation() {
        return bornLocation;
    }

    public void setBornLocation(String bornLocation) {
        this.bornLocation = bornLocation;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public Date getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(Date dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public Date getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(Date dateExpire) {
        this.dateExpire = dateExpire;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Serializable getProfil() {
        return profil;
    }

    public void setProfil(Serializable profil) {
        this.profil = profil;
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

    public Profession getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Profession professionId) {
        this.professionId = professionId;
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
    public Set<AffectationCours> getAffectationCoursSet() {
        return affectationCoursSet;
    }

    public void setAffectationCoursSet(Set<AffectationCours> affectationCoursSet) {
        this.affectationCoursSet = affectationCoursSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enseignantId != null ? enseignantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enseignant)) {
            return false;
        }
        Enseignant other = (Enseignant) object;
        if ((this.enseignantId == null && other.enseignantId != null) || (this.enseignantId != null && !this.enseignantId.equals(other.enseignantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Enseignant[ enseignantId=" + enseignantId + " ]";
    }
    
}

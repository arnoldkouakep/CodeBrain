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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author KSA-INET
 */
@Entity
@Table(name = "ANNEE_ACADEMIC", catalog = "", schema = "BRAIN", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"SESSION"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnneeAcademic.findAll", query = "SELECT a FROM AnneeAcademic a")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@anneeAcademic", scope = AnneeAcademic.class, resolver = ObjectIdResolver.class)
public class AnneeAcademic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ANNEE_ACADEMIC_ID", nullable = false, length = 64)
    private String anneeAcademicId;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String session;
    @Basic(optional = false)
    @Column(name = "DATE_OUVERTURE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOuverture;
    @Column(name = "DATE_FERMETURE")
    @Temporal(TemporalType.DATE)
    private Date dateFermeture;
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
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userCreated;
    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
    private Set<Etablissement> etablissementSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "session", fetch = FetchType.LAZY)
    private Set<ParametreAnneeAcademic> parametreAnneeAcademicSet;

    public AnneeAcademic() {
    }

    public AnneeAcademic(String anneeAcademicId) {
        this.anneeAcademicId = anneeAcademicId;
    }

    public AnneeAcademic(String anneeAcademicId, String session, Date dateOuverture) {
        this.anneeAcademicId = anneeAcademicId;
        this.session = session;
        this.dateOuverture = dateOuverture;
    }

    public String getAnneeAcademicId() {
        return anneeAcademicId;
    }

    public void setAnneeAcademicId(String anneeAcademicId) {
        this.anneeAcademicId = anneeAcademicId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Date getDateFermeture() {
        return dateFermeture;
    }

    public void setDateFermeture(Date dateFermeture) {
        this.dateFermeture = dateFermeture;
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
    public Set<Etablissement> getEtablissementSet() {
        return etablissementSet;
    }

    public void setEtablissementSet(Set<Etablissement> etablissementSet) {
        this.etablissementSet = etablissementSet;
    }

    @XmlTransient
    public Set<ParametreAnneeAcademic> getParametreAnneeAcademicSet() {
        return parametreAnneeAcademicSet;
    }

    public void setParametreAnneeAcademicSet(Set<ParametreAnneeAcademic> parametreAnneeAcademicSet) {
        this.parametreAnneeAcademicSet = parametreAnneeAcademicSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (anneeAcademicId != null ? anneeAcademicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnneeAcademic)) {
            return false;
        }
        AnneeAcademic other = (AnneeAcademic) object;
        if ((this.anneeAcademicId == null && other.anneeAcademicId != null) || (this.anneeAcademicId != null && !this.anneeAcademicId.equals(other.anneeAcademicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.AnneeAcademic[ anneeAcademicId=" + anneeAcademicId + " ]";
    }

}

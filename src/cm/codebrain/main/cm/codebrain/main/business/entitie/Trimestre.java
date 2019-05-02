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
@Table(catalog = "", schema = "BRAIN", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CODE", "ANNEE_ACADEMIC_ID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trimestre.findAll", query = "SELECT t FROM Trimestre t")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@trimestre", scope = Trimestre.class, resolver = ObjectIdResolver.class)
public class Trimestre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TRIMESTRE_ID", nullable = false, length = 64)
    private String trimestreId;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String code;
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
    @JoinColumn(name = "ANNEE_ACADEMIC_ID", referencedColumnName = "ANNEE_ACADEMIC_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AnneeAcademic anneeAcademicId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trimestreId", fetch = FetchType.LAZY)
    private Set<Sequence> sequenceSet;
    @OneToMany(mappedBy = "trimestreId", fetch = FetchType.LAZY)
    private Set<ParametreAnneeAcademic> parametreAnneeAcademicSet;

    public Trimestre() {
    }

    public Trimestre(String trimestreId) {
        this.trimestreId = trimestreId;
    }

    public Trimestre(String trimestreId, String code, Date dateOuverture) {
        this.trimestreId = trimestreId;
        this.code = code;
        this.dateOuverture = dateOuverture;
    }

    public String getTrimestreId() {
        return trimestreId;
    }

    public void setTrimestreId(String trimestreId) {
        this.trimestreId = trimestreId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public AnneeAcademic getAnneeAcademicId() {
        return anneeAcademicId;
    }

    public void setAnneeAcademicId(AnneeAcademic anneeAcademicId) {
        this.anneeAcademicId = anneeAcademicId;
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
    public Set<Sequence> getSequenceSet() {
        return sequenceSet;
    }

    public void setSequenceSet(Set<Sequence> sequenceSet) {
        this.sequenceSet = sequenceSet;
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
        hash += (trimestreId != null ? trimestreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trimestre)) {
            return false;
        }
        Trimestre other = (Trimestre) object;
        if ((this.trimestreId == null && other.trimestreId != null) || (this.trimestreId != null && !this.trimestreId.equals(other.trimestreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Trimestre[ trimestreId=" + trimestreId + " ]";
    }
    
}

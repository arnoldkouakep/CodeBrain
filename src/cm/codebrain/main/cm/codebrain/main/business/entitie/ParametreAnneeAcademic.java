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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author KSA-INET
 */
@Entity
@Table(name = "PARAMETRE_ANNEE_ACADEMIC", catalog = "", schema = "BRAIN", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ETABLISSEMENT_ID", "SESSION"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametreAnneeAcademic.findAll", query = "SELECT p FROM ParametreAnneeAcademic p")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@parametreAnneeAcademic", scope = ParametreAnneeAcademic.class, resolver = ObjectIdResolver.class)
public class ParametreAnneeAcademic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PARAMETRE_ANNEE_ACADEMIC_ID", nullable = false, length = 64)
    private String parametreAnneeAcademicId;
    @Column(length = 64)
    private String statut;
    @Column(name = "STATE_DB", length = 64)
    private String stateDb;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @JoinColumn(name = "SESSION", referencedColumnName = "SESSION", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AnneeAcademic session;
    @JoinColumn(name = "ETABLISSEMENT_ID", referencedColumnName = "ETABLISSEMENT_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Etablissement etablissementId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userCreated;

    public ParametreAnneeAcademic() {
    }

    public ParametreAnneeAcademic(String parametreAnneeAcademicId) {
        this.parametreAnneeAcademicId = parametreAnneeAcademicId;
    }

    public String getParametreAnneeAcademicId() {
        return parametreAnneeAcademicId;
    }

    public void setParametreAnneeAcademicId(String parametreAnneeAcademicId) {
        this.parametreAnneeAcademicId = parametreAnneeAcademicId;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
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

    public AnneeAcademic getSession() {
        return session;
    }

    public void setSession(AnneeAcademic session) {
        this.session = session;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parametreAnneeAcademicId != null ? parametreAnneeAcademicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametreAnneeAcademic)) {
            return false;
        }
        ParametreAnneeAcademic other = (ParametreAnneeAcademic) object;
        if ((this.parametreAnneeAcademicId == null && other.parametreAnneeAcademicId != null) || (this.parametreAnneeAcademicId != null && !this.parametreAnneeAcademicId.equals(other.parametreAnneeAcademicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.ParametreAnneeAcademic[ parametreAnneeAcademicId=" + parametreAnneeAcademicId + " ]";
    }
    
}

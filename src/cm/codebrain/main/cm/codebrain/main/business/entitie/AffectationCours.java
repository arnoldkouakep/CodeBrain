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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author KSA-INET
 */
@Entity
@Table(name = "AFFECTATION_COURS", catalog = "", schema = "BRAIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AffectationCours.findAll", query = "SELECT a FROM AffectationCours a")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@affectationCours", scope = AffectationCours.class, resolver = ObjectIdResolver.class)
public class AffectationCours implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AFFECTATION_COURS_ID", nullable = false, length = 64)
    private String affectationCoursId;
    @Column(name = "STATE_DB", length = 64)
    private String stateDb;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @JoinColumn(name = "COURS_ID", referencedColumnName = "COURS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cours coursId;
    @JoinColumn(name = "ENSEIGNANT_ID", referencedColumnName = "ENSEIGNANT_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Enseignant enseignantId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userCreated;

    public AffectationCours() {
    }

    public AffectationCours(String affectationCoursId) {
        this.affectationCoursId = affectationCoursId;
    }

    public String getAffectationCoursId() {
        return affectationCoursId;
    }

    public void setAffectationCoursId(String affectationCoursId) {
        this.affectationCoursId = affectationCoursId;
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

    public Cours getCoursId() {
        return coursId;
    }

    public void setCoursId(Cours coursId) {
        this.coursId = coursId;
    }

    public Enseignant getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(Enseignant enseignantId) {
        this.enseignantId = enseignantId;
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
        hash += (affectationCoursId != null ? affectationCoursId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AffectationCours)) {
            return false;
        }
        AffectationCours other = (AffectationCours) object;
        if ((this.affectationCoursId == null && other.affectationCoursId != null) || (this.affectationCoursId != null && !this.affectationCoursId.equals(other.affectationCoursId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.AffectationCours[ affectationCoursId=" + affectationCoursId + " ]";
    }
    
}

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
    @NamedQuery(name = "Cours.findAll", query = "SELECT c FROM Cours c")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@cours", scope = Cours.class, resolver = ObjectIdResolver.class)
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COURS_ID", nullable = false, length = 64)
    private String coursId;
    @Basic(optional = false)
    @Column(nullable = false, length = 64)
    private String code;
    @Basic(optional = false)
    @Column(name = "LIBELLE_FR", nullable = false, length = 64)
    private String libelleFr;
    @Basic(optional = false)
    @Column(name = "LIBELLE_EN", nullable = false, length = 64)
    private String libelleEn;
    @Basic(optional = false)
    @Column(nullable = false)
    private double credit;
    @Column(name = "STATE_DB", length = 64)
    private String stateDb;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @JoinColumn(name = "GROUPE_ID", referencedColumnName = "GROUPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Groupe groupeId;
    @JoinColumn(name = "CLASSE_ID", referencedColumnName = "CLASSE_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classe classeId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coursId", fetch = FetchType.LAZY)
    private Set<AffectationCours> affectationCoursSet;

    public Cours() {
    }

    public Cours(String coursId) {
        this.coursId = coursId;
    }

    public Cours(String coursId, String code, String libelleFr, String libelleEn, double credit) {
        this.coursId = coursId;
        this.code = code;
        this.libelleFr = libelleFr;
        this.libelleEn = libelleEn;
        this.credit = credit;
    }

    public String getCoursId() {
        return coursId;
    }

    public void setCoursId(String coursId) {
        this.coursId = coursId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelleFr() {
        return libelleFr;
    }

    public void setLibelleFr(String libelleFr) {
        this.libelleFr = libelleFr;
    }

    public String getLibelleEn() {
        return libelleEn;
    }

    public void setLibelleEn(String libelleEn) {
        this.libelleEn = libelleEn;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
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

    public Groupe getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Groupe groupeId) {
        this.groupeId = groupeId;
    }

    public Classe getClasseId() {
        return classeId;
    }

    public void setClasseId(Classe classeId) {
        this.classeId = classeId;
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
        hash += (coursId != null ? coursId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cours)) {
            return false;
        }
        Cours other = (Cours) object;
        if ((this.coursId == null && other.coursId != null) || (this.coursId != null && !this.coursId.equals(other.coursId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Cours[ coursId=" + coursId + " ]";
    }

}

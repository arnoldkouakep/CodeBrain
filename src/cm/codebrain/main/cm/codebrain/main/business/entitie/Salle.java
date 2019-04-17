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
    @NamedQuery(name = "Salle.findAll", query = "SELECT s FROM Salle s")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@cb")
public class Salle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SALLE_ID", nullable = false, length = 64)
    private String salleId;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salleId", fetch = FetchType.LAZY)
    private Set<Student> studentSet;
    @JoinColumn(name = "CLASSE_ID", referencedColumnName = "CLASSE_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classe classeId;
    @JoinColumn(name = "GROUPE_ID", referencedColumnName = "GROUPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Groupe groupeId;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userCreated;

    public Salle() {
    }

    public Salle(String salleId) {
        this.salleId = salleId;
    }

    public Salle(String salleId, String code, String intitule) {
        this.salleId = salleId;
        this.code = code;
        this.intitule = intitule;
    }

    public String getSalleId() {
        return salleId;
    }

    public void setSalleId(String salleId) {
        this.salleId = salleId;
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
    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    public Classe getClasseId() {
        return classeId;
    }

    public void setClasseId(Classe classeId) {
        this.classeId = classeId;
    }

    public Groupe getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Groupe groupeId) {
        this.groupeId = groupeId;
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
        hash += (salleId != null ? salleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salle)) {
            return false;
        }
        Salle other = (Salle) object;
        if ((this.salleId == null && other.salleId != null) || (this.salleId != null && !this.salleId.equals(other.salleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Salle[ salleId=" + salleId + " ]";
    }
    
}

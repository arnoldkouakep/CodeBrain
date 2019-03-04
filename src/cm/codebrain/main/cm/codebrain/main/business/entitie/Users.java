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
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findByUsersId", query = "SELECT u FROM Users u WHERE u.usersId = :usersId")
    , @NamedQuery(name = "Users.findByUserCode", query = "SELECT u FROM Users u WHERE u.userCode = :userCode")
    , @NamedQuery(name = "Users.findByLogin", query = "SELECT u FROM Users u WHERE u.login = :login")
    , @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password")
    , @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "Users.findByTel", query = "SELECT u FROM Users u WHERE u.tel = :tel")
    , @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email")
    , @NamedQuery(name = "Users.findByCni", query = "SELECT u FROM Users u WHERE u.cni = :cni")
    , @NamedQuery(name = "Users.findByStateDb", query = "SELECT u FROM Users u WHERE u.stateDb = :stateDb")
    , @NamedQuery(name = "Users.findByDtCreated", query = "SELECT u FROM Users u WHERE u.dtCreated = :dtCreated")
    , @NamedQuery(name = "Users.findByDtModified", query = "SELECT u FROM Users u WHERE u.dtModified = :dtModified")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@cb", scope = Users.class)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USERS_ID", nullable = false, length = 64)
    private String usersId;
    @Basic(optional = false)
    @Column(name = "USER_CODE", nullable = false, length = 64)
    private String userCode;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String login;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String password;
    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;
    @Column(name = "LAST_NAME", length = 100)
    private String lastName;
    @Column(length = 20)
    private String tel;
    @Column(length = 200)
    private String email;
    @Column(length = 45)
    private String cni;
    @Column(name = "STATE_DB", length = 64)
    private String stateDb;
    @Column(name = "DT_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dtCreated;
    @Column(name = "DT_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModified;
    @OneToMany(mappedBy = "userModified")
    private Collection<Student> studentCollection;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Student> studentCollection1;
    @OneToMany(mappedBy = "userModified")
    private Collection<Groupe> groupeCollection;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Groupe> groupeCollection1;
    @OneToMany(mappedBy = "userModified")
    private Collection<Salle> salleCollection;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Salle> salleCollection1;
    @OneToMany(mappedBy = "userModified")
    private Collection<Sections> sectionsCollection;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Sections> sectionsCollection1;
    @OneToMany(mappedBy = "userModified")
    private Collection<Levels> levelsCollection;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Levels> levelsCollection1;
    @OneToMany(mappedBy = "userModified")
    private Collection<Etablissement> etablissementCollection;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Etablissement> etablissementCollection1;
    @JoinColumn(name = "LEVELS_ID", referencedColumnName = "LEVELS_ID", nullable = false)
    @ManyToOne(optional = false)
    private Levels levelsId;
    @OneToMany(mappedBy = "userModified")
    private Collection<Users> usersCollection;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne
    private Users userModified;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Users> usersCollection1;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID")
    @ManyToOne
    private Users userCreated;
    @OneToMany(mappedBy = "userModified")
    private Collection<Widget> widgetCollection;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Widget> widgetCollection1;
    @OneToMany(mappedBy = "userModified")
    private Collection<Classe> classeCollection;
    @OneToMany(mappedBy = "userCreated")
    private Collection<Classe> classeCollection1;

    public Users() {
    }

    public Users(String usersId) {
        this.usersId = usersId;
    }

    public Users(String usersId, String userCode, String login, String password) {
        this.usersId = usersId;
        this.userCode = userCode;
        this.login = login;
        this.password = password;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
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
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection1() {
        return studentCollection1;
    }

    public void setStudentCollection1(Collection<Student> studentCollection1) {
        this.studentCollection1 = studentCollection1;
    }

    @XmlTransient
    public Collection<Groupe> getGroupeCollection() {
        return groupeCollection;
    }

    public void setGroupeCollection(Collection<Groupe> groupeCollection) {
        this.groupeCollection = groupeCollection;
    }

    @XmlTransient
    public Collection<Groupe> getGroupeCollection1() {
        return groupeCollection1;
    }

    public void setGroupeCollection1(Collection<Groupe> groupeCollection1) {
        this.groupeCollection1 = groupeCollection1;
    }

    @XmlTransient
    public Collection<Salle> getSalleCollection() {
        return salleCollection;
    }

    public void setSalleCollection(Collection<Salle> salleCollection) {
        this.salleCollection = salleCollection;
    }

    @XmlTransient
    public Collection<Salle> getSalleCollection1() {
        return salleCollection1;
    }

    public void setSalleCollection1(Collection<Salle> salleCollection1) {
        this.salleCollection1 = salleCollection1;
    }

    @XmlTransient
    public Collection<Sections> getSectionsCollection() {
        return sectionsCollection;
    }

    public void setSectionsCollection(Collection<Sections> sectionsCollection) {
        this.sectionsCollection = sectionsCollection;
    }

    @XmlTransient
    public Collection<Sections> getSectionsCollection1() {
        return sectionsCollection1;
    }

    public void setSectionsCollection1(Collection<Sections> sectionsCollection1) {
        this.sectionsCollection1 = sectionsCollection1;
    }

    @XmlTransient
    public Collection<Levels> getLevelsCollection() {
        return levelsCollection;
    }

    public void setLevelsCollection(Collection<Levels> levelsCollection) {
        this.levelsCollection = levelsCollection;
    }

    @XmlTransient
    public Collection<Levels> getLevelsCollection1() {
        return levelsCollection1;
    }

    public void setLevelsCollection1(Collection<Levels> levelsCollection1) {
        this.levelsCollection1 = levelsCollection1;
    }

    @XmlTransient
    public Collection<Etablissement> getEtablissementCollection() {
        return etablissementCollection;
    }

    public void setEtablissementCollection(Collection<Etablissement> etablissementCollection) {
        this.etablissementCollection = etablissementCollection;
    }

    @XmlTransient
    public Collection<Etablissement> getEtablissementCollection1() {
        return etablissementCollection1;
    }

    public void setEtablissementCollection1(Collection<Etablissement> etablissementCollection1) {
        this.etablissementCollection1 = etablissementCollection1;
    }

    public Levels getLevelsId() {
        return levelsId;
    }

    public void setLevelsId(Levels levelsId) {
        this.levelsId = levelsId;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    public Users getUserModified() {
        return userModified;
    }

    public void setUserModified(Users userModified) {
        this.userModified = userModified;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection1() {
        return usersCollection1;
    }

    public void setUsersCollection1(Collection<Users> usersCollection1) {
        this.usersCollection1 = usersCollection1;
    }

    public Users getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Users userCreated) {
        this.userCreated = userCreated;
    }

    @XmlTransient
    public Collection<Widget> getWidgetCollection() {
        return widgetCollection;
    }

    public void setWidgetCollection(Collection<Widget> widgetCollection) {
        this.widgetCollection = widgetCollection;
    }

    @XmlTransient
    public Collection<Widget> getWidgetCollection1() {
        return widgetCollection1;
    }

    public void setWidgetCollection1(Collection<Widget> widgetCollection1) {
        this.widgetCollection1 = widgetCollection1;
    }

    @XmlTransient
    public Collection<Classe> getClasseCollection() {
        return classeCollection;
    }

    public void setClasseCollection(Collection<Classe> classeCollection) {
        this.classeCollection = classeCollection;
    }

    @XmlTransient
    public Collection<Classe> getClasseCollection1() {
        return classeCollection1;
    }

    public void setClasseCollection1(Collection<Classe> classeCollection1) {
        this.classeCollection1 = classeCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersId != null ? usersId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.usersId == null && other.usersId != null) || (this.usersId != null && !this.usersId.equals(other.usersId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cm.codebrain.main.business.entitie.Users[ usersId=" + usersId + " ]";
    }
    
}

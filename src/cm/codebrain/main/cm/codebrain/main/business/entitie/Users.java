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
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")})
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@users", scope = Users.class, resolver = ObjectIdResolver.class)
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
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Enseignant> enseignantSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Enseignant> enseignantSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Salle> salleSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Salle> salleSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<AffectationCours> affectationCoursSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<AffectationCours> affectationCoursSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Levels> levelsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Levels> levelsSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<AnneeAcademic> anneeAcademicSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<AnneeAcademic> anneeAcademicSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Etablissement> etablissementSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Etablissement> etablissementSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Classe> classeSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Classe> classeSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Student> studentSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Student> studentSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Groupe> groupeSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Groupe> groupeSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Sequence> sequenceSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Sequence> sequenceSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Profession> professionSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Profession> professionSet1;
    @JoinColumn(name = "LEVELS_ID", referencedColumnName = "LEVELS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Levels levelsId;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Users> usersSet;
    @JoinColumn(name = "USER_MODIFIED", referencedColumnName = "USERS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userModified;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Users> usersSet1;
    @JoinColumn(name = "USER_CREATED", referencedColumnName = "USERS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userCreated;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<ParametreAnneeAcademic> parametreAnneeAcademicSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<ParametreAnneeAcademic> parametreAnneeAcademicSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Section> sectionSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Section> sectionSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Widget> widgetSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Widget> widgetSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Cours> coursSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Cours> coursSet1;
    @OneToMany(mappedBy = "userModified", fetch = FetchType.LAZY)
    private Set<Trimestre> trimestreSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated", fetch = FetchType.LAZY)
    private Set<Trimestre> trimestreSet1;


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
    public Set<Enseignant> getEnseignantSet() {
        return enseignantSet;
    }

    public void setEnseignantSet(Set<Enseignant> enseignantSet) {
        this.enseignantSet = enseignantSet;
    }

    @XmlTransient
    public Set<Enseignant> getEnseignantSet1() {
        return enseignantSet1;
    }

    public void setEnseignantSet1(Set<Enseignant> enseignantSet1) {
        this.enseignantSet1 = enseignantSet1;
    }

    @XmlTransient
    public Set<Salle> getSalleSet() {
        return salleSet;
    }

    public void setSalleSet(Set<Salle> salleSet) {
        this.salleSet = salleSet;
    }

    @XmlTransient
    public Set<Salle> getSalleSet1() {
        return salleSet1;
    }

    public void setSalleSet1(Set<Salle> salleSet1) {
        this.salleSet1 = salleSet1;
    }

    @XmlTransient
    public Set<AffectationCours> getAffectationCoursSet() {
        return affectationCoursSet;
    }

    public void setAffectationCoursSet(Set<AffectationCours> affectationCoursSet) {
        this.affectationCoursSet = affectationCoursSet;
    }

    @XmlTransient
    public Set<AffectationCours> getAffectationCoursSet1() {
        return affectationCoursSet1;
    }

    public void setAffectationCoursSet1(Set<AffectationCours> affectationCoursSet1) {
        this.affectationCoursSet1 = affectationCoursSet1;
    }

    @XmlTransient
    public Set<Levels> getLevelsSet() {
        return levelsSet;
    }

    public void setLevelsSet(Set<Levels> levelsSet) {
        this.levelsSet = levelsSet;
    }

    @XmlTransient
    public Set<Levels> getLevelsSet1() {
        return levelsSet1;
    }

    public void setLevelsSet1(Set<Levels> levelsSet1) {
        this.levelsSet1 = levelsSet1;
    }

    @XmlTransient
    public Set<AnneeAcademic> getAnneeAcademicSet() {
        return anneeAcademicSet;
    }

    public void setAnneeAcademicSet(Set<AnneeAcademic> anneeAcademicSet) {
        this.anneeAcademicSet = anneeAcademicSet;
    }

    @XmlTransient
    public Set<AnneeAcademic> getAnneeAcademicSet1() {
        return anneeAcademicSet1;
    }

    public void setAnneeAcademicSet1(Set<AnneeAcademic> anneeAcademicSet1) {
        this.anneeAcademicSet1 = anneeAcademicSet1;
    }

    @XmlTransient
    public Set<Etablissement> getEtablissementSet() {
        return etablissementSet;
    }

    public void setEtablissementSet(Set<Etablissement> etablissementSet) {
        this.etablissementSet = etablissementSet;
    }

    @XmlTransient
    public Set<Etablissement> getEtablissementSet1() {
        return etablissementSet1;
    }

    public void setEtablissementSet1(Set<Etablissement> etablissementSet1) {
        this.etablissementSet1 = etablissementSet1;
    }

    @XmlTransient
    public Set<Classe> getClasseSet() {
        return classeSet;
    }

    public void setClasseSet(Set<Classe> classeSet) {
        this.classeSet = classeSet;
    }

    @XmlTransient
    public Set<Classe> getClasseSet1() {
        return classeSet1;
    }

    public void setClasseSet1(Set<Classe> classeSet1) {
        this.classeSet1 = classeSet1;
    }

    @XmlTransient
    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    @XmlTransient
    public Set<Student> getStudentSet1() {
        return studentSet1;
    }

    public void setStudentSet1(Set<Student> studentSet1) {
        this.studentSet1 = studentSet1;
    }

    @XmlTransient
    public Set<Groupe> getGroupeSet() {
        return groupeSet;
    }

    public void setGroupeSet(Set<Groupe> groupeSet) {
        this.groupeSet = groupeSet;
    }

    @XmlTransient
    public Set<Groupe> getGroupeSet1() {
        return groupeSet1;
    }

    public void setGroupeSet1(Set<Groupe> groupeSet1) {
        this.groupeSet1 = groupeSet1;
    }

    @XmlTransient
    public Set<Sequence> getSequenceSet() {
        return sequenceSet;
    }

    public void setSequenceSet(Set<Sequence> sequenceSet) {
        this.sequenceSet = sequenceSet;
    }

    @XmlTransient
    public Set<Sequence> getSequenceSet1() {
        return sequenceSet1;
    }

    public void setSequenceSet1(Set<Sequence> sequenceSet1) {
        this.sequenceSet1 = sequenceSet1;
    }

    @XmlTransient
    public Set<Profession> getProfessionSet() {
        return professionSet;
    }

    public void setProfessionSet(Set<Profession> professionSet) {
        this.professionSet = professionSet;
    }

    @XmlTransient
    public Set<Profession> getProfessionSet1() {
        return professionSet1;
    }

    public void setProfessionSet1(Set<Profession> professionSet1) {
        this.professionSet1 = professionSet1;
    }

    public Levels getLevelsId() {
        return levelsId;
    }

    public void setLevelsId(Levels levelsId) {
        this.levelsId = levelsId;
    }

    @XmlTransient
    public Set<Users> getUsersSet() {
        return usersSet;
    }

    public void setUsersSet(Set<Users> usersSet) {
        this.usersSet = usersSet;
    }

    public Users getUserModified() {
        return userModified;
    }

    public void setUserModified(Users userModified) {
        this.userModified = userModified;
    }

    @XmlTransient
    public Set<Users> getUsersSet1() {
        return usersSet1;
    }

    public void setUsersSet1(Set<Users> usersSet1) {
        this.usersSet1 = usersSet1;
    }

    public Users getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Users userCreated) {
        this.userCreated = userCreated;
    }

    @XmlTransient
    public Set<ParametreAnneeAcademic> getParametreAnneeAcademicSet() {
        return parametreAnneeAcademicSet;
    }

    public void setParametreAnneeAcademicSet(Set<ParametreAnneeAcademic> parametreAnneeAcademicSet) {
        this.parametreAnneeAcademicSet = parametreAnneeAcademicSet;
    }

    @XmlTransient
    public Set<ParametreAnneeAcademic> getParametreAnneeAcademicSet1() {
        return parametreAnneeAcademicSet1;
    }

    public void setParametreAnneeAcademicSet1(Set<ParametreAnneeAcademic> parametreAnneeAcademicSet1) {
        this.parametreAnneeAcademicSet1 = parametreAnneeAcademicSet1;
    }

    @XmlTransient
    public Set<Section> getSectionSet() {
        return sectionSet;
    }

    public void setSectionSet(Set<Section> sectionSet) {
        this.sectionSet = sectionSet;
    }

    @XmlTransient
    public Set<Section> getSectionSet1() {
        return sectionSet1;
    }

    public void setSectionSet1(Set<Section> sectionSet1) {
        this.sectionSet1 = sectionSet1;
    }

    @XmlTransient
    public Set<Widget> getWidgetSet() {
        return widgetSet;
    }

    public void setWidgetSet(Set<Widget> widgetSet) {
        this.widgetSet = widgetSet;
    }

    @XmlTransient
    public Set<Widget> getWidgetSet1() {
        return widgetSet1;
    }

    public void setWidgetSet1(Set<Widget> widgetSet1) {
        this.widgetSet1 = widgetSet1;
    }

    @XmlTransient
    public Set<Cours> getCoursSet() {
        return coursSet;
    }

    public void setCoursSet(Set<Cours> coursSet) {
        this.coursSet = coursSet;
    }

    @XmlTransient
    public Set<Cours> getCoursSet1() {
        return coursSet1;
    }

    public void setCoursSet1(Set<Cours> coursSet1) {
        this.coursSet1 = coursSet1;
    }

    @XmlTransient
    public Set<Trimestre> getTrimestreSet() {
        return trimestreSet;
    }

    public void setTrimestreSet(Set<Trimestre> trimestreSet) {
        this.trimestreSet = trimestreSet;
    }

    @XmlTransient
    public Set<Trimestre> getTrimestreSet1() {
        return trimestreSet1;
    }

    public void setTrimestreSet1(Set<Trimestre> trimestreSet1) {
        this.trimestreSet1 = trimestreSet1;
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

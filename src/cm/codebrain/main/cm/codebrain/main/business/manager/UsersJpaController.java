/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Levels;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Student;
import java.util.HashSet;
import java.util.Set;
import cm.codebrain.main.business.entitie.Groupe;
import cm.codebrain.main.business.entitie.Salle;
import cm.codebrain.main.business.entitie.AnneeAcademic;
import cm.codebrain.main.business.entitie.Etablissement;
import cm.codebrain.main.business.entitie.Section;
import cm.codebrain.main.business.entitie.Widget;
import cm.codebrain.main.business.entitie.Classe;
import cm.codebrain.main.business.manager.exceptions.IllegalOrphanException;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class UsersJpaController extends CodeBrainEntityManager implements Serializable {

    public UsersJpaController(EntityManager em) {
        setEntityManager(em);
    }
    
    public void create(Users users) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (users.getStudentSet() == null) {
            users.setStudentSet(new HashSet<Student>());
        }
        if (users.getStudentSet1() == null) {
            users.setStudentSet1(new HashSet<Student>());
        }
        if (users.getGroupeSet() == null) {
            users.setGroupeSet(new HashSet<Groupe>());
        }
        if (users.getGroupeSet1() == null) {
            users.setGroupeSet1(new HashSet<Groupe>());
        }
        if (users.getSalleSet() == null) {
            users.setSalleSet(new HashSet<Salle>());
        }
        if (users.getSalleSet1() == null) {
            users.setSalleSet1(new HashSet<Salle>());
        }
        if (users.getLevelsSet() == null) {
            users.setLevelsSet(new HashSet<Levels>());
        }
        if (users.getLevelsSet1() == null) {
            users.setLevelsSet1(new HashSet<Levels>());
        }
        if (users.getAnneeAcademicSet() == null) {
            users.setAnneeAcademicSet(new HashSet<AnneeAcademic>());
        }
        if (users.getAnneeAcademicSet1() == null) {
            users.setAnneeAcademicSet1(new HashSet<AnneeAcademic>());
        }
        if (users.getEtablissementSet() == null) {
            users.setEtablissementSet(new HashSet<Etablissement>());
        }
        if (users.getEtablissementSet1() == null) {
            users.setEtablissementSet1(new HashSet<Etablissement>());
        }
        if (users.getUsersSet() == null) {
            users.setUsersSet(new HashSet<Users>());
        }
        if (users.getUsersSet1() == null) {
            users.setUsersSet1(new HashSet<Users>());
        }
        if (users.getSectionSet() == null) {
            users.setSectionSet(new HashSet<Section>());
        }
        if (users.getSectionSet1() == null) {
            users.setSectionSet1(new HashSet<Section>());
        }
        if (users.getWidgetSet() == null) {
            users.setWidgetSet(new HashSet<Widget>());
        }
        if (users.getWidgetSet1() == null) {
            users.setWidgetSet1(new HashSet<Widget>());
        }
        if (users.getClasseSet() == null) {
            users.setClasseSet(new HashSet<Classe>());
        }
        if (users.getClasseSet1() == null) {
            users.setClasseSet1(new HashSet<Classe>());
        }
        List<String> illegalOrphanMessages = null;
        Levels levelsIdOrphanCheck = users.getLevelsId();
        if (levelsIdOrphanCheck != null) {
            Users oldUserModifiedOfLevelsId = levelsIdOrphanCheck.getUserModified();
            if (oldUserModifiedOfLevelsId == null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Levels " + levelsIdOrphanCheck + " already has an item of type Users whose levelsId column cannot be null. Please make another selection for the levelsId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Levels levelsId = users.getLevelsId();
            if (levelsId != null) {
                levelsId = (Levels) refreshEntity(levelsId.getClass(), levelsId.getLevelsId());
                users.setLevelsId(levelsId);
            }
            Users userModified = users.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                users.setUserModified(userModified);
            }
            Users userCreated = users.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                users.setUserCreated(userCreated);
            }
            Set<Student> attachedStudentSet = new HashSet<Student>();
            for (Student studentSetStudentToAttach : users.getStudentSet()) {
                studentSetStudentToAttach = (Student) refreshEntity(studentSetStudentToAttach.getClass(), studentSetStudentToAttach.getStudentId());
                attachedStudentSet.add(studentSetStudentToAttach);
            }
            users.setStudentSet(attachedStudentSet);
            Set<Student> attachedStudentSet1 = new HashSet<Student>();
            for (Student studentSet1StudentToAttach : users.getStudentSet1()) {
                studentSet1StudentToAttach = (Student) refreshEntity(studentSet1StudentToAttach.getClass(), studentSet1StudentToAttach.getStudentId());
                attachedStudentSet1.add(studentSet1StudentToAttach);
            }
            users.setStudentSet1(attachedStudentSet1);
            Set<Groupe> attachedGroupeSet = new HashSet<Groupe>();
            for (Groupe groupeSetGroupeToAttach : users.getGroupeSet()) {
                groupeSetGroupeToAttach = (Groupe) refreshEntity(groupeSetGroupeToAttach.getClass(), groupeSetGroupeToAttach.getGroupeId());
                attachedGroupeSet.add(groupeSetGroupeToAttach);
            }
            users.setGroupeSet(attachedGroupeSet);
            Set<Groupe> attachedGroupeSet1 = new HashSet<Groupe>();
            for (Groupe groupeSet1GroupeToAttach : users.getGroupeSet1()) {
                groupeSet1GroupeToAttach = (Groupe) refreshEntity(groupeSet1GroupeToAttach.getClass(), groupeSet1GroupeToAttach.getGroupeId());
                attachedGroupeSet1.add(groupeSet1GroupeToAttach);
            }
            users.setGroupeSet1(attachedGroupeSet1);
            Set<Salle> attachedSalleSet = new HashSet<Salle>();
            for (Salle salleSetSalleToAttach : users.getSalleSet()) {
                salleSetSalleToAttach = (Salle) refreshEntity(salleSetSalleToAttach.getClass(), salleSetSalleToAttach.getSalleId());
                attachedSalleSet.add(salleSetSalleToAttach);
            }
            users.setSalleSet(attachedSalleSet);
            Set<Salle> attachedSalleSet1 = new HashSet<Salle>();
            for (Salle salleSet1SalleToAttach : users.getSalleSet1()) {
                salleSet1SalleToAttach = (Salle) refreshEntity(salleSet1SalleToAttach.getClass(), salleSet1SalleToAttach.getSalleId());
                attachedSalleSet1.add(salleSet1SalleToAttach);
            }
            users.setSalleSet1(attachedSalleSet1);
            Set<Levels> attachedLevelsSet = new HashSet<Levels>();
            for (Levels levelsSetLevelsToAttach : users.getLevelsSet()) {
                levelsSetLevelsToAttach = (Levels) refreshEntity(levelsSetLevelsToAttach.getClass(), levelsSetLevelsToAttach.getLevelsId());
                attachedLevelsSet.add(levelsSetLevelsToAttach);
            }
            users.setLevelsSet(attachedLevelsSet);
            Set<Levels> attachedLevelsSet1 = new HashSet<Levels>();
            for (Levels levelsSet1LevelsToAttach : users.getLevelsSet1()) {
                levelsSet1LevelsToAttach = (Levels) refreshEntity(levelsSet1LevelsToAttach.getClass(), levelsSet1LevelsToAttach.getLevelsId());
                attachedLevelsSet1.add(levelsSet1LevelsToAttach);
            }
            users.setLevelsSet1(attachedLevelsSet1);
            Set<AnneeAcademic> attachedAnneeAcademicSet = new HashSet<AnneeAcademic>();
            for (AnneeAcademic anneeAcademicSetAnneeAcademicToAttach : users.getAnneeAcademicSet()) {
                anneeAcademicSetAnneeAcademicToAttach = (AnneeAcademic) refreshEntity(anneeAcademicSetAnneeAcademicToAttach.getClass(), anneeAcademicSetAnneeAcademicToAttach.getAnneeAcademicId());
                attachedAnneeAcademicSet.add(anneeAcademicSetAnneeAcademicToAttach);
            }
            users.setAnneeAcademicSet(attachedAnneeAcademicSet);
            Set<AnneeAcademic> attachedAnneeAcademicSet1 = new HashSet<AnneeAcademic>();
            for (AnneeAcademic anneeAcademicSet1AnneeAcademicToAttach : users.getAnneeAcademicSet1()) {
                anneeAcademicSet1AnneeAcademicToAttach = (AnneeAcademic) refreshEntity(anneeAcademicSet1AnneeAcademicToAttach.getClass(), anneeAcademicSet1AnneeAcademicToAttach.getAnneeAcademicId());
                attachedAnneeAcademicSet1.add(anneeAcademicSet1AnneeAcademicToAttach);
            }
            users.setAnneeAcademicSet1(attachedAnneeAcademicSet1);
            Set<Etablissement> attachedEtablissementSet = new HashSet<Etablissement>();
            for (Etablissement etablissementSetEtablissementToAttach : users.getEtablissementSet()) {
                etablissementSetEtablissementToAttach = (Etablissement) refreshEntity(etablissementSetEtablissementToAttach.getClass(), etablissementSetEtablissementToAttach.getEtablissementId());
                attachedEtablissementSet.add(etablissementSetEtablissementToAttach);
            }
            users.setEtablissementSet(attachedEtablissementSet);
            Set<Etablissement> attachedEtablissementSet1 = new HashSet<Etablissement>();
            for (Etablissement etablissementSet1EtablissementToAttach : users.getEtablissementSet1()) {
                etablissementSet1EtablissementToAttach = (Etablissement) refreshEntity(etablissementSet1EtablissementToAttach.getClass(), etablissementSet1EtablissementToAttach.getEtablissementId());
                attachedEtablissementSet1.add(etablissementSet1EtablissementToAttach);
            }
            users.setEtablissementSet1(attachedEtablissementSet1);
            Set<Users> attachedUsersSet = new HashSet<Users>();
            for (Users usersSetUsersToAttach : users.getUsersSet()) {
                usersSetUsersToAttach = (Users) refreshEntity(usersSetUsersToAttach.getClass(), usersSetUsersToAttach.getUsersId());
                attachedUsersSet.add(usersSetUsersToAttach);
            }
            users.setUsersSet(attachedUsersSet);
            Set<Users> attachedUsersSet1 = new HashSet<Users>();
            for (Users usersSet1UsersToAttach : users.getUsersSet1()) {
                usersSet1UsersToAttach = (Users) refreshEntity(usersSet1UsersToAttach.getClass(), usersSet1UsersToAttach.getUsersId());
                attachedUsersSet1.add(usersSet1UsersToAttach);
            }
            users.setUsersSet1(attachedUsersSet1);
            Set<Section> attachedSectionSet = new HashSet<Section>();
            for (Section sectionSetSectionToAttach : users.getSectionSet()) {
                sectionSetSectionToAttach = (Section) refreshEntity(sectionSetSectionToAttach.getClass(), sectionSetSectionToAttach.getSectionId());
                attachedSectionSet.add(sectionSetSectionToAttach);
            }
            users.setSectionSet(attachedSectionSet);
            Set<Section> attachedSectionSet1 = new HashSet<Section>();
            for (Section sectionSet1SectionToAttach : users.getSectionSet1()) {
                sectionSet1SectionToAttach = (Section) refreshEntity(sectionSet1SectionToAttach.getClass(), sectionSet1SectionToAttach.getSectionId());
                attachedSectionSet1.add(sectionSet1SectionToAttach);
            }
            users.setSectionSet1(attachedSectionSet1);
            Set<Widget> attachedWidgetSet = new HashSet<Widget>();
            for (Widget widgetSetWidgetToAttach : users.getWidgetSet()) {
                widgetSetWidgetToAttach = (Widget) refreshEntity(widgetSetWidgetToAttach.getClass(), widgetSetWidgetToAttach.getWidgetId());
                attachedWidgetSet.add(widgetSetWidgetToAttach);
            }
            users.setWidgetSet(attachedWidgetSet);
            Set<Widget> attachedWidgetSet1 = new HashSet<Widget>();
            for (Widget widgetSet1WidgetToAttach : users.getWidgetSet1()) {
                widgetSet1WidgetToAttach = (Widget) refreshEntity(widgetSet1WidgetToAttach.getClass(), widgetSet1WidgetToAttach.getWidgetId());
                attachedWidgetSet1.add(widgetSet1WidgetToAttach);
            }
            users.setWidgetSet1(attachedWidgetSet1);
            Set<Classe> attachedClasseSet = new HashSet<Classe>();
            for (Classe classeSetClasseToAttach : users.getClasseSet()) {
                classeSetClasseToAttach = (Classe) refreshEntity(classeSetClasseToAttach.getClass(), classeSetClasseToAttach.getClasseId());
                attachedClasseSet.add(classeSetClasseToAttach);
            }
            users.setClasseSet(attachedClasseSet);
            Set<Classe> attachedClasseSet1 = new HashSet<Classe>();
            for (Classe classeSet1ClasseToAttach : users.getClasseSet1()) {
                classeSet1ClasseToAttach = (Classe) refreshEntity(classeSet1ClasseToAttach.getClass(), classeSet1ClasseToAttach.getClasseId());
                attachedClasseSet1.add(classeSet1ClasseToAttach);
            }
            users.setClasseSet1(attachedClasseSet1);
            persist(users);
//            if (levelsId != null) {
//                levelsId.setUserModified(users);
//                levelsId = em.merge(levelsId);
//            }
//            if (userModified != null) {
//                userModified.getUsersSet().add(users);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getUsersSet().add(users);
//                userCreated = em.merge(userCreated);
//            }
//            for (Student studentSetStudent : users.getStudentSet()) {
//                Users oldUserModifiedOfStudentSetStudent = studentSetStudent.getUserModified();
//                studentSetStudent.setUserModified(users);
//                studentSetStudent = em.merge(studentSetStudent);
//                if (oldUserModifiedOfStudentSetStudent != null) {
//                    oldUserModifiedOfStudentSetStudent.getStudentSet().remove(studentSetStudent);
//                    oldUserModifiedOfStudentSetStudent = em.merge(oldUserModifiedOfStudentSetStudent);
//                }
//            }
//            for (Student studentSet1Student : users.getStudentSet1()) {
//                Users oldUserCreatedOfStudentSet1Student = studentSet1Student.getUserCreated();
//                studentSet1Student.setUserCreated(users);
//                studentSet1Student = em.merge(studentSet1Student);
//                if (oldUserCreatedOfStudentSet1Student != null) {
//                    oldUserCreatedOfStudentSet1Student.getStudentSet1().remove(studentSet1Student);
//                    oldUserCreatedOfStudentSet1Student = em.merge(oldUserCreatedOfStudentSet1Student);
//                }
//            }
//            for (Groupe groupeSetGroupe : users.getGroupeSet()) {
//                Users oldUserModifiedOfGroupeSetGroupe = groupeSetGroupe.getUserModified();
//                groupeSetGroupe.setUserModified(users);
//                groupeSetGroupe = em.merge(groupeSetGroupe);
//                if (oldUserModifiedOfGroupeSetGroupe != null) {
//                    oldUserModifiedOfGroupeSetGroupe.getGroupeSet().remove(groupeSetGroupe);
//                    oldUserModifiedOfGroupeSetGroupe = em.merge(oldUserModifiedOfGroupeSetGroupe);
//                }
//            }
//            for (Groupe groupeSet1Groupe : users.getGroupeSet1()) {
//                Users oldUserCreatedOfGroupeSet1Groupe = groupeSet1Groupe.getUserCreated();
//                groupeSet1Groupe.setUserCreated(users);
//                groupeSet1Groupe = em.merge(groupeSet1Groupe);
//                if (oldUserCreatedOfGroupeSet1Groupe != null) {
//                    oldUserCreatedOfGroupeSet1Groupe.getGroupeSet1().remove(groupeSet1Groupe);
//                    oldUserCreatedOfGroupeSet1Groupe = em.merge(oldUserCreatedOfGroupeSet1Groupe);
//                }
//            }
//            for (Salle salleSetSalle : users.getSalleSet()) {
//                Users oldUserModifiedOfSalleSetSalle = salleSetSalle.getUserModified();
//                salleSetSalle.setUserModified(users);
//                salleSetSalle = em.merge(salleSetSalle);
//                if (oldUserModifiedOfSalleSetSalle != null) {
//                    oldUserModifiedOfSalleSetSalle.getSalleSet().remove(salleSetSalle);
//                    oldUserModifiedOfSalleSetSalle = em.merge(oldUserModifiedOfSalleSetSalle);
//                }
//            }
//            for (Salle salleSet1Salle : users.getSalleSet1()) {
//                Users oldUserCreatedOfSalleSet1Salle = salleSet1Salle.getUserCreated();
//                salleSet1Salle.setUserCreated(users);
//                salleSet1Salle = em.merge(salleSet1Salle);
//                if (oldUserCreatedOfSalleSet1Salle != null) {
//                    oldUserCreatedOfSalleSet1Salle.getSalleSet1().remove(salleSet1Salle);
//                    oldUserCreatedOfSalleSet1Salle = em.merge(oldUserCreatedOfSalleSet1Salle);
//                }
//            }
//            for (Levels levelsSetLevels : users.getLevelsSet()) {
//                Users oldUserModifiedOfLevelsSetLevels = levelsSetLevels.getUserModified();
//                levelsSetLevels.setUserModified(users);
//                levelsSetLevels = em.merge(levelsSetLevels);
//                if (oldUserModifiedOfLevelsSetLevels != null) {
//                    oldUserModifiedOfLevelsSetLevels.getLevelsSet().remove(levelsSetLevels);
//                    oldUserModifiedOfLevelsSetLevels = em.merge(oldUserModifiedOfLevelsSetLevels);
//                }
//            }
//            for (Levels levelsSet1Levels : users.getLevelsSet1()) {
//                Users oldUserCreatedOfLevelsSet1Levels = levelsSet1Levels.getUserCreated();
//                levelsSet1Levels.setUserCreated(users);
//                levelsSet1Levels = em.merge(levelsSet1Levels);
//                if (oldUserCreatedOfLevelsSet1Levels != null) {
//                    oldUserCreatedOfLevelsSet1Levels.getLevelsSet1().remove(levelsSet1Levels);
//                    oldUserCreatedOfLevelsSet1Levels = em.merge(oldUserCreatedOfLevelsSet1Levels);
//                }
//            }
//            for (AnneeAcademic anneeAcademicSetAnneeAcademic : users.getAnneeAcademicSet()) {
//                Users oldUserModifiedOfAnneeAcademicSetAnneeAcademic = anneeAcademicSetAnneeAcademic.getUserModified();
//                anneeAcademicSetAnneeAcademic.setUserModified(users);
//                anneeAcademicSetAnneeAcademic = em.merge(anneeAcademicSetAnneeAcademic);
//                if (oldUserModifiedOfAnneeAcademicSetAnneeAcademic != null) {
//                    oldUserModifiedOfAnneeAcademicSetAnneeAcademic.getAnneeAcademicSet().remove(anneeAcademicSetAnneeAcademic);
//                    oldUserModifiedOfAnneeAcademicSetAnneeAcademic = em.merge(oldUserModifiedOfAnneeAcademicSetAnneeAcademic);
//                }
//            }
//            for (AnneeAcademic anneeAcademicSet1AnneeAcademic : users.getAnneeAcademicSet1()) {
//                Users oldUserCreatedOfAnneeAcademicSet1AnneeAcademic = anneeAcademicSet1AnneeAcademic.getUserCreated();
//                anneeAcademicSet1AnneeAcademic.setUserCreated(users);
//                anneeAcademicSet1AnneeAcademic = em.merge(anneeAcademicSet1AnneeAcademic);
//                if (oldUserCreatedOfAnneeAcademicSet1AnneeAcademic != null) {
//                    oldUserCreatedOfAnneeAcademicSet1AnneeAcademic.getAnneeAcademicSet1().remove(anneeAcademicSet1AnneeAcademic);
//                    oldUserCreatedOfAnneeAcademicSet1AnneeAcademic = em.merge(oldUserCreatedOfAnneeAcademicSet1AnneeAcademic);
//                }
//            }
//            for (Etablissement etablissementSetEtablissement : users.getEtablissementSet()) {
//                Users oldUserModifiedOfEtablissementSetEtablissement = etablissementSetEtablissement.getUserModified();
//                etablissementSetEtablissement.setUserModified(users);
//                etablissementSetEtablissement = em.merge(etablissementSetEtablissement);
//                if (oldUserModifiedOfEtablissementSetEtablissement != null) {
//                    oldUserModifiedOfEtablissementSetEtablissement.getEtablissementSet().remove(etablissementSetEtablissement);
//                    oldUserModifiedOfEtablissementSetEtablissement = em.merge(oldUserModifiedOfEtablissementSetEtablissement);
//                }
//            }
//            for (Etablissement etablissementSet1Etablissement : users.getEtablissementSet1()) {
//                Users oldUserCreatedOfEtablissementSet1Etablissement = etablissementSet1Etablissement.getUserCreated();
//                etablissementSet1Etablissement.setUserCreated(users);
//                etablissementSet1Etablissement = em.merge(etablissementSet1Etablissement);
//                if (oldUserCreatedOfEtablissementSet1Etablissement != null) {
//                    oldUserCreatedOfEtablissementSet1Etablissement.getEtablissementSet1().remove(etablissementSet1Etablissement);
//                    oldUserCreatedOfEtablissementSet1Etablissement = em.merge(oldUserCreatedOfEtablissementSet1Etablissement);
//                }
//            }
//            for (Users usersSetUsers : users.getUsersSet()) {
//                Users oldUserModifiedOfUsersSetUsers = usersSetUsers.getUserModified();
//                usersSetUsers.setUserModified(users);
//                usersSetUsers = em.merge(usersSetUsers);
//                if (oldUserModifiedOfUsersSetUsers != null) {
//                    oldUserModifiedOfUsersSetUsers.getUsersSet().remove(usersSetUsers);
//                    oldUserModifiedOfUsersSetUsers = em.merge(oldUserModifiedOfUsersSetUsers);
//                }
//            }
//            for (Users usersSet1Users : users.getUsersSet1()) {
//                Users oldUserCreatedOfUsersSet1Users = usersSet1Users.getUserCreated();
//                usersSet1Users.setUserCreated(users);
//                usersSet1Users = em.merge(usersSet1Users);
//                if (oldUserCreatedOfUsersSet1Users != null) {
//                    oldUserCreatedOfUsersSet1Users.getUsersSet1().remove(usersSet1Users);
//                    oldUserCreatedOfUsersSet1Users = em.merge(oldUserCreatedOfUsersSet1Users);
//                }
//            }
//            for (Section sectionSetSection : users.getSectionSet()) {
//                Users oldUserModifiedOfSectionSetSection = sectionSetSection.getUserModified();
//                sectionSetSection.setUserModified(users);
//                sectionSetSection = em.merge(sectionSetSection);
//                if (oldUserModifiedOfSectionSetSection != null) {
//                    oldUserModifiedOfSectionSetSection.getSectionSet().remove(sectionSetSection);
//                    oldUserModifiedOfSectionSetSection = em.merge(oldUserModifiedOfSectionSetSection);
//                }
//            }
//            for (Section sectionSet1Section : users.getSectionSet1()) {
//                Users oldUserCreatedOfSectionSet1Section = sectionSet1Section.getUserCreated();
//                sectionSet1Section.setUserCreated(users);
//                sectionSet1Section = em.merge(sectionSet1Section);
//                if (oldUserCreatedOfSectionSet1Section != null) {
//                    oldUserCreatedOfSectionSet1Section.getSectionSet1().remove(sectionSet1Section);
//                    oldUserCreatedOfSectionSet1Section = em.merge(oldUserCreatedOfSectionSet1Section);
//                }
//            }
//            for (Widget widgetSetWidget : users.getWidgetSet()) {
//                Users oldUserModifiedOfWidgetSetWidget = widgetSetWidget.getUserModified();
//                widgetSetWidget.setUserModified(users);
//                widgetSetWidget = em.merge(widgetSetWidget);
//                if (oldUserModifiedOfWidgetSetWidget != null) {
//                    oldUserModifiedOfWidgetSetWidget.getWidgetSet().remove(widgetSetWidget);
//                    oldUserModifiedOfWidgetSetWidget = em.merge(oldUserModifiedOfWidgetSetWidget);
//                }
//            }
//            for (Widget widgetSet1Widget : users.getWidgetSet1()) {
//                Users oldUserCreatedOfWidgetSet1Widget = widgetSet1Widget.getUserCreated();
//                widgetSet1Widget.setUserCreated(users);
//                widgetSet1Widget = em.merge(widgetSet1Widget);
//                if (oldUserCreatedOfWidgetSet1Widget != null) {
//                    oldUserCreatedOfWidgetSet1Widget.getWidgetSet1().remove(widgetSet1Widget);
//                    oldUserCreatedOfWidgetSet1Widget = em.merge(oldUserCreatedOfWidgetSet1Widget);
//                }
//            }
//            for (Classe classeSetClasse : users.getClasseSet()) {
//                Users oldUserModifiedOfClasseSetClasse = classeSetClasse.getUserModified();
//                classeSetClasse.setUserModified(users);
//                classeSetClasse = em.merge(classeSetClasse);
//                if (oldUserModifiedOfClasseSetClasse != null) {
//                    oldUserModifiedOfClasseSetClasse.getClasseSet().remove(classeSetClasse);
//                    oldUserModifiedOfClasseSetClasse = em.merge(oldUserModifiedOfClasseSetClasse);
//                }
//            }
//            for (Classe classeSet1Classe : users.getClasseSet1()) {
//                Users oldUserCreatedOfClasseSet1Classe = classeSet1Classe.getUserCreated();
//                classeSet1Classe.setUserCreated(users);
//                classeSet1Classe = em.merge(classeSet1Classe);
//                if (oldUserCreatedOfClasseSet1Classe != null) {
//                    oldUserCreatedOfClasseSet1Classe.getClasseSet1().remove(classeSet1Classe);
//                    oldUserCreatedOfClasseSet1Classe = em.merge(oldUserCreatedOfClasseSet1Classe);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsers(users.getUsersId()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Users persistentUsers = (Users) find(Users.class, users.getUsersId());
//            Levels levelsIdOld = persistentUsers.getLevelsId();
            Levels levelsIdNew = users.getLevelsId();
//            Users userModifiedOld = persistentUsers.getUserModified();
            Users userModifiedNew = users.getUserModified();
            Users userCreatedOld = persistentUsers.getUserCreated();
            Users userCreatedNew = users.getUserCreated();
//            Set<Student> studentSetOld = persistentUsers.getStudentSet();
            Set<Student> studentSetNew = users.getStudentSet();
//            Set<Student> studentSet1Old = persistentUsers.getStudentSet1();
            Set<Student> studentSet1New = users.getStudentSet1();
//            Set<Groupe> groupeSetOld = persistentUsers.getGroupeSet();
            Set<Groupe> groupeSetNew = users.getGroupeSet();
//            Set<Groupe> groupeSet1Old = persistentUsers.getGroupeSet1();
            Set<Groupe> groupeSet1New = users.getGroupeSet1();
//            Set<Salle> salleSetOld = persistentUsers.getSalleSet();
            Set<Salle> salleSetNew = users.getSalleSet();
//            Set<Salle> salleSet1Old = persistentUsers.getSalleSet1();
            Set<Salle> salleSet1New = users.getSalleSet1();
//            Set<Levels> levelsSetOld = persistentUsers.getLevelsSet();
            Set<Levels> levelsSetNew = users.getLevelsSet();
//            Set<Levels> levelsSet1Old = persistentUsers.getLevelsSet1();
            Set<Levels> levelsSet1New = users.getLevelsSet1();
//            Set<AnneeAcademic> anneeAcademicSetOld = persistentUsers.getAnneeAcademicSet();
            Set<AnneeAcademic> anneeAcademicSetNew = users.getAnneeAcademicSet();
//            Set<AnneeAcademic> anneeAcademicSet1Old = persistentUsers.getAnneeAcademicSet1();
            Set<AnneeAcademic> anneeAcademicSet1New = users.getAnneeAcademicSet1();
//            Set<Etablissement> etablissementSetOld = persistentUsers.getEtablissementSet();
            Set<Etablissement> etablissementSetNew = users.getEtablissementSet();
//            Set<Etablissement> etablissementSet1Old = persistentUsers.getEtablissementSet1();
            Set<Etablissement> etablissementSet1New = users.getEtablissementSet1();
//            Set<Users> usersSetOld = persistentUsers.getUsersSet();
            Set<Users> usersSetNew = users.getUsersSet();
//            Set<Users> usersSet1Old = persistentUsers.getUsersSet1();
            Set<Users> usersSet1New = users.getUsersSet1();
//            Set<Section> sectionSetOld = persistentUsers.getSectionSet();
            Set<Section> sectionSetNew = users.getSectionSet();
//            Set<Section> sectionSet1Old = persistentUsers.getSectionSet1();
            Set<Section> sectionSet1New = users.getSectionSet1();
//            Set<Widget> widgetSetOld = persistentUsers.getWidgetSet();
            Set<Widget> widgetSetNew = users.getWidgetSet();
//            Set<Widget> widgetSet1Old = persistentUsers.getWidgetSet1();
            Set<Widget> widgetSet1New = users.getWidgetSet1();
//            Set<Classe> classeSetOld = persistentUsers.getClasseSet();
            Set<Classe> classeSetNew = users.getClasseSet();
//            Set<Classe> classeSet1Old = persistentUsers.getClasseSet1();
            Set<Classe> classeSet1New = users.getClasseSet1();
//            List<String> illegalOrphanMessages = null;
//            if (levelsIdNew != null && !levelsIdNew.equals(levelsIdOld)) {
//                Users oldUserModifiedOfLevelsId = levelsIdNew.getUserModified();
//                if (oldUserModifiedOfLevelsId != null) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("The Levels " + levelsIdNew + " already has an item of type Users whose levelsId column cannot be null. Please make another selection for the levelsId field.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            if (levelsIdNew != null) {
                levelsIdNew = (Levels) refreshEntity(levelsIdNew.getClass(), levelsIdNew.getLevelsId());
                users.setLevelsId(levelsIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                users.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                users.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                users.setUserCreated(userCreatedOld);
            }
            Set<Student> attachedStudentSetNew = new HashSet<Student>();
            for (Student studentSetNewStudentToAttach : studentSetNew) {
                studentSetNewStudentToAttach = (Student) refreshEntity(studentSetNewStudentToAttach.getClass(), studentSetNewStudentToAttach.getStudentId());
                attachedStudentSetNew.add(studentSetNewStudentToAttach);
            }
            studentSetNew = attachedStudentSetNew;
            users.setStudentSet(studentSetNew);
            Set<Student> attachedStudentSet1New = new HashSet<Student>();
            for (Student studentSet1NewStudentToAttach : studentSet1New) {
                studentSet1NewStudentToAttach = (Student) refreshEntity(studentSet1NewStudentToAttach.getClass(), studentSet1NewStudentToAttach.getStudentId());
                attachedStudentSet1New.add(studentSet1NewStudentToAttach);
            }
            studentSet1New = attachedStudentSet1New;
            users.setStudentSet1(studentSet1New);
            Set<Groupe> attachedGroupeSetNew = new HashSet<Groupe>();
            for (Groupe groupeSetNewGroupeToAttach : groupeSetNew) {
                groupeSetNewGroupeToAttach = (Groupe) refreshEntity(groupeSetNewGroupeToAttach.getClass(), groupeSetNewGroupeToAttach.getGroupeId());
                attachedGroupeSetNew.add(groupeSetNewGroupeToAttach);
            }
            groupeSetNew = attachedGroupeSetNew;
            users.setGroupeSet(groupeSetNew);
            Set<Groupe> attachedGroupeSet1New = new HashSet<Groupe>();
            for (Groupe groupeSet1NewGroupeToAttach : groupeSet1New) {
                groupeSet1NewGroupeToAttach = (Groupe) refreshEntity(groupeSet1NewGroupeToAttach.getClass(), groupeSet1NewGroupeToAttach.getGroupeId());
                attachedGroupeSet1New.add(groupeSet1NewGroupeToAttach);
            }
            groupeSet1New = attachedGroupeSet1New;
            users.setGroupeSet1(groupeSet1New);
            Set<Salle> attachedSalleSetNew = new HashSet<Salle>();
            for (Salle salleSetNewSalleToAttach : salleSetNew) {
                salleSetNewSalleToAttach = (Salle) refreshEntity(salleSetNewSalleToAttach.getClass(), salleSetNewSalleToAttach.getSalleId());
                attachedSalleSetNew.add(salleSetNewSalleToAttach);
            }
            salleSetNew = attachedSalleSetNew;
            users.setSalleSet(salleSetNew);
            Set<Salle> attachedSalleSet1New = new HashSet<Salle>();
            for (Salle salleSet1NewSalleToAttach : salleSet1New) {
                salleSet1NewSalleToAttach = (Salle) refreshEntity(salleSet1NewSalleToAttach.getClass(), salleSet1NewSalleToAttach.getSalleId());
                attachedSalleSet1New.add(salleSet1NewSalleToAttach);
            }
            salleSet1New = attachedSalleSet1New;
            users.setSalleSet1(salleSet1New);
            Set<Levels> attachedLevelsSetNew = new HashSet<Levels>();
            for (Levels levelsSetNewLevelsToAttach : levelsSetNew) {
                levelsSetNewLevelsToAttach = (Levels) refreshEntity(levelsSetNewLevelsToAttach.getClass(), levelsSetNewLevelsToAttach.getLevelsId());
                attachedLevelsSetNew.add(levelsSetNewLevelsToAttach);
            }
            levelsSetNew = attachedLevelsSetNew;
            users.setLevelsSet(levelsSetNew);
            Set<Levels> attachedLevelsSet1New = new HashSet<Levels>();
            for (Levels levelsSet1NewLevelsToAttach : levelsSet1New) {
                levelsSet1NewLevelsToAttach = (Levels) refreshEntity(levelsSet1NewLevelsToAttach.getClass(), levelsSet1NewLevelsToAttach.getLevelsId());
                attachedLevelsSet1New.add(levelsSet1NewLevelsToAttach);
            }
            levelsSet1New = attachedLevelsSet1New;
            users.setLevelsSet1(levelsSet1New);
            Set<AnneeAcademic> attachedAnneeAcademicSetNew = new HashSet<AnneeAcademic>();
            for (AnneeAcademic anneeAcademicSetNewAnneeAcademicToAttach : anneeAcademicSetNew) {
                anneeAcademicSetNewAnneeAcademicToAttach = (AnneeAcademic) refreshEntity(anneeAcademicSetNewAnneeAcademicToAttach.getClass(), anneeAcademicSetNewAnneeAcademicToAttach.getAnneeAcademicId());
                attachedAnneeAcademicSetNew.add(anneeAcademicSetNewAnneeAcademicToAttach);
            }
            anneeAcademicSetNew = attachedAnneeAcademicSetNew;
            users.setAnneeAcademicSet(anneeAcademicSetNew);
            Set<AnneeAcademic> attachedAnneeAcademicSet1New = new HashSet<AnneeAcademic>();
            for (AnneeAcademic anneeAcademicSet1NewAnneeAcademicToAttach : anneeAcademicSet1New) {
                anneeAcademicSet1NewAnneeAcademicToAttach = (AnneeAcademic) refreshEntity(anneeAcademicSet1NewAnneeAcademicToAttach.getClass(), anneeAcademicSet1NewAnneeAcademicToAttach.getAnneeAcademicId());
                attachedAnneeAcademicSet1New.add(anneeAcademicSet1NewAnneeAcademicToAttach);
            }
            anneeAcademicSet1New = attachedAnneeAcademicSet1New;
            users.setAnneeAcademicSet1(anneeAcademicSet1New);
            Set<Etablissement> attachedEtablissementSetNew = new HashSet<Etablissement>();
            for (Etablissement etablissementSetNewEtablissementToAttach : etablissementSetNew) {
                etablissementSetNewEtablissementToAttach = (Etablissement) refreshEntity(etablissementSetNewEtablissementToAttach.getClass(), etablissementSetNewEtablissementToAttach.getEtablissementId());
                attachedEtablissementSetNew.add(etablissementSetNewEtablissementToAttach);
            }
            etablissementSetNew = attachedEtablissementSetNew;
            users.setEtablissementSet(etablissementSetNew);
            Set<Etablissement> attachedEtablissementSet1New = new HashSet<Etablissement>();
            for (Etablissement etablissementSet1NewEtablissementToAttach : etablissementSet1New) {
                etablissementSet1NewEtablissementToAttach = (Etablissement) refreshEntity(etablissementSet1NewEtablissementToAttach.getClass(), etablissementSet1NewEtablissementToAttach.getEtablissementId());
                attachedEtablissementSet1New.add(etablissementSet1NewEtablissementToAttach);
            }
            etablissementSet1New = attachedEtablissementSet1New;
            users.setEtablissementSet1(etablissementSet1New);
            Set<Users> attachedUsersSetNew = new HashSet<Users>();
            for (Users usersSetNewUsersToAttach : usersSetNew) {
                usersSetNewUsersToAttach = (Users) refreshEntity(usersSetNewUsersToAttach.getClass(), usersSetNewUsersToAttach.getUsersId());
                attachedUsersSetNew.add(usersSetNewUsersToAttach);
            }
            usersSetNew = attachedUsersSetNew;
            users.setUsersSet(usersSetNew);
            Set<Users> attachedUsersSet1New = new HashSet<Users>();
            for (Users usersSet1NewUsersToAttach : usersSet1New) {
                usersSet1NewUsersToAttach = (Users) refreshEntity(usersSet1NewUsersToAttach.getClass(), usersSet1NewUsersToAttach.getUsersId());
                attachedUsersSet1New.add(usersSet1NewUsersToAttach);
            }
            usersSet1New = attachedUsersSet1New;
            users.setUsersSet1(usersSet1New);
            Set<Section> attachedSectionSetNew = new HashSet<Section>();
            for (Section sectionSetNewSectionToAttach : sectionSetNew) {
                sectionSetNewSectionToAttach = (Section) refreshEntity(sectionSetNewSectionToAttach.getClass(), sectionSetNewSectionToAttach.getSectionId());
                attachedSectionSetNew.add(sectionSetNewSectionToAttach);
            }
            sectionSetNew = attachedSectionSetNew;
            users.setSectionSet(sectionSetNew);
            Set<Section> attachedSectionSet1New = new HashSet<Section>();
            for (Section sectionSet1NewSectionToAttach : sectionSet1New) {
                sectionSet1NewSectionToAttach = (Section) refreshEntity(sectionSet1NewSectionToAttach.getClass(), sectionSet1NewSectionToAttach.getSectionId());
                attachedSectionSet1New.add(sectionSet1NewSectionToAttach);
            }
            sectionSet1New = attachedSectionSet1New;
            users.setSectionSet1(sectionSet1New);
            Set<Widget> attachedWidgetSetNew = new HashSet<Widget>();
            for (Widget widgetSetNewWidgetToAttach : widgetSetNew) {
                widgetSetNewWidgetToAttach = (Widget) refreshEntity(widgetSetNewWidgetToAttach.getClass(), widgetSetNewWidgetToAttach.getWidgetId());
                attachedWidgetSetNew.add(widgetSetNewWidgetToAttach);
            }
            widgetSetNew = attachedWidgetSetNew;
            users.setWidgetSet(widgetSetNew);
            Set<Widget> attachedWidgetSet1New = new HashSet<Widget>();
            for (Widget widgetSet1NewWidgetToAttach : widgetSet1New) {
                widgetSet1NewWidgetToAttach = (Widget) refreshEntity(widgetSet1NewWidgetToAttach.getClass(), widgetSet1NewWidgetToAttach.getWidgetId());
                attachedWidgetSet1New.add(widgetSet1NewWidgetToAttach);
            }
            widgetSet1New = attachedWidgetSet1New;
            users.setWidgetSet1(widgetSet1New);
            Set<Classe> attachedClasseSetNew = new HashSet<Classe>();
            for (Classe classeSetNewClasseToAttach : classeSetNew) {
                classeSetNewClasseToAttach = (Classe) refreshEntity(classeSetNewClasseToAttach.getClass(), classeSetNewClasseToAttach.getClasseId());
                attachedClasseSetNew.add(classeSetNewClasseToAttach);
            }
            classeSetNew = attachedClasseSetNew;
            users.setClasseSet(classeSetNew);
            Set<Classe> attachedClasseSet1New = new HashSet<Classe>();
            for (Classe classeSet1NewClasseToAttach : classeSet1New) {
                classeSet1NewClasseToAttach = (Classe) refreshEntity(classeSet1NewClasseToAttach.getClass(), classeSet1NewClasseToAttach.getClasseId());
                attachedClasseSet1New.add(classeSet1NewClasseToAttach);
            }
            classeSet1New = attachedClasseSet1New;
            users.setClasseSet1(classeSet1New);
            users = (Users) merge(users);
//            if (levelsIdOld != null && !levelsIdOld.equals(levelsIdNew)) {
//                levelsIdOld.setUserModified(null);
//                levelsIdOld = em.merge(levelsIdOld);
//            }
//            if (levelsIdNew != null && !levelsIdNew.equals(levelsIdOld)) {
//                levelsIdNew.setUserModified(users);
//                levelsIdNew = em.merge(levelsIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getUsersSet().remove(users);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getUsersSet().add(users);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getUsersSet().remove(users);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getUsersSet().add(users);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (Student studentSetOldStudent : studentSetOld) {
//                if (!studentSetNew.contains(studentSetOldStudent)) {
//                    studentSetOldStudent.setUserModified(null);
//                    studentSetOldStudent = em.merge(studentSetOldStudent);
//                }
//            }
//            for (Student studentSetNewStudent : studentSetNew) {
//                if (!studentSetOld.contains(studentSetNewStudent)) {
//                    Users oldUserModifiedOfStudentSetNewStudent = studentSetNewStudent.getUserModified();
//                    studentSetNewStudent.setUserModified(users);
//                    studentSetNewStudent = em.merge(studentSetNewStudent);
//                    if (oldUserModifiedOfStudentSetNewStudent != null && !oldUserModifiedOfStudentSetNewStudent.equals(users)) {
//                        oldUserModifiedOfStudentSetNewStudent.getStudentSet().remove(studentSetNewStudent);
//                        oldUserModifiedOfStudentSetNewStudent = em.merge(oldUserModifiedOfStudentSetNewStudent);
//                    }
//                }
//            }
//            for (Student studentSet1OldStudent : studentSet1Old) {
//                if (!studentSet1New.contains(studentSet1OldStudent)) {
//                    studentSet1OldStudent.setUserCreated(null);
//                    studentSet1OldStudent = em.merge(studentSet1OldStudent);
//                }
//            }
//            for (Student studentSet1NewStudent : studentSet1New) {
//                if (!studentSet1Old.contains(studentSet1NewStudent)) {
//                    Users oldUserCreatedOfStudentSet1NewStudent = studentSet1NewStudent.getUserCreated();
//                    studentSet1NewStudent.setUserCreated(users);
//                    studentSet1NewStudent = em.merge(studentSet1NewStudent);
//                    if (oldUserCreatedOfStudentSet1NewStudent != null && !oldUserCreatedOfStudentSet1NewStudent.equals(users)) {
//                        oldUserCreatedOfStudentSet1NewStudent.getStudentSet1().remove(studentSet1NewStudent);
//                        oldUserCreatedOfStudentSet1NewStudent = em.merge(oldUserCreatedOfStudentSet1NewStudent);
//                    }
//                }
//            }
//            for (Groupe groupeSetOldGroupe : groupeSetOld) {
//                if (!groupeSetNew.contains(groupeSetOldGroupe)) {
//                    groupeSetOldGroupe.setUserModified(null);
//                    groupeSetOldGroupe = em.merge(groupeSetOldGroupe);
//                }
//            }
//            for (Groupe groupeSetNewGroupe : groupeSetNew) {
//                if (!groupeSetOld.contains(groupeSetNewGroupe)) {
//                    Users oldUserModifiedOfGroupeSetNewGroupe = groupeSetNewGroupe.getUserModified();
//                    groupeSetNewGroupe.setUserModified(users);
//                    groupeSetNewGroupe = em.merge(groupeSetNewGroupe);
//                    if (oldUserModifiedOfGroupeSetNewGroupe != null && !oldUserModifiedOfGroupeSetNewGroupe.equals(users)) {
//                        oldUserModifiedOfGroupeSetNewGroupe.getGroupeSet().remove(groupeSetNewGroupe);
//                        oldUserModifiedOfGroupeSetNewGroupe = em.merge(oldUserModifiedOfGroupeSetNewGroupe);
//                    }
//                }
//            }
//            for (Groupe groupeSet1OldGroupe : groupeSet1Old) {
//                if (!groupeSet1New.contains(groupeSet1OldGroupe)) {
//                    groupeSet1OldGroupe.setUserCreated(null);
//                    groupeSet1OldGroupe = em.merge(groupeSet1OldGroupe);
//                }
//            }
//            for (Groupe groupeSet1NewGroupe : groupeSet1New) {
//                if (!groupeSet1Old.contains(groupeSet1NewGroupe)) {
//                    Users oldUserCreatedOfGroupeSet1NewGroupe = groupeSet1NewGroupe.getUserCreated();
//                    groupeSet1NewGroupe.setUserCreated(users);
//                    groupeSet1NewGroupe = em.merge(groupeSet1NewGroupe);
//                    if (oldUserCreatedOfGroupeSet1NewGroupe != null && !oldUserCreatedOfGroupeSet1NewGroupe.equals(users)) {
//                        oldUserCreatedOfGroupeSet1NewGroupe.getGroupeSet1().remove(groupeSet1NewGroupe);
//                        oldUserCreatedOfGroupeSet1NewGroupe = em.merge(oldUserCreatedOfGroupeSet1NewGroupe);
//                    }
//                }
//            }
//            for (Salle salleSetOldSalle : salleSetOld) {
//                if (!salleSetNew.contains(salleSetOldSalle)) {
//                    salleSetOldSalle.setUserModified(null);
//                    salleSetOldSalle = em.merge(salleSetOldSalle);
//                }
//            }
//            for (Salle salleSetNewSalle : salleSetNew) {
//                if (!salleSetOld.contains(salleSetNewSalle)) {
//                    Users oldUserModifiedOfSalleSetNewSalle = salleSetNewSalle.getUserModified();
//                    salleSetNewSalle.setUserModified(users);
//                    salleSetNewSalle = em.merge(salleSetNewSalle);
//                    if (oldUserModifiedOfSalleSetNewSalle != null && !oldUserModifiedOfSalleSetNewSalle.equals(users)) {
//                        oldUserModifiedOfSalleSetNewSalle.getSalleSet().remove(salleSetNewSalle);
//                        oldUserModifiedOfSalleSetNewSalle = em.merge(oldUserModifiedOfSalleSetNewSalle);
//                    }
//                }
//            }
//            for (Salle salleSet1OldSalle : salleSet1Old) {
//                if (!salleSet1New.contains(salleSet1OldSalle)) {
//                    salleSet1OldSalle.setUserCreated(null);
//                    salleSet1OldSalle = em.merge(salleSet1OldSalle);
//                }
//            }
//            for (Salle salleSet1NewSalle : salleSet1New) {
//                if (!salleSet1Old.contains(salleSet1NewSalle)) {
//                    Users oldUserCreatedOfSalleSet1NewSalle = salleSet1NewSalle.getUserCreated();
//                    salleSet1NewSalle.setUserCreated(users);
//                    salleSet1NewSalle = em.merge(salleSet1NewSalle);
//                    if (oldUserCreatedOfSalleSet1NewSalle != null && !oldUserCreatedOfSalleSet1NewSalle.equals(users)) {
//                        oldUserCreatedOfSalleSet1NewSalle.getSalleSet1().remove(salleSet1NewSalle);
//                        oldUserCreatedOfSalleSet1NewSalle = em.merge(oldUserCreatedOfSalleSet1NewSalle);
//                    }
//                }
//            }
//            for (Levels levelsSetOldLevels : levelsSetOld) {
//                if (!levelsSetNew.contains(levelsSetOldLevels)) {
//                    levelsSetOldLevels.setUserModified(null);
//                    levelsSetOldLevels = em.merge(levelsSetOldLevels);
//                }
//            }
//            for (Levels levelsSetNewLevels : levelsSetNew) {
//                if (!levelsSetOld.contains(levelsSetNewLevels)) {
//                    Users oldUserModifiedOfLevelsSetNewLevels = levelsSetNewLevels.getUserModified();
//                    levelsSetNewLevels.setUserModified(users);
//                    levelsSetNewLevels = em.merge(levelsSetNewLevels);
//                    if (oldUserModifiedOfLevelsSetNewLevels != null && !oldUserModifiedOfLevelsSetNewLevels.equals(users)) {
//                        oldUserModifiedOfLevelsSetNewLevels.getLevelsSet().remove(levelsSetNewLevels);
//                        oldUserModifiedOfLevelsSetNewLevels = em.merge(oldUserModifiedOfLevelsSetNewLevels);
//                    }
//                }
//            }
//            for (Levels levelsSet1OldLevels : levelsSet1Old) {
//                if (!levelsSet1New.contains(levelsSet1OldLevels)) {
//                    levelsSet1OldLevels.setUserCreated(null);
//                    levelsSet1OldLevels = em.merge(levelsSet1OldLevels);
//                }
//            }
//            for (Levels levelsSet1NewLevels : levelsSet1New) {
//                if (!levelsSet1Old.contains(levelsSet1NewLevels)) {
//                    Users oldUserCreatedOfLevelsSet1NewLevels = levelsSet1NewLevels.getUserCreated();
//                    levelsSet1NewLevels.setUserCreated(users);
//                    levelsSet1NewLevels = em.merge(levelsSet1NewLevels);
//                    if (oldUserCreatedOfLevelsSet1NewLevels != null && !oldUserCreatedOfLevelsSet1NewLevels.equals(users)) {
//                        oldUserCreatedOfLevelsSet1NewLevels.getLevelsSet1().remove(levelsSet1NewLevels);
//                        oldUserCreatedOfLevelsSet1NewLevels = em.merge(oldUserCreatedOfLevelsSet1NewLevels);
//                    }
//                }
//            }
//            for (AnneeAcademic anneeAcademicSetOldAnneeAcademic : anneeAcademicSetOld) {
//                if (!anneeAcademicSetNew.contains(anneeAcademicSetOldAnneeAcademic)) {
//                    anneeAcademicSetOldAnneeAcademic.setUserModified(null);
//                    anneeAcademicSetOldAnneeAcademic = em.merge(anneeAcademicSetOldAnneeAcademic);
//                }
//            }
//            for (AnneeAcademic anneeAcademicSetNewAnneeAcademic : anneeAcademicSetNew) {
//                if (!anneeAcademicSetOld.contains(anneeAcademicSetNewAnneeAcademic)) {
//                    Users oldUserModifiedOfAnneeAcademicSetNewAnneeAcademic = anneeAcademicSetNewAnneeAcademic.getUserModified();
//                    anneeAcademicSetNewAnneeAcademic.setUserModified(users);
//                    anneeAcademicSetNewAnneeAcademic = em.merge(anneeAcademicSetNewAnneeAcademic);
//                    if (oldUserModifiedOfAnneeAcademicSetNewAnneeAcademic != null && !oldUserModifiedOfAnneeAcademicSetNewAnneeAcademic.equals(users)) {
//                        oldUserModifiedOfAnneeAcademicSetNewAnneeAcademic.getAnneeAcademicSet().remove(anneeAcademicSetNewAnneeAcademic);
//                        oldUserModifiedOfAnneeAcademicSetNewAnneeAcademic = em.merge(oldUserModifiedOfAnneeAcademicSetNewAnneeAcademic);
//                    }
//                }
//            }
//            for (AnneeAcademic anneeAcademicSet1OldAnneeAcademic : anneeAcademicSet1Old) {
//                if (!anneeAcademicSet1New.contains(anneeAcademicSet1OldAnneeAcademic)) {
//                    anneeAcademicSet1OldAnneeAcademic.setUserCreated(null);
//                    anneeAcademicSet1OldAnneeAcademic = em.merge(anneeAcademicSet1OldAnneeAcademic);
//                }
//            }
//            for (AnneeAcademic anneeAcademicSet1NewAnneeAcademic : anneeAcademicSet1New) {
//                if (!anneeAcademicSet1Old.contains(anneeAcademicSet1NewAnneeAcademic)) {
//                    Users oldUserCreatedOfAnneeAcademicSet1NewAnneeAcademic = anneeAcademicSet1NewAnneeAcademic.getUserCreated();
//                    anneeAcademicSet1NewAnneeAcademic.setUserCreated(users);
//                    anneeAcademicSet1NewAnneeAcademic = em.merge(anneeAcademicSet1NewAnneeAcademic);
//                    if (oldUserCreatedOfAnneeAcademicSet1NewAnneeAcademic != null && !oldUserCreatedOfAnneeAcademicSet1NewAnneeAcademic.equals(users)) {
//                        oldUserCreatedOfAnneeAcademicSet1NewAnneeAcademic.getAnneeAcademicSet1().remove(anneeAcademicSet1NewAnneeAcademic);
//                        oldUserCreatedOfAnneeAcademicSet1NewAnneeAcademic = em.merge(oldUserCreatedOfAnneeAcademicSet1NewAnneeAcademic);
//                    }
//                }
//            }
//            for (Etablissement etablissementSetOldEtablissement : etablissementSetOld) {
//                if (!etablissementSetNew.contains(etablissementSetOldEtablissement)) {
//                    etablissementSetOldEtablissement.setUserModified(null);
//                    etablissementSetOldEtablissement = em.merge(etablissementSetOldEtablissement);
//                }
//            }
//            for (Etablissement etablissementSetNewEtablissement : etablissementSetNew) {
//                if (!etablissementSetOld.contains(etablissementSetNewEtablissement)) {
//                    Users oldUserModifiedOfEtablissementSetNewEtablissement = etablissementSetNewEtablissement.getUserModified();
//                    etablissementSetNewEtablissement.setUserModified(users);
//                    etablissementSetNewEtablissement = em.merge(etablissementSetNewEtablissement);
//                    if (oldUserModifiedOfEtablissementSetNewEtablissement != null && !oldUserModifiedOfEtablissementSetNewEtablissement.equals(users)) {
//                        oldUserModifiedOfEtablissementSetNewEtablissement.getEtablissementSet().remove(etablissementSetNewEtablissement);
//                        oldUserModifiedOfEtablissementSetNewEtablissement = em.merge(oldUserModifiedOfEtablissementSetNewEtablissement);
//                    }
//                }
//            }
//            for (Etablissement etablissementSet1OldEtablissement : etablissementSet1Old) {
//                if (!etablissementSet1New.contains(etablissementSet1OldEtablissement)) {
//                    etablissementSet1OldEtablissement.setUserCreated(null);
//                    etablissementSet1OldEtablissement = em.merge(etablissementSet1OldEtablissement);
//                }
//            }
//            for (Etablissement etablissementSet1NewEtablissement : etablissementSet1New) {
//                if (!etablissementSet1Old.contains(etablissementSet1NewEtablissement)) {
//                    Users oldUserCreatedOfEtablissementSet1NewEtablissement = etablissementSet1NewEtablissement.getUserCreated();
//                    etablissementSet1NewEtablissement.setUserCreated(users);
//                    etablissementSet1NewEtablissement = em.merge(etablissementSet1NewEtablissement);
//                    if (oldUserCreatedOfEtablissementSet1NewEtablissement != null && !oldUserCreatedOfEtablissementSet1NewEtablissement.equals(users)) {
//                        oldUserCreatedOfEtablissementSet1NewEtablissement.getEtablissementSet1().remove(etablissementSet1NewEtablissement);
//                        oldUserCreatedOfEtablissementSet1NewEtablissement = em.merge(oldUserCreatedOfEtablissementSet1NewEtablissement);
//                    }
//                }
//            }
//            for (Users usersSetOldUsers : usersSetOld) {
//                if (!usersSetNew.contains(usersSetOldUsers)) {
//                    usersSetOldUsers.setUserModified(null);
//                    usersSetOldUsers = em.merge(usersSetOldUsers);
//                }
//            }
//            for (Users usersSetNewUsers : usersSetNew) {
//                if (!usersSetOld.contains(usersSetNewUsers)) {
//                    Users oldUserModifiedOfUsersSetNewUsers = usersSetNewUsers.getUserModified();
//                    usersSetNewUsers.setUserModified(users);
//                    usersSetNewUsers = em.merge(usersSetNewUsers);
//                    if (oldUserModifiedOfUsersSetNewUsers != null && !oldUserModifiedOfUsersSetNewUsers.equals(users)) {
//                        oldUserModifiedOfUsersSetNewUsers.getUsersSet().remove(usersSetNewUsers);
//                        oldUserModifiedOfUsersSetNewUsers = em.merge(oldUserModifiedOfUsersSetNewUsers);
//                    }
//                }
//            }
//            for (Users usersSet1OldUsers : usersSet1Old) {
//                if (!usersSet1New.contains(usersSet1OldUsers)) {
//                    usersSet1OldUsers.setUserCreated(null);
//                    usersSet1OldUsers = em.merge(usersSet1OldUsers);
//                }
//            }
//            for (Users usersSet1NewUsers : usersSet1New) {
//                if (!usersSet1Old.contains(usersSet1NewUsers)) {
//                    Users oldUserCreatedOfUsersSet1NewUsers = usersSet1NewUsers.getUserCreated();
//                    usersSet1NewUsers.setUserCreated(users);
//                    usersSet1NewUsers = em.merge(usersSet1NewUsers);
//                    if (oldUserCreatedOfUsersSet1NewUsers != null && !oldUserCreatedOfUsersSet1NewUsers.equals(users)) {
//                        oldUserCreatedOfUsersSet1NewUsers.getUsersSet1().remove(usersSet1NewUsers);
//                        oldUserCreatedOfUsersSet1NewUsers = em.merge(oldUserCreatedOfUsersSet1NewUsers);
//                    }
//                }
//            }
//            for (Section sectionSetOldSection : sectionSetOld) {
//                if (!sectionSetNew.contains(sectionSetOldSection)) {
//                    sectionSetOldSection.setUserModified(null);
//                    sectionSetOldSection = em.merge(sectionSetOldSection);
//                }
//            }
//            for (Section sectionSetNewSection : sectionSetNew) {
//                if (!sectionSetOld.contains(sectionSetNewSection)) {
//                    Users oldUserModifiedOfSectionSetNewSection = sectionSetNewSection.getUserModified();
//                    sectionSetNewSection.setUserModified(users);
//                    sectionSetNewSection = em.merge(sectionSetNewSection);
//                    if (oldUserModifiedOfSectionSetNewSection != null && !oldUserModifiedOfSectionSetNewSection.equals(users)) {
//                        oldUserModifiedOfSectionSetNewSection.getSectionSet().remove(sectionSetNewSection);
//                        oldUserModifiedOfSectionSetNewSection = em.merge(oldUserModifiedOfSectionSetNewSection);
//                    }
//                }
//            }
//            for (Section sectionSet1OldSection : sectionSet1Old) {
//                if (!sectionSet1New.contains(sectionSet1OldSection)) {
//                    sectionSet1OldSection.setUserCreated(null);
//                    sectionSet1OldSection = em.merge(sectionSet1OldSection);
//                }
//            }
//            for (Section sectionSet1NewSection : sectionSet1New) {
//                if (!sectionSet1Old.contains(sectionSet1NewSection)) {
//                    Users oldUserCreatedOfSectionSet1NewSection = sectionSet1NewSection.getUserCreated();
//                    sectionSet1NewSection.setUserCreated(users);
//                    sectionSet1NewSection = em.merge(sectionSet1NewSection);
//                    if (oldUserCreatedOfSectionSet1NewSection != null && !oldUserCreatedOfSectionSet1NewSection.equals(users)) {
//                        oldUserCreatedOfSectionSet1NewSection.getSectionSet1().remove(sectionSet1NewSection);
//                        oldUserCreatedOfSectionSet1NewSection = em.merge(oldUserCreatedOfSectionSet1NewSection);
//                    }
//                }
//            }
//            for (Widget widgetSetOldWidget : widgetSetOld) {
//                if (!widgetSetNew.contains(widgetSetOldWidget)) {
//                    widgetSetOldWidget.setUserModified(null);
//                    widgetSetOldWidget = em.merge(widgetSetOldWidget);
//                }
//            }
//            for (Widget widgetSetNewWidget : widgetSetNew) {
//                if (!widgetSetOld.contains(widgetSetNewWidget)) {
//                    Users oldUserModifiedOfWidgetSetNewWidget = widgetSetNewWidget.getUserModified();
//                    widgetSetNewWidget.setUserModified(users);
//                    widgetSetNewWidget = em.merge(widgetSetNewWidget);
//                    if (oldUserModifiedOfWidgetSetNewWidget != null && !oldUserModifiedOfWidgetSetNewWidget.equals(users)) {
//                        oldUserModifiedOfWidgetSetNewWidget.getWidgetSet().remove(widgetSetNewWidget);
//                        oldUserModifiedOfWidgetSetNewWidget = em.merge(oldUserModifiedOfWidgetSetNewWidget);
//                    }
//                }
//            }
//            for (Widget widgetSet1OldWidget : widgetSet1Old) {
//                if (!widgetSet1New.contains(widgetSet1OldWidget)) {
//                    widgetSet1OldWidget.setUserCreated(null);
//                    widgetSet1OldWidget = em.merge(widgetSet1OldWidget);
//                }
//            }
//            for (Widget widgetSet1NewWidget : widgetSet1New) {
//                if (!widgetSet1Old.contains(widgetSet1NewWidget)) {
//                    Users oldUserCreatedOfWidgetSet1NewWidget = widgetSet1NewWidget.getUserCreated();
//                    widgetSet1NewWidget.setUserCreated(users);
//                    widgetSet1NewWidget = em.merge(widgetSet1NewWidget);
//                    if (oldUserCreatedOfWidgetSet1NewWidget != null && !oldUserCreatedOfWidgetSet1NewWidget.equals(users)) {
//                        oldUserCreatedOfWidgetSet1NewWidget.getWidgetSet1().remove(widgetSet1NewWidget);
//                        oldUserCreatedOfWidgetSet1NewWidget = em.merge(oldUserCreatedOfWidgetSet1NewWidget);
//                    }
//                }
//            }
//            for (Classe classeSetOldClasse : classeSetOld) {
//                if (!classeSetNew.contains(classeSetOldClasse)) {
//                    classeSetOldClasse.setUserModified(null);
//                    classeSetOldClasse = em.merge(classeSetOldClasse);
//                }
//            }
//            for (Classe classeSetNewClasse : classeSetNew) {
//                if (!classeSetOld.contains(classeSetNewClasse)) {
//                    Users oldUserModifiedOfClasseSetNewClasse = classeSetNewClasse.getUserModified();
//                    classeSetNewClasse.setUserModified(users);
//                    classeSetNewClasse = em.merge(classeSetNewClasse);
//                    if (oldUserModifiedOfClasseSetNewClasse != null && !oldUserModifiedOfClasseSetNewClasse.equals(users)) {
//                        oldUserModifiedOfClasseSetNewClasse.getClasseSet().remove(classeSetNewClasse);
//                        oldUserModifiedOfClasseSetNewClasse = em.merge(oldUserModifiedOfClasseSetNewClasse);
//                    }
//                }
//            }
//            for (Classe classeSet1OldClasse : classeSet1Old) {
//                if (!classeSet1New.contains(classeSet1OldClasse)) {
//                    classeSet1OldClasse.setUserCreated(null);
//                    classeSet1OldClasse = em.merge(classeSet1OldClasse);
//                }
//            }
//            for (Classe classeSet1NewClasse : classeSet1New) {
//                if (!classeSet1Old.contains(classeSet1NewClasse)) {
//                    Users oldUserCreatedOfClasseSet1NewClasse = classeSet1NewClasse.getUserCreated();
//                    classeSet1NewClasse.setUserCreated(users);
//                    classeSet1NewClasse = em.merge(classeSet1NewClasse);
//                    if (oldUserCreatedOfClasseSet1NewClasse != null && !oldUserCreatedOfClasseSet1NewClasse.equals(users)) {
//                        oldUserCreatedOfClasseSet1NewClasse.getClasseSet1().remove(classeSet1NewClasse);
//                        oldUserCreatedOfClasseSet1NewClasse = em.merge(oldUserCreatedOfClasseSet1NewClasse);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = users.getUsersId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Users users;
//            try {
//                users = () refreshEntity(Users.class, id);
//                users.getUsersId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
//            }
//            Levels levelsId = users.getLevelsId();
//            if (levelsId != null) {
//                levelsId.setUserModified(null);
//                levelsId = em.merge(levelsId);
//            }
//            Users userModified = users.getUserModified();
//            if (userModified != null) {
//                userModified.getUsersSet().remove(users);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = users.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getUsersSet().remove(users);
//                userCreated = em.merge(userCreated);
//            }
//            Set<Student> studentSet = users.getStudentSet();
//            for (Student studentSetStudent : studentSet) {
//                studentSetStudent.setUserModified(null);
//                studentSetStudent = em.merge(studentSetStudent);
//            }
//            Set<Student> studentSet1 = users.getStudentSet1();
//            for (Student studentSet1Student : studentSet1) {
//                studentSet1Student.setUserCreated(null);
//                studentSet1Student = em.merge(studentSet1Student);
//            }
//            Set<Groupe> groupeSet = users.getGroupeSet();
//            for (Groupe groupeSetGroupe : groupeSet) {
//                groupeSetGroupe.setUserModified(null);
//                groupeSetGroupe = em.merge(groupeSetGroupe);
//            }
//            Set<Groupe> groupeSet1 = users.getGroupeSet1();
//            for (Groupe groupeSet1Groupe : groupeSet1) {
//                groupeSet1Groupe.setUserCreated(null);
//                groupeSet1Groupe = em.merge(groupeSet1Groupe);
//            }
//            Set<Salle> salleSet = users.getSalleSet();
//            for (Salle salleSetSalle : salleSet) {
//                salleSetSalle.setUserModified(null);
//                salleSetSalle = em.merge(salleSetSalle);
//            }
//            Set<Salle> salleSet1 = users.getSalleSet1();
//            for (Salle salleSet1Salle : salleSet1) {
//                salleSet1Salle.setUserCreated(null);
//                salleSet1Salle = em.merge(salleSet1Salle);
//            }
//            Set<Levels> levelsSet = users.getLevelsSet();
//            for (Levels levelsSetLevels : levelsSet) {
//                levelsSetLevels.setUserModified(null);
//                levelsSetLevels = em.merge(levelsSetLevels);
//            }
//            Set<Levels> levelsSet1 = users.getLevelsSet1();
//            for (Levels levelsSet1Levels : levelsSet1) {
//                levelsSet1Levels.setUserCreated(null);
//                levelsSet1Levels = em.merge(levelsSet1Levels);
//            }
//            Set<AnneeAcademic> anneeAcademicSet = users.getAnneeAcademicSet();
//            for (AnneeAcademic anneeAcademicSetAnneeAcademic : anneeAcademicSet) {
//                anneeAcademicSetAnneeAcademic.setUserModified(null);
//                anneeAcademicSetAnneeAcademic = em.merge(anneeAcademicSetAnneeAcademic);
//            }
//            Set<AnneeAcademic> anneeAcademicSet1 = users.getAnneeAcademicSet1();
//            for (AnneeAcademic anneeAcademicSet1AnneeAcademic : anneeAcademicSet1) {
//                anneeAcademicSet1AnneeAcademic.setUserCreated(null);
//                anneeAcademicSet1AnneeAcademic = em.merge(anneeAcademicSet1AnneeAcademic);
//            }
//            Set<Etablissement> etablissementSet = users.getEtablissementSet();
//            for (Etablissement etablissementSetEtablissement : etablissementSet) {
//                etablissementSetEtablissement.setUserModified(null);
//                etablissementSetEtablissement = em.merge(etablissementSetEtablissement);
//            }
//            Set<Etablissement> etablissementSet1 = users.getEtablissementSet1();
//            for (Etablissement etablissementSet1Etablissement : etablissementSet1) {
//                etablissementSet1Etablissement.setUserCreated(null);
//                etablissementSet1Etablissement = em.merge(etablissementSet1Etablissement);
//            }
//            Set<Users> usersSet = users.getUsersSet();
//            for (Users usersSetUsers : usersSet) {
//                usersSetUsers.setUserModified(null);
//                usersSetUsers = em.merge(usersSetUsers);
//            }
//            Set<Users> usersSet1 = users.getUsersSet1();
//            for (Users usersSet1Users : usersSet1) {
//                usersSet1Users.setUserCreated(null);
//                usersSet1Users = em.merge(usersSet1Users);
//            }
//            Set<Section> sectionSet = users.getSectionSet();
//            for (Section sectionSetSection : sectionSet) {
//                sectionSetSection.setUserModified(null);
//                sectionSetSection = em.merge(sectionSetSection);
//            }
//            Set<Section> sectionSet1 = users.getSectionSet1();
//            for (Section sectionSet1Section : sectionSet1) {
//                sectionSet1Section.setUserCreated(null);
//                sectionSet1Section = em.merge(sectionSet1Section);
//            }
//            Set<Widget> widgetSet = users.getWidgetSet();
//            for (Widget widgetSetWidget : widgetSet) {
//                widgetSetWidget.setUserModified(null);
//                widgetSetWidget = em.merge(widgetSetWidget);
//            }
//            Set<Widget> widgetSet1 = users.getWidgetSet1();
//            for (Widget widgetSet1Widget : widgetSet1) {
//                widgetSet1Widget.setUserCreated(null);
//                widgetSet1Widget = em.merge(widgetSet1Widget);
//            }
//            Set<Classe> classeSet = users.getClasseSet();
//            for (Classe classeSetClasse : classeSet) {
//                classeSetClasse.setUserModified(null);
//                classeSetClasse = em.merge(classeSetClasse);
//            }
//            Set<Classe> classeSet1 = users.getClasseSet1();
//            for (Classe classeSet1Classe : classeSet1) {
//                classeSet1Classe.setUserCreated(null);
//                classeSet1Classe = em.merge(classeSet1Classe);
//            }
            remove(Users.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Users findUsers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

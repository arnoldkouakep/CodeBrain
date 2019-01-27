/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Levels;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Student;
import java.util.ArrayList;
import java.util.Collection;
import cm.codebrain.main.business.entitie.Groupe;
import cm.codebrain.main.business.entitie.Salle;
import cm.codebrain.main.business.entitie.Sections;
import cm.codebrain.main.business.entitie.Etablissement;
import cm.codebrain.main.business.entitie.Widget;
import cm.codebrain.main.business.entitie.Classe;
import cm.codebrain.main.business.manager.exceptions.IllegalOrphanException;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author KSA-INET
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (users.getStudentCollection() == null) {
            users.setStudentCollection(new ArrayList<Student>());
        }
        if (users.getStudentCollection1() == null) {
            users.setStudentCollection1(new ArrayList<Student>());
        }
        if (users.getGroupeCollection() == null) {
            users.setGroupeCollection(new ArrayList<Groupe>());
        }
        if (users.getGroupeCollection1() == null) {
            users.setGroupeCollection1(new ArrayList<Groupe>());
        }
        if (users.getSalleCollection() == null) {
            users.setSalleCollection(new ArrayList<Salle>());
        }
        if (users.getSalleCollection1() == null) {
            users.setSalleCollection1(new ArrayList<Salle>());
        }
        if (users.getSectionsCollection() == null) {
            users.setSectionsCollection(new ArrayList<Sections>());
        }
        if (users.getSectionsCollection1() == null) {
            users.setSectionsCollection1(new ArrayList<Sections>());
        }
        if (users.getLevelsCollection() == null) {
            users.setLevelsCollection(new ArrayList<Levels>());
        }
        if (users.getLevelsCollection1() == null) {
            users.setLevelsCollection1(new ArrayList<Levels>());
        }
        if (users.getEtablissementCollection() == null) {
            users.setEtablissementCollection(new ArrayList<Etablissement>());
        }
        if (users.getEtablissementCollection1() == null) {
            users.setEtablissementCollection1(new ArrayList<Etablissement>());
        }
        if (users.getUsersCollection() == null) {
            users.setUsersCollection(new ArrayList<Users>());
        }
        if (users.getUsersCollection1() == null) {
            users.setUsersCollection1(new ArrayList<Users>());
        }
        if (users.getWidgetCollection() == null) {
            users.setWidgetCollection(new ArrayList<Widget>());
        }
        if (users.getWidgetCollection1() == null) {
            users.setWidgetCollection1(new ArrayList<Widget>());
        }
        if (users.getClasseCollection() == null) {
            users.setClasseCollection(new ArrayList<Classe>());
        }
        if (users.getClasseCollection1() == null) {
            users.setClasseCollection1(new ArrayList<Classe>());
        }
        List<String> illegalOrphanMessages = null;
        Levels levelsIdOrphanCheck = users.getLevelsId();
        if (levelsIdOrphanCheck != null) {
            Users oldUserModifiedOfLevelsId = levelsIdOrphanCheck.getUserModified();
            if (oldUserModifiedOfLevelsId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Levels " + levelsIdOrphanCheck + " already has an item of type Users whose levelsId column cannot be null. Please make another selection for the levelsId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Levels levelsId = users.getLevelsId();
            if (levelsId != null) {
                levelsId = em.getReference(levelsId.getClass(), levelsId.getLevelsId());
                users.setLevelsId(levelsId);
            }
            Users userModified = users.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                users.setUserModified(userModified);
            }
            Users userCreated = users.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                users.setUserCreated(userCreated);
            }
            Collection<Student> attachedStudentCollection = new ArrayList<Student>();
            for (Student studentCollectionStudentToAttach : users.getStudentCollection()) {
                studentCollectionStudentToAttach = em.getReference(studentCollectionStudentToAttach.getClass(), studentCollectionStudentToAttach.getStudentId());
                attachedStudentCollection.add(studentCollectionStudentToAttach);
            }
            users.setStudentCollection(attachedStudentCollection);
            Collection<Student> attachedStudentCollection1 = new ArrayList<Student>();
            for (Student studentCollection1StudentToAttach : users.getStudentCollection1()) {
                studentCollection1StudentToAttach = em.getReference(studentCollection1StudentToAttach.getClass(), studentCollection1StudentToAttach.getStudentId());
                attachedStudentCollection1.add(studentCollection1StudentToAttach);
            }
            users.setStudentCollection1(attachedStudentCollection1);
            Collection<Groupe> attachedGroupeCollection = new ArrayList<Groupe>();
            for (Groupe groupeCollectionGroupeToAttach : users.getGroupeCollection()) {
                groupeCollectionGroupeToAttach = em.getReference(groupeCollectionGroupeToAttach.getClass(), groupeCollectionGroupeToAttach.getGroupeId());
                attachedGroupeCollection.add(groupeCollectionGroupeToAttach);
            }
            users.setGroupeCollection(attachedGroupeCollection);
            Collection<Groupe> attachedGroupeCollection1 = new ArrayList<Groupe>();
            for (Groupe groupeCollection1GroupeToAttach : users.getGroupeCollection1()) {
                groupeCollection1GroupeToAttach = em.getReference(groupeCollection1GroupeToAttach.getClass(), groupeCollection1GroupeToAttach.getGroupeId());
                attachedGroupeCollection1.add(groupeCollection1GroupeToAttach);
            }
            users.setGroupeCollection1(attachedGroupeCollection1);
            Collection<Salle> attachedSalleCollection = new ArrayList<Salle>();
            for (Salle salleCollectionSalleToAttach : users.getSalleCollection()) {
                salleCollectionSalleToAttach = em.getReference(salleCollectionSalleToAttach.getClass(), salleCollectionSalleToAttach.getSalleId());
                attachedSalleCollection.add(salleCollectionSalleToAttach);
            }
            users.setSalleCollection(attachedSalleCollection);
            Collection<Salle> attachedSalleCollection1 = new ArrayList<Salle>();
            for (Salle salleCollection1SalleToAttach : users.getSalleCollection1()) {
                salleCollection1SalleToAttach = em.getReference(salleCollection1SalleToAttach.getClass(), salleCollection1SalleToAttach.getSalleId());
                attachedSalleCollection1.add(salleCollection1SalleToAttach);
            }
            users.setSalleCollection1(attachedSalleCollection1);
            Collection<Sections> attachedSectionsCollection = new ArrayList<Sections>();
            for (Sections sectionsCollectionSectionsToAttach : users.getSectionsCollection()) {
                sectionsCollectionSectionsToAttach = em.getReference(sectionsCollectionSectionsToAttach.getClass(), sectionsCollectionSectionsToAttach.getSectionsId());
                attachedSectionsCollection.add(sectionsCollectionSectionsToAttach);
            }
            users.setSectionsCollection(attachedSectionsCollection);
            Collection<Sections> attachedSectionsCollection1 = new ArrayList<Sections>();
            for (Sections sectionsCollection1SectionsToAttach : users.getSectionsCollection1()) {
                sectionsCollection1SectionsToAttach = em.getReference(sectionsCollection1SectionsToAttach.getClass(), sectionsCollection1SectionsToAttach.getSectionsId());
                attachedSectionsCollection1.add(sectionsCollection1SectionsToAttach);
            }
            users.setSectionsCollection1(attachedSectionsCollection1);
            Collection<Levels> attachedLevelsCollection = new ArrayList<Levels>();
            for (Levels levelsCollectionLevelsToAttach : users.getLevelsCollection()) {
                levelsCollectionLevelsToAttach = em.getReference(levelsCollectionLevelsToAttach.getClass(), levelsCollectionLevelsToAttach.getLevelsId());
                attachedLevelsCollection.add(levelsCollectionLevelsToAttach);
            }
            users.setLevelsCollection(attachedLevelsCollection);
            Collection<Levels> attachedLevelsCollection1 = new ArrayList<Levels>();
            for (Levels levelsCollection1LevelsToAttach : users.getLevelsCollection1()) {
                levelsCollection1LevelsToAttach = em.getReference(levelsCollection1LevelsToAttach.getClass(), levelsCollection1LevelsToAttach.getLevelsId());
                attachedLevelsCollection1.add(levelsCollection1LevelsToAttach);
            }
            users.setLevelsCollection1(attachedLevelsCollection1);
            Collection<Etablissement> attachedEtablissementCollection = new ArrayList<Etablissement>();
            for (Etablissement etablissementCollectionEtablissementToAttach : users.getEtablissementCollection()) {
                etablissementCollectionEtablissementToAttach = em.getReference(etablissementCollectionEtablissementToAttach.getClass(), etablissementCollectionEtablissementToAttach.getEtablissementId());
                attachedEtablissementCollection.add(etablissementCollectionEtablissementToAttach);
            }
            users.setEtablissementCollection(attachedEtablissementCollection);
            Collection<Etablissement> attachedEtablissementCollection1 = new ArrayList<Etablissement>();
            for (Etablissement etablissementCollection1EtablissementToAttach : users.getEtablissementCollection1()) {
                etablissementCollection1EtablissementToAttach = em.getReference(etablissementCollection1EtablissementToAttach.getClass(), etablissementCollection1EtablissementToAttach.getEtablissementId());
                attachedEtablissementCollection1.add(etablissementCollection1EtablissementToAttach);
            }
            users.setEtablissementCollection1(attachedEtablissementCollection1);
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : users.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getUsersId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            users.setUsersCollection(attachedUsersCollection);
            Collection<Users> attachedUsersCollection1 = new ArrayList<Users>();
            for (Users usersCollection1UsersToAttach : users.getUsersCollection1()) {
                usersCollection1UsersToAttach = em.getReference(usersCollection1UsersToAttach.getClass(), usersCollection1UsersToAttach.getUsersId());
                attachedUsersCollection1.add(usersCollection1UsersToAttach);
            }
            users.setUsersCollection1(attachedUsersCollection1);
            Collection<Widget> attachedWidgetCollection = new ArrayList<Widget>();
            for (Widget widgetCollectionWidgetToAttach : users.getWidgetCollection()) {
                widgetCollectionWidgetToAttach = em.getReference(widgetCollectionWidgetToAttach.getClass(), widgetCollectionWidgetToAttach.getWidgetId());
                attachedWidgetCollection.add(widgetCollectionWidgetToAttach);
            }
            users.setWidgetCollection(attachedWidgetCollection);
            Collection<Widget> attachedWidgetCollection1 = new ArrayList<Widget>();
            for (Widget widgetCollection1WidgetToAttach : users.getWidgetCollection1()) {
                widgetCollection1WidgetToAttach = em.getReference(widgetCollection1WidgetToAttach.getClass(), widgetCollection1WidgetToAttach.getWidgetId());
                attachedWidgetCollection1.add(widgetCollection1WidgetToAttach);
            }
            users.setWidgetCollection1(attachedWidgetCollection1);
            Collection<Classe> attachedClasseCollection = new ArrayList<Classe>();
            for (Classe classeCollectionClasseToAttach : users.getClasseCollection()) {
                classeCollectionClasseToAttach = em.getReference(classeCollectionClasseToAttach.getClass(), classeCollectionClasseToAttach.getClasseId());
                attachedClasseCollection.add(classeCollectionClasseToAttach);
            }
            users.setClasseCollection(attachedClasseCollection);
            Collection<Classe> attachedClasseCollection1 = new ArrayList<Classe>();
            for (Classe classeCollection1ClasseToAttach : users.getClasseCollection1()) {
                classeCollection1ClasseToAttach = em.getReference(classeCollection1ClasseToAttach.getClass(), classeCollection1ClasseToAttach.getClasseId());
                attachedClasseCollection1.add(classeCollection1ClasseToAttach);
            }
            users.setClasseCollection1(attachedClasseCollection1);
            em.persist(users);
            if (levelsId != null) {
                levelsId.setUserModified(users);
                levelsId = em.merge(levelsId);
            }
            if (userModified != null) {
                userModified.getUsersCollection().add(users);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getUsersCollection().add(users);
                userCreated = em.merge(userCreated);
            }
            for (Student studentCollectionStudent : users.getStudentCollection()) {
                Users oldUserModifiedOfStudentCollectionStudent = studentCollectionStudent.getUserModified();
                studentCollectionStudent.setUserModified(users);
                studentCollectionStudent = em.merge(studentCollectionStudent);
                if (oldUserModifiedOfStudentCollectionStudent != null) {
                    oldUserModifiedOfStudentCollectionStudent.getStudentCollection().remove(studentCollectionStudent);
                    oldUserModifiedOfStudentCollectionStudent = em.merge(oldUserModifiedOfStudentCollectionStudent);
                }
            }
            for (Student studentCollection1Student : users.getStudentCollection1()) {
                Users oldUserCreatedOfStudentCollection1Student = studentCollection1Student.getUserCreated();
                studentCollection1Student.setUserCreated(users);
                studentCollection1Student = em.merge(studentCollection1Student);
                if (oldUserCreatedOfStudentCollection1Student != null) {
                    oldUserCreatedOfStudentCollection1Student.getStudentCollection1().remove(studentCollection1Student);
                    oldUserCreatedOfStudentCollection1Student = em.merge(oldUserCreatedOfStudentCollection1Student);
                }
            }
            for (Groupe groupeCollectionGroupe : users.getGroupeCollection()) {
                Users oldUserModifiedOfGroupeCollectionGroupe = groupeCollectionGroupe.getUserModified();
                groupeCollectionGroupe.setUserModified(users);
                groupeCollectionGroupe = em.merge(groupeCollectionGroupe);
                if (oldUserModifiedOfGroupeCollectionGroupe != null) {
                    oldUserModifiedOfGroupeCollectionGroupe.getGroupeCollection().remove(groupeCollectionGroupe);
                    oldUserModifiedOfGroupeCollectionGroupe = em.merge(oldUserModifiedOfGroupeCollectionGroupe);
                }
            }
            for (Groupe groupeCollection1Groupe : users.getGroupeCollection1()) {
                Users oldUserCreatedOfGroupeCollection1Groupe = groupeCollection1Groupe.getUserCreated();
                groupeCollection1Groupe.setUserCreated(users);
                groupeCollection1Groupe = em.merge(groupeCollection1Groupe);
                if (oldUserCreatedOfGroupeCollection1Groupe != null) {
                    oldUserCreatedOfGroupeCollection1Groupe.getGroupeCollection1().remove(groupeCollection1Groupe);
                    oldUserCreatedOfGroupeCollection1Groupe = em.merge(oldUserCreatedOfGroupeCollection1Groupe);
                }
            }
            for (Salle salleCollectionSalle : users.getSalleCollection()) {
                Users oldUserModifiedOfSalleCollectionSalle = salleCollectionSalle.getUserModified();
                salleCollectionSalle.setUserModified(users);
                salleCollectionSalle = em.merge(salleCollectionSalle);
                if (oldUserModifiedOfSalleCollectionSalle != null) {
                    oldUserModifiedOfSalleCollectionSalle.getSalleCollection().remove(salleCollectionSalle);
                    oldUserModifiedOfSalleCollectionSalle = em.merge(oldUserModifiedOfSalleCollectionSalle);
                }
            }
            for (Salle salleCollection1Salle : users.getSalleCollection1()) {
                Users oldUserCreatedOfSalleCollection1Salle = salleCollection1Salle.getUserCreated();
                salleCollection1Salle.setUserCreated(users);
                salleCollection1Salle = em.merge(salleCollection1Salle);
                if (oldUserCreatedOfSalleCollection1Salle != null) {
                    oldUserCreatedOfSalleCollection1Salle.getSalleCollection1().remove(salleCollection1Salle);
                    oldUserCreatedOfSalleCollection1Salle = em.merge(oldUserCreatedOfSalleCollection1Salle);
                }
            }
            for (Sections sectionsCollectionSections : users.getSectionsCollection()) {
                Users oldUserModifiedOfSectionsCollectionSections = sectionsCollectionSections.getUserModified();
                sectionsCollectionSections.setUserModified(users);
                sectionsCollectionSections = em.merge(sectionsCollectionSections);
                if (oldUserModifiedOfSectionsCollectionSections != null) {
                    oldUserModifiedOfSectionsCollectionSections.getSectionsCollection().remove(sectionsCollectionSections);
                    oldUserModifiedOfSectionsCollectionSections = em.merge(oldUserModifiedOfSectionsCollectionSections);
                }
            }
            for (Sections sectionsCollection1Sections : users.getSectionsCollection1()) {
                Users oldUserCreatedOfSectionsCollection1Sections = sectionsCollection1Sections.getUserCreated();
                sectionsCollection1Sections.setUserCreated(users);
                sectionsCollection1Sections = em.merge(sectionsCollection1Sections);
                if (oldUserCreatedOfSectionsCollection1Sections != null) {
                    oldUserCreatedOfSectionsCollection1Sections.getSectionsCollection1().remove(sectionsCollection1Sections);
                    oldUserCreatedOfSectionsCollection1Sections = em.merge(oldUserCreatedOfSectionsCollection1Sections);
                }
            }
            for (Levels levelsCollectionLevels : users.getLevelsCollection()) {
                Users oldUserModifiedOfLevelsCollectionLevels = levelsCollectionLevels.getUserModified();
                levelsCollectionLevels.setUserModified(users);
                levelsCollectionLevels = em.merge(levelsCollectionLevels);
                if (oldUserModifiedOfLevelsCollectionLevels != null) {
                    oldUserModifiedOfLevelsCollectionLevels.getLevelsCollection().remove(levelsCollectionLevels);
                    oldUserModifiedOfLevelsCollectionLevels = em.merge(oldUserModifiedOfLevelsCollectionLevels);
                }
            }
            for (Levels levelsCollection1Levels : users.getLevelsCollection1()) {
                Users oldUserCreatedOfLevelsCollection1Levels = levelsCollection1Levels.getUserCreated();
                levelsCollection1Levels.setUserCreated(users);
                levelsCollection1Levels = em.merge(levelsCollection1Levels);
                if (oldUserCreatedOfLevelsCollection1Levels != null) {
                    oldUserCreatedOfLevelsCollection1Levels.getLevelsCollection1().remove(levelsCollection1Levels);
                    oldUserCreatedOfLevelsCollection1Levels = em.merge(oldUserCreatedOfLevelsCollection1Levels);
                }
            }
            for (Etablissement etablissementCollectionEtablissement : users.getEtablissementCollection()) {
                Users oldUserModifiedOfEtablissementCollectionEtablissement = etablissementCollectionEtablissement.getUserModified();
                etablissementCollectionEtablissement.setUserModified(users);
                etablissementCollectionEtablissement = em.merge(etablissementCollectionEtablissement);
                if (oldUserModifiedOfEtablissementCollectionEtablissement != null) {
                    oldUserModifiedOfEtablissementCollectionEtablissement.getEtablissementCollection().remove(etablissementCollectionEtablissement);
                    oldUserModifiedOfEtablissementCollectionEtablissement = em.merge(oldUserModifiedOfEtablissementCollectionEtablissement);
                }
            }
            for (Etablissement etablissementCollection1Etablissement : users.getEtablissementCollection1()) {
                Users oldUserCreatedOfEtablissementCollection1Etablissement = etablissementCollection1Etablissement.getUserCreated();
                etablissementCollection1Etablissement.setUserCreated(users);
                etablissementCollection1Etablissement = em.merge(etablissementCollection1Etablissement);
                if (oldUserCreatedOfEtablissementCollection1Etablissement != null) {
                    oldUserCreatedOfEtablissementCollection1Etablissement.getEtablissementCollection1().remove(etablissementCollection1Etablissement);
                    oldUserCreatedOfEtablissementCollection1Etablissement = em.merge(oldUserCreatedOfEtablissementCollection1Etablissement);
                }
            }
            for (Users usersCollectionUsers : users.getUsersCollection()) {
                Users oldUserModifiedOfUsersCollectionUsers = usersCollectionUsers.getUserModified();
                usersCollectionUsers.setUserModified(users);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldUserModifiedOfUsersCollectionUsers != null) {
                    oldUserModifiedOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldUserModifiedOfUsersCollectionUsers = em.merge(oldUserModifiedOfUsersCollectionUsers);
                }
            }
            for (Users usersCollection1Users : users.getUsersCollection1()) {
                Users oldUserCreatedOfUsersCollection1Users = usersCollection1Users.getUserCreated();
                usersCollection1Users.setUserCreated(users);
                usersCollection1Users = em.merge(usersCollection1Users);
                if (oldUserCreatedOfUsersCollection1Users != null) {
                    oldUserCreatedOfUsersCollection1Users.getUsersCollection1().remove(usersCollection1Users);
                    oldUserCreatedOfUsersCollection1Users = em.merge(oldUserCreatedOfUsersCollection1Users);
                }
            }
            for (Widget widgetCollectionWidget : users.getWidgetCollection()) {
                Users oldUserModifiedOfWidgetCollectionWidget = widgetCollectionWidget.getUserModified();
                widgetCollectionWidget.setUserModified(users);
                widgetCollectionWidget = em.merge(widgetCollectionWidget);
                if (oldUserModifiedOfWidgetCollectionWidget != null) {
                    oldUserModifiedOfWidgetCollectionWidget.getWidgetCollection().remove(widgetCollectionWidget);
                    oldUserModifiedOfWidgetCollectionWidget = em.merge(oldUserModifiedOfWidgetCollectionWidget);
                }
            }
            for (Widget widgetCollection1Widget : users.getWidgetCollection1()) {
                Users oldUserCreatedOfWidgetCollection1Widget = widgetCollection1Widget.getUserCreated();
                widgetCollection1Widget.setUserCreated(users);
                widgetCollection1Widget = em.merge(widgetCollection1Widget);
                if (oldUserCreatedOfWidgetCollection1Widget != null) {
                    oldUserCreatedOfWidgetCollection1Widget.getWidgetCollection1().remove(widgetCollection1Widget);
                    oldUserCreatedOfWidgetCollection1Widget = em.merge(oldUserCreatedOfWidgetCollection1Widget);
                }
            }
            for (Classe classeCollectionClasse : users.getClasseCollection()) {
                Users oldUserModifiedOfClasseCollectionClasse = classeCollectionClasse.getUserModified();
                classeCollectionClasse.setUserModified(users);
                classeCollectionClasse = em.merge(classeCollectionClasse);
                if (oldUserModifiedOfClasseCollectionClasse != null) {
                    oldUserModifiedOfClasseCollectionClasse.getClasseCollection().remove(classeCollectionClasse);
                    oldUserModifiedOfClasseCollectionClasse = em.merge(oldUserModifiedOfClasseCollectionClasse);
                }
            }
            for (Classe classeCollection1Classe : users.getClasseCollection1()) {
                Users oldUserCreatedOfClasseCollection1Classe = classeCollection1Classe.getUserCreated();
                classeCollection1Classe.setUserCreated(users);
                classeCollection1Classe = em.merge(classeCollection1Classe);
                if (oldUserCreatedOfClasseCollection1Classe != null) {
                    oldUserCreatedOfClasseCollection1Classe.getClasseCollection1().remove(classeCollection1Classe);
                    oldUserCreatedOfClasseCollection1Classe = em.merge(oldUserCreatedOfClasseCollection1Classe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsers(users.getUsersId()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getUsersId());
            Levels levelsIdOld = persistentUsers.getLevelsId();
            Levels levelsIdNew = users.getLevelsId();
            Users userModifiedOld = persistentUsers.getUserModified();
            Users userModifiedNew = users.getUserModified();
            Users userCreatedOld = persistentUsers.getUserCreated();
            Users userCreatedNew = users.getUserCreated();
            Collection<Student> studentCollectionOld = persistentUsers.getStudentCollection();
            Collection<Student> studentCollectionNew = users.getStudentCollection();
            Collection<Student> studentCollection1Old = persistentUsers.getStudentCollection1();
            Collection<Student> studentCollection1New = users.getStudentCollection1();
            Collection<Groupe> groupeCollectionOld = persistentUsers.getGroupeCollection();
            Collection<Groupe> groupeCollectionNew = users.getGroupeCollection();
            Collection<Groupe> groupeCollection1Old = persistentUsers.getGroupeCollection1();
            Collection<Groupe> groupeCollection1New = users.getGroupeCollection1();
            Collection<Salle> salleCollectionOld = persistentUsers.getSalleCollection();
            Collection<Salle> salleCollectionNew = users.getSalleCollection();
            Collection<Salle> salleCollection1Old = persistentUsers.getSalleCollection1();
            Collection<Salle> salleCollection1New = users.getSalleCollection1();
            Collection<Sections> sectionsCollectionOld = persistentUsers.getSectionsCollection();
            Collection<Sections> sectionsCollectionNew = users.getSectionsCollection();
            Collection<Sections> sectionsCollection1Old = persistentUsers.getSectionsCollection1();
            Collection<Sections> sectionsCollection1New = users.getSectionsCollection1();
            Collection<Levels> levelsCollectionOld = persistentUsers.getLevelsCollection();
            Collection<Levels> levelsCollectionNew = users.getLevelsCollection();
            Collection<Levels> levelsCollection1Old = persistentUsers.getLevelsCollection1();
            Collection<Levels> levelsCollection1New = users.getLevelsCollection1();
            Collection<Etablissement> etablissementCollectionOld = persistentUsers.getEtablissementCollection();
            Collection<Etablissement> etablissementCollectionNew = users.getEtablissementCollection();
            Collection<Etablissement> etablissementCollection1Old = persistentUsers.getEtablissementCollection1();
            Collection<Etablissement> etablissementCollection1New = users.getEtablissementCollection1();
            Collection<Users> usersCollectionOld = persistentUsers.getUsersCollection();
            Collection<Users> usersCollectionNew = users.getUsersCollection();
            Collection<Users> usersCollection1Old = persistentUsers.getUsersCollection1();
            Collection<Users> usersCollection1New = users.getUsersCollection1();
            Collection<Widget> widgetCollectionOld = persistentUsers.getWidgetCollection();
            Collection<Widget> widgetCollectionNew = users.getWidgetCollection();
            Collection<Widget> widgetCollection1Old = persistentUsers.getWidgetCollection1();
            Collection<Widget> widgetCollection1New = users.getWidgetCollection1();
            Collection<Classe> classeCollectionOld = persistentUsers.getClasseCollection();
            Collection<Classe> classeCollectionNew = users.getClasseCollection();
            Collection<Classe> classeCollection1Old = persistentUsers.getClasseCollection1();
            Collection<Classe> classeCollection1New = users.getClasseCollection1();
            List<String> illegalOrphanMessages = null;
            if (levelsIdNew != null && !levelsIdNew.equals(levelsIdOld)) {
                Users oldUserModifiedOfLevelsId = levelsIdNew.getUserModified();
                if (oldUserModifiedOfLevelsId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Levels " + levelsIdNew + " already has an item of type Users whose levelsId column cannot be null. Please make another selection for the levelsId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (levelsIdNew != null) {
                levelsIdNew = em.getReference(levelsIdNew.getClass(), levelsIdNew.getLevelsId());
                users.setLevelsId(levelsIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                users.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                users.setUserCreated(userCreatedNew);
            }
            Collection<Student> attachedStudentCollectionNew = new ArrayList<Student>();
            for (Student studentCollectionNewStudentToAttach : studentCollectionNew) {
                studentCollectionNewStudentToAttach = em.getReference(studentCollectionNewStudentToAttach.getClass(), studentCollectionNewStudentToAttach.getStudentId());
                attachedStudentCollectionNew.add(studentCollectionNewStudentToAttach);
            }
            studentCollectionNew = attachedStudentCollectionNew;
            users.setStudentCollection(studentCollectionNew);
            Collection<Student> attachedStudentCollection1New = new ArrayList<Student>();
            for (Student studentCollection1NewStudentToAttach : studentCollection1New) {
                studentCollection1NewStudentToAttach = em.getReference(studentCollection1NewStudentToAttach.getClass(), studentCollection1NewStudentToAttach.getStudentId());
                attachedStudentCollection1New.add(studentCollection1NewStudentToAttach);
            }
            studentCollection1New = attachedStudentCollection1New;
            users.setStudentCollection1(studentCollection1New);
            Collection<Groupe> attachedGroupeCollectionNew = new ArrayList<Groupe>();
            for (Groupe groupeCollectionNewGroupeToAttach : groupeCollectionNew) {
                groupeCollectionNewGroupeToAttach = em.getReference(groupeCollectionNewGroupeToAttach.getClass(), groupeCollectionNewGroupeToAttach.getGroupeId());
                attachedGroupeCollectionNew.add(groupeCollectionNewGroupeToAttach);
            }
            groupeCollectionNew = attachedGroupeCollectionNew;
            users.setGroupeCollection(groupeCollectionNew);
            Collection<Groupe> attachedGroupeCollection1New = new ArrayList<Groupe>();
            for (Groupe groupeCollection1NewGroupeToAttach : groupeCollection1New) {
                groupeCollection1NewGroupeToAttach = em.getReference(groupeCollection1NewGroupeToAttach.getClass(), groupeCollection1NewGroupeToAttach.getGroupeId());
                attachedGroupeCollection1New.add(groupeCollection1NewGroupeToAttach);
            }
            groupeCollection1New = attachedGroupeCollection1New;
            users.setGroupeCollection1(groupeCollection1New);
            Collection<Salle> attachedSalleCollectionNew = new ArrayList<Salle>();
            for (Salle salleCollectionNewSalleToAttach : salleCollectionNew) {
                salleCollectionNewSalleToAttach = em.getReference(salleCollectionNewSalleToAttach.getClass(), salleCollectionNewSalleToAttach.getSalleId());
                attachedSalleCollectionNew.add(salleCollectionNewSalleToAttach);
            }
            salleCollectionNew = attachedSalleCollectionNew;
            users.setSalleCollection(salleCollectionNew);
            Collection<Salle> attachedSalleCollection1New = new ArrayList<Salle>();
            for (Salle salleCollection1NewSalleToAttach : salleCollection1New) {
                salleCollection1NewSalleToAttach = em.getReference(salleCollection1NewSalleToAttach.getClass(), salleCollection1NewSalleToAttach.getSalleId());
                attachedSalleCollection1New.add(salleCollection1NewSalleToAttach);
            }
            salleCollection1New = attachedSalleCollection1New;
            users.setSalleCollection1(salleCollection1New);
            Collection<Sections> attachedSectionsCollectionNew = new ArrayList<Sections>();
            for (Sections sectionsCollectionNewSectionsToAttach : sectionsCollectionNew) {
                sectionsCollectionNewSectionsToAttach = em.getReference(sectionsCollectionNewSectionsToAttach.getClass(), sectionsCollectionNewSectionsToAttach.getSectionsId());
                attachedSectionsCollectionNew.add(sectionsCollectionNewSectionsToAttach);
            }
            sectionsCollectionNew = attachedSectionsCollectionNew;
            users.setSectionsCollection(sectionsCollectionNew);
            Collection<Sections> attachedSectionsCollection1New = new ArrayList<Sections>();
            for (Sections sectionsCollection1NewSectionsToAttach : sectionsCollection1New) {
                sectionsCollection1NewSectionsToAttach = em.getReference(sectionsCollection1NewSectionsToAttach.getClass(), sectionsCollection1NewSectionsToAttach.getSectionsId());
                attachedSectionsCollection1New.add(sectionsCollection1NewSectionsToAttach);
            }
            sectionsCollection1New = attachedSectionsCollection1New;
            users.setSectionsCollection1(sectionsCollection1New);
            Collection<Levels> attachedLevelsCollectionNew = new ArrayList<Levels>();
            for (Levels levelsCollectionNewLevelsToAttach : levelsCollectionNew) {
                levelsCollectionNewLevelsToAttach = em.getReference(levelsCollectionNewLevelsToAttach.getClass(), levelsCollectionNewLevelsToAttach.getLevelsId());
                attachedLevelsCollectionNew.add(levelsCollectionNewLevelsToAttach);
            }
            levelsCollectionNew = attachedLevelsCollectionNew;
            users.setLevelsCollection(levelsCollectionNew);
            Collection<Levels> attachedLevelsCollection1New = new ArrayList<Levels>();
            for (Levels levelsCollection1NewLevelsToAttach : levelsCollection1New) {
                levelsCollection1NewLevelsToAttach = em.getReference(levelsCollection1NewLevelsToAttach.getClass(), levelsCollection1NewLevelsToAttach.getLevelsId());
                attachedLevelsCollection1New.add(levelsCollection1NewLevelsToAttach);
            }
            levelsCollection1New = attachedLevelsCollection1New;
            users.setLevelsCollection1(levelsCollection1New);
            Collection<Etablissement> attachedEtablissementCollectionNew = new ArrayList<Etablissement>();
            for (Etablissement etablissementCollectionNewEtablissementToAttach : etablissementCollectionNew) {
                etablissementCollectionNewEtablissementToAttach = em.getReference(etablissementCollectionNewEtablissementToAttach.getClass(), etablissementCollectionNewEtablissementToAttach.getEtablissementId());
                attachedEtablissementCollectionNew.add(etablissementCollectionNewEtablissementToAttach);
            }
            etablissementCollectionNew = attachedEtablissementCollectionNew;
            users.setEtablissementCollection(etablissementCollectionNew);
            Collection<Etablissement> attachedEtablissementCollection1New = new ArrayList<Etablissement>();
            for (Etablissement etablissementCollection1NewEtablissementToAttach : etablissementCollection1New) {
                etablissementCollection1NewEtablissementToAttach = em.getReference(etablissementCollection1NewEtablissementToAttach.getClass(), etablissementCollection1NewEtablissementToAttach.getEtablissementId());
                attachedEtablissementCollection1New.add(etablissementCollection1NewEtablissementToAttach);
            }
            etablissementCollection1New = attachedEtablissementCollection1New;
            users.setEtablissementCollection1(etablissementCollection1New);
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getUsersId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            users.setUsersCollection(usersCollectionNew);
            Collection<Users> attachedUsersCollection1New = new ArrayList<Users>();
            for (Users usersCollection1NewUsersToAttach : usersCollection1New) {
                usersCollection1NewUsersToAttach = em.getReference(usersCollection1NewUsersToAttach.getClass(), usersCollection1NewUsersToAttach.getUsersId());
                attachedUsersCollection1New.add(usersCollection1NewUsersToAttach);
            }
            usersCollection1New = attachedUsersCollection1New;
            users.setUsersCollection1(usersCollection1New);
            Collection<Widget> attachedWidgetCollectionNew = new ArrayList<Widget>();
            for (Widget widgetCollectionNewWidgetToAttach : widgetCollectionNew) {
                widgetCollectionNewWidgetToAttach = em.getReference(widgetCollectionNewWidgetToAttach.getClass(), widgetCollectionNewWidgetToAttach.getWidgetId());
                attachedWidgetCollectionNew.add(widgetCollectionNewWidgetToAttach);
            }
            widgetCollectionNew = attachedWidgetCollectionNew;
            users.setWidgetCollection(widgetCollectionNew);
            Collection<Widget> attachedWidgetCollection1New = new ArrayList<Widget>();
            for (Widget widgetCollection1NewWidgetToAttach : widgetCollection1New) {
                widgetCollection1NewWidgetToAttach = em.getReference(widgetCollection1NewWidgetToAttach.getClass(), widgetCollection1NewWidgetToAttach.getWidgetId());
                attachedWidgetCollection1New.add(widgetCollection1NewWidgetToAttach);
            }
            widgetCollection1New = attachedWidgetCollection1New;
            users.setWidgetCollection1(widgetCollection1New);
            Collection<Classe> attachedClasseCollectionNew = new ArrayList<Classe>();
            for (Classe classeCollectionNewClasseToAttach : classeCollectionNew) {
                classeCollectionNewClasseToAttach = em.getReference(classeCollectionNewClasseToAttach.getClass(), classeCollectionNewClasseToAttach.getClasseId());
                attachedClasseCollectionNew.add(classeCollectionNewClasseToAttach);
            }
            classeCollectionNew = attachedClasseCollectionNew;
            users.setClasseCollection(classeCollectionNew);
            Collection<Classe> attachedClasseCollection1New = new ArrayList<Classe>();
            for (Classe classeCollection1NewClasseToAttach : classeCollection1New) {
                classeCollection1NewClasseToAttach = em.getReference(classeCollection1NewClasseToAttach.getClass(), classeCollection1NewClasseToAttach.getClasseId());
                attachedClasseCollection1New.add(classeCollection1NewClasseToAttach);
            }
            classeCollection1New = attachedClasseCollection1New;
            users.setClasseCollection1(classeCollection1New);
            users = em.merge(users);
            if (levelsIdOld != null && !levelsIdOld.equals(levelsIdNew)) {
                levelsIdOld.setUserModified(null);
                levelsIdOld = em.merge(levelsIdOld);
            }
            if (levelsIdNew != null && !levelsIdNew.equals(levelsIdOld)) {
                levelsIdNew.setUserModified(users);
                levelsIdNew = em.merge(levelsIdNew);
            }
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getUsersCollection().remove(users);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getUsersCollection().add(users);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getUsersCollection().remove(users);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getUsersCollection().add(users);
                userCreatedNew = em.merge(userCreatedNew);
            }
            for (Student studentCollectionOldStudent : studentCollectionOld) {
                if (!studentCollectionNew.contains(studentCollectionOldStudent)) {
                    studentCollectionOldStudent.setUserModified(null);
                    studentCollectionOldStudent = em.merge(studentCollectionOldStudent);
                }
            }
            for (Student studentCollectionNewStudent : studentCollectionNew) {
                if (!studentCollectionOld.contains(studentCollectionNewStudent)) {
                    Users oldUserModifiedOfStudentCollectionNewStudent = studentCollectionNewStudent.getUserModified();
                    studentCollectionNewStudent.setUserModified(users);
                    studentCollectionNewStudent = em.merge(studentCollectionNewStudent);
                    if (oldUserModifiedOfStudentCollectionNewStudent != null && !oldUserModifiedOfStudentCollectionNewStudent.equals(users)) {
                        oldUserModifiedOfStudentCollectionNewStudent.getStudentCollection().remove(studentCollectionNewStudent);
                        oldUserModifiedOfStudentCollectionNewStudent = em.merge(oldUserModifiedOfStudentCollectionNewStudent);
                    }
                }
            }
            for (Student studentCollection1OldStudent : studentCollection1Old) {
                if (!studentCollection1New.contains(studentCollection1OldStudent)) {
                    studentCollection1OldStudent.setUserCreated(null);
                    studentCollection1OldStudent = em.merge(studentCollection1OldStudent);
                }
            }
            for (Student studentCollection1NewStudent : studentCollection1New) {
                if (!studentCollection1Old.contains(studentCollection1NewStudent)) {
                    Users oldUserCreatedOfStudentCollection1NewStudent = studentCollection1NewStudent.getUserCreated();
                    studentCollection1NewStudent.setUserCreated(users);
                    studentCollection1NewStudent = em.merge(studentCollection1NewStudent);
                    if (oldUserCreatedOfStudentCollection1NewStudent != null && !oldUserCreatedOfStudentCollection1NewStudent.equals(users)) {
                        oldUserCreatedOfStudentCollection1NewStudent.getStudentCollection1().remove(studentCollection1NewStudent);
                        oldUserCreatedOfStudentCollection1NewStudent = em.merge(oldUserCreatedOfStudentCollection1NewStudent);
                    }
                }
            }
            for (Groupe groupeCollectionOldGroupe : groupeCollectionOld) {
                if (!groupeCollectionNew.contains(groupeCollectionOldGroupe)) {
                    groupeCollectionOldGroupe.setUserModified(null);
                    groupeCollectionOldGroupe = em.merge(groupeCollectionOldGroupe);
                }
            }
            for (Groupe groupeCollectionNewGroupe : groupeCollectionNew) {
                if (!groupeCollectionOld.contains(groupeCollectionNewGroupe)) {
                    Users oldUserModifiedOfGroupeCollectionNewGroupe = groupeCollectionNewGroupe.getUserModified();
                    groupeCollectionNewGroupe.setUserModified(users);
                    groupeCollectionNewGroupe = em.merge(groupeCollectionNewGroupe);
                    if (oldUserModifiedOfGroupeCollectionNewGroupe != null && !oldUserModifiedOfGroupeCollectionNewGroupe.equals(users)) {
                        oldUserModifiedOfGroupeCollectionNewGroupe.getGroupeCollection().remove(groupeCollectionNewGroupe);
                        oldUserModifiedOfGroupeCollectionNewGroupe = em.merge(oldUserModifiedOfGroupeCollectionNewGroupe);
                    }
                }
            }
            for (Groupe groupeCollection1OldGroupe : groupeCollection1Old) {
                if (!groupeCollection1New.contains(groupeCollection1OldGroupe)) {
                    groupeCollection1OldGroupe.setUserCreated(null);
                    groupeCollection1OldGroupe = em.merge(groupeCollection1OldGroupe);
                }
            }
            for (Groupe groupeCollection1NewGroupe : groupeCollection1New) {
                if (!groupeCollection1Old.contains(groupeCollection1NewGroupe)) {
                    Users oldUserCreatedOfGroupeCollection1NewGroupe = groupeCollection1NewGroupe.getUserCreated();
                    groupeCollection1NewGroupe.setUserCreated(users);
                    groupeCollection1NewGroupe = em.merge(groupeCollection1NewGroupe);
                    if (oldUserCreatedOfGroupeCollection1NewGroupe != null && !oldUserCreatedOfGroupeCollection1NewGroupe.equals(users)) {
                        oldUserCreatedOfGroupeCollection1NewGroupe.getGroupeCollection1().remove(groupeCollection1NewGroupe);
                        oldUserCreatedOfGroupeCollection1NewGroupe = em.merge(oldUserCreatedOfGroupeCollection1NewGroupe);
                    }
                }
            }
            for (Salle salleCollectionOldSalle : salleCollectionOld) {
                if (!salleCollectionNew.contains(salleCollectionOldSalle)) {
                    salleCollectionOldSalle.setUserModified(null);
                    salleCollectionOldSalle = em.merge(salleCollectionOldSalle);
                }
            }
            for (Salle salleCollectionNewSalle : salleCollectionNew) {
                if (!salleCollectionOld.contains(salleCollectionNewSalle)) {
                    Users oldUserModifiedOfSalleCollectionNewSalle = salleCollectionNewSalle.getUserModified();
                    salleCollectionNewSalle.setUserModified(users);
                    salleCollectionNewSalle = em.merge(salleCollectionNewSalle);
                    if (oldUserModifiedOfSalleCollectionNewSalle != null && !oldUserModifiedOfSalleCollectionNewSalle.equals(users)) {
                        oldUserModifiedOfSalleCollectionNewSalle.getSalleCollection().remove(salleCollectionNewSalle);
                        oldUserModifiedOfSalleCollectionNewSalle = em.merge(oldUserModifiedOfSalleCollectionNewSalle);
                    }
                }
            }
            for (Salle salleCollection1OldSalle : salleCollection1Old) {
                if (!salleCollection1New.contains(salleCollection1OldSalle)) {
                    salleCollection1OldSalle.setUserCreated(null);
                    salleCollection1OldSalle = em.merge(salleCollection1OldSalle);
                }
            }
            for (Salle salleCollection1NewSalle : salleCollection1New) {
                if (!salleCollection1Old.contains(salleCollection1NewSalle)) {
                    Users oldUserCreatedOfSalleCollection1NewSalle = salleCollection1NewSalle.getUserCreated();
                    salleCollection1NewSalle.setUserCreated(users);
                    salleCollection1NewSalle = em.merge(salleCollection1NewSalle);
                    if (oldUserCreatedOfSalleCollection1NewSalle != null && !oldUserCreatedOfSalleCollection1NewSalle.equals(users)) {
                        oldUserCreatedOfSalleCollection1NewSalle.getSalleCollection1().remove(salleCollection1NewSalle);
                        oldUserCreatedOfSalleCollection1NewSalle = em.merge(oldUserCreatedOfSalleCollection1NewSalle);
                    }
                }
            }
            for (Sections sectionsCollectionOldSections : sectionsCollectionOld) {
                if (!sectionsCollectionNew.contains(sectionsCollectionOldSections)) {
                    sectionsCollectionOldSections.setUserModified(null);
                    sectionsCollectionOldSections = em.merge(sectionsCollectionOldSections);
                }
            }
            for (Sections sectionsCollectionNewSections : sectionsCollectionNew) {
                if (!sectionsCollectionOld.contains(sectionsCollectionNewSections)) {
                    Users oldUserModifiedOfSectionsCollectionNewSections = sectionsCollectionNewSections.getUserModified();
                    sectionsCollectionNewSections.setUserModified(users);
                    sectionsCollectionNewSections = em.merge(sectionsCollectionNewSections);
                    if (oldUserModifiedOfSectionsCollectionNewSections != null && !oldUserModifiedOfSectionsCollectionNewSections.equals(users)) {
                        oldUserModifiedOfSectionsCollectionNewSections.getSectionsCollection().remove(sectionsCollectionNewSections);
                        oldUserModifiedOfSectionsCollectionNewSections = em.merge(oldUserModifiedOfSectionsCollectionNewSections);
                    }
                }
            }
            for (Sections sectionsCollection1OldSections : sectionsCollection1Old) {
                if (!sectionsCollection1New.contains(sectionsCollection1OldSections)) {
                    sectionsCollection1OldSections.setUserCreated(null);
                    sectionsCollection1OldSections = em.merge(sectionsCollection1OldSections);
                }
            }
            for (Sections sectionsCollection1NewSections : sectionsCollection1New) {
                if (!sectionsCollection1Old.contains(sectionsCollection1NewSections)) {
                    Users oldUserCreatedOfSectionsCollection1NewSections = sectionsCollection1NewSections.getUserCreated();
                    sectionsCollection1NewSections.setUserCreated(users);
                    sectionsCollection1NewSections = em.merge(sectionsCollection1NewSections);
                    if (oldUserCreatedOfSectionsCollection1NewSections != null && !oldUserCreatedOfSectionsCollection1NewSections.equals(users)) {
                        oldUserCreatedOfSectionsCollection1NewSections.getSectionsCollection1().remove(sectionsCollection1NewSections);
                        oldUserCreatedOfSectionsCollection1NewSections = em.merge(oldUserCreatedOfSectionsCollection1NewSections);
                    }
                }
            }
            for (Levels levelsCollectionOldLevels : levelsCollectionOld) {
                if (!levelsCollectionNew.contains(levelsCollectionOldLevels)) {
                    levelsCollectionOldLevels.setUserModified(null);
                    levelsCollectionOldLevels = em.merge(levelsCollectionOldLevels);
                }
            }
            for (Levels levelsCollectionNewLevels : levelsCollectionNew) {
                if (!levelsCollectionOld.contains(levelsCollectionNewLevels)) {
                    Users oldUserModifiedOfLevelsCollectionNewLevels = levelsCollectionNewLevels.getUserModified();
                    levelsCollectionNewLevels.setUserModified(users);
                    levelsCollectionNewLevels = em.merge(levelsCollectionNewLevels);
                    if (oldUserModifiedOfLevelsCollectionNewLevels != null && !oldUserModifiedOfLevelsCollectionNewLevels.equals(users)) {
                        oldUserModifiedOfLevelsCollectionNewLevels.getLevelsCollection().remove(levelsCollectionNewLevels);
                        oldUserModifiedOfLevelsCollectionNewLevels = em.merge(oldUserModifiedOfLevelsCollectionNewLevels);
                    }
                }
            }
            for (Levels levelsCollection1OldLevels : levelsCollection1Old) {
                if (!levelsCollection1New.contains(levelsCollection1OldLevels)) {
                    levelsCollection1OldLevels.setUserCreated(null);
                    levelsCollection1OldLevels = em.merge(levelsCollection1OldLevels);
                }
            }
            for (Levels levelsCollection1NewLevels : levelsCollection1New) {
                if (!levelsCollection1Old.contains(levelsCollection1NewLevels)) {
                    Users oldUserCreatedOfLevelsCollection1NewLevels = levelsCollection1NewLevels.getUserCreated();
                    levelsCollection1NewLevels.setUserCreated(users);
                    levelsCollection1NewLevels = em.merge(levelsCollection1NewLevels);
                    if (oldUserCreatedOfLevelsCollection1NewLevels != null && !oldUserCreatedOfLevelsCollection1NewLevels.equals(users)) {
                        oldUserCreatedOfLevelsCollection1NewLevels.getLevelsCollection1().remove(levelsCollection1NewLevels);
                        oldUserCreatedOfLevelsCollection1NewLevels = em.merge(oldUserCreatedOfLevelsCollection1NewLevels);
                    }
                }
            }
            for (Etablissement etablissementCollectionOldEtablissement : etablissementCollectionOld) {
                if (!etablissementCollectionNew.contains(etablissementCollectionOldEtablissement)) {
                    etablissementCollectionOldEtablissement.setUserModified(null);
                    etablissementCollectionOldEtablissement = em.merge(etablissementCollectionOldEtablissement);
                }
            }
            for (Etablissement etablissementCollectionNewEtablissement : etablissementCollectionNew) {
                if (!etablissementCollectionOld.contains(etablissementCollectionNewEtablissement)) {
                    Users oldUserModifiedOfEtablissementCollectionNewEtablissement = etablissementCollectionNewEtablissement.getUserModified();
                    etablissementCollectionNewEtablissement.setUserModified(users);
                    etablissementCollectionNewEtablissement = em.merge(etablissementCollectionNewEtablissement);
                    if (oldUserModifiedOfEtablissementCollectionNewEtablissement != null && !oldUserModifiedOfEtablissementCollectionNewEtablissement.equals(users)) {
                        oldUserModifiedOfEtablissementCollectionNewEtablissement.getEtablissementCollection().remove(etablissementCollectionNewEtablissement);
                        oldUserModifiedOfEtablissementCollectionNewEtablissement = em.merge(oldUserModifiedOfEtablissementCollectionNewEtablissement);
                    }
                }
            }
            for (Etablissement etablissementCollection1OldEtablissement : etablissementCollection1Old) {
                if (!etablissementCollection1New.contains(etablissementCollection1OldEtablissement)) {
                    etablissementCollection1OldEtablissement.setUserCreated(null);
                    etablissementCollection1OldEtablissement = em.merge(etablissementCollection1OldEtablissement);
                }
            }
            for (Etablissement etablissementCollection1NewEtablissement : etablissementCollection1New) {
                if (!etablissementCollection1Old.contains(etablissementCollection1NewEtablissement)) {
                    Users oldUserCreatedOfEtablissementCollection1NewEtablissement = etablissementCollection1NewEtablissement.getUserCreated();
                    etablissementCollection1NewEtablissement.setUserCreated(users);
                    etablissementCollection1NewEtablissement = em.merge(etablissementCollection1NewEtablissement);
                    if (oldUserCreatedOfEtablissementCollection1NewEtablissement != null && !oldUserCreatedOfEtablissementCollection1NewEtablissement.equals(users)) {
                        oldUserCreatedOfEtablissementCollection1NewEtablissement.getEtablissementCollection1().remove(etablissementCollection1NewEtablissement);
                        oldUserCreatedOfEtablissementCollection1NewEtablissement = em.merge(oldUserCreatedOfEtablissementCollection1NewEtablissement);
                    }
                }
            }
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    usersCollectionOldUsers.setUserModified(null);
                    usersCollectionOldUsers = em.merge(usersCollectionOldUsers);
                }
            }
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Users oldUserModifiedOfUsersCollectionNewUsers = usersCollectionNewUsers.getUserModified();
                    usersCollectionNewUsers.setUserModified(users);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldUserModifiedOfUsersCollectionNewUsers != null && !oldUserModifiedOfUsersCollectionNewUsers.equals(users)) {
                        oldUserModifiedOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldUserModifiedOfUsersCollectionNewUsers = em.merge(oldUserModifiedOfUsersCollectionNewUsers);
                    }
                }
            }
            for (Users usersCollection1OldUsers : usersCollection1Old) {
                if (!usersCollection1New.contains(usersCollection1OldUsers)) {
                    usersCollection1OldUsers.setUserCreated(null);
                    usersCollection1OldUsers = em.merge(usersCollection1OldUsers);
                }
            }
            for (Users usersCollection1NewUsers : usersCollection1New) {
                if (!usersCollection1Old.contains(usersCollection1NewUsers)) {
                    Users oldUserCreatedOfUsersCollection1NewUsers = usersCollection1NewUsers.getUserCreated();
                    usersCollection1NewUsers.setUserCreated(users);
                    usersCollection1NewUsers = em.merge(usersCollection1NewUsers);
                    if (oldUserCreatedOfUsersCollection1NewUsers != null && !oldUserCreatedOfUsersCollection1NewUsers.equals(users)) {
                        oldUserCreatedOfUsersCollection1NewUsers.getUsersCollection1().remove(usersCollection1NewUsers);
                        oldUserCreatedOfUsersCollection1NewUsers = em.merge(oldUserCreatedOfUsersCollection1NewUsers);
                    }
                }
            }
            for (Widget widgetCollectionOldWidget : widgetCollectionOld) {
                if (!widgetCollectionNew.contains(widgetCollectionOldWidget)) {
                    widgetCollectionOldWidget.setUserModified(null);
                    widgetCollectionOldWidget = em.merge(widgetCollectionOldWidget);
                }
            }
            for (Widget widgetCollectionNewWidget : widgetCollectionNew) {
                if (!widgetCollectionOld.contains(widgetCollectionNewWidget)) {
                    Users oldUserModifiedOfWidgetCollectionNewWidget = widgetCollectionNewWidget.getUserModified();
                    widgetCollectionNewWidget.setUserModified(users);
                    widgetCollectionNewWidget = em.merge(widgetCollectionNewWidget);
                    if (oldUserModifiedOfWidgetCollectionNewWidget != null && !oldUserModifiedOfWidgetCollectionNewWidget.equals(users)) {
                        oldUserModifiedOfWidgetCollectionNewWidget.getWidgetCollection().remove(widgetCollectionNewWidget);
                        oldUserModifiedOfWidgetCollectionNewWidget = em.merge(oldUserModifiedOfWidgetCollectionNewWidget);
                    }
                }
            }
            for (Widget widgetCollection1OldWidget : widgetCollection1Old) {
                if (!widgetCollection1New.contains(widgetCollection1OldWidget)) {
                    widgetCollection1OldWidget.setUserCreated(null);
                    widgetCollection1OldWidget = em.merge(widgetCollection1OldWidget);
                }
            }
            for (Widget widgetCollection1NewWidget : widgetCollection1New) {
                if (!widgetCollection1Old.contains(widgetCollection1NewWidget)) {
                    Users oldUserCreatedOfWidgetCollection1NewWidget = widgetCollection1NewWidget.getUserCreated();
                    widgetCollection1NewWidget.setUserCreated(users);
                    widgetCollection1NewWidget = em.merge(widgetCollection1NewWidget);
                    if (oldUserCreatedOfWidgetCollection1NewWidget != null && !oldUserCreatedOfWidgetCollection1NewWidget.equals(users)) {
                        oldUserCreatedOfWidgetCollection1NewWidget.getWidgetCollection1().remove(widgetCollection1NewWidget);
                        oldUserCreatedOfWidgetCollection1NewWidget = em.merge(oldUserCreatedOfWidgetCollection1NewWidget);
                    }
                }
            }
            for (Classe classeCollectionOldClasse : classeCollectionOld) {
                if (!classeCollectionNew.contains(classeCollectionOldClasse)) {
                    classeCollectionOldClasse.setUserModified(null);
                    classeCollectionOldClasse = em.merge(classeCollectionOldClasse);
                }
            }
            for (Classe classeCollectionNewClasse : classeCollectionNew) {
                if (!classeCollectionOld.contains(classeCollectionNewClasse)) {
                    Users oldUserModifiedOfClasseCollectionNewClasse = classeCollectionNewClasse.getUserModified();
                    classeCollectionNewClasse.setUserModified(users);
                    classeCollectionNewClasse = em.merge(classeCollectionNewClasse);
                    if (oldUserModifiedOfClasseCollectionNewClasse != null && !oldUserModifiedOfClasseCollectionNewClasse.equals(users)) {
                        oldUserModifiedOfClasseCollectionNewClasse.getClasseCollection().remove(classeCollectionNewClasse);
                        oldUserModifiedOfClasseCollectionNewClasse = em.merge(oldUserModifiedOfClasseCollectionNewClasse);
                    }
                }
            }
            for (Classe classeCollection1OldClasse : classeCollection1Old) {
                if (!classeCollection1New.contains(classeCollection1OldClasse)) {
                    classeCollection1OldClasse.setUserCreated(null);
                    classeCollection1OldClasse = em.merge(classeCollection1OldClasse);
                }
            }
            for (Classe classeCollection1NewClasse : classeCollection1New) {
                if (!classeCollection1Old.contains(classeCollection1NewClasse)) {
                    Users oldUserCreatedOfClasseCollection1NewClasse = classeCollection1NewClasse.getUserCreated();
                    classeCollection1NewClasse.setUserCreated(users);
                    classeCollection1NewClasse = em.merge(classeCollection1NewClasse);
                    if (oldUserCreatedOfClasseCollection1NewClasse != null && !oldUserCreatedOfClasseCollection1NewClasse.equals(users)) {
                        oldUserCreatedOfClasseCollection1NewClasse.getClasseCollection1().remove(classeCollection1NewClasse);
                        oldUserCreatedOfClasseCollection1NewClasse = em.merge(oldUserCreatedOfClasseCollection1NewClasse);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = users.getUsersId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUsersId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            Levels levelsId = users.getLevelsId();
            if (levelsId != null) {
                levelsId.setUserModified(null);
                levelsId = em.merge(levelsId);
            }
            Users userModified = users.getUserModified();
            if (userModified != null) {
                userModified.getUsersCollection().remove(users);
                userModified = em.merge(userModified);
            }
            Users userCreated = users.getUserCreated();
            if (userCreated != null) {
                userCreated.getUsersCollection().remove(users);
                userCreated = em.merge(userCreated);
            }
            Collection<Student> studentCollection = users.getStudentCollection();
            for (Student studentCollectionStudent : studentCollection) {
                studentCollectionStudent.setUserModified(null);
                studentCollectionStudent = em.merge(studentCollectionStudent);
            }
            Collection<Student> studentCollection1 = users.getStudentCollection1();
            for (Student studentCollection1Student : studentCollection1) {
                studentCollection1Student.setUserCreated(null);
                studentCollection1Student = em.merge(studentCollection1Student);
            }
            Collection<Groupe> groupeCollection = users.getGroupeCollection();
            for (Groupe groupeCollectionGroupe : groupeCollection) {
                groupeCollectionGroupe.setUserModified(null);
                groupeCollectionGroupe = em.merge(groupeCollectionGroupe);
            }
            Collection<Groupe> groupeCollection1 = users.getGroupeCollection1();
            for (Groupe groupeCollection1Groupe : groupeCollection1) {
                groupeCollection1Groupe.setUserCreated(null);
                groupeCollection1Groupe = em.merge(groupeCollection1Groupe);
            }
            Collection<Salle> salleCollection = users.getSalleCollection();
            for (Salle salleCollectionSalle : salleCollection) {
                salleCollectionSalle.setUserModified(null);
                salleCollectionSalle = em.merge(salleCollectionSalle);
            }
            Collection<Salle> salleCollection1 = users.getSalleCollection1();
            for (Salle salleCollection1Salle : salleCollection1) {
                salleCollection1Salle.setUserCreated(null);
                salleCollection1Salle = em.merge(salleCollection1Salle);
            }
            Collection<Sections> sectionsCollection = users.getSectionsCollection();
            for (Sections sectionsCollectionSections : sectionsCollection) {
                sectionsCollectionSections.setUserModified(null);
                sectionsCollectionSections = em.merge(sectionsCollectionSections);
            }
            Collection<Sections> sectionsCollection1 = users.getSectionsCollection1();
            for (Sections sectionsCollection1Sections : sectionsCollection1) {
                sectionsCollection1Sections.setUserCreated(null);
                sectionsCollection1Sections = em.merge(sectionsCollection1Sections);
            }
            Collection<Levels> levelsCollection = users.getLevelsCollection();
            for (Levels levelsCollectionLevels : levelsCollection) {
                levelsCollectionLevels.setUserModified(null);
                levelsCollectionLevels = em.merge(levelsCollectionLevels);
            }
            Collection<Levels> levelsCollection1 = users.getLevelsCollection1();
            for (Levels levelsCollection1Levels : levelsCollection1) {
                levelsCollection1Levels.setUserCreated(null);
                levelsCollection1Levels = em.merge(levelsCollection1Levels);
            }
            Collection<Etablissement> etablissementCollection = users.getEtablissementCollection();
            for (Etablissement etablissementCollectionEtablissement : etablissementCollection) {
                etablissementCollectionEtablissement.setUserModified(null);
                etablissementCollectionEtablissement = em.merge(etablissementCollectionEtablissement);
            }
            Collection<Etablissement> etablissementCollection1 = users.getEtablissementCollection1();
            for (Etablissement etablissementCollection1Etablissement : etablissementCollection1) {
                etablissementCollection1Etablissement.setUserCreated(null);
                etablissementCollection1Etablissement = em.merge(etablissementCollection1Etablissement);
            }
            Collection<Users> usersCollection = users.getUsersCollection();
            for (Users usersCollectionUsers : usersCollection) {
                usersCollectionUsers.setUserModified(null);
                usersCollectionUsers = em.merge(usersCollectionUsers);
            }
            Collection<Users> usersCollection1 = users.getUsersCollection1();
            for (Users usersCollection1Users : usersCollection1) {
                usersCollection1Users.setUserCreated(null);
                usersCollection1Users = em.merge(usersCollection1Users);
            }
            Collection<Widget> widgetCollection = users.getWidgetCollection();
            for (Widget widgetCollectionWidget : widgetCollection) {
                widgetCollectionWidget.setUserModified(null);
                widgetCollectionWidget = em.merge(widgetCollectionWidget);
            }
            Collection<Widget> widgetCollection1 = users.getWidgetCollection1();
            for (Widget widgetCollection1Widget : widgetCollection1) {
                widgetCollection1Widget.setUserCreated(null);
                widgetCollection1Widget = em.merge(widgetCollection1Widget);
            }
            Collection<Classe> classeCollection = users.getClasseCollection();
            for (Classe classeCollectionClasse : classeCollection) {
                classeCollectionClasse.setUserModified(null);
                classeCollectionClasse = em.merge(classeCollectionClasse);
            }
            Collection<Classe> classeCollection1 = users.getClasseCollection1();
            for (Classe classeCollection1Classe : classeCollection1) {
                classeCollection1Classe.setUserCreated(null);
                classeCollection1Classe = em.merge(classeCollection1Classe);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
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

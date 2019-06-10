/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.enumerations.EnumError;
import cm.codebrain.main.business.enumerations.EnumLibelle;
import cm.codebrain.ui.application.MessageForm;
import static cm.codebrain.ui.application.enumerations.EnumVariable.User;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import cm.codebrain.ui.application.security.LoginForm;
import cm.codebrain.ui.application.security.MainForm;
import cm.codebrain.ui.application.security.ReLoginForm;
import cm.codebrain.ui.application.services.CodeBrainServiceAsync;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainAcces {

    private MainForm mainForm;
    private LoginForm login;
    private ReLoginForm reLogin;

    public CodeBrainAcces() {
    }
    
    private boolean connexion;
    
    public void start() {
        
        Locale.initBundle();

        Loading.show(null, Dictionnaire.get(EnumLibelle.Business_Libelle_Connexion), new Executable<Boolean>() {
            @Override
            public void execute() throws Exception {
                connexion = getAdministrationService().connexion();
            }

            @Override
            public Boolean success() {
                return connexion;
            }

            @Override
            public void error(Exception ex) {
                System.out.println(ex.getMessage());
                MessageForm.showsError(Dictionnaire.get(EnumError.LoseConnexionException), "Message", false, null);
            }
        });
        
        if(connexion){
            mainForm = new MainForm(this);

            mainForm.setVisible(true);
            if (GlobalParameters.get(User) == null) {
                login = new LoginForm(this, mainForm, false);
                login.setVisible(true);
            } else {
                reLogin = new ReLoginForm(this, mainForm, false, ((Users) GlobalParameters.get(User)).getLogin());
                reLogin.setVisible(true);
            }
        }
    }

    public void restart() {
        if (mainForm == null) {
            start();
        } else {
            reset();
            start();
        }
    }

    public void reset() {
        mainForm.dispose();
        mainForm.revalidate();
    }

    public void load() {
        
        mainForm.loadMenu();
        mainForm.initStatus(Boolean.TRUE);

        mainForm.refresh();
    }

    public void logout() {
        if(!disConnexion()){
            GlobalParameters.init();
            restart();
        }
    }

    public Boolean disConnexion(){
        Loading.show(null, Dictionnaire.get(EnumLibelle.Business_Libelle_Deconnexion), new Executable<Boolean>() {
                @Override
                public void execute() throws Exception {
                    connexion = getAdministrationService().disConnexion();
                }

                @Override
                public Boolean success() {
                    return connexion;
                }

                @Override
                public void error(Exception ex) {
                    System.out.println(ex.getMessage());
                    MessageForm.showsError(Dictionnaire.get(EnumError.UserLoginException), "Message", false, null);
                }
            });
        return connexion;
    }
    
    public void quit() {
        if(!disConnexion()){
            GlobalParameters.init();
            System.exit(0);
        }
    }

    private CodeBrainServiceAsync getAdministrationService() {
        CodeBrainServiceAsync svc = null;
        try {
            svc = CodeBrainServiceAsync.class.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(CodeBrainAcces.class.getName()).log(Level.SEVERE, null, ex);
        }
        return svc;
    }
}

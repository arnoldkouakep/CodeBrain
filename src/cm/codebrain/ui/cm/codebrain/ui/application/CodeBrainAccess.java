/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.enumerations.EnumError;
import cm.codebrain.main.business.enumerations.EnumLibelle;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.controller.Locale;
import static cm.codebrain.ui.application.enumerations.EnumVariable.User;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import cm.codebrain.ui.application.security.LoginForm;
import cm.codebrain.ui.application.security.MainForm;
import cm.codebrain.ui.application.security.ReLoginForm;
import cm.codebrain.ui.application.services.CodeBrainServiceAsync;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainAccess {

    private boolean connexion;

    private MainForm mainForm;
    private LoginForm login;
    private ReLoginForm reLogin;

    public void start() {

        Locale.initBundle();

        Loading.show(null, Dictionnaire.get(EnumLibelle.Business_Libelle_Connexion), new Executable<Boolean>() {
            @Override
            public void execute() throws Exception {
                getAdministrationService().connexion(new Executable<Boolean>() {
                    @Override
                    public void execute() throws Exception {
                    }

                    @Override
                    public void success(Boolean result) {
                    }

                    @Override
                    public void error(Exception ex) {
                    }
                });
            }

            @Override
            public void success(Boolean result) {
                connexion = result;
            }

            @Override
            public void error(Exception ex) {
                System.out.println(ex.getMessage());
                MessageForm.showsError(Dictionnaire.get(EnumError.LoseConnexionException), "Message", false, null);
            }
        });

        if (connexion) {
            mainForm = new MainForm(this);

            mainForm.setVisible(true);
            if (GlobalParameters.get(User) == null) {
                login = new LoginForm(mainForm, false);
//                login = new LoginForm(this, mainForm, false);
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
        mainForm.refresh();
    }

    public void logout() {
        if (!disConnexion()) {
            GlobalParameters.init();
            restart();
        }
    }

    private CodeBrainServiceAsync getAdministrationService() {
        CodeBrainServiceAsync svc = null;
        try {
            svc = CodeBrainServiceAsync.class.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
        return svc;
    }
}

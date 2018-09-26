package com.codename1.uikit.cleanmodern;

import bmg.crud.UserCo;
import bmg.crud.UserCrud;
import bmg.entities.User;
import com.codename1.components.FloatingHint;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.social.GoogleConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

public class SignInForm extends BaseForm {

    public TextField username;
    public TextField password;
    public String token;

    public SignInForm(Resources res) {
        super(new BorderLayout());

        if (!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout) getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");

        add(BorderLayout.NORTH, new Label(res.getImage("log.png"), "LogoLabel"));

        username = new TextField("", "Nom d'utilisateur", 20, TextField.ANY);
        password = new TextField("", "Mot de passe", 20, TextField.ANY);

        UserCrud uc = new UserCrud();
        username.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Se connecter");
        Button googleLogin = new Button("   ");
        googleLogin.setUIID("gooBtn");
        
        Button signUp = new Button("S'inscrire");
        signUp.setUIID("Link");
        signUp.addActionListener(e -> new SignUpForm(res).show());

        Label doneHaveAnAccount = new Label("Vous n'avez pas un compte?");

        Container content = BoxLayout.encloseY(
                
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                //fbLogin,
                googleLogin,
                createLineSeparator(),
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp)
               //  FlowLayout.encloseCenter(signUp)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        // fbLogin.requestFocus();

        signIn.addActionListener(e -> {

            ConnectionRequest connectionRequest = new ConnectionRequest();
            connectionRequest.setUrl("http://localhost/Codenameone/userCo.php?");
            NetworkManager.getInstance().addToQueue(connectionRequest);
            connectionRequest.addResponseListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    // try {

                    for (User u : uc.getUserCo(new String(connectionRequest.getResponseData()))) {
                        if (username.getText().equals(u.getLogin()) && password.getText().equals(u.getPassword())) {
                            User us = new User();
                            us.setId_u(u.getId_u());
                            us.setEmail(u.getEmail());
                            us.setNom(u.getNom());
                            us.setPrenom(u.getPrenom());
                            us.setLogin(u.getLogin());
                            us.setPassword(u.getPassword());
                            us.setUrl(u.getUrl());
                            
                            UserCo.userCo = us;
                            System.out.println(us.getId_u());
                            new ProfileForm(res).show();
                        }

                        

                    }
                }
                
            });
        });

        googleLogin.addActionListener((e) -> {
            String clientId = "1089817790948-2uss6qcvmcn7dl6b48uqols6es4siqpk.apps.googleusercontent.com";
            String redirectURI = "https://www.youtube.com/";
            String clientSecret = "Jcd_AyLPuaZUl1OvVli7O2-P";
            Login gc = GoogleConnect.getInstance();
            gc.setClientId(clientId);
            gc.setRedirectURI(redirectURI);
            gc.setClientSecret(clientSecret);
            gc.setScope("https://www.googleapis.com/auth/youtube.force-ssl");
            gc.setCallback(new LoginCallback() {
                @Override
                public void loginFailed(String errorMessage) {
                    Dialog.show("Error Logging In", "There was an error logging in: " + errorMessage, "OK", null);
                }

                @Override
                public void loginSuccessful() {
                    Dialog.show("Connexion établie", "vous-êtes correctement connecter ", "Daccord", null);
                    String token=gc.getAccessToken().getToken();
                    System.out.println(token);
                  //  new ProfileForm(res).show();

                }
            });
            if (!gc.isUserLoggedIn()) {
                gc.doLogin();
            } else {
                String token = gc.getAccessToken().getToken();
                System.out.println(token);
            }
        });

    }

}

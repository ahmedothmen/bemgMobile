package com.codename1.uikit.cleanmodern;

import bmg.crud.UserCrud;
import bmg.entities.User;
import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.WebBrowser;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class SignUpForm extends BaseForm {

    private String fileName = "No picture";

    public String token;
    public TextField nom;
    public TextField prenom;
    public TextField username;
    public TextField email;
    public TextField numtel;
    public Picker dat;
    public WebBrowser pic;


    // public String urlImage ;
    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        //wb.setURL(pictureimage);

        // wb.setURL("https://fb-s-b-a.akamaihd.net/h-ak-fbx/v/t1.0-1/p720x720/14947471_1779836112256321_5171029153036668520_n.jpg?oh=1f69541a6fa33ff56c51a6cf4dd1393b&oe=597627C8&__gda__=1501961830_110e66fade66bb4ba64758d9558b1242");
        nom = new TextField("", "Nom", 20, TextField.ANY);
        prenom = new TextField("", "Prénom", 20, TextField.ANY);
        username = new TextField("", "Nom d'utilisateur", 20, TextField.ANY);
        email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        numtel = new TextField("", "Numéro téléphone", 20, TextField.PHONENUMBER);
        // WebView wv = new WebView();
        //TextField daten = new TextField("", "Date de naissance", 20, TextField.EMAILADDR);
        dat = new Picker();
        //dat.setType(Display.PICKER_TYPE_DATE);

        TextField password = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirmer mot de passe", 20, TextField.PASSWORD);
        nom.setSingleLineTextArea(false);
        prenom.setSingleLineTextArea(false);
        numtel.setSingleLineTextArea(false);
        //daten.setSingleLineTextArea(false);
        username.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        Button signUp = new Button("S'inscrire");
        Button fbSignUp = new Button("   ");
        fbSignUp.setUIID("btnfb");
        Button signIn = new Button("Connexion");
        Button browse = new Button("Image...");
        //Button btn = new Button();
        //btn.setUIID("Button2");
        //  btn.setIcon(Image.createImage(ee));
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        browse.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Avez-vous déjà un compte?");
        UserCrud uc = new UserCrud();
        pic = new WebBrowser();
      
       // pic.setURL("http://origin.webcdn.theblackdesertonline.net/forum/service_live/monthly_02_2016/Black_Wood.jpg.906a72f6056add12e45f04378aea480c.jpg");

        
       
        Container content = BoxLayout.encloseY(
                new Label("Inscription", "LogoLabel"),
                pic,
                
                
                createLineSeparator(),
                new FloatingHint(nom),
               // createLineSeparator(),
                new FloatingHint(prenom),
               // createLineSeparator(),
                new FloatingHint(numtel),
                //createLineSeparator(),
                new Picker(),
                //createLineSeparator(),
                new FloatingHint(username),
                //createLineSeparator(),
                new FloatingHint(email),
                //createLineSeparator(),
                new FloatingHint(password),
                //createLineSeparator(),
                new FloatingHint(confirmPassword)
                //createLineSeparator()
        );
              pic.setVisible(false);

        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                signUp, fbSignUp,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn,browse)
        ));
        signUp.requestFocus();
        
        signUp.addActionListener(e -> {
              if (nom.getText() == "" 
               || prenom.getText() == "" 
               || email.getText() == "" 
               || password.getText() == "" 
               || numtel.getText() == "" 
               || username.getText() == "" 
               || dat.getText() == "") {
                Dialog.show("Information", "Veuillez remplir tous les champs", "Ok", null);
            } else  {

                User u = new User(nom.getText(), prenom.getText(), email.getText(), username.getText(), password.getText(), dat.getText(), numtel.getText(), fileName);
                uc.signUp(u);
                System.out.println("Success");
                new SignInForm(res).show();
                //new ActivateForm(res).show();
            }
        });
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    MultipartRequest cr = new MultipartRequest();
                    String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                    fileName = extractFileName(filePath) + "jpg";
                    cr.setUrl("http://localhost/Codenameone/inserImage.php");
                    cr.setPost(true);
                    String mime = "image/jpeg";
                    cr.addData("file", filePath, mime);
                    cr.setFilename("file", fileName);

                    InfiniteProgress prog = new InfiniteProgress();
                    Dialog dlg = prog.showInifiniteBlocking();
                    cr.setDisposeOnCompletion(dlg);
                    //NetworkManager.getInstance().addToQueueAndWait(cr);
                     NetworkManager.getInstance().addToQueue(cr);

                } catch (IOException ex) {

                }

            }

            private String extractFileName(String filePathName) {
                if (filePathName == null) {
                    return null;
                }

                int dotPos = filePathName.lastIndexOf('.');
                int slashPos = filePathName.lastIndexOf('\\');
                if (slashPos == -1) {
                    slashPos = filePathName.lastIndexOf('/');
                }

                if (dotPos > slashPos) {
                    return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0,
                            dotPos);
                }

                return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
            }
        }
        );

        fbSignUp.addActionListener((e) -> {
            String clientId = "234379707024214";
            String redirectURI = "https://www.google.fr/";
            String clientSecret = "465781f7e50eaa8d468369f3d3b8cc2e";
            Login fb = FacebookConnect.getInstance();
            fb.setClientId(clientId);
            fb.setRedirectURI(redirectURI);
            fb.setClientSecret(clientSecret);
            //Sets a LoginCallback listener
            fb.setCallback(new LoginCallback() {
                @Override
                public void loginSuccessful() {
                    try {
                        token = fb.getAccessToken().getToken();
                        System.out.println(token);
                        testLoadJSONUsingJSONParser(token);
                    } catch (Exception e) {
                    }
                }

                @Override
                public void loginFailed(String errorMessage) {
                    Dialog.show("No!", "it did not work!", "sad", null);
                }
            });
            if (!fb.isUserLoggedIn()) {
                fb.doLogin();
                System.out.println("connected");
            } else {
                token = fb.getAccessToken().getToken();
                System.out.println(token);
                System.out.println("aaaaaaaaaaaa");
            }
        });
    }

    public void testLoadJSONUsingJSONParser(String token) {
        ConnectionRequest req = new ConnectionRequest() {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                InputStreamReader reader = new InputStreamReader(input);
                JSONParser parser = new JSONParser();
                Map<String, Object> response = parser.parseJSON(reader);
                String firstname = (String) response.get("first_name");
                String lastname = (String) response.get("last_name");
                String sexe = (String) response.get("gender");
                String email = (String) response.get("email");
                String birthday = (String) response.get("birthday");
                String username2 = (String) response.get("username");

                Map<String, Object> pictureimage = new LinkedHashMap<String, Object>();
                pictureimage = (Map<String, Object>) response.get("picture");
                Map<String, Object> dataimage = new LinkedHashMap<String, Object>();
                dataimage = (Map<String, Object>) pictureimage.get("data");
                String urlImage = (String) dataimage.get("url");
                System.out.println("photo= " + pictureimage);
                nom.setText(firstname);
                prenom.setText(lastname);
                dat.setText(birthday);
                username.setText(firstname + "." + lastname);
                pic.setURL(urlImage);

            }
        };

        req.setPost(false);
        req.setHttpMethod("GET");
        req.setUrl("https://graph.facebook.com/v2.9/me?fields=id,email,last_name,first_name,birthday,gender,picture.width(1000).height(500)&access_token=" + token);
        NetworkManager.getInstance().addToQueue(req);
    }

}

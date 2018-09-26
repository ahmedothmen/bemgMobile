package com.codename1.uikit.cleanmodern;

import bmg.crud.UserCo;
import bmg.crud.UserCrud;
import bmg.entities.User;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    
    public ProfileForm(Resources res) {
        //super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        
       ImageViewer img1 = new ImageViewer();
            EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("Logo.png"),true);
            URLImage uRLImage = URLImage.createToStorage(placeholder,UserCo.userCo.getUrl(),"http://localhost/Codenameone/uploads/"+UserCo.userCo.getUrl());
            img1.setImage(uRLImage);
            Image img2 = img1.getImage();

        Image img = res.getImage("profile-background.jpg");
        
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);



        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(4,
                                
                                FlowLayout.encloseCenter(
                                        
                                        new Label(img2, "PictureWhiteBackgrond")
                                )
                                
                        )
                )
        ));


        
        TextField firstname = new TextField(UserCo.userCo.getNom(), "Nom", 20, TextField.ANY);
        firstname.setUIID("TextFieldBlack");
        addStringValue("Nom", firstname);

        TextField lastname = new TextField(UserCo.userCo.getPrenom(), "Prenom", 20, TextField.ANY);
        lastname.setUIID("TextFieldBlack");
        addStringValue("Prénom", lastname);

        TextField username = new TextField(UserCo.userCo.getLogin());
        username.setUIID("TextFieldBlack");
        addStringValue("Nom d'utilisateur", username);

        TextField birthday = new TextField(UserCo.userCo.getDaten());
        //birthday.setUIID("TextFieldBlack");
        //addStringValue("Date de naissance", birthday);        
        
        TextField email = new TextField(UserCo.userCo.getEmail(), "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", email);

        TextField phonenum = new TextField(UserCo.userCo.getNumtel(), "PhoneNumber", 20, TextField.ANY);
        //phonenum.setUIID("TextFieldBlack");
        //addStringValue("Numero de téléphone", phonenum);

        TextField password = new TextField(UserCo.userCo.getPassword(), "Password", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        addStringValue("Mot de passe", password);

        Button btnEdit = new Button("Modifier");
        btnForm("Ediiit", btnEdit);
        Button btnDelete = new Button("Supprimer");
        btnForm("Ediiit", btnDelete);

        UserCrud uc = new UserCrud();

        btnEdit.addActionListener(e -> {
            User u = new User(firstname.getText(),
                    lastname.getText(),
                    email.getText(),
                    username.getText(),
                    password.getText(),
                    birthday.getText(),
                    phonenum.getText(),
                    null
            );
            uc.UpdateUserProfile(u);
            System.out.println("Edit Success");
            System.out.println(email.getText() + " " + phonenum.getText());
            // new ActivateForm(res).show();
        }
                
        );
        btnDelete.addActionListener(e -> {
            User u = new User(UserCo.userCo.getId_u());
            uc.removeUser(u);            
            System.out.println("Delete Success");
            new SignInForm(res).show();

        }
                
        );
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void btnForm(String s, Component v) {
        add(BorderLayout.center(v));
    }
}
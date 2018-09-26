package com.codename1.uikit.cleanmodern;

import bmg.crud.CommentairePropCrud;
import bmg.crud.FavorisProprieteCrud;
import bmg.crud.ProprieteCRUD;
import bmg.crud.UserCo;
import bmg.entities.CommentairePropriete;
import bmg.entities.FavorisPropriete;
import bmg.entities.Propriete;
import bmg.entities.User;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class DetailPropFavorisForm extends BaseForm {

    private Resources theme = UIManager.initFirstTheme("/theme");

    public DetailPropFavorisForm(Resources res, Propriete p1) {

        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });
         ImageViewer img1 = new ImageViewer();
            EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("Logo.png"),true);
            URLImage uRLImage = URLImage.createToStorage(placeholder,p1.getUrl(),"http://localhost/Codenameone/uploads/"+p1.getUrl());

                    img1.setImage(uRLImage);

        
            Image img = img1.getImage();
        
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);

        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                facebook,
                                FlowLayout.encloseCenter(
                                        new Label(res.getImage("profile-pic.jpg"), "PictureWhiteBackgrond")),
                                twitter
                        )
                )
        ));

        TextField categorie = new TextField("categorie");
        categorie.setText(p1.getCategoriePropriete());
        categorie.setEnabled(true);
        categorie.setUIID("TextFieldBlack");
        addStringValue("Categorie", categorie);
        
             TextField typePropriete = new TextField("");
        typePropriete.setText(p1.getTypePropriete());
        typePropriete.setEnabled(false);
        typePropriete.setUIID("TextFieldBlack");
        addStringValue("Type propriete", typePropriete);

        TextField pays = new TextField("");
        pays.setText(p1.getPays());
        pays.setEnabled(true);
        pays.setUIID("TextFieldBlack");
        addStringValue("Pays", pays);

        TextField ville = new TextField("");
        ville.setText(p1.getVille());
        ville.setEnabled(true);
        ville.setUIID("TextFieldBlack");
        addStringValue("Ville", ville);

        TextField rue = new TextField("");
        rue.setText(p1.getRue());
        rue.setEnabled(true);
        rue.setUIID("TextFieldBlack");
        addStringValue("Rue", rue);

        TextField titre = new TextField("");
        titre.setText(p1.getTitre());
        titre.setEnabled(true);
        titre.setUIID("TextFieldBlack");
        addStringValue("Titre", titre);

        TextField prix = new TextField("");
        prix.setText(p1.getPrix());
        prix.setEnabled(true);
        prix.setUIID("TextFieldBlack");
        addStringValue("Prix", prix);

        TextField nbrChambre = new TextField("");
        nbrChambre.setText(p1.getNbrChambre());
        nbrChambre.setEnabled(true);
        nbrChambre.setUIID("TextFieldBlack");
        addStringValue("Nombre Chambre", nbrChambre);

        TextField nbrVoyageur = new TextField("");
        nbrVoyageur.setText(p1.getNbrVoyageur());
        nbrVoyageur.setEnabled(true);
        nbrVoyageur.setUIID("TextFieldBlack");
        addStringValue("Nombre Voyageur", nbrVoyageur);
        
         TextArea description = new TextArea();
            description.setText(p1.getDescription());
            typePropriete.setEnabled(false);
        description.setUIID("TextFieldBlack");
        addStringValue("description", description);
        
                Label id = new Label("");
                id.setVisible(false);
                
        TextArea contenu = new TextArea();
        contenu.setHint("saisir le commentaire");
        contenu.setUIID("TextFieldBlack");
        addStringValue("Commentez", contenu);

        Button b1 = new Button("Ajouter Commentaire");
        add(BorderLayout.west(new Label("", "PaddedLabel")).
                add(BorderLayout.CENTER, b1));
        b1.requestFocus();
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                User u = new User(1, "aaa", "aaaa");
                CommentairePropriete cp = new CommentairePropriete(contenu.getText(), p1.getId(), u.getId_u());
                new CommentairePropCrud().addComment(cp);
                
                System.out.println(cp);

            }
        });
        Button coms = new Button("Voir Commentaire");;
        add(coms);
        coms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DetailCommentForm1(theme, p1).show();
            }
        });

        Button delete = new Button("Supprimer favoris");;
        add(delete);
     
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ProprieteCRUD pc = new ProprieteCRUD();
                 if(Dialog.show("voulez vous supprimer cette propriete ?", "", "ok", "annuler") ) {
                     FavorisProprieteCrud fpc = new FavorisProprieteCrud();
                     fpc.removeFavoris(p1.getId());
                     new MesFavorisProp(theme).show();
                 }
            }
        });
        
       

        /*  CheckBox cb1 = CheckBox.createToggle(res.getImage("on-off-off.png"));
        cb1.setUIID("Label");
        cb1.setPressedIcon(res.getImage("on-off-on.png"));
        CheckBox cb2 = CheckBox.createToggle(res.getImage("on-off-off.png"));
        cb2.setUIID("Label");
        cb2.setPressedIcon(res.getImage("on-off-on.png"));
        
        addStringValue("Facebook", FlowLayout.encloseRightMiddle(cb1));
        addStringValue("Twitter", FlowLayout.encloseRightMiddle(cb2));*/
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}

package com.codename1.uikit.cleanmodern;

import bmg.crud.CommentairePropCrud;
import bmg.crud.FavorisProprieteCrud;
import bmg.crud.ProprieteCRUD;
import bmg.crud.UserCo;
import bmg.entities.CommentairePropriete;
import bmg.entities.FavorisPropriete;
import bmg.entities.Propriete;
import bmg.entities.Resrevation;
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
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class DetailPropForm extends BaseForm {

    private Resources theme = UIManager.initFirstTheme("/theme");
     private Form current,Form;

    public DetailPropForm(Resources res, Propriete p1) {

        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Detail Propriete");
        getContentPane().setScrollVisible(false);

              super.addSideMenu(res);
  
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
                                FlowLayout.encloseCenter(
                                        new Label())
                                
                        )
                )
        ));

        TextField categorie = new TextField("categorie");
        categorie.setText(p1.getCategoriePropriete());
        categorie.setEnabled(false);
        categorie.setUIID("TextFieldBlack");
        addStringValue("Categorie", categorie);
        
             TextField typePropriete = new TextField("");
        typePropriete.setText(p1.getTypePropriete());
        typePropriete.setEnabled(false);
        typePropriete.setUIID("TextFieldBlack");
        addStringValue("Type propriete", typePropriete);

        TextField pays = new TextField("");
        pays.setText(p1.getPays());
        pays.setEnabled(false);
        pays.setUIID("TextFieldBlack");
        addStringValue("Pays", pays);

        TextField ville = new TextField("");
        ville.setText(p1.getVille());
        ville.setEnabled(false);
        ville.setUIID("TextFieldBlack");
        addStringValue("Ville", ville);

        TextField rue = new TextField("");
        rue.setText(p1.getRue());
        rue.setEnabled(false);
        rue.setUIID("TextFieldBlack");
        addStringValue("Rue", rue);

        TextField titre = new TextField("");
        titre.setText(p1.getTitre());
        titre.setEnabled(false);
        titre.setUIID("TextFieldBlack");
        addStringValue("Titre", titre);

        TextField prix = new TextField("");
        prix.setText(p1.getPrix());
        prix.setEnabled(false);
        prix.setUIID("TextFieldBlack");
        addStringValue("Prix", prix);

        TextField nbrChambre = new TextField("");
        nbrChambre.setText(p1.getNbrChambre());
        nbrChambre.setEnabled(false);
        nbrChambre.setUIID("TextFieldBlack");
        addStringValue("Nombre Chambre", nbrChambre);

        TextField nbrVoyageur = new TextField("");
        nbrVoyageur.setText(p1.getNbrVoyageur());
        nbrVoyageur.setEnabled(false);
        nbrVoyageur.setUIID("TextFieldBlack");
        addStringValue("Nombre Voyageur", nbrVoyageur);
         

       
        
            TextArea description = new TextArea();
            description.setText(p1.getDescription());
            typePropriete.setEnabled(false);
        description.setUIID("TextFieldBlack");
        addStringValue("description", description);
           

        TextArea contenu = new TextArea();
        contenu.setHint("saisir le commentaire");
        contenu.setUIID("TextFieldBlack");
        addStringValue("Commentez", contenu);
        Label note=new Label(" Noter L'application ");
         add(note);
   
           Slider gg = createStarRankSlider();  
           gg.setMaxValue(5);
           gg.setMinValue(0);
        add(FlowLayout.encloseCenter(gg));
           
                   
         Button btnOk = new Button("Verifier la note");

        add(btnOk);
         btnOk.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                note.setText("note: "+gg.getProgress());
               System.out.println(note);  
            }
        }); 
         Button reclamer = new Button("Reclamer");
         add(reclamer);
         reclamer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ReclamationAdd rec = new ReclamationAdd(theme, p1);
                rec.show();
                
            }
        });
         
         Button cb1 = new Button("Add facebook");
         add(cb1);
        cb1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt)
                        {
                 String accessToken = "EAACEdEose0cBAKoKoTXOtuIAb8j1FZC3M2ZAZCOT9HyuilGZAdw8WSEhUs7araZBnUfpyDmO9Aq9BncdLFTVZCM32hZBZBxwhKDJqqUoMZAzfXPASv58mCmFZAoNvy1O9IZChAtZCdsd85C6D8mKLNcppG1XqNvRyfkd7RmyfGpf6pZA9JOEOPbjZBBrmv";
                 FacebookClient fbClient= new DefaultFacebookClient(accessToken);
                 FacebookType response = fbClient.publish("me/feed", FacebookType.class,
                // Parameter.with("message", "Visiter Notre Application BeMyGuest" )
                         Parameter.with("message", "Visiter Notre Application BeMyGuest il y aura plein de propriétées pour vous ! on vous attend")

         );
                     System.out.println("Votre propriete à été publiée");
                      System.out.println("fb.com/"+response.getId()); 
                         }
                        });
        
        
        Button b1 = new Button("Ajouter Commentaire");
        add(BorderLayout.west(new Label("", "PaddedLabel")).
                add(BorderLayout.CENTER, b1));
        b1.requestFocus();
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                User u = new User(1, "aaa", "aaaa");
                
                CommentairePropriete cp = new CommentairePropriete(contenu.getText(),p1.getId(),u.getId_u());
                cp.setRating(gg.getProgress());
                new CommentairePropCrud().addComment(cp);
                Dialog.show("Ajout avec succés", "Votre commentaire a été ajouté avec succés","ok",null);

                new DetailCommentForm1(theme, p1).show();
                
                System.out.println(cp);

            }
        });
        Button coms = new Button("Voir Commentaire");
        add(coms);
        coms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DetailCommentForm1(theme, p1).show();
            }
        });
        
        Button ajouterFavoris = new Button("Ajouter favoris");
        add(ajouterFavoris);
        Button reserver = new Button("Reserver");
        add(reserver);
        reserver.addActionListener(e->{
        Resrevation r  = new   Resrevation();  
        r.setId_r(p1.getId());
       Rserver r1 = new Rserver(res, r);
       r1.show();
        });
        ajouterFavoris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                User u = new User(1, "aaa", "aaaa");
                FavorisPropriete fp = new FavorisPropriete(p1.getId(),u.getId_u());
                Dialog.show("Ajout avec succés", "Proprietée ajoutée avec succés a votre liste favoris","ok",null);
                new FavorisProprieteCrud().addFavoris(fp);
                new MesFavorisProp(theme).show();
            }
        });
        
         Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> new ProprietefeedForm(res).show());

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
    private void initStarRankStyle(Style s, Image star) {
    s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
    s.setBorder(Border.createEmpty());
    s.setBgImage(star);
    s.setBgTransparency(0);
}
        private Slider createStarRankSlider() {
    Slider starRank = new Slider();
    starRank.setEditable(true);
    starRank.setMinValue(0);
    starRank.setMaxValue(10);
    starRank.setPropertyValue("hh", theme);
//    starRank.setProgress(5);
    Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
            derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
    Style s = new Style(0xffff33, 0, fnt, (byte)0);
    Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    s.setOpacity(100);
    s.setFgColor(0);
    Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
    initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
    starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));

//starRank.addPointerPressedListener((evt) -> {
//                  
//
//    evt.getSource().toString();
//});

return starRank;
        
}
}

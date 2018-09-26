

package com.codename1.uikit.cleanmodern;

import bmg.crud.FavorisCrud;
import bmg.crud.ProprieteCRUD;
import bmg.entities.Favoris;
import bmg.entities.Propriete;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
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
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class ProprietefeedForm extends BaseForm {
        private Resources theme=UIManager.initFirstTheme("/theme");
        private Form f1;
        Propriete p1;


    public ProprietefeedForm(Resources res) {

        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Welcome BeMyGuest");
        getContentPane().setScrollVisible(true);
        
        super.addSideMenu(res);
        ConnectionRequest connectionRequest= new ConnectionRequest();
        connectionRequest.setUrl("http://localhost/Codenameone/afficherPropriete.php");
        NetworkManager.getInstance().addToQueue(connectionRequest);
        
         connectionRequest.addResponseListener(new ActionListener() {

            public void actionPerformed(ActionEvent ev) {
            
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("rent.jpg"), spacer1, "15 Likes  ", "d", "BeMyGuest ");
                
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        
        
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
     
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton allProp = RadioButton.createToggle("All Prop", barGroup);
        allProp.setUIID("SelectBar");
       
        
        RadioButton ajouterProp = RadioButton.createToggle("Add Prop", barGroup);
        ajouterProp.setUIID("SelectBar");
        ajouterProp.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            new AjouterPropForm(theme).show();
            }
        });    
        RadioButton mesprops = RadioButton.createToggle("My Prop", barGroup);
        mesprops.setUIID("SelectBar");
        mesprops.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
             new MesProprietes(theme).show();

            }
        });  
        
        
        RadioButton favoris = RadioButton.createToggle("My Favorites", barGroup);
        favoris.setUIID("SelectBar");
         favoris.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
             new MesFavorisProp(theme).show();

            }
        });  
          
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, allProp, ajouterProp, mesprops, favoris),
                FlowLayout.encloseBottom(arrow)
        ));
                allProp.setSelected(true);

        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(allProp, arrow);
        });
        bindButtonSelection(allProp, arrow);
        bindButtonSelection(ajouterProp, arrow);
        bindButtonSelection(mesprops, arrow);
        bindButtonSelection(favoris, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        

       
            ProprieteCRUD pc = new ProprieteCRUD();
                  
                        ImageViewer img = new ImageViewer();
            EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("Logo.png"),true);
                for(Propriete p : pc.getListP(new String(connectionRequest.getResponseData()))){                 
                    pc.getListP(new String(connectionRequest.getResponseData()));
                    
                    URLImage uRLImage = URLImage.createToStorage(placeholder,p.getUrl(),"http://localhost/Codenameone/uploads/"+p.getUrl());
                    img.setImage(uRLImage);
                    System.out.println(p);
                    
        
                    Image im = img.getImage();
                    addButton(im,p, p.getPays(),p.getVille(),p.getTitre(), false, 26, 32);
                    clearClientProperties();
                    }            
            }   
            });

    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
  
    
   private void addButton(Image img,Propriete p, String pays,String titre,String ville, boolean liked, int likeCount, int commentCount) {
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       cnt.setLeadComponent(image);
       Label spacer1 = new Label();
        cnt.getAllStyles().setMargin(7, 7, 2, 2);
        
         Label titre1 = new Label(titre);
       titre1.setUIID("NewsTopLine");
       
           Label ville1 = new Label(ville);
       ville1.setUIID("NewsTopLine");

       Font largeBoldProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

        Label ta = new Label(pays);
        ta.getAllStyles().setFgColor(0xB85A5A);
        ta.getAllStyles().setFont(largeBoldProportionalFont);

        Font smallBoldProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.FACE_MONOSPACE, Font.SIZE_SMALL);

        ta.getAllStyles().setFont(largeBoldProportionalFont);
         titre1.getAllStyles().setFont(largeBoldProportionalFont);
        ville1.getAllStyles().setFont(largeBoldProportionalFont);
        
       Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
       likes.setTextPosition(RIGHT);
       if(!liked) {
           FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
       } else {
           Style s = new Style(likes.getUnselectedStyle());
           s.setFgColor(0xff2d55);
           FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
           likes.setIcon(heartImage);
       }
       Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
       FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);
       Label titreProp = new Label("Titre :");
              Label ville2 = new Label("Ville :");

       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,ville1,
                       BoxLayout.encloseX(titre1)
               ));
       cnt.getAllStyles().setBgColor(0xFCF6F8);
        cnt.getAllStyles().setBgTransparency(255);
       add(cnt);
       image.addActionListener(e ->{
          // new AjouterPropForm(theme).show();
         Propriete p3  = p;
         
           System.out.println(theme==null);
           
               new DetailPropForm(theme, p3).show();
               System.out.println("id ya daly :" +p3.getId());
       });
   }
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}

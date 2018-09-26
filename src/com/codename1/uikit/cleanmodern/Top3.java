package com.codename1.uikit.cleanmodern;



import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import bmg.crud.CrudRservation;
import bmg.entities.Resrevation;
import java.util.ArrayList;


public class Top3 extends BaseForm {
    
    

    public Top3(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Reservation List");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
         addTab(swipe, res.getImage("bk.jpg"), spacer1, "  ", "", "");
        addTab(swipe, res.getImage("dog.jpg"), spacer2, "100 Likes  ", "66 Comments", "Dogs are cute: story at 11");
                
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
                
        
        
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
         Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM); 
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton reservation = RadioButton.createToggle("reservation", barGroup);
        reservation.setUIID("SelectBar");
        Button demande = new Button("demande");
        demande .setUIID("SelectBar");
        Button popular = new Button("Top 3 ");
        
        popular.setUIID("SelectBar");
       Button myFavorite = new Button("fidels");
      
       myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, demande ,reservation, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));
        
     popular.getAllStyles().setFont(largeBoldSystemFont);
        
        demande.getAllStyles().setFont(largeBoldSystemFont);
            
        demande.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                      ListDemandeReservation r= new ListDemandeReservation(res);
                       r.showBack();
                        }
                    }); 
       reservation.getAllStyles().setFont(largeBoldSystemFont);
           reservation.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                      ListReservationByTitre r = new ListReservationByTitre  (res);
                       r.showBack();
                        }
                    });  
         myFavorite.getAllStyles().setFont(largeBoldSystemFont);
          myFavorite.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                     Top3Client r = new Top3Client (res);
                       r.showBack();
                        }
                    });
         
         
         ConnectionRequest connectionRequest= new ConnectionRequest();
      connectionRequest.setUrl("http://localhost/script/Top3.php");
       NetworkManager.getInstance().addToQueue(connectionRequest);
       connectionRequest.addResponseListener(new ActionListener() {

            public void actionPerformed(ActionEvent ev) {   
        
        
        
        CrudRservation  fc = new CrudRservation ();
               ArrayList<Resrevation> l =fc.getTop3(new String(connectionRequest.getResponseData()));
               
               for(Resrevation f : l){
                   
                    Container cnt = new Container();
                     
                    Container cnt1 = addButton(res.getImage("log.png"),"                        "+ f.getTitre(),"Cette chambre est reserver "+f.getPrenom()+" fois",cnt);
             
                    cnt1.getAllStyles().setBgColor(0xFCF6F8);
                    cnt1.getAllStyles().setBgTransparency(255);
                    
                 
                   Form form = Display.getInstance().getCurrent();
                     
                        
                      form.refreshTheme(true);
              
               
               
               
               }
        
            }   
            });
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
      
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
       
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
    
   private Container  addButton(Image img, String title ,String et ,Container cnt ) {
       int height = Display.getInstance().convertToPixels(13.5f);
       int width = Display.getInstance().convertToPixels(11f);
        Label image = new  Label(img.fill(width, height));
      
       image.setUIID("Label");
       cnt = BorderLayout.west(image);
       
       cnt.getAllStyles().setMargin(7, 7, 2, 2);
       
      
       Label ta = new Label(title);
     
    Font largeBoldProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
       
       ta.getAllStyles().setFgColor(0xB85A5A);
      ta.getAllStyles().setFont(largeBoldProportionalFont);
  
         Font smallBoldProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
       
   
    
       Label etat = new Label(et);
        etat.getAllStyles().setFont(smallBoldProportionalFont);  
            
       
       

    
      ;  
   
         
       
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,etat,
                       BoxLayout.encloseX()
               ));
      
       add(cnt);
      return cnt ;
   }
    
   
   
   
 


}

package com.codename1.uikit.cleanmodern;



import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
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
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import bmg.crud.CrudRservation;
import bmg.entities.Resrevation;
import java.util.ArrayList;


public class ListPropriete extends BaseForm  {
    
      public static final String ACCOUNT_SID = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
  public static final String AUTH_TOKEN = "your_auth_token"; 

    public ListPropriete(Resources res) {
        super("Newsfeed", BoxLayout.y());
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> new ListDemandeReservation(res).show());       
        
        Tabs swipe = new Tabs();
        Label spacer1 = new Label();
        Label spacer2 = new Label();
         addTab(swipe, res.getImage("bk.jpg"), spacer1, "", "", "");
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
                
        Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM); 
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Demande", barGroup);
        all.setUIID("SelectBar");
        Button reservation = new Button("Rservation");
        reservation.setUIID("SelectBar");
        Button popular = new Button("Top 3 ");
        popular.setUIID("SelectBar");
       Button myFavorite = new Button("client fidel");
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
  
        
     
        popular.getAllStyles().setFont(largeBoldSystemFont);
      
         all.getAllStyles().setFont(largeBoldSystemFont);
        
        reservation.getAllStyles().setFont(largeBoldSystemFont);
        
           popular.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                     Top3 r = new Top3 (res);
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
       connectionRequest.setUrl("http://localhost/script/ListPropriete.php" );
       NetworkManager.getInstance().addToQueue(connectionRequest);
       connectionRequest.addResponseListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent ev) {
        
        CrudRservation  fc = new CrudRservation ();
               ArrayList<Resrevation> l =fc.listDemande(new String(connectionRequest.getResponseData()));
               
               for(Resrevation f : l){
                   
                     Button refuser = new Button ("reserver");
                    Container cnt = new Container();
                     
                    Container cnt1 = addButton(res.getImage("news-item-1.jpg"),"                        "+ f.getTitre(),"Cette chambre est situe dans  "+f.getPrenom(),refuser,cnt);
             

                    
                   
                
                refuser.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                   
                          Rserver r = new Rserver (res,f) ; 
                            r.show();
                           
                        }
                    });
               
               
               
               }
             Form form = Display.getInstance().getCurrent();
                     
                        
                      form.refreshTheme(true);
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
    
   private Container  addButton(Image img, String title , String date , Button refuser ,Container cnt ) {
       int height = Display.getInstance().convertToPixels(13.5f);
       int width = Display.getInstance().convertToPixels(11f);
        Label image = new  Label(img.fill(width, height));
      
       image.setUIID("Label");
       cnt = BorderLayout.west(image);
       
       cnt.getAllStyles().setMargin(7, 7, 2, 2);
       
        cnt.getAllStyles().setBgColor(0xFCF6F8);
                    cnt.getAllStyles().setBgTransparency(255);
       Label ta = new Label(title);
     
  Font largeBoldProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
       
       ta.getAllStyles().setFgColor(0xB85A5A);
      ta.getAllStyles().setFont(largeBoldProportionalFont);
  
       
       
 Font smallBoldProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);     
       
Label datee = new Label(date);
       
        datee.getAllStyles().setFont(smallBoldProportionalFont);  
     
    
       

       
        
            
       
       refuser.getAllStyles().setFont(smallBoldProportionalFont);  
           
    
      ;  
   
         
       
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,datee,
                       BoxLayout.encloseX(refuser)
               ));
      
       add(cnt);
      return cnt ;
   }
    
 
   
    /*public void SendSmS(String number)  { 


    Twilio.init("ACd919090a5cbcbdc57362bde0e7a2e55c","01f2bc1b95e3f1538d95a3fcfa189b62");

    Message message = Message.creator(new PhoneNumber(number),
        new PhoneNumber(number), 
        "This is the ship that made the Kessel Run in fourteen parsecs?").create();

 
}  */public  void callURL() {
     
        
        String myURL="https://rest.nexmo.com/sms/json?api_key=5cda4d36&api_secret=975ddd6c0d079a0d&to=216"+
                "50866087"+"&from=BeMyGuest&text=Vous+avez+recu+une+demande+de+reservation+Code+du+message+:+"+1523;
      ConnectionRequest connectionRequest= new ConnectionRequest();
  connectionRequest.setUrl(myURL);
        NetworkManager.getInstance().addToQueue(connectionRequest); 

        
    }
}

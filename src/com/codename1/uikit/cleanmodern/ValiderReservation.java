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
import com.codename1.ui.FontImage;
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


public class ValiderReservation extends BaseForm {
    
    

    public ValiderReservation(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Demande List");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        ConnectionRequest connectionRequest= new ConnectionRequest();
       connectionRequest.setUrl("http://localhost/script/selectRes.php");
       NetworkManager.getInstance().addToQueue(connectionRequest);
       connectionRequest.addResponseListener(new ActionListener() {

            public void actionPerformed(ActionEvent ev) {
                
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("bk.jpg"), spacer1, "", "", "You can see all your demande ");
        addTab(swipe, res.getImage("dog.jpg"), spacer2, "", "", "Dogs are cute: story at 11");
                
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
        
             CrudRservation  fc = new CrudRservation ();
               ArrayList<Resrevation> l =fc.getListT(new String(connectionRequest.getResponseData()));
               
               for(Resrevation f : l){
                    Button accepter = new Button ("accepter");
                     Button refuser = new Button ("refuser");
                    Container cnt = new Container();
                     addButton(res.getImage("log.png"), f.getNom()+" "+f.getPrenom(),"demande non traiter","date arrivee"+f.getDateDebut(),"date depart "+f.getDateFin(),accepter,refuser,cnt);
           accepter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                        CrudRservation res = new CrudRservation();
                        res.update(f);
                         l.remove(f);
                         cnt.removeAll();
                       cnt.refreshTheme(focusScrolling);
                        }
                    });
                
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
    
   private Container  addButton(Image img, String title , String date , String date1,String et , Button accepter ,Button refuser ,Container cnt ) {
       int height = Display.getInstance().convertToPixels(19.5f);
       int width = Display.getInstance().convertToPixels(14f);
        Label image = new  Label(img.fill(width, height));
      
       image.setUIID("Label");
       cnt = BorderLayout.west(image);
       cnt.getAllStyles().setBorder(Border.createEtchedRaised());
       cnt.getAllStyles().setMargin(7, 7, 2, 2);
       cnt.getAllStyles().setFgColor(0xB85A5A);
      
       TextArea ta = new TextArea(title);
       
       ta.setUIID("NewsTopLine");
       ta.setEditable(false);
TextArea datee = new TextArea(date);
       datee.setUIID("NewsTopLine");
       datee.setEditable(false);
  TextArea dateFin = new TextArea(date1);
       dateFin.setUIID("NewsTopLine");
        dateFin.setEditable(false);    
    
       TextArea etat = new TextArea(et);
       etat.setUIID("NewsTopLine");
       etat.setEditable(false);    
            
       
       

    
      ;  
   
         
       
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,datee,dateFin,etat,
                       BoxLayout.encloseX(accepter, refuser)
               ));
      
       add(cnt);
      return cnt ;
   }
    
}

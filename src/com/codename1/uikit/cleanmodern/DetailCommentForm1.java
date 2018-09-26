
package com.codename1.uikit.cleanmodern;

import bmg.crud.CommentairePropCrud;
import bmg.crud.UserCo;
import bmg.entities.CommentairePropriete;
import bmg.entities.Propriete;
import bmg.entities.User;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
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
public class DetailCommentForm1 extends BaseForm {


    public DetailCommentForm1(Resources res,Propriete p1) {
        
        super("Newsfeed", BoxLayout.y());
        
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Feed Back");
        getContentPane().setScrollVisible(true);
        
        
        super.addSideMenu(res);
        
          
        tb.addSearchCommand(e -> {});
        
        
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("");
        Label twitter = new Label("");
        
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3, 
                            facebook,
                            FlowLayout.encloseCenter(
                              ),
                            twitter
                    )
                )
        ));
         ConnectionRequest connectionRequest= new ConnectionRequest();
        connectionRequest.setUrl("http://localhost/Codenameone/afficherCommentaireByProp.php?id_p="+p1.getId());
        NetworkManager.getInstance().addToQueue(connectionRequest);
        
         connectionRequest.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
            CommentairePropCrud cpc = new CommentairePropCrud();
             for(CommentairePropriete c : cpc.getListCom(new String(connectionRequest.getResponseData())))
             {
                 
                 
                System.out.println(c.getContenu());
                TextArea contenu = new TextArea("Contenu et note");
               // TextArea note = new TextArea("Note");
                
                contenu.setText(c.getContenu()
                        + " et la note " +c.getRating() + "/5");
              //  note.setText(String.valueOf(c.getRating()));
                
                contenu.setEnabled(false);
              //  note.setEnabled(false);

                contenu.setUIID("TextFieldBlack");
                addStringValue("Contenu et note", contenu);
            //    contenu.setUIID("TextFieldBlack");
            //    addStringValue("Note", note);
                
          

                
             }

       }
        });
         //Button retour = new Button("retour");
          Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());

        
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

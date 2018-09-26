/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.codename1.uikit.cleanmodern;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.AutoCompleteTextField;
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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class AddExperience extends BaseForm {

        private String fileName= "No Picture";
    public AddExperience(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
       /* TextField Nom = new TextField("", "Username", 20, TextField.ANY);
        TextField Description = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField Type = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField  = new TextField("", "Confirm Password", 20, TextField.PASSWORD);*/
       /* username.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);*/
        
          TextField Nom = new TextField("", "Nom");
        TextField Description = new TextField("", "Description");
         TextField Price = new TextField("", "Price");
        
        String[] Type={"Exotique","Mexicaine","Forestiere","Montagne","Sahara"};
        AutoCompleteTextField act= new AutoCompleteTextField(Type);
        String[] Participants={"1","2","3","4","5"};
        AutoCompleteTextField tfParticipants= new AutoCompleteTextField(Participants);
                
        Picker Arrival= new Picker();
        Arrival.setType(Display.PICKER_TYPE_DATE);
        
        
        Picker Departure= new Picker();
        Departure.setType(Display.PICKER_TYPE_DATE);
        
    /*    f.add(tfNom);
        f.add(tfDescription);
        f.add(act);
        f.add(tfParticipants);
        f.add(arrival);
        f.add(departure);
        f.add(tfPrice);*/
          
        
        
        
        String contenu = "votre Experience a été ajouté ,";
        Button next = new Button("Insert");
        Button signIn = new Button("Sign In");
         Button AddPicture = new Button("Pick Picture From Gallery");
        signIn.addActionListener(e -> previous.showBack());
       signIn.setUIID("Link");
       Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Add EXperience", "LogoLabel"),
                new FloatingHint(Nom),
                createLineSeparator(),
                new FloatingHint(Description),
                createLineSeparator(),
                 new Label("Type of experience"),
                act,
                 new Label("Number of participants"),
                tfParticipants,
                 new Label("Date Arrival"),
                Arrival,
                 new Label("Date Departure"),
                Departure,
                 new FloatingHint(Price),
                createLineSeparator()
                 
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                AddPicture,
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        //next.addActionListener(e -> new ActivateForm(res).show());
        next.addActionListener(new ActionListener() {       
         @Override
            public void actionPerformed(ActionEvent evt) {
                 String dateString = null;
        SimpleDateFormat sda = new SimpleDateFormat("yyyy-MM-dd");
        dateString = sda.format( Arrival.getDate());
         SimpleDateFormat sdd = new SimpleDateFormat("yyyy-MM-dd");
        dateString = sdd.format( Departure.getDate());

                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/pi/insert.php?nom_xp=" + Nom.getText() + "&description=" + Description.getText() + "&type=" + act.getText() +"&participants=" + tfParticipants.getText() +"&arrival=" + dateString +"&departure=" + dateString +"&prix_xp=" + Price.getText()+"&image_name="+fileName+ "");

                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);
                        System.out.println(s);
                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "Add successful", "Ok", null);
                            SmSSender.sendSMS(contenu, "+21624217710");
                        } else {
                            Dialog.show("Erreur", "Add failed", "Ok", null);
                        }
                    }
                });

                NetworkManager.getInstance().addToQueue(req);
                new afficherExps(res).show();
            }
        });
        
        AddPicture.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    MultipartRequest cr = new MultipartRequest();
                    String  filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                    fileName= extractFileName(filePath) +"jpg";
                    cr.setUrl("http://localhost/pi/inserImage.php");
                    cr.setPost(true);
                    
                    String mime="image/jpeg";
                    cr.addData("file", filePath, mime); 
                    cr.setFilename("file", fileName);
                    
                    InfiniteProgress prog = new InfiniteProgress();
                    Dialog dlg = prog.showInifiniteBlocking();
                    cr.setDisposeOnCompletion(dlg);
                    NetworkManager.getInstance().addToQueueAndWait(cr);
                    
                } catch (IOException ex) {
                    
                }
            }
        });
               

     
    }
    
    
     public static String extractFileName( String filePathName )
  {
    if ( filePathName == null )
      return null;

    int dotPos = filePathName.lastIndexOf( '.' );
    int slashPos = filePathName.lastIndexOf( '\\' );
    if ( slashPos == -1 )
      slashPos = filePathName.lastIndexOf( '/' );

    if ( dotPos > slashPos )
    {
      return filePathName.substring( slashPos > 0 ? slashPos + 1 : 0,
          dotPos );
    }

    return filePathName.substring( slashPos > 0 ? slashPos + 1 : 0 );
  }

}

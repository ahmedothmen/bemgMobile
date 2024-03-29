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

import com.codename1.components.FloatingHint;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import bmg.crud.ReclamationDAO;
import bmg.entities.Propriete;
import bmg.entities.Reclamation;
import bmg.entities.User;
import com.codename1.l10n.DateFormat;



/**
 * Sign in UI
 *
 * @author Shai Almog
 */
public class ReclamationAdd extends BaseForm {
    ReclamationDAO d = new ReclamationDAO();

    public ReclamationAdd(Resources res,Propriete p) {
        super(new BorderLayout());
        
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        
        add(BorderLayout.NORTH, new Label(res.getImage("Logo.png"), "LogoLabel"));
       // date=getCurrentDate() ;
        TextField type = new TextField("", "type de reclamation", 20, TextField.ANY);
        TextField contenu = new TextField("", "Contenu de votre", 20, TextField.ANY);
        type.setSingleLineTextArea(false);
        contenu.setSingleLineTextArea(false);
        Button signIn = new Button("ajouter votre reclamation");
        Button signUp = new Button("Sign Up");
        signUp.addActionListener(e -> new SignUpForm(res).show());
     
   
        Container content = BoxLayout.encloseY(
                new FloatingHint(type),
                createLineSeparator(),
                new FloatingHint(contenu),
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter()
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        signIn.addActionListener (new ActionListener() {
     

             
        

            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest connectionRequest= new ConnectionRequest();
                User u=new User(1);
                
                Reclamation r = new Reclamation(contenu.getText(),type.getText(),u.getId_u(),p.getId());
                System.out.println(u.getId_u());
                System.out.println(p.getId());
                System.out.println(contenu.getText()+"type"+type.getText());
                d.addREC(r);
                new DetailPropForm(res, p).show();
          /*  String myURL="https://rest.nexmo.com/sms/json?api_key=904fb0b7&api_secret=ecef347f5d024550&to=216"+
                "92674090"+"&from=BeMyGuest&text=Vous+avez+recu+une+reclmation+Code+du+message+:+"+1523;
    
        connectionRequest.setUrl(myURL);
        NetworkManager.getInstance().addToQueue(connectionRequest);
       */
       //    new AfficherRecForm(res).show();
            }
        });
    }
    
}

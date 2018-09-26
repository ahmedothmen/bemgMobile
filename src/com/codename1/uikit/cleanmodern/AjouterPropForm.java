

package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
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
import com.codename1.ui.util.Resources;
import bmg.crud.ProprieteCRUD;
import bmg.crud.UserCo;
import bmg.entities.Propriete;
import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import java.io.IOException;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class AjouterPropForm extends BaseForm {
     Container content;
     private String fileName="No picture";

    public AjouterPropForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        
  
                
        ComboBox comboBoxCategorie = new ComboBox();
        comboBoxCategorie.addItem("Appartement");
        comboBoxCategorie.addItem("Maison");
        
        ComboBox comboBoxType = new ComboBox();
        comboBoxType.addItem("Chambre entier");
        comboBoxType.addItem("Chambre partagée");
           
        


       // TextField categorieTf = new TextField("", "Categorie", 20, TextField.ANY);

        //TextField typeTf = new TextField("", "Type", 20, TextField.ANY);
        TextField paysTf = new TextField("", "Pays", 20, TextField.ANY);
       
        TextField villeTf = new TextField("", "Ville", 20, TextField.ANY);
        TextField rueTf = new TextField("", "Rue", 20, TextField.ANY);
        TextField titreTf = new TextField("", "Titre", 20, TextField.ANY);
        TextField prixTf = new TextField("", "Prix", 20, TextField.ANY);
        TextField nbrVoyageurTf = new TextField("", "Nombre voyageur", 20, TextField.ANY);
        TextField nbrChambreTf = new TextField("", "Nombre Chambre", 20, TextField.ANY);
        TextField description = new TextField("", "description", 20, TextField.ANY);

       //categorieTf.setSingleLineTextArea(false);
        //typeTf.setSingleLineTextArea(false);
        paysTf.setSingleLineTextArea(false);
        villeTf.setSingleLineTextArea(false);
        rueTf.setSingleLineTextArea(false);
        titreTf.setSingleLineTextArea(false);
        prixTf.setSingleLineTextArea(false);
        nbrVoyageurTf.setSingleLineTextArea(false);
        nbrChambreTf.setSingleLineTextArea(false);
        description.setSingleLineTextArea(false);

        Button ajouter = new Button("Ajouter");
        ajouter.setUIID("Link");
        Button AddPicture = new Button("Ajouter image");
                AddPicture.setUIID("Link");


                


        content = BoxLayout.encloseY(
                new Label("Ajouter une propriete", "LogoLabel"),
              //  new FloatingHint(categorieTf),
               // createLineSeparator(),
                //new FloatingHint(typeTf),
                //createLineSeparator(),
                new FloatingHint(paysTf),
                createLineSeparator(),
                new FloatingHint(villeTf),
                createLineSeparator(),
                new FloatingHint(rueTf),
                createLineSeparator(),
                new FloatingHint(titreTf),
                createLineSeparator(),
                new FloatingHint(prixTf),
                createLineSeparator(),
                new FloatingHint(nbrVoyageurTf),
                createLineSeparator(),
                new FloatingHint(nbrChambreTf),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator()
                
        );
         
         
        content.setScrollableY(true);
        content.add(comboBoxCategorie);
       content.add(comboBoxType);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                FlowLayout.encloseCenter(ajouter,AddPicture)
        ));
        
        ajouter.requestFocus();
        
                ajouter.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent evt) {
                    
                  Propriete typedProp = new Propriete(comboBoxCategorie.getSelectedItem().toString()/*categorieTf.getText()*/,comboBoxType.getSelectedItem().toString()/*typeTf.getText()*/, paysTf.getText(),villeTf.getText(),rueTf.getText(),titreTf.getText(),prixTf.getText(),nbrVoyageurTf.getText(),nbrChambreTf.getText(),description.getText(),fileName,UserCo.userCo.getId_u());

                  if(paysTf.getText().equals("") ||villeTf.getText().equals("")|| rueTf.getText().equals("") || titreTf.getText().equals("") ||prixTf.getText().equals("")|| nbrVoyageurTf.getText().equals("")|| nbrChambreTf.getText().equals("")|| description.getText().equals("")){
                        Dialog.show("Saisir des données valides", "Veuillez saisir des données valides","ok",null);
                  }
                  else{
                  


                    new ProprieteCRUD().addProp(typedProp); 
                 String myURL="https://rest.nexmo.com/sms/json?api_key=1ffcfb32&api_secret=24d46938722883a8&to=216"+
                "53737860"+"&from=SocialPro&text=une+nouvelle+propriete+a+été+crée:+"+1523;
                ConnectionRequest connectionRequest1= new ConnectionRequest();
                    connectionRequest1.setUrl(myURL);
                    Dialog.show("Ajout avec succés", "Proprietée ajoutée avec succés","ok",null);
                new ProprietefeedForm(res).show();
                
            }
            }

        }); 
              AddPicture.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    MultipartRequest cr = new MultipartRequest();
                    String  filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                    fileName= extractFileName(filePath) +"jpg";
                    cr.setUrl("http://localhost/Codenameone/inserImage.php");
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

            private String extractFileName(String filePathName) {
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
            
            );
  
    
    }
  
}

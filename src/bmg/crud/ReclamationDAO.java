/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bmg.crud;

import bmg.entities.Reclamation;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;


/**
 * 
 * 
 *
 * @author LENOVO
 */
public class ReclamationDAO {
    private ConnectionRequest connectionRequest;
     

    public void addREC(Reclamation r) {
     connectionRequest = new ConnectionRequest() {
            @Override
            protected void postResponse() {
            /*    Dialog d = new Dialog("Ajouter une propriete");
                TextArea popupBody = new TextArea("Propriete ajoutée avec succés");
                popupBody.setUIID("PopupBody");
                popupBody.setEditable(false);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, popupBody);
                d.showDialog();*/
            }
        };
            connectionRequest.setUrl("http://localhost/Codenameone/insertReclamation.php?contenu=" + r.getContenu()
                
                + "&type=" + r.getType()
                + "&idU=" + r.getIdu()
                + "&idP=" + r.getIdp()
               );
        NetworkManager.getInstance().addToQueue(connectionRequest);
         
    }
       
           
}
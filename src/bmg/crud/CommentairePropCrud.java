/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmg.crud;

import bmg.entities.CommentairePropriete;
import bmg.entities.Propriete;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daly
 */
public class CommentairePropCrud {
    private ConnectionRequest connectionRequest;
    public static Form listOfProprietes;

    public void addComment(CommentairePropriete cmtProp) {
        connectionRequest = new ConnectionRequest() {
            @Override
            protected void postResponse() {
            /*    Dialog d = new Dialog("Ajout un commentaire");
                TextArea popupBody = new TextArea("Votre commentaire est ajouté avec succés");
                popupBody.setUIID("PopupBody");
                popupBody.setEditable(false);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, popupBody);
                d.showDialog();*/
            }
        };
        connectionRequest.setUrl("http://localhost/Codenameone/insertCommentaire.php?contenu=" + cmtProp.getContenu()+"&rating="+cmtProp.getRating()+"&id_p=" + cmtProp.getIdp() + "&id_u=" + cmtProp.getIdu());

        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

     public ArrayList<CommentairePropriete> getListCom(String json) {
      
        ArrayList <CommentairePropriete> listCom = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();
            

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("commentairepropriete");

            for (Map<String, Object> obj : list) {
               CommentairePropriete c = new CommentairePropriete(obj.get("contenu").toString(),Integer.parseInt(obj.get("rating").toString()));

                listCom.add(c);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listCom;
               
    }
       
}

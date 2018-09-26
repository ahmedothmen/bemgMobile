/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmg.crud;

import bmg.entities.CommentairePropriete;
import bmg.entities.FavorisPropriete;
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
public class FavorisProprieteCrud {
    private ConnectionRequest connectionRequest;
    public static Form listOfProprietes;

    public void addFavoris(FavorisPropriete fp) {
        connectionRequest = new ConnectionRequest() {
            @Override
            protected void postResponse() {
             /*   Dialog d = new Dialog("Favoris ajoutée");
                TextArea popupBody = new TextArea("la propriete a été ajouté a votre liste de favoris");
                popupBody.setUIID("PopupBody");
                popupBody.setEditable(false);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, popupBody);
                d.showDialog();*/
            }
        };
        connectionRequest.setUrl("http://localhost/Codenameone/insertFavoris.php?idP=" + fp.getIdp() + "&idU=" + fp.getIdu());

        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void removeFavoris(int idP) {   // remove Propriete by id
        connectionRequest = new ConnectionRequest() {
            @Override
            protected void postResponse() {
              /*  Dialog d = new Dialog("Supprimer ma propriete");
                TextArea popupBody = new TextArea("Propriete supprimée avec succés");
                popupBody.setUIID("PopupBody");
                popupBody.setEditable(false);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, popupBody);
                d.showDialog();*/
            }
        };

        connectionRequest.setUrl("http://localhost/Codenameone/removeFavorisProp.php?idP=" + idP);
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

         public ArrayList<Propriete> getListFavoris(String json) {
      
        ArrayList <Propriete> listAct = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("propriete");
            for (Map<String, Object> obj : list) {
               Propriete p = new Propriete(Integer.parseInt(obj.get("id_p").toString()),obj.get("categoriePropriete").toString(),obj.get("typePropriete").toString(),obj.get("pays").toString(),obj.get("ville").toString(),obj.get("rue").toString(),obj.get("titre").toString(),obj.get("prix").toString(),obj.get("nbrChambre").toString(),obj.get("nbrVoyageur").toString(),obj.get("description").toString(),obj.get("url").toString());
                listAct.add(p);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listAct;

    }

}

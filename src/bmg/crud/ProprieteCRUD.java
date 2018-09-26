/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmg.crud;

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
import bmg.entities.Propriete;

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
public class ProprieteCRUD {

    private ConnectionRequest connectionRequest;
    public static Form listOfProprietes;

    public void addProp(Propriete propriete) {
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
        connectionRequest.setUrl("http://localhost/Codenameone/insertPropriete.php?catProp=" + propriete.getCategoriePropriete() + "&typeProp=" + propriete.getTypePropriete() + "&pays=" + propriete.getPays() + "&ville=" + propriete.getVille() + "&rue=" + propriete.getRue() + "&titre=" + propriete.getTitre() + "&prix=" + propriete.getPrix() + "&nbrChambre=" + propriete.getNbrChambre() + "&nbrVoyageur=" + propriete.getNbrVoyageur() + "&description=" + propriete.getDescription()+"&url="+propriete.getUrl()+ "&animaux=" + "1" + "&fumeur=" + "1" + "&enfant=" + "1" + "&alcool=" + "1" + "&id_u=" + propriete.getUser());

        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void removePropriete(Propriete propriete) {   // remove Propriete by id
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

        connectionRequest.setUrl("http://localhost/Codenameone/removePropriete.php?id_p=" + propriete.getId());
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void updatePropriete(Propriete propriete) {
        connectionRequest = new ConnectionRequest() {

            @Override
            protected void postResponse() {
              /*  Dialog d = new Dialog("Popup Title");
                TextArea popupBody = new TextArea("Propriete mis a jour ");
                popupBody.setUIID("PopupBody");
                popupBody.setEditable(false);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, popupBody);
                d.show();*/
            }
        };
        connectionRequest.setUrl("http://localhost/Codenameone/updatePropriete.php?categoriePropriete="+propriete.getCategoriePropriete()+"&typePropriete="+propriete.getTypePropriete()+"&pays="+propriete.getPays()+"&ville="+propriete.getVille()+"&rue="+propriete.getRue()+"&titre="+propriete.getTitre()+"&prix="+propriete.getPrix()+"&nbrChambre="+propriete.getNbrChambre()+"&nbrVoyageur="+propriete.getNbrVoyageur()+"&description="+propriete.getDescription()+"&id_p="+propriete.getId());

        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

   
     public ArrayList<Propriete> getListP(String json) {
      
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
        public ArrayList<Propriete> getListPByUser(String json) {
      
        ArrayList <Propriete> listPropByUser = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();
            

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("propriete");

            for (Map<String, Object> obj : list) {
               Propriete p = new Propriete(Integer.parseInt(obj.get("id_p").toString()),obj.get("categoriePropriete").toString(),obj.get("typePropriete").toString(),obj.get("pays").toString(),obj.get("ville").toString(),obj.get("rue").toString(),obj.get("titre").toString(),obj.get("prix").toString(),obj.get("nbrChambre").toString(),obj.get("nbrVoyageur").toString(),obj.get("description").toString(),obj.get("url").toString());
                listPropByUser.add(p);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listPropByUser;
               
    }
        public int getNbrProp(String json) {
      
        int nbr = 0;

        try {

            JSONParser j = new JSONParser();
            

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("propriete");

            for (Map<String, Object> obj : list) {

            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return nbr;
               
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmg.crud;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.NavigationCommand;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;

import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;

import bmg.entities.Resrevation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import java.util.List;

import java.util.Map;





/**
 *
 * @author HP
 */
public class CrudRservation {
    
static List<Resrevation> reservations = new ArrayList<Resrevation >();
    
    
 public void reserver (Picker d , Picker d1,Resrevation r){
 int id = 1;
          int idp=r.getId_r();
          int udud=UserCo.userCo.getId_u();;
      System.out.println(idp);
     String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
        dateString = sdfr.format( d.getDate());     
        String dateString1 = null;
        SimpleDateFormat sdfr1 = new SimpleDateFormat("yyyy-MM-dd");
        dateString1 = sdfr1.format( d.getDate());   
        
        
        ConnectionRequest   connectionRequest=new ConnectionRequest(){
            @Override
            protected void postResponse() {
               
           
            } };
          
          connectionRequest.setUrl("http://localhost/script/insert.php?dateDebut=" +dateString+ "&dateFin=" +dateString1+"&idu="+id+"&idp="+idp+"&idud="+id);
        NetworkManager.getInstance().addToQueue(connectionRequest); 

}


  public void update( Resrevation  b){
      ConnectionRequest  connectionRequest = new ConnectionRequest() {
            
            @Override
            protected void postResponse() { 

            }
        };
        connectionRequest.setUrl("http://localhost/script/update.php?id_r="+b.getId_r());
        
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

public void remove (Resrevation r){
    ConnectionRequest  connectionRequest = new ConnectionRequest() {
            
            @Override
            protected void postResponse() { 
         
          
            }
        };
        connectionRequest.setUrl("http://localhost/script/remove.php?id_r="+r.getId_r());
        
        NetworkManager.getInstance().addToQueue(connectionRequest);


}






 public ArrayList<Resrevation> getListT(String json) {
      
        ArrayList <Resrevation> listAct = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("reservation");
            for (Map<String, Object> obj : list) {
               Resrevation a = new Resrevation(Integer.parseInt((String) obj.get("id_r")),(String) obj.get("dateDebut"),(String) obj.get("dateFin"),(String) obj.get("etat"),(String) obj.get("nom"),(String) obj.get("prenom"),(String) obj.get("titre"));
             

                listAct.add(a);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listAct;

     


 }
 public ArrayList<Resrevation> getTop3(String json) {
      
        ArrayList <Resrevation> listAct = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("reservation");
            for (Map<String, Object> obj : list) {
               Resrevation a = new Resrevation((String) obj.get("nb"),(String) obj.get("titre"));
               System.err.println(a);

                listAct.add(a);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listAct;

     


 }

 public ArrayList<Resrevation> getTop3Client(String json) {
      
        ArrayList <Resrevation> listAct = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("reservation");
            for (Map<String, Object> obj : list) {
               Resrevation a = new Resrevation((String) obj.get("nb"),(String) obj.get("nom"),(String) obj.get("prenom"));
               System.err.println(a);

                listAct.add(a);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listAct;

    
 }

 public ArrayList<Resrevation> listDemande(String json) {
      
        ArrayList <Resrevation> listAct = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("reservation");
            for (Map<String, Object> obj : list) {
               Resrevation a = new Resrevation(Integer.parseInt((String) obj.get("id_p")),(String) obj.get("nb"),(String) obj.get("titre"));
             

                listAct.add(a);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listAct;

     


 }


}

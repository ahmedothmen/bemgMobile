/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bmg.entities;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
//import java.util.Objects;

/**
 *
 * @author User
 */
public class Experience {
    private int id_xp;
    private int id_user;
    private String nom_xp;
    private String description;
    private String type;
    private String arrival;
    private String departure;
    private int participants;
    private float prix_xp;
    private String image_name;

    public Experience() {
    }

    public Experience(int id_xp, int id_user, String nom_xp, String description, String type, String arrival, String departure, int participants, float prix_xp, String image_name) {
        this.id_xp = id_xp;
        this.id_user = id_user;
        this.nom_xp = nom_xp;
        this.description = description;
        this.type = type;
        this.arrival = arrival;
        this.departure = departure;
        this.participants = participants;
        this.prix_xp = prix_xp;
        this.image_name = image_name;
    }

    public Experience(int id_xp, String nom_xp, String description, String type, String arrival, String departure, int participants, float prix_xp) {
        this.id_xp = id_xp;
        this.nom_xp = nom_xp;
        this.description = description;
        this.type = type;
        this.arrival = arrival;
        this.departure = departure;
        this.participants = participants;
        this.prix_xp = prix_xp;
    }

    public int getId_xp() {
        return id_xp;
    }

    public void setId_xp(int id_xp) {
        this.id_xp = id_xp;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom_xp() {
        return nom_xp;
    }

    public void setNom_xp(String nom_xp) {
        this.nom_xp = nom_xp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public float getPrix_xp() {
        return prix_xp;
    }

    public void setPrix_xp(float prix_xp) {
        this.prix_xp = prix_xp;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    @Override
    public String toString() {
        return "Experience{" + "nom_xp=" + nom_xp + ", description=" + description + ", type=" + type + ", arrival=" + arrival + ", departure=" + departure + ", participants=" + participants + ", prix_xp=" + prix_xp + '}';
    }

  

    
  public ArrayList<Experience> getListExperience(String json) {
        ArrayList<Experience> listExperiences = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> experiences = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) experiences.get("experience");

            for (Map<String, Object> obj : list) {
                Experience e = new Experience();
               // e.setAge(Integer.parseInt(obj.get("age").toString()));
                e.setNom_xp(obj.get("nom_xp").toString());
                e.setDescription(obj.get("description").toString());
                e.setType(obj.get("type").toString());
                e.setArrival(obj.get("arrival").toString());
                e.setDeparture(obj.get("departure").toString());
                e.setParticipants(Integer.parseInt(obj.get("participants").toString()));
                e.setPrix_xp(Integer.parseInt(obj.get("prix_xp").toString()));
                e.setImage_name(obj.get("image_name").toString());
                
                listExperiences.add(e);

            }

        } catch (IOException ex) {
         }
        return listExperiences;

    }
   
    
    
    
    
    
    
    
}

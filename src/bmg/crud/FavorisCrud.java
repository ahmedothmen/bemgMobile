/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmg.crud;

import bmg.entities.Favoris;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.ui.Form;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Oussaa
 */
public class FavorisCrud {
    
    private ConnectionRequest connectionRequest;
    public static Form listOfFavoris;
    
    
    
    
      
      
      public ArrayList<Favoris> getListT(String json) {
      
        ArrayList <Favoris> listAct = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("favoris");

            for (Map<String, Object> obj : list) {
               Favoris a = new Favoris(Integer.parseInt(obj.get("id").toString()), obj.get("firstname").toString(), obj.get("lastname").toString(), obj.get("alias").toString(), obj.get("imageurl").toString());
                listAct.add(a);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listAct;

    }

}

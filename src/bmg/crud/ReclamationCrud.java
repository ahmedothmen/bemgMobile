/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bmg.crud;

import bmg.entities.Reclamation;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class ReclamationCrud {
    
    public ArrayList<Reclamation> getList(String json){
    
    ArrayList <Reclamation> listAct = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> actualite  = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("reclamation");

            for (Map<String, Object> obj : list) {
               Reclamation r = new Reclamation( obj.get("type").toString(), obj.get("contenu").toString());
                listAct.add(r);
            }

        } 
         catch(IOException ex)
         {
             
         }
        
        return listAct;
    }
}

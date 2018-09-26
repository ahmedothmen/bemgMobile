package bmg.crud;

import bmg.entities.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Oussaa
 */
public class UserCrud {

    public ArrayList<User> getUserCo(String json) {
        ArrayList<User> listusr = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> actualite = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) actualite.get("utilisateur");
            for (Map<String, Object> obj : list) {
                User u = new User(Integer.parseInt(obj.get("id").toString()),
                        obj.get("nom").toString(),
                        obj.get("prenom").toString(),
                        obj.get("email").toString(),
                        obj.get("username").toString(),
                        obj.get("password").toString(),
                        obj.get("imageurl").toString()
                );
                listusr.add(u);
            }
        } catch (IOException ex) {
        }
        return listusr;
    }

    public void signUp(User u) {
        try {
            
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl("http://localhost/Codenameone/insertUser.php?firstname=" + u.getNom()
                + "&lastname=" + u.getPrenom()
                + "&email=" + u.getEmail()
                + "&password=" + u.getPassword()
                + "&numtel=" + u.getNumtel()
                + "&username=" + u.getLogin()
                + "&daten=" + u.getDaten()
                + "&imageurl=" + u.getUrl());
        req.addResponseListener((NetworkEvent evt1) -> {
            byte[] data = (byte[]) evt1.getMetaData();
            String s = new String(data);
            if (s.equals("success")) {
                Dialog.show("Confirmation", "Inscription effectué avec succées", "Ok", null);
            }
        });
        NetworkManager.getInstance().addToQueue(req);
        } catch (Exception e) {
                                     Dialog.show("Information", "Nom d'utilisateur ou mot de passe incorrect", "Ok", null);

        }
        

    }

    public void UpdateUserProfile(User user) {
        Dialog.show("Confirmation", "Voulez-vous vraiment modifier votre profil?", "Oui", "Annuler");

        ConnectionRequest request = new ConnectionRequest() {

            @Override
            protected void postResponse() {

                Dialog.show("Confirmation", "Modification effectué avec succées", "Ok", null);

            }
        };
        request.setUrl("http://localhost/Codenameone/updateProfileUser.php?firstname=" + user.getNom()
                + "&lastname=" + user.getPrenom()
                + "&username=" + user.getLogin()
                + "&email=" + user.getEmail()
                + "&numtel=" + user.getNumtel()
                + "&password=" + user.getPassword()
                + "&id=" + UserCo.userCo.getId_u());
        NetworkManager.getInstance().addToQueue(request);
    }

    public void removeUser(User u) {
        Dialog.show("Confirmation", "Voulez-vous vraiment supprimer votre compte?", "Oui", "Annuler");
        ConnectionRequest req = new ConnectionRequest() {

            @Override

            protected void postResponse() {
                Dialog.show("Confirmation", "Supprission effectué avec succées", "Ok", null);
            }
        };
        req.setUrl("http://localhost/Codenameone/deleteUser.php?id=" + UserCo.userCo.getId_u());
        NetworkManager.getInstance().addToQueue(req);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmg.entities;

/**
 *
 * @author Daly
 */
public class FavorisPropriete {
    private int id;
    private int idp;
    private int idu;

    public int getId() {
        return id;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public FavorisPropriete(int idp, int idu) {
        this.idp = idp;
        this.idu = idu;
    }

   

    public FavorisPropriete(int id) {
        this.id = id;
    }
    

    @Override
    public String toString() {
        return "FavorisPropriete{" + "id=" + id + ", idp=" + idp + ", idu=" + idu + '}';
    }
    
    
}

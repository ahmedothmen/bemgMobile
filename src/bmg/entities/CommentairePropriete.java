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
public class CommentairePropriete {
    private int id;
    private String contenu;
    private int rating;
    private int idp;
    private int idu ;

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

    public int getId() {
        return id;
    }

  

    public CommentairePropriete(String contenu, int rating, int idp, int idu) {
        this.contenu = contenu;
        this.rating = rating;
        this.idp = idp;
        this.idu = idu;
    }
    
    

    public CommentairePropriete(String contenu, int rating) {
        this.contenu = contenu;
        this.rating = rating;
    }
    

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CommentairePropriete{" + "id=" + id + ", contenu=" + contenu + ", p=" + idp + ", u=" + idu + '}';
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public CommentairePropriete(String contenu, int idp, int idu) {
        this.contenu = contenu;
        this.idp = idp;
        this.idu = idu;
    }




    public CommentairePropriete(String contenu) {
        this.contenu = contenu;
    }



    
    
    
}

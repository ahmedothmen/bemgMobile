/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bmg.entities;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Reclamation {
      int id_rec ;
     String contenu ;
     String type ;
     int leSignale ;
    Date date  ;
    Propriete p;
    User u ;
    int idu,idp;

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }



    public Reclamation(String contenu, String type) {
        this.contenu = contenu;
        this.type = type;
    }

    public Reclamation( String contenu, String type,  Date date) {
       
        this.contenu = contenu;
        this.type = type;
        
        this.date = date;
    }

    public Reclamation(int id_rec, String contenu, String type, Date date, Propriete p, User u) {
        this.id_rec = id_rec;
        this.contenu = contenu;
        this.type = type;
        this.date = date;
        this.p = p;
        this.u = u;
    }
    
    public Reclamation(String contenu, String type, int idu, int idp) {
        this.contenu = contenu;
        this.type = type;
        this.idu = idu;
        this.idp = idp;
    }

    public Reclamation(String contenu, String type,User u, Propriete p) {
        this.contenu = contenu;
        this.type = type;
        
             this.u = u;
        this.p = p;
   
    }
    

    public Propriete getP() {
        return p;
    }

    public void setP(Propriete p) {
        this.p = p;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id_rec=" + id_rec + ", contenu=" + contenu + ", type=" + type + ", leSignale=" + leSignale + ", date=" + date + '}';
    }

    public Reclamation() {
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLeSignale() {
        return leSignale;
    }

    public void setLeSignale(int leSignale) {
        this.leSignale = leSignale;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

   
}

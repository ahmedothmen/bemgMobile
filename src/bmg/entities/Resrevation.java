/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmg.entities;

import bmg.entities.Propriete;
import bmg.entities.User;
import java.util.Date;



/**
 *
 * @author HP
 */
public class Resrevation {

private int id_r;
private  String	dateDebut;
private  String  dateFin;
 private String etat ;
private String nom ;
private String prenom ;
private String titre;

    public Resrevation() {
    }

    public Resrevation(int id_r, String dateDebut, String dateFin, String etat, String nom, String prenom, String titre) {
        this.id_r = id_r;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
        this.nom = nom;
        this.prenom = prenom;
        this.titre = titre;
    }

    public Resrevation(int id_r, String dateDebut, String dateFin, String etat, String nom, String prenom) {
        this.id_r = id_r;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Resrevation(int id_r, String dateDebut, String dateFin, String etat) {
        this.id_r = id_r;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
    }

    public Resrevation(int id_r, String titre) {
        this.id_r = id_r;
        this.titre = titre;
    }

    public Resrevation(String prenom, String titre) {
        this.prenom = prenom;
        this.titre = titre;
    }

    public Resrevation(String nom, String prenom, String titre) {
        this.nom = nom;
        this.prenom = prenom;
        this.titre = titre;
    }

    public Resrevation(String titre) {
        this.titre = titre;
    }

    public Resrevation(int id_r, String prenom, String titre) {
        this.id_r = id_r;
        this.prenom = prenom;
        this.titre = titre;
    }
  
   
    public int getId_r() {
        return id_r;
    }

    public void setId_r(int id_r) {
        this.id_r = id_r;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

   

  

    
    
    @Override
    public String toString() {
        return "id: " + id_r +"date debut"+dateDebut+"date Fin"+dateFin+ "etat: " + etat;
    }

   

   

   

  


   

   


}

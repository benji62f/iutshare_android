package com.example.lefebvrb.iutshare;

/**
 * Created by lefebvrb on 25/03/15.
 */
public class User {
    static int age;
    static int id;
    static String login;
    static String mdp;
    static String nom;
    static String prenom;
    static String type;

    public User(int age, int id, String login, String mdp, String nom, String prenom, String type){
        this.age = age;
        this.id = id;
        this.login = login;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
    }
}

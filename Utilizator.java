package org.poo.cb;

import java.util.ArrayList;

public class Utilizator {
    private String email;
    private String nume;
    private String prenume;
    private String adresa;
    private Portofoliu portofoliu;
    ArrayList<String> prieteni = new ArrayList<>();
    private boolean premium;

    public Utilizator(String email, String prenume, String nume, String adresa, boolean premium) {
        this.email = email;
        this.prenume = prenume;
        this.nume = nume;
        this.adresa = adresa;
        this.portofoliu = new Portofoliu();
        this.premium = premium;
    }


    public String getEmail() {
        return email;
    }
    public Portofoliu getPortofoliu() {
        return portofoliu;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    public boolean getPremium() {
        return this.premium;
    }

    public String toString() {
        String toString = "" + "{\"email\":\"" + this.email
                + "\",\"firstname\":\"" + this.prenume
                + "\",\"lastname\":\"" + this.nume
                + "\",\"address\":\"" + this.adresa
                + "\",\"friends\":[";
        if (this.prieteni.size() == 0) {
            toString += "]}";
        } else if (this.prieteni.size() == 1) {
            toString += "\"" + this.prieteni.get(0) + "\"]}";
        } else {
            int i;
            for (i = 0; i < this.prieteni.size() - 1; i++) {
                toString += "\"" + this.prieteni.get(i) + "\",]}";
            }
            toString += "\"" + this.prieteni.get(i) + "\"]}";
        }
        return toString;
    }
}

package org.poo.cb;

public class UtilizatorBuilder {
    private String email;
    private String nume;
    private String prenume;
    private String adresa;
    private boolean premium;
    public UtilizatorBuilder email(String email) {
        this.email = email;
        return this;
    }
    public UtilizatorBuilder nume(String nume) {
        this.nume = nume;
        return this;
    }
    public UtilizatorBuilder prenume(String prenume) {
        this.prenume = prenume;
        return this;
    }
    public UtilizatorBuilder adresa(String adresa) {
        this.adresa = adresa;
        return this;
    }
    public UtilizatorBuilder premium(boolean premium) {
        this.premium = premium;
        return this;
    }
    public Utilizator build() {
        return new Utilizator(email, prenume, nume, adresa, premium);
    }
}

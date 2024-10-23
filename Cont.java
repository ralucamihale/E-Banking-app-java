package org.poo.cb;

public class Cont {
    private String valuta;
    double suma;

    public Cont(String valuta) {
        this.valuta = valuta;
    }

    public String getValuta() {
        return valuta;
    }

    public double getSuma() {
        return suma;
    }

    public double addMoney(double suma) {
        this.suma += suma;
        return this.suma;
    }

    public double removeMoney(double suma) {
        this.suma -= suma;
        return this.suma;
    }
}

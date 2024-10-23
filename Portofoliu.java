package org.poo.cb;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Portofoliu {
    ArrayList<Cont> conturi = new ArrayList<>();
    ArrayList<Actiuni> actiuni = new ArrayList<>();

    @Override
    public String toString() {
        final DecimalFormat df = new DecimalFormat("0.00");
        String toString1 = "{\"stocks\":[";
        if (this.actiuni.size() == 0) {
            toString1 += "],";
        } else if (this.actiuni.size() == 1) {
            toString1 += "{\"stockName\":\"" + this.actiuni.get(0).getNume() +
                    "\",\"amount\":" + this.actiuni.get(0).valoare +
                    "}],";
        } else {
            int i;
            for (i = 0; i < this.actiuni.size() - 1; i++) {
                toString1 += "{\"stockName\":\"" + this.actiuni.get(i).getNume() +
                        "\",\"amount\":" + this.actiuni.get(i).valoare +
                        "},";
            }
            toString1 += "{\"stockName\":\"" + this.actiuni.get(i).getNume() +
                    "\",\"amount\":" + this.actiuni.get(i).valoare +
                    "}],";
        }

        String toString2 = "\"accounts\":[";
        if (this.conturi.size() == 0) {
            toString1 += "],";
        } else if (this.conturi.size() == 1) {
            toString2 += "{\"currencyName\":\"" + this.conturi.get(0).getValuta() +
                    "\",\"amount\":\"" + df.format(this.conturi.get(0).suma) +
                    "\"}]}";
        } else {
            int i;
            for (i = 0; i < this.conturi.size() - 1; i++) {
                toString2 += "{\"currencyName\":\"" + this.conturi.get(i).getValuta() +
                        "\",\"amount\":\"" + df.format(this.conturi.get(i).suma) +
                        "\"},";
            }
            toString2 += "{\"currencyName\":\"" + this.conturi.get(i).getValuta() +
                    "\",\"amount\":\"" + df.format(this.conturi.get(i).suma) +
                    "\"}]}";
        }
        return toString1 + toString2;
    }
}

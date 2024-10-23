package org.poo.cb;

public class ActiuniFactory {
    public Actiuni create(String company, String noOfStocks) {
        int intStocks = Integer.parseInt(noOfStocks);
        return new Actiuni(company, intStocks);
    }
}

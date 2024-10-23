package org.poo.cb;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Operatii operatii = Operatii.getInstance();
        if(args == null) {
            System.out.println("Running Main");
        } else {
            String rataSchimbare = "src/main/resources/" + args[0];
            String valoriActiuni = "src/main/resources/" + args[1];
            String comenzi = "src/main/resources/" + args[2];

            try (BufferedReader br = new BufferedReader(new FileReader(comenzi))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] comanda = line.split(" ");
                    if (comanda[0].equals("CREATE")) {
                        String[] dateCreate = new String[4];
                        dateCreate[0] = comanda[2];
                        dateCreate[1] = comanda[3];
                        dateCreate[2] = comanda[4];
                        dateCreate[3] = "";
                        for (int i = 5; i < comanda.length - 1; i++) {
                            dateCreate[3] = dateCreate[3] + comanda[i] + " ";
                        }
                        dateCreate[3] = dateCreate[3] + comanda[comanda.length - 1];
                        operatii.createUser(dateCreate);
                    } else if (comanda[0].equals("LIST") && comanda[1].equals("USER")) {
                            operatii.listUser(comanda[2]);
                    } else if (comanda[0].equals("ADD") && comanda[1].equals("FRIEND")) {
                        operatii.addFriend(comanda[2], comanda[3]);
                    } else if (comanda[0].equals("ADD") && comanda[1].equals("ACCOUNT")) {
                        operatii.addAccount(comanda[2], comanda[3]);
                    } else if (comanda[0].equals("ADD") && comanda[1].equals("MONEY")) {
                        operatii.addMoney(comanda[2], comanda[3], comanda[4]);
                    } else if (comanda[0].equals("LIST") && comanda[1].equals("PORTFOLIO")) {
                        operatii.listPortfolio(comanda[2]);
                    } else if (comanda[0].equals("EXCHANGE")) {
                        operatii.exchangeMoney(rataSchimbare, comanda[2], comanda[3], comanda[4], comanda[5]);
                    } else if (comanda[0].equals("TRANSFER")) {
                        operatii.transferMoney(comanda[2], comanda[3], comanda[4], comanda[5]);
                    } else if (comanda[0].equals("RECOMMEND")) {
                        operatii.recommendStocks(valoriActiuni);
                    } else if (comanda[0].equals("BUY") && comanda[1].equals("STOCKS")) {
                        operatii.buyStocks(comanda[2], comanda[3], comanda[4], valoriActiuni);
                    } else if (comanda[0].equals("BUY") && comanda[1].equals("PREMIUM")) {
                        operatii.buyPremium(comanda[2]);
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("FILE NOT FOUND");
            } catch (IOException ex) {
                System.out.println("IOEX");
            }
            operatii.users = new ArrayList<>();
        }
    }
}

package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Operatii {
    private static Operatii operatii;
    ArrayList<Utilizator> users = new ArrayList<>();

    private Operatii() {
    }
    public static Operatii getInstance() {
        if (operatii == null) {
            operatii = new Operatii();
        }
        return operatii;
    }

    public void createUser(String[] date){ // perfect
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(date[0])) {
                System.out.println("User with " + date[0] + " already exists");
                found = true;
            }
        }
        if (!found) {
            Utilizator utilizator = new UtilizatorBuilder()
                    .email(date[0]).prenume(date[1])
                                    .nume(date[2]).adresa(date[3]).premium(false).build();
            users.add(utilizator);
        }
    }
    public void listUser(String email) {
        boolean found = false;
        int k = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                found = true;
                k = i;
            }
        }

        if (!found) {
            System.out.println("User with " + email + " doesn't exist");
        } else {
            System.out.println(users.get(k).toString());
        }
    }
    public void addFriend(String myEmail, String friendEmail) {
        boolean foundEmail = false;
        boolean foundFriend = false;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(myEmail))
                foundEmail = true;
            else if (users.get(i).getEmail().equals(friendEmail))
                foundFriend = true;
        }

        if (!foundEmail) {
            System.out.println("User with " + myEmail + " doesn't exist");
        } else if (!foundFriend) {
            System.out.println("User with " + friendEmail + " doesn't exist");
        } else {
            boolean alreadyFriend = false;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getEmail().equals(myEmail)) {
                    for (int j = 0; j < users.get(i).prieteni.size(); j++) {
                        if (users.get(i).prieteni.get(j).equals(friendEmail)) {
                            alreadyFriend = true;
                            System.out.println("User with " + friendEmail + " is already a friend");
                        }
                    }
                    if (!alreadyFriend) {
                        users.get(i).prieteni.add(friendEmail);
                    }
                } else if (users.get(i).getEmail().equals(friendEmail) && alreadyFriend == false) {
                    users.get(i).prieteni.add(myEmail);
                }
            }
        }
    }

    public void addAccount(String email, String currency) {
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                for (int j = 0; j < users.get(i).getPortofoliu().conturi.size(); j++) {
                    if (users.get(i).getPortofoliu().conturi.get(j).getValuta().equals(currency)) {
                        found = true;
                        System.out.println("Account in currency " + currency + " already exists for user");
                    }
                }
                if (!found) {
                    users.get(i).getPortofoliu().conturi.add(new Cont(currency));
                }
            }
        }
    }
    public void addMoney(String email, String currency, String sum) {
        double doubleSum = Double.parseDouble(sum);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                for (int j = 0; j < users.get(i).getPortofoliu().conturi.size(); j++) {
                    if (users.get(i).getPortofoliu().conturi.get(j).getValuta().equals(currency)) {
                        users.get(i).getPortofoliu().conturi.get(j).addMoney(doubleSum);
                    }
                }
            }
        }
    }
    public void listPortfolio(String email) {
        boolean found = false;
//        System.out.println(users.get(0).getEmail() + " " + email + " " + users.get(0).getEmail().equals(email));
        int k = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                found = true;
                k = i;
            }
        }

        if (!found) {
            System.out.println("User with " + email + " doesn't exist");
        } else {
            System.out.println(users.get(k).getPortofoliu().toString());
        }
    }
    public void exchangeMoney(String file, String email, String source, String dest, String amount) {
        double exchangeRate = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] currency = line.split(",");
            int i;
            for (i = 0; i < currency.length && !currency[i].equals(source); i++) {
            }
            while ((line = br.readLine()) != null) {
                String[] exchange = line.split(",");
                if (exchange[0].equals(dest)) {
                    exchangeRate = Double.parseDouble(exchange[i]);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        } catch (IOException ex) {
            System.out.println("IOEX");
        }

        boolean transferPosibil = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                double transfer = Double.parseDouble(amount) * exchangeRate;
                for (int j = 0; j < users.get(i).getPortofoliu().conturi.size(); j++) {
                    if (users.get(i).getPortofoliu().conturi.get(j).getValuta().equals(source)) {
                        if (users.get(i).getPortofoliu().conturi.get(j).getSuma() - transfer < 0) {
                            System.out.println("Insufficient amount in account " + source + " for exchange");
                        } else if (transfer > users.get(i).getPortofoliu().conturi.get(j).getSuma()/2 && users.get(i).getPremium() == false) {
                            transferPosibil = true;
                            transfer = transfer + transfer/100;
                            users.get(i).getPortofoliu().conturi.get(j).removeMoney(transfer);
                        } else {
                            transferPosibil = true;
                            users.get(i).getPortofoliu().conturi.get(j).removeMoney(transfer);
                        }
                    }
                }
                for (int j = 0; j < users.get(i).getPortofoliu().conturi.size() && transferPosibil; j++) {
                    if (users.get(i).getPortofoliu().conturi.get(j).getValuta().equals(dest)) {
                        users.get(i).getPortofoliu().conturi.get(j).addMoney(Double.parseDouble(amount));
                    }
                }
            }
        }
    }
    public void transferMoney(String myEmail, String friendEmail, String currency, String amount) {
        double suma = Double.parseDouble(amount);
        int i;
        for (i = 0; i < users.size() && !users.get(i).getEmail().equals(myEmail); i++) {
        }
        int j;
        for (j = 0; j < users.get(i).getPortofoliu().conturi.size() &&
                !users.get(i).getPortofoliu().conturi.get(j).getValuta().equals(currency); j++) {
        }
        if (users.get(i).getPortofoliu().conturi.get(j).getSuma() < suma) {
            System.out.println("Insufficient amount in account " + currency + " for transfer");
        } else {
            int k;
            boolean found = false;
            for (k = 0; k < users.get(i).prieteni.size(); k++) {
                if (users.get(i).prieteni.get(k).equals(friendEmail))
                    found = true;
            }
            if (!found) {
                System.out.println("You are not allowed to transfer money to " + friendEmail);
            } else {
                for (k = 0; k < users.size() && !users.get(k).getEmail().equals(friendEmail); k++) {
                }
                int t;
                for (t = 0; t < users.get(k).getPortofoliu().conturi.size() &&
                        !users.get(k).getPortofoliu().conturi.get(t).getValuta().equals(currency); j++) {
                }
                users.get(i).getPortofoliu().conturi.get(j).removeMoney(suma);
                users.get(k).getPortofoliu().conturi.get(t).addMoney(suma);
            }
        }
    }
    public void recommendStocks(String file) {
        ArrayList<String> stocksRec = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] actiune = line.split(",");
                double sma5 = 0;
                double sma10 = 0;
                for (int i = 1; i <= 5; i++) {
                    sma10 += Double.parseDouble(actiune[i]);
                }
                for (int i = 6; i < actiune.length; i++) {
                    sma10 += Double.parseDouble(actiune[i]);
                    sma5 += Double.parseDouble(actiune[i]);
                }
                sma10 /= 10;
                sma5 /= 5;
                if (sma5 > sma10) {
                    stocksRec.add(actiune[0]);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        } catch (IOException ex) {
            System.out.println("IOEX");
        }
        System.out.print("{\"stocksToBuy\":[");
        if (stocksRec.size() == 0) {
            System.out.print("]}");
        } else if (stocksRec.size() == 1) {
            System.out.print("\"" + stocksRec.get(0) + "\"]}");
        } else {
            int i;
            for (i = 0; i < stocksRec.size() - 1; i++) {
                System.out.print("\"" + stocksRec.get(i) + "\",");
            }
            System.out.print("\"" + stocksRec.get(i) + "\"]}");
        }
        System.out.println();
    }
    public ArrayList<String> getRecommendedStocks(String file) {
        ArrayList<String> stocksRec = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] actiune = line.split(",");
                double sma5 = 0;
                double sma10 = 0;
                for (int i = 1; i <= 5; i++) {
                    sma10 += Double.parseDouble(actiune[i]);
                }
                for (int i = 6; i < actiune.length; i++) {
                    sma10 += Double.parseDouble(actiune[i]);
                    sma5 += Double.parseDouble(actiune[i]);
                }
                sma10 /= 10;
                sma5 /= 5;
                if (sma5 > sma10) {
                    stocksRec.add(actiune[0]);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        } catch (IOException ex) {
            System.out.println("IOEX");
        }
        return stocksRec;
    }
    public void buyStocks(String email, String company, String noOfStocks, String file) {
        int i;
        for (i = 0; i < users.size() && !users.get(i).getEmail().equals(email); i++);
        int j;
        for (j = 0; j < users.get(i).getPortofoliu().conturi.size() &&
                !users.get(i).getPortofoliu().conturi.get(j).getValuta().equals("USD"); j++);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] actiune = line.split(",");
                if (actiune[0].equals(company)) {
                    int lastElem = actiune.length - 1;
                    double pret = Integer.parseInt(noOfStocks) * Double.parseDouble(actiune[lastElem]);
                    if (users.get(i).getPortofoliu().conturi.get(j).suma < pret) {
                        System.out.println("Insufficient amount in account for buying stock");
                    } else {
                        if (users.get(i).getPremium() == false) {
                            ActiuniFactory factory = new ActiuniFactory();
                            users.get(i).getPortofoliu().actiuni.add(factory.create(company, noOfStocks));
                            users.get(i).getPortofoliu().conturi.get(j).removeMoney(pret);
                        } else {
                            ArrayList<String> stocksRec = getRecommendedStocks(file);
                            boolean foundStock = false;
                            int k;
                            for (k = 0; k < stocksRec.size(); k++) {
                                if (stocksRec.equals(company)) {
                                    foundStock = true;
                                    ActiuniFactory factory = new ActiuniFactory();
                                    users.get(i).getPortofoliu().actiuni.add(factory.create(company, noOfStocks));
                                    users.get(i).getPortofoliu().conturi.get(j).removeMoney(pret - pret * 5/100);
                                }
                            }
                            if (!foundStock) {
                                ActiuniFactory factory = new ActiuniFactory();
                                users.get(i).getPortofoliu().actiuni.add(factory.create(company, noOfStocks));
                                users.get(i).getPortofoliu().conturi.get(j).removeMoney(pret);
                            }
                        }
                    }
                }
            }
            } catch (FileNotFoundException ex) {
                System.out.println("FILE NOT FOUND");
            } catch (IOException ex) {
                System.out.println("IOEX");
            }
    }
    public void buyPremium(String email) {
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email))
                found = true;
        }
        if (!found) {
            System.out.println("User with " + email + " doesn't exist");
        } else {
            int i;
            for (i = 0; i < users.size() && !users.get(i).getEmail().equals(email); i++);
            int j;
            for (j = 0; j < users.get(i).getPortofoliu().conturi.size() &&
                    !users.get(i).getPortofoliu().conturi.get(j).getValuta().equals("USD"); j++);
            if (users.get(i).getPortofoliu().conturi.get(j).getSuma() < 100) {
                System.out.println("Insufficient amount in account for buying premium option");
            } else {
                users.get(i).getPortofoliu().conturi.get(j).removeMoney(100);
                users.get(i).setPremium(true);
            }
        }
    }
}

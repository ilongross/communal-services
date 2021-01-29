package communal_services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PaymentData {
    private List<PayCase> payments = new LinkedList<>();
    private Map<Integer, Double> paymentsMap = new TreeMap<>();

    public PaymentData(String fileName) {
        fillList(fileName);
        fillMapList();
    }

    public void makeCalculation () {
        try {
            AccountsData ad = new AccountsData();
            Map<Integer, AccountCase> mapListAccounts = ad.getMapList();
            for (PayCase p : this.payments) {
                for (Integer key : mapListAccounts.keySet()) {
                    if(p.getId() == key) {
                        mapListAccounts.get(key).setBalance(p.getBalance());
                    }
                }
            }
            BufferedWriter brw = IOMethods.rewrite(Distribution.PATH + "/accounts.txt");
            for (Integer id : mapListAccounts.keySet()) {
                StringBuilder accLine = new StringBuilder();
                accLine.append((String.format("%08d", mapListAccounts.get(id).getId())) + "/");
                accLine.append(mapListAccounts.get(id).getAddress() + "/");
                accLine.append(mapListAccounts.get(id).getName() + "/");
                accLine.append(mapListAccounts.get(id).getLastname() + "/");
                accLine.append(mapListAccounts.get(id).getPatronymic() + "/");
                accLine.append(mapListAccounts.get(id).getSquare() + "/");
                accLine.append(mapListAccounts.get(id).getBalance());
                brw.write(accLine + "\n");
            }
            brw.close();

        } catch (FileNotFoundException e) {
            System.out.println("File with accounts isn't found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error i/o for change list of accounts");
            e.printStackTrace();
        }

    }

    private void fillList (String fn) {
        try {
            BufferedReader br = IOMethods.read(fn);
            String accLine;
            while((accLine = br.readLine()) != null) {
                PayCase pay = new PayCase(accLine);
                this.payments.add(pay);
            }
        } catch (IOException e) {
            System.out.println("Error of read payments");
            e.printStackTrace();
        }
    }
    private void fillMapList () {
        for (PayCase pay : this.payments) {
            int key = pay.getId();
            this.paymentsMap.put(pay.getId(), pay.getBalance());
        }
    }

    public List<PayCase> getPayments() {
        return payments;
    }
    public Map<Integer, Double> getPaymentsMap() {
        return paymentsMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Payments data:\n");
        for (PayCase p : payments) {
            sb.append(p);
            sb.append("\n");
        }
        return String.valueOf(sb);
    }
}

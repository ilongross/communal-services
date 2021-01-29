package communal_services;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountsData {
    private List<AccountCase> list = new ArrayList<>();
    private List<AccountCase> debtors = new ArrayList<>();
    private Map<Integer, AccountCase> mapList = new HashMap<>();

    public AccountsData () throws FileNotFoundException {
        fillLists();
        fillMapList();
    }

    public void addNewAccount (String address, String name, String lastname,
                               String patronymic, double square) throws IOException {
        List<AccountCase> list = new AccountsData().getList();
        AccountCase newAcc = new AccountCase(address, name, lastname, patronymic, square);
        newAcc.setId(list.size() + 1);
        list.add(newAcc);
        BufferedWriter bw = IOMethods.rewrite(Distribution.PATH + "/accounts.txt");
        for (AccountCase acc : list) {
            StringBuilder accLine = new StringBuilder();
            accLine.append((String.format("%08d", acc.getId())) + "/");
            accLine.append(acc.getAddress() + "/");
            accLine.append(acc.getName() + "/");
            accLine.append(acc.getLastname() + "/");
            accLine.append(acc.getPatronymic() + "/");
            accLine.append(acc.getSquare() + "/");
            accLine.append("0.00");
            bw.write(accLine + "\n");
        }
        bw.close();
    }

    private void fillLists () {
        try {
            BufferedReader br = IOMethods.read(Distribution.PATH + "/accounts.txt");
            String accLine;
            while((accLine = br.readLine()) != null) {
                AccountCase acc = new AccountCase(accLine);
                this.list.add(acc);
                if(acc.getBalance() < 0)
                    this.debtors.add(acc);
            }
            br.close();

            File debtorsFile = new File(Distribution.PATH + "/" +
                    Distribution.DATE_FOR_FILENAME + "/debtors.txt");

            BufferedWriter brw = IOMethods.rewrite(debtorsFile.getPath());
            for (AccountCase debtor : this.debtors) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%08d", debtor.getId()) + "/");
                sb.append(String.format("%.2f", debtor.getBalance()) + "\n");
                brw.write(String.valueOf(sb));
            }
            brw.close();
        } catch (IOException e) {
            System.out.println("Error i/o");
            e.printStackTrace();
        }
    }
    private void fillMapList () {
        for (AccountCase acc : this.list) {
            this.mapList.put(acc.getId(), acc);
        }
    }
    public Map<Integer, AccountCase> getMapList() {
        return mapList;
    }
    public List<AccountCase> getList() {
        return list;
    }
    public List<AccountCase> getDebtors() {
        return debtors;
    }

    public String toStringDebtors () {
        StringBuilder result = new StringBuilder();
        result.append("Debtors:\n");
        for (AccountCase debtor : this.debtors) {
            result.append(String.format("%08d", debtor.getId()) + ": " +
                    String.format("%.2f", debtor.getBalance())
                            .replaceAll(",","."));
            result.append("\n");
        }
        return String.valueOf(result);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Citizens:\n");
        for (AccountCase acc : list) {
            result.append(acc);
            result.append("\n");
        }
        return String.valueOf(result);
    }
}

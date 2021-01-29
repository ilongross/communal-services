package communal_services;

import java.io.*;
import java.util.*;

public class MetersData {
    private List<MeterCase> metersList = new ArrayList<>();
    private Map<Integer, MeterCase> meterMapList = new HashMap<>();
    private Map<Integer, String> metersStorage = new TreeMap<>();

    public MetersData(Tariffs t, String fileName) {
        fillList(fileName);
        fillMapList();
    }

    public void makeCalculation (Tariffs t) {
        try {
            AccountsData ad = new AccountsData();
            Map<Integer, AccountCase> mapListAccounts = ad.getMapList();

            for (Integer id : mapListAccounts.keySet()) {
                mapListAccounts.get(id).setBalance(-(
                        t.getMaintenance() * mapListAccounts.get(id).getSquare() +
                                t.getGarbage() + t.getHeating()));
            }
            for (MeterCase meter : this.metersList) {
                double id = meter.getId();
                for (Integer key : mapListAccounts.keySet()) {
                    if (id == key) {
                        mapListAccounts.get(key)
                                .setBalance(-t.getTariffValue(meter.getService()) * meter.getValue());
                        fillMetersStorage(t, mapListAccounts);
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
                String balance = String.format("%.2f", mapListAccounts.get(id).getBalance());
                accLine.append(balance.replace(",", "."));
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

    private void fillMetersStorage (Tariffs tariff, Map<Integer, AccountCase> mapAccounts) {
        Map<Integer, String> result = new TreeMap<>();

        for (MeterCase meter : this.metersList) {
            int id = meter.getId();
            if(result.containsKey(id)){
                StringBuilder sb = new StringBuilder();
                double value = tariff.getTariffValue(meter.getService()) * meter.getValue();
                sb.append(tariff.getUniSymbol(meter.getService()) + "=");
                sb.append(String.format("%.2f", value));
                String subJoinService = result.get(id) + "/" + sb;
                result.put(id, subJoinService);
            }
            else {
                StringBuilder sb = new StringBuilder();
                double value = tariff.getTariffValue(meter.getService()) * meter.getValue();
                sb.append("ht=" + String.format("%.2f", tariff.getHeating()) + "/");
                sb.append("gb=" + String.format("%.2f", tariff.getGarbage()) + "/");
                sb.append("mt=" + String.format("%.2f", tariff.getMaintenance()
                        * mapAccounts.get(id).getSquare()) + "/");
                sb.append(tariff.getUniSymbol(meter.getService()) + "=" +
                        String.format("%.2f", value));
                result.put(id, String.valueOf(sb));
            }
        }
        File storageMetersFile = new File(Distribution.PATH + "/" +
                Distribution.DATE_FOR_FILENAME + "/" + "storage_meters_results.txt");
        try {
            BufferedWriter brw = IOMethods.rewrite(storageMetersFile.getPath());
            for (Integer key : result.keySet()) {
                this.metersStorage.put(key, result.get(key));
                brw.write(String.format("%08d", key) + "/" + result.get(key) + "\n");
            }
            brw.close();
        } catch (IOException e) {
            System.out.println("Error i/o for storage of meters results");
            e.printStackTrace();
        }
    }

    private void fillList(String fn) {
        try {
            BufferedReader br = IOMethods.read(fn);
            String accLine;
            while((accLine = br.readLine()) != null) {
                MeterCase meter = new MeterCase(accLine);
                metersList.add(meter);
            }
        } catch (IOException e) {
            System.out.println("Error of read meters data");
            e.printStackTrace();
        }
    }
    private void fillMapList () {
        for (MeterCase meter : this.metersList) {
            this.meterMapList.put(meter.getId(), meter);
        }
    }
    public List<MeterCase> getMetersList() {
        return metersList;
    }
    public Map<Integer, MeterCase> getMeterMapList() {
        return meterMapList;
    }
    public Map<Integer, String> getMetersStorage() {
        return metersStorage;
    }

    public String toStringMetersStorage () {
        StringBuilder sb = new StringBuilder();
        sb.append("Accrued:\n");
        for (Integer key  : this.metersStorage.keySet()) {
            sb.append(String.format("%08d", key) + ": ");
            sb.append(this.metersStorage.get(key).replaceAll(",", "."));
            sb.append("\n");
        }
        return String.valueOf(sb);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Meters data:\n");
        for (MeterCase m : metersList) {
            sb.append(m);
            sb.append("\n");
        }
        return String.valueOf(sb);
    }
}

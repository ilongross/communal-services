package communal_services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Report {
    String date = Distribution.DATE_FOR_OUTPUT;
    private Map<Integer, AccountCase> accountsList = new TreeMap<>();
    private Map<Integer, String> metersStorage = new TreeMap<>();
    private Map<Integer, Double> paymentsMap = new TreeMap<>();

    public Report(Map<Integer, AccountCase> accounts,
                  Map<Integer, String> metersStorage,
                  Map<Integer, Double> payments) {
        this.accountsList.putAll(accounts);
        this.metersStorage.putAll(metersStorage);
        this.paymentsMap.putAll(payments);
    }

    public void doReport () {
        for (Integer id : accountsList.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("---------------------------------------------------------------\n");
            sb.append(String.format("id: %08d\n", id));
            sb.append(String.format("%-15s%-20s%-10s%-10s%-10s\n", "Date", "Service", "Accrued", "Paid", "Debt"));
            sb.append(String.format("%-15s%-20s%-10s%-10s%-10s\n", this.date, "", "", "", ""));

            String[] serviceStorageArray = metersStorage.get(id).split("/");
            double totalPayment = 0;
            for (int i = 0; i < serviceStorageArray.length; i++) {
                String[] serviceValue = serviceStorageArray[i].split("=");
                double value = Double.parseDouble(serviceValue[1].replaceAll(",", "."));
                sb.append(String.format("%-15s%-20s%-10.2f%-10s%-10s\n", "",
                        Tariffs.getRusNameTariff(serviceValue[0]),
                        value, "", ""));
                totalPayment += Double.parseDouble(serviceValue[1].replaceAll(",", "."));
            }
            double paid = paymentsMap.get(id);
            double debt = totalPayment - paid;
            sb.append(String.format("%-15s%-20s%-10.2f%-10.2f%-10.2f\n", "TOTAL", "", totalPayment, paid, debt));
            sb.append("---------------------------------------------------------------\n");

            File reportFile = new File(Distribution.PATH + "/" +
                    Distribution.DATE_FOR_FILENAME + "/report.txt");
            try {
                BufferedWriter brw = IOMethods.write(reportFile.getPath());
                brw.write(String.valueOf(sb));
                brw.close();
            } catch (IOException e) {
                System.out.println("Error i/o to report.txt");
                e.printStackTrace();
            }
        }
    }
}
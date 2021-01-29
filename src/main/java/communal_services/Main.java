package communal_services;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String pathMeters = "";
        String pathPayments = "";

        Scanner scan = new Scanner(System.in);
        System.out.println("\n-- COMMUNAL SERVICES --");
        System.out.print("Enter file with meters data: ");
        pathMeters = scan.nextLine();
        System.out.print("Enter file with payments: ");
        pathPayments = scan.nextLine();

        Tariffs tariff = new Tariffs();
        Distribution.copyFile(Distribution.PATH + "/tariffs.txt", "tariffs.txt");
        try {
            // Create list of current accounts data
            AccountsData citizens = new AccountsData();
            // Create list of meters for posting
            MetersData md = new MetersData(tariff, pathMeters);
            // Posting meters and create debt for accounts
            md.makeCalculation(tariff);

            // Create list of payments for posting
            PaymentData pd = new PaymentData(pathPayments);
            // Posting payments and change accounts balances
            pd.makeCalculation();

            Distribution.copyFile(Distribution.PATH + "/accounts.txt", "accounts.txt");
            Distribution.copyFile(pathMeters, "meters_data.txt");
            Distribution.copyFile(pathPayments, "payments.txt");

            AccountsData ad = new AccountsData();
            // Create Report for all accounts
            Report report = new Report(citizens.getMapList(), md.getMetersStorage(), pd.getPaymentsMap());
            report.doReport();
            System.out.println("Calculate completed.");
            System.out.println("-- Process finished --\n");
        } catch (FileNotFoundException e) {
            System.out.println("Not found file with list of accounts!");
            e.printStackTrace();
        }
    }
}
package communal_services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tariffs {
    private double electricity;
    private double coldWater;
    private double hotWater;
    private double drainage;
    private double heating;
    private double garbage;
    private double maintenance;


    private Map<String, Double> tariffs = new LinkedHashMap<>();

    public Tariffs() {
        getTariffsList();
        tariffValueSwitch();
    }

    public Tariffs(double electricity, double coldWater, double hotWater, double drainage,
                   double heating, double garbage, double maintenance) {
        this.electricity = electricity;
        this.coldWater = coldWater;
        this.hotWater = hotWater;
        this.drainage = drainage;
        this.heating = heating;
        this.garbage = garbage;
        this.maintenance = maintenance;
    }

    private void getTariffsList () {
        try {
            BufferedReader br = IOMethods.read(Distribution.PATH + "/tariffs.txt");
            String s;
            while((s = br.readLine()) != null) {
                String[] tariffData = s.split("=");
                String serviceName = tariffData[0];
                double serviceValue = Double.parseDouble(tariffData[1]);
                this.tariffs.put(serviceName, serviceValue);
            }
        } catch (IOException e) {
            System.out.println("Error file i/o");
            e.printStackTrace();
        }
    }

    public void tariffValueSwitch () {
        for (String key : this.tariffs.keySet()){
            switch (key) {
                case "el":
                    this.electricity = this.tariffs.get(key);
                    break;
                case "cw":
                    this.coldWater = this.tariffs.get(key);
                    break;
                case "hw":
                    this.hotWater = this.tariffs.get(key);
                    break;
                case "dr":
                    this.drainage = this.tariffs.get(key);
                    break;
                case "ht":
                    this.heating = this.tariffs.get(key);
                    break;
                case "gb":
                    this.garbage = this.tariffs.get(key);
                    break;
                case "mt":
                    this.maintenance = this.tariffs.get(key);
                    break;
                default:
                    System.out.println("Tariff not found.");
            }
        }
    }

    public double getTariffValue(String serviceRus) {
        double result = 0.0;
        switch(serviceRus) {
            case "Электроэнергия":
                result = getElectricity();
                break;
            case "ХВС":
                result = getColdWater();
                break;
            case "ГВС":
                result = getHotWater();
                break;
            case "Водоотведение":
                result = getDrainage();
                break;
            case "Отопление":
                result = getHeating();
                break;
            case "Вывоз мусора":
                result = getGarbage();
                break;
            case "Капитальный ремонт":
                result = getMaintenance();
                break;
        }
        return result;
    }

    public String getUniSymbol (String serviceRus) {
        String result = "";
        switch(serviceRus) {
            case "Электроэнергия":
                result = "el";
                break;
            case "ХВС":
                result = "cw";
                break;
            case "ГВС":
                result = "hw";
                break;
            case "Водоотведение":
                result = "dr";
                break;
            case "Отопление":
                result = "ht";
                break;
            case "Вывоз мусора":
                result = "gb";
                break;
            case "Капитальный ремонт":
                result = "mt";
                break;
        }
        return result;
    }

    public static String getRusNameTariff (String uniSymbol) {
        String result = "";
        switch(uniSymbol) {
            case "el":
                result = "Электроэнергия";
                break;
            case "cw":
                result = "ХВС";
                break;
            case "hw":
                result = "ГВС";
                break;
            case "dr":
                result = "Водоотведение";
                break;
            case "ht":
                result = "Отопление";
                break;
            case "gb":
                result = "Вывоз мусора";
                break;
            case "mt":
                result = "Капитальный ремонт";
                break;
        }
        return result;
    }

    private enum services {
        ELECTRICITY,
        COLD_WATER,
        HOT_WATER,
        DRAINAGE,
        HEATING,
        GARBAGE,
        MAINTENANCE
    }

    @Override
    public String toString() {
        return "Current tariffs:\n" +
                "Electricity = " + electricity + " rub/kVt\n" +
                "Cold water = " + coldWater + " rub/m3\n" +
                "Hot water = " + hotWater + " rub/m3\n" +
                "Drainage = " + drainage + " rub/m3\n" +
                "Heating = " + heating + " rub\n" +
                "Garbage = " + garbage + " rub\n" +
                "Maintenance = " + maintenance + " rub\n";
    }

    public double getElectricity() {
        return electricity;
    }
    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }
    public double getColdWater() {
        return coldWater;
    }
    public void setColdWater(double coldWater) {
        this.coldWater = coldWater;
    }
    public double getHotWater() {
        return hotWater;
    }
    public void setHotWater(double hotWater) {
        this.hotWater = hotWater;
    }
    public double getDrainage() {
        return drainage;
    }
    public void setDrainage(double drainage) {
        this.drainage = drainage;
    }
    public double getHeating() {
        return heating;
    }
    public void setHeating(double heating) {
        this.heating = heating;
    }
    public double getGarbage() {
        return garbage;
    }
    public void setGarbage(double garbage) {
        this.garbage = garbage;
    }
    public double getMaintenance() {
        return maintenance;
    }
    public void setMaintenance(double maintenance) {
        this.maintenance = maintenance;
    }
}

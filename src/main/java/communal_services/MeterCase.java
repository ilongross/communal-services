package communal_services;

public class MeterCase {
    private int id;
    private String service;
    private double value;

    public MeterCase (String meterLine) {
        initMeterCase(meterLine);
    }

    private void initMeterCase (String line) {
        String meterLine = line.replaceAll("\\s+", "/");
        String[] caseData = meterLine.split("/");
        this.id = Integer.parseInt(caseData[0].replaceAll("^0+(?=.)", ""));
        this.service = caseData[1];
        if(service.equals("Водоотведение"))
            this.value = Double.parseDouble(caseData[2]);
        else
            this.value = Double.parseDouble(caseData[3]);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%08d", id) + ": " + service + " = " + value;
    }
}

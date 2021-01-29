package communal_services;

public class PayCase {
    private int id;
    private double balance;

    public PayCase(String payLine) {
        initPayCase(payLine);
    }

    private void initPayCase (String line) {
        String payLine = line.replaceAll("\\s+", "/");
        String[] caseData = payLine.split("/");
        this.id = Integer.parseInt(caseData[0].replaceAll("^0+(?=.)", ""));
        this.balance = Double.parseDouble(caseData[1]);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("%08d", id) +
                ": " + balance;
    }
}

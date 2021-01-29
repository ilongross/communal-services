package communal_services;

public class AccountCase {
    private int id;
    private String address;
    private String name;
    private String lastname;
    private String patronymic;
    private double square;
    private double balance;

    public AccountCase(String accLine) {
        initAcc(accLine);
    }

    public AccountCase(String address, String name, String lastname,
                       String patronymic, double square) {
        this.id = 1;
        this.address = address;
        this.name = name;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.square = square;
    }

    public AccountCase(int id, double balance) {
        this.id = id;
        this.balance += balance;
    }

    private void initAcc(String accLine) {
        String[] accData = accLine.split("/");
        this.id = Integer.parseInt(accData[0].replaceAll("^0+(?=.)", ""));
        this.address = accData[1];
        this.name = accData[2];
        this.lastname = accData[3];
        this.patronymic = accData[4];
        this.square = Double.parseDouble(accData[5]);
        this.balance = Double.parseDouble(accData[6]);
    }

    @Override
    public String toString() {
        return  "id=" + String.format("%08d", id) +
                " address: " + address +
                "; " + lastname +
                " " + name +
                " " + patronymic +
                "; square=" + square +
                "; balance=" + String.format("%.2f", balance)
                .replaceAll(",", ".");
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 47 * result - address.hashCode();
        result = 47 * result + id;
        return result;
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof AccountCase &&
                address.equals(((AccountCase)o).address) &&
                id ==((AccountCase)o).id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getPatronymic() {
        return patronymic;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    public double getSquare() {
        return square;
    }
    public void setSquare(double square) {
        this.square = square;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance += balance;
    }
}

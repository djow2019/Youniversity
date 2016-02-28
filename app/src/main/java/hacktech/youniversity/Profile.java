package hacktech.youniversity;

/**
 * Created by Derek on 2/27/2016.
 */
public class Profile {

    public static Profile profile;

    private double balance;

    private int totalOccupancy;
    private int maxOccupancy;

    // value ranging from -100 to 100
    private int reputation;

    private String universityName;
    private String userName;

    public Profile(String userName, String universityName) {

        this.universityName = universityName;
        this.userName = userName;
        balance = 500000;
        totalOccupancy = 0;
        maxOccupancy = 0;
        reputation = 0;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double cost) {
        if (cost > balance)
            return false;
        balance -= cost;
        return true;
    }

    public void deposit(double amount) {
        balance += amount;
    }

}

package hacktech.youniversity;

import java.util.ArrayList;

import hacktech.youniversity.buildings.Building;

/**
 * Created by Derek on 2/27/2016.
 */
public class Profile {

    private double balance;

    private int totalOccupancy;
    private int maxOccupancy;

    // value ranging from -1 to 1
    private double reputation;

    private String universityName;
    private String userName;

    private ArrayList<Building> ownedBuildings = new ArrayList<>();

    private long lastLogOff;

    /* income per in game year  where
    * Income = numStudents *tuition - expenses
    */
    private int tuition;

    public Profile(String userName, String universityName) {

        this.universityName = universityName;
        this.userName = userName;
        balance = 1000000;
        totalOccupancy = 0;
        maxOccupancy = 0;
        reputation = 0;
        this.tuition = 25000;
    }

    public String toString() {
        return "Name: " + getUserName() +
                "\nUniversity: " + getUniversityName() +
                "\nReputation: " + (int) (getReputation() * 100) +
                "\nBalance: $" + getBalance() +
                "\nTuition: $" + getTuition() +
                "\nIncome: $" + (int) getIncome() +
                "\nOccupancy: " + getTotalOccupancy() + "/" + getMaxOccupancy();


    }

    public void addStudentSpotsAvailable(int seats) {
        maxOccupancy += seats;
    }

    public void removeStudentSpotsAvailable(int seats) {
        maxOccupancy -= seats;
    }

    /* Each time it is called, 2% of available seats of students are called
    * Plus an additional bonus for reputation up to 2%
    */
    public void generateStudents() {
        int num = (int) (.02 * (maxOccupancy - totalOccupancy) +
                reputation * (maxOccupancy - totalOccupancy));
        totalOccupancy += num;
    }

    public String getUserName() {
        return userName;
    }

    public int getBalance() {
        return (int) balance;
    }

    public double getReputation() {
        return reputation;
    }

    public double getIncome() {
        return getTotalOccupancy() * tuition - getExpenses();
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public int getTotalOccupancy() {
        return totalOccupancy;
    }

    public int getTuition() {
        return tuition;
    }

    public String getUniversityName() {
        return universityName;
    }

    public boolean withdraw(double cost) {
        if (cost > balance)
            return false;
        balance -= cost;
        return true;
    }

    /* 1% of the cost of each building each year */
    public double getExpenses() {

        double cost = 0;

        for (Building building : ownedBuildings) {
            cost += (0.01) * building.getPrice();
        }

        return cost;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void addBuilding(Building building) {
        ownedBuildings.add(building);
    }

    public void removeBuilding(Building building) {
        ownedBuildings.remove(building);
    }

    public ArrayList<Building> getBuildings() {
        return ownedBuildings;
    }

}

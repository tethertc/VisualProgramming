package travelplanner;

public class Trip {
    private String destination;
    private double budget;

    public Trip(String destination, double budget) {
        this.destination = destination;
        this.budget = budget;
    }
    public String getDestination() { return destination; }
    public double getBudget() { return budget; }
}
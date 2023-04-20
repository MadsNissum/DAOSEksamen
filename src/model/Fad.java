package model;

public class Fad {

    int fadID;
    String fadType;
    double kapacitetILiter;
    String oprindelse;

    public Fad(int fadID, String fadType, double kapacitetILiter, String oprindelse) {
        this.fadID = fadID;
        this.fadType = fadType;
        this.kapacitetILiter = kapacitetILiter;
        this.oprindelse = oprindelse;
    }

    public int getFadID() {
        return fadID;
    }

    public String toString() {
        return "ID: " + fadID + " " + fadType + " " + oprindelse;
    }
}

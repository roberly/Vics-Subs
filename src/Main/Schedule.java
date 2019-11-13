package Main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Schedule
{
    private final StringProperty sunday;
    private final StringProperty monday;
    private final StringProperty tuesday;
    private final StringProperty wednesday;
    private final StringProperty thursday;
    private final StringProperty friday;
    private final StringProperty saturday;

    public Schedule(String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday)
    {
        this.sunday = new SimpleStringProperty(sunday);
        this.monday = new SimpleStringProperty(monday);
        this.tuesday = new SimpleStringProperty(tuesday);
        this.wednesday = new SimpleStringProperty(wednesday);
        this.thursday = new SimpleStringProperty(thursday);
        this.friday = new SimpleStringProperty(friday);
        this.saturday = new SimpleStringProperty(saturday);
    }

    public String getSunday() {
        return sunday.get();
    }

    public StringProperty sundayProperty() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday.set(sunday);
    }

    public String getMonday() {
        return monday.get();
    }

    public StringProperty mondayProperty() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday.set(monday);
    }

    public String getTuesday() {
        return tuesday.get();
    }

    public StringProperty tuesdayProperty() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday.set(tuesday);
    }

    public String getWednesday() {
        return wednesday.get();
    }

    public StringProperty wednesdayProperty() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday.set(wednesday);
    }

    public String getThursday() {
        return thursday.get();
    }

    public StringProperty thursdayProperty() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday.set(thursday);
    }

    public String getFriday() {
        return friday.get();
    }

    public StringProperty fridayProperty() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday.set(friday);
    }

    public String getSaturday() {
        return saturday.get();
    }

    public StringProperty saturdayProperty() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday.set(saturday);
    }



}

package planificadordeturnos.models;

import java.util.Date;
import java.util.List;

public class Shift {

    private String id;
    private String date;
    private String place;
    private String startHour;
    private String endHour;
    private List<User> leadList;
    private String assignedLead;

    public Shift() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public List<User> getLeadList() {
        return leadList;
    }

    public void setLeadList(List<User> leadList) {
        this.leadList = leadList;
    }

    public String getAssignedLead() {
        return assignedLead;
    }

    public void setAssignedLead(String assignedLead) {
        this.assignedLead = assignedLead;
    }
}

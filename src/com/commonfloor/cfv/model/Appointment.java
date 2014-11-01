package com.commonfloor.cfv.model;

public class Appointment {
  private String listingId;
  private String locality;
  private String timeSlot;
  public Appointment(String listingId, String locality, String timeSlot) {
    this.listingId = listingId;
    this.locality = locality;
    this.timeSlot = timeSlot;
  }
  public String getListingId() {
    return listingId;
  }
  public void setListingId(String listingId) {
    this.listingId = listingId;
  }
  public String getLocality() {
    return locality;
  }
  public void setLocality(String locality) {
    this.locality = locality;
  }
  public String getTimeSlot() {
    return timeSlot;
  }
  public void setTimeSlot(String timeSlot) {
    this.timeSlot = timeSlot;
  }
  @Override
  public String toString() {
    return "Appointment [listingId=" + listingId + ", locality=" + locality + ", timeSlot="
        + timeSlot + "]";
  }
}

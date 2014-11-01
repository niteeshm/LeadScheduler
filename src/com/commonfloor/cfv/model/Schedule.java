package com.commonfloor.cfv.model;

public class Schedule {
  private String listingId;
  private String locality;
  private String agentId;
  private String timeSlot;
  
  public Schedule() {}
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
  public String getAgentId() {
    return agentId;
  }
  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }
  public String getTimeSlot() {
    return timeSlot;
  }
  public void setTimeSlot(String timeSlot) {
    this.timeSlot = timeSlot;
  } 
  
}

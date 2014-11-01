package com.commonfloor.cfv.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Agents {
  private String name;
  private String id;
  private String phoneNumber;
  private Map<String,ArrayList<String>> timeSlots;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public Map<String, ArrayList<String>> getTimeSlots() {
    return this.timeSlots;
  }
  public void createTimeSlotMap(){
    this.timeSlots = new LinkedHashMap<String, ArrayList<String>>();
    for(int i=1;i<4;i++){
      this.timeSlots.put("T"+i, new ArrayList<String>());
    }
  }
  public void setTimeSlots(Map<String, ArrayList<String>> timeSlots) {
    this.timeSlots = timeSlots;
  }

  @Override
  public String toString() {
    return "Agents [name=" + name + ", id=" + id + ", phoneNumber=" + phoneNumber + ", timeSlots="
        + timeSlots + "]";
  } 
}

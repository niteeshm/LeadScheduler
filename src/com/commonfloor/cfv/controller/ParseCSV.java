package com.commonfloor.cfv.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commonfloor.cfv.model.Agents;
import com.commonfloor.cfv.model.Appointment;

/**
 * Servlet implementation class ParseCSV
 */
@WebServlet("/ParseCSV")
public class ParseCSV extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public ParseCSV() {
    super();
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    Map<String, ArrayList<String>> prefMap = createPrefMap(response);
    Map<String, Map<String, Integer>> localityMap = createLocalityMap(prefMap, response);
    Map<String, Agents> agentsMap = createAgentMap(response);
    Map<String, Appointment> unscheduledAppointments = new LinkedHashMap<String, Appointment>();
    Map<String, Agents> scheduledAgentsMap =
        scheduleLeads(agentsMap, localityMap, unscheduledAppointments, response);
    out.print(prefMap + "<br>" + localityMap + "<br><br> " + agentsMap + "<br><br>"
        + scheduledAgentsMap + "<br><br>" + unscheduledAppointments);
    request.setAttribute("unscheduledAppointments", unscheduledAppointments);
    request.setAttribute("scheduledAgentsMap", scheduledAgentsMap);
    RequestDispatcher rd = request.getRequestDispatcher("schedule.jsp");
    rd.forward(request, response);
  }

  private Map<String, Agents> scheduleLeads(Map<String, Agents> agentsMap,
      Map<String, Map<String, Integer>> localityMap,
      Map<String, Appointment> unscheduledAppointments, HttpServletResponse response) {
    String path = "/Users/niteeshmehra/Downloads/testData.csv";
    String dataLine = "";

    double totalAppointments = getAppointmentCount();
    double totalAgents = getAgentCount();
    double imaxAppointments = totalAppointments / totalAgents;
    int maxAppointments = (int) Math.ceil(imaxAppointments);
    int maxAppointmentsPerSlot = 3;
    File file = new File(path);
    try {

      BufferedReader br = new BufferedReader(new FileReader(file));
      dataLine = br.readLine();
      while ((dataLine = br.readLine()) != null) {
        String[] str = dataLine.split(",");
        String listingId = str[1];
        String locality = str[3];
        String timeSlot = str[4];
        Agents agent;
        Map<String, Integer> agentRankMap = localityMap.get(locality);
        Iterator it1 = agentRankMap.entrySet().iterator();
        Iterator it2 = agentRankMap.entrySet().iterator();
        Boolean assigned = false;
        while (it1.hasNext()) {
          Map.Entry priority = (Map.Entry) it1.next();
          String agentId = (String) priority.getKey();
          int rank = (int) priority.getValue();
          agent = agentsMap.get(agentId);
          Map<String, ArrayList<String>> timeSlotMap = agent.getTimeSlots();
          ArrayList<String> timeSlotList = timeSlotMap.get(timeSlot);
          if (timeSlotList.size() < maxAppointmentsPerSlot) {
            timeSlotList.add(locality + "," + timeSlot + "," + listingId);
            timeSlotMap.put(timeSlot, timeSlotList);
            assigned = true;
            break;
          }
        }
        if (assigned == false) {
          Appointment apt = new Appointment(listingId, locality, timeSlot);
          unscheduledAppointments.put(listingId, apt);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return agentsMap;
  }

  private Map<String, Agents> createAgentMap(HttpServletResponse response) {
    Map<String, Agents> agentsMap = new LinkedHashMap<String, Agents>();
    String path = "/Users/niteeshmehra/Downloads/AgentDetails (1).csv";
    String dataLine = "";
    File file = new File(path);
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      dataLine = br.readLine();
      while ((dataLine = br.readLine()) != null) {
        String[] str = dataLine.split(",");
        Agents a = new Agents();
        a.setId(str[0]);
        a.setName(str[1]);
        a.setPhoneNumber(str[2]);
        a.createTimeSlotMap();
        agentsMap.put(str[0], a);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return agentsMap;
  }

  private int getAgentCount() {
    String path = "/Users/niteeshmehra/Downloads/Pref (1).csv";
    String dataLine = "";
    int count = 0;
    File file = new File(path);
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      dataLine = br.readLine();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dataLine.split(",").length;
  }

  private int getAppointmentCount() {
    String path = "/Users/niteeshmehra/Downloads/testData.csv";
    String dataLine = "";
    int count = 0;
    File file = new File(path);
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      dataLine = br.readLine();
      while ((dataLine = br.readLine()) != null) {
        count++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return count;
  }

  private Map<String, ArrayList<String>> createPrefMap(HttpServletResponse response) {
    String path = "/Users/niteeshmehra/Downloads/Pref (1).csv";
    String dataLine = "";
    Map<String, ArrayList<String>> prefMap = new LinkedHashMap<String, ArrayList<String>>();
    File file = new File(path);
    try {
      PrintWriter out = response.getWriter();
      BufferedReader br = new BufferedReader(new FileReader(file));
      dataLine = br.readLine();
      String[] str = dataLine.split(",");
      for (int i = 0; i < str.length; i++) {
        Agents a = new Agents();
        a.setId(str[i]);
        ArrayList<String> prefList = new ArrayList<String>();
        BufferedReader br1 = new BufferedReader(new FileReader(file));
        dataLine = br1.readLine();
        while ((dataLine = br1.readLine()) != null) {
          String[] str1 = dataLine.split(",");
          prefList.add(str1[i]);
        }
        prefMap.put(a.getId(), prefList);
      }
      return prefMap;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {
    List<Entry<String, Integer>> list =
        new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());
    // Sorting the list based on values
    Collections.sort(list, new Comparator<Entry<String, Integer>>() {
      public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
        if (order) {
          return o1.getValue().compareTo(o2.getValue());
        } else {
          return o2.getValue().compareTo(o1.getValue());
        }
      }
    });
    // Maintaining insertion order with the help of LinkedList
    Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
    for (Entry<String, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
  }

  private Map<String, Map<String, Integer>> createLocalityMap(
      Map<String, ArrayList<String>> prefMap, HttpServletResponse response) {

    Map<String, Map<String, Integer>> localityMap =
        new LinkedHashMap<String, Map<String, Integer>>();
    try {
      PrintWriter out = response.getWriter();
      response.setContentType("text/html");
      String locality = "";
      BufferedReader br =
          new BufferedReader(new FileReader("/Users/niteeshmehra/Downloads/localities.csv"));
      while ((locality = br.readLine()) != null) {
        Map<String, Integer> rankMap = new LinkedHashMap<String, Integer>();
        Iterator iter = prefMap.entrySet().iterator();
        while (iter.hasNext()) {
          Map.Entry pref = (Map.Entry) iter.next();
          String agentId = (String) pref.getKey();
          ArrayList<String> prefList = (ArrayList) pref.getValue();
          Iterator listIterator = prefList.listIterator();
          int count = 1;
          while (listIterator.hasNext()) {
            String a = (String) listIterator.next();
            if (a.equals(locality)) {
              rankMap.put(agentId, count);
              localityMap.put(locality, rankMap);
              break;
            }
            count++;
          }
        }
        rankMap = sortByComparator(rankMap, true);
        localityMap.put(locality, rankMap);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return localityMap;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    service(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    service(request, response);
  }

}

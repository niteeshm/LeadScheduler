package com.commonfloor.cfv.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.commonfloor.cfv.model.Agents;

/**
 * Servlet implementation class SMSController
 */
@WebServlet("/SMSController")
public class SMSController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public SMSController() {
    super();
  }


  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.print("<link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css\">");
    HttpSession session = request.getSession();
    LinkedHashMap<String, Agents> agentMap =
        (LinkedHashMap<String, Agents>) session.getAttribute("agentMap");
    Iterator iter = agentMap.entrySet().iterator();
    Map.Entry entry = (Map.Entry) iter.next();
    Agents agent = (Agents) entry.getValue();
    String phoneNumber = agent.getPhoneNumber();
    phoneNumber = "9972604208";
    String apiUrl =
        "http://203.212.70.200/smpp/sendsms?username=maxheaphttp&password=maxheap123&to="
            + phoneNumber + "&from=CFLOOR&udh=&text=Dear%20" + agent.getName()
            + ",%20Details%20of%20the%20payment%20towards%20CommonFloor%20of%20Rs"
            + agent.getPhoneNumber() + "%20sent%20to%20" + agent.getId()
            + ".%20Please%20check%20for%20details.%20Call%209620206060%20for%20any%20queries";
    URL url = new URL(apiUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.connect();
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuffer responseMsg = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      responseMsg.append(inputLine);
    }
    if (responseMsg.toString().contains("Sent"))
      out.print("USER HAS BEEN INFORMED<br>");
    else
      out.print(responseMsg);

    phoneNumber = "9686804421";
    apiUrl =
        "http://203.212.70.200/smpp/sendsms?username=maxheaphttp&password=maxheap123&to="
            + phoneNumber + "&from=CFLOOR&udh=&text=Dear%20" + agent.getName()
            + ",%20Details%20of%20the%20payment%20towards%20CommonFloor%20of%20Rs"
            + agent.getPhoneNumber() + "%20sent%20to%20" + agent.getId()
            + ".%20Please%20check%20for%20details.%20Call%209620206060%20for%20any%20queries";
    url = new URL(apiUrl);
    conn = (HttpURLConnection) url.openConnection();
    conn.connect();
    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    inputLine = "";
    responseMsg = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      responseMsg.append(inputLine);
    }
    if (responseMsg.toString().contains("Sent"))
      out.print("AGENT HAS BEEN INFORMED<br>");
    else
      out.print(responseMsg);
    out.print("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\"></script><script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js\"></script>");
    in.close();

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

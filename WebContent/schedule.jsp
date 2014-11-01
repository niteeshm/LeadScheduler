<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page
	import="org.apache.catalina.tribes.group.interceptors.TwoPhaseCommitInterceptor.MapEntry"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.commonfloor.cfv.model.Agents"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="com.commonfloor.cfv.model.Appointment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link href="cover.css" rel="stylesheet">
<title>Schedule</title>
</head>
<body>
	<h2>Scheduled Data</h2>
	<table border=1>
		<tr>
			<th>Agent ID</th>
			<th>Name</th>
			<th>Number</th>
			<th colspan=3>T1</th>
			<th colspan=3>T2</th>
			<th colspan=3>T3</th>
		</tr>
		<%
		  LinkedHashMap<String, Agents> scheduledAgentsMap =
		      (LinkedHashMap) request.getAttribute("scheduledAgentsMap");
		  Iterator it1 = scheduledAgentsMap.entrySet().iterator();
		  while (it1.hasNext()) {
		    Map.Entry scheduledAgentObj = (Map.Entry) it1.next();
		    String agentId = (String) scheduledAgentObj.getKey();
		    Agents scheduledAgentList = (Agents) scheduledAgentObj.getValue();
		    String name = scheduledAgentList.getName();
		    String number = scheduledAgentList.getPhoneNumber();
		%>
		<tr>
			<td><%=agentId%></td>
			<td><%=name%></td>
			<td><%=number%></td>

			<%
			  Map<String, ArrayList<String>> timeSlots = scheduledAgentList.getTimeSlots();
			    Iterator iter = timeSlots.entrySet().iterator();
			    while (iter.hasNext()) {
			      Map.Entry ts = (Map.Entry) iter.next();
			      ArrayList al = (ArrayList) ts.getValue();
			      Iterator listIterator = al.listIterator();
			        
			      String tsData = "";
			      while (listIterator.hasNext()) {
			        tsData = (String) listIterator.next();
			        %>
			        <td><%=tsData%></td>
			     <%  
			      }
			      switch(al.size()){
			        case 0: %><td></td><td></td><td></td><%
			        		break;
			        case 1:  %><td></td><td></td><%
			        		break;
			        case 2:	 %><td></td><%
			        		break;
			      }
			    }
		  }
			%>
		
	</table>
	<br>
	<br>
	<br>
	<h2>Unscheduled Data</h2>
	<table border=1>
		<tr>
			<th>Listing ID</th>
			<th>Locality</th>
			<th>TimeSlot</th>
		</tr>
		<%
		  LinkedHashMap<String, Appointment> unAp =
		      (LinkedHashMap) request.getAttribute("unscheduledAppointments");
		  Iterator it2 = unAp.entrySet().iterator();
		  while (it2.hasNext()) {
		    Map.Entry unApObj = (Map.Entry) it2.next();
		    String listingId = (String) unApObj.getKey();
		    Appointment unApList = (Appointment) unApObj.getValue();
		    String locality = unApList.getLocality();
		    String timeSlot = unApList.getTimeSlot();
		%>
		<tr>
			<td><%=listingId%></td>
			<td><%=locality%></td>
			<td><%=timeSlot%></td>
		</tr>
		<%
		session.setAttribute("agentMap", scheduledAgentsMap);
		  }
		%>
	</table>
	<a href="SMSController" class="btn btn-lg btn-default">SEND SMSES</a>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>

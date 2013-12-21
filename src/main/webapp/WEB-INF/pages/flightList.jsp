<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Flight Result</title>
	</head>
	<body>
		<h1>flight search result, ${flightInfo.tripType}</h1>
		<div id="departDiv">
			<h2>depart </h2>
			<table>
				<tr>
					<th>flight code</th>
					<th>departure</th>
					<th>arrival</th>
					<th>low fare</th>
					<th>hi flyer</th>
				</tr>
		      	<c:forEach var="flightDetail" items="${flightInfo.goFlightDetails}">
			        <tr>
			          <td><c:out value="${flightDetail.flightCode}"/></td>
			          <td><c:out value="${flightDetail.departureTime}"/> (<c:out value="${flightDetail.departureStation}"/>)</td>
			          <td><c:out value="${flightDetail.arrivalTime}"/> (<c:out value="${flightDetail.arrivalStation}"/>)</td>
			        </tr>
		      	</c:forEach>
		    </table>
		</div>
    	
    	<div id="returnDiv">
			<h2>return</h2>
		</div>
	</body>
	
</html>
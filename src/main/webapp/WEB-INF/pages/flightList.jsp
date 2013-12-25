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
		<h1>${flightInfo.tripType} flight search result</h1>
		
		<div id="departDiv">
			<h2>depart on ${flightInfo.goDateStr}</h2>
			<table>
				<tr>
					<th>flight code</th>
					<th width="180">departure</th>
					<th width="180">arrival</th>
					<th width="180">low fare</th>
					<th width="180">hi flyer</th>
				</tr>
		      	<c:forEach var="flightDetail" items="${flightInfo.goFlightDetails}">
			        <tr>
			          <td align="center"><c:out value="${flightDetail.flightCode}"/></td>
			          <td align="center"><c:out value="${flightDetail.departureTime}"/> (<c:out value="${flightDetail.departureStation}"/>)</td>
			          <td align="center"><c:out value="${flightDetail.arrivalTime}"/> (<c:out value="${flightDetail.arrivalStation}"/>)</td>
			          <td align="center">
			          	<table>
			          		<c:if test="${not empty flightDetail.lowFare.adultPriceString}">
			          			<tr><td align="center">adult: </td><td><c:out value="${flightDetail.lowFare.adultPriceString}"/></td></tr>
			          		</c:if>
			          		<c:if test="${not empty flightDetail.lowFare.childrenPriceString}">
			          			<tr><td align="center">children: </td><td><c:out value="${flightDetail.lowFare.childrenPriceString}"/></td></tr>
			          		</c:if>
			          		<c:if test="${not empty flightDetail.lowFare.infantPriceString}">
			          			<tr><td align="center">infant: </td><td><c:out value="${flightDetail.lowFare.infantPriceString}"/></td></tr>
			          		</c:if>
			          	</table>
			          </td>
			          <td align="center">
			          	<table>
			          		<c:if test="${not empty flightDetail.hiFare.adultPriceString}">
			          			<tr><td align="center">adult: </td><td><c:out value="${flightDetail.hiFare.adultPriceString}"/></td></tr>
			          		</c:if>
			          		<c:if test="${not empty flightDetail.hiFare.childrenPriceString}">
			          			<tr><td align="center">children: </td><td><c:out value="${flightDetail.hiFare.childrenPriceString}"/></td></tr>
			          		</c:if>
			          		<c:if test="${not empty flightDetail.hiFare.infantPriceString}">
			          			<tr><td align="center">infant: </td><td><c:out value="${flightDetail.hiFare.infantPriceString}"/></td></tr>
			          		</c:if>
			          	</table>
			          </td>
			        </tr>
		      	</c:forEach>
		    </table>
		</div>
    	
    	<c:if test="${flightInfo.tripType eq 'round-trip'}">
    		<div id="returnDiv">
				<h2>return on ${flightInfo.returnDateStr}</h2>
				<table>
					<tr>
						<th>flight code</th>
						<th width="180">departure</th>
						<th width="180">arrival</th>
						<th width="180">low fare</th>
						<th width="180">hi flyer</th>
					</tr>
			      	<c:forEach var="flightDetail" items="${flightInfo.returnFlightDetails}">
				        <tr>
				          <td align="center"><c:out value="${flightDetail.flightCode}"/></td>
				          <td align="center"><c:out value="${flightDetail.departureTime}"/> (<c:out value="${flightDetail.departureStation}"/>)</td>
				          <td align="center"><c:out value="${flightDetail.arrivalTime}"/> (<c:out value="${flightDetail.arrivalStation}"/>)</td>
				          <td align="center">
				          	<table>
				          		<c:if test="${not empty flightDetail.lowFare.adultPriceString}">
				          			<tr><td align="center">adult: </td><td><c:out value="${flightDetail.lowFare.adultPriceString}"/></td></tr>
				          		</c:if>
				          		<c:if test="${not empty flightDetail.lowFare.childrenPriceString}">
				          			<tr><td align="center">children: </td><td><c:out value="${flightDetail.lowFare.childrenPriceString}"/></td></tr>
				          		</c:if>
				          		<c:if test="${not empty flightDetail.lowFare.infantPriceString}">
				          			<tr><td align="center">infant: </td><td><c:out value="${flightDetail.lowFare.infantPriceString}"/></td></tr>
				          		</c:if>
				          	</table>
				          </td>
				          <td align="center">
				          	<table>
				          		<c:if test="${not empty flightDetail.hiFare.adultPriceString}">
				          			<tr><td align="center">adult: </td><td><c:out value="${flightDetail.hiFare.adultPriceString}"/></td></tr>
				          		</c:if>
				          		<c:if test="${not empty flightDetail.hiFare.childrenPriceString}">
				          			<tr><td align="center">children: </td><td><c:out value="${flightDetail.hiFare.childrenPriceString}"/></td></tr>
				          		</c:if>
				          		<c:if test="${not empty flightDetail.hiFare.infantPriceString}">
				          			<tr><td align="center">infant: </td><td><c:out value="${flightDetail.hiFare.infantPriceString}"/></td></tr>
				          		</c:if>
				          	</table>
				          </td>
				        </tr>
			      	</c:forEach>
			    </table>
			</div>
    	</c:if>
    	
	</body>
	
</html>
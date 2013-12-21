<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Scrapper Sample</title>
		<link rel="stylesheet" type="text/css" href="css/jquery.ui.datepicker.css"/>
		<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.3.custom.css"/>
		<script src="js/jquery-1.9.1.js"></script>
		<script src="js/jquery-ui-1.10.3.custom.js"></script>
		<script>
			$(function() {
				$( "#departureDate" ).datepicker();
				$( "#arrivalDate" ).datepicker();
			});
		</script>
		
		<script>
			$(document).ready(function() {
			    var adultbox = myform.passengerAdultNum;
				var kidbox = myform.passengerChildrenNum;
				var infantbox = myform.passengerInfantNum;
				for (var i=0;i<=9;i++){
					adultbox.options[i] = new Option(i+' Adult', i);
					kidbox.options[i] = new Option(i+' Kid', i);
					infantbox.options[i] = new Option(i+' Infant', i);
					if(i==0){
						adultbox.options[i].selected = 'selected';
						kidbox.options[i].selected = 'selected';
						infantbox.options[i].selected = 'selected';
					}
				}
			});
		</script>
		
		<script type="text/javascript">
			 function setOptions(chosen) {
			 	console.log("chosen = "+chosen);
				var selbox = myform.destination;
				selbox.options.length = 0;
				if (chosen == "CGK") {
				  selbox.options[selbox.options.length] = new Option('Bali (DPS)','DPS');
				  selbox.options[selbox.options.length] = new Option('Jeddah (JED)','JED');
				}else if(chosen == "DPS") {
				  selbox.options[selbox.options.length] = new Option('Jakarta (CGK)','CGK');
				  selbox.options[selbox.options.length] = new Option('Bandung (BDO)','BDO');
				}else if(chosen == "JED") {
				  selbox.options[selbox.options.length] = new Option('Jakarta (CGK)','CGK');
				}else{
					
				}
			}
			
			function printInputValues(){
				console.log('trip type = '+myform.tripType.value);
				console.log('origin = '+myform.origin.options[myform.origin.selectedIndex].value);
				console.log('destination = '+myform.destination.options[myform.destination.selectedIndex].value);
				console.log('depart date = '+myform.departureDate.value);
				console.log('return date = '+myform.arrivalDate.value);
				console.log('adult num = '+myform.passengerAdultNum.selectedIndex.value);
				console.log('kid num = '+myform.passengerChildrenNum.selectedIndex.value);
				console.log('infant num = '+myform.passengerInfantNum.selectedIndex.value);
			}
			
			/* function validatePassengerAdult(chosen){
				console.log(chosen);
				if(chosen>0 && myform.adultNumOpt.selectedIndex.value==0){
					myform.adultNumOpt.selectedIndex = 1;
					document.getElementById('msgLabel').innerHTML = 'there should be minimum 1 adult';
					console.log(myform.adultNumOpt.selectedIndex.value);
				}else{
					document.getElementById('msgLabel').innerHTML = ' ';
					alert(myform.adultNumOpt.selectedIndex.value);
				}
			} */
			
		</script>
	</head>
	<body>
		<h1>Input Page</h1>
		<form id="myform" action="/samplescrapper/search/airasia.html" method="post">
			<input type="radio" name="tripType" value="RoundTrip">round trip</input> 
			<input type="radio" name="tripType" value="OneWay">one way</input>
			<br/>
			<label>origin: </label>
			<select name="origin" size="1" 
					onchange="setOptions(this.options[this.selectedIndex].value);">
					<option value=" " selected="selected"> </option>
					<option value="CGK">Jakarta (CGK)</option>
					<option value="DPS">Bali (DPS)</option>
					<option value="JED">Jeddah (JED)</option>
			</select>
			<br />
			<label>destination: </label>
			<select name="destination" size="1">
				<option value=" " selected="selected"> </option>
			</select>
			<br />
			<p>depart date: <input type="text" id="departureDate" name="departureDate" /></p>
			<p>return date: <input type="text" id="arrivalDate" name="arrivalDate" /></p>
			
			<select name="passengerAdultNum" size="1" ></select>
			<select name="passengerChildrenNum" size="1"></select>
			<select name="passengerInfantNum" size="1"></select>
			<br/>
			<label id="msgLabel"/>
			<br/>
			<input type="button" value="Submit" onclick="printInputValues(); submit();"/>
			
		</form>
		
	</body>
	
</html>
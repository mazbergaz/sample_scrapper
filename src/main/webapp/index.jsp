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
			
			var oriVals;
			var destVals;
			//var currencyMap;
			var stationMaps = {};
			
			$(function() {
				// set date picker
				$( "#departureDate" ).datepicker();
				$( "#arrivalDate" ).datepicker();
				
				// set passenger number boxes
			    var adultbox = myform.passengerAdultNum;
				var infantbox = myform.passengerInfantNum;
				for (var i=0;i<=9;i++){
					if(i>0){
						adultbox.options[i-1] = new Option(i+' Adult', i);
					}
					infantbox.options[i] = new Option(i+' Infant', i);
				}
				adultbox.options[0].selected = 'selected';
				infantbox.options[0].selected = 'selected';
				
				oriVals = (function () {
				    var json = null;
				    $.ajax({
				        'async': false,
				        'global': false,
				        'url': "js/resources/originVal.json",
				        'dataType': "json",
				        'success': function (data) {
				            json = data;
				        }
				    });
				    return json;
				})();
				
				// set origin options
				var originbox = myform.origin;
				originbox.options[0] = new Option(' ', ' ');
				for(var i=0; i<oriVals.length; i++){
					var optGroup = document.createElement('optgroup');
					optGroup.label = oriVals[i].category;
					originbox.appendChild(optGroup);
					for(var j=0; j<oriVals[i].items.length; j++){
						//originbox.options[originbox.options.length] = new Option(oriVals[i].items[j].label, oriVals[i].items[j].value);
						stationMaps[oriVals[i].items[j].value] = oriVals[i].items[j].label;
						var objOption = document.createElement("option");
						objOption.innerHTML = oriVals[i].items[j].label;
						objOption.value = oriVals[i].items[j].value;
						optGroup.appendChild(objOption);
					}
				}
				
				destVals = (function () {
				    var json = null;
				    $.ajax({
				        'async': false,
				        'global': false,
				        'url': "js/resources/destinationVal.json",
				        'dataType': "json",
				        'success': function (data) {
				            json = data;
				        }
				    });
				    return json;
				})();
				
				/*currencyMap = (function () {
				    var json = null;
				    $.ajax({
				        'async': false,
				        'global': false,
				        'url': "js/resources/currencyMap.json",
				        'dataType': "json",
				        'success': function (data) {
				            json = data;
				        }
				    });
				    return json;
				})();*/
				
			});
			
			function setOptions(chosen) {
				var selbox = myform.destination;
				selbox.options.length = 0;
				for(var i=0; i<destVals[chosen].Routes.length; i++){
					var optGroup = document.createElement('optgroup');
					optGroup.label = destVals[chosen].Routes[i].Country;
					selbox.appendChild(optGroup);
					for(var j=0; j<destVals[chosen].Routes[i].Stations.length; j++){
						var stationCode = destVals[chosen].Routes[i].Stations[j];
						//selbox.options[selbox.options.length] = new Option(stationMaps[stationCode], stationCode);
						//document.getElementById('currency').value = destVals[chosen].BaseCurrency;
						var objOption = document.createElement("option");
						objOption.innerHTML = stationMaps[stationCode];
						objOption.value = stationCode;
						optGroup.appendChild(objOption);
					}
				}
			}
			
			function setCurrency(chosen){
				document.getElementById('currency').value = destVals[chosen].BaseCurrency;
			}
			
		</script>
	</head>
	<body>
		<h1>Input Page</h1>
		<form id="myform" action="/samplescrapper/search/airasia.html" method="post">
			<input type="radio" name="tripType" value="round-trip">round trip</input> 
			<input type="radio" name="tripType" value="one-way">one way</input>
			<br/>
			<label>origin: </label>
			<select name="origin" size="1" onchange="setOptions(this.options[this.selectedIndex].value);"></select>
			<br />
			<label>destination: </label>
			<select name="destination" size="1" onchange="setCurrency(this.options[this.selectedIndex].value);"></select>
			<br />
			<p>depart date: <input type="text" id="departureDate" name="departureDate" /></p>
			<p>return date: <input type="text" id="arrivalDate" name="arrivalDate" /></p>
			
			<input type="hidden" id="currency" name="currency" />
			
			<select name="passengerAdultNum" size="1" ></select>
			<select name="passengerInfantNum" size="1"></select>
			<br/>
			<label id="msgLabel"/>
			<br/>
			<input type="button" value="Submit" onclick="submit();"/>
			
		</form>
		
	</body>
	
</html>
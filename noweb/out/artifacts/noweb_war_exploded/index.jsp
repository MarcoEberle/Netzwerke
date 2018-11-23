<%--
  Created by IntelliJ IDEA.
  User: Marco
  Date: 22.11.2018
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<html>
<head>
  <title>Taxizentrale</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body background="https://www.visitberlin.de/system/files/image/Taxis_iStock.com_Foto%20Maxiphoto_DL_PPT_0.jpg">

<center>
  <h1>Wilkommen bei der super krassen Taxizentrale</h1>
  <h1>Geben Sie hier Ihre Daten ein:</h1>

  <form method="POST" action="http://localhost:8081/noweb/resources/requestTime/add">
    Taxinummer:  <input type="number"  name="TaxiNr" value="" ><br>
    Startadresse: <input type="text"  name="StartAdresse" value="" ><br>
    Zieladresse:  <input type="text"  name="EndAdresse" value=""><br>
    gewünschte Fahrzeit in Minuten: <input type="text"  name="TimeArrive" value=""><br>
    <input type="submit" value="Submit">
  </form>
</center>
</body>
</html>

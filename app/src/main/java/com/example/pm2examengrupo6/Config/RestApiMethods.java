package com.example.pm2examengrupo6.Config;

public class RestApiMethods {

    //http://localhost/PM2E1Grupo6/CreatePerson.php
    public static final String Http = "http://";
    private static final String ipaddress = "192.168.3.9"; // ip personal
    public static final String RestApi = "PM2E1Grupo6"; //carpeta en htdocs
    public static final String separador = "/";
    //Api names
    public static final String PostPerson = "CreatePerson.php";
    private static final String GetName = "BuscarNombre.php?nombre=";
    public static final String GetList = "ListPerson.php";
    private static final String PutUpdate = "UpdatePerson.php";
    private static final String DeletePerson= "DeletePerson.php";


    //Endpoint
    public static final String EndPointCreatePerson= "http://" + ipaddress + separador + RestApi + separador + PostPerson;
    public static final String EndPointGetPerson = "http://" + ipaddress + separador + RestApi + separador + GetName;
    public static final String EndPointGetList = "http://" + ipaddress + separador + RestApi + separador + GetList;
    public static final String EndPointSetUpdatePerson = "http://" + ipaddress + separador + RestApi + separador + PutUpdate;
    public static final String EndPointDeletePerson = "http://" + ipaddress + separador + RestApi + separador + DeletePerson;

}

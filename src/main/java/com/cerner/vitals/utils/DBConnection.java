//package com.cerner.vitals.utils;
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//    private static DBConnection objref;
//    private static Connection con;
//
//    private DBConnection(){
//        try {
//            Class.forName("org.postgresql.Driver");
//            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/vital_app","bb106075","ProjectJava2020!");
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static DBConnection getInstance(){
//        if (objref == null){
//            objref = new DBConnection();
//        }
//        return objref;
//    }
//
//    public Connection getConnection(){
//        return con;
//
//    }
//
//}

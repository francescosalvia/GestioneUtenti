package com.contactlab.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase
{
    private static Connection con;


    private static Connection getConnection() throws SQLException {
        if (con == null){
            MysqlDataSource dataSource = new MysqlDataSource();

            dataSource.setServerName("127.0.0.1");
            dataSource.setPortNumber(3306);
            dataSource.setUser("root");
            dataSource.setPassword("root");
            dataSource.setDatabaseName("gestione_utenti");

            con = dataSource.getConnection();
        }

        return con;
    }

    public static ResultSet risultato(String leggi) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(leggi);

        return ps.executeQuery();
    }

    public void leggiDB(){

    }




}

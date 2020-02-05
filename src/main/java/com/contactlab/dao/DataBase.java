package com.contactlab.dao;

import com.contactlab.data.Indirizzo;
import com.contactlab.data.OrdineCompletato;
import com.contactlab.data.Utente;
import com.contactlab.service.ServizioUtente;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static Connection con;


    private static Connection getConnection() throws SQLException {
        if (con == null) {
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

    public List<Utente> getUtenti() throws SQLException {


        List<Utente> utenti = new ArrayList<>();
        ResultSet ut = risultato("SELECT * FROM utente");

        while (ut.next()) {
            String nome = ut.getString("nome");
            String cognome = ut.getString("cognome");
            String email = ut.getString("email");
            String telefono = ut.getString("telefono");

            Utente utente = new Utente(nome, cognome, email, telefono);

            utenti.add(utente);
        }

        return  utenti;

    }

    public Indirizzo getIndirizzo(String email) throws SQLException {

        Indirizzo indirizzo = null;

        ResultSet in = risultato("SELECT * FROM indirizzi where email = '" + email + "'");

        while (in.next()) {
            indirizzo = new Indirizzo();

            String via = in.getString("via");
            indirizzo.setVia(via);

            String citta = in.getString("citta");
            indirizzo.setCitta(citta);

            String provincia = in.getString("provincia");
            indirizzo.setProvincia(provincia);

            String cap = in.getString("cap");
            indirizzo.setCap(cap);

            String nazione = in.getString("nazione");
            indirizzo.setNazione(nazione);


        }

        return  indirizzo;

    }



}

/*

            ResultSet in = risultato("SELECT * FROM indirizzi where email = '" + email + "'");

            while (in.next()) {
                Indirizzo indirizzo = new Indirizzo();

                String via = in.getString("via");
                indirizzo.setVia(via);

                String citta = in.getString("citta");
                indirizzo.setCitta(citta);

                String provincia = in.getString("provincia");
                indirizzo.setProvincia(provincia);

                String cap = in.getString("cap");
                indirizzo.setCap(cap);

                String nazione = in.getString("nazione");
                indirizzo.setNazione(nazione);

                s.aggiungiIndirizzo(email, indirizzo);
            }


            ResultSet oc = risultato("SELECT * FROM ordini_completati where email = '" + email + "'");

            while (oc.next()) {
                OrdineCompletato ordineCompletato = new OrdineCompletato();

                String idEvento = oc.getString("id_evento");
                ordineCompletato.setIdEvento(idEvento);

                String orderId = oc.getString("order_id");
                ordineCompletato.setOrderId(orderId);

                String storeCode = oc.getString("store_code");
                ordineCompletato.setStoreCode(storeCode);
            }


 */
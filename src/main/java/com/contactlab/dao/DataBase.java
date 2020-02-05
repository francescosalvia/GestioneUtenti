package com.contactlab.dao;

import com.contactlab.data.*;
import com.contactlab.service.ServizioUtente;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

        return utenti;

    }

    public Indirizzo getIndirizzo(String email) throws SQLException {

        Indirizzo indirizzo = null;

        ResultSet in = risultato("SELECT * FROM indirizzi where email = '" + email + "'");

        while (in.next()) {
            indirizzo = new Indirizzo();

            indirizzo.setVia(in.getString("via"));
            indirizzo.setCitta(in.getString("citta"));
            indirizzo.setProvincia(in.getString("provincia"));
            indirizzo.setCap(in.getString("cap"));
            indirizzo.setNazione(in.getString("nazione"));

        }

        return indirizzo;

    }


    public List<Evento> getEvento(String email) throws SQLException {

        List<Evento> evento = new ArrayList<>();

        ResultSet oc = risultato("SELECT * FROM ordini_completati where email = '" + email + "'");

        while (oc.next()) {
            OrdineCompletato ordineCompletato = new OrdineCompletato();

            ordineCompletato.setIdEvento(oc.getString("id_evento"));

            ordineCompletato.setOrderId(oc.getString("order_id"));
            ordineCompletato.setStoreCode(oc.getString("store_code"));
            ordineCompletato.setShippingMethod(oc.getString("shipping_method"));
            ordineCompletato.setPaymentMethod(oc.getString("payment_method"));
            ordineCompletato.setData(oc.getTimestamp("data").toLocalDateTime());

            Amount amount = new Amount();
            amount.setTotal(oc.getDouble("prezzo_totale"));
            amount.setTax(oc.getDouble("tassa_totale"));
            ordineCompletato.setAmounts(amount);

            HashMap<String, String> map = new HashMap<>();
            if (!StringUtils.isEmpty(oc.getString("ip"))) {
                String key = "ip";
                map.put(key, oc.getString("ip"));
            }
            if (!StringUtils.isEmpty(oc.getString("browser"))) {
                String key = "browser";
                map.put(key, oc.getString("browser"));
            }
            if (!StringUtils.isEmpty(oc.getString("carta_di_credito"))) {
                String key = "cartaCredito";
                map.put(key, oc.getString("carta_di_credito"));
            }
            ordineCompletato.setNuoviValori(map);

            List<DettaglioOrdine> dettaglioOrdine = new ArrayList<>();
            ResultSet pro = risultato("SELECT * FROM  dettaglio_ordini WHERE id_evento = '" + oc.getString("id_evento") + "'");
            while (pro.next()) {
                DettaglioOrdine dettaglio = new DettaglioOrdine();

                dettaglio.setId(pro.getString("id_prodotto"));
                dettaglio.setOrderLineId(pro.getString("order_line_id"));
                dettaglio.setQuantita(pro.getInt("quantita"));
                dettaglio.setPrice(pro.getDouble("prezzo"));
                dettaglio.setTax(pro.getDouble("tassa"));
                dettaglio.setIdEvento(pro.getString("id_evento"));

                dettaglioOrdine.add(dettaglio);
            }

            ordineCompletato.setDettaglioOrdini(dettaglioOrdine);

            evento.add(ordineCompletato);
        }

        ResultSet lin = risultato("SELECT * FROM  logged_in where email = '" + email + "'");
        while (lin.next()) {
            LoggedIn login = new LoggedIn();

            login.setIdEvento(lin.getString("id_evento"));
            login.setData(lin.getTimestamp("data").toLocalDateTime());

            HashMap<String, String> map = new HashMap<>();
            if (!StringUtils.isEmpty(lin.getString("ip"))) {
                String key = "ip";
                map.put(key, lin.getString("ip"));
            }
            if (!StringUtils.isEmpty(lin.getString("browser"))) {
                String key = "browser";
                map.put(key, lin.getString("browser"));
            }

            login.setNuoviValori(map);

            evento.add(login);
        }

        ResultSet lon = risultato("SELECT * FROM  logged_out where email = '" + email + "'");
        while (lon.next()) {
            LoggedOut loggedOut = new LoggedOut();

            loggedOut.setIdEvento(lon.getString("id_evento"));
            loggedOut.setData(lon.getTimestamp("data").toLocalDateTime());

            HashMap<String, String> map = new HashMap<>();
            if (!StringUtils.isEmpty(lon.getString("ip"))) {
                String key = "ip";
                map.put(key, lon.getString("ip"));
            }
            if (!StringUtils.isEmpty(lon.getString("browser"))) {
                String key = "browser";
                map.put(key, lon.getString("browser"));
            }

            loggedOut.setNuoviValori(map);

            evento.add(loggedOut);
        }


        return evento;

    }


}

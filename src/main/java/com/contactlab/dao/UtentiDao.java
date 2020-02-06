package com.contactlab.dao;

import com.contactlab.data.*;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UtentiDao {

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


    public List<Utente> getUtenti() throws SQLException {


        List<Utente> utenti = new ArrayList<>();

        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM utente");

        ResultSet ut = ps.executeQuery();

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

        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM indirizzi where email = ?");
        ps.setString(1, email);
        ResultSet in = ps.executeQuery();


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


    public List<OrdineCompletato> getOrdineCompletatoPerEmail(String email) throws SQLException {

        List<OrdineCompletato> evento = new ArrayList<>();


        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM ordini_completati where email = ?");
        ps.setString(1, email);
        ResultSet oc = ps.executeQuery();


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

            evento.add(ordineCompletato);
        }

        return evento;
    }

    public List<OrdineCompletato> getOrdineCompletato() throws SQLException {

        List<OrdineCompletato> evento = new ArrayList<>();


        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM ordini_completati");
        ResultSet oc = ps.executeQuery();


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

            evento.add(ordineCompletato);
        }

        return evento;
    }


    public List<DettaglioOrdine> getDettaglioOrdine(String idEvento) throws SQLException {

        List<DettaglioOrdine> dettaglioOrdini = new ArrayList<>();

        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM dettaglio_ordini where id_evento = ?");
        ps.setString(1, idEvento);
        ResultSet dt = ps.executeQuery();


        while (dt.next()) {

            DettaglioOrdine dettaglio = new DettaglioOrdine();

            dettaglio.setId(dt.getString("id_prodotto"));
            dettaglio.setOrderLineId(dt.getString("order_line_id"));
            dettaglio.setQuantita(dt.getInt("quantita"));
            dettaglio.setPrice(dt.getDouble("prezzo"));
            dettaglio.setTax(dt.getDouble("tassa"));

            dettaglioOrdini.add(dettaglio);
        }

        return dettaglioOrdini;

    }


    public List<LoggedIn> getLoggedIn() throws SQLException {

        List<LoggedIn> evento = new ArrayList<>();


        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM logged_in");
        ResultSet lin = ps.executeQuery();

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

        return evento;
    }

    public List<LoggedOut> getLoggedOut() throws SQLException {

        List<LoggedOut> evento = new ArrayList<>();

        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM logged_out");
        ResultSet lon = ps.executeQuery();

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


    public List<LoggedIn> getLoggedInPerEmail(String email) throws SQLException {

        List<LoggedIn> evento = new ArrayList<>();


        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM logged_in where email = ?");
        ps.setString(1, email);
        ResultSet lin = ps.executeQuery();

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

        return evento;
    }

    public List<LoggedOut> getLoggedOutPerEmail(String email) throws SQLException {

        List<LoggedOut> evento = new ArrayList<>();

        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM logged_out where email = ?");
        ps.setString(1, email);
        ResultSet lon = ps.executeQuery();

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


    public void insertUtente(String nome, String cognome, String email, String telefono) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("INSERT INTO utente(nome,cognome,email,telefono) VALUES (?,?,?,?)");
        ps.setString(1, nome);
        ps.setString(2, cognome);
        ps.setString(3, email);
        ps.setString(4, telefono);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();


    }


    public void insertIndirizzo(String via, String citta, String provincia, String cap, String nazione, String email) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("INSERT INTO indirizzi(via, citta, provincia, cap, nazione, email) VALUES (?,?,?,?,?,?)");
        ps.setString(1, via);
        ps.setString(2, citta);
        ps.setString(3, provincia);
        ps.setString(4, cap);
        ps.setString(5, nazione);
        ps.setString(6, email);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();


    }

    public int insertTipoEvento(String tipoEvento) throws SQLException {
        PreparedStatement ps2 = getConnection().prepareStatement("INSERT INTO registro_eventi(tipo_evento) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        ps2.setString(1,tipoEvento);
        ps2.executeUpdate();
        ResultSet rs2 = ps2.getGeneratedKeys();
        rs2.next();

        return rs2.getInt("id_evento");

    }

    public int checkOrderId(String order_id) throws SQLException {

        int idEvento = 0;
        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM ordini_completati WHERE order_id = ? ");
        ps.setString(1, order_id);
        ResultSet te = ps.executeQuery();
        while (te.next()) {
            idEvento = te.getInt("id_evento");
        }
        return  idEvento;
    }

    public int getIdEvento(String tipoEvento) throws SQLException {
        int idEvento = 0;
        PreparedStatement ps = getConnection().prepareStatement("SELECT id_evento FROM registro_eventi WHERE tipo_evento = ? ORDER BY idEvento DESC LIMIT 1");
        ps.setString(1, tipoEvento);
        ResultSet te = ps.executeQuery();
        while (te.next()) {
            idEvento = te.getInt("id_evento");
        }
        return idEvento;
    }




    public void insertOrdiniCompletati(int idEvento,String orderId, String storeCode, String payment_method, String shipping_method, Double prezzo_totale,
                                       Double tassa_totale, String ip, String browser, String cartaDiCredito, LocalDateTime data, String email,String tipoEvento) throws SQLException {


        PreparedStatement ps = getConnection().prepareStatement("INSERT INTO ordini_completati(id_evento, order_id,store_code, payment_method, shipping_method, " +
                                                                    " prezzo_totale, tassa_totale,ip ,browser,carta_di_credito,data,email) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

        Timestamp timestamp = Timestamp.valueOf(data);

        ps.setInt(1, idEvento);
        ps.setString(2, orderId);
        ps.setString(3, storeCode);
        ps.setString(4, payment_method);
        ps.setString(5, shipping_method);
        ps.setDouble(6, prezzo_totale);
        ps.setDouble(7, tassa_totale);
        ps.setString(8, ip);
        ps.setString(9, browser);
        ps.setString(10, cartaDiCredito);
        ps.setTimestamp(11, timestamp);
        ps.setString(12, email);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();


    }


    public Utente getUtentePerEmail(String email) throws SQLException {

        Utente utente = null;

        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM utente where email = ?");
        ps.setString(1,email);

        ResultSet ut = ps.executeQuery();

        while (ut.next()) {
            String nome = ut.getString("nome");
            String cognome = ut.getString("cognome");
            String email2 = ut.getString("email");
            String telefono = ut.getString("telefono");

            utente = new Utente(nome, cognome, email2, telefono);

        }
        return utente;
    }


}


package com.contactlab.service;

import com.contactlab.dao.UtentiDao;
import com.contactlab.data.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServizioUtente {

    private UtentiDao dataBase = new UtentiDao();


    /**
     * METODI
     **/


    /**
     * METODI LEGGI
     **/

    public List<Utente> getUtenti() throws SQLException {

        return dataBase.getUtenti();
    }

    public List<Utente> getIndirizzo() throws SQLException {

        List<Utente> utenti;

        utenti = dataBase.getUtenti();

        for (Utente utente : utenti) {
            utente.setIndirizzo(dataBase.getIndirizzo(utente.getEmail()));
        }
        return utenti;
    }

    public List<OrdineCompletato> getOrdineCompletatiConDettaglio() throws SQLException {

        List<OrdineCompletato> ordini = new ArrayList<>();

        ordini.addAll(dataBase.getOrdineCompletato());

        for(OrdineCompletato ordine : ordini)
        {
            ordine.setDettaglioOrdini(dataBase.getDettaglioOrdine(ordine.getIdEvento()));
        }

        return ordini;
    }

    public List<Evento> getOrdineCompletatiConDettaglioConEmail(String email) throws SQLException {

        List<Evento> ordini = new ArrayList<>();

        ordini.addAll(dataBase.getOrdineCompletatoPerEmail(email));

        for(Evento ordine : ordini)
        {
            if (ordine instanceof OrdineCompletato)
            {
                ((OrdineCompletato) ordine).setDettaglioOrdini(dataBase.getDettaglioOrdine(ordine.getIdEvento()));
            }

        }

        return ordini;
    }



    public List<OrdineCompletato> getOrdineCompletati() throws SQLException {

        return dataBase.getOrdineCompletato();
    }

    public List<LoggedIn> getLoggedIn() throws SQLException {

        return dataBase.getLoggedIn();
    }

    public List<LoggedOut> getLoggedOut() throws SQLException {

        return dataBase.getLoggedOut();
    }

    public List<Evento> getEventi() throws SQLException {
        List<Evento> eventi = new ArrayList<>();

        eventi.addAll(dataBase.getOrdineCompletato());
        eventi.addAll(dataBase.getLoggedIn());
        eventi.addAll(dataBase.getLoggedOut());

        return eventi;
    }

    public List<Utente> getUtentiCompleto(Boolean indirizzo, Boolean eventi) throws SQLException {
        // todo - tutti gli utenti, con indirizzo ed eventi in base al tipo di boolean
        List<Utente> utenti;

        utenti = dataBase.getUtenti();

        if (indirizzo) {
            for (Utente utente : utenti) {
                utente.setIndirizzo(dataBase.getIndirizzo(utente.getEmail()));
            }
        }
        if (eventi) {
            for (Utente utente : utenti) {
                List<Evento> evento = new ArrayList<>();
                String email = utente.getEmail();
                evento.addAll(getOrdineCompletatiConDettaglioConEmail(email));
                evento.addAll(dataBase.getLoggedOutPerEmail(email));
                evento.addAll(dataBase.getLoggedInPerEmail(email));
                utente.setEventi(evento);
            }

        }

        return utenti;
    }


}

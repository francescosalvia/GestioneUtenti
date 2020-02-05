package com.contactlab.service;

import com.contactlab.dao.DataBase;
import com.contactlab.data.Evento;
import com.contactlab.data.Indirizzo;
import com.contactlab.data.Utente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServizioUtente {

    private DataBase dataBase = new DataBase();

    private List<Utente> utenti = new ArrayList<>();


    public List<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(List<Utente> utenti) {
        this.utenti = utenti;
    }

    /**
     * METODI
     **/

    public Optional<Utente> cercaUtenti(String email) {

        Optional<Utente> opt = Optional.empty();

        for (Utente utente : utenti) {
            if (utente.getEmail().equalsIgnoreCase(email)) {
                opt = Optional.of(utente);
            }
        }
        return opt;
    }

    /**
     * METODI AGGIUNGI
     **/

    public void aggiungiUtente(Utente utente) {
        utenti.add(utente);
    }

    public void aggiungiIndirizzo(String email, Indirizzo indirizzo) {

        Optional<Utente> utenteSelezionato = cercaUtenti(email);

        if (utenteSelezionato.isPresent()) {
            utenteSelezionato.get().setIndirizzo(indirizzo);
        }

    }

    public void aggiungiEvento(String email, List<Evento> eventi) {
        Optional<Utente> utenteSelezionato = cercaUtenti(email);

        if (utenteSelezionato.isPresent()) {
            utenteSelezionato.get().setEventi(eventi);
        }
    }


    /**
     * METODI RIMUOVI
     **/

    public void rimuoviUtente(String email) {
        utenti.removeIf(utente -> utente.getEmail().equalsIgnoreCase(email));
    }

    public void rimuoviEvento(String email, String idEvento) {

        Optional<Utente> utenteSelezionato = cercaUtenti(email);

        if (utenteSelezionato.isPresent()) {
            utenteSelezionato.get().getEventi().removeIf(evento -> evento.getIdEvento().equalsIgnoreCase(idEvento));
        }

    }


    /**
     * METODI STAMPA
     **/

    public void stampaUtenti() {
        for (Utente utente : utenti) {
            System.out.println(utente.toString());
        }
    }

    public void stampaEventi() {
        for (Utente utente : utenti) {
            System.out.println(utente.getEventi());
        }
    }

    /** METODI LEGGI **/

    public void leggiUtendiDB() throws SQLException {

        utenti = dataBase.getUtenti();
    }

    public void leggiIndirizziDB() throws SQLException {

        for (Utente utente : utenti) {
            utente.setIndirizzo(dataBase.getIndirizzo(utente.getEmail()));
        }
    }

    public void leggiEventiDB() throws SQLException {

        for (Utente utente : utenti) {
            utente.setEventi(dataBase.getEvento(utente.getEmail()));
        }
    }


}

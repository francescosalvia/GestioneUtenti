package com.contactlab.service;

import com.contactlab.dao.UtentiDao;
import com.contactlab.data.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServizioUtente {

    private static Logger log = LoggerFactory.getLogger(ServizioUtente.class);

    private UtentiDao dataBase = new UtentiDao();


    /**
     * METODI
     **/


    /**
     * METODI LEGGI
     **/

    public List<Utente> getUtenti() throws SQLException {
        log.info("Entrato nel metodo getUtenti");
        return dataBase.getUtenti();
    }

    public List<Utente> getIndirizzo() throws SQLException {
        log.info("Entrato nel metodo getIndirizzo");
        List<Utente> utenti;

        utenti = dataBase.getUtenti();

        for (Utente utente : utenti) {
            utente.setIndirizzo(dataBase.getIndirizzo(utente.getEmail()));
        }
        log.info("Aggiunto tutti gli indirizzi ");
        return utenti;
    }

    public List<OrdineCompletato> getOrdineCompletatiConDettaglio() throws SQLException {
        log.info("Entrato nel metodo getOrdineCompletatiConDettaglio");
        List<OrdineCompletato> ordini = new ArrayList<>();

        ordini.addAll(dataBase.getOrdineCompletato());
        log.info("Aggiunto tutti gli ordini completati");
        for (OrdineCompletato ordine : ordini) {
            ordine.setDettaglioOrdini(dataBase.getDettaglioOrdine(ordine.getIdEvento()));
        }
        log.info("Aggiunto tutti i dettagli degli ordini completati");

        return ordini;
    }

    public List<Evento> getOrdineCompletatiConDettaglioConEmail(String email) throws SQLException {

        List<Evento> ordini = new ArrayList<>();

        ordini.addAll(dataBase.getOrdineCompletatoPerEmail(email));

        for (Evento ordine : ordini) {
            if (ordine instanceof OrdineCompletato) {
                ((OrdineCompletato) ordine).setDettaglioOrdini(dataBase.getDettaglioOrdine(ordine.getIdEvento()));
            }
        }


        return ordini;
    }


    public List<OrdineCompletato> getOrdineCompletati() throws SQLException {
        log.info("Entrato nel metodo getOrdineCompletati");
        return dataBase.getOrdineCompletato();
    }

    public List<LoggedIn> getLoggedIn() throws SQLException {
        log.info("Entrato nel metodo getLoggedIn");
        return dataBase.getLoggedIn();
    }

    public List<LoggedOut> getLoggedOut() throws SQLException {
        log.info("Entrato nel metodo getLoggedOut");
        return dataBase.getLoggedOut();

    }

    public List<Evento> getEventi() throws SQLException {
        log.info("Entrato nel metodo getEventi");
        List<Evento> eventi = new ArrayList<>();

        eventi.addAll(dataBase.getOrdineCompletato());
        log.info("Eventi ordiniCompletati aggiunti con successo");
        eventi.addAll(dataBase.getLoggedIn());
        log.info("Eventi LogIn aggiunti con successo");
        eventi.addAll(dataBase.getLoggedOut());
        log.info("Eventi LogOut aggiunti con successo");

        return eventi;
    }

    public List<Utente> getUtentiCompleto(Boolean indirizzo, Boolean eventi) throws SQLException {
        log.info("Entrato nel metodo getUtentiCompleto con indirizzo = {} ed evento = {} ", indirizzo, eventi);

        List<Utente> utenti;

        utenti = dataBase.getUtenti();
        log.info("Metodo getutenti eseguito correttamente con {} utenti inseriti", utenti.size());

        if (indirizzo) {
            for (Utente utente : utenti) {
                utente.setIndirizzo(dataBase.getIndirizzo(utente.getEmail()));
            }
            log.info("Indirizzi aggiunti con successo");
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
            log.info("Eventi aggiunti con successo");
        }

        return utenti;
    }


    public JSONArray creaJsonUtenti() throws SQLException {
        log.info("Entrato nel metodo creaJsonUtenti");
        List<Utente> utenti;

        utenti = dataBase.getUtenti();

        JSONArray utentiArray = new JSONArray();

        for (Utente ute : utenti) {

            JSONObject jsnUtente = new JSONObject();
            jsnUtente.put("nome", ute.getNome());
            jsnUtente.put("cognome", ute.getCognome());
            jsnUtente.put("email", ute.getEmail());
            jsnUtente.put("telefono", ute.getTelefono());
            utentiArray.put(jsnUtente);
        }

        log.info("Utenti JSON aggiunti con successo");
        return utentiArray;

    }

    public JSONArray creaJsonUtentiConIndirizzo() throws SQLException {
        log.info("Entrato nel metodo creaJsonUtentiConIndirizzo");
        List<Utente> utenti;

        utenti = getIndirizzo();

        JSONArray utentiArray = new JSONArray();

        for (Utente ute : utenti) {
            Indirizzo indirizzo = ute.getIndirizzo();
            JSONObject jsnUtente = new JSONObject();
            jsnUtente.put("nome", ute.getNome());
            jsnUtente.put("cognome", ute.getCognome());
            jsnUtente.put("email", ute.getEmail());
            jsnUtente.put("telefono", ute.getTelefono());
            if (indirizzo != null) {
                jsnUtente.put("via", ute.getIndirizzo().getVia());
                jsnUtente.put("citta", ute.getIndirizzo().getCitta());
                jsnUtente.put("provincia", ute.getIndirizzo().getProvincia());
                jsnUtente.put("cap", ute.getIndirizzo().getCap());
                jsnUtente.put("nazione", ute.getIndirizzo().getNazione());
            }
            utentiArray.put(jsnUtente);
        }
        log.info("Utenti con Indirizzi JSON aggiunti con successo");
        return utentiArray;
    }


    public JSONArray creaJsonUtentiConOrdineCompletato() throws SQLException {
        log.info("Entrato nel metodo creaJsonUtentiConOrdineCompletato");
        List<Utente> utenti;

        utenti = getUtentiCompleto(true, true);

        JSONArray utentiArray = new JSONArray();

        for (Utente ute : utenti) {
            Indirizzo indirizzo = ute.getIndirizzo();
            JSONObject jsnUtente = new JSONObject();
            jsnUtente.put("nome", ute.getNome());
            jsnUtente.put("cognome", ute.getCognome());
            jsnUtente.put("email", ute.getEmail());
            jsnUtente.put("telefono", ute.getTelefono());
            if (indirizzo != null) {
                jsnUtente.put("via", ute.getIndirizzo().getVia());
                jsnUtente.put("citta", ute.getIndirizzo().getCitta());
                jsnUtente.put("provincia", ute.getIndirizzo().getProvincia());
                jsnUtente.put("cap", ute.getIndirizzo().getCap());
                jsnUtente.put("nazione", ute.getIndirizzo().getNazione());
            }

            JSONArray jsnEventiArray = new JSONArray();

            for (int j = 0; j < ute.getEventi().size(); j++) {

                JSONObject jsnEvento = new JSONObject();
                Evento eve = ute.getEventi().get(j);

                jsnEvento.put("idEvento", eve.getIdEvento());
                if (eve.getData() != null) {
                    String data = eve.getData().toString();
                    jsnEvento.put("Data", data);
                }


                if (eve instanceof OrdineCompletato) {
                    OrdineCompletato ordineCompletato = (OrdineCompletato) eve;
                    jsnEvento.put("tipo", "OrdineCompletato");
                    jsnEvento.put("OrderId", ordineCompletato.getOrderId());
                    jsnEvento.put("metoodoPagemento", ordineCompletato.getPaymentMethod());
                    jsnEvento.put("MetodoSpedizione", ordineCompletato.getShippingMethod());
                    jsnEvento.put("StoreCode", ordineCompletato.getStoreCode());

                    if (ordineCompletato.getNuoviValori() != null) {
                        JSONObject jsnNuoviValori = new JSONObject();

                        for (Map.Entry<String, String> mappa : ordineCompletato.getNuoviValori().entrySet()) {
                            jsnNuoviValori.put(mappa.getKey(), mappa.getValue());
                        }

                        jsnEvento.put("NuoviValoriOrdineCompletato", jsnNuoviValori);
                    }

                    if (ordineCompletato.getAmounts() != null) {
                        JSONObject jsnAmmount = new JSONObject();
                        jsnAmmount.put("totale", ordineCompletato.getAmounts().getTotal());
                        jsnAmmount.put("tassaTotale", ordineCompletato.getAmounts().getTax());
                        jsnEvento.put("Ammount", jsnAmmount);
                    }

                    if (ordineCompletato.getDettaglioOrdini() != null) {
                        for (int k = 0; k < ordineCompletato.getDettaglioOrdini().size(); k++) {
                            JSONObject jsnProdotti = new JSONObject();
                            DettaglioOrdine dt = ordineCompletato.getDettaglioOrdini().get(k);
                            jsnProdotti.put("OrderLineId", dt.getOrderLineId());
                            jsnProdotti.put("id", dt.getId());
                            jsnProdotti.put("quantita", dt.getQuantita());
                            jsnProdotti.put("priceProdotto", dt.getPrice());
                            jsnProdotti.put("taxProdotto", dt.getTax());
                            jsnEvento.put("Product", jsnProdotti);
                        }

                    }

                }
                if (eve instanceof LoggedIn) {

                    jsnEvento.put("tipo", "LogIN");

                    if (eve.getNuoviValori() != null) {

                        JSONObject jsnNuoviValori = new JSONObject();

                        for (Map.Entry<String, String> mappa : eve.getNuoviValori().entrySet()) {
                            jsnNuoviValori.put(mappa.getKey(), mappa.getValue());
                        }
                        jsnEvento.put("NuoviValoriOrdineLogin", jsnNuoviValori);
                    }


                }
                if (eve instanceof LoggedOut) {

                    jsnEvento.put("tipo", "LogOUT");

                    if (eve.getNuoviValori() != null) {
                        JSONObject jsnNuoviValori = new JSONObject();

                        for (Map.Entry<String, String> mappa : eve.getNuoviValori().entrySet()) {
                            jsnNuoviValori.put(mappa.getKey(), mappa.getValue());
                        }

                        jsnEvento.put("NuoviValoriOrdineLogout", jsnNuoviValori);
                    }
                }

                jsnEventiArray.put(jsnEvento);
            }
            jsnUtente.put("Eventi", jsnEventiArray);

            utentiArray.put(jsnUtente);
        }
        return utentiArray;

    }


    public void caricaUtente() throws SQLException {
        String file = "C:\\Users\\francesco.salvia\\Desktop\\GesioneUtenti\\utente.txt";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            List<Utente> utenti = dataBase.getUtenti();

            int contatore = 0;

            while ((line = br.readLine()) != null) {
                String[] details = line.split(";");
                contatore++;

                if (details.length == 4) {
                    String nome = details[0];
                    String cognome = details[1];
                    String email = details[2];
                    String telefono = details[3];

                    boolean inserito = false;

                    int uSize = utenti.size();

                    if (uSize > 0) {
                        for (int i = 0; i < uSize; i++) {
                            if (email.equalsIgnoreCase(utenti.get(i).getEmail())) {
                                System.out.println("email gia inserita!! Controlla il file alla riga " + contatore);
                                inserito = true;
                            }
                        }
                        if (!inserito) {
                            dataBase.insertUtente(nome, cognome, email, telefono);
                        }
                    } else {

                        dataBase.insertUtente(nome, cognome, email, telefono);

                    }
                }
            }
        } catch (IOException e) {
            log.error("IoException nel metodo caricaUtenti  ", e);
        }
    }

    public void caricaIndirizzi() throws SQLException {
        String file = "C:\\Users\\francesco.salvia\\Desktop\\GesioneUtenti\\indirizzi.txt";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            List<Utente> utenti = getIndirizzo();


            while ((line = br.readLine()) != null) {
                String[] details = line.split(";");


                if (details.length == 6) {
                    String via = details[0];
                    String citta = details[1];
                    String provincia = details[2];
                    String cap = details[3];
                    String nazione = details[4];
                    String email = details[5];


                    int uSize = utenti.size();

                    if (uSize > 0) {
                        for (int i = 0; i < uSize; i++) {
                            if (email.equalsIgnoreCase(utenti.get(i).getEmail())) {
                                if (utenti.get(i).getIndirizzo() == null) {
                                    dataBase.insertIndirizzo(via, citta, provincia, cap, nazione, email);
                                }
                            }

                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("IoException nel metodo caricaIndirizzi  ", e);
        }
    }


    public void caricaOrdineCO() throws SQLException {
        String file = "C:\\Users\\francesco.salvia\\Desktop\\GesioneUtenti\\testataCO.txt";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            List<Utente> utenti = dataBase.getUtenti();

            int contatore = 0;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(";");

                contatore++;

                if (details.length == 12) {
                    String idEvento = details[0];
                    String orderId = details[1];
                    String storeCode = details[2];
                    String payment_method = details[3];
                    String shipping_method = details[4];
                    String prezzototale = details[5];
                    double prezzo_totale = Double.parseDouble(prezzototale);
                    String tassatotale = details[6];
                    double tassa_totale = Double.parseDouble(tassatotale);
                    String ip = details[7];
                    String browser = details[8];
                    String cartaDiCredito = details[9];
                    String localDate = details[10];
                    LocalDateTime data = controlloDate(localDate, contatore);
                    String email = details[11];

                    int uSize = utenti.size();

                    if (uSize > 0) {
                        for (int i = 0; i < uSize; i++) {
                            if (email.equalsIgnoreCase(utenti.get(i).getEmail())) {
                                dataBase.insertOrdiniCompletati(idEvento,  orderId,  storeCode,  payment_method,  shipping_method,  prezzo_totale
                                        , tassa_totale,  ip,  browser,  cartaDiCredito,  data,  email);
                            }
                        }
                    }

                }
            }
        } catch (IOException e) {
            log.error("IoException nel metodo caricaUtenti  ", e);
        }
    }




    public static LocalDateTime controlloDate(String date, int contatore) {
        LocalDateTime localDate = null;
        try {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            localDate = LocalDateTime.parse(date, formatter1);
        } catch (DateTimeParseException e) {
            log.error("C'è un errore alla con la data alla riga {}  Verrà sostituita con null", contatore, e);
        }

        return localDate;
    }



}

import com.contactlab.data.*;
import com.contactlab.service.ServizioUtente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.SQLException;
import java.util.List;

public class Main
{
    private static Logger log = LoggerFactory.getLogger(Main.class);
    private static ServizioUtente s = new ServizioUtente();
    public static void main(String[] args){

        try {

            List<Utente> utenti = s.getUtenti();
            for (Utente value : utenti) {
                System.out.println(value.toString());
            }

            System.out.println("--------------------------");

            utenti = s.getIndirizzo();
            for (Utente utente : utenti) {
                System.out.println(utente.toString());
            }

            System.out.println("--------------------------");

            List<OrdineCompletato> ordiniCompletati = s.getOrdineCompletati();

            for (OrdineCompletato ordineCompletato : ordiniCompletati) {
                System.out.println(ordineCompletato.toString());
            }

            System.out.println("--------------------------");

            List<Evento> eventi = s.getEventi();

            for (Evento evento : eventi) {
                System.out.println(evento.toString());
            }

            System.out.println("--------------------------");

            List<LoggedIn> loggedIn = s.getLoggedIn();

            for (LoggedIn login : loggedIn) {
                System.out.println(login.toString());
            }

            System.out.println("--------------------------");

            List<LoggedOut> loggedOut = s.getLoggedOut();

            for (LoggedOut logout : loggedOut) {
                System.out.println(logout.toString());
            }


            System.out.println("--------------------------");
            System.out.println("--------------------------");

            ordiniCompletati = s.getOrdineCompletatiConDettaglio();

            for (OrdineCompletato ordineCompletato : ordiniCompletati) {
                System.out.println(ordineCompletato.toString());
            }

            System.out.println("--------------------------");
            System.out.println("--------------------------");


            utenti = s.getUtentiCompleto(true,true);

            for (int i = 0; i < utenti.size(); i++)
            {
                System.out.println(utenti.get(i).toString());
            }

            System.out.println("--------------------------");
            System.out.println("--------------------------");


            utenti = s.getUtentiCompleto(false,true);

            for (int i = 0; i < utenti.size(); i++)
            {
                System.out.println(utenti.get(i).toString());
            }

            System.out.println("--------------------------");
            System.out.println("--------------------------");

            System.out.println(s.creaJsonUtenti());

            System.out.println("--------------------------");
            System.out.println("--------------------------");

            System.out.println(s.creaJsonUtentiConIndirizzo());

            System.out.println("--------------------------");
            System.out.println("--------------------------");
            System.out.println("--------------------------");
            System.out.println("--------------------------");
            System.out.println(s.creaJsonUtentiConOrdineCompletato());

            System.out.println("--------------------------");

            s.caricaUtente();

            s.caricaIndirizzi();

            s.caricaOrdineCO();


        } catch (SQLException e) {
            log.error("SQLException errore ", e);
        }


    }

}

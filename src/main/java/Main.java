import com.contactlab.service.ServizioUtente;

import java.sql.SQLException;

public class Main
{

    private static ServizioUtente s = new ServizioUtente();
    public static void main(String[] args){

        try {

            s.leggiUtendiDB();
            s.stampaUtenti();

            System.out.println("----------------------------");
            s.leggiIndirizziDB();
            s.stampaUtenti();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}

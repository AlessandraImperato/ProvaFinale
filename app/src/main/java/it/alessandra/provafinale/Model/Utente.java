package it.alessandra.provafinale.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by utente7.academy on 14/12/2017.
 */

public class Utente extends Users implements Serializable {
    public Utente(){
        super();
    }

    public Utente(String username, String password, String tipo, String nome, String cognome, List<Pacco> pacchi){
        super(username,password,tipo,nome,cognome,pacchi);
    }

}

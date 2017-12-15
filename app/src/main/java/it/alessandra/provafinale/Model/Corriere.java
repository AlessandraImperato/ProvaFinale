package it.alessandra.provafinale.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by utente7.academy on 14/12/2017.
 */

public class Corriere extends Users implements Serializable{
    public Corriere(){
        super();
    }
    public Corriere(String username, String password, String tipo, String nome, String cognome, List<Pacco> pacchi){
        super(username,password,tipo,nome,cognome,pacchi);
    }

    public Pacco findPackById(String id){
        Pacco pacco = new Pacco();
        for (Pacco tmp : pacchi){
            if(tmp.getId().equals(id)){
                pacco = tmp;
            }
        }
        return pacco;
    }
}

package it.alessandra.provafinale.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente7.academy on 14/12/2017.
 */

public class GestorePacchi implements Serializable {
    List<Users> allUsers;
    //List<Pacco> allPacks;

    public GestorePacchi(){
        allUsers = new ArrayList<>();
        //allPacks = new ArrayList<>();
    }

    public GestorePacchi(List<Users> allUsers){
        this.allUsers = allUsers;
        //this.allPacks = allPacks;
    }

    public List<Users> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<Users> allUsers) {
        this.allUsers = allUsers;
    }


    public List<Corriere> getCouriers(){
        List<Corriere> corrieri = new ArrayList<>();
        for(Users tmp : allUsers){
            if(tmp instanceof Corriere){
                corrieri.add((Corriere) tmp);
            }
        }
        return corrieri;
    }

    public List<Utente> getUtenti(){
        List<Utente> utenti = new ArrayList<>();
        for(Users tmp : allUsers){
            if(tmp instanceof Utente){
                utenti.add((Utente) tmp);
            }
        }
        return utenti;
    }

    public void setCorrieri(List<Corriere> corrieri){
        for (Corriere tmp : corrieri){
            allUsers.add(tmp);
        }
    }
    public void setUtenti(List<Utente> utenti){
        for (Utente tmp : utenti){
            allUsers.add(tmp);
        }
    }

    public Utente getUtenteByUser(String username){
        Utente utente = new Utente();
        for(Users tmp : allUsers){
            if (tmp instanceof Utente){
                if (tmp.getUsername().equals(username)){
                    utente = (Utente) tmp;
                }
            }
        }
        return utente;
    }

    public Corriere getCorriereByUser(String username){
        Corriere corriere = new Corriere();
        for(Users tmp : allUsers){
            if (tmp instanceof Corriere){
                if (tmp.getUsername().equals(username)){
                    corriere = (Corriere) tmp;
                }
            }
        }
        return corriere;
    }
}

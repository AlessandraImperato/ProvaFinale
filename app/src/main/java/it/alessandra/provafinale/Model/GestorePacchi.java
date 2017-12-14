package it.alessandra.provafinale.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente7.academy on 14/12/2017.
 */

public class GestorePacchi implements Serializable {
    List<Users> allUsers;

    public GestorePacchi(){
        allUsers = new ArrayList<>();
    }

    public GestorePacchi(List<Users> allUsers){
        this.allUsers = allUsers;
    }

    public List<Users> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<Users> allUsers) {
        this.allUsers = allUsers;
    }
}

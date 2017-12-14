package it.alessandra.provafinale.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente7.academy on 14/12/2017.
 */

public class Users implements Serializable{
    public String username;
    public String password;
    public String tipo;
    public List<Pacco> pacchi;

    public Users(){
        username = null;
        password = null;
        tipo = null;
        pacchi = new ArrayList<>();
    }

    public Users(String username, String password, String tipo, List<Pacco> pacchi){
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.pacchi = pacchi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Pacco> getPacchi() {
        return pacchi;
    }

    public void setPacchi(List<Pacco> pacchi) {
        this.pacchi = pacchi;
    }
}

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
    public String nome;
    public String cognome;
    public List<Pacco> pacchi;

    public Users(){
        username = null;
        password = null;
        tipo = null;
        nome = null;
        cognome = null;
        pacchi = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Users(String username, String password, String tipo, String nome, String cognome, List<Pacco> pacchi){
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.nome = nome;
        this.cognome = cognome;
        this.pacchi = pacchi;
    }
    public Users(String username, String password, String tipo, String nome, String cognome){
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.nome = nome;
        this.cognome = cognome;
        this.pacchi = new ArrayList<>();
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

package it.alessandra.provafinale.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by utente7.academy on 14/12/2017.
 */

public class Pacco implements Serializable {
    private String deposito;
    private String indirizzo;
    private String destinatario;
    private String dimensione;
    private String id;
    private String stato;
    private Date dataConsegna;
    private String corriereAssegnato;

    public Pacco(){
        deposito = null;
        indirizzo = null;
        destinatario = null;
        dimensione = null;
        id = null;
        stato = null;
        dataConsegna = null;
    }

    public Pacco(String deposito, String indirizzo, String destinatario, String dimensione, String id, String stato, Date dataConsegna){
        this.deposito = deposito;
        this.indirizzo = indirizzo;
        this.destinatario = destinatario;
        this.dimensione = dimensione;
        this.id = id;
        this.stato = stato;
        this.dataConsegna = dataConsegna;
    }
    public Pacco(String deposito, String indirizzo, String destinatario, String dimensione, String stato, Date dataConsegna, String corriereAssegnato){
        this.deposito = deposito;
        this.indirizzo = indirizzo;
        this.destinatario = destinatario;
        this.dimensione = dimensione;
        this.id = null;
        this.stato = stato;
        this.dataConsegna = dataConsegna;
        this.corriereAssegnato = corriereAssegnato;
    }

    public String getCorriereAssegnato() {
        return corriereAssegnato;
    }

    public void setCorriereAssegnato(String corriereAssegnato) {
        this.corriereAssegnato = corriereAssegnato;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getDimensione() {
        return dimensione;
    }

    public void setDimensione(String dimensione) {
        this.dimensione = dimensione;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Date getDataConsegna() {
        return dataConsegna;
    }

    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }
}

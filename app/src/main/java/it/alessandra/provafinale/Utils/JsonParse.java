package it.alessandra.provafinale.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.alessandra.provafinale.Model.Corriere;
import it.alessandra.provafinale.Model.Pacco;
import it.alessandra.provafinale.Model.Users;
import it.alessandra.provafinale.Model.Utente;

/**
 * Created by utente7.academy on 14/12/2017.
 */

public class JsonParse {
    public static List<Corriere> getListCouriers(String json) throws JSONException {
        List<Corriere> corrieri = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> courier = jsonObject.keys();
            while (courier.hasNext()) {
                Corriere corriere = new Corriere();
                corriere.setTipo("Corriere");
                String oneKey = courier.next();
                JSONObject oneCourier = jsonObject.getJSONObject(oneKey);
                Iterator<String> field = oneCourier.keys();
                while (field.hasNext()) {
                    String oneKey2 = field.next();
                    switch (oneKey2) {
                        case "cognome":
                            corriere.setCognome(oneCourier.getString(oneKey2));
                            break;
                        case "nome":
                            corriere.setNome(oneCourier.getString(oneKey2));
                            break;
                        case "password":
                            corriere.setPassword(oneCourier.getString(oneKey2));
                            break;
                        case "username":
                            corriere.setUsername(oneCourier.getString(oneKey2));
                    }
                }
                corrieri.add(corriere);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return corrieri;
    }

    public static int key(String json) {
        int index = 1;
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                index++;
                iterator.next();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return index;
    }

    public static List<Pacco> getListPack(String json) throws JSONException {
        List<Pacco> pacchi = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> pack = jsonObject.keys();
            while (pack.hasNext()) {
                Pacco pacco = new Pacco();
                String oneKey = pack.next();
                pacco.setId(oneKey);
                JSONObject onePack = jsonObject.getJSONObject(oneKey);
                Iterator<String> field = onePack.keys();
                while (field.hasNext()) {
                    String oneKey2 = field.next();
                    switch (oneKey2) {
                        case "corriere":
                            pacco.setCorriereAssegnato(onePack.getString(oneKey2));
                            break;
                        case "data di consegna":
                            pacco.setDataConsegna(SettingDate.formatToDate(onePack.getString(oneKey2)));
                            break;
                        case "stato":
                            pacco.setStato(onePack.getString(oneKey2));
                            break;
                        case "destinatario":
                            pacco.setDestinatario(onePack.getString(oneKey2));
                            break;
                        case "deposito":
                            pacco.setDeposito(onePack.getString(oneKey2));
                            break;
                        case "indirizzo":
                            pacco.setIndirizzo(onePack.getString(oneKey2));
                            break;
                        case "dimensione":
                            pacco.setDimensione(onePack.getString(oneKey2));
                            break;
                    }
                }
                pacchi.add(pacco);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pacchi;
    }

    public static List<Utente> getListUtenti(String json) throws JSONException {
        List<Utente> utenti = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> utente = jsonObject.keys();
            while (utente.hasNext()) {
                Utente utente1 = new Utente();
                utente1.setTipo("Utente");
                String oneKey = utente.next();
                JSONObject oneUtente = jsonObject.getJSONObject(oneKey);
                Iterator<String> field = oneUtente.keys();
                while (field.hasNext()) {
                    String oneKey2 = field.next();
                    switch (oneKey2) {
                        case "cognome":
                            utente1.setCognome(oneUtente.getString(oneKey2));
                            break;
                        case "nome":
                            utente1.setNome(oneUtente.getString(oneKey2));
                            break;
                        case "password":
                            utente1.setPassword(oneUtente.getString(oneKey2));
                            break;
                    }
                }
                utenti.add(utente1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return utenti;
    }

    public static List<Users> getAllUser(String json) throws JSONException {
        List<Users> user = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> userType = jsonObject.keys();
            while (userType.hasNext()) {
                Users users = new Users();
                String oneKey = userType.next();
                JSONObject oneUtente = jsonObject.getJSONObject(oneKey);
                Iterator<String> field = oneUtente.keys();
                while (field.hasNext()) {
                    String oneKey2 = field.next();
                    if(oneKey.equals("Corrieri")){
                        users.setTipo("Corriere");
                    }
                    else if(oneKey.equals("Utenti")){
                        users.setTipo("Utenti");

                    }
                    users.setUsername(oneKey2);
                    JSONObject specific = oneUtente.getJSONObject(oneKey2);
                    Iterator<String> field2 = specific.keys();
                    while (field2.hasNext()){
                        String oneKey3 = field2.next();
                        switch (oneKey3) {
                            case "cognome":
                                users.setCognome(oneUtente.getString(oneKey3));
                                break;
                            case "nome":
                                users.setNome(oneUtente.getString(oneKey3));
                                break;
                            case "password":
                                users.setPassword(oneUtente.getString(oneKey3));
                                break;
                        }
                    }
                }
                user.add(users);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}

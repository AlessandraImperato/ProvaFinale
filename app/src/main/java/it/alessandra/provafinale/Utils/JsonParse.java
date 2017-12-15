package it.alessandra.provafinale.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.alessandra.provafinale.Model.Corriere;
import it.alessandra.provafinale.Model.Pacco;

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
                    }
                }
                pacchi.add(pacco);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pacchi;
    }
}

package it.alessandra.provafinale.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.alessandra.provafinale.Model.Corriere;

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
                    }
                }
                corrieri.add(corriere);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return corrieri;
    }
}

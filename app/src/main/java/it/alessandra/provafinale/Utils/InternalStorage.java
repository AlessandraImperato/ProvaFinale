package it.alessandra.provafinale.Utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by utente7.academy on 14/12/2017.
 */

public final class InternalStorage {
    private InternalStorage(){}

    public static void writeObject(Context context, String key, Object object){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = context.openFileOutput(key,Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Object readObject(Context context, String key) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;

        try {
            fis = context.openFileInput(key);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } catch (IOException e) {
            return object;
        }
        catch (ClassNotFoundException e){
            return object;
        }
        return object;
    }
}

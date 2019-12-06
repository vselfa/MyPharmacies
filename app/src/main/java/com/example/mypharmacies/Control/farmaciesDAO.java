package com.example.mypharmacies.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import com.example.mypharmacies.Model.Farmacia;
import com.example.mypharmacies.Model.Poblacio;

public class farmaciesDAO {
    private MySQLiteHelper connexion;
    private SQLiteDatabase db;
    private ArrayList<Farmacia> llistaFarmacies;

    public farmaciesDAO(Context context) {
        connexion = new MySQLiteHelper( context );
        db = connexion.getWritableDatabase();
    }

    public ArrayList<Farmacia> getFarmaciesPoblacio (String poblacio) {
        // Paràmetre poblacio = m0, m1 ....
        String sql = "SELECT * FROM Farmacies WHERE Poblacio = '" + poblacio +"';";
        // Llegim les dades
        Cursor c = db.rawQuery(sql, null);
        llistaFarmacies = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Farmacia m = new Farmacia();
                m.setCodi(c.getString(0));     m.setNom(c.getString(1));
                m.setLat(c.getFloat(2));       m.setLon(c.getFloat(3));
                m.setPoblacio(c.getString(4)); m.setTelefon(c.getString(5));
                m.setAdresa(c.getString(6));   m.setFoto(c.getString(7));
                llistaFarmacies.add(m);
            } while (c.moveToNext());
        }
        return llistaFarmacies;
    }
    public  void closeDB () {
        db.close();
    }

    public String getNomPoblacio (String cod) {
        String sql = "SELECT * FROM Poblacions WHERE codi = '" + cod +"';";
        Cursor c = db.rawQuery(sql, null);
        Poblacio p = new Poblacio();
        c.moveToFirst();  // I si no el troba?
        p.setPoblacio(c.getString(1));
        return p.getPoblacio();
    }

    public boolean insertFarmacia (Farmacia farmacia) {
        ContentValues newRecord = new ContentValues();
        newRecord.put("codi", farmacia.getCodi()); newRecord.put("nom", farmacia.getNom());
        newRecord.put("lat", farmacia.getLat());   newRecord.put("lon", farmacia.getLon());
        // New elements
        newRecord.put("poblacio", farmacia.getPoblacio());
        newRecord.put("telefon", farmacia.getTelefon());
        newRecord.put("adresa", farmacia.getAdresa());
        newRecord.put("foto", farmacia.getFoto());
        // To check restrictions
        long answer = db.insert("Farmacies", null, newRecord );
        if (answer <= 0)return false;
        else return true;
    }
}
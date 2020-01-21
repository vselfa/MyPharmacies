package com.example.mypharmacies.Control;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.mypharmacies.Model.Poblacio;
import java.util.ArrayList;

public class poblacionsDAO  {
    private MySQLiteHelper connexion;     private SQLiteDatabase db;
    ArrayList<Poblacio> llistaPoblacions; ArrayList<String> nomPoblacions;
    public poblacionsDAO(Context context) {
        connexion = new MySQLiteHelper( context );
        db = connexion.getWritableDatabase();
    }
    public ArrayList<String> getNomPoblacions () {
        String sql =  "SELECT * FROM Poblacions" ;
        Cursor c = db.rawQuery(sql, null);         // Llegim les dades
        nomPoblacions = new ArrayList<>();
        if (c.moveToFirst()) {
            do {  nomPoblacions.add( c.getString(1));
            } while(c.moveToNext());
        }
        return nomPoblacions;
    }
    public ArrayList<Poblacio> getPoblacions () {
        String sql =  "SELECT * FROM Poblacions" ;
        Cursor c = db.rawQuery(sql, null);         // Llegim les dades
        llistaPoblacions = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Poblacio m = new Poblacio();
                m.setCodi(c.getString(0)); m.setPoblacio(c.getString(1));
                m.setLat(c.getFloat(2));   m.setLon(c.getFloat(3));
                llistaPoblacions.add(m);
            } while(c.moveToNext());
        }
        return llistaPoblacions;
    }

    public boolean insertPoblacio (Poblacio poble) {
        ContentValues newRecord = new ContentValues();
        newRecord.put("codi", poble.getCodi()); newRecord.put("poblacio", poble.getPoblacio());
        newRecord.put("lat", poble.getLat());   newRecord.put("lon", poble.getLon());
        // To check restrictions
        long answer = db.insert("Poblacions", null, newRecord );
        if (answer <= 0)return false;
        else return true;
    }
    public boolean eliminarPoblacio (String codiPoble) {
        int answer = db.delete("Poblacions", "codi= '" + codiPoble +"'", null);
        if (answer <= 0)return false;
        else return true;
    }
    public boolean actualitzarPoblacio (Poblacio poble) {
        ContentValues newRecord = new ContentValues();
        newRecord.put("codi", poble.getCodi()); newRecord.put("poblacio", poble.getPoblacio());
        newRecord.put("lat", poble.getLat());   newRecord.put("lon", poble.getLon());
        int answer = db.update("Poblacions", newRecord,
                "codi='" + poble.getCodi() + "'", null);
        if (answer <= 0)return false;
        else return true;
    }
    public  void closeDB () {
        db.close();
    }
}
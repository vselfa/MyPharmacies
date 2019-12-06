package com.example.mypharmacies.Control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbFarmacies2";
    private static int DATABASE_VERSION = 1; // Telèfono en un camp a part + camp Adresa

    //SQL sentence to create a table Poblacions. Pròxima versió: Canviar poblacio per nom!!!
    private String sqlCreatePoblacions =  "CREATE TABLE Poblacions (codi TEXT PRIMARY KEY, " +
            "poblacio TEXT NOT NULL, Lat REAL, Lon REAL) ";

    private String sqlInsertPoblacio1 = "INSERT INTO Poblacions (codi, poblacio, Lat, Lon) " +
            "VALUES ('m0', 'Carcaixent', 39.119223, -0.453422)";
    private String sqlInsertPoblacio2 = "INSERT INTO Poblacions (codi, poblacio, Lat, Lon) " +
            "VALUES ('m1', 'Alzira', 39.1515544, -0.4369795)";
    private String sqlInsertPoblacio3 = "INSERT INTO Poblacions (codi, poblacio, Lat, Lon) " +
            "VALUES ('m2', 'Tavernes de la Valldigna', 39.0727708,-0.2836759)";

    //SQL sentence to create a table Farmacies. Afegir camp Snippet = Adreça + telèfon!!!!!
    // Momentaneament utilitzarem el camp Foto!!!
    private String sqlCreateFarmacies =  "CREATE TABLE Farmacies  (" +
            "   codi TEXT PRIMARY KEY, nom TEXT NOT NULL, Lat REAL, Lon REAL, Poblacio TEXT, " +
            "   Telefon TEXT, Adresa TEXT, Foto TEXT,   " +
            "   FOREIGN KEY(Poblacio) REFERENCES Poblacions(codi)" +
            ")";

    private String sqlInsertFarmacia1 = "INSERT INTO Farmacies (codi, nom, Lat, Lon, Poblacio, " +
            "Telefon , Adresa, Foto) " +
            "VALUES ('m0', 'Estrela / Romera', 39.1217030, -0.4514444, 'm0', " +
            " '962430014', 'C/ Julian Ribera, 42','url foto')";

    private String sqlInsertFarmacia2 = "INSERT INTO Farmacies (codi, nom, Lat, Lon, Poblacio, " +
            "Telefon, Adresa, Foto) " +
            "VALUES ('m1', 'Escrivà Hermanos', 39.121083, -0.449172, 'm0', " +
            " '962431932', 'C/ Comandante Hernandez, 26' ,'url foto')";

    private String sqlInsertFarmacia3 = "INSERT INTO Farmacies (codi, nom, Lat, Lon, Poblacio, " +
            "Telefon ,Adresa, Foto) " +
            "VALUES ('m2', 'Patricio Soler', 39.124503, -0.4484, 'm0', "+
            " '962432822', 'C/ Jose Vidal Canet, 16' ,'url foto')";

    // ALzira
    private String sqlInsertFarmacia4 = "INSERT INTO Farmacies (codi, nom, Lat, Lon, Poblacio, " +
            "Telefon, Adresa, Foto) " +
            "VALUES ('m3', 'Alamanzon', 39.1506435,-0.4378113, 'm1', "+
            " '962410243', 'Av. Sants Patrons, 15','url foto')";
    // Tavernes de la Valldigna
    private String sqlInsertFarmacia5 = "INSERT INTO Farmacies (codi, nom, Lat, Lon, Poblacio, " +
            "Telefon, Adresa, Foto) " +
            "VALUES ('m4', 'A. Castelló', 39.0750315,-0.263039, 'm2', "+
            " '962823796', 'Carrer Verge dels Desamparats, 21,' ,'url foto')";

    // The constructor: Create the database
    public MySQLiteHelper(Context context) {
        super(context , DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called if the DB is accessed but not yet created
    @Override
    public void onCreate(SQLiteDatabase db) {
        createSentences(db);
    }

    // Called, if the database version is increased in your application code.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Modificant taules: Afegint taula farmacies. String query = "DROP TABLE Farmacies";
        //db.execSQL(query);
        db.execSQL(sqlCreateFarmacies);
        db.execSQL(sqlInsertFarmacia1); db.execSQL(sqlInsertFarmacia2); db.execSQL(sqlInsertFarmacia3);
        db.execSQL(sqlInsertFarmacia4); db.execSQL(sqlInsertFarmacia5);
    }

    protected void createSentences (SQLiteDatabase db) {
        db.execSQL(sqlCreatePoblacions);      db.execSQL(sqlInsertPoblacio1);
        db.execSQL(sqlInsertPoblacio2);       db.execSQL(sqlInsertPoblacio3);
        //String query = "DROP TABLE Farmacies"  db.execSQL(query);
        db.execSQL(sqlCreateFarmacies);
        db.execSQL(sqlInsertFarmacia1); db.execSQL(sqlInsertFarmacia2); db.execSQL(sqlInsertFarmacia3);
        db.execSQL(sqlInsertFarmacia4); db.execSQL(sqlInsertFarmacia5);
    }
}

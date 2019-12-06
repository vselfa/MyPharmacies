package com.example.mypharmacies.View.infoFarmacia;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.mypharmacies.Control.MySQLiteHelper;
import com.example.mypharmacies.R;
import java.util.HashMap;

public class InfoFarmaciaFragment extends Fragment {

    private String codiFarmacia, query;
    private MySQLiteHelper connexion;
    private TextView nomFarmacia, adresaFarmacia, telefonFarmacia;
    private ImageView fotoFarmacia;
    private HashMap<String, Integer> llistaFotosFarmacies;

    private ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info_farmacia, container, false);
        // The objects and layouts for the pharmacy information
        nomFarmacia = root.findViewById(R.id.nomFarmacia);
        adresaFarmacia = root.findViewById(R.id.adresa);
        telefonFarmacia = root.findViewById(R.id.telefon);
        fotoFarmacia = root.findViewById(R.id.fotoFarmacia);
        codiFarmacia = getArguments().getString("codiFarmacia");
        query = "SELECT * FROM Farmacies WHERE Codi = '" + codiFarmacia +"';";
        carregarFotosFarmacies();

        // Connecting to the database
        connexion = new MySQLiteHelper( root.getContext() );
        final SQLiteDatabase db = connexion.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        // Filling the fields of the layout
        if (c.moveToFirst()) {
            String codi = c.getString(0);
            nomFarmacia.setText(c.getString(1));
            adresaFarmacia.setText(c.getString(6));
            telefonFarmacia.setText(c.getString(5));
            if (llistaFotosFarmacies.get(codi) != null) {
                fotoFarmacia.setImageResource(llistaFotosFarmacies.get(codi));
            }
            else {
                fotoFarmacia.setImageResource(getResources()
                        .getIdentifier("foto_no_disponible" , "drawable", getActivity().getPackageName()));
            }
        }
        // Making a call phone
        telefonFarmacia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + telefonFarmacia.getText().toString().trim()));
                startActivity(callIntent );
            }
        });
        return root;
    }

    public void carregarFotosFarmacies () {
        llistaFotosFarmacies = new HashMap<String, Integer>();
        int totalFotos = 1;         int mesFotos = 0;
        String nomBaseFoto = "m";
        // Comptem  les fotos de la carpeta drawable
        do { // Retornarà 0 en acabar
            mesFotos = getResources().getIdentifier(nomBaseFoto + totalFotos++, "drawable", getActivity().getPackageName());
        } while (mesFotos != 0);
        // Per evitar l'index final
        totalFotos--;
        // Afegim les fotos a l HashMap: id integer - nom: m0, m1 etc
        // Posició 0 - m0; posició 1 - m1; etc !!!
        for (int i = 0; i < totalFotos; i++ ){
            llistaFotosFarmacies.put(nomBaseFoto + i,
                    getResources().getIdentifier(nomBaseFoto + i, "drawable", getActivity().getPackageName()));
        }
    }
}

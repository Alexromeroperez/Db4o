package com.arp.db4o.actividades;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.arp.db4o.R;
import com.arp.db4o.Servicio;
import com.arp.db4o.gestor.GestorDb4o;
import com.arp.db4o.pojo.Posicion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Posicion> posiciones;
    private GestorDb4o gdb;
    private GregorianCalendar fecha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i=new Intent(this,Servicio.class);
        startService(i);
        init();

    }

    private void init(){
        gdb=new GestorDb4o(this);
        posiciones=gdb.getRuta(new GregorianCalendar().getTime());
        gdb.close();
        Intent i=new Intent(MainActivity.this,MapsActivity.class);
        i.putExtra("posiciones",posiciones);
        startActivity(i);
    }

    public void fecha(View v){
        DialogFragment newFragment = new Datepicker();
        newFragment.show(getFragmentManager(), "Elige la fecha de la ruta");
    }

    public class Datepicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            fecha=new GregorianCalendar();
            fecha.set(year,dayOfMonth,dayOfMonth);
            posiciones=new ArrayList<>();

            posiciones=gdb.getRuta(fecha.getTime());
            Log.v("POSICIONES1",gdb.getRuta(fecha.getTime()).toString());
            Log.v("POSICIONES2",posiciones.toString());
            gdb.close();
            Intent i=new Intent(MainActivity.this,MapsActivity.class);
            i.putExtra("posiciones",posiciones);
            startActivity(i);
        }
    }
}

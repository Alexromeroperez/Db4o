package com.arp.db4o.gestor;

import android.content.Context;

import com.arp.db4o.pojo.Posicion;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.AndroidSupport;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alex on 06/03/2016.
 */
public class GestorDb4o {
    private ObjectContainer bd;
    private ObjectSet<Posicion> posiciones;

    public GestorDb4o() {
    }

    public GestorDb4o(Context c) {
        bd=Db4oEmbedded.openFile(dbConfig(), c.getExternalFilesDir(null) + "/bd.db4o");
    }

    public ArrayList<Posicion> getRuta(final Date d){
        posiciones=bd.query(new Predicate<Posicion>() {
            @Override
            public boolean match(Posicion posicion) {
                if (posicion.getFecha().getDay() == d.getDay()
                        && posicion.getFecha().getMonth() == d.getMonth()
                        && posicion.getFecha().getYear() == d.getYear())
                    return true;
                return false;
            }
        });
        ArrayList<Posicion> lista = new ArrayList();
        for (Posicion p : posiciones) {
            lista.add(p);
        }
        return lista;

    }

    public void insert(Posicion p) {
        bd.store(p);
        bd.commit();
    }

    public void close(){
        bd.close();
    }

    private EmbeddedConfiguration dbConfig(){
        EmbeddedConfiguration configuracion = Db4oEmbedded.newConfiguration();
        configuracion.common().add(new AndroidSupport());
        configuracion.common().activationDepth(25);
        configuracion.common().objectClass(GregorianCalendar.class).storeTransientFields(true);
        configuracion.common().objectClass(GregorianCalendar.class).callConstructor(true);
        configuracion.common().exceptionsOnNotStorable(false);
        configuracion.common().objectClass(Posicion.class).objectField("fecha").indexed(true);
        return configuracion;
    }
}

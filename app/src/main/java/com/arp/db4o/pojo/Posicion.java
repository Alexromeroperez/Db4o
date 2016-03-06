package com.arp.db4o.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Alex on 16/02/2016.
 */
public class Posicion implements Parcelable{
    private double longitud,latitud;
    private Date fecha;

    protected Posicion(Parcel in) {
        longitud = in.readDouble();
        latitud = in.readDouble();
    }

    public static final Creator<Posicion> CREATOR = new Creator<Posicion>() {
        @Override
        public Posicion createFromParcel(Parcel in) {
            return new Posicion(in);
        }

        @Override
        public Posicion[] newArray(int size) {
            return new Posicion[size];
        }
    };

    @Override
    public String toString() {
        return "Posicion{" +
                "longitud=" + longitud +
                ", latitud=" + latitud +
                ", fecha=" + fecha +
                '}';
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Posicion() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(longitud);
        parcel.writeDouble(latitud);
    }
}

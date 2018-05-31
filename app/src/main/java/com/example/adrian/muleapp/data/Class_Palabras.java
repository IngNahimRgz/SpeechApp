package com.example.adrian.muleapp.data;

public class Class_Palabras {
    String palabra;
    int veces_repetida;


    public Class_Palabras(String palabra, int veces_repetida) {
        this.palabra = palabra;
        this.veces_repetida = veces_repetida;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getVeces_repetida() {
        return veces_repetida;
    }

    public void setVeces_repetida(int veces_repetida) {
        this.veces_repetida = veces_repetida;
    }
}


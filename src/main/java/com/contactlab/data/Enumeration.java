package com.contactlab.data;

public enum Enumeration {

    ORDINECOMPLETATO("ordineCompletato"),
    LOGIN("login"),
    LOGOUT("logout"),
    CARTADICREDITO("carta di credito");


    private final String tipo;

    Enumeration(String tipo)
    {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
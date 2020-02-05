package com.contactlab.data;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Evento
{
    private String idEvento;
    private LocalDateTime data;
    private HashMap<String, String> nuoviValori;


    public Evento() {
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public HashMap<String, String> getNuoviValori() {
        return nuoviValori;
    }

    public void setNuoviValori(HashMap<String, String> nuoviValori) {
        this.nuoviValori = nuoviValori;
    }

    @Override
    public String toString() {
        return " Evento{" +
                "idEvento='" + idEvento + '\'' +
                ", data=" + data +
                ", nuoviValori=" + nuoviValori +
                '}';
    }
}


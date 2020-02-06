package com.contactlab.data;

public class DettaglioOrdine extends OrdineCompletato {

    private String orderLineId;
    private String id;
    private int quantita;
    private double price;
    private double tax;
    private String idEvento;

    public DettaglioOrdine() {
    }


    public String getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(String orderLineId) {
        this.orderLineId = orderLineId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    @Override
    public String toString() {
        return " DettaglioOrdine{" +
                "orderLineId='" + orderLineId + '\'' +
                ", id='" + id + '\'' +
                ", quantita=" + quantita +
                ", price=" + price +
                ", tax=" + tax +
                '}';
    }
}

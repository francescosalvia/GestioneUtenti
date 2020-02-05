package com.contactlab.data;

import java.util.List;

public class OrdineCompletato extends  Evento {

    private String orderId;
    private String storeCode;
    private String paymentMethod;
    private String shippingMethod;
    private Amount amounts;
    private List<DettaglioOrdine> dettaglioOrdini;


    public OrdineCompletato() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Amount getAmounts() {
        return amounts;
    }

    public void setAmounts(Amount amounts) {
        this.amounts = amounts;
    }

    public List<DettaglioOrdine> getDettaglioOrdini() {
        return dettaglioOrdini;
    }

    public void setDettaglioOrdini(List<DettaglioOrdine> dettaglioOrdini) {
        this.dettaglioOrdini = dettaglioOrdini;
    }


    @Override
    public String toString() {
        return " OrdineCompletato{" +
                "orderId='" + orderId + '\'' +
                ", storeCode='" + storeCode + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", amounts=" + amounts +
                ", dettaglioOrdini=" + dettaglioOrdini +
                '}';
    }
}

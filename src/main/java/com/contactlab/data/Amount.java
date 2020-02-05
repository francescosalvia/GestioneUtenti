package com.contactlab.data;

public class Amount {

    private double total;
    private double tax;

    public Amount() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "com.contactlab.data.Amount{" +
                "total=" + total +
                ", tax=" + tax +
                '}';
    }
}


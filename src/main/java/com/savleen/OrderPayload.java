package com.savleen;

public class OrderPayload {
    private String orderType; // buy, sell, or cancel
    private String currencyPair;
    private double price;
    private double quantity;

    public OrderPayload(String orderType, String currencyPair, double price, double quantity) {
        this.orderType = orderType;
        this.currencyPair = currencyPair;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderPayload{" +
                "orderType='" + orderType + '\'' +
                ", currencyPair='" + currencyPair + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}


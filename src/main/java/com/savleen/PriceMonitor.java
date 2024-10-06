package com.savleen;

public class PriceMonitor {

    private double triggerPrice;
    private String currencyPair;

    public PriceMonitor(double triggerPrice, String currencyPair) {
        this.triggerPrice = triggerPrice;
        this.currencyPair = currencyPair;
    }

    public void checkPrice(double marketPrice) {
        if (marketPrice <= triggerPrice) {
            // Prepare Buy order
            OrderPayload buyOrder = new OrderPayload("buy", currencyPair, marketPrice, 1.0);
            System.out.println("Buy Order Prepared: " + buyOrder);
        } else if (marketPrice >= triggerPrice) {
            // Prepare Sell order
            OrderPayload sellOrder = new OrderPayload("sell", currencyPair, marketPrice, 1.0);
            System.out.println("Sell Order Prepared: " + sellOrder);
        }
    }
}

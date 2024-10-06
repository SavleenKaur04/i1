package com.savleen;

import java.net.URI;
import java.util.Scanner;

import java.util.Scanner;

public class TradingApplication {


    public static void main(String[] args) {
        String apiKey = ApiConfig.getApiKey();
        String apiSecret = ApiConfig.getApiSecret();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the trigger price for BTC/USDT: ");
        double triggerPrice = scanner.nextDouble();

        // Set up the WebSocket connection and price monitor
        PriceMonitor priceMonitor = new PriceMonitor(triggerPrice, "BTCUSDT");
        try {
            URI uri = new URI("wss://stream.coindcx.com/ws");
            CoinDCXWebSocketClient client = new CoinDCXWebSocketClient(uri, priceMonitor, apiKey, apiSecret);
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

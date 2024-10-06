package com.savleen;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CoinDCXWebSocketClient extends WebSocketClient {

    private PriceMonitor priceMonitor;
    private String apiKey;
    private String apiSecret;
    public CoinDCXWebSocketClient(URI serverUri, PriceMonitor priceMonitor, String apiKey, String apiSecret) {
        super(serverUri);
        this.priceMonitor = priceMonitor;
         this.apiKey=apiKey;
        this.apiSecret=apiSecret;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket");
        // Subscribe to BTC/USDT market data
        String subscriptionMessage = "{\"event\": \"join\", \"pair\": \"BTCUSDT\"}";
        send(subscriptionMessage);
        String authMessage = createAuthMessage();
        send(authMessage);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);

        // Parse the market price from the message (assuming JSON format)
        JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
        if (jsonMessage.has("price")) {
            double marketPrice = jsonMessage.get("price").getAsDouble();
            priceMonitor.checkPrice(marketPrice);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket closed with reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error occurred: " + ex.getMessage());
    }

    private String createAuthMessage() {
        long timestamp = System.currentTimeMillis();
        String payload = "{\"event\":\"login\",\"api_key\":\"" + apiKey + "\",\"timestamp\":\"" + timestamp + "\"}";

        // Create a signature using HMAC SHA256
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] signatureBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String signature = Base64.getEncoder().encodeToString(signatureBytes);

            // Construct the final authentication message
            return "{\"event\":\"login\",\"api_key\":\"" + apiKey + "\",\"signature\":\"" + signature + "\",\"timestamp\":\"" + timestamp + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

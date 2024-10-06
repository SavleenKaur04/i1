package com.savleen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiConfig {

    private static Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieve the API Key and Secret from the properties file
    public static String getApiKey() {
        return properties.getProperty("coindcx.api.key");
    }

    public static String getApiSecret() {
        return properties.getProperty("coindcx.api.secret");
    }
}


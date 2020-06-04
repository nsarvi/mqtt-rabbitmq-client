package com.vmw.data.datatx.rabbitmq;

public class MqttUtils {

    public static int getPort() {
        Object port = System.getProperty("mqtt.port", "1883");
        return Integer.parseInt(port.toString());
    }


    public static String getHost() {
        return System.getProperty("rabbit.host", "gcp.rabbit");

    }

    public static String getMqttUser() {
        return System.getProperty("mqtt.user", "mqttuser");
    }

    public static String getMqttPwd() {
        return System.getProperty("mqtt.pwd", "mqttuser");
    }

}

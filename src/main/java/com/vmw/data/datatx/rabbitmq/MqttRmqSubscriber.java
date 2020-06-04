package com.vmw.data.datatx.rabbitmq;

import org.eclipse.paho.client.mqttv3.*;

import java.net.URI;
import java.net.URISyntaxException;

public class MqttRmqSubscriber implements MqttCallback {

     private final int qos = 1;
    private String topic = "demo/mqtt/test1";
    private MqttClient client;

    public MqttRmqSubscriber(String uri) throws MqttException, URISyntaxException {
        this(new URI(uri));
    }

    public MqttRmqSubscriber(URI uri) throws MqttException {
        String host = String.format("tcp://%s:%d", uri.getHost(), uri.getPort());
        String clientId = MqttClient.generateClientId();
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(MqttUtils.getMqttUser());
        conOpt.setPassword(MqttUtils.getMqttPwd().toCharArray());
        this.client = new MqttClient(host, clientId);
        this.client.setCallback(this);
        this.client.connect(conOpt);
        this.client.subscribe(this.topic, qos);
    }

    private String[] getAuth(URI uri) {
        String a = uri.getAuthority();
        String[] first = a.split("@");
        return first[0].split(":");
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        System.out.println("Message received :"+String.format("[%s] %s", topic, new String(message.getPayload())));
    }

    public static void main(String[] args) throws MqttException, URISyntaxException {
        MqttRmqSubscriber s = new MqttRmqSubscriber("tcp://"+MqttUtils.getHost()+":"+MqttUtils.getPort());
    }
}

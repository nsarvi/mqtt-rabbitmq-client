package com.vmw.data.datatx.rabbitmq;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MqttRmqClient {

    public static void main(String[] args) throws MqttException, InterruptedException {

        int noOfConnections=10000;
        List<MqttClient> connList=new ArrayList<>();

        for (int i=0;i<noOfConnections;i++) {

            MqttClient client = new MqttClient(
                    "tcp://"+MqttUtils.getHost()+":"+MqttUtils.getPort(),
                    MqttClient.generateClientId());

            System.out.println("Creating a connection object :" + client);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MqttUtils.getMqttUser());
            options.setPassword(MqttUtils.getMqttPwd().toCharArray());
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            //connect
            client.connect(options);
            // publish
            client.publish(
                        "demo/mqtt/test1", // topic
                        ("Payload data :" + i).getBytes(), // payload
                        1, // QoS
                        true);
            connList.add(client);
        }

        System.out.println("Waiting to disconnect :" );
        TimeUnit.MINUTES.sleep(5);
        for (int i=0;i<noOfConnections;i++) {
            connList.get(i).disconnect();
            System.out.println("Disconnected "+connList.get(i).toString() +"   :: "+i);

        }

    }

}

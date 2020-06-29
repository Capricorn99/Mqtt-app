package com.example.mqttapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    Button sendButton;
    EditText pubMess;
    Button options;
    Switch connectState;
    MqttAndroidClient client;
    public TextView mess;
    public String server;
    public String port;
    public String pubTopic ;
    public String subTopic ;
    int notiId = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mainService = new Intent(MainActivity.this, MainService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(mainService);
        } else {
            startService(mainService);
        }
        pubMess = findViewById(R.id.sendText);
        sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pubMess.getText().toString().equals("")) {
                    pub(pubTopic, pubMess.getText().toString());
                    pubMess.setText("");
                }
            }
        });
        options = findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdvancedOptions.class);
                intent.putExtra("server", server);
                intent.putExtra("port", port);
                intent.putExtra("pubTopic", pubTopic);
                intent.putExtra("subTopic", subTopic);
                startActivityForResult(intent, 1);
            }
        });
        mess = findViewById(R.id.mess);
        connectState = findViewById(R.id.connectState);
        connectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectState.isChecked()) {
                    connectMqtt();
                }
                else {
                    disconnect();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                server = data.getStringExtra("SERVER");
                port = data.getStringExtra("PORT");
                pubTopic = data.getStringExtra("PUBTOPIC");
                subTopic = data.getStringExtra("SUBTOPIC");
            }
        }
        connectMqtt();
    }

    public void connectMqtt(){
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(MainActivity.this, "tcp://" + server + ":" + port , clientId);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                mess.setText(message.toString());
                NotifyMng.callNotify4mess(MainActivity.this, notiId++, "gi cung duoc", "MQTT", message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("TYM", "onSuccess");
                    sub(subTopic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("TYM", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sub(String subTopic) {
        String topic = subTopic;
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("TYM", "subYes");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void pub(String pubTopic, String pubMess) {
        String topic = pubTopic;
        String payload = pubMess;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            IMqttToken disconToken = client.disconnect();
            disconToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // we are now successfully disconnected
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // something went wrong, but probably we are disconnected anyway
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

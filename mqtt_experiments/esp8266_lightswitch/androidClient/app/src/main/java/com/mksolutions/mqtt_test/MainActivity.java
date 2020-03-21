package com.mksolutions.mqtt_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    public enum SwitchStatus {
        on,
        off
    }

    MqttHelper mqttHelper;
    TextView dataReceived;
    TextView connStatus;
    String channel = "onoffswitch";
    SwitchStatus status = SwitchStatus.off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button switchButton = (Button) findViewById(R.id.switchButton);
            Button brokerConnectClick = (Button) findViewById(R.id.connectButton);

            dataReceived = (TextView) findViewById(R.id.buttonStatusText);
            connStatus = (TextView) findViewById((R.id.connStatusText));

            brokerConnectClick.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startMqtt();
                }
            });

            switchButton.setOnClickListener(onSwitchButtonClick);
        }
        catch(Exception e){
            return;            // Always must return something
        }
    }

    private View.OnClickListener onSwitchButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            try
            {
                SwitchStatus updatedStatus;
                if(status == SwitchStatus.on)
                {
                    updatedStatus = SwitchStatus.off;
                }
                else
                {
                    updatedStatus = SwitchStatus.on;
                }

                if(mqttHelper.mqttAndroidClient.isConnected()){
                    mqttHelper.publish(updatedStatus.toString());
                }
            }
            catch(Exception ex) {
                return;
            }

        }
    };

    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                connStatus.setTextColor(Color.GREEN);
                connStatus.setText("Connected");
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                status = SwitchStatus.valueOf(mqttMessage.toString());
                if(status == SwitchStatus.on)
                {
                    dataReceived.setTextColor(Color.GREEN);
                }
                else
                {
                    dataReceived.setTextColor(Color.RED);
                }
                Log.w("Debug", mqttMessage.toString());
                dataReceived.setText(status.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}




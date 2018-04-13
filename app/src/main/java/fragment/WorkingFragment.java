package fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import learn.shafi.mqttclient.R;


public class WorkingFragment extends Fragment {


    public static String URI = "";
    public static String PORT = "";
    public static String CLIENT_ID = "";
    public static String USERNAME = "";
    public static String AIO_KEY = "";

    String finalURI= "";

    TextView isConnectedTV;
    ImageView isConnectedIV;

    TextView light1OnOffStateTV;
    TextView light2OnOffStateTV;
    TextView light3OnOffStateTV;

    SwitchCompat light1Switch;
    SwitchCompat light2Switch;
    SwitchCompat light3Switch;

    ImageView liveLight1IV;
    ImageView liveLight2IV;
    ImageView liveLight3IV;

    ImageView liveLight1ApIV;
    ImageView liveLight2ApIV;
    ImageView liveLight3ApIV;


    MqttAndroidClient mqttAndroidClient;

   
    
    private String userName;
    private String port;
    private String uri;
    private String aioKey;
    private String clientID;


    private final String  TOPIC_ON_OFF_1 = "TOPIC_ON_OFF_1";  // topic pattern for adafruit is {username}/feeds/{topicName}
    private final String TOPIC_ON_OFF_2 = "TOPIC_ON_OFF_2";
    private final String TOPIC_ON_OFF_3 = "TOPIC_ON_OFF_3";
    
    private final String ON_OF_L1_STATE = "ON_OF_L1_STATE";
    private final String ON_OF_L2_STATE = "ON_OF_L2_STATE";
    private final String ON_OF_L3_STATE = "ON_OF_L3_STATE";
    
    public WorkingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_working, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.d("URI",URI);
        Log.d("PORT", PORT);
        Log.d("CLIENT_ID",CLIENT_ID);
        Log.d("USERNAME",USERNAME);
        Log.d("AIO_KEY",AIO_KEY);

        uri = "tcp://io.adafruit.com";
        port = "1883";
        finalURI = uri+":"+port;
        Log.d("FinalUri",finalURI);

        clientID = "testinlala";   // choose any unique id
        userName = "{your username in adafruit}";
        aioKey = "{your aio key in adafruit}";


//            uri = URI;
//            port = PORT;
//            finalURI = URI+":"+PORT;
//            Log.d("FinalURI",finalURI);
//            clientID = CLIENT_ID;
//            Log.d("Client_Id",clientID);
//            userName = USERNAME;
//            Log.d("Username",userName);
//            aioKey = AIO_KEY;
//            Log.d("Aio_Key",aioKey);


        liveLight1IV = view.findViewById(R.id.liveLight1IV);
        liveLight2IV = view.findViewById(R.id.liveLight2IV);
        liveLight3IV = view.findViewById(R.id.liveLight3IV);

        liveLight1ApIV = view.findViewById(R.id.liveLight1ApIV);
        liveLight2ApIV = view.findViewById(R.id.liveLight2ApIV);
        liveLight3ApIV = view.findViewById(R.id.liveLight3ApIV);

        isConnectedTV = view.findViewById(R.id.isConnectedTV);
        //isConnectedIV = view.findViewById(R.id.isConnectedIV);


        clientID = clientID+System.currentTimeMillis();

        mqttAndroidClient = new MqttAndroidClient(getContext(), finalURI, clientID);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                if (reconnect) {

                    String reconnectMessage = "Reconnected to "+finalURI;
                    isConnectedTV.setText(reconnectMessage);

                    //addToHistory("Reconnected to : " + serverURI);
                    // Because Clean Session is true, we need to re-subscribe
                    //subscribeToTopic();ru

                    Log.d("Reconnected","True");

                } else {
                   // addToHistory("Connected to: " + serverURI);
                    Log.d("Connected","True");
                    String connectMessage = "Connected to "+finalURI;
                    isConnectedTV.setText(connectMessage);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                //addToHistory("The Connection was lost.");

                String connectionLostMessage = "The connection was lost";
                isConnectedTV.setText(connectionLostMessage);

                Log.d("Disconnected","True");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
               // addToHistory("Incoming message: " + new String(message.getPayload()));


                Log.d("Topic",topic);
                Log.d("TopicMessage",message.toString());

                switch (topic){


                    case TOPIC_ON_OFF_1:

                        if(message.toString().equals("ON")){
                            liveLight1IV.setImageResource(R.drawable.lightbulb_on);
                        }else if(message.toString().equals("OFF")){
                            liveLight1IV.setImageResource(R.drawable.lightbulb_off);
                        }
                        break;

                    case TOPIC_ON_OFF_2:

                        Log.d("EnteronOff2","True");

                        if(message.toString().equals("ON")){
                            liveLight2IV.setImageResource(R.drawable.lightbulb_on);
                        }else if(message.toString().equals("OFF")){
                            liveLight2IV.setImageResource(R.drawable.lightbulb_off);
                        }
                        break;

                    case TOPIC_ON_OFF_3:

                        if(message.toString().equals("ON")){
                            liveLight3IV.setImageResource(R.drawable.lightbulb_on);
                        }else if(message.toString().equals("OFF")){
                            liveLight3IV.setImageResource(R.drawable.lightbulb_off);
                        }
                        break;

                    case ON_OF_L1_STATE:

                        if(message.toString().equals("1")){
                            liveLight1ApIV.setImageResource(R.drawable.lightbulb_on);
                        }else if(message.toString().equals("0")){
                            liveLight1ApIV.setImageResource(R.drawable.lightbulb_off);
                        }
                        break;
                    case ON_OF_L2_STATE:

                        if(message.toString().equals("1")){
                            liveLight2ApIV.setImageResource(R.drawable.lightbulb_on);
                        }else if(message.toString().equals("0")){
                            liveLight2ApIV.setImageResource(R.drawable.lightbulb_off);
                        }
                        break;
                    case ON_OF_L3_STATE:

                        if(message.toString().equals("1")){
                            liveLight3ApIV.setImageResource(R.drawable.lightbulb_on);
                        }else if(message.toString().equals("0")){
                            liveLight3ApIV.setImageResource(R.drawable.lightbulb_off);
                        }
                        break;

                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {


            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setUserName(userName);
        mqttConnectOptions.setPassword(aioKey.toCharArray());
        mqttConnectOptions.setCleanSession(false);


        try {

            String connectingMessage ="Connecting to "+finalURI;
            isConnectedTV.setText(connectingMessage);

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);

                    subscribeToTopic(TOPIC_ON_OFF_1);
                    subscribeToTopic(TOPIC_ON_OFF_2);
                    subscribeToTopic(TOPIC_ON_OFF_3);
                    subscribeToTopic(ON_OF_L1_STATE);
                    subscribeToTopic(ON_OF_L2_STATE);
                    subscribeToTopic(ON_OF_L3_STATE);

                    Log.d("Connected","True");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                   // addToHistory("Failed to connect to: " + serverUri);

                    String failedConnectionMessage = "Failed to connect "+ finalURI;
                    isConnectedTV.setText(failedConnectionMessage);
                    //Toast.makeText(getContext(), "Failed to connect\n"+finalURI, Toast.LENGTH_SHORT).show();
                    Log.d("FailedToConnect",exception.toString());

                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }


     // working with light control panel

        light1OnOffStateTV = view.findViewById(R.id.light1OnOffStateTV);
        light2OnOffStateTV = view.findViewById(R.id.light2OnOffStateTV);
        light3OnOffStateTV = view.findViewById(R.id.light3OnOffStateTV);


        light1Switch = view.findViewById(R.id.light1Switch);
        light2Switch = view.findViewById(R.id.light2Switch);
        light3Switch = view.findViewById(R.id.light3Switch);


        String onMessage = "ON";
        String offMessage = "OFF";

        light1Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    publishMessage(TOPIC_ON_OFF_1,"ON");
                    light1OnOffStateTV.setText(onMessage);
                }else {
                    publishMessage(TOPIC_ON_OFF_1,"OFF");
                    light1OnOffStateTV.setText(offMessage);
                }
            }
        });

        light2Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    publishMessage(TOPIC_ON_OFF_2,"ON");
                    light2OnOffStateTV.setText(onMessage);

                }else {
                    publishMessage(TOPIC_ON_OFF_2,"OFF");
                    light2OnOffStateTV.setText(offMessage);
                }
            }
        });

        light3Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    publishMessage(TOPIC_ON_OFF_3,"ON");
                    light3OnOffStateTV.setText(onMessage);
                }else {
                    publishMessage(TOPIC_ON_OFF_3,"OFF");
                    light3OnOffStateTV.setText(offMessage);
                }
            }
        });



    }


    public void subscribeToTopic(String topic){
        try {
            mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                   // addToHistory("Subscribed!");

                    //Toast.makeText(getContext(), "Subscribed to "+topic, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                  //  addToHistory("Failed to subscribe");
                    Toast.makeText(getContext(), "Failed subscribed to "+topic, Toast.LENGTH_SHORT).show();
                }
            });

            // THIS DOES NOT WORK!
//            mqttAndroidClient.subscribe(subscriptionTopic, 0, new IMqttMessageListener() {
//                @Override
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    // message Arrived!
//                    System.out.println("Message: " + topic + " : " + new String(message.getPayload()));
//                }
//            });

        } catch (MqttException ex){
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public void publishMessage(String publishTopic,String publishMessage){

        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(publishMessage.getBytes());
            mqttAndroidClient.publish(publishTopic, message);
            //addToHistory("Message Published");
           // Toast.makeText(getContext(), "Published to topic "+publishTopic, Toast.LENGTH_SHORT).show();
            if(!mqttAndroidClient.isConnected()){
                //addToHistory(mqttAndroidClient.getBufferedMessageCount() + " messages in buffer.");
                Toast.makeText(getContext(), mqttAndroidClient.getBufferedMessageCount()+" messages in buffer", Toast.LENGTH_SHORT).show();
            }
        } catch (MqttException e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
            Log.d("PublishError",e.toString());
            Toast.makeText(getContext(), "Error happened during published", Toast.LENGTH_SHORT).show();
        }


    }
}

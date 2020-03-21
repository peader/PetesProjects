#include <EEPROM.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>
 
// Connect to the WiFi
const char* ssid = "ssid";
const char* password = "password";
const char* mqtt_server = "broker ip";
 
WiFiClient espClient;
PubSubClient client(espClient);


int led = 5;     // LED pin
int button = 16; // push button is connected
int temp = 0; 
bool isOn = false;// temporary variable for reading the button pin status

void setup() {
  Serial.begin(9600); 
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  pinMode(led, OUTPUT);   // declare LED as output
  pinMode(button, INPUT); // declare push button as input
}

void loop() {
  
     if (!client.connected()) {
      reconnect();
     }
     client.loop();
  
     temp = digitalRead(button);
     
     if (temp == HIGH) {
        if(!isOn){
          isOn = true;
          digitalWrite(led, HIGH);
          Serial.println("LED Turned ON");
        }
        else
        {
          isOn = false;
          digitalWrite(led, LOW);
          Serial.println("LED Turned OFF");          
        }
     }
     delay(100);
}

void setup_wifi() {

  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}


void callback(char* topic, byte* payload, unsigned int length) {
     Serial.print("Message arrived [");
     Serial.print(topic);
     Serial.print("] "); 

    if (!strncmp((char *)payload, "on", length))
    {
      // ESP8266 light switched on"
      digitalWrite(led, HIGH);
      Serial.print("Mqtt light switched on");
    }

    if (!strncmp((char *)payload, "off", length))
    {
      digitalWrite(led, LOW);
      Serial.print("Mqtt light switched off");
    }
     
    
    Serial.println();
}

void reconnect() {
   // Loop until we're reconnected
   while (!client.connected()) {
     Serial.print("Attempting MQTT connection...");
     // Attempt to connect
     if (client.connect("ESP8266 Client")) {
        Serial.println("connected");
        // ... and subscribe to topic
        client.subscribe("onoffswitch");
     } else {
        Serial.print("failed, rc=");
        Serial.print(client.state());
        Serial.println(" try again in 5 seconds");
        // Wait 5 seconds before retrying
        delay(5000);
      }
   }
}

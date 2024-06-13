/*
  Make sure your Firebase project's '.read' and '.write' rules are set to 'true'. 
  Ignoring this will prevent the MCU from communicating with the database. 
  For more details- https://github.com/Rupakpoddar/ESP32Firebase 
*/

#include <ESP32Firebase.h>

#define _SSID "shadreck"          // Your WiFi SSID
#define _PASSWORD "22220000"      // Your WiFi Password
#define REFERENCE_URL "https://fourth-year-projects-default-rtdb.firebaseio.com/"  // Your Firebase project reference url

Firebase firebase(REFERENCE_URL);

int age=0;

// Define the analog pin where the gas sensor is connected
const int gasSensorPin = 34;  // GPIO34 (A2)
int humiditySensor=35;

//this is the code for the lcd display
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// Set the LCD address to 0x27 or 0x3F for a 16 chars and 2 line display
LiquidCrystal_I2C lcd(0x27, 16, 4);  // Change 0x27 to 0x3F if necessary


//this is the code for the temperature sensor
#include <OneWire.h>
#include <DallasTemperature.h>

// Data wire is connected to GPIO4
#define ONE_WIRE_BUS 4

// Setup a oneWire instance to communicate with any OneWire device
OneWire oneWire(ONE_WIRE_BUS);

// Pass oneWire reference to DallasTemperature library
DallasTemperature sensors(&oneWire);


//this is the code for the fans and bulbs

int bulb1=33;
int bulb2=25;
int bulb3=26;
int bulb4=27;
int fan2=14;
int fan1=12;
int humidifier=13;

bool fanb=false;
bool bulbb=false;

void setup() {
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(1000);

  //lcd code
  Wire.begin(21, 22);  // SDA, SCL pins for ESP32

  // Initialize the LCD
  lcd.init();
  lcd.backlight();

  // Print a message to the LCD.
  lcd.clear();
  lcd.setCursor(0, 0);  // Set the cursor to column 0, line 0
  lcd.print("E Kraal");
  lcd.setCursor(0, 1);  // Set the cursor to column 0, line 1
  lcd.print("Temperature: 0");
  lcd.setCursor(0, 2);  // Set the cursor to column 0, line 1
  lcd.print("Humidity   : 0");
  lcd.setCursor(0, 3);  // Set the cursor to column 0, line 1
  lcd.print("Air        : 0");

  // Connect to WiFi
  Serial.println();
  Serial.println();
  Serial.print("Connecting to: ");
  Serial.println(_SSID);
  WiFi.begin(_SSID, _PASSWORD);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print("-");
  }

  Serial.println("");
  Serial.println("WiFi Connected");

  // Print the IP address
  Serial.print("IP Address: ");
  Serial.print("http://");
  Serial.print(WiFi.localIP());
  Serial.println("/");

  //these are the codes for the sensors and the outputs like bulbs and fans
  pinMode(gasSensorPin, INPUT);
  pinMode(humiditySensor, INPUT);
  sensors.begin();

  //the codes for the fan and bulb
  
  pinMode(bulb1, OUTPUT);
  pinMode(bulb2, OUTPUT);
  pinMode(bulb3, OUTPUT);
  pinMode(bulb4, OUTPUT);
  pinMode(fan1, OUTPUT);
  pinMode(fan2, OUTPUT);



}

void loop() {
  
  sensors.requestTemperatures();
  
  // Fetch the temperature in Celsius
  float temperatureC = sensors.getTempCByIndex(0);
  // Fetch the temperature in Fahrenheit
  float temperatureF = sensors.getTempFByIndex(0);

  int gasSensorValue = analogRead(gasSensorPin);

  //here we are setting the bulb and fan high or low depending on the values of the temperature
   lcd.clear();
  lcd.setCursor(0, 0);  // Set the cursor to column 0, line 0
  lcd.print("E Kraal");
  lcd.setCursor(0, 1);  // Set the cursor to column 0, line 1
  lcd.print("Temperature: "+ String(temperatureC));
  lcd.setCursor(0, 2);  // Set the cursor to column 0, line 1
  lcd.print("Humidity   : " +String(analogRead(humiditySensor)));
  lcd.setCursor(0, 3);  // Set the cursor to column 0, line 1
  lcd.print("Air        : " + String(gasSensorValue));


  if(temperatureC<26){

    digitalWrite(bulb1, HIGH);
  }else if(temperatureC>30){

    digitalWrite(fan1, HIGH);
  }else{

    if(!fanb){

      digitalWrite(fan1, LOW);
      
    }

    if(!bulbb){

      digitalWrite(bulb1, LOW);
    }
  }


  // Check if the readings are valid


  if (temperatureC == DEVICE_DISCONNECTED_C) {
    Serial.println("Error: Could not read temperature data");
  } else {
    Serial.print("Temperature: ");
    Serial.print(temperatureC);
    Serial.println(" °C");
    firebase.setString("temperature", String(temperatureC)+" °C");
    firebase.setString("air", String(gasSensorValue));
    firebase.setString("humidity", String(analogRead(humiditySensor)));
    
    
  }

  String fanStatus = firebase.getString("fan1");
  if (fanStatus == "on") {
    digitalWrite(fan1, HIGH);  // Turn the fan on
    fanb=true;
  } else if (fanStatus == "off") {
    digitalWrite(fan1, LOW);  // Turn the fan off
    fanb=false;
  }
  
  String fan2Status = firebase.getString("fan2");
  if (fan2Status == "on") {
    digitalWrite(fan2, HIGH);  // Turn the fan on
    fanb=true;
  } else if (fan2Status == "off") {
    digitalWrite(fan2, LOW);  // Turn the fan off
    fanb=false;
  }

  String banStatus = firebase.getString("bulb1");
  if (banStatus == "on") {
    digitalWrite(bulb1, HIGH);  // Turn the fan on
    bulbb=true;
  } else if (banStatus == "off") {
    digitalWrite(bulb1, LOW);  // Turn the fan off
    bulbb=false;
  }
  
  String ban2Status = firebase.getString("bulb2");
  if (ban2Status == "on") {
    digitalWrite(bulb2, HIGH);  // Turn the fan on
    bulbb=true;
  } else if (ban2Status == "off") {
    digitalWrite(bulb2, LOW);  // Turn the fan off
    bulbb=false;
  }
  
  String ban3Status = firebase.getString("bulb3");
  if (ban2Status == "on") {
    digitalWrite(bulb3, HIGH);  // Turn the fan on
    bulbb=true;
  } else if (ban3Status == "off") {
    digitalWrite(bulb3, LOW);  // Turn the fan off
    bulbb=false;
  }
  
}

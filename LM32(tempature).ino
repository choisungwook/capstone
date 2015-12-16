void setup()
{
    Serial.begin(9600);//Set Baud Rate to 9600 bps
}

void loop()
{ 
    int val;
    int dat;
    val=analogRead(0);//Connect LM35 on Analog 0
    dat=(500*val)>>10;
    Serial.print("Tep:"); //Display the temperature on Serial monitor
    Serial.print(dat);
    Serial.println("C");
    delay(1000);
}

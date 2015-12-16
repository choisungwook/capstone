void setup() 
{
  Serial.begin(9600);
}

void loop()
{
  int value= analogRead(A0);//reads the analaog value from the methane sensor's AOUT pin
  Serial.print("value: ");
  Serial.println(value);//prints the methane value
  delay(1000);
}

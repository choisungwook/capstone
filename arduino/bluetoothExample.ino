/*
 Name:		Sketch1.ino
 Created:	11/13/2015 12:09:06 AM
 Author:	choisunguk
*/

// the setup function runs once when you press reset or power the board

#include "SoftwareSerial.h"

byte buffer[1024]; // 데이터를 수신 받을 버퍼
int bufferPosition; // 버퍼에 데이타를 저장할 때 기록할 위치
SoftwareSerial BTSerial(3, 2);

void setup() {
	Serial.begin(9600);
	Serial.println("Bluetooth exmaple start");
	BTSerial.begin(9600);
}

// the loop function runs over and over again until power down or reset
void loop() {
	while (BTSerial.available()){ // 블루투스로 데이터 수신
		byte data = BTSerial.read(); // 수신 받은 데이터 저장
		buffer[bufferPosition++] = data; // 수신 받은 데이터를 버퍼에 저장
		delay(10);
	}
		
	if (bufferPosition)
	{
		Serial.println("input data : ");

		for (int i = 0; i < bufferPosition; i++)
			Serial.write(buffer[i]);

		bufferPosition = 0;
	}
	

	
}


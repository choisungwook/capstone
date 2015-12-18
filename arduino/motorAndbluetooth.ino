/*
Name:		Sketch1.ino
Created:	11/13/2015 12:09:06 AM
Author:	choisunguk
*/

// the setup function runs once when you press reset or power the board
#include <DynamixelSerial\DynamixelSerial.h>
#include "SoftwareSerial.h"
#define SUFFIX 'E'
#define PREFIX 'S'
#define SERVODELAY	1
#define SERVOSPEED	20
#define END			3000

byte recvbuffer[1024]; // 데이터를 수신 받을 버퍼
int bufferPosition; // 버퍼에 데이타를 저장할 때 기록할 위치
SoftwareSerial BTSerial(3, 2); //TX, RX

void setup() {	
	Dynamixel.begin(1000000, 4);
	//Serial.begin(9600);
	BTSerial.begin(9600);	
	Dynamixel.move(1, 0);
	delay(100);
}

// the loop function runs over and over again until power down or reset
void loop() {
	while (BTSerial.available()){ // 블루투스로 데이터 수신
		byte data = BTSerial.read(); // 수신 받은 데이터 저장

		if (data == PREFIX)
		{
			bufferPosition = 0;
		}
		else if (data == SUFFIX)
		{
			//모터제어
			int angle = 0;

			//tmp 출력
			for (int i = 0; i < bufferPosition; i++)
			{
				angle *= 10;
				angle += (recvbuffer[i] -'0');
			}

			Dynamixel.move(1, angle);
			Serial.println(angle);

		}
		else
		{
			recvbuffer[bufferPosition++] = data;
		}


	}

	/*if (bufferPosition)
	{

	for (int i = 0; i < bufferPosition; i++)
	Serial.write(buffer[i]);

	bufferPosition = 0;
	}*/



}


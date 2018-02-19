# Zeitmessung Grossanzeige

Selbstgebaute Grossanzeige für ALGE Zeitmessungen. 
Die Grossanzeige kann über RS-232 mit den folgenden ALGE Zeitmessungen betrieben werden:
* ALGE OPTIc 
* ALGE Video Timer 

## Hardware Aufbau

Die GAZ ist intern mit eine Raspberry PI Control Board ausgerüstet. Dieses liest das RS-232 Signal und steuert über die SPI Schnittstelle einen 
MAX 7219 7-SEG Kontroller an. 

Die einzelnen 7-Segment Digits sind auf einzelnen Platinen aufgebaut. 
Jeweils 5 einzelne LED bilden ein Segment. Die LED werden direkt über die externe 12 V Speisung angesteuert und über
ein SN74LS00 Open Kollektor auf Ground gezogen um diese einzuschalten. 
Ein SN74LS08 wird verwendet um die Digit und Segment Steuersignale miteinander zu verknüpfen.

Ein DC/DC Wandler (TEM 3-1211N) wandelt die externe Speisung von 12 V auf 5 V. Dieses wird für das Raspberry PI und die Speisung der TTL Logik Bausteine verwendet.

## Software

Die Software besteht aus einen Java Program, welches die [pi4j](http://pi4j.com/) Bibliothek verwendet um das RS 232 Signal zu lesen und die SPI Schnittstelle zum 
7-Segment Kontroller anzusteuern.

## Bilder




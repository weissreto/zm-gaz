Zeitmessung Grossanzeige
========================

Hardware Konfiguration Raspberry PI:
------------------------------------

Um die Serielle Schnittstelle für die Kommunikation mit den ALGE Timers 
benutzen zu können muss man diese zuerst konfigurieren:

1. Starte eine Konsole
2. Starte das Kommando raspi-config

  > raspi-config
  
3. Wähle die Option: 7 Advanced Options
4. Wähle die Option: A8 Serial
5. Wähle die Option: No
6. Wähle die Option: Save
7. Starte einen Editor um die Datei boot/config.txt zu bearbeiten:

  > sudo nano /boot/config.txt
  
8. Editiere folgende Zeile:

  enable_uart=0
  
um in :
  
  enable_uart=1
  
9. Speichere die Änderung mit CTRL-X, dann y für yes, dann Enter

Installation:
-------------

Um die Software für die Grossanzeige zu installieren gehe wie folgt vor:
1. Erstelle das Verzeichnis /opt/zm-gaz
2. Kopiere die Zip Datei zm-gaz-*.zip ins Verzeichnis /opt/zm-gaz
3. Entpacke die Datei
4. Starte eine Konsole
4. Gehe ins Verzeichnis /opt/zm-gaz/zm-gaz-*.zip/bin
6. Starte die Datei InstallService.sh mit 
  
  > sudo ./InstallService.sh
  
7. Folge den Anweisungen um den gaz.service Service zu installieren.

Der gaz.service wird nun automatisch beim Systemstart gestartet. 

gaz.service
-----------

Der gaz.service ist ein systemd Service. Er beinhaltet die Software für die Grossanzeige. 
Der Service wird beim Systemstart automatisch gestarted. 

Der Zustand des gaz.service kann mit: 
  
  > sudo systemctl status gaz.service
  
angezeigt werden.

Er kann mit:

  > sudo systemctl stop gaz.service
  
gestoppt werden und mit 

  > sudo systemctl start gaz.service
  
gestartet werden.

gaz Kommando
------------

Die Software für die Grossanzeige kann auch aus der Konsole direkt gestartet werden. Dies geschieht mit

  > ./gaz 

Das Kommando ist vor allem für das Debuggen oder für neu Entwicklungen hilfreich. 


Das Zeitmessteam des Turnverein Küssnacht wünscht viel Spass mit der Grossanzeige!
 

 
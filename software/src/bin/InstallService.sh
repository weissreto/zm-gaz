#!/bin/sh

# -----------------------------------------------------------------------------
# Description:
# -----------------------------------------------------------------------------
# Zeitmessung Grossanzeige systemd service installer
# -----------------------------------------------------------------------------

USER=`whoami`
if [ "$USER" != "root" ]
then
  echo "Du musst root sein um dieses Script auszufuehren! Gebrauche das folgende Kommando: sudo InstallService.sh"
  exit 0
fi

GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

cd ..
INSTALLDIR=`pwd`
cd bin

echo ""
printf "Willkommen zur Zeitmessung Grossanzeige systemd Service Installer!\n"
echo ""


echo "---"
echo "Bitte waehlen Sie das ZM GAZ installation Verzeichnis aus"
read -p "Waehle Verzeichnis: [$INSTALLDIR"] SELECTEDDIR
if [ -z $SELECTEDDIR ]
then
  echo ""
else
  INSTALLDIR=$SELECTEDDIR
  echo ""
fi


echo "---"
REAL_USER=`ls -ld $INSTALLDIR | awk '{print $3}'`
echo "Bitte waehlen Sie den Benutzer aus unter welchem der ZM GAZ Service laufen soll (darf nicht root sein)"
read -p "Waehle Benutzer: [$REAL_USER"] SELECTEDUSER
if [ -z $SELECTEDUSER ]
then
  echo ""
else
  REAL_USER=$SELECTEDUSER
  echo ""
fi

echo "---"
REAL_GROUP=`ls -ld $INSTALLDIR | awk '{print $4}'`
echo "Bitte waehlen Sie die Gruppe aus unter welchem der ZM GAZ Service laufen soll"
read -p "Waehle Gruppe: [$REAL_GROUP"] SELECTEDGROUP
if [ -z $SELECTEDGROUP ]
then
  echo ""
else
  REAL_GROUP=$SELECTEDGROUP
  echo ""
fi

echo "Installiere ZM GAZ Service..."
echo ""
echo "- Konfiguriere Pfad und Benutzer/Gruppe im Service Script"

sed "s|/<installationdir>|$INSTALLDIR|g" gaz.service > gazConfigured1.service
sed "s|<user>|$REAL_USER|g" gazConfigured1.service > gazConfigured2.service
sed "s|<group>|$REAL_GROUP|g" gazConfigured2.service > gazConfigured3.service
printf "...${GREEN}erledigt${NC}\n"

echo "- Kopiere Service Script nach '/etc/systemd/system'"

if [ -d "/etc/systemd/system" ]
then
  cp gazConfigured3.service /etc/systemd/system/gaz.service
  printf "...${GREEN}erledigt${NC}\n"
  echo "- Aufraeumen"
  rm gazConfigured1.service
  rm gazConfigured2.service
  rm gazConfigured3.service
  printf "...${GREEN}Erledigt${NC}\n"
  echo "- Lade systemd Daemon neu"
  systemctl daemon-reload
  printf "...${GREEN}erledigt${NC}\n"
  echo "- Konfiguriere, dass der ZM GAZ Service beim booten gestarted wird"
  systemctl enable gaz.service
  printf "...${GREEN}erledigt${NC}\n"
  
  echo ""
  echo "gaz.service wurde erfolgreich installiert"
  printf "Du kannst den Service mit '${GREEN}sudo systemctl start gaz.service${NC}' in der Konsole starten\n"
  printf "Um den Service zu stoppen benutze '${GREEN}sudo systemctl stop gaz.service${NC}'"
  echo "Viel Spass!"

else
  printf "Das Verzeichnis '/etc/systemd/system' existiert nicht, ${RED}breche Installation ab${NC}. Unterstuetzt deine Linux Distribution systemd?\n"
  exit 1
fi

echo ""

exit 0
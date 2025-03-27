---
layout: post
title: How to turn a Raspberry pi into a website kiosk
tags: 
 - Raspberry 
 - systemd
 - kiosk
 - website
---

I recently had the need to display a website on a spare monitor. The challenge was that I needed to show the website in full screen and the whole setup should persist even after a reboot of the computer running the browser.

I had a spare raspberry pi lying around so I decided to use that for the browser.
In order to automatically launch the browser and navigate to the website I wanted I used a bash script together with a service running under systemd.

It turns out you actually need to run gui programs under a user account so the default /etc/systemd folder was not what I wanted to use. Instead I had to place the service description file in the /etc/systemd/user/ folder and enable and start it using the "--user" flag of systemd:
``` bash
systemd --user enable myservice.service
```

Odds are if I ever want to do something similar in the future I won"t remember how, so I created a little bash installation script that automates the process:

``` bash
#!/bin/bash

# Prompt user for webpage name
read -p "Enter the webpage name (e.g., example.com): " WEBPAGE_NAME

# Extract root domain from webpage name
ROOT_DOMAIN=$(echo "$WEBPAGE_NAME" | sed -E 's/https?:\/\/([^\/]+).*/\1/' | tr '.' '-')

# Create launcher script
LAUNCHER_SCRIPT="/opt/start-$ROOT_DOMAIN.sh"
sudo echo "#!/bin/bash" > "$LAUNCHER_SCRIPT"
sudo echo "chromium-browser --kiosk $WEBPAGE_NAME" >> "$LAUNCHER_SCRIPT"
sudo chmod +x "$LAUNCHER_SCRIPT"

# Create service file in user's systemd folder
SERVICE_FILE=/etc/systemd/user/launch-$ROOT_DOMAIN.service
echo "[Unit]" > "$SERVICE_FILE"
echo "Description=Start $ROOT_DOMAIN kiosk" >> "$SERVICE_FILE"
echo "After=network.target" >> "$SERVICE_FILE"
echo "" >> "$SERVICE_FILE"
echo "[Service]" >> "$SERVICE_FILE"
echo "Type=Simple" >> "$SERVICE_FILE"
echo "ExecStart=/opt/start-$ROOT_DOMAIN.sh" >> "$SERVICE_FILE"
echo "Restart=on-failure" >> "$SERVICE_FILE"
echo "RestartSec=30" >> "$SERVICE_FILE"
echo "" >> "$SERVICE_FILE"
echo "[Install]" >> "$SERVICE_FILE"
echo "WantedBy=default.target" >> "$SERVICE_FILE"

# Reload systemd daemon and enable service
systemctl --user enable launch-$ROOT_DOMAIN.service

echo "Launcher script and service created for $ROOT_DOMAIN!"
echo "Reboot your computer to start the kiosk."
```

**Note: ** for some reason I had to enable the service again as the command in the script did not work the first time:

``` bash
systemctl --user enable launch-$ROOT_DOMAIN.service
```

Hopefully future me or someone out there finds it useful :)

P.S. Codeium wrote the boiler plate code for me, well tried to at least. I had to correct it in places but it was useful for the majority of what I wanted but you really need to know what you want.
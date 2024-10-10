---
layout: post
title: Logarex Smart Meter IR Interface
---
## Background
We recently had a logarex smart electricity meter installed in our house to replace the old analogue one. This type of meter comes with an IR interface that you can query power comsumption values with. With a little help from an esp8266 and a IR reader/writer we should be able to make these values availabe on our home network.

## How to
1. Clone the tasmota repository (this is the firmware we'll use on our esp8266) with this command:
``` powershell
git clone https://github.com/arendst/Tasmota.git
```
2. Checkout the latest stable release. For me this was v12.2.0:
``` powershell
git checkout v12.2.0
```
3. Open the repositories root folder in visual studio code.
4. Install the platform io extension.
5. plug in your esp8266 board (make sure to choose the correct usb port that maps to the correct com port)
![_config.yml]({{ site.baseurl }}/images/config.png)
6. Flash the esp8266 with the new firmware.


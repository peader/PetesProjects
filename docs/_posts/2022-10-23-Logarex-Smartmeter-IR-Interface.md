---
layout: post
title: Logarex Smart Meter IR Inteface
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
5. 
![_config.yml]({{ site.baseurl }}/images/config.png)

The easiest way to make your first post is to edit this one. Go into /_posts/ and update the Hello World markdown file. For more instructions head over to the [Jekyll Now repository](https://github.com/barryclark/jekyll-now) on GitHub.
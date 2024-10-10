---
layout: post
title: Kubernetes Home Lab
---
## Background
I recently changed positions and moved from a pure software engineering role to a devops one. I've been working on devops task throughout my software development career and have always found them to be very rewarding hence the move. Plus I still inevitably have programming tasks when we need to customize a piece of software or create a completely custom tool (win win :)).
I want to hone my devops skills so I thought a home lab would be in order. This post aims to document the process of creating a kubernetes control node on a Raspberry pi 5 I recently purchased.

Here's the list of things I want to achieve in this session:
- Flash the pi 5 sd card with a suitable OS
- Setup the pi 5 with ssh.
- Install kubernetes on the pi 5.

We'll use Raspberry Pi Imager to flash the pi's sd card with the pi's OS. Imager has the added benefit of allowing us to already set the wlan user and password as well as add a public ssh key for easy access once setup.

My laptop OS is windows so I'll generate a ssh key and copy the content of my public key to the pi's OS (via the window in pi imager)

```
ssh-keygen
cat ~/.ssh/id_rsa.pub | set-clipboard
```

Paste the copied key into the relevant window in pi imager.

Everything's setup so I can directly ssh onto my pi which I called control-node with the following command:

```
ssh peter@control-node
```
and we're in :)

Next up we need to install kubernetes on the node:

- Append the following cgroup settings to the  "/boot/cmdline.txt" file:
```
cgroup_memory=1 cgroup_enable=memory to
```
- download and install k3s (the optimized version of kubernetes for lower spec hardware):
```
sudo curl -sfL https://get.k3s.io | sh -
```
- Test that everything is working:
```
sudo kubectl get node
```
You should get the following output:
```
NAME           STATUS   ROLES                  AGE     VERSION
control-node   Ready    control-plane,master   2m39s   v1.30.4+k3s1
```

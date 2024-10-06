---
layout: post
title: Devops Coding Challenge
---

So I've been working as a Devops engineer professionally for almost a year now. I've learned a lot and I want to put those skills to the test. 
I'm challenging myself to:
- Deploy a development environment with an IAC (Infrastructure as Code) tool.
- Create a test application comprising of a simple rest api backend and a html/javascript front end.
- Create a test, staging and production environment in which to deploy this test application.
- Have some form of monitoring on the health of each of these stages and the test application running within them.

Here we Go!

## The infrastructure 
I'm going to use ansible for as my IAC software. It's what I've been using in work but also it's lightweight and only requires that I have an ssh connection to the host I want to provision. 
Prerequisites:
- ssh server installed on the target host
- ansible cli installed on the host from which I'll be deploying. Both happen to be the same machine for this challenge.

### Challenges
This part of the challenge was relatively straight forward, at least the ansible usage was. Ansible has a really neat feature where you can set the host to "localhost" and simply run the playbook.
I did encounter some issues getting docker, act and kubernetes installed on the raspberry pi. These issues all boiled down to the architecture (arm64) not being the standard architecture to deploy these tools onto. Also the raspberry pi does not come with cgroups enabled as standard so that's something you need to explicitly set  


## The application 
For the backend restapi server I chose python and flask. I use python quite a bit in work and like it's simplicity. 
For the frontend I chose nginx together with it's builtin index.html to call and display results from the backend server. 
The application itself displays moose facts at random from an array of moose facts stored on the backend. 
I created docker images for both the frontend and backend services. 

### Challenges
Developing the application locally was relatively straight forward. Issues arose when I tried integrating the application into kubernetes:
- the kubernetes network could not use my local docker image cache. I therefore needed to deploy a local docker registry, push application images to it and then reference these images in my kubernetes deployment files.
- Calling the backend services from my frontend webapp required me to hardcode the backend services IP address in the frontend application and also open a routed directly to the front end. I didn't want to do either of those things so I used the nginx pass_proxy feature to proxy requests from the nginx server to an internal location on my kubernetes cluster. This worked really well but I spent hours pulling my hair out because it wasn't working only to realize my browser had cached an older rest api endpoint location. Hitting shift+f5 solved that annoyance. 


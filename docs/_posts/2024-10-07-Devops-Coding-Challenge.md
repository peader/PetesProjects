---
layout: post
title: Devops Coding Challenge
---

So I've been working as a Devops engineer professionally for almost a year now. I've learned a lot and I want to put those skills to the test. 
This challenge is:
- Deploy a development environment with an IAC (Infrastructure as Code) tool.
- Create a test application comprising of a simple rest api backend and a html/javascript front end.
- Create a test, staging and production environment in which to deploy this test application.
- Setup a ci pipeline to automate the building and deployment of the application. 
- Have some form of monitoring on the health of the application versions in each of the environments.
- Have the entire solution run locally on my raspberry pi 5.

Here we Go!

## The infrastructure 
I'm going to use ansible as my IAC software. It's what I've been using in work but also it's lightweight and only requires that I have an ssh connection to the host I want to provision. 
Prerequisites:
- ssh server installed on the target host
- ansible cli installed on the host from which I'll be deploying. Both happen to be the same machine for this challenge.

### Challenges
This part of the challenge was relatively straight forward, at least the ansible usage was. Ansible has a really neat feature where you can set the host to "localhost" and simply run the playbook.
I did encounter some issues getting docker, act and kubernetes installed on the raspberry pi. These issues all boiled down to the architecture (arm64) not being the standard architecture to deploy these tools onto. Another issue was that the raspberry pi does not come with cgroups enabled as standard so that's something you need to explicitly set for docker to function. 

## The application 
For the backend restapi server I chose python and flask. I use python quite a bit in work and like it's simplicity. 
For the frontend I chose nginx together with it's builtin index.html to call and display results from the backend server. 
The application itself displays moose facts at random from an array of moose facts stored on the backend. 
I created docker images for both the frontend and backend services and pushed these built images to a local registry for later reference from the kubernetes deployment. 

### Challenges
Developing the application locally was relatively straight forward. Issues arose when I tried integrating the application into kubernetes:
- the kubernetes network could not use my local docker image cache. I therefore needed to deploy a local docker registry, push application images to it and then reference these images in my kubernetes deployment files.
- Calling the backend services from my frontend webapp required me to hardcode the backend services IP address in the frontend application and also open a route directly to the front end. I didn't want to do either of those things so I used the nginx pass_proxy feature to proxy requests from the nginx server to an internal location on my kubernetes cluster. This worked really well but I spent hours trying to figure why my changes weren't showing up in the browser only to realize my browser had cached an older rest api endpoint location. Hitting shift+f5 solved that annoyance. (note to my future self :-) )

## Test environment 
I wanted to use kubernetes to deploy my application into 3 distinct environments (dev, stg and prod). Although this is probably not necessary for a single server and docker compose files would likely have been sufficient I wanted to practice my kubernetes skills.
- The first step was to create deployment and sevice yaml files for the backend and frontend parts of the application.
- Once I got this working I wanted to convert those files into templates which, together with the use of helm, could be used to create distinct environments by only changing a limited set of variables.

### Challenges
This part of the challenge went surprisingly smoothly. Helm was a very easy tool to work with and I have the advantage of already having experience with jinja (the templating framework that helm is built on top of)

## CI/CD Pipeline
Originally I wanted to use gitlab and a gitlab runner as my ci/cd framework. I quickly realized that this solution, although feature rich, would be too time consuming to get running, not to mention it doesn't support installation on arm64 architecturea as standard. In the end I went with GitHub local actions. This is a neat CLI tool which allows you to run GitHub actions on your local machine.

### Challenges
- Although I've used GitHub actions in the past it had been a while so there was a small learning curve to get back up to speed.
- Another arm64 problem popped up. Namely, the widely adopted helm GitHub actions module does not support this architecture. I needed to download the modules source code and explicitly target the arm64 helm binaries to get it to work

## Monitoring 
I used the open source Kuma Uptime as my monitoring solution. I was limited in the time I had left to complete the challenge and therefore setup the monitoring dashboards manually.

### Challenges
It wasn't clear to me if it's possible to provision Kuma Uptime with monitoring dashboards during the softwares initiation phase. As a possible upgrade to the project I would swap out Kuma Uptime with grafana and make the provisioning part of the setup process.

## Conclusion 
I had a lot of fun putting this project together. It gives me confidence in my skillset and especially with regards to on premise solutions. 
The project code can be found here: https://github.com/peader/tech-challenge



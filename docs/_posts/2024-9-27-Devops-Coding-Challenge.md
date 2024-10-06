---
layout: post
title: Devops Coding Challenge
---

So I've been working as a Devops engineer professionally for almost a year now. I've learned a lot and I want to put those skills to the test. 
I'm challenging myself to:
- Deploy a development environment with an IAC (Infrastructure as Code).
- Create a test application comprising of a simple rest api backend and a html/javascript front end.
- Create a test, staging and production environment in which to deploy this test application.
- Have some form of monitoring on the health of each of these stages and the test application running within them.

Here we Go!

First up is setting up the prerequisite infrastructure. I'm gunna use ansible for this as it's what I've been using in work but also it's lightweight and only requires that I have an ssh connection to the host I want to provision. 
Prerequisites:
- ssh server installed on the target host
- ansible cli installed on the host from which I'll be deploying. Both happen to be the same machine in for this challenge.

I'll create a folder called infrastrure and 
---
layout: post
title: Enabling HTTPS on services running in Home Network
---

## The Problem
You've installed some web service on a server running in your home network e.g. hedgedoc running on a raspberry pi. Great, the world is bright and you're feeling good about yourself. You're excited and want to show your family. You send them the link to the webserver e.g. the ip address. Here begin the headaches.

- "Why can't I use a normal website name...?"
- "Why is it warning me that the website isn't trusted"

And suddenly your project is not so shiny anymore and the hurdle to get your family to use it is too big. You're dead in the water.

This has been my experience for the longest time. I need these services to just work but therefore I need domain names and https to work. Custom domain names are relatively easy to solve with a custom DNS like pi-hole with custom dns entries. https is more difficult. The issue there is the certificates.

For certificates you have a few options:
1.) Pay for a custom certificate and install it in front of your service
2.) Automate lets encrypt certificate generation via the HTTP-01 challenge
3.) Automate lets encrypt certificate generation via DNS-01 challenge

Option 1 is expensive and we would need to renew the certificate manually every 6 months. This renewal period gets shorter every year.

Option 2 would require me to open my service to the internet with a public IP address. This is really not an option as I really don't want to expose my little services to the shit show that is the open internet atm.

Option 3 is the only viable option really and I've tried to setup this up in the past with nginx and adding a custom TXT record to my DNS service providers, "namecheap", portal. This didn't work and I gave up until recently. In the next section I'll describe how I finally got it working.  

## Caddy to the rescue!!
I've been aware of the existence of Caddy for years but I only just recently looked into it in more depth. Holy shit those guys make the whole HTTPS topic easier. Out of the box Caddy supports certificate generation and usage via HTTPS-01 (option 2 that we discussed earlier). They also support certificate generation via DNS-01 challenges but this is not included in the core package.

Not to worry though we can grab the custom Caddy variant from their docker hub. First off we need to identify our DNS service provider, in my case "namecheap".
Here is the image I used: [namecheap](https://hub.docker.com/r/caddybuilds/caddy-namecheap)

### Namecheap setup
We need to navigate to the namecheap portal and setup api access. 
**Note: ** I needed to add a balance of $50 to be eligible to use their api.

- go to the tools section of the namecheap portal [tools](https://ap.www.namecheap.com/settings/tools/apiaccess/)

- Enable api access

- Add any public ip (not required for our cert but required for the api to function)

- note down the api key and your namecheap username (that's the name you use to login)

### Local setup
My local setup consists of the application docker containers and the caddy docker container all on the same docker network.
**Note: ** Be sure that the application docker container is not bound to port 80. caddy will need access to that.

- ssh onto your services host machine and perform the following:
``` sh
mkdir caddy
touch caddy/docker-compose.yml
touch caddy/Caddyfile
```

- Paste following into the Caddyfile you just created:
``` txt
house.petesprojects.net {
    tls {
          dns namecheap {
          api_key {env.NAMECHEAP_API_KEY}
          user {env.NAMECHEAP_API_USER}
          }
        }
    reverse_proxy app:80
}
```

**Side note: ** The domain name for this blog is issued by namecheap and because I'm cheap I'm going to reuse that domain "petesprojects.net" as a wild card for my internal services. Obviously you will need to change the domain name to one that you own.

- Paste the following into the docker compose file, ensuring to update the namecheap environment variables:

``` yml
services:
  caddy:
    image: caddybuilds/caddy-namecheap:latest
    networks:
      - appnetwork
    restart: unless-stopped
    cap_add:
      - NET_ADMIN
    ports:
      - "80:80"
      - "443:443"
      - "443:443/udp"
    volumes:
      - $PWD/Caddyfile:/etc/caddy/Caddyfile
      - $PWD/site:/srv
      - caddy_data:/data
      - caddy_config:/config
    environment:
      - NAMECHEAP_API_KEY=<your namecheap api key>
      - NAMECHEAP_API_USER=<your namecheap user name>

volumes:
  caddy_data:
  caddy_config:
networks:
  appnetwork:
    internal: false
    name: app_network
```

**Note: ** Make sure that your application container has access to the same docker network i.e. "app_network" 

- Start the caddy container:
``` sh
cd caddy
docker compose up -d
```

### Custom DNS setup
As I mentioned in the introduction we need some way for the clients running in our local network to know where to go when looking for "house.petesprojects.net". I happen to have a pi-hole instance already running in my home network so all I needed to do was go to the dns records section of my pi-hole instance:
http://<pi-hole-ip>/admin/settings/dnsrecords and add a row with the "house.petesprojects.net" as the domain and the ip of my application server as the ip.

**Note: ** I restarted my router in order to force all the clients in my home network to see the new dns entry.

## Conclusion
Now when I'm home and I open a browser to "home.petesprojects.net" I don't get any warnings, no custom action needs to be taken. It's just smooth surfing. :)

FIN!

![HTTPS]({{ site.baseurl }}/images/localhttps/https.png)



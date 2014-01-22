# Setting up a Clojure Webserver on an AWS EC2 Ubuntu 12.04 instance

This is pretty straightforward, but just wanted to keep it somewhere for the
future.

## Instance setup

It's not a bad idea to start with upgrading everything.

```bash
 $ sudo apt-get update
 $ sudo apt-get upgrade
 $ sudo reboot
 
```

## Clojure setup

Next, we need to install Java. I prefer to go with Oracle's
Java instead of openjdk.

```bash
 $ sudo apt-add-repository ppa:webupd8team/java
 $ sudo apt-get update
 $ sudo apt-get install oracle-java7-installer
 
```

Now we get leiningen.

```bash
 $ mkdir ~/source
 $ cd ~/source
 $ wget https://raw.github.com/technomancy/leiningen/stable/bin/lein
 $ chmod a+x lein
 $ sudo ln -s ~/source/lein /usr/local/bin/lein
 $ lein
 
```

Things should be good to go on the Clojure side!

## GitHub setup

We're going to assume that the project is saved on GitHub (all mine would be).
I'm just going to put my email.  It's already in my footer, so spam bots come at
me!

```bash
 $ sudo apt-get install git
 $ cd ~/.ssh
 $ ssh-keygen -t rsa -C "troyastorino@gmail.com"
 $ ssh-add id_rsa
 $ cat id_rsa
 
```
Copy the output and add it as an ssh key to your GitHub settings.

## Nginx config

This consists of creating site configurations in /etc/nginx/sites-available and
then creating symbolic links from /etc/nginx/sites-enabled to there. A basic
config will look something like this:

```plain
server {
  listen 80;
  server_name troyastorino.com;
  
  location / {
    proxy_pass http://127.0.0.1:3000/;
  }
  
  location ~* ^.+\.(jpg|jpeg|gif|png|ico|css|zip|tgz|gz|rar|bz2|doc|xls|exe|pdf|ppt|txt|tar|mid|midi|wav|bmp|rtf|js)$ {
    root ~/troyastorino/resources/public;
  }
  
}

```

You start nginx with 

```bash
 $ sudo /etc/init.d/nginx start
 
```

The rest you can figure out.

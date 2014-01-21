# Sending email from your local system on OS X 10.8

During local development of a website, I wanted to make sure I was sending
confirmation emails and the like properly. I was using python on the server for
the website, and started to use a combination of the
[smtplib](http://docs.python.org/2/library/smtplib.html) and
[email](http://docs.python.org/2/library/email.html) modules. The python
documentation has
[great examples](http://docs.python.org/2/library/email-examples.html) of how to
use these to send email, so I won't repeat them here.

Getting my setup to work properly would have something like this run successfully from
the python shell:
```
>>> import smtplib
>>> s = smtplib.SMTP('localhost')
>>> s.sendmail('from.address@email.com', '<your email address>', 'This is a test email')
{}
```
and it would cause a email to show up in your email inbox!

First, I discovered that PostFix was the built-in SMTP server on OS
X. So a simple 

```
$ sudo postfix start
```
should have gotten me going, right? Unfortunately not, so I'm writing this post
to hopefully prevent some of my pain in the future.

First, I got the error
```
postfix: fatal: chdir(/Library/Server/Mail/Data/spool): No such file or directory
```
That had a pretty easy fix.
```
$ sudo mkdir -p /Library/Server/Mail/Data/spool
```

After that, the PostFix process started successfully, and I could send mail from
the command line, but python was still having a problem connecting to the
server. After some internet and PostFix-log (`/var/log/mail.log`) searching, I
found that a missing `submit.cred` configuration file was the problem. So, all
that was needed was to create a `/etc/postfix/submit.cred` file with the
following content:
```
submitcred version 1
localhost|<username>|<password>
```
With `<username>` and `<password>` replaced appropriately (I wasn't sure exactly
which username and password were necessary, so I used my system login credentials,
and they seem to work fine). Then, I needed to set the appropriate permissions
on the file:
```
$ sudo chmod 600 /etc/postfix/submit.cred
```
And that was it! I can now send emails from python. I'm happy.

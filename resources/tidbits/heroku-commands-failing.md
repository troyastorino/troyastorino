# All Heroku CLI commands erroring out

_Note: I'm running OS X 10.8_

So, I haven't used Heroku in a while, but in remaking my website I decided I'd 
just run it from Heroku for the awesome convenience. Everything was working fine 
at first, but suddenly when I tried to add my domain name to the app heroku 
failed. In fact, every heroku command I entered at the command line failed with 
the same error message: 

 ```bash
 /System/Library/Frameworks/Ruby.framework/Versions/1.8/usr/lib/ruby/1.8/rubygems/custom_require.rb:31:in `gem_original_require': no such file to load -- netrc (LoadError)
	from /System/Library/Frameworks/Ruby.framework/Versions/1.8/usr/lib/ruby/1.8/rubygems/custom_require.rb:31:in `require'
	from /Users/troyastorino/.heroku/client/lib/heroku/auth.rb:6
	from /System/Library/Frameworks/Ruby.framework/Versions/1.8/usr/lib/ruby/1.8/rubygems/custom_require.rb:31:in `gem_original_require'
	from /System/Library/Frameworks/Ruby.framework/Versions/1.8/usr/lib/ruby/1.8/rubygems/custom_require.rb:31:in `require'
	from /Users/troyastorino/.heroku/client/lib/heroku/client.rb:4
	from /System/Library/Frameworks/Ruby.framework/Versions/1.8/usr/lib/ruby/1.8/rubygems/custom_require.rb:31:in `gem_original_require'
	from /System/Library/Frameworks/Ruby.framework/Versions/1.8/usr/lib/ruby/1.8/rubygems/custom_require.rb:31:in `require'
	from /Library/Ruby/Gems/1.8/gems/heroku-2.15.1/lib/heroku.rb:6
	from /System/Library/Frameworks/Ruby.framework/Versions/1.8/usr/lib/ruby/1.8/rubygems/custom_require.rb:31:in `gem_original_require'
	from /System/Library/Frameworks/Ruby.framework/Versions/1.8/usr/lib/ruby/1.8/rubygems/custom_require.rb:31:in `require'
	from /Library/Ruby/Gems/1.8/gems/heroku-2.15.1/bin/heroku:6
	from /usr/bin/heroku:19:in `load'
	from /usr/bin/heroku:19
    
 ```
 
After digging around for a little while, it became apparent that the only 
solution to this issue was to reinstall the heroku toolbelt. First, I needed to 
remove the Heorku client directory: 

```bash
$ rm -r ~/.heroku/client

```
    
Next, I [downloaded the Heroku toolbelt here](https://toolbelt.heroku.com/).

Finally, I logged in with `heroku login`, and all was working again!

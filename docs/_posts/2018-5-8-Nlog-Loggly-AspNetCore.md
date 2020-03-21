---
layout: post
title: Loggly with AspNetCore and nlog!
---

First you'll need a loggly account. If you don't already have one you can sign up for their free tier [here](https://www.loggly.com/signup/).

Once you're signed up you'll need to head over to "Source Setup" > "Customer Tokens" and copy the customer token from the list. We'll use this token a little bit later.

![_config.yml]({{ site.baseurl }}/images/loggly-token.png)

Next you should create an ASP .NET 2.0 Web API. Note: ensure you have VS 2017 and the relevant NET Core 2.0 SDK installed.

Open Visual Studio

Go to "File" > "New" > "Project" and select "ASP .NET Core Web Application".

![_config.yml]({{ site.baseurl }}/images/vs-new-project.png)

Then select "Web API" and click "ok".

![_config.yml]({{ site.baseurl }}/images/vs-web-api.png)

Next you'll need to install some nuget packages. Specifically:

    <PackageReference Include="NLog" Version="4.5.4" />
    <PackageReference Include="NLog.Targets.Loggly" Version="4.7.0" />
    <PackageReference Include="NLog.Web.AspNetCore" Version="4.5.3" />
	
Once those are installed you'll need to create a new IServiceCollection extension to configure your Loggly library.

{% gist d4bc2750b6826cdf624b3ffb598f897f %}

Next you'll need to add an Nlog configuration file. Note: the properties of this file need to be set to "Copy always" or "Copy if newer" in visual studio.

{% gist 7c159915791f21efdcf1b9e18b6fa7c5 %}

Now you'll need somewhere to read your loggly customer token from. I strongly advise using the "User Secrets" facility that comes as part of Net Core. This removes the risk of pushing sensitive information to your repository as part of a code commit. That being said you could also store your loggly settings, and all other settings for that matter, in your appsettings.json file. Replace "Your Token" with your loggly customer token.

{% gist 413d21919976d16b39983de3bb012172 %}

Now you'll need to make some changes to your "Startup.cs" file in order to turn on logging and set up your configuration.

{% gist ded13cdfdddee5d6a61feecaf16e44cf %}

Finally you need to inject the ILogger service into your values controller and actually do some logging. 

{% gist 3271f0b93f3ca14a74de24ea2613699b %}

All going well you should be able to run your app, access the values controller and see the log information on your loggly account. Note: it may take a minute or two for loggly to be updated.


Thanks for taking the time to read this and in case you had any problems building the example described above you can always reference the example [here](https://github.com/peader/AspNetCoreLogglyNlogTutorial).



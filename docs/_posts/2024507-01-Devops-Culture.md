---
layout: post
title: Devops Culture!
tags:
 - Devops
 - flow
 - culture
 - opinion
---

## Foreword
I have worked in software development for over 10 years and in that time have always been drawn towards devops related tasks. It is only in the last 2 years that I've held the title "Devops Engineer". I wanted to find the reason why I am drawn to this area of software development and what it even brings, in terms of value, to the teams and business' who are supposed to benefit from it. The following blog post is my attempt to answer these questions. These are just my opinions and are heavily influenced by the books: "The Phoenix Project" by Gene Kim and John Willis, and "Flow" by Mihaly Csikyentmihaly.

Originally, when someone asked me what the goal of devops was I would have answered "To enable  the development teams to more effectively deliver software". Although I still think this is important, it hides, at least it did for me, the wider context of what it takes to make a development team more effective. I would inevitably end up focusing on developer tooling and pipeline automization. Don't get me wrong, these are worthwhile areas to work on but, as I mentioned, are not always the ones that yield the greatest benefit. So how do we figure out what we should work on to really have an impact as a devops engineer?
For this it is important to understand the entire process of software development. From planning all the way through to delivery. In the Phoenix Project this "flow" of work is called the value stream.

## The wood from the trees
We generally are not lucky enough to be able to develop software applications in isolation. The majority of times it is a multi departmental effort spanning sales, support, project management, development, security, operations and quality assurance to name but a few. Each of these stakeholders have their own workflows, priorities and goals. They all inevitably impact the process of developing our application so it is important to understand as much as we can about each of them.

Here's an example that we can work with. Imagine you're part of a development team at a game dev company developing an online driver sim application. You've dialed in your CI pipelines and can build and unit test the code base after each commit. You generate releases and artifacts after a successful pull request and hand over your work to the QA department for testing and quality checks. Unfortunately, QA needs at least a week to work through all their processes before the changes move on to the final deployment step. This means there is a minimum lead time of one week before the changes the development team made can add value to the business and end customer. But this is ok though, right...? I mean we fulfilled the requirements in our "definition of done" so there should be no more work on the development side of things. Unfortunately, reality does not always adhere to the "definition of done" that was originally defined. Speaking from experience, to truely know if what we develop works, in terms of meeting customer expectations, it needs to be in use by those same customers. For example, I once developed an input system for a driving simulator, I was sure it worked but it was not until the end users tested it and gave their feedback that we realised it just didn't feel right when compared to the real thing. I inevitably needed to reimplement the system but at this stage I had lost context because of the delay between initial development and delivery. Now imagine you can only release to customers at quarterly intervals....:/

The point I'm trying to make here is that we cannot work in isolation from the teams upstream and more importantly downstream of us if our goal is to effectively add value for our customers and ultimately the business at large. In the example above it would probably be worthwhile spending time trying to understand the QA process and investing effort into reducing the time required at this stage of the process. In the Phoenix project they define this as "exploiting the contraint" or removing the bottleneck.

## Is it working?
As mentioned in the previous section it is really important to get timely feedback from our customers to know if what we're doing is working. It should be clear now that we can only achieve timely customer feedback by first optimizing the movement of work through the system aka "value stream" i.e. remove the bottlenecks.
Ok, but are there other signs that what I'm doing is working? Yes, there are! Observability and monitoring to the rescue! We can use these practices to gather feedback from almost all stages of our value stream. Tools like grafana or datadog allow us to gather, consolidate and visualize information about our value stream. As a side note, it is also possible to integrate these tools with our CI pipelines.

Again our example, you are part of a development team at a game dev company developing an online driver sim application. Your backend endpoints are taking way too long to return a response. Your first instinct is to beef up the server. More CPU and RAM should fix the problem right....? Maybe, but this guess will definitely mean more capital investment into your computing resources. Instead lets try to analyze the problem with the help of our monitoring tools. We look into the traces for the endpoints that are especially slow. On closer inspection we notice that these endpoints spend a relatively long time on querying the database. Hmmm, how about we reindex the associated tables. We try this approach, reexamine the monitoring tool for the same traces and boom, the latency is gone. All thanks to our fast feedback loops.
In summary to this section to make informed decisions and readjust our direction we need quick feedback from the systems we work with and on.

## Where do we go from here?
Our value stream is optimized and we're getting continuous and timely feedback from our monitoring systems. We know what we're doing is working. What more do we need? Well at this point things get exciting. We can experiment!
Back to our game dev example, one of our devs has the idea that we can improve user engagement by adding an "invite friend" button into the lobby area of our driver sim. They get the go ahead from project management and finish the development the next morning (they're excited :)). The code changes are merged and since our value stream is optimized the changes reach our customers within the hour. We can see, from our monitoring, that there is a huge uptick in new user sign ups and we can correlate it with clicks on our invite button! Hooray!
I hope this example shows how much potential working in this way has. Additionally, our dev in this example is super happy. Their suggestion has positively impacted the business, they have a sense of creativity and agency in their professional life that enhances their overall quality of life. I honestly don't think this is over the top either. We spend so much of our life at work. If your working life is satisfying than surely that has a positive impact on the rest of your life.

Did you notice though that this would not have happened if project management had said no. This is a very important aspect of experimentation. We must have the trust of our colleagues to be able try things out with the potential that the things we try will fail.

## Conclusion
When we, well when I used to, think of devops I would often end up obsessing over tools and technical implementations. I now see that the process of understanding the value streams, at all levels, of the company you're working with is just as important, if not more so, than the technical solutions you're using. Additionally, the trust we cultivate between colleagues, in the end the human factor, is also incrediably important.


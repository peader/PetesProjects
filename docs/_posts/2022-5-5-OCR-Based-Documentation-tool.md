---
layout: post
title: Cutting through the cruft!
---

So living in Germany you tend to accumulate a fair amount of documentation. What's worse is that it all could be potentially important to some future beaurocratic process. I wanted to make the archiving of these documents digital and searchable.
I haven't really found any android application out there that does what I want so here I go down the road of trying to build my own.
I want to see if I can leverage my dotnet knowledge and build a cross platform C# based application using the brand new .MAUI framework.
Let's see how it goes :)

## Approach
- Dotnet MAUI in combination with the tesseract ocr nuget package: https://github.com/charlesw/tesseract/blob/master/docs/examples.md
- Come up with a strategy for categorizing and making the documentation easily searchable without performance loss over time.

## First things first
- Setup the project as an open source one on [GitHub](https://github.com/peader/CruftBuster).
- Add a CI pipeline for building the android version of the application. Many thanks to the authors of this [blog article](https://blog.taranissoftware.com/building-net-maui-apps-with-github-actions) for the write up on using GitHub actions with .MAUI.

## Next up
- Add some unit testing.
- Intergrate the tesseract nuget package.
- Create a camera widget for capturing an image.
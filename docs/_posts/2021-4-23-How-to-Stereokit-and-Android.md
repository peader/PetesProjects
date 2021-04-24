I've been meaning to try out the very cool [StereoKit](https://stereokit.net/) on my Oculus device for a while now. As of yet there isn't a ready made Visual Studio Template for creatiing and deploying to Android devices like there is for [Hololens and UWP](https://marketplace.visualstudio.com/items?itemName=NickKlingensmith.StereoKitTemplates) devices.
I could just be patient and wait for Nick and the team to release the Android template or........ I could try build the source code and example android project there in :).
Ok, here we go:
1) First off Ensure you have the Xamarin workload from Visual Studio 2019 installed:
	- Open the Visual Studio Installer and click modify on your Visual Studio 2019 installation.
	- Select the "Mobile development with .Net" workload and "Mobile development with c++".	
2) Since I'm going to try and build the stereokit example from scratch I need to have a few additional tools installed and configured:
	- The [android NDK](https://developer.android.com/ndk/downloads) 
		- Once downloaded you can extract the folder to a convenient location.
	- xmake installed on my windows machine.
		- You can either install via powershell
		``` powershell
			Invoke-Expression (Invoke-Webrequest 'https://xmake.io/psget.text' -UseBasicParsing).Content
		```
		- Or install using the windows exe from the [xmake release page](https://github.com/xmake-io/xmake/releases)
		
		---
		*Credit to Nick, the author of Stereokit, for the info on configuring the android NDK*

		- You'll next need to set up your Android NDK directories

		On Windows:
		``` powershell
			xmake global --ndk=C:/Microsoft/AndroidNDK64/android-ndk-r16b or
			xmake global --ndk=C:/Users/progr/AppData/Local/Android/Sdk/ndk/21.3.6528147
			xmake global --android_sdk=C:/Microsoft/AndroidSDK or
			xmake global --android_sdk=C:/Users/progr/AppData/Local/Android/Sdk
		```
		Then configure xmake to build for android:
		``` powershell
			xmake f -p android -a arm64-v8a --ndk_cxxstl=c++_static
		```
		or all combined
		``` powershell
			xmake f -p android -a arm64-v8a --ndk_cxxstl=c++_static --android_sdk=C:/Users/progr/AppData/Local/Android/Sdk --ndk=C:/Users/progr/AppData/Local/Android/Sdk/ndk/21.3.6528147
		```

		And then build
		xmake
		---

		- Install make using chocolatey (chocolatey is optional really it's just my preferred tool for installing applications)
		``` powershell
			choco install make
		```
		- Go to %programdata%\chocolatey\bin and copy the make.exe into the folder where you extracted the android NDK.

3) Open the Stereokit solution (located at the root of the sterokit repository)
4) Build the c# stereokit project.
5) Build the StereoKitTest_Android project.
6) Connect your Oculus device (ensure developer mode is enabled on your Oculus)
7) In Visual Studio click the play button to deploy and run the StereoKitTest_Android app on your Oculus (you should see the Oculus device name on the play button prior to clicking it).

And that's it. A little bit fiddly but sure to get easier once the visual studio template and nuget packages are released.
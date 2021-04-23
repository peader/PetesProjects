1) Ensure you have the Xamarin workload from Visual Studio 2019 installed:
	- Open the Visual Studio Installer and click modify on your Visual Studio 2019 installation.
	- Select the "Mobile development with .Net" workload.	
2) Since I'm going to try and build the stereokit example from scratch I need to have a few additional tools installed and configured:
	- The [android NDK](https://developer.android.com/ndk/downloads) 
		- Once downloaded you can extract the folder to a convenient location.
	- xmake installed on my windows machine.
		- You can either install via powershell
		``` powershell
			Invoke-Expression (Invoke-Webrequest 'https://xmake.io/psget.text' -UseBasicParsing).Content
		```
		- Or install using the windows exe from the [xmake release page](https://github.com/xmake-io/xmake/releases)

I do have this little snippet related to Android xmake setup:
## Setting up Android

You'll first need to set up your Android NDK directories

On Windows:
xmake global --ndk=C:/Microsoft/AndroidNDK64/android-ndk-r16b or
xmake global --ndk=C:/Users/progr/AppData/Local/Android/Sdk/ndk/21.3.6528147
xmake global --android_sdk=C:/Microsoft/AndroidSDK or
xmake global --android_sdk=C:/Users/progr/AppData/Local/Android/Sdk

Then configure xmake to build for android:
xmake f -p android -a arm64-v8a --ndk_cxxstl=c++_static
or all combined
xmake f -p android -a arm64-v8a --ndk_cxxstl=c++_static --android_sdk=C:/Users/progr/AppData/Local/Android/Sdk --ndk=C:/Users/progr/AppData/Local/Android/Sdk/ndk/21.3.6528147

And then build
xmake
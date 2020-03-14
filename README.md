#

## App Description:

* Tap It Out is an app for Android made to train non-musicians and non-dancers on how to find the beat. It uses Spotify Music Player to actually play the track, which in tegrated with Spotify's Software Development Kit (SDK). While the song plays, the user gets a visual cue for when to tap to the beat. After that brief period, the visual stops and the user's performance is scored against the audio analysis beats information provided by Spotify's Web API. The preference for using real songs is to familiarize the user with music they would hear in a real lesson or during social dancing.

## Set Up:
* Install and register Spotify App if planning on using a physical device. If intending to use an emulator, it will require Spotify App torun
* Download Android Studio: https://developer.android.com/studio
* Clone the repository
* Register the app with Spotify to get a Client ID: https://developer.spotify.com/documentation/general/guides/app-settings/
* Create a file named "apikey.properties" to the Trainingtofindthebeat directory, this is the Project directory
* Add your Client ID to the apikey.properties file:
    CLIENT_ID = "<Your_client_ID_here>" 

## Citation(s):
* Spotify
  01/01/2020
  Android SDK Quick Start (Kotlin) BETA 
  https://developer.spotify.com/documentation/android/quick-start/kotlin/

## Author(s):
* C. Gutierrez

A product of the Nemesii Team - “Do better.“

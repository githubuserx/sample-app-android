# Sample-app-android
*BSD License*

This is a sample app for the Open918 library, which parses the contents of UIC 918 tickets. UIC 918.2 and 918.3 is a UIC standard for several kinds of tickets that are used in domestic and international railways and other public transport. This app is an adaptation of the [MyFirstRailpocket app](https://play.google.com/store/apps/details?id=nl.waarisdetrein.myfirstrailpocket), available on Google Play, with a few less features to demonstrate the library.

## How to use
An APK file can be found under releases and used to install this on an Android device.

<img src="http://i.imgur.com/FNRl6Vs.png" alt="Initial screen" width="320">
<img src="http://i.imgur.com/S1Nshe6.png" alt="Ticket Details" width="320">

## Features
The Open918 library is beta quality - it's nowhere feature complete but has fairly mature parsing capabilities. This sample app needs some tender loving care in the styling and UX departments, it may be added in the future.

### Implemented
* Scanning tickets with the ZXing library using a camera
* Viewing contents of tickets: details and parameters, the text version, a barcode version and a table of parameters.

### Missing
* Support for multiple barcode scanning libraries (Google, commercial with your own license, etc)
* Saving results
* The library is currently embedded, needs to be removed and referenced to a public maven repo

## Contributors and Contributing

This library is based on work by BliksemLabs:

* Joel Haasnoot

Pull requests are appreciated.
# Mobile app module

## Android features

1. **Firebase Auth login** feature using gmail and email and password and setup
2. **Geofence** was to be used to send notifications when user was not within the locality set and there was someone lurking around the house as sensed by motion sensor or a knock on the door so user could view through home camera.So currently was set but implementation of full feature could be done in future.
3. Connection to **webservice Rest API** we used retrofit and moshi
4. To **display video** we decided to use exernal library due to time constraints and settled for [Video LAN/ Lib Vlc library](https://gitlab.cs.ut.ee/antikivi/mciot_home_security)

## Challenges 
> We had to go with MVP for presentation due to the following challenges.
* Network security config was a challenge since last time used it was awhile and in Java.
* Not being on the same network as webservice and devices and testing off implementation.
* Not being in UT network was a challenge and also the devices had to be swited off to avoid overheating
* Google android docs some of deprecated methods are still not updated so finding right solutions to some was a challenge especially for geofencing 

## Areas could have improved on
* **Ui**: home page we didnt have time to replace the place holders with live data to be easily viewed by user and better format the data display and add progress bars.
* **Notificaion alerts connected to geofence and messages received also had no time process and add notifications.but for future improvement of the app it is something feasible.

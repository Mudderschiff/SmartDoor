# Service module

## Solution
Web service is created with Spring Boot Web. It defines endpoints in
specific controllers. For MQTT communication, Eclipse Paho was chosen. 
Created 4 listeners and 1 producer. Business logic resides in service classes.

## Prerequisities
java 11\
gradle 7.3.1\
git 2.30.1

## Build and run
### Build
Get the code\
`git clone https://gitlab.cs.ut.ee/antikivi/mciot_home_security.git` \
move to dir\
`cd mciot_home_security/homsec_service`\
build it \
`gradle clean assemble`\

### MQTT
Start MQTT broker(on default port 1883) with command\
`mosquitto`

### Configure
Configure MQTT broker in file src/main/resources/application.properties(substitute the mqtt_broker_host): \
`mqtt.host_uri=tcp://<mqtt_broker_host>:1883`
### Run
and then run\
`java -jar build/libs/homsec_service-1.0-SNAPSHOT.jar` \
Webservice is served on localhost:8080\
Try it: http://localhost:8080/home



## Tools/technologies/libraries used
Java, Gradle, Spring boot web, Eclipse Pahos, Eclipse Mosquitto, Etais private could


# Challenges faced
Two biggest issues were deployment and accessing camera. I already deployed it to etais private cloud,
but it required vpn to UT network. I also deployed it to VM in google cloud,
but did not manage to configure firewall. Also, this approach assumes that deployable image is ready and finished since you can modify it only temporarily. \
I also tried Google AppEngine. For making gcloud deploy scripts to work, it was necessary to change build.gradle significantly(and did not work). It was easier to introduce Maven pom.xml to the project which did not need any change. When I learned that I need another service to set up, decided to stay in etais.

## Future improvements
Camera was more difficult. How to give access to it securely, periodically(as response to query from app->ws since did not want to transfer feed all the time when it was not actually needed/used) while camera is behind router.
Streaming http request(from ws to gw/camera) that is converted to streaming response to mobile app? Sending stream through mqtt and then streaming it inside http response for mobile app?
Open route in router for mobile app to directly access camera and iot->ws->app providing camera location?
# Report (IoT side)

[ Technical README.md ](https://gitlab.cs.ut.ee/antikivi/mciot_home_security/-/blob/main/iot_home/Technical_README.md)

## Which functional feature blocks does the project fulfill? (IoT side)
Edge integration platforms used are iotempower for most of the nodes. The interfaces of each
nodes are kept fairly simple. Most of the logic was implemented in Node-red. For example
After a rfid uid was accepted a mqtt message is send to the lock to unlock it for 5 seconds.
We also implemented a new platform which wasn't covered in the course so far, esphome. Esphome
was used to implement the camera functionality and the camera-server for the esp32 with the cam module.
This simplified the implementation of the camera node significantly.

## What would you add/do if you had more time?
One of the issues was that i couldn't make the camera accessible from outside of the local network.
The reason being no access to the router and thus no possibility to configure port forwarding.
We played with the idea to send snapshots in intervals via mqtt but the getting the image always lead to an error because the request was too long.
Another alternative is streaming the camera feed to our webserver. Which would be a great idea because then accessing the
camera feed would only be possible for people that have access to the server. This we also couldn't implement because of time constraints.

## What was the most challenging problem?
Initially getting mqtt and iotempower to run under WSL. This took a lot of time.
Afterwards, it was difficult to connect all components together because for us each component that was implemented by our teammates was a blackbox
And finally, communication is always a little slower if everything is done remotely and it's hard to express idea's or implementations by talking.


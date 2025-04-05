# IoT Story

Gabriel is a 25 year old bachelor that works at a software company making a great salary.
Being a dashing, rich, young polyglot he is ofcourse very popular on the dating market.
The perks of being desired also comes with a fair shair of plights. Sometimes, he get's into
unconfortable situations with the opposite gender because sadly, some women might not handle a rejection
with grace. After an incident, which he wishes not to talk about, he uses his skills as a developer to work on 
a smart door to record and monitor any potential criminal activity happening outside of his billion dollar mansion.
The spy hole was replaced by a camera that streams what's happening on the poarch. He also
included a motion sensor that will detect any movements on the poarch. If for example he get's a notification
on his complementary smart phone app that movement is happening he can then get a live stream of the front poarch directly in app in real time.
He also replaced the locking mechanism to open only when it senses a accepted rfid tag.
If he chooses to he can give temporary rfid tags to friends,family or potential female suitor.
Everyone trying to open the door through such a tag (be it accepted or not accepted) will then
receive visual and sound cutes to see if it was successful or not succesfull. Every succesfull
and unsuccesfull attempt at opening the door is also tracked via the smart phone app.
He also replaced the door bell with a more silent solution. Now, no one angry can keep ringing the doorbell and disturb you.
There only option is to knock and let's see how long they can last while there knuckles get more and more bruised.
If someone is visiting Gabriel he will just simply knock on the door and Gabriel will receive a notification on his smartphone, which he can then
choose to ignore if he wants too. Finnally, some peace. For security reason's the the api to interact with the smart door is seperated on a webserver.
The gateway running on a local network, that manages all of the smart door functionality, mostly only sends out sensor information (motion,knock etc) to the webserver's broker.
Which then translate's these mqtt messages to rest endpoints. Outside of the local network it is only possible to open the door if you have sufficient access rights to our secure webserver and are able to call the correct endpoint.
The smart phone app is additionally protected through firebase authentication because you generally shouldn't handle password yourself.
This further restricts any advesaries to get sensitive data or even unlock our door. These functionalities already sound pretty amazing but gabriel has bigger plans.
He already implemented a map inside of his smart phone app which makes use of a geofence. In the future it would be possible to subscribe or publish to choosen topics depending on
your location. So, for example if Gabriel is doing work on his front poarch he can then temporarily usubscribe to the motion sensor topic because he is in the area of the motion sensor and 
outside of the geofence he can for example disable receiving knock information if he's out and about.

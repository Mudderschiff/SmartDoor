/* setup.cpp
 * This is the configuration code for a IoTempower node.
 * Here, you define all the devices (and eventually their interactions)
 * connected to the node specified with this directory.
 * If you want to see more device configuration examples,
 * check $IOTEMPOWER_ROOT/examples/running-node-test-setup.cpp
 *
 * Or check out the command reference for potential devices you can add.
 * 
 * This whole comemnt block can be deleted
 * */
#define BUZZER D5
output(r_led, D6, "on", "off");

output(buzzer, D5, "sound", "no_sound").on_change([&] (Device& dev) {
	if(dev.is("sound")) {
		IN(r_led).set("on");
		tone(BUZZER, 300);
		delay(1000);
		IN(r_led).set("off");
		noTone(BUZZER);
	}
	return true;
});

output(g_led, D7, "on", "off");
input(knock, D1, "no knock", "knock");

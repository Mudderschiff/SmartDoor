package ee.ut.cs.home_sec;

public enum AlertType {
    KNOCK,
    MOTION,
    RFID;

    public String getValue(){
        return name().toLowerCase();
    }
}

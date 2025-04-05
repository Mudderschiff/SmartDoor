package ee.ut.cs.home_sec;

import java.time.Instant;

public class Alert {

    private final Instant timestamp;
    private final String type;
    private final String message;
    private final String tag;

    public Alert(Instant timestamp, String type, String message, String tag) {
        this.timestamp = timestamp;
        this.type = type;
        this.message = message;
        this.tag = tag;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "Alert{" +
            "timestamp=" + timestamp +
            ", type='" + type + '\'' +
            ", message='" + message + '\'' +
            ", tag='" + tag + '\'' +
            '}';
    }

    public static class Builder {

        private Instant timestamp;
        private String type;
        private String message;
        private String tag;

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Alert build() {
            return new Alert(timestamp, type, message, tag);
        }
    }
}

package client.command;

import java.io.Serializable;

public class TimeoutEndCommand implements Serializable {

    private final String timeoutMessage;

    public TimeoutEndCommand(String timeoutMessage) {
        this.timeoutMessage = timeoutMessage;
    }

    public String getTimeoutMessage() {
        return timeoutMessage;
    }
}

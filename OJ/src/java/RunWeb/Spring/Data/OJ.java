package java.RunWeb.Spring.Data;

import javax.xml.crypto.Data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

public class OJ {
    private final Long ID;
    private final String[] messages;
    private final Date time;

    public OJ(Long inID, String[] inMessages, Date inTime) {
        this.ID = inID;
        this.messages = inMessages;
        time = inTime;
    }

    public Long getID() {
        return ID;
    }

    public String[] getMessages() {
        return messages;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object,
                new String[]{"ID", "messages", "time"});
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this,
                new String[]{"ID", "messages", "time"});
    }
}

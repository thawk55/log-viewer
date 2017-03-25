package tyler.hawkins.models;

import java.math.BigInteger;


public class NewLog {
    private String version;
    private String channel;
    private String level;
    private String opcode;
    private short task;
    private BigInteger keyword;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public short getTask() {
        return task;
    }

    public void setTask(short task) {
        this.task = task;
    }

    public BigInteger getKeyword() {
        return keyword;
    }

    public void setKeyword(BigInteger keyword) {
        this.keyword = keyword;
    }

    public NewLog() {

    }

    public NewLog(String version, String channel, String level, String opcode, short task, BigInteger keyword) {
        this.version = version;
        this.channel = channel;
        this.level = level;
        this.opcode = opcode;
        this.task = task;
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewLog newLog = (NewLog) o;

        if (task != newLog.task) return false;
        if (version != null ? !version.equals(newLog.version) : newLog.version != null) return false;
        if (channel != null ? !channel.equals(newLog.channel) : newLog.channel != null) return false;
        if (level != null ? !level.equals(newLog.level) : newLog.level != null) return false;
        if (opcode != null ? !opcode.equals(newLog.opcode) : newLog.opcode != null) return false;
        return keyword != null ? keyword.equals(newLog.keyword) : newLog.keyword == null;

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (opcode != null ? opcode.hashCode() : 0);
        result = 31 * result + (int) task;
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewLog{" +
                "version='" + version + '\'' +
                ", channel='" + channel + '\'' +
                ", level='" + level + '\'' +
                ", opcode='" + opcode + '\'' +
                ", task=" + task +
                ", keyword=" + keyword +
                '}';
    }
}

package tyler.hawkins.models;

public class Log {
    private int id;
    private String level;
    private String dateTime;
    private String source;
    private int eventId;
    private String taskCategory;
    private String info;

    public Log(int id, String level, String dateTime, String source, int eventId, String taskCategory, String info) {
        this.id = id;
        this.level = level;
        this.dateTime = dateTime;
        this.source = source;
        this.eventId = eventId;
        this.taskCategory = taskCategory;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (id != log.id) return false;
        if (eventId != log.eventId) return false;
        if (level != null ? !level.equals(log.level) : log.level != null) return false;
        if (dateTime != null ? !dateTime.equals(log.dateTime) : log.dateTime != null) return false;
        if (source != null ? !source.equals(log.source) : log.source != null) return false;
        if (taskCategory != null ? !taskCategory.equals(log.taskCategory) : log.taskCategory != null) return false;
        return info != null ? info.equals(log.info) : log.info == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + eventId;
        result = 31 * result + (taskCategory != null ? taskCategory.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", level='" + level + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", source='" + source + '\'' +
                ", eventId=" + eventId +
                ", taskCategory='" + taskCategory + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}

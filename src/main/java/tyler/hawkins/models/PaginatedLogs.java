package tyler.hawkins.models;

import java.util.ArrayList;

public class PaginatedLogs {
    private ArrayList<Log> logs = new ArrayList<>();
    private int totalSize;
    private int offset;
    private int size;

    public PaginatedLogs() {
    }

    public PaginatedLogs(ArrayList<Log> logs, int totalSize, int offset, int size) {
        this.logs = logs;
        this.totalSize = totalSize;
        this.offset = offset;
        this.size = size;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<Log> logs) {
        this.logs = logs;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaginatedLogs that = (PaginatedLogs) o;

        if (totalSize != that.totalSize) return false;
        if (offset != that.offset) return false;
        if (size != that.size) return false;
        return logs != null ? logs.equals(that.logs) : that.logs == null;

    }

    @Override
    public int hashCode() {
        int result = logs != null ? logs.hashCode() : 0;
        result = 31 * result + totalSize;
        result = 31 * result + offset;
        result = 31 * result + size;
        return result;
    }

    @Override
    public String toString() {
        return "PaginatedLogs{" +
                "logs=" + logs +
                ", totalSize=" + totalSize +
                ", offset=" + offset +
                ", size=" + size +
                '}';
    }
}

package tyler.hawkins.daos;

import tyler.hawkins.models.Log;
import tyler.hawkins.models.NewLog;
import tyler.hawkins.models.PaginatedLogs;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;


public class LogViewerDAO {
    private DataSource dataSource;
    private String tableName = "logs";

    public LogViewerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Log getLog(short id){
        String sql = String.format(
            " SELECT id, level, date_time, source, event_id, task_category, info " +
            " FROM %s WHERE id = ?", tableName);


        Log log = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setShort(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                log = parseLog(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return log;
    }

    public PaginatedLogs getLogs(String search, int limit, int skip){
        String whereClause = parseSearch(search);

        String sql = String.format(
                " SELECT id, level, date_time, source, event_id, task_category, info " +
                " FROM %s %s" +
                " LIMIT %d, %d", tableName, whereClause, skip, limit);
        ArrayList<Log> logs = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                logs.add(parseLog(rs));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        int totalSize = 0;
        String countSQL = String.format(" SELECT COUNT(*) AS count " +
                " FROM %s %s", tableName, whereClause);
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(countSQL)) {
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                totalSize = rs.getInt("count");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return new PaginatedLogs(logs, totalSize, skip, limit);
    }

    public Log insert(NewLog log){

        String sql = String.format(
            "INSERT INTO %s (level, date_time, source, event_id, task_category, info) " +
            "VALUES (?, ?, ?, ?, ?, ?)", tableName);

        short newId = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, log.getLevel());
            ps.setString(2, log.getDateTime());
            ps.setString(3, log.getSource());
            ps.setInt(4, log.getEventId());
            ps.setString(5, log.getTaskCategory());
            ps.setString(6, log.getInfo());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                newId = rs.getShort(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (newId == 0) {
            return null;
        }

        return getLog(newId);
    }

    public boolean remove(short id) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", tableName);
        boolean success;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            success = (rowCount == 1);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return success;
    }

    private String getColumnSearch(String column, String query){
        String columnName;
        switch(column.trim().toLowerCase()){
            case "level":
                columnName = "level";
                break;
            case "datetime":
            case "date and time":
            case "date time":
            case "date_time":
                columnName = "date_time";
                break;
            case "source":
                columnName = "source";
                break;
            case "eventid":
            case "event":
            case "event id":
            case "event_id":
                columnName = "event_id";
                break;
            case "taskcategory":
            case "task category":
            case "task":
            case "category":
            case "task_category":
                columnName = "task_category";
                break;
            case "log":
            case "info":
                columnName = "info";
                break;
            default:
                return "";
        }
        return String.format("AND %s COLLATE UTF8_GENERAL_CI LIKE '%%%s%%'", columnName, query.trim());
    }

    private String parseSearch(String search) {
        StringBuilder sb = new StringBuilder(" WHERE 1=1 ");
        String[] searchTerms = search.split(";");
        for(int i = 0; i < searchTerms.length; i++){
            String[] parts = searchTerms[i].split(":");
            if(parts.length != 2){
                continue;
            }
            sb.append(getColumnSearch(parts[0], parts[1]));

        }
        return sb.toString();
    }

    private Log parseLog(ResultSet rs) throws SQLException {
        return new Log(
                rs.getInt("id"),
                rs.getString("level"),
                rs.getString("date_time"),
                rs.getString("source"),
                rs.getInt("event_id"),
                rs.getString("task_category"),
                rs.getString("info")
        );
    }

}

package tyler.hawkins.daos;

import tyler.hawkins.models.Log;
import tyler.hawkins.models.NewLog;

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
            " SELECT id, level, datetime, source, eventId, taskCategory, info " +
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

    public ArrayList<Log> getLogs(int limit, int skip){

        String sql = String.format(
                " SELECT id, level, datetime, source, eventId, taskCategory, info " +
                " FROM %s" +
                " LIMIT %d, %d", tableName, skip, limit);
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
        return logs;
    }

    public Log insert(NewLog log){

        String sql = String.format(
            "INSERT INTO %s (level, datetime, source, eventId, taskCategory, info) " +
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

    private Log parseLog(ResultSet rs) throws SQLException {
        return new Log(
            rs.getInt("id"),
            rs.getString("level"),
            rs.getString("dateTime"),
            rs.getString("source"),
            rs.getInt("eventId"),
            rs.getString("taskCategory"),
            rs.getString("info")
        );
    }


}

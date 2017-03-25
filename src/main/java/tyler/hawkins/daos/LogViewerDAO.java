package tyler.hawkins.daos;

import tyler.hawkins.models.Log;
import tyler.hawkins.models.NewLog;

import javax.sql.DataSource;
import java.math.BigDecimal;
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
            " SELECT id, version, channel, level, opcode, task, keyword " +
            " FROM %s WHERE id = ?", tableName);


        Log log = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setShort(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                log = new Log(
                    rs.getShort("id"),
                    rs.getString("version"),
                    rs.getString("channel"),
                    rs.getString("level"),
                    rs.getString("opcode"),
                    rs.getShort("task"),
                    rs.getBigDecimal("keyword").toBigInteger()
                );
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return log;
    }

    public ArrayList<Log> getLogs(int limit, int skip){

        String sql = String.format(
                " SELECT id, version, channel, level, opcode, task, keyword " +
                " FROM %s" +
                " LIMIT %d, %d", tableName, skip, limit);
        ArrayList<Log> logs = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                logs.add(new Log(
                    rs.getShort("id"),
                    rs.getString("version"),
                    rs.getString("channel"),
                    rs.getString("level"),
                    rs.getString("opcode"),
                    rs.getShort("task"),
                    rs.getBigDecimal("keyword").toBigInteger()
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return logs;
    }

    public Log insert(NewLog log){

        String sql = String.format(
            "INSERT INTO %s (version, channel, level, opcode, task, keyword) " +
            "VALUES (?, ?, ?, ?, ?, ?)", tableName);

        short newId = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, log.getVersion());
            ps.setString(2, log.getChannel());
            ps.setString(3, log.getLevel());
            ps.setString(4, log.getOpcode());
            ps.setShort(5, log.getTask());
            ps.setBigDecimal(6, new BigDecimal(log.getKeyword()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                newId = rs.getShort(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(newId == 0){
            return null;
        }

        return getLog(newId);
    }

    public boolean remove(short id){
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



}

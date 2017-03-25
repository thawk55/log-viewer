package tyler.hawkins;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton class to connect to Database
 */
public class LogViewerDatasource {
    private static DataSource logViewerDataSource;

    private LogViewerDatasource(){

    }

    private static DataSource getMySQLDataSource() {
        Properties props = new Properties();

        MysqlDataSource mysqlDS = null;
        try(FileInputStream fis = new FileInputStream("src/main/resources/database.properties")) {
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }

    public static DataSource getDataSource(){
        if(logViewerDataSource == null){
            logViewerDataSource = getMySQLDataSource();
        }
        return logViewerDataSource;
    }
}


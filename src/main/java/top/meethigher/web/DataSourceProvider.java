package top.meethigher.web;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

public class DataSourceProvider {


    private final String db = "osm-png-sqlite.db";

    public DataSource getDataSource() {
        String dbFile = System.getProperty("user.dir").replace("\\", "/") + "/" + db;
        File file = new File(dbFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignore) {
            }
        }
        String url = "jdbc:sqlite:" + dbFile; // SQLite数据库文件路径
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setMinimumIdle(2);
        config.setMaximumPoolSize(20);
        return new HikariDataSource(config);
    }

    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}

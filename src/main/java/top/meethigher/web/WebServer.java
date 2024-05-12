package top.meethigher.web;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jooby.Jooby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import top.meethigher.cache.CacheStore;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class WebServer extends Jooby {

    private final Logger log = LoggerFactory.getLogger(WebServer.class);

    private final String defaultImage = "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";

    private final JdbcTemplate jdbcTemplate;

    private final CacheStore<String, byte[]> cacheStore;

    {

        // 配置静态资源，优先应用路径下static，其次类路径下static
        assets("/**", "/static");
        get("/{z}/{x}/{y}", context -> {
            long start = System.currentTimeMillis();
            //设置允许跨域
            context.setResponseHeader("Access-Control-Allow-Origin", "*");
            context.setResponseHeader("Access-Control-Allow-Headers", "*");
            context.setResponseHeader("Access-Control-Allow-Methods", "*");
            context.setResponseHeader("Access-Control-Allow-Credentials", "true");
            int z = context.path("z").intValue();
            int x = context.path("x").intValue();
            int y = context.path("y").intValue();
            String key = String.format("%s_%s_%s", z, x, y);
            if (getCacheStore().contains(key)) {
                context.send(getCacheStore().get(key));
            } else {
                List<byte[]> list = getJdbcTemplate().queryForList("select png from tiles where z=? and x=? and y=?", byte[].class, z, x, y);
                if (list.isEmpty()) {
                    context.send(Base64.decode(getDefaultImage()));
                } else {
                    byte[] data = list.get(0);
                    context.send(data);
                    getCacheStore().put(key, data, 1, TimeUnit.HOURS);
                }
            }
            log.info("response {} consumed {} ms", context.getRequestPath(), System.currentTimeMillis() - start);
            return null;
        });
    }

    public WebServer(JdbcTemplate jdbcTemplate, CacheStore<String, byte[]> cacheStore) {
        this.jdbcTemplate = jdbcTemplate;
        this.cacheStore = cacheStore;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public CacheStore<String, byte[]> getCacheStore() {
        return cacheStore;
    }
}

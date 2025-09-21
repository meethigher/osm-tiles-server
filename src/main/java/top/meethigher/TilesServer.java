package top.meethigher;

import top.meethigher.cache.impl.LeastRecentlyUsedCacheStore;
import top.meethigher.simple.startup.log.SimpleApplication;
import top.meethigher.web.DataSourceProvider;
import top.meethigher.web.WebServer;

public class TilesServer extends SimpleApplication {
    public static void main(String[] args) throws Exception {
        runApp(TilesServer.class, args);
    }

    @Override
    public void run() throws Exception {
        LeastRecentlyUsedCacheStore<String, byte[]> cache = new LeastRecentlyUsedCacheStore<>(100, true);
        new WebServer(new DataSourceProvider().getJdbcTemplate(), cache).start();
    }
}
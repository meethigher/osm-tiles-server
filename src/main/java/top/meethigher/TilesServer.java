package top.meethigher;

import top.meethigher.simple.startup.log.SimpleApplication;
import top.meethigher.web.WebServer;

public class TilesServer extends SimpleApplication {
    public static void main(String[] args) throws Exception {
        runApp(TilesServer.class, args);
    }

    @Override
    public void run() throws Exception {
        new WebServer().start();
    }
}
package util;

import java.io.FileReader;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getConnectionString() {
        String connectionString=null;
        try(FileReader re=new FileReader("db.properties"))
        {
            Properties p=new Properties();
            p.load(re);
            connectionString=p.getProperty("url")+"?user="+p.getProperty("user")+"&password="+p.getProperty("password");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return connectionString;
    }
}




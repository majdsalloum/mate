package Network;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class InternetConnection {

    public InputStream getPage(String urlPath) throws IOException {
        URL url;
        try{
         url= new URL(urlPath);}
        catch (Exception e)
         {
            String google = "https://www.google.com/search/web?v=1.0&q=";
            String search = urlPath;
            String charset = "UTF-8";
             url = new URL(google + URLEncoder.encode(search, charset));
        }
        return url.openStream();

    }
}

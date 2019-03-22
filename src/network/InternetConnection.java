package network;

import gui.Window;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class InternetConnection {

    private Window window;

    public InternetConnection(Window window) {
        this.window = window;
    }

    public void getPage(String urlPath) {
//
//        try {
//            URL url;
//            try {
//                url = new URL(urlPath);
//            } catch (Exception e) {
//                String google = "https://www.google.com/search/web?v=1.0&q=";
//                String search = urlPath;
//                String charset = "UTF-8";
//                url = new URL(google + URLEncoder.encode(search, charset));
//            }
//            InputStream inputStream = url.openStream();
//
//            String data = "";
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            String line = br.readLine();
//            while (line != null) {
//                data = data + "\n" + line;
//                line = br.readLine();
//            }
//            window.onLoad(data);
//        } catch (IOException e) {
//
//        }
        window.onLoad("hey");
    }
}

package network;

import gui.Window;
import javafx.application.Platform;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

public class InternetConnection {
    private Window window;
    private String urlPath;

    public InternetConnection(Window window) {
        this.window = window;
    }

    private void getPageLogic() {
        try {
            URL url;
            try {
                url = new URL(urlPath);
            } catch (Exception e) {
                String google = "https://www.google.com/search/web?v=1.0&q=";
                String search = urlPath;
                String charset = "UTF-8";
                url = new URL(google + URLEncoder.encode(search, charset));
            }
            InputStream inputStream = url.openStream();
            StringBuilder dataBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine();
            while (line != null) {
                dataBuilder.append(line);
                line = br.readLine();
                if (line != null)
                    dataBuilder.append("\n");
            }
            final String data = dataBuilder.toString();
            Platform.runLater(() -> window.onLoad(data));
        } catch (Exception e) {
            Platform.runLater(
                    () -> {
                        //TODO MAKE THIS FUNCTION
                        //window.errorHappened(e);
                    }
            );
        }
    }

    public void getPage(String urlPath) {
        this.urlPath = urlPath;
        new Thread(this::getPageLogic).start();
    }
}

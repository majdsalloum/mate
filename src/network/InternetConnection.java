package network;


import gui.Window;
import javafx.application.Platform;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class InternetConnection {
    private Window window;
    private String urlPath;
    private URL url;

    public InternetConnection(Window window) {
        this.window = window;
    }

    private void getPageLogic() {
        try {
//            try {
//                url = new URL(urlPath);
//            } catch (Exception e) {
//                try {
//                    tryAddProtocol();
//                } catch (Exception e2) {
//                    tryInSearchEngine();
//                }
//            }
//
//            InputStream inputStream = url.openStream();
//            StringBuilder dataBuilder = new StringBuilder();
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            String line = br.readLine();
//            while (line != null) {
//                dataBuilder.append(line);
//                line = br.readLine();
//                if (line != null)
//                    dataBuilder.append("\n");
//            }
            final String data;// = dataBuilder.toString();
            data = "<html>" +
                    "<head>" +
                    "</head>" +
                    "<body>" +
                    "<ol start='20'>" +
                    "<li> shadi</li>" +
                    "<li> fadi </li>" +
                    "</ol>" +

                    "<table>" +
                    "<tr>" +
                    "<td>hey</td>" +
                    "<td>bye</td>" +
                    "</tr>" +
                    "</table>" +
                    "hi bye <br> hello <br> <br> a7la zalameh" +
                    "</body>" +
                    "</html>";

            Platform.runLater(() -> window.onLoad(data));
        } catch (Exception e) {
            Platform.runLater(
                    () -> {
                        //TODO MAKE THIS FUNCTION
                        //window.errorHappened(e);
                        window.onLoad("Error in Page");
                    }
            );
        }
    }

    public void getPage(String urlPath) {
        this.urlPath = urlPath;
        new Thread(this::getPageLogic).start();
    }

    private void tryAddProtocol() throws MalformedURLException {
//        try {
        url = new URL("http://" + urlPath);
//        }
//        catch (MalformedURLException e)
//        {
//            url = new URL("https://"+urlPath);
//        }
    }

    private void tryInSearchEngine() throws UnsupportedEncodingException, MalformedURLException {
        String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        String search = urlPath;
        String charset = "UTF-8";
        url = new URL(google + URLEncoder.encode(search, charset));
    }
}

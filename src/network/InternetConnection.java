package network;



import gui.Window;
import javafx.application.Platform;
import tests.ParsingTest;

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

            try {
                url = new URL(urlPath);
                ParsingTest.log("url ");
            } catch (Exception e) {
                try {
                    tryInSearchEngine();
                    ParsingTest.log("add protocol header");
                } catch (Exception e2) {
                    tryInSearchEngine();
                    ParsingTest.log("search in engine");
                }
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
            final String data= dataBuilder.toString();
            Platform.runLater(() -> {
                    window.onLoad(data,urlPath);
            });
        } catch (Exception e) {
            Platform.runLater(
                    () -> {
                        //TODO MAKE THIS FUNCTION
                        //window.errorHappened(e);
                            window.onLoad("<html><body>Error in Page loading</body></html>",urlPath);
                    }
            );
        }
//        final String data;// = dataBuilder.toString();
//        data = "<!DOCTYPE HTML>" +
//                "<html>\n" +
//                "<body>" +
//                "Hello" +
//                "</body>" +
//                "</html>";
//        Platform.runLater(() -> {
//            window.onLoad(data, urlPath);
//        });
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
    public enum SearchEngine {
        GOOGLE,
        YAHOO,
        BING
    }
    SearchEngine defaultSearchEngine =SearchEngine.GOOGLE;

    private void tryInSearchEngine() throws UnsupportedEncodingException, MalformedURLException {
        String website="";
        switch (defaultSearchEngine)
        {
            case BING:
                website = "https://www.bing.com/search?q=";
                break;
            case YAHOO:
                website ="https://search.yahoo.com/search?p=";
                break;
            case GOOGLE:
                website = "https://www.google.com/search?q=";
                break;
        }
        String search = urlPath;
        String charset = "UTF-8";
        url = new URL(website + URLEncoder.encode(search, charset));
    }

    public SearchEngine getDefaultSearchEngine() {
        return defaultSearchEngine;
    }

    public void setDefaultSearchEngine(SearchEngine defaultSearchEngine) {
        this.defaultSearchEngine = defaultSearchEngine;
    }
}

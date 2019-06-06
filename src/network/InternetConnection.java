package network;


import core.exceptions.InvalidContentException;
import core.exceptions.InvalidSyntaxException;
import gui.TextPage;
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
            Thread.sleep(1000);
//            try {
//                url = new URL(urlPath);
//                ParsingTest.log("url ");
//            } catch (Exception e) {
//                try {
//                    tryInSearchEngine();
//                    System.out.println(url);
//                    ParsingTest.log("add protocol header");
//                } catch (Exception e2) {
//                    tryInSearchEngine();
//                    ParsingTest.log("search in engine");
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
            final String data;//= dataBuilder.toString();
            data = "<html>\n" +
                    "<head>\n" +
                    "    <title>hello world!</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <p>\n" +
                    "        <b>mate browser</b>\n" +
                    "    </p>\n" +
                    "    <b><u>Students info:</u></b>\n" +
                    "    <table>\n" +
                    "        <!--<th >name</th>-->\n" +
                    "        <!--<th>age</th>-->\n" +
                    "        <!--<th>phone number</th>-->\n" +
                    "        <tr>\n" +
                    "            <td>shadi<br>shadi</td>\n" +
                    "            <td>20</td>\n" +
                    "            <td>0991431726</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>majd</td>\n" +
                    "            <td>21</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>reem</td>\n" +
                    "            <td>20</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>rita</td>\n" +
                    "            <td>20</td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "    <br>\n" +
                    "    <br>\n" +
                    "<form>" +
                    "<select>" +
                    "<option>a1</option>" +
                    "<option>a2</option>" +
                    "</select>" +
                    "</form>" +
                    "    <br>\n" +
                    "    <b><u>project tasks:</u></b>\n" +
                    "    <ul>\n" +
                    "        <li>Internet<br> connection</li>\n" +
                    "        <li>Html viewer\n" +
                    "        <ol>\n" +
                    "            <li>get html text</li>\n" +
                    "            <li>parse text to tree of nodes</li>\n" +
                    "            <li>render Tags tree to javaFX application</li>\n" +
                    "        </ol></li>\n" +
                    "        <li>Save and load Html pages</li>\n" +
                    "        <li>PDF viewer</li>\n" +
                    "        <li>Multitab</li>\n" +
                    "    </ul>\n" +
                    "</body>\n" +
                    "</html>";
            Platform.runLater(() -> {
                try {
                    window.onLoad(data,urlPath);
                } catch (InvalidContentException e) {
                    window.showErrorMessage("Error in showing page",
                            "Page Parsing error\nInvalid content exception\n",
                            e.getMessage());
                    window.setPageAndUpdate(new TextPage(window , urlPath , data));
                } catch (InvalidSyntaxException e) {
                    window.showErrorMessage("Error in showing page",
                            "Page Parsing error\nInvalid syntax exception\n",
                            e.getMessage());
                    window.setPageAndUpdate(new TextPage(window , urlPath , data));

                }
            });
        } catch (Exception e) {
            Platform.runLater(
                    () -> {
                        //TODO MAKE THIS FUNCTION
                        //window.errorHappened(e);
                        try {
                            window.onLoad("<html><body>Error in Page loading</body></html>",urlPath);
                        } catch (InvalidContentException e1) {
                            e1.printStackTrace();
                        } catch (InvalidSyntaxException e1) {
                            e1.printStackTrace();
                        }
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

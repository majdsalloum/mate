package gui;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Images {
    private static Map<String, Image> images = new HashMap<>();

    public static Image logo = getImage("..\\img//mate.png");
    public static Image menu = getImage("..\\img//menu.png");

    static public Image getImage(String path) {
        InputStream url = Images.class.getResourceAsStream(path);
        if (images.containsKey(path))
            return images.get(path);
        images.put(path, new Image(url));
        return images.get(path);
    }

}

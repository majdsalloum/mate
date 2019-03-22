import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextField;

public class Page {
    protected String path;

    public Node getContent()
    {
       return null;
    }

    public void setPath(String URL) {
        this.path = URL;
    }

    public String getPath() {
        return path;
    }


}

package gui;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfViewerFX.PDFViewer;
import core.exceptions.UnSupportedSaveTypeException;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class PDFPage extends Page{
    BorderPane pane;
    public PDFPage(Window window, String path, String data) {
        super(window, path, data);
        PDFViewer pdfViewer=new PDFViewer();
        try {
            pdfViewer.loadPDF(path);
            pane=new BorderPane(pdfViewer);
        } catch (PDFException e) {
            pane = new BorderPane(new Label("Page not found"));
        }
    }
    @Override
    public Node getContent()
    {
        return pane;
    }

    @Override
    public String toBeSaved() throws UnSupportedSaveTypeException {
        throw new UnSupportedSaveTypeException("PDF page");
    }
}

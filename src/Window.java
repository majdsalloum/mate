import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Window {
    private List<String> searchLog;
    private PageToolBar pageToolBar;
    private Page page;
    private Integer pageIndexInSearchLog=0;
    private VBox content;
    public Window()
    {
        searchLog =new ArrayList<>();
        searchLog.add("matte:\\home");
        pageToolBar =new PageToolBar(this);
        //pageToolBar.setWindow(this);

        page= new HomePage();
        searchLog.add(page.getPath());
        pageToolBar.getTextSearch().setText(page.getPath());
        content = new VBox();
        Integer pageCounter=0;
    }
    public void changeContent(Page page)
    {
        this.page=page;
    }
    public Node getContent()
    {
        //changeContent();
        content.getChildren().addAll(pageToolBar.getToolBar(),page.getContent());
        return content;
    }

    public void openNewPage(String path)
    {
        /**send path to url connection and get page*/
        /**get page type */
        page = new HomePage();
        searchLog.add(path);
        pageIndexInSearchLog++;

    }
    Integer getPageIndexInSearchLog()
    {
        return pageIndexInSearchLog;
    }







    public List<String> getSearchLog() {
        return searchLog;
    }

    public void setSearchLog(List<String> searchLog) {
        this.searchLog = searchLog;
    }

    public PageToolBar getPageToolBar() {
        return pageToolBar;
    }

    public void setPageToolBar(PageToolBar pageToolBar) {
        this.pageToolBar = pageToolBar;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}

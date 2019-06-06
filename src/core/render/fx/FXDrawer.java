package core.render.fx;

import core.render.Alignment;
import core.render.Drawer;
import core.render.Effect;
import core.render.actions.Action;
import core.render.actions.FormAction;
import core.render.actions.HrefAction;
import core.render.fx.panes.*;
import core.tags.Tag;
import gui.Page;
import gui.UserInterface;
import gui.Window;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class FXDrawer extends Drawer {
    Tab tab;
    Page page;
    UserInterface ui;
    LinkedList<DrawerPane> parents = new LinkedList<>();
    LinkedList<SelectionList> selectionLists = new LinkedList<>();

    public FXDrawer(Tab tab, Page page, UserInterface userInterface, String baseUrl) {
        super(baseUrl);
        this.tab = tab;
        this.page = page;
        this.ui = userInterface;
    }

    private void drawNode(Node node) {
        if (parents.size() > 0) {
            parents.getLast().add(node);
        } else {
            page.getDrawerPane().getParent().getChildren().add(node);
        }
    }

    private Pane getParent() {
        if (parents.size() > 0) {
            return parents.getLast().getParent();
        } else
            return page.getDrawerPane().getParent();
    }

    @Override
    public void drawText(String text) {
        drawText(text, Font.getDefault().getSize(), Font.getDefault().getName());
    }

    @Override
    public void drawText(String text, double fontSize, String fontName) {
        Label fxText = new Label(text);
        fxText.setFont(new Font(fontName, fontSize));
        if (hasEffect(Effect.FONT_BOLD))
            fxText.setStyle("-fx-font-weight: bold;");
        if (hasEffect(Effect.FONT_ITALIC))
            fxText.setStyle(fxText.getStyle() + " fx-font-style : italic;");
        if (hasEffect((Effect.FONT_UNDERLINE)))
            fxText.setUnderline(true);
        if (hasAction(HrefAction.class)) {
            fxText.setStyle(fxText.getStyle() + "-fx-font-style: italic;");
            Styler.changeColorToPassiveLink(fxText);
            fxText.setOnMouseEntered(Styler::changeColorToActiveLink);
            fxText.setOnMouseExited(Styler::changeColorToPassiveLink);
        }
        drawNode(fxText);
    }


    @Override
    public void setTitle(String text) {
        tab.setText(text);
    }

    @Override
    public void drawImage(String path) {
        final ImageView iv = new ImageView();
        drawNode(iv);
        page.getWindow().showLoading();
        (new Thread(() -> {
            Image image = new Image(getRelativePath(path));
            Platform.runLater(() -> {
                iv.setImage(image);
                page.getWindow().hideLoading();
            });
        }
        )).start();
    }

    private void usePane(DrawerPane drawerPane) {
        parents.addLast(drawerPane);
    }

    private void unUsePane() {
        DrawerPane drawerPane = parents.pollLast();
        drawNode(drawerPane.getParent());
    }

    @Override
    public void drawTable() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        usePane(new GridDrawerPane(gridPane));
    }

    @Override
    public void drawTableHeader() {
        useEffect(Effect.FONT_BOLD);
    }

    @Override
    public void endDrawTableHeader() {
        unUseEffect(Effect.FONT_BOLD);
    }

    @Override
    public void drawTableColumn() {
        ((GridDrawerPane) parents.getLast()).setCol(((GridDrawerPane) parents.getLast()).getCol() + 1);
        VBoxFlowPane vBoxFlowPane = new VBoxFlowPane();
        vBoxFlowPane.setMarign(2, 2, 2, 2);
        usePane(vBoxFlowPane);
    }

    @Override
    public void endDraTableColumn() {
        unUsePane();
    }

    @Override
    public void drawTableRow() {
        GridDrawerPane p = (GridDrawerPane) parents.getLast();
        p.setRow(p.getRow() + 1);
        p.setCol(0);
    }

    @Override
    public void endDrawTableRow() {
    }

    @Override
    public void drawUnOrderedList(String symbol) {
        VBox vBox = new VBox();
        UnOrderedListDrawPane listDrawPane = new UnOrderedListDrawPane(vBox, symbol);
        usePane(listDrawPane);
    }

    @Override
    public void drawOrderedList(String start, String symbol) {
        VBox vBox = new VBox();
        OrderedListDrawPane listDrawPane = new OrderedListDrawPane(vBox, Integer.parseInt(start), symbol);
        usePane(listDrawPane);
    }


    @Override
    public void endDrawOrderedList() {
        unUsePane();
    }

    @Override
    public void endDrawUnOrderedList() {
        unUsePane();
    }

    @Override
    public void drawListItem() {
        VBoxFlowPane flowPane = new VBoxFlowPane();
        Label space = new Label();
        space.setPrefWidth(16);
        Label space2 = new Label();
        space2.setPrefWidth(4);
        flowPane.getParent().getChildren().add(space);
        String string = "";
        if (parents.getLast().getClass().equals(UnOrderedListDrawPane.class))
            string = ((UnOrderedListDrawPane) parents.getLast()).getSymbol().getNext();
        else if (parents.getLast().getClass().equals(OrderedListDrawPane.class))
            string = ((OrderedListDrawPane) parents.getLast()).getSymbol().getNext();
        usePane(new ListItem(space, space2, new Label(string)));
    }

    @Override
    public void endDrawListItem() {
        unUsePane();
    }

    @Override
    public void drawNewLine() {
        if (!parents.isEmpty())
            parents.getLast().drawLine();
        else page.getDrawerPane().drawLine();
    }

    @Override
    public void drawSelectionList(Boolean multiple, String name, Integer size) {
        SelectionList selectionList = new SelectionList(multiple);
        selectionList.setSize(size);
        FormEntry formEntry = new FormEntry(selectionList.getContent(), name);
        selectionLists.addLast(selectionList);
        //todo fix the size of list
    }

    @Override
    public void endDrawSelectionList() {
        drawNode(selectionLists.pop().getContent());
    }

    @Override
    public void drawOptionGroup(String optionGroup) {
        selectionLists.getLast().addGroup(optionGroup);
    }

    @Override
    public void endDrawOptionGroup() {

    }

    @Override
    public void drawParagraph() {
        VBoxFlowPane vBoxFlowPane = new VBoxFlowPane();
        vBoxFlowPane.setMarign(10, 0, 10, 0);
        usePane(vBoxFlowPane);
    }

    @Override
    public void endDrawParagraph() {
        unUsePane();
    }

    @Override
    public void addOption(String text, String value, Boolean disabled, Boolean selected) {
        Button button = new Button(text);
        button.setUserData(value);
        button.setDisable(disabled);
        selectionLists.getLast().addItem(button);
        if (selected)
            selectionLists.getLast().select(button);
    }

    private void recursiveDrawDOM(Tag tag, TreeItem<String> parent) {
        for (Object element : tag.getChildren()) {
            if (element instanceof String)
                parent.getChildren().add(new TreeItem<>(element.toString()));
            else {
                TreeItem<String> newParent = new TreeItem<>(element.toString());
                parent.getChildren().add(newParent);
                if (((Tag) element).getChildren().isEmpty())
                    continue;
                newParent.setExpanded(true);
                recursiveDrawDOM((Tag) element, newParent);
            }
        }
    }


    @Override
    public void drawDOM(Tag rootTag) {
        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> root = new TreeItem("Document");
        root.setExpanded(true);
        treeView.setRoot(root);
        recursiveDrawDOM(rootTag, root);
        Stage DOMStage = new Stage();
        DOMStage.setTitle("DOM Tree View");
        DOMStage.setScene(new Scene(new VBox() {{
            this.getChildren().add(treeView);
        }}));
        DOMStage.show();
    }

    @Override
    public void linkTag(Tag node) {
        // TODO MAKE THIS FUNCtION
        return;

    }

    @Override
    public void endDrawTable() {
        Parent parent = parents.getLast().getParent();
        ((Pane) parent).setMaxWidth(parent.getBaselineOffset());//todo make sure if this correct
        unUsePane();
    }

    @Override
    public void drawCaption() {
        DrawerPane drawerPane = new DrawerPane(new FlowPane());
        usePane(drawerPane);
        useAlignment(Alignment.CENTER);
    }

    @Override
    public void endDrawCaption() {
        unUseAlignment();
    }

    @Override
    public void useAction(Action action) {
        super.useAction(action);
        DrawerPane drawerPane = new DrawerPane(new FlowPane());
        if (action instanceof HrefAction) {
            drawerPane.getParent().setCursor(Cursor.HAND);
            HrefAction hrefAction = (HrefAction) action;
            drawerPane.getParent().setOnMouseClicked((event) -> {
                Window window = this.ui.createNewWindow();
                window.search(hrefAction.getLink());
            });
        }
        parents.addLast(drawerPane);
    }

    @Override
    public void unUseAction() {
        super.unUseAction();
        unUsePane();
    }

    @Override
    public void drawFileInput(String name, String accept, Boolean multiple) {
        usePane(new DrawerPane(new HBox()));
        FormAction formAction = getLastAction(FormAction.class);
        Button button = new Button("Browser");
        Text text = new Text();
        text.setUserData("No File Chosen");
        text.setText(text.getUserData().toString());
        FileChooser fileChooser = new FileChooser();
        if (accept != null) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(accept, accept.split(",")));
        }
        if (formAction != null) {
            button.setOnAction(
                    e -> {
                        FormEntry formEntry = new FormEntry(text, null);
                        boolean hasFiles;
                        if (multiple) {
                            formEntry.value = fileChooser.showOpenMultipleDialog(Stage.getWindows().get(0));
                            hasFiles = !((List<File>) formEntry.value).isEmpty();
                        } else {
                            formEntry.value = fileChooser.showOpenDialog(Stage.getWindows().get(0));
                            hasFiles = formEntry.value != null;
                        }
                        if (!hasFiles)
                            text.setText(text.getUserData().toString());
                        else
                            text.setText(formEntry.value.toString());
                        formAction.setAttribute(name, formEntry);
                    }
            );
        }
        drawNode(button);
        drawNode(text);
        unUsePane();
    }


    @Override
    public void drawInput(String type, String name, String value, String placeHolder) {
        TextInputControl textField;
        switch (type) {
            case "password":
                textField = new PasswordField() {{
                    this.setText(value);
                }};
                break;
            case "hidden":
                textField = null;
                break;
            default:
                textField = new TextField(value);

        }
        final FormAction action = getLastAction(FormAction.class);
        action.setAttribute(name, new FormEntry(textField, value));
        if (textField == null)
            return;
        textField.setPromptText(placeHolder);
        textField.textProperty().addListener((o, old, newVal) -> action.setAttribute(name, new FormEntry(textField, value)));
        drawNode(textField);
    }

    static private class FormEntry {
        public Node node;
        public Object value;

        public FormEntry() {
            this(null, null);
        }

        public FormEntry(Node node, Object value) {
            this.node = node;
            this.value = value;
        }

    }

    protected void resetForm(FormAction formAction) {
        Map<String, Object> fields = formAction.getFields();
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            FormEntry formEntry = (FormEntry) field.getValue();
            if (formEntry.node != null) {
                if (formEntry.node instanceof TextInputControl) {
                    ((TextInputControl) formEntry.node).clear();
                } else if (formEntry.node instanceof Text) {
                    if ((formEntry.node).getUserData() != null)
                        ((Text) formEntry.node).setText((formEntry.node).getUserData().toString());
                }
            }
        }
    }

    protected void submitForm(FormAction formAction) {
        // TODO
    }

    @Override
    public void beginDrawButton(String type) {
        final FormAction formAction = getLastAction(FormAction.class);
        StackPane buttonPane = new StackPane();
        buttonPane.setEffect(new DropShadow(10, Color.GRAY));
        buttonPane.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
        buttonPane.setPadding(new Insets(2, 2, 2, 2));
        buttonPane.setCursor(Cursor.HAND);
        if (formAction != null && type != null && !type.equals("button"))
            buttonPane.setOnMouseClicked(e -> {
                if (type.equals("submit"))
                    submitForm(formAction);
                else
                    resetForm(formAction);
            });
        usePane(new DrawerPane(buttonPane));
    }

    @Override
    public void endDrawButton() {
        unUsePane();
    }

    @Override
    public void drawRadioInput(String name, String value, Boolean checked) {
        RadioButton radioButton = new RadioButton();
        radioButton.setUserData(value);
        radioButton.setSelected(checked);
        FormAction formAction = getLastAction(FormAction.class);
        if (formAction.getFields().keySet().contains(name)) {
            if (formAction.getFields().get(name) instanceof ToggleGroup) {
                radioButton.setToggleGroup((ToggleGroup) formAction.getFields().get(name));
            }
        } else {
            ToggleGroup toggleGroup = new ToggleGroup();
            //FormEntry formEntry = new FormEntry(toggleGroup , null);
            formAction.setAttribute(name, toggleGroup);
            radioButton.setToggleGroup(toggleGroup);
        }
        drawNode(radioButton);
    }

    @Override
    public void drawCheckBoxInput(String name, String value, Boolean checked) {
        CheckBox checkBox = new CheckBox();
        checkBox.setUserData(value);
        checkBox.setSelected(checked);
        FormAction formAction = getLastAction(FormAction.class);
        formAction.setAttribute(name, checkBox);
        drawNode(checkBox);
    }
}
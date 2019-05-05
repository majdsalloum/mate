package core.render.Symbols;

public class UnOrderedSymbol extends Symbol {
    String type="disk";

    public UnOrderedSymbol(String type)
    {
        this.type=type;
    }

    @Override
    public String getNext() {
        switch (type)
        {
            case "disk":
                symbol="●";
                break;
            case "circle":
                symbol="○";
                break;
            case "square":
                symbol="■";
                break;
            case "none":
                symbol="";
                break;
        }
        return symbol;
    }
}

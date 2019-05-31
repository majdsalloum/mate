package core.render.Symbols;

public class OrderedSymbol extends Symbol {
    int start = 1;
    String type="1";
    public OrderedSymbol(Integer start , String type)
    {
        this.start=start;
        this.type=type;
    }
    public OrderedSymbol(String type)
    {
        this.start=1;
        this.type=type;
    }
    public String getNext()
    {
        switch (type)
        {
            case "1":
                symbol=start+"";
                break;
            case "A":
                symbol=(convertToAlphaNumber(start)).toUpperCase();
                break;
            case "a":
                symbol=(convertToAlphaNumber(start)).toLowerCase();
                break;
            case "I":
                symbol=(convertToRomanNumber(start)).toUpperCase();
                break;
            case "i":
                symbol=(convertToRomanNumber(start)).toLowerCase();
                break;
        }
        start++;
        return symbol;
    }
    private String convertToAlphaNumber(int i)
    {
        return null;
    }//todo do this
    private String convertToRomanNumber(int i)
    {
        return null;
    }//todo do this
}

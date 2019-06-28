package core.render.Symbols;

import java.util.Arrays;

public class OrderedSymbol extends Symbol {
    int start = 1;
    String type="1";
    String []types={"a","A","1","i","I"};
    public OrderedSymbol(Integer start , String type)
    {
        this.start=start;
        if (Arrays.asList(types).contains(type))
        this.type=type;
        else this.type="1";

    }
    public OrderedSymbol(String type)
    {
        this.start=1;
        if (Arrays.asList(types).contains(type))
            this.type=type;
        else this.type="1";
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
    private String convertToRomanNumber(int num)
    {
        String[] romanCharacters = { "M", "CM", "D", "C", "XC", "L", "X", "IX", "V", "I" };
        int[] romanValues = { 1000, 900, 500, 100, 90, 50, 10, 9, 5, 1 };
        String result = "";

        for (int i = 0; i < romanValues.length; i++) {
            int numberInPlace = num / romanValues[i];
            if (numberInPlace == 0) continue;
            result += numberInPlace == 4 && i > 0? romanCharacters[i] + romanCharacters[i - 1]:
                    new String(new char[numberInPlace]).replace("\0",romanCharacters[i]);
            num = num % romanValues[i];
        }
        return result;
    }

    private String convertToAlphaNumber(int i)
    {
        if( i<0 ) {
            return "-"+convertToAlphaNumber(-i-1);
        }
        int quot = i/26;
        int rem = i%26;
        char letter = (char)((int)'A' + rem);
        if( quot == 0 ) {
            return ""+letter;
        } else {
            return convertToAlphaNumber(quot-1) + letter;
        }
    }
}

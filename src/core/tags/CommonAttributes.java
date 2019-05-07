package core.tags;

final public class CommonAttributes {

    private CommonAttributes() {
    }


    // ACCORDING TO W3 Specification
    static final String[] GLOBAL_HTML_ATTRIBUTES = {"class", "accessKey", "dir", "hidden", "id", "lang", "style", "title", "name"};

    static <T> T[] joinArrays(T[] a, T[] b) {
        final int alen = a.length;
        final int blen = b.length;
        if (alen == 0) {
            return b;
        }
        if (blen == 0) {
            return a;
        }
        final T[] result = (T[]) java.lang.reflect.Array.
                newInstance(a.getClass().getComponentType(), alen + blen);
        System.arraycopy(a, 0, result, 0, alen);
        System.arraycopy(b, 0, result, alen, blen);
        return result;
    }
}

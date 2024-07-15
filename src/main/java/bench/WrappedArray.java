package bench;

public class WrappedArray {
    private final String[] array;

    WrappedArray(String[] array) {
        this.array = array;
    }

    public String get(int index) {
        return array[index];
    }
}

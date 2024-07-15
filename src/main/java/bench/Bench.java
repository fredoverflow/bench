package bench;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Bench {
    private static final String[] array = {"apple", "banana", "cherry"};
    private static final WrappedArray wrappedArray = new WrappedArray(array);
    private static final List<String> arrayAsList = Arrays.asList(array);
    private static final List<String> arrayList = new ArrayList<>(arrayAsList);
    private static final List<String> vector = new Vector<>(arrayAsList);

    @Benchmark // 0,518 ± 0,003  ns/op
    public String array() {
        return array[1];
    }

    @Benchmark // 0,778 ± 0,015  ns/op
    public String wrappedArray() {
        return wrappedArray.get(1);
    }

    @Benchmark // 0,908 ± 0,006  ns/op
    public String arrayAsList() {
        return arrayAsList.get(1);
    }

    @Benchmark // 1,120 ± 0,026  ns/op
    public String arrayList() {
        return arrayList.get(1);
    }

    @Benchmark // 16,597 ± 0,131  ns/op
    public String vector() {
        return vector.get(1);
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}

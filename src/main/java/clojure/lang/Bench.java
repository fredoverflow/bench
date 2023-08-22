package clojure.lang;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Bench {
    private static final int N = 4_000_000;

    @Benchmark // 53,2 ms
    public ArrayList<String> arrayList() {
        var result = new ArrayList<String>();
        for (int i = 0; i < N; ++i) {
            result.add("fred");
        }
        return result;
    }

    @Benchmark // 238 ms
    public LinkedList<String> linkedList() {
        var result = new LinkedList<String>();
        for (int i = 0; i < N; ++i) {
            result.add("fred");
        }
        return result;
    }

    // @Benchmark // hochgerechnet ca. 200 Minuten
    public CopyOnWriteArrayList<String> copyOnWrite() {
        var result = new CopyOnWriteArrayList<String>();
        for (int i = 0; i < N; ++i) {
            result.add("fred");
        }
        return result;
    }

    @Benchmark // 108 ms
    public PersistentVector persistentVector() {
        var result = PersistentVector.EMPTY;
        for (int i = 0; i < N; ++i) {
            result = result.cons("fred");
        }
        return result;
    }

    @Benchmark // 46,3 ms
    public PersistentVector transientPersistent() {
        var result = PersistentVector.EMPTY.asTransient();
        for (int i = 0; i < N; ++i) {      //////////////
            result = result.conj("fred");
        }
        return result.persistent();
    }                /////////////


    private static final ArrayList<String> list = new Bench().arrayList();

    @Benchmark // 17,9 ms
    public int listReduce() {
        return list.stream().reduce(0, (sumSoFar, string) -> sumSoFar + string.length(), Integer::sum);
    }

    private static final PersistentVector vector = new Bench().transientPersistent();

    @Benchmark // 60,5 ms
    public int vectorReduceJava() {
        @SuppressWarnings("unchecked")
        Stream<String> stream = vector.stream();

        return stream.reduce(0, (sumSoFar, string) -> sumSoFar + string.length(), Integer::sum);
    }

    @Benchmark // 16,8 ms
    public int vectorReduceClojure() {
        return (Integer) vector.reduce(new AFn() {
            // public Object invoke()
            // public Object invoke(Object a)
            @Override
            public Object invoke(Object sumSoFar, Object string) {
                return (Integer) sumSoFar + ((String) string).length();
            }
            // public Object invoke(Object a, Object b, Object c)
            // public Object invoke(Object a, Object b, Object c, Object d)
            // ...
        }, 0);
    }


    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}

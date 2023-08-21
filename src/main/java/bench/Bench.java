package bench;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

class Int {
    private int x;

    public Int(int i) {
        x = i;
    }
}

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Bench {
    private static final int N = 1_000;

    @Benchmark
    public Int[] heap() {
        Int[] a = new Int[N];
        for (int i = 0; i < N; ++i) {
            a[i] = new Int(i); // if (exhausted) collect();
        }                      // pointer += sizeof(Int);
        return a; // 21 ms
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}

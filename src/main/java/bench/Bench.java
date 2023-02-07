package bench;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Bench {
    @Benchmark
    public String replace_Char() {
        return "The quick brown fox jumps over the lazy dog".replace(' ', ';');
    }

    @Benchmark
    public String replace_String() {
        return "The quick brown fox jumps over the lazy dog".replace(" ", ";");
    }

    @Benchmark
    public String replaceAll() {
        return "The quick brown fox jumps over the lazy dog".replaceAll(" ", ";");
    }

    @Benchmark
    public String replaceAll_PreCompiled() {
        return SPACE.matcher("The quick brown fox jumps over the lazy dog").replaceAll(";");
    }

    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}

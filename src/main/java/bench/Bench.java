package bench;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Bench {
    @Param("DE12345678901234567890")
    private String kompakt;

    @Benchmark
    public String substring() {
        String kompakt = this.kompakt;
        return kompakt.substring(0, 4) + ' ' +
                kompakt.substring(4, 8) + ' ' +
                kompakt.substring(8, 12) + ' ' +
                kompakt.substring(12, 16) + ' ' +
                kompakt.substring(16, 20) + ' ' +
                kompakt.substring(20, 22);
    }

    @Benchmark
    public String charAt() {
        String kompakt = this.kompakt;
        return "" +
                kompakt.charAt(0) + kompakt.charAt(1) + kompakt.charAt(2) + kompakt.charAt(3) + ' ' +
                kompakt.charAt(4) + kompakt.charAt(5) + kompakt.charAt(6) + kompakt.charAt(7) + ' ' +
                kompakt.charAt(8) + kompakt.charAt(9) + kompakt.charAt(10) + kompakt.charAt(11) + ' ' +
                kompakt.charAt(12) + kompakt.charAt(13) + kompakt.charAt(14) + kompakt.charAt(15) + ' ' +
                kompakt.charAt(16) + kompakt.charAt(17) + kompakt.charAt(18) + kompakt.charAt(19) + ' ' +
                kompakt.charAt(20) + kompakt.charAt(21);
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}

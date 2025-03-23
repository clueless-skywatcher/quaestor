package io.quaestor.benchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import io.quaestor.idsets.roaring.RoaringBitmapIDSet;

@State(Scope.Benchmark)
public class RoaringBitmapIDSetBenchmark {
    private RoaringBitmapIDSet roaringBitmap;

    @Setup(Level.Iteration)
    public void setUp() {
        roaringBitmap = new RoaringBitmapIDSet();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testAdd() {
        for (int i = 0; i < 20000; i++) {
            roaringBitmap.add(i);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testContains() {
        for (int i = 0; i < 20000; i++) {
            roaringBitmap.contains(i);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(RoaringBitmapIDSetBenchmark.class.getSimpleName())
            .forks(1)
            .warmupIterations(1)
            .measurementIterations(1)
            .build();

        new Runner(opt).run();
    }
}

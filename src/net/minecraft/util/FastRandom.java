package net.minecraft.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FastRandom extends Random
{
    public static long mix(long left, long right)
    {
        left = left * (left * 6364136223846793005L + 1442695040888963407L);
        return left + right;
    }
    private final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    private volatile long seed;
    private volatile boolean seedSet;

    public FastRandom() {
        this.setSeed(System.nanoTime());
    }
    public FastRandom(long seed) {
        this.setSeed(seed);
    }

    @Override
    public synchronized void setSeed(long seed) {
        this.seed = seed ^ 0x5DEECE66DL;
        this.seedSet = true;
    }

    public long getSeed() {
        return this.seed;
    }

    @Override
    public int next(int bits) {
        if (!seedSet) {
            return threadLocalRandom.nextInt(bits);
        }
        seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
        return (int) (seed >>> (48 - bits));
    }

    @Override
    public int nextInt() {
        return next(32);
    }

    @Override
    public int nextInt(int bound) {
        if (bound <= 0)
            throw new IllegalArgumentException("bound must be positive");

        if ((bound & -bound) == bound) // i.e., bound is a power of 2
            return (int) ((bound * (long) next(31)) >> 31);

        int bits, val;
        do {
            bits = next(31);
            val = bits % bound;
        } while (bits - val + (bound - 1) < 0);
        return val;
    }

    @Override
    public long nextLong() {
        return ((long) next(32) << 32) + next(32);
    }

    @Override
    public boolean nextBoolean() {
        return next(1) != 0;
    }

    @Override
    public float nextFloat() {
        return next(24) / ((float) (1 << 24));
    }

    @Override
    public double nextDouble() {
        return (((long) (next(26)) << 27) + next(27)) / (double) (1L << 53);
    }

    @Override
    public double nextGaussian() {
        if (haveNextNextGaussian) {
            haveNextNextGaussian = false;
            return nextNextGaussian;
        } else {
            double v1, v2, s;
            do {
                v1 = 2 * nextDouble() - 1; // between -1 and 1
                v2 = 2 * nextDouble() - 1; // between -1 and 1
                s = v1 * v1 + v2 * v2;
            } while (s >= 1 || s == 0);
            double multiplier = Math.sqrt(-2 * Math.log(s)/s);
            nextNextGaussian = v2 * multiplier;
            haveNextNextGaussian = true;
            return v1 * multiplier;
        }
    }

    private transient boolean haveNextNextGaussian;
    private transient double nextNextGaussian;

    public long setDecorationSeed(long baseSeed, int x, int z)
    {
        this.setSeed(baseSeed);
        long i = this.nextLong() | 1L;
        long j = this.nextLong() | 1L;
        long k = (long)x * i + (long)z * j ^ baseSeed;
        this.setSeed(k);
        return k;
    }

    public long setFeatureSeed(long baseSeed, int x, int z)
    {
        long i = baseSeed + (long)x + (long)(10000 * z);
        this.setSeed(i);
        return i;
    }

    public long setLargeFeatureSeed(long seed, int x, int z)
    {
        this.setSeed(seed);
        long i = this.nextLong();
        long j = this.nextLong();
        long k = (long)x * i ^ (long)z * j ^ seed;
        this.setSeed(k);
        return k;
    }

    public long setBaseChunkSeed(int x, int z)
    {
        long i = (long)x * 341873128712L + (long)z * 132897987541L;
        this.setSeed(i);
        return i;
    }
}

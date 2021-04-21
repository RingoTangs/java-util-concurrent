package com.ymy.boot.thread;

import java.util.concurrent.*;

/**
 * ForkJoinPool: 分支合并(多线程计算 ==> 合并计算结果)
 * 案例: 计算 1-100 的和, 多线程计算然后分支合并
 *
 * @author Ringo
 * @date 2021/4/17 21:36
 */
public class ForkJoinPoolDemo {
    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Future 获得计算结果
        Future<Integer> future = forkJoinPool.submit(new ComputedTask(1, -2));
        System.out.println(future.get());
    }
}

/**
 * 分支计算 1-50、51-100的和然后合并
 *
 * @author Ringo
 * @date 2021/4/17
 */
class ComputedTask extends RecursiveTask<Integer> {
    private int start; // 开始
    private int end; // 结束
    private int result; // 结果

    // 分支计算的阈值(数量 < 10个就直接 for 循环计算)
    private int threshold = 10;

    // 构造方法
    public ComputedTask(int start, int end) {
        if (start > end) {
            this.start = end;
            this.end = start;
            return;
        }
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= threshold) {
            for (int i = start; i <= end; i++) {
                result += i;
            }
            return result;
        }

        // 分成两个任务
        int mid = (start + end) / 2;
        ComputedTask task1 = new ComputedTask(start, mid);
        ComputedTask task2 = new ComputedTask(mid + 1, end);

        // 开一个新线程计算 task1
        task1.fork();

        // 使用本线程计算 task2
        Integer task2Res = task2.compute();
        return task1.join() + task2Res;
    }
}

package book.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class WeighAnimalTask extends RecursiveTask<Double> {
    private int start;
    private int end;
    private Double[] weights;
    public final int taskNr;

    public static AtomicInteger weightProducer = new AtomicInteger(0);
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final AtomicInteger forkCounter = new AtomicInteger(0);

    public WeighAnimalTask(Double[] weights, int start, int end) {
        this.start = start;
        this.end = end;
        this.weights = weights;
        this.taskNr =  counter.incrementAndGet();
        System.out.println("creating new task # "+ this.taskNr +" [start = " + start + ", end = " + end  + "]");
    }

    protected Double compute() {

        if(end - start <= 3) {
            System.out.println("computing task #" + taskNr);
            double sum = 0;
            for(int i = start; i < end; i++) {
                weights[i] =(double) weightProducer.incrementAndGet();
                sum += weights[i];
            }
            System.out.println("Returning result for task task #" + taskNr +", value = "+sum);
            return sum;

        } else {
            int middle = start + ((end - start) / 2);
            System.out.println("forking #" + forkCounter.incrementAndGet() +" for task # "+ taskNr);
            WeighAnimalTask otherTask = new WeighAnimalTask(weights, start, middle);
            otherTask.fork();
            Double resultThis = new WeighAnimalTask(weights, middle, end).compute();
            System.out.println("join waiting for task #" + otherTask.taskNr);
            Double resultThat = otherTask.join();
            Double result = resultThis + resultThat;
            System.out.println("Returning result for task task #" + taskNr +", value = "+result);
            return result;
        }
    }



    public static void main(String[] args) {
        Double[] weights = new Double[10];

        WeighAnimalTask task = new WeighAnimalTask(weights, 0, weights.length);
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("invoking forkJoin for task # " + task.taskNr);
        Double sum = pool.invoke(task);
        System.out.println("Sum: " + sum);
    }
}

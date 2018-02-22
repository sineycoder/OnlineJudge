package cn.jsut.coder.judger;

import java.util.concurrent.ArrayBlockingQueue;

public class JudgerRun implements Runnable {

    private static final JudgerRun run = new JudgerRun();
    private static final ArrayBlockingQueue<String> queue = Judger.getQueue();

    private JudgerRun(){}

    public static JudgerRun getRun(){
        return run;
    }

    @Override
    public void run() {

        try {
            String take = queue.take();
            System.out.println(Thread.currentThread().getName()+" 开始判题");
            System.out.println(Thread.currentThread().getName()+" 所判题内容为 "+take);
            System.out.println(Thread.currentThread().getName()+"判题结束");
        } catch (InterruptedException e) {

        }
    }
}

package cn.jsut.coder.judger;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:
 * @date:2018/1/20 14:15
 * @version:
 * @copyright:
 */
@Component
public class Judger {

    //获取系统CPU个数，然后开启对应CPU*10的队列大小
    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);
    private static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(THREAD_SIZE*10);
    private static final JudgerRun run = JudgerRun.getRun();

    public static ArrayBlockingQueue<String> getQueue(){
        return queue;
    }

    public void judge(String code) throws InterruptedException {
        queue.put(code);
        executorService.execute(run);
    }

}

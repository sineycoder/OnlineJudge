package cn.jsut.oj.Ecoder.Tools;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DeleteQueue implements Runnable{

    private BlockingQueue<File> deleteQueue = null;
    public DeleteQueue(){
        deleteQueue  = new ArrayBlockingQueue<>(Runtime.getRuntime().availableProcessors()*20);
    }


    public void addQueue(File file){
        try {
            deleteQueue.put(file);
        } catch (Exception e) {
            Helper.getHelper().outputLogs("DeleteQueue puts error -> " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                File file = deleteQueue.take();
                Helper.getHelper().deleteAll(file);
                Helper.getHelper().outputLogs("[ OK ] DeleteQueue path : "+file.toString());
            } catch (Exception e) {
                Helper.getHelper().outputLogs("DeleteQueue take error -> " + e.getMessage());
            }
        }
    }
}

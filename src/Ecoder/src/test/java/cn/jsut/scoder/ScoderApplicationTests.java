package cn.jsut.scoder;

import cn.jsut.coder.app.ScoderApplication;
import cn.jsut.coder.judger.Judger;
import cn.jsut.coder.service.ScoderServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScoderApplication.class})
public class ScoderApplicationTests {

	@Autowired
	Judger judger;

	@Autowired
	ScoderServiceImp service;


	@Test
	public void t(){
		System.out.println(service);
	}

	static int num = 10;
	private static CountDownLatch latch = new CountDownLatch(num);
	@Test
	public void test1() throws InterruptedException {

		for(int i = 0;i<1000;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						latch.await();
						judger.judge(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			},"name"+i).start();
			latch.countDown();
//			System.out.println(i);
		}
		Thread.sleep(20000);
	}

}

package cn.jsut.oj.Ecoder.server;


import cn.jsut.oj.Ecoder.Tools.Helper;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

public class StartJudger {
	private static final int SERVER_PORT = 8989;//judge port
	/**
	 * 使用Thrift的多线程服务端
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			TProcessor processor = new JudgeServer.Processor<JudgeServer.Iface>(new JudgeServerImp());
			TServerSocket transport = new TServerSocket(SERVER_PORT);//阻塞IO
			TThreadPoolServer.Args Args = new TThreadPoolServer.Args(transport);//设置服务类型，这里用多线程服务类型
			Args.processor(processor);
			Args.protocolFactory(new TCompactProtocol.Factory());//使用块传输，压缩数据，传输较快
			TServer server = new TThreadPoolServer(Args);
			server.serve();
		}catch(Exception e){
			Helper.getHelper().outputLogs("transport main error -> "+e.getMessage());
		}
	}
}

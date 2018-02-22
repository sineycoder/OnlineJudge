package cn.jsut.oj.Ecoder.server;

import cn.jsut.oj.Ecoder.Tools.DeleteQueue;
import cn.jsut.oj.Ecoder.Tools.Helper;
import cn.jsut.oj.Ecoder.lang.Lang;
import cn.jsut.oj.Ecoder.po.JudgeBody;
import cn.jsut.oj.Ecoder.po.JudgeResult;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;

/**
 * 测评处
 * @author siney
 *
 */
public class JudgeServerImp implements JudgeServer.Iface {
	private static final String JUDGE_PATH = System.getenv("JUDGE_PATH");
	private static final String COMPILE_PATH = System.getenv("JUDGER_FILE");
	private static final String JUDGE_LOGS = System.getenv("JUDGE_LOGS");
	private static final String COMPILE_FILE = System.getenv("JUDGER_FILE")+"/compile";
	private static final String JUDGER_FILE = System.getenv("JUDGER_FILE")+"/judge";
	private static DeleteQueue deleteQueue = null;
	static {
		/**
		 * create judge directory,if not exist,create all.
		 */
		File file = new File(JUDGE_PATH);
		if (!file.exists()) file.mkdirs();
		file = new File(COMPILE_PATH);
		if (!file.exists()) file.mkdirs();

		Helper.getHelper().initJudge(COMPILE_FILE,"/compile");
		Helper.getHelper().initJudge(JUDGER_FILE,"/judge");
		deleteQueue = new DeleteQueue();
		new Thread(deleteQueue).start();//start DeleteQueue Thread,防止删除文件时影响返回时间

		Helper.getHelper().createNewFile(new File(JUDGE_LOGS));
		Helper.getHelper().createNewFile(new File(System.getenv("JUDGE_QUEUE_LOGS")));
	}


	/**
	 * @param content JudgeBody
	 * @return CompileTest Result
	 */
	@Override
	public String compileJudge(String content) {
		JudgeBody body = JSON.parseObject(content,JudgeBody.class);
		JudgeResult result = compile(body);
		File file = new File(JUDGE_PATH + "/" + body.getJudgeId());
		deleteQueue.addQueue(file);//only compile.finally,deleteAll files.
		if(result != null)return JSON.toJSONString(new JudgeResult[]{result});
		return null;
	}


	/**
	 *
	 * @return a JudgeResult object.if success,return null; or ,return this obj.
	 */
	private JudgeResult compile(JudgeBody body) {
		//创建目标id的文件夹目录
		File file = new File(JUDGE_PATH + "/" + body.getJudgeId());
		JudgeResult result = null;
		try {

			if (file.exists()) Helper.getHelper().deleteAll(file);
			Helper.getHelper().mkdir(file);

			Lang lang = Helper.getHelper().judgeLanguage(body,file);//创建对应语言的类
//			System.out.println(lang);

			result = Helper.getHelper().compiling(lang,COMPILE_FILE,file,"compiling");//如果没错误，则返回null
//			System.out.println("compiling result = "+result);
		} catch (Exception e) {
			Helper.getHelper().outputLogs(e.getMessage());
		}
		return result;
	}

	@Override
	public String startJudge(String content){
		JudgeBody body = JSON.parseObject(content,JudgeBody.class);
		JudgeResult result = compile(body);
		if(result != null){

			deleteQueue.addQueue(new File(JUDGE_PATH + "/" + body.getJudgeId()));
			return JSON.toJSONString(new JudgeResult[]{result});
		}
		JudgeResult[] results = judge(body);
		return JSON.toJSONString(results);///*******/
	}

	public JudgeResult[] judge(JudgeBody body){
		JudgeResult[] results = null;
		File file = null;
		try {
			//创建目标id的文件夹目录
			file = new File(JUDGE_PATH + "/" + body.getJudgeId());
			Lang lang = null;
			if(!body.isSpecialJudge()){
				results = new JudgeResult[body.getInput().size()];
				lang = Helper.getHelper().judgeLanguage(body,file);//创建对应语言的类
				//start judging
				for(int i = 0;i<results.length;i++){
					Helper.getHelper().writeCase(file,body,i);
					results[i] = Helper.getHelper().judging(lang,JUDGER_FILE,file,body,i,"judging");
				}

			}else{
				//special judge

			}
		}catch (Exception e){
			Helper.getHelper().outputLogs("func:judge -> "+e.getMessage());
		}finally {
			if(file != null){
				deleteQueue.addQueue(file);
			}
		}
		return results;
	}

}

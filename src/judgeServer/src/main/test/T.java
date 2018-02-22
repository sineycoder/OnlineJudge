import cn.jsut.oj.Ecoder.po.JudgeBody;
import cn.jsut.oj.Ecoder.po.JudgeResult;
import cn.jsut.oj.Ecoder.server.JudgeServer;
import cn.jsut.oj.Ecoder.server.JudgeServerImp;
import com.alibaba.fastjson.JSON;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class T {

    @Test
    public void test(){
        /*JudgeContent test = new JudgeContent();
        test.setCode("Code1");
        test.setCpu_limit(112);
        String jsonString = JSON.toJSONString(test);
        System.out.println(jsonString);
        JudgeContent result = JSON.parseObject(jsonString,JudgeContent.class);
        System.out.println(result);*/

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sf.format(new Date());
        System.out.println(format);

    }

    @Test
    public void client(){
        String SERVER_IP = "localhost";
        int PORT = 8989;
        int TIME_OUT = 300000;
        try{
            //获取会话
            TTransport transport = new TSocket(SERVER_IP,PORT,TIME_OUT);
            //选择会话协议，注意要和服务端一致，不然无法通信
            TProtocol protocol = new TCompactProtocol(transport);
            JudgeServer.Client client = new JudgeServer.Client(protocol);
            //打开会话
            transport.open();


            List<String> in = new ArrayList<>();
            List<String> out = new ArrayList<>();
            int size = 1;
            for(int i = 0;i<size;i++){
                in.add(i+" "+(i+2));
                out.add(""+(i+i+2));
            }
            JudgeBody j = new JudgeBody();
            j.setJudgeId(UUID.randomUUID().toString().replace("-",""));
            j.setJudgeLanguage("JAVA");
            /*j.setCodes(
                    "#include<stdio.h>\n"+
                            "int main(){\n" +
                            "int a,b;\n" +
                            "scanf(\"%d%d\",&a,&b);\n" +
                            "printf(\"%d\",a+b);\n" +
                            "return 0;\n" +
                            "}\n"
            );*/
            j.setCodes("import java.util.Scanner;\n" +
                    "public class Main{\n" +
                    "    public static void main(String args[]){\n" +
                    "    int[] arr = new int[1000000];\n" +
                    "        Scanner sn = new Scanner(System.in);\n" +
                    "        int a = sn.nextInt(),b = sn.nextInt();\n" +
                    "        System.out.println(a + b+1);\n" +
                    "        sn.close();\n" +
                    "    }\n" +
                    "}\n");
            j.setCpu_limit(1000);
            j.setRam_limit(64*1024);
            j.setInput(in);
            j.setOutput(out);
//        System.out.println(JSON.toJSONString(j));
            long a = System.currentTimeMillis();
            System.out.println("GO..");
            String s = client.startJudge(JSON.toJSONString(j));
            JudgeResult[] results = JSON.parseObject(s,JudgeResult[].class);
            long b = System.currentTimeMillis();
            System.out.println(b-a);
            for (JudgeResult u:results) {
                System.out.println(u);
            }
            transport.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test()
    public void test2(){
        List<String> in = new ArrayList<>();
        List<String> out = new ArrayList<>();
        int size = 10;
        for(int i = 0;i<size;i++){
            in.add(i+" "+(i+2));
            out.add(""+(i+i+2));
        }
        JudgeBody j = new JudgeBody();
        j.setJudgeId(UUID.randomUUID().toString().replace("-",""));
        j.setJudgeLanguage("C");
        j.setCodes(
                "#include<stdio.h>\n"+
                        "int main(){\n" +
                        "int a,b;\n" +
                        "scanf(\"%d%d\",&a,&b);\n" +
                        "printf(\"%d\",a+b);\n" +
                        "return 0;\n" +
                        "}\n"
        );
        j.setCpu_limit(800);
        j.setRam_limit(65535);
        j.setInput(in);
        j.setOutput(out);
//        System.out.println(JSON.toJSONString(j));
        long a = System.currentTimeMillis();
        String s = new JudgeServerImp().startJudge(JSON.toJSONString(j));
        long b = System.currentTimeMillis();

        System.out.println(b-a);
    }
    @Test
    public void test3(){
        Scanner sn = new Scanner(System.in);
        int a = sn.nextInt(),b = sn.nextInt();
        System.out.println(a + b);
        sn.close();
    }
}

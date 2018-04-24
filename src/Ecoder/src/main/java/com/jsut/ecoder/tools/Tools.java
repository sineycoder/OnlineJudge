package com.jsut.ecoder.tools;

import com.alibaba.fastjson.JSON;
import com.jsut.ecoder.po.ProblemRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Component
public class Tools {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private JudgerHelper judgerHelper;

    public static final String IMAGE_REP_PATH = System.getenv("JUDGE_PATH")+"/images/repository";
    public static final String IMAGE_USE_PATH = System.getenv("JUDGE_PATH")+"/images/use";
    public static final String IMAGE_UPLOAD = System.getenv("JUDGE_PATH")+"/images/upload";

    public Tools(){
        File file = new File(IMAGE_USE_PATH);
        if(!file.exists())file.mkdirs();
        file = new File(IMAGE_REP_PATH);
        if(!file.exists())file.mkdirs();
        file = new File(IMAGE_UPLOAD);
        if(!file.exists())file.mkdirs();
        JudgerHelper.getHelper().initJudge(IMAGE_USE_PATH+"/default_portrait.jpg","/default_portrait.jpg");
    }

    public static void cutImage(String sourcePath,String descpath,int x1, int y1,int width,int height)throws Exception{
        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            is = new FileInputStream(sourcePath);
            String fileSuffix = sourcePath.substring(sourcePath
                    .lastIndexOf(".") + 1);
            Iterator<ImageReader> it = ImageIO
                    .getImageReadersByFormatName(fileSuffix);
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x1, y1, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, fileSuffix, new File(descpath));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is = null;
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iis = null;
            }
        }

    }


    public void sendEmail(String to,String content)throws Exception{
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mailMessage);

        message.setFrom(javaMailSender.getUsername());
        message.setTo(to);
        message.setSubject("来自Ecoder的信息");
        message.setText(content,true);

        javaMailSender.send(mailMessage);
    }

    public String returnNowDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getFilenameSuffix(String filename){
        return filename.substring(filename.lastIndexOf("."));
    }

    public String getFilenamePreffix(String filename){
        return filename.substring(0,filename.lastIndexOf("."));
    }

    public void writeImage(HttpServletResponse response, String imagePath)throws Exception{
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream inputStream = new FileInputStream(imagePath);
        byte[] b = new byte[1024];
        while(inputStream.read(b) != -1){
            outputStream.write(b);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    public void unZipAndGetCase(MultipartFile file, String dir_path,
                                Map<String,Object> map, ProblemRecords records) throws Exception {
        String dir = dir_path + UUID.randomUUID().toString().replace("-","");
        File f = new File(dir);
        if(!f.exists()){
            f.mkdirs();
        }
        f = new File(dir+"/"+file.getOriginalFilename());
        file.transferTo(f);
        System.out.println(f);
        Process exec = Runtime.getRuntime().exec("unzip " + f.getAbsolutePath() + " -d " + dir);
        exec.waitFor();
        f.delete();
        f = new File(dir);
        File[] files_in = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if(s != null && s.toLowerCase().endsWith(".in"))
                    return true;
                return false;
            }
        });
        List<File> in_list = Arrays.asList(files_in);
        File[] files_out = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if(s != null && s.toLowerCase().endsWith(".out"))
                    return true;
                return false;
            }
        });
        List<File> out_list = Arrays.asList(files_out);
        Collections.sort(in_list, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        Collections.sort(out_list, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        in_list.toArray(files_in);
        out_list.toArray(files_out);
        if(files_in.length != files_out.length){
            map.put("error","the number of [.in] & [.out] are not match !");
        }else{
            int length = files_in.length;
            List<String> in = new ArrayList<>();
            List<String> out = new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            BufferedReader br = null;
            for(int i = 0;i < length;i++){
                String filename_in = files_in[i].getName().substring(0,files_in[i].getName().lastIndexOf("."));
                String filename_out = files_out[i].getName().substring(0,files_out[i].getName().lastIndexOf("."));
                System.out.println(files_in[i]+" "+files_out[i]);
                if(filename_in.equals(filename_out)){
                    //in file
                    String s = null;
                    sb.setLength(0);
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(files_in[i]),"UTF-8"));
                    while((s = br.readLine()) != null){
                        sb.append(s+"\n");
                    }
                    in.add(sb.toString().trim());
                    //out file
                    s = null;
                    sb.setLength(0);
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(files_out[i]),"UTF-8"));
                    while((s = br.readLine()) != null){
                        sb.append(s+"\n");
                    }
                    out.add(sb.toString().trim());
                }else{
                    map.put("error","fileName ["+filename_in+"] & ["+filename_out+"] is not match");
                    judgerHelper.deleteAll(f);
                    return;
                }
            }
            records.setProblemInput(JSON.toJSONString(in));
            records.setProblemOutput(JSON.toJSONString(out));
            judgerHelper.deleteAll(f);
        }
    }


}

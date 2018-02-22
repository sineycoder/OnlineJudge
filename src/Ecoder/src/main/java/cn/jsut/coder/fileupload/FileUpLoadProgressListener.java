package cn.jsut.coder.fileupload;

import cn.jsut.coder.po.ProgressEntity;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author:
 * @date:2018/1/17 9:00
 * @version:
 * @copyright:
 */
@Component
public class FileUpLoadProgressListener implements ProgressListener {
    private HttpSession session;

    public void setSession(HttpSession session){
        this.session = session;
        ProgressEntity status = new ProgressEntity();
        session.setAttribute("status",status);
    }

    @Override
    public void update(long nowByte, long process, int count) {
        ProgressEntity progressEntity = (ProgressEntity) session.getAttribute("status");
        progressEntity.setUpByte(nowByte);
        progressEntity.setUpSize(process);
        progressEntity.setUpItems(count);
    }
}

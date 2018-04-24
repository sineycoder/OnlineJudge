package com.jsut.ecoder.timer;

import com.jsut.ecoder.po.Notice;
import com.jsut.ecoder.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TimeSchedule {

    @Autowired
    private CommonService commonService;

    /*@Scheduled(fixedRate = 1000)
    public void accessDb() throws Exception {
        List<Notice> notices = commonService.queryContestStatus(new Date());
        System.out.println(notices.size());
    }*/


}

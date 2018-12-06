package com.datagroup.ESLS.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BatchController {
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobOperator jobOperator;
    @Resource(name="goodsJob")
    private Job batchJob;
    // 秒分时天月 每1分钟执行1次0 0/1 * * * ?
    @Scheduled(cron = "0 0/1000 * * * ?")
    public void job3() throws Exception {
        JobExecution run = jobLauncher.run(batchJob, new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters());
        run.getId();
    }

}

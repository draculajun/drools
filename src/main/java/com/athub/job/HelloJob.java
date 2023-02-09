package com.athub.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @Author Wang wenjun
 */
public class HelloJob implements Job {

    static int i = 0;

    public HelloJob() {
        i++;
    }

    public void execute(JobExecutionContext context) {
        System.out.println("---helloJob--- " + i);
    }

}

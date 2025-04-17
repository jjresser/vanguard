package com.example.demo.cron;

import com.example.demo.service.CronJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
public class CronJobScheduler implements SchedulingConfigurer {

    @Value("${spring.cronjob.seconds}")
    private int cronSeconds;

    @Autowired
    private CronJobService cronJobService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        String cronExpression = "*/"+cronSeconds+" * * * * *";
        taskRegistrar.addTriggerTask(
                cronJobService::executeScheduledTask,
                new CronTrigger(cronExpression)
        );
    }
}

package br.com.reflection;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class Extracao 
{
	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws Exception 
	{
		/**
		 * Carregar o arquivo dos Jobs 
		 * 
		 **/
		File jobs = new File("C:\\Users\\jonas.melo\\Desktop\\job-1.0.jar");
		URI uriJabA = jobs.toURI();
		
		
		URL[] urlJars = new URL[] {uriJabA.toURL()};
         
		
        /**
         * Cria 2 classes dos tipo JobA e JobB por reflection
         * 
         **/
        ClassLoader loader = new URLClassLoader(urlJars);
        Class<? extends Job> jobA = (Class<? extends Job>) loader.loadClass("br.com.job.JobA");
        Class<? extends Job> jobB = (Class<? extends Job>) loader.loadClass("br.com.job.JobB");
        
        
        /**
         * Utiliza o Quartz para agendar a execucao dos Jobs
         * 
         **/
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();  
        Scheduler scheduler = schedFact.getScheduler();
            
        JobDetail jobADetail = JobBuilder.newJob(jobA).build();      
        Trigger triggerJobA = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).forJob(jobADetail).build();  
        scheduler.scheduleJob(jobADetail, triggerJobA);  
        scheduler.start();
        
        JobDetail jobBDetail = JobBuilder.newJob(jobB).build();      
        Trigger triggerJobB = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0/6 * * * * ?")).forJob(jobBDetail).build();  
        scheduler.scheduleJob(jobBDetail, triggerJobB);  
        scheduler.start();
    }
}
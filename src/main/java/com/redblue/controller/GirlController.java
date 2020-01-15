package com.redblue.controller;

import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redblue.service.GirlService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GirlController {

	@Autowired
	GirlService girlService;

	@GetMapping("/getGirl")
	public String getGirl() {
		int count = 0;

		for (int i = 0; i < 10000; i++) {
			String result = girlService.getGirlById(i);
			if (result.startsWith("defualt")) {
				count++;
			} else {
				count = 0;
			}

			if (count == 3) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		return "OK";
	}

	@GetMapping("/tryGirl")
	public String tryGirl() {
		girlService.tryGirlById(1);
		return "OK";
	}

	@GetMapping("/limitGirl")
	public String limitGirl() {

		ThreadPoolTaskExecutor executor = getThreadPoolTaskExecutor();
		for (int i = 0; i < 100; i++) {
			int id = i;
			executor.execute(() -> {
				girlService.limitGirlById(id);
			});
		}

		return "OK";
	}

	@GetMapping("/bulkheadGirl")
	public String bulkheadGirl() {

		ThreadPoolTaskExecutor executor = getThreadPoolTaskExecutor();
		for (int i = 0; i < 100; i++) {
			int id = i;
			executor.execute(() -> {
				girlService.bulkheadGirlById(id);
			});
		}

		return "OK";
	}

	@GetMapping("/hello")
	public String hell(String name) {
		String s = "hello " + name + " !";
		log.info("s " + s + " " + new Date());
		int i = 1 / 0;
		return s;
	}

	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 核心线程数
		executor.setCorePoolSize(3);

		// 最大线程数
		executor.setMaxPoolSize(100);

		// 队列最大长度
		executor.setQueueCapacity(100);

		// 线程池维护线程所允许的空闲时间
		executor.setKeepAliveSeconds(300);

		// 线程池对拒绝任务(无线程可用)的处理策略 ThreadPoolExecutor.CallerRunsPolicy策略
		// ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
		// AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
		// CallerRunsPolicy:若已达到待处理队列长度，将由主线程直接处理请求
		// DiscardOldestPolicy:抛弃旧的任务；会导致被丢弃的任务无法再次被执行
		// DiscardPolicy:抛弃当前任务；会导致被丢弃的任务无法再次被执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		// 初始化
		executor.initialize();

		return executor;
	}
}

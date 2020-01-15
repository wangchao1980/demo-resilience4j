package com.redblue.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.redblue.service.GirlService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Retry(name = "girl")
public class GirlServiceImpl implements GirlService {

	@Autowired
	RestTemplate restTemplate;

	// 测试断路器
	@CircuitBreaker(name = "girl", fallbackMethod = "getDefaultGirlById")
	@Override
	public String getGirlById(Integer id) {

		Random random = new java.util.Random();
		int n = random.nextInt(5);
		log.info("n: " + n);
		if (n == 4) {
			throw new RuntimeException();
		} else if (n == 3) {
			;
		}

		String result = "girl-" + id;
		log.info("getGirlById: " + result);

		return result;
	}

	// fallbackMethod需要增加异常参数
	public String getDefaultGirlById(Integer id, Exception e) {
		String result = "defualt-girl-" + id;
		log.info("getDefaultGirlById: " + result);
		// e.printStackTrace();

		return result;
	}

	// 测试retry，retry注解一定要放在类上
	@Override
	public String tryGirlById(Integer id) {
		log.info("tryGirlById: " + id);
		restTemplate.getForObject("http://127.0.0.1:8080/hello?name={1}", String.class, id);
		return String.valueOf(id);
	}

	// 限流
	@RateLimiter(name = "girl")
	@Override
	public String limitGirlById(Integer id) {
		log.info("limitGirlById: " + id);
		return String.valueOf(id);
	}

	// 隔离
	@Bulkhead(name = "girl")
	@Override
	public String bulkheadGirlById(Integer id) {
		log.info("bulkheadGirlById: " + id);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return String.valueOf(id);
	}

	@Override
	public List<String> getGirlsByIds(List<Integer> ids) {

		List<String> results = new ArrayList<String>();

		if (ids != null) {
			for (Integer id : ids) {
				results.add(String.valueOf(id));
			}
		}

		log.info("getGirlsByIds: " + results.toString());

		return results;
	}

}

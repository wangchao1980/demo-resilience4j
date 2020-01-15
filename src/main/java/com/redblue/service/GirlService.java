package com.redblue.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface GirlService {
	
	public String getGirlById(Integer id);
	
	public String tryGirlById(Integer id);
	
	public String limitGirlById(Integer id);
	
	public String bulkheadGirlById(Integer id);
	
	public List<String> getGirlsByIds(List<Integer> ids);

}

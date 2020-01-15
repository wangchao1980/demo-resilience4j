package com.redblue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
class DemoResilience4jApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	//@Test
	public void getGirl() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/getGirl")).andExpect(status().isOk()).andReturn();
		log.info(mvcResult.getResponse().getContentAsString());
		String result = mvcResult.getResponse().getContentAsString();
		
	}
	
	//@Test
	public void tryGirl() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tryGirl")).andExpect(status().isOk()).andReturn();
		log.info(mvcResult.getResponse().getContentAsString());
		String result = mvcResult.getResponse().getContentAsString();
		
	}
	
	//@Test
	public void limitGirl() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/limitGirl")).andExpect(status().isOk()).andReturn();
		log.info(mvcResult.getResponse().getContentAsString());
		String result = mvcResult.getResponse().getContentAsString();
		
	}
	
	@Test
	public void bulkheadGirl() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/bulkheadGirl")).andExpect(status().isOk()).andReturn();
		log.info(mvcResult.getResponse().getContentAsString());
		String result = mvcResult.getResponse().getContentAsString();
		
	}

}
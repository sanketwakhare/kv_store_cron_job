package com.sanket.kv_store_cron_job;

import com.sanket.kv_store_cron_job.services.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KvStoreCronJobApplicationTests {

	@Autowired
	private StoreService storeService;

	@Test
	void contextLoads() {
		storeService.deleteExpiredEntries();
		System.out.println("entries deleted by CRON job");
	}

}

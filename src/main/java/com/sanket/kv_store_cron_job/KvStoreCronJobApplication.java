package com.sanket.kv_store_cron_job;

import com.sanket.kv_store_cron_job.services.StoreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KvStoreCronJobApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KvStoreCronJobApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}

package com.example.TroEasyAPI;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TroEasyApiApplication {

	// Tự động tìm nạp .env ở cả thư mục TroEasyAPI hoặc thư mục gốc TroEasy
	static {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		if (dotenv.get("DB_URL") == null) {
			dotenv = Dotenv.configure().directory("./TroEasyAPI").ignoreIfMissing().load();
		}
		if (dotenv.get("DB_URL") == null) {
			dotenv = Dotenv.configure().directory("../").ignoreIfMissing().load();
		}
		dotenv.entries().forEach(entry -> {
			if (System.getProperty(entry.getKey()) == null) {
				System.setProperty(entry.getKey(), entry.getValue());
			}
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(TroEasyApiApplication.class, args);
	}

}

package com.example.travelplan;

import com.example.travelplan.model.TouristAttraction;
import com.example.travelplan.repository.TouristAttractionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TravelplanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelplanApplication.class, args);

	}

	@Bean
	public CommandLineRunner dataLoader(TouristAttractionRepository repo) {
		return args -> {
			repo.save(new TouristAttraction("東京タワー", "東京のシンボルタワー", 35.6586, 139.7454));
			repo.save(new TouristAttraction("浅草寺", "浅草にある古刹", 35.7148, 139.7967));
			// 他のサンプルデータ…
		};

	}
}

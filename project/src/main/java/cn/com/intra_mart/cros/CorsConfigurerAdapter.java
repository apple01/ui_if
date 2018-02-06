package cn.com.intra_mart.cros;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		System.out.println("ndims:addCorsMappings");
		registry.addMapping("/ndims/ui_if/*").allowedOrigins("*");
	}
}

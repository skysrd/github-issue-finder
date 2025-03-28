package skysrd.githubissuefinder.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "skysrd.githubissuefinder.client")
public class FeignConfig {
}
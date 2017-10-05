package p.hh.fiboot2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import p.hh.fiboot2.dto.ModelMapperCreator;

@SpringBootApplication
@EntityScan(basePackages = {"p.hh.fiboot2.domain"})
@EnableJpaRepositories(basePackages = {"p.hh.fiboot2.dao"})
@ComponentScan(basePackages = {"p.hh.fiboot2.*"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapperCreator().create();
    }
}

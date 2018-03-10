package com.cira.ontology_creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.cira.ontology_creation.domains.Pipeline;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "classpath:application-${spring.profiles.active}.properties", encoding = "UTF-8"),
        @PropertySource(value = "file:${user.home}/.ontology_creation/application-${spring.profiles.active}.properties", encoding = "UTF-8")
})
public class OntologyCreationApplication implements CommandLineRunner {

    @Autowired
    private Pipeline pipeline;
    @Value("${pipeline.input.documents}")
    private String s;

    public static void main(String[] args) {
        SpringApplication.run(OntologyCreationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        pipeline.run();
    }
}

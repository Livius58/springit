package com.vega.springit;

import com.vega.springit.config.SpringitProperties;
import com.vega.springit.domain.Comment;
import com.vega.springit.domain.Link;
import com.vega.springit.repository.CommentRepository;
import com.vega.springit.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;


@SpringBootApplication
@EnableConfigurationProperties(SpringitProperties.class)
@EnableJpaAuditing
public class SpringitApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringitApplication.class);

    @Autowired
    private ApplicationContext applicationContext;
    public static void main(String[] args) {

        SpringApplication.run(SpringitApplication.class, args);

    }

    @Bean
    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository){
        return args ->{

            Link link=new Link("Getting started with Spring Boot 2","https://therealdanvega.com");
            linkRepository.save(link);

            Comment comment= new Comment("This Spring Boot 2 link is awesome",link);
            commentRepository.save(comment);

            //we need to tell link that it is a comment for you
            link.addComment(comment);

            Link firstLink=linkRepository.findByTitle("Getting started with Spring Boot 2");
            System.out.println(firstLink.getTitle());
        };
    }

/*
    @Bean
    CommandLineRunner runner(){
        return args-> {
            System.out.println("Printing out all the bean names in the application context.");
            System.out.println("-----------------------------------------------------------");
            String[] beans=applicationContext.getBeanDefinitionNames();
            Arrays.sort(beans);
            for (String bean : beans){
                System.out.println(bean);
            }
        };
    }
*/

}

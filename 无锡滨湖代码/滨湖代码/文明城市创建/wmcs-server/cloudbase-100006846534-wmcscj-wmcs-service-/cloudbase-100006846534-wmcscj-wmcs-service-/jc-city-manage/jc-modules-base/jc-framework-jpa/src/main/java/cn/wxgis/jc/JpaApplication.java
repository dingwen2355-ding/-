package cn.wxgis.jc;

import cn.wxgis.jc.jpa.service.JpacommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Autowired
    private JpacommentService jpacommentService;

    @Override
    public void run(String... args) throws Exception {
        jpacommentService.alterAllTableAndColumn();
    }
}

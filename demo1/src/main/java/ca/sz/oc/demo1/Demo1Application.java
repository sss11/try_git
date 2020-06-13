package ca.sz.oc.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {

    public static Boolean healthy=null;
    public static int count=0;
    public static void main(String[] args) {
        
        double random = Math.random() * 100;

        Demo1Application.healthy = random > 50 ? true : false;
        System.out.println("Demo1Application healthy=" + Demo1Application.healthy + " ramdom=" + random);

        SpringApplication.run(Demo1Application.class, args);
        
    }

}

package si.opkp;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource({"beans.xml"})
public class Application {

   private static ApplicationContext context;

   public static ApplicationContext getContext() {
      return context;
   }

   public static void main(String[] args) {
      context = new SpringApplicationBuilder()
            .bannerMode(Banner.Mode.OFF)
            .sources(Application.class)
            .run(args);
   }

}

package th.co.ttb.exam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;

@SpringBootApplication
@EnableJpaAuditing
public class ExamApplication {
    private static final Logger LOG = LogManager.getLogger(ExamApplication.class);

    public static String OS = System.getProperty("os.name").toLowerCase();
    public static String sp = "\\";
    public static HttpHeaders headersJSON;
    public static HttpHeaders headersTEXT;
    public static String env;
    @Value("${env}")
    public void setEnv(String setEnv) {
        ExamApplication.env = setEnv;
    }

    public static String site;
    @Value("${site}")
    public void setsite(String site) {
        ExamApplication.site = site;
    }

    public static String proj;
    @Value("${project}")
    public void setproj(String proj) {
        ExamApplication.proj = proj;
    }
    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return args -> {

            try {
                // Logging at different levels
                LOG.trace("This is a TRACE log.");
                LOG.debug("This is a DEBUG log.");
                LOG.info("This is an INFO log.");
                LOG.warn("This is a WARN log.");
                LOG.error("This is an ERROR log.");
                LOG.fatal("This is a FATAL log.");
                System.out.println("------------------------------- " + site + " - " + proj + " -------------------------------");
                System.out.printf("%-25s : %s\n", "env", env);
                System.out.printf("%-25s : %s\n", "site", site);

                headersJSON = new HttpHeaders();
                headersJSON.add("Content-Type", "application/json ; charset=UTF-8");

                headersTEXT = new HttpHeaders();
                headersTEXT.add("Content-Type", "text/html ; charset=UTF-8");

            } catch (Exception ex) {
                LOG.error(ex);
            } finally {
                System.out.println("------------------------------------------------------------------------------------");
            }
        };
    }
}

//package th.co.ttb.exam.configs;
//
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.OpenAPI;
//import org.springdoc.core.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("public")
//                .pathsToMatch("/apis/**")
//                .build();
//    }
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("API Documentation")
//                        .version("1.0.0")
//                        .description("API Documentation for TTB Exam Project"));
//    }
//}

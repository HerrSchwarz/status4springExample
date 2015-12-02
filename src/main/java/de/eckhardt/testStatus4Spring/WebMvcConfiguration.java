package de.eckhardt.testStatus4Spring;

import com.github.herrschwarz.status4spring.StatusController;
import com.github.herrschwarz.status4spring.inspectors.HostInspector;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration {

  @Bean
  IResourceResolver resourceResolver() {
    return new SpringResourceResourceResolver();
  }

  @Bean
  public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  ThymeleafViewResolver viewResolver() {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine());
    resolver.setViewNames(new String[] { "*" });
    resolver.setCharacterEncoding(UTF_8.name());
    return resolver;
  }

  @Bean
  TemplateResolver templateResolver() {
    TemplateResolver resolver = new TemplateResolver();
    resolver.setPrefix("classpath:/templates/");
    resolver.setSuffix(".html");
    resolver.setResourceResolver(resourceResolver());
    resolver.setCharacterEncoding(UTF_8.name());
    resolver.setCacheable(false);
    return resolver;
  }

  @Bean
  SpringTemplateEngine templateEngine() {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setTemplateResolver(templateResolver());
    return engine;
  }

  @Bean
  MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:messages/status_messages");
    return messageSource;
  }

  @Bean
  public StatusController statusController() {
    StatusController statusController = new StatusController();
    statusController.addHealthInspector(new HostInspector("Mail Server", "zimbra.silpion.de", 25));
    statusController.addHealthInspector(new HostInspector("Mail Server", "zmbra.silpion.de", 25));
    statusController.setVersion("0.0.1");
    // statusController.setHeader("header :: header");
    return statusController;
  }
}

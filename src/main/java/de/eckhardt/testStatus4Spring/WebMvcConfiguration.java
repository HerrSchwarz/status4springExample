package de.eckhardt.testStatus4Spring;

import static java.nio.charset.StandardCharsets.UTF_8;

import de.herrschwarz.status4spring.StatusController;
import de.herrschwarz.status4spring.inspectors.HostInspector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.yml")
public class WebMvcConfiguration {

  @Value("${internal.urls.status}")
  private String statusUrl;

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
    ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
    resolver.setPrefix("/WEB-INF/views");
    resolver.setSuffix(".html");
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
    statusController.setVersion("0.0.1 - " + statusUrl);
    // statusController.setHeader("header :: header");
    return statusController;
  }
}

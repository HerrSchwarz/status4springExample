package de.eckhardt.testStatus4Spring;

import static java.lang.Boolean.TRUE;
import static org.springframework.web.util.WebUtils.HTML_ESCAPE_CONTEXT_PARAM;

import ch.qos.logback.classic.selector.servlet.LoggerContextFilter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public final class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    super.onStartup(servletContext);
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] { RootConfiguration.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] { WebMvcConfiguration.class };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/test/*" };
  }

  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] { new LoggerContextFilter() };
  }

  @Override
  protected void customizeRegistration(ServletRegistration.Dynamic registration) {
    registration.setInitParameter(HTML_ESCAPE_CONTEXT_PARAM, TRUE.toString());
  }
}

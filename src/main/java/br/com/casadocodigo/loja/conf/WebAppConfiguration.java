package br.com.casadocodigo.loja.conf;

import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebAppConfiguration extends WebMvcConfigurerAdapter{
    
	// para resolver o problema de nao encontrar CSS	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}

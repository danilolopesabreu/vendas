package br.com.hostelpro.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Versao {

	@Value("${info.app.version}")
    private String version;
	
	private Logger logger = LoggerFactory.getLogger(Versao.class);
	
	@EventListener(ApplicationReadyEvent.class)
	public void versao() {
		this.logger.info("[Versao] "+version);
	}
	
}

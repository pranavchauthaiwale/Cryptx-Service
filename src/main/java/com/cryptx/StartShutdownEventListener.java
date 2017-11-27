package com.cryptx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartShutdownEventListener {
	
	private static final Logger logger = LoggerFactory.getLogger(StartShutdownEventListener.class);

	@EventListener
	public void onStartup(ApplicationReadyEvent event) {
		logger.info("Application Start Requested");
	}
	
	@EventListener
	public void onShutdown(ContextStoppedEvent event) {
		logger.info("Application Stop Requested");
	}
}

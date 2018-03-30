package service.impl;

import org.springframework.kafka.support.SendResult;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;

import service.KafkaProviderService;

public class KafkaProviderServiceImpl implements KafkaProviderService{
    private KafkaTemplate<String, String> kafkaTemplate;
    
    
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}


	public void sendMessage(String msg) {

    	ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send("new_order", msg);
	}
}

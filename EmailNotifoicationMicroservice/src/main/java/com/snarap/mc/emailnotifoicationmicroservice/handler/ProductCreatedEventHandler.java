package com.snarap.mc.emailnotifoicationmicroservice.handler;

import com.snarap.mc.core.ProductCreatedEvent;
import com.snarap.mc.emailnotifoicationmicroservice.exception.NonRetryableException;
import com.snarap.mc.emailnotifoicationmicroservice.exception.RetryableException;
import org.apache.tomcat.util.security.Escape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@KafkaListener(topics = "product-created-events-topic")
public class ProductCreatedEventHandler {
    private RestTemplate restTemplate;
    private  final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductCreatedEventHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @KafkaHandler
    public  void  handle(ProductCreatedEvent productCreatedEvent){
        LOGGER.info("Received event: {}", productCreatedEvent.getTitle());

        String url = "http://localhost:8090/response/200";
        try {
            ResponseEntity<String> response =  restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if(response.getStatusCode().value() == HttpStatus.OK.value()){
                LOGGER.info("Received response: {}", response.getBody());
            }
        }catch (ResourceAccessException e){
            LOGGER.error(e.getMessage());
            throw new RetryableException(e);
        }catch (HttpServerErrorException e){
            LOGGER.error(e.getMessage());
            throw  new NonRetryableException(e);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new  NonRetryableException(e);
        }


    }

}

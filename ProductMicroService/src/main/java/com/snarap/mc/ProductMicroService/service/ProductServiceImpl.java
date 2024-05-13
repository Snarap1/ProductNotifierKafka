package com.snarap.mc.ProductMicroService.service;

import com.snarap.mc.ProductMicroService.service.dto.CreateProductDto;

import com.snarap.mc.core.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService{
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(
            CreateProductDto createProductDto) throws ExecutionException, InterruptedException {
        //todo save to db
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent productCreatedEvent
                = new ProductCreatedEvent(productId, createProductDto.getTitle(),createProductDto.getPrice(), createProductDto.getQuantity());

       SendResult<String,ProductCreatedEvent> result =
               kafkaTemplate.send("product-created-events-topic",productId,productCreatedEvent).get();

        LOGGER.info("Topic: {}",result.getRecordMetadata().topic());
        LOGGER.info("Partition: {}",result.getRecordMetadata().partition());
        LOGGER.info("Offset: {}",result.getRecordMetadata().offset());


        LOGGER.info("Return: {}",productId);
        return productId;
    }
}

package de.collectioncompanion.TasksMS;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingQueueConfig {

    @Bean
    public Queue queue() {
        return new Queue("nameOfMyTasksQueue");
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange("nameOfMyTasksExchange");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(""); // "" = key
    }

}

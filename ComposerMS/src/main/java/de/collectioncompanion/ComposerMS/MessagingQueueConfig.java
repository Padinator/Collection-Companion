package de.collectioncompanion.ComposerMS;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingQueueConfig {

    /*
     * Methods for creating a rabbitmq
     */
    @Bean
    public Queue queueIn() {
        return new Queue("nameOfMyTasksQueue"); // The name is irrelevant
    }

    @Bean
    public Queue queueOut() {
        return new Queue("nameOfMyResultsQueue"); // The name is irrelevant
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange("nameOfMyTasksExchange");
    }

    /*
    // First variant
    @Bean
    public Binding bindingOut(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(""); // "" = key
    }
     */

    // Second variant
    @Bean
    public Binding bindingOut(DirectExchange exchange) {
        return BindingBuilder.bind(queueOut()).to(exchange).with(""); // "" = key
    }
}

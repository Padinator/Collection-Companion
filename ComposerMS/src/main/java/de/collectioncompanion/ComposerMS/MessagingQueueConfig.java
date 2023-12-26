package de.collectioncompanion.ComposerMS;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MessagingQueueConfig {

    /*
     * Define connection factories for differing rabbitmqs
     */
    @Bean
    @Primary
    public ConnectionFactory amqpConnectionFactoryIn() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        //connectionFactory.setHost("amqp://guest:guest@rabbitmq-broker-tasks"); // Device host -> localhost or WSL hostname (DESKTOP-M06B2DG) for Docker
        //connectionFactory.setVirtualHost("rabbitmq-broker-tasks"); // Virtual host of rabbitmq -> "/"
        System.out.println(connectionFactory.getHost());
        connectionFactory.setPort(5672);
        //connectionFactory.setUsername("admin123"); // Username for login to rabbitmq
        //connectionFactory.setPassword("admin123"); // Pwd for login to rabbitmq
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory amqpConnectionFactoryOut() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        //connectionFactory.setHost("amqp://guest:guest@rabbitmq-broker-results"); // Device host -> localhost or WSL hostname (DESKTOP-M06B2DG) for Docker
        System.out.println(connectionFactory.getHost());
        //connectionFactory.setVirtualHost("rabbitmq-broker-tasks"); // Virtual host of rabbitmq -> "/"
        connectionFactory.setPort(5672);
        //connectionFactory.setUsername("admin123"); // Username for login to rabbitmq
        //connectionFactory.setPassword("admin123"); // Pwd for login to rabbitmq
        return connectionFactory;
    }

    /*
     * Define RabbitAdmin
     */
    @Bean
    @Primary
    public RabbitAdmin rabbitAdminIn() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(amqpConnectionFactoryIn());
        //RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplateIn());
        System.out.println("QueueIn: " + rabbitAdmin.declareQueue(queueIn()));
        return rabbitAdmin;
    }

    @Bean
    public RabbitAdmin rabbitAdminOut() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(amqpConnectionFactoryOut());
        //RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplateOut());
        System.out.println("QueueOut: " + rabbitAdmin.declareQueue(queueOut()));
        return rabbitAdmin;
    }

    /*
     * Define templates for communicating with rabbitmqs
     */
    @Bean
    @Primary
    public RabbitTemplate rabbitTemplateIn() {
        return new RabbitTemplate(amqpConnectionFactoryIn());
    }

    @Bean
    public RabbitTemplate rabbitTemplateOut() {
        return new RabbitTemplate(amqpConnectionFactoryOut());
    }

    /*
     * Methods for creating a rabbitmq
     */
    @Bean
    public Queue queueIn() {
        return new Queue("nameOfMyTasksQueue");
    }

    @Bean
    public Queue queueOut() {
        return new Queue("nameOfMyResultsQueue");
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange("nameOfMyTasksExchange");
    }

    /*
    // First variant
    @Bean
    public Binding bindingIn(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(""); // "" = key
    }

    @Bean
    public Binding bindingOut(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(""); // "" = key
    }
    */

    // Second variant
    /*@Bean
    public Binding bindingIn(DirectExchange exchange) {
        return BindingBuilder.bind(queueIn()).to(exchange).with(""); // "" = key
    }*/


    @Bean
    public Binding bindingOut(DirectExchange exchange) {
        return BindingBuilder.bind(queueOut()).to(exchange).with(""); // "" = key
    }
}

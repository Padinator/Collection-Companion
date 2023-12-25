package de.collectioncompanion.ComposerMS;

import de.collectioncompanion.ComposerMS.adapter.inbound.MessagingAdapterIn;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class MessagingQueueConfig {

    /*
     * Define connection factories for differing rabbitmqs
     */
    @Bean
    @Primary
    public ConnectionFactory amqpConnectionFactoryIn() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost"); // Manual execution
        //connectionFactory.setHost("rabbit_mq_broker_in"); // Execution with Docker
        connectionFactory.setPort(5672);
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory amqpConnectionFactoryOut() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost"); // Manual execution
        //connectionFactory.setHost("rabbit_mq_broker_out"); // Execution with Docker
        connectionFactory.setPort(5673);
        return connectionFactory;
    }

    /*
     * Define RabbitAdmin
     */
    @Bean
    @Primary
    public RabbitAdmin rabbitAdminIn() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(amqpConnectionFactoryIn());
        rabbitAdmin.declareQueue(queueIn());
        return rabbitAdmin;
    }

    @Bean
    public RabbitAdmin rabbitAdminOut() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(amqpConnectionFactoryOut());
        rabbitAdmin.declareQueue(queueOut());
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

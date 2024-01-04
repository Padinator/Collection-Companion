package de.collectioncompanion.ResultsMS;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingQueueConfig {

    @Bean
    public Queue queue() {
        return new Queue("nameOfMyResultsQueue");
    }

    /*
    @Bean
    public GsonBuilderCustomizer typeAdapterRegistration() {
        return builder -> {
            builder.registerTypeAdapter(Collection.class, new CollectionImpl());
        };
    }
    */

    /*
    @Bean
    public SimpleModule customSerializer() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Collection.class, new NullSerializer<>());
        return module;
    }
    */

}

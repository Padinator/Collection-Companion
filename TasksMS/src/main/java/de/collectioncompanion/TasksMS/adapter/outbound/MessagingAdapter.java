package de.collectioncompanion.TasksMS.adapter.outbound;

import de.collectioncompanion.TasksMS.data_files.CollectionRequest;
import de.collectioncompanion.TasksMS.data_files.CollectionRequestDTO;
import de.collectioncompanion.TasksMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingAdapter implements UpdatesNotificationPort {

    @Autowired
    private Exchange exchange;

    @Autowired
    private AmqpTemplate template;

    @Override
    public void notifyUpdate(CollectionRequest collectionRequest) {
        CollectionRequestDTO collectionRequestDTO = new CollectionRequestDTO(collectionRequest);
        System.out.println("Push following collection request to rabbitmq: " + collectionRequest);
        template.convertAndSend(exchange.getName(), "", // "/collection" = route
                de.collectioncompanion.TasksMS.data_files.CollectionRequestDTO.toJson(collectionRequestDTO));
    }

}

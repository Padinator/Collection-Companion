package de.collectioncompanion.ComposerMS.adapter.outbound;

import data_files.CollectionImpl;
import data_files.CollectionListDTO;
import de.collectioncompanion.ComposerMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class MessagingAdapterOut implements UpdatesNotificationPort {

    @Autowired
    private Exchange exchange;

    @Autowired
    private AmqpTemplate template;

    @Override
    public void notifyUpdate(long id, ArrayList<CollectionImpl> collections) {
        CollectionListDTO collectionListDTO = new CollectionListDTO(id, collections);
        System.out.println("Push to out queue: " + collections);
        template.convertAndSend(exchange.getName(), "", // "" = route
                CollectionListDTO.toJson(collectionListDTO));
    }

}

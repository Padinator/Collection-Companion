package de.collectioncompanion.ComposerMS.adapter.outbound;

import data_files.CollectionDTO;
import data_files.CollectionImpl;
import de.collectioncompanion.ComposerMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ports.Collection;

@Controller
public class MessagingAdapterOut implements UpdatesNotificationPort {

    @Autowired
    private Exchange exchange;

    @Autowired
    private AmqpTemplate template;

    @Override
    public void notifyUpdate(long id, Collection collection) {
        CollectionDTO collectionDTO = new CollectionDTO(id, new CollectionImpl(collection));
        System.out.println("Push to out queue: " + collection);
        template.convertAndSend(exchange.getName(), "", // "" = route
                CollectionDTO.toJson(collectionDTO));
    }

}

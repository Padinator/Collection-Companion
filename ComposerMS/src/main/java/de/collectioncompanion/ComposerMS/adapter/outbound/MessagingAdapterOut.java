package de.collectioncompanion.ComposerMS.adapter.outbound;

import de.collectioncompanion.ComposerMS.data_files.CollectionDTO;
import de.collectioncompanion.ComposerMS.ports.data_files.Collection;
import de.collectioncompanion.ComposerMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class MessagingAdapterOut implements UpdatesNotificationPort {

    @Autowired
    private Exchange exchange;

    @Autowired
    private AmqpTemplate template;

    @Override
    public void notifyUpdate(long id, Collection collection) {
        CollectionDTO collectionDTO = new CollectionDTO(id, collection);
        template.convertAndSend(exchange.getName(), "/collection", // "/collection" = route
                de.collectioncompanion.ComposerMS.data_files.CollectionDTO.toJson(collectionDTO));
    }

}

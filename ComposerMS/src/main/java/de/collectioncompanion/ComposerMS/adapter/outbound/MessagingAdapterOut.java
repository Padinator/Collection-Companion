package de.collectioncompanion.ComposerMS.adapter.outbound;

import de.collectioncompanion.ComposerMS.MessagingQueueConfig;
import de.collectioncompanion.ComposerMS.data_files.CollectionDTO;
import de.collectioncompanion.ComposerMS.ports.data_files.Collection;
import de.collectioncompanion.ComposerMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingAdapterOut implements UpdatesNotificationPort {

    @Autowired
    private Exchange exchange;

    /*@Autowired // Does not work
    @Qualifier("rabbitTemplateOut")*/
    private AmqpTemplate template = new MessagingQueueConfig().rabbitTemplateOut();

    @Override
    public void notifyUpdate(long id, Collection collection) {
        CollectionDTO collectionDTO = new CollectionDTO(id, collection);
        System.out.println("Push to out queue: " + collection);
        template.convertAndSend(exchange.getName(), "", // "" = route
                de.collectioncompanion.ComposerMS.data_files.CollectionDTO.toJson(collectionDTO));
    }

}

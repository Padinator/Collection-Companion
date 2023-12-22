package de.collectioncompanion.ResultsMS.adapter.inbound;

import de.collectioncompanion.ResultsMS.adapter.outbound.RestServerOut;
import de.collectioncompanion.ResultsMS.data_files.CollectionDTO;
import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import de.collectioncompanion.ResultsMS.ports.data_files.CollectionQueue;
import de.collectioncompanion.ResultsMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingAdapter {

    @Autowired
    private UpdatesNotificationPort updatesNotificationPort;

    @Autowired
    private RestServerOut restServerOut;

    @RabbitListener(queues = "#{nameOfMyResultsQueue.name}")
    public void receive(String receiveJson) {
        CollectionDTO collectionDTO = CollectionDTO.fromJson(receiveJson);
        Collection collection = collectionDTO.getCollection();
        long id = collectionDTO.getId();

        updatesNotificationPort.notifyUpdate(collection); // Dequeue resulting collection from queue
        CollectionQueue.enqueueCollection(id, collection); // Push result into an own java class queue
        restServerOut.addResultingCollectionToDB(collection); // Add request into DB
    }

}

package de.collectioncompanion.ResultsMS.adapter.inbound;

import de.collectioncompanion.ResultsMS.adapter.outbound.RestServerOut;
import de.collectioncompanion.ResultsMS.data_files.CollectionDTO;
import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import de.collectioncompanion.ResultsMS.ports.data_files.CollectionList;
import de.collectioncompanion.ResultsMS.ports.inbound.UpdatesNotificationPort;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingAdapter {

    /* @Autowired */
    private final UpdatesNotificationPort updatesNotificationPort = new UpdatesNotificationPort() {
        @Override
        public void notifyUpdate(long id, Collection collection) {
            System.out.println("Dequeued collection with id " + id + " from rabbitmq:\n" + collection);
        }
    };

    @Autowired
    private RestServerOut restServerOut;

    /**
     * Receive messages from rabbitmq and send it back to the
     *
     * @param receiveJson Takes elements as json
     */
    @RabbitListener(queues = "#{nameOfMyResultsQueue.name}")
    public void receive(String receiveJson) {
        CollectionDTO collectionDTO = CollectionDTO.fromJson(receiveJson);
        Collection collection = collectionDTO.collection();
        long id = collectionDTO.id();

        updatesNotificationPort.notifyUpdate(id, collection); // Dequeue resulting collection from queue
        CollectionList.pushCollection(id, collection); // Push result into an own java class queue
        notifyAll(); // Notify all threads that in own queue is a new entry to get
        restServerOut.addResultingCollectionToDB(collection); // Add request into DB
    }

}

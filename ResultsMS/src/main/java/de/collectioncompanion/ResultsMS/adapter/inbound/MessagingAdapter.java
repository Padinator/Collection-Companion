package de.collectioncompanion.ResultsMS.adapter.inbound;

import data_files.CollectionDTO;
import de.collectioncompanion.ResultsMS.adapter.outbound.DatabaseServerOut;
import de.collectioncompanion.ResultsMS.data_files.CollectionList;
import de.collectioncompanion.ResultsMS.ports.inbound.UpdatesNotificationPort;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ports.Collection;


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
    private DatabaseServerOut databaseServerOut;

    /**
     * Receive messages from rabbitmq and send it back to the
     *
     * @param receiveJson Takes elements as json
     */
    @RabbitListener(queues = "#{queue.name}")
    public void receive(String receiveJson) {
        if (!receiveJson.contains("searchTerm")) {
            System.out.println(receiveJson);
            CollectionDTO collectionDTO = CollectionDTO.fromJson(receiveJson);
            Collection collection = collectionDTO.collection();
            long id = collectionDTO.id();

            updatesNotificationPort.notifyUpdate(id, collection); // Notify dequeuing a collection from rabbitmq
            String dbCollectionId = databaseServerOut.addResultingCollectionToDB(collection).getBody(); // Add collection into DB and get ID of inserted collection
            collection.setId(dbCollectionId);
            CollectionList.pushCollection(id, collection); // Push result into an own java class queue
        }
    }

}

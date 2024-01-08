package de.collectioncompanion.ResultsMS.adapter.inbound;

import data_files.CollectionListDTO;
import de.collectioncompanion.ResultsMS.adapter.outbound.DatabaseServerOut;
import de.collectioncompanion.ResultsMS.data_files.CollectionList;
import de.collectioncompanion.ResultsMS.ports.inbound.UpdatesNotificationPort;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ports.Collection;

import java.util.List;


@Controller
public class MessagingAdapter {

    /* @Autowired */
    private final UpdatesNotificationPort updatesNotificationPort = new UpdatesNotificationPort() {
        @Override
        public void notifyUpdate(long id, List<Collection> collections) {
            System.out.println("Dequeued collection with id " + id + " from rabbitmq:\n" + collections);
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
            CollectionListDTO collectionListDTO = CollectionListDTO.fromJson(receiveJson);
            List<Collection> collections = (List) collectionListDTO.collection();
            long id = collectionListDTO.id();

            updatesNotificationPort.notifyUpdate(id, collections); // Notify dequeuing a collection from rabbitmq
            databaseServerOut.addResultingCollectionsToDB(collections); // Add request into DB
            CollectionList.pushCollection(id, collections); // Push result into an own java class queue
        }
    }

}

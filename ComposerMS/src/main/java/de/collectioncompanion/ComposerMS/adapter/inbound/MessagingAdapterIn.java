package de.collectioncompanion.ComposerMS.adapter.inbound;

import data_files.CollectionRequest;
import data_files.CollectionRequestDTO;
import de.collectioncompanion.ComposerMS.adapter.outbound.RestServerOut;
import de.collectioncompanion.ComposerMS.ports.inbound.UpdatesNotificationPort;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingAdapterIn {

    /* @Autowired */
    private final UpdatesNotificationPort updatesNotificationPort = new UpdatesNotificationPort() {
        @Override
        public void notifyUpdate(CollectionRequest collectionRequest) {
            System.out.println("Updates notification port, MessagingAdapterIn: " + collectionRequest);
        }
    };

    @Autowired
    private RestServerOut restServerOut;

    @RabbitListener(queues = "#{queueIn.name}")
    public void receive(String receiveJson) {
        if (receiveJson.contains("searchTerm")) {
            CollectionRequestDTO collectionRequestDTO = CollectionRequestDTO.fromJson(receiveJson);
            CollectionRequest collectionRequest = collectionRequestDTO.collectionRequest();

            System.out.println("Received JSON: " + receiveJson);
            System.out.println("Received collection request DTO: " + collectionRequestDTO);
            System.out.println("Received CollectionRequest: " + collectionRequest);

            // Forward request to web crawler (takes maybe a little time -> fix ???)
            updatesNotificationPort.notifyUpdate(collectionRequest); // Output dequeue message
            restServerOut.requestWebCrawler(collectionRequest);
        }
    }

}

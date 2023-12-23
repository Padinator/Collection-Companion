package de.collectioncompanion.ComposerMS.adapter.inbound;

import de.collectioncompanion.ComposerMS.adapter.outbound.RestServerOut;
import de.collectioncompanion.ComposerMS.data_files.CollectionRequest;
import de.collectioncompanion.ComposerMS.data_files.CollectionRequestDTO;
import de.collectioncompanion.ComposerMS.ports.inbound.UpdatesNotificationPort;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class MessagingAdapterIn {

    @Autowired
    private UpdatesNotificationPort updatesNotificationPort;

    @Autowired
    private RestServerOut restServerOut;

    @RabbitListener(queues = "#{nameOfMyTasksQueue.name}")
    public void receive(String receiveJson) {
        CollectionRequestDTO collectionRequestDTO = CollectionRequestDTO.fromJson(receiveJson);
        CollectionRequest collectionRequest = collectionRequestDTO.collectionRequest();
        long id = collectionRequest.id();

        // Dequeue resulting collection from queue
        updatesNotificationPort.notifyUpdate(collectionRequest);

        // Forward request to web crawler
        ResponseEntity<String> requestedCollection = restServerOut.requestWebCrawler(collectionRequest);
    }

}

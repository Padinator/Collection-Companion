package de.collectioncompanion.TasksMS.adapter.inbound;

import de.collectioncompanion.TasksMS.data_files.CollectionRequest;
import de.collectioncompanion.TasksMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @Autowired
    private UpdatesNotificationPort updatesNotificationPort;

    /**
     * Receives arguments for creating a CollectionRequest (passed category and search term) and pushes it into the
     * rabbitmq.
     *
     * @param category The category of the requested collection
     * @param searchTerm The search term to search for the requested collection in the passed category
     * @param id The ID of the request
     * @return Return the response as String
     */
    @PostMapping
    public ResponseEntity<String> doRequest(@RequestParam String category, @RequestParam String searchTerm, @RequestParam long id) {
        updatesNotificationPort.notifyUpdate(new CollectionRequest(id, category, searchTerm));
        return ResponseEntity.status(200).body("Request was pushed successfully into rabbitmq!");
    }

}

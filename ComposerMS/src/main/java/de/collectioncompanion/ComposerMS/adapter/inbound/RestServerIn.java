package de.collectioncompanion.ComposerMS.adapter.inbound;

import data_files.CollectionImpl;
import de.collectioncompanion.ComposerMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @Autowired
    private UpdatesNotificationPort updatesNotificationPort;

    @PostMapping
    public ResponseEntity<String> pushCollectionIntoQueue(@RequestParam long id, @RequestBody CollectionImpl collection) {
        updatesNotificationPort.notifyUpdate(id, collection);
        return ResponseEntity.status(200).body("Collection was pushed successfully into queue!");
    }

}

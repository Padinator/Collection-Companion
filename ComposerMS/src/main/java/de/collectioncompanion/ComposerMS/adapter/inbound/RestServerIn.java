package de.collectioncompanion.ComposerMS.adapter.inbound;

import de.collectioncompanion.ComposerMS.ports.data_files.Collection;
import de.collectioncompanion.ComposerMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @Autowired
    private UpdatesNotificationPort updatesNotificationPort;

    /**
     * Das macht noch nicht so viel Sinn mit dem übergaben der Collection!!!!!!!
     *
     * @param id
     * @param collection
     * @return
     */
    @GetMapping
    public ResponseEntity<String> receiveCollection(@RequestParam long id, @RequestParam Collection collection) {
        updatesNotificationPort.notifyUpdate(id, collection);
        return ResponseEntity.status(200).body("Collection was received successfully and enqueued successfully to "
                + "rabbitmq!");
    }

}

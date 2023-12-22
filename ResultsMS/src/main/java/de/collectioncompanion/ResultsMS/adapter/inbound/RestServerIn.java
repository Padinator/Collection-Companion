package de.collectioncompanion.ResultsMS.adapter.inbound;

import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import de.collectioncompanion.ResultsMS.ports.data_files.CollectionQueue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @GetMapping
    public ResponseEntity<String> getCollection(@RequestParam long id) {
        Collection collection;

        while ((collection = CollectionQueue.dequeueCollection(id)) == null) // Geht das?????
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        return ResponseEntity.status(200).body(collection.toString());
    }

}

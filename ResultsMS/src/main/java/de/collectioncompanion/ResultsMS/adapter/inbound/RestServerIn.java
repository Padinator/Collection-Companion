package de.collectioncompanion.ResultsMS.adapter.inbound;

import de.collectioncompanion.ResultsMS.data_files.ResultWaiterThread;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ports.Collection;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    /**
     * Anfragen können immer noch nicht gleichzeitig beantwortet werden!!!!!!
     *
     * @param id
     * @return
     */
    @GetMapping
    public ResponseEntity<String> getCollection(@RequestParam long id) {
        ResultWaiterThread resultWaiterThread = new ResultWaiterThread(id);

        try {
            resultWaiterThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Requested collection is: " + resultWaiterThread.getCollections().toString());

        return ResponseEntity.status(200).body(resultWaiterThread.getCollections()
                .stream().map(Collection::toJSON).toList().toString());
    }

}

package de.collectioncompanion.ResultsMS.adapter.inbound;

import de.collectioncompanion.ResultsMS.data_files.ResultWaiterThread;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        System.out.println("Requested collection is: " + resultWaiterThread.getCollection().toString());
        System.out.println("Response is: " + ResponseEntity.status(200).body(resultWaiterThread.getCollection().toString()));

        return ResponseEntity.status(200).body(resultWaiterThread.getCollection().toString());
    }

}

package de.collectioncompanion.ResultsMS.adapter.inbound;

import de.collectioncompanion.ResultsMS.data_files.ResultWaiterThread;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(200).body(resultWaiterThread.getCollection().toString());
    }

}

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
        String result;
        int returnCode = 200;

        try {
            resultWaiterThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        result = resultWaiterThread.getCollections().stream().map(Collection::toJSON).toList().toString();
        System.out.println("Requested collections as JSON are: " + result);

        if (resultWaiterThread.getCollections().isEmpty())
            returnCode = 404;

        return ResponseEntity.status(returnCode).body(result);
    }

}

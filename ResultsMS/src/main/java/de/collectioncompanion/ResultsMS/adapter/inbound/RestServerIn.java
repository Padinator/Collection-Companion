package de.collectioncompanion.ResultsMS.adapter.inbound;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.collectioncompanion.ResultsMS.data_files.ResultWaiterThread;

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
    @CrossOrigin(exposedHeaders = { "Access-Control-Allow-Origin",
            "Access-Control-Allow-Methods\", \"GET,POST,PATCH,OPTIONS" })
    public ResponseEntity<String> getCollection(@RequestParam long id) {
        ResultWaiterThread resultWaiterThread = new ResultWaiterThread(id);

        try {
            resultWaiterThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Requested collection is: " + resultWaiterThread.getCollection().toString());
        System.out.println(
                "Response is: " + ResponseEntity.status(200).body(resultWaiterThread.getCollection().toString()));

        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(resultWaiterThread.getCollection().toString());
    }

}

package de.collectioncompanion.StatisticsMS;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServerIn {

    @GetMapping
    @CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Methods\", \"GET,POST,PATCH,OPTIONS"})
    public ResponseEntity<String> foo() {
        String result = "{ \"test\": \"123\" }";
        System.out.println(ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(result));
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(result);

        // 2. variant
        /*
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "GET,POST,PATCH,OPTIONS");
        ResponseEntity<String> response = new ResponseEntity<String>(result, responseHeaders, HttpStatus.OK);
        System.out.println(response);
        return response;
         */
    }

}

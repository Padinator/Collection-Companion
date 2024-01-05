package de.collectioncompanion.StatisticsMS;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8090")
public class RestServerIn {

    @GetMapping
    public ResponseEntity<String> foo() {
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("{ \"test\": \"123\" }");
    }

}

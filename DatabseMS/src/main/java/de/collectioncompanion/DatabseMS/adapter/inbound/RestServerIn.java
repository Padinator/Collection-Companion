package de.collectioncompanion.DatabseMS.adapter.inbound;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.adapter.outbound.DatabaseOut;
import de.collectioncompanion.DatabseMS.data_files.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ports.Collection;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @Autowired
    private DatabaseOut databaseOut;

    /**
     * Accepts get requests with exactly two parameters named like parameters below
     *
     * @param category   The category of the collection
     * @param searchTerm The search term of the request
     * @return Return a response as response-object
     */
    @GetMapping
    public ResponseEntity<String> getCollection(@RequestParam String category, @RequestParam String searchTerm) {
        Collection result = databaseOut.requestCollectionFromDB(category, searchTerm);
        System.out.println(result.toJSON());

        System.out.println("Result is empty: " + result.isEmpty());

        if (!result.isEmpty())
            System.out.println("Result is valid: " + result.isValid());

        if (!result.isEmpty() && result.isValid()) // Found a valid collection
            return ResponseEntity.status(200).body(result.toJSON());
        else // Found no collection or an outdated collection
            return ResponseEntity.status(204).body(result.toString());
    }

    /**
     * Inserts a passed collection into DB
     *
     * @param collection The collection to insert into DB
     * @return Returns the success/response as string
     */
    @PostMapping
    public ResponseEntity<String> addNewCollection(@RequestBody CollectionImpl collection) {
        System.out.println("Received collection to insert: " + collection);
        databaseOut.addCollection(collection);
        return ResponseEntity.status(200).body(collection.getId());
    }

    @GetMapping("/users")
    public ResponseEntity<String> getUser(@RequestParam String username) {
        User user = databaseOut.requestUserFromDB(username);

        if (user == null) // No user was found
            return ResponseEntity.status(404).body("No user was found!");

        user.setPassword("");
        return ResponseEntity.status(200).body(user.toString());
    }

    @PostMapping("/users")
    public ResponseEntity<String> addNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        User user = databaseOut.addUser(new User(username, password, email, new LinkedList<>(), new LinkedList<>()));
        if (user != null)
            return ResponseEntity.status(200).body("Inserted successfully user into DB!");
        else 
            return ResponseEntity.status(403).body("User already exists!");
    }

    @PostMapping("/users/sammlung")
    public ResponseEntity<String> addSammlungToUser(@RequestParam String username, @RequestParam String name, @RequestParam String visibility, @RequestParam String category) {
        databaseOut.addSammlungToUser(username, name, visibility, category);
        return ResponseEntity.status(200).body("Added successfully Sammlung to User!");
    }

    @PostMapping("/users/sammlung/collections")
    public ResponseEntity<String> addCollectionToUsersSammlung(@RequestParam String username, @RequestParam int sammlungNummer, @RequestBody CollectionImpl collection) {
        if (databaseOut.addCollectionToUsersSammlung(username, sammlungNummer, collection))
            return ResponseEntity.status(200).body("Added successfully collection to user in DB!");
        return ResponseEntity.status(403).body("Could not add Collection to Users Sammlung!");
    }

    @PostMapping("/users/friends")
    public ResponseEntity<String> addFriendToUser(@RequestParam String username, @RequestParam String usernameFriend) {
        if (databaseOut.addFriendToUser(username, usernameFriend))
            return ResponseEntity.status(200).body("Added successfully Friend to User in DB!");
        return ResponseEntity.status(403).body("Could not add Friend to User!");
    }
}

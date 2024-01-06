package de.collectioncompanion.DatabseMS.service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.data_files.User;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import de.collectioncompanion.DatabseMS.ports.service.CollectionRepo;
import de.collectioncompanion.DatabseMS.ports.service.UserRepo;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import static ports.CollectionFormatter.compareGameNames;

@Service
public class DatabaseImpl implements Database {

    public Collection selectCollection(String category, String searchTerm, CollectionRepo collectionRepo) {
        List<Collection> results = collectionRepo.findAll().stream() // Query DB
                .filter(collection -> collection.getValue("category").equals(category)
                        && compareGameNames(collection.getValue("title"), searchTerm))
                .toList();

        System.out.println("\n\nFound:");
        System.out.println(collectionRepo.findAll() + "\n");
        System.out.println(collectionRepo.findAll().stream() // Query DB
                .filter(collection -> collection.getValue("category").equals(category))
                .toList() + "\n");

        System.out.println(collectionRepo.findAll().stream() // Query DB
                .filter(collection -> compareGameNames(collection.getValue("title"), searchTerm))
                .toList() + "\n\n");

        if (results.size() == 0)
            return new CollectionImpl(new TreeMap<>());

        System.out.println("Return: " + results.get(0));
        return results.get(0);
    }

    @Override
    public void insertCollection(Collection collection, CollectionRepo collectionRepo) {
        // Insert all data into DB
        // collection.putEntry("title", "Passengers Of Execution");
        // collection.putEntry("category", "game");
        // collection.putEntry("time_stamp", "1734567890109");
        System.out.println("Collection to insert: " + collection);
        Collection c = collectionRepo.insert(collection);
        System.out.println("Inserted: " + c);
        System.out.println("Complete content:\n" + collectionRepo.findAll());
    }

    @Override
    public void insertUser(User user, UserRepo userRepo) {
        User u = userRepo.insert(user);
        System.out.println("Inserted new User into DB: " + u);
    }

    @Override
    public User selectUser(String username, UserRepo userRepo) {
        Optional<User> user = userRepo.findById(username);

        if (user.isEmpty()) // if no user was found
            return null;

        return user.get();
    }

    @Override
    public void insertCollectionToUser(String username, CollectionImpl collection, UserRepo userRepo,
            CollectionRepo collectionRepo) {
        Optional<User> optinalUser = userRepo.findById(username);

        if (optinalUser.isPresent()) {
            User user = optinalUser.get();
            List<String> collectionId = user.getCollectionId();

            if (!collectionId.contains(collection.getId())) {
                collectionId.add(collection.getId());
                userRepo.save(user);
            }
        }
    }

    @Override
    public void insertFriendToUser(String username, String usernameFriend, UserRepo userRepo) {
        Optional<User> user1 = userRepo.findById(username), user2 = userRepo.findById(usernameFriend);

        if (user1.isPresent() && user2.isPresent()) {
            User user = user1.get();
            List<String> friendsId = user.getUserFriendsId();

            if (!friendsId.contains(usernameFriend)) {
                user.getUserFriendsId().add(usernameFriend);
                userRepo.save(user);
            }
        }
    }

}

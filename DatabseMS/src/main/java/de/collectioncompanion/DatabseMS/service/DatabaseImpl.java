package de.collectioncompanion.DatabseMS.service;

import de.collectioncompanion.DatabseMS.data_files.Sammlung;
import de.collectioncompanion.DatabseMS.data_files.User;
import de.collectioncompanion.DatabseMS.ports.service.CollectionRepo;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import de.collectioncompanion.DatabseMS.ports.service.UserRepo;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static ports.CollectionFormatter.compareGameNames;

@Service
public class DatabaseImpl implements Database {

    /*
     * "Search result"-requests only for table "search results = collection"
     */
    @Override
    public List<Collection> selectCollections(String category, String searchTerm, CollectionRepo collectionRepo) {
        List<Collection> results = collectionRepo.findAll().stream() // Query DB
                .filter(collection -> collection.getValue("category").equals(category)
                        && compareGameNames(collection.getValue("title"), searchTerm))
                .toList();

        System.out.println("Return results:\n" + results);
        return results;
    }

    @Override
    public Collection selectCollection(String id, CollectionRepo collectionRepo) {
        Optional<Collection> result = collectionRepo.findById(id);
        return result.orElse(null);

    }

    @Override
    public boolean insertCollection(Collection collection, CollectionRepo collectionRepo) {
        System.out.println("Collection to insert: " + collection);
        Collection c = collectionRepo.insert(collection);
        System.out.println("Inserted: " + c);
        return true;
    }

    /*
     * "User"-requests
     */
    @Override
    public User selectUser(String username, UserRepo userRepo) {
        Optional<User> user = userRepo.findById(username);

        if (user.isEmpty()) // if no user was found
            return null;

        return user.get();
    }

    @Override
    public User insertUser(User user, UserRepo userRepo) {
        if (selectUser(user.getUsername(), userRepo) != null)
            return null;
        User u = userRepo.insert(user);
        System.out.println("Inserted new User into DB: " + u);
        return u;
    }

    @Override
    public User updateUser(String oldUsername, String newUsername, String newPassword, String newEmail, UserRepo userRepo) {
        Optional<User> optionalOldUser = userRepo.findById(oldUsername);
        Optional<User> optionalNewUser = userRepo.findById(newUsername);

        if (optionalNewUser.isEmpty() && optionalOldUser.isPresent()) { // Old user exists and new user not
            User oldUser = optionalOldUser.get();
            userRepo.delete(oldUser); // Delete old user
            oldUser.setUsername(newUsername);
            oldUser.setPassword(newPassword);
            oldUser.setEmail(newEmail);
            return userRepo.save(oldUser); // Save updated user and return him
        }

        return null;
    }

    @Override
    public List<String> getUsers(String currentUser, String friendSearchTerm, UserRepo userRepo) {
        Optional<User> optionalCurrentUser = userRepo.findById(currentUser);

        if (optionalCurrentUser.isPresent()) {
            User currentUsr = optionalCurrentUser.get();
            return userRepo.findAll()
                    .stream()
                    .filter(user -> !user.getUsername().equals(currentUser) // User != current User
                            && !currentUsr.getUserFriendsId().contains(user.getUsername()) // User and current User may not be friends
                            && !user.getUserFriendRequestsId().contains(currentUser) // User may not have a friend request from current user
                            && user.compareUsernames(friendSearchTerm)) // User must be partly similar to friendSearchTerm
                    .map(User::getUsername)
                    .toList();
        }

        return new LinkedList<>();
    }

    /*
     * "Sammlung" requests
     */
    @Override
    public boolean insertSammlungToUser(String username, String name, String visibility, String category,
                                        UserRepo userRepo) {
        Optional<User> optionalUser = userRepo.findById(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getSammlungen().add(new Sammlung(name, visibility, category, new LinkedList<>(), new LinkedList<>()));
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSammlungOfUser(String username, int sammlungNummer, String newVisibility,
                                        UserRepo userRepo) {
        Optional<User> optionalUser = userRepo.findById(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Sammlung> sammlungen = user.getSammlungen();

            if (sammlungNummer <= sammlungen.size()) {
                sammlungen.get(sammlungNummer - 1).setVisibility(newVisibility);
                System.out.println(user.toJSON());
                userRepo.save(user);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean copySammlungToUser(String username, String usernameFriend, int sammlungIdFriend, UserRepo userRepo) {
        Optional<User> optionalUser = userRepo.findById(username);
        Optional<User> optionalFriendUser = userRepo.findById(usernameFriend);

        if (optionalUser.isPresent() && optionalFriendUser.isPresent()) { // User and friend user exist
            User user = optionalUser.get();
            User friendUser = optionalFriendUser.get();
            List<Sammlung> sammlungenFriend = friendUser.getSammlungen();

            if (0 <= sammlungIdFriend && sammlungIdFriend < sammlungenFriend.size()) { // Sammlung of friend exists
                user.getSammlungen().add(sammlungenFriend.get(sammlungIdFriend));
                userRepo.save(user);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean addSammlungEvaluationToFriend(String username, String usernameFriend, int sammlungIdFriend, boolean evaluation, UserRepo userRepo) {
        Optional<User> optionalUser = userRepo.findById(username);
        Optional<User> optionalFriendUser = userRepo.findById(usernameFriend);

        if (optionalUser.isPresent() && optionalFriendUser.isPresent()) { // User and friend user exist
            User user = optionalUser.get();
            User friendUser = optionalFriendUser.get();
            List<Sammlung> sammlungenFriend = friendUser.getSammlungen();

            if (0 <= sammlungIdFriend && sammlungIdFriend < sammlungenFriend.size()) { // Sammlung of friend exists
                List<Pair<String, Boolean>> evaluations = friendUser.getSammlungen().get(sammlungIdFriend).getEvaluations();

                if (evaluations.stream().map(Pair::getKey).toList().contains(username)) // Remove old evaluation of user
                    evaluations.removeIf(eval -> eval.getKey().equals(username));

                // Add new evaluation of user
                evaluations.add(new MutablePair<>(username, evaluation));
                userRepo.save(friendUser);

                return true;
            }
        }

        return false;
    }

    /*
     * "User friends"-requests
     */
    @Override
    public boolean insertFriendToUser(String username, String usernameFriend, UserRepo userRepo) {
        Optional<User> optUser1 = userRepo.findById(username), optUser2 = userRepo.findById(usernameFriend);

        if (optUser1.isPresent() && optUser2.isPresent()) {
            User user1 = optUser1.get();
            User user2 = optUser2.get();

            List<String> friendsId1 = user1.getUserFriendsId();
            List<String> friendsId2 = user2.getUserFriendsId();
            List<String> friendRequestsId1 = user1.getUserFriendRequestsId();
            List<String> friendRequestsId2 = user2.getUserFriendRequestsId();

            if (!friendsId1.contains(usernameFriend) && !friendsId2.contains(username) // Both users may not be friends
                    && (friendRequestsId1.contains(usernameFriend) || friendRequestsId2.contains(username))) { // user1 must have a friend request of user2 or the other way round
                // Add friends
                user1.getUserFriendsId().add(usernameFriend);
                user2.getUserFriendsId().add(username);

                // Remove friend requests
                friendRequestsId1.removeIf(friendRequest -> friendRequest.equals(usernameFriend));
                friendRequestsId2.removeIf(friendRequest -> friendRequest.equals(username));

                // Save results
                userRepo.save(user1);
                userRepo.save(user2);
                System.out.println("New user1 friends: " + user1);
                System.out.println("New user2 friends: " + user2);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean insertFriendRequestToUser(String username, String usernameFriend, UserRepo userRepo) {
        Optional<User> optUser1 = userRepo.findById(username), optUser2 = userRepo.findById(usernameFriend);

        if (optUser1.isPresent() && optUser2.isPresent()) {
            User user1 = optUser1.get();
            User user2 = optUser2.get();
            List<String> friendsId1 = user1.getUserFriendsId();
            List<String> friendsId2 = user2.getUserFriendsId();
            List<String> friendRequestsId2 = user2.getUserFriendRequestsId();

            if (!friendsId1.contains(usernameFriend) && !friendsId2.contains(username) // Both users may not be friends
                    && !friendRequestsId2.contains(username)) { // Friend User may not have a friend request of User
                friendRequestsId2.add(username);
                userRepo.save(user2);
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean declineFriendRequestToUser(String username, String usernameFriend, UserRepo userRepo) {
        Optional<User> optionalUser = userRepo.findById(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getUserFriendRequestsId().contains(usernameFriend)) {
                user.getUserFriendRequestsId().removeIf(uname -> uname.equals(usernameFriend));
                userRepo.save(user);
                return true;
            }
        }

        return false;
    }

    /*
     * "Collection/Search result"-requests in table "User"
     */
    @Override
    public boolean insertCollectionToUser(String username, int sammlungNummer, String collectionID,
                                          UserRepo userRepo, CollectionRepo collectionRepo) {
        Optional<User> optionalUser = userRepo.findById(username);
        Optional<Collection> optionalCollection = collectionRepo.findById(collectionID);

        if (optionalUser.isPresent() && optionalCollection.isPresent()) { // User and Collection must exist
            User user = optionalUser.get();
            List<Sammlung> sammlungen = user.getSammlungen();

            if (0 < sammlungNummer && sammlungNummer <= sammlungen.size()) { // Sammlung must exist
                Sammlung sammlung = sammlungen.get(sammlungNummer - 1);

                if (!sammlung.getCollectionIds().contains(collectionID)) { // If Collection does not exist in current Sammlung
                    sammlung.getCollectionIds().add(collectionID);
                    userRepo.save(user);
                    return true;
                }
            }
        }

        return false;
    }

}


let urlParams = new URLSearchParams(window.location.search);
let username = urlParams.get('username');

/*
 * Get all friend requests of a user (GET-USER)
 */
async function getUserData(username) {
    let friendsURL = new URL("http://localhost:8080/collection/users");
    friendsURL.searchParams.append("username", username);

    return fetch(friendsURL, { method: 'GET' })
        .then(response => response.json());
}

/*
 * Fetches data of a collecton/search result (GET-COLLECTION)
 */
async function getCollection(collectionID) {
    let collectionURL = new URL("http://localhost:8080/collection/" + collectionID);
    return await fetch(collectionURL, { method: 'GET' })
        .then((respone) => respone.json());
}

/*
 * Add a collection/search result to a Sammlung (POST-COLLECTION-TO-USER)
 */
async function addCollectionToSammlung(sammlungNummer, id) {
    let collecionURL = "http://localhost:8080/collection/users/sammlung/collections";
    collecionURL += "?username=" + username + "&sammlungNummer=" + sammlungNummer + "&collectionID=" + id;
    console.log("sammlungNummer: " + sammlungNummer);
    console.log(collecionURL);

    return await fetch(collecionURL, {
        method: "POST",
        headers: {
            "Content-Type": "text/plain"
        },
        body: JSON.stringify({})
    }).then(response => response.text());
}

/*
 * Search for users to be friends (GET-FRIENDS)
 */
async function searchForUsers(currentUser, friendSearchTerm) {
    const possibleFriendsURL = new URL("http://localhost:8080/collection/all-users");
    possibleFriendsURL.searchParams.append("currentUser", currentUser);
    possibleFriendsURL.searchParams.append("friendSearchTerm", friendSearchTerm);
    console.log("possibleFriendsURL: " + possibleFriendsURL);

    return await fetch(possibleFriendsURL, { method: 'GET' })
        .then(response => response.json());
}

/*
 * Do a friend request (POST-FRIEND_REQUEST)
 */
async function doFriendRequest(currentUser, usernameFriend) {
    const friendsURL = new URL("http://localhost:8080/collection/users/friend-requests");
    friendsURL.searchParams.append("username", currentUser);
    friendsURL.searchParams.append("usernameFriend", usernameFriend);
    console.log("friendsURL: " + friendsURL);

    return await fetch(friendsURL, {
        method: 'POST',
        headers: { 'Content-Type': 'text/plain' },
        body: JSON.stringify({})
    })
        .then(response => response.text());
}

/*
 * Accept friend request (POST-FRIEND-REQUEST)
 */
async function acceptFriendRequest(currentUser, usernameFriend) {
    const friendRequestURL = new URL("http://localhost:8080/collection/users/friends");

    friendRequestURL.searchParams.append("username", currentUser);
    friendRequestURL.searchParams.append("usernameFriend", usernameFriend);

    return await fetch(friendRequestURL, {
        method: 'POST',
        headers: { 'Content-Type': 'text/plain' }
    })
        .then(response => response.text());
}

/*
 * Decline friend request (DELETE-FRIEND-REQUEST)
 */
async function declineFriendRequest(currentUser, usernameFriend) {
    const friendRequestURL = new URL("http://localhost:8080/collection/users/friend-requests");

    friendRequestURL.searchParams.append("username", currentUser);
    friendRequestURL.searchParams.append("usernameFriend", usernameFriend);

    return await fetch(friendRequestURL, { method: 'DELETE' })
        .then(response => response.text());
}

/*
 * Import Sammlung to Friends Collection (POST-COPY-SAMMLUNG)
 */
async function importSammlungFromFriend(currentUser, userFriend, sammlungID) {
    const importSammlungUrl = new URL("http://localhost:8080/collection/users/copy-sammlung");

    importSammlungUrl.searchParams.append("username", currentUser);
    importSammlungUrl.searchParams.append("usernameFriend", userFriend);
    importSammlungUrl.searchParams.append("sammlungIdFriend", sammlungID);

    return await fetch(importSammlungUrl,
        {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain' },
        }).then(response => response.text());
}
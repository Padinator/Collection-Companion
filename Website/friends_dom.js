
// Load first all DOM Elements
document.addEventListener("DOMContentLoaded", function () {
    let urlParams = new URLSearchParams(window.location.search);
    let username = urlParams.get('username');

    document.getElementById("cc-mainpage-button").addEventListener('click', function (event) {
        console.log(username);
        passUsername(username, 'mainpage');
    });

    document.getElementById("cc-account-button").addEventListener('click', function (event) {
        passUsername(username, 'account');
    });

    document.getElementById("cc-collection-button").addEventListener('click', function (event) {
        passUsername(username, 'collection');
    });

    document.getElementById("cc-friends-link").addEventListener('click', function (event) {
        passUsername(username, 'friends');
    });
});

// cc-saved-users-sammlungen
window.onload = (event) => {
    let friend = urlParams.get('friend');
    getUserData(friend).then(user => {
        document.getElementById("cc-friend-name").innerHTML = "Profil von " + user["username"];
        // console.log(user["userFriendsID"]);
        // console.log(user);
        let userSammlungen = user["sammlungen"];

        for (let i = 0; i < userSammlungen.length; i++)
            if (userSammlungen[i]["visibility"] != "privat")
                generateFriendSammlungen(user, i);
    });
};

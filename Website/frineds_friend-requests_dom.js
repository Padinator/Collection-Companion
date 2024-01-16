
/*
 * Update list of friends and friend requests during window reload
 */
window.onload = (event) => {
	let friendsDropdown = document.getElementById("cc-freundeDropdown");
	friendsDropdown.innerHTML = ""; // Delete all friends and friend requests
	friendsDropdown.appendChild(createAddFriendsDropdownElement());

	getUserData(username)
	.then(userData => {
		let friendRequests = userData["userFriendRequestsId"];
		let friends = userData["userFriendsID"];

		console.log("Friend-Requests: " + friendRequests);
		console.log("Friends: " + friends);

		// Append all friend requests to dropdown
		friendRequests.forEach(friendRequest => {
			friendsDropdown.appendChild(createDropdownElementFriendRequest(friendRequest));
		});

		// Append all friends to dropdown
		friends.forEach(friend => {
			friendsDropdown.appendChild(createDropdownElementFriend(friend));
		});
	});
}

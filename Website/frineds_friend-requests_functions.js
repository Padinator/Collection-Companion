
/*
 * Create a button as first element of a dropdown
 */
function createAddFriendsDropdownElement() {
	let addFriendElem = document.createElement("li");
	let a = document.createElement("a");

	// Set porperties/attributes
	a.setAttribute("class", "dropdown-item");
	a.setAttribute("data-bs-toggle", "modal");
	a.setAttribute("data-bs-target", "#freundeSuchenModal");
	a.innerText = "Freund Hinzufügen";

	// Add elements to their parent
	addFriendElem.appendChild(a);

	return addFriendElem;
}

/*
 * Create dropdown element for a friend
 */
function createDropdownElementFriendRequest(usernameFriend) {
	let friendRequestElem = document.createElement("li");
	let span = document.createElement("span");
	let buttonDiv = document.createElement("div");
	let buttonAccept = document.createElement("button");
	let buttonDecline = document.createElement("button");

	// Set properties/attributes
	friendRequestElem.setAttribute("class", "dropdown-item d-flex justify-content-between align-items-center");
	span.setAttribute("class", "flex-grow-1");
	span.innerText = usernameFriend;
	buttonAccept.setAttribute("class", "btn btn-success btn-sm");
	buttonAccept.innerText = "✔";
	buttonDecline.setAttribute("class", "btn btn-danger btn-sm");
	buttonDecline.innerText = "✘";
	buttonAccept.addEventListener("click", (event) => {
		acceptFriendRequest(username, usernameFriend)
		.then(response => {
			window.location.reload();
		})
		.catch((error) => console.log(error));
	});
	buttonDecline.addEventListener("click", (event) => {
		declineFriendRequest(username, usernameFriend)
		.then(response => {
			window.location.reload();
		})
		.catch((error) => console.log(error));
	});

	// Add elements to their parent
	buttonDiv.appendChild(buttonAccept);
	buttonDiv.appendChild(buttonDecline);
	friendRequestElem.appendChild(span);
	friendRequestElem.appendChild(buttonDiv);

	return friendRequestElem;
}

 /*
 * Create dropdown element for a friend request
 */
function createDropdownElementFriend(usernameFriend) {
	let friendElem = document.createElement("li");
	let a = document.createElement("a");

	// Set porperties/attributes
	a.setAttribute("class", "dropdown-item");
	a.addEventListener('click', function (event) {
		passUsernameAndFriend(username, usernameFriend, 'friends');
	});
	a.innerText = usernameFriend;

	// Add elements to their parent
	friendElem.appendChild(a);

	return friendElem;
}

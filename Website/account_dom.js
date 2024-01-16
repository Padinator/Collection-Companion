
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

// Event listener for account submitting button
document.getElementById("cc-submit-account-data-button").addEventListener("click", event => {
	const newUsername = document.getElementById("username").value;
	const newPassword = document.getElementById("password").value;
	const newEmail = document.getElementById("email").value;
	const userURL = new URL("http://localhost:8080/collection/users");
	let urlParams = new URLSearchParams(window.location.search);
		let username = urlParams.get('username');

	userURL.searchParams.append("oldUsername", newUsername);
	userURL.searchParams.append("newUsername", newUsername);
	userURL.searchParams.append("newPassword", newPassword);
	userURL.searchParams.append("newEmail", newEmail);

	console.log(userURL.href);

	if (newUsername === "" || newPassword === "" || newEmail === "")
		console.log("error");
	else {
		fetch(userURL.href, {
			method: 'PUT',
			headers: {
				'Content-Type': 'text/plain',
			},
		})
		.then(response => response.text())
		.then(response => console.log(response))
		.catch(error => console.log(error));
	}
});

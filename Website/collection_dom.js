
/*
 * Load first all DOM Elements
 */
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

/*
 * Load each time reloading the page all Sammlungen
 */
window.onload = (event) => {
	getUserData(username)
		.then(data => {
			let sammlungContainer = document.getElementById('sammlungContainer');

			// Iterate over all Sammlungen an create entries
			for (let i = 0; i < data['sammlungen'].length; ++i)
				sammlungContainer.appendChild(createSammlungsEintrag(i + 1, data["sammlungen"][i]["name"], data["sammlungen"][i]["visibility"]));
		})
		.catch((error) => console.log(error));
};

/*
 * Add event listener for creating a collection
 */
document.getElementById('cc-create-collection-button').addEventListener('click', function (event) {
	document.getElementById('sammlungName').value = '';
	document.getElementById('sichtbarkeit').selectedIndex = 0;
	document.getElementById('kategorie').selectedIndex = 0;
});

/*
 * Add event for submitting a new Sammlung
 */
document.getElementById('cc-create-collection-submit-button').addEventListener('click', function (event) {
	event.preventDefault();

	let name = document.getElementById('sammlungName').value;
	let visibility = document.getElementById('sichtbarkeit').value;
	let category = document.getElementById('kategorie').value;

	let urlParams = new URLSearchParams(window.location.search);
	let username = urlParams.get('username');

	console.log('Sammlungsname: ', name);
	console.log('Sichtbarkeit: ', visibility);
	console.log('Kategorie: ', category);
	console.log("Username: ", username);

	let collectionUrl = new URL("http://localhost:8080/collection/users/sammlung");

	collectionUrl.searchParams.append("username", username);
	collectionUrl.searchParams.append("name", name);
	collectionUrl.searchParams.append("visibility", visibility);
	collectionUrl.searchParams.append("category", category);

	console.log(collectionUrl.href);

	fetch(collectionUrl.href, {
		method: "POST",
		mode: 'no-cors',
		headers: {
			'Content-Type': 'text/plain',
		}
	})
		.then(response => response)
		.then(respone => {
			console.log(respone.text());
			console.log(respone);

			if (respone.status == "200") {
				let sammlungContainer = document.getElementById('sammlungContainer');
				sammlungContainer.appendChild(createSammlungsEintrag(sammlungContainer.children.length, name, visibility))
				console.log("Eintrag wurde erstellt!");
			}
		}).catch((error) => console.log(error));
	window.location.reload();
});

// document.getElementById("cc-collection-entry").addEventListener('click', function () {

//     let developers = document.getElementById("developers");
//     developers.innerHTML = "Toooom";

//     let price = document.getElementById("price");
//     price.innerHTML = "44444";
// });

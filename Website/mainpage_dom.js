
// Extract later to another file
document.getElementById("freundSuchenInput").addEventListener("keypress", event => {
	if (event.key === "Enter")
		event.preventDefault(); // Cancel the default action, if needed
});

document.getElementById("freundSuchenInput").addEventListener("keyup", event => {
	if (event.key === "Enter")
		event.preventDefault(); // Cancel the default action, if needed

	document.getElementById("cc-search-friend-button").click(); // Trigger the button element with a click
});

document.getElementById("cc-search-friend-button").addEventListener('click', function (event) {
	event.preventDefault();
	const freundList = document.getElementById('freundListe');
	freundList.innerHTML = "";

	const friendSearchTerm = document.getElementById("freundSuchenInput").value;
	let urlParams = new URLSearchParams(window.location.search);
	//let username = urlParams.get('username');

	// Search for friends
	searchForUsers(username, friendSearchTerm)
		.then(friends => {
			console.log("friends: " + friends);
			// Display all found friends
			friends.forEach(friend => {
				const listelement = document.createElement("li");
				listelement.className = "list-group-item d-flex justify-content-between align-items-center";
				listelement.textContent = friend;

				const addButton = document.createElement("button");
				addButton.className = "btn btn-success btn-sm";
				addButton.textContent = "Hinzufügen";
				addButton.addEventListener("click", event => {
					event.preventDefault();
					alert("Freund '" + friend + "' wurde hinzugefügt!");
					freundList.removeChild(listelement); // Remove potential friend from list after requesting him to be a friend

					// Fetch/Do POST-Request to add friend via API-Gateway
					doFriendRequest(username, friend)
						.catch((error) => console.log(error));
				});

				listelement.appendChild(addButton);
				freundList.appendChild(listelement);
			});
		})
		.catch((error) => console.log(error));
});



// Do something
const fetchData = {
	method: 'GET'
}

let category = 'Kategorien';

document.querySelectorAll('#category-select .dropdown-item').forEach(function (item) {
	item.addEventListener('click', function () {
		document.getElementById('category-text').textContent = this.textContent;
		document.getElementById('category-text').value = this.textContent;
		let search = window.location.search.replace("?", "");
		search = search.replace("category=game", "");
		search = search.replace("category=movie", "");
		search = search.replace("category=series", "");

		if (this.textContent == 'Spiele') {
			history.pushState({}, '', window.location.pathname + '?category=game&' + search);
			category = 'game';
		} else if (this.textContent == 'Filme') {
			history.pushState({}, '', window.location.pathname + '?category=movie&' + search);
			category = 'movie';
		} else if (this.textContent == 'Serien') {
			history.pushState({}, '', window.location.pathname + '?category=series&' + search);
			category = 'series';
		}
		console.log(document.getElementById('category-text').value);
	});
});

/*
 * Add "enter"-action to input field for searching results
 */
document.getElementById("search").addEventListener("keypress", function (event) {
	if (event.key === "Enter") {
		event.preventDefault(); // Cancel the default action, if needed
		document.getElementById("cc-search-button").click(); // Trigger the button element with a click
	}
});

/*
 * Add event listener for searching for collections/search results
 */
document.getElementById('cc-search-button').addEventListener('click', function (e) {
	// Check if a category is selected
	if (category === 'Kategorien') {
		alert('Bitte suchen Sie sich eine Kategorie aus.');
		e.preventDefault();
	} else {
		if (document.getElementById('search').value != '') {
			console.log("Search term: " + document.getElementById('search').value);

			let urlParams = new URLSearchParams(window.location.search);
			const username = urlParams.get('username');
			let searchTerm = document.getElementById('search').value;
			history.replaceState({}, '', window.location.pathname + '?category=' + encodeURIComponent(category) + '&searchTerm=' + encodeURIComponent(searchTerm) + "&username=" + username);
			console.log('Current URL:', window.location.href); // Log the current URL

			let URL = window.location.href;
			URL = URL.replace("localhost:8090/mainpage.html", "localhost:8080/collection");
			console.log(URL);

			// Fetch API-Gateway for search results
			fetch(URL, fetchData)
			.then(response => response.json())
			.then(data => {
				let searchResultsDiv = document.getElementById('cc-search-result-entries');
				searchResultsDiv.innerHTML = ''; // Remove all child nodes

				for (let i = 0; i < data.length; ++i)
					searchResultsDiv.appendChild(createEntry(data[i], i));

				console.log(data);
			})
			.catch(error => {
				console.error('There was a problem with the fetch operation:', error.message);
				console.log("Error, don't know why here is the URL: " + URL);
			});
			e.preventDefault();
		} else
			alert('Bitte geben Sie eine suche ein.');
	}
});

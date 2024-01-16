
/*
 * Creates an entry for a search result
 */
function createEntry(data, number) {
	let entry = document.createElement('div');
	let divContent = document.createElement('div');
	let titleElem = document.createElement('h5');
	let descriptionElem = document.createElement('p');
	let button = document.createElement('button');

	// Set attributes and "innerText"
	entry.className = 'p-2 border mb-3 rounded position-relative';
	titleElem.classname = 'text-left';
	titleElem.innerText = data["title"];
	descriptionElem.innerText = data["short_description"];
	button.classname = 'btn btn-secondary btn-sm position-absolute bottom-0 end-0 m-2';
	button.id = 'cc-add-collection-button-' + number;
	button.setAttribute('data-bs-toggle', 'modal');
	button.setAttribute('data-bs-target', '#sammlungHinzufuegen');
	button.addEventListener('click', event => setSammlungHinzufuegenContent(data));
	button.innerText = "Hinzufügen";

	// Append content elements to content div
	divContent.appendChild(titleElem);
	divContent.appendChild(descriptionElem);

	// Append "innerHTML" objects to entry
	entry.appendChild(divContent);
	entry.appendChild(button);

	return entry;
}

/*
 * Displays all elements for showing a collection/search result
 */
function setSammlungHinzufuegenContent(data) {
	console.log(data);

	let sammlungsDiv = document.getElementById('sammlungsinhalt');
	let headerTitle = document.getElementById('sammlungErstellenModalLabel');
	let displayingData = {};

	sammlungsDiv.innerHTML = ''; // Reset content

	// Unpack all data of a collection/search result
	for (const [key, value] of Object.entries(data)) {
		let resultStr = "";

		if (key === 'id' || key === 'time_stamp') // Value is ID => ignore
			continue;
		if (key === 'title') {
			headerTitle.innerText = value;
			continue;
		} else if (key === 'detailed_description') // Value is a detailed description => limit chars
			resultStr = value.substring(0, 200) + " ...";
		else if (key === 'short_description') // Value is a short description => limit chars
			resultStr = value.substring(0, 100) + " ...";
		else if (Array.isArray(value)) { // Value is an array -> bring in one line separated string
			resultStr = value.join(', ');
		} else // Value is a single value
			resultStr = value;

		// Set attributes
		let title = document.createElement('p');
		let content = key === 'main_img' ? document.createElement('img') : document.createElement('p'); // p => text; img => main image
		let resultKey = key.toLowerCase().replaceAll('_', ' ');
		resultStr = resultStr.toLowerCase().replaceAll('_', ' ');

		resultKey = resultKey.charAt(0).toUpperCase() + resultKey.slice(1);
		resultStr = resultStr.charAt(0).toUpperCase() + resultStr.slice(1);

		title.innerText = resultKey + ":";
		title.setAttribute('style', 'float: left; width: 50%');

		if (key === "main_img")
			content.setAttribute('src', value);
		else
			content.innerText = resultStr;
		content.setAttribute('style', 'float: left; width: 50%');

		// Add to parent
		sammlungsDiv.appendChild(title);
		sammlungsDiv.appendChild(content);
	}

	// Set Dropdown for "Sammlung hinzufuegen"
	let sammlungsDropdown = document.getElementById('kategorie');
	let firstOption = document.createElement('option');

	// Remove last/old options
	sammlungsDropdown.innerHTML = "";

	// First option is only for displaying what to do
	firstOption.selected = true;
	firstOption.disabled = true;
	firstOption.innerText = "Wähle eine Sammlung!";
	sammlungsDropdown.appendChild(firstOption);

	// Fetch all sammlungen of current user
	getUserData(username)
	.then(userData => {
		console.log("user data:");
		console.log(userData);
		let usersSammlungen = userData["sammlungen"];
		let userSammlungenDict = {};

		// Shwo all sammlungen not containing this collection/search result
		let sammlungURL = "http://localhost:8080/collection/users/sammlung/collections";
		sammlungURL += "?username=" + username;
		
		for (let i = 0; i < usersSammlungen.length; ++i)
			if (!usersSammlungen[i]["collectionID"].includes(data["id"]))
				userSammlungenDict[i] = usersSammlungen[i];

		console.log(userSammlungenDict);

		// Display all Sammlungen of current user not containing the search result/collection
		for (const [index, sammlung] of Object.entries(userSammlungenDict)) {
			let sammlungElem = document.createElement('option');
			sammlungElem.id = "cc-collection-search_result-" + (parseInt(index) + 1); // index + 1 = sammlungNummer
			sammlungElem.innerText = sammlung.name;
			sammlungsDropdown.appendChild(sammlungElem);
		};

		// Send "add request" to add search result/collection into DB
		document.getElementById("cc-add-collection-button-two").addEventListener("click", event => {
			event.preventDefault(); // Prevent popup from disappearing

			const dropdownForSammlungen = document.getElementById("kategorie");
			const selectedSammlung = dropdownForSammlungen.selectedIndex;
			const sammlungNummer = dropdownForSammlungen.children[selectedSammlung].id.split("-").at(-1);
			console.log(sammlungNummer);

			addCollectionToSammlung(sammlungNummer, data["id"])
			.then(response => {
				sammlungsDropdown.removeChild(sammlungsDropdown.children[dropdownForSammlungen.selectedIndex]); // Remove the Sammlung from List, wich a collection was added to
			})
			.catch((error) => console.log(error));
		});
	})
	.catch((error) => console.log(error));
}

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
		passUsername(username, 'friends');
	});
	a.innerText = usernameFriend;

	// Add elements to their parent
	friendElem.appendChild(a);

	return friendElem;
}
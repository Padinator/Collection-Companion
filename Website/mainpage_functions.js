
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
	// button.classname = 'btn btn-secondary btn-sm position-absolute bottom-0 end-0 m-2';
	button.id = 'cc-add-collection-button-' + number;
    button.classList.add("btn", "btn-secondary", "btn-sm", "position-absolute", "bottom-0", "end-0" ,"m-2");
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

	// Unpack all data of a collection/search result
	const result = addDataForPopup(data);
	sammlungsDiv.innerHTML = result["sammlungsDiv"].innerHTML;
	headerTitle.innerHTML = result["headerTitle"].innerHTML;

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
 * Add passed data to div and title for creating a popup and return title and div
 */
function addDataForPopup(data) {
	let sammlungsDiv = document.createElement('div');
	let headerTitle = document.createElement('h5');

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

	return { "sammlungsDiv": sammlungsDiv, "headerTitle": headerTitle }
}

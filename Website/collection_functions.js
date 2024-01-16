
/*
 * Creates an entry for a Sammlung
 */
function createSammlungsEintrag(number, sammlungsTitel, visibility) {
    let sammlungEintrag = document.createElement('div');
    sammlungEintrag.className = 'p-2 border mb-3 rounded position-relative';

    let titelElement = document.createElement('h5');
    titelElement.className = 'text-left';
    titelElement.textContent = sammlungsTitel;
    sammlungEintrag.appendChild(titelElement);

    let bearbeitenButton = document.createElement('button');
    bearbeitenButton.className = 'btn btn-secondary btn-sm position-absolute bottom-0 end-0 m-2';
    bearbeitenButton.id = 'cc-edit-collection-button-' + number;
    bearbeitenButton.textContent = 'Bearbeiten';
    bearbeitenButton.setAttribute('data-bs-toggle', 'modal');
    bearbeitenButton.setAttribute('data-bs-target', '#sammlungBearbeiten');
    sammlungEintrag.appendChild(bearbeitenButton);

    bearbeitenButton.addEventListener('click', function (event) {
        bearbeitenButtonClickHandler(event, visibility); // Set handling for editing a Sammlung
        showAllEntrys(event); // Set handling for showing data of all collections/search results of a Sammlung
    });

    return sammlungEintrag;
}

/*
 * Creates list of all collection-IDs of a Sammlung
 */
function showAllEntrys(event) {
    let list = document.getElementById("cc-list-of-all-sammlungen");
    let sammlungNummer = event.target.id.split("-").at(-1);
    list.innerHTML = ""; // Remove old list items/collections/search results
    console.log(event.target.id);
    console.log(sammlungNummer);
    console.log("username: " + username);

    getUserData(username).then(data => {
        let collectionIDs = data["sammlungen"][parseInt(sammlungNummer - 1)]["collectionID"];
        console.log(data);
        console.log(collectionIDs);
        for (let i = 0; i < collectionIDs.length; i++) {
            let listItem = document.createElement("li");
            listItem.classList.add("list-group-item", "d-flex", "justify-content-between", "align-items-center");
            listItem.setAttribute("data-bs-toggle", "modal");
            listItem.setAttribute("data-bs-target", "#showEntryModal");
            listItem.setAttribute("id", "cc-collection-entry-" + i);

            let closeButton = document.createElement("button");
            closeButton.type = "button";
            closeButton.classList.add("btn", "btn-outline-danger", "btn-sm", "cc-delete-entry-in-collection-button");
            closeButton.textContent = "X";

            // closeButton.addEventListener("click", function () {
            // });

            getCollection(collectionIDs[i])
                .then(collection => {
                    listItem.appendChild(document.createTextNode(collection["title"]));
                    listItem.appendChild(closeButton);
                    listItem.addEventListener('click', event => {
                        const result = addDataForPopup(collection);
                        let sammlungsDiv = result["sammlungsDiv"];
                        let headerTitle = result["headerTitle"];
                        document.getElementById("sammlungErstellenModalLabel").innerHTML = headerTitle.innerHTML;
                        document.getElementById("sammlungsinhalt").innerHTML = sammlungsDiv.innerHTML;
                    });
                    list.appendChild(listItem);
                });
        }
    })
}

/*
 * Handles editting button for Sammlungen
 */
function bearbeitenButtonClickHandler(event, visibility) {
    let sichtbarkeitElement = document.getElementById('sichtbarkeit-bearbeiten');
    let sammlungNummer = event.target.id.split("-").at(-1);

    sichtbarkeitElement.value = visibility;
    console.log("Übergebene Visibility:", visibility);
    console.log(sammlungNummer);

    // Add event for editing a Sammlung
    document.getElementById('cc-submit-collection-button').addEventListener('click', function (event) {
        event.preventDefault();

        let sammlungUrl = new URL("http://localhost:8080/collection/users/sammlung");
        let urlParams = new URLSearchParams(window.location.search);
        let username = urlParams.get('username');
        let newVisibility = document.getElementById('sichtbarkeit-bearbeiten').value;

        sammlungUrl.searchParams.append("username", username);
        sammlungUrl.searchParams.append("sammlungNummer", sammlungNummer);
        sammlungUrl.searchParams.append("newVisibility", newVisibility);
        console.log(sammlungUrl.href);

        fetch(sammlungUrl.href, {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'text/plain',
                //'Content-Type': 'application/json',
                'Content-Length': '0'
            },
            body: JSON.stringify({})
        })
            .then(response => response.text())
            .then(response => console.log(response))
            .catch((error) => console.log(error));
    });
}

// Hier war was

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

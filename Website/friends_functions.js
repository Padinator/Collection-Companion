function generateFriendSammlungen(user, id) {
    let listEntry = document.createElement("li");
    listEntry.classList.add("list-group-item", "d-flex", "justify-content-between", "align-items-center");
    listEntry.setAttribute("id", "cc-collection-friend-entry-" + id);
    let textNode = document.createTextNode(user["sammlungen"][id]["name"]);
    listEntry.appendChild(textNode);
    let divElement = document.createElement("div");

    let thumbsUpButton = document.createElement("button");
    thumbsUpButton.type = "button";
    thumbsUpButton.classList.add("btn", "btn-success");
    thumbsUpButton.id = "cc-thumbs-up-button";
    thumbsUpButton.innerHTML = "👍";
    divElement.appendChild(thumbsUpButton);
    thumbsUpButton.addEventListener('click', function(event) {
        evaluateSammlungFromFriend(username, user["username"], id, true)
        .catch(error => console.log(error));
    });

    let thumbsDownButton = document.createElement("button");
    thumbsDownButton.type = "button";
    thumbsDownButton.classList.add("btn", "btn-danger");
    thumbsDownButton.id = "cc-thumbs-down-button";
    thumbsDownButton.innerHTML = "👎";
    divElement.appendChild(thumbsDownButton);
    thumbsDownButton.addEventListener('click', function(event) {
        evaluateSammlungFromFriend(username, user["username"], id, false)
        .catch(error => console.log(error));
    });

    let showLink = document.createElement("a");
    showLink.href = "#";
    showLink.classList.add("btn", "btn-primary");
    showLink.id = "cc-show-friend-collection-button";
    showLink.setAttribute("data-bs-toggle", "modal");
    showLink.setAttribute("data-bs-target", "#sammlungAnsehen");
    showLink.innerHTML = "Ansehen";
    divElement.appendChild(showLink);

    let importCollection = document.createElement("a");
    importCollection.href = "#";
    importCollection.classList.add("btn", "btn-primary");
    importCollection.id = "cc-import-friend-collection-button-" + id;
    importCollection.innerHTML = "Importieren";
    divElement.appendChild(importCollection);

    showLink.addEventListener('click', function (event) {
        let usersSammlungenTitel = user["sammlungen"][id]["name"];
        let list = document.getElementById("cc-friends-collection");

        let collectionIDs = user["sammlungen"].find(item => item.name === usersSammlungenTitel)["collectionID"];
        console.log(collectionIDs);

        for (let i = 0; i < collectionIDs.length; i++) {
            let listItem = document.createElement("li");
            listItem.classList.add("list-group-item");
            listItem.setAttribute("data-bs-toggle", "modal");
            listItem.setAttribute("data-bs-target", "#showCollectionEntry");

            getCollection(collectionIDs[i])
                .then(collection => {
                    // listItem.appendChild(document.createTextNode(collection["title"]));
                    listItem.innerHTML = collection["title"];
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
    });

    importCollection.addEventListener('click', function (event) {
        // console.log(user["sammlungen"]);
        console.log(user);

        let sammlungsIndex = event.target.id.split("-").at(-1);
        console.log(user["sammlungen"][sammlungsIndex]);

        importSammlungFromFriend(username, user["username"], sammlungsIndex)
            .catch(error => console.log(error));
    });

    listEntry.appendChild(divElement);

    let friendList = document.getElementById("cc-saved-users-sammlungen");
    friendList.appendChild(listEntry);
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
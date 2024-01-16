function generateFriendSammlungen(user, id) {
    let listEntry = document.createElement("li");
    listEntry.classList.add("list-group-item", "d-flex", "justify-content-between", "align-items-center");
    let textNode = document.createTextNode(user["sammlungen"][id]["name"]);
    listEntry.appendChild(textNode);
    let divElement = document.createElement("div");

    let thumbsUpButton = document.createElement("button");
    thumbsUpButton.type = "button";
    thumbsUpButton.classList.add("btn", "btn-success");
    thumbsUpButton.id = "cc-thumbs-up-button";
    thumbsUpButton.innerHTML = "👍";
    divElement.appendChild(thumbsUpButton);

    let thumbsDownButton = document.createElement("button");
    thumbsDownButton.type = "button";
    thumbsDownButton.classList.add("btn", "btn-danger");
    thumbsDownButton.id = "cc-thumbs-down-button";
    thumbsDownButton.innerHTML = "👎";
    divElement.appendChild(thumbsDownButton);

    let showLink = document.createElement("a");
    showLink.href = "#";
    showLink.classList.add("btn", "btn-primary");
    showLink.id = "cc-show-friend-collection-button";
    showLink.setAttribute("data-bs-toggle", "modal");
    showLink.setAttribute("data-bs-target", "#sammlungAnsehen");
    showLink.innerHTML = "Ansehen";
    divElement.appendChild(showLink);

    showLink.addEventListener('click', function(event) {
        console.log("Hier!");
    });

    listEntry.appendChild(divElement);

    let friendList = document.getElementById("cc-saved-users-sammlungen");
    friendList.appendChild(listEntry);
}
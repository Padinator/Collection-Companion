
function getValuesFromLogin() {
	// Get Username from Input Form
	let username = document.getElementById("username").value;
	// Get Password from Input Form
	let password = document.getElementById("password").value;

	console.log("Data:", username, password);

	let urlWithParams = new URL("http://localhost:8080/collection/users");

	urlWithParams.searchParams.append("username", username);

	console.log("URL:", urlWithParams);

	let res = fetch(urlWithParams.href, {
		method: "GET",
	}).then((response) => {
		console.log(response.status);
		if (response.status == 404) {
			console.log("No User was found!");
			alert("No User was found");
		} else {
			// go on to the next page
			let nextPageUrl = new URL("http://localhost:8090/mainpage.html");
			nextPageUrl.searchParams.append("username", username);
			window.location.href = nextPageUrl.href;
			console.log("User was found!");
		}
	});
}

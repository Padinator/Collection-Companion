
function getRegisterValues() {
	let username = document.getElementById("username").value;
	let password = document.getElementById("password").value;
	let email = document.getElementById("email").value;

	console.log(username, password, email);

	let urlWithParams = new URL("http://localhost:8080/collection/users");

	urlWithParams.searchParams.append("username", username);
	urlWithParams.searchParams.append("password", password);
	urlWithParams.searchParams.append("email", email);

	console.log(String(urlWithParams.href));

	fetch(String(urlWithParams.href), {
		method: "POST",
		headers: {
			'Content-Type': 'text/plain',
		},
		body: JSON.stringify({}),
	}).then(respone => respone.text())
		.then((res) => {
			console.log(res);
			if (res == "User already exists!") {
				alert("User already exists");
			} else {
				window.location.href = "http://localhost:8090/index.html";
			}
		}).catch(error => console.log(error));
}


document.getElementById("email").addEventListener("keypress", event => {
	if (event.key === "Enter") {
		event.preventDefault(); // Cancel the default action, if needed
		document.getElementById("cc-registration").click(); // Trigger the button element with a click
	}
});

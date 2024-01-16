
// Add "enter"-action to input field for login
document.getElementById("password").addEventListener("keypress", function(event) {
	if (event.key === "Enter") {
		event.preventDefault(); // Cancel the default action, if needed
		document.getElementById("cc-login").click(); // Trigger the button element with a click
	}
});

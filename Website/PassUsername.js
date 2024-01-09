
function passUsername(username, site) {

    let newUrl = new URL("http://localhost:8090/" + site + ".html");
    newUrl.searchParams.append("username", username);

    console.log(newUrl.href);

    window.location.href = newUrl.href;
}
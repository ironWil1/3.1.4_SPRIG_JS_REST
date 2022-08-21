fetch(urlListOfUsers)
    .then((res) => res.json())
    .then((data) => {
        userInfo +=
            '<a>User: </a>' + data[0][0] +
            '<a> With roles: </a>' + getRoles(data[0][1]);
        $('#userInfo').append(userInfo);
    })
urlDeleteUser = 'http://localhost:8080/api/admin/delete'

function handleSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.target);
    console.log(data)
    let userInfo = Object.fromEntries(data.entries());
    const value = data.get('email');
    console.log({ value });
    userInfo.roles = [userInfo.roles]
    let json = JSON.stringify(userInfo)
    console.log(json)
    fetch(urlDeleteUser, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: json
    }).then(json => {
        console.log(json)
        showUserInfo();
        drawTable();
// Handle success
    })
        .catch(err => {
            console.log(err) // Handle errors
        });
}

const deleteForm = document.querySelector('.deleteForm');
console.log(deleteForm)
deleteForm.addEventListener('submit', handleSubmit);
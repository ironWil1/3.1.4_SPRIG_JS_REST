urlSaveUser = 'http://localhost:8080/api/admin/save'
function handleSaveSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.target);
    let userInfo = Object.fromEntries(data.entries());
    let json = JSON.stringify(userInfo)
    let obj = JSON.parse(json)
    obj.roles = [obj.roles]
    json = JSON.stringify(obj)
    console.log(json)
    fetch(urlSaveUser, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: json
    }).then(json => {
        console.log(json)
        if ($(".editForm")[0]) {
            console.log('INSIDE OF MODAL')
            $('#closeButton').click()
        }
        showUserInfo();
        drawTable();
// Handle success
    })
        .catch(err => {
            console.log(err) // Handle errors
        });
}

if ($(".editForm")[0]) {
    const editForm = document.querySelector('.editForm');
    editForm.addEventListener('submit', handleSaveSubmit);
}





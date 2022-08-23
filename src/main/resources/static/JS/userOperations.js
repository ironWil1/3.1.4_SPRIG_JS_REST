urlSaveUser = 'http://localhost:8080/api/admin/save'

async function _postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(data)
    });

    return response.json();
}

function handleSubmit(event) {
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
        console.log(json) // Handle success
    })
        .catch(err => {
            console.log(err) // Handle errors
        });
}

const form = document.querySelector('form');
form.addEventListener('submit', handleSubmit);

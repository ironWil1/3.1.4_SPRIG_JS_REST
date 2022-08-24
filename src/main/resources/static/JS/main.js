const urlListOfUsers = 'http://localhost:8080/api/admin';
const urlOneUser = 'http://localhost:8080/api/admin/edit/';

let usersTable = '';
let userInfo = '';

function newLocation() {
    window.location.href = "admin/new";
}

function goHome() {
    window.location.href = "admin";
}

function getRoles(roles) {
    let tableRoles = '';
    if (roles.length > 1) {
        tableRoles += 'ADMIN USER'
    } else {
        tableRoles += 'USER'
    }
    return tableRoles
}

function showEditModal(id) {
    let modalInstance = new bootstrap.Modal(document.getElementById('editModal'));
    let modal = document.getElementById('editModal');

    let href = urlOneUser + id
    console.log(href)

    fetch(href)
        .then(result => result.json())
        .then(user => {
            $('#InputEditID').val(user.id);
            $('#InputEditUsername').val(user.username);
            $('#InputEditSurname').val(user.surName);
            $('#InputEditAge').val(user.age);
            $('#InputEditEmail').val(user.email);
            $('#InputEditPassword').val(user.password);
        })
    modalInstance.show(modal);
}

function showDeleteModal(id) {
    let modalInstance = new bootstrap.Modal(document.getElementById('deleteModal'));
    let modal = document.getElementById('deleteModal');

    let href = urlOneUser + id

    fetch(href)
        .then(result => result.json())
        .then(user => {
            $('#InputDeleteID').val(user.id);
            $('#InputDeleteUsername').val(user.username);
            $('#InputDeleteSurname').val(user.surName);
            $('#InputDeleteAge').val(user.age);
            $('#InputDeleteEmail').val(user.email);
            $('#InputDeletePassword').val(user.password);
            $('#FormDeleteRoleSelect').val(getRoles(user.roles));
        })
    modalInstance.show(modal);
}

//GET - Read the user fields
fetch(urlListOfUsers)
    .then((res) => res.json())
    .then((data) => {
        for (let key = 0, size = data[1].length; key < size; key++) {
            let reff = 'http://localhost:8080/api/admin/edit/' + data[1][key].id
            usersTable += '<tr><td class="id">'
                + data[1][key].id
                + '</td><td class="username">'
                + data[1][key].username
                + '</td>'
                + '</td><td class="surName">'
                + data[1][key].surName
                + '</td>'
                + '</td><td class="age">'
                + data[1][key].age
                + '</td>'
                + '</td><td class="email">'
                + data[1][key].email
                + '</td>'
                + '</td><td class="roles">'
                + getRoles(data[1][key].roles)
                + '</td><td class="btnEdit">'
                + '<button type="button" class="btn btn-primary btn-sm me-2" ' +
                'onclick="showEditModal(' + data[1][key].id + ')" ' +
                'data-bs-toggle="modal">Edit</button>'
                + '<button type="button" href = urlOneUser class="btn btn-danger btn-sm" ' +
                'onclick="showDeleteModal(' + data[1][key].id + ')" '
                + 'data-bs-toggle="modal" data-bs-target="">'
                + 'Delete </button>'
                + '</td>';
        }
        userInfo +=
            '<a>User: </a>' + data[0][0] +
            '<a> With roles: </a>' + getRoles(data[0][1]);

        $('#tbody').append(usersTable);

        $('#userInfo').append(userInfo);
    })




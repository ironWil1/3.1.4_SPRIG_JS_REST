const urlListOfUsers = 'http://localhost:8080/api/admin';
const urlLoggedUser = 'http://localhost:8080/api/admin/userInfo';
const urlOneUser = 'http://localhost:8080/api/admin/edit/';

function getRoles(roles) {
    let tableRoles = '';
    if (roles.length > 1) {
        tableRoles = 'ADMIN USER'
    } else {
        tableRoles = 'USER'
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

function showUserInfo() {
    fetch(urlLoggedUser)
        .then((res) => {
            if (res.ok) {
                res.json()
                    .then((data) => {
                        console.log(data)
                        let userInfo = '';
                        $("#userInfo").html("");
                        userInfo =
                            '<a>User: </a>' + data.email +
                            '<a> With roles: </a>' + getRoles(data.roles);
                        $('#userInfo').append(userInfo);
                    })
            } else {
                window.location.href = 'http://localhost:8080/logout';
            }
        });
}

function drawTable() {
    fetch(urlListOfUsers)
        .then((res) => {
            if (res.ok) {
                res.json()
                    .then((data) => {
                        console.log(data);
                        $("#tbody").html("");
                        let usersTable = '';
                        for (let key = 0, size = data[data.length - 1].length; key < size; key++) {
                            console.log(data[data.length - 1][key].length)
                            usersTable += '<tr><td class="id">'
                                + data[data.length - 1][key].id
                                + '</td><td class="username">'
                                + data[data.length - 1][key].username
                                + '</td>'
                                + '</td><td class="surName">'
                                + data[data.length - 1][key].surName
                                + '</td>'
                                + '</td><td class="age">'
                                + data[data.length - 1][key].age
                                + '</td>'
                                + '</td><td class="email">'
                                + data[data.length - 1][key].email
                                + '</td>'
                                + '</td><td class="roles">'
                                + getRoles(data[data.length - 1][key].roles)
                                + '</td><td class="btnEdit">'
                                + '<button type="button" class="btn btn-primary btn-sm me-2" ' +
                                'onclick="showEditModal(' + data[data.length - 1][key].id + ')" ' +
                                'data-bs-toggle="modal">Edit</button>'
                                + '<button type="button" href = urlOneUser class="btn btn-danger btn-sm" ' +
                                'onclick="showDeleteModal(' + data[data.length - 1][key].id + ')" '
                                + 'data-bs-toggle="modal" data-bs-target="">'
                                + 'Delete </button>'
                                + '</td>';
                        }
                        $('#tbody').append(usersTable);
                    });
            }
        });
}

function drawUserPage() {
    fetch(urlLoggedUser)
        .then((res) => res.json())
        .then((data) => {
            $("#userPage").html("");
            let userPage = '';
            userPage = '<tr><td class="id">'
                    + data.id
                    + '</td><td class="username">'
                    + data.username
                    + '</td>'
                    + '</td><td class="surName">'
                    + data.surName
                    + '</td>'
                    + '</td><td class="age">'
                    + data.age
                    + '</td>'
                    + '</td><td class="email">'
                    + data.email
                    + '</td>'
                    + '</td><td class="roles">'
                    + getRoles(data.roles)
                    + '</td><tr>'
            console.log(userPage)
            $('#userPage').append(userPage);
        })
}

function checkIfAdmin() {
    fetch(urlLoggedUser)
        .then((res) => res.json())
        .then((data) => {
            $("#navAdminUser").html("");
            let navRoleTabs = '';
            if (data.roles.length > 1) {
                navRoleTabs =
                    '<ul class="nav flex-column nav-pills">' +
                        '<li class="nav-item" >\n' +
                            '<a class="nav-link" href="/admin">Admin</a>' +
                        '</li>' +
                        '<li class="nav-item">' +
                            '<a class="nav-link active" href="/user">User</a>' +
                        '</li>' +
                    '</ul>'
            } else {
                navRoleTabs =
                    '<ul class="nav flex-column nav-pills">\n' +
                        '<li class="nav-item" >\n' +
                            '<a class="nav-link active">User</a>\n' +
                        '</li>' +
                    '</ul>'
            }
            $('#navAdminUser').append(navRoleTabs);
        });
}


if ($("#userInfo")[0]) {
    showUserInfo();
}
if ($(".tbody")[0]) {
    drawTable();
}
if ($(".userPage")[0]) {
    drawUserPage();
}

if ($("#navAdminUser")[0]) {
    checkIfAdmin();
}


function fillAdminPage() {
    let adminPage = '';
    $("#adminPage").html("");
    if ($('#usersTableNav').hasClass('active')) {
        adminPage = ' <table class="table border-top-2 bg-white" id = "adminPageTable" align="left">\n' +
            '                <thead class="mb-3 bg-light">\n' +
            '                <tr>\n' +
            '                    <th class="fw-normal fs-4">All users</th>\n' +
            '                </tr>\n' +
            '                </thead>\n' +
            '                <tbody>\n' +
            '\n' +
            '                <tr>\n' +
            '                    <td colspan="6">\n' +
            '                        <table\n' +
            '                                class="table mt-2 table-striped border-top-2 bg-white"\n' +
            '                                align="left"\n' +
            '                        >\n' +
            '                            <thead class="thead-dark">\n' +
            '                            <tr>\n' +
            '                                <th>User ID</th>\n' +
            '                                <th>Name</th>\n' +
            '                                <th>Last name</th>\n' +
            '                                <th>Age</th>\n' +
            '                                <th>Email</th>\n' +
            '                                <th>Role</th>\n' +
            '                                <th>Actions with user</th>\n' +
            '                            </tr>\n' +
            '                            </thead>\n' +
            '                            <tbody class="tbody" id="tbody">\n' +
            '                            </tbody>\n' +
            '                        </table>\n' +
            '                    </td>\n' +
            '                </tr>\n' +
            '                </tbody>\n' +
            '            </table>'
        $("#adminPage").append(adminPage);
        drawTable();
    } else {
        adminPage = '<form class="addForm" action="#" id="addUserForm" method="post">' +
            ' <table class="table border-top-2 bg-white" id = "addUserTable" align="left">\n' +
            '                <thead class="mb-3 bg-light">\n' +
            '                    <tr>\n' +
            '                        <th class="fw-normal fs-4">\n' +
            '                            Add new user\n' +
            '                        </th>\n' +
            '                    </tr>\n' +
            '                    </thead>\n' +
            '                    <tbody align="center">\n' +
            '<form class="form-check-input align-items-center min-vw-100">\n' +
            '                    <tr>\n' +
            '                        <td>\n' +

            '                                <div class="form-group align-items-center">\n' +
            '                                    <label for="username">First Name</label>\n' +
            '                                    <div class="col-sm-5">\n' +
            '                                        <input type="text"\n' +
            '                                               class="form-control form-control-sm w-30" name="username" id="username">\n' +
            '\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                                <div class="form-group align-items-center">\n' +
            '                                    <label for="surName">Last name</label>\n' +
            '                                    <div class="col-sm-5">\n' +
            '                                        <input type="text"\n' +
            '                                               class="form-control form-control-sm w-30" name="surName" id="surName">\n' +
            '\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                                <div class="form-group align-items-center">\n' +
            '                                    <label for="age">Age</label>\n' +
            '                                    <div class="col-sm-5">\n' +
            '                                        <input type="text"\n' +
            '                                               class="form-control form-control-sm w-30" name="age" id="age">\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                                <div class="form-group align-items-center">\n' +
            '                                    <label for="email">Email</label>\n' +
            '                                    <div class="col-sm-5">\n' +
            '                                        <input type="text"\n' +
            '                                               class="form-control form-control-sm w-30" name="email" id="email">\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                                <div class="form-group align-items-center">\n' +
            '                                    <label for="password">Password</label>\n' +
            '                                    <div class="col-sm-5">\n' +
            '                                        <input type="password"\n' +
            '                                               class="form-control form-control-sm w-30"\n' +
            '                                               name="password"\n' +
            '                                               id="password">\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                                <div class="form-group">\n' +
            '                                    <label for="roles">Role</label>\n' +
            '                                    <div class="col-sm-5">\n' +
            '                                        <select multiple class="form-control form-control-sm w-30" size="2" name="roles"\n' +
            '                                                id="roles" required>\n' +
            '                                            <option value="ROLE_USER">User</option>\n' +
            '                                            <option value="ROLE_ADMIN">Admin</option>\n' +
            '                                        </select>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +

            '                        </td>\n' +
            '                    </tr>\n' +
            '\n' +
            '                    <tr>\n' +
            '                        <td>\n' +
            '                            <div class="d-grid gap-2 d-md-block align-items-center">\n' +
            '                                <button class="btn btn-info" type="submit"">Add new user</button>\n' +
            '                            </div>\n' +
            '                        </td>\n' +
            '                    </tr>\n' +
            '</form>'+
            '                    </tbody>\n' +
            '                </table>' +
            '</form>'

        $("#adminPage").append(adminPage);

        const form = document.querySelector('form');
        form.addEventListener('submit', handleSaveSubmit);
    }
}


if ($("#navsUsersTableAndAddNewUser")[0]) {
    $("#navsUsersTableAndAddNewUser").ready(function () {
        $('#usersTableNav').addClass('active');
        fillAdminPage();
    })

    $('#usersTableNav').on('click', function() {
        $('#usersTableNav')
            .removeClass('active')
            .filter(this)
            .addClass('active');
        $('#addNewUserNav').removeClass('active')
        fillAdminPage();
    });
    $('#addNewUserNav').on('click', function() {
        let adminPage = '';
        $("#adminPage").html("");
        $('#addNewUserNav')
            .removeClass('active')
            .filter(this)
            .addClass('active');
        $('#usersTableNav').removeClass('active')
        fillAdminPage();
    });
}


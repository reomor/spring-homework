let form = $("#authorForm");
let modal = $("#authorModal");
let ajaxUrl = "/rest/authors";

$(function () {
    ajaxGetAll();
});

function ajaxGetAll() {
    $.get(ajaxUrl,
        function (data) {
            let table = $("#tableBody");
            table.empty();
            $.each(data, function (key, value) {
                let author = "<tr><td>" + value.name + " " + value.sername + "</td>" +
                    "<td>" + value.dateOfBirth + "</td>" +
                    "<td>" + value.biography + "</td>" +
                    "<td><div class=\"btn-group mr-2\" role=\"group\" aria-label=\"Action group\">" +
                    "<button type='button' class='btn btn-primary' onclick='ajaxGet(\"" + value.id + "\");'>Edit</button>" +
                    "<button type='button' class='btn btn-secondary' onclick='ajaxDelete(\"" + value.id + "\");'>Delete</button>" +
                    "</div></td></tr>";
                table.append(author);
            });
        });
}

function ajaxGet(id) {
    $("#authorModalLabel").html("Edit author");
    $.get(ajaxUrl + "/" + id,
        function (data) {
            $.each(data, function (key, value) {
                form.find("input[name='" + key + "']").val(value);
            });
        }).done(function () {
        modal.modal();
    });
}

function ajaxDelete(id) {
    $.ajax({
        url: ajaxUrl + "/" + id,
        type: "DELETE"
    }).done(function () {
        location.reload(true);
    });
    ajaxGetAll();
}

function ajaxSave() {
    let url = ajaxUrl;
    let type = "POST";
    let formData = {
        id: $("#inputAuthorId").val(),
        name: $("#inputAuthorName").val(),
        sername: $("#inputAuthorSername").val(),
        dateOfBirth: $("#inputAuthorDateOfBirth").val(),
        biography: $("#inputAuthorBiography").val()
    };

    if (formData.id !== "") {
        url += "/" + formData.id;
        type = "PUT";
    }

    $.ajax({
        url: url,
        type: type,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(formData)
    }).done(function () {
        modal.modal("hide");
        location.reload(true);
    });
}

function addNew() {
    $("#authorModalLabel").html("New author");
    form.find(":input").val("");
    modal.modal();
}
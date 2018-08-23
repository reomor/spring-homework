let form = $("#bookForm");
let modal = $("#bookModal");
let ajaxUrl = "/rest/books";

$(function () {
    ajaxGetAll();
});

function ajaxGetAll() {
    $.get(ajaxUrl,
        function (data) {
            let table = $("#tableBody");
            table.empty();
            $.each(data, function (key, value) {
                console.log(key + "|" + value);
                let book = "<tr><td>" + value.title + "</td>" +
                    "<td>" + value.genre.genreName + "</td>" +
                    "<td>" + value.isbn + "</td>" +
                    "<td>" + "" + "</td>" +
                    "<td>" + value.description + "</td>" +
                    "<td><div class=\"btn-group mr-2\" role=\"group\" aria-label=\"Action group\">" +
                    "<button type='button' class='btn btn-primary' onclick='ajaxGet(\"" + value.id + "\")'>Edit</button>" +
                    "<button type='button' class='btn btn-secondary' onclick='" + value.id + "'>Delete</button>" +
                    "</div></td></tr>";
                table.append(book);
            });
        });
}

function ajaxGet(id) {
    $("#bookModalLabelModalLabel").html("Edit book");
    $.get(ajaxUrl + "/" + id,
        function (data) {
            $.each(data, function (key, value) {
                console.log(value);
                form.find("input[name='" + key + "']").val(value);
            });
        }).done(function () {
        modal.modal();
    });
}
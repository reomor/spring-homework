let form = $("#bookForm");
let modal = $("#bookModal");
let ajaxUrl = "/rest/books";
let authorsAjaxUrl = "/rest/authors";

$(function () {
    ajaxGetAll();
});

function ajaxGetAll() {
    $.get(ajaxUrl,
        function (data) {
            let table = $("#tableBody");
            table.empty();
            $.each(data, function (key, value) {
                let authors = "";
                $.each(value.authors, function (k, v) {
                    authors += v.name + " " + v.sername + "<br/>";
                });

                let book = "<tr><td>" + value.title + "</td>" +
                    "<td>" + value.genre.genreName + "</td>" +
                    "<td>" + value.isbn + "</td>" +
                    "<td>" + authors + "</td>" +
                    "<td>" + value.description + "</td>" +
                    "<td><div class=\"btn-group mr-2\" role=\"group\" aria-label=\"Action group\">" +
                    "<button type='button' class='btn btn-primary' onclick='ajaxGet(\"" + value.id + "\")'>Edit</button>" +
                    "<button type='button' class='btn btn-secondary' onclick='ajaxDelete(\"" + value.id + "\"'>Delete</button>" +
                    "</div></td></tr>";
                table.append(book);
            });
        });
}

function ajaxGet(id) {
    $("#bookModalLabel").html("Edit book");
    $.get(ajaxUrl + "/" + id,
        function (data) {
            let book = data.book;
            let authorList = data.authorList;
            let authorIds = data.authorIds;
            $.each(book, function (key, value) {
                switch (key) {
                    case 'genre':
                        form.find("input[name='genreName']").val(value.genreName);
                        form.find("input[name='genreDescription']").val(value.genreDescription);
                        break;
                    case 'authors':
                        let select = $("#authors-select");
                        select.empty();
                        $.each(authorList, function (k, v) {
                            if (v.id) {
                                select.append(
                                    $("<option></option>")
                                        .attr("value", v.id)
                                        .attr("selected", authorIds.includes(v.id))
                                        .text(v.name + " " + v.sername)
                                );
                            }
                        });
                        break;
                    case 'comments':
                        $("#commentsTableBody").empty();
                        $.each(value, function (k, v) {
                            let comment = "<tr><td>" + v.date + "</td>" +
                                "<td>" + v.commentBody + "</td></tr>";
                            $("#commentsTableBody").append(comment);
                        });
                        break;
                    default:
                        form.find("input[name='" + key + "']").val(value);
                }
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
        location.reload(true)
    });
    ajaxGetAll();
}

function ajaxSave() {
    let url = ajaxUrl;
    let type = "POST";
    let formData = {
        book: {
            id: $("#inputBookId").val(),
            title: $("#inputBookTitle").val(),
            genre: {
                genreName: $("#inputBookGenreName").val(),
                genreDescription: $("#inputBookGenreDescription").val()
            },
            isbn: $("#inputBookIsbn").val(),
            description: $("#inputBookDescription").val(),
        },
        authorList: [],
        authorIds: $("#authors-select").val()
    };

    if (formData.book.id !== "") {
        url += "/" + formData.book.id;
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
    $("#bookModalLabel").html("New book");
    form.find(":input").val("");
    $.get(authorsAjaxUrl,
        function (data) {
            let select = $("#authors-select");
            $.each(data, function (key, value) {
                select.append(
                    $("<option></option>")
                        .attr("value", value.id)
                        .text(value.name + " " + value.sername));
            });
        });
    modal.modal();
}

function addComment() {
    let id = $("#inputBookId").val();
    if (id === "") {
        return;
    }

    let url = ajaxUrl + "/comment/" + id;
    let formData = {
        commentBody: $("#inputCommentBody").val()
    };

    $.ajax({
        url: url,
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(formData)
    }).done(function () {
        modal.modal("hide");
        location.reload(true);
    });
    ajaxGet(id);
}
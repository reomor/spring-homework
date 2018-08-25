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
                        $.each(authorList, function (k, v) {
                            if (v.id) {
                                select.append(
                                    $("<option></option>")
                                        // .attr("value", v.id)
                                        .attr("value", v.id)
                                        .attr("selected", authorIds.includes(v.id))
                                        .text(v.name + " " + v.sername)
                                );
                            }
                        });
                        break;
                    case 'comments':
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
    console.log(JSON.stringify(formData));
    console.log(url);
    console.log(type);
    $.ajax({
        url: url,
        type: type,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(formData)
        //data: JSON.stringify(form.serializeArray())
    }).done(function () {
        modal.modal("hide");
        location.reload(true);
    });
}

function addNew() {
    $("#bookModalLabel").html("New book");
    form.find(":input").val("");
    modal.modal();
}
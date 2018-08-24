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
                console.log(value);
                $.each(value.authors, function (k, v) {
                    console.log(k + " | " + v);
                    //authors += v.name + " " + v.sername + "<br/>";
                });

                let book = "<tr><td>" + value.title + "</td>" +
                    "<td>" + value.genre.genreName + "</td>" +
                    "<td>" + value.isbn + "</td>" +
                    "<td>" + authors + "</td>" +
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
                switch (key) {
                    case 'genre':
                        form.find("input[name='genreName']").val(value.genreName);
                        form.find("input[name='genreDescription']").val(value.genreDescription);
                        break;
                    case 'authors':
                        let select = $("#authors-select");
                        $.each(value, function (k, v) {
                            if (v.id) {
                                select.append(
                                    $("<option></option>")
                                        .attr("value", v.id)
                                        .text(v.name + " " + v.sername)
                                );
                            }
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

function ajaxSave() {
    let url = ajaxUrl;
    let type = "POST";
    let formData = {
        id: $("#inputBookId").val(),
        authors: $("#authors-select").val()
    }
    console.log(JSON.stringify(formData));
}
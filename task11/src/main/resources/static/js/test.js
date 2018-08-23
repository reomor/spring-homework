let form = $("#authorForm");
var ajaxUrl = "/rest/authors";

$(function () {
    $("#getAll").click(function (event) {
        event.preventDefault();
        ajaxGetAll();
    });
});

function ajaxGetAll() {
    $.get(ajaxUrl,
        function (date) {
            let table = $("#tableBody");
            table.empty();
            $.each(date, function (key, value) {
                // form.find("input[name='" + key + "']").val(value);
                //alert(key + " " + value);
                var obj = JSON.stringify(value);
                console.log(obj);
                var author = "<tr><td>" + value.name + "</td>" + "<td><button type='button' class='btn btn-primary' onclick='ajaxGet(\"" + value.id + "\");'>View</button></td></tr>";
                //console.log(obj);
                table.append(author);
            });
        });
}

function ajaxGet(id) {
    console.log(id);
    $.get(ajaxUrl + "/" + id,
        function (date) {
            $.each(date, function (key, value) {
                console.log(value);
                form.find("input[name='" + key + "']").val(value);
            });
        });
    $("#authorModal").modal();
}

function ajaxSave() {
    console.log(JSON.stringify(form.serializeArray()));
    console.log(form.serialize());
    var formData = {
        name: $("#inputAuthorName").val(),
        sername: $("#inputAuthorSername").val(),
        dateOfBirth: $("#inputAuthorDateOfBirth").val(),
        biography: $("#inputAuthorBiography").val()
    };
    console.log(formData);
    $.ajax({
        url: ajaxUrl,
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(formData)
        //data: JSON.stringify(form.serializeArray())
    }).done(function () {
        $("#authorModal").modal("hide");
    });
}


/*function ajaxSave() {
    console.log(JSON.stringify(form.serializeArray()));
    var array = form.serializeArray();
    var json = {};
    $.each(array, function () {
       json[this.name] = this.value || '';
    });
    console.log(json);
    $.ajax({
        url: ajaxUrl,
        type: "POST",
        contentType: "application/json",
        data: form.serialize(),
        dataType: "json"
    }).done(function () {
        $("#authorModal").modal("hide");
    });
}*/

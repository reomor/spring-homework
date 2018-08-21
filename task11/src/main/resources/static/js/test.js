let form = $("#authorForm");
let table = $("#tableBody");
var ajaxUrl = "/rest/authors";

$(function () {
    $("#getAll").click(function (event) {
        event.preventDefault();
        ajaxGetAll();
    })
});

function ajaxGetAll() {
    $.get(ajaxUrl,
        function (date) {
        table.empty();
            $.each(date, function (key, value) {
                // form.find("input[name='" + key + "']").val(value);
                //alert(key + " " + value);
                var obj = JSON.stringify(value);
                console.log(obj);
                var author = "<tr><td>" + value.name + "</td>" + "<td><button type='button' class='btn btn-primary' onclick='ajaxGet(\"" + value.id +"\");'>View</button></td></tr>";
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
                // form.find("input[name='" + key + "']").val(value);
                console.log(value);
            });
        });

    $("#authorModal").modal();
}
$(document).ready(function () {
    $("#btn1").click(function () {
        $("#test1").text("Hello world!");
    });
    $("input").keyup(function () {
        var value1, value2, value3;
        value1 = $("#va1").val();
        value2 = $("#va2").val();
        value1 = parseInt(value1);
        value2 = parseInt(value2);
        value3 = value1 * value2;
        $("#result").text(value3);
    });
});

$(function () {
    $('#learnBtn').click(function () {
        $.ajax({
            "url": "../info",
            "timeout": 2000,
//            "beforeSend": function () {
//                $('#info').text("Requesting new random value...");
//            },
            "success": function (data, textStatus, jqXHR) {
                $('#randomView').text(textStatus + ': ' + data);
            },
            "error": function (jqXHR, textStatus, errorThrown) {
                $('#randomView').text(textStatus + ':' + errorThrown);
            },
//            "complete": function (jqXHR, textStatus) {
//                $('#info').text("Request was completely done with status: " + textStatus);
//            }
        });
    });
     $('#secondBtn').click(function () {
            $.ajax({
                "url": "../product/get",
                "timeout": 2000,
    //            "beforeSend": function () {
    //                $('#info').text("Requesting new random value...");
    //            },
                "success": function (data, textStatus, jqXHR) {
                    $('#randomView').text(textStatus + ': ' + data);
                },
                "error": function (jqXHR, textStatus, errorThrown) {
                    $('#randomView').text(textStatus + ':' + errorThrown);
                },
    //            "complete": function (jqXHR, textStatus) {
    //                $('#info').text("Request was completely done with status: " + textStatus);
    //            }
            });
        });
});
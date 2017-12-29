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
                    var json = $.parseJSON(data);
                    var code = json.code;
                    var descr = json.description;
                    alert(code);
                    console.log(code);
                    $('#prodCode').text(code);
                    $('#prodDescr').text(descr);
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
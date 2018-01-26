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
});
$(function() {
    var pltk = document.getElementById('plitka');
    var dtls = document.getElementById('details');
    dtls.style.display = "none";
    $('#catalogReturn').click(function(){
        pltk.style.display="block";
        dtls.style.display="none";
    });
     $('#secondBtn').click(function () {
        console.log('second button click event')
        pltk.style.display = "none";
        dtls.style.display = "block";
//            $.ajax({
//                "url": "../product/get",
//                "timeout": 2000,
//    //            "beforeSend": function () {
//    //                $('#info').text("Requesting new random value...");
//    //            },
//                "success": function (data, textStatus, jqXHR) {
//                    var json = $.parseJSON(data);
//                    var code = json.code;
//                    var descr = json.description;
//                    alert(code);
//                    console.log(code);
//                    $('#prodCode').text(code);
//                    $('#prodDescr').text(descr);
//                },
//                "error": function (jqXHR, textStatus, errorThrown) {
//                    $('#randomView').text(textStatus + ':' + errorThrown);
//                },
//    //            "complete": function (jqXHR, textStatus) {
//    //                $('#info').text("Request was completely done with status: " + textStatus);
//    //            }
//            });
        });
});
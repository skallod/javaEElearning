$(function () {
    console.log("ready");
    $("#form").submit(function (e) {
        console.log("submit");
        var postData = $(this).serializeArray();
        var formURL = $(this).attr("action");
        $.ajax(
            {
                url: formURL,
                type: "POST",
                data: postData,
                success: function (data, textStatus) {
                    console.log("success", data, textStatus);
                    $(".err").text("");
                    if (!data.text && $.isArray(data)) {
                        data.forEach(function (key) {
                            console.log("key=", key);
                            $("#err-" + key.field).text(key.defaultMessage);
                        });
                    } else{
                         $("#contents").text(data.text);
                    }
                    //todo create cookie
                    window.location.href = '/pages/common/catalog.html';
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("error", jqXHR, textStatus, errorThrown)
                }
            });
        e.preventDefault();
    });
});
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
                         var login_obj = data.name;
                         console.log("nm ",login_obj)
                         //$("#contents").text(data.text);
                         Cookies.set('user_name', login_obj);
                         window.location.href = '/pages/common/catalog.html';
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("error", jqXHR, textStatus, errorThrown)
                }
            });
        e.preventDefault();
    });
});
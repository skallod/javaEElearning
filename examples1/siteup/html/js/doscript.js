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
$(function () {
    var pltk = document.getElementById('plitka');
    var dtls = document.getElementById('details');
    dtls.style.display = "none";
    $('#catalogReturn').click(function () {
        pltk.style.display = "block";
        dtls.style.display = "none";
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
$(function () {
    var testj = document.getElementById('prodCode');
    function loadData(dataUrl, target) {
        console.log("start");
        var xhr = new XMLHttpRequest();
        xhr.overrideMimeType("application/json");
        xhr.open('GET', dataUrl, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    //parse jsoon data
                    var products = JSON.parse(xhr.responseText).products;
                    //var code = products[0].code;
                    //console.log("code="+code);
                    console.log("success");
                    for (var i = 0; i < products.length; i++) {
                        console.log("code="+products[i].code);
                        console.log("price="+products[i].price);
                        testj.innerHTML = products[i].code;
                    }


                } else {
                    console.log(xhr.statusText);
                    
                }
            }
        }
        xhr.send();
    }

    // Load the countries and states using XHR
    console.log("before sending");
    loadData('../../data/products.json', testj);
});

$(function () {
    $.ajax({
        data: "",
        method: "GET",
        url: "getuser",
        timeout: 2000,
        error: function() {
            console.error("blah to get ajax response");
        },
        success: function(response) {
            $(".username").text(response);
        }
    });
});
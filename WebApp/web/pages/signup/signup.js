$(function () {
    $("form.form-signup").submit(function () {
        var parameters = $(this).serialize();

        $.ajax({
            data: parameters,
            url: this.action,
            timeout: 2000,
            error: function () {
                console.log("Error");
            },
            success: function (resp) {
                if (resp === "home.html" || resp === "signup.html") {
                    window.location.assign(resp);
                } else {
                    $(".error").text(resp);
                }
            }
        });
        return false
    })
});
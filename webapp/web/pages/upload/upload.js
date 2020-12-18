$(function () {
    $("form.form-upload").submit(function () {
        var formData = new FormData(this);
        $.ajax({
            method: "POST",
            data: formData,
            url: this.action,
            processData: false,
            contentType: false,
            timeout: 5000,
            error: function () {
                $(".error").text("Failed to upload - AJAX error.");
                console.log("AJAX error.");
            },
            success: function (resp) {
                if (resp === "home.html") {
                    displaySuccessMessage();
                } else {
                    $(".error").text(resp);
                }
            }
        });
        return false
    })
});

$(function() {
    $("#success-modal-button").click(function() {
        window.location.assign("home.html");
    })
});

function displaySuccessMessage() {
    var message = "Map uploaded successfully."
    $(".success-modal-body").text(message);
    $("#success-modal").modal("show");
}
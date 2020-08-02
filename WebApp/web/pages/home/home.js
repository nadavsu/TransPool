//On-load get the user and on success loading the user card.
$(getUser(loadUserCard));

//On-load get the maps and load DTOs to cards UI.
$(function () {
    $.ajax({
        method: "POST",
        url: "get-map-details",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        success: function (maps) {
            if (maps.length === 0) {
                $(".card-columns").append(
                    $("<h2>")
                        .text("No maps uploaded (yet).")
                )
            } else {
                $.each(maps || [], loadMap);
                initializeOpenMapSubmit();
            }
        }
    })
});

//Loading map cards---------------------------------------------------------------------------------------------------//
// map = {mapName: "" ; uploaderName: "" ; numOfTripOffers: "" ; numOfTripRequests: "" ; numOfMatchedRequests: "" ; numOfStops: "", numOfPaths: ""}
function loadMap(index, map) {
    var mapCard =
        $("<div class='card map-card'>")
            .append(
                $("<img class='card-img-top' src='common/images/sample_map.png' alt='Map'>")
            ).append(
            $("<div class='card-body'>")
                .append(
                    $("<h4 class='card-title map-name'>")
                        .text(map.mapName)
                ).append(
                $("<p class='card-text num-of-offers'>")
                    .text(map.numOfTripOffers + " Ride offers.")
            ).append(
                $("<p class='card-text num-of-requests'>")
                    .text(map.numOfTripRequests + " Ride requests, " + map.numOfMatchedRequests + " are matched.")
            ).append(
                $("<p class='card-text stops-paths'>")
                    .text(map.numOfStops + " Stops, " + map.numOfPaths + " paths")
            ).append(
                createOpenMapForm(map.mapName)
            )
        ).append(
            $("<div class='card-footer'>")
                .append(
                    $("<small class='text-muted'>")
                        .text("Uploaded by " + map.uploaderName)
                )
        );

    $(".card-columns").append(mapCard);
}

function createOpenMapForm(mapName) {
    return $("<form class='open-map' method='POST' action='get-map-type'>")
        .append(
            $("<input type='hidden' name='map-name' id='map-name'>")
                .attr("value", mapName)
        )
        .append(
            $("<button type='submit' class='btn btn-primary'>")
                .text("Open")
        );
}


//Loading user card---------------------------------------------------------------------------------------------------//
// user = {ID: "" ; username: "" ; balance: "" ; transactionHistory: {date: "{}" ; transactionType: "" ; amount: ""}}
function loadUserCard(user) {
    var lastThreeTransactions = user.transactionHistory.slice(Math.max(user.transactionHistory.length - 3, 0));
    $.each(lastThreeTransactions || [], loadRecentTransactions);
}

// transaction = {"date":{"time":{"hour":0,"minute":0,"second":0,"nano":0},"day":1},"type":"PAY","transactionAmount":200.0}
function loadRecentTransactions(index, transaction) {
    var transactionHour = checkTime(transaction.date.time.hour);
    var transactionMinute = checkTime(transaction.date.time.minute);
    var img = checkTransactionType(transaction.type);

    var newTransaction = $("<li class='list-group-item transaction'>")
        .append(
            $("<div>")
                .append(
                    $("<h6 class='transaction-date'>")
                        .text("Day " + transaction.date.day + " at " + transactionHour + ":" + transactionMinute)
                        .append("<br>")
                        .append(
                            $("<img class='transaction-type'>")
                                .attr("src", img)
                        ).append(
                            $("<small class='text-muted transaction-amount'>")
                                .text(" $" + transaction.transactionAmount)
                        ).append(
                            $("<br>")
                        ).append(
                            $("<p class='small text-muted amount-before'>")
                                .text("Amount before: $" + transaction.amountBefore)
                        ).append(
                            $("<p class='small text-muted amount-after'>")
                                .text("Amount after: $" + transaction.amountAfter)
                        )
                )
        );

    $(".last-transactions").append(newTransaction);
}

//Loading map on click------------------------------------------------------------------------------------------------//
//On-submit when opening a map

function initializeOpenMapSubmit() {
    $("form.open-map").submit(function () {
        var parameters = $(this).serialize();
        $.ajax({
            data: parameters,
            url: this.action,
            timeout: 2000,
            error: function () {
                console.log("AJAX Error");
            },
            success: function (resp) {
                window.location.assign(resp);
            }
        });
        return false;
    });
}
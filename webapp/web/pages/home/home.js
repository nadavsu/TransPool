var refreshRate = 2000; //ms
var feedbacksVersion = 0;
var ridersVersion = 0;

var DRIVER_TYPE = "driver";
var RIDER_TYPE = "rider";
var CURRENT_USER_TYPE;

var RIDER_IMAGE = "common/images/cards/request_icon.svg";
var DRIVER_IMAGE = "common/images/cards/offer_icon.svg";
var MATCH_IMAGE = "common/images/cards/match_icon.svg";



//On-load get the user and on success loading the user card.
$(function () {
    getUser(loadUserCard);
    getDriverFeedbacks(function (feedback) {
        feedbacksVersion = feedback.version;
    });
    getDriverRiders(function (riders) {
        ridersVersion = riders.version;
    });
    getMapList();
    setInterval(updateHomePage, refreshRate)
});

function updateHomePage() {
    getMapList();
    if (CURRENT_USER_TYPE === DRIVER_TYPE) {
        getDriverFeedbacks(function (feedback) {
            if (feedback.version !== feedbacksVersion) {
                feedbacksVersion = feedback.version;
                $.each(feedback.feedbacks || [], appendFeedbackMessage);
                $("#notification-modal").modal("show");
            }
        });
        getDriverRiders(function (riders) {
            if (riders.version !== ridersVersion) {
                ridersVersion = riders.version;
                riderList = riders.riders;
                $.each(riderList || [], appendRidersMessage);
                $("#notification-modal").modal("show");
            }
        })
    }
}

//On-load get the maps and load DTOs to cards UI.
function getMapList() {
    $.ajax({
        method: "POST",
        url: "get-map-engines",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        success: function (maps) {
            $('#all-maps').empty();
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
}

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
// user = {ID: "" ; userType ; username: "" ; balance: "" ; transactionHistory: {date: "{}" ; transactionType: "" ; amount: ""}}
function loadUserCard(user) {
    CURRENT_USER_TYPE = user.userType;
    $('.username').text(user.username);
    $('.balance').text(user.balance);
    $('ul.last-transactions').empty();
    var lastThreeTransactions = user.transactionHistory.slice(Math.max(user.transactionHistory.length - 3, 0));
    $.each(lastThreeTransactions || [], loadRecentTransactions);

    if (user.userType === DRIVER_TYPE) {
        $('img.user-image').attr("src" ,DRIVER_IMAGE);
    } else if (user.userType === RIDER_TYPE) {
        $('img.user-image').attr("src" ,RIDER_IMAGE);
    } else {
        $('img.user-image').attr("src" ,MATCH_IMAGE);
    }
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
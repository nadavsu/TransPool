$(function() {
    $("#success-modal-button").click(function() {
        location.reload();
    })
});


//Function to load URL parameters into an array.
function getUrlVars() {
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < hashes.length; i++) {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = decodeURI(hash[1]);
    }
    return vars;
}

function initializeNewTripForm(mapName) {
    $('input.map-name').attr("value", mapName);
    $('form.new-trip').submit(function () {
        var parameters = $(this).serialize();
        $.ajax({
            data: parameters,
            url: this.action,
            timeout: 2000,
            error: function () {
                console.log("AJAX Error");
            },
            success: function (resp) {
                $("div.notification-modal-body").text(resp);
                $("#notification-modal").modal("show");
            }
        });
        return false;
    })
}

//----------------------------------------------------------------------------------------------------------------------
//tripOffer = {driverName, departureTime, sourceStopName, destinationStopName, matchedRequestsDetails:[strings], PPK, fuelConsumption, route:[]}
function loadTripOffer(index, tripOffer) {
    var currentOffer =
        $('<button class="list-group-item list-group-item-action offer">')
            .append(
                $('<div class="text-center">')
                    .append(
                        $('<img src="common/images/cards/offer_icon.png">')
                    )
            ).append(
            $('<div>')
                .append(
                    $('<h5 class="trip-offer-driver-name">')
                        .text(tripOffer.driverName)
                ).append(
                    $('<h5 class="trip-offer-title">')
                        .text("Trip from " + tripOffer.sourceStopName + " to " + tripOffer.destinationStopName)
                ).append(
                $('<h6 class="subtitle text-muted">')
                    .text(tripOffer.recurrences + " from " + tripOffer.departureTime)
            ).append(
                $("<br>")
            ).append(
                $('<h5 class="subtitle">')
                    .text("Route:")
            ).append(
                $('<ul class="list-group route-list">')
                    .append(
                        createRouteList(tripOffer.route)
                    )
            ).append(
                $("<br>")
            ).append(
                $('<h5 class="subtitle">')
                    .text("Riders:")
            ).append(
                $('<ul class="list-group riders-list">')
                    .append(
                        createRidersList(tripOffer.matchedRequestsDetails)
                    )
            ).append(
                $('<h6 class="PPK">')
                    .text("PPK: " + tripOffer.PPK)
            ).append(
                $('<h6 class="fuel-consumption">')
                    .text("Fuel Consumption: " + tripOffer.fuelConsumption)
            )
        );

    $('ul.offers-list').append(currentOffer);
}

function createRidersList(arrayOfMatchedRequestDetails) {
    var ridersList = $('<div>');
    var i;
    for (i = 0; i < arrayOfMatchedRequestDetails.length; i++) {
        ridersList.append(
            $('<li class="list-group-item rider">')
                .append('<p>').text(arrayOfMatchedRequestDetails[i])
        );
    }
    return ridersList;
}

//route is an array of strings.
function createRouteList(route) {
    var routeList = $('<div>');
    var i;
    for (i = 0; i < route.length; i++) {
        routeList.append(
            $('<li class="list-group-item stop">')
                .append('<p>').text(route[i])
        );
    }
    return routeList;
}


//----------------------------------------------------------------------------------------------------------------------
//feedback = {averageRating, feedbacks:[{feedbackerID, feedbackerName, rating, comment}]}
function loadFeedbackTab(feedback) {
    $(".averageRating").text(feedback.averageRating);
    var feedbacksList = feedback.feedbacks;
    $.each(feedbacksList || [], loadFeedbackList);
}

function loadFeedbackList(index, feedback) {
    var currentFeedback =
        $('<li class="list-group-item feedback">')
            .append(
                $('<h5 class="feedbacker-name">')
                    .text(feedback.feedbackerName)
            ).append(
            $('<h6 class="subtitle text-muted rating">')
                .text(feedback.rating + "/5 stars")
        ).append(
            $('<p class="comment">')
                .text(feedback.comment)
        );
    $('ul.user-feedbacks').append(currentFeedback);
}

//----------------------------------------------------------------------------------------------------------------------
//tripRequest = {riderName, sourceStopName, destinationStopName, requestTime:string}
function loadTripRequest(index, tripRequest) {
    var currentRequest =
        $("<button class='list-group-item list-group-item-action request'>")
            .append(
                $('<div class="text-center">')
                    .append(
                        $('<img src="common/images/cards/request_icon.png">')
                    )
            ).append(
            $('<div>')
                .append(
                    $('<h5 class="trip-request-title">')
                        .text(tripRequest.riderName)
                ).append(
                $('<h6 class="subtitle text-muted">')
                    .text("Gets on " + tripRequest.sourceStopName)
            ).append(
                $('<h6 class="subtitle text-muted">')
                    .text("Gets off " + tripRequest.destinationStopName)
            ).append(
                $('<p class="requestTime">')
                    .text("Requested time of departure: " + tripRequest.requestTime)
            ).append(
                $('<a href="#" class="btn btn-primary">View</a>\n')
            )
        );

    $('ul.requests-list').append(currentRequest);
}

//----------------------------------------------------------------------------------------------------------------------
//matchedTrip = {riderName, sourceStopName, destinationStopName, departureTime:{}, arrivalTime{}, routeDescription:[], tripPrice, averageFuelConsumption}
function loadMatchedTrip(index, matchedTrip) {
    var currentMatch =
        $("<button class='list-group-item list-group-item-action match'>")
            .append(
                $('<div class="text-center">')
                    .append(
                        $('<img src="common/images/cards/match_icon.png">')
                    )
            ).append(
            $('<div>')
                .append(
                    $('<h5 class="match-title">')
                        .text(matchedTrip.riderName)
                ).append(
                $('<h6 class="subtitle text-muted">')
                    .text("Gets on " + matchedTrip.sourceStopName)
            ).append(
                $('<h6 class="subtitle text-muted">')
                    .text("Gets off " + matchedTrip.destinationStopName)
            ).append(
                $('<p class="request-time">')
                    .text("Departure time: " + matchedTrip.departureTime)
            ).append(
                $('<p class="arrival-time">')
                    .text("Arrival time: " + matchedTrip.arrivalTime)
            ).append(
                $('<h5 class="subtitle">')
                    .text("Route description:")
            ).append(
                $('<ul class="list-group route-description">')
                    .append(
                        createRouteList(matchedTrip.routeDescription)
                    )
            ).append(
                $('<h6 class="subtitle">')
                    .text("Total price: " + matchedTrip.totalPrice)
            ).append(
                $('<p class="small">')
                    .text("Congratulations! By travelling with TransPool you have saved " + matchedTrip.averageFuelConsumption + " litres of fuel!")
            )
                .append(
                    $('<a href="#" class="btn btn-primary">View</a>\n')
                )
        );

    $('ul.matches-list').append(currentMatch);

}


//----------------------------------------------------------------------------------------------------------------------

function loadStops(index, stopName) {
    var stop =
        $('<button class="list-group-item list-group-item-action stop-name-list-item">')
            .text(stopName);
    $('ul.stops-list').append(stop);
}

//----------------------------------------------------------------------------------------------------------------------
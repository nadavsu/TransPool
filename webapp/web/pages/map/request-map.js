var mapName;
var refreshRate = 3000;
$(function () {
    mapName = getUrlVars()["map-name"];
    $('title.page-title').text("TransPool - " + mapName);
    initializeRequestMapPage();
    setInterval(updateRequestMapPage, refreshRate);
});

function updateRequestMapPage() {
    getMapTripOffers()
}

function initializeRequestMapPage() {
    $.ajax({
        data: {"map-name": mapName},
        method: "POST",
        url: "get-request-map",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        //Resp:
        //[userTripRequestsJson, userMatchedTripRequestsJson, mapTripOffersJson, userFeedbacksJson, userTripRequestsFromMapJson]
        success: function (resp) {
            generateMap(resp[0]);
            $.each(resp[1] || [], loadTripRequest);         //Loading the trip requests of the user.
            $.each(resp[2] || [], loadMatchedTrip);         //Loading the matched trips of the user.
            $.each(resp[3] || [], loadTripOffer);           //Loading trip offers in the right tab pane.
            $.each(resp[4] || [], loadFeedbacksForm);       //Loading the select values in the feedback form
            $.each(resp[5] || [], loadFindAMatchForm);      //Loading the select values in the matching form.
            initializeNewTripForm(mapName);
            initializeForm($('form.leave-feedback'));
            initializeFindMatchesForm();
        }
    })
}

//----------------------------------------------------------------------------------------------------------------------

function getMapTripOffers() {
    $.ajax({
        data: {'map-name': mapName},
        url: 'get-map-offers',
        timeout: 2000,
        error: function() {
            console.error('Failed to get ajax response')
        },
        //Resp - List<TripOfferDTO>
        success: function(resp) {
            $('ul.offers-list').empty();
            $.each(resp || [], loadTripOffer);
        }
    })
}

function initializeFindMatchesForm() {
    $('form.find-a-match').submit(function() {
        var parameters = $(this).serialize();
        $('ul.results-list').empty();
        $.ajax({
            method: "POST",
            data: parameters,
            url: this.action,
            timeout: 2000,
            error: function () {
                console.log("AJAX Error");
            },
            //resp = [List<PossibleRouteDTO>, idOfTripRequest]
            success: function (resp) {
                if (resp === "We couldn't find any results for your search :(") {
                    $("div.notification-modal-body").text(resp);
                    $("#notification-modal").modal("show");
                } else {
                    var i;
                    var possibleRoutesList = resp[0];
                    var tripRequestToMatchID = resp[1];
                    for (i = 0; i < possibleRoutesList.length; i++) {
                        showResults(i, possibleRoutesList[i], tripRequestToMatchID);
                    }
                    initializeForm($('form.result-match-form'));
                }
            }
        });
        return false;
    })
}

//Loads the select values (combobox) for the matching form.
function loadFindAMatchForm(index, tripRequest) {
    var option = $('<option>').attr("value", tripRequest.requestID);
    option.text("Trip from " + tripRequest.sourceStopName + " to " + tripRequest.destinationStopName + " on " + tripRequest.requestTime);
    $('select.requests-to-match-select').append(option);
}

//Loads the select values (combbox) for the feedback form
//Feedbackee is the username of the feedbackee.
function loadFeedbacksForm(index, feedbackee) {
    var option = $('<option>').attr("value", feedbackee);
    option.text(feedbackee);
    $('select.feedbackees-select').append(option);
}

//----------------------------------------------------------------------------------------------------------------------

//possibleRoute = {route:[string], totalPrice, averageFuelConsumption, totalTripDuration, arrivalTime, DepartureTime
function showResults(index, possibleRoute, tripRequestToMatchID) {
    var result =
        $('<li class="list-group-item result">')
            .append(
                $('<h5>').text("Route description")
            ).append(
                $('<ul class="list-group-flush route-description">')
                    .append(
                        createRouteList(possibleRoute.route)
                    )
            ).append(
                $('<br><p class="total-price text-bold">')
                    .text("Total price: $" + possibleRoute.totalPrice)
            ).append(
                $('<p class="average-fuel-consumption">')
                    .text("Average fuel consumption: " + possibleRoute.averageFuelConsumption)
            ).append(
                $('<p class="trip-duration">')
                    .text("Trip duration in minutes: " + possibleRoute.totalTripDuration)
            ).append(
                $('<h6 class="departure-time subtitle">')
                    .text("Depart on " + possibleRoute.departureTime)
            ).append(
                $('<h6 class="arrival-time subtitle">')
                    .text("Arrive on " + possibleRoute.arrivalTime)
            ).append(
                createResultMatchTripForm(index, tripRequestToMatchID)
                );
    $('ul.results-list').append(result);
}

function createResultMatchTripForm(index, tripRequestToMatchID) {
    return $('<form class="result-match-form" method="GET" action="create-match">')
        .append(
            $('<input type=hidden name="possible-route-id">')
                .attr("value", index)
        ).append(
        $('<input type=hidden name="request-to-match">')
            .attr("value", tripRequestToMatchID)
    ).append(
        $('<input type=hidden name="map-name">')
            .attr("value", mapName)
    )
        .append(
            $('<button class="btn btn-md btn-primary btn-block" type="submit">')
                .text("Choose ride")
        )

}
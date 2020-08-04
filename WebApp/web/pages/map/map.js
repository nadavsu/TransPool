$(function() {
    $("#notification-modal-button").click(function() {
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

function initializeForm(form) {
    form.submit(function() {
        var parameters = $(this).serialize();
        $.ajax({
            method: this.method,
            data: parameters,
            url: this.action,
            timeout: 2000,
            error: function() {
                console.log("AJAX Error");
            },
            success: function(resp) {
                $("div.notification-modal-body").text(resp);
                $("#notification-modal").modal("show");
            }
        });
        return false;
    })
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
//mapDetails = List<StopDTO> allStops, List<PathDTO> allPaths, width, length
function generateMap(mapDetails) {
    var widthMultiplier = 600 / mapDetails.width;
    var lengthMultiplier = 575 / mapDetails.length;
    var allStops = mapDetails.allStops;
    var allPaths = mapDetails.allPaths;

    for (var j = 0; j < allPaths.length; j++) {
        loadPath(allPaths[j], widthMultiplier, lengthMultiplier);
    }
    for (var i = 0; i < allStops.length; i++) {
        loadStop(allStops[i], widthMultiplier, lengthMultiplier);
    }

}

//Stop - {x, y, stopName}
function loadStop(stop, widthMultiplier, lengthMultiplier) {
    var x = stop.x * widthMultiplier;
    var y = stop.y * lengthMultiplier;
    var stopGroup = $('<g class=stop>').attr('id', stop.stopName);
    var currentStop = stopGroup
            .append(
                $('<circle r="7"/>')
                    .attr({
                        cx: x,
                        cy: y
                    })
            )
            .append(
                $('<text class="stop-label">').attr({
                    x : x + 10,
                    y : y
                }).text(stop.stopName)
            );
    $('svg.map-graph').append(currentStop);
    $("#map-graph").html($("#map-graph").html());
}

//path - x1, y1, sourceName, x2, y2 destinationName
function loadPath(path, widthMultiplier, lengthMultiplier) {
    var x1 = path.x1 * widthMultiplier;
    var y1 = path.y1 * lengthMultiplier;
    var x2 = path.x2 * widthMultiplier;
    var y2 = path.y2 * lengthMultiplier;
    var currPath =
        $('<g class=path>')
            .append(
                $('<line>')
                    .attr({
                        x1: x1,
                        y1: y1,
                        x2: x2,
                        y2: y2,
                        "marker-end" : "url(#arrow)"
                    })
            );
    $('svg.map-graph').append(currPath);
    $("#map-graph").html($("#map-graph").html());
}

//----------------------------------------------------------------------------------------------------------------------
//tripOffer = {driverName, departureTime, sourceStopName, destinationStopName, matchedRequestsDetails:[strings], PPK, fuelConsumption, route:[]}
function loadTripOffer(index, tripOffer) {
    var currentOffer =
        $('<button class="list-group-item list-group-item-action offer">')
            .append(
                $('<div class="text-center">')
                    .append(
                        $('<img src="common/images/cards/offer_icon.svg">')
                    )
            ).append(
            $('<div>')
                .append(
                    $('<h4 class="trip-offer-driver-name">')
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
//tripRequest = {requestID, riderName, sourceStopName, destinationStopName, requestTime:string}
function loadTripRequest(index, tripRequest) {
    var currentRequest =
        $("<button class='list-group-item list-group-item-action request view-request'>")
            .append(
                $('<div class="text-center">')
                    .append(
                        $('<img src="common/images/cards/request_icon.svg">')
                    )
            ).append(
            $('<div>')
                .append(
                    $('<h4 class="trip-request-title">')
                        .text(tripRequest.riderName)
                ).append(
                $('<h6 class="subtitle text-muted request-source-stop">')
                    .text("Gets on " + tripRequest.sourceStopName)
            ).append(
                $('<h6 class="subtitle text-muted request-destination-stop">')
                    .text("Gets off " + tripRequest.destinationStopName)
            ).append(
                $('<p class="request-time">')
                    .text("Requested time of departure: " + tripRequest.requestTime)
            ).append(
                $('<button class="btn btn-primary view-request">').text('View')
            )
        );

    $('ul.requests-list').append(currentRequest);
}

//----------------------------------------------------------------------------------------------------------------------
//matchedTrip = {riderName, sourceStopName, destinationStopName, departureTime:{}, arrivalTime{}, routeDescription:[], tripPrice, averageFuelConsumption}
function loadMatchedTrip(index, matchedTrip) {
    var currentMatch =
        $("<button class='list-group-item list-group-item-action match view-match'>")
            .append(
                $('<div class="text-center">')
                    .append(
                        $('<img src="common/images/cards/match_icon.svg">')
                    )
            ).append(
            $('<div>')
                .append(
                    $('<h5 class="match-title match-rider-name">')
                        .text(matchedTrip.riderName)
                ).append(
                $('<h6 class="subtitle text-muted match-source-stop">')
                    .text("Gets on " + matchedTrip.sourceStopName)
            ).append(
                $('<h6 class="subtitle text-muted match-destination-stop">')
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
                    .text("Total price: $" + matchedTrip.tripPrice)
            ).append(
                $('<p class="small">')
                    .text("Congratulations! By travelling with TransPool you have saved " + matchedTrip.averageFuelConsumption + " litres of fuel!")
            )
                .append(
                    $('<button class="btn btn-primary view-match">').text('View')
                )
        );

    $('ul.matches-list').append(currentMatch);

}


//----------------------------------------------------------------------------------------------------------------------


function getMap() {
    $.ajax({
        data: {'map-name': mapName},
        url: 'get-map',
        timeout: 2000,
        error: function() {
            console.error('Failed to get ajax response')
        },
        //Resp - BasicMapDTO
        success: function(resp) {
            generateMap(resp);
        }
    })
}
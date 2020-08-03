var mapName;
$(function() {
    mapName = getUrlVars()["map-name"];
    $('title.page-title').text("TransPool - " + mapName);
    initializeOfferMapPage()

});

function initializeOfferMapPage() {
    $.ajax({
        data: {"map-name": mapName},
        method: "POST",
        url: "get-offer-map",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        //Resp:
        //[mapDetails, userTripOffersJson, allFeedbacksJson, mapTripRequestsJson, mapMatchedTripsJson]
        success: function (resp) {
            generateMap(resp[0]);
            $.each(resp[1] || [], loadTripOffer);
            $.each(resp[3] || [], loadTripRequest);
            $.each(resp[4] || [], loadMatchedTrip);
            loadFeedbackTab(resp[2]);
            initializeNewTripForm(mapName);
        }
    })
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


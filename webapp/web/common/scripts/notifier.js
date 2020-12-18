function getDriverFeedbacks(onSuccess) {
    $.ajax({
        url: 'get-driver-feedbacks',
        data: {'feedback-version' : feedbacksVersion},
        timeout: 2000,
        error: function () {
            console.error('Failed to get ajax response from get-driver-feedbacks')
        },
        //Resp - FeedbackDTO
        success: function (feedback) {
            onSuccess(feedback);
        }
    })
}

//feedback - feedbackerID, feedbackerName, rating, comment
function appendFeedbackMessage(index, feedback) {
    var message = feedback.feedbackerName + " has left you a feedback!\n" +
        "Rating: " + feedback.rating + "\n" +
        "Comment: " + feedback.comment;

    $('.notification-modal-body').append(
        $('<p>').text(message)
    );
}

function getDriverRiders(onSuccess) {
    $.ajax({
        url: 'get-driver-riders',
        data: {'riders-version' : ridersVersion},
        timeout: 2000,
        error: function() {
            console.error('Failed to get ajax response from get-driver-riders')
        },
        success: function (ridersAndVersion) {
            onSuccess(ridersAndVersion);
        }
    })
}

function appendRidersMessage(index, rider) {
    var message = "Congrats! " + rider.riderUsername + " has joined your ride (ride number " + rider.tripID + ") on " +
        rider.departureTime + "! " +
        rider.riderUsername + " is riding with you from " + rider.sourceStopName + " to " + rider.destinationStopName +
        ". You have made $" + rider.tripPrice + " from this offer."

    $('.notification-modal-body').append(
        $('<p>').text(message)
    );
}


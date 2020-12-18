var PAY = "PAY";
var CREDIT_CHARGE = "CREDIT_CHARGE";
var RECEIVE = "RECEIVE";

function checkTransactionType(type) {
    if (type === PAY) {
        return "common/images/transactions/pay.svg";
    } else if (type === CREDIT_CHARGE) {
        return "common/images/transactions/charge.svg"
    } else if (type === RECEIVE) {
        return "common/images/transactions/receive.svg"
    } else {
        return "";
    }
}

function checkTime(i) {
    return (i < 10) ? "0" + i : i;
}
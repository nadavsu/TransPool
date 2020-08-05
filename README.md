![Title and slogan cropped](https://user-images.githubusercontent.com/53123142/81192618-24fa4e80-8fc3-11ea-9d8e-809b7ee11ac5.png)
TransPool, the ride sharing application. With TransPool, it has never been easier finding someone to ride with for work. Whether you are fed up with public transportation and looking for a way to reduce your commute time or if you want to cover your automobile fees each month. TransPool is the application for you!

# Application Instructions

## Getting Started

After opening Tomcat, navigate to - localhost:8080/TransPool/ to open up the web application, and the landing page should open.

To go straight to the signup page - localhost:8080/TransPool/signup.html

Enter your username, choose your account type and you should be redirected to the TransPool homepage seen below

## The Homepage

Use the navbar on the top page to navigate through the website. You can start by uploading a map to the system by clicking the blue "Upload" button on the top right.

After uploading maps and doing some actions the page should look something like this:

![homepage2](https://user-images.githubusercontent.com/53123142/89461530-cafd5680-d774-11ea-8f3b-a4522b63cd88.png)

### User Card

- A blue circle is the icon for a rider account and a red circle is the icon for a driver account
- Last three transactions and balance can be viewed under the username.
- A button to charge credit to your account is displayed on the bottom of this card, and it will take you to the account page of the user.
- To know more about transactions go to the transactions section in the readme file.

### Map Cards

- Map cards contain basic information about the uploaded map.
- You can open a map by clicking the Open button on the card, which will take you to page two.



## Account Page
![account](https://user-images.githubusercontent.com/53123142/89461613-e23c4400-d774-11ea-8f8a-ce4649bb8584.png)



The account page displays the balance information and all transactions made for the user throughout the system's life.

- You can charge your account with credit by entering an amount and then clicking the 'Charge' button.
- Transaction types are marked with icons
  - ![pay](https://github.com/nadavsu/TransPool/blob/master/WebApp/web/common/images/transactions/charge.svg)Red arrow for **payment**
  - ![receive](https://github.com/nadavsu/TransPool/blob/master/WebApp/web/common/images/transactions/receive.svg)Green arrow for **recieving money**
  - ![charge](https://github.com/nadavsu/TransPool/blob/master/WebApp/web/common/images/transactions/charge.svg)Blue plus sign for **charge**



## Map Page

When clicking 'Open' on a map in the homepage, you will see the map page. This page is different for each user type as each user can do different actions.

### For Drivers

![driver-map](https://user-images.githubusercontent.com/53123142/89461666-f1bb8d00-d774-11ea-8abe-e9ca83b68286.png)

The left side bar shows information about the user's offers, and the user's feedbacks from all the maps. The right side bar shows requests and matches on this map.

**IMPORTANT: To create a new trip offer - the route must be entered as stop names, comma seperated without spaces between the commas!**



### For Riders

![rider-map](https://user-images.githubusercontent.com/53123142/89461721-0861e400-d775-11ea-8d71-f809d5271d9b.png)

The left side-bar shows all information about the current user, while the right side-bar shows all information about the current map (regardless of the user).

#### Left side-bar

In the left side bar, the user can view his ride requests, his upcoming matches, or create a new trip request for the map.

#### Right side-bar

On the right side bar, the user can view the trip offers on this current map. In addition to that, the user can find a match for a request he has made on this map, or feedback a driver he had ridden with in the past.

##### Finding a match

![find-a-match](https://user-images.githubusercontent.com/53123142/89463036-fda84e80-d776-11ea-8a68-4c3a18831595.png)

To find a match, you must choose the request you want to match, choose the amount of matches you want to see and if there are any matches found, then a list of ride descriptions will be shown below. Each ride description has a button 'Match' which will allow you to join the ride.

Once you join a ride, the driver will be notified.

##### Feedbacking

To feedback a user, you must choose the relvant user you want to feedback, add a comment (optional) and rating, and click 'Feedback'. The user will receive a notification about your feedback.

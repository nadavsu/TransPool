#Things That Need To Be Done

##TransPool Trips:
 - Calculate the calculatable fields in TransPoolTrip using the paths in the map
    - *Show all riders (inc. IDs) who have been assigned to the trip.
    - **List of stops of the TransPool trip. Each member of that list should contain the stop name, 
      and the rider getting on/off (Maybe make it a pair of stop and a list of riders? will be constructed
      from the tripRequest?)

 
 ##Matching
   - Create the matching menu.
   - Search for a match.
   
##Scheduling
   - Should be 2 enums of recurrences and day (maybe remove day - check email).
   
   
## Exceptions:
- Check for NPE when trying to add a trip to empty data. 
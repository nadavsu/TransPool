![Title and slogan cropped](https://user-images.githubusercontent.com/53123142/81192618-24fa4e80-8fc3-11ea-9d8e-809b7ee11ac5.png)
##  TransPool - The System

### Running TransPool

- In order to start the application, double click on the file TransPool.bat found in the folder of this readme file.
- Before using the application, a TransPool data file must be loaded to the system. The application will not let you do any other operations before that.
- To upload a TransPool data file, choose 1 in the main menu, then copy the **full** **path** of the file you would like to upload into the application and press ENTER. Make sure that the file type is `.xml`
- After the step above is done, you can start using the TransPool application freely, just follow the instructions in the console.



### Important notes:

- The names of the stops are case sensitive.
- ui.jar and engine.jar need to be in the same folder for the application to work.

--
## Classes Description - Engine Module

![engines](/Volumes/HDD/nadav/Documents/MTA/שנה ב׳/סמסטר ב׳/TransPool/TransPool files/readme/UML/Engines.png)

###  `Engine` Class

- The main engine of the program.
- Contains a static variable of type `TransPoolData` called `data`.
- Handles the functions that deal with the variable `data` such as getting the data (or a part of it) and checking if the data is empty or not.

### `FileEngine` Class

- Extends `Engine`.
- Contains a private data member of type string called `fileDirectory`  which holds the file directory the engine will work on.
- Handles the funcitons that deal with operations on files such as loading a file to the system.

### `MatchingEngine` Class

- Extends `Engine`
- Handles the functions that deal with matching a trip request to a TransPool Trip. Functions include:
  - Finding possible matches.
  - Creating the match and adding it to the system.
  - Updating data in the system after a match.
- Contains 3 data members:
  - The trip request the engine will work on.
  - The list of possible matches found by the engine.
  - The chosen possible match by the user which will be updated after a match.

### `TripEngine` Class

- Extends `Engine`
- Handles the functions that deal with the creation of new trip requests (and TransPool trips in the future).

### `TransPoolData` Class

- Holds all the TransPool data such as:
  - The map.
  - All trip requests.
  - All TransPool trips.
  - All matched trips.



## Classes Description - UI Module

![Package menu](/Users/nadavsuliman/Desktop/Package menu.png)

### `Menu` Class

- Abstract class containing a string member called `menuName`.
- Has the abstract functions `run()` and `show()`
  - `run()` runs the current menu
  - `show()` show's the menu's components such as title, or any other inside menus.

### `OptionedMenu` Class

- Abstract class containing a list of `Menu`s.
- It is a type of menu that contains a list of other `Menu`s. 
- Implements the `run()` function from `Menu`
  - Calls the `getOptionFromUser()` which gets a number from the user.
  - The number is the index of a menu inside the menus list.
  - Calls the `run()` function on the chosen menu.

### `Option` Class

- A plain option which extends `Menu`. 
- Implements the `show()` function as printing the menu to the screen (without the title).
- Implements the `run()` function by just showing the menu.

### `InputMenu` Class

- An abstract class which inherits from `Menu`, 
- Wraps up all the menus which get input from the user.

### Bottom Hierarchy Classes (from the UML above)

- All implement their own `run()` functions, and do the operations using their engine.

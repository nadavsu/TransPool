# User Interface

- Responsible for interacting with the user
- getting user interface
- should be the only module that allows printing to the page.
- gets the user data and passes it onto the engine for processing



# Engine

- Responsible for all the processing that is going around in the program
- The engine is like the control unit in the program - everybody is connected to it and every module relies on it (try not to make it everyone).
- The engine is like a router. Only processing should be done there such as creating new objects (abstract factory?) and such.
- Validation should be done through the engine. So move Validator to the engine.
- File loading is done inside the engine.



# Data

- A module containing all the classes including a class with all the data (like the TransPool class in JAXB).
- The data is not stored in the engine, but the engine will have access to it. (there will be two way reliance, maybe it actually should be a part of the engine?).
- 

# System

- contains the exception (and constants?) relies on the engine.



        stopSet = JAXBMap
                .getStops()
                .getStop()
                .stream()
                .map(Stop::new)
                .collect(Collectors.toSet());
                
        List<data.jaxb.Stop> JAXBStops = JAXBMap.getStops().getStop();
        for (data.jaxb.Stop JAXBStop : JAXBStops) {
            if (!stopSet.add(new Stop(JAXBStop))) {
                throw new StopDuplicationException();
            }
        }
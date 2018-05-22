Partners: Sean Gallagher & David White

Project Description:
Traffic simulation.
Grid (single intersection at first, then scaled) of roads. Customizable spawn rates at each end of road. Cars drive straight accross (possibly more complicated destinations later). Each intersection has stoplight (changes right of way on timer) or stopsign (changes based on cars that are there).

Why MVC:
The model will be the state of all cars/stoplights and update rules for it.
The views: 
    1. The main view will be a graphical depiction of roads, showing cars moving on it in accordance with simulation.
    2. Sidebar table of live stats (throughput at certain places, maybe speed, etc).
    3. Sliders to change car spawn rates for various points -- can be changed in real time.
Controller: Takes the settings decided in view and inputs them to the model. Communicates state of the roads to the view.


Core classes:
    RoadSegment (length of road between intersections)
    Intersection (Potentially split up into two: Stoplight, Stopsign)
    Spawner/Generator
    Endpoint/Despawner/CarCompactor
    (Note: we are going to represent cars just as arrays of 0's, and 1's.)
    




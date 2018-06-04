Partners: Sean Gallagher & David White

=======================
FINAL UPDATE

Everything currently implemented should work well. We also designed a stopsign node to 
be used in place of stoplights. Ideally you would click on a stoplight and switch it
to stopsign and vice versa. Unfortunately we didn't have time to implement this fully.

We also wanted to let you customize the number of roads. In the code it can be scaled by changing
one number in the view and two in the model, but we would have needed to connect the view
changing better to the window size and road lenght and so on, so again we didn't have time
to do that (just more places where we aimed to set up for future expansion of features.

Finally, we realize the sliders on the road map itself are not quite formatted in the right 
place, but we chose to put our last bit of time into cleaning the code rather than fixing that,
as it doesn't inhibit the program all that much.

======================






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
    




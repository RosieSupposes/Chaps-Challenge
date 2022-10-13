# Chaps-challenge

Start the game by running the program through nz.ac.vuw.ecs.swen225.gp22.app.Main. 

Our game is candy land themed. The gingerbread chap player needs to collect all the 
candy jar treasures by collecting lollipop keys to unlock all the doors before he can 
make it through the licorice gate. 

We implemented all required key commands, as well as one extra Ctrl+Q to 
quick save the game (and recording) without exiting. 

Our Recorder can be accessed through the Home Menu under "Replay Game". 
You have the ability to replay the game forward and backwards. Speed is via
the button that is next to the play/pause button. 

When running Fuzz tests on level 1, if Chap manages to win the level,
a pop-up will appear. DO NOT CLICK OFF THE WINDOW. You do not need to click
anything, and if you click off the window robot key presses will mess with 
your other programs. Level 2 will automatically begin eventually. 

When running Fuzz tests on level 2, our Chap has a tendency to die very quickly. 
However, you can use the menubar (New > Level 2) (Or use the key command, Ctrl+2)
while the Fuzz test is still running to re-run Level 2 and keep testing inputs. 
This allows you to test level 2 more thoroughly. 

We made and tested our program using IntelliJ and VSCode. We also made sure 
it works on the ECS machines. 

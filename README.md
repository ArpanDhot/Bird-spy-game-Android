## Drone simulator Java
This drone simulator game has been made using Java and JavaFX.The application employs concepts of Object Oriented Programming to create blueprints for several objects in the game.

The inspiration for the theme came from Commando 2, a fighting game. This theme, in its narrative, powerfully uses binary opposition theory (Levi Strauss) that helps to thicken the plot and further the narrative; and introduce contrast. The game includes the lead drone and its enemies in the form of a plane shooting bullets and the tank launching enemy drones. The theme is further enforced by connotating the enemy with strong war imagery and the good drone with light colour. Furthermore, the object's actions demote their identity, such as the enemies inherited evil actions such as hitting and chasing. In contrast, the good enemy tries to dodge the obstacles incoming in its path. The scene colour scheme is bright colours from the sky and clouds to the pipe. To reinforce the good drone belonging there, they are spawned from a stationary object that, in this instance, is a pipe. On the other side, the enemies are spawned from moving objects such as the tank and the enemy drone. The scene should subconsciously create a sense of good getting invaded by bad.

### Object Oriented Programming implantation overview
The bird spy game comprises many objects with one thing in common their movement. Therefore, I created an abstract Position class containing coordinates and appropriate methods. Making the class abstract was for a reason, to not allow the user to create instances of the Position class but only inherit it and provide the unimplemented methods. Furthermore, inheritance implementation reduced the code repetition between similar objects and made the project more manageable to update from one location. 

The majority of the classes in this project incorporate encapsulation. This concept enabled hiding an object's internal representation, or state, from the outside. For example, the attributes in the bird class are private; therefore, they are only accessible within the class. The only way to access them is by using the public getter and setter methods. Furthermore, it enhances security because programmers cannot manipulate attributes from every class.  

### Features
- User-placeable objects
- User-traceable objects
- User-movable objects
- Custom-level creation
- Level save and load using JSON
- Button to play sound
- Sound control settings
- Automatically save sound settings


## Application Demo
![about](/Demo/cc3044ee9322070418fc.gif)


### Instruction to play the game
- To play the game, download the "bird-spy-apk.zip" file on your device. 
- Extract the APK file from the zip file. 
- Install the application on your android device.


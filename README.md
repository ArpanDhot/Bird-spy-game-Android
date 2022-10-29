## Bird Spy Game Android
The bird spy game was made using native Android. It is a 2D game that follows the puzzle, casual, and strategy shooter theme. The game consists of a bird being a controllable character, and its purpose is to survive from the enemies while collecting and storing the collected food in the tree. The application employs concepts of Object Oriented Programming to create blueprints for several objects in the game.

### Object Oriented Programming implantation overview
The bird spy game comprises many objects with one thing in common is their movement. Therefore, I created an abstract Position class containing coordinates and appropriate methods. Making the class abstract was for a reason, to not allow the user to create instances of the Position class but only inherit it and provide the unimplemented methods. Furthermore, inheritance implementation reduced the code repetition between similar objects and made the project more manageable by allowing to make the updates from one location.  

The majority of the classes in this project incorporate encapsulation. This concept enabled hiding an object's internal representation, or state, from the outside. For example, the attributes in the bird class are private; therefore, they are only accessible within the class. The only way to access them is by using the public getter and setter methods. Furthermore, it enhances security because programmers cannot manipulate attributes from every class.  

### Features
- User-placeable objects
- User-traceable objects
- User-movable objects
- Custom-level creation
- Level save and load using JSON
- Sound control settings
- Automatically save sound settings


## Application Demo
### Gameplay
![about](/Demo/20221029.gif)
### Creating custom level

### Instruction to play the game
- To play the game, download the "bird-spy-apk.zip" file on your device. 
- Extract the APK file from the zip file. 
- Install the application on your android device.


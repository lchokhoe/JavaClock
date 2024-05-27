<a name="readme-top"></a>
<!-- Clock Application with Java Swing -->
## Clock Application with Java Swing

In this assignment, I was tasked to develop an Application that is executable and with a user interface. This README.md file will give some notes on compiling and running the Application.

The option chosen is the "Analog Clock" under the "Graphics" section with the following functions:
* An Analog Clock to tell the time using a traditional clock face.
* A functioning Stopwatch which can be used for tracking lap times.
* A timer which can be used to alert users once the time is up.

The functions done with close reference to the Microsoft version of the "Clock" application which is available on most Windows machines.

### Built With

* Java
* JDK17
* Java Swing



<!-- GETTING STARTED -->
## Getting Started

Download the zip file containing the source code from the Google Drive link in the email sent.
Ensure that you have the correct JDK version or one that is compatible with JDK17.

### Installation

1. Unzip the file and navigate to the `Javaclock` folder
2. Compile the `.java` files
   ```sh
   javac -d . ./src/main/java/com/clockapp/*.java ./src/main/java/com/clockapp/functionpanels/*.java
   ```
3. Run the `Main.java` file
   ```sh
   java -cp . com.clockapp.Main
   ```

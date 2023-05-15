# CSE 110 Project: Team 16

## Instructions to run the MS1 version of SayItAssistant:
<br>

**Mac/Linux Instructions**: <br><br>
1. Navigate to the location of the project directory through the terminal (/cse-110-project-team-16) <br>
2. Run the command `chmod +x SayItAssistant.sh` <br>
3. Run the application with the command `./SayItAssistant.sh` <br>
**Note**:
The application will open 5 seconds after the server is started.<br>
4. Allow the application access to your microphone when asking a question<br>
5. Close out of the application normally after you are finished. <br>
6. To correctly shut down the server, run the command `lsof -i:8100` and take note of the PID value. <br>
7. Run `kill -9 {PID}` to kill the server.<br><br>

**Windows Instructions**: <br>

1. Install git bash
2. Navigate to the location of the project directory through the git bash terminal or through the file explorer(/cse-110-project-team-16) <br>
3. Run the application with `./SayItAssistant` or double click on the `SayItAssistant.sh` file.
**Note**:
The application will open 5 seconds after the server is started.<br>
4. Close out of the application normally after you are finished. <br>
5. To correctly shut down the server, run the command prompt as adminsitrator and run the command `netstat -ano | :8100`. The last string of numbers is the PID. Take note of this number.
6. Run `taskkill /F /PID {PID}`, where the PID number from the last step is inserted between the brackets. This kills the server. 
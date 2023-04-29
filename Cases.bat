@echo off
REM Set the directory where input files are located
cd C:\Users\yasmi\Desktop\autosar

REM Compile the Java program
javac ContainerSorter.java

REM Run the program for the normal case with "input.arxml" file
java ContainerSorter input.arxml

REM Run the program for the not valid Autosar file case with "invalid_file.arxml" file
java ContainerSorter invalid_file.arxml

REM Create an empty file for testing
echo. > empty.arxml

REM Run the program for the empty file case with "empty.arxml" file
java ContainerSorter empty.arxml

REM Pause to keep the command prompt open after execution
pause

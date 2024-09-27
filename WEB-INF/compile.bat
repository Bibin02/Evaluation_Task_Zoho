@echo off
REM Set the path to your JDK bin directory
REM set JDK_PATH="pathto\Java\jdk-11.0.1\bin"

REM Set the directory where your Java source files are located
REM set SOURCE_DIR="pathto\src"
set SOURCE_DIR="."

REM Navigate to the source directory
cd %SOURCE_DIR%

REM Store .class files to the destination directory
set DESTINATION_DIR="classes"

REM Iterate through each .java file and compile it
for %%f in (*.java) do (
    echo Compiling %%f...
    javac -d %DESTINATION_DIR% %%f
)

echo Compilation complete.
pause

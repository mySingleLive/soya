@if "%DEBUG%" == "" @echo off

if "%OS%"=="Windows_NT" setlocal


set DIR_NAME=%~dp0
if "%DIR_NAME%" == "" set DIR_NAME=.\


set COMMAND_COM="cmd.exe"
if exist "%SystemRoot%\system32\cmd.exe" set COMMAND_COM="%SystemRoot%\system32\cmd.exe"
if exist "%SystemRoot%\command.com" set COMMAND_COM="%SystemRoot%\command.com"

:check_JAVA_HOME
if not "%JAVA_HOME%" == "" goto have_JAVA_HOME

echo.
echo ERROR: Environment variable JAVA_HOME has not been set.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
echo.
goto end

:have_JAVA_HOME

set JAVA_EXE=%JAVA_HOME%\bin\java.exe
set TOOLS_JAR=%JAVA_HOME%\lib\tools.jar


:check_SOYA_HOME
if "%SOYA_HOME%" == "" set SOYA_HOME=%DIR_NAME%..

set CP=
if "x%~1" == "x-cp" set CP=$~2
if "x%~1" == "x-classpath" set CP=$~2
if "x" == "x%CP%" goto :execute
shift
shift

:execute

set STAETER_CLASSPATH=%SOYA_HOME%\libs\soya.jar;.

if "x" == "x%CP%" goto empty_CP
set CP=%STAETER_CLASSPATHCP%;%CP%;.
goto after_CP
:empty_CP
set CP=%STAETER_CLASSPATH%;.
:after_CP


set STARTER_MAIN_CLASS=org.soya.tools.Main

"%JAVA_EXE%" -classpath "%CP%" %STARTER_MAIN_CLASS% %*


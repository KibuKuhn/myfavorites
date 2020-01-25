set dir=%cd%
set PATH=%dir%\jdk-13\bin
set args=%1 %2 %3 %4
start javaw %args% -jar %dir%\myfavorites.jar
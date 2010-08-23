@echo off

del ..\fx-datamanager\src\main\resources\context-datastore.xml
del ..\fx-backtest\src\main\resources\context-datastore.xml
del ..\fx-backtest\src\main\resources\context-datamanager.xml

fsutil hardlink create ..\fx-datamanager\src\main\resources\context-datastore.xml ..\fx-datastore\src\main\resources\context-datastore.xml
fsutil hardlink create ..\fx-backtest\src\main\resources\context-datastore.xml ..\fx-datastore\src\main\resources\context-datastore.xml
fsutil hardlink create ..\fx-backtest\src\main\resources\context-datamanager.xml ..\fx-datamanager\src\main\resources\context-datamanager.xml
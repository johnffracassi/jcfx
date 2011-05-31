@echo off
del db\*.sqlite3
rake db:migrate db:seed
rake sw:profiles
rake sw:games

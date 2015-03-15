

# Database Structure #

  * **object\_set** - a table containing information about object sets.
    * _name_ - set name
    * _description_ - set description
  * **object`_`** - a table containing information about objects.
    * _name_ - object name (filename)
    * _path_ - object path/URL
  * **statistic** - a table containing information about statistic (i.e feature).
    * _name_ - statistic name (i.e. feature name)
  * **stat\_real** - a table containing statistic values of type REAL.
    * _value_ - REAL value
  * **stat\_text** - a table containing statistic values of type TEXT.
    * _value_ - TEXT value

## Notes ##
  * `db/visualCultures.sql` can create the database table structure as described here.


---


# db/importToSqlite.py #

`importToSqlite.py` is a script for importing our "results.txt" into a SQLite database.

## Usage ##
`./importToSqlite.py <sqlite-db> <result file> <set name> <set description>`

## Example ##
  * `$ cd db`
  * `$ sqlite3 test < visualCultures.sql` - create a SQLite database `test` and create table s according to the description in [DbScripts#Database\_Structure](DbScripts#Database_Structure.md)
  * `$ ./importToSqlite.py test sample-results.txt set1 testset`
  * `$ echo "select * from object_;" | sqlite3 test` - print `object_` table


---


# db/importToSqliteV2.py #

## Usage ##
`./importToSqliteV2.py <dbname> <data-file> <set-name> <set-description>`

## Direction ##
  * `$ cd db`
  * `$ sqlite3 dbfile < dbSchema.sql` - create a SQLite database `dbfile` and create table s according to the description in dbSchema.sql.
  * `$ ./importToSqliteV2.py dbfile data_withmetadata.csv onemanga onemanga` - this will insert each row in data\_withmetadata.csv into the database.


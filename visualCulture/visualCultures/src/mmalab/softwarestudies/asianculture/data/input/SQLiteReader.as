package mmalab.softwarestudies.asianculture.data.input
{
	import flash.data.SQLConnection;
	import flash.data.SQLResult;
	import flash.data.SQLStatement;
	import flash.events.SQLErrorEvent;
	import flash.filesystem.File;
	
	import mmalab.softwarestudies.asianculture.data.models.Dataset;

	public class SQLiteReader
	{
		private var sqlFilePath:String;
		private var conn:SQLConnection = new SQLConnection();

		public function SQLiteReader(sqlFilePath:String)
		{
			this.sqlFilePath = sqlFilePath;
		}
		
		public function connect():void {
			//add an event handeler for the open event
//			conn.addEventListener(SQLEvent.OPEN, fillData);
			//create the database if it doesn't exist, otherwise just opens it
			var dbFile:File = File.applicationDirectory.resolvePath (this.sqlFilePath);
			conn.open(dbFile); // using synchronous connection
		}
		
		public function getData(colname:String, min, max):Array {
			var sql:SQLStatement = new SQLStatement();
			//set the statement to connect to our database
			
			sql.sqlConnection = conn;
			conn.addEventListener(SQLErrorEvent.ERROR, errorHandler);
			//parse the sql command that creates the IMAGE table if it doesn't exist
/*			sql.text = "﻿SELECT o.name, r.val FROM object_ o " +
				"JOIN stat_real r ON r.obj_id = o.id " +
				"JOIN statistic s ON r.stat_id = s.id " +
				"WHERE s.name= :statName";
			sql.parameters[":statName"] = colname;
	*/		
			sql.text = "select o.name as name, r.val as x, r2.val as y from object_ o " +
				"join stat_real r on r.obj_id = o.id " +
				"join statistic s on r.stat_id = s.id " +
				"join stat_real r2 on r2.obj_id = o.id " +
				"join statistic s2 on r2.stat_id = s2.id " +
				"where s.name= 'Color7_R' and s2.name='Color5_V' " +
				"group by o.name order by o.name";
			
			if (min != null) {
				sql.text += " AND r.val >= :min";
				sql.parameters[":min"] = min;
			}

			if (max != null) {
				sql.text += " AND r.val <= :max";
				sql.parameters[":max"] = max;
			}
			sql.execute();
			
			var result:SQLResult = sql.getResult();
			if (result != null && result.data.length > 0)
			{
				//trace("id:", row.id, ", name:", row.name, ", type:", row.type);
				return result.data;
			}
			trace(result.data.length);
			return null;
		}

		/**
		 * Returns a Dataset object containing the specified statistic categories and values for each object of the database 
		 * @param colnames the statistical categories
		 * @return Dataset
		 * 
		 */
		public function getDataset(colnames:Array):Dataset {
			var sql:SQLStatement = new SQLStatement();
			//set the statement to connect to our database
			
			sql.sqlConnection = conn;
			conn.addEventListener(SQLErrorEvent.ERROR, errorHandler);
			
			var selectString	:String = "";
			var joinString		:String = "";
			var whereString		:String = "";
			for (var i:int=0; colnames != null && i<colnames.length; i++) {
				selectString += "r"+i+".val as " + colnames[i].name +", ";
				joinString += "join stat_real r"+i+" on r"+i+".obj_id = o.id "
				//	"join statistic s"+i+" on r"+i+".stat_id = s"+i+".id ";
				whereString += "r"+i+".stat_id = "+colnames[i].id+" AND ";
			}
			
			sql.text = "select o.name as name, " + selectString.slice(0, selectString.lastIndexOf(",")) +
				" from object_ o " + joinString +
				" where " + whereString.slice(0, whereString.lastIndexOf("AND")) +
				" group by o.name order by o.name";
			sql.execute();
			
			var result:SQLResult = sql.getResult();
			if (result != null && result.data.length > 0)
			{
				//trace("id:", row.id, ", name:", row.name, ", type:", row.type);
				return new Dataset(result.data);
			}
			trace(result.data.length);
			return null;
		}
		
		/**
		 * Returns an array of Objects(id, name) matching the names of the stat columns specified 
		 * @param colnames
		 * @return Array
		 * 
		 */
		public function getStatsList(colnames:Array):Array {
			var sql:SQLStatement = new SQLStatement();
			//set the statement to connect to our database
			
			sql.sqlConnection = conn;
			conn.addEventListener(SQLErrorEvent.ERROR, errorHandler);
			
			var whereString:String = "";
			if (colnames != null && colnames.length > 0)
				whereString = " where ";
			for (var i:int=0; colnames != null && i<colnames.length; i++) {
				whereString += "s.name = '" + colnames[i] + "' OR ";
			}
			sql.text = "select s.id as id, s.name as name from statistic s " + whereString.slice(0, whereString.lastIndexOf("OR")) + " order by s.name";
			
			sql.execute();
			
			var result:SQLResult = sql.getResult();
			if (result != null && result.data.length > 0)
			{
				//trace("id:", row.id, ", name:", row.name, ", type:", row.type);
				trace(result.data.length);
				return result.data;
			}
			return null;
		}
		
		private function errorHandler(event:SQLErrorEvent):void {
			trace("An error occured while executing the statement.");
		}
		
		public function closeConnection():void {
			conn.close();
		}
	}
}
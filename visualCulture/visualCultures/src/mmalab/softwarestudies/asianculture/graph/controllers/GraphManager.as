package mmalab.softwarestudies.asianculture.graph.controllers
{
	import mmalab.softwarestudies.asianculture.data.input.SQLiteReader;
	import mmalab.softwarestudies.asianculture.data.models.Dataset;
	import mmalab.softwarestudies.asianculture.data.models.Statistic;

	[Bindable]
	public class GraphManager
	{
		public var selectedValues : Array;

		public var data:Dataset;
		public var statsList:Array;		
		private var dbReader:SQLiteReader;

		private var stId:Boolean = false;
		
		public function GraphManager()
		{
			dbReader = new SQLiteReader("visualCultures.db");
			dbReader.connect();
			
//			data = dbReader.getDataset(getRandomStatNames(12));

			selectedValues = null;
//			dbReader.closeConnection();
		}
		
		public function setSelectedValue(idx:Array):void {
			selectedValues = idx;
		}
		
		public function setDataset(colNames:Array):void {
			this.data = dbReader.getDataset(colNames);
		}

		public function setRandomSet(numStats:int):void {

			var fullStatsList:Array = dbReader.getStatsList(null);			
			var randomStat:int;
			
			var statItem:Statistic;
			for (var j:int=0; statsList != null && j<statsList.length; j++) {
				statsList[j] = null;
				delete statsList[j]
			}
			
			statsList = new Array()
			for (var i:int=0; i<numStats; i++) {
				randomStat = Math.floor( Math.random() * fullStatsList.length);
				statItem = new Statistic(fullStatsList[randomStat].id, fullStatsList[randomStat].name);
				statsList.push(statItem);
			}

			this.data = dbReader.getDataset(statsList);
		}
	}
}
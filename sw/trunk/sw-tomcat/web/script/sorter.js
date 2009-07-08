/*--------------------------------------------------------------*/
// HTML TABLE SORTER
// OBJECT ORIENTED JAVASCRIPT IMPLEMENTATION OF QUICKSORT
// @author	Terrill Dent 
// @source	http://www.terrill.ca
// @date	August 28th, 2006
/*--------------------------------------------------------------*/
function TSorter(){
	var table = Object;
	var trs = Array;
	var ths = Array;
	var curSortCol = Object;
	var prevSortCol = '3';
	var sortType = Object;

	function get(){}

	function getCell(index)
	{
		if(curSortCol < 0)
		{
			return 1;
		}
		else
		{
			return trs[index].cells[curSortCol];
		}
	}

	/*----------------------INIT------------------------------------*/
	// Initialize the variable
	// @param tableName - the name of the table to be sorted
	/*--------------------------------------------------------------*/
	this.init = function(tableName)
	{
		table = document.getElementById(tableName);
		ths = table.getElementsByTagName("th");
		for(var i = 0; i < ths.length ; i++)
		{
			ths[i].onclick = function()
			{
				sort(this);
				playerChanged();
			}
		}
		return true;
	};
	
	/*----------------------SORT------------------------------------*/
	// Sorts a particular column. If it has been sorted then call reverse
	// if not, then use quicksort to get it sorted.
	// Sets the arrow direction in the headers.
	// @param oTH - the table header cell (<th>) object that is clicked
	/*--------------------------------------------------------------*/
	function sort(oTH)
	{
		var numeric = false;
		curSortCol = oTH.cellIndex;
		sortType = oTH.abbr;
		trs = table.tBodies[0].getElementsByTagName("tr");

		//set the get function
		setGet(sortType)
		
		if(sortType == 'num')
		{
			numeric = true;
		}

		// it would be nice to remove this to save time,
		// but we need to close any rows that have been expanded
		for(var j=0; j<trs.length; j++)
		{
			if(trs[j].className == 'detail_row')
			{
				closeDetails(j+2);
			}
		}

		// if already sorted just reverse
		if(prevSortCol == curSortCol)
		{
			oTH.className = (oTH.className != 'ascend' ? 'ascend' : 'descend' );
			reverseTable();
		}
		// not sorted - call quicksort
		else
		{
			oTH.className = 'ascend';
			
			if(ths[prevSortCol].className != 'exc_cell') {
				ths[prevSortCol].className = '';
			}
			
			quicksort(0, trs.length, numeric);
		}
		prevSortCol = curSortCol;
	}
	
	/*--------------------------------------------------------------*/
	// Sets the GET function so that it doesnt need to be 
	// decided on each call to get() a value.
	// @param: colNum - the column number to be sorted
	/*--------------------------------------------------------------*/
	function setGet(sortType)
	{
		switch(sortType)
		{   
			case "link_column":
				get = function(index){
					return getCell(index).firstChild.firstChild.nodeValue;
				};
				break;
			default:
				get = function(index){	return getCell(index).firstChild.nodeValue;};
				break;
		};	
	}

	/*-----------------------EXCHANGE-------------------------------*/
	//  A complicated way of exchanging two rows in a table.
	//  Exchanges rows at index i and j
	/*--------------------------------------------------------------*/
	function exchange(i, j)
	{
		if(i == j+1) {
			table.tBodies[0].insertBefore(trs[i], trs[j]);
		} else if(j == i+1) {
			table.tBodies[0].insertBefore(trs[j], trs[i]);
		} else {
			var tmpNode = table.tBodies[0].replaceChild(trs[i], trs[j]);
			if(typeof(trs[i]) == "undefined") {
				table.appendChild(tmpNode);
			} else {
				table.tBodies[0].insertBefore(tmpNode, trs[i]);
			}
		}
	}
	
	/*----------------------REVERSE TABLE----------------------------*/
	//  Reverses a table ordering
	/*--------------------------------------------------------------*/
	function reverseTable()
	{
		for(var i = 1; i<trs.length; i++)
		{
			table.tBodies[0].insertBefore(trs[i], trs[0]);
		}
	}

	/*----------------------QUICKSORT-------------------------------*/
	// This quicksort implementation is a modified version of this tutorial: 
	// http://www.the-art-of-web.com/javascript/quicksort/
	// @param: lo - the low index of the array to sort
	// @param: hi - the high index of the array to sort
	/*--------------------------------------------------------------*/
	function quicksort(lo, hi, numeric)
	{
		if(hi <= lo+1) return;
		 
		if((hi - lo) == 2) {
			if(compare(get(hi-1), get(lo), numeric)) exchange(hi-1, lo);
			return;
		}
		
		var i = lo + 1;
		var j = hi - 1;
		
		if(compare(get(lo), get(i), numeric)) exchange(i, lo);
		if(compare(get(j), get(lo), numeric)) exchange(lo, j);
		if(compare(get(lo), get(i), numeric)) exchange(i, lo);
		
		var pivot = get(lo);
		
		while(true) {
			j--;
			while(compare(pivot, get(j), numeric)) j--;
			i++;
			while(compare(get(i), pivot, numeric)) i++;
			if(j <= i) break;
			exchange(i, j);
		}
		exchange(lo, j);
		
		if((j-lo) < (hi-j)) {
			quicksort(lo, j, numeric);
			quicksort(j+1, hi, numeric);
		} else {
			quicksort(j+1, hi, numeric);
			quicksort(lo, j, numeric);
		}
	}
	
	function compare(a, b, numeric) 
	{
		if(numeric == true) 
		{
			//alert(numeric + ' / ' + parseFloat(a) + '>' + parseFloat(b) + '? ' + (parseFloat(a) > parseFloat(b)));
			return parseFloat(a) > parseFloat(b);
		} 
		else 
		{
			//alert(numeric + ' / ' + (a) + '>' + (b) + '? ' + ((a) > (b)));
			return a > b;
		}
	}
	
}

function getReqParam(param){
	var query = location.search;
	var queryString = query.split('?');
	var values = null;
	if (queryString[1]) {
		var parameters = queryString[1];
		var parametersPairs = parameters.split('&');
		if (parametersPairs.length==null) {
			//console.log("Parametro..: " + parameters);
			values = parameters.split('=');
		} else {
			for(var i = 0; i<parametersPairs.length;i++) {
				if (parametersPairs[i].match(param)) {
					//console.log("Parametro encontrado..: " + parametersPairs[i]);
					values = parametersPairs[i].split('=');
					break;
				}
			}
		}

	}
	return values;
}

function redirectPage(url){
	location.href=url;
}

function showMessages(msg){
	dojo.byId("messages").style.display='block';		
	//console.log(msg);
  	dojo.html.set(dojo.byId("messages"), msg, 
  		  		{
		      		parseContent: true
		      	});
}

function openPage(parentId,url){
	dojo.require("dijit.dijit");
	dojo.require("dojox.layout.ContentPane");
	var parent = dijit.byId(parentId);
	if (parent!=null) {
		parent.destroyDescendants();
	}
	var fullUrl = dojo.getObject('basePath') + url;
	
	// create a ContentPane as the left pane in the BorderContainer
	var newPage = new dojox.layout.ContentPane(
			{
				href:fullUrl
				,loadingMessage: "Loading....."
				,preventCache:true
				//,parseOnLoad:true
				//,isLoaded:true
				,extractContent:false
				,executeScripts:true
				//,refreshOnShow:true
			}
	);
	
	parent.addChild(newPage);
	newPage.startup();
	newPage.refresh();
	parent.startup();
}

function getSelectedCell(columnName,items){
	var value = '';
    if (items.length) {
        // Iterate through the list of selected items.
        // The current item is available in the variable
        // "selectedItem" within the following function:
        dojo.forEach(items, function(selectedItem) {
            if (selectedItem !== null) {
                // Iterate through the list of attributes of each item.
                // The current attribute is available in the variable
                // "attribute" within the following function:
                dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute) {
                    // Get the value of the current attribute:
                	if (attribute==columnName){
                        value = grid.store.getValues(selectedItem, attribute);
                    }
                }); // end forEach
            } // end if
        }); // end forEach
    } // end if
    return value;	
}
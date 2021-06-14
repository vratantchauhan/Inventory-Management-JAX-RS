package com.vratant.jerseyapi.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vratant.jerseyapi.dao.InventoryDAO;
import com.vratant.jerseyapi.model.Inventory;

@Path("/inventory")
public class InventoryResource {

	 	@GET 
	 	@Produces(MediaType.APPLICATION_XML)
	    public Response getItAll() {
	 		List<Inventory> inventoryInDB = new ArrayList<Inventory>();
	 		inventoryInDB = InventoryDAO.listInventories();
	 		GenericEntity<List<Inventory>> entity
	 	    = new GenericEntity<List<Inventory>>(inventoryInDB) {};
	 	    Response response = Response.ok(entity).build();
	 	   return response;
	    }
	 	
	 	@GET
	 	@Path("{id}")
	 	@Produces(MediaType.APPLICATION_XML)
	 	public Response get(@PathParam("id") int id) {
	 		
	 		List<Inventory> inventoryInDB = new ArrayList<Inventory>();
	 		inventoryInDB = InventoryDAO.searchInventory(id);
	 		GenericEntity<List<Inventory>> entity
	 	    = new GenericEntity<List<Inventory>>(inventoryInDB) {};
	 	    Response response = Response.ok(entity).build();
	 	    return response;
	 	}
	 	
	 	@POST
	 	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML} )
	 	public Response add(Inventory inventory) throws URISyntaxException {
	 	    int newProductId = InventoryDAO.insertInventory(inventory);
	 	    URI uri = new URI("/" + newProductId);
	 	    return Response.created(uri).build();
	 	}
	 	
	 	@PUT
	 	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	 	@Path("{id}")
	 	public Response update(@PathParam("id") int id, Inventory inventory) throws URISyntaxException {
	 		int newProductId = InventoryDAO.updateInventory(id, inventory);
	 		URI uri = new URI("/" + newProductId);
	 	    return Response.created(uri).build();
	 	}
	 	
	 	@DELETE
	 	@Path("{id}")
	 	public Response delete(@PathParam("id") int id) {
	 	    if (InventoryDAO.inventoryDelete(id)) {
	 	        return Response.ok().build();
	 	    } else {
	 	        return Response.notModified().build();
	 	    }
	 	}
}


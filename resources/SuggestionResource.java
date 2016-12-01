package org.jimmy.suggestion.DB.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jimmy.suggestion.DB.controller.SuggestionController;
import org.jimmy.suggestion.DB.objects.Suggestion;

@Path("/suggestions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class SuggestionResource {
	
	SuggestionController suggestionController = new SuggestionController();
	
	@GET
	public List<Suggestion> getSuggestions(@QueryParam("keyword") String keyword, @QueryParam("start") int start,
													@QueryParam("size") int size,@QueryParam("end") int end){
		if(keyword != null){
			return suggestionController.getSuggestionsWithKeyword(keyword);
		}
		if(start >= 0 && end > start){
			return suggestionController.getSuggestionsByPagination(start, end);
		}
		if(size > 0){
			return suggestionController.getSuggestionsBySize(size);
		}
		return suggestionController.getAllSuggestions();
	}
	
	@GET
	@Path("/{suggestionId}")
	public Suggestion getSuggestionById(@PathParam("suggestionId") int suggestionId){
		return suggestionController.getSuggestion(suggestionId);
	}
	
	@POST
	public void createSuggestion(Suggestion s){
		suggestionController.createSuggestion(s);
	}
	
	@DELETE
	@Path("/{suggestionId}")
	public void deleteSuggestion(@PathParam("suggestionId") int suggestionId){
		suggestionController.deleteSuggestion(suggestionId);
	}
	
	@DELETE
	public void deleteAllSuggestions(){
		suggestionController.deleteAllSuggestions();
	}
	
	@PUT
	public void updateSuggestion(Suggestion s){
		suggestionController.updateSuggestion(s);
	}
	
	@PUT
	@Path("/{suggestionId}")
	public void rateSuggestion(@PathParam("suggestionId") int suggestionId, @QueryParam("rate") boolean rate){
		suggestionController.rateSuggestion(suggestionId, rate);
	}
}

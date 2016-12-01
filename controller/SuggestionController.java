package org.jimmy.suggestion.DB.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Date;

import org.jimmy.suggestion.DB.objects.Suggestion;

public class SuggestionController {
	
	ArrayList<Suggestion> listOfSuggestions;
	
	public SuggestionController(){
		try {
			DatabaseController.init();
			listOfSuggestions = DatabaseController.getSuggestionsList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Suggestion> getSuggestionsByPagination(int start, int end){
		if(end >= listOfSuggestions.size()){
			return new ArrayList<Suggestion>();
		}
		return listOfSuggestions.subList(start, end);
	}
	
	public ArrayList<Suggestion> getAllSuggestions(){
		return new ArrayList<Suggestion>(listOfSuggestions);
	}
	
	public void createSuggestion(Suggestion s){
		if(listOfSuggestions.isEmpty()){
			s.setId(1);
		} else {
			s.setId(listOfSuggestions.get(listOfSuggestions.size() - 1).getId() + 1);
		}
		s.setRating(0);
		s.setDate(new Date());
		listOfSuggestions.add(s);
		DatabaseController.saveToDB();
	}
	
	public void updateSuggestion(Suggestion s){
		Suggestion suggestion = getSuggestion(s.getId());
		if(suggestion != null){
			suggestion.setMessage(s.getMessage());
			suggestion.setRating(s.getRating());
			suggestion.setDate(s.getDate());

		}
		DatabaseController.saveToDB();
	}
	
	public void deleteSuggestion(int suggestionId){
		Suggestion s = getSuggestion(suggestionId);
		if(s != null){
			listOfSuggestions.remove(s);
		}
		DatabaseController.saveToDB();
	}
	
	public void deleteAllSuggestions(){
		listOfSuggestions.clear();
		DatabaseController.saveToDB();

	}
	
	public Suggestion getSuggestion(int suggestionId){
		for(Suggestion suggestions: listOfSuggestions){
			if(suggestionId == suggestions.getId()){
				return suggestions;
			}
		}
		return null;
	}
	
	public ArrayList<Suggestion> getSuggestionsWithKeyword(String keyword){
		ArrayList<Suggestion> suggestionsWithKeyword = new ArrayList<Suggestion>();
		for(Suggestion suggestions: listOfSuggestions){
			if(suggestions.getMessage().contains(keyword)){
				suggestionsWithKeyword.add(suggestions);
			}
		}
		return suggestionsWithKeyword;
	}
	
	public List<Suggestion> getSuggestionsBySize(int size){
		if(size > listOfSuggestions.size()){
			return listOfSuggestions;
		}
		return listOfSuggestions.subList(0,size - 1);
	}
	
	
	public void rateSuggestion(int suggestionId, boolean rate){
		Suggestion s = getSuggestion(suggestionId);
		if(s.getId() == suggestionId){
			if(rate == true){
				s.setRating(s.getRating() + 1);
			} else {
				s.setRating(s.getRating() - 1);
			}
		}
	}
		
	public int getNumSuggestions(){
		return listOfSuggestions.size();
	}

}

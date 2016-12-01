package org.jimmy.suggestion.DB.objects;

import java.util.Date;

public class Suggestion {
	private String message;
	private Date date;
	private int rating;
	private int id;
	
	public Suggestion(){}
	
	public Suggestion(String message){
		this.setMessage(message);
		this.setRating(0);
		setDate(new Date());
	}
	
	public Suggestion(String message, Date date, int rating, int id){
		this.setMessage(message);
		this.setRating(rating);
		this.setId(id);
		setDate(date);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

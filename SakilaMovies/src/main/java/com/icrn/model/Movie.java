package com.icrn.model;

public class Movie {
	private int filmId;
	private String title;
	private String description;
	private int releaseYear;
	private int rentalDuration;
	private double rentalRate;
	private double replacementCost;
	private String rating;
	private String features;
	private String genre;
	public Movie(){};
	
	public Movie(int filmId, String title, String description) {
		this.filmId = filmId;
		this.title = title;
		this.description = description;
	}

	public Movie(int filmId, String title, String description, int releaseYear, int rentalDuration, double rentalRate,
			double replacementCost, String rating, String features) {
		this.filmId = filmId;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
		this.rating = rating;
		this.features = features;
	}
	public Movie(int filmId, String title, String description, int releaseYear, int rentalDuration, double rentalRate,
			double replacementCost, String rating, String features, String genre) {
		this.filmId = filmId;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
		this.rating = rating;
		this.features = features;
		this.genre = genre;
	}
	public void setFilmId(int filmId){
		this.filmId = filmId;
	}
	public int getFilmId() {
		return filmId;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public int getReleaseYear() {
		return releaseYear;
	}
	public int getRentalDuration() {
		return rentalDuration;
	}
	public double getRentalRate() {
		return rentalRate;
	}
	public double getReplacementCost() {
		return replacementCost;
	}
	public String getRating() {
		return rating;
	}
	public String getFeatures() {
		return features;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}
	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}
	public void setReplacementCost(double replacementCost) {
		this.replacementCost = replacementCost;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public void setFeatures(String features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "Movie [filmId=" + filmId + ", title=" + title + ", description=" + description + ", releaseYear="
				+ releaseYear + ", rentalDuration=" + rentalDuration + ", rentalRate=" + rentalRate
				+ ", replacementCost=" + replacementCost + ", rating=" + rating + ", features=" + features + ", genre="
				+ genre + "]";
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}

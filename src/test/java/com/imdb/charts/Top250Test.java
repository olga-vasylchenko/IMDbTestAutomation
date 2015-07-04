package com.imdb.charts;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import com.imdb.BaseWebFixture;
import com.imdb.pageObjects.charts.GenrePage;
import com.imdb.pageObjects.charts.Top250Page;

import domain.MovieGenres;
import domain.Top250SortingOptions;

public class Top250Test extends BaseWebFixture {

	private Top250Page top250Page;
	private GenrePage genrePage;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		top250Page = Top250Page.open(driver);
	}

	@Test
	public void top250ListContainsAtLeastOneMovie() {
		int actualNumberOfMovies = top250Page.getNumberOfMoviesInList();
		assertTrue("The actual number of movies in Top 250 list ("
				+ actualNumberOfMovies + ") is not greater or equal to 1.",
				actualNumberOfMovies >= 1);
	}

	@Test
	public void westernGenreListContainsAtLeastOneMovie() throws Exception {
		genrePage = top250Page.selectGenre(MovieGenres.WESTERN);
		int actualNumberOfResults = genrePage.getNumberOfResults();
		assertTrue("The actual number of results in '" + MovieGenres.WESTERN
				+ "' genre list (" + actualNumberOfResults
				+ ") is not greater or equal to 1.", actualNumberOfResults >= 1);
	}

	@Test
	public void atLeastOneMovieIsReturnedForEachSortingOption()
			throws Exception {
		Top250SortingOptions[] sortingOptions = Top250SortingOptions.values();
		for (Top250SortingOptions option : sortingOptions) {
			top250Page.selectSortingtOption(option);
			int actualNumberOfResults = top250Page.getNumberOfMoviesInList();
			assertTrue("The actual number of movies for '" + option
					+ "' sorting option (" + actualNumberOfResults
					+ ") is not greater or equal to 1.",
					actualNumberOfResults >= 1);
		}
	}

	public static void main(String[] args) throws Exception {
		JUnitCore.main("com.imdb.charts.Top250Test");
	}
}

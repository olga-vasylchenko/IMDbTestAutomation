package com.imdb.pageObjects.charts;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.imdb.pageObjects.PageObject;

import domain.MovieGenres;
import domain.Top250SortingOptions;

public class Top250Page extends PageObject {

	private static String url = "http://www.imdb.com/chart/top";

	private final String listOfMoviesCssString = "#main table.chart .lister-list tr";

	@FindBy(css = listOfMoviesCssString)
	private List<WebElement> listOfMovies;

	@FindBy(id = "main")
	private WebElement mainBlock;

	@FindBy(css = "#main select.lister-sort-by")
	private WebElement sortingOptionSelectbox;

	private HashMap<MovieGenres, String> movieGenresMap = new HashMap<>();
	private HashMap<Top250SortingOptions, String> sortingOptionsMap = new HashMap<>();

	protected Top250Page(WebDriver driver) {
		super(driver);
		createMovieGenresMap();
		createSortingOptionsMap();
	}

	@Override
	protected ExpectedCondition<?> pageIsLoaded() {
		return ExpectedConditions.visibilityOf(mainBlock);
	}

	public static Top250Page open(WebDriver driver) {
		driver.get(url);
		return new Top250Page(driver);
	}

	public int getNumberOfMoviesInList() {
		return listOfMovies.size();
	}

	public GenrePage selectGenre(MovieGenres genre) throws Exception {
		String genreLinkText = movieGenresMap.get(genre);

		if (genreLinkText == null) {
			throw new Exception(
					"Unexpected movie genre '"
							+ genre
							+ "'  was passed to the test. There might be no link on a page for this movie genre.");
		}

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.linkText(genreLinkText)));

		driver.findElement(By.linkText(genreLinkText)).click();
		return new GenrePage(driver);
	}

	public void selectSortingtOption(Top250SortingOptions option)
			throws Exception {
		String sortingOptionText = sortingOptionsMap.get(option);

		if (sortingOptionText == null) {
			throw new Exception(
					"Unexpected sorting option '"
							+ option
							+ "'  was passed to the test. There might be no option in the selectbox for this sorting option.");
		}

		Select sortingOptions = new Select(sortingOptionSelectbox);
		sortingOptions.selectByVisibleText(sortingOptionText);
		waitForAjaxToComplete();
	}

	private void createMovieGenresMap() {
		movieGenresMap.put(MovieGenres.ACTION, "Action");
		movieGenresMap.put(MovieGenres.ADVENTURE, "Adventure");
		movieGenresMap.put(MovieGenres.FILMNOIR, "Film-Noir");
		movieGenresMap.put(MovieGenres.WESTERN, "Western");
	}

	private void createSortingOptionsMap() {
		sortingOptionsMap.put(Top250SortingOptions.RANKING, "Ranking");
		sortingOptionsMap.put(Top250SortingOptions.IMDB_RATING, "IMDb Rating");
		sortingOptionsMap
				.put(Top250SortingOptions.RELEASE_DATE, "Release Date");
		sortingOptionsMap.put(Top250SortingOptions.NUMBER_OF_RATINGS,
				"Number of Ratings");
		sortingOptionsMap.put(Top250SortingOptions.YOUR_RATING, "Your Rating");
	}
}

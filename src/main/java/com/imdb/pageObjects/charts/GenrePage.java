package com.imdb.pageObjects.charts;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.imdb.pageObjects.PageObject;

public class GenrePage extends PageObject {

    @FindBy(css = "#main table.results tr")
    private List<WebElement> listOfResults;

    @FindBy(id = "main")
    private WebElement mainBlock;

    public GenrePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ExpectedCondition<?> pageIsLoaded() {
        return ExpectedConditions.visibilityOf(mainBlock);
    }

    public int getNumberOfResults() {
        return listOfResults.size();
    }
}

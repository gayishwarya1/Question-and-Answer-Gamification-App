package fr.utcapitole.demo.gui.cucumber.steps;

import fr.utcapitole.demo.gui.cucumber.Context;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class BasicSteps {
    public static final int DRIVER_WAIT_TIMEOUT_SECONDS = 2;

    private final Context context;

    public BasicSteps(Context context) {
        this.context = context;
    }

    @Given("the categories are")
    public void theCategoriesAre(DataTable categories) {
        categories.asList().forEach(context::createCategory);
    }

    @Then("the user may choose a category among")
    public void theUserMayChooseACategoryAmong(DataTable categories) {
        context.fluentWait().until(
                presenceOfElementLocated(By.xpath("//select[@id='category']"))
                        .andThen(link -> {
                            List<WebElement> options = context.getWebDriver().findElements(By.xpath("//select[@id='category']/option"));
                            List<String> actualCategories = options.stream().map(WebElement::getText).toList();
                            System.out.println(actualCategories);
                            String[] expectedCategories = new String[categories.values().size()];
                            categories.values().toArray(expectedCategories);
                            assertThat(actualCategories).containsExactlyInAnyOrder(expectedCategories);
                            return true;
                        })
        );
    }

    @When("the user navigates to {string}")
    public void theClientNavigatesTo(String path) {
        context.getWebDriver().navigate().to(context.buildApplicationUrl(path));
    }

    @Then("the page contains a link {string} to {string}")
    public void thePageContainsLink(String text, String path) {
        context.fluentWait().until(
                presenceOfElementLocated(By.xpath("//a[contains(text(),'" + text + "')]"))
                        .andThen(link -> {
                            System.out.println("Found link " + text + ": " + link.getAttribute("href"));
                            assertThat(link.getAttribute("href")).isEqualTo(context.buildApplicationUrl(path));
                            return true;
                        })
        );
    }

    @Then("the page contains {string}")
    public void thePageContains(String text) {
        assertThat(context.getWebDriver().getPageSource()).contains(text);
    }

    @Given("the user logs in as {string} with password {string}")
    public void theUserIs(String username, String password) {
        context.getWebDriver().navigate().to(context.buildApplicationUrl("/login"));
        context.fluentWait().until(
                presenceOfElementLocated(By.xpath("//input[@id='username']"))
                        .andThen(link -> {
                            WebElement usernameInput = context.getWebDriver().findElement(By.xpath("//input[@id='username']"));
                            WebElement passwordInput = context.getWebDriver().findElement(By.xpath("//input[@id='password']"));
                            WebElement signInButton = context.getWebDriver().findElement(By.xpath("//button[@type='submit']"));
                            usernameInput.sendKeys(username);
                            passwordInput.sendKeys(password);
                            signInButton.click();
                            return true;
                        })
        );
    }

    @When("the user enters {string} in {string}")
    public void theUserEnters(String value, String field) {
        context.fluentWait().until(
                presenceOf(elementWithLabel(field))
                        .andThen(input -> {
                            input.clear();
                            input.sendKeys(value);
                            return true;
                        })
        );
    }

    @And("the user selects {string} in {string}")
    public void theUserSelects(String value, String field) {
        context.fluentWait().until(
                presenceOf(elementWithLabel(field))
                        .andThen(input -> {
                            Select s = new Select(input);
                            s.selectByVisibleText(value);
                            return true;
                        })
        );
    }

    @Then("the user is on URL {string}")
    public void theUserIsOnURL(String url) {
        assertThat(context.getWebDriver().getCurrentUrl()).isEqualTo(context.buildApplicationUrl(url));
    }

    @And("the user submits")
    public void theUserSubmits() {
        context.fluentWait().until(
                presenceOfElementLocated(By.xpath("//input[@type='submit']"))
                        .andThen(input -> {
                            input.click();
                            return true;
                        })
        );
    }

    @When("the user clicks on the first {string} button")
    public void theUserClicksOnTheFirstButton(String buttonText) {
        context.fluentWait().until(
                presenceOfElementLocated(By.xpath("(//button[contains(., '" + buttonText + "')])[1]"))
                        .andThen(input -> {
                            input.click();
                            return true;
                        })
        );
    }

    public static ExpectedCondition<WebElement> presenceOf(Function<WebDriver, By> locator) {
        return driver -> presenceOfElementLocated(locator.apply(driver)).apply(driver);
    }

    public static Function<WebDriver, By> elementWithLabel(String labelText) {
        return presenceOfElementLocated(labelWithText(labelText))
                .andThen(label -> label.getAttribute("for"))
                .andThen(By::id);
    }

    public static By labelWithText(String text) {
        return elementWithText("label", text);
    }

    public static By elementWithText(String elementName, String text) {
        return By.xpath("//" + elementName + "[normalize-space(.)='" + text + "']");
    }
}

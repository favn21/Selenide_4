package org.example;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
public class SearchTest {
    @Test
    public void testSearch() {
        WebDriverConfig.setUp();
        open("https://www.wildberries.ru/");

        $(By.cssSelector("#searchInput")).setValue("Iphone 13").pressEnter();
        sleep(10000);

        $(By.cssSelector(".searching-results__title")).shouldHave(text("Iphone 13"));
        sleep(30000);

        $$x("//div[@class='catalog-page__filters-block']").first().shouldHave(text("По популярности"));

        $(By.xpath("//button[contains(text(), 'По популярности')]")).shouldBe(visible);

        $$(".product-card__brand").first().shouldHave(text("Apple"));

        $(By.cssSelector(".search-catalog__btn.search-catalog__btn--clear.search-catalog__btn--active")).click();

        $(By.cssSelector("#searchInput")).shouldHave(value(""));
    }
}

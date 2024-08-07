package org.example;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CityChangeTest {


    @Test
    public void testCityChange() {
        WebDriverConfig.setUp();

        open("https://www.wildberries.ru/");

        $(By.xpath("//span[@data-wba-header-name='DLV_Adress']")).click();
        sleep(10000);

        $(By.cssSelector(".ymaps-2-1-79-searchbox-input__input")).setValue("Санкт-Петербург").pressEnter();
        sleep(20000);

        $$(".address-item.j-poo-option").first().click();

        $(By.cssSelector("button.details-self__btn.btn-main")).click();

        $(By.xpath("//span[@class='simple-menu__link simple-menu__link--address j-geocity-link j-wba-header-item' and text()='Санкт-Петербург, Санкт-Петербург, Спасский переулок, 9']"))//Санкт-Петербург, Санкт-Петербург, Спасский переулок, 9 г. Санкт-Петербург, м Ленинский проспект, пр-кт. Ленинский, 87 корп. 1
                .shouldBe(visible);

        SelenideElement productCardLink = $$(".product-card").first().shouldBe(visible);
        productCardLink.scrollTo();

        SelenideElement productPriceElement = productCardLink.$x(".//ins[contains(@class, 'price__lower-price')]");
        productPriceElement.shouldBe(visible);
        String productPriceText = productPriceElement.getText().trim();
        String productPrice = extractPrice(productPriceText);

        String productName = productCardLink.$x(".//span[contains(@class, 'product-card__name')]").getText();//это
        productCardLink.click();

        SelenideElement addToBasketButton = $x("//button[@class='order__button btn-main']");
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", addToBasketButton);
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].click();", addToBasketButton);

        $(By.cssSelector("span.navbar-pc__notify")).shouldHave(text("1"));

        $(By.cssSelector(".navbar-pc__icon--basket")).click();

        SelenideElement cartProductNameElement = $x(".//span[contains(@class, 'good-info__good-name')]");
        String productNameInCart = cartProductNameElement.getText();
        String modifiedProductNameInCart = productNameInCart + " /";
        modifiedProductNameInCart.equals(matchText(productName));

        SelenideElement cartProductPriceElement = $x("//div[contains(@class, 'list-item__price-new')]");
        cartProductPriceElement.shouldBe(visible);
        String cartProductPriceText = cartProductPriceElement.getText().trim();
        String cartProductPrice = extractPrice(cartProductPriceText);

        SelenideElement totalCartPriceElement = $x("//span[contains(@data-link, 'basketPriceWithCurrencyV2')]");
        totalCartPriceElement.shouldBe(visible);
        String totalCartPriceText = totalCartPriceElement.getText().trim();
        String totalCartPrice = extractPrice(totalCartPriceText);

        SelenideElement orderButton = $x("//button[@class='b-btn-do-order btn-main j-btn-confirm-order']");
        orderButton.shouldBe(Condition.visible);
        orderButton.shouldBe(Condition.enabled);
    }

    private String extractPrice(String priceText) {
        return priceText.replaceAll("[^0-9]", "");
    }
}

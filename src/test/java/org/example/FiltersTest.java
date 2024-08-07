package org.example;
import com.codeborne.selenide.CollectionCondition;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
public class FiltersTest {

    @Test
    public void testFilters() {
        WebDriverConfig.setUp();

        open("https://www.wildberries.ru/");
        sleep(40000);
        $("button.nav-element__burger.j-menu-burger-btn.j-wba-header-item").click();

        sleep(10000);

        $x("//span[contains(@class, 'menu-burger__main-list-link') and text()='Электроника']").click();
        $x("//span[contains(@class, 'menu-burger__link--next') and text()='Ноутбуки и компьютеры']").click();
        $x("//a[contains(@class, 'menu-burger__link') and contains(@href, 'noutbuki-ultrabuki')]").click();

        $("button.dropdown-filter__btn.dropdown-filter__btn--all").click();

        $x("//input[@name='startN']").setValue("100000");
        $x("//input[@name='endN']").setValue("149000");
        $x("//div[contains(@class, 'checkbox-with-text') and .//span[contains(text(), 'Apple')]]").click();
        $x("//span[contains(@class, 'radio-with-text__decor') and following-sibling::span[contains(text(), 'до 5 дней')]]").click();
        $x("//span[contains(@class, 'checkbox-with-text__text') and contains(text(), '13.3')]").click();
        $x("//button[contains(@class, 'filters-desktop__btn-main') and contains(text(), 'Показать')]").click();


        $x("//span[@class='your-choice__btn' and contains(text(), 'от 100 000 до 149 000')]").shouldBe(visible);
        $x("//span[@class='your-choice__btn' and contains(text(), 'до 5 дней')]").shouldBe(visible);
        $x("//span[@class='your-choice__btn' and contains(text(), 'Apple')]").shouldBe(visible);
        $x("//span[@class='your-choice__btn' and contains(text(), '13.3')]").shouldBe(visible);

        String totalItemsText = $x("//span[contains(@class, 'goods-count')]/span").getText();
        int totalItems = Integer.parseInt(totalItemsText.replaceAll("[^0-9]", ""));

        $$("div.product-card__wrapper").shouldHave(CollectionCondition.size(totalItems));

        $x("//li[contains(@class, 'your-choice__item--reset')]//button[contains(@class, 'your-choice__btn') and contains(text(), 'Сбросить все')]").shouldBe(visible);
        closeWebDriver();
    }
}

package pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class PaymentTypeSwitcher {
    private static final SelenideElement buyCreditBtn = $(new Selectors.ByText("Купить в кредит"));
    private static final SelenideElement buyBtn = $(new Selectors.ByText("Купить"));

    public void buyCredit() {
        buyCreditBtn.click();
    }

    public void buy() {
        buyBtn.click();
    }
}

package objects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class NotCredit {
    private static SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private static SelenideElement month = $("[placeholder='08']");
    private static SelenideElement year = $("[placeholder='22']");
    private static SelenideElement owner = $$(".input__control").get(3);
    private static SelenideElement csv = $("[placeholder='999']");
    private static SelenideElement submitBtn = $(new Selectors.ByText("Продолжить"));
    private static SelenideElement okNotification = $(By.className("notification_status_ok"));
    private static SelenideElement errorNotification = $(By.className("notification_status_error"));
    private static SelenideElement wrongCardFormatNotification = $$(".input__sub").get(0);
    private static SelenideElement wrongMonthFormatNotification = $$(".input__sub").get(1);
    private static SelenideElement wrongYearNotification = $$(".input__sub").get(2);
    private static SelenideElement wrongOwnerNameNotification = $$(".input__sub").get(3);
    private static SelenideElement wrongCsvNotification = $$(".input__sub").get(4);


    public void submitInfo(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getCardNumber());
        month.setValue(String.valueOf(info.getMonth()));
        year.setValue(String.valueOf(info.getYear()));
        owner.setValue(info.getOwner());
        csv.setValue(String.valueOf(info.getCsv()));
        submitBtn.click();
    }

    public void verifySubmitOk() {
        okNotification.waitUntil(Condition.visible, 15000).shouldHave(Condition.text("Успешно"));
        errorNotification.shouldBe(Condition.hidden);
    }

    public void verifySubmitDecline() {
        errorNotification.waitUntil(Condition.visible, 15000).shouldHave(Condition.text("Ошибка"));
        okNotification.shouldBe(Condition.hidden);
    }

    public void verifyWrongCardFormat(){
        wrongCardFormatNotification.shouldHave(Condition.visible).shouldHave(Condition.text("Неверный формат"));
    }

    public void verifyWrongMonthFormat(){
        wrongMonthFormatNotification.shouldHave(Condition.visible).shouldHave(Condition.text("Неверный формат"));
    }

}

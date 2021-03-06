package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.CardInfo;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentForm {
    private static SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private static SelenideElement month = $("[placeholder='08']");
    private static SelenideElement year = $("[placeholder='22']");
    private static SelenideElement owner = $$(".input__control").get(3);
    private static SelenideElement cvv = $("[placeholder='999']");
    private static SelenideElement submitBtn = $(new Selectors.ByText("Продолжить"));
    private static SelenideElement okNotification = $(".notification_status_ok");
    private static SelenideElement errorNotification = $(".notification_status_error");
    private static SelenideElement wrongCardFormatNotification = $(".input__sub");
    private static SelenideElement wrongMonthFormatNotification = $(".input__sub");
    private static SelenideElement expiredYearNotification = $(".input__sub");
    private static SelenideElement wrongOwnerNameNotification = $(".input__sub");
    private static SelenideElement wrongCvvNotification = $(".input__sub");


    public void submitInfo(CardInfo info) {
        cardNumber.setValue(info.getCardNumber());
        month.setValue(String.valueOf(info.getMonth()));
        year.setValue(String.valueOf(info.getYear()));
        owner.setValue(info.getOwner());
        cvv.setValue(String.valueOf(info.getCvv()));
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

    public void verifyWrongCardFormat() {
        wrongCardFormatNotification.shouldHave(Condition.visible).shouldHave(Condition.text("Неверный формат"));
    }

    public void verifyWrongMonthFormat() {
        wrongMonthFormatNotification.shouldHave(Condition.visible).shouldHave(Condition.text("Неверный формат"));
    }

    public void verifyWrongOwnerFormat() {
        wrongOwnerNameNotification.shouldHave(Condition.visible).shouldHave(Condition.text("Неверный формат"));
    }

    public void verifyYearExpired() {
        expiredYearNotification.shouldHave(Condition.visible).shouldHave(Condition.text("Истёк срок действия карты"));
    }

    public void verifyWrongCvv() {
        wrongCvvNotification.shouldHave(Condition.visible).shouldHave(Condition.text("Неверный формат"));
    }

}

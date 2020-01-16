import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import data.CardInfo;
import database.Dao;
import org.junit.jupiter.api.BeforeEach;
import pages.CreditPaymentPage;
import pages.PaymentPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static com.codeborne.selenide.Selenide.*;

public class MainTest {
    private static final String URL = "http://localhost:8080/";

    @BeforeEach
    void clearAll() {
       Dao.clearAllTables();
       open(URL);
    }

    @Test
    @DisplayName("Оплата тура с использованием карты со статусом 'APPROVED'")
    void shouldSubmitPaymentRequestApprovedCard() {
        PaymentPage paymentPage = new PaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        paymentPage.getPaymentTypeSwitcher().buy();
        paymentPage.getPaymentForm().submitInfo(cardInfo);
        paymentPage.getPaymentForm().verifySubmitOk();
        assertTrue(Dao.acceptApprovedPayment());
    }

    @Test
    @DisplayName("Оплата тура с использованием карты со статусом 'DECLINED'.")
    void shouldDeclinePaymentRequestDeclinedCard() {
        PaymentPage paymentPage = new PaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        paymentPage.getPaymentTypeSwitcher().buy();
        paymentPage.getPaymentForm().submitInfo(cardInfo);
        paymentPage.getPaymentForm().verifySubmitDecline();
        assertTrue(Dao.acceptDeclinedPayment());
    }

    @Test
    @DisplayName("Оплата тура с использованием неизвестной карты.")
    void shouldDeclinePaymentRequestUnknownCard() {
        PaymentPage paymentPage = new PaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getUnknownCardInfo();
        paymentPage.getPaymentTypeSwitcher().buy();
        paymentPage.getPaymentForm().submitInfo(cardInfo);
        paymentPage.getPaymentForm().verifySubmitDecline();
        assertTrue(Dao.acceptDeclinedPayment());
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием карты со статусом 'APPROVED'")
    void shouldSubmitCreditPaymentRequestApprovedCard() {
        CreditPaymentPage creditPaymentPage = new CreditPaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        creditPaymentPage.getPaymentTypeSwitcher().buyCredit();
        creditPaymentPage.getPaymentForm().submitInfo(cardInfo);
        creditPaymentPage.getPaymentForm().verifySubmitOk();
        assertTrue(Dao.acceptApprovedCreditPayment());
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием карты со статусом 'DECLINED'")
    void shouldDeclineCreditPaymentRequestDeclinedCard() {
        CreditPaymentPage creditPaymentPage = new CreditPaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        creditPaymentPage.getPaymentTypeSwitcher().buyCredit();
        creditPaymentPage.getPaymentForm().submitInfo(cardInfo);
        creditPaymentPage.getPaymentForm().verifySubmitDecline();
        assertTrue(Dao.acceptDeclinedCreditPayment());
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием неизвестной карты")
    void shouldDeclineCreditPaymentRequestUnknownCard() {
        Dao.clearAllTables();
        CreditPaymentPage creditPaymentPage = new CreditPaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getUnknownCardInfo();
        creditPaymentPage.getPaymentTypeSwitcher().buyCredit();
        creditPaymentPage.getPaymentForm().submitInfo(cardInfo);
        creditPaymentPage.getPaymentForm().verifySubmitDecline();
        assertTrue(Dao.acceptDeclinedCreditPayment());
    }

    @Test
    @DisplayName("Оплата тура с использование карты неверного формата")
    void shouldDeclineRequestForCardWrongFormat() {
        PaymentPage paymentPage = new PaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker();
        cardInfo.setCardNumber(faker.finance().creditCard(CreditCardType.AMERICAN_EXPRESS));
        paymentPage.getPaymentTypeSwitcher().buy();
        paymentPage.getPaymentForm().submitInfo(cardInfo);
        paymentPage.getPaymentForm().verifyWrongCardFormat();
    }

    @Test
    @DisplayName("Оплата тура. Месяц введен в неправильном формате - 'm' вместо 'mm'")
    void shouldDeclineRequestForMonthWrongFormat() {
        CreditPaymentPage creditPaymentPage = new CreditPaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setMonth("2");
        creditPaymentPage.getPaymentTypeSwitcher().buyCredit();
        creditPaymentPage.getPaymentForm().submitInfo(cardInfo);
        creditPaymentPage.getPaymentForm().verifyWrongMonthFormat();
    }

    @Test
    @DisplayName("Оплата тура. Поле 'Владелец' введено кириллицей")
    void shouldDeclineRequestForOwnerWrongFormatCyrillicInput() {
        CreditPaymentPage creditPaymentPage = new CreditPaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker(new Locale("ru-RU"));
        cardInfo.setOwner(faker.name().fullName());
        creditPaymentPage.getPaymentTypeSwitcher().buyCredit();
        creditPaymentPage.getPaymentForm().submitInfo(cardInfo);
        creditPaymentPage.getPaymentForm().verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'Владелец' введены только цифры")
    void shouldDeclineRequestForOwnerWrongFormatNumbersInput() {
        CreditPaymentPage creditPaymentPage = new CreditPaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setOwner("3123124");
        creditPaymentPage.getPaymentTypeSwitcher().buyCredit();
        creditPaymentPage.getPaymentForm().submitInfo(cardInfo);
        creditPaymentPage.getPaymentForm().verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'Владелец' введены спецсимволы")
    void shouldDeclineRequestForOwnerWrongFormatInputSpecialCharsInput() {
        PaymentPage paymentPage = new PaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setOwner("!!!%%%%%???");
        paymentPage.getPaymentTypeSwitcher().buy();
        paymentPage.getPaymentForm().submitInfo(cardInfo);
        paymentPage.getPaymentForm().verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура картой, действие которой закончилось")
    void shouldDeclineRequestForExpiderCard() {
        PaymentPage paymentPage = new PaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setYear(Integer.valueOf(LocalDate.now().minusYears(2).format(DateTimeFormatter.ofPattern("yy"))));
        paymentPage.getPaymentTypeSwitcher().buy();
        paymentPage.getPaymentForm().submitInfo(cardInfo);
        paymentPage.getPaymentForm().verifyYearExpired();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'CVV' введен слишком короткий код")
    void shouldDeclineRequestForWrongCvv() {
        PaymentPage paymentPage = new PaymentPage();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker();
        cardInfo.setCvv(faker.number().numberBetween(10, 99));
        paymentPage.getPaymentTypeSwitcher().buy();
        paymentPage.getPaymentForm().submitInfo(cardInfo);
        paymentPage.getPaymentForm().verifyWrongCvv();
    }

}

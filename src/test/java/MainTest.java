import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import data.CardInfo;
import database.Dao;
import pages.PaymentPage;
import pages.CreditPaymentPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.StartPage;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.*;

public class MainTest {
    private static final String URL = "http://localhost:8080/";

    @Test
    @DisplayName("Оплата тура с использованием карты со статусом 'APPROVED'")
    void shouldSubmitPaymentRequestApprovedCard() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.buy();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        paymentPage.submitInfo(cardInfo);
        paymentPage.verifySubmitOk();
        assertEquals("APPROVED", Dao.getLastPaymentStatus());
        assertEquals(Dao.getLastOrderId(), Dao.getLastTransactionId());
    }

    @Test
    @DisplayName("Оплата тура с использованием карты со статусом 'DECLINED'.")
    void shouldDeclinePaymentRequestDeclinedCard() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.buy();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        paymentPage.submitInfo(cardInfo);
        paymentPage.verifySubmitDecline();
        assertEquals("DECLINED", Dao.getLastPaymentStatus());
        assertEquals(Dao.getLastOrderId(), Dao.getLastTransactionId());
    }

    @Test
    @DisplayName("Оплата тура с использованием неизвестной карты.")
    void shouldDeclinePaymentRequestUnknownCard() throws SQLException {
        open(URL);
        Dao.clearAllTables();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.buy();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getUnknownCardInfo();
        paymentPage.submitInfo(cardInfo);
        paymentPage.verifySubmitDecline();
        assertEquals(Dao.getLastPaymentStatus(), "table is empty");
        assertEquals(Dao.getLastOrderId(), "table is empty");
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием карты со статусом 'APPROVED'")
    void shouldSubmitCreditPaymentRequestApprovedCard() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifySubmitOk();
        assertEquals("APPROVED", Dao.getLastCreditPaymentStatus());
        assertEquals(Dao.getLastOrderId(), Dao.getLastBankId());
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием карты со статусом 'DECLINED'")
    void shouldDeclineCreditPaymentRequestDeclinedCard() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifySubmitDecline();
        assertEquals("DECLINED", Dao.getLastCreditPaymentStatus());
        assertEquals(Dao.getLastOrderId(), Dao.getLastBankId());
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием неизвестной карты")
    void shouldDeclineCreditPaymentRequestUnknownCard() throws SQLException {
        open(URL);
        Dao.clearAllTables();
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getUnknownCardInfo();
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifySubmitDecline();
        assertEquals(Dao.getLastCreditPaymentStatus(), "table is empty");
        assertEquals(Dao.getLastOrderId(), "table is empty");
    }

    @Test
    @DisplayName("Оплата тура с использование карты неверного формата")
    void shouldDeclineRequestForCardWrongFormat() {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker();
        cardInfo.setCardNumber(faker.finance().creditCard(CreditCardType.AMERICAN_EXPRESS));
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifyWrongCardFormat();
    }

    @Test
    @DisplayName("Оплата тура. Месяц введен в неправильном формате - 'm' вместо 'mm'")
    void shouldDeclineRequestForMonthWrongFormat() {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setMonth("2");
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifyWrongMonthFormat();
    }

    @Test
    @DisplayName("Оплата тура. Поле 'Владелец' введено кириллицей")
    void shouldDeclineRequestForOwnerWrongFormatCyrillicInput() {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker(new Locale("ru-RU"));
        cardInfo.setOwner(faker.name().fullName());
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'Владелец' введены только цифры")
    void shouldDeclineRequestForOwnerWrongFormatNumbersInput() {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setOwner("3123124");
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'Владелец' введены спецсимволы")
    void shouldDeclineRequestForOwnerWrongFormatInputSpecialCharsInput() {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setOwner("!!!%%%%%???");
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура картой, действие которой закончилось")
    void shouldDeclineRequestForExpiderCard() {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setYear(Integer.valueOf(LocalDate.now().minusYears(2).format(DateTimeFormatter.ofPattern("yy"))));
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifyYearExpired();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'CVV' введен слишком короткий код")
    void shouldDeclineRequestForWrongCvv() {
        open(URL);
        StartPage startPage = new StartPage();
        CreditPaymentPage creditPaymentPage = startPage.buyCredit();
        CardInfo cardInfo = new CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker();
        cardInfo.setCvv(faker.number().numberBetween(10, 99));
        creditPaymentPage.submitInfo(cardInfo);
        creditPaymentPage.verifyWrongCvv();
    }

}

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import data.DataHelper;
import database.Dao;
import database.DataBase;
import objects.Credit;
import objects.NotCredit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import objects.StartPage;

import javax.swing.text.DateFormatter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.*;

public class MainTest {
    private static final String URL = "http://localhost:8080/";
    private static final DataBase dataBase = DataBase.MYSQL;

    @Test
    @DisplayName("Оплата тура НЕ в кредит с использованием карты со статусом 'APPROVED'")
    void shouldSubmitNotCreditRequestApprovedCard() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buy();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        notCredit.submitInfo(cardInfo);
        notCredit.verifySubmitOk();
        assertEquals("APPROVED", Dao.getLastStatusNotCredit(dataBase));
        assertEquals(Dao.getLastOrderId(dataBase), Dao.getLastTransactionId(dataBase));
    }

    @Test
    @DisplayName("Оплата тура НЕ в кредит с использованием карты со статусом 'DECLINED'.")
    void shouldDeclineNotCreditRequestDeclinedCard() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buy();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        notCredit.submitInfo(cardInfo);
        notCredit.verifySubmitDecline();
        assertEquals("DECLINED", Dao.getLastStatusNotCredit(dataBase));
        assertEquals(Dao.getLastOrderId(dataBase), Dao.getLastTransactionId(dataBase));
    }

    @Test
    @DisplayName("Оплата тура НЕ в кредит с использованием неизвестной карты.")
    void shouldDeclineNotCreditRequestUnknownCard() {
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buy();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getUnknownCardInfo();
        notCredit.submitInfo(cardInfo);
        notCredit.verifySubmitDecline();
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием карты со статусом 'APPROVED'")
    void shouldSubmitCreditRequestApprovedCard() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        Credit credit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        credit.submitInfo(cardInfo);
        credit.verifySubmitOk();
        assertEquals("APPROVED", Dao.getLastStatusCredit(dataBase));
        assertEquals(Dao.getLastOrderId(dataBase), Dao.getLastBankId(dataBase));
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием карты со статусом 'DECLINED'")
    void shouldDeclineCreditRequestDeclinedCard() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        Credit credit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        credit.submitInfo(cardInfo);
        credit.verifySubmitDecline();
        assertEquals("DECLINED", Dao.getLastStatusCredit(dataBase));
        assertEquals(Dao.getLastOrderId(dataBase), Dao.getLastBankId(dataBase));
    }

    @Test
    @DisplayName("Оплата тура в кредит с использованием неизвестной карты")
    void shouldDeclineCreditRequestUnknownCard() {
        open(URL);
        StartPage startPage = new StartPage();
        Credit credit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getUnknownCardInfo();
        credit.submitInfo(cardInfo);
        credit.verifySubmitDecline();
    }

    @Test
    @DisplayName("Оплата тура с использование карты неверного формата")
    void shouldDeclineRequestForCardWrongFormat(){
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker();
        cardInfo.setCardNumber(faker.finance().creditCard(CreditCardType.AMERICAN_EXPRESS));
        notCredit.submitInfo(cardInfo);
        notCredit.verifyWrongCardFormat();
    }

    @Test
    @DisplayName("Оплата тура. Месяц введен в неправильном формате - 'm' вместо 'mm'")
    void shouldDeclineRequestForMonthWrongFormat(){
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setMonth("2");
        notCredit.submitInfo(cardInfo);
        notCredit.verifyWrongMonthFormat();
    }

    @Test
    @DisplayName("Оплата тура. Поле 'Владелец' введено кириллицей")
    void shouldDeclineRequestForOwnerWrongFormatCyrillicInput(){
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker(new Locale("ru-RU"));
        cardInfo.setOwner(faker.name().fullName());
        notCredit.submitInfo(cardInfo);
        notCredit.verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'Владелец' введены только цифры")
    void shouldDeclineRequestForOwnerWrongFormatNumbersInput(){
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setOwner("3123124");
        notCredit.submitInfo(cardInfo);
        notCredit.verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'Владеле' введены спецсимволы")
    void shouldDeclineRequestForOwnerWrongFormatInputSpecialCharsInput(){
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setOwner("!!!%%%%%???");
        notCredit.submitInfo(cardInfo);
        notCredit.verifyWrongOwnerFormat();
    }

    @Test
    @DisplayName("Оплата тура картой, действие которой закончилось")
    void shouldDeclineRequestForExpiderCard(){
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        cardInfo.setYear(Integer.valueOf(LocalDate.now().minusYears(2).format(DateTimeFormatter.ofPattern("yy"))));
        notCredit.submitInfo(cardInfo);
        notCredit.verifyYearExpired();
    }

    @Test
    @DisplayName("Оплата тура. В поле 'CVV' введен слишком короткий код")
    void shouldDeclineRequestForWrongCvv(){
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        Faker faker = new Faker();
        cardInfo.setCvv(faker.number().numberBetween(10, 99));
        notCredit.submitInfo(cardInfo);
        notCredit.verifyWrongCvv();
    }

}

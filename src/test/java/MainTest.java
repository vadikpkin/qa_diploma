import data.DataHelper;
import database.Dao;
import objects.Credit;
import objects.NotCredit;
import org.junit.jupiter.api.Test;
import objects.StartPage;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.*;

public class MainTest {
    private static final String URL = "http://localhost:8080/";
    ///Tests for NOT credit purchase
    @Test
    void shouldSubmitNotCreditRequestApprovedCardPostgresSql() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buy();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        notCredit.submitInfo(cardInfo);
        notCredit.verifySubmitOk();
        assertEquals("APPROVED", Dao.getLastStatusNotCredit());
    }

    @Test
    void shouldDeclineNotCreditRequestDeclinedCardPostgresSql() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buy();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        notCredit.submitInfo(cardInfo);
        notCredit.verifySubmitDecline();
        assertEquals("DECLINED", Dao.getLastStatusNotCredit());
    }

    @Test
    void shouldDeclineNotCreditRequestUnknownCardPostgresSql() {
        open(URL);
        StartPage startPage = new StartPage();
        NotCredit notCredit = startPage.buy();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        cardInfo.setCardNumber("4444 4444 4444 4443");
        notCredit.submitInfo(cardInfo);
        notCredit.verifySubmitDecline();
    }

    ///Tests for credit purchase
    @Test
    void shouldSubmitCreditRequestApprovedCardPostgresSql() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        Credit credit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getApprovedCardInfo();
        credit.submitInfo(cardInfo);
        credit.verifySubmitOk();
        assertEquals("APPROVED", Dao.getLastStatusCredit());
    }

    @Test
    void shouldDeclineCreditRequestDeclinedCardPostgresSql() throws SQLException {
        open(URL);
        StartPage startPage = new StartPage();
        Credit credit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        credit.submitInfo(cardInfo);
        credit.verifySubmitDecline();
        assertEquals("DECLINED", Dao.getLastStatusCredit());
    }
// Tests unknown card purchase
    @Test
    void shouldDeclineCreditRequestUnknownCardPostgresSql() {
        open(URL);
        StartPage startPage = new StartPage();
        Credit credit = startPage.buyCredit();
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo();
        cardInfo = cardInfo.getDeclinedCardInfo();
        cardInfo.setCardNumber("4444 4444 4444 4443");
        credit.submitInfo(cardInfo);
        credit.verifySubmitDecline();
    }


}

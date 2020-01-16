package data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Data
public class CardInfo {
    private static String approvedCardNumber = "4444 4444 4444 4441";
    private static String declinedCardNumber = "4444 4444 4444 4442";
    private static Faker faker = new Faker(new Locale("en-US"));

    private Date date = faker.date().future(1000, TimeUnit.DAYS);
    private String month = new SimpleDateFormat("MM").format(date);
    private int year = Integer.parseInt(new SimpleDateFormat("yy").format(date));
    private String owner = faker.name().fullName();
    private int cvv = faker.number().numberBetween(100, 999);
    private String cardNumber;

    public CardInfo getApprovedCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(approvedCardNumber);
        return cardInfo;
    }

    public CardInfo getDeclinedCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(declinedCardNumber);
        return cardInfo;
    }

    public CardInfo getUnknownCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(faker.finance().creditCard(CreditCardType.MASTERCARD));
        return cardInfo;
    }

}
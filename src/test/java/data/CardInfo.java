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
    String cardNumber;
    String month;
    int year;
    String owner;
    int cvv;

    public CardInfo getApprovedCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(approvedCardNumber);
        Faker faker = new Faker(new Locale("en-US"));
        Date date = faker.date().future(1000, TimeUnit.DAYS);
        cardInfo.setMonth(new SimpleDateFormat("MM").format(date));
        cardInfo.setYear(Integer.parseInt(new SimpleDateFormat("yy").format(date)));
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCvv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

    public CardInfo getDeclinedCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(declinedCardNumber);
        Faker faker = new Faker(new Locale("en-US"));
        Date date = faker.date().future(1000, TimeUnit.DAYS);
        cardInfo.setMonth(new SimpleDateFormat("MM").format(date));
        cardInfo.setYear(Integer.parseInt(new SimpleDateFormat("yy").format(date)));
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCvv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

    public CardInfo getUnknownCardInfo() {
        CardInfo cardInfo = new CardInfo();
        Faker faker = new Faker(new Locale("en-US"));
        cardInfo.setCardNumber(faker.finance().creditCard(CreditCardType.MASTERCARD));
        Date date = faker.date().future(1000, TimeUnit.DAYS);
        cardInfo.setMonth(new SimpleDateFormat("MM").format(date));
        cardInfo.setYear(Integer.parseInt(new SimpleDateFormat("yy").format(date)));
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCvv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

}
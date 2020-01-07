package data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Data;
import java.util.Locale;

public class DataHelper {
    String approvedCardNumber = "4444 4444 4444 4441";
    String declinedCardNumber =  "4444 4444 4444 4442";


    @Data
    public static class CardInfo {
        String cardNumber;
        String month;
        int year;
        String owner;
        int csv;
    public CardInfo getApprovedCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber("4444 4444 4444 4441");
        Faker faker = new Faker(new Locale("en-US"));
        cardInfo.setMonth("04");
        cardInfo.setYear(24);
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCsv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

    public CardInfo getDeclinedCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber("4444 4444 4444 4442");
        Faker faker = new Faker(new Locale("en-US"));
        cardInfo.setMonth("04");
        cardInfo.setYear(24);
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCsv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

    public CardInfo getUnknownCardInfo() {
        CardInfo cardInfo = new CardInfo();
        Faker faker = new Faker(new Locale("en-US"));
        cardInfo.setCardNumber(faker.finance().creditCard(CreditCardType.MASTERCARD));
        cardInfo.setMonth("04");
        cardInfo.setYear(24);
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCsv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

    }
}

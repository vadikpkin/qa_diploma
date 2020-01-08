package data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static String approvedCardNumber = "4444 4444 4444 4441";
    private static String declinedCardNumber =  "4444 4444 4444 4442";

    @Data
    public static class CardInfo {
        String cardNumber;
        String month;
        int year;
        String owner;
        int cvv;

    public CardInfo getApprovedCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(approvedCardNumber);
        Faker faker = new Faker(new Locale("en-US"));
        cardInfo.setMonth(String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("MM"))));
        cardInfo.setYear(Integer.valueOf(LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy"))));
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCvv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

    public CardInfo getDeclinedCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(declinedCardNumber);
        Faker faker = new Faker(new Locale("en-US"));
        cardInfo.setMonth(String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("MM"))));
        cardInfo.setYear(Integer.valueOf(LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy"))));
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCvv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

    public CardInfo getUnknownCardInfo() {
        CardInfo cardInfo = new CardInfo();
        Faker faker = new Faker(new Locale("en-US"));
        cardInfo.setCardNumber(faker.finance().creditCard(CreditCardType.MASTERCARD));
        cardInfo.setMonth(String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("MM"))));
        cardInfo.setYear(Integer.valueOf(LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy"))));
        cardInfo.setOwner(faker.name().fullName());
        cardInfo.setCvv(faker.number().numberBetween(100, 999));
        return cardInfo;
    }

    }
}

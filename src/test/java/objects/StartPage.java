package objects;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class StartPage {
    private static final SelenideElement buyCreditBtn = $(new Selectors.ByText("Купить в кредит"));
    private static final SelenideElement buyBtn = $(new Selectors.ByText("Купить"));

    public Credit buyCredit(){
        buyCreditBtn.click();
        return new Credit();
    }

    public NotCredit buy(){
        buyBtn.click();
        return new NotCredit();
    }
}

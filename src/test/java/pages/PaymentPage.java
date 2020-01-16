package pages;

import lombok.Data;

@Data
public class PaymentPage {
    private PaymentTypeSwitcher paymentTypeSwitcher;

    private PaymentForm paymentForm;

    public PaymentPage() {
        this.paymentTypeSwitcher = new PaymentTypeSwitcher();
        this.paymentForm = new PaymentForm();
    }

    public PaymentTypeSwitcher getPaymentTypeSwitcher() {
        return paymentTypeSwitcher;
    }

    public PaymentForm getPaymentForm() {
        return paymentForm;
    }
}

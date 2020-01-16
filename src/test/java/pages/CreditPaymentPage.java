package pages;

public class CreditPaymentPage {
    private PaymentTypeSwitcher paymentTypeSwitcher;

    private PaymentForm paymentForm;

    public CreditPaymentPage() {
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

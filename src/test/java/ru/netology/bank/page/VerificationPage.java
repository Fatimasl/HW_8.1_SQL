package ru.netology.bank.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bank.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorEl = $("[data-test-id=error-notification] .notification__content");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void tryToVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        tryToVerify(verificationCode);
        return new DashboardPage();
    }

    public void errorAfterInvalidVerification(String errorText) {
        errorEl.shouldBe(visible);
        errorEl.shouldHave(text(errorText));
    }
}

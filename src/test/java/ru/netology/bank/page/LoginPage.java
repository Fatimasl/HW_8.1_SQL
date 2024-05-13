package ru.netology.bank.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bank.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    SelenideElement loginEl = $("[data-test-id=login] input");
    SelenideElement passwordEl = $("[data-test-id=password] input");
    SelenideElement actionLoginEl = $("[data-test-id=action-login]");
    SelenideElement errorEl = $("[data-test-id=error-notification] .notification__content");

    public void tryToLogin(DataHelper.AuthInfo info){
        loginEl.setValue(info.getLogin());
        passwordEl.setValue(info.getPassword());
        actionLoginEl.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        tryToLogin(info);
        return new VerificationPage();
    }

    public void errorAfterInvalidLogin(String errorText) {
        errorEl.shouldBe(visible);
        errorEl.shouldHave(text(errorText));
    }
}
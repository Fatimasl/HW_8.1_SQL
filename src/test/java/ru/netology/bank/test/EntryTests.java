package ru.netology.bank.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.bank.data.DataHelper;
import ru.netology.bank.data.SQLHelper;
import ru.netology.bank.page.DashboardPage;
import ru.netology.bank.page.LoginPage;
import ru.netology.bank.page.VerificationPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class EntryTests {
    LoginPage loginPage;

    @AfterEach
    public void tearDown() {
        SQLHelper.cleanAuthCodes();
    }

    @AfterAll
    public static void tearDownAll() {
        SQLHelper.cleanDatabase();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        open("http://localhost:9999");
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Successful entry")
    public void successfulEntry() {
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        verificationPage.validVerify(SQLHelper.getVerificationCode());
    }

    @Test
    @DisplayName("mustFailedByInvalidLoginPassword")
    public void mustFailedByInvalidLoginPassword() {
        DataHelper.AuthInfo authInfo = DataHelper.generateRandomAuthInfo();
        loginPage.tryToLogin(authInfo);
        loginPage.errorAfterInvalidLogin("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("mustFailedByInvalidVerificationCode")
    public void mustFailedByInvalidVerificationCode() {
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        verificationPage.tryToVerify(DataHelper.generateRandomVerificationCode());
        verificationPage.errorAfterInvalidVerification("Ошибка! Неверно указан код! Попробуйте еще раз.");
    }
}

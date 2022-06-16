package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.pages.PaymentMethod;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditGateTest {
    public static String url = System.getProperty("sut.url");

    @BeforeEach
    public void openPage() {
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SqlHelper.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void creditPositiveAllFieldValidApproved() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", SqlHelper.getCreditRequestStatus());
    }

    @Test
    void creditPositiveAllFieldValidDeclined() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", SqlHelper.getCreditRequestStatus());
    }

    @Test
    void creditNegativeAllFieldEmpty() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", SqlHelper.getOrderCount());
    }


    @Test
    void creditNegativeMounthFieldEmpty() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardWithoutMounth());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeNumberFieldEmpty() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardWithoutNumber());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearFieldEmpty() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardWithoutYear());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeHolderFieldEmpty() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardWithoutHolder());
        payment.waitNotificationRequiredFieldError();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeCVVFieldEmpty() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardWithoutCVV());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeNumberCard15Symbols() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getNumberCard15Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeCardNotInDatabase() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth1Symbol() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonthOver12() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00ThisYear() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00OverThisYear() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeYear00() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeYear1Symbol() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearUnderThisYear() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearOverThisYearOn6() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv1Symbol() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv2Symbols() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwner1Word() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerCirillic() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardHolderCirillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerNumeric() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerSpecialSymbols() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelper.getOrderCount());
    }
}


import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class FirstAt { /*ВСЕГО 14 тестов за 1 мин. 12 сек.*/
    private static WebDriver driver;
    private static String NumberOrder = "458211";
    private static String CardHolder= "JOHN FELLING";
    private static String Sum = "291.86";
    private static String CardType;
    private static String Number;
    private static String Answer;
    private static String FirstNum;
    private static String SecondNum;
    private static final String [] FieldCardNumber = {"4000000000000002","5555555555554444","4000000000000044","4000000000000077","5555555555554477","4000000000000051"};
    private final WebElement orderNumber = driver.findElement(By.id("order-number"));
    private final WebElement cardNumberValue = driver.findElement(By.id("input-card-number"));
    private final WebElement CardHolderName = driver.findElement(By.id("input-card-holder"));
    private final WebElement actionSubmit = driver.findElement(By.id("action-submit"));
    private final WebElement orderSum = driver.findElement(By.id("total-amount"));
    private final WebElement cardExpiresMonth = driver.findElement(By.id("card-expires-month"));
    private final WebElement cardExpiresYear = driver.findElement(By.id("card-expires-year"));
    private final WebElement inputCardCVC = driver.findElement(By.id("input-card-cvc"));
    private final WebElement Question = driver.findElement(By.id("cvc-hint-toggle"));

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\TestingFiles\\group7\\MelnikovPR3\\src\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
        driver.manage().window().fullscreen();
        System.out.println("Test start");
    }

    @Test
    public void t01checkOrderNumber() {
        Assert.assertEquals(NumberOrder, orderNumber.getText());
    }

    @Test
    public void t02checkOrderSum() {
                Assert.assertEquals(Sum, orderSum.getText());
    }

    @Test
    public void t03checkCardNumberField() {
        String[] FieldCardsNotValid = {"440000000000000024","3400000000000000","358976", "367879","375623","394323","51567","55565","50323","56454","595645","60677","615678","635764","643879","675644","62344","65234","69","12123","09823","212314","7789567","87978","98878"};
        String[] FieldCardsValid = {"4000000000000002", "4000000000000044", "4000000000000077", "4000000000000051", "5555555555554444", "5555555555554477"};
        Actions actions = new Actions(driver);
        actions.click(actionSubmit).build().perform();
        Assert.assertEquals("error", driver.findElement(By.id("input-card-number")).getAttribute("class"));
        for (int i = 0; i < 4; i++) {
            cardNumberValue.sendKeys(FieldCardsValid[i]);
            Assert.assertEquals("visa identified valid", driver.findElement(By.id("input-card-number")).getAttribute("class"));
            cardNumberValue.clear();
        }
        for (int i = 4; i < 6; i++) {
            cardNumberValue.sendKeys(FieldCardsValid[i]);
            Assert.assertEquals("mastercard identified valid", driver.findElement(By.id("input-card-number")).getAttribute("class"));
            cardNumberValue.clear();
        }
        for (int i = 0; i <25; i++) {
            cardNumberValue.sendKeys(FieldCardsNotValid[i]);
            Number = FieldCardsNotValid[i];
            FirstNum = Number.substring(0,1);
            SecondNum = Number.substring(1,2);
            switch (FirstNum) {
                case "4":
                    Answer = "identified error visa";
                break;
                case "3":
                    switch (SecondNum){
                        case "4":
                        case "7":
                            Answer = "error amex identified";
                        break;
                        case "5":
                            Answer = "error jcb identified";
                        break;
                        case "6":
                            Answer = "error dinersclub identified";
                        break;
                        default:
                            Answer = "error unknown";
                        break;
                    }
                    break;
                case "5":
                    switch (SecondNum){
                        case "1":case "2":case "3":case "4":case "5":
                            Answer = "error mastercard identified";
                            break;
                        default:
                            Answer = "error maestro identified";
                            break;
                    }
                    break;
                case "6":
                    switch (SecondNum){
                        case "0": case "1":case "3":case "4":case "7":
                            Answer = "error maestro identified";
                            break;
                        case "2":
                            Answer = "error unionpay identified";
                            break;
                        case "5":
                            Answer = "error discover identified";
                            break;
                        default:
                            Answer = "error unknown";
                            break;
                    }
                    break;
                default:
                    Answer = "error unknown";
            }
            Assert.assertEquals(Answer, driver.findElement(By.id("input-card-number")).getAttribute("class"));
            cardNumberValue.clear();
        }
    }

    @Test
    public void t05checkCardHolderField(){
        String [] FieldCardHolder = {"NICKM", "NICK", "NIKOLAY MELNIKOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOV", "#MELNIKOV","NIK","NIK0LAY","123","#$@%","NIKOLAY MELNIKOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOV"};
        Actions actions = new Actions(driver);
        actions.click(actionSubmit).build().perform();
        Assert.assertEquals("error", driver.findElement(By.id("input-card-holder")).getAttribute("class"));
        for (int i = 0; i < 9; i++) {
            CardHolderName.sendKeys(FieldCardHolder[i]);
            if (i == 0 || i == 1 || i == 2)
                Assert.assertEquals("valid", driver.findElement(By.id("input-card-holder")).getAttribute("class"));
            else
                Assert.assertEquals("error", driver.findElement(By.id("input-card-holder")).getAttribute("class"));
               CardHolderName.clear();
        }
    }

    @Test
    public void t07checkMonthYearFields2() {
        int Y, M, Y1, M1;
        Actions actions = new Actions(driver);
        actions.click(actionSubmit).build().perform();
        Assert.assertEquals("error", driver.findElement(By.id("card-expires-month")).getAttribute("class"));
        Assert.assertEquals("error", driver.findElement(By.id("card-expires-year")).getAttribute("class"));
        Select SelectMonth = new Select(cardExpiresMonth);
        Select SelectYear = new Select(cardExpiresYear);
        Date date = new Date();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        M = ld.getMonthValue();
        Y = ld.getDayOfYear();
        Y1 = Y;
        for (int i=1; i<4; i++){
            SelectYear.selectByIndex(i);
            M1 = M -1;
            for (int j=1; j<6; j++){
                if (M1>12)
                    break;
                SelectMonth.selectByValue(String.valueOf(M1));
                if ((Y == Y1)&(M > M1)){
                    Assert.assertEquals("error", driver.findElement(By.id("card-expires-month")).getAttribute("class"));
                    Assert.assertEquals("valid error", driver.findElement(By.id("card-expires-year")).getAttribute("class"));
                } else {
                    Assert.assertEquals("valid", driver.findElement(By.id("card-expires-month")).getAttribute("class"));
                    Assert.assertEquals("valid", driver.findElement(By.id("card-expires-year")).getAttribute("class"));
                }
                M1 = M1+1;
            }
            Y1 = Y1 + 1;
            }
        }

    @Test
    public void t08checkCVC(){
        Actions actions = new Actions(driver);
        actions.click(actionSubmit).build().perform();
        Assert.assertEquals("error", driver.findElement(By.id("input-card-cvc")).getAttribute("class"));
        inputCardCVC.sendKeys("1");
        Assert.assertEquals("error", driver.findElement(By.id("input-card-cvc")).getAttribute("class"));
        actions.click(actionSubmit).build().perform();
        inputCardCVC.clear();
        inputCardCVC.sendKeys("14");
        Assert.assertEquals("error", driver.findElement(By.id("input-card-cvc")).getAttribute("class"));
        actions.click(actionSubmit).build().perform();
        inputCardCVC.clear();
        inputCardCVC.sendKeys("123");
        Assert.assertEquals("valid", driver.findElement(By.id("input-card-cvc")).getAttribute("class"));
        actions.click(actionSubmit).build().perform();
        inputCardCVC.clear();
    }

    @Test
    public void t08checkQuestion(){
        Actions actions = new Actions(driver);
        actions.moveToElement(Question).build().perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void t09checkConfirmedCardWith3D (){
        Select SelectMonth = new Select(cardExpiresMonth);
        Select SelectYear = new Select(cardExpiresYear);
        cardNumberValue.sendKeys(FieldCardNumber[0]);
        CardHolderName.sendKeys(CardHolder);
        SelectMonth.selectByIndex(7);
        SelectYear.selectByValue("2021");
        inputCardCVC.sendKeys("234");
        actionSubmit.click();
        CardType = "VISA";
        driver.manage().window().fullscreen();
        WebElement SecureInButton = driver.findElement(By.id("success"));
        SecureInButton.click();
        driver.manage().window().fullscreen();
        WebElement PaymentItemOrderNumber = driver.findElement(By.id("payment-item-ordernumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(NumberOrder, PaymentItemOrderNumber.getText());
        WebElement PaymentItemStatus = driver.findElement(By.id("payment-item-status")).findElement(By.className("payment-info-item-data"));
        WebElement PaymentItemCardHolder = driver.findElement(By.id("payment-item-cardholder")).findElement(By.className("payment-info-item-data"));
        WebElement PaymentItemCardNumber = driver.findElement(By.id("payment-item-cardnumber")).findElement(By.className("payment-info-item-data"));
        WebElement PaymentItemTotal = driver.findElement(By.id("payment-item-total-amount"));
        Assert.assertEquals("Confirmed", PaymentItemStatus.getText());
        Assert.assertEquals(CardHolder, PaymentItemCardHolder.getText());
        Assert.assertEquals("..."+FieldCardNumber[0].substring(12), PaymentItemCardNumber.getText());
        Assert.assertEquals(Sum, PaymentItemTotal.getText());
        Assert.assertEquals("Success", driver.findElement(By.id("payment-status-title")).getText());
        Assert.assertEquals(CardType,driver.findElement(By.id("payment-item-cardtype")).findElement(By.className("payment-info-item-data")).getText());
        driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
    }

    @Test
    public void t10checkDeclineCardWith3D (){
        CardType = "MASTERCARD";
        Select SelectMonth = new Select(cardExpiresMonth);
        Select SelectYear = new Select(cardExpiresYear);
        cardNumberValue.sendKeys(FieldCardNumber[1]);
        CardHolderName.sendKeys(CardHolder);
        SelectMonth.selectByIndex(7);
        SelectYear.selectByValue("2021");
        inputCardCVC.sendKeys("234");
        actionSubmit.click();
        driver.manage().window().fullscreen();
        WebElement SecureInButton = driver.findElement(By.id("success"));
        SecureInButton.click();
        driver.manage().window().fullscreen();
        WebElement PaymentItemOrderNumber = driver.findElement(By.id("payment-item-ordernumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(NumberOrder, PaymentItemOrderNumber.getText());
        WebElement PaymentItemStatus = driver.findElement(By.id("payment-item-status")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("Declined by issuing bank", PaymentItemStatus.getText());
        WebElement PaymentItemCardHolder = driver.findElement(By.id("payment-item-cardholder")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(CardHolder, PaymentItemCardHolder.getText());
        WebElement PaymentItemCardNumber = driver.findElement(By.id("payment-item-cardnumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("..."+FieldCardNumber[1].substring(12), PaymentItemCardNumber.getText());
        WebElement PaymentItemTotal = driver.findElement(By.id("payment-item-total-amount"));
        Assert.assertEquals(Sum, PaymentItemTotal.getText());
        Assert.assertEquals("Decline", driver.findElement(By.id("payment-status-title")).getText());
        Assert.assertEquals(CardType,driver.findElement(By.id("payment-item-cardtype")).findElement(By.className("payment-info-item-data")).getText());
    driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
    }

    @Test
    public void t11checkPendingCardWith3D (){
        CardType = "VISA";
        Select SelectMonth = new Select(cardExpiresMonth);
        Select SelectYear = new Select(cardExpiresYear);
        cardNumberValue.sendKeys(FieldCardNumber[2]);
        CardHolderName.sendKeys(CardHolder);
        SelectMonth.selectByIndex(7);
        SelectYear.selectByValue("2021");
        inputCardCVC.sendKeys("234");
        actionSubmit.click();
        driver.manage().window().fullscreen();
        WebElement SecureInButton = driver.findElement(By.id("success"));
        SecureInButton.click();
        driver.manage().window().fullscreen();
        WebElement PaymentItemOrderNumber = driver.findElement(By.id("payment-item-ordernumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(NumberOrder, PaymentItemOrderNumber.getText());
        WebElement PaymentItemStatus = driver.findElement(By.id("payment-item-status")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("CONFIRMED", PaymentItemStatus.getText());
        WebElement PaymentItemCardHolder = driver.findElement(By.id("payment-item-cardholder")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(CardHolder, PaymentItemCardHolder.getText());
        WebElement PaymentItemCardNumber = driver.findElement(By.id("payment-item-cardnumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("..."+FieldCardNumber[2].substring(12), PaymentItemCardNumber.getText());
        WebElement PaymentItemTotal = driver.findElement(By.id("payment-item-total-amount"));
        Assert.assertEquals(Sum, PaymentItemTotal.getText());
        Assert.assertEquals("Info", driver.findElement(By.id("payment-status-title")).getText());
        Assert.assertEquals(CardType,driver.findElement(By.id("payment-item-cardtype")).findElement(By.className("payment-info-item-data")).getText());
        driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
    }

    @Test
    public void t12checkCardWith3DError (){
        for (int i=0; i<3;i++) {
            WebElement cardNumberValue = driver.findElement(By.id("input-card-number"));
            WebElement CardHolderName = driver.findElement(By.id("input-card-holder"));
            WebElement actionSubmit = driver.findElement(By.id("action-submit"));
            WebElement cardExpiresMonth = driver.findElement(By.id("card-expires-month"));
            WebElement cardExpiresYear = driver.findElement(By.id("card-expires-year"));
            Select SelectMonth = new Select(cardExpiresMonth);
            Select SelectYear = new Select(cardExpiresYear);
            WebElement inputCardCVC = driver.findElement(By.id("input-card-cvc"));driver.manage().window().fullscreen();
            cardNumberValue.sendKeys(FieldCardNumber[i]);
            CardHolderName.sendKeys(CardHolder);
            SelectMonth.selectByIndex(7);
            SelectYear.selectByValue("2021");
            inputCardCVC.sendKeys("234");
            actionSubmit.click();
            WebElement SecureInButton = driver.findElement(By.id("failure"));
            SecureInButton.click();
            driver.manage().window().fullscreen();
            WebElement PaymentItemOrderNumber = driver.findElement(By.id("payment-item-ordernumber")).findElement(By.className("payment-info-item-data"));
            Assert.assertEquals(NumberOrder, PaymentItemOrderNumber.getText());
            WebElement PaymentItemStatus = driver.findElement(By.id("payment-item-status")).findElement(By.className("payment-info-item-data"));
            Assert.assertEquals("Declined by issuing bank", PaymentItemStatus.getText());
            WebElement PaymentItemCardHolder = driver.findElement(By.id("payment-item-cardholder")).findElement(By.className("payment-info-item-data"));
            Assert.assertEquals(CardHolder, PaymentItemCardHolder.getText());
            WebElement PaymentItemCardNumber = driver.findElement(By.id("payment-item-cardnumber")).findElement(By.className("payment-info-item-data"));
            Assert.assertEquals("..." + FieldCardNumber[i].substring(12), PaymentItemCardNumber.getText());
            WebElement PaymentItemTotal = driver.findElement(By.id("payment-item-total-amount"));
            Assert.assertEquals(Sum, PaymentItemTotal.getText());
            Assert.assertEquals("Decline", driver.findElement(By.id("payment-status-title")).getText());
            driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
        }
    }

    @Test
    public void t13checkConfirmedCardWithOut3D (){
        Select SelectMonth = new Select(cardExpiresMonth);
        Select SelectYear = new Select(cardExpiresYear);
        cardNumberValue.sendKeys(FieldCardNumber[3]);
        CardHolderName.sendKeys(CardHolder);
        SelectMonth.selectByIndex(7);
        SelectYear.selectByValue("2021");
        inputCardCVC.sendKeys("234");
        actionSubmit.click();
        CardType = "VISA";
        driver.manage().window().fullscreen();
        WebElement PaymentItemOrderNumber = driver.findElement(By.id("payment-item-ordernumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(NumberOrder, PaymentItemOrderNumber.getText());
        WebElement PaymentItemStatus = driver.findElement(By.id("payment-item-status")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("Confirmed", PaymentItemStatus.getText());
        WebElement PaymentItemCardHolder = driver.findElement(By.id("payment-item-cardholder")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(CardHolder, PaymentItemCardHolder.getText());
        WebElement PaymentItemCardNumber = driver.findElement(By.id("payment-item-cardnumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("..."+FieldCardNumber[3].substring(12), PaymentItemCardNumber.getText());
        WebElement PaymentItemTotal = driver.findElement(By.id("payment-item-total-amount"));
        Assert.assertEquals(Sum, PaymentItemTotal.getText());
        Assert.assertEquals("Success", driver.findElement(By.id("payment-status-title")).getText());
        Assert.assertEquals(CardType,driver.findElement(By.id("payment-item-cardtype")).findElement(By.className("payment-info-item-data")).getText());
        driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
    }

    @Test
    public void t14checkDeclineCardWithOut3D (){
        Select SelectMonth = new Select(cardExpiresMonth);
        Select SelectYear = new Select(cardExpiresYear);
        cardNumberValue.sendKeys(FieldCardNumber[4]);
        CardHolderName.sendKeys(CardHolder);
        SelectMonth.selectByIndex(7);
        SelectYear.selectByValue("2021");
        inputCardCVC.sendKeys("234");
        actionSubmit.click();
        CardType = "MASTERCARD";
        driver.manage().window().fullscreen();
        WebElement PaymentItemOrderNumber = driver.findElement(By.id("payment-item-ordernumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(NumberOrder, PaymentItemOrderNumber.getText());
        WebElement PaymentItemStatus = driver.findElement(By.id("payment-item-status")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("Declined by issuing bank", PaymentItemStatus.getText());
        WebElement PaymentItemCardHolder = driver.findElement(By.id("payment-item-cardholder")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(CardHolder, PaymentItemCardHolder.getText());
        WebElement PaymentItemCardNumber = driver.findElement(By.id("payment-item-cardnumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("..."+FieldCardNumber[4].substring(12), PaymentItemCardNumber.getText());
        WebElement PaymentItemTotal = driver.findElement(By.id("payment-item-total-amount"));
        Assert.assertEquals(Sum, PaymentItemTotal.getText());
        Assert.assertEquals("Decline", driver.findElement(By.id("payment-status-title")).getText());
        Assert.assertEquals(CardType,driver.findElement(By.id("payment-item-cardtype")).findElement(By.className("payment-info-item-data")).getText());
        driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
    }

    @Test
    public void t15checkPendingCardWithOut3D (){
        Select SelectMonth = new Select(cardExpiresMonth);
        Select SelectYear = new Select(cardExpiresYear);
        cardNumberValue.sendKeys(FieldCardNumber[5]);
        CardHolderName.sendKeys(CardHolder);
        SelectMonth.selectByIndex(7);
        SelectYear.selectByValue("2021");
        inputCardCVC.sendKeys("234");
        actionSubmit.click();
        CardType = "VISA";
        driver.manage().window().fullscreen();
        WebElement PaymentItemOrderNumber = driver.findElement(By.id("payment-item-ordernumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(NumberOrder, PaymentItemOrderNumber.getText());
        WebElement PaymentItemStatus = driver.findElement(By.id("payment-item-status")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("CONFIRMED", PaymentItemStatus.getText());
        WebElement PaymentItemCardHolder = driver.findElement(By.id("payment-item-cardholder")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals(CardHolder, PaymentItemCardHolder.getText());
        WebElement PaymentItemCardNumber = driver.findElement(By.id("payment-item-cardnumber")).findElement(By.className("payment-info-item-data"));
        Assert.assertEquals("..."+FieldCardNumber[5].substring(12), PaymentItemCardNumber.getText());
        WebElement PaymentItemTotal = driver.findElement(By.id("payment-item-total-amount"));
        Assert.assertEquals(Sum, PaymentItemTotal.getText());
        Assert.assertEquals("Info", driver.findElement(By.id("payment-status-title")).getText());
        Assert.assertEquals(CardType,driver.findElement(By.id("payment-item-cardtype")).findElement(By.className("payment-info-item-data")).getText());
        driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
    }

    @AfterClass
    public static void tearDown() {
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.quit();
        System.out.println("Test end");
    }
}

package com.finalProject.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.finalProject.util.CityMapper;
import com.finalProject.util.SmsCodeHolder;

import jakarta.annotation.PreDestroy;

@Service
public class AutoCreateOrderService {

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	private final String USERNAME = "altons093185@gmail.com";
	private final String PASSWORD = "aP123817346";

	private final String LOGIN_URL = "https://www.costco.com.tw/login";
	private final String PRODUCT_URL = "https://www.costco.com.tw/p/153145";

	Integer cityId = CityMapper.getCityIdByName(""); // cityNameFromFrontend
	Integer zipCode;

//	@PostConstruct
	public void runOnStartup() {
		try {
			setupDriver();
			login();
			visitProductPageAndAddToCart();

			clickViewCart();
			Thread.sleep(1000);

			clickProceedToCheckout();
			Thread.sleep(1000);

			clickChangeAddress();
			Thread.sleep(1000);

			clickAddNewAddress();
			Thread.sleep(1000);

			fillInNewAddressForm();
			Thread.sleep(1000);

			fillInCVV();
			Thread.sleep(1000);

//			clickSubmitPayment();
			Thread.sleep(1000);
//			waitForOtpAndFillIn();
			Thread.sleep(1000);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setupDriver() {
//		System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver"); // ← 修改這行
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.addArguments("user-data-dir=C:/使用者/alton/AppData/Local/Google/Chrome/User Data");
		options.addArguments("profile-directory=Profile 1"); // 這是預設的個人資料名稱

		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(4));
		js = (JavascriptExecutor) driver;
	}

	public void login() {
		try {
			driver.get(LOGIN_URL);
			System.out.println("🔐 開始登入...");

			// 填入帳號密碼
			WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_username")));
			WebElement passwordInput = driver.findElement(By.id("j_password"));
			emailInput.sendKeys(USERNAME);
			passwordInput.sendKeys(PASSWORD);

			// 點擊登入按鈕
			WebElement loginBtn = driver.findElement(By.id("loginSubmit"));
			loginBtn.click();

			// 等待跳轉
			Thread.sleep(4000);
			System.out.println("✅ 登入成功");

		} catch (Exception e) {
			System.out.println("❌ 登入失敗");
			e.printStackTrace();
			return;
		}
	}

	public void visitProductPageAndAddToCart() {
		try {
			driver.get(PRODUCT_URL);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
			Thread.sleep(1000);

			// 點掉 GDPR 同意按鈕（如果有）
			try {
				WebElement acceptAllBtn = wait
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.gdpr-accept")));
				acceptAllBtn.click();
				System.out.println("✅ 點擊 GDPR 同意按鈕");
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println("❕ 沒有出現 GDPR，同意按鈕，跳過");
			}

			// 點擊加入購物車按鈕
			WebElement addToCartButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));

			try {
				addToCartButton.click();
				System.out.println("✅ 已點擊加入購物車");
			} catch (ElementClickInterceptedException e) {
				js.executeScript("arguments[0].click();", addToCartButton);
				System.out.println("✅ 改用 JS 點擊加入購物車");
			}

		} catch (Exception e) {
			System.out.println("❌ 加入購物車流程失敗");
			e.printStackTrace();
			return;
		}
	}

	// ✅ 點擊「查看購物車」
	public void clickViewCart() {
		try {
			WebElement viewCartBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("addToCartPopupCheckoutBtn")));
			viewCartBtn.click();
			System.out.println("✅ 已點擊『查看購物車』");
		} catch (Exception e) {
			System.out.println("❌ 點擊『查看購物車』失敗，改用 JS");
			js.executeScript("document.getElementById('addToCartPopupCheckoutBtn').click();");
		}
	}

	// ✅ 點擊「前往結帳」
	public void clickProceedToCheckout() {
		try {
			WebElement checkoutBtn = wait.until(ExpectedConditions
					.elementToBeClickable(By.cssSelector("button.btn.checkoutButton.btn-place-order")));
			checkoutBtn.click();
			System.out.println("✅ 已點擊『前往結帳』");
		} catch (Exception e) {
			System.out.println("❌ 點擊『前往結帳』失敗，改用 JS");
			js.executeScript("document.querySelector('button.btn.checkoutButton.btn-place-order').click();");
		}
	}

	// ✅ 點擊「請確認地址是否正確 → 點此」
	public void clickChangeAddress() {
		try {
			WebElement changeAddressBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.js-change-shipping-address-btn")));
			changeAddressBtn.click();
			System.out.println("✅ 已點擊『更改地址』");
		} catch (Exception e) {
			System.out.println("❌ 點擊『更改地址』失敗，改用 JS");
			js.executeScript("document.querySelector('a.js-change-shipping-address-btn').click();");
		}
	}

	// ✅ 點擊「新增其他地址」
	public void clickAddNewAddress() {
		try {
			WebElement addNewAddressBtn = wait.until(ExpectedConditions
					.elementToBeClickable(By.cssSelector("a.js-edit-address-item[data-address-id='12130812610734']")));
			addNewAddressBtn.click();
			System.out.println("✅ 已點擊『新增其他地址』");
		} catch (Exception e) {
			System.out.println("❌ 點擊『新增其他地址』失敗，改用 JS");
			js.executeScript("document.querySelector('a.js-add-new-address-btn').click();");
		}
	}

	public void fillInNewAddressForm() {
		try {
			System.out.println("📝 開始填寫地址...");

			// 姓名
			WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName1")));
			nameInput.clear();
			nameInput.sendKeys("吳冠諭");

			// 電話
			WebElement phoneInput = driver.findElement(By.id("phone1"));
			phoneInput.clear();
			phoneInput.sendKeys("0980938534");

			// 選擇縣市（新北市 value="2"）
			WebElement citySelect = driver.findElement(By.id("city"));
			Select cityDropdown = new Select(citySelect);
			cityDropdown.selectByValue("2");

			Thread.sleep(1000); // ⏳ 小延遲讓郵遞區號刷新

			// 選擇郵遞區號（242新莊區）
			WebElement postalSelect = driver.findElement(By.id("postalCode"));
			Select postalDropdown = new Select(postalSelect);
			postalDropdown.selectByValue("242");

			// 地址
			WebElement addressInput = driver.findElement(By.id("homeAddress1"));
			addressInput.clear();
			addressInput.sendKeys("中平路110巷7號4樓");

			Thread.sleep(500); // ⏳ 讓欄位完成填寫

			// 點擊「儲存地址」
			WebElement saveButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("button.js-edit-address-save-button")));
			saveButton.click();

			System.out.println("✅ 已填寫並儲存地址");

		} catch (Exception e) {
			System.out.println("❌ 填寫地址或儲存失敗");
			e.printStackTrace();
			return;
		}
	}

	public void fillInCVV() {
		try {
			WebElement cvvInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cvvNumber")));
			cvvInput.clear();
			cvvInput.sendKeys("060");
			System.out.println("✅ 已填入 CVV");
		} catch (Exception e) {
			System.out.println("❌ 無法填入 CVV");
			e.printStackTrace();
			return;
		}
	}

	public void clickSubmitPayment() {
		try {
			WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("paymentstep-btn-submit")));
			payBtn.click();
			System.out.println("✅ 已點擊『付款』按鈕，訂單送出中...");
		} catch (Exception e) {
			System.out.println("❌ 點擊『付款』失敗");
			e.printStackTrace();
			return;
		}
	}

	public void waitForOtpAndFillIn() {
		try {
			System.out.println("📨 等待簡訊驗證碼中...");
			int retries = 60;
			while (retries-- > 0) {
				String identifierLetter = SmsCodeHolder.getIdentifier(); // 英文驗證碼
				String verificationCode = SmsCodeHolder.getCode(); // 數字驗證碼

				if (verificationCode != null && identifierLetter != null) {
					WebElement radioBtn = driver
							.findElement(By.cssSelector("input[name='identifier'][value='" + identifierLetter + "']"));
					radioBtn.click();

					WebElement otpInput = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("challengeValue")));
					otpInput.sendKeys(verificationCode);
					System.out.println("✅ 已填入驗證碼：" + verificationCode);
				}
				Thread.sleep(1000);
				WebElement submitButton = driver.findElement(By.id("btnVerifySubmit"));
				submitButton.click();
				System.out.println("✅ 已送出驗證碼");
			}
		} catch (Exception e) {
			System.out.println("❌ 60 秒內未收到驗證碼");
			e.printStackTrace();
			return;
		}

	}

	@PreDestroy
	public void cleanup() {
		if (driver != null) {
			driver.quit();
		}
	}
}

package com.finalProject.service;

import java.time.Duration;
import java.util.List;

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

	public void startAutoProcess(String recipientName, String recipientPhone, String recipientCity,
			String recipientZipCode, String recipientAddress, List<String> productIds) {
		try {
			setupDriver();
			login();
			for (String productId : productIds) {
				visitProductPageAndAddToCart(productId);
				System.out.println("商品ID: " + productId);
			}

			clickViewCart();
			Thread.sleep(1000);

			clickProceedToCheckout();
			Thread.sleep(1000);

			clickChangeAddress();
			Thread.sleep(1000);

			clickAddNewAddress();
			Thread.sleep(1000);

			fillInNewAddressForm(recipientName, recipientPhone, recipientCity, recipientZipCode, recipientAddress);
			Thread.sleep(1000);

			fillInCVV();
			Thread.sleep(1000);

			clickSubmitPayment();
			Thread.sleep(1000);
			waitForOtpAndFillIn();
			Thread.sleep(1000);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自動下單流程失敗", e);
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
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

	public void visitProductPageAndAddToCart(String productIds) {
		try {
			String PRODUCT_URL = "https://www.costco.com.tw/p/" + productIds;
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

	public void fillInNewAddressForm(String recipientName, String recipientPhone, String recipientCity,
			String recipientZipCode, String recipientAddress) {
		try {
			System.out.println("📝 開始填寫地址...");

			// 姓名
			WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName1")));
			nameInput.clear();
			Thread.sleep(1000);
			System.out.println(recipientName);
			nameInput.sendKeys(recipientName);

			// 電話
			WebElement phoneInput = driver.findElement(By.id("phone1"));
			phoneInput.clear();
			Thread.sleep(1000);
			phoneInput.sendKeys(recipientPhone);

			// 選擇縣市（新北市 value="2"）
			String parseToCityId = CityMapper.getCityIdByName(recipientCity); // 新北市=>2
			System.out.println(parseToCityId);
			WebElement citySelect = driver.findElement(By.id("city"));
			Select cityDropdown = new Select(citySelect);
			Thread.sleep(1000);
			cityDropdown.selectByValue(parseToCityId);

			Thread.sleep(1000); // ⏳ 小延遲讓郵遞區號刷新

			// 選擇郵遞區號（242新莊區）
			String parseToZipCode = recipientZipCode.substring(0, 3);
			System.out.println(parseToZipCode);
			WebElement postalSelect = driver.findElement(By.id("postalCode"));
			Select postalDropdown = new Select(postalSelect);
			Thread.sleep(1000);
			postalDropdown.selectByValue(parseToZipCode);

			// 地址
			WebElement addressInput = driver.findElement(By.id("homeAddress1"));
			addressInput.clear();
			Thread.sleep(1000);
			addressInput.sendKeys(recipientAddress);

			Thread.sleep(1000); // ⏳ 讓欄位完成填寫

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
			Thread.sleep(1000);
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
			String identifierLetter = null; // 英文驗證碼
			String verificationCode = null; // 數字驗證碼
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Cardinal-ElementContainer")));
			System.out.println("✅ DOM 中已出現驗證容器");
			// 等待 iframe 出現
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Cardinal-ElementContainer")));
			Thread.sleep(1000);
			WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Cardinal-CCA-IFrame")));

			// 切換進 iframe
			driver.switchTo().frame(iframe);
			System.out.println("✅ 已切換至銀行驗證 iframe");

			Thread.sleep(1000);
			WebElement sendSMSBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnVerifySubmit")));
			sendSMSBtn.click();
			System.out.println("✅ 已送出驗證碼");

			System.out.println("📨 等待簡訊驗證碼中...");
			int retries = 60;
			while (retries-- > 0) {
				identifierLetter = SmsCodeHolder.getIdentifier(); // 英文驗證碼
				verificationCode = SmsCodeHolder.getCode(); // 數字驗證碼
				System.out.println("set英文= " + identifierLetter);
				System.out.println("set數字= " + verificationCode);
				if (verificationCode != null && identifierLetter != null) {
					WebElement radioBtn = wait.until(ExpectedConditions.elementToBeClickable(
							By.cssSelector("input[name='identifier'][value='" + identifierLetter + "']")));
					radioBtn.click();

					WebElement otpInput = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("challengeValue")));
					otpInput.sendKeys(verificationCode);
					System.out.println("✅ 已填入驗證碼：" + verificationCode);
					break;
				}
				Thread.sleep(1000);
			}

			if (verificationCode == null || identifierLetter == null) {
				throw new RuntimeException("未能在 60 秒內獲取驗證碼");
			}
			WebElement sendVerifyCodeBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("btnVerifySubmit")));
//			sendVerifyCodeBtn.click();

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

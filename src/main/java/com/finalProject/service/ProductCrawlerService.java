package com.finalProject.service;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.finalProject.model.dto.ProductDto;
import com.finalProject.model.entity.Category;
import com.finalProject.model.entity.Product;
import com.finalProject.repository.CategoryRepository;
import com.finalProject.repository.ProductRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class ProductCrawlerService {

	private final ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	public ProductCrawlerService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@PostConstruct
	public void testRun() {
		crawlCostcoHotBuys();
		//		shouldFindExistingProductById();
	}

	public void shouldFindExistingProductById() {

		List<String> productIds = new ArrayList<>();
		productIds.add("1010656");
		//		Map<String, Element> productIdToElement = new HashMap<>();

		List<ProductDto> existingProducts = productRepository.findByProductIdIn(productIds);

		System.out.println(existingProducts);
		System.out.println("hi");
	}

	// 每天早上8點抓一次
	@Scheduled(cron = "0 0 8 * * ?")
	@Transactional
	public void crawlCostcoHotBuys() {

		int count = 0;
		System.out.println("🚀 開始 Costco 熱門優惠爬蟲");

		// System.setProperty("webdriver.chrome.driver",
		// "C:/chromedriver/chromedriver.exe"); // 改成你自己的路徑
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");

		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		int page = 0;
		String url;
		try {
			while (true) {
				if (page == 0) {
					url = "https://www.costco.com.tw/c/hot-buys";
					// url = "https://www.costco.com.tw/c/hot-buys";
				} else {
					url = "https://www.costco.com.tw/c/hot-buys?page=" + page;
				}

				System.out.println("🌀 爬取第 " + (page + 1) + " 頁：" + url);

				driver.get(url);

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-list-item")));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("discount-row-message")));
				} catch (TimeoutException e) {
					//					System.out.println(e.getMessage());
					System.out.println("❌ 無商品內容，結束爬蟲！");
					break;
				}

				Thread.sleep(6000); // 保險等待

				String html = driver.getPageSource();
				Document doc = Jsoup.parse(html);
				Elements items = doc.select(".product-list-item");

				if (items.isEmpty()) {
					System.out.println("✅ 第 " + page + " 頁無商品，爬蟲結束");
					break;
				}

				List<Product> productsToSave = new ArrayList<>();
				Optional<Category> categoryOpt = categoryRepository.findByNameEn("hot-buys");
				Integer categoryId = categoryOpt.get().getCategoryId();
				Category category = categoryOpt.get();

				for (Element item : items) {
					String nameZh = item.select(".js-lister-name .notranslate").text();
					String nameEn = item.select(".lister-name-en").text();
					String imageUrl = item.select(".product-image img").attr("src");

					String unitPrice = item.select(".product-price-pre-unit-amount").text();

					// 原價與實付價
					String currentPriceText = item.select(".original-price .notranslate").text(); // ex: "$429"
					String discountPriceText = item.select(".discount-row-message").text(); // ex: "商品已折價 $110"
					boolean stockStatus = item.select(".stock-status .out-of-stock-message").isEmpty(); // ex: "庫存充足"

					int discountPrice = parsePrice(discountPriceText); // 110
					int currentPrice = parsePrice(currentPriceText); // 429
					// int originalPrice = currentPrice + discount; // 539

					// 產品 ID 從 href 取出（ex: /p/107056）
					String href = item.select("a.js-lister-name").attr("href");
					String productId = extractProductId(href); // 107056

					Product product = new Product();
					product.setProductId(productId);
					product.setNameZh(nameZh);
					product.setNameEn(nameEn);
					product.setImgUrl(imageUrl);
					// product.setOriginalPrice(originalPrice);
					product.setDiscountAmount(discountPrice);
					product.setCurrentPrice(currentPrice);
					;
					product.setUnitPrice(unitPrice);

					LocalDateTime now = LocalDateTime.now();
					product.setCreatedAt(now);
					product.setIsActive(true);
					product.setIsInStock(stockStatus);
					product.setLastSeenAt(now);
					product.setSourceUrl("https://www.costco.com.tw/p/" + productId);
					product.setCategories(Set.of(category));

					System.out.println("===========");
					System.out.println("商品 ID: " + productId);
					// System.out.println("商品名稱(中): " + nameZh);
					// System.out.println("商品名稱(英): " + nameEn);
					// System.out.println("商品圖片: " + imageUrl);
					// System.out.println("單價: " + unitPrice);
					// System.out.println("原價: " + originalPrice);
					System.out.println("折扣: " + discountPrice);
					// System.out.println("小計: " + currentPrice);
					System.out.println("庫存狀態: " + stockStatus);
					count++;

					productsToSave.add(product);

					System.out.println("已加入商品：" + product.getNameZh());
				}
				page++;
				productRepository.saveAll(productsToSave);
				System.out.println("目前商品數量: " + count);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
			System.out.println("🚀 爬蟲結束，共抓取 " + count + " 件商品");
		}
	}

	public void crawlCostcoWhatsNew() {

	}

	// 把 "$539"、"商品已折價 $110" → 轉成整數
	private int parsePrice(String text) {
		try {
			return Integer.parseInt(text.replaceAll("[^\\d]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	// 從 href 中提取 productId，例如 "/p/107056" → "107056"
	private String extractProductId(String href) {
		if (href == null || !href.contains("/p/"))
			return "";
		return href.substring(href.lastIndexOf("/p/") + 3);
	}
}

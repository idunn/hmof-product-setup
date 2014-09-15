import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

System.setProperty('phantomjs.binary.path', 'C:\\Java\\phantomjs.exe')
driver = {new PhantomJSDriver(new DesiredCapabilities())}

/*File file = new File("C:/Java/chrome/chromedriver.exe")
System.setProperty('webdriver.chrome.driver', file.getAbsolutePath())
DesiredCapabilities capabilities = DesiredCapabilities.chrome()
capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"))
//WebDriver driver = new ChromeDriver()
ChromeOptions options = new ChromeOptions()
options.addArguments("--test-type")
capabilities.setCapability("chrome.binary",file.getAbsolutePath())
capabilities.setCapability(ChromeOptions.CAPABILITY, options)
driver = new ChromeDriver(capabilities)
//WebDriver driver = new ChromeDriver(options)
*/

reportsDir = "target/geb-reports"

waiting {
	timeout = 100
	retryInterval =1
}
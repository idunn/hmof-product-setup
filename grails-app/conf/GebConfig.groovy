import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

//System.setProperty('phantomjs.binary.path', 'C:\\Java\\phantomjs.exe')
//driver = {new PhantomJSDriver(new DesiredCapabilities())}
reportsDir = "target/geb-reports"

waiting {
	timeout = 100
	retryInterval =1
}
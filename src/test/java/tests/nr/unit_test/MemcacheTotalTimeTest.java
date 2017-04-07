package tests.nr.unit_test;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-test-config.xml")
@ImportResource("classpath:log4j2-test.xml")
@PropertySource("classpath:app.properties")
public class MemcacheTotalTimeTest {


	
	final String webExternalCallCountPath = "src/test/resources/json_mocks/web_external_call_count.json";
	final String webExternalAverageTimePath = "src/test/resources/json_mocks/web_external_average_time.json";
	final String httpDispatcherPath = "src/test/resources/json_mocks/httpDispatcher_all_count.json";

	

	@Before
	public void readMockDataFromFiles() {
		
		

		try {
			FileInputStream webExternalAverageTimeFile = new FileInputStream(webExternalAverageTimePath);
			FileInputStream webExternalCallCountFile = new FileInputStream(webExternalCallCountPath);
			FileInputStream httpDispatcherFile = new FileInputStream(httpDispatcherPath);
			IOUtils.toString(webExternalAverageTimeFile, Charset.forName("UTF-8"));
			
			
		} catch (IOException e) {
			fail(e.getMessage());

		}

	}

	/**
	 * Test for calculating Memcache total time with this formula
	 * (webExternalAverageTime*webExternalCallCount)/ httpDispatcherCallCount
	 */
	@Test
	public void memcacheTotalTimeAggregation_success() {
		
		
	}
}

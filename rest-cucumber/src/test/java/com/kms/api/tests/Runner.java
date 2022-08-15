package com.kms.api.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = {"com.kms.api"},
    tags = "@product",
    plugin = {
      "pretty",
      "html:test-output.html",
      "json:target/cucumber-report/cucumber.json",
      "me.jvt.cucumber.report.PrettyReports:target/maven-cucumber-report"
    },
    dryRun = false,
    monochrome = true,
    snippets = CucumberOptions.SnippetType.CAMELCASE)
public class Runner {

  @BeforeClass
  public static void setUpClass() {
    //    ch.qos.logback.classic.Logger root =
    //        (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger("org.apache.http");
    //    root.setLevel(ch.qos.logback.classic.Level.INFO);
  }

  @SuppressWarnings("unchecked")
  @AfterClass
  public static void tearDown() {
    File reportOutputDirectory = new File("target/maven-cucumber-report");
    List jsonFiles = new ArrayList<>();
    jsonFiles.add("target/cucumber-report/cucumber.json");
    Configuration configuration = new Configuration(reportOutputDirectory, "Laptop Bag App");
    configuration.setBuildNumber("0.01");
    configuration.addClassifications("Environment", "LOCAL");
    configuration.addClassifications("Platform", System.getProperty("os.name").toUpperCase());
    configuration.setSortingMethod(SortingMethod.NATURAL);
    configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
    configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));
    ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
    reportBuilder.generateReports();
  }
}

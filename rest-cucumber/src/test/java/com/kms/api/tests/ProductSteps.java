package com.kms.api.tests;

import com.kms.api.model.LaptopBag;
import com.kms.api.requests.RequestFactory;
import com.kms.api.util.RequestBuilder;
import com.kms.api.util.ValidationUtil;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

import static com.kms.api.util.RestUtil.mapRestResponseToPojo;

public class ProductSteps extends TestBase {

  private String path = "";
  private Object requestPayload;
  private Object invalidRequestPayload;
  private LaptopBag reqAddLaptop;
  private LaptopBag reqUpdateLaptop;
  private LaptopBag resAddLaptop;
  private LaptopBag resUpdateLaptop;
  private int id;
  private Response res;

  @Given("^the path \"([^\"]*)\" to the endpoint$")
  public void thePathToAddTheProduct(String path) {
    this.path = path;
  }

  @And(
      "^the payload of the request with BrandName as \"([^\"]*)\", Features as \"([^\"]*)\", LaptopName as \"([^\"]*)\"$")
  public void thePayloadOfTheRequestWithBrandNameAsFeaturesAsLaptopNameAs(
      String brandName, String feature, String laptopName) {
    String[] array = feature.split(",");
    List<String> lst = Arrays.asList(array);
    id = (int) (Math.random() * 10000);
    requestPayload = RequestBuilder.requestPayload(laptopName, brandName, id, lst);
  }

  @When("^I perform the request to add new product$")
  public void iPerformTheRequestToApplication() {
    reqAddLaptop = (LaptopBag) requestPayload;
    res = RequestFactory.addProduct(path, reqAddLaptop);
    resAddLaptop = mapRestResponseToPojo(res, LaptopBag.class);
  }

  @Then("^the status code \"([^\"]*)\" should return$")
  public void theStatusCodeShouldReturn(String statusCode) {
    ValidationUtil.validateStatusCode(res, Integer.parseInt(statusCode));
  }

  @And("^the product is added successfully with an integer Id$")
  public void theProductIsAddedSuccessfullyWithAnIntegerId() {
    ValidationUtil.validateStringEqual(resAddLaptop.getId(), id);
  }

  @But("^I supply invalid json payload$")
  public void theInvalidPayloadOfTheRequest() {
    invalidRequestPayload = "";
  }

  @When("^I perform the request to add the invalid product$")
  public void iPerformTheInvalidRequestToApplication() {
    res = RequestFactory.addProduct(path, invalidRequestPayload);
  }

  @When("I perform the PUT request with id and BrandName as \"([^\"]*)\", Features as \"([^\"]*)\", LaptopName as \"([^\"]*)\"$")
  public void iPerformThePUTRequestWithIdAndBrandNameAsFeaturesAsLaptopNameAs(String brandName, String feature, String laptopName) {
    String[] array = feature.split(",");
    List<String> lst = Arrays.asList(array);
    requestPayload = RequestBuilder.requestPayload(laptopName, brandName, id, lst);

    reqUpdateLaptop = (LaptopBag) requestPayload;
    res = RequestFactory.updateProduct(path, reqUpdateLaptop);
    resUpdateLaptop = mapRestResponseToPojo(res, LaptopBag.class);
  }

  @And("Details should get updated")
  public void detailsShouldGetUpdated() {
    ValidationUtil.validateStringEqual(resUpdateLaptop.getId(),id);
    ValidationUtil.validatePojoObjects(reqUpdateLaptop,resUpdateLaptop);
  }
}

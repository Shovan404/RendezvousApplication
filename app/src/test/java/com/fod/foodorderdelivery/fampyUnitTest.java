package com.fod.foodorderdelivery;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.BLL.Feedbacks;
import com.fod.foodorderdelivery.BLL.Orders;
import com.fod.foodorderdelivery.BLL.Users;
import com.fod.foodorderdelivery.Model.Feedback;
import com.fod.foodorderdelivery.ServerResponse.UserResponse;
import com.fod.foodorderdelivery.URL.URL;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class fampyUnitTest {
    URL url = new URL();
    Feedbacks feedbacks = new Feedbacks();
    Orders orders = new Orders();
    Users users = new Users();

    @Rule
    public Timeout globalTimeout = Timeout.seconds(500000);

    @Before
    public void beforeTest() {
        url = new URL();
        feedbacks = new Feedbacks();
        orders = new Orders();
        users = new Users();
    }

    @Test
    public void testOrder() {
        orders.foodId = "foodid";
        orders.foodName = "foodName";
        orders.quantity = 2;
        orders.price = 100;
        boolean result = orders.order();
        assertEquals(true, result);
    }

    @Test
    public void testVAT() {
        double result = orders.calculateVAT(100);
        assertEquals("13.00", String.format("%.2f", result));
    }

    @Test
    public void testDeliveryCost() {
        double result = orders.calculateDeliveryCost(100);
        assertEquals("7.00", String.format("%.2f", result));
    }

    @Test
    public void testNetTotal() {
        double result = orders.calculateNetTotal(100);
        assertEquals("120.00", String.format("%.2f", result));
    }

    @Test
    public void testLogin() {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<UserResponse> usersCall = fampyAPI.checkUser("shovan@gmail.com", "shovan1234");
        try {
            Response<UserResponse> response = usersCall.execute();
            String token = response.body().getToken();
            assertThat(token, is(IsNull.notNullValue()));
            url.token = "Bearer " + token;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void testLoginFail() {
        boolean result = users.userAuthentication("shovan@gmail.com", "wrongpassword");
        assertEquals(false, result);
    }

    @Test
    public void checkPassword() {
        boolean result = users.checkPassword("shovan1234");
        assertEquals(true, result);
    }

    @Test
    public void checkPasswordFail() {
        boolean result = users.checkPassword("wrongpassword");
        assertEquals(false, result);
    }

    @Test
    public void testPasswordChange() {
        boolean result = users.changePassword("shocan123456");
        assertEquals(true, result);
    }

    @Test
    public void testPhoneNumber() {
        boolean result = users.isValidPhone("11234234");
        assertEquals(true, result);
    }

    @Test
    public void testPhoneNumberFail() {
        boolean result = users.isValidPhone("1000000000000000");
        assertEquals(false, result);
    }

    @Test
    public void testFeedback() {
        boolean result = feedbacks.sendFeedback
                (new Feedback("feedback@gmail.com", "feedback message"));
        assertEquals(true, result);
    }


    @After
    public void afterTest() {
        url = null;
        feedbacks = null;
        orders = null;
        users = null;
    }


}

// Package was needed to run mocking
package toRunMocking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    private void getRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time () {
        Restaurant rSpy = Mockito.spy(restaurant);
        Mockito.when(rSpy.getCurrentTime()).thenReturn(LocalTime.of(10, 30, 0));

        boolean isOpen = rSpy.isRestaurantOpen();

        assertTrue(isOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time () {
        Restaurant rSpy = Mockito.spy(restaurant);

        // Assuming that the restaurant is closed at the closing time
        Mockito.when(rSpy.getCurrentTime()).thenReturn(LocalTime.of(22, 0, 0));

        boolean isOpen = rSpy.isRestaurantOpen();

        assertFalse(isOpen);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        Assertions.assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        Assertions.assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class, () ->
                restaurant.removeFromMenu("French fries")
        );
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /** Placed in this class to show that calculateTotalOrderValue will be a part of the restaurants daily activity */
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>BILLING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void calculateTotalOrderValue_when_no_item_is_passed_should_return_0 () {
        //Arrange
        List <String> orderItems = new ArrayList<>();

        //Act
        int orderValue = this.restaurant.calculateTotalOrderValue(orderItems);

        //Assert
        assertThat(0, is(orderValue));
    }

    @Test
    public void calculateTotalOrderValue_when_items_passed_should_return_the_total_bill_amount() {
        /*  restaurantMenu = [{ "Sweet corn soup", 119 }, { "Vegetable lasagne", 269 }] */
        //Arrange
        this.restaurant.addToMenu("Fruit Juice", 20);
        this.restaurant.addToMenu("Waffles", 100);

        List <String> orderItems = new ArrayList<>();
        orderItems.add("Sweet corn soup");
        orderItems.add("Waffles");

        //Act
        int orderValue = this.restaurant.calculateTotalOrderValue(orderItems);

        //Assert
        /* Price of "Sweet corn soup" + "Waffles" i.e. 119 + 100 from the mocked object */
        assertThat(119 + 100, is(orderValue));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<BILLING>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
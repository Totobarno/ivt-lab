package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore store1;
  private TorpedoStore store2;

  @BeforeEach
  public void init(){
    store1 = mock(TorpedoStore.class);
    store2 = mock(TorpedoStore.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    this.ship = new GT4500(store1, store2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(store1.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(store1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(store1.fire(1)).thenReturn(true);
    when(store2.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(store1, times(1)).fire(1);
    verify(store2, times(1)).fire(1);
  }

  @Test
  public void firstFire_From_Store1(){
    //Arrange
    when(store1.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    verify(store1, times(1)).fire(1);
    assertTrue(result);
  }

  @Test
  public void secondFire_From_Store2(){
    //Arrange
    when(store2.fire(1)).thenReturn(true);

    //Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    verify(store2, times(1)).fire(1);
  }

  @Test
  public void store_Failure(){
    //Arrange
    when(store1.fire(1)).thenReturn(false);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    verify(store2, times(0)).fire(1);
    assertFalse(result);
  }

  @Test
  public void store1_Empty(){
    //Arrange
    when(store1.isEmpty()).thenReturn(false);
    when(store2.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    verify(store1, times(0)).fire(1);
    verify(store2, times(1)).fire(1);
    assertTrue(result);
  }

  @Test
  public void both_Store_Fire_One_Empty(){
    //Arrange
    when(store1.fire(1)).thenReturn(true);
    when(store2.isEmpty()).thenReturn(false);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    verify(store1, times(1)).fire(1);
    verify(store2, times(0)).fire(1);
    assertTrue(result);
  }

}

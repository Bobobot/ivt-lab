package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  TorpedoStore primaryStore;
  TorpedoStore secondaryStore;

  @BeforeEach
  public void init(){
    primaryStore = mock(TorpedoStore.class);
    secondaryStore = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryStore, secondaryStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Fail() {
    // Arrange
    when (primaryStore.fire(1)).thenReturn(false);
    when (secondaryStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Primary_All_Success() {
    // Arrange
    when (primaryStore.fire(1)).thenReturn(false);
    when (secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Secondary_All_Success() {
    // Arrange
    when (primaryStore.fire(1)).thenReturn(true);
    when (secondaryStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Primary_Secondary_All_Success() {
    // Arrange
    when (primaryStore.fire(1)).thenReturn(false);
    when (secondaryStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Secondary_Single_Success() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when (primaryStore.fire(1)).thenReturn(true);
    when (secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);

    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Secondary_Single_Success2() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.isEmpty()).thenReturn(true);
    when (primaryStore.fire(1)).thenReturn(true);
    when (secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    assertEquals(false, ship.fireLaser(FiringMode.SINGLE));

    verify(primaryStore, times(2)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_PrimaryEmpty(){
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(true);
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_SecondaryEmpty(){
    // Arrange
    when(secondaryStore.isEmpty()).thenReturn(true);
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_SecondaryFull(){
    // Arrange
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.isEmpty()).thenReturn(true);
    when(primaryStore.fire(1)).thenReturn(false);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_PrimaryFull(){
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

}

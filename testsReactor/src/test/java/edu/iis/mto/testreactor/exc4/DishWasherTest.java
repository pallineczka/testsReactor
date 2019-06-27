package edu.iis.mto.testreactor.exc4;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class DishWasherTest {

    WaterPump waterPump;
    Engine engine;
    DirtFilter dirtFilter;
    Door door;
    DishWasher dishWasher;

    @Before
    public void initialize(){

        waterPump = mock(WaterPump.class);
        engine = mock(Engine.class);
        dirtFilter = mock(DirtFilter.class);
        door = mock(Door.class);

        dishWasher = new DishWasher(waterPump, engine, dirtFilter, door);
    }

    @Test
    public void TestDishWasherWithECOWashingProgramAndTabletsUsed(){

        ProgramConfiguration program = ProgramConfiguration.builder()
                                                           .withProgram(WashingProgram.ECO)
                                                           .withTabletsUsed(true)
                                                           .build();
        Mockito.when(door.closed()).thenReturn(false);
        Mockito.when(dirtFilter.capacity()).thenReturn(60.0d);

        dishWasher.start(program);
    }

    @Test (expected = PumpException.class)
    public void TestDishWasherWithECOWashingProgramAndTabletsNotUsed(){

        ProgramConfiguration program = ProgramConfiguration.builder()
                                                           .withProgram(WashingProgram.INTENSIVE)
                                                           .withTabletsUsed(false)
                                                           .build();
        Mockito.when(door.closed()).thenReturn(false);
        Mockito.when(dirtFilter.capacity()).thenReturn(5.0d);
        //Mockito.when(waterPump.pour(WashingProgram.INTENSIVE)).thenReturn(ArgumentMatchers.any());

        dishWasher.start(program);
    }

    @Test
    public void TestProgramShouldHaveTheSameWashingProgram(){

        ProgramConfiguration program = ProgramConfiguration.builder()
                                                           .withProgram(WashingProgram.INTENSIVE)
                                                           .withTabletsUsed(false)
                                                           .build();
        Mockito.when(door.closed()).thenReturn(false);
        Mockito.when(dirtFilter.capacity()).thenReturn(5.0d);

        assertEquals(program.getProgram(), WashingProgram.INTENSIVE);
    }

    @Test
    public void TestProgramIsNotTabletsUsed(){

        ProgramConfiguration program = ProgramConfiguration.builder()
                                                           .withProgram(WashingProgram.INTENSIVE)
                                                           .withTabletsUsed(false)
                                                           .build();
        Mockito.when(door.closed()).thenReturn(false);
        Mockito.when(dirtFilter.capacity()).thenReturn(5.0d);

        assertEquals(program.isTabletsUsed(), false);
    }
}

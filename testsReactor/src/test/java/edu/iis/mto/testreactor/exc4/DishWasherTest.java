package edu.iis.mto.testreactor.exc4;

import static edu.iis.mto.testreactor.exc4.Status.DOOR_OPEN;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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

    RunResult result;

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
        //nie działa wyjątek :(

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

    @Test
    public void TestDoorsOpen(){

        ProgramConfiguration program = ProgramConfiguration.builder()
                                                           .withProgram(WashingProgram.INTENSIVE)
                                                           .withTabletsUsed(false)
                                                           .build();
        Mockito.when(door.closed()).thenReturn(true);
        Mockito.when(dirtFilter.capacity()).thenReturn(5.0d);

        dishWasher.start(program);

        assertEquals(dishWasher.start(program), Status.DOOR_OPEN);
    }

    @Test
    public void TestEngineShouldRunProgramOneTimes(){
        Mockito.when(engine.runProgram(WashingProgram.ECO)).thenReturn(true);
        verify(engine,times(1)).runProgram(WashingProgram.ECO);
    }
}

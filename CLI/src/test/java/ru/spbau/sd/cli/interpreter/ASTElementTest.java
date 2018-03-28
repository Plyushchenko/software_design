package ru.spbau.sd.cli.interpreter;

import org.junit.Test;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.commands.Command;

import java.util.Collections;

public class ASTElementTest {
    @Test
    public void executionTest() {
        Command cmd = Mockito.mock(Command.class);
        ASTElement prev = Mockito.mock(ASTElement.class);
        Mockito.when(prev.execute(Mockito.any(), Mockito.any())).
                thenReturn(ExecutionResult.OK);

        ASTElement element = new ASTElement(cmd, Collections.emptyList());
        element.setPrevious(prev);
        element.execute(null, null);
        Mockito.verify(cmd).run(Mockito.eq(Collections.emptyList()), Mockito.any(),
                Mockito.any());
        Mockito.verify(prev).execute(Mockito.any(), Mockito.any());
    }
}

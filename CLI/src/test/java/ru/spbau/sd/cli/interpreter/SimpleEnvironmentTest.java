package ru.spbau.sd.cli.interpreter;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;

public class SimpleEnvironmentTest {
    @Test
    public void setGetTest() {
        final String varName = "name";
        final String varValue = "value";
        Environment environment = new SimpleEnvironment();
        environment.set(varName, varValue);
        Assert.assertEquals(varValue, environment.get(varName));
    }

    @Test
    public void emptyTest() {
        final String key = "key";
        final String empty = "";
        Environment environment = new SimpleEnvironment();
        Assert.assertEquals(empty, environment.get(key));
    }
}

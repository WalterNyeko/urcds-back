package com.sweroad.webapp.decorator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sweroad.model.Crash;

@RunWith(PowerMockRunner.class)
public class CrashDecoratorTest {

	private String crashActions;
	private Crash crash;
	private CrashDecorator crashDecorator;

	@Before
	public void setUp() throws Exception {
		crashDecorator = PowerMockito.spy(new CrashDecorator());
		crash = new Crash();
		crash.setId(1L);
		crashActions = "<a href=\"crashview?id=1\" alt=\"View crash\">View</a> | ";
		crashActions += "<a href=\"crashform?id=1\" alt=\"Edit crash\">Edit</a> | ";
		crashActions += "<a href=\"crashremove?id=1\" alt=\"Remove crash\" onclick=\"return confirm('Remove crash?');\">Remove</a>";

		PowerMockito.doCallRealMethod().when(crashDecorator).getActions();
		//PowerMockito.doReturn(crashActions).when(crashDecorator).getCurrentRowObject();
	}

	@Test
	public void testThatCrashActionLinksAreCorrect() {
		//assertEquals(crashActions, crashDecorator.getActions());
	}
}

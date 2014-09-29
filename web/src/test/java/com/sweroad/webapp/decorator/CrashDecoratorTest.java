package com.sweroad.webapp.decorator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sweroad.model.Crash;

@RunWith(PowerMockRunner.class)
public class CrashDecoratorTest {

	//private String crashActions;
	private Crash crash;
	private CrashDecorator crashDecorator;

	@Before
	public void setUp() throws Exception {
		crashDecorator = PowerMockito.spy(new CrashDecorator());
		crash = new Crash();
		crash.setId(1L);
//		crashActions = "<a href=\"crashview?id=1\"><img src=\"/images/bt_View.gif\" alt=\"View\" title=\"View\" hspace=\"4\" /></a> | ";
//		crashActions += "<a href=\"crashform?id=1\"><img src=\"/images/bt_Edit.gif\" alt=\"Edit\" title=\"Edit\" hspace=\"4\" /></a> | ";
//		crashActions += "<a href=\"crashremove?id=1\" onclick=\"return confirm('Remove crash?');\"><img src=\"/images/bt_Remove.gif\" alt=\"Remove\" title=\"Remove\" hspace=\"4\" /></a>";

		PowerMockito.doCallRealMethod().when(crashDecorator).getActions();
		//PowerMockito.doReturn(crashActions).when(crashDecorator).getCurrentRowObject();
	}

	@Test
	public void testThatCrashActionLinksAreCorrect() {
		//assertEquals(crashActions, crashDecorator.getActions());
	}
}

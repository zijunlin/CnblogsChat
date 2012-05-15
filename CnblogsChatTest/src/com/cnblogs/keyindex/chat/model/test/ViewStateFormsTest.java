package com.cnblogs.keyindex.chat.model.test;

import java.util.Map;

import android.content.Intent;
import android.test.AndroidTestCase;

import com.cnblogs.keyindex.chat.model.ViewStateForms;

public class ViewStateFormsTest extends AndroidTestCase {

	private ViewStateForms state;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		state = new ViewStateForms();
		state.setEvent("eventId");
		state.setEventArg("eventArg");
		state.setEventTaget("eventTaget");
		state.setViewState("ViewState");
	}

	public void test_Can_Parcel() {

		Intent intent = new Intent();
		intent.putExtra(ViewStateForms.INTENT_EXTRA_KEY, state);

		ViewStateForms expect = intent
				.getParcelableExtra(ViewStateForms.INTENT_EXTRA_KEY);
		assertNotNull(expect);
		assertEquals(expect.getEvent(), state.getEvent());
		assertEquals(expect.getEventArg(), state.getEventArg());
		assertEquals(expect.getEventTaget(), state.getEventTaget());
		assertEquals(expect.getViewState(), state.getViewState());
	}

	public void test_Can_GetForms() {
		Map<String, String> expected = state.getForms();
		assertNotNull(expected);
		assertEquals(expected.get(ViewStateForms.EVENT_KEY), state.getEvent());
		assertEquals(expected.get(ViewStateForms.EVENT_ARG_KEY),
				state.getEventArg());
		assertEquals(expected.get(ViewStateForms.EVENT_TAGET_KEY),
				state.getEventTaget());
		assertEquals(expected.get(ViewStateForms.STATE_KEY),
				state.getViewState());

	}

	
}

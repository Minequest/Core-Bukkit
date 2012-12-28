/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MQTest.Core.EventsAPI;

import org.junit.Before;
import org.junit.Test;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Events.QuestEvent;

public class EventManagerTest {
	


	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testRegisterEvent() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetNewEvent() {
		//fail("Not yet implemented");
	}
	
	private class TestEvent extends QuestEvent {

		private long milliseconds;
		private long initialmilliseconds;
		
		/*
		 * (non-Javadoc)
		 * @see com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
		 * Basic Quest Event:
		 * [0]: delay in milliseconds
		 * [1]: task to trigger
		 */
		@Override
		public void parseDetails(String[] details) {
			milliseconds = 1000;
			initialmilliseconds = System.currentTimeMillis();
		}

		@Override
		public boolean conditions() {
			if ((System.currentTimeMillis()-initialmilliseconds)<milliseconds)
				return false;
			return true;
		}

		@Override
		public CompleteStatus action() {
			return CompleteStatus.SUCCESS;
		}

		@Override
		public Integer switchTask() {
			return null;
		}
		
	}

}

/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.ide.syncing.ui.old.views;

import org.eclipse.core.runtime.ListenerList;

import com.aptana.core.CorePlugin;
import com.aptana.core.io.IConnectionPoint;
import com.aptana.ide.syncing.core.old.ISmartSyncListener;
import com.aptana.ide.syncing.core.old.SmartSyncEvent;
import com.aptana.ide.syncing.core.old.VirtualFileSyncPair;

/**
 * @author Kevin Sawicki (ksawicki@aptana.com)
 */
public class SmartSyncEventManager
{

	private static SmartSyncEventManager manager;

	private ListenerList listeners;

	private SmartSyncEventManager()
	{
		listeners = new ListenerList();
	}

	/**
	 * Adds a listener
	 * 
	 * @param listener
	 */
	public void addListener(ISmartSyncListener listener)
	{
		listeners.add(listener);
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 */
	public void removeListener(ISmartSyncListener listener)
	{
		listeners.remove(listener);
	}

	/**
	 * Fires a smart sync event
	 * 
	 * @param completedPairs
	 * @param sourceManager
	 * @param destinationManager
	 * @param comment
	 */
	public void fireEvent(VirtualFileSyncPair[] completedPairs, IConnectionPoint sourceManager,
			IConnectionPoint destinationManager, String comment)
	{
		SmartSyncEvent event = new SmartSyncEvent();
		event.sourceManager = sourceManager;
		event.destinationManager = destinationManager;
		event.completedPairs = completedPairs;
		event.comment = comment;
		Object[] listens = listeners.getListeners();
		for (int i = 0; i < listens.length; i++)
		{
			if (listens[i] instanceof ISmartSyncListener)
			{
				try
				{
					((ISmartSyncListener) listens[i]).smartSyncComplete(event);
				}
				catch (Exception e)
				{
					CorePlugin.logError(Messages.SmartSyncEventManager_ERR_ExceptionNotifyingSmartSyncListener, e);
				}
				catch (Error e)
				{
					CorePlugin.logError(Messages.SmartSyncEventManager_ERR_ErrorNotifyingSmartSyncListener, e);
				}
			}
		}
	}

	/**
	 * Gets the smart sync event manager
	 * 
	 * @return - event manager
	 */
	public synchronized static SmartSyncEventManager getManager()
	{
		if (manager == null)
		{
			manager = new SmartSyncEventManager();
		}
		return manager;
	}
}

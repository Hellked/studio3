/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.editor.js;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JSPlugin extends AbstractUIPlugin
{
	public static final String PLUGIN_ID = "com.aptana.editor.js"; //$NON-NLS-1$
	private static JSPlugin plugin;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static JSPlugin getDefault()
	{
		return plugin;
	}

	/**
	 * getImage
	 * 
	 * @param path
	 * @return
	 */
	public static Image getImage(String path)
	{
		ImageRegistry registry = plugin.getImageRegistry();
		Image image = registry.get(path);
		if (image == null)
		{
			ImageDescriptor id = getImageDescriptor(path);
			if (id == null)
			{
				return null;
			}
			registry.put(path, id);
			image = registry.get(path);
		}
		return image;
	}

	/**
	 * getImageDescriptor
	 * 
	 * @param path
	 * @return
	 */
	public static ImageDescriptor getImageDescriptor(String path)
	{
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * logError
	 * 
	 * @param msg
	 * @param e
	 */
	public static void logError(String msg, Throwable e)
	{
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, msg, e));
	}

	/**
	 * logInfo
	 * 
	 * @param string
	 */
	public static void logInfo(String string)
	{
		getDefault().getLog().log(new Status(IStatus.INFO, PLUGIN_ID, string));
	}

	/**
	 * logWarning
	 * 
	 * @param msg
	 */
	public static void logWarning(String msg)
	{
		getDefault().getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, msg));
	}

	/**
	 * trace
	 * 
	 * @param string
	 */
	public static void trace(String string)
	{
		if (getDefault() == null || !getDefault().isDebugging())
			return;
		getDefault().getLog().log(new Status(IStatus.OK, PLUGIN_ID, string));
	}

	/**
	 * The constructor
	 */
	public JSPlugin()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;

		Job job = new JSMetadataLoader();
		job.schedule();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		super.stop(context);
	}
}

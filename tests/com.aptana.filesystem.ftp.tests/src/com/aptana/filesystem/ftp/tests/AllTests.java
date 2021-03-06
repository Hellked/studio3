/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.filesystem.ftp.tests;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.aptana.filesystem.ftp.FTPConnectionPointTest;
import com.aptana.testing.categories.IntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({ FTPConnectionPointTest.class, FTPConnectionTest.class, FTPConnectionWithBasePathTest.class, })
@Category({ IntegrationTests.class })
public class AllTests
{
}

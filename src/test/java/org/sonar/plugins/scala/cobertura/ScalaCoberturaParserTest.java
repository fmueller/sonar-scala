/*
 * Sonar Scala Plugin
 * Copyright (C) 2011 Felix Müller
 * felix.mueller.berlin@googlemail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.scala.cobertura;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.sonar.api.resources.Resource;
import org.sonar.plugins.scala.language.ScalaFile;
import org.sonar.plugins.scala.language.ScalaPackage;

public class ScalaCoberturaParserTest {

    private ScalaCoberturaParser underTest;

    @Before
    public void setUp() {
        underTest = new ScalaCoberturaParser();
    }

    @Test
    public void should_create_ScalaFile_resource_when_deep_package() {
        Resource resource = underTest.getResource("com.mock.scalapackage.MockScalaClass");
        assertNotNull(resource);
        assertTrue(resource instanceof  ScalaFile);

        ScalaFile file = (ScalaFile)resource;
        assertEquals("MockScalaClass", file.getName());

        ScalaPackage scalaPackage = file.getParent();
        assertNotNull(scalaPackage);
        assertEquals("com.mock.scalapackage", scalaPackage.getName());
    }

    @Test
    public void should_create_ScalaFile_resource_when_root_package() {
        Resource resource = underTest.getResource("MockScalaClass");
        assertNotNull(resource);
        assertTrue(resource instanceof  ScalaFile);

        ScalaFile file = (ScalaFile)resource;
        assertEquals("MockScalaClass", file.getName());

        ScalaPackage scalaPackage = file.getParent();
        assertNotNull(scalaPackage);
        assertEquals("[default]", scalaPackage.getName());
    }

    // TODO remove this test once the sbt scct plugin is patched to produce the correct class name.
    @Test
    public void should_create_ScalaFile_resource_when_scct_bug() {
        Resource resource = underTest.getResource("src.main.scala.com.mock.scalapackage.MockScalaClass");
        assertNotNull(resource);
        assertTrue(resource instanceof  ScalaFile);

        ScalaFile file = (ScalaFile)resource;
        assertEquals("MockScalaClass", file.getName());

        ScalaPackage scalaPackage = file.getParent();
        assertNotNull(scalaPackage);
        assertEquals("com.mock.scalapackage", scalaPackage.getName());
    }

    @Test
    public void should_create_ScalaFile_resource_when_scct_bug_for_play_app() {
        Resource resource = underTest.getResource("app.com.mock.scalapackage.MockScalaClass");
        assertNotNull(resource);
        assertTrue(resource instanceof  ScalaFile);

        ScalaFile file = (ScalaFile)resource;
        assertEquals("MockScalaClass", file.getName());

        ScalaPackage scalaPackage = file.getParent();
        assertNotNull(scalaPackage);
        assertEquals("com.mock.scalapackage", scalaPackage.getName());
    }

}

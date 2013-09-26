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
package org.sonar.plugins.scala.surefire;

import org.junit.Test;
import org.sonar.api.batch.CoverageExtension;
import org.sonar.api.resources.Project;
import org.sonar.plugins.scala.language.Scala;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SurefireSensorTest {

    private SurefireSensor sensor = new SurefireSensor();

    @Test
    public void should_execute_on_project() {
        Project project = mock(Project.class);
        when(project.getLanguageKey()).thenReturn(Scala.INSTANCE.getKey());
        when(project.getAnalysisType()).thenReturn(Project.AnalysisType.REUSE_REPORTS);
        assertTrue(sensor.shouldExecuteOnProject(project));
        when(project.getAnalysisType()).thenReturn(Project.AnalysisType.DYNAMIC);
        assertTrue(sensor.shouldExecuteOnProject(project));
    }

    @Test
    public void should_not_execute_if_static_analysis() {
        Project project = mock(Project.class);
        when(project.getLanguageKey()).thenReturn(Scala.INSTANCE.getKey());
        when(project.getAnalysisType()).thenReturn(Project.AnalysisType.STATIC);
        assertFalse(sensor.shouldExecuteOnProject(project));
    }

    @Test
    public void should_not_execute_on_java_project() {
        Project project = mock(Project.class);
        when(project.getLanguageKey()).thenReturn("java");
        when(project.getAnalysisType()).thenReturn(Project.AnalysisType.DYNAMIC);
        assertFalse(sensor.shouldExecuteOnProject(project));
    }

    @Test
    public void should_depend_on_coverage_sensors() {
        assertEquals(CoverageExtension.class, sensor.dependsUponCoverageSensors());
    }

    @Test
    public void test_toString() {
        assertEquals("Scala SurefireSensor", sensor.toString());
    }

}
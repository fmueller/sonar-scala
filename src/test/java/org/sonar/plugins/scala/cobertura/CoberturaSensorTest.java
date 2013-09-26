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
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Project;
import org.sonar.plugins.scala.language.Scala;
import org.sonar.test.TestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoberturaSensorTest {

  private CoberturaSensor sensor;

  @Before
  public void setUp() throws Exception {
    sensor = new CoberturaSensor();
  }

  /**
   * See SONARPLUGINS-696
   */
  @Test
  public void should_parse_report() {
    SensorContext context = mock(SensorContext.class);
    sensor.parseReport(TestUtils.getResource("/org/sonar/plugins/scala/cobertura/coverage.xml"), context);
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
  public void test_toString() {
    assertEquals(sensor.toString(), "Scala CoberturaSensor");
  }
}

/*
 * Sonar Scala Plugin
 * Copyright (C) 2011 - 2014 All contributors
 * dev@sonar.codehaus.org
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.CoreProperties;
import org.sonar.api.batch.CoverageExtension;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;
import org.sonar.plugins.scala.language.Scala;
import org.sonar.plugins.surefire.api.AbstractSurefireParser;
import org.sonar.plugins.surefire.api.SurefireUtils;

import java.io.File;

public class SurefireSensor implements Sensor {

    private static final Logger LOG = LoggerFactory.getLogger(SurefireSensor.class);

    @DependsUpon
    public Class<?> dependsUponCoverageSensors() {
        return CoverageExtension.class;
    }

    public boolean shouldExecuteOnProject(Project project) {
        return project.getAnalysisType().isDynamic(true) && Scala.INSTANCE.getKey().equals(project.getLanguageKey());
    }

    public void analyse(Project project, SensorContext context) {
        String path = (String) project.getProperty(CoreProperties.SUREFIRE_REPORTS_PATH_PROPERTY);
        File pathFile = project.getFileSystem().resolvePath(path);
        collect(project, context, pathFile);
    }

    protected void collect(Project project, SensorContext context, File reportsDir) {
        LOG.info("parsing {}", reportsDir);
        SUREFIRE_PARSER.collect(project, context, reportsDir);
    }

    private static final AbstractSurefireParser SUREFIRE_PARSER = new AbstractSurefireParser() {
        @Override
        protected Resource<?> getUnitTestResource(String classKey) {
            String filename = classKey.replace('.', '/') + ".scala";
            org.sonar.api.resources.File sonarFile = new org.sonar.api.resources.File(filename);
            sonarFile.setQualifier(Qualifiers.UNIT_TEST_FILE);
            return sonarFile;
        }
    };

    @Override
    public String toString() {
        return "Scala SurefireSensor";
    }

}

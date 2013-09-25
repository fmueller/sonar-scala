Sonar Scala Plugin
===========
Supports Sonar 3.0+ and requires Cobertura and Surefire plugins.

To include test and coverage reports:

Install these plugins in your scala project:

https://github.com/bseibel/sbt-simple-junit-xml-reporter-plugin
- Creates junit xml reports for output from scalatest.

http://mtkopone.github.io/scct/
- Creates a Scala-friendly code-coverage report, and includes a coberura xml report.


Add the following properties to your project's sonar-project.properties file:

sonar.dynamicAnalysis=reuseReports
sonar.surefire.reportsPath=test-reports
sonar.core.codeCoveragePlugin=cobertura
sonar.java.coveragePlugin=cobertura
sonar.cobertura.reportPath=target/scala-[scala-version]/coverage-report/cobertura.xml

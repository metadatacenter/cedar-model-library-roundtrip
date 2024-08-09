package org.metadatacenter.exportconverter.tools;

import org.metadatacenter.config.CedarConfig;
import org.metadatacenter.config.environment.CedarEnvironmentVariableProvider;
import org.metadatacenter.exportconverter.ExportResourceEnumerator;
import org.metadatacenter.model.SystemComponent;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class ProcessExportGenerateJson {
  public static void main(String[] args) throws IOException {
    System.out.println("Reading config");
    SystemComponent systemComponent = SystemComponent.ADMIN_TOOL;
    Map<String, String> environment = CedarEnvironmentVariableProvider.getFor(systemComponent);
    CedarConfig cedarConfig = CedarConfig.getInstance(environment);

    String sourceDir = cedarConfig.getImportExportConfig().getExportDir();
    System.out.println("Source dir:=>" + sourceDir + "<=");
    String targetDir = Path.of(cedarConfig.getImportExportConfig().getExportConvertedDir(), "logs").toString();
    System.out.println("Target dir:=>" + targetDir + "<=");

    ExportResourceEnumerator enumerator = new ExportResourceEnumerator(sourceDir, targetDir);
    Instant startedAt = Instant.now();
    enumerator.parseAndOutputJson();
    Instant completedAt = Instant.now();
    System.out.println("Parsing started at   " + startedAt);
    System.out.println("Parsing completed at " + completedAt);
    long duration = Duration.between(startedAt, completedAt).getSeconds();
    System.out.println("Parsing took " + duration + " seconds.");

  }

}

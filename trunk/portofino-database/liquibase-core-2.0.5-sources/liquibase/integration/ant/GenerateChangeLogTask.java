package liquibase.integration.ant;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.diff.Diff;
import liquibase.diff.DiffResult;
import org.apache.tools.ant.BuildException;

import java.io.PrintStream;

public class GenerateChangeLogTask extends BaseLiquibaseTask {

	private String diffTypes;
    private String dataDir;

	public String getDiffTypes() {
		return diffTypes;
	}

	public void setDiffTypes(String diffTypes) {
		this.diffTypes = diffTypes;
	}

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    @Override
	public void execute() throws BuildException {
        super.execute();
        
		Liquibase liquibase = null;
		try {
			PrintStream writer = createPrintStream();
			if (writer == null) {
				throw new BuildException("generateChangeLog requires outputFile to be set");
			}

			liquibase = createLiquibase();

			Database database = liquibase.getDatabase();
			Diff diff = new Diff(database, getDefaultSchemaName());
			if (getDiffTypes() != null) {
				diff.setDiffTypes(getDiffTypes());
			}
//			diff.addStatusListener(new OutDiffStatusListener());
			DiffResult diffResult = diff.compare();
            diffResult.setDataDir(getDataDir());

			if (getChangeLogFile() == null) {
				diffResult.printChangeLog(writer, database);
			} else {
				diffResult.printChangeLog(getChangeLogFile(), database);
			}

			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new BuildException(e);
		} finally {
			closeDatabase(liquibase);
		}
	}
}
